package com.zfgt.orm;

import java.util.Date;

/**
 * 充值提现记录
 * 
 * @author 曾礼强 2015年6月29日 15:15:21
 */
public class UserCzTx {
	private String date;// 日期
	private String djsxmze;// 待结算项目总额
	private String djsUserZe;// 待结算用户总额
	private String dqxmze;// 3天内到期项目总额
	private String dqUserZe;// 3天内到期用户总额
	private String ydqsxmze;// 已到期未结算金额
	private String czMoney;// 充值金额
	private String czCount;// 充值次数
	private String txMoney;// 提现金额
	private String txCount;// 提现次数
	private String ctxMoney;// c提现金额
	private String ctxCount;// c提现次数

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDjsxmze() {
		return djsxmze;
	}

	public void setDjsxmze(String djsxmze) {
		this.djsxmze = djsxmze;
	}

	public String getDqxmze() {
		return dqxmze;
	}

	public void setDqxmze(String dqxmze) {
		this.dqxmze = dqxmze;
	}

	public String getYdqsxmze() {
		return ydqsxmze;
	}

	public void setYdqsxmze(String ydqsxmze) {
		this.ydqsxmze = ydqsxmze;
	}

	public String getCzMoney() {
		return czMoney;
	}

	public void setCzMoney(String czMoney) {
		this.czMoney = czMoney;
	}

	public String getCzCount() {
		return czCount;
	}

	public void setCzCount(String czCount) {
		this.czCount = czCount;
	}

	public String getTxMoney() {
		return txMoney;
	}

	public void setTxMoney(String txMoney) {
		this.txMoney = txMoney;
	}

	public String getTxCount() {
		return txCount;
	}

	public void setTxCount(String txCount) {
		this.txCount = txCount;
	}

	public String getDjsUserZe() {
		return djsUserZe;
	}

	public void setDjsUserZe(String djsUserZe) {
		this.djsUserZe = djsUserZe;
	}

	public String getDqUserZe() {
		return dqUserZe;
	}

	public void setDqUserZe(String dqUserZe) {
		this.dqUserZe = dqUserZe;
	}

	public String getCtxMoney() {
		return ctxMoney;
	}

	public void setCtxMoney(String ctxMoney) {
		this.ctxMoney = ctxMoney;
	}

	public String getCtxCount() {
		return ctxCount;
	}

	public void setCtxCount(String ctxCount) {
		this.ctxCount = ctxCount;
	}

}
