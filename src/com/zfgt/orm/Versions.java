package com.zfgt.orm;

import java.util.Date;

/**
 * Versions entity. @author MyEclipse Persistence Tools
 */

public class Versions implements java.io.Serializable {

	// Fields

	private String id;
	private String content;
	private String versions;
	private Date insertTime;
	private String status;
	private String type;
	private String clientType;

	// Constructors

	/** default constructor */
	public Versions() {
	}

	/** minimal constructor */
	public Versions(Date insertTime) {
		this.insertTime = insertTime;
	}

	/** full constructor */
	public Versions(String content, String versions, Date insertTime,
			String status, String type) {
		this.content = content;
		this.versions = versions;
		this.insertTime = insertTime;
		this.status = status;
		this.type = type;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getVersions() {
		return this.versions;
	}

	public void setVersions(String versions) {
		this.versions = versions;
	}

	public Date getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getClientType() {
		if(type.equals("0")){
			return "IOS";
		}else if(type.equals("1")){
			return "Android";
		}
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

}