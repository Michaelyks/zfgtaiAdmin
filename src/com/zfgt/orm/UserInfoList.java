package com.zfgt.orm;

import java.util.Date;

public class UserInfoList {
	private String id;
	private String inMoney;

	private String username;
	private String province;
	private String city;
	private String cardType;
	private Date insertTime;
	private String registPlatform;
	private String realName ;
	private String sex;
	private String age;
	private String birthday;
	private String level;
	private String isBindBank;
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getInMoney() {
		return inMoney;
	}
	public void setInMoney(String inMoney) {
		this.inMoney = inMoney;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	public String getRegistPlatform() {
		return registPlatform;
	}
	public void setRegistPlatform(String registPlatform) {
		this.registPlatform = registPlatform;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getIsBindBank() {
		return isBindBank;
	}
	public void setIsBindBank(String isBindBank) {
		this.isBindBank = isBindBank;
	}


}
