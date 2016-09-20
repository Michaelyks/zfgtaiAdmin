package com.zfgt.orm;

import java.io.Serializable;


/**
 * activity统计ORM
 * 无映射表
 * @author 曾礼强
 *
 */
public class ActivityStat implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String channel;
	private String channelCount;
	private String regCount;//注册人数
	private String bindCount;//绑卡人数
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getChannelCount() {
		return channelCount;
	}
	public void setChannelCount(String channelCount) {
		this.channelCount = channelCount;
	}
	public String getRegCount() {
		return regCount;
	}
	public void setRegCount(String regCount) {
		this.regCount = regCount;
	}
	public String getBindCount() {
		return bindCount;
	}
	public void setBindCount(String bindCount) {
		this.bindCount = bindCount;
	}
	
	
}
