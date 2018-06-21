package com.jt.app.task;

import com.jt.app.model.Tenement;
import com.jt.app.service.websocket.TenementServiceV1;
import com.jt.app.util.GlobleBasicCache;
import com.jt.app.util.TimeUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class InitTask {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    private int flag = 0;

    /*@Autowired
    private TenementServiceV1 tenementServiceV1;*/

    @Scheduled(cron = "0/30 * * * * ?")
    public void initScheduleTask() {
        if (flag == 0) {
            List<WebSocketSession> userSessionMap = GlobleBasicCache.getAllWebSocketSession();
            long startTime, endTime = 0;
            if (userSessionMap != null) {
                startTime = System.currentTimeMillis();
                for (WebSocketSession webSocketSession : userSessionMap) {
                    try {
                        if (webSocketSession.isOpen()) {
                            webSocketSession.sendMessage(new TextMessage("用户【" + webSocketSession.getId() + "】，发送消息时间：" + TimeUtil.ymdHms2str()));
                        } else {
                            GlobleBasicCache.removeSocketSession(webSocketSession.getId());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                endTime = System.currentTimeMillis();
                logger.info("给用户数【" + userSessionMap.size() + "】，发送消息耗时：" + (endTime - startTime));
            }
            flag = 1;
        } else {
            logger.info("只执行一次！已经执行过");
        }
    }

    /**
     * 初始化派奖任务
     */
    @PostConstruct
    public void initSendAwardTask() {
        final String lotteryStatus = "1";   //待开奖
        /*List<Tenement> tenements = tenementServiceV1.getNoSendAwardTenement(lotteryStatus);
        if (tenements == null || tenements.size() == 0) {
            logger.info("没有待开奖任务！");
        } else {
            for (Tenement tenement : tenements) {
                if (!TimeUtil.beforeDate(tenement.getOpenLotteryTime())){
                    //SendAwardTask sendAwardTask = new SendAwardTask(tenement);
                    //这里先不写，主要是通过junit测试，不是开机自动测试
                }
            }
        }*/
    }

}
