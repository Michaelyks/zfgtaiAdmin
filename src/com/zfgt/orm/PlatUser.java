package com.zfgt.orm;


/**
 * 按日期统计注册人数
 * 无映射表
 * @author 曾礼强
 */
public class PlatUser {
	private String date;//日期
	private String userscount;//用户人数
	private String insMoney;//投资的金额
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getUserscount() {
		return userscount;
	}
	public void setUserscount(String userscount) {
		this.userscount = userscount;
	}
	public String getInsMoney() {
		return insMoney;
	}
	public void setInsMoney(String insMoney) {
		this.insMoney = insMoney;
	}
	
	
}
