package com.zfgt.orm;

import java.util.Date;

import com.sun.org.apache.bcel.internal.generic.DADD;

/**
 * 资金流水
 * @author 曾礼强
 * 无表
 */
public class CapitalRecord {
	private Date date;//日期
	private String regCount;//注册人数
	private String ivsCount;//投资人数
	private String ivsMoney;//成交金额
	private String czMoney;//充值金额
	private String txMoney;//提现金额
	private String ctxMoney;// c提现金额;
	public String getCtxMoney() {
		return ctxMoney;
	}
	public void setCtxMoney(String ctxMoney) {
		this.ctxMoney = ctxMoney;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getRegCount() {
		return regCount;
	}
	public void setRegCount(String regCount) {
		this.regCount = regCount;
	}
	public String getIvsCount() {
		return ivsCount;
	}
	public void setIvsCount(String ivsCount) {
		this.ivsCount = ivsCount;
	}
	public String getIvsMoney() {
		return ivsMoney;
	}
	public void setIvsMoney(String ivsMoney) {
		this.ivsMoney = ivsMoney;
	}
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
