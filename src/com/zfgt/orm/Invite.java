package com.zfgt.orm;


import java.util.Date;


/**邀请记录列表
 * @author 覃文勇
 * @createTime 2015-11-5下午6:05:25
 */
public class Invite {
	// Fields
	
	private String id;//主键
	private Long beInvitedId;//被邀请人id
	private Long inviteId;//邀请人id
	private Date insertTime;//插入时间
	private Date updateTime;//修改时间
	private String status;//状态   0：未投资 1：已投资
	private String type;//类型  1 邀请好友
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
