package com.zfgt.orm;

/**
 * 零钱包报表（无映射）
 * @author liuchao
 *
 */
public class CoinPurseReport {
	private int id;
	private String date="";//日期
	private String inMoney="";//转入金额
	private String aWeekRetentionRate="";//7天保留率
	private String aWeekWithoutMovingMoneyRate="";//7天不可动金额比
	private String aWeekWithoutMovingMoney="";//7天不可动金额
	private String outMoney="";//转出金额
	private String cunliang="";//存量
	private String fuxi="";//付息
	private String chongzhiRate="";//转入金额占充值金额比重
	private String baiyimaozhanghuRate="";//转入金额占中泰理财账户比重
	private String touziRate="";//转入金额占投资金额比重	
	private String shouyiMoney="";
	private String inTime="";
	private String outTime="";
	private String shouyiTime="";
	private String mobileNum="";
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getInMoney() {
		return inMoney;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setInMoney(String inMoney) {
		this.inMoney = inMoney;
	}
	public String getaWeekRetentionRate() {
		return aWeekRetentionRate;
	}
	public String getShouyiMoney() {
		return shouyiMoney;
	}
	public void setShouyiMoney(String shouyiMoney) {
		this.shouyiMoney = shouyiMoney;
	}
	public String getInTime() {
		return inTime;
	}
	public void setInTime(String inTime) {
		this.inTime = inTime;
	}
	public String getOutTime() {
		return outTime;
	}
	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}
	public String getShouyiTime() {
		return shouyiTime;
	}
	public void setShouyiTime(String shouyiTime) {
		this.shouyiTime = shouyiTime;
	}
	public String getMobileNum() {
		return mobileNum;
	}
	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}
	public void setaWeekRetentionRate(String aWeekRetentionRate) {
		this.aWeekRetentionRate = aWeekRetentionRate;
	}
	public String getaWeekWithoutMovingMoneyRate() {
		return aWeekWithoutMovingMoneyRate;
	}
	public void setaWeekWithoutMovingMoneyRate(String aWeekWithoutMovingMoneyRate) {
		this.aWeekWithoutMovingMoneyRate = aWeekWithoutMovingMoneyRate;
	}
	public String getaWeekWithoutMovingMoney() {
		return aWeekWithoutMovingMoney;
	}
	public void setaWeekWithoutMovingMoney(String aWeekWithoutMovingMoney) {
		this.aWeekWithoutMovingMoney = aWeekWithoutMovingMoney;
	}
	public String getOutMoney() {
		return outMoney;
	}
	public void setOutMoney(String outMoney) {
		this.outMoney = outMoney;
	}
	public String getCunliang() {
		return cunliang;
	}
	public void setCunliang(String cunliang) {
		this.cunliang = cunliang;
	}
	public String getFuxi() {
		return fuxi;
	}
	public void setFuxi(String fuxi) {
		this.fuxi = fuxi;
	}
	public String getChongzhiRate() {
		return chongzhiRate;
	}
	public void setChongzhiRate(String chongzhiRate) {
		this.chongzhiRate = chongzhiRate;
	}
	public String getBaiyimaozhanghuRate() {
		return baiyimaozhanghuRate;
	}
	public void setBaiyimaozhanghuRate(String baiyimaozhanghuRate) {
		this.baiyimaozhanghuRate = baiyimaozhanghuRate;
	}
	public String getTouziRate() {
		return touziRate;
	}
	public void setTouziRate(String touziRate) {
		this.touziRate = touziRate;
	}
}
