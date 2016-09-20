package com.zfgt.orm;

import java.util.Date;

/**
 * @author 覃文勇
 * @createTime 2015-8-12下午3:43:04
 */
public class AdminLog implements java.io.Serializable{
	private Long id;
	
	private String type;//类型 1：后台用户表 2：部门表 3：模块表   4：用户申请表
	
	private Long typeId;//被操作的数据id
	
	private Date insertTime;//插入时间
	
	private Long usersAdminId;//操作人id
	
	private String note;//备注
	
	private UsersAdmin usersAdmin;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public Long getUsersAdminId() {
		return usersAdminId;
	}

	public void setUsersAdminId(Long usersAdminId) {
		this.usersAdminId = usersAdminId;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public UsersAdmin getUsersAdmin() {
		return usersAdmin;
	}

	public void setUsersAdmin(UsersAdmin usersAdmin) {
		this.usersAdmin = usersAdmin;
	}
	
	

}
