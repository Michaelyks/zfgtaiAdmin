package com.zfgt.admin.Mcoin.dao;

import java.util.Date;

public class MeowIncomeDAO {
private String type;
private String note;
public String getNote() {
	return note;
}
public void setNote(String note) {
	this.note = note;
}
private String totalCoin;
public String getTotalCoin() {
	return totalCoin;
}
public void setTotalCoin(String totalCoin) {
	this.totalCoin = totalCoin;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
private String number;
private String userName;
private Date insertTime;
public String getNumber() {
	return number;
}
public void setNumber(String number) {
	this.number = number;
}
public String getUserName() {
	return userName;
}
public void setUserName(String userName) {
	this.userName = userName;
}
public Date getInsertTime() {
	return insertTime;
}
public void setInsertTime(Date insertTime) {
	this.insertTime = insertTime;
}
}
