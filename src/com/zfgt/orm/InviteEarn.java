package com.zfgt.orm;


import java.util.Date;


/**邀请好友投资奖励
 * @author 曾礼强
 * @createTime 2015-11-5下午6:05:25
 */
public class InviteEarn {
	// Fields
	
	private String id;//主键 UUID
	private Long beInvitedId;//被邀请人id
	private Long inviteId;//邀请人id
	private String beInvitePhone;//被邀请人号码
	private Long copies;//购买份数;1元1份;
	private String investorsId;//投资记录id
	private Double earnMoney;//邀请投资奖励金额
	private Date insertTime;//插入时间
	private Date updateTime;//修改时间
	private Date returnTime;//发放奖励时间
	private String status;//状态   0：未发放 1：已发放
	private String type;//类型  1 邀请好友投资
	private String note;//备注
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Long getBeInvitedId() {
		return beInvitedId;
	}
	public void setBeInvitedId(Long beInvitedId) {
		this.beInvitedId = beInvitedId;
	}

	public Long getInviteId() {
		return inviteId;
	}
	public void setInviteId(Long inviteId) {
		this.inviteId = inviteId;
	}

	public String getBeInvitePhone() {
		return beInvitePhone;
	}
	public void setBeInvitePhone(String beInvitePhone) {
		this.beInvitePhone = beInvitePhone;
	}

	public Long getCopies() {
		return copies;
	}
	public void setCopies(Long copies) {
		this.copies = copies;
	}
	
	public String getInvestorsId() {
		return investorsId;
	}
	public void setInvestorsId(String investorsId) {
		this.investorsId = investorsId;
	}
	public Double getEarnMoney() {
		return earnMoney;
	}
	public void setEarnMoney(Double earnMoney) {
		this.earnMoney = earnMoney;
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
	
	public Date getReturnTime() {
		return returnTime;
	}
	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
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
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	

	
}
