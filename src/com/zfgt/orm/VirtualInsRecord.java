package com.zfgt.orm;

import java.util.Date;




/**
 * 虚拟投资表
 * @author 曾礼强
 * 2015年7月29日下午5:05:34
 */
public class VirtualInsRecord implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;//逻辑id,UUID
	private String username;//用户名
	private Double payInMoney;//投资金额
	private String productId;//产品ID
	private Date insertTime;//插入时间
	private Product product;
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Double getPayInMoney() {
		return payInMoney;
	}
	public void setPayInMoney(Double payInMoney) {
		this.payInMoney = payInMoney;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}


}