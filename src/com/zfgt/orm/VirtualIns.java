package com.zfgt.orm;

import java.util.Date;




/**
 * 虚拟记录表
 * @author 曾礼强
 * 2015年7月29日下午5:05:34
 */
public class VirtualIns implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;//逻辑id,UUID
	private Integer userCount;//虚拟投资人数	
	private Double inMoney;//虚拟投资金额
	private String productId;//产品ID
	private Date insertTime;//插入时间
	private Long usersAdminId;//管理员ID
	private String status;//状态 0处理中 1成功 2失败
	private Product product;
	private UsersAdmin admin;
	private String statusChina;
	
	
	public String getStatusChina() {
		if("0".equals(status)){
			return "处理中";
		}
		if("1".equals(status)){
			return "成功";
		}
		if("2".equals(status)){
			return "失败";
		}
		return "";
	}
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

	public Integer getUserCount() {
		return userCount;
	}
	public void setUserCount(Integer userCount) {
		this.userCount = userCount;
	}
	public Double getInMoney() {
		return inMoney;
	}
	public void setInMoney(Double inMoney) {
		this.inMoney = inMoney;
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
	public Long getUsersAdminId() {
		return usersAdminId;
	}
	public void setUsersAdminId(Long usersAdminId) {
		this.usersAdminId = usersAdminId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public UsersAdmin getAdmin() {
		return admin;
	}
	public void setAdmin(UsersAdmin admin) {
		this.admin = admin;
	}


}