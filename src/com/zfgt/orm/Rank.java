package com.zfgt.orm;

/**
 * 金额排行表
 * 无映射实体类 
 * @author 覃文勇
 * @createTime 2015-7-30下午5:33:04
 */
public class Rank {
	
	private String usersId;//用户id
	
	private String usersname;//用户名
	
	private String realname;//真实姓名
	
	private String inmoney;//投资总金额
	
	private String money; //充值总金额

	public String getUsersId() {
		return usersId;
	}

	public void setUsersId(String usersId) {
		this.usersId = usersId;
	}

	
	public String getUsersname() {
		return usersname;
	}

	public void setUsersname(String usersname) {
		this.usersname = usersname;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getInmoney() {
		return inmoney;
	}

	public void setInmoney(String inmoney) {
		this.inmoney = inmoney;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}
	
	

}
