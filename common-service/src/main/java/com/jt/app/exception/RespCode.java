package com.jt.app.exception;

public enum RespCode {

    SUCCESS(0, "成功"),
    PARAMETER_ERROR(0001, "参数错误"),
    REQUEST_ERROR(0002, "请求异常，请联系管理员"),
    LOGIN_FAIL(0003, "用户名或密码错误"),
    NULL_POINT(0004,"空指针异常！");






    private Integer resCode;
    private String resMsg;

    RespCode(Integer resCode, String resMsg) {
        this.resCode = resCode;
        this.resMsg = resMsg;
    }

    public Integer getResCode() {
        return resCode;
    }

    public String getResMsg() {
        return resMsg;
    }
}
