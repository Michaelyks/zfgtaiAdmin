package com.zfgt.orm;

import java.util.Date;

/**
 * 转让费率
 * @author liuchao
 *
 */
public class TransferCostRate implements java.io.Serializable {
	private static final long serialVersionUID = -5827704118870359202L;
	private long id;
	private long minDate;//最小天数
	private long maxDate;//最大天数
	private double rate;//折损率
	private String productId;//产品id
	private String status;//状态0:可用,1不可用
	private Long userAdminId;//管理员id
	
	private Product product;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getMinDate() {
		return minDate;
	}
	public void setMinDate(long minDate) {
		this.minDate = minDate;
	}
	public long getMaxDate() {
		return maxDate;
	}
	public void setMaxDate(long maxDate) {
		this.maxDate = maxDate;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getUserAdminId() {
		return userAdminId;
	}
	public void setUserAdminId(Long userAdminId) {
		this.userAdminId = userAdminId;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
	
}
