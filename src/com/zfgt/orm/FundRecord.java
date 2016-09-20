package com.zfgt.orm;


import java.util.Date;

import com.zfgt.common.util.QwyUtil;


/**资金流水记录
 * @author qwy
 *
 * @createTime 2015-04-27 09:35:26
 */
@SuppressWarnings("serial")
public class FundRecord implements java.io.Serializable {

	// Fields

	private String id;//
	private Double money;//操作金额
	private String status;//状态 0:收入; 1:支出
	private String type;//操作类别  cz:用户充值   tx:提现  zf:在线支付,buy:购买理财产品;back:返款；lx:收益;jl:邀请投资奖励
	private Date insertTime;//插入时间
	private Long usersId;//用户id
	private String operatedWay;//操作途径;"管理员后台充值","在线充值","在线提现","购买理财产品"
	private String note;//备注
	private Double usersCost;//用户剩余总额;
	private String txRecordId;//提现记录ID;
	
	private Users users;
	
	//以下字段不做ORM映射;
	private String jylx;
	private Double couponValue;//使用投资券面值

	// Constructors

	/** default constructor */
	public FundRecord() {
	}

	/** minimal constructor */
	public FundRecord(Double money, String status, String type,
			Date insertTime, Long usersId) {
		this.money = money;
		this.status = status;
		this.type = type;
		this.insertTime = insertTime;
		this.usersId = usersId;
	}

	/** full constructor */
	public FundRecord(Double money, String status, String type,
			Date insertTime, Long usersId, String operatedWay, String note) {
		this.money = money;
		this.status = status;
		this.type = type;
		this.insertTime = insertTime;
		this.usersId = usersId;
		this.operatedWay = operatedWay;
		this.note = note;
	}

	// Property accessors

	public String getJylx() {
		String temp = "其它";
		if("cz".equalsIgnoreCase(type)){
			temp = "充值";
		}else if("zf".equalsIgnoreCase(type)){
			temp = "支付";
		}else if("tx".equalsIgnoreCase(type)){
			temp = "提现";
		}else if("txsq".equalsIgnoreCase(type)){
			temp = "提现申请";
		}else if("buy".equalsIgnoreCase(type)){
			temp = "投资";
		}else if("back".equalsIgnoreCase(type)){
			temp = "返款";
		}else if("lx".equalsIgnoreCase(type)){
			temp = "收益";
		}
		return temp;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Double getMoney() {
		return this.money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public Long getUsersId() {
		return this.usersId;
	}

	public void setUsersId(Long usersId) {
		this.usersId = usersId;
	}

	public String getOperatedWay() {
		return this.operatedWay;
	}

	public void setOperatedWay(String operatedWay) {
		this.operatedWay = operatedWay;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Double getUsersCost() {
		return usersCost;
	}

	public void setUsersCost(Double usersCost) {
		this.usersCost = usersCost;
	}

	public String getTxRecordId() {
		return txRecordId;
	}

	public void setTxRecordId(String txRecordId) {
		this.txRecordId = txRecordId;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}
	
	public Double getCouponValue() {
		if("buy".equals(type)&&!QwyUtil.isNullAndEmpty(note)&&note.contains("其中投资券用了")){
			    String s=note.split("其中")[1];
				s=s.substring(s.indexOf("了")+1,s.indexOf("元")-1);
				couponValue=Double.valueOf(s);			
		}else{
			couponValue=0d;
		}
		return couponValue;
	}


}