package com.zfgt.orm;

import java.util.Date;

public class ZeroPurse {
	private Date insertTime;//插入时间
	private String updateTime;//插入时间
	private String leftMoney;//剩余金额
	private String bankAccount;
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getLeftMoney() {
		return leftMoney;
	}
	public void setLeftMoney(String leftMoney) {
		this.leftMoney = leftMoney;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	private String username;//用户名
}
