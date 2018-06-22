package com.jt.app.task;

import com.jt.app.model.LotteryUser;
import com.jt.app.model.Tenement;
import com.jt.app.redis.GlobalCacheKey;
import com.jt.app.redis.service.RedisService;
import com.jt.app.service.websocket.LotteryUserServiceV1;
import com.jt.app.service.websocket.LotteryUserTempServiceV1;
import com.jt.app.util.SpringUtil;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

/**
 * 派奖任务调度
 */
public class SendAwardTask extends TimerTask {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    private static LotteryUserServiceV1 lotteryUserServiceV1;
    private static LotteryUserTempServiceV1 lotteryUserTempServiceV1;
    private static RedisService redisService;

    private CountDownLatch countDownLatch;

    private Tenement tenement;

    static {
        lotteryUserServiceV1 = SpringUtil.getBean(LotteryUserServiceV1.class);
        lotteryUserTempServiceV1 = SpringUtil.getBean(LotteryUserTempServiceV1.class);
        redisService = SpringUtil.getBean(RedisService.class);
    }

    public SendAwardTask(Tenement tenement, CountDownLatch countDownLatch) {
        this.tenement = tenement;
        this.countDownLatch = countDownLatch;
    }

    public SendAwardTask() {
    }

    @Override
    public void run() {
        long startTime, endTime = 0L;
        long startTimeTo = 0L, endTimeTo = 0L;
        try {
            startTimeTo = System.currentTimeMillis();
            logger.info("租户{}开始开奖，开奖号码为：{}，开始把开奖事件异步给其他方法执行派奖！", tenement.getName(), tenement.getLotteryNumber());
            //TODO 派发业务逻辑
            //已经派奖完成，开始修改用户订单状态

            //先添加用户彩票状态到临时表
            List<LotteryUser> lotteryUserList = (List<LotteryUser>) redisService.hget(GlobalCacheKey.tenement_key, GlobalCacheKey.tenement_key + tenement.getId());
            if (lotteryUserList == null || lotteryUserList.size() == 0) {
                logger.info("缓存中不存在数据，开始查库。。。");
                lotteryUserList = lotteryUserServiceV1.getLotteryUsersByTenementId(tenement.getId());
                redisService.hset(GlobalCacheKey.tenement_key, GlobalCacheKey.tenement_key + tenement.getId(), lotteryUserList);
            }
            if (lotteryUserList == null || lotteryUserList.size() == 0) {
                logger.error("缓存和数据库中不存在租户{}的彩票用户！", tenement.getName());
            }
            startTime = System.currentTimeMillis();
            String lotteryUserSql = bluidLotteryUser(lotteryUserList);
            endTime = System.currentTimeMillis();
            logger.info("租户拼接彩票用户数据耗时：{}", (endTime - startTime) + "毫秒");
            startTime = System.currentTimeMillis();
            boolean flag1 = lotteryUserTempServiceV1.jointAddLotteryUser(lotteryUserSql);
            endTime = System.currentTimeMillis();
            logger.info("添加彩票用户到临时表", flag1 ? "成功！耗时：{}" : "失败！耗时：{}", (endTime - startTime) + "毫秒");

            //更改彩票用户状态
            startTime = System.currentTimeMillis();
            boolean flag2 = lotteryUserServiceV1.updateLotteryUserStatus();
            endTime = System.currentTimeMillis();
            logger.info("更新彩票用户状态", flag2 ? "成功！耗时：{}" : "失败！耗时：{}", (endTime - startTime) + "毫秒");
            endTimeTo = System.currentTimeMillis();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            logger.info("处理完租户{}任务共耗时：{}", tenement.getName(), (endTimeTo - startTimeTo) + "毫秒");
            countDownLatch.countDown();
        }
    }

    //设置所以用户不中奖
    public String bluidLotteryUser(List<LotteryUser> lotteryUserList) {
        StringBuilder sb = new StringBuilder();
        if (lotteryUserList.size() > 0) {
            sb.append("insert into t_lotteryUserTemp(id,name,lotteryType,lotteryStatus,lotteryNumber,tenementId,winLotteryStatus) values");
            for (int i = 0; i < lotteryUserList.size(); i++) {
                LotteryUser lotteryUser = lotteryUserList.get(i);
                if (i < lotteryUserList.size() - 1) {
                    sb.append("(" + lotteryUser.getId() + ",'" + lotteryUser.getName() + "',1,2," + lotteryUser.getLotteryNumber() + "," + lotteryUser.getTenementId() + ",3),");
                }
                if (i == lotteryUserList.size() - 1) {
                    sb.append("(" + lotteryUser.getId() + ",'" + lotteryUser.getName() + "',1,2," + lotteryUser.getLotteryNumber() + "," + lotteryUser.getTenementId() + ",3)");
                }
            }
        }
        return sb.toString();
    }

    public Integer random10(Integer baseNumber) {
        return new Random().nextInt(baseNumber) + 1;
    }

    public CountDownLatch getCountDownLatch() {
        return countDownLatch;
    }

    public void setCountDownLatch(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    public Tenement getTenement() {
        return tenement;
    }

    public void setTenement(Tenement tenement) {
        this.tenement = tenement;
    }
}
