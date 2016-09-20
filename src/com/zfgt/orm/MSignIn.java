package com.zfgt.orm;


import java.util.Date;

/**喵币签到表
 * @author 覃文勇
 * @createTime 2015-10-26上午11:37:28
 */
public class MSignIn {
	// Fields
	
	private Long id;//主键
	private Long usersId;//用户id
	private String type;//签到来源（1:PC端  2:WAP端   3:移动端）
	private String status;//状态   0：未签到     1：已签到
	private Date insertTime;//插入时间
	private String note;//备注
	private Long lianxuDay;//当月连续签到天数
	
	private Users users;
	
	
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public Users getUsers() {
		return users;
	}
	public void setUsers(Users users) {
		this.users = users;
	}
	public Long getLianxuDay() {
		return lianxuDay;
	}
	public void setLianxuDay(Long lianxuDay) {
		this.lianxuDay = lianxuDay;
	}
	
	
}
