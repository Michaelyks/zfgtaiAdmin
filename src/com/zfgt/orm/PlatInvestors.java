package com.zfgt.orm;

import java.util.Date;

public class PlatInvestors {
	private String date;//日期
	private String real_name;
	public String getReal_name() {
		return real_name;
	}
	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	private String insMoney;//投资的金额
	public String getInsMoney() {
		return insMoney;
	}
	public void setInsMoney(String insMoney) {
		this.insMoney = insMoney;
	}
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	public String getCopies() {
		return copies;
	}
	public void setCopies(String copies) {
		this.copies = copies;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getCoupon() {
		return coupon;
	}
	public void setCoupon(String coupon) {
		this.coupon = coupon;
	}
	private Date insertTime;//插入时间
	private String copies;
	private String username;//用户名
	private String coupon;
	
}
