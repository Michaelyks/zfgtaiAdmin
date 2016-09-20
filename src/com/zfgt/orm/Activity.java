package com.zfgt.orm;

import java.util.Date;


/**移动设备激活情况;
 * @author qwy
 *
 * @createTime 2015-06-09 11:20:35
 */
public class Activity implements java.io.Serializable {

	// Fields

	private String id;//UUID
	private String imei;//手机唯一标识码
	private String channel;//安装渠道
	private Date insertTime;//激活时间

	// Constructors

	/** default constructor */
	public Activity() {
	}

	/** minimal constructor */
	public Activity(Date insertTime) {
		this.insertTime = insertTime;
	}

	/** full constructor */
	public Activity(String imei, String channel, Date insertTime) {
		this.imei = imei;
		this.channel = channel;
		this.insertTime = insertTime;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImei() {
		return this.imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getChannel() {
		return this.channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Date getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

}