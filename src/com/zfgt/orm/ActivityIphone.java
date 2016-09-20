package com.zfgt.orm;

import java.util.Date;


/**移动设备渠道统计（IOS）
 * @author 覃文勇
 * @createTime 2016-2-19下午4:49:38
 */
public class ActivityIphone implements java.io.Serializable {

	// Fields

	private String id;//UUID
	private Date insertTime;//插入时间	
	private String type;//标识类型 1:mac 2：idfa
	private String imei;//手机唯一标识码
	private String status;//状态 0：未激活 1：已激活
	private Date updateTime;//激活时间
    private String os ;//用户设备 iOS 系统版本
	private String appId;//应用唯一标识(appStore)
    private String callback;//激活回调地址
	private String note;//备注

	// Constructors

	/** default constructor */
	public ActivityIphone() {
	}

	/** minimal constructor */
	public ActivityIphone(Date insertTime,Date updateTime) {
		this.insertTime = insertTime;
		this.updateTime=updateTime;
	}
	

	/** full constructor */
	public ActivityIphone(String imei, String appId, Date insertTime,String status,String type,String note
			,String os,String callback) {
		this.imei = imei;
		this.appId = appId;
		this.insertTime = insertTime;
		this.status=status;
		this.type=type;
		this.os=os;
		this.callback=callback;
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


	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public Date getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}
	
	

}