package com.zfgt.orm;

import java.util.Date;

/**
 * CoinPurseFundsRecord entity. @author MyEclipse Persistence Tools
 */

public class CoinPurseFundsRecord implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private Double money;//操作金额
	private String status;//状态 0:成功; 1:失败
	private String type;//操作类别  to:转入   out:转出  shouyi:收益
	private Date insertTime;
	private Long usersId;//用户id
	private Double usersCost;//零钱包剩余金额
	private String recordId;//原记录的id
	private String note;
	
	private Users users;
	
	//无映射
	private String typeChina;//操作类别  to:转入   out:转出  shouyi:收益
	private String statusChina;//状态 0:成功; 1:失败
	
	public String getTypeChina(){
		if("to".equals(type)){
			return "转入";
		}
		if("to".equals(type)){
			return "转出";
		}
		if("to".equals(type)){
			return "收益";
		}
		return typeChina;
	}
	
	public String getStatusChina(){
		if("0".equals(status)){
			return "成功";
		}
		if("1".equals(status)){
			return "失败";
		}
		return statusChina;
	}

	// Constructors

	/** default constructor */
	public CoinPurseFundsRecord() {
	}

	/** minimal constructor */
	public CoinPurseFundsRecord(String id, Double money, String status,
			String type, Date insertTime, Long usersId) {
		this.id = id;
		this.money = money;
		this.status = status;
		this.type = type;
		this.insertTime = insertTime;
		this.usersId = usersId;
	}

	/** full constructor */
	public CoinPurseFundsRecord(String id, Double money, String status,
			String type, Date insertTime, Long usersId, Double usersCost,
			String recordId, String note) {
		this.id = id;
		this.money = money;
		this.status = status;
		this.type = type;
		this.insertTime = insertTime;
		this.usersId = usersId;
		this.usersCost = usersCost;
		this.recordId = recordId;
		this.note = note;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Double getMoney() {
		return this.money;
	}

	public void setMoney(Double money) {
		this.money = money;
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

	public Date getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public Long getUsersId() {
		return this.usersId;
	}

	public void setUsersId(Long usersId) {
		this.usersId = usersId;
	}

	public Double getUsersCost() {
		return this.usersCost;
	}

	public void setUsersCost(Double usersCost) {
		this.usersCost = usersCost;
	}

	public String getRecordId() {
		return this.recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

}