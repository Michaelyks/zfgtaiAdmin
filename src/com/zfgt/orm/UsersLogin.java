package com.zfgt.orm;

import java.io.Serializable;

import com.zfgt.common.util.QwyUtil;

/**用作用户登录成功存放的信息;<br>
 * 没有ORM映射;
 * @author qwy
 *
 * @createTime 2015-05-22 18:16:31
 */
public class UsersLogin implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long usersId;
	private String username;
	private String username2;
	private double leftMoney;
	private long userType; 
	private String isBindBank;//是否绑定了银行卡;0:未绑定;1已绑定;
	
	public long getUsersId() {
		return usersId;
	}
	public void setUsersId(long usersId) {
		this.usersId = usersId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public double getLeftMoney() {
		return leftMoney;
	}
	public void setLeftMoney(double leftMoney) {
		this.leftMoney = leftMoney;
	}
	public UsersLogin(long usersId, String username,long userType, double leftMoney) {
		this.usersId = usersId;
		this.username = username;
		this.leftMoney = leftMoney;
		this.userType = userType;
	}
	
	@SuppressWarnings("unused")
	private UsersLogin(){
		
	}
	public long getUserType() {
		return userType;
	}
	public void setUserType(long userType) {
		this.userType = userType;
	}
	public String getIsBindBank() {
		return isBindBank;
	}
	public void setIsBindBank(String isBindBank) {
		this.isBindBank = isBindBank;
	}
	public String getUsername2() {
		return QwyUtil.replaceStringToX(username);
	}
	
}
