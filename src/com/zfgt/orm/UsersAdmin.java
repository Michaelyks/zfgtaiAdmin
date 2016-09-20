package com.zfgt.orm;

import java.util.Date;

import com.zfgt.common.util.QwyUtil;


/**
 * UsersAdmin entity. @author MyEclipse Persistence Tools
 */

public class UsersAdmin implements java.io.Serializable {

	// Fields

	private Long id;
	private String username;
	private String password;
	private Long userType;//用户类型; -1:超级管理员 0:管理员;1普通人员
	private String userStatus;//状态;0:帐号可用; 1:帐号不可用;
	private Date insertTime;
	private Date updateTime;
	private Date lastTime;//上一次登录时间
	private String isOnline;
	private String note;
	private Long createrId;
	private Long rolesId;
	private Long deptId;

	private Roles roles;
	private UsersAdmin usersAdmin;
	private Dept dept;
	
	//无映射
	private String statusChina;//状态中文  0 ：可用  1：禁用
	private String typeChina;//用户类型; -1:超级管理员 0:管理员;1普通人员

	// Constructors

	/** default constructor */
	public UsersAdmin() {
	}

	/** minimal constructor */
	public UsersAdmin(Long userType, String userStatus, Date insertTime) {
		this.userType = userType;
		this.userStatus = userStatus;
		this.insertTime = insertTime;
	}

	/** full constructor */
	public UsersAdmin(String username, String password, Long userType,
			String userStatus, Date insertTime, Date updateTime,
			Date lastTime, String isOnline, String note,
			Long createrId,Long rolesId,Long deptId) {
		this.username = username;
		this.password = password;
		this.userType = userType;
		this.userStatus = userStatus;
		this.insertTime = insertTime;
		this.updateTime = updateTime;
		this.lastTime = lastTime;
		this.isOnline = isOnline;
		this.note = note;
		this.createrId=createrId;
		this.rolesId=rolesId;
		this.deptId=deptId;
	}

	// Property accessors
	

	public Long getId() {
		return this.id;
	}

	public String getStatusChina() {
		if(!QwyUtil.isNullAndEmpty(userStatus)){
			if("0".equals(userStatus)){
				return "可用";
			}if("1".equals(userStatus)){
				return "禁用";
			}
		}
		return statusChina;
	}

	public void setStatusChina(String statusChina) {
		this.statusChina = statusChina;
	}
    public String getTypeChina(){
    	if(!QwyUtil.isNullAndEmpty(userType)){
    		if(userType==-1){
				return "超级管理员";
			}if(userType==0){
				return "管理员";
			}if(userType==1){
				return "普通人员";
			}
    	}
	return typeChina;
}
	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getUserType() {
		return this.userType;
	}

	public void setUserType(Long userType) {
		this.userType = userType;
	}

	public String getUserStatus() {
		return this.userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
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

	public Date getLastTime() {
		return this.lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public String getIsOnline() {
		return this.isOnline;
	}

	public void setIsOnline(String isOnline) {
		this.isOnline = isOnline;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Long getCreaterId() {
		return createrId;
	}

	public void setCreaterId(Long createrId) {
		this.createrId = createrId;
	}

	public Long getRolesId() {
		return rolesId;
	}

	public void setRolesId(Long rolesId) {
		this.rolesId = rolesId;
	}

	public Roles getRoles() {
		return roles;
	}

	public void setRoles(Roles roles) {
		this.roles = roles;
	}

	public UsersAdmin getUsersAdmin() {
		if(!QwyUtil.isNullAndEmpty(usersAdmin))
			return usersAdmin;
		return this;
	}

	public void setUsersAdmin(UsersAdmin usersAdmin) {
		this.usersAdmin = usersAdmin;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

}