package com.jt.app.redis.task;

import com.jt.app.model.mongodb.Order;
import com.jt.app.redis.GlobalCacheKey;
import com.jt.app.redis.MsgReceive;
import com.jt.app.redis.service.RedisService;
import com.jt.app.service.mongodb.OrderServiceV1;
import com.jt.app.util.message.MsgFormat;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CountDownLatch;

@Component
public class SyncOrderToDB implements MsgReceive {

    private static Logger logger = LoggerFactory.getLogger(SyncOrderToDB.class);

    @Autowired
    private OrderServiceV1 orderServiceV1;
    @Autowired
    private RedisService redisService;

    private CountDownLatch latch;

    @Autowired
    public SyncOrderToDB(CountDownLatch latch) {
        this.latch = latch;
    }

    /**
     * 接收消息的方法
     */
    public void getMsg(String message) {
        logger.info("消息客户端接收到消息！" + message);
        if (StringUtils.isNotBlank(message)) {
            Iterable<Order> orders = (Iterable<Order>) redisService.hget(message.split("-")[0], message.split("-")[1]);
            //Order order = (Order) redisService.hget(GlobalCacheKey.order_key, "aa");
            if (orders == null) {
                logger.error("缓存中数据为空！");
                return;
            }
            long startTime, endTime = 0;
            //检查数据库中是否有订单，如有则刪除
            long recodeNum = orderServiceV1.getOrderRepository().count();
            if (recodeNum > 0) {
                startTime = System.currentTimeMillis();
                boolean delFlag = orderServiceV1.deleteAll();
                endTime = System.currentTimeMillis();
                logger.info("刪除mongodb数据【" + recodeNum + "】条" + (delFlag ? "成功！耗时【" + (endTime - startTime) / 1000 + "】秒" : "失败！"));
            }
            //开始处理接受消息逻辑
            startTime = System.currentTimeMillis();
            orderServiceV1.getOrderRepository().save(orders);
            endTime = System.currentTimeMillis();
            logger.info("消息入库成功！总耗时：【" + (endTime - startTime) / 1000 + "】秒");
        } else {
            logger.info("消息接收失败！");
        }
        latch.countDown();
    }
}
