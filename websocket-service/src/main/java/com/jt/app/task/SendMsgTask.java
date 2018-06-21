package com.jt.app.task;

import com.jt.app.model.Msg;
import com.jt.app.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class SendMsgTask {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final static String topicName = "/topic/subscribeTest";

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Scheduled(cron = "0/5 * * * * ?")
    public void test3mTask() {
        //logger.info("test3mTask---{}", TimeUtile.ymdHms2str());
        Msg msg = new Msg();
        Map<String, Object> contentMap = new HashMap<>();
        contentMap.put("msg", "定时任务每5秒执行一次");
        contentMap.put("sendTime", TimeUtil.ymdHms2str());
        msg.setContent(contentMap);
        messagingTemplate.convertAndSend(topicName, msg);
    }
}
