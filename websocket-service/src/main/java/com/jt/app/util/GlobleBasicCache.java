package com.jt.app.util;

import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class GlobleBasicCache {

    public static ConcurrentHashMap<String, WebSocketSession> userSessionMap = new ConcurrentHashMap<>();

    private static ConcurrentHashMap<String, Long> amqMsgMap = new ConcurrentHashMap<>();

    public static void setUserSessionMap(String sessionId, WebSocketSession session) {
        if (!userSessionMap.containsKey(sessionId)) {
            userSessionMap.put(sessionId, session);
        }
    }

    public static WebSocketSession getUserSessionMap(String sessionId) {
        if (!StringUtils.isEmpty(sessionId)) {
            return userSessionMap.get(sessionId);
        } else {
            return null;
        }
    }

    public static void removeSocketSession(String sessionId) {
        if (userSessionMap.containsKey(sessionId)) {
            userSessionMap.remove(sessionId);
        }
    }

    public static void setAmqMsgMap(String key, Long startTime) {
        amqMsgMap.put(key, startTime);
    }

    public static List<WebSocketSession> getAllWebSocketSession() {
        Collection<WebSocketSession> sessions = userSessionMap.values();
        List<WebSocketSession> sessionList = new ArrayList<>(sessions.size());
        ;
        if (sessions.size() > 0) {
            for (WebSocketSession webSocketSession : sessions) {
                sessionList.add(webSocketSession);
            }
            return sessionList;
        } else {
            return null;
        }
    }

    public static Long getAmqMsgMap(String key) {
        if (amqMsgMap.containsKey(key)) {
            return amqMsgMap.get(key);
        }
        return 0L;
    }
}
