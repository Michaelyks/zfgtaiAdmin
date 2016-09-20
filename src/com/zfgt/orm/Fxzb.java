package com.zfgt.orm;

import java.io.Serializable;
/**
 * 发息总表
 * @author 曾礼强
 *2015年7月6日 13:55:29
 */
public class Fxzb implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title;//产品名称
	private String annualEarnings;//年化收益
	private String allCopies;//总份数
	private String finishTime;//项目预计结束时间;普通项目的话:为项目预计结束时间; 新手专属项目:预计进入已售罄的时间; 
	//临时字段,没有ORM映射
	private String cplx;//产品类型(中文)
	private String jxfs;//计息方式(中文)
	private String fxfs;//付息方式(中文)
	private String qtje;//动态的起头金额;
	private String sflx;//实发利息
	private String insertTime;//上线时间
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAnnualEarnings() {
		return annualEarnings;
	}
	public void setAnnualEarnings(String annualEarnings) {
		this.annualEarnings = annualEarnings;
	}
	public String getAllCopies() {
		return allCopies;
	}
	public void setAllCopies(String allCopies) {
		this.allCopies = allCopies;
	}
	public String getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}
	public String getCplx() {
		return cplx;
	}
	public void setCplx(String cplx) {
		this.cplx = cplx;
	}
	public String getJxfs() {
		return jxfs;
	}
	public void setJxfs(String jxfs) {
		this.jxfs = jxfs;
	}
	public String getFxfs() {
		return fxfs;
	}
	public void setFxfs(String fxfs) {
		this.fxfs = fxfs;
	}
	public String getQtje() {
		return qtje;
	}
	public void setQtje(String qtje) {
		this.qtje = qtje;
	}
	public String getSflx() {
		return sflx;
	}
	public void setSflx(String sflx) {
		this.sflx = sflx;
	}
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
	
}
