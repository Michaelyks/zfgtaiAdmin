package com.zfgt.orm;

import java.io.Serializable;
import java.util.Date;

import com.zfgt.common.util.QwyUtil;

/**
 * @author 覃文勇
 * @createTime 2015-8-14下午1:03:14
 */
public class UsersApply  implements Serializable{
	private Long id;//主键
	private Long usersId;//用户
	private Date insertTime;//创建时间
	private Date updateTime;//更新时间
	private Long usersAdminId;//操作人
	private String status;//部门状态 1：待处理 2：已处理
	private String note;//备注
	
	private Users users;
	private UsersAdmin usersAdmin;
	private String statusChina;
	
	

	// Constructors

	public String getStatusChina() {
		if(!QwyUtil.isNullAndEmpty(status)){
			if("1".equals(status)){
				return "待处理";
			}if("2".equals(status)){
				return "已处理";
			}
		}
		return statusChina;
	}
	public void setStatusChina(String statusChina) {
		this.statusChina = statusChina;
	}
	/** default constructor */
	public UsersApply(){
		
	}
	/** full constructor */
	public UsersApply(Long id,Long usersId,Date insertTime,Date updateTime,Long usersAdminId, String status,String note){
		this.id=id;
		this.usersId=usersId;
		this.insertTime=insertTime;
		this.updateTime=updateTime;
		this.usersAdminId=usersAdminId;
		this.status=status;
		this.note=note;
	}
	public Users getUsers() {
		return users;
	}
	public void setUsers(Users users) {
		this.users = users;
	}
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
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
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
