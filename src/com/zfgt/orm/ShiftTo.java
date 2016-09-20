package com.zfgt.orm;

import java.util.Date;

/**
 * ShiftTo entity. @author MyEclipse Persistence Tools
 */

public class ShiftTo implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private Long usersId;//用户ID
	private String status;//状态 -1 无效,0:未发息,1:发息中,2:已转出
	private String type;//类型0:成功1失败
	private String sendInterest;//类型0:未发息 1已发息
	private Double inMoney;//转入金额
	private Date insertTime;//插入时间
	private Double leftMoney;//剩余金额
	private String note;
	private Date updateTime;//修改时间
	
	private Users users;
    //无映射
	private String  typeChina;//类型0:成功1失败(中文)
	private String statusChina;//状态 -1 无效,0:未发息,1:发息中,2:已转出(中文)
	
	public String getTypeChina(){
		if("0".equals(type)){
			return "成功";
		}
		if("1".equals(type)){
			return "失败";
		}
		return typeChina;
	}
	
	public String getStatusChina(){
		if("-1".equals(status)){
			return "无效";
		}
		if("0".equals(status)){
			return "未发息";
		}
		if("1".equals(status)){
			return "发息中";
		}
		if("2".equals(status)){
			return "已转出";
		}
		return statusChina;
	}
	
	// Constructors

	/** default constructor */
	public ShiftTo() {
	}

	/** minimal constructor */
	public ShiftTo(String id, Long usersId, String status, String type,
			Double inMoney, Date insertTime, Double leftMoney) {
		this.id = id;
		this.usersId = usersId;
		this.status = status;
		this.type = type;
		this.inMoney = inMoney;
		this.insertTime = insertTime;
		this.leftMoney = leftMoney;
	}

	/** full constructor */
	public ShiftTo(String id, Long usersId, String status, String type,
			Double inMoney, Date insertTime, Double leftMoney,
			String note, Date updateTime) {
		this.id = id;
		this.usersId = usersId;
		this.status = status;
		this.type = type;
		this.inMoney = inMoney;
		this.insertTime = insertTime;
		this.leftMoney = leftMoney;
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

	public Double getLeftMoney() {
		return this.leftMoney;
	}

	public void setLeftMoney(Double leftMoney) {
		this.leftMoney = leftMoney;
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

	public String getSendInterest() {
		return sendInterest;
	}

	public void setSendInterest(String sendInterest) {
		this.sendInterest = sendInterest;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}
	
}