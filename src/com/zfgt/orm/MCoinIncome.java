package com.zfgt.orm;


import java.util.Date;

/**喵币收入记录表
 * @author 覃文勇
 * @createTime 2015-10-26下午2:27:01
 */
public class MCoinIncome {
	// Fields
	
	private String id;//主键
	private String recordNumber;//流水号，唯一
	private Long usersId;//用户id
	private Long coin;//喵币数量(单位：个数)
	private String type;//1:签到获得 2:邀请好友(注册) 3:被邀请(注册) 4:被邀请(第一笔投资) 5:投资获得 6:分享活动获得 7:手动赠送
	                    //如果该用户被邀请注册的,那么首次购买产品,则会赠送2次喵币
	private String status;// 状态;默认为0
	private Date insertTime;//插入时间
	private String note;//备注	
	private Users users;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRecordNumber() {
		return recordNumber;
	}

	public void setRecordNumber(String recordNumber) {
		this.recordNumber = recordNumber;
	}

	public Long getUsersId() {
		return usersId;
	}

	public void setUsersId(Long usersId) {
		this.usersId = usersId;
	}

	public Long getCoin() {
		return coin;
	}

	public void setCoin(Long coin) {
		this.coin = coin;
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

	
}
