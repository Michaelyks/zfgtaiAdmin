package com.zfgt.orm;

import java.util.Date;

import com.zfgt.common.util.DESEncrypt;
import com.zfgt.common.util.QwyUtil;

/**
 * 用户实体表
 * 
 * @author qwy
 * 
 * @createTime 2015-04-14 21:35:12
 */
@SuppressWarnings("serial")
public class Users implements java.io.Serializable {

	// Fields
	private Long id;//逻辑id,自增+1,起始值为1000
	private String username;//用户名
	private String password;//密码
	private Long userInfoId;//用户信息ID
	private Long userType;//用户类型(大于等于0的,属于用户,否则为管理员) -1: 超级管理员(拥有所有权限) 0:普通用户
	private String userStatus;//用户状态
	private Date insertTime;//注册时间
	private Date updateTime;//更新时间
	private Date updatePasswordTime;//修改密码时间
	private Long inviteId;//邀请人id,另一个用户id
	private Date lastTime;//上一次登录时间
	private String isOnline;//是否在线
	private String phone;//手机帐号;
	private UsersInfo usersInfo;//用户信息表;
	private String buyFreshmanProduct;//是否购买过新手产品;0:没有购买过; 1:购买过
	private String payPassword;//支付密码
	private String registType;//注册类型,0:手机注册; 1:邮箱注册;2:其它; 默认为0
	private String registPlatform;//'0:web端注册; 1:Android移动端; 2:IOS移动端; 3:微信注册;'
	private String registChannel;//注册渠道;
	private String province;//所属省份
	private String city;//所属城市
	private String cardType;//号码类型
	private String usernameJM;//用户名解密
	
	// Constructors

	public String getUsernameJM() {
		if(!QwyUtil.isNullAndEmpty(username)){
			return DESEncrypt.jieMiUsername(username);
		}
		return "usernameJM";
	}

	public UsersInfo getUsersInfo() {
		return usersInfo;
	}

	public void setUsersInfo(UsersInfo usersInfo) {
		this.usersInfo = usersInfo;
	}

	/** default constructor */
	public Users() {
	}

	/** minimal constructor */
	public Users(Date insertTime) {
		this.insertTime = insertTime;
	}

	/** full constructor */
	public Users(String username, String password, Long userInfoId,
			Long userType, String userStatus, Date insertTime, Date updateTime,
			Date updatePasswordTime, Long inviteId, Date lastTime,
			String isOnline) {
		this.username = username;
		this.password = password;
		this.userInfoId = userInfoId;
		this.userType = userType;
		this.userStatus = userStatus;
		this.insertTime = insertTime;
		this.updateTime = updateTime;
		this.updatePasswordTime = updatePasswordTime;
		this.inviteId = inviteId;
		this.lastTime = lastTime;
		this.isOnline = isOnline;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getUserInfoId() {
		return this.userInfoId;
	}

	public void setUserInfoId(Long userInfoId) {
		this.userInfoId = userInfoId;
	}

	public Long getUserType() {
		return this.userType;
	}

	public void setUserType(Long userType) {
		this.userType = userType;
	}

	public String getUserStatus() {
		return this.userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
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

	public Date getUpdatePasswordTime() {
		return this.updatePasswordTime;
	}

	public void setUpdatePasswordTime(Date updatePasswordTime) {
		this.updatePasswordTime = updatePasswordTime;
	}

	public Long getInviteId() {
		return this.inviteId;
	}

	public void setInviteId(Long inviteId) {
		this.inviteId = inviteId;
	}

	public Date getLastTime() {
		return this.lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public String getIsOnline() {
		return this.isOnline;
	}

	public void setIsOnline(String isOnline) {
		this.isOnline = isOnline;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getBuyFreshmanProduct() {
		return buyFreshmanProduct;
	}

	public void setBuyFreshmanProduct(String buyFreshmanProduct) {
		this.buyFreshmanProduct = buyFreshmanProduct;
	}

	public String getPayPassword() {
		return payPassword;
	}

	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}

	public String getRegistType() {
		return registType;
	}

	public void setRegistType(String registType) {
		this.registType = registType;
	}

	public String getRegistPlatform() {
		return registPlatform;
	}

	public void setRegistPlatform(String registPlatform) {
		this.registPlatform = registPlatform;
	}

	public String getRegistChannel() {
		return registChannel;
	}

	public void setRegistChannel(String registChannel) {
		this.registChannel = registChannel;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	

}