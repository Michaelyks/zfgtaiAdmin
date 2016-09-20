package com.zfgt.orm;

import java.util.Date;


/**
 * 活动表实体类
 * @author oi
 *
 */
public class HdUsers implements java.io.Serializable {

	private static final long serialVersionUID = 1872926246160150216L;
	private long id;
	private String username;
	private String hdFlagId;
	private Date insertTime;
	private Long userId;
	private String note;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getHdFlagId() {
		return hdFlagId;
	}
	public void setHdFlagId(String hdFlagId) {
		this.hdFlagId = hdFlagId;
	}
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
}