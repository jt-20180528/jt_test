package com.jt.app.config;

import com.jt.app.util.GlobleBasicCache;
import com.jt.app.util.TimeUtil;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.util.concurrent.CopyOnWriteArraySet;

public class H5WebSocketHandler extends AbstractWebSocketHandler {

    private CopyOnWriteArraySet<String> sessionSet = new CopyOnWriteArraySet<>();

    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        session.sendMessage(new TextMessage(message.getPayload()));
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessionSet.add(session.getId());
        GlobleBasicCache.setUserSessionMap(session.getId(),session);
        //Timer timer = new Timer();
        //timer.schedule(new JmterTask(session.getId(), TimeUtile.ymdHms2str(), session), 1 * 1000, 5 * 1000);
        session.sendMessage(new TextMessage("连接成功，发送时间：" + TimeUtil.ymdHms2str()));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessionSet.remove(session.getId());
    }

}
