package com.zfgt.admin.product.dao;
import java.util.Date;
public class CZProcedures {
	private String insertTime;//插入时间
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	private String money;//剩余金额
}
