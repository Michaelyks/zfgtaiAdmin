/**
 * 
 */
package com.zfgt.orm;

/**
 * 资金速动明细
 * @author 曾礼强
 * 2015年7月20日上午10:42:19
 */
public class Zjsumx {
	private String date;//日期
	private String productAllMoney;	//发布产品总额
	private String payIns;//到期还本付息总额
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getProductAllMoney() {
		return productAllMoney;
	}
	public void setProductAllMoney(String productAllMoney) {
		this.productAllMoney = productAllMoney;
	}
	public String getPayIns() {
		return payIns;
	}
	public void setPayIns(String payIns) {
		this.payIns = payIns;
	}
	
}
