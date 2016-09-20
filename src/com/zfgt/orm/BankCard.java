package com.zfgt.orm;

import com.zfgt.common.util.QwyUtil;

/**用来存放界面上显示的银行卡信息;<br>
 * 没有对应的映射文件;此表不需要存进数据库;<br>
 * 是对银行卡信息的一个集中处理;
 * @author qwy
 *
 * @createTime 2015-05-22 11:58:58
 */
public class BankCard {
	
	private String accountId;
	private String cardLast;
	private String relName;
	private String idCard;
	private String bankName;
	private String type;//第三方支付; 0:易宝支付; 1:连连支付
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getCardLast() {
		return cardLast;
	}
	public void setCardLast(String cardLast) {
		this.cardLast = cardLast;
	}
	public String getRelName() {
		return relName;
	}
	public void setRelName(String relName) {
		this.relName = relName;
	}
	public String getIdCard() {
		idCard = QwyUtil.replaceStringToX(idCard);
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	

}
