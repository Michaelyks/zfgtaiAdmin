package com.zfgt.orm;


import java.util.Date;


/**分享活动表
 * @author 覃文勇
 * @createTime 2015-10-26上午11:27:16
 */
public class MShare {
	// Fields
	
	private Long id;//主键
	private Long usersId;//用户id
	private String flag;//活动id
	private String type;//分享平台 
	private Date insertTime;//插入时间
	private String note;//备注	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUsersId() {
		return usersId;
	}
	public void setUsersId(Long usersId) {
		this.usersId = usersId;
	}

	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	
}
