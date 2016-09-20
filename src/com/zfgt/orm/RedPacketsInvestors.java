package com.zfgt.orm;

import java.util.Date;
//用户通过活动投资所得金额
public class RedPacketsInvestors {
	private Long id;//主键
	private Long redId;//拆红包的id
	private Long usersId;//用户id
	private Double inMoney;//投资奖励金额
	private Date insertTime;//插入时间
	private String investorsId;//投资列表id
	private Long invitesId;//推荐人Id
	public Long getInvitesId() {
		return invitesId;
	}
	public void setInvitesId(Long invitesId) {
		this.invitesId = invitesId;
	}
	private Double inviteInMoney;//推荐人发投资的金额
	private Date updateTime;
	private String status;// 状态;0:成功;1:失败;
	private String note;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getRedId() {
		return redId;
	}
	public void setRedId(Long redId) {
		this.redId = redId;
	}
	public Long getUsersId() {
		return usersId;
	}
	public void setUsersId(Long usersId) {
		this.usersId = usersId;
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
	public String getInvestorsId() {
		return investorsId;
	}
	public void setInvestorsId(String investorsId) {
		this.investorsId = investorsId;
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



