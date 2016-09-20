package com.zfgt.orm;

import java.util.Date;



/**
 * @author 曾礼强
 * 2015年8月17日下午4:25:50
 * 零钱包自动转入记录
 */
public class AutoShiftTo implements java.io.Serializable {

	// Fields

	private String id;
	private Long usersId;//用户ID
	private String status;//状态 0:成功1：失败
	private String type;//状态 0:启动1：关闭
	private Double leftMoney;//剩余金额
	private Date insertTime;//插入时间
	private String note;//备注
	private Date updateTime;//更新时间

	// Constructors

	/** default constructor */
	public AutoShiftTo() {
	}

	/** minimal constructor */
	public AutoShiftTo(String id, Long usersId, String status, String type,
			Double leftMoney, Date insertTime) {
		this.id = id;
		this.usersId = usersId;
		this.status = status;
		this.type = type;
		this.leftMoney = leftMoney;
		this.insertTime = insertTime;
	}

	/** full constructor */
	public AutoShiftTo(String id, Long usersId, String status, String type,
			Double leftMoney, Date insertTime, String note,
			Date updateTime) {
		this.id = id;
		this.usersId = usersId;
		this.status = status;
		this.type = type;
		this.leftMoney = leftMoney;
		this.insertTime = insertTime;
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

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getLeftMoney() {
		return this.leftMoney;
	}

	public void setLeftMoney(Double leftMoney) {
		this.leftMoney = leftMoney;
	}

	public Date getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
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