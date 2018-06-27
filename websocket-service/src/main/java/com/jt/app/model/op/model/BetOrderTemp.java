package com.jt.app.model.op.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 投注订单表
 */

@Entity
@Table(name="t_bet_order_temp",indexes = {
		@Index(name = "IDX_LOTTERY_NUMBER_ID",  columnList="LOTTERY_NUMBER_ID")})
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class BetOrderTemp implements Serializable {
	private static final long serialVersionUID = 4089503494197642659L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	/**
	 * 开奖号码
	 */
	@Column(name="LOTTERY_NUMBER")
	private String lotteryNumber;

	/**
	 * 彩种期号ID
	 */
	@Column(name="LOTTERY_NUMBER_ID")
	private Integer lotteryNumberId;

	/**
	 * 赔率
	 */
	@Column(name="ODDS")
	private Double odds;

	/**
	 * 中奖注数
	 */
	@Column(name="WIN_COUNT")
	private Integer winCount;

	/**
	 * 中奖金额
	 */
	@Column(name="WIN_AMOUNT")
	private Double winAmount;

	/**
	 * 开奖状态
	 */
	@Column(name="WIN_STATUS")
	private Integer winStatus;

	/**
	 * 中奖信息加密
	 */
	@Column(name="WIN_SECRET")
	private String winSecret;

	public BetOrderTemp() {
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLotteryNumber() {
		return lotteryNumber;
	}

	public void setLotteryNumber(String lotteryNumber) {
		this.lotteryNumber = lotteryNumber;
	}

	public Integer getLotteryNumberId() {
		return lotteryNumberId;
	}

	public void setLotteryNumberId(Integer lotteryNumberId) {
		this.lotteryNumberId = lotteryNumberId;
	}

	public Double getOdds() {
		return odds;
	}

	public void setOdds(Double odds) {
		this.odds = odds;
	}

	public Integer getWinCount() {
		return winCount;
	}

	public void setWinCount(Integer winCount) {
		this.winCount = winCount;
	}

	public Double getWinAmount() {
		return winAmount;
	}

	public void setWinAmount(Double winAmount) {
		this.winAmount = winAmount;
	}

	public Integer getWinStatus() {
		return winStatus;
	}

	public void setWinStatus(Integer winStatus) {
		this.winStatus = winStatus;
	}

	public String getWinSecret() {
		return winSecret;
	}

	public void setWinSecret(String winSecret) {
		this.winSecret = winSecret;
	}
}