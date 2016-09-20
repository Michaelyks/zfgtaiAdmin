package com.zfgt.admin.product.dao;

import java.util.Date;

public class TXProcedures {
	
	private String date;//日期
	
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

	private Date insertTime;//插入时间
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}

	private String count;
	
	
	private String czMoney;
	private String txMoney;
	public String getCzMoney() {
		return czMoney;
	}
	public void setCzMoney(String czMoney) {
		this.czMoney = czMoney;
	}
	public String getTxMoney() {
		return txMoney;
	}
	public void setTxMoney(String txMoney) {
		this.txMoney = txMoney;
	}
}
