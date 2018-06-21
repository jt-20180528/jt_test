package com.jt.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;



/**
 * @Auther: Administrator
 * @Date: 2018/5/19 16:51
 * @Description: 租户
 */
@Entity
@Table(name = "t_tenement")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class Tenement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(nullable = false, length = 50)
    private String name;
    @Column(columnDefinition = "tinyint(1) DEFAULT 1 COMMENT '彩种类型：1重庆时时彩'")
    private Integer lotteryType;
    @Column(columnDefinition = "tinyint(1) DEFAULT 1 COMMENT '彩票状态：1待开奖、2已开奖'")
    private Integer lotteryStatus;
    private Integer lotteryNumber;
    @Temporal(TemporalType.TIMESTAMP)
    @Column( columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'")
    private Date openLotteryTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLotteryType() {
        return lotteryType;
    }

    public void setLotteryType(Integer lotteryType) {
        this.lotteryType = lotteryType;
    }

    public Integer getLotteryStatus() {
        return lotteryStatus;
    }

    public void setLotteryStatus(Integer lotteryStatus) {
        this.lotteryStatus = lotteryStatus;
    }

    public Integer getLotteryNumber() {
        return lotteryNumber;
    }

    public void setLotteryNumber(Integer lotteryNumber) {
        this.lotteryNumber = lotteryNumber;
    }

    public Tenement() {
    }

    public Tenement(String name, Integer lotteryType, Integer lotteryStatus, Integer lotteryNumber, Date openLotteryTime) {
        this.name = name;
        this.lotteryType = lotteryType;
        this.lotteryStatus = lotteryStatus;
        this.lotteryNumber = lotteryNumber;
        this.openLotteryTime = openLotteryTime;
    }

    public Date getOpenLotteryTime() {
        return openLotteryTime;
    }

    public void setOpenLotteryTime(Date openLotteryTime) {
        this.openLotteryTime = openLotteryTime;
    }
}
