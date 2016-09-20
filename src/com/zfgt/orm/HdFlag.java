package com.zfgt.orm;

import java.util.Date;

import com.zfgt.common.util.QwyUtil;

/**
 * 活动标识符
 * @author 覃文勇
 * @createTime 2015-8-24下午5:58:05
 */
public class HdFlag {

	private Long id;//主键
	
	private String status;//状态 0：活动进行中；1 ：活动已结束
	
	private String flag;//活动标识符
	
	private Date insertTime;//插入时间
	
	private Date endTime;//结束时间
	
	private  Date updateTime;//更新时间
	
	private String note;//备注
	
	private String statusChina;// 状态中文（无实体映射）
	
	public String getStatusChina() {
		if(!QwyUtil.isNullAndEmpty(status)){
			if("0".equals(status)){
				return "活动进行中";
			}if("1".equals(status)){
				return "活动已结束";
			}
		}
		return statusChina;
	}

	public void setStatusChina(String statusChina) {
		this.statusChina = statusChina;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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
	
	
}
