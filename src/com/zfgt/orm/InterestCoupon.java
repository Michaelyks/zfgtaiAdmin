package com.zfgt.orm;

import java.util.Date;

/**
 * 加息券
 * 
 * @author bym
 *
 */
public class InterestCoupon {
	private String id;// 逻辑id
	private Long usersId;// 加息劵所属用户
	private Integer couponStatus;// 状态 0未使用,1未用完,2已用完,3已过期
	private Integer couponType;// 类别 如:0:加息券
	private Double interestRate;// 增加利率
	private Date insertTime;// 插入时间
	private Date updateTime;// 最近更新时间
	private Date useTime;// 使用时间
	private Date overTime;// 过期时间
	private Integer isAlways;// 是否永久有效; 0:否; 1:永久有效;
	private String note;// 备注
	private Long fromId;// 发红包者的id;一般为管理员账户的id; -1为线程自动发放id;
	private Users users;// 用户;

	private String couponStatusChina;

	public InterestCoupon() {
		super();
	}

	public InterestCoupon(Long usersId, Integer couponType, Double interestRate, Date overTime, Integer isAlways, String note, Long fromId) {
		this.usersId = usersId;
		this.couponType = couponType;
		this.couponStatus = 0;
		this.interestRate = interestRate;
		this.insertTime = new Date();
		this.overTime = overTime;
		this.isAlways = isAlways;
		this.note = note;
		this.fromId = fromId;
	}

	public String getIsAlwaysChina() {
		if ("0".equals(isAlways)) {
			return "否";
		}
		if ("1".equals(isAlways)) {
			return "是";
		}
		return "其他";
	}

	public String getCouponStatusChina() {
		if (couponStatus == 0) {
			return "未使用";
		}
		if (couponStatus == 2) {
			return "已用完";
		}
		if (couponStatus == 3) {
			return "已过期";
		}
		return "其他";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getUsersId() {
		return usersId;
	}

	public void setUsersId(Long usersId) {
		this.usersId = usersId;
	}

	public Integer getCouponStatus() {
		return couponStatus;
	}

	public void setCouponStatus(Integer couponStatus) {
		this.couponStatus = couponStatus;
	}

	public Integer getCouponType() {
		return couponType;
	}

	public void setCouponType(Integer couponType) {
		this.couponType = couponType;
	}

	public Double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getUseTime() {
		return useTime;
	}

	public void setUseTime(Date useTime) {
		this.useTime = useTime;
	}

	public Date getOverTime() {
		return overTime;
	}

	public void setOverTime(Date overTime) {
		this.overTime = overTime;
	}

	public Integer getIsAlways() {
		return isAlways;
	}

	public void setIsAlways(Integer isAlways) {
		this.isAlways = isAlways;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Long getFromId() {
		return fromId;
	}

	public void setFromId(Long fromId) {
		this.fromId = fromId;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

}
