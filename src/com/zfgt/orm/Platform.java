package com.zfgt.orm;


import java.util.Date;

/**
 * Platform entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class Platform implements java.io.Serializable {

	// Fields

	private Long id;//逻辑id; 1
	private Double totalMoney;//发布产品总额
	private Double totalProfit;//总收益
	private Double collectMoney;//已募集总金额
	private Double totalCoupon;//赠送的投资券总金额
	private Double useCoupon;//有效使用投资券的总金额
	private Integer registerCount;//注册人数
	private Date insertTime;//插入时间
	private Date updateTime;//更新时间
	private Double freshmanCoupon;//新手仅有一次投资机会的投资券
	private Double virtualMoney;//虚拟投资总额;
	private Long totalCoin;//平台发放喵币;
	private Long payCoin;//平台支出总喵币;
	private Long leftCoin;//平台剩余总喵币;

	// Constructors

	/** default constructor */
	public Platform() {
	}

	/** minimal constructor */
	public Platform(Date insertTime) {
		this.insertTime = insertTime;
	}

	/** full constructor */
	public Platform(Double totalMoney, Double totalProfit, Double collectMoney,
			Double totalCoupon, Double useCoupon, Integer registerCount,
			Date insertTime, Date updateTime) {
		this.totalMoney = totalMoney;
		this.totalProfit = totalProfit;
		this.collectMoney = collectMoney;
		this.totalCoupon = totalCoupon;
		this.useCoupon = useCoupon;
		this.registerCount = registerCount;
		this.insertTime = insertTime;
		this.updateTime = updateTime;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getTotalMoney() {
		return this.totalMoney;
	}

	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}

	public Double getTotalProfit() {
		return this.totalProfit;
	}

	public void setTotalProfit(Double totalProfit) {
		this.totalProfit = totalProfit;
	}

	public Double getCollectMoney() {
		return this.collectMoney;
	}

	public void setCollectMoney(Double collectMoney) {
		this.collectMoney = collectMoney;
	}

	public Double getTotalCoupon() {
		return this.totalCoupon;
	}

	public void setTotalCoupon(Double totalCoupon) {
		this.totalCoupon = totalCoupon;
	}

	public Double getUseCoupon() {
		return this.useCoupon;
	}

	public void setUseCoupon(Double useCoupon) {
		this.useCoupon = useCoupon;
	}

	public Integer getRegisterCount() {
		return this.registerCount;
	}

	public void setRegisterCount(Integer registerCount) {
		this.registerCount = registerCount;
	}

	public Date getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Double getFreshmanCoupon() {
		return freshmanCoupon;
	}

	public void setFreshmanCoupon(Double freshmanCoupon) {
		this.freshmanCoupon = freshmanCoupon;
	}

	public Double getVirtualMoney() {
		return virtualMoney;
	}

	public void setVirtualMoney(Double virtualMoney) {
		this.virtualMoney = virtualMoney;
	}

	public Long getTotalCoin() {
		return totalCoin;
	}

	public void setTotalCoin(Long totalCoin) {
		this.totalCoin = totalCoin;
	}

	public Long getPayCoin() {
		return payCoin;
	}

	public void setPayCoin(Long payCoin) {
		this.payCoin = payCoin;
	}

	public Long getLeftCoin() {
		return leftCoin;
	}

	public void setLeftCoin(Long leftCoin) {
		this.leftCoin = leftCoin;
	}



}