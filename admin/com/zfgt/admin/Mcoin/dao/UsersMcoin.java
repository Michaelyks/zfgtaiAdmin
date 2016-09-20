package com.zfgt.admin.Mcoin.dao;
public class UsersMcoin {
  private String username;
  private String addMCoin;
  public String getAddMCoin() {
	return addMCoin;
}
public void setAddMCoin(String addMCoin) {
	this.addMCoin = addMCoin;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}

public String getUsersId() {
	return usersId;
}
public void setUsersId(String usersId) {
	this.usersId = usersId;
}
public String getReal_name() {
	return real_name;
}
public void setReal_name(String real_name) {
	this.real_name = real_name;
}
public String getTotal_point() {
	return total_point;
}
public void setTotal_point(String total_point) {
	this.total_point = total_point;
}
public String getCoin() {
	return coin;
}
public void setCoin(String coin) {
	this.coin = coin;
}
private String usersId;
  private String real_name;
  private String total_point;
  private String coin;
  private Double usage_rate;
public Double getUsage_rate() {
	return usage_rate;
}
public void setUsage_rate(Double usage_rate) {
	this.usage_rate = usage_rate;
}
}
