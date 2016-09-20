package com.zfgt.orm;

import java.util.Date;

import com.zfgt.common.util.QwyUtil;


/**
 * @author 覃文勇
 * @createTime 2015-8-10下午1:44:58
 */
public class Roles  implements java.io.Serializable {
	
	private Long id;//主键
	private String roleName;//角色名称
	private String note;//备注
	private Long status;//状态 0:禁用 1： 可用
	private Date insertTime;//创建时间
	private Long usersAdminId;//创建人id
	private UsersAdmin usersAdmin;//后台用户	
	private String statusChina;//状态中文
	
	
	
	
	
	public String getStatusChina() {
		if(!QwyUtil.isNullAndEmpty(status)){
			if(status==0L){
				return "禁用";
			}if(status==1L){
				return "可用";
			}
		}
		return statusChina;
	}
	
	public Roles(){
		
	}
    public Roles(Long id,String roleName,String note,Long status,Date insertTime,Long usersAdminId){
    	this.id=id;
    	this.roleName=roleName;
    	this.note=note;
    	this.status=status;
    	this.insertTime=insertTime;
    	this.usersAdminId=usersAdminId;
    }
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
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

	public UsersAdmin getUsersAdmin() {
		return usersAdmin;
	}
	public void setUsersAdmin(UsersAdmin usersAdmin) {
		this.usersAdmin = usersAdmin;
	}
	

}
