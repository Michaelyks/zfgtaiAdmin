package com.zfgt.orm;

import java.util.Date;

import com.zfgt.common.util.QwyUtil;

/**
 * StartPage entity. @author MyEclipse Persistence Tools
 */

public class StartPage implements java.io.Serializable {

	// Fields

	private Long id;
	private String title;//标题
	private String imgUrl;//图片地址
	private Date insertTime;
	private String type;//类型0:IOS; 1:Android,2:所有
	private String status;//状态0使用中 1待使用
	private String typeChina;//类型0:IOS; 1:Android,2:所有
	public String getTypeChina() {
		if("0".equals(type)){
			return "IOS";
		}else if("1".equals(type)){
			return "Android";
		}else{
			return "所有";
		}
		
	}
	
	// Constructors

	/** default constructor */
	public StartPage() {
	}

	/** minimal constructor */
	public StartPage(Date insertTime) {
		this.insertTime = insertTime;
	}

	/** full constructor */
	public StartPage(String title, String imgUrl, Date insertTime,
			String type, String status) {
		this.title = title;
		this.imgUrl = imgUrl;
		this.insertTime = insertTime;
		this.type = type;
		this.status = status;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImgUrl() {
		return this.imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Date getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}