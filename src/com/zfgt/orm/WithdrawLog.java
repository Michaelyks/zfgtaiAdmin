package com.zfgt.orm;

import java.util.Date;

/**手动提现记录表
 * @author 覃文勇
 * @createTime 2016-3-29下午2:35:48
 */
public class WithdrawLog {
private String id;
private String username;
private String requestId;//原订单号
private Long recordNumber;//原流水号
private String orderId;//商业订单号
private String accountNumber;//银通订单号
private Long usersId;//用户ID
private Double money;//提现金额
private String status;//提现状态;0:待审核;1:提现成功;2提现失败;3:正在审核
private Date insertTime;//插入时间
private Date oldTime;//原始时间
private String type;//提现类型;0:易宝提现;1:支付宝提现;2:连连提现
private String errorCode;//错误码
private String note;//备注
private Long usersAdminId;//操作人id

private Users users;
private UsersAdmin usersAdmin;



public Users getUsers() {
	return users;
}
public void setUsers(Users users) {
	this.users = users;
}
public UsersAdmin getUsersAdmin() {
	return usersAdmin;
}
public void setUsersAdmin(UsersAdmin usersAdmin) {
	this.usersAdmin = usersAdmin;
}
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

public String getAccountNumber() {
	return accountNumber;
}
public void setAccountNumber(String accountNumber) {
	this.accountNumber = accountNumber;
}
public Long getRecordNumber() {
	return recordNumber;
}
public void setRecordNumber(Long recordNumber) {
	this.recordNumber = recordNumber;
}
public Long getUsersId() {
	return usersId;
}
public void setUsersId(Long usersId) {
	this.usersId = usersId;
}
public Double getMoney() {
	return money;
}
public void setMoney(Double money) {
	this.money = money;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public Date getInsertTime() {
	return insertTime;
}
public void setInsertTime(Date insertTime) {
	this.insertTime = insertTime;
}

public Date getOldTime() {
	return oldTime;
}
public void setOldTime(Date oldTime) {
	this.oldTime = oldTime;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
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
public Long getUsersAdminId() {
	return usersAdminId;
}
public void setUsersAdminId(Long usersAdminId) {
	this.usersAdminId = usersAdminId;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}


}
