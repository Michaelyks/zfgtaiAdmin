package com.zfgt.orm;

import java.util.Date;
/**
 * CoinPurse entity. @author MyEclipse Persistence Tools
 */

public class CoinPurse implements java.io.Serializable {

	// Fields

	private String id;
	private Long usersId;//用户id
	private String status;//状态 0:正常用户
	private Double inMoney;//钱包金额
	private Date insertTime;//插入时间
	private Long investDay;//投资天数
	private Double payInterest;//总收益
	private Double earnings;//收益率
	private String note;//备注
	private Date updateTime;//更新时间

	// Constructors

	/** default constructor */
	public CoinPurse() {
	}

	/** minimal constructor */
	public CoinPurse(String id, Long usersId, String status, Double inMoney,
			Date insertTime, Long investDay, Double payInterest,
			Double earnings) {
		this.id = id;
		this.usersId = usersId;
		this.status = status;
		this.inMoney = inMoney;
		this.insertTime = insertTime;
		this.investDay = investDay;
		this.payInterest = payInterest;
		this.earnings = earnings;
	}

	/** full constructor */
	public CoinPurse(String id, Long usersId, String status, Double inMoney,
			Date insertTime, Long investDay, Double payInterest,
			Double earnings, String note, Date updateTime) {
		this.id = id;
		this.usersId = usersId;
		this.status = status;
		this.inMoney = inMoney;
		this.insertTime = insertTime;
		this.investDay = investDay;
		this.payInterest = payInterest;
		this.earnings = earnings;
		this.note = note;
		this.updateTime = updateTime;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getUsersId() {
		return this.usersId;
	}

	public void setUsersId(Long usersId) {
		this.usersId = usersId;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getInMoney() {
		return this.inMoney;
	}

	public void setInMoney(Double inMoney) {
		this.inMoney = inMoney;
	}

	public Date getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public Long getInvestDay() {
		return this.investDay;
	}

	public void setInvestDay(Long investDay) {
		this.investDay = investDay;
	}

	public Double getPayInterest() {
		return this.payInterest;
	}

	public void setPayInterest(Double payInterest) {
		this.payInterest = payInterest;
	}

	public Double getEarnings() {
		return this.earnings;
	}

	public void setEarnings(Double earnings) {
		this.earnings = earnings;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}