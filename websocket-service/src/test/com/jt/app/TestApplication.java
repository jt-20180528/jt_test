package com.jt.app;

import com.jt.app.model.LotteryUser;
import com.jt.app.model.Tenement;
import com.jt.app.model.User;
import com.jt.app.model.op.model.BetOrderTemp;
import com.jt.app.redis.GlobalCacheKey;
import com.jt.app.redis.service.RedisService;
import com.jt.app.service.BetOrderServiceV1;
import com.jt.app.service.LotteryUserServiceV1;
import com.jt.app.service.TenementServiceV1;
import com.jt.app.service.UserServiceV1;
import com.jt.app.task.SendAwardTask;
import com.jt.app.util.EncryptUtil;
import com.jt.app.util.TimeUtil;
import org.apache.http.util.Asserts;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * 测试多个租户同时开奖，看写库是否会锁表
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {WebSocketApplication.class})
@ActiveProfiles(profiles = "test")
public class TestApplication {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TenementServiceV1 tenementServiceV1;
    @Autowired
    private LotteryUserServiceV1 lotteryUserServiceV1;
    @Autowired
    private UserServiceV1 userServiceV1;
    @Autowired
    private RedisService redisService;
    @Autowired
    private BetOrderServiceV1 betOrderServiceV1;

    private CountDownLatch countDownLatch = null;

    /**
     * 测试生成10个租户，每个租户1万用户下单
     */
    @Test
    public void testOrder() {
        final int tenementNum = 10;
        long startTime, endTime = 0L;
        long startTimeAll = 0L, endTimeAll = 0L;

        startTimeAll = System.currentTimeMillis();
        //先检查有无足额租户，没有刪除重建
        startTime = System.currentTimeMillis();
        long tenementCount = tenementServiceV1.getTenementRepository().count();
        endTime = System.currentTimeMillis();
        logger.info("查询租户数量耗时：{}", (endTime - startTime) + "毫秒");
        if (tenementCount > 0) {
            tenementServiceV1.getTenementRepository().deleteAll();
            logger.info("检查租户个数为{}个，需要刪除{}个租户！", tenementCount, tenementCount);
        }
        //生成10个租户
        String jointSql = bluidTenementSql(tenementNum);
        logger.info("{}租户的拼接sql:{}", tenementNum, jointSql);
        startTime = System.currentTimeMillis();
        boolean flag = tenementServiceV1.jointAddTenement(jointSql);
        endTime = System.currentTimeMillis();
        logger.info("拼接插入租户耗时：{}", (endTime - startTime) + "毫秒");
        if (flag) {
            logger.info("{}个租户创建完成！", tenementNum);
        }
        //为每个租户创建1万个用户
        final int user1w = 10000;
        List<Tenement> tenementList = tenementServiceV1.getTenementRepository().findAll();
        if (tenementList == null || tenementList.size() != tenementNum) {
            Asserts.check(false, "创建" + tenementNum + "个租户出错！");
        }
        //刪除每个租户之下的用户
        lotteryUserServiceV1.getTenementRepository().deleteAll();
        logger.info("刪除所有用户完成！");
        for (Tenement tenement : tenementList) {
            String bluidLotteryUser = bluidLotteryUser(user1w, tenement.getId());
            logger.info("拼接彩票用户sql:{}", bluidLotteryUser);
            startTime = System.currentTimeMillis();
            boolean flag1 = lotteryUserServiceV1.jointAddLotteryUser(bluidLotteryUser);
            endTime = System.currentTimeMillis();
            logger.info("拼接插入彩票用户耗时：{}", (endTime - startTime) + "毫秒");
            logger.info("租户{}添加完成！", tenement.getName());
        }

        //重新从库中查询出租户对应的彩票用户放入缓存
        startTime = System.currentTimeMillis();
        for (Tenement tenement : tenementList) {
            List<LotteryUser> lotteryUsers = lotteryUserServiceV1.getLotteryUsersByTenementId(tenement.getId());
            redisService.hset(GlobalCacheKey.tenement_key, GlobalCacheKey.tenement_key + tenement.getId(), lotteryUsers);
            logger.info("租户{}放入缓存完成！", tenement.getName());
        }
        endTime = System.currentTimeMillis();
        logger.info("{}个租户放入缓存共耗时：{}", tenementNum, (endTime - startTime) + "毫秒");

        //查询所有租户开奖,并添加到任务调度线程中
        final Integer lotteryStatus = 1;   //待开奖
        List<Tenement> tenements = tenementServiceV1.getNoSendAwardTenement(lotteryStatus);
        if (tenements != null && tenements.size() > 0) {
            logger.info("查询到一共有{}个待开奖租户,准备把这些租户放入定时任务处理线程中，同时分别处理!", tenements.size());
            Timer timer = new Timer();
            countDownLatch = new CountDownLatch(tenements.size());
            for (Tenement tenement : tenements) {
                if (!TimeUtil.beforeDate(tenement.getOpenLotteryTime())) {
                    //logger.info("数据库中的开奖时间：{}", tenement.getOpenLotteryTime());
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.SECOND, 20);
                    timer.schedule(new SendAwardTask(tenement, countDownLatch), calendar.getTime());
                    logger.info("租户{}已经添加到等待派奖队列中，派奖时间为：{}", tenement.getName(), calendar.getTime());
                } else {
                    logger.info("已经错过开奖时间的租户{}！", tenement.getName());
                }
            }
        }
        try {
            countDownLatch.await();
            endTimeAll = System.currentTimeMillis();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("等待线程中出错！");
        } finally {
            logger.error("timer线程执行完成，任务结束！");
            //logger.info("{}个租户每个租户{}个用户从下单到更改主单记录完成共耗时：{}包含40s开奖时间！", tenementNum,user1w,(endTimeAll - startTime)/1000 + "秒");
        }
    }

    public String bluidTenementSql(Integer tenementNum) {
        //10个租户，奇数租户开奖时间都是当前时间之后的1分钟，偶数租户开奖时间都是当前时间之后的2分钟
        Calendar calendar = Calendar.getInstance();

        StringBuilder sb = new StringBuilder();
        if (tenementNum > 0) {
            sb.append("insert into t_tenement(name,lotteryType,lotteryStatus,lotteryNumber,openLotteryTime) values");
            for (int i = 0; i < tenementNum; i++) {
                calendar.add(Calendar.MINUTE, i % 2 == 0 ? 1 : 2);
                Date openLotteryTime = calendar.getTime();
                if (i < tenementNum - 1) {
                    sb.append("('" + String.valueOf("user-" + this.random10(8)) + "',1,1," + this.random10(10) + ",'" + TimeUtil.ymdHms2str(openLotteryTime) + "'),");
                }
                if (i == tenementNum - 1) {
                    sb.append("('" + String.valueOf("user-" + this.random10(8)) + "',1,1," + this.random10(10) + ",'" + TimeUtil.ymdHms2str(openLotteryTime) + "')");
                }
            }
        }
        return sb.toString();
    }

    public String bluidLotteryUser(Integer lotteryUserNum, Integer tenementNum) {
        StringBuilder sb = new StringBuilder();
        if (lotteryUserNum > 0) {
            sb.append("insert into t_lotteryUser(name,lotteryType,lotteryStatus,lotteryNumber,tenementId,winLotteryStatus) values");
            for (int i = 0; i < lotteryUserNum; i++) {
                if (i < lotteryUserNum - 1) {
                    sb.append("('" + String.valueOf("user-" + this.random10(8)) + "',1,1," + this.random10(10) + "," + tenementNum + ",1),");
                }
                if (i == lotteryUserNum - 1) {
                    sb.append("('" + String.valueOf("user-" + this.random10(8)) + "',1,1," + this.random10(10) + "," + tenementNum + ",1)");
                }
            }
        }
        return sb.toString();
    }

    public String bluidBetOrder(Integer recodeNum, String tableName, String prefix, double rate, Integer lotteryNumberId) {
        StringBuilder sb = new StringBuilder();
        if (tableName.equals("t_bet_order")) {
            sb.append("insert into " + tableName + "(ORDERNO,BATCH_ORDERNO,TENANT_CODE,USER_ID,USER_NAME,GAME_ID,BET_SINGLE_AMOUNT,BET_COUNT,BET_TOTAL_AMOUNT,BET_DIGITS,BET_ID,BET_MULTIPLE,BET_NUMBER,BET_SECRET,BET_TIME,BET_WAY,CHASE_NUMBER_ID,CHASE_ORDER_ID,DEVICE,LOTTERY_NUMBER,LOTTERY_NUMBER_ID,ODDS,ORDER_STATUS,WIN_COUNT,WIN_AMOUNT,WIN_STATUS,AWARD_STATUS,AWARD_TIME,REBATE_CHOOSE,REBATE_USER,REBATE_AMOUNT,AGENT_REBATE_AMOUNT,BET_REBATE_STATUS,AGENT_REBATE_STATUS,LAST_MODIFIED_BY,LAST_MODIFIED_DATE,ADAPT,PLATFORM_TYPE,WIN_SECRET,ROOM_ID) values");
        }
        if (tableName.equals("t_bet_order_temp")) {
            sb.append("insert into " + tableName + "(LOTTERY_NUMBER,LOTTERY_NUMBER_ID,ODDS,WIN_COUNT,WIN_AMOUNT,WIN_STATUS,WIN_SECRET) values");
        }
        for (int i = 0; i < recodeNum; i++) {
            if (tableName.equals("t_bet_order")) {
                sb.append("('" + String.valueOf(prefix + "order-" + this.random10(8)) + "',");
                sb.append("'" + String.valueOf(prefix + "batchOrderno-" + this.random10(8)) + "',");
                sb.append("'" + String.valueOf(prefix + "tenantCode-" + this.random10(8)) + "',");
                sb.append("" + (recodeNum + 2) * rate + ",");
                sb.append("'" + String.valueOf(prefix + "userName-" + this.random10(8)) + "',");
                sb.append("" + (recodeNum * 1.2) * rate + ",");
                sb.append("" + (recodeNum * 1.2) * rate + ",");
                sb.append("" + (recodeNum * 3.3) * rate + ",");
                sb.append("" + (recodeNum * 11.5) * rate + ",");
                sb.append("'" + String.valueOf(prefix + "betDigits-" + this.random10(8)) + "',");
                sb.append("" + (recodeNum * 3) * rate + ",");
                sb.append("" + (recodeNum * 4.1) * rate + ",");
                sb.append("'" + String.valueOf(prefix + "betNumber-" + this.random10(8)) + "',");
                sb.append("'" + String.valueOf(prefix + "betSecret-" + this.random10(8)) + "',");
                sb.append("'" + TimeUtil.ymdHms2str() + "',");
                sb.append("" + (recodeNum % 2 == 0 ? 0 : 1) * rate + ",");
                sb.append("" + (recodeNum + 100001) * rate + ",");
                sb.append("" + (recodeNum + 1001) * rate + ",");
                sb.append("" + (recodeNum % 2 == 0 ? 1 : 2) * rate + ",");
                sb.append("'" + (recodeNum + 1002) * rate + "',");
                sb.append("" + lotteryNumberId + ",");
                sb.append("" + (recodeNum * 1.8) * rate + ",");
                sb.append("" + (recodeNum % 2 == 0 ? 1 : 2) * rate + ",");
                sb.append("" + (recodeNum * 2.1) * rate + ",");
                sb.append("" + (recodeNum * 1.1) * rate + ",");
                sb.append("" + (recodeNum % 2 == 0 ? 1 : 2) * rate + ",");
                sb.append("" + (recodeNum % 2 == 0 ? 0 : 1) * rate + ",");
                sb.append("'" + TimeUtil.ymdHms2str() + "',");
                sb.append("" + (recodeNum * 1.1) * rate + ",");
                sb.append("" + (recodeNum * 1.1) * rate + ",");
                sb.append("" + (recodeNum * 1.1) * rate + ",");
                sb.append("" + (recodeNum * 1.1) * rate + ",");
                sb.append("" + (recodeNum % 2 == 0 ? 0 : 1) * rate + ",");
                sb.append("" + (recodeNum % 2 == 0 ? 0 : 1) * rate + ",");
                sb.append("'" + String.valueOf(prefix + "lastModifiedBy-" + this.random10(8)) + "',");
                sb.append("'" + TimeUtil.ymdHms2str() + "',");
                sb.append("" + (recodeNum % 2 == 0 ? 0 : 1) * rate + ",");
                sb.append("" + (recodeNum % 2 == 0 ? 0 : 1) * rate + ",");
                sb.append("'" + String.valueOf(prefix + "winSecret-" + this.random10(8)) + "',");
                sb.append("" + (recodeNum * 2) * rate + "),");
            }
            if (tableName.equals("t_bet_order_temp")) {
                sb.append("('" + (recodeNum + 1001) * rate + "',");
                sb.append("" + lotteryNumberId + ",");
                sb.append("" + (recodeNum * 1.8) * rate + ",");
                sb.append("" + (recodeNum + 102) * rate + ",");
                sb.append("" + (recodeNum * 1) * rate + ",");
                sb.append("" + (recodeNum + 103) * rate + ",");
                sb.append("'" + String.valueOf(prefix + "winSecret-" + this.random10(8)) + "'),");
            }
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }

    public Integer random10(Integer baseNumber) {
        return new Random().nextInt(baseNumber) + 1;
    }

    /**
     * 测试从库接收到数据时间
     */
    @Test
    public void testMultiMysqlSyncTime() {
        final String userName = "用户-t2-0";
        boolean existUser = true;
        logger.info("开始阻塞查询时间：{}", System.currentTimeMillis());
        while (existUser) {
            User user = userServiceV1.getUserByName(userName);
            if (user != null) {
                existUser = false;
                logger.info("查找到用户名为：" + userName + "的记录，用户信息：" + user.toString() + ",\n当前时间：" + System.currentTimeMillis());
            }
        }
    }

    /**
     * 测试mysql主从库，从从库读取数据
     */
    @Test
    public void testReadSlaveMysql() {
        List<Tenement> tenements = tenementServiceV1.getTenementRepository().findAll();
        if (tenements == null || tenements.size() == 0) {
            logger.info("数据库中没有数据，先添加数据！");
            final Integer tenementNum = 50000;
            String jointTenementSql = bluidTenementSql(tenementNum);
            boolean flag = tenementServiceV1.jointAddTenement(jointTenementSql);
            if (flag) {
                logger.info("新增{}条数据到租户表中成功！", tenementNum);
            } else {
                logger.error("新增{}条数据到租户表中失败！", tenementNum);
            }
        } else {
            while (true) {
                tenementServiceV1.getTenementRepository().findAll();
            }
        }
    }

    @Test
    public void testInsertBetOrder() {
        final int recodeNum = 10000;
        final String tableName = "t_bet_order_temp";
        String prefix = "";
        double rate = 1.0;
        Integer lotteryNumberId = 10000;
        boolean isMultiTenant = false; //是否清除原有记录
        boolean isCreateTempTable = true;
        if (tableName.equals("t_bet_order_temp")) {
            prefix = "temp-";
            rate = 1.2; //修改添加不同数据
            lotteryNumberId = 10002; //修改中奖号码
        }
        int insertNum = 20;
        long startTime, endTime;
        long startTimeAll, endTimeAll;
        long betOrderCount = 0;
        //检查表中是否有数据，有则清除
        if (tableName.equals("t_bet_order_temp")) {
            betOrderCount = betOrderServiceV1.getRecodeCountBetOrderTemp();
        }
        if (tableName.equals("t_bet_order")) {
            betOrderCount = betOrderServiceV1.getRecodeCountBetOrder();
        }

        //bet_order表需要大量数据，不轻易删除
        if (tableName.equals("t_bet_order_temp")) {
            if (!isMultiTenant) {
                if (betOrderCount > 0) {
                    logger.info("表{}中存在记录{}条，需要刪除！", tableName, betOrderCount);
                    startTime = System.currentTimeMillis();
                    boolean f1 = betOrderServiceV1.deleteAllBySql(tableName);
                    endTime = System.currentTimeMillis();
                    logger.info("刪除表{},{}条记录,耗时：{}毫秒", tableName, betOrderCount, (endTime - startTime));
                }
            }
        }

        startTimeAll = System.currentTimeMillis();
        for (int i = 0; i < insertNum; i++) {
            startTime = System.currentTimeMillis();
            String jointBetOrderSql = bluidBetOrder(recodeNum, tableName, prefix, rate, lotteryNumberId);
            endTime = System.currentTimeMillis();
            //logger.info("第{}次拼接sql:{}", i + 1, jointBetOrderSql);
            logger.info("第{}次拼接{}条记录耗时：{}毫秒！", i + 1, recodeNum, (endTime - startTime));
            startTime = System.currentTimeMillis();
            boolean flag = betOrderServiceV1.jointAddBetOrder(jointBetOrderSql);
            endTime = System.currentTimeMillis();
            logger.info("第{}次入库{}条记录耗时：{}毫秒！", i + 1, recodeNum, (endTime - startTime));
        }
        endTimeAll = System.currentTimeMillis();
        logger.info("新增完成{}条记录耗时：{}毫秒！", recodeNum * insertNum, (endTimeAll - startTimeAll));
    }

    @Test
    public void testSyncBetOrderStatusByExist() {
        long startTime, endTime;
        startTime = System.currentTimeMillis();
        boolean flag = betOrderServiceV1.updateBetOrderStatusByExist();
        endTime = System.currentTimeMillis();
        logger.info("同步主单状态{}，耗时{}毫秒！", flag ? "成功" : "失败", (startTime - endTime));
    }

    @Test
    public void testSyncBetOrderStatusByJoin() {
        long startTime, endTime;
        Integer lotteryNumberId = 10001;
        startTime = System.currentTimeMillis();
        boolean flag = betOrderServiceV1.updateBetOrderStatusByJpaSql(lotteryNumberId);
        //boolean flag = betOrderServiceV1.updateBetOrderStatusByJoinSql(lotteryNumberId);
        endTime = System.currentTimeMillis();
        logger.info("同步主单状态{}，耗时{}毫秒！", flag ? "成功" : "失败", (endTime - startTime));
    }

    /**
     * 测试从从库获取数据
     */
    @Test
    public void testGetDataBySlave() {
        final Integer lotteryNumberId = 10001;
        List<Object> betOrderTemps = betOrderServiceV1.getBetOrderTempsByLNId(lotteryNumberId);
        logger.info("获取临时表成功！数据记录数为：{}", betOrderTemps.size());
    }

    public List<User> globalBatchUser(Integer addNum, String nameFlag) {
        List<User> users = new ArrayList<User>(addNum + 1);
        User user = null;
        for (int i = 0; i < addNum; i++) {
            user = new User();
            user.setUserName("用户" + nameFlag + i);
            user.setAddress("广东-广州");
            user.setCreateTime(TimeUtil.ymdHms2date());
            user.setUpdateTime(TimeUtil.ymdHms2date());
            user.setCreateUser("lj" + i);
            user.setEmail("1245282613@qq.com");
            user.setLastLoginAddr("广东-广州");
            user.setLoginName("root" + nameFlag + i);
            user.setLoginPasswd(EncryptUtil.md5Encode("root" + i));
            //userServiceV1.addUser(user);
            users.add(user);
        }
        return users;
    }

    /**
     * 测试使用jpa向主库添加数据
     */
    @Test
    public void testBatchAdd1WTime() {
        //首先刪除已有t2測試
        final String nameFlag = "-t2-";
        long startTime, endTime = 0;
        final Integer addNum = 1;
        final Integer batchSize = 100000;
        Integer usersNum = userServiceV1.getCountByNameLike(nameFlag);
        if (usersNum > 0) {
            startTime = System.currentTimeMillis();
            Integer delNum = userServiceV1.deleteAllByNameLike(nameFlag);
            endTime = System.currentTimeMillis();
            if (delNum > 0) {
                logger.info("刪除t2测试数据一共：【" + usersNum + "】条，用时：【" + (endTime - startTime) / 1000 + "】秒");
            }
        }
        List<User> users = this.globalBatchUser(addNum, nameFlag);
        User u = userServiceV1.getUserRepository().saveAndFlush(users.get(0));
        startTime = System.currentTimeMillis();
        //Integer reslut = userServiceV1.batchInsert(users, batchSize);
        endTime = System.currentTimeMillis();
        /*if (reslut == 1) {
            logger.info("批量插入【" + batchSize + "】成功！");
        } else {
            logger.error("批量插入【" + batchSize + "】失败！");
        }*/
        logger.info("添加" + addNum + "条记录花费时间：【" + (endTime - startTime) / 1000 + "】秒");
    }
}
