package com.zfgt.orm;

import java.util.Date;


/**用户信息表
 * @author qwy
 *
 * @createTime 2015-04-14 21:43:04
 */
@SuppressWarnings("serial")
public class UsersInfo implements java.io.Serializable {

	// Fields

	private Long id;//逻辑id,自增+1,起始值为1000
	private Double totalMoney;//用户总金额
	private Double leftMoney;//用户当前可用余额
	private Double freezeMoney;//存储冻结资金
	private Double inviteEarnMoney;//邀请好友获得的总奖励
	private String age;//年龄
	private String sex;//性别
	private String phone;//手机号码
	private String isVerifyPhone;//是否验证手机号码;0:未验证; 1:已验证
	private String email;//邮箱
	private String isVerifyEmail;//是否验证邮箱;0:未验证; 1:已验证
	/*private String bankName;//银行名称
	private String bankAccount;//银行卡账号
	private String bankAccountName;//银行卡户名
*/	private String realName;//用户真实姓名
	private String idcard;//身份证号
	private String isVerifyIdcard;//是否验证身份证号码;0:未验证; 1:已验证
	private Date birthday;//生日
	private Date insertTime;//插入时间
	private Date updateTime;//更新时间
	private String dataProgress;//记录个人资料进度
	private String qq;//QQ号码
	private String userPhotos;//用户头像路径
	private String nickName;//昵称
	private String levels;//信用等级
	private String note;//备注
	private String selfIntroduction;//自我介绍
	private String signature;//个性签名
	private Long investCount;//投资次数
	private Long usersId;//用户Id;
	private Users users;//用户表;
	private Double totalProfit;//投资总收益
	private String isBindBank;//是否绑定银行卡;0:未绑定; 1:已绑定
	private Long totalPoint;//总喵币
	private String level;//vip等级
	private String pointStatus;//积分状态	
	
	
	
	public Double getTotalProfit() {
		return totalProfit;
	}
	public void setTotalProfit(Double totalProfit) {
		this.totalProfit = totalProfit;
	}
	public Long getUsersId() {
		return usersId;
	}
	public void setUsersId(Long usersId) {
		this.usersId = usersId;
	}
	public Users getUsers() {
		return users;
	}
	public void setUsers(Users users) {
		this.users = users;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getTotalMoney() {
		return totalMoney==null?0:totalMoney;
	}
	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}
	public Double getLeftMoney() {
		
		return leftMoney==null?0:leftMoney;
	}
	public void setLeftMoney(Double leftMoney) {
		this.leftMoney = leftMoney;
	}
	public Double getFreezeMoney() {
		return freezeMoney==null?0:freezeMoney;
	}
	public void setFreezeMoney(Double freezeMoney) {
		this.freezeMoney = freezeMoney;
	}
	
	public Double getInviteEarnMoney() {
		return inviteEarnMoney;
	}
	public void setInviteEarnMoney(Double inviteEarnMoney) {
		this.inviteEarnMoney = inviteEarnMoney;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getIsVerifyPhone() {
		return isVerifyPhone;
	}
	public void setIsVerifyPhone(String isVerifyPhone) {
		this.isVerifyPhone = isVerifyPhone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIsVerifyEmail() {
		return isVerifyEmail;
	}
	public void setIsVerifyEmail(String isVerifyEmail) {
		this.isVerifyEmail = isVerifyEmail;
	}
	/*public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public String getBankAccountName() {
		return bankAccountName;
	}
	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}*/
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getIsVerifyIdcard() {
		return isVerifyIdcard;
	}
	public void setIsVerifyIdcard(String isVerifyIdcard) {
		this.isVerifyIdcard = isVerifyIdcard;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
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
	public String getDataProgress() {
		return dataProgress;
	}
	public void setDataProgress(String dataProgress) {
		this.dataProgress = dataProgress;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getUserPhotos() {
		return userPhotos;
	}
	public void setUserPhotos(String userPhotos) {
		this.userPhotos = userPhotos;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getLevels() {
		return levels;
	}
	public void setLevels(String levels) {
		this.levels = levels;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getSelfIntroduction() {
		return selfIntroduction;
	}
	public void setSelfIntroduction(String selfIntroduction) {
		this.selfIntroduction = selfIntroduction;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public Long getInvestCount() {
		return investCount;
	}
	public void setInvestCount(Long investCount) {
		this.investCount = investCount;
	}
	public String getIsBindBank() {
		return isBindBank;
	}
	public void setIsBindBank(String isBindBank) {
		this.isBindBank = isBindBank;
	}
	public Long getTotalPoint() {
		return totalPoint;
	}
	public void setTotalPoint(Long totalPoint) {
		this.totalPoint = totalPoint;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getPointStatus() {
		return pointStatus;
	}
	public void setPointStatus(String pointStatus) {
		this.pointStatus = pointStatus;
	}


}