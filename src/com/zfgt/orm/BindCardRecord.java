package com.zfgt.orm;

import java.util.Date;

/**
 * BindCardRecord entity. @author MyEclipse Persistence Tools
 */

public class BindCardRecord implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private Long identityid;//用户唯一标识
	private String identitytype;//标识类型
	private String bindid;//绑卡ID
	private String bankcode;//银行编号
	private String card_name;//银行名称
	private String card_last;//尾号
	private String card_top;//头6位
	private String phone;//手机号码
	private Date insertTime;//插入时间
	private String status;//状态是否解绑或者绑定成功:0失败 , 1成功
	private String type;//操作类型:0绑定银行卡 , 1解绑银行卡
	private String note;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Long getIdentityid() {
		return identityid;
	}
	public void setIdentityid(Long identityid) {
		this.identityid = identityid;
	}
	public String getIdentitytype() {
		return identitytype;
	}
	public void setIdentitytype(String identitytype) {
		this.identitytype = identitytype;
	}
	public String getBindid() {
		return bindid;
	}
	public void setBindid(String bindid) {
		this.bindid = bindid;
	}
	
	public String getBankcode() {
		return bankcode;
	}
	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}
	public String getCard_name() {
		return card_name;
	}
	public void setCard_name(String card_name) {
		this.card_name = card_name;
	}
	public String getCard_last() {
		return card_last;
	}
	public void setCard_last(String card_last) {
		this.card_last = card_last;
	}
	public String getCard_top() {
		return card_top;
	}
	public void setCard_top(String card_top) {
		this.card_top = card_top;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
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
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}


}