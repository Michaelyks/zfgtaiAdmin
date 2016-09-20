package com.zfgt.orm;

import java.util.Date;
//下发用户拆红的金额
public class SendRedPackets {
	private Long id;
	private Long usersId;//用户id
	private Long redId;//拆红包的id
	private Double inMoney;//发红包的金额
	private Date insertTime;//发红包时间
	private Long invitesId;//推荐人Id
	private Double inviteInMoney;//推荐人发红包的金额
	private Date updateTime;
	private String status;// 状态;0:成功;1:失败;
	private String note;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUsersId() {
		return usersId;
	}
	public void setUsersId(Long usersId) {
		this.usersId = usersId;
	}
	public Long getRedId() {
		return redId;
	}
	public void setRedId(Long redId) {
		this.redId = redId;
	}
	public Double getInMoney() {
		return inMoney;
	}
	public void setInMoney(Double inMoney) {
		this.inMoney = inMoney;
	}
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	public Long getInvitesId() {
		return invitesId;
	}
	public void setInvitesId(Long invitesId) {
		this.invitesId = invitesId;
	}
	public Double getInviteInMoney() {
		return inviteInMoney;
	}
	public void setInviteInMoney(Double inviteInMoney) {
		this.inviteInMoney = inviteInMoney;
	}
	public Date getUpdateTime() {
		return updateTime;
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
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}

}
