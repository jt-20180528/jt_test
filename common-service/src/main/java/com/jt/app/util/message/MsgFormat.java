package com.jt.app.util.message;

import java.io.Serializable;

public class MsgFormat implements Serializable {

    private Object msg;
    private MsgType type;

    /**
     * 消息格式统一转化
     * @param msg
     * @return
     */
    public MsgFormat converMessageFormat(Object msg, MsgType type){
        this.setMsg(msg);
        this.setType(type == null ? MsgType.COMMONMSG : type);
        return this;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public MsgType getType() {
        return type;
    }

    public void setType(MsgType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "MsgFormat{" +
                "msg=" + msg +
                ", type=" + type +
                '}';
    }

    public MsgFormat() {
    }

    public MsgFormat(Object msg, MsgType type) {
        this.msg = msg;
        this.type = type;
    }
}
