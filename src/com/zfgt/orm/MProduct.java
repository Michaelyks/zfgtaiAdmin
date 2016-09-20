package com.zfgt.orm;

import java.util.Date;

/**
 * 喵商品表
 * 
 * @author 覃文勇
 * @createTime 2015-10-26上午9:34:34
 */
public class MProduct {
	// Fields

	private String id;// 主键
	private String title;// 商品名称
	private String description;// 商品描述
	private Long usersAdminId;// 用户id
	private Date insertTime;// 插入时间
	private Date updateTime;// 修改时间
	private Long price;// 商品单价（喵币）
	private Long stock;// 库存量
	private Long leftStock;// 剩余库存量
	private String status;// 状态 0：可用1：不可用（删除）
	private String type;// 类型 1:理财券 2：话费券 3:流量券  4 实物
	private Long vip;// 要求用户等级>=（1 ，2 ，3，4，5，6）
	private String img;// 商品图片地址;默认第一张为缩略图
	private Double postage;// 邮费
	private String detailURL;// 喵商品详情链接
	private String explains;//兑换说明
	private String money;// 投资劵金额
	private String marketPrice; //市场价格

	
	// 索引 （无映射）
	private int index;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getUsersAdminId() {
		return usersAdminId;
	}

	public void setUsersAdminId(Long usersAdminId) {
		this.usersAdminId = usersAdminId;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Long getStock() {
		return stock;
	}

	public void setStock(Long stock) {
		this.stock = stock;
	}

	public Long getLeftStock() {
		return leftStock;
	}

	public void setLeftStock(Long leftStock) {
		this.leftStock = leftStock;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getVip() {
		return vip;
	}

	public void setVip(Long vip) {
		this.vip = vip;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Double getPostage() {
		return postage;
	}

	public void setPostage(Double postage) {
		this.postage = postage;
	}

	public String getDetailURL() {
		return detailURL;
	}

	public void setDetailURL(String detailURL) {
		this.detailURL = detailURL;
	}



	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getExplains() {
		return explains;
	}

	public void setExplains(String explains) {
		this.explains = explains;
	}

	public String getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
	}
	
	
}
