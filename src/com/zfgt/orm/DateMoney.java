package com.zfgt.orm;

import java.io.Serializable;
/**
 * 无映射
 * 方便拓展充值统计和体现统计
 * @author 曾礼强
 * 
 */
public class DateMoney implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String date;//日期
	private String money;//金额
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	
}
