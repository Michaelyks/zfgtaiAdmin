package com.zfgt.orm;

import java.util.Date;

import com.zfgt.common.util.QwyUtil;

/**
 * Versions entity. @author MyEclipse Persistence Tools
 */

public class PushMessage implements java.io.Serializable {

	// Fields

	private Long id;
	private String title;//标题
	private String content;//内容
	private Date insertTime;//插入
	private String status;//状态: 0:待发送; 1: 已发送; 2:发送失败; 3: 发送取消
	private String type;//0:所有人; 1:个人;2:群组
	private String target;//推送目标; baiyimao:所有人;
	private Date updateTime;
	private String statusName;
	// Constructors

	/** default constructor */
	public PushMessage() {
	}

	/** minimal constructor */
	public PushMessage(Date insertTime) {
		this.insertTime = insertTime;
	}
	public String  getStatusName() {
		if(!QwyUtil.isNullAndEmpty(status)){
			switch (status) {
			case "0":
				return "待发送";
			case "1":
				return "已发送";
			case "2":
				return "发送失败";
			case "3":
				return "发送取消";
			default:
				return "其他";
			}
		}
		return "";
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getTarget() {
		target = QwyUtil.isNullAndEmpty(target)?"baiyimao":target;
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

}