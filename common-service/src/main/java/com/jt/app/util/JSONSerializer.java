package com.jt.app.util;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.*;

public class JSONSerializer {

    private static final String DEFAULT_CHARSET_NAME = "UTF-8";

    @Deprecated
    public static <T> String obj2Json(T object) {
        return JSON.toJSONString(object);
    }

    @Deprecated
    public static <T> T json2Obj(String string, Class<T> clz) {
        return JSON.parseObject(string, clz);
    }

    /**
     * javabean to json
     */
    public static <T> String objToJson(T object) {
        if (object == null) {
            return null;
        }
        Gson gson = new Gson();
        String json = gson.toJson(object);
        return json;
    }

    /**
     * list to json
     */
    public static <T> String listToJson(List<T> list) {
        if (list == null || list.size() == 0) {
            return null;
        }
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    /**
     * map to json
     */
    public static String mapToJson(Map map) {
        if (map == null || map.size() == 0) {
            return null;
        }
        Gson gson = new Gson();
        String json = gson.toJson(map);
        return json;
    }

    /**
     * json to javabean
     */
    public static <T> T jsonToObj(String json, Class<T> t) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        Gson gson = new Gson();
        T obj = gson.fromJson(json, t);//对于javabean直接给出class实例
        return obj;
    }

    /**
     * json字符串转List集合
     */
    public static <T> List<T> jsonToList(String json, Class<T[]> type) {
        if (StringUtils.isEmpty(json)) {
            return Collections.emptyList();
        }
        Gson gson = new Gson();
        T[] list = new Gson().fromJson(json, type);
        return Arrays.asList(list);
    }

    /**
     * json字符串转List集合
     */
    public static <K, V> List<Map<K, V>> json2Map(String json, Class<Map[]> type) {
        if (StringUtils.isEmpty(json)) {
            return Collections.emptyList();
        }
        Gson gson = new Gson();
        Map<K, V>[] list = new Gson().fromJson(json, type);
        return Arrays.asList(list);
    }

    /**
     * json字符串转Map
     */
    public static <K, V> Map<K, V> jsonToMap(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<K, V>>() {
        }.getType();
        Map<K, V> maps = gson.fromJson(json, type);
        return maps;
    }

    public static <T> T mapToObject(Map<String, Object> map, Class<T> beanClass) throws Exception {
        if (map == null)
            return null;

        T obj = beanClass.newInstance();

        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            int mod = field.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                continue;
            }

            field.setAccessible(true);
            field.set(obj, map.get(field.getName()));
        }

        return obj;
    }

    public static Map<String, Object> objectToMap(Object obj) throws Exception {
        if (obj == null) {
            return null;
        }

        Map<String, Object> map = new HashMap<String, Object>();

        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(obj));
        }

        return map;
    }
}
