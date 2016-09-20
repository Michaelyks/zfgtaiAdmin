package com.zfgt.orm;

import java.util.Date;

/**
 * ShangyouQie entity. @author MyEclipse Persistence Tools
 */

public class ShangyouQie implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

	private Integer id;
	private String staus;//状态0:有用1:失效
	private String score;
	private Date insertTime;
	private Date updateTime;
	private String phone;
	private Integer lotteryCount;

	// Constructors

	/** default constructor */
	public ShangyouQie() {
	}

	/** minimal constructor */
	public ShangyouQie(String staus, Date updateTime, Integer lotteryCount) {
		this.staus = staus;
		this.updateTime = updateTime;
		this.lotteryCount = lotteryCount;
	}

	/** full constructor */
	public ShangyouQie(String staus, String score, Date insertTime,
			Date updateTime, String phone, Integer lotteryCount) {
		this.staus = staus;
		this.score = score;
		this.insertTime = insertTime;
		this.updateTime = updateTime;
		this.phone = phone;
		this.lotteryCount = lotteryCount;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStaus() {
		return this.staus;
	}

	public void setStaus(String staus) {
		this.staus = staus;
	}

	public String getScore() {
		return this.score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public Date getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getLotteryCount() {
		return this.lotteryCount;
	}

	public void setLotteryCount(Integer lotteryCount) {
		this.lotteryCount = lotteryCount;
	}

}