package com.zfgt.orm;

public class Reflect {
private String username;
private String requestId;///原订单号

private String orderId;//商业订单号
private String accounId;
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getRequestId() {
	return requestId;
}
public void setRequestId(String requestId) {
	this.requestId = requestId;
}
public String getOrderId() {
	return orderId;
}
public void setOrderId(String orderId) {
	this.orderId = orderId;
}
public String getAccounId() {
	return accounId;
}
public void setAccounId(String accounId) {
	this.accounId = accounId;
}
}
