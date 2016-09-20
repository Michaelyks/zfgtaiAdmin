package com.zfgt.admin.product.dao;

import java.util.Date;

public class ShareHongBaoDao {
private String numner;
private String username;
private Double inMoney;
private Double inviteInMoney;
private Date insertTime;
private Double inMoneys;
private Double inviteInMoneys;
public Double getInMoney() {
	return inMoney;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public void setInMoney(Double inMoney) {
	this.inMoney = inMoney;
}
public Double getInviteInMoney() {
	return inviteInMoney;
}
public void setInviteInMoney(Double inviteInMoney) {
	this.inviteInMoney = inviteInMoney;
}
public Double getInMoneys() {
	return inMoneys;
}
public Date getInsertTime() {
	return insertTime;
}
public void setInsertTime(Date insertTime) {
	this.insertTime = insertTime;
}
public void setInMoneys(Double inMoneys) {
	this.inMoneys = inMoneys;
}
public Double getInviteInMoneys() {
	return inviteInMoneys;
}
public void setInviteInMoneys(Double inviteInMoneys) {
	this.inviteInMoneys = inviteInMoneys;
}
public String getNumner() {
	return numner;
}
public void setNumner(String numner) {
	this.numner = numner;
}
}
