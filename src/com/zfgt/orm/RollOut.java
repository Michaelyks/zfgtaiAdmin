package com.zfgt.orm;

import java.util.Date;

/**
 * RollOut entity. @author MyEclipse Persistence Tools
 */

public class RollOut implements java.io.Serializable {

	// Fields

	private String id;
	private Long usersId;//用户ＩＤ
	private String status;//状态 0:成功1：失败
	private String type;//类型0:转出
	private Double inMoney;//转出金额
	private Date insertTime;//插入时间
	private Double leftMoney;//剩余金额
	private String note;
	private Date updateTime;//修改时间
	
	private Users users;
	//无映射
	private String statusChina;//状态 0:成功1：失败(中文)
	private String typeChina;//类型0:转出
	
	public String getStatusChina(){
		if("0".equals(status)){
			return "成功";
		}
		if("1".equals(status)){
			return "失败";
		}
		return statusChina;
	}
	
	public String getTypeChina(){
		if("0".equals(type)){
			return "转出";
		}
		return typeChina;
	}

	// Constructors

	/** default constructor */
	public RollOut() {
	}

	/** minimal constructor */
	public RollOut(String id, Long usersId, String status, String type,
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
	public RollOut(String id, Long usersId, String status, String type,
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

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

}