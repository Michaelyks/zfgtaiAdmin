package com.zfgt.orm;

import java.util.Date;

import com.zfgt.common.util.DESEncrypt;
import com.zfgt.common.util.QwyUtil;

/**账户表;<br>
 * 用来存储用户的银行卡信息;
 * @author qwy
 *
 * @createTime 2015-5-19下午9:38:17
 */
public class Account implements java.io.Serializable {

	// Fields

	private String id;
	private Long usersId;//用户ID
	private String status;//'是否绑定;0:已绑定;1:已解除绑定;'
	private String requestId;//绑卡请求ID;唯一
	private Date insertTime;//插入时间
	private Date updateTime;//修改时间
	private String bankName;//银行名称
	private String bankAccount;//银行卡账号
	private String bankCode;//银行编码
	private String bankAccountName;//银行卡户名
	private String idcard;//身份证号
	private String phone;//手机号码
	private String cardTop;//卡前6位
	private String cardLast;//卡后4位
	private String identityId;//绑卡时的唯一标识符
	private Integer identityType;// '0：IMEI 1：MAC地址 2：用户ID 3：用户Email 4：用户手机号 5：用户身份证号 6：用户纸质订单协议号',
	private String note;
	private String registIp;//用户绑定卡时的ip
	private String type;//第三方支付; 0:易宝支付; 1:连连支付
	private String provinceCode;//省份code
	private String cityCode;//城市code
	private String braBankName;//开户支行名称
	
	private Users users;//用户;
	private String typeChina;//类型中文
	private String jmbankAccount;//解密银行卡号
	private String jmIdcard;//解密身份证号码
	private String jmPhone;//解密预留手机号码
	private String statusChina;//状态中文
	// Constructors

	public String getStatusChina() {
		if("0".equals(status)){
			return "已绑定";
		}else if("1".equals(status)){
			return "已解除绑定";
		}
		return "其他";
	}
	
	

	public String getJmbankAccount() {
		if(!QwyUtil.isNullAndEmpty(bankAccount)){
			return DESEncrypt.jieMiBankCard(bankAccount);
		}
		return "";
	}



	public String getProvinceCode() {
		return provinceCode;
	}



	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}



	public String getCityCode() {
		return cityCode;
	}



	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}



	public String getBraBankName() {
		return braBankName;
	}



	public void setBraBankName(String braBankName) {
		this.braBankName = braBankName;
	}



	public String getJmIdcard() {
		if(!QwyUtil.isNullAndEmpty(idcard)){
			return DESEncrypt.jieMiIdCard(idcard);
		}
		return "";
	}



	public String getJmPhone() {
		if(!QwyUtil.isNullAndEmpty(phone)){
			return DESEncrypt.jieMiUsername(phone);
		}
		return "";
	}



	public String getTypeChina() {
		if("0".equals(type)){
			return "易宝支付";
		}else if("1".equals(type)){
			return "连连支付";
		}
		return "其他";
	}



	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	/** default constructor */
	public Account() {
	}

	/** minimal constructor */
	public Account(Long usersId, Date insertTime, Date updateTime) {
		this.usersId = usersId;
		this.insertTime = insertTime;
		this.updateTime = updateTime;
	}


	// Property accessors

	public String getId() {
		return this.id;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
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

	public String getBankName() {
		return this.bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAccount() {
		return this.bankAccount;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getBankAccountName() {
		return this.bankAccountName;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	public String getIdcard() {
		return this.idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCardTop() {
		return cardTop;
	}

	public void setCardTop(String cardTop) {
		this.cardTop = cardTop;
	}

	public String getCardLast() {
		return cardLast;
	}

	public void setCardLast(String cardLast) {
		this.cardLast = cardLast;
	}

	public String getIdentityId() {
		return identityId;
	}

	public void setIdentityId(String identityId) {
		this.identityId = identityId;
	}


	public Integer getIdentityType() {
		return identityType;
	}

	public void setIdentityType(Integer identityType) {
		this.identityType = identityType;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getRegistIp() {
		return registIp;
	}

	public void setRegistIp(String registIp) {
		this.registIp = registIp;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/** full constructor */
	public Account(String id, Long usersId, String status, String requestId,
			Date insertTime, Date updateTime, String bankName,
			String bankAccount, String bankAccountName, String idcard,
			String phone, String cardTop, String cardLast, String identityId,
			Integer identityType, String note, String registIp,String bankCode,String type, Users users) {
		this.id = id;
		this.usersId = usersId;
		this.status = status;
		this.requestId = requestId;
		this.insertTime = insertTime;
		this.updateTime = updateTime;
		this.bankName = bankName;
		this.bankAccount = bankAccount;
		this.bankAccountName = bankAccountName;
		this.idcard = idcard;
		this.phone = phone;
		this.cardTop = cardTop;
		this.cardLast = cardLast;
		this.identityId = identityId;
		this.identityType = identityType;
		this.note = note;
		this.registIp = registIp;
		this.bankCode = bankCode;
		this.type = type;
		this.users = users;
	}

}