package com.zfgt.orm;

import java.util.Date;

public class CoinPurseProfit {
	private Date insertTime;
	private String payInterest;//收益
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	public String getPayInterest() {
		return payInterest;
	}
	public void setPayInterest(String payInterest) {
		this.payInterest = payInterest;
	}


}
