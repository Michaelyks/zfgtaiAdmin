package com.zfgt.orm;

import java.util.Calendar;
import java.util.Date;

import com.zfgt.common.util.DESEncrypt;
import com.zfgt.common.util.QwyUtil;

/**
 * 投资列表
 * 
 * @author qwy
 *
 * @createTime 2015-04-15 15:26:42
 */
@SuppressWarnings("serial")
public class Investors implements java.io.Serializable {

	// Fields

	private String id;// 逻辑id,UUID
	private Long usersId;// 购买人id
	private String productId;// 投资人对应的理财项目id
	private String investorType;// 类别 预留,默认为0
	private String investorStatus;// 状态;0:待付款 ,1:已付款, 2:结算中, 3:已结算,4:已删除
	private Date insertTime;// 插入时间
	private Date payTime;// 支付时间
	private Date updateTime;// 更新时间
	private Date startTime;// 起息时间
	private Date clearTime;// 结算时间
	private Double inMoney;// 投入金额;(自己投入的金额)
	private Double clearMoney;// 结算后的金额
	private Double coupon;// 投资券金额(分)
	private Double couponAnnualRate = 0d;// 加息券年化利率
	private Double couponExpectEarnings = 0d;// 加息券预期收益
	private Long earnings;// 预收益率
	private Double allEarnings;// 总收益率
	private Double allShouyi;// 总收益金额
	private Long copies;// 持有份数
	private Product product;// 产品
	private Users users;// 用户
	private Double annualEarnings;// 年化收益,包含coupon_annual_rate
	private Double expectEarnings;// 预期收益总金额
	private String payInterestWay;// 付息方式; 0: 按月付息到期还本 1:到期还本付息
	private String calcInterestWay;// 计息方式; 0: T+0 1:T+1
	private Date finishTime;// 项目预计到期时间;

	private String contractId;// 合同id
	private Contract contract;

	private String isDraw;// 是否抽奖 0：未抽奖 1：已抽奖

	private Long investCount;// 投资次数（用于基金产品转让）

	private String isTransfer;// 是否转让中(0:非转让,1:转让中)
	private String isHairMcoin;// 是否发放瞄币 0：未发放 1：已发放

	// 以下字段没有ORM映射;
	private Date ydqsj;//
	private String tzzt;// 投资状态;
	private String username;// 投资人姓名;
	private Integer tzts;// 投资天数
	private Date ffsysj;// 发放收益时间;
	private Date sysj;// 收益时间;

	public Date getSysj() {
		return sysj;
	}

	public void setSysj(Date sysj) {
		this.sysj = sysj;
	}

	public Date getFfsysj() {
		if (QwyUtil.isNullAndEmpty(finishTime)) {
			return null;
		} else {
			return QwyUtil.addDaysFromOldDate(finishTime, 1).getTime();
		}
	}

	public Integer getTzts() {
		if (!QwyUtil.isNullAndEmpty(startTime) && !QwyUtil.isNullAndEmpty(finishTime) && !QwyUtil.isNullAndEmpty(calcInterestWay)) {
			// T+0的计息方式;
			return QwyUtil.getDifferDays(startTime, finishTime);
		} else {
			return 0;
		}
	}

	public String getUsername() {
		if (!QwyUtil.isNullAndEmpty(username))
			return username;
		if (QwyUtil.isNullAndEmpty(users))
			return "******";
		String un = users.getUsername();
		String username = QwyUtil.isNullAndEmpty(un) ? users.getPhone() : un;
		String newUn = DESEncrypt.jieMiUsername(username);
		return QwyUtil.replaceStringToX(newUn);
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getIsTransfer() {
		return isTransfer;
	}

	public void setIsTransfer(String isTransfer) {
		this.isTransfer = isTransfer;
	}

	public Double getExpectEarnings() {
		return expectEarnings;
	}

	public String getPayInterestWay() {
		return payInterestWay;
	}

	public void setPayInterestWay(String payInterestWay) {
		this.payInterestWay = payInterestWay;
	}

	public String getCalcInterestWay() {
		return calcInterestWay;
	}

	public void setCalcInterestWay(String calcInterestWay) {
		this.calcInterestWay = calcInterestWay;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getClearTime() {
		return clearTime;
	}

	public void setClearTime(Date clearTime) {
		this.clearTime = clearTime;
	}

	public void setExpectEarnings(Double expectEarnings) {
		this.expectEarnings = expectEarnings;
	}

	public Double getAnnualEarnings() {
		return annualEarnings;
	}

	public void setAnnualEarnings(Double annualEarnings) {
		this.annualEarnings = annualEarnings;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	/** default constructor */
	public Investors() {
	}

	/** minimal constructor */
	public Investors(Long usersId, String productId, Date insertTime, Double inMoney, Long copies) {
		this.usersId = usersId;
		this.productId = productId;
		this.insertTime = insertTime;
		this.inMoney = inMoney;
		this.copies = copies;
	}

	/** full constructor */
	public Investors(Long usersId, String productId, String investorType, String investorStatus, Date insertTime, Date updateTime, Double inMoney, Double clearMoney, Double coupon, Long earnings,
			Double allEarnings, Double allShouyi, Long copies, String contractId, String isDraw) {
		this.usersId = usersId;
		this.productId = productId;
		this.investorType = investorType;
		this.investorStatus = investorStatus;
		this.insertTime = insertTime;
		this.updateTime = updateTime;
		this.inMoney = inMoney;
		this.clearMoney = clearMoney;
		this.coupon = coupon;
		this.earnings = earnings;
		this.allEarnings = allEarnings;
		this.allShouyi = allShouyi;
		this.copies = copies;
		this.contractId = contractId;
		this.isDraw = isDraw;
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

	public String getProductId() {
		return this.productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getInvestorType() {
		return this.investorType;
	}

	public void setInvestorType(String investorType) {
		this.investorType = investorType;
	}

	public String getInvestorStatus() {
		return this.investorStatus;
	}

	public void setInvestorStatus(String investorStatus) {
		this.investorStatus = investorStatus;
	}

	public Date getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Double getInMoney() {
		return this.inMoney;
	}

	public void setInMoney(Double inMoney) {
		this.inMoney = inMoney;
	}

	public Double getClearMoney() {
		return this.clearMoney;
	}

	public void setClearMoney(Double clearMoney) {
		this.clearMoney = clearMoney;
	}

	public Double getCoupon() {
		return this.coupon;
	}

	public void setCoupon(Double coupon) {
		this.coupon = coupon;
	}

	public Long getEarnings() {
		return this.earnings;
	}

	public void setEarnings(Long earnings) {
		this.earnings = earnings;
	}

	public Double getAllEarnings() {
		return this.allEarnings;
	}

	public void setAllEarnings(Double allEarnings) {
		this.allEarnings = allEarnings;
	}

	public Double getAllShouyi() {
		return this.allShouyi;
	}

	public void setAllShouyi(Double allShouyi) {
		this.allShouyi = allShouyi;
	}

	public Long getCopies() {
		return this.copies;
	}

	public void setCopies(Long copies) {
		this.copies = copies;
	}

	public Date getYdqsj() {
		if (!QwyUtil.isNullAndEmpty(product)) {
			if ("1".equals(product.getProductType())) {
				// 新手产品,的预到期时间;
				Calendar finishTimeFreshman = QwyUtil.addDaysFromOldDate(startTime, 4);
				ydqsj = finishTimeFreshman.getTime();
			} else {
				ydqsj = product.getFinishTime();
			}
		}
		return ydqsj;
	}

	public String getTzzt() {

		if ("0".equals(investorStatus)) {
			// 新手产品,的预到期时间;
			tzzt = "待付款";
		} else if ("1".equals(investorStatus)) {
			// 新手产品,的预到期时间;
			tzzt = "已付款";
		} else if ("2".equals(investorStatus)) {
			// 新手产品,的预到期时间;
			tzzt = "结算中";
		} else if ("3".equals(investorStatus)) {
			// 新手产品,的预到期时间;
			tzzt = "已结算";
		} else if ("4".equals(investorStatus)) {
			// 新手产品,的预到期时间;
			tzzt = "已删除";
		} else {
			// 新手产品,的预到期时间;
			tzzt = "其它";
		}

		return tzzt;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getIsDraw() {
		return isDraw;
	}

	public void setIsDraw(String isDraw) {
		this.isDraw = isDraw;
	}

	public Long getInvestCount() {
		return investCount;
	}

	public void setInvestCount(Long investCount) {
		this.investCount = investCount;
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public String getIsHairMcoin() {
		return isHairMcoin;
	}

	public void setIsHairMcoin(String isHairMcoin) {
		this.isHairMcoin = isHairMcoin;
	}

	public Double getCouponAnnualRate() {
		return couponAnnualRate;
	}

	public void setCouponAnnualRate(Double couponAnnualRate) {
		this.couponAnnualRate = couponAnnualRate;
	}

	public Double getCouponExpectEarnings() {
		return couponExpectEarnings;
	}

	public void setCouponExpectEarnings(Double couponExpectEarnings) {
		this.couponExpectEarnings = couponExpectEarnings;
	}

}