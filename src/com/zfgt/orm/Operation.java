package com.zfgt.orm;

import java.io.Serializable;

/**
 * 运营报表
 * @author 曾礼强
 *2015年7月2日 13:48:11
 *无数据库
 */
public class Operation  implements Serializable{
	private static final long serialVersionUID = 1L;
	private String date;//日期
	private String jhUser;//激活用户
/*	private String zrUser;//自然用户
	private String zrUserPercentage;//自然用户百分比
*/	private String inscount;//投资的次数
	private String stcs;//首投次数
	private String ftinscs;//复投资的次数
//	private String hbfxPercentage;//还本付息再次投资率
	private String allcopies;//投资金额
	private String hkje;//还款金额
	private String payLx;//支付利息
	private String insusercount;//投资的人数
	private String stinsrs;//首投的人数
	private String xzectz;//新增第二次投资金额
	private String ftinsrs;//复投资的人数
	private String rjtz;//人均投资
	private String stze;//首投总额
	private String xzectzPercentage;//首投总额
	private String ftl;//转换率
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getJhUser() {
		return jhUser;
	}
	public void setJhUser(String jhUser) {
		this.jhUser = jhUser;
	}
	public String getInscount() {
		return inscount;
	}
	public void setInscount(String inscount) {
		this.inscount = inscount;
	}
	public String getStcs() {
		return stcs;
	}
	public void setStcs(String stcs) {
		this.stcs = stcs;
	}
	public String getFtinscs() {
		return ftinscs;
	}
	public void setFtinscs(String ftinscs) {
		this.ftinscs = ftinscs;
	}
	public String getAllcopies() {
		return allcopies;
	}
	public void setAllcopies(String allcopies) {
		this.allcopies = allcopies;
	}
	public String getHkje() {
		return hkje;
	}
	public void setHkje(String hkje) {
		this.hkje = hkje;
	}
	public String getPayLx() {
		return payLx;
	}
	public void setPayLx(String payLx) {
		this.payLx = payLx;
	}
	public String getInsusercount() {
		return insusercount;
	}
	public void setInsusercount(String insusercount) {
		this.insusercount = insusercount;
	}
	public String getStinsrs() {
		return stinsrs;
	}
	public void setStinsrs(String stinsrs) {
		this.stinsrs = stinsrs;
	}
	public String getXzectz() {
		return xzectz;
	}
	public void setXzectz(String xzectz) {
		this.xzectz = xzectz;
	}
	public String getFtinsrs() {
		return ftinsrs;
	}
	public void setFtinsrs(String ftinsrs) {
		this.ftinsrs = ftinsrs;
	}
	public String getRjtz() {
		return rjtz;
	}
	public void setRjtz(String rjtz) {
		this.rjtz = rjtz;
	}
	public String getStze() {
		return stze;
	}
	public void setStze(String stze) {
		this.stze = stze;
	}
	public String getXzectzPercentage() {
		return xzectzPercentage;
	}
	public void setXzectzPercentage(String xzectzPercentage) {
		this.xzectzPercentage = xzectzPercentage;
	}
	public String getFtl() {
		return ftl;
	}
	public void setFtl(String ftl) {
		this.ftl = ftl;
	}
	
}
