package com.zfgt.orm;

import java.util.Date;

/**
 * @author 覃文勇
 * @createTime 2015-8-11下午4:10:15
 */
public class RolesRight implements java.io.Serializable {
	
 private Long id;//主键
 
 private Long createId;//角色id
 
 private Long modulId;//模块id
 private String status;//状态;0:可用; 1:不可用;
 
 private Date insertTime;//插入时间
 private Date updateTime;//更新时间
 
 private Long usersAdminId;//创建人
 
 private String note;//备注
 
 private Roles roles;
 
 private Modul modul;
 
 private UsersAdmin usersAdmin;//被分配权限的用户;

 public RolesRight(){
	 
 }

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public Long getCreateId() {
	return createId;
}

public void setCreateId(Long createId) {
	this.createId = createId;
}

public Long getModulId() {
	return modulId;
}

public void setModulId(Long modulId) {
	this.modulId = modulId;
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

public Date getUpdateTime() {
	return updateTime;
}

public void setUpdateTime(Date updateTime) {
	this.updateTime = updateTime;
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

public Roles getRoles() {
	return roles;
}

public void setRoles(Roles roles) {
	this.roles = roles;
}

public Modul getModul() {
	return modul;
}

public void setModul(Modul modul) {
	this.modul = modul;
}

public UsersAdmin getUsersAdmin() {
	return usersAdmin;
}

public void setUsersAdmin(UsersAdmin usersAdmin) {
	this.usersAdmin = usersAdmin;
}
 
}
