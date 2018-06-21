package com.jt.app.activemq;

import com.jt.app.WebSocketApplication;
import com.jt.app.util.GlobleBasicCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

    @Value("${spring.activemq.queueName}")
    private static String queueName;

    // 使用JmsListener配置消费者监听的队列，其中text是接收到的消息
    @JmsListener(destination = "queue.test")
    public void receiveQueue(String text) {
        Long entTime = System.currentTimeMillis();
        Long startTime = GlobleBasicCache.getAmqMsgMap("queue.test");
        if (startTime == 0L) {
            logger.error("缓存中不存在开始时间，无法计算！");
        } else {
            logger.info("获取到消息发送开始时间【" + startTime.toString() + "】，共耗时：" + (entTime - startTime) + "毫秒！");
        }
        logger.info("Consumer收到的报文为:" + text);
    }
}
