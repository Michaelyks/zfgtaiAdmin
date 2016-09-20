package com.zfgt.orm;

import java.util.Date;

/**
 * RootTxRecord entity. @author MyEclipse Persistence Tools
 */

public class RootTxRecord implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private Double money;//操作金额
	private Date insertTime;//插入时间
	private Long usersId;//用户ID
	private Long userAdminId;//管理员ID
	private String status;//状态：0成功 1失败
	private Date updateTime;//更新时间
	private String drawType;//提现类型;  0:T+0到账; 1:T+1到账;
	private String requestId;//请求ID
	private String usersIp;//请求ID
	private String ybOrderId;//易宝交易流水号
	private String errorCode;//错误码
	private String note;//备注
	private String errorMessage;//错误码
	private Users users;//用户;
	// Constructors
	
	
	public String getTxzt() {
		String temp = "待审核";
		if("0".equals(status)){
			temp = "提现成功";
		}else if("1".equals(status))
			temp = "提现失败";
		return temp;
	}

	/** default constructor */
	public RootTxRecord() {
	}

	/** minimal constructor */
	public RootTxRecord(String id, Date insertTime, Date updateTime) {
		this.id = id;
		this.insertTime = insertTime;
		this.updateTime = updateTime;
	}


	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}



	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Date getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getUsersId() {
		return usersId;
	}

	public void setUsersId(Long usersId) {
		this.usersId = usersId;
	}

	public Long getUserAdminId() {
		return userAdminId;
	}

	public void setUserAdminId(Long userAdminId) {
		this.userAdminId = userAdminId;
	}

	public String getDrawType() {
		return drawType;
	}

	public void setDrawType(String drawType) {
		this.drawType = drawType;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getUsersIp() {
		return usersIp;
	}

	public void setUsersIp(String usersIp) {
		this.usersIp = usersIp;
	}

	public String getYbOrderId() {
		return ybOrderId;
	}

	public void setYbOrderId(String ybOrderId) {
		this.ybOrderId = ybOrderId;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}
	
	

}