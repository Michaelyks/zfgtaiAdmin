package com.zfgt.orm;

import java.util.Date;
//下发红包流水
public class RedPacketsRecord {
	private Long id;//
	private Long usersId;// 用户id
	private Long redId;//拆红包的id
	private Double inMoney;// 操作金额
	private Date insertTime;// 插入时间
	private Long invitesId;//推荐人Id
	private String type;// 1：投资所发金额；0；拆红包所发金额
	private Date updateTime;//更新时间

	private Double inviteInMoney;//推荐人发金额
	private String status;// 状态;0:成功;1:失败;
	private String note;//备注
	
	public Long getRedId() {
		return redId;
	}
	public void setRedId(Long redId) {
		this.redId = redId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getInMoney() {
		return inMoney;
	}
	public void setInMoney(Double inMoney) {
		this.inMoney = inMoney;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getUsersId() {
		return usersId;
	}
	public void setUsersId(Long usersId) {
		this.usersId = usersId;
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
	
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}