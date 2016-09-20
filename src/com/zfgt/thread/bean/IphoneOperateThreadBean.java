package com.zfgt.thread.bean;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.orm.ErrorRecord;
import com.zfgt.thread.dao.ThreadDAO;

/**运行单个渠道统计详情(IOS)
 * @author 覃文勇
 * @createTime 2016-3-17上午11:32:50
 */
@Service
public class IphoneOperateThreadBean {

	private Logger log = Logger.getLogger(IphoneOperateThreadBean.class);

	@Resource
	private ThreadDAO dao;

	/**
	 * 单个渠道投资统计(IOS)
	 */
	public void addIphoneOperateInvest(String yesterdays) {
		StringBuffer sb = new StringBuffer();
		// 一般投资统计
		sb.append(
				"INSERT INTO iphone_operate_day ( date, registChannel, investUserSum, firstInvestUserSum, reInvestUserSum, investCount, "
						+ "avgInvestCentSum, copiesSum, firstInvestCount, reInvestCount, reInvestRate, investCentSum, couponCentSum, firstInvestCentSum, "
						+ "reInvestCentSum ) SELECT z.date, IFNULL(s.registChannel,''), IFNULL(s.investUserSum, 0), IFNULL(s.firstInvestUserSum, 0), "
						+ "IFNULL(s.reInvestUserSum, 0), IFNULL(s.investCount, 0), IFNULL(s.avgInvestCentSum, 0), IFNULL(s.copiesSum, 0), IFNULL(s.firstInvestCount, 0), "
						+ "ifnull(s.reInvestCount, 0), ifnull(s.reInvestRate, 0), ifnull(s.investCentSum, 0), ifnull(s.couponCentSum, 0), ifnull(s.firstInvestCentSum, 0), "
						+ "ifnull(s.reInvestCentSum, 0) FROM ( SELECT DATE_FORMAT(x.insert_time, '%Y-%m-%d') date FROM dateday X ) z "
						+ "LEFT JOIN ( SELECT DATE_FORMAT(a.pay_time, '%Y-%m-%d') investDate, c.regist_channel registChannel, count(DISTINCT a.users_id) investUserSum, "
						+ "count( DISTINCT CASE WHEN a.pay_time = b.minPayTime THEN a.users_id ELSE NULL END ) firstInvestUserSum, "
						+ "count( DISTINCT CASE WHEN a.pay_time > b.minPayTime THEN a.users_id ELSE NULL END ) reInvestUserSum, count(1) investCount, "
						+ "sum(a.in_money) investCentSum, sum(a.in_money) / count(DISTINCT a.users_id) avgInvestCentSum, sum(a.copies) copiesSum, "
						+ "sum( CASE WHEN a.pay_time = b.minPayTime THEN 1 ELSE 0 END ) firstInvestCount, sum( CASE WHEN a.pay_time > b.minPayTime THEN 1 ELSE 0 END ) reInvestCount, "
						+ "count( DISTINCT CASE WHEN a.pay_time > b.minPayTime THEN a.users_id ELSE NULL END ) / count(DISTINCT a.users_id) AS reInvestRate, "
						+ "sum(a.coupon) couponCentSum, sum( CASE WHEN a.pay_time = b.minPayTime THEN a.in_money ELSE 0 END ) firstInvestCentSum, "
						+ "sum( CASE WHEN a.pay_time > b.minPayTime THEN a.in_money ELSE 0 END ) reInvestCentSum FROM investors a LEFT JOIN ( SELECT users_id, "
						+ "MIN( DATE_FORMAT(pay_time, '%Y-%m-%d')) minPayDate, MIN(pay_time) minPayTime FROM investors a WHERE a.investor_status IN (1, 2, 3) "
						+ "GROUP BY users_id ) b ON a.users_id = b.users_id LEFT JOIN users c ON b.users_id = c.id AND c.regist_platform='2' WHERE a.investor_status IN (1, 2, 3) "
						+ "GROUP BY DATE_FORMAT(a.pay_time, '%Y-%m-%d'), c.regist_channel ) s ON z.date = s.investDate  "
						+ "ON DUPLICATE KEY UPDATE investUserSum = VALUES (investUserSum), firstInvestUserSum = VALUES (firstInvestUserSum), "
						+ "reInvestUserSum = VALUES (reInvestUserSum), investCount = VALUES (investCount), avgInvestCentSum = VALUES (avgInvestCentSum), "
						+ "copiesSum = VALUES (copiesSum), firstInvestCount = VALUES (firstInvestCount), reInvestCount = VALUES (reInvestCount), "
						+ "reInvestRate = VALUES (reInvestRate), investCentSum = VALUES (investCentSum), couponCentSum = VALUES (couponCentSum), "
						+ "firstInvestCentSum = VALUES (firstInvestCentSum), reInvestCentSum = VALUES (reInvestCentSum)");
		log.info(sb.toString());
		String rets = dao.excuteSql(sb.toString(), null) + "";
		int ret = Integer.valueOf(rets);
		log.info("rets:" + rets);
		if (ret == 0) {
			log.info("【error】IphoneOperateThreadBean.addIphoneOperateInvest插入失败");
			ErrorRecord errorRecord = new ErrorRecord(Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(), "每日一般投资数据统计异常无数据");
			String id = dao.save(errorRecord);
			if (StringUtils.isBlank(id)) {
				log.info("【error】IphoneOperateThreadBean.addIphoneOperateInvest插入失败,errorRecord equasl " + errorRecord.toString());
			}
		}

		// 注册当天投资统计
		sb = new StringBuffer();
		sb.append(
				"INSERT INTO iphone_operate_day ( date, registChannel, regInvestCount, regInvestUserSum, regInvestCentSum ) "
						+ "SELECT z.date, IFNULL(s.registChannel,''), IFNULL(s.regInvestCount, 0), IFNULL(s.regInvestUserSum, 0), IFNULL(s.regInvestCentSum, 0) "
						+ "FROM ( SELECT DATE_FORMAT(x.insert_time, '%Y-%m-%d') date FROM dateday x ) z "
						+ "LEFT JOIN "
						+" ( SELECT DATE_FORMAT(a.insert_time, '%Y-%m-%d') date, a.regist_channel registChannel, count(a.id) AS regInvestCount, count(DISTINCT a.id) regInvestUserSum, "
						+ "sum(b.in_money) regInvestCentSum FROM users a LEFT JOIN investors b ON a.id = b.users_id AND a.regist_platform='2'  WHERE b.investor_status IN (1, 2, 3) "
						+ "AND DATE_FORMAT(a.insert_time, '%Y-%m-%d') = DATE_FORMAT(b.insert_time, '%Y-%m-%d') "
						+ "GROUP BY DATE_FORMAT(a.insert_time, '%Y-%m-%d'), a.regist_channel ) s ON z.date = s.date  "
						+ "ON DUPLICATE KEY UPDATE regInvestCount = VALUES (regInvestCount), regInvestUserSum = VALUES (regInvestUserSum), "
						+ "regInvestCentSum = VALUES (regInvestCentSum)");
		log.info(sb.toString());
		rets = dao.excuteSql(sb.toString(), null) + "";
		ret = Integer.valueOf(rets);
		log.info("rets:" + rets);
		if (ret == 0) {
			log.info("【error】IphoneOperateThreadBean.addIphoneOperateInvest插入失败");
			ErrorRecord errorRecord = new ErrorRecord(Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(), "每日注册当天投资数据统计异常无数据");
			String id = dao.save(errorRecord);
			if (StringUtils.isBlank(id)) {
				log.info("【error】IphoneOperateThreadBean.addIphoneOperateInvest插入失败,errorRecord equasl " + errorRecord.toString());
			}
		}

		// 首投当天复投统计(新增二次投资)
		sb = new StringBuffer();
		sb.append(
				" INSERT INTO iphone_operate_day ( DATE,registChannel , newTwoInvestCount, newTwoInvestUserSum, newTwoInvestCentSum ) " +
				" SELECT z.date, IFNULL(s.registChannel,''), IFNULL(s.newTwoInvestCount, 0), IFNULL(s.newTwoInvestUserSum, 0), IFNULL(s.newTwoInvestCentSum, 0) " +
				" FROM ( SELECT DATE_FORMAT(x.insert_time, '%Y-%m-%d') DATE FROM dateday X ) z" +
				" LEFT JOIN " +
				" ( " +
				" SELECT DATE_FORMAT(a.pay_time, '%Y-%m-%d') DATE, c.regist_channel registChannel, COUNT(a.id) newTwoInvestCount, COUNT(DISTINCT a.users_id) newTwoInvestUserSum, SUM(a.in_money) newTwoInvestCentSum FROM investors a" +
				" LEFT JOIN " +
				" ( SELECT users_id, MIN(pay_time) pay_time FROM investors WHERE investor_status IN (1, 2, 3) GROUP BY users_id ) b ON a.users_id = b.users_id AND DATE_FORMAT(a.pay_time, '%Y-%m-%d') = DATE_FORMAT(b.pay_time, '%Y-%m-%d') " +
				" LEFT JOIN users c ON a.users_id = c.id AND c.regist_platform='2' WHERE a.investor_status IN (1, 2, 3) AND a.pay_time > b.pay_time GROUP BY DATE_FORMAT(a.pay_time, '%Y-%m-%d'), c.regist_channel " +
				" ) s " +
				" ON z.date = s.date  " +
				" ON DUPLICATE KEY UPDATE newTwoInvestCount = VALUES (newTwoInvestCount), newTwoInvestUserSum = VALUES (newTwoInvestUserSum), newTwoInvestCentSum = VALUES (newTwoInvestCentSum)");
		log.info(sb.toString());
		rets = dao.excuteSql(sb.toString(), null) + "";
		ret = Integer.valueOf(rets);
		log.info("rets:" + rets);
		if (ret == 0) {
			log.info("【error】IphoneOperateThreadBean.addIphoneOperateInvest插入失败");
			ErrorRecord errorRecord = new ErrorRecord(Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(), "每日首投当天复投数据统计异常无数据");
			String id = dao.save(errorRecord);
			if (StringUtils.isBlank(id)) {
				log.info("【error】IphoneOperateThreadBean.addIphoneOperateInvest插入失败,errorRecord equasl " + errorRecord.toString());
			}
		}
	}

	/**
	 * 
	 */
	public void addIphoneOperateRegist(String yesterdays) {
		// 注册
		StringBuffer sb = new StringBuffer();

		sb.append(
				"INSERT INTO iphone_operate_day ( date, registChannel, regUserSum)"
						+" SELECT z.date, IFNULL(s.registChannel,''), "
						+" ifnull(s.regUserSum, 0) regUserSum FROM ( SELECT DATE_FORMAT(x.insert_time, '%Y-%m-%d') date "
						+" FROM dateday x ) z"
						+" LEFT JOIN "
						+" ( SELECT DATE_FORMAT(a.insert_time, '%Y-%m-%d') date, "
						+" a.regist_channel registChannel, COUNT(a.id) regUserSum FROM users a  "
						+" WHERE a.regist_platform='2'  GROUP BY DATE_FORMAT(a.insert_time, '%Y-%m-%d'), a.regist_channel ) s ON z.date = s.date "
						+"  ON DUPLICATE KEY UPDATE regUserSum = VALUES (regUserSum)");
		log.info(sb.toString());
		String rets = dao.excuteSql(sb.toString(), null) + "";
		int ret = Integer.valueOf(rets);
		log.info("rets:" + rets);
		if (ret == 0) {
			log.info("【error】IphoneOperateThreadBean.addIphoneOperateRegist插入失败");
			ErrorRecord errorRecord = new ErrorRecord(Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(), "每日用户数据统计异常无数据");
			String id = dao.save(errorRecord);
			if (StringUtils.isBlank(id)) {
				log.info("【error】IphoneOperateThreadBean.addIphoneOperateRegist插入失败,errorRecord equasl " + errorRecord.toString());
			}
		}

		// 绑卡
		sb = new StringBuffer();

		sb.append(
				"INSERT INTO iphone_operate_day ( date, registChannel, bindBankUserSum ) SELECT z.date, IFNULL(s.registChannel,''), count(s.users_id) bindBankUserSum "
						+ " FROM "
						+ " ( SELECT DATE_FORMAT(x.insert_time, '%Y-%m-%d') date FROM dateday x ) z "
						+ " LEFT JOIN "
						+ " (SELECT DATE_FORMAT( a.insert_time, '%Y-%m-%d' ) date, b.regist_channel registChannel, a.users_id FROM account a  "
						+ " LEFT JOIN users b ON a.users_id = b.id AND a.status='0' AND b.regist_platform='2'  ) s "
						+ " ON z.date = s.date GROUP BY z.date, s.registChannel "
						+ "ON DUPLICATE KEY UPDATE bindBankUserSum = VALUES (bindBankUserSum)");
		log.info(sb.toString());
		rets = dao.excuteSql(sb.toString(), null) + "";
		ret = Integer.valueOf(rets);
		log.info("rets:" + rets);
		if (ret == 0) {
			log.info("【error】IphoneOperateThreadBean.addIphoneOperateRegist插入失败");
			ErrorRecord errorRecord = new ErrorRecord(Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(), "每日用户数据统计异常无数据");
			String id = dao.save(errorRecord);
			if (StringUtils.isBlank(id)) {
				log.info("【error】IphoneOperateThreadBean.addIphoneOperateRegist插入失败,errorRecord equasl " + errorRecord.toString());
			}
		}

		// 激活人数
		sb = new StringBuffer();
		sb.append(
				" INSERT INTO iphone_operate_day ( DATE, registChannel, activateUserSum ) SELECT z.date, IFNULL(s.registChannel,''), IFNULL(s.activateUserSum, 0) activateUserSum " +
				" FROM " +
				" ( SELECT DATE_FORMAT(X.insert_time, '%Y-%m-%d') DATE FROM dateday X  ) z " +
				" LEFT JOIN " +
				" ( SELECT DATE_FORMAT(a.insert_time, '%Y-%m-%d') DATE,a.note registChannel, COUNT(a.id) activateUserSum FROM activity_iphone a GROUP BY DATE_FORMAT(a.insert_time, '%Y-%m-%d'),a.note) s " +
				" ON z.date = s.date ON DUPLICATE KEY UPDATE activateUserSum = VALUES (activateUserSum)");
		log.info(sb.toString());
		rets = dao.excuteSql(sb.toString(), null) + "";
		ret = Integer.valueOf(rets);
		log.info("rets:" + rets);
		if (ret == 0) {
			log.info("【error】IphoneOperateThreadBean.addIphoneOperateRegist插入失败");
			ErrorRecord errorRecord = new ErrorRecord(Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(), "每日用户数据统计异常无数据");
			String id = dao.save(errorRecord);
			if (StringUtils.isBlank(id)) {
				log.info("【error】IphoneOperateThreadBean.addIphoneOperateRegist插入失败,errorRecord equasl " + errorRecord.toString());
			}
		}
		
		// 渠道转化率（）
		sb = new StringBuffer();
		sb.append(" UPDATE iphone_operate_day a SET regActivityRate=IFNULL(a.bindBankUserSum/a.activateUserSum,0) ");
		log.info(sb.toString());
		rets = dao.excuteSql(sb.toString(), null) + "";
		ret = Integer.valueOf(rets);
		log.info("rets:" + rets);
		if (ret == 0) {
			log.info("【error】IphoneOperateThreadBean.addIphoneOperateRegist插入失败");
			ErrorRecord errorRecord = new ErrorRecord(Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(), "每日用户数据统计异常无数据");
			String id = dao.save(errorRecord);
			if (StringUtils.isBlank(id)) {
				log.info("【error】IphoneOperateThreadBean.addIphoneOperateRegist插入失败,errorRecord equasl " + errorRecord.toString());
			}
		}
	}

	public void addIphoneOperateRecharge(String yesterdays) {
		//充值次数，充值人数，充值金额，平均充值金额
		StringBuffer sb = new StringBuffer();

		sb.append(
				"INSERT INTO iphone_operate_day ( date, registChannel, rechargeCount, rechargeUserSum, rechargeCentSum, avgRechargeCentSum ) "
						+ "SELECT z.date, IFNULL(s.registChannel,''), ifnull(s.rechargeCount, 0) rechargeCount, ifnull(s.rechargeUserSum, 0) rechargeUserSum, "
						+ "ifnull(s.rechargeCentSum, 0) rechargeCentSum, ifnull(s.avgRechargeCentSum, 0) avgRechargeCentSum FROM ( "
						+ "SELECT DATE_FORMAT(x.insert_time, '%Y-%m-%d') date FROM dateday x ) z LEFT JOIN "
						+ "( SELECT DATE_FORMAT(a.insert_time, '%Y-%m-%d') date, b.regist_channel registChannel, count(a.id) rechargeCount, "
						+ "COUNT(DISTINCT a.users_id) rechargeUserSum, sum(a.money) rechargeCentSum, sum(a.money) / count(a.id) avgRechargeCentSum "
						+ "FROM cz_record a LEFT JOIN users b ON a.users_id = b.id and b.regist_platform='2' where a.status = '1' "
						+ "GROUP BY DATE_FORMAT(a.insert_time, '%Y-%m-%d'), b.regist_channel ) s "
						+ "ON z.date = s.date ON DUPLICATE KEY UPDATE rechargeCount = VALUES (rechargeCount), "
						+ "rechargeUserSum = VALUES (rechargeUserSum), rechargeCentSum = VALUES (rechargeCentSum), avgRechargeCentSum = VALUES (avgRechargeCentSum)");

		log.info(sb.toString());
		String rets = dao.excuteSql(sb.toString(), null) + "";
		int ret = Integer.valueOf(rets);
		log.info("rets:" + rets);
		if (ret == 0) {
			log.info("【error】IphoneOperateThreadBean.addIphoneOperateRecharge插入失败");
			ErrorRecord errorRecord = new ErrorRecord(Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(), "每日充值金额统计异常无数据");
			String id = dao.save(errorRecord);
			if (StringUtils.isBlank(id)) {
				log.info("【error】IphoneOperateThreadBean.addIphoneOperateRecharge插入失败,errorRecord equasl " + errorRecord.toString());
			}
		}
	}

	public void addIphoneOperateWithdraw(String yesterdays) {
		// 申请提现次数,申请提现人数,申请提现总金额
		StringBuffer sb = new StringBuffer();

		sb.append(
				"INSERT INTO iphone_operate_day ( date, registChannel, withdrawCount, withdrawUserSum, withdrawCentSum ) "
						+ " SELECT z.date, IFNULL(s.registChannel,''), ifnull(s.withdrawCount, 0) withdrawCount, ifnull(s.withdrawUserSum, 0) withdrawUserSum, ifnull(s.withdrawCentSum, 0) withdrawCentSum " 
						+ " FROM ( SELECT DATE_FORMAT(x.insert_time, '%Y-%m-%d') date FROM dateday x ) z  "
						+ " LEFT JOIN ( SELECT DATE_FORMAT(a.insert_time, '%Y-%m-%d') date, b.regist_channel registChannel, "
						+ " count(a.id) withdrawCount, COUNT(DISTINCT a.users_id) withdrawUserSum, sum(a.money) withdrawCentSum FROM fund_record a LEFT JOIN users b "
						+ " ON a.users_id = b.id AND b.regist_platform='2'  where a.type='tx' GROUP BY DATE_FORMAT(a.insert_time, '%Y-%m-%d'), b.regist_channel ) s ON z.date = s.date "
						+ " ON DUPLICATE KEY UPDATE withdrawCount = VALUES (withdrawCount), "
						+ " withdrawUserSum = VALUES (withdrawUserSum), withdrawCentSum = VALUES (withdrawCentSum)");

		log.info(sb.toString());
		String rets = dao.excuteSql(sb.toString(), null) + "";
		int ret = Integer.valueOf(rets);
		log.info("rets:" + rets);
		if (ret == 0) {
			log.info("【error】IphoneOperateThreadBean.addIphoneOperateWithdraw插入失败");
			ErrorRecord errorRecord = new ErrorRecord(Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(), "每日提现申请金额统计异常无记录");
			String id = dao.save(errorRecord);
			if (StringUtils.isBlank(id)) {
				log.info("【error】IphoneOperateThreadBean.addIphoneOperateWithdraw插入失败,errorRecord equasl " + errorRecord.toString());
			}
		}

		// 成功提现次数,成功提现人数,成功提现总额
		sb = new StringBuffer();
		sb.append(
				" INSERT INTO iphone_operate_day ( DATE, registChannel, successWithdrawCount, successWithdrawUserSum, successWithdrawCentSum )" +
				" SELECT z.date, IFNULL(s.registChannel,''), IFNULL(s.successWithdrawCount, 0) successWithdrawCount, IFNULL(s.successWithdrawUserSum, 0) successWithdrawUserSum, IFNULL(s.successWithdrawCentSum, 0) successWithdrawCentSum " +
				" FROM " +
				" ( SELECT DATE_FORMAT(x.insert_time, '%Y-%m-%d') DATE FROM dateday X ) z " +
				" LEFT JOIN " +
				" ( SELECT DATE_FORMAT(a.insert_time, '%Y-%m-%d') DATE,  b.regist_channel registChannel, COUNT(a.id) successWithdrawCount, COUNT(DISTINCT a.users_id) successWithdrawUserSum , SUM(a.money) successWithdrawCentSum " +
				" FROM tx_record a LEFT JOIN users b ON " +
				" a.users_id = b.id AND b.regist_platform='2' WHERE a. STATUS = 1  GROUP BY DATE_FORMAT(a.insert_time, '%Y-%m-%d'), b.regist_channel ) s ON z.date = s.date ON DUPLICATE KEY UPDATE successWithdrawCount = VALUES (successWithdrawCount), successWithdrawUserSum = VALUES (successWithdrawUserSum)");

		log.info(sb.toString());
		rets = dao.excuteSql(sb.toString(), null) + "";
		ret = Integer.valueOf(rets);
		log.info("rets:" + rets);

		if (ret == 0) {
			log.info("【error】IphoneOperateThreadBean.addIphoneOperateWithdraw插入失败");
			ErrorRecord errorRecord = new ErrorRecord(Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(), "每日提现成功金额统计异常无记录");
			String id = dao.save(errorRecord);
			if (StringUtils.isBlank(id)) {
				log.info("【error】IphoneOperateThreadBean.addIphoneOperateWithdraw插入失败,errorRecord equasl " + errorRecord.toString());
			}
		}

	}
	/**
	 * 更新 还款本金统计，还款利息统计，
	 * @param yesterdays
	 */
	public void addIphoneOperateRepayment(String yesterdays) {
		StringBuffer sb = new StringBuffer();

		sb.append(
				" INSERT INTO iphone_operate_day ( DATE, registChannel, repayPrincipalCentSum, repayInterestCentSum, repayAlreadyAllCentSum )" +
				" SELECT z.date, IFNULL(s.registChannel,''), IFNULL(s.repayPrincipalCentSum, 0) repayPrincipalCentSum, IFNULL(s.repayInterestCentSum, 0) repayInterestCentSum, IFNULL(s.repayAlreadyAllCentSum, 0) repayAlreadyAllCentSum" +
				" FROM " +
				" ( SELECT DATE_FORMAT(x.insert_time, '%Y-%m-%d') DATE FROM dateday X ) z" +
				" LEFT JOIN " +
				" ( SELECT DATE_FORMAT(a.return_time, '%Y-%m-%d') DATE, b.regist_channel registChannel, SUM(a.pay_money) repayPrincipalCentSum, SUM(a.pay_interest) repayInterestCentSum, SUM(a.already_pay) repayAlreadyAllCentSum" +
				" FROM interest_details a LEFT JOIN users b ON a.users_id = b.id  AND b.regist_platform='2' WHERE a. STATUS = 2" +
				" GROUP BY DATE_FORMAT(a.return_time, '%Y-%m-%d'), b.regist_channel ) s ON z.date = s.date " +
				" ON DUPLICATE KEY UPDATE repayPrincipalCentSum = VALUES (repayPrincipalCentSum), repayInterestCentSum = VALUES (repayInterestCentSum), repayAlreadyAllCentSum = VALUES (repayAlreadyAllCentSum)");

		log.info(sb.toString());
		String rets = dao.excuteSql(sb.toString(), null) + "";
		int ret = Integer.valueOf(rets);
		log.info("rets:" + rets);
		if (ret == 0) {
			log.info("【error】IphoneOperateThreadBean.addIphoneOperateRepayment插入失败");
			ErrorRecord errorRecord = new ErrorRecord(Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(), "每日提现申请金额统计异常无记录");
			String id = dao.save(errorRecord);
			if (StringUtils.isBlank(id)) {
				log.info("【error】IphoneOperateThreadBean.addIphoneOperateRepayment插入失败,errorRecord equasl " + errorRecord.toString());
			}
		}
	}

}
