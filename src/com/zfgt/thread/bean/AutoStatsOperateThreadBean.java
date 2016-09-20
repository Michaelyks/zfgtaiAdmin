package com.zfgt.thread.bean;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.orm.ErrorRecord;
import com.zfgt.thread.dao.ThreadDAO;

@Service
public class AutoStatsOperateThreadBean {

	private Logger log = Logger.getLogger(AutoStatsOperateThreadBean.class);

	@Resource
	private ThreadDAO dao;

	/**
	 * 投资统计
	 */
	public void addStatsOperateInvest(String dayStr) {
		String sqlStr = null;
		sqlStr = getStatsInvestInsertSql(dayStr, dayStr);
		String rets = dao.excuteSql(sqlStr, null) + "";
		int ret = Integer.valueOf(rets);
		log.info("rets:" + rets);
		if (ret == 0) {
			log.info("【error】addStatsOperateInvest插入失败");
			ErrorRecord errorRecord = new ErrorRecord(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "每日一般投资数据统计异常无数据," + dayStr);
			String id = dao.save(errorRecord);
			if (StringUtils.isBlank(id)) {
				log.info("【error】addStatsOperateInvest插入失败,errorRecord equasl " + errorRecord.toString());
			}
		}

		// 注册当天投资统计
		sqlStr = null;
		sqlStr = getStatsRegInvestInsertSql(dayStr, dayStr);
		rets = dao.excuteSql(sqlStr, null) + "";
		ret = Integer.valueOf(rets);
		log.info("rets:" + rets);
		if (ret == 0) {
			log.info("【error】addStatsOperateInvest插入失败");
			ErrorRecord errorRecord = new ErrorRecord(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
					"每日注册当天投资数据统计异常无数据," + dayStr);
			String id = dao.save(errorRecord);
			if (StringUtils.isBlank(id)) {
				log.info("【error】addStatsOperateInvest插入失败,errorRecord equasl " + errorRecord.toString());
			}
		}

		// 首投当天复投统计(新增二次投资)
		sqlStr = null;
		sqlStr = null;
		sqlStr = getStatsNewTwoInvestInvestInsertSql(dayStr, dayStr);
		rets = dao.excuteSql(sqlStr, null) + "";
		ret = Integer.valueOf(rets);
		log.info("rets:" + rets);
		if (ret == 0) {
			log.info("【error】addStatsOperateInvest插入失败");
			ErrorRecord errorRecord = new ErrorRecord(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
					"每日首投当天复投数据统计异常无数据," + dayStr);
			String id = dao.save(errorRecord);
			if (StringUtils.isBlank(id)) {
				log.info("【error】addStatsOperateInvest插入失败,errorRecord equasl " + errorRecord.toString());
			}
		}

	}

	/**
	 * 用户统计
	 */
	public void addStatsOperateRegist(String dayStr) {
		String sqlStr = null;
		// 注册
		sqlStr = getStatsRegistInsertSql(dayStr, dayStr);
		String rets = dao.excuteSql(sqlStr, null) + "";
		int ret = Integer.valueOf(rets);
		log.info("rets:" + rets);
		if (ret == 0) {
			log.info("【error】addStatsOperateRegist插入失败");
			ErrorRecord errorRecord = new ErrorRecord(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "每日用户数据统计异常无数据," + dayStr);
			String id = dao.save(errorRecord);
			if (StringUtils.isBlank(id)) {
				log.info("【error】addStatsOperateRegist插入失败,errorRecord equasl " + errorRecord.toString());
			}
		}

		// 绑卡
		sqlStr = null;
		sqlStr = getStatsBindBankInsertSql(dayStr, dayStr);
		rets = dao.excuteSql(sqlStr, null) + "";
		ret = Integer.valueOf(rets);
		log.info("rets:" + rets);
		if (ret == 0) {
			log.info("【error】addStatsOperateRegist插入失败");
			ErrorRecord errorRecord = new ErrorRecord(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "每日用户数据统计异常无数据," + dayStr);
			String id = dao.save(errorRecord);
			if (StringUtils.isBlank(id)) {
				log.info("【error】addStatsOperateRegist插入失败,errorRecord equasl " + errorRecord.toString());
			}
		}

		// 激活人数
		sqlStr = null;
		sqlStr = getStatsActivateInsertSql(dayStr, dayStr);
		rets = dao.excuteSql(sqlStr, null) + "";
		ret = Integer.valueOf(rets);
		log.info("rets:" + rets);
		if (ret == 0) {
			log.info("【error】addStatsOperateRegist插入失败");
			ErrorRecord errorRecord = new ErrorRecord(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "每日用户数据统计异常无数据," + dayStr);
			String id = dao.save(errorRecord);
			if (StringUtils.isBlank(id)) {
				log.info("【error】addStatsOperateRegist插入失败,errorRecord equasl " + errorRecord.toString());
			}
		}
	}

	/**
	 * 充值统计
	 * 
	 * @param dayStr
	 */
	public void addStatsOperateRecharge(String dayStr) {
		String sqlStr = null;
		sqlStr = getStatsRechargeInsertSql(dayStr, dayStr);
		String rets = dao.excuteSql(sqlStr, null) + "";
		int ret = Integer.valueOf(rets);
		log.info("rets:" + rets);
		if (ret == 0) {
			log.info("【error】addStatsOperateRecharge插入失败");
			ErrorRecord errorRecord = new ErrorRecord(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "每日充值金额统计异常无数据," + dayStr);
			String id = dao.save(errorRecord);
			if (StringUtils.isBlank(id)) {
				log.info("【error】addStatsOperateRecharge插入失败,errorRecord equasl " + errorRecord.toString());
			}
		}
	}

	/**
	 * 提现统计
	 * 
	 * @param dayStr
	 */
	public void addStatsOperateWithdraw(String dayStr) {
		String sqlStr = null;
		// 申请
		sqlStr = getStatsWithdrawApplyInsertSql(dayStr, dayStr);

		String rets = dao.excuteSql(sqlStr, null) + "";
		int ret = Integer.valueOf(rets);
		log.info("rets:" + rets);
		if (ret == 0) {
			log.info("【error】addStatsOperateWithdraw插入失败");
			ErrorRecord errorRecord = new ErrorRecord(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "每日提现申请金额统计异常无记录," + dayStr);
			String id = dao.save(errorRecord);
			if (StringUtils.isBlank(id)) {
				log.info("【error】addStatsOperateWithdraw插入失败,errorRecord equasl " + errorRecord.toString());
			}
		}

		// 成功
		sqlStr = null;

		sqlStr = getStatsWithdrawSuccessInsertSql(dayStr, dayStr);

		rets = dao.excuteSql(sqlStr, null) + "";
		ret = Integer.valueOf(rets);
		log.info("rets:" + rets);

		if (ret == 0) {
			log.info("【error】addStatsOperateWithdraw插入失败");
			ErrorRecord errorRecord = new ErrorRecord(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "每日提现成功金额统计异常无记录," + dayStr);
			String id = dao.save(errorRecord);
			if (StringUtils.isBlank(id)) {
				log.info("【error】addStatsOperateWithdraw插入失败,errorRecord equasl " + errorRecord.toString());
			}
		}

		// TODO new

		// 当天成功
		sqlStr = null;

		sqlStr = getStatsWithdrawSameDaySuccessInsertSql(dayStr, dayStr);

		rets = dao.excuteSql(sqlStr, null) + "";
		ret = Integer.valueOf(rets);
		log.info("rets:" + rets);

		if (ret == 0) {
			log.info("【error】addStatsOperateWithdraw插入失败");
			ErrorRecord errorRecord = new ErrorRecord(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "当日提现成功金额统计异常无记录," + dayStr);
			String id = dao.save(errorRecord);
			if (StringUtils.isBlank(id)) {
				log.info("【error】addStatsOperateWithdraw插入失败,errorRecord equasl " + errorRecord.toString());
			}
		}

		// 当天提现未到账
		sqlStr = null;

		sqlStr = getStatsWithdrawSameDayNoArrivalInsertSql(dayStr, dayStr);

		rets = dao.excuteSql(sqlStr, null) + "";
		ret = Integer.valueOf(rets);
		log.info("rets:" + rets);

		if (ret == 0) {
			log.info("【error】addStatsOperateWithdraw插入失败");
			ErrorRecord errorRecord = new ErrorRecord(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "当日提现成功金额统计异常无记录," + dayStr);
			String id = dao.save(errorRecord);
			if (StringUtils.isBlank(id)) {
				log.info("【error】addStatsOperateWithdraw插入失败,errorRecord equasl " + errorRecord.toString());
			}
		}

		// 昨天提现今天到账
		sqlStr = null;

		sqlStr = getStatsYesterdayWithdrawTodayArrivalInsertSql(dayStr, dayStr);

		rets = dao.excuteSql(sqlStr, null) + "";
		ret = Integer.valueOf(rets);
		log.info("rets:" + rets);

		if (ret == 0) {
			log.info("【error】addStatsOperateWithdraw插入失败");
			ErrorRecord errorRecord = new ErrorRecord(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "当日提现成功金额统计异常无记录," + dayStr);
			String id = dao.save(errorRecord);
			if (StringUtils.isBlank(id)) {
				log.info("【error】addStatsOperateWithdraw插入失败,errorRecord equasl " + errorRecord.toString());
			}
		}

		// 非今天提现但今天到账笔数
		sqlStr = null;

		sqlStr = getStatsWithdrawNotSameDayTodayArrivalInsertSql(dayStr, dayStr);

		rets = dao.excuteSql(sqlStr, null) + "";
		ret = Integer.valueOf(rets);
		log.info("rets:" + rets);

		if (ret == 0) {
			log.info("【error】addStatsOperateWithdraw插入失败");
			ErrorRecord errorRecord = new ErrorRecord(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "当日提现成功金额统计异常无记录," + dayStr);
			String id = dao.save(errorRecord);
			if (StringUtils.isBlank(id)) {
				log.info("【error】addStatsOperateWithdraw插入失败,errorRecord equasl " + errorRecord.toString());
			}
		}
	}

	/**
	 * 付息统计
	 * 
	 * @param dayStr
	 */
	public void addStatsOperateRepayment(String dayStr) {
		String sqlStr = null;

		sqlStr = getStatsRepaymentInsertSql(dayStr, dayStr);

		String rets = dao.excuteSql(sqlStr, null) + "";
		int ret = Integer.valueOf(rets);
		log.info("rets:" + rets);
		if (ret == 0) {
			log.info("【error】addStatsOperateWithdraw插入失败");
			ErrorRecord errorRecord = new ErrorRecord(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "每日提现申请金额统计异常无记录," + dayStr);
			String id = dao.save(errorRecord);
			if (StringUtils.isBlank(id)) {
				log.info("【error】addStatsOperateWithdraw插入失败,errorRecord equasl " + errorRecord.toString());
			}
		}
	}

	// =================================sql拼接

	/**
	 * 一般投资统计插入
	 * 
	 * @param startDateStr
	 * @param endDateStr
	 * @return
	 */
	public static String getStatsInvestInsertSql(String startDateStr, String endDateStr) {
		StringBuffer sb = new StringBuffer();
		sb.append(
				"INSERT INTO back_stats_operate_day ( date, registPlatform, investUserSum, firstInvestUserSum, reInvestUserSum, investCount, avgInvestCentSum, copiesSum, firstInvestCount, reInvestCount, reInvestRate, investCentSum, couponCentSum, firstInvestCentSum, reInvestCentSum ) ");
		getStatsInvestQuerySql(sb, startDateStr, endDateStr);

		sb.append("ON DUPLICATE KEY UPDATE investUserSum = VALUES (investUserSum), firstInvestUserSum = VALUES (firstInvestUserSum), "
				+ "reInvestUserSum = VALUES (reInvestUserSum), investCount = VALUES (investCount), avgInvestCentSum = VALUES (avgInvestCentSum), "
				+ "copiesSum = VALUES (copiesSum), firstInvestCount = VALUES (firstInvestCount), reInvestCount = VALUES (reInvestCount), "
				+ "reInvestRate = VALUES (reInvestRate), investCentSum = VALUES (investCentSum), couponCentSum = VALUES (couponCentSum), "
				+ "firstInvestCentSum = VALUES (firstInvestCentSum), reInvestCentSum = VALUES (reInvestCentSum)");
		return sb.toString();
	}

	/**
	 * 一般投资统计
	 * 
	 * @param sb
	 * @param startDateStr
	 * @param endDateStr
	 * @return
	 */
	public static StringBuffer getStatsInvestQuerySql(StringBuffer sb, String startDateStr, String endDateStr) {
		sb.append("SELECT z.date, z.registPlatform, IFNULL(s.investUserSum, 0), IFNULL(s.firstInvestUserSum, 0), IFNULL(s.reInvestUserSum, 0), "
				+ "IFNULL(s.investCount, 0), IFNULL(s.avgInvestCentSum, 0), IFNULL(s.copiesSum, 0), IFNULL(s.firstInvestCount, 0),  ifnull(s.reInvestCount, 0), "
				+ "ifnull(s.reInvestRate, 0), ifnull(s.investCentSum, 0), ifnull(s.couponCentSum, 0), ifnull(s.firstInvestCentSum, 0),  ifnull(s.reInvestCentSum, 0) ");
		sb.append("FROM ( SELECT DATE_FORMAT(x.insert_time, '%Y-%m-%d') date, y.id registPlatform FROM dateday x, type_regist_platform y where 1 = 1 ");
		if (StringUtils.isNotBlank(startDateStr)) {
			sb.append(" and DATE_FORMAT(x.insert_time, '%Y-%m-%d') >= '");
			sb.append(startDateStr);
			sb.append("'");
		}
		if (StringUtils.isNotBlank(endDateStr)) {
			sb.append(" and DATE_FORMAT(x.insert_time, '%Y-%m-%d') <= '");
			sb.append(endDateStr);
			sb.append("'");
		}
		sb.append(") z ");
		sb.append("LEFT JOIN ( SELECT DATE_FORMAT(a.pay_time, '%Y-%m-%d') investDate, c.regist_platform, count(DISTINCT a.users_id) investUserSum, "
				+ "count( DISTINCT CASE WHEN a.pay_time = b.minPayTime THEN a.users_id ELSE NULL END ) firstInvestUserSum, "
				+ "count( DISTINCT CASE WHEN a.pay_time > b.minPayTime THEN a.users_id ELSE NULL END ) reInvestUserSum, count(1) investCount, "
				+ "sum(a.in_money) investCentSum, sum(a.in_money) / count(DISTINCT a.users_id) avgInvestCentSum, sum(a.copies) copiesSum, "
				+ "sum( CASE WHEN a.pay_time = b.minPayTime THEN 1 ELSE 0 END ) firstInvestCount, sum( CASE WHEN a.pay_time > b.minPayTime THEN 1 ELSE 0 END ) reInvestCount, "
				+ "count( DISTINCT CASE WHEN a.pay_time > b.minPayTime THEN a.users_id ELSE NULL END ) / count(DISTINCT a.users_id) AS reInvestRate, "
				+ "sum(a.coupon) couponCentSum, sum( CASE WHEN a.pay_time = b.minPayTime THEN a.in_money ELSE 0 END ) firstInvestCentSum, "
				+ "sum( CASE WHEN a.pay_time > b.minPayTime THEN a.in_money ELSE 0 END ) reInvestCentSum FROM investors a inner JOIN ( SELECT users_id, "
				+ "MIN( DATE_FORMAT(pay_time, '%Y-%m-%d')) minPayDate, MIN(pay_time) minPayTime FROM investors a WHERE a.investor_status IN (1, 2, 3) "
				+ "GROUP BY users_id ) b ON a.users_id = b.users_id inner JOIN users c ON b.users_id = c.id WHERE a.investor_status IN (1, 2, 3) ");

		if (StringUtils.isNotBlank(startDateStr)) {
			sb.append(" and DATE_FORMAT(a.pay_time, '%Y-%m-%d') >= '");
			sb.append(startDateStr);
			sb.append("'");
		}
		if (StringUtils.isNotBlank(endDateStr)) {
			sb.append(" and DATE_FORMAT(a.pay_time, '%Y-%m-%d') <= '");
			sb.append(endDateStr);
			sb.append("'");
		}

		sb.append("GROUP BY DATE_FORMAT(a.pay_time, '%Y-%m-%d'), c.regist_platform ");
		sb.append(") s ON z.date = s.investDate AND z.registPlatform = s.regist_platform ");
		return sb;
	}

	/**
	 * 注册投资统计插入
	 * 
	 * @param sb
	 * @param startDateStr
	 * @param endDateStr
	 * @return
	 */
	public static String getStatsRegInvestInsertSql(String startDateStr, String endDateStr) {
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO back_stats_operate_day ( date, registPlatform, regInvestCount, regInvestUserSum, regInvestCentSum ) ");
		getStatsRegInvestQuerySql(sb, startDateStr, endDateStr);
		sb.append("ON DUPLICATE KEY UPDATE regInvestCount = VALUES (regInvestCount), regInvestUserSum = VALUES (regInvestUserSum), " + "regInvestCentSum = VALUES (regInvestCentSum)");

		return sb.toString();
	}

	/**
	 * 注册投资统计
	 * 
	 * @param sb
	 * @param startDateStr
	 * @param endDateStr
	 * @return
	 */
	public static StringBuffer getStatsRegInvestQuerySql(StringBuffer sb, String startDateStr, String endDateStr) {

		sb.append("SELECT z.date, z.registPlatform, IFNULL(s.regInvestCount, 0), IFNULL(s.regInvestUserSum, 0), IFNULL(s.regInvestCentSum, 0) "
				+ "FROM ( SELECT DATE_FORMAT(x.insert_time, '%Y-%m-%d') date, y.id registPlatform FROM dateday x, type_regist_platform y where 1 = 1 ");
		if (StringUtils.isNotBlank(startDateStr)) {
			sb.append(" and DATE_FORMAT(x.insert_time, '%Y-%m-%d') >= '");
			sb.append(startDateStr);
			sb.append("'");
		}
		if (StringUtils.isNotBlank(endDateStr)) {
			sb.append(" and DATE_FORMAT(x.insert_time, '%Y-%m-%d') <= '");
			sb.append(endDateStr);
			sb.append("'");
		}
		sb.append(") z ");
		sb.append("LEFT JOIN ( SELECT DATE_FORMAT(a.insert_time, '%Y-%m-%d') date, a.regist_platform, count(a.id) AS regInvestCount, count(DISTINCT a.id) regInvestUserSum, "
				+ "sum(b.in_money) regInvestCentSum FROM users a inner JOIN investors b ON a.id = b.users_id WHERE b.investor_status IN (1, 2, 3) "
				+ "AND DATE_FORMAT(a.insert_time, '%Y-%m-%d') = DATE_FORMAT(b.insert_time, '%Y-%m-%d') ");
		if (StringUtils.isNotBlank(startDateStr)) {
			sb.append(" and DATE_FORMAT(b.insert_time, '%Y-%m-%d') >= '");
			sb.append(startDateStr);
			sb.append("'");
		}
		if (StringUtils.isNotBlank(endDateStr)) {
			sb.append(" and DATE_FORMAT(b.insert_time, '%Y-%m-%d') <= '");
			sb.append(endDateStr);
			sb.append("'");
		}
		sb.append("GROUP BY DATE_FORMAT(a.insert_time, '%Y-%m-%d'), a.regist_platform ) s ON z.date = s.date AND z.registPlatform = s.regist_platform ");

		return sb;
	}

	/**
	 * 新增2次投资插入
	 * 
	 * @param startDateStr
	 * @param endDateStr
	 * @return
	 */
	private String getStatsNewTwoInvestInvestInsertSql(String startDateStr, String endDateStr) {
		StringBuffer sb = new StringBuffer();

		sb.append("INSERT INTO back_stats_operate_day ( date, registPlatform, newTwoInvestCount, newTwoInvestUserSum, newTwoInvestCentSum ) ");
		getStatsNewTwoInvestSql(sb, startDateStr, endDateStr);
		sb.append("ON DUPLICATE KEY UPDATE newTwoInvestCount = VALUES (newTwoInvestCount), newTwoInvestUserSum = VALUES (newTwoInvestUserSum), newTwoInvestCentSum = VALUES (newTwoInvestCentSum)");

		return sb.toString();
	}

	/**
	 * 新增2次投资
	 * 
	 * @param sb
	 * @param startDateStr
	 * @param endDateStr
	 * @return
	 */
	public StringBuffer getStatsNewTwoInvestSql(StringBuffer sb, String startDateStr, String endDateStr) {

		sb.append("SELECT z.date, z.registPlatform, IFNULL(s.newTwoInvestCount, 0), IFNULL(s.newTwoInvestUserSum, 0), IFNULL(s.newTwoInvestCentSum, 0) "
				+ "FROM ( SELECT DATE_FORMAT(x.insert_time, '%Y-%m-%d') date, y.id registPlatform FROM dateday x, type_regist_platform y where 1 = 1 ");
		if (StringUtils.isNotBlank(startDateStr)) {
			sb.append(" and DATE_FORMAT(x.insert_time, '%Y-%m-%d') >= '");
			sb.append(startDateStr);
			sb.append("'");
		}
		if (StringUtils.isNotBlank(endDateStr)) {
			sb.append(" and DATE_FORMAT(x.insert_time, '%Y-%m-%d') <= '");
			sb.append(endDateStr);
			sb.append("'");
		}
		sb.append(") z ");
		sb.append("LEFT JOIN ( SELECT DATE_FORMAT(a.pay_time, '%Y-%m-%d') date, c.regist_platform, count(a.id) newTwoInvestCount, "
				+ "count(DISTINCT a.users_id) newTwoInvestUserSum, sum(a.in_money) newTwoInvestCentSum FROM investors a LEFT JOIN "
				+ "( SELECT users_id, MIN(pay_time) pay_time FROM investors WHERE investor_status IN (1, 2, 3) GROUP BY users_id ) b ON a.users_id = b.users_id "
				+ "AND DATE_FORMAT(a.pay_time, '%Y-%m-%d') = DATE_FORMAT(b.pay_time, '%Y-%m-%d') LEFT JOIN users c ON a.users_id = c.id "
				+ "WHERE a.investor_status IN (1, 2, 3) AND a.pay_time > b.pay_time ");

		if (StringUtils.isNotBlank(startDateStr)) {
			sb.append(" and DATE_FORMAT(a.pay_time, '%Y-%m-%d') >= '");
			sb.append(startDateStr);
			sb.append("'");
		}
		if (StringUtils.isNotBlank(endDateStr)) {
			sb.append(" and DATE_FORMAT(a.pay_time, '%Y-%m-%d') <= '");
			sb.append(endDateStr);
			sb.append("'");
		}

		sb.append("GROUP BY DATE_FORMAT(a.pay_time, '%Y-%m-%d'), c.regist_platform ) s ON z.date = s.date AND z.registPlatform = s.regist_platform ");

		return sb;
	}

	/**
	 * 注册插入
	 * 
	 * @param startDateStr
	 * @param endDateStr
	 * @return
	 */
	public static String getStatsRegistInsertSql(String startDateStr, String endDateStr) {
		StringBuffer sb = new StringBuffer();

		sb.append("INSERT INTO back_stats_operate_day ( date, registPlatform, regUserSum) ");
		getStatsRegistSql(sb, startDateStr, endDateStr);
		sb.append("ON DUPLICATE KEY UPDATE regUserSum = VALUES (regUserSum)");

		return sb.toString();
	}

	/**
	 * 注册
	 * 
	 * @param sb
	 * @param startDateStr
	 * @param endDateStr
	 * @return
	 */
	public static StringBuffer getStatsRegistSql(StringBuffer sb, String startDateStr, String endDateStr) {

		sb.append("SELECT z.date, z.registPlatform, ifnull(s.regUserSum, 0) regUserSum FROM (");
		sb.append("SELECT DATE_FORMAT(x.insert_time, '%Y-%m-%d') date, y.id registPlatform FROM dateday x, type_regist_platform y where 1 = 1 ");
		if (StringUtils.isNotBlank(startDateStr)) {
			sb.append("and DATE_FORMAT(x.insert_time, '%Y-%m-%d') >= '");
			sb.append(startDateStr);
			sb.append("'");
		}
		if (StringUtils.isNotBlank(endDateStr)) {
			sb.append(" and DATE_FORMAT(x.insert_time, '%Y-%m-%d') <= '");
			sb.append(endDateStr);
			sb.append("'");
		}

		sb.append(") z LEFT JOIN ( SELECT DATE_FORMAT(a.insert_time, '%Y-%m-%d') date, a.regist_platform registPlatform, COUNT(a.id) regUserSum ");
		sb.append("FROM users a LEFT JOIN users_info b ON a.id = b.users_id where 1 = 1 ");

		if (StringUtils.isNotBlank(startDateStr)) {
			sb.append(" and DATE_FORMAT(a.insert_time, '%Y-%m-%d') >= '");
			sb.append(startDateStr);
			sb.append("'");
		}
		if (StringUtils.isNotBlank(endDateStr)) {
			sb.append(" and DATE_FORMAT(a.insert_time, '%Y-%m-%d') <= '");
			sb.append(endDateStr);
			sb.append("'");
		}

		sb.append("GROUP BY DATE_FORMAT(a.insert_time, '%Y-%m-%d'), a.regist_platform ) s ON z.date = s.date AND z.registPlatform = s.registPlatform ");

		return sb;
	}

	/**
	 * 绑卡插入
	 * 
	 * @param startDateStr
	 * @param endDateStr
	 * @return
	 */
	public static String getStatsBindBankInsertSql(String startDateStr, String endDateStr) {
		StringBuffer sb = new StringBuffer();

		sb.append("INSERT INTO back_stats_operate_day ( date, registPlatform, bindBankUserSum) ");
		getStatsBindBankSql(sb, startDateStr, endDateStr);
		sb.append("ON DUPLICATE KEY UPDATE bindBankUserSum = VALUES (bindBankUserSum)");

		return sb.toString();
	}

	/**
	 * 绑卡
	 * 
	 * @param sb
	 * @param startDateStr
	 * @param endDateStr
	 * @return
	 */
	public static StringBuffer getStatsBindBankSql(StringBuffer sb, String startDateStr, String endDateStr) {

		sb.append("SELECT z.date, z.registPlatform, count(DISTINCT s.users_id) bindBankUserSum FROM ( ");
		sb.append("SELECT DATE_FORMAT(x.insert_time, '%Y-%m-%d') date, y.id registPlatform FROM dateday x, type_regist_platform y where 1 = 1 ");
		if (StringUtils.isNotBlank(startDateStr)) {
			sb.append(" and DATE_FORMAT(x.insert_time, '%Y-%m-%d') >= '");
			sb.append(startDateStr);
			sb.append("'");
		}
		if (StringUtils.isNotBlank(endDateStr)) {
			sb.append(" and DATE_FORMAT(x.insert_time, '%Y-%m-%d') <= '");
			sb.append(endDateStr);
			sb.append("'");
		}

		sb.append(") z LEFT JOIN (SELECT DATE_FORMAT( a.insert_time, '%Y-%m-%d' ) date, b.regist_platform registPlatform, a.users_id ");
		sb.append("FROM account a LEFT JOIN users b ON a.users_id = b.id where 1 = 1 and a.STATUS = 0 ");

		if (StringUtils.isNotBlank(startDateStr)) {
			sb.append(" and DATE_FORMAT(a.insert_time, '%Y-%m-%d') >= '");
			sb.append(startDateStr);
			sb.append("'");
		}
		if (StringUtils.isNotBlank(endDateStr)) {
			sb.append(" and DATE_FORMAT(a.insert_time, '%Y-%m-%d') <= '");
			sb.append(endDateStr);
			sb.append("'");
		}

		sb.append(") s ON z.date = s.date AND z.registPlatform = s.registPlatform GROUP BY z.date, z.registPlatform ");

		return sb;
	}

	/**
	 * 激活用户插入
	 * 
	 * @param startDateStr
	 * @param endDateStr
	 * @return
	 */
	public static String getStatsActivateInsertSql(String startDateStr, String endDateStr) {
		StringBuffer sb = new StringBuffer();

		sb.append("INSERT INTO back_stats_operate_day ( date, registPlatform, activateUserSum ) ");
		getStatsActivatSql(sb, startDateStr, endDateStr);
		sb.append("ON DUPLICATE KEY UPDATE activateUserSum = VALUES (activateUserSum);");

		return sb.toString();
	}

	/**
	 * 激活用户
	 * 
	 * @param sb
	 * @param startDateStr
	 * @param endDateStr
	 * @return
	 */
	public static StringBuffer getStatsActivatSql(StringBuffer sb, String startDateStr, String endDateStr) {

		sb.append("SELECT z.date, 1, ifnull(s.activateUserSum, 0) activateUserSum FROM ( ");
		sb.append("SELECT DATE_FORMAT(insert_time, '%Y-%m-%d') date FROM dateday where 1 = 1 ");

		if (StringUtils.isNotBlank(startDateStr)) {
			sb.append(" and DATE_FORMAT(insert_time, '%Y-%m-%d') >= '");
			sb.append(startDateStr);
			sb.append("'");
		}
		if (StringUtils.isNotBlank(endDateStr)) {
			sb.append(" and DATE_FORMAT(insert_time, '%Y-%m-%d') <= '");
			sb.append(endDateStr);
			sb.append("'");
		}

		sb.append(") z LEFT JOIN ( SELECT DATE_FORMAT(insert_time, '%Y-%m-%d') date, count(1) activateUserSum FROM activity where 1 = 1 ");

		if (StringUtils.isNotBlank(startDateStr)) {
			sb.append(" and DATE_FORMAT(insert_time, '%Y-%m-%d') >= '");
			sb.append(startDateStr);
			sb.append("'");
		}
		if (StringUtils.isNotBlank(endDateStr)) {
			sb.append(" and DATE_FORMAT(insert_time, '%Y-%m-%d') <= '");
			sb.append(endDateStr);
			sb.append("'");
		}

		sb.append("GROUP BY DATE_FORMAT(insert_time, '%Y-%m-%d')) s ON z.date = s.date GROUP BY z.date ");

		return sb;
	}

	/**
	 * 充值插入
	 * 
	 * @param startDateStr
	 * @param endDateStr
	 * @return
	 */
	public static String getStatsRechargeInsertSql(String startDateStr, String endDateStr) {
		StringBuffer sb = new StringBuffer();

		sb.append("INSERT INTO back_stats_operate_day ( date, registPlatform, rechargeCount, rechargeUserSum, rechargeCentSum, avgRechargeCentSum ) ");
		getStatsRechargeSql(sb, startDateStr, endDateStr);
		sb.append(
				"ON DUPLICATE KEY UPDATE rechargeCount = VALUES (rechargeCount), rechargeUserSum = VALUES (rechargeUserSum), rechargeCentSum = VALUES (rechargeCentSum), avgRechargeCentSum = VALUES (avgRechargeCentSum)");

		return sb.toString();
	}

	/**
	 * 充值
	 * 
	 * @param sb
	 * @param startDateStr
	 * @param endDateStr
	 * @return
	 */
	public static StringBuffer getStatsRechargeSql(StringBuffer sb, String startDateStr, String endDateStr) {
		sb.append("SELECT z.date, z.registPlatform, ifnull(s.rechargeCount, 0) rechargeCount, ifnull(s.rechargeUserSum, 0) rechargeUserSum, "
				+ "ifnull(s.rechargeCentSum, 0) rechargeCentSum, ifnull(s.avgRechargeCentSum, 0) avgRechargeCentSum FROM ( "
				+ "SELECT DATE_FORMAT(x.insert_time, '%Y-%m-%d') date, y.id registPlatform FROM dateday x, type_regist_platform y where 1 = 1 ");
		if (StringUtils.isNotBlank(startDateStr)) {
			sb.append(" and DATE_FORMAT(x.insert_time, '%Y-%m-%d') >= '");
			sb.append(startDateStr);
			sb.append("'");
		}
		if (StringUtils.isNotBlank(endDateStr)) {
			sb.append(" and DATE_FORMAT(x.insert_time, '%Y-%m-%d') <= '");
			sb.append(endDateStr);
			sb.append("'");
		}
		sb.append(") z LEFT JOIN ( SELECT DATE_FORMAT(a.insert_time, '%Y-%m-%d') date, b.regist_platform registPlatform, count(a.id) rechargeCount, "
				+ "COUNT(DISTINCT a.users_id) rechargeUserSum, sum(a.money) rechargeCentSum, sum(a.money) / count(a.id) avgRechargeCentSum "
				+ "FROM cz_record a LEFT JOIN users b ON a.users_id = b.id where a.status = 1 ");

		if (StringUtils.isNotBlank(startDateStr)) {
			sb.append(" and DATE_FORMAT(a.insert_time, '%Y-%m-%d') >= '");
			sb.append(startDateStr);
			sb.append("'");
		}
		if (StringUtils.isNotBlank(endDateStr)) {
			sb.append(" and DATE_FORMAT(a.insert_time, '%Y-%m-%d') <= '");
			sb.append(endDateStr);
			sb.append("'");
		}

		sb.append("GROUP BY DATE_FORMAT(a.insert_time, '%Y-%m-%d'), b.regist_platform ) s " + "ON z.date = s.date AND z.registPlatform = s.registPlatform ");
		return sb;
	}

	/**
	 * 提现申请插入
	 * 
	 * @param startDateStr
	 * @param endDateStr
	 * @return
	 */
	public static String getStatsWithdrawApplyInsertSql(String startDateStr, String endDateStr) {
		StringBuffer sb = new StringBuffer();

		sb.append("INSERT INTO back_stats_operate_day ( date, registPlatform, withdrawCount, withdrawUserSum, withdrawCentSum ) ");
		getStatsWithdrawApplySql(sb, startDateStr, endDateStr);
		sb.append("ON DUPLICATE KEY UPDATE withdrawCount = VALUES (withdrawCount), withdrawUserSum = VALUES (withdrawUserSum), withdrawCentSum = VALUES (withdrawCentSum)");
		return sb.toString();
	}

	/**
	 * 提现申请
	 * 
	 * @param sb
	 * @param startDateStr
	 * @param endDateStr
	 * @return
	 */
	public static StringBuffer getStatsWithdrawApplySql(StringBuffer sb, String startDateStr, String endDateStr) {

		sb.append("SELECT z.date, z.registPlatform, ifnull(s.withdrawCount, 0) withdrawCount, ifnull(s.withdrawUserSum, 0) withdrawUserSum, ifnull(s.withdrawCentSum, 0) withdrawCentSum FROM ( ");
		sb.append("SELECT DATE_FORMAT(x.insert_time, '%Y-%m-%d') date, y.id registPlatform FROM dateday x, type_regist_platform y where 1 = 1 ");
		if (StringUtils.isNotBlank(startDateStr)) {
			sb.append(" and DATE_FORMAT(x.insert_time, '%Y-%m-%d') >= '");
			sb.append(startDateStr);
			sb.append("'");
		}
		if (StringUtils.isNotBlank(endDateStr)) {
			sb.append(" and DATE_FORMAT(x.insert_time, '%Y-%m-%d') <= '");
			sb.append(endDateStr);
			sb.append("'");
		}

		sb.append(") z LEFT JOIN ( SELECT DATE_FORMAT(a.insert_time, '%Y-%m-%d') date, b.regist_platform registPlatform, count(a.id) withdrawCount, "
				+ "COUNT(DISTINCT a.users_id) withdrawUserSum, sum(a.money) withdrawCentSum FROM fund_record a LEFT JOIN users b ON a.users_id = b.id  where a.type='tx' ");
		if (StringUtils.isNotBlank(startDateStr)) {
			sb.append(" and DATE_FORMAT(a.insert_time, '%Y-%m-%d') >= '");
			sb.append(startDateStr);
			sb.append("'");
		}
		if (StringUtils.isNotBlank(endDateStr)) {
			sb.append(" and DATE_FORMAT(a.insert_time, '%Y-%m-%d') <= '");
			sb.append(endDateStr);
			sb.append("'");
		}
		sb.append("GROUP BY DATE_FORMAT(a.insert_time, '%Y-%m-%d'), b.regist_platform ) s ON z.date = s.date " + "AND z.registPlatform = s.registPlatform ");

		return sb;
	}

	/**
	 * 提现成功插入
	 * 
	 * @param startDateStr
	 * @param endDateStr
	 * @return
	 */
	public static String getStatsWithdrawSuccessInsertSql(String startDateStr, String endDateStr) {
		StringBuffer sb = new StringBuffer();

		sb.append("INSERT INTO back_stats_operate_day ( date, registPlatform, successWithdrawCount, successWithdrawUserSum, successWithdrawCentSum ) ");
		getStatsWithdrawSuccessSql(sb, startDateStr, endDateStr);
		sb.append("ON DUPLICATE KEY UPDATE successWithdrawCount = VALUES (successWithdrawCount), successWithdrawUserSum = VALUES (successWithdrawUserSum), "
				+ "successWithdrawCentSum = VALUES (successWithdrawCentSum)");
		return sb.toString();
	}

	/**
	 * 提现成功
	 * 
	 * @param sb
	 * @param startDateStr
	 * @param endDateStr
	 * @return
	 */
	public static StringBuffer getStatsWithdrawSuccessSql(StringBuffer sb, String startDateStr, String endDateStr) {

		sb.append("SELECT z.date, z.registPlatform, ifnull(s.successWithdrawCount, 0) successWithdrawCount, ifnull(s.successWithdrawUserSum, 0) successWithdrawUserSum, "
				+ "ifnull(s.successWithdrawCentSum, 0) successWithdrawCentSum FROM ( SELECT DATE_FORMAT(x.insert_time, '%Y-%m-%d') date, y.id registPlatform FROM dateday x, type_regist_platform y where 1 = 1 ");
		if (StringUtils.isNotBlank(startDateStr)) {
			sb.append(" and DATE_FORMAT(x.insert_time, '%Y-%m-%d') >= '");
			sb.append(startDateStr);
			sb.append("'");
		}
		if (StringUtils.isNotBlank(endDateStr)) {
			sb.append(" and DATE_FORMAT(x.insert_time, '%Y-%m-%d') <= '");
			sb.append(endDateStr);
			sb.append("'");
		}

		sb.append(
				") z LEFT JOIN ( SELECT DATE_FORMAT(a.insert_time, '%Y-%m-%d') date, b.regist_platform registPlatform, count(a.id) successWithdrawCount, COUNT(DISTINCT a.users_id) successWithdrawUserSum, "
						+ "sum(a.money) successWithdrawCentSum FROM tx_record a LEFT JOIN users b ON a.users_id = b.id WHERE a. STATUS = 1 ");
		if (StringUtils.isNotBlank(startDateStr)) {
			sb.append(" and DATE_FORMAT(a.insert_time, '%Y-%m-%d') >= '");
			sb.append(startDateStr);
			sb.append("'");
		}
		if (StringUtils.isNotBlank(endDateStr)) {
			sb.append(" and DATE_FORMAT(a.insert_time, '%Y-%m-%d') <= '");
			sb.append(endDateStr);
			sb.append("'");
		}

		sb.append("GROUP BY DATE_FORMAT(a.insert_time, '%Y-%m-%d'), b.regist_platform ) s ON z.date = s.date AND z.registPlatform = s.registPlatform ");

		return sb;
	}

	/**
	 * 当天提现成功插入
	 * 
	 * @param startDateStr
	 * @param endDateStr
	 * @return
	 */
	public static String getStatsWithdrawSameDaySuccessInsertSql(String startDateStr, String endDateStr) {
		StringBuffer sb = new StringBuffer();

		sb.append("INSERT INTO back_stats_operate_day ( date, registPlatform, sameDaySuccessWithdrawCount, sameDaySuccessWithdrawCentSum ) ");
		getStatsWithdrawSameDaySuccessSql(sb, startDateStr, endDateStr);
		sb.append("ON DUPLICATE KEY UPDATE sameDaySuccessWithdrawCount = VALUES (sameDaySuccessWithdrawCount), sameDaySuccessWithdrawCentSum = VALUES (sameDaySuccessWithdrawCentSum)");
		return sb.toString();
	}

	/**
	 * 当天提现成功
	 * 
	 * @param sb
	 * @param startDateStr
	 * @param endDateStr
	 * @return
	 */
	public static StringBuffer getStatsWithdrawSameDaySuccessSql(StringBuffer sb, String startDateStr, String endDateStr) {

		sb.append(
				"SELECT z.date, z.registPlatform, ifnull(s.sameDaySuccessWithdrawCount, 0) sameDaySuccessWithdrawCount, ifnull(s.sameDaySuccessWithdrawCentSum, 0) sameDaySuccessWithdrawCentSum FROM ( SELECT DATE_FORMAT(x.insert_time, '%Y-%m-%d') date, y.id registPlatform FROM dateday x, type_regist_platform y where 1 = 1 ");
		if (StringUtils.isNotBlank(startDateStr)) {
			sb.append(" and DATE_FORMAT(x.insert_time, '%Y-%m-%d') >= '");
			sb.append(startDateStr);
			sb.append("'");
		}
		if (StringUtils.isNotBlank(endDateStr)) {
			sb.append(" and DATE_FORMAT(x.insert_time, '%Y-%m-%d') <= '");
			sb.append(endDateStr);
			sb.append("'");
		}

		sb.append(
				") z LEFT JOIN ( SELECT DATE_FORMAT(a.insert_time, '%Y-%m-%d') date, b.regist_platform registPlatform, count(a.id) sameDaySuccessWithdrawCount, "
						+ "sum(a.money) sameDaySuccessWithdrawCentSum FROM tx_record a LEFT JOIN users b ON a.users_id = b.id WHERE a. STATUS = 1 and DATE_FORMAT(a.insert_time, '%Y-%m-%d') = DATE_FORMAT(a.check_time, '%Y-%m-%d') ");
		if (StringUtils.isNotBlank(startDateStr)) {
			sb.append(" and DATE_FORMAT(a.insert_time, '%Y-%m-%d') >= '");
			sb.append(startDateStr);
			sb.append("'");
		}
		if (StringUtils.isNotBlank(endDateStr)) {
			sb.append(" and DATE_FORMAT(a.insert_time, '%Y-%m-%d') <= '");
			sb.append(endDateStr);
			sb.append("'");
		}

		sb.append("GROUP BY DATE_FORMAT(a.insert_time, '%Y-%m-%d'), b.regist_platform ) s ON z.date = s.date AND z.registPlatform = s.registPlatform ");

		return sb;
	}

	/**
	 * 当天提现未到账插入
	 * 
	 * @param startDateStr
	 * @param endDateStr
	 * @return
	 */
	public static String getStatsWithdrawSameDayNoArrivalInsertSql(String startDateStr, String endDateStr) {
		StringBuffer sb = new StringBuffer();

		sb.append("INSERT INTO back_stats_operate_day ( date, registPlatform, todayWithdrawNoArrivalCount, todayWithdrawNoArrivalCentSum ) ");
		getStatsWithdrawSameDayNoArrivalSql(sb, startDateStr, endDateStr);
		sb.append("ON DUPLICATE KEY UPDATE todayWithdrawNoArrivalCount = VALUES (todayWithdrawNoArrivalCount), todayWithdrawNoArrivalCentSum = VALUES (todayWithdrawNoArrivalCentSum)");
		return sb.toString();
	}

	/**
	 * 当天提现未到账
	 * 
	 * @param sb
	 * @param startDateStr
	 * @param endDateStr
	 * @return
	 */
	public static StringBuffer getStatsWithdrawSameDayNoArrivalSql(StringBuffer sb, String startDateStr, String endDateStr) {

		sb.append(
				"SELECT z.date, z.registPlatform, ifnull(s.todayWithdrawNoArrivalCount, 0) todayWithdrawNoArrivalCount, ifnull(s.todayWithdrawNoArrivalCentSum, 0) todayWithdrawNoArrivalCentSum FROM ( SELECT DATE_FORMAT(x.insert_time, '%Y-%m-%d') date, y.id registPlatform FROM dateday x, type_regist_platform y where 1 = 1 ");
		if (StringUtils.isNotBlank(startDateStr)) {
			sb.append(" and DATE_FORMAT(x.insert_time, '%Y-%m-%d') >= '");
			sb.append(startDateStr);
			sb.append("'");
		}
		if (StringUtils.isNotBlank(endDateStr)) {
			sb.append(" and DATE_FORMAT(x.insert_time, '%Y-%m-%d') <= '");
			sb.append(endDateStr);
			sb.append("'");
		}

		sb.append(
				") z LEFT JOIN ( SELECT DATE_FORMAT(a.insert_time, '%Y-%m-%d') date, b.regist_platform registPlatform, count(a.id) todayWithdrawNoArrivalCount, "
						+ "sum(a.money) todayWithdrawNoArrivalCentSum FROM tx_record a LEFT JOIN users b ON a.users_id = b.id WHERE a. STATUS = 1 and DATE_FORMAT(a.insert_time, '%Y-%m-%d') != DATE_FORMAT(a.check_time, '%Y-%m-%d')");
		if (StringUtils.isNotBlank(startDateStr)) {
			sb.append(" and DATE_FORMAT(a.insert_time, '%Y-%m-%d') >= '");
			sb.append(startDateStr);
			sb.append("'");
		}
		if (StringUtils.isNotBlank(endDateStr)) {
			sb.append(" and DATE_FORMAT(a.insert_time, '%Y-%m-%d') <= '");
			sb.append(endDateStr);
			sb.append("'");
		}

		sb.append("GROUP BY DATE_FORMAT(a.insert_time, '%Y-%m-%d'), b.regist_platform ) s ON z.date = s.date AND z.registPlatform = s.registPlatform ");

		return sb;
	}

	/**
	 * 昨天提现今天到账插入
	 * 
	 * @param startDateStr
	 * @param endDateStr
	 * @return
	 */
	public static String getStatsYesterdayWithdrawTodayArrivalInsertSql(String startDateStr, String endDateStr) {
		StringBuffer sb = new StringBuffer();

		sb.append("INSERT INTO back_stats_operate_day ( date, registPlatform, yesterdayWithdrawTodayArrivalCount, yesterdayWithdrawTodayArrivalCentSum ) ");
		getStatsYesterdayWithdrawTodayArrivalSql(sb, startDateStr, endDateStr);
		sb.append("ON DUPLICATE KEY UPDATE yesterdayWithdrawTodayArrivalCount = VALUES (yesterdayWithdrawTodayArrivalCount), yesterdayWithdrawTodayArrivalCentSum = VALUES (yesterdayWithdrawTodayArrivalCentSum)");
		return sb.toString();
	}

	/**
	 * 昨天提现今天到账
	 * 
	 * @param sb
	 * @param startDateStr
	 * @param endDateStr
	 * @return
	 */
	public static StringBuffer getStatsYesterdayWithdrawTodayArrivalSql(StringBuffer sb, String startDateStr, String endDateStr) {

		sb.append(
				"SELECT z.date, z.registPlatform, ifnull(s.yesterdayWithdrawTodayArrivalCount, 0) yesterdayWithdrawTodayArrivalCount, ifnull(s.yesterdayWithdrawTodayArrivalCentSum, 0) yesterdayWithdrawTodayArrivalCentSum FROM ( SELECT DATE_FORMAT(x.insert_time, '%Y-%m-%d') date, y.id registPlatform FROM dateday x, type_regist_platform y where 1 = 1 ");
		if (StringUtils.isNotBlank(startDateStr)) {
			sb.append(" and DATE_FORMAT(x.insert_time, '%Y-%m-%d') >= '");
			sb.append(startDateStr);
			sb.append("'");
		}
		if (StringUtils.isNotBlank(endDateStr)) {
			sb.append(" and DATE_FORMAT(x.insert_time, '%Y-%m-%d') <= '");
			sb.append(endDateStr);
			sb.append("'");
		}

		sb.append(
				") z LEFT JOIN ( SELECT DATE_FORMAT(a.check_time, '%Y-%m-%d') date, b.regist_platform registPlatform, count(a.id) yesterdayWithdrawTodayArrivalCount, COUNT(DISTINCT a.users_id) sameDaySuccessWithdrawCentSum, "
						+ "sum(a.money) yesterdayWithdrawTodayArrivalCentSum FROM tx_record a LEFT JOIN users b ON a.users_id = b.id WHERE a. STATUS = 1 and DATE_FORMAT(DATE_ADD(a.insert_time,INTERVAL 1 day) , '%Y-%m-%d') = DATE_FORMAT(a.check_time, '%Y-%m-%d')");
		if (StringUtils.isNotBlank(startDateStr)) {
			sb.append(" and DATE_FORMAT(a.check_time, '%Y-%m-%d') >= '");
			sb.append(startDateStr);
			sb.append("'");
		}
		if (StringUtils.isNotBlank(endDateStr)) {
			sb.append(" and DATE_FORMAT(a.check_time, '%Y-%m-%d') <= '");
			sb.append(endDateStr);
			sb.append("'");
		}

		sb.append("GROUP BY DATE_FORMAT(a.check_time, '%Y-%m-%d'), b.regist_platform ) s ON z.date = s.date AND z.registPlatform = s.registPlatform ");

		return sb;
	}

	/**
	 * 非今天提现但今天到账笔数插入
	 * 
	 * @param startDateStr
	 * @param endDateStr
	 * @return
	 */
	public static String getStatsWithdrawNotSameDayTodayArrivalInsertSql(String startDateStr, String endDateStr) {
		StringBuffer sb = new StringBuffer();

		sb.append("INSERT INTO back_stats_operate_day ( date, registPlatform, notSameDayWithdrawTodayArrivalCount, notSameDayWithdrawTodayArrivalCentSum ) ");
		getStatsWithdrawNotSameDayTodayArrivalSql(sb, startDateStr, endDateStr);
		sb.append("ON DUPLICATE KEY UPDATE notSameDayWithdrawTodayArrivalCount = VALUES (notSameDayWithdrawTodayArrivalCount), notSameDayWithdrawTodayArrivalCentSum = VALUES (notSameDayWithdrawTodayArrivalCentSum)");
		return sb.toString();
	}

	/**
	 * 非今天提现但今天到账笔数
	 * 
	 * @param sb
	 * @param startDateStr
	 * @param endDateStr
	 * @return
	 */
	public static StringBuffer getStatsWithdrawNotSameDayTodayArrivalSql(StringBuffer sb, String startDateStr, String endDateStr) {

		sb.append(
				"SELECT z.date, z.registPlatform, ifnull(s.notSameDayWithdrawTodayArrivalCount, 0) notSameDayWithdrawTodayArrivalCount, ifnull(s.notSameDayWithdrawTodayArrivalCentSum, 0) notSameDayWithdrawTodayArrivalCentSum FROM ( SELECT DATE_FORMAT(x.insert_time, '%Y-%m-%d') date, y.id registPlatform FROM dateday x, type_regist_platform y where 1 = 1 ");
		if (StringUtils.isNotBlank(startDateStr)) {
			sb.append(" and DATE_FORMAT(x.insert_time, '%Y-%m-%d') >= '");
			sb.append(startDateStr);
			sb.append("'");
		}
		if (StringUtils.isNotBlank(endDateStr)) {
			sb.append(" and DATE_FORMAT(x.insert_time, '%Y-%m-%d') <= '");
			sb.append(endDateStr);
			sb.append("'");
		}

		sb.append(
				") z LEFT JOIN ( SELECT DATE_FORMAT(a.check_time, '%Y-%m-%d') date, b.regist_platform registPlatform, count(a.id) notSameDayWithdrawTodayArrivalCount, COUNT(DISTINCT a.users_id) userSum, "
						+ "sum(a.money) notSameDayWithdrawTodayArrivalCentSum FROM tx_record a LEFT JOIN users b ON a.users_id = b.id WHERE a. STATUS = 1 and DATE_FORMAT(a.insert_time , '%Y-%m-%d') != DATE_FORMAT(a.check_time, '%Y-%m-%d')");
		if (StringUtils.isNotBlank(startDateStr)) {
			sb.append(" and DATE_FORMAT(a.check_time, '%Y-%m-%d') >= '");
			sb.append(startDateStr);
			sb.append("'");
		}
		if (StringUtils.isNotBlank(endDateStr)) {
			sb.append(" and DATE_FORMAT(a.check_time, '%Y-%m-%d') <= '");
			sb.append(endDateStr);
			sb.append("'");
		}

		sb.append("GROUP BY DATE_FORMAT(a.check_time, '%Y-%m-%d'), b.regist_platform ) s ON z.date = s.date AND z.registPlatform = s.registPlatform ");

		return sb;
	}

	/**
	 * 付息(还款)统计插入
	 * 
	 * @param startDateStr
	 * @param endDateStr
	 * @return
	 */
	public static String getStatsRepaymentInsertSql(String startDateStr, String endDateStr) {
		StringBuffer sb = new StringBuffer();

		sb.append("INSERT INTO back_stats_operate_day ( date, registPlatform, repayPrincipalCentSum, repayInterestCentSum, repayAlreadyAllCentSum ) ");
		getStatsRepaymentSql(sb, startDateStr, endDateStr);

		sb.append(
				"ON DUPLICATE KEY UPDATE repayPrincipalCentSum = VALUES (repayPrincipalCentSum), repayInterestCentSum = VALUES (repayInterestCentSum), repayAlreadyAllCentSum = VALUES (repayAlreadyAllCentSum)");

		return sb.toString();
	}

	/**
	 * 付息(还款)统计
	 * 
	 * @param sb
	 * @param startDateStr
	 * @param endDateStr
	 * @return
	 */
	public static StringBuffer getStatsRepaymentSql(StringBuffer sb, String startDateStr, String endDateStr) {

		sb.append(
				"SELECT z.date, z.registPlatform, IFNULL(s.repayPrincipalCentSum, 0) repayPrincipalCentSum, IFNULL(s.repayInterestCentSum, 0) repayInterestCentSum, IFNULL(s.repayAlreadyAllCentSum, 0) repayAlreadyAllCentSum FROM ( ");
		sb.append("SELECT DATE_FORMAT(x.insert_time, '%Y-%m-%d') date, y.id registPlatform FROM dateday x, type_regist_platform y where 1 = 1 ");
		if (StringUtils.isNotBlank(startDateStr)) {
			sb.append(" and DATE_FORMAT(x.insert_time, '%Y-%m-%d') >= '");
			sb.append(startDateStr);
			sb.append("'");
		}
		if (StringUtils.isNotBlank(endDateStr)) {
			sb.append(" and DATE_FORMAT(x.insert_time, '%Y-%m-%d') <= '");
			sb.append(endDateStr);
			sb.append("'");
		}

		sb.append(") z LEFT JOIN ( SELECT DATE_FORMAT(a.return_time, '%Y-%m-%d') date, b.regist_platform, sum(a.pay_money) repayPrincipalCentSum, sum(a.pay_interest) repayInterestCentSum, "
				+ "sum(a.already_pay) repayAlreadyAllCentSum FROM interest_details a LEFT JOIN users b ON a.users_id = b.id WHERE a. STATUS = 2 ");
		if (StringUtils.isNotBlank(startDateStr)) {
			sb.append(" and DATE_FORMAT(a.return_time, '%Y-%m-%d') >= '");
			sb.append(startDateStr);
			sb.append("'");
		}
		if (StringUtils.isNotBlank(endDateStr)) {
			sb.append(" and DATE_FORMAT(a.return_time, '%Y-%m-%d') <= '");
			sb.append(endDateStr);
			sb.append("'");
		}

		sb.append("GROUP BY DATE_FORMAT(a.return_time, '%Y-%m-%d'), b.regist_platform ) s ON z.date = s.date AND z.registPlatform = s.regist_platform ");

		return sb;
	}
}
