package com.jt.app.model.mongodb;


import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单实体类，测试Mongodb使用
 */
public class Order implements Serializable {

    /**
     * 编号.
     */
    @Id
    private String id;
    /**
     * 订单编号
     */
    private String orderCode;
    /**
     * 用户编号
     */
    private String userId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 订单状态
     */
    private Integer status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Order(String orderCode, String userId, Date createTime, Integer status) {
        this.orderCode = orderCode;
        this.userId = userId;
        this.createTime = createTime;
        this.status = status;
    }

    public Order() {
    }

    public Order(String id, String orderCode, String userId, Date createTime, Integer status) {
        this.id = id;
        this.orderCode = orderCode;
        this.userId = userId;
        this.createTime = createTime;
        this.status = status;
    }
}
