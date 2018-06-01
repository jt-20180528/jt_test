package com.jt.app.model.mongodb;


import org.springframework.data.annotation.Id;

import java.util.Date;

public class Order {

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
}
