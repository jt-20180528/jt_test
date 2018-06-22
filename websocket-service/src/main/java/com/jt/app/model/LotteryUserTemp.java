package com.jt.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Auther: Administrator
 * @Date: 2018/5/19 16:51
 * @Description: 彩票用户
 */
@Entity
@Table(name = "t_lotteryUserTemp")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class LotteryUserTemp implements Serializable {

    @Id
    private Integer id;
    @Column(length = 20, nullable = false)
    private String name;

    @Column(columnDefinition = "tinyint(1) DEFAULT 1 COMMENT '彩种类型：1重庆时时彩'")
    private Integer lotteryType;
    @Column(columnDefinition = "tinyint(1) DEFAULT 1 COMMENT '彩票状态：1待开奖、2已开奖'")
    private Integer lotteryStatus;
    private Integer lotteryNumber;
    private Integer tenementId;
    @Column(columnDefinition = "tinyint(1) DEFAULT 1 COMMENT '中奖状态：1待开奖、2中奖、3未中奖'")
    private Integer winLotteryStatus;

    public LotteryUserTemp() {
    }

    public LotteryUserTemp(Integer id, String name, Integer lotteryType, Integer lotteryStatus, Integer lotteryNumber, Integer tenementId,Integer winLotteryStatus) {
        this.id = id;
        this.name = name;
        this.lotteryType = lotteryType;
        this.lotteryStatus = lotteryStatus;
        this.lotteryNumber = lotteryNumber;
        this.tenementId = tenementId;
        this.winLotteryStatus = winLotteryStatus;
    }

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

    public Integer getTenementId() {
        return tenementId;
    }

    public void setTenementId(Integer tenementId) {
        this.tenementId = tenementId;
    }

    public Integer getWinLotteryStatus() {
        return winLotteryStatus;
    }

    public void setWinLotteryStatus(Integer winLotteryStatus) {
        this.winLotteryStatus = winLotteryStatus;
    }
}
