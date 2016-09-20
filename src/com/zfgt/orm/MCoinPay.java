package com.zfgt.orm;


import java.util.Date;

/**喵币支出记录表
 * @author 覃文勇
 * @createTime 2015-10-26上午11:49:29
 */
public class MCoinPay {
	// Fields
	
	private String id;//主键
	private String recordNumber;//流水号，唯一
	private Long usersId;//用户id
	private Long coin;//喵币数量(单位：个数)
	private String type;//1:兑换投资  2:兑换话费  3:兑换实体商品(需邮寄)
	private String mProductId;//喵商品id
	private Long copies;//购买数量(份、个)
	private String status;// 状态;0:消费成功;1:消费失败;
	private Date insertTime;//插入时间、
	private Long mUsersAddressId;//用户收货地址id
	private String note;//备注
	private String msgStatus;//短信发送状态;0:未发送;1:已发送
	
	private Users users;
	private MProduct mProduct;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRecordNumber() {
		return recordNumber;
	}

	public void setRecordNumber(String recordNumber) {
		this.recordNumber = recordNumber;
	}

	public Long getUsersId() {
		return usersId;
	}

	public void setUsersId(Long usersId) {
		this.usersId = usersId;
	}

	public Long getCoin() {
		return coin;
	}

	public void setCoin(Long coin) {
		this.coin = coin;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public String getMProductId() {
		return mProductId;
	}

	public void setMProductId(String mProductId) {
		this.mProductId = mProductId;
	}

	public Long getCopies() {
		return copies;
	}

	public void setCopies(Long copies) {
		this.copies = copies;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public Long getMUsersAddressId() {
		return mUsersAddressId;
	}

	public void setMUsersAddressId(Long mUsersAddressId) {
		this.mUsersAddressId = mUsersAddressId;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public MProduct getMProduct() {
		return mProduct;
	}

	public void setMProduct(MProduct mProduct) {
		this.mProduct = mProduct;
	}

	public String getMsgStatus() {
		return msgStatus;
	}

	public void setMsgStatus(String msgStatus) {
		this.msgStatus = msgStatus;
	}

	
}
