package com.zfgt.orm;

import java.util.Date;




/**领投人
 * @author qwy
 *
 * @createTime 2015-4-15下午3:28:09
 */
@SuppressWarnings("serial")
public class LeadInvestor implements java.io.Serializable {

	// Fields

	private String id;//逻辑id,UUID
	private Long usersId;//用户id
	private Long totalCount;//发起总次数
	private Double totalShouyi;//总收益金额
	private Long positiveCount;//正收益次数
	private Long negativeCount;//负收益次数
	private String realName;//真实姓名
	private String status;//状态 0:待审核 1:审核通过 2:审核失败
	private Date insertTime;//插入时间
	private Date updateTime;//更新时间
	private Double earningsCostSum;//各次运营产品收益和,在此基础上累加
	private String note;//备注
	private Users users;//用户
	private String idCard;//身份证号码;
	private String phone;//电话号码;
	// Constructors

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	/** default constructor */
	public LeadInvestor() {
	}

	/** minimal constructor */
	public LeadInvestor(Long usersId, Date insertTime) {
		this.usersId = usersId;
		this.insertTime = insertTime;
	}

	/** full constructor */
	public LeadInvestor(Long usersId, Long totalCount, Double totalShouyi,
			Long positiveCount, Long negativeCount, String realName,
			String status, Date insertTime, Date updateTime,
			Double earningsCostSum, String note) {
		this.usersId = usersId;
		this.totalCount = totalCount;
		this.totalShouyi = totalShouyi;
		this.positiveCount = positiveCount;
		this.negativeCount = negativeCount;
		this.realName = realName;
		this.status = status;
		this.insertTime = insertTime;
		this.updateTime = updateTime;
		this.earningsCostSum = earningsCostSum;
		this.note = note;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getUsersId() {
		return this.usersId;
	}

	public void setUsersId(Long usersId) {
		this.usersId = usersId;
	}

	public Long getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	public Double getTotalShouyi() {
		return this.totalShouyi;
	}

	public void setTotalShouyi(Double totalShouyi) {
		this.totalShouyi = totalShouyi;
	}

	public Long getPositiveCount() {
		return this.positiveCount;
	}

	public void setPositiveCount(Long positiveCount) {
		this.positiveCount = positiveCount;
	}

	public Long getNegativeCount() {
		return this.negativeCount;
	}

	public void setNegativeCount(Long negativeCount) {
		this.negativeCount = negativeCount;
	}

	public String getRealName() {
		return this.realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Double getEarningsCostSum() {
		return this.earningsCostSum;
	}

	public void setEarningsCostSum(Double earningsCostSum) {
		this.earningsCostSum = earningsCostSum;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}