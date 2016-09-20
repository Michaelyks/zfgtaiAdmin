package com.zfgt.orm;

import com.zfgt.common.util.QwyUtil;

/**
 * 年龄分布表
 * 无映射实体类
 * @author 覃文勇
 * @createTime 2015-7-23上午9:37:44
 */
public class Age {
	private String ageCeng;//年龄层
	
	private String sexChina;//性别
	
	private Float rate;//比例
	
	private String rsCount;//人数
	
	private String csCount;//投资次数
	
	private String jeCount;//投资总额
	
	private String bankName;//银行
	
	private String cgCount;//成功投资次数
	
	private String sbCount;//失败投资次数

	public String getRsCount() {
		return rsCount;
	}

	public void setRsCount(String rsCount) {
		this.rsCount = rsCount;
	}

	public String getCsCount() {
		return csCount;
	}

	public void setCsCount(String csCount) {
		this.csCount = csCount;
	}

	public String getJeCount() {
		if(QwyUtil.isNullAndEmpty(jeCount)){
			jeCount="0";
		}
		return jeCount;
	}

	public void setJeCount(String jeCount) {
		this.jeCount = jeCount;
	}

	public String getAgeCeng() {
		return ageCeng;
	}

	public void setAgeCeng(String ageCeng) {
		this.ageCeng = ageCeng;
	}

	public String getSexChina() {
		return sexChina;
	}

	public void setSexChina(String sexChina) {
		this.sexChina = sexChina;
	}

	public Float getRate() {
		return rate;
	}

	public void setRate(Float rate) {
		this.rate = rate;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getCgCount() {
		return cgCount;
	}

	public void setCgCount(String cgCount) {
		this.cgCount = cgCount;
	}

	public String getSbCount() {
		return sbCount;
	}

	public void setSbCount(String sbCount) {
		this.sbCount = sbCount;
	}


}
