package com.zfgt.orm;

import java.util.Date;

/**
 * PlatChannel entity. @author MyEclipse Persistence Tools
 */

public class PlatChannel implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer channel;
	private Date insertTime;
	private String channelName;//渠道名称
	private String date;//渠道日期

	// Constructors

	/** default constructor */
	public PlatChannel() {
	}

	/** full constructor */
	public PlatChannel(Integer channel, Date insertTime) {
		this.channel = channel;
		this.insertTime = insertTime;
	}

	// Property accessors

	public Integer getChannel() {
		return this.channel;
	}

	public void setChannel(Integer channel) {
		this.channel = channel;
	}

	public Date getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}