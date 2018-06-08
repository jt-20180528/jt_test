package com.jt.app.util.message;

public enum MsgType {

    SYNCDB(1,"同步到数据库"), COMMONMSG(2,"普通消息"), TASKMSG(3,"定时任务消息");

    //消息编码
    private Integer msgCode;

    //消息描述
    private String description;

    MsgType(Integer msgCode,String description){
        this.description = description;
        this.msgCode = msgCode;
    }

    public Integer getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(Integer msgCode) {
        this.msgCode = msgCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "MsgType{" +
                "msgCode=" + msgCode +
                ", description='" + description + '\'' +
                '}';
    }
}
