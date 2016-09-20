package com.zfgt.orm;

import java.io.Serializable;

/**
 * 发息明细
 * 
 * @author 曾礼强
 * 
 */
public class Fxmx implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title;// 产品名称
	private String username;// 用户ID
	private String realName;// 用户姓名
	private String projectAnnualEarnings;// 项目年化收益
	private String annualEarnings;// 最终年化收益
	private String couponAnnualEarnings;// 加息券年化收益
	private String bcsy;// 本次收益
	private String inMoney;// 投资本金
	private String fx;// 付息
	private String insertTime;// 上线日期
	private String payTime;// 购买时间
	private String returnTime;// 结算日期

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getAnnualEarnings() {
		return annualEarnings;
	}

	public void setAnnualEarnings(String annualEarnings) {
		this.annualEarnings = annualEarnings;
	}

	public String getBcsy() {
		return bcsy;
	}

	public void setBcsy(String bcsy) {
		this.bcsy = bcsy;
	}

	public String getInMoney() {
		return inMoney;
	}

	public void setInMoney(String inMoney) {
		this.inMoney = inMoney;
	}

	public String getFx() {
		return fx;
	}

	public void setFx(String fx) {
		this.fx = fx;
	}

	public String getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(String returnTime) {
		this.returnTime = returnTime;
	}

	public String getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public String getCouponAnnualEarnings() {
		return couponAnnualEarnings;
	}

	public void setCouponAnnualEarnings(String couponAnnualEarnings) {
		this.couponAnnualEarnings = couponAnnualEarnings;
	}

	public String getProjectAnnualEarnings() {
		return projectAnnualEarnings;
	}

	public void setProjectAnnualEarnings(String projectAnnualEarnings) {
		this.projectAnnualEarnings = projectAnnualEarnings;
	}

}
