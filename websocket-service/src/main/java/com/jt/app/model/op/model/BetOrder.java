package com.jt.app.model.op.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 投注订单表
 */

@Entity
@Table(name="t_bet_order",indexes = {
		@Index(name = "IDX_ORDERNO",  columnList="ORDERNO"),
		@Index(name = "IDX_USER_ID", columnList="USER_ID"),
		@Index(name = "IDX_BET_ID", columnList="BET_ID"),
		@Index(name = "IDX_LOTTERY_NUMBER_ID", columnList="LOTTERY_NUMBER_ID"),
		@Index(name = "IDX_GAME_ID", columnList="GAME_ID"),
		@Index(name = "IDX_TENANT_CODE", columnList="TENANT_CODE"),
		@Index(name = "IDX_BET_TIME", columnList="BET_TIME")})
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class BetOrder implements Serializable {
	private static final long serialVersionUID = 4089503494197642659L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	/**
	 * 投注单号
	 */
	@Column(name="ORDERNO")
	private String orderno;
	
	/**
	 * 批处理订单号
	 */
	@Column(name="BATCH_ORDERNO")
	private String batchOrderno;
	
	/**
	 * 租户代号
	 */
	@Column(name="TENANT_CODE")
	private String tenantCode;
	
	/**
	 * 用户id
	 */
	@Column(name="USER_ID")
	private Integer userId;
	
	/**
	 * 用户名
	 */
	@Column(name="USER_NAME")
	private String userName;
	
	/**
	 * 游戏id
	 */
	@Column(name="GAME_ID")
	private Integer gameId;
	
	/**
	 * 单注金额 - 2.00
	 */
	@Column(name="BET_SINGLE_AMOUNT")
	private Double betSingleAmount;
	
	/**
	 * 投注注数
	 */
	@Column(name="BET_COUNT")
	private Integer betCount;
	
	/**
	 * 投注总额
	 */
	@Column(name="BET_TOTAL_AMOUNT")
	private Double betTotalAmount;
	
	/**
	 * 投注位
	 */
	@Column(name="BET_DIGITS")
	private String betDigits;
	
	/**
	 * 投注类型Id
	 */
	@Column(name="BET_ID")
	private Integer betId;
	
	/**
	 * 投注倍数
	 */
	@Column(name="BET_MULTIPLE")
	private Integer betMultiple;
	
	/**
	 * 投注号码
	 */
	@Lob
	@Column(name="BET_NUMBER")
	private String betNumber;
	
	/**
	 * 投注加密信息
	 */
	@Column(name="BET_SECRET")
	private String betSecret;

	/**
	 * 投注时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="BET_TIME")
	private Date betTime;

	/**
	 * 投注方式
	 */
	@Column(name="BET_WAY",columnDefinition = "tinyint(1) DEFAULT 1 COMMENT '投注方式'")
	private Integer betWay;
	
	/**
	 * 追号号码ID
	 */
	@Column(name="CHASE_NUMBER_ID")
	private Integer chaseNumberId;
	
	/**
	 * 追号记录ID
	 */
	@Column(name="CHASE_ORDER_ID")
	private Integer chaseOrderId;
	
	/**
	 * 投注设备
	 */
	@Column(name="DEVICE")
	private Integer Device;
	
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
	 * 订单状态
	 */
	@Column(name="ORDER_STATUS", columnDefinition="tinyint(1) DEFAULT 1 COMMENT '订单状态'")
	private Integer orderStatus;
	
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
	 * 派奖状态
	 */
	@Column(name="AWARD_STATUS")
	private Integer awardStatus;
	
	/**
	 * 派奖时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="AWARD_TIME")
	private Date awardTime;
	
	/**
	 * 所选返点
	 */
	@Column(name="REBATE_CHOOSE")
	private Double rebateChoose;
	
	/**
	 * 用户返点
	 */
	@Column(name="REBATE_USER")
	private Float rebateUser;
	
	/**
	 * 投注返点金额
	 */
	@Column(name="REBATE_AMOUNT")
	private Double rebateAmount;
	
	/**
	 * 代理返点金额（注单所有代理返点之和）
	 * @Deprecated 2018-01-13 by z。可以从期号表或用户日结报表统计。
	 */
	@Deprecated
	@Column(name="AGENT_REBATE_AMOUNT")
	private Double agentRebateAmount;
	
	/**
	 * 返点发放状态
	 */
	@Column(name="BET_REBATE_STATUS", columnDefinition="tinyint(1) DEFAULT 1 COMMENT '发放状态'")
	private Integer betRebateStatus;
	
	/**
	 * 代理返点派发状态
	 */
	@Column(name = "AGENT_REBATE_STATUS", columnDefinition="tinyint(1) DEFAULT 1 COMMENT '代理返点派发状态'")
	private Integer agentRebateStatus;
	  
	@Column(name = "LAST_MODIFIED_BY", columnDefinition="varchar(16) DEFAULT NULL COMMENT '最后修改人'")
	private String lastModifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_MODIFIED_DATE", columnDefinition="timestamp NULL DEFAULT NULL COMMENT '最后修改时间'")
	private Date lastModifiedDate;
	
	/**
	 * 适应赔率(如果为0,取本表的odds字段作为赔率,如果为1,则取赔率表的赔率开奖)
	 */
	@Column(name="ADAPT",columnDefinition = "tinyint(1) DEFAULT 1 COMMENT '适应赔率(如果为0,取本表的odds字段作为赔率,如果为1,则取赔率表的赔率开奖)'")
	private Integer adapt;
	
	/**
	 * 代替原PLAYGROUP字段
	 */
	@Column(name = "PLATFORM_TYPE", columnDefinition="tinyint(2) DEFAULT 1 COMMENT '平台类型'", nullable=false)
	private Integer platformType;
	
	/**
	 * 中奖信息加密
	 */
	@Column(name="WIN_SECRET")
	private String winSecret;

	@Column(name = "ROOM_ID")
	private Integer roomId;
	
	public BetOrder() {
	}

	public Date getAwardTime() {
		return this.awardTime;
	}

	public void setAwardTime(Date awardTime) {
		this.awardTime = awardTime;
	}

	public String getBatchOrderno() {
		return this.batchOrderno;
	}

	public void setBatchOrderno(String batchOrderno) {
		this.batchOrderno = batchOrderno;
	}

	public Double getBetSingleAmount() {
		return this.betSingleAmount;
	}

	public void setBetSingleAmount(Double betSingleAmount) {
		this.betSingleAmount = betSingleAmount;
	}

	public Integer getBetCount() {
		return this.betCount;
	}

	public void setBetCount(Integer betCount) {
		this.betCount = betCount;
	}

	public String getBetDigits() {
		return this.betDigits;
	}

	public void setBetDigits(String betDigits) {
		this.betDigits = betDigits;
	}

	public Integer getBetId() {
		return this.betId;
	}

	public void setBetId(Integer betId) {
		this.betId = betId;
	}

	public Integer getBetMultiple() {
		return this.betMultiple;
	}

	public void setBetMultiple(Integer betMultiple) {
		this.betMultiple = betMultiple;
	}

	public String getBetNumber() {
		return this.betNumber;
	}

	public void setBetNumber(String betNumber) {
		this.betNumber = betNumber;
	}

	public String getBetSecret() {
		return this.betSecret;
	}

	public void setBetSecret(String betSecret) {
		this.betSecret = betSecret;
	}

	public Date getBetTime() {
		return this.betTime;
	}

	public void setBetTime(Date betTime) {
		this.betTime = betTime;
	}

	public Double getBetTotalAmount() {
		return this.betTotalAmount;
	}

	public void setBetTotalAmount(Double betTotalAmount) {
		this.betTotalAmount = betTotalAmount;
	}

	public Integer getChaseNumberId() {
		return this.chaseNumberId;
	}

	public void setChaseNumberId(Integer chaseNumberId) {
		this.chaseNumberId = chaseNumberId;
	}

	public Integer getChaseOrderId() {
		return this.chaseOrderId;
	}
	public void setChaseOrderId(Integer chaseOrderId) {
		this.chaseOrderId = chaseOrderId;
	}

	public Integer getGameId() {
		return this.gameId;
	}

	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}

	public String getLotteryNumber() {
		return this.lotteryNumber;
	}

	public void setLotteryNumber(String lotteryNumber) {
		this.lotteryNumber = lotteryNumber;
	}

	public Integer getLotteryNumberId() {
		return this.lotteryNumberId;
	}

	public void setLotteryNumberId(Integer lotteryNumberId) {
		this.lotteryNumberId = lotteryNumberId;
	}

	public Double getOdds() {
		return this.odds;
	}

	public void setOdds(Double odds) {
		this.odds = odds;
	}

	public String getOrderno() {
		return this.orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public Double getRebateAmount() {
		return this.rebateAmount;
	}

	public void setRebateAmount(Double rebateAmount) {
		this.rebateAmount = rebateAmount;
	}

	public Double getRebateChoose() {
		return this.rebateChoose;
	}

	public void setRebateChoose(Double rebateChoose) {
		this.rebateChoose = rebateChoose;
	}

	public String getTenantCode() {
		return this.tenantCode;
	}

	public void setTenantCode(String tenantCode) {
		this.tenantCode = tenantCode;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Double getWinAmount() {
		return this.winAmount;
	}

	public void setWinAmount(Double winAmount) {
		this.winAmount = winAmount;
	}

	public Integer getWinCount() {
		return this.winCount;
	}

	public void setWinCount(Integer winCount) {
		this.winCount = winCount;
	}

	public String getWinSecret() {
		return this.winSecret;
	}

	public void setWinSecret(String winSecret) {
		this.winSecret = winSecret;
	}

	public Float getRebateUser() {
		return rebateUser;
	}

	public void setRebateUser(Float rebateUser) {
		this.rebateUser = rebateUser;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Integer getAdapt() {
		return adapt;
	}

	public void setAdapt(Integer adapt) {
		this.adapt = adapt;
	}

	@Deprecated
	public Double getAgentRebateAmount() {
		return agentRebateAmount;
	}

	@Deprecated
	public void setAgentRebateAmount(Double agentRebateAmount) {
		this.agentRebateAmount = agentRebateAmount;
	}

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}
}