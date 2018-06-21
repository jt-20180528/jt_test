package com.jt.app.redis;

public class GlobalCacheKey {

    public static final String order_key = "ORDERS";
    public static final String user_key = "USERS";
    public static final String role_key = "ROLES";
    public static final String resource_key = "RESOURCES";

    public static final String order_batch_key = "ORDER_BATCH";
    public static final String latest_order_key = "LATEST_ORDER";

    //租户缓存对象
    public static final String tenement_key = "TENEMENT";

    //彩票用户缓存对象
    public static final String lottery_user_key = "LOTTERY_USER";
}
