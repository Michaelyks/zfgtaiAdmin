package com.zfgt.orm;

import java.util.Date;

/**
 * 支付利息详情表;<br>
 * 该表在点击"立即投资"时,显示相关的利息记录;
 * 
 * @author qwy
 *
 * @createTime 2015-4-23上午2:25:37
 */
public class InterestDetails implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields
	private String id;// 逻辑id,UUID
	private String productId;// 产品ID
	private Long usersId;// 用户id
	private String status;// '状态 0未支付,1已冻结,2已支付,3已删除',
	private Double inMoney;// 投资本金
	private Long investDay;// 投资天数
	private Date insertTime;// 插入时间
	private Date startTime;// 起息时间
	private Date finishTime;// 项目结束时间
	private Date returnTime;// 回报时间
	private Double payMoney;// 支付本金
	private Long payDay;// 支付天数
	private Double payInterest;// 支付利息
	private Double alreadyPay;// '已经支付金额',
	private Long alreadyPayDay;// '已经投资天数',
	private String note;// 备注
	private Product product;// 产品
	private Users users;// 用户
	private String investorsId;// 投资列表id
	private Investors investors;// 投资列表;
	private Long orders;// 排序编号
	private Long copies;// 购买份数;1元1份;
	private Date updateTime;// 更新时间;
	private Double payInterestCentCoupon;// 支付利息，加息券部分

	private String isSendMessage;// 发送短信 0:未发送 1：已发送
	// Constructors

	public String getInvestorsId() {
		return investorsId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getAlreadyPay() {
		return alreadyPay;
	}

	public void setAlreadyPay(Double alreadyPay) {
		this.alreadyPay = alreadyPay;
	}

	public Long getAlreadyPayDay() {
		return alreadyPayDay;
	}

	public void setAlreadyPayDay(Long alreadyPayDay) {
		this.alreadyPayDay = alreadyPayDay;
	}

	public void setInvestorsId(String investorsId) {
		this.investorsId = investorsId;
	}

	public Investors getInvestors() {
		return investors;
	}

	public void setInvestors(Investors investors) {
		this.investors = investors;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	/** default constructor */
	public InterestDetails() {
	}

	/** minimal constructor */
	public InterestDetails(Long usersId, Double inMoney, Long investDay, Date insertTime, Date startTime, Date finishTime, Date returnTime, Double payMoney, Long payDay, Double payInterest) {
		this.usersId = usersId;
		this.inMoney = inMoney;
		this.investDay = investDay;
		this.insertTime = insertTime;
		this.startTime = startTime;
		this.finishTime = finishTime;
		this.returnTime = returnTime;
		this.payMoney = payMoney;
		this.payDay = payDay;
		this.payInterest = payInterest;
	}

	/** full constructor */
	public InterestDetails(String productId, Long usersId, Double inMoney, Long investDay, Date insertTime, Date startTime, Date finishTime, Date returnTime, Double payMoney, Long payDay,
			Double payInterest, String note) {
		this.productId = productId;
		this.usersId = usersId;
		this.inMoney = inMoney;
		this.investDay = investDay;
		this.insertTime = insertTime;
		this.startTime = startTime;
		this.finishTime = finishTime;
		this.returnTime = returnTime;
		this.payMoney = payMoney;
		this.payDay = payDay;
		this.payInterest = payInterest;
		this.note = note;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Double getInMoney() {
		return this.inMoney;
	}

	public void setInMoney(Double inMoney) {
		this.inMoney = inMoney;
	}

	public Long getInvestDay() {
		return this.investDay;
	}

	public void setInvestDay(Long investDay) {
		this.investDay = investDay;
	}

	public Date getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getFinishTime() {
		return this.finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public Date getReturnTime() {
		return this.returnTime;
	}

	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}

	public Double getPayMoney() {
		return this.payMoney;
	}

	public void setPayMoney(Double payMoney) {
		this.payMoney = payMoney;
	}

	public Long getPayDay() {
		return this.payDay;
	}

	public void setPayDay(Long payDay) {
		this.payDay = payDay;
	}

	public Double getPayInterest() {
		return this.payInterest;
	}

	public void setPayInterest(Double payInterest) {
		this.payInterest = payInterest;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public InterestDetails(String id, String productId, Long usersId, String status, Double inMoney, Long investDay, Date insertTime, Date startTime, Date finishTime, Date returnTime, Double payMoney,
			Long payDay, Double payInterest, Double alreadyPay, Long alreadyPayDay, String note, Product product, Users users, String investorsId, Investors investors, String isSendMessage) {
		this.id = id;
		this.productId = productId;
		this.usersId = usersId;
		this.status = status;
		this.inMoney = inMoney;
		this.investDay = investDay;
		this.insertTime = insertTime;
		this.startTime = startTime;
		this.finishTime = finishTime;
		this.returnTime = returnTime;
		this.payMoney = payMoney;
		this.payDay = payDay;
		this.payInterest = payInterest;
		this.alreadyPay = alreadyPay;
		this.alreadyPayDay = alreadyPayDay;
		this.note = note;
		this.product = product;
		this.users = users;
		this.investorsId = investorsId;
		this.investors = investors;
		this.isSendMessage = isSendMessage;
	}

	public Long getOrders() {
		return orders;
	}

	public void setOrders(Long orders) {
		this.orders = orders;
	}

	public Long getCopies() {
		return copies;
	}

	public void setCopies(Long copies) {
		this.copies = copies;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getIsSendMessage() {
		return isSendMessage;
	}

	public void setIsSendMessage(String isSendMessage) {
		this.isSendMessage = isSendMessage;
	}

	public Double getPayInterestCentCoupon() {
		return payInterestCentCoupon;
	}

	public void setPayInterestCentCoupon(Double payInterestCentCoupon) {
		this.payInterestCentCoupon = payInterestCentCoupon;
	}
}