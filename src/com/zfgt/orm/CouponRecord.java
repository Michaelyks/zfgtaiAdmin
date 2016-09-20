package com.zfgt.orm;

import java.util.Date;

public class CouponRecord {

	// Fields

	private String id;//
	private Double money;// 操作金额
	private String couponId;// 投资券ID
	private String productId;// 产品ID
	private Long usersId;// 用户id
	private String investorsId;// 投资列表的id
	private Date insertTime;// 插入时间
	private String status;// 默认为0
	private String note;// 备注

	// Constructors

	/** default constructor */
	public CouponRecord() {
	}

	/** minimal constructor */
	public CouponRecord(Double money, Long usersId, Date insertTime) {
		this.money = money;
		this.usersId = usersId;
		this.insertTime = insertTime;
	}

	/** full constructor */
	public CouponRecord(Double money, String productId, Long usersId, String investorsId, Date insertTime, String status, String note) {
		this.money = money;
		this.productId = productId;
		this.usersId = usersId;
		this.investorsId = investorsId;
		this.insertTime = insertTime;
		this.status = status;
		this.note = note;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Double getMoney() {
		return this.money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

	public String getProductId() {
		return this.productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Long getUsersId() {
		return this.usersId;
	}

	public void setUsersId(Long usersId) {
		this.usersId = usersId;
	}

	public String getInvestorsId() {
		return this.investorsId;
	}

	public void setInvestorsId(String investorsId) {
		this.investorsId = investorsId;
	}

	public Date getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}