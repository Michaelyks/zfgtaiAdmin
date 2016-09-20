package com.zfgt.orm;


public class ProductTable {
	private String index;//序号
	private String title;//产品名称
	private String lcqx;//理财期限,以天为单位,项目结束时间-项目开始时间
	private String annualEarnings;//年化收益
	private String allCopies;//总份数
	private String hasCopies;//跟投份数(跟投份数=总份数-剩余份数)
	private String leftCopies;//剩余份数(当前一元等于1份)
	private String insertTime;//插入时间
	private String productPassTime;//项目审核通过时间
	private String finishTime;//项目预计结束时间;普通项目的话:为项目预计结束时间; 新手专属项目:预计进入已售罄的时间; 
	//临时字段,没有ORM映射
	private String wcjd;//完成进度;
	private String cplx;//产品类型(中文)
	private String cpzt;//产品状态(中文)
	private String tzqx;//动态更新投资天数;
	private String qtje;//动态的起头金额;
	private String yflx;//预发利息
	private String sflx;//实发利息
	private String sjHashCopies;//实际募集份数
	private String xnCopies;//虚拟投资
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLcqx() {
		return lcqx;
	}
	public void setLcqx(String lcqx) {
		this.lcqx = lcqx;
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
	public String getLeftCopies() {
		return leftCopies;
	}
	public void setLeftCopies(String leftCopies) {
		this.leftCopies = leftCopies;
	}
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
	public String getProductPassTime() {
		return productPassTime;
	}
	public void setProductPassTime(String productPassTime) {
		this.productPassTime = productPassTime;
	}
	public String getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}
	public String getWcjd() {
		return wcjd;
	}
	public void setWcjd(String wcjd) {
		this.wcjd = wcjd;
	}
	public String getCplx() {
		return cplx;
	}
	public void setCplx(String cplx) {
		this.cplx = cplx;
	}
	public String getCpzt() {
		return cpzt;
	}
	public void setCpzt(String cpzt) {
		this.cpzt = cpzt;
	}
	public String getTzqx() {
		return tzqx;
	}
	public void setTzqx(String tzqx) {
		this.tzqx = tzqx;
	}
	public String getQtje() {
		return qtje;
	}
	public void setQtje(String qtje) {
		this.qtje = qtje;
	}
	public String getHasCopies() {
		return hasCopies;
	}
	public void setHasCopies(String hasCopies) {
		this.hasCopies = hasCopies;
	}
	public String getYflx() {
		return yflx;
	}
	public void setYflx(String yflx) {
		this.yflx = yflx;
	}
	public String getSflx() {
		return sflx;
	}
	public void setSflx(String sflx) {
		this.sflx = sflx;
	}
	public String getSjHashCopies() {
		return sjHashCopies;
	}
	public void setSjHashCopies(String sjHashCopies) {
		this.sjHashCopies = sjHashCopies;
	}
	public String getXnCopies() {
		return xnCopies;
	}
	public void setXnCopies(String xnCopies) {
		this.xnCopies = xnCopies;
	}
	
}
