package com.zfgt.orm;

import java.util.Date;

public class InterestCouponRecord {

	/**
	 * 逻辑id
	 */
	private String id;

	/**
	 * 加息券id
	 */
	private String interestCouponId;

	/**
	 * 产品id
	 */
	private String productId;

	/**
	 * 用户id
	 */
	private Long usersId;

	/**
	 * 投资id
	 */
	private String investorsId;

	/**
	 * 插入时间
	 */
	private Date insertTime;

	/**
	 * 使用备注
	 */
	private String useNote;

	/**
	 * 最近更新时间
	 */
	private Date updateTime;

	/**
	 * 加息券利率
	 */
	private Double interestRate;

	/**
	 * 应收利息(单位 分)
	 */
	private Long interestReceivableCent;

	/**
	 * 已收利息
	 */
	private Long interestReceivedCent;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInterestCouponId() {
		return interestCouponId;
	}

	public void setInterestCouponId(String interestCouponId) {
		this.interestCouponId = interestCouponId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Long getUsersId() {
		return usersId;
	}

	public void setUsersId(Long usersId) {
		this.usersId = usersId;
	}

	public String getInvestorsId() {
		return investorsId;
	}

	public void setInvestorsId(String investorsId) {
		this.investorsId = investorsId;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public String getUseNote() {
		return useNote;
	}

	public void setUseNote(String useNote) {
		this.useNote = useNote;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}

	public Long getInterestReceivableCent() {
		return interestReceivableCent;
	}

	public void setInterestReceivableCent(Long interestReceivableCent) {
		this.interestReceivableCent = interestReceivableCent;
	}

	public Long getInterestReceivedCent() {
		return interestReceivedCent;
	}

	public void setInterestReceivedCent(Long interestReceivedCent) {
		this.interestReceivedCent = interestReceivedCent;
	}

}