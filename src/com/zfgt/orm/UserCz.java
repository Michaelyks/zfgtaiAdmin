package com.zfgt.orm;

/**
 * 充值或提现记录（用于报表显示  ，无映射）
 * @author 覃文勇
 * @createTime 2015-8-4下午2:05:36
 */
public class UserCz {
	
 private String id;// 序号
 
 private String recordNumber;//充值流水号
 
 private String username;//用户名
 
 private String realname;//姓名
 
 private String type;//类型
 
 private String money;//支付金额
 
 private String insertTime;
 
 private String checkTime;
 
 private String orderId;//平台订单号（充值）
 
 private String ybOrderId;//易宝流水号
 
 private String status;//充值状态
 
 private String note;//备注
 
 private String requestId;//平台订单号（提现）
 
 private String drawType;//提现类型;  0:T+0到账; 1:T+1到账;

 

public String getId() {
	return id;
}

public void setId(String id) {
	this.id = id;
}

public String getRecordNumber() {
	return recordNumber;
}

public void setRecordNumber(String recordNumber) {
	this.recordNumber = recordNumber;
}

public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}

public String getRealname() {
	return realname;
}

public void setRealname(String realname) {
	this.realname = realname;
}

public String getType() {
	return type;
}

public void setType(String type) {
	this.type = type;
}

public String getMoney() {
	return money;
}

public void setMoney(String money) {
	this.money = money;
}

public String getInsertTime() {
	return insertTime;
}

public void setInsertTime(String insertTime) {
	this.insertTime = insertTime;
}

public String getCheckTime() {
	return checkTime;
}

public void setCheckTime(String checkTime) {
	this.checkTime = checkTime;
}

public String getOrderId() {
	return orderId;
}

public void setOrderId(String orderId) {
	this.orderId = orderId;
}

public String getYbOrderId() {
	return ybOrderId;
}

public void setYbOrderId(String ybOrderId) {
	this.ybOrderId = ybOrderId;
}

public String getStatus() {
	return status;
}

public void setStatus(String status) {
	this.status = status;
}

public String getNote() {
	return note;
}

public void setNote(String note) {
	this.note = note;
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
 
 
}
