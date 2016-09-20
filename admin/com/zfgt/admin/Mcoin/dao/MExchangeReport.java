package com.zfgt.admin.Mcoin.dao;

import java.util.Date;

public class MExchangeReport {
	private String insDate;
	private String address;
	private String contractName;
	private String address_detail;
	
public String getAddress_detail() {
		return address_detail;
	}
	public void setAddress_detail(String address_detail) {
		this.address_detail = address_detail;
	}
public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContractName() {
		return contractName;
	}
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
public String getInsDate() {
		return insDate;
	}
	public void setInsDate(String insDate) {
		this.insDate = insDate;
	}

private String username;
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getLevel() {
	return level;
}
public void setLevel(String level) {
	this.level = level;
}
public String getPrice() {
	return price;
}
public void setPrice(String price) {
	this.price = price;
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

private String title;
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
private String level;
private String price;
private String id;
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
private String status;
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}

private Date insertTime;

private String copies;
private String Coin;
public String getCoin() {
	return Coin;
}
public void setCoin(String coin) {
	Coin = coin;
}

private String msgStatus;
private String mCoinPayId;
private String type;

public String getMsgStatus() {
	return msgStatus;
}
public void setMsgStatus(String msgStatus) {
	this.msgStatus = msgStatus;
}
public String getmCoinPayId() {
	return mCoinPayId;
}
public void setmCoinPayId(String mCoinPayId) {
	this.mCoinPayId = mCoinPayId;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}


}
