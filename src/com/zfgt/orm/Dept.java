package com.zfgt.orm;

import java.util.Date;

/**
 * @author 覃文勇
 * @createTime 2015-8-11下午4:48:56
 */
public class Dept implements java.io.Serializable{
	private Long id;//主键
	private String deptName;//部门名称
	private Date insertTime;//创建时间
	private Date updateTime;//更新时间
	private String status;//部门状态 0：可用  1：禁用
	private String note;//备注
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
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
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	
	
}
