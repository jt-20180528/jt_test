package com.jt.app.task;

import com.jt.app.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.TimerTask;

public class JmterTask extends TimerTask {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String sessionId;
    private String dateTime;
    private WebSocketSession session;

    @Override
    public void run() {
        try {
            session.sendMessage(new TextMessage("连接成功，发送时间：" + TimeUtil.ymdHms2str()));
            logger.info("jmeter定时任务，每5秒执行一次！时间：" + TimeUtil.ymdHms2str());
        }catch(Exception e){
            e.printStackTrace();
            logger.error("jmeter定时任务出错！");
        }
    }

    public JmterTask(String sessionId, String dateTime, WebSocketSession session) {
        this.sessionId = sessionId;
        this.dateTime = dateTime;
        this.session = session;
    }

    public JmterTask() {
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public WebSocketSession getSession() {
        return session;
    }

    public void setSession(WebSocketSession session) {
        this.session = session;
    }
}
