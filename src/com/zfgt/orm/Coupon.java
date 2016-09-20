package com.zfgt.orm;


import java.util.Date;

/** 投资劵 实体
 * @author qwy
 * 
 * @createdTime 2015-04-24 11:16:01
 */
public class Coupon {
	private String id;// 不解释
	private Long usersId;// 投资劵所属用户
	private Double money;// 投资劵的金额
	private Double initMoney;//投资券初始金额
	private Date insertTime; // 获得时间
	private Date updateTime;//更新时间;
	private Date useTime;//使用时间;
	private Date overTime;// 过期时间
	private String type;// 类别 如:0:常规投资券; 1:新手投资券
	private String status;// 状态 0未使用,1未用完,2已用完,3已过期
	private String isAlways;//是否永久有效; 0:否; 1:永久有效;
	private String productId; // 产品id
	private String investorsId;//投资列表id
	private String note;//备注
	private Long fromId;//发红包者的id;一般为管理员账户的id; -1为线程自动发放id;
	private Users users;//用户;
	private String statusChina;//状态;
	private String isAlwaysChina;//是否永久有效; 0:否; 1:永久有效;
	
	public String getIsAlwaysChina() {
		if ("0".equals(status)) {
			return "否";
		}
		if ("1".equals(status)) {
			return "是";
		}
		return "其他";
	}
	public String getStatusChina() {
		if ("0".equals(status)) {
			return "未使用";
		}
		if ("2".equals(status)) {
			return "已用完";
		}
		if ("3".equals(status)) {
			return "已过期";
		}
		return "其他";
	}
	public Long getFromId() {
		return fromId;
	}
	public void setFromId(Long fromId) {
		this.fromId = fromId;
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
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public Double getInitMoney() {
		return initMoney;
	}
	public void setInitMoney(Double initMoney) {
		this.initMoney = initMoney;
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
	public String getIsAlways() {
		return isAlways;
	}
	public void setIsAlways(String isAlways) {
		this.isAlways = isAlways;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getInvestorsId() {
		return investorsId;
	}
	public void setInvestorsId(String investorsId) {
		this.investorsId = investorsId;
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
