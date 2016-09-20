package com.zfgt.orm;

import java.util.Date;

/**
 * InsSucSmsRecord entity. @author MyEclipse Persistence Tools
 */

public class InsSucSmsRecord implements java.io.Serializable {

	// Fields

	private Long id;
	private String smsContent;
	private String mobile;
	private String status;
	private String sid;
	private Date insertTime;
	private Date updateTime;
	private String code;
	private String note;
	private Long usersAdminId;

	// Constructors

	/** default constructor */
	public InsSucSmsRecord() {
	}

	/** minimal constructor */
	public InsSucSmsRecord(String smsContent, String status,
			Date insertTime) {
		this.smsContent = smsContent;
		this.status = status;
		this.insertTime = insertTime;
	}

	/** full constructor */
	public InsSucSmsRecord(String smsContent, String mobile, String status,
			String sid, Date insertTime, Date updateTime,
			String code, String note, Long usersAdminId) {
		this.smsContent = smsContent;
		this.mobile = mobile;
		this.status = status;
		this.sid = sid;
		this.insertTime = insertTime;
		this.updateTime = updateTime;
		this.code = code;
		this.note = note;
		this.usersAdminId = usersAdminId;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSmsContent() {
		return this.smsContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSid() {
		return this.sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public Date getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Long getUsersAdminId() {
		return this.usersAdminId;
	}

	public void setUsersAdminId(Long usersAdminId) {
		this.usersAdminId = usersAdminId;
	}

}