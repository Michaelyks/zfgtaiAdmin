package com.zfgt.orm;

/**
 * SystemConfig entity. @author MyEclipse Persistence Tools
 */

public class SystemConfig implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String fileUrl;
	private String httpUrl;
	private String fileName;
	private String companyTel;
	private String companyName;
	private Integer payInterestDay;
	private String tjy;//推荐语
	private String hdby;//活动标语
	private String hdlj;//活动连接
	private Double earnings;//零钱包收益率
	private String coinPurseExplanation;//零钱包说明
	private String smsTip;//短信标签
	private String zrgzUrlMobile;//转让规则
	private String bankSafeUrlMobile;//银行安全地址
	private String smsQm;//短信签名
	private String isCleanMcoin;//是否开启:0:未开启清空瞄币; 1:已开启清空瞄币
	private String msgCost;//话费短信
	// Constructors
	/** full constructor */
	public SystemConfig(String fileUrl, String httpUrl, String fileName,
			String companyTel, String companyName, Integer payInterestDay,String smsTip) {
		this.fileUrl = fileUrl;
		this.httpUrl = httpUrl;
		this.fileName = fileName;
		this.companyTel = companyTel;
		this.companyName = companyName;
		this.payInterestDay = payInterestDay;
		this.smsTip=smsTip;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public String getMsgCost() {
		return msgCost;
	}

	public void setMsgCost(String msgCost) {
		this.msgCost = msgCost;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileUrl() {
		return this.fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getHttpUrl() {
		return this.httpUrl;
	}

	public void setHttpUrl(String httpUrl) {
		this.httpUrl = httpUrl;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getCompanyTel() {
		return this.companyTel;
	}

	public void setCompanyTel(String companyTel) {
		this.companyTel = companyTel;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Integer getPayInterestDay() {
		return this.payInterestDay;
	}

	public void setPayInterestDay(Integer payInterestDay) {
		this.payInterestDay = payInterestDay;
	}

	public String getTjy() {
		return tjy;
	}

	public void setTjy(String tjy) {
		this.tjy = tjy;
	}
	public String getHdby() {
		return hdby;
	}

	public void setHdby(String hdby) {
		this.hdby = hdby;
	}

	public String getHdlj() {
		return hdlj;
	}

	public void setHdlj(String hdlj) {
		this.hdlj = hdlj;
	}

	public Double getEarnings() {
		return earnings;
	}

	public void setEarnings(Double earnings) {
		this.earnings = earnings;
	}

	public String getCoinPurseExplanation() {
		return coinPurseExplanation;
	}

	public void setCoinPurseExplanation(String coinPurseExplanation) {
		this.coinPurseExplanation = coinPurseExplanation;
	}

	public String getSmsTip() {
		return smsTip;
	}

	public void setSmsTip(String smsTip) {
		this.smsTip = smsTip;
	}

	public String getZrgzUrlMobile() {
		return zrgzUrlMobile;
	}

	public void setZrgzUrlMobile(String zrgzUrlMobile) {
		this.zrgzUrlMobile = zrgzUrlMobile;
	}

	public String getBankSafeUrlMobile() {
		return bankSafeUrlMobile;
	}

	public void setBankSafeUrlMobile(String bankSafeUrlMobile) {
		this.bankSafeUrlMobile = bankSafeUrlMobile;
	}

	public String getSmsQm() {
		return smsQm;
	}

	public void setSmsQm(String smsQm) {
		this.smsQm = smsQm;
	}


	public String getIsCleanMcoin() {
		return isCleanMcoin;
	}

	public void setIsCleanMcoin(String isCleanMcoin) {
		this.isCleanMcoin = isCleanMcoin;
	}

	/** default constructor */
	public SystemConfig() {
	}
	

}