package com.jt.app.redis;

/**
 * 统一消息接受方法接口
 */
public interface MsgReceive {

    void getMsg(Object msg);
}
