package com.zfgt.orm;


import java.util.Date;

/**喵币流水表
 * @author 覃文勇
 * @createTime 2015-10-29下午4:59:58
 */
public class MCoinRecord {
	// Fields
	
	private String id;//主键
	private String recordId;//流水号（支出或收入的流水号id）
	private Long usersId;//用户id
	private Long coin;//喵币数量(单位：个数)
	private String coinType;//喵币使用类型  1：用户支出 2：用户收入
	private String type;//类型
	private String status;// 状态;0:消费成功;1:消费失败;
	private Date insertTime;//插入时间
	private String note;//备注
	private Long totalCoin ;//用户当前总喵币

	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRecordId() {
		return recordId;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
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
	public String getCoinType() {
		return coinType;
	}
	public void setCoinType(String coinType) {
		this.coinType = coinType;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Long getTotalCoin() {
		return totalCoin;
	}
	public void setTotalCoin(Long totalCoin) {
		this.totalCoin = totalCoin;
	}
    
	
}
