package com.zfgt.orm;

import java.util.Date;

import com.zfgt.common.util.QwyUtil;

/**
 * Notice entity. @author MyEclipse Persistence Tools
 */

public class Notice implements java.io.Serializable {

	// Fields

	private String id;//
	private String title;//标题
	private String content;//文章内容
	private Date insertTime;//插入时间
	private Long usersAdminId;//管理员id;
	private String type;//类型;-1:隐藏的公告(不显示在界面,做seo优化);0:平台公告; 1:获奖公告;2:媒体公告; 默认为0;3:系统消息;4:活动中心; 
	private String titleUrl;//标题缩略图
	private String description;//描述
	private String isTop;//是否置顶; 0:不置顶; 1:置顶;
	private String typeZw;//类型;-1:隐藏的公告(不显示在界面,做seo优化);0:平台公告; 1:获奖公告;2:媒体公告;3:系统消息;4:活动中心; 默认为0;
	private String noticeId;//公告ID
	private String hdUrl;//活动Url
	//公告（中文）
	public String getTypeZw(){
		if(!QwyUtil.isNullAndEmpty(type)){
			switch (type) {
			case "-2":
				return "下架";
			case "-1":
				return "隐藏的公告";
			case "0":
				return "平台公告";
			case "1":
				return "获奖公告";
			case "2":
				return "媒体公告";
			case "3":
				return "系统消息";
			case "4":
				return "活动中心";
			default:
				break;
			}
		}
		return "";
		
	}
	// Constructors

	/** default constructor */
	public Notice() {
	}

	/** minimal constructor */
	public Notice(Date insertTime, Long usersAdminId) {
		this.insertTime = insertTime;
		this.usersAdminId = usersAdminId;
	}

	/** full constructor */
	public Notice(String title, String content, Date insertTime,
			Long usersAdminId, String type) {
		this.title = title;
		this.content = content;
		this.insertTime = insertTime;
		this.usersAdminId = usersAdminId;
		this.type = type;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public Long getUsersAdminId() {
		return this.usersAdminId;
	}

	public void setUsersAdminId(Long usersAdminId) {
		this.usersAdminId = usersAdminId;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitleUrl() {
		return titleUrl;
	}

	public void setTitleUrl(String titleUrl) {
		this.titleUrl = titleUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIsTop() {
		return isTop;
	}

	public void setIsTop(String isTop) {
		this.isTop = isTop;
	}

	public String getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}

	public String getHdUrl() {
		return hdUrl;
	}

	public void setHdUrl(String hdUrl) {
		this.hdUrl = hdUrl;
	}
	
	

}