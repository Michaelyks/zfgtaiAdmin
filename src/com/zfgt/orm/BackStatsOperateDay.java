package com.zfgt.orm;

import java.util.Date;

import com.zfgt.common.util.QwyUtil;

public class BackStatsOperateDay {

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 日期
	 */
	private Date date;

	/**
	 * 日期
	 */
	private String dates;

	/**
	 * '注册平台 0web 1android 2ios 3weixin'
	 */
	private int registPlatform;

	/**
	 * '注册人数'
	 */
	private int regUserSum;

	/**
	 * 绑定银行卡用户
	 */
	private int bindBankUserSum;

	/**
	 * 激活用户人数
	 */
	private int activateUserSum;

	/**
	 * 投资人数
	 */
	private int investUserSum;

	/**
	 * 首投人数
	 */
	private int firstInvestUserSum;

	/**
	 * 复投人数
	 */
	private int reInvestUserSum;

	/**
	 * 投资次数
	 */
	private int investCount;

	/**
	 * 首投次数
	 */
	private int firstInvestCount;

	/**
	 * 购买份数
	 */
	private int copiesSum;

	/**
	 * 复投次数
	 */
	private int reInvestCount;

	/**
	 * 投资金额统计
	 */
	private double investCentSum;

	/**
	 * 投资劵金额统计
	 */
	private double couponCentSum;

	/**
	 * 首投金额统计
	 */
	private double firstInvestCentSum;

	/**
	 * 复投金额统计
	 */
	private double reInvestCentSum;

	/**
	 * 人均投资总额
	 */
	private double avgInvestCentSum;

	/**
	 * 还款本金统计
	 */
	private double repayPrincipalCentSum;

	/**
	 * 还款利息
	 */
	private double repayInterestCentSum;

	/**
	 * 充值次数
	 */
	private int rechargeCount;

	/**
	 * 充值人数
	 */
	private int rechargeUserSum;

	/**
	 * 充值金额
	 */
	private double rechargeCentSum;

	/**
	 * 平均充值金额
	 */
	private double avgRechargeCentSum;

	/**
	 * 提现次数
	 */
	private int withdrawCount;

	/**
	 * 提现用户人数
	 */
	private int withdrawUserSum;

	/**
	 * 提现总额
	 */
	private double withdrawCentSum;

	/**
	 * 成功提现次数
	 */
	private int successWithdrawCount;

	/**
	 * 成功提现人数
	 */
	private int successWithdrawUserSum;

	/**
	 * 成功提现总额
	 */
	private double successWithdrawCentSum;

	/**
	 * 当天成功提现次数
	 */
	private int sameDaySuccessWithdrawCount;

	/**
	 * 当天成功提现总额
	 */
	private double sameDaySuccessWithdrawCentSum;

	/**
	 * 当天提现未到账的笔数
	 */
	private int todayWithdrawNoArrivalCount;

	/**
	 * 当天提现没到账的金额
	 */
	private double todayWithdrawNoArrivalCentSum;

	/**
	 * 昨天提现今天到账笔数
	 */
	private int yesterdayWithdrawTodayArrivalCount;

	/**
	 * 昨天提现今天到账金额
	 */
	private double yesterdayWithdrawTodayArrivalCentSum;

	/**
	 * 非今天提现但今天到账笔数
	 */
	private int notSameDayWithdrawTodayArrivalCount;

	/**
	 * 非今天提现但今天到账金额
	 */
	private double notSameDayWithdrawTodayArrivalCentSum;

	/**
	 * 复投率
	 */
	private double reInvestRate;

	/**
	 * 注册投资人数
	 */
	private int regInvestUserSum;

	/**
	 * 注册投资总额
	 */
	private double regInvestCentSum;

	/**
	 * 注册投资转换率
	 */
	private double regInvestRate;

	/**
	 * 新增二次投资次数
	 */
	private int newTwoInvestCount;

	/**
	 * 新增二次投资人数
	 */
	private int newTwoInvestUserSum;

	/**
	 * 新增二次投资总额
	 */
	private double newTwoInvestCentSum;

	/**
	 * 新增二次投资率
	 */
	private double newTwoInvestRate;

	/**
	 * 待结算产品总额
	 */
	private double stillBalanceProductCentSum;

	/**
	 * 待结算用户总额
	 */
	private double stillBalanceUserCentSum;

	/**
	 * 3天内到期产品总额
	 */
	private double dueThreeDayProductCentSum;

	/**
	 * 3天内到期用户总额
	 */
	private double dueThreeDayUserCentSum;

	/**
	 * 已到期未结算金额
	 */
	private double dueNoBalanceCentSum;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getRegistPlatform() {
		return registPlatform;
	}

	public void setRegistPlatform(int registPlatform) {
		this.registPlatform = registPlatform;
	}

	public int getRegUserSum() {
		return regUserSum;
	}

	public void setRegUserSum(int regUserSum) {
		this.regUserSum = regUserSum;
	}

	public int getBindBankUserSum() {
		return bindBankUserSum;
	}

	public void setBindBankUserSum(int bindBankUserSum) {
		this.bindBankUserSum = bindBankUserSum;
	}

	public int getActivateUserSum() {
		return activateUserSum;
	}

	public void setActivateUserSum(int activateUserSum) {
		this.activateUserSum = activateUserSum;
	}

	public int getInvestUserSum() {
		return investUserSum;
	}

	public void setInvestUserSum(int investUserSum) {
		this.investUserSum = investUserSum;
	}

	public int getFirstInvestUserSum() {
		return firstInvestUserSum;
	}

	public void setFirstInvestUserSum(int firstInvestUserSum) {
		this.firstInvestUserSum = firstInvestUserSum;
	}

	public int getReInvestUserSum() {
		return reInvestUserSum;
	}

	public void setReInvestUserSum(int reInvestUserSum) {
		this.reInvestUserSum = reInvestUserSum;
	}

	public int getInvestCount() {
		return investCount;
	}

	public void setInvestCount(int investCount) {
		this.investCount = investCount;
	}

	public int getFirstInvestCount() {
		return firstInvestCount;
	}

	public void setFirstInvestCount(int firstInvestCount) {
		this.firstInvestCount = firstInvestCount;
	}

	public int getCopiesSum() {
		return copiesSum;
	}

	public void setCopiesSum(int copiesSum) {
		this.copiesSum = copiesSum;
	}

	public int getReInvestCount() {
		return reInvestCount;
	}

	public void setReInvestCount(int reInvestCount) {
		this.reInvestCount = reInvestCount;
	}

	public double getInvestCentSum() {
		return investCentSum;
	}

	public void setInvestCentSum(double investCentSum) {
		this.investCentSum = investCentSum;
	}

	public double getCouponCentSum() {
		return couponCentSum;
	}

	public void setCouponCentSum(double couponCentSum) {
		this.couponCentSum = couponCentSum;
	}

	public double getFirstInvestCentSum() {
		return firstInvestCentSum;
	}

	public void setFirstInvestCentSum(double firstInvestCentSum) {
		this.firstInvestCentSum = firstInvestCentSum;
	}

	public double getReInvestCentSum() {
		return reInvestCentSum;
	}

	public void setReInvestCentSum(double reInvestCentSum) {
		this.reInvestCentSum = reInvestCentSum;
	}

	public double getAvgInvestCentSum() {
		return avgInvestCentSum;
	}

	public void setAvgInvestCentSum(double avgInvestCentSum) {
		this.avgInvestCentSum = avgInvestCentSum;
	}

	public double getRepayPrincipalCentSum() {
		return repayPrincipalCentSum;
	}

	public void setRepayPrincipalCentSum(double repayPrincipalCentSum) {
		this.repayPrincipalCentSum = repayPrincipalCentSum;
	}

	public double getRepayInterestCentSum() {
		return repayInterestCentSum;
	}

	public void setRepayInterestCentSum(double repayInterestCentSum) {
		this.repayInterestCentSum = repayInterestCentSum;
	}

	public int getRechargeCount() {
		return rechargeCount;
	}

	public void setRechargeCount(int rechargeCount) {
		this.rechargeCount = rechargeCount;
	}

	public int getRechargeUserSum() {
		return rechargeUserSum;
	}

	public void setRechargeUserSum(int rechargeUserSum) {
		this.rechargeUserSum = rechargeUserSum;
	}

	public double getRechargeCentSum() {
		return rechargeCentSum;
	}

	public void setRechargeCentSum(double rechargeCentSum) {
		this.rechargeCentSum = rechargeCentSum;
	}

	public double getAvgRechargeCentSum() {
		return avgRechargeCentSum;
	}

	public void setAvgRechargeCentSum(double avgRechargeCentSum) {
		this.avgRechargeCentSum = avgRechargeCentSum;
	}

	public int getWithdrawCount() {
		return withdrawCount;
	}

	public void setWithdrawCount(int withdrawCount) {
		this.withdrawCount = withdrawCount;
	}

	public int getWithdrawUserSum() {
		return withdrawUserSum;
	}

	public void setWithdrawUserSum(int withdrawUserSum) {
		this.withdrawUserSum = withdrawUserSum;
	}

	public double getWithdrawCentSum() {
		return withdrawCentSum;
	}

	public void setWithdrawCentSum(double withdrawCentSum) {
		this.withdrawCentSum = withdrawCentSum;
	}

	public int getSuccessWithdrawCount() {
		return successWithdrawCount;
	}

	public void setSuccessWithdrawCount(int successWithdrawCount) {
		this.successWithdrawCount = successWithdrawCount;
	}

	public int getSuccessWithdrawUserSum() {
		return successWithdrawUserSum;
	}

	public void setSuccessWithdrawUserSum(int successWithdrawUserSum) {
		this.successWithdrawUserSum = successWithdrawUserSum;
	}

	public double getSuccessWithdrawCentSum() {
		return successWithdrawCentSum;
	}

	public void setSuccessWithdrawCentSum(double successWithdrawCentSum) {
		this.successWithdrawCentSum = successWithdrawCentSum;
	}

	public double getReInvestRate() {
		return reInvestRate;
	}

	public void setReInvestRate(double reInvestRate) {
		this.reInvestRate = reInvestRate;
	}

	public int getRegInvestUserSum() {
		return regInvestUserSum;
	}

	public void setRegInvestUserSum(int regInvestUserSum) {
		this.regInvestUserSum = regInvestUserSum;
	}

	public double getRegInvestCentSum() {
		return regInvestCentSum;
	}

	public void setRegInvestCentSum(double regInvestCentSum) {
		this.regInvestCentSum = regInvestCentSum;
	}

	public double getRegInvestRate() {
		return regInvestRate;
	}

	public void setRegInvestRate(double regInvestRate) {
		this.regInvestRate = regInvestRate;
	}

	public int getNewTwoInvestCount() {
		return newTwoInvestCount;
	}

	public void setNewTwoInvestCount(int newTwoInvestCount) {
		this.newTwoInvestCount = newTwoInvestCount;
	}

	public int getNewTwoInvestUserSum() {
		return newTwoInvestUserSum;
	}

	public void setNewTwoInvestUserSum(int newTwoInvestUserSum) {
		this.newTwoInvestUserSum = newTwoInvestUserSum;
	}

	public double getNewTwoInvestCentSum() {
		return newTwoInvestCentSum;
	}

	public void setNewTwoInvestCentSum(double newTwoInvestCentSum) {
		this.newTwoInvestCentSum = newTwoInvestCentSum;
	}

	public double getNewTwoInvestRate() {
		return newTwoInvestRate;
	}

	public void setNewTwoInvestRate(double newTwoInvestRate) {
		this.newTwoInvestRate = newTwoInvestRate;
	}

	public double getStillBalanceProductCentSum() {
		return stillBalanceProductCentSum;
	}

	public void setStillBalanceProductCentSum(double stillBalanceProductCentSum) {
		this.stillBalanceProductCentSum = stillBalanceProductCentSum;
	}

	public double getStillBalanceUserCentSum() {
		return stillBalanceUserCentSum;
	}

	public void setStillBalanceUserCentSum(double stillBalanceUserCentSum) {
		this.stillBalanceUserCentSum = stillBalanceUserCentSum;
	}

	public double getDueThreeDayProductCentSum() {
		return dueThreeDayProductCentSum;
	}

	public void setDueThreeDayProductCentSum(double dueThreeDayProductCentSum) {
		this.dueThreeDayProductCentSum = dueThreeDayProductCentSum;
	}

	public double getDueThreeDayUserCentSum() {
		return dueThreeDayUserCentSum;
	}

	public void setDueThreeDayUserCentSum(double dueThreeDayUserCentSum) {
		this.dueThreeDayUserCentSum = dueThreeDayUserCentSum;
	}

	public double getDueNoBalanceCentSum() {
		return dueNoBalanceCentSum;
	}

	public void setDueNoBalanceCentSum(double dueNoBalanceCentSum) {
		this.dueNoBalanceCentSum = dueNoBalanceCentSum;
	}

	public String getDates() {
		return dates;
	}

	public void setDates(String dates) {
		this.dates = dates;
	}

	public void setAvgInvestCentSum() {

		if (this.investCentSum == 0 || this.investUserSum == 0) {
			this.avgInvestCentSum = 0d;
			return;
		}
		try {
			this.avgInvestCentSum = Double.valueOf(QwyUtil.calcNumber(this.investCentSum, this.investUserSum, "/", 2) + "");
		} catch (Exception e) {
			e.printStackTrace();
			this.avgInvestCentSum = 0d;
		}
	}

	public void setNewTwoInvestRate() {
		if (this.investCount == 0 || this.newTwoInvestCount == 0) {
			this.newTwoInvestRate = 0d;
			return;
		}
		try {
			this.newTwoInvestRate = Double.valueOf(QwyUtil.calcNumber(QwyUtil.calcNumber(this.newTwoInvestUserSum, this.firstInvestUserSum, "/", 4), 100, "*", 2) + "");
		} catch (Exception e) {
			e.printStackTrace();
			this.newTwoInvestRate = 0d;
		}
	}

	public void setReInvestRate() {
		if (this.investUserSum == 0 || this.reInvestUserSum == 0) {
			this.reInvestRate = 0d;
			return;
		}
		try {
			this.reInvestRate = Double.valueOf(QwyUtil.calcNumber(QwyUtil.calcNumber(this.reInvestUserSum, this.investUserSum, "/", 4), 100, "*", 2) + "");
		} catch (Exception e) {
			e.printStackTrace();
			this.reInvestRate = 0d;
		}
	}

	public int getSameDaySuccessWithdrawCount() {
		return sameDaySuccessWithdrawCount;
	}

	public void setSameDaySuccessWithdrawCount(int sameDaySuccessWithdrawCount) {
		this.sameDaySuccessWithdrawCount = sameDaySuccessWithdrawCount;
	}

	public double getSameDaySuccessWithdrawCentSum() {
		return sameDaySuccessWithdrawCentSum;
	}

	public void setSameDaySuccessWithdrawCentSum(double sameDaySuccessWithdrawCentSum) {
		this.sameDaySuccessWithdrawCentSum = sameDaySuccessWithdrawCentSum;
	}

	public int getTodayWithdrawNoArrivalCount() {
		return todayWithdrawNoArrivalCount;
	}

	public void setTodayWithdrawNoArrivalCount(int todayWithdrawNoArrivalCount) {
		this.todayWithdrawNoArrivalCount = todayWithdrawNoArrivalCount;
	}

	public double getTodayWithdrawNoArrivalCentSum() {
		return todayWithdrawNoArrivalCentSum;
	}

	public void setTodayWithdrawNoArrivalCentSum(double todayWithdrawNoArrivalCentSum) {
		this.todayWithdrawNoArrivalCentSum = todayWithdrawNoArrivalCentSum;
	}

	public int getYesterdayWithdrawTodayArrivalCount() {
		return yesterdayWithdrawTodayArrivalCount;
	}

	public void setYesterdayWithdrawTodayArrivalCount(int yesterdayWithdrawTodayArrivalCount) {
		this.yesterdayWithdrawTodayArrivalCount = yesterdayWithdrawTodayArrivalCount;
	}

	public double getYesterdayWithdrawTodayArrivalCentSum() {
		return yesterdayWithdrawTodayArrivalCentSum;
	}

	public void setYesterdayWithdrawTodayArrivalCentSum(double yesterdayWithdrawTodayArrivalCentSum) {
		this.yesterdayWithdrawTodayArrivalCentSum = yesterdayWithdrawTodayArrivalCentSum;
	}

	public int getNotSameDayWithdrawTodayArrivalCount() {
		return notSameDayWithdrawTodayArrivalCount;
	}

	public void setNotSameDayWithdrawTodayArrivalCount(int notSameDayWithdrawTodayArrivalCount) {
		this.notSameDayWithdrawTodayArrivalCount = notSameDayWithdrawTodayArrivalCount;
	}

	public double getNotSameDayWithdrawTodayArrivalCentSum() {
		return notSameDayWithdrawTodayArrivalCentSum;
	}

	public void setNotSameDayWithdrawTodayArrivalCentSum(double notSameDayWithdrawTodayArrivalCentSum) {
		this.notSameDayWithdrawTodayArrivalCentSum = notSameDayWithdrawTodayArrivalCentSum;
	}

}
