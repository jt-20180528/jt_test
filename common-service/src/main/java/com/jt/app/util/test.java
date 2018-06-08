package com.jt.app.util;

public enum test {
    SyncDB(1,"同步到数据库"), commonMsg(2,"普通消息"), taskMsg(3,"定时任务消息");
    public Integer msgCode;
    public String description;
    test(Integer msgCode,String description){
        this.description = description;
        this.msgCode = msgCode;
    }
}
