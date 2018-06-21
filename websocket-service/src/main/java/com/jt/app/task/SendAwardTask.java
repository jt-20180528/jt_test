package com.jt.app.task;

import com.jt.app.model.LotteryUser;
import com.jt.app.model.Tenement;
import com.jt.app.redis.GlobalCacheKey;
import com.jt.app.redis.service.RedisService;
import com.jt.app.service.websocket.LotteryUserServiceV1;
import com.jt.app.service.websocket.LotteryUserTempServiceV1;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Random;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

/**
 * 派奖任务调度
 */
public class SendAwardTask extends TimerTask {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LotteryUserServiceV1 lotteryUserServiceV1;
    @Autowired
    private LotteryUserTempServiceV1 lotteryUserTempServiceV1;
    @Autowired
    private RedisService redisService;

    private CountDownLatch countDownLatch;

    private Tenement tenement;

    public SendAwardTask(Tenement tenement, CountDownLatch countDownLatch) {
        this.tenement = tenement;
        this.countDownLatch = countDownLatch;
    }

    public SendAwardTask() {
    }

    ;

    @Override
    public void run() {
        try {
            logger.info("租户{}开始开奖，开奖号码为：{}，开始把开奖事件异步给其他方法执行派奖！", tenement.getName(), tenement.getLotteryNumber());
            //TODO 派发业务逻辑
            //已经派奖完成，开始修改用户订单状态

            //先添加用户彩票状态到临时表
            List<LotteryUser> lotteryUserList = (List<LotteryUser>) redisService.hget(GlobalCacheKey.tenement_key, tenement.getId());
            if (lotteryUserList == null || lotteryUserList.size() == 0) {
                logger.info("缓存中不存在数据，开始查库。。。");
                lotteryUserList = lotteryUserServiceV1.getLotteryUsersByTenementId(tenement.getId());
                redisService.hset(GlobalCacheKey.tenement_key, GlobalCacheKey.tenement_key + tenement.getId(), lotteryUserList);
            }
            if (lotteryUserList == null || lotteryUserList.size() == 0) {
                logger.error("缓存和数据库中不存在租户{}的彩票用户！", tenement.getName());
            }
            String lotteryUserSql = bluidLotteryUser(lotteryUserList);
            boolean flag1 = lotteryUserTempServiceV1.jointAddLotteryUser(lotteryUserSql);
            logger.info("添加彩票用户{}", flag1 ? "成功！" : "失败！");

            //更改彩票用户状态
            boolean flag2 = lotteryUserServiceV1.updateLotteryUserStatus();
            logger.info("更新彩票用户状态{}", flag2 ? "成功！" : "失败！");
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            countDownLatch.countDown();
        }
    }

    //设置所以用户不中奖
    public String bluidLotteryUser(List<LotteryUser> lotteryUserList) {
        StringBuilder sb = new StringBuilder();
        if (lotteryUserList.size() > 0) {
            sb.append("insert into t_lotteryUserTemp (id,name,lotteryType,lotteryStatus,lotteryNumber,tenementId,winLotteryStatus) values");
            for (int i = 0; i < lotteryUserList.size(); i++) {
                LotteryUser lotteryUser = lotteryUserList.get(i);
                if (i < lotteryUserList.size() - 1) {
                    sb.append(" (" + lotteryUser.getId() + ",'" + lotteryUser.getName() + "',1,2," + lotteryUser.getLotteryNumber() + "," + lotteryUser.getTenementId() + ",3),");
                }
                if (i == i - 1) {
                    sb.append(" (" + lotteryUser.getId() + ",'" + lotteryUser.getName() + "',1,2," + lotteryUser.getLotteryNumber() + "," + lotteryUser.getTenementId() + ",3)");
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
