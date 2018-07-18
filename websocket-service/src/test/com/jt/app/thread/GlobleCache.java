package com.jt.app.thread;

import org.apache.commons.lang.StringUtils;

import java.util.concurrent.ConcurrentHashMap;

public class GlobleCache {

    private static ConcurrentHashMap<String, Object> scheduledTaskCache = new ConcurrentHashMap<String, Object>();

    public static void setTaskCache(String key, Object value) {
        scheduledTaskCache.put(key, value);
    }

    public static Object getTaskCache(String key) {
        if (StringUtils.isNotBlank(key)) {
            return scheduledTaskCache.get(key);
        } else {
            return null;
        }
    }
}
