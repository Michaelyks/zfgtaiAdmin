package com.zfgt.orm;

import java.io.Serializable;

/**
 * 用户转换
 * @author 曾礼强
 *
 */
public class UsersConvert  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String date;//日期
	private String reuserscount;//注册人数
	private String reginscount;//注册并投资的人数
	private String regcopies;//注册投资购买的份数
	private String bindcount;//绑卡的人数
	private String insusercount;//投资的人数
	private String inscount;//投资的次数
	private String ftinscs;//复投资的次数
	private String ftinsrs;//复投资的人数
	private String allcopies;//所有的份数
	private String stinsrs;//首投的人数
	private String zhl;//转换率
	private String ftl;//转换率
	private String rjtz;//人均投资
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getReuserscount() {
		return reuserscount;
	}
	public void setReuserscount(String reuserscount) {
		this.reuserscount = reuserscount;
	}
	public String getReginscount() {
		return reginscount;
	}
	public void setReginscount(String reginscount) {
		this.reginscount = reginscount;
	}
	public String getRegcopies() {
		return regcopies;
	}
	public void setRegcopies(String regcopies) {
		this.regcopies = regcopies;
	}
	public String getBindcount() {
		return bindcount;
	}
	public void setBindcount(String bindcount) {
		this.bindcount = bindcount;
	}
	public String getInsusercount() {
		return insusercount;
	}
	public void setInsusercount(String insusercount) {
		this.insusercount = insusercount;
	}
	public String getInscount() {
		return inscount;
	}
	public void setInscount(String inscount) {
		this.inscount = inscount;
	}
	public String getFtinscs() {
		return ftinscs;
	}
	public void setFtinscs(String ftinscs) {
		this.ftinscs = ftinscs;
	}
	public String getFtinsrs() {
		return ftinsrs;
	}
	public void setFtinsrs(String ftinsrs) {
		this.ftinsrs = ftinsrs;
	}
	public String getAllcopies() {
		return allcopies;
	}
	public void setAllcopies(String allcopies) {
		this.allcopies = allcopies;
	}
	public String getStinsrs() {
		return stinsrs;
	}
	public void setStinsrs(String stinsrs) {
		this.stinsrs = stinsrs;
	}
	public String getZhl() {
		return zhl;
	}
	public void setZhl(String zhl) {
		this.zhl = zhl;
	}
	public String getFtl() {
		return ftl;
	}
	public void setFtl(String ftl) {
		this.ftl = ftl;
	}
	public String getRjtz() {
		return rjtz;
	}
	public void setRjtz(String rjtz) {
		this.rjtz = rjtz;
	}
	
}
