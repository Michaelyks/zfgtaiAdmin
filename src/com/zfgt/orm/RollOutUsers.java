package com.zfgt.orm;

import java.util.Date;

public class RollOutUsers {
	private String leftMoney;//剩余金额
	private String usersname;
	public String getLeftMoney() {
		return leftMoney;
	}
	public void setLeftMoney(String leftMoney) {
		this.leftMoney = leftMoney;
	}
	public String getUsersname() {
		return usersname;
	}
	public void setUsersname(String usersname) {
		this.usersname = usersname;
	}
	private Date insertTime;
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
}
