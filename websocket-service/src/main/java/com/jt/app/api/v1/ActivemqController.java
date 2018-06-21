package com.jt.app.api.v1;

import com.jt.app.WebSocketApplication;
import com.jt.app.activemq.Producer;
import com.jt.app.util.GlobleBasicCache;
import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Destination;


@RestController
public class ActivemqController {

    private static final Logger logger = LoggerFactory.getLogger(ActivemqController.class);

    @Autowired
    public Producer producer;

    @Value("${spring.activemq.queueName}")
    private String queueName;

    @RequestMapping("/sendMsgToMq/{msg}")
    public Object sendMsgToMq(@PathVariable String msg) {
        long startTime, endTime = 0;
        try {
            startTime = System.currentTimeMillis();
            GlobleBasicCache.setAmqMsgMap(queueName, startTime);
            Destination a = new ActiveMQQueue(queueName);
            producer.sendMessage(a, msg);
            endTime = System.currentTimeMillis();
            logger.info("发送消息[" + msg + "]到队列:" + queueName + "耗时：[" + (endTime - startTime) + "]毫秒");
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }

    }
}
