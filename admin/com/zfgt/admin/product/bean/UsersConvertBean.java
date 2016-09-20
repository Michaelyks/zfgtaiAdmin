package com.zfgt.admin.product.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.zfgt.admin.product.dao.InvestorsDAO;
import com.zfgt.common.util.DESEncrypt;
import com.zfgt.common.util.ObjectUtil;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.BackStatsOperateDay;
import com.zfgt.orm.Operation;
import com.zfgt.orm.Users;
import com.zfgt.orm.UsersConvert;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * 用户转换
 * 
 * @author 曾礼强 2015年6月24日 09:12:58
 */
@Service
public class UsersConvertBean {
	@Resource
	InvestorsDAO dao;

	/**
	 * 根据日期统计用户注册并且投资人数及投资金额
	 * 
	 * @param insertTime
	 *            注册时间
	 * @param registPlatform
	 *            注册平台
	 * @return
	 */

	public PageUtil<UsersConvert> findUsersCountByDate(PageUtil pageUtil, String insertTime, String registPlatform) throws Exception {
		List<Object> list = new ArrayList<Object>();
		StringBuffer buffer = new StringBuffer();
		buffer.append(" select us.date as date, sum(regUserSum ) as regUserSum,sum(investCount ) as investCount,sum(investUserSum ) as investUserSum ,sum(investCentSum )as  investCentSum , ");
		buffer.append(" sum(bindBankUserSum ) as bindBankUserSum ,sum(firstInvestUserSum ) as firstInvestUserSum ,sum(reInvestUserSum ) as reInvestUserSum  ,sum(reInvestCount ) as reInvestCount ,");
		buffer.append(
				"  sum(regInvestUserSum )as  regInvestUserSum , sum(regInvestCentSum ) as regInvestCentSum  ,sum(avgRechargeCentSum ) as avgRechargeCentSum  ,  sum(regInvestRate ) as regInvestRate  , ");
		buffer.append("  sum(reInvestRate ) as reInvestRate ");
		buffer.append(" FROM back_stats_operate_day us WHERE 1=1 ");
		if (!QwyUtil.isNullAndEmpty(registPlatform))
			buffer.append("  and us.registPlatform = " + registPlatform);

		// 发布时间
		if (!QwyUtil.isNullAndEmpty(insertTime)) {
			String[] time = QwyUtil.splitTime(insertTime);
			if (time.length > 1) {
				buffer.append(" AND us.date >= ? ");
				list.add(QwyUtil.fmMMddyyyy.parse(time[0]));
				buffer.append(" AND us.date <= ? ");
				list.add(QwyUtil.fmMMddyyyy.parse(time[1]));
			} else {
				buffer.append(" AND us.date >= ? ");
				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 00:00:00"));
				buffer.append(" AND us.date <= ? ");
				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 23:59:59"));
			}
		}
		buffer.append(" GROUP BY DATE_FORMAT(us.date, '%Y-%m-%d')  ");
		buffer.append(" ORDER BY us.date DESC ");
		StringBuffer bufferCount = new StringBuffer();
		bufferCount.append(" SELECT COUNT(*)  ");
		bufferCount.append(" FROM (");
		bufferCount.append(buffer);
		bufferCount.append(") t");
		pageUtil = dao.getBySqlAndSqlCount(pageUtil, buffer.toString(), bufferCount.toString(), list.toArray());
		List<UsersConvert> platUsers = toUserConvert(pageUtil.getList());
		pageUtil.setList(platUsers);
		return pageUtil;
	}

	/**
	 * 转换list
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	private List<UsersConvert> toUserConvert(List<Object[]> list) throws Exception {
		List<UsersConvert> usersConverts = new ArrayList<UsersConvert>();
		if (!QwyUtil.isNullAndEmpty(list)) {
			for (Object[] obj : list) {
				UsersConvert convert = new UsersConvert();
				// 0日期 1注册人数 2投资人次 3投资人数 4投资金额 5 绑定人数 6 首投人数 7复投人数 8复投次数 9注册投资人数
				// 10注册投资金额
				convert.setDate(QwyUtil.fmyyyyMMdd.format(obj[0]));
				// 注册人数
				convert.setReuserscount(!QwyUtil.isNullAndEmpty(obj[1]) ? obj[1] + "" : "0");
				// 投资的次数
				convert.setInscount(!QwyUtil.isNullAndEmpty(obj[2]) ? obj[2] + "" : "0");
				// 投资的人数
				convert.setInsusercount(!QwyUtil.isNullAndEmpty(obj[3]) ? obj[3] + "" : "0");
				// 投资金额
				convert.setAllcopies(!QwyUtil.isNullAndEmpty(obj[4]) ? QwyUtil.calcNumber(obj[4], 100, "/", 2) + "" : "0");
				// 绑卡的人数
				convert.setBindcount(!QwyUtil.isNullAndEmpty(obj[5]) ? obj[5] + "" : "0");
				// 首投人数
				convert.setStinsrs(!QwyUtil.isNullAndEmpty(obj[6]) ? obj[6] + "" : "0");
				// 复投资人数
				convert.setFtinsrs(!QwyUtil.isNullAndEmpty(obj[7]) ? obj[7] + "" : "0");

				// 复投资的次数
				convert.setFtinscs(!QwyUtil.isNullAndEmpty(obj[8]) ? obj[8] + "" : "0");
				// 注册并投资的人数
				convert.setReginscount(!QwyUtil.isNullAndEmpty(obj[9]) ? obj[9] + "" : "0");
				// 注册投资购买的金额
				convert.setRegcopies(!QwyUtil.isNullAndEmpty(obj[10]) ? QwyUtil.calcNumber(obj[10], 100, "/", 2) + "" : "0");

				// 人均投资
				if (convert.getInsusercount().equals("0") || convert.getAllcopies().equals("0")) {
					convert.setRjtz("0.00");
				} else {
					convert.setRjtz(QwyUtil.calcNumber(convert.getAllcopies(), convert.getInsusercount(), "/", 2).toString());
				}
				// 转换率
				if (convert.getStinsrs().equals("0") || convert.getReuserscount().equals("0")) {
					convert.setZhl("0");
				} else {
					BigDecimal zhl = QwyUtil.calcNumber(convert.getStinsrs(), convert.getReuserscount(), "/");
					convert.setZhl(QwyUtil.calcNumber(zhl, 0.01, "/", 2).toString());
				}
				if (convert.getInsusercount().equals("0") || convert.getFtinsrs().equals("0")) {
					convert.setFtl("0");
				} else {
					BigDecimal zhl = QwyUtil.calcNumber(convert.getFtinsrs(), convert.getInsusercount(), "/");
					convert.setFtl(QwyUtil.calcNumber(zhl, 0.01, "/", 2).toString());
				}
				usersConverts.add(convert);
			}
		}
		return usersConverts;
	}

	public void createQueryListSql(StringBuffer queryListSql, String registPlatform, String beginDate, String endDate) {
		queryListSql.append("select * from(");
		queryListSql.append(" SELECT dd.insert_time AS rq,zcrstab.registerCount AS zcrs,tzrctab.payCount AS tzrc,tzrstab.payCount AS tzrs,tzjetab.sumMoney AS tzje,");
		queryListSql.append(
				" bdrstab.accountCount AS bdrs,strstab.shoutouCount AS strs,ftrstab.futouCount AS ftrs,ftcstab.futouCount AS ftcs,zctzrstab.registerCount AS zctzrs,zctzjetab.registerCount AS zctzje");
		queryListSql.append(" FROM  dateday dd LEFT JOIN");
		// 用户注册
		queryListSql.append(" (");
		queryListSql.append(" SELECT DATE_FORMAT(insert_time,'%Y-%m-%d') AS registerDate, COUNT(*) AS registerCount FROM users where 1=1 ");
		if (!QwyUtil.isNullAndEmpty(registPlatform)) {
			queryListSql.append(" and REGIST_PLATFORM='" + registPlatform + "'");
		}
		if (!QwyUtil.isNullAndEmpty(beginDate) && !QwyUtil.isNullAndEmpty(endDate)) {
			queryListSql.append(" and DATE_FORMAT(insert_time,'%Y-%m-%d') BETWEEN DATE_FORMAT('" + beginDate + "','%Y-%m-%d')  AND DATE_FORMAT('" + endDate + "','%Y-%m-%d')");
		} else {

			queryListSql.append(" AND DATE_ADD( DATE_FORMAT(insert_time  , '%Y-%m-%d') ,INTERVAL 31 DAY)>'" + QwyUtil.fmyyyyMMdd.format(new Date()) + "'");
			queryListSql.append(" AND  DATE_FORMAT(insert_time  , '%Y-%m-%d') <'" + QwyUtil.fmyyyyMMdd.format(new Date()) + "'");
		}
		queryListSql.append(" GROUP BY DATE_FORMAT(insert_time,'%Y-%m-%d')) zcrstab ON dd.insert_time=zcrstab.registerDate");
		// 投资人次
		queryListSql.append(" LEFT JOIN");
		queryListSql.append(" (");
		queryListSql.append(" SELECT DATE_FORMAT(pay_time,'%Y-%m-%d') AS payDate, COUNT(*) AS payCount FROM investors LEFT JOIN users ON investors.USERS_ID=USERS.ID");
		queryListSql.append(" WHERE investor_status IN ('1','2','3')");
		if (!QwyUtil.isNullAndEmpty(registPlatform)) {
			queryListSql.append(" and USERS.REGIST_PLATFORM='" + registPlatform + "'");
		}
		if (!QwyUtil.isNullAndEmpty(beginDate) && !QwyUtil.isNullAndEmpty(endDate)) {
			queryListSql.append(" and DATE_FORMAT(investors.pay_time,'%Y-%m-%d') BETWEEN DATE_FORMAT('" + beginDate + "','%Y-%m-%d')  AND DATE_FORMAT('" + endDate + "','%Y-%m-%d')");
		}

		else {
			queryListSql.append(" AND DATE_ADD( DATE_FORMAT(investors.pay_time  , '%Y-%m-%d') ,INTERVAL 31 DAY)>'" + QwyUtil.fmyyyyMMdd.format(new Date()) + "'");
			queryListSql.append(" AND  DATE_FORMAT(investors.pay_time  , '%Y-%m-%d') <'" + QwyUtil.fmyyyyMMdd.format(new Date()) + "'");
		}

		queryListSql.append(" GROUP BY DATE_FORMAT(pay_time,'%Y-%m-%d')");
		queryListSql.append(" ) tzrctab ON dd.insert_time=tzrctab.payDate");
		// 投资人数
		queryListSql.append(" LEFT JOIN");
		queryListSql.append(" (");
		queryListSql.append(" SELECT payDate,COUNT(*) AS payCount FROM");
		queryListSql.append(" (");
		queryListSql
				.append(" SELECT DISTINCT DATE_FORMAT(pay_time,'%Y-%m-%d')AS payDate,users_id FROM investors LEFT JOIN users ON investors.USERS_ID=USERS.ID WHERE investor_status IN ('1','2','3')");
		if (!QwyUtil.isNullAndEmpty(registPlatform)) {
			queryListSql.append(" and USERS.REGIST_PLATFORM='" + registPlatform + "'");
		}
		if (!QwyUtil.isNullAndEmpty(beginDate) && !QwyUtil.isNullAndEmpty(endDate)) {
			queryListSql.append(" and DATE_FORMAT(investors.pay_time,'%Y-%m-%d') BETWEEN DATE_FORMAT('" + beginDate + "','%Y-%m-%d')  AND DATE_FORMAT('" + endDate + "','%Y-%m-%d')");
		} else {
			queryListSql.append(" AND DATE_ADD( DATE_FORMAT(investors.pay_time  , '%Y-%m-%d') ,INTERVAL 31 DAY)>'" + QwyUtil.fmyyyyMMdd.format(new Date()) + "'");
			queryListSql.append(" AND  DATE_FORMAT(investors.pay_time   , '%Y-%m-%d') <'" + QwyUtil.fmyyyyMMdd.format(new Date()) + "'");
		}
		queryListSql.append(" ");
		queryListSql.append(" ) tab1 GROUP BY payDate");
		queryListSql.append(" )tzrstab ON dd.insert_time=tzrstab.payDate");

		// 投资金额
		queryListSql.append(" LEFT JOIN");
		queryListSql.append(" (");
		queryListSql.append(" SELECT DATE_FORMAT(pay_time,'%Y-%m-%d') AS payDate,SUM(in_money) sumMoney FROM investors LEFT JOIN users ON investors.USERS_ID=USERS.ID where 1=1");
		if (!QwyUtil.isNullAndEmpty(registPlatform)) {
			queryListSql.append(" and USERS.REGIST_PLATFORM='" + registPlatform + "'");
		}
		if (!QwyUtil.isNullAndEmpty(beginDate) && !QwyUtil.isNullAndEmpty(endDate)) {
			queryListSql.append(" and DATE_FORMAT(investors.pay_time,'%Y-%m-%d') BETWEEN DATE_FORMAT('" + beginDate + "','%Y-%m-%d')  AND DATE_FORMAT('" + endDate + "','%Y-%m-%d')");
		} else {
			queryListSql.append(" AND DATE_ADD( DATE_FORMAT(investors.pay_time  , '%Y-%m-%d') ,INTERVAL 31 DAY) >'" + QwyUtil.fmyyyyMMdd.format(new Date()) + "'");
			queryListSql.append(" AND  DATE_FORMAT(investors.pay_time    , '%Y-%m-%d') <'" + QwyUtil.fmyyyyMMdd.format(new Date()) + "'");
		}
		queryListSql.append(" and investor_status IN ('1','2','3')");
		queryListSql.append(" GROUP BY DATE_FORMAT(pay_time,'%Y-%m-%d')");
		queryListSql.append(" )tzjetab ON dd.insert_time=tzjetab.payDate");
		// queryListSql.append(" LEFT JOIN");
		// queryListSql.append(" (");
		// queryListSql.append(" SELECT visitDate,COUNT(*) visitCount
		// FROM(SELECT DISTINCT DATE_FORMAT(insert_time,'%Y-%m-%d') AS
		// visitDate,imei FROM activity)tab7 GROUP BY visitDate");
		// queryListSql.append(" )fwrstab ON dd.insert_time=fwrstab.visitDate");
		// 绑定人数
		queryListSql.append(" LEFT JOIN");
		queryListSql.append(" (");
		queryListSql.append(" SELECT DATE_FORMAT(account.insert_time,'%Y-%m-%d') accountDate,COUNT(*) accountCount FROM account LEFT JOIN users ON account.USERS_ID=USERS.ID where STATUS='0'");
		if (!QwyUtil.isNullAndEmpty(registPlatform)) {
			queryListSql.append(" and USERS.REGIST_PLATFORM='" + registPlatform + "'");
		}
		if (!QwyUtil.isNullAndEmpty(beginDate) && !QwyUtil.isNullAndEmpty(endDate)) {
			queryListSql.append(" and DATE_FORMAT(account.insert_time,'%Y-%m-%d') BETWEEN DATE_FORMAT('" + beginDate + "','%Y-%m-%d')  AND DATE_FORMAT('" + endDate + "','%Y-%m-%d')");
		} else {
			queryListSql.append(" AND DATE_ADD( DATE_FORMAT(account.insert_time  , '%Y-%m-%d') ,INTERVAL 31 DAY) >'" + QwyUtil.fmyyyyMMdd.format(new Date()) + "'");
			queryListSql.append(" AND  DATE_FORMAT(account.insert_time, '%Y-%m-%d') <'" + QwyUtil.fmyyyyMMdd.format(new Date()) + "'");
		}
		queryListSql.append(" GROUP BY DATE_FORMAT(account.insert_time,'%Y-%m-%d')");
		queryListSql.append(" )bdrstab ON dd.insert_time=bdrstab.accountDate");
		// 首投人数
		queryListSql.append(" LEFT JOIN");
		queryListSql.append(" (");
		queryListSql.append(" SELECT shoutouDate,COUNT(*) AS shoutouCount FROM (");
		queryListSql.append(" SELECT DATE_FORMAT(investors1.pay_time,'%Y-%m-%d') AS shoutouDate,investors1.users_id FROM");
		queryListSql.append(" (SELECT investors.* FROM investors LEFT JOIN users ON investors.users_id=users.id WHERE investor_status IN ('1','2','3')");
		if (!QwyUtil.isNullAndEmpty(registPlatform)) {
			queryListSql.append(" and users.regist_platform='" + registPlatform + "'");
		}
		if (!QwyUtil.isNullAndEmpty(beginDate) && !QwyUtil.isNullAndEmpty(endDate)) {
			queryListSql.append(" and DATE_FORMAT(investors.pay_time,'%Y-%m-%d') BETWEEN DATE_FORMAT('" + beginDate + "','%Y-%m-%d')  AND DATE_FORMAT('" + endDate + "','%Y-%m-%d')");
		} else {
			queryListSql.append(" AND DATE_ADD( DATE_FORMAT(investors.pay_time  , '%Y-%m-%d') ,INTERVAL 31 DAY)  >'" + QwyUtil.fmyyyyMMdd.format(new Date()) + "'");
			queryListSql.append(" AND  DATE_FORMAT(investors.pay_time, '%Y-%m-%d') <'" + QwyUtil.fmyyyyMMdd.format(new Date()) + "'");
		}
		queryListSql.append(" ) investors1");
		queryListSql.append(" INNER JOIN");
		queryListSql.append(" (");
		queryListSql.append(" SELECT MIN(t.pay_time) minDate,users_id FROM investors t WHERE t.investor_status IN ('1','2','3') GROUP BY t.users_id");
		queryListSql.append(" ) investors2 ON investors1.users_id=investors2.users_id and investors1.pay_time=minDate GROUP BY investors1.users_id");
		queryListSql.append(" ) tab4 GROUP BY shoutouDate");
		queryListSql.append(" )strstab ON dd.insert_time=strstab.shoutouDate");
		queryListSql.append(" LEFT JOIN");
		// 复投人数
		queryListSql.append(" (");
		queryListSql.append(" SELECT futouDate,COUNT(*) AS futouCount FROM");
		queryListSql.append(" (");
		queryListSql.append(" SELECT DISTINCT DATE_FORMAT(investors1.pay_time,'%Y-%m-%d') AS futouDate,investors1.users_id FROM");
		queryListSql.append(" (SELECT investors.* FROM investors LEFT JOIN users ON investors.users_id=users.id WHERE investors.investor_status IN ('1','2','3')");
		if (!QwyUtil.isNullAndEmpty(registPlatform)) {
			queryListSql.append(" and users.regist_platform='" + registPlatform + "'");
		}
		if (!QwyUtil.isNullAndEmpty(beginDate) && !QwyUtil.isNullAndEmpty(endDate)) {
			queryListSql.append(" and DATE_FORMAT(investors.pay_time,'%Y-%m-%d') BETWEEN DATE_FORMAT('" + beginDate + "','%Y-%m-%d')  AND DATE_FORMAT('" + endDate + "','%Y-%m-%d')");
		} else {
			queryListSql.append(" AND DATE_ADD( DATE_FORMAT(investors.pay_time  , '%Y-%m-%d') ,INTERVAL 31 DAY) >'" + QwyUtil.fmyyyyMMdd.format(new Date()) + "'");
			queryListSql.append(" AND  DATE_FORMAT(investors.pay_time, '%Y-%m-%d') <'" + QwyUtil.fmyyyyMMdd.format(new Date()) + "'");
		}
		queryListSql.append(" ) investors1");
		queryListSql.append(" INNER JOIN");
		queryListSql.append(" (");
		queryListSql.append(" SELECT MIN(t.pay_time) minDate,users_id FROM investors t WHERE t.investor_status IN ('1','2','3') GROUP BY t.users_id");
		queryListSql.append(" ) investors2 ON investors1.users_id=investors2.users_id");
		queryListSql.append(" where investors1.pay_time>minDate");
		queryListSql.append(" ) tab5 GROUP BY futouDate");
		queryListSql.append(" )ftrstab ON dd.insert_time=ftrstab.futouDate");
		// 复投次数
		queryListSql.append(" LEFT JOIN");
		queryListSql.append(" (");
		queryListSql.append(" SELECT futouDate,COUNT(*) AS futouCount FROM");
		queryListSql.append("(");
		queryListSql.append(" SELECT DATE_FORMAT(investors1.pay_time,'%Y-%m-%d') AS futouDate,investors1.users_id FROM");
		queryListSql.append(" (SELECT investors.* FROM investors LEFT JOIN users ON investors.users_id=users.id WHERE 1=1");
		if (!QwyUtil.isNullAndEmpty(registPlatform)) {
			queryListSql.append(" and users.regist_platform='" + registPlatform + "'");
		}
		if (!QwyUtil.isNullAndEmpty(beginDate) && !QwyUtil.isNullAndEmpty(endDate)) {
			queryListSql.append(" and DATE_FORMAT(investors.pay_time,'%Y-%m-%d') BETWEEN DATE_FORMAT('" + beginDate + "','%Y-%m-%d')  AND DATE_FORMAT('" + endDate + "','%Y-%m-%d')");
		} else {
			queryListSql.append(" AND DATE_ADD( DATE_FORMAT(investors.pay_time  , '%Y-%m-%d') ,INTERVAL 31 DAY)  >'" + QwyUtil.fmyyyyMMdd.format(new Date()) + "'");
			queryListSql.append(" AND  DATE_FORMAT(investors.pay_time, '%Y-%m-%d') <'" + QwyUtil.fmyyyyMMdd.format(new Date()) + "'");
		}
		queryListSql.append(" ) investors1");
		queryListSql.append(" INNER JOIN");
		queryListSql.append(" (");
		queryListSql.append(" SELECT MIN(t.pay_time) minDate,users_id FROM investors t WHERE t.investor_status IN ('1','2','3') GROUP BY t.users_id");
		queryListSql.append(" ) investors2 ON investors1.users_id=investors2.users_id ");
		queryListSql.append(" WHERE investors1.pay_time>minDate");
		queryListSql.append(" ) tab5 GROUP BY futouDate");
		queryListSql.append(" )ftcstab ON dd.insert_time=ftcstab.futouDate");
		// 注册投资人数
		queryListSql.append(" LEFT JOIN");
		queryListSql.append(" (");
		queryListSql.append(" SELECT registerDate,COUNT(*) registerCount FROM(");
		queryListSql.append(" SELECT DISTINCT DATE_FORMAT(u.insert_time,'%Y-%m-%d') AS registerDate,u.id usersId");
		queryListSql.append(
				" FROM users u INNER JOIN investors i ON u.id=i.users_id AND DATE_FORMAT(u.insert_time,'%Y-%m-%d')=DATE_FORMAT(i.pay_time,'%Y-%m-%d') WHERE i.investor_status IN ('1','2','3')");
		if (!QwyUtil.isNullAndEmpty(registPlatform)) {
			queryListSql.append(" and u.regist_platform='" + registPlatform + "'");
		}
		if (!QwyUtil.isNullAndEmpty(beginDate) && !QwyUtil.isNullAndEmpty(endDate)) {
			queryListSql.append(" and DATE_FORMAT(i.pay_time,'%Y-%m-%d') BETWEEN DATE_FORMAT('" + beginDate + "','%Y-%m-%d')  AND DATE_FORMAT('" + endDate + "','%Y-%m-%d')");
			queryListSql.append(" and DATE_FORMAT(u.insert_time,'%Y-%m-%d') BETWEEN DATE_FORMAT('" + beginDate + "','%Y-%m-%d')  AND DATE_FORMAT('" + endDate + "','%Y-%m-%d')");
		} else {
			queryListSql.append(" AND DATE_ADD( DATE_FORMAT(i.pay_time  , '%Y-%m-%d') ,INTERVAL 31 DAY)  >'" + QwyUtil.fmyyyyMMdd.format(new Date()) + "'");
			queryListSql.append(" AND  DATE_FORMAT(i.pay_time, '%Y-%m-%d') <'" + QwyUtil.fmyyyyMMdd.format(new Date()) + "'");
			queryListSql.append(" AND DATE_ADD( DATE_FORMAT(u.insert_time , '%Y-%m-%d') ,INTERVAL 31 DAY)  >'" + QwyUtil.fmyyyyMMdd.format(new Date()) + "'");
			queryListSql.append(" AND  DATE_FORMAT(u.insert_time , '%Y-%m-%d') <'" + QwyUtil.fmyyyyMMdd.format(new Date()) + "'");
		}
		queryListSql.append(" )tab6");
		queryListSql.append(" GROUP BY registerDate");
		queryListSql.append(" )zctzrstab ON dd.insert_time=zctzrstab.registerDate");
		// 注册投资金额
		queryListSql.append(" LEFT JOIN");
		queryListSql.append(" (");
		queryListSql.append(" SELECT registerDate,SUM(in_money) registerCount FROM(");
		queryListSql.append(" SELECT DATE_FORMAT(u.insert_time,'%Y-%m-%d') AS registerDate,i.in_money in_money");
		queryListSql.append(
				" FROM users u INNER JOIN investors i ON u.id=i.users_id AND DATE_FORMAT(u.insert_time,'%Y-%m-%d')=DATE_FORMAT(i.pay_time,'%Y-%m-%d') WHERE i.investor_status IN ('1','2','3')");
		if (!QwyUtil.isNullAndEmpty(registPlatform)) {
			queryListSql.append(" and u.regist_platform='" + registPlatform + "'");
		}
		if (!QwyUtil.isNullAndEmpty(beginDate) && !QwyUtil.isNullAndEmpty(endDate)) {
			queryListSql.append(" and DATE_FORMAT(i.pay_time,'%Y-%m-%d') BETWEEN DATE_FORMAT('" + beginDate + "','%Y-%m-%d')  AND DATE_FORMAT('" + endDate + "','%Y-%m-%d')");
			queryListSql.append(" and DATE_FORMAT(u.insert_time,'%Y-%m-%d') BETWEEN DATE_FORMAT('" + beginDate + "','%Y-%m-%d')  AND DATE_FORMAT('" + endDate + "','%Y-%m-%d')");
		} else {
			queryListSql.append(" AND DATE_ADD( DATE_FORMAT(i.pay_time  , '%Y-%m-%d') ,INTERVAL 31 DAY)  >'" + QwyUtil.fmyyyyMMdd.format(new Date()) + "'");
			queryListSql.append(" AND  DATE_FORMAT(i.pay_time, '%Y-%m-%d') <'" + QwyUtil.fmyyyyMMdd.format(new Date()) + "'");
			queryListSql.append(" AND DATE_ADD( DATE_FORMAT(u.insert_time , '%Y-%m-%d') ,INTERVAL 31 DAY) >'" + QwyUtil.fmyyyyMMdd.format(new Date()) + "'");
			queryListSql.append(" AND  DATE_FORMAT(u.insert_time , '%Y-%m-%d') <'" + QwyUtil.fmyyyyMMdd.format(new Date()) + "'");
		}
		queryListSql.append(" )tab6");
		queryListSql.append(" GROUP BY registerDate");
		queryListSql.append(" )zctzjetab ON dd.insert_time=zctzjetab.registerDate");
		if (!QwyUtil.isNullAndEmpty(beginDate) && !QwyUtil.isNullAndEmpty(endDate)) {
			queryListSql.append(" where DATE_FORMAT(dd.insert_time,'%Y-%m-%d') BETWEEN DATE_FORMAT('" + beginDate + "','%Y-%m-%d')  AND DATE_FORMAT('" + endDate + "','%Y-%m-%d')");
		}

		else {
			// String time = QwyUtil.fmyyyyMMdd.format(new Date());
			queryListSql.append(" where DATE_ADD( DATE_FORMAT(dd.insert_time  , '%Y-%m-%d') ,INTERVAL 31 DAY)  >'" + QwyUtil.fmyyyyMMdd.format(new Date()) + "'");
			queryListSql.append(" AND  DATE_FORMAT(dd.insert_time , '%Y-%m-%d') <'" + QwyUtil.fmyyyyMMdd.format(new Date()) + "'");
		}
		queryListSql.append(" ORDER BY rq DESC");
		queryListSql.append(")tt");
	}

	public void createQueryCountSql(StringBuffer queryCountSql, String registPlatform, String beginDate, String endDate) {
		queryCountSql.append("select * from(");
		queryCountSql.append(" SELECT");
		// 注册人数
		queryCountSql.append(" (SELECT COUNT(*) AS zhucerenshu FROM users where 1=1");
		if (!QwyUtil.isNullAndEmpty(registPlatform)) {
			queryCountSql.append(" and REGIST_PLATFORM='" + registPlatform + "'");
		}
		if (!QwyUtil.isNullAndEmpty(beginDate) && !QwyUtil.isNullAndEmpty(endDate)) {
			queryCountSql.append(" and DATE_FORMAT(insert_time,'%Y-%m-%d') BETWEEN DATE_FORMAT('" + beginDate + "','%Y-%m-%d')  AND DATE_FORMAT('" + endDate + "','%Y-%m-%d')");
		}
		queryCountSql.append(" ) AS zcrs,");
		// 投资人次
		queryCountSql.append(" (SELECT COUNT(*) touzirenci FROM investors LEFT JOIN users ON investors.USERS_ID=USERS.ID WHERE investor_status IN ('1','2','3')");
		if (!QwyUtil.isNullAndEmpty(registPlatform)) {
			queryCountSql.append(" and users.REGIST_PLATFORM='" + registPlatform + "'");
		}
		if (!QwyUtil.isNullAndEmpty(beginDate) && !QwyUtil.isNullAndEmpty(endDate)) {
			queryCountSql.append(" and DATE_FORMAT(investors.pay_time,'%Y-%m-%d') BETWEEN DATE_FORMAT('" + beginDate + "','%Y-%m-%d')  AND DATE_FORMAT('" + endDate + "','%Y-%m-%d')");
		}
		queryCountSql.append(") AS tzrc,");
		// 投资人数
		queryCountSql.append(" (SELECT COUNT(*) AS touzirenshu FROM (SELECT DISTINCT users_id FROM investors LEFT JOIN users ON investors.USERS_ID=USERS.ID WHERE investor_status IN ('1','2','3')");
		if (!QwyUtil.isNullAndEmpty(registPlatform)) {
			queryCountSql.append(" and users.REGIST_PLATFORM='" + registPlatform + "'");
		}
		if (!QwyUtil.isNullAndEmpty(beginDate) && !QwyUtil.isNullAndEmpty(endDate)) {
			queryCountSql.append(" and DATE_FORMAT(investors.pay_time,'%Y-%m-%d') BETWEEN DATE_FORMAT('" + beginDate + "','%Y-%m-%d')  AND DATE_FORMAT('" + endDate + "','%Y-%m-%d')");
		}
		queryCountSql.append(" )tab1) AS tzrs,");
		// 投资金额
		queryCountSql.append(" (SELECT SUM(in_money) touzijine FROM investors LEFT JOIN users ON investors.USERS_ID=USERS.ID WHERE investor_status IN ('1','2','3')");
		if (!QwyUtil.isNullAndEmpty(registPlatform)) {
			queryCountSql.append(" and users.REGIST_PLATFORM='" + registPlatform + "'");
		}
		if (!QwyUtil.isNullAndEmpty(beginDate) && !QwyUtil.isNullAndEmpty(endDate)) {
			queryCountSql.append(" and DATE_FORMAT(investors.pay_time,'%Y-%m-%d') BETWEEN DATE_FORMAT('" + beginDate + "','%Y-%m-%d')  AND DATE_FORMAT('" + endDate + "','%Y-%m-%d')");
		}
		queryCountSql.append(" ) AS tzje,");
		// 绑定人数
		queryCountSql.append(" (SELECT COUNT(*) bangdingrenshu FROM account LEFT JOIN users ON account.USERS_ID=USERS.ID where STATUS='0'");
		if (!QwyUtil.isNullAndEmpty(registPlatform)) {
			queryCountSql.append(" and users.REGIST_PLATFORM='" + registPlatform + "'");
		}
		if (!QwyUtil.isNullAndEmpty(beginDate) && !QwyUtil.isNullAndEmpty(endDate)) {
			queryCountSql.append(" and DATE_FORMAT(account.insert_time,'%Y-%m-%d') BETWEEN DATE_FORMAT('" + beginDate + "','%Y-%m-%d')  AND DATE_FORMAT('" + endDate + "','%Y-%m-%d')");
		}
		queryCountSql.append(" ) AS bdrs,");
		// 首投人数
		queryCountSql.append(" (select count(*) shoutourenshu from (SELECT COUNT(*) AS shoutouCount FROM");
		queryCountSql.append(" (SELECT investors.* FROM investors LEFT JOIN users ON investors.users_id=users.id WHERE 1=1");
		if (!QwyUtil.isNullAndEmpty(registPlatform)) {
			queryCountSql.append(" and users.regist_platform='" + registPlatform + "'");
		}
		if (!QwyUtil.isNullAndEmpty(beginDate) && !QwyUtil.isNullAndEmpty(endDate)) {
			queryCountSql.append(" and DATE_FORMAT(investors.pay_time,'%Y-%m-%d') BETWEEN DATE_FORMAT('" + beginDate + "','%Y-%m-%d')  AND DATE_FORMAT('" + endDate + "','%Y-%m-%d')");
		}
		queryCountSql.append(" ) investors1");
		queryCountSql.append(" INNER JOIN");
		queryCountSql.append(" (");
		queryCountSql.append(" SELECT MIN(t.pay_time) minDate,users_id FROM investors t WHERE t.investor_status IN ('1','2','3') GROUP BY t.users_id");
		queryCountSql.append(" ) investors2 ON investors1.users_id=investors2.users_id where investors1.pay_time=minDate GROUP BY investors1.users_id)tab");
		queryCountSql.append(" ) AS strs,");
		// 复投人数
		queryCountSql.append(" (SELECT COUNT(*) AS futourenshu FROM");
		queryCountSql.append(" (");
		queryCountSql.append(" SELECT DISTINCT investors1.users_id FROM");
		queryCountSql.append(" (SELECT investors.* FROM investors LEFT JOIN users ON investors.users_id=users.id WHERE investors.investor_status IN ('1','2','3')");
		if (!QwyUtil.isNullAndEmpty(registPlatform)) {
			queryCountSql.append(" and users.regist_platform='" + registPlatform + "'");
		}
		if (!QwyUtil.isNullAndEmpty(beginDate) && !QwyUtil.isNullAndEmpty(endDate)) {
			queryCountSql.append(" and DATE_FORMAT(investors.pay_time,'%Y-%m-%d') BETWEEN DATE_FORMAT('" + beginDate + "','%Y-%m-%d')  AND DATE_FORMAT('" + endDate + "','%Y-%m-%d')");
		}
		queryCountSql.append(" ) investors1");
		queryCountSql.append(" LEFT JOIN");
		queryCountSql.append(" (");
		queryCountSql.append(" SELECT MIN(t.pay_time) minDate,users_id FROM investors t WHERE t.investor_status IN ('1','2','3') GROUP BY t.users_id");
		queryCountSql.append(" ) investors2 ON investors1.users_id=investors2.users_id");
		queryCountSql.append(" where investors1.pay_time>minDate");
		queryCountSql.append(" ) tab5) AS ftrs,");
		// 复投次数
		queryCountSql.append(" (SELECT COUNT(*) AS futoucishu FROM");
		queryCountSql.append(" (SELECT investors.* FROM investors LEFT JOIN users ON investors.users_id=users.id WHERE 1=1");
		if (!QwyUtil.isNullAndEmpty(registPlatform)) {
			queryCountSql.append(" and users.regist_platform='" + registPlatform + "'");
		}
		if (!QwyUtil.isNullAndEmpty(beginDate) && !QwyUtil.isNullAndEmpty(endDate)) {
			queryCountSql.append(" and DATE_FORMAT(investors.pay_time,'%Y-%m-%d') BETWEEN DATE_FORMAT('" + beginDate + "','%Y-%m-%d')  AND DATE_FORMAT('" + endDate + "','%Y-%m-%d')");
		}
		queryCountSql.append(" ) investors1");
		queryCountSql.append(" LEFT JOIN");
		queryCountSql.append(" (");
		queryCountSql.append(" SELECT MIN(t.pay_time) minDate,users_id FROM investors t WHERE t.investor_status IN ('1','2','3') GROUP BY t.users_id");
		queryCountSql.append(" ) investors2 ON investors1.users_id=investors2.users_id");
		queryCountSql.append(" where investors1.pay_time>minDate AND investors1.investor_status IN ('1','2','3')");
		queryCountSql.append(" ) AS ftcs,");
		// 注册投资人数
		queryCountSql.append(" (SELECT COUNT(*) zhucetouzirenshu FROM( ");
		queryCountSql.append(" SELECT DISTINCT DATE_FORMAT(u.insert_time,'%Y-%m-%d') AS registerDate,u.id usersId");
		queryCountSql.append(
				" FROM users u INNER JOIN investors i ON u.id=i.users_id AND DATE_FORMAT(u.insert_time,'%Y-%m-%d')=DATE_FORMAT(i.pay_time,'%Y-%m-%d') WHERE i.investor_status IN ('1','2','3')");
		if (!QwyUtil.isNullAndEmpty(registPlatform)) {
			queryCountSql.append(" and u.regist_platform='" + registPlatform + "'");
		}
		if (!QwyUtil.isNullAndEmpty(beginDate) && !QwyUtil.isNullAndEmpty(endDate)) {
			queryCountSql.append(" and DATE_FORMAT(i.pay_time,'%Y-%m-%d') BETWEEN DATE_FORMAT('" + beginDate + "','%Y-%m-%d')  AND DATE_FORMAT('" + endDate + "','%Y-%m-%d')");
			queryCountSql.append(" and DATE_FORMAT(u.insert_time,'%Y-%m-%d') BETWEEN DATE_FORMAT('" + beginDate + "','%Y-%m-%d')  AND DATE_FORMAT('" + endDate + "','%Y-%m-%d')");
		}
		queryCountSql.append(" )tab6) AS zctzrs,");
		// 注册投资金额
		queryCountSql.append(" (SELECT SUM(in_money) zhucetouzijine");
		queryCountSql.append(
				" FROM users u INNER JOIN investors i ON u.id=i.users_id AND DATE_FORMAT(u.insert_time,'%Y-%m-%d')=DATE_FORMAT(i.pay_time,'%Y-%m-%d') WHERE i.investor_status IN ('1','2','3')");
		if (!QwyUtil.isNullAndEmpty(registPlatform)) {
			queryCountSql.append(" and u.regist_platform='" + registPlatform + "'");
		}
		if (!QwyUtil.isNullAndEmpty(beginDate) && !QwyUtil.isNullAndEmpty(endDate)) {
			queryCountSql.append(" and DATE_FORMAT(i.pay_time,'%Y-%m-%d') BETWEEN DATE_FORMAT('" + beginDate + "','%Y-%m-%d')  AND DATE_FORMAT('" + endDate + "','%Y-%m-%d')");
			queryCountSql.append(" and DATE_FORMAT(u.insert_time,'%Y-%m-%d') BETWEEN DATE_FORMAT('" + beginDate + "','%Y-%m-%d')  AND DATE_FORMAT('" + endDate + "','%Y-%m-%d')");
		}
		queryCountSql.append(" ) AS zctzje");
		queryCountSql.append(")tt");
	}

	/**
	 * 根据日期统计用户注册并且投资人数及投资金额
	 * 
	 * @param insertTime
	 *            注册时间
	 * @param registPlatform
	 *            注册平台
	 * @return
	 */
	public List<UsersConvert> findUsersCountByDate(String insertTime, String registPlatform) throws Exception {
		ArrayList<Object> list = new ArrayList<Object>();
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT r.date ,r.reuserscount ,r.reginscount ,r.regcopies ,    ");
		buffer.append(" r.bindcount,r.insusercount ,r.inscount ,r.ftcs ,r.ftrs, ");
		buffer.append(" r.allcopies,r.strs ");
		buffer.append(" FROM (");
		buffer.append(userConvert(registPlatform));
		buffer.append(" ) r ");
		buffer.append(" WHERE 1=1 ");
		// 时间
		if (!QwyUtil.isNullAndEmpty(insertTime)) {
			String[] time = QwyUtil.splitTime(insertTime);
			if (time.length > 1) {
				buffer.append(" AND r.date >= ? ");
				list.add(QwyUtil.fmMMddyyyy.parse(time[0]));
				buffer.append(" AND r.date <= ? ");
				list.add(QwyUtil.fmMMddyyyy.parse(time[1]));
			} else {
				buffer.append(" AND r.date >= ? ");
				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 00:00:00"));
				buffer.append(" AND r.date <= ? ");
				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 23:59:59"));
			}
		}
		buffer.append(" ORDER BY  r.date  DESC");
		StringBuffer bufferCount = new StringBuffer();
		bufferCount.append(" SELECT COUNT(t.date)  ");
		bufferCount.append(" FROM (");
		bufferCount.append(buffer);
		bufferCount.append(") t");
		List<Object[]> obs = dao.LoadAllSql(buffer.toString(), list.toArray());
		List<UsersConvert> platUsers = toUserConvert(obs);
		return platUsers;
	}

	/**
	 * 用户转换的SQL语句
	 * 
	 * @param registPlatform
	 *            注册平台
	 */
	public String userConvert(String registPlatform) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT dd.insert_time as date ,q.usercount as reuserscount ,q.inscount as reginscount ,q.insmoney as regcopies ,    ");
		buffer.append(" w.bindcount as bindcount,e.userscount as insusercount ,e.inscount as inscount ,e.ftcs as ftcs ,e.ftrs as ftrs, ");
		buffer.append(" e.copies as allcopies,e.strs as strs ");
		buffer.append(" FROM ");
		buffer.append(" dateday dd ");
		buffer.append(" LEFT JOIN ");
		buffer.append(" (" + regDate(registPlatform));
		buffer.append(" ) q ");
		buffer.append(" ON DATE_FORMAT(dd.insert_time, '%Y-%m-%d' ) = q.date ");
		buffer.append(" LEFT JOIN (");
		buffer.append(bindCount(registPlatform));
		buffer.append(" ) w ");
		buffer.append(" ON DATE_FORMAT(dd.insert_time, '%Y-%m-%d' )= w.date ");
		buffer.append(" LEFT JOIN (");
		buffer.append(insCount(registPlatform));
		buffer.append(" )e ON DATE_FORMAT(dd.insert_time, '%Y-%m-%d' )=e.date ");
		return buffer.toString();
	}

	/**
	 * 得到JASPER
	 * 
	 * @param sourceFileName
	 *            文件名
	 */
	public List<JasperPrint> getUserConvertJasperPrintList(PageUtil pageUtil, String insertTime, String registPlatform, String sourceFileName) throws Exception {
		List<JasperPrint> list = new ArrayList<JasperPrint>();
		List<UsersConvert> converts = findUsersCountByDate(pageUtil, insertTime, registPlatform).getList();
		if (!QwyUtil.isNullAndEmpty(converts)) {
			UsersConvert convert = tjUsersConvert(insertTime, registPlatform);
			Map<String, String> map = QwyUtil.getValueMap(convert);
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(converts);
			// JasperPrint
			// js=JasperFillManager.fillReport(context.getRealPath(path)
			// +File.separator+getJxmlStr(), map,
			// ds);"D:\\table"+File.separator+"releaseProduct.jasper"
			JasperPrint js = JasperFillManager.fillReport(sourceFileName, map, ds);
			list.add(js);
		}
		return list;
	}

	/**
	 * 得到JASPER
	 * 
	 * @param sourceFileName
	 *            文件名
	 */
	public List<JasperPrint> getOperationJasperPrintList(String insertTime, String registPlatform, String sourceFileName) throws Exception {
		List<JasperPrint> list = new ArrayList<JasperPrint>();
		List<BackStatsOperateDay> converts = findOperationByDate(registPlatform, insertTime);
		List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
		if (!QwyUtil.isNullAndEmpty(converts)) {

			BackStatsOperateDay backStatsOperateDay = new BackStatsOperateDay();

			for (BackStatsOperateDay b : converts) {
				// ObjectUtil.accumulationObject(backStatsOperateDay, b);

				b.setInvestCentSum(QwyUtil.calcNumber(b.getInvestCentSum(), 100, "/", 2).doubleValue());
				b.setRepayPrincipalCentSum(QwyUtil.calcNumber(b.getRepayPrincipalCentSum(), 100, "/", 2).doubleValue());
				b.setRepayInterestCentSum(QwyUtil.calcNumber(b.getRepayInterestCentSum(), 100, "/", 2).doubleValue());
				b.setAvgInvestCentSum(QwyUtil.calcNumber(b.getAvgInvestCentSum(), 100, "/", 2).doubleValue());
				b.setFirstInvestCentSum(QwyUtil.calcNumber(b.getFirstInvestCentSum(), 100, "/", 2).doubleValue());

				Map<String, Object> map = ObjectUtil.declaredObjectToMap(b);
				ObjectUtil.changeMapDoubleToString(map);
				list1.add(map);
			}

			for (Map<String, Object> map : findOperationTotal(registPlatform, insertTime)) {
				backStatsOperateDay = (BackStatsOperateDay) ObjectUtil.mapToObject(map, backStatsOperateDay);
			}

			// 人均投资
			backStatsOperateDay.setAvgInvestCentSum();
			// 新增二次投资率
			backStatsOperateDay.setNewTwoInvestRate();
			// 复投
			backStatsOperateDay.setReInvestRate();

			backStatsOperateDay.setInvestCentSum(QwyUtil.calcNumber(backStatsOperateDay.getInvestCentSum(), 100, "/", 2).doubleValue());
			backStatsOperateDay.setRepayPrincipalCentSum(QwyUtil.calcNumber(backStatsOperateDay.getRepayPrincipalCentSum(), 100, "/", 2).doubleValue());
			backStatsOperateDay.setRepayInterestCentSum(QwyUtil.calcNumber(backStatsOperateDay.getRepayInterestCentSum(), 100, "/", 2).doubleValue());
			backStatsOperateDay.setAvgInvestCentSum(QwyUtil.calcNumber(backStatsOperateDay.getAvgInvestCentSum(), 100, "/", 2).doubleValue());
			backStatsOperateDay.setFirstInvestCentSum(QwyUtil.calcNumber(backStatsOperateDay.getFirstInvestCentSum(), 100, "/", 2).doubleValue());

			Map<String, Object> map = ObjectUtil.declaredObjectToMap(backStatsOperateDay);
			ObjectUtil.changeMapDoubleToString(map);
			ObjectUtil.changeMapToString(map);
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(list1);
			// JasperPrint
			// js=JasperFillManager.fillReport(context.getRealPath(path)
			// +File.separator+getJxmlStr(), map,
			// ds);"D:\\table"+File.separator+"releaseProduct.jasper"
			JasperPrint js = JasperFillManager.fillReport(sourceFileName, map, ds);
			list.add(js);
		}
		return list;
	}

	public Object[] insUsersCountSQL(String insertTime, String registPlatform) throws Exception {
		ArrayList<Object> list = new ArrayList<Object>();
		StringBuffer buffer = new StringBuffer();
		buffer.append("select * FROM (");

		buffer.append(" SELECT (SELECT  count(DISTINCT users_id) FROM investors LEFT JOIN users ON investors.USERS_ID=USERS.ID WHERE investor_status IN ('1','2','3')");
		if (!QwyUtil.isNullAndEmpty(registPlatform)) {
			buffer.append(" and users.REGIST_PLATFORM='" + registPlatform + "'");
		}
		if (!QwyUtil.isNullAndEmpty(insertTime)) {
			String[] time = QwyUtil.splitTime(insertTime);
			if (time.length > 1) {
				buffer.append(" AND DATE_FORMAT(investors.pay_time, '%Y-%m-%d' )>= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[0])));
				buffer.append(" AND DATE_FORMAT(investors.pay_time, '%Y-%m-%d' )<= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[1])));
			} else {
				buffer.append(" AND DATE_FORMAT(investors.pay_time, '%Y-%m-%d' )>= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[0])));
				buffer.append(" AND DATE_FORMAT(investors.pay_time, '%Y-%m-%d' )<= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[0])));
			}
		}
		buffer.append(" )as tee,");

		buffer.append(" (");
		buffer.append(" SELECT COUNT(DISTINCT investors1.users_id) FROM");
		buffer.append(" (SELECT investors.* FROM investors LEFT JOIN users ON investors.users_id=users.id WHERE investors.investor_status IN ('1','2','3')");
		if (!QwyUtil.isNullAndEmpty(registPlatform)) {
			buffer.append(" and users.regist_platform='" + registPlatform + "'");
		}
		if (!QwyUtil.isNullAndEmpty(insertTime)) {
			String[] time = QwyUtil.splitTime(insertTime);
			if (time.length > 1) {
				buffer.append(" AND DATE_FORMAT(investors.pay_time, '%Y-%m-%d' )>= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[0])));
				buffer.append(" AND DATE_FORMAT(investors.pay_time, '%Y-%m-%d' )<= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[1])));
			} else {
				buffer.append(" AND DATE_FORMAT(investors.pay_time, '%Y-%m-%d' )>= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[0])));
				buffer.append(" AND DATE_FORMAT(investors.pay_time, '%Y-%m-%d' )<= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[0])));
			}
		}
		buffer.append(" ) investors1");
		buffer.append(" LEFT JOIN");
		buffer.append(" (");
		buffer.append(" SELECT MIN(t.pay_time) minDate,users_id FROM investors t WHERE t.investor_status IN ('1','2','3') GROUP BY t.users_id");
		buffer.append(" ) investors2 ON investors1.users_id=investors2.users_id");
		buffer.append(" where investors1.pay_time>minDate");
		buffer.append(" )  AS tab5,");
		buffer.append(" (SELECT COUNT(*) bangdingrenshu FROM account LEFT JOIN users ON account.USERS_ID=USERS.ID where STATUS='0'");
		if (!QwyUtil.isNullAndEmpty(registPlatform)) {
			buffer.append("  and users.REGIST_PLATFORM='" + registPlatform + "'");
		}
		if (!QwyUtil.isNullAndEmpty(insertTime)) {
			String[] time = QwyUtil.splitTime(insertTime);
			if (time.length > 1) {
				buffer.append(" AND DATE_FORMAT(account.insert_time, '%Y-%m-%d' )>= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[0])));
				buffer.append(" AND DATE_FORMAT(account.insert_time, '%Y-%m-%d' )<= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[1])));
			} else {
				buffer.append(" AND DATE_FORMAT(account.insert_time, '%Y-%m-%d' )>= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[0])));
				buffer.append(" AND DATE_FORMAT(account.insert_time, '%Y-%m-%d' )<= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[0])));
			}
		}
		buffer.append(" )as bind ) ftrs");
		List objList = dao.LoadAllSql(buffer.toString(), list.toArray());
		return (Object[]) objList.get(0);
		// return "0";
	}

	/**
	 * 合计
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public UsersConvert tjUsersConvert(String insertTime, String registPlatform) {
		List<Object> list = new ArrayList<Object>();
		UsersConvert cr = new UsersConvert();
		try {

			StringBuffer buffer = new StringBuffer("FROM BackStatsOperateDay rr  where 1=1 ");
			if (!QwyUtil.isNullAndEmpty(registPlatform))
				buffer.append("  and rr.registPlatform =" + registPlatform);
			if (!QwyUtil.isNullAndEmpty(insertTime)) {
				String[] time = QwyUtil.splitTime(insertTime);
				if (time.length > 1) {
					buffer.append(" AND rr.date >= ? ");
					list.add(QwyUtil.fmMMddyyyy.parse(time[0]));
					buffer.append(" AND rr.date  <= ? ");
					list.add(QwyUtil.fmMMddyyyy.parse(time[1]));
				} else {
					buffer.append(" AND rr.date  >= ? ");
					list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 00:00:00"));
					buffer.append(" AND rr.date  <= ? ");
					list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 23:59:59"));
				}
			}
			List<BackStatsOperateDay> convert = dao.LoadAll(buffer.toString(), list.toArray());
			// 注册人数
			if (QwyUtil.isNullAndEmpty(cr.getReuserscount()))
				cr.setReuserscount("0");

			if (QwyUtil.isNullAndEmpty(cr.getBindcount()))
				cr.setBindcount("0");
			// 投资人数

			if (QwyUtil.isNullAndEmpty(cr.getInsusercount()))
				cr.setInsusercount("0");
			// 投资金额
			if (QwyUtil.isNullAndEmpty(cr.getAllcopies()))
				cr.setAllcopies("0");
			// 人均投资金额(元)
			if (QwyUtil.isNullAndEmpty(cr.getRjtz())) {
				cr.setRjtz("0.00");
			}
			// 投资次数
			if (QwyUtil.isNullAndEmpty(cr.getInscount())) {
				cr.setInscount("0");
			}
			// 复投次数
			if (QwyUtil.isNullAndEmpty(cr.getFtinscs())) {
				cr.setFtinscs("0");
			}
			// 注册投资人数
			if (QwyUtil.isNullAndEmpty(cr.getReginscount())) {
				cr.setReginscount("0");
			}

			// 注册投资金额(元)
			if (QwyUtil.isNullAndEmpty(cr.getRegcopies())) {
				cr.setRegcopies("0");
			}
			// 首投人数
			if (QwyUtil.isNullAndEmpty(cr.getStinsrs())) {
				cr.setStinsrs("0");
			}
			// 复投人数
			if (QwyUtil.isNullAndEmpty(cr.getFtinsrs())) {
				cr.setFtinsrs("0");
			}

			// 复投率(%)
			if (QwyUtil.isNullAndEmpty(cr.getFtl())) {
				cr.setFtl("0");
			}

			if (!QwyUtil.isNullAndEmpty(convert)) {

				for (BackStatsOperateDay q : convert) {

					cr.setReuserscount(QwyUtil.calcNumber(cr.getReuserscount(), q.getRegUserSum(), "+") + "");
					// cr.setBindcount(QwyUtil.calcNumber(cr.getBindcount(),
					// q.getBindBankUserSum(), "+") + "");
					// cr.setInsusercount(QwyUtil.calcNumber(cr.getInsusercount(),
					// q.getInvestUserSum(), "+") + "");
					cr.setAllcopies(QwyUtil.calcNumber(cr.getAllcopies(), q.getInvestCentSum(), "+") + "");
					// cr.setRjtz(QwyUtil.calcNumber(cr.getRjtz(),
					// q.getAvgInvestCentSum(), "+") + "");
					cr.setInscount(QwyUtil.calcNumber(cr.getInscount(), q.getInvestCount(), "+") + "");
					cr.setFtinscs(QwyUtil.calcNumber(cr.getFtinscs(), q.getReInvestCount(), "+") + "");
					cr.setRegcopies(QwyUtil.calcNumber(cr.getRegcopies(), q.getRegInvestCentSum(), "+") + "");
					cr.setStinsrs(QwyUtil.calcNumber(cr.getStinsrs(), q.getFirstInvestUserSum(), "+") + "");
					cr.setReginscount(QwyUtil.calcNumber(cr.getReginscount(), q.getRegInvestUserSum(), "+") + "");
					// cr.setFtinsrs(QwyUtil.calcNumber(cr.getFtinsrs(),
					// q.getReInvestUserSum(), "+") + "");
				}
				// 转换率
				if (cr.getStinsrs().equals("0") || cr.getReuserscount().equals("0")) {
					cr.setZhl("0");
				} else {
					BigDecimal zhl = QwyUtil.calcNumber(cr.getStinsrs(), cr.getReuserscount(), "/");
					cr.setZhl(QwyUtil.calcNumber(zhl, 0.01, "/", 2).toString());
				}

				// 投资人数
				Object[] obj = insUsersCountSQL(insertTime, registPlatform);
				if (!QwyUtil.isNullAndEmpty(obj)) {
					cr.setInsusercount(obj[0] + "");
					cr.setFtinsrs(obj[1] == null ? "0" : obj[1].toString());
					cr.setBindcount(obj[2] == null ? "0" : obj[2].toString());
				}

				// 人均投资金额(元)
				if (!cr.getInsusercount().equals("0")) {
					cr.setRjtz(QwyUtil.calcNumber(cr.getAllcopies(), cr.getInsusercount(), "/", 2).toString());
				}
				// 复投率
				if (cr.getInsusercount().equals("0") || cr.getFtinsrs().equals("0")) {
					cr.setFtl("0");
				} else {
					BigDecimal zhl = QwyUtil.calcNumber(cr.getFtinsrs(), cr.getInsusercount(), "/");
					cr.setFtl(QwyUtil.calcNumber(zhl, 0.01, "/", 2).toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cr;
	}

	/**
	 * 以日期分组日期、注册人数、注册并投资人数、投资金额
	 * 
	 * @param registPlatform
	 *            注册平台
	 */
	public String regDate(String registPlatform) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("	SELECT DATE_FORMAT( us.insert_time, '%Y-%m-%d' ) as date ,  ");
		buffer.append(" COUNT(DISTINCT us.id) as usercount , ");
		buffer.append(
				" COUNT(DISTINCT CASE WHEN (ins.investor_status in ('1','2','3') AND ins.id is not NULL AND DATE_FORMAT( us.insert_time, '%Y-%m-%d' ) = DATE_FORMAT( ins.pay_time, '%Y-%m-%d' )) then us.id else null end )  as inscount, ");
		buffer.append(
				" SUM(CASE WHEN (ins.investor_status in ('1','2','3') AND ins.id is not NULL AND DATE_FORMAT( us.insert_time, '%Y-%m-%d' ) = DATE_FORMAT( ins.pay_time, '%Y-%m-%d' )) then ins.in_money else 0 end )  as insmoney ");
		buffer.append(" FROM  users as us ");
		buffer.append(" LEFT JOIN investors ins ON  us.id = ins.users_id  ");
		if (!QwyUtil.isNullAndEmpty(registPlatform)) {
			buffer.append(" WHERE us.regist_platform = '" + registPlatform + "' ");
		}
		buffer.append(" GROUP BY DATE_FORMAT( us.insert_time, '%Y-%m-%d' ) ");
		buffer.append(" ORDER BY DATE_FORMAT( us.insert_time, '%Y-%m-%d' ) DESC  ");
		return buffer.toString();
	}

	/**
	 * 绑定银行的人数
	 * 
	 * @param @param
	 *            registPlatform 注册平台
	 * @return
	 */
	public String bindCount(String registPlatform) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT DATE_FORMAT( ac.insert_time, '%Y-%m-%d' ) as date ,  ");
		buffer.append(" COUNT(DISTINCT ac.users_id) as bindcount ");
		buffer.append(" FROM account ac ");
		buffer.append(" WHERE ac.status = '0' ");
		if (!QwyUtil.isNullAndEmpty(registPlatform)) {
			buffer.append(" AND ac.users_id in (");
			buffer.append(" SELECT id FROM users us  ");
			buffer.append(" WHERE us.regist_platform = '" + registPlatform + "' ");
			buffer.append(" )  ");
		}

		buffer.append(" GROUP BY DATE_FORMAT( ac.insert_time, '%Y-%m-%d' ) ");
		buffer.append(" ORDER BY DATE_FORMAT( ac.insert_time, '%Y-%m-%d' ) DESC ");
		return buffer.toString();
	}

	/**
	 * 投资情况
	 * 
	 * @param registPlatform
	 *            注册平台
	 */
	public String insCount(String registPlatform) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT t.date as date,SUM(t.userscount) as userscount,SUM(t.copies) as copies,SUM(t.inscount) as inscount,t.strs,t.ftcs,t.ftrs from ( ");
		buffer.append(" SELECT DATE_FORMAT( ins.pay_time, '%Y-%m-%d' ) as date ,   ");
		buffer.append(" COUNT(DISTINCT ins.users_id) as userscount, ");
		buffer.append(" SUM(ins.in_money) as copies, ");
		buffer.append(" COUNT(DISTINCT ins.id) as inscount, ");
		buffer.append(" ( ");
		buffer.append(strsTable(registPlatform));
		buffer.append("  )  as strs, ");
		buffer.append(" w.ftcs as ftcs, ");
		buffer.append(" w.ftrs as ftrs ");
		buffer.append(" FROM investors ins ");
		buffer.append(" LEFT JOIN ( ");

		buffer.append(ftTable(registPlatform));
		buffer.append(" ) w ");
		buffer.append(" on DATE_FORMAT( w.date, '%Y-%m-%d' ) = DATE_FORMAT( ins.pay_time, '%Y-%m-%d' ) ");
		buffer.append(" WHERE ins.investor_status in ('1','2','3') ");
		if (!QwyUtil.isNullAndEmpty(registPlatform)) {
			buffer.append(" AND ins.users_id in (");
			buffer.append(" SELECT id FROM users us  ");
			buffer.append(" WHERE us.regist_platform = '" + registPlatform + "' ");
			buffer.append(" )  ");
		}
		buffer.append(" GROUP BY DATE_FORMAT( ins.pay_time, '%Y-%m-%d' ) ");
		buffer.append(" ORDER BY DATE_FORMAT( ins.pay_time, '%Y-%m-%d' ) DESC  ");
		buffer.append(" ) t ");
		buffer.append(" GROUP BY t.date ORDER BY t.date DESC ");
		return buffer.toString();
	}

	/**
	 * 复投表
	 * 
	 * @param registPlatform
	 *            注册平台
	 */
	public String ftTable(String registPlatform) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT DATE_FORMAT( t.date, '%Y-%m-%d' ) as date,COUNT(*) as ftcs,COUNT(DISTINCT t.users_id)as ftrs from ( ");
		// buffer.append(" SELECT ins.pay_time as date , ins.users_id as
		// users_id FROM investors ins WHERE ins.pay_time not in (SELECT
		// MIN(ins.pay_time) FROM investors ins GROUP BY users_id) ");
		buffer.append(
				" SELECT ins.pay_time as date , ins.users_id as users_id FROM investors ins WHERE NOT EXISTS (SELECT 1 FROM(SELECT MIN(ins.pay_time) mintime FROM investors ins GROUP BY users_id )et WHERE ins.pay_time=mintime)  ");
		buffer.append(" AND ins.investor_status in ('1','2','3') ");
		if (!QwyUtil.isNullAndEmpty(registPlatform)) {
			buffer.append(" AND ins.users_id in (");
			buffer.append(" SELECT id FROM users us  ");
			buffer.append(" WHERE us.regist_platform = '" + registPlatform + "' ");
			buffer.append(" )  ");
		}
		buffer.append(" ) t GROUP BY DATE_FORMAT( t.date, '%Y-%m-%d' ) ");
		buffer.append(" ORDER BY DATE_FORMAT( t.date, '%Y-%m-%d ' ) DESC  ");
		return buffer.toString();

	}

	/**
	 * 首次投资
	 * 
	 * @param registPlatform
	 *            注册平台
	 */
	public String strsTable(String registPlatform) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT COUNT(*) from ( ");
		buffer.append(" SELECT MIN(ins.pay_time) as date FROM investors ins ");
		buffer.append(" WHERE ins.investor_status in ('1','2','3') ");
		if (!QwyUtil.isNullAndEmpty(registPlatform)) {
			buffer.append(" AND ins.users_id in (");
			buffer.append(" SELECT id FROM users us  ");
			buffer.append(" WHERE us.regist_platform = '" + registPlatform + "' ");
			buffer.append(" )  ");
		}
		buffer.append(" GROUP BY users_id ");
		buffer.append(" ) q WHERE DATE_FORMAT( q.date, '%Y-%m-%d' ) = DATE_FORMAT( ins.pay_time, '%Y-%m-%d' ) ");
		buffer.append(" GROUP BY DATE_FORMAT( q.date, '%Y-%m-%d' ) ");
		buffer.append(" ORDER BY DATE_FORMAT( q.date, '%Y-%m-%d ' ) DESC ");
		return buffer.toString();

	}

	/**
	 * 根据日期获取运营数据
	 * 
	 * @param registPlatform
	 * @param insertTime
	 *            时间
	 * @param pageUtil
	 * @return
	 * @throws Exception
	 */
	public List<BackStatsOperateDay> findOperationByDate(String registPlatform, String insertTime) throws Exception {
		StringBuffer yunyingQueryListSql = new StringBuffer();
		String beginDate = "";
		String endDate = "";
		if (!QwyUtil.isNullAndEmpty(insertTime)) {
			String[] time = QwyUtil.splitTime(insertTime);
			if (time.length == 2) {
				beginDate = QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[0].trim()));
				endDate = QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[1].trim()));
			} else {
				endDate = QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[0].trim()));
				beginDate = QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[0].trim()));
			}
			endDate = endDate + " 23:59:59";
		}

		createYunyingQueryListSql(yunyingQueryListSql, beginDate, endDate, true);
		List<Map<String, Object>> list = dao.LoadAllListMapSql(yunyingQueryListSql.toString(), null);
		List<BackStatsOperateDay> list1 = toBackStatsOperateDay(list);
		return list1;
	}

	public List<Map<String, Object>> findOperationTotal(String registPlatform, String insertTime) throws Exception {
		StringBuffer yunyingQueryListSql = new StringBuffer();
		String beginDate = "";
		String endDate = "";
		if (!QwyUtil.isNullAndEmpty(insertTime)) {
			String[] time = QwyUtil.splitTime(insertTime);
			if (time.length == 2) {
				beginDate = QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[0].trim()));
				endDate = QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[1].trim()));
			} else {
				endDate = QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[0].trim()));
				beginDate = QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[0].trim()));
			}
		}

		if (QwyUtil.isNullAndEmpty(endDate)) {
			endDate = QwyUtil.fmyyyyMMdd.format(new Date()) + " 23:59:59";
		} else {
			endDate = endDate + " 23:59:59";
		}

		createOperateQueryListSql(yunyingQueryListSql, beginDate, endDate, false);
		List<Map<String, Object>> list = dao.LoadAllListMapSql(yunyingQueryListSql.toString(), null);
		return list;
	}

	/**
	 * 根据日期获取运营数据
	 * 
	 * @param registPlatform
	 * @param insertTime
	 *            时间
	 * @param pageUtil
	 * @return
	 * @throws Exception
	 */
	public PageUtil<BackStatsOperateDay> findOperationByDate(PageUtil pageUtil, String registPlatform, String insertTime) throws Exception {
		StringBuffer yunyingQueryListSql = new StringBuffer();
		String beginDate = "";
		String endDate = "";
		if (!QwyUtil.isNullAndEmpty(insertTime)) {
			String[] time = QwyUtil.splitTime(insertTime);
			if (time.length == 2) {
				beginDate = QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[0].trim()));
				endDate = QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[1].trim()));
			} else {
				endDate = QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[0].trim()));
				beginDate = QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[0].trim()));
			}
			endDate = endDate + " 23:59:59";
		}
		createYunyingQueryListSql(yunyingQueryListSql, beginDate, endDate, true);
		StringBuffer bufferCount = new StringBuffer();
		bufferCount.append(" SELECT COUNT(*)  ");
		bufferCount.append(" FROM (");
		bufferCount.append(yunyingQueryListSql);
		bufferCount.append(") t");
		pageUtil = dao.getListMapBySqlAndSqlCount(pageUtil, yunyingQueryListSql.toString(), bufferCount.toString(), null);
		yunyingQueryListSql.setLength(0);
		bufferCount.setLength(0);

		List<Map<String, Object>> list = pageUtil.getList();
		List<BackStatsOperateDay> list1 = toBackStatsOperateDay(list);
		pageUtil.setList(list1);
		return pageUtil;
	}

	public void createYunyingQueryListSql(StringBuffer yunyingQueryListSql, String beginDate, String endDate, boolean isGroupBy) {
		yunyingQueryListSql.append(
				"SELECT date, sum(regUserSum) regUserSum, sum(bindBankUserSum) bindBankUserSum, sum(activateUserSum) activateUserSum, sum(investUserSum) investUserSum, sum(firstInvestUserSum) firstInvestUserSum, sum(reInvestUserSum) reInvestUserSum, sum(investCount) investCount, sum(firstInvestCount) firstInvestCount, sum(reInvestCount) reInvestCount, sum(copiesSum) copiesSum, sum(investCentSum) investCentSum, sum(couponCentSum) couponCentSum, sum(firstInvestCentSum) firstInvestCentSum, sum(reInvestCentSum) reInvestCentSum, sum(repayPrincipalCentSum) repayPrincipalCentSum, sum(repayInterestCentSum) repayInterestCentSum, sum(repayAlreadyAllCentSum) repayAlreadyAllCentSum, sum(rechargeCount) rechargeCount, sum(rechargeUserSum) rechargeUserSum, sum(rechargeCentSum) rechargeCentSum, sum(withdrawCount) withdrawCount, sum(withdrawUserSum) withdrawUserSum, sum(withdrawCentSum) withdrawCentSum, sum(successWithdrawCount) successWithdrawCount, sum(successWithdrawUserSum) successWithdrawUserSum, sum(successWithdrawCentSum) successWithdrawCentSum, sum(regInvestCount) regInvestCount, sum(regInvestUserSum) regInvestUserSum, sum(regInvestCentSum) regInvestCentSum, sum(newTwoInvestCount) newTwoInvestCount, sum(newTwoInvestUserSum) newTwoInvestUserSum, sum(newTwoInvestCentSum) newTwoInvestCentSum");
		yunyingQueryListSql.append(" from back_stats_operate_day");
		yunyingQueryListSql.append(" where 1 = 1");
		if (StringUtils.isNotBlank(beginDate)) {
			yunyingQueryListSql.append(" and date >= '");
			yunyingQueryListSql.append(beginDate);
			yunyingQueryListSql.append("'");
		}
		if (StringUtils.isNotBlank(endDate)) {
			yunyingQueryListSql.append(" and date <= '");
			yunyingQueryListSql.append(endDate);
			yunyingQueryListSql.append("'");
		}
		if (isGroupBy) {
			yunyingQueryListSql.append(" group by date");
		}

		yunyingQueryListSql.append(" order by date desc");
	}

	public void createOperateQueryListSql(StringBuffer yunyingQueryListSql, String beginDate, String endDate, boolean isGroupBy) {
		yunyingQueryListSql.append(
				"SELECT s.*, ss.*, sss.*, ssss.* FROM ( SELECT count(DISTINCT a.users_id) investUserSum, count( DISTINCT CASE WHEN a.pay_time = b.minPayTime THEN a.users_id ELSE NULL END ) firstInvestUserSum, count( DISTINCT CASE WHEN a.pay_time > b.minPayTime THEN a.users_id ELSE NULL END ) reInvestUserSum, count(1) investCount, sum(a.in_money) investCentSum, sum(a.copies) copiesSum, sum( CASE WHEN a.pay_time = b.minPayTime THEN 1 ELSE 0 END ) firstInvestCount, sum( CASE WHEN a.pay_time > b.minPayTime THEN 1 ELSE 0 END ) reInvestCount, sum(a.coupon) couponCentSum, sum( CASE WHEN a.pay_time = b.minPayTime THEN a.in_money ELSE 0 END ) firstInvestCentSum, sum( CASE WHEN a.pay_time > b.minPayTime THEN a.in_money ELSE 0 END ) reInvestCentSum FROM investors a inner JOIN ( SELECT users_id, MIN( DATE_FORMAT(pay_time, '%Y-%m-%d')) minPayDate, MIN(pay_time) minPayTime FROM investors a WHERE a.investor_status IN (1, 2, 3) GROUP BY users_id ) b ON a.users_id = b.users_id inner JOIN users c ON b.users_id = c.id WHERE a.investor_status IN (1, 2, 3) ");
		if (StringUtils.isNotBlank(beginDate)) {
			yunyingQueryListSql.append(" and a.pay_time >= '");
			yunyingQueryListSql.append(beginDate);
			yunyingQueryListSql.append("'");
		}
		if (StringUtils.isNotBlank(endDate)) {
			yunyingQueryListSql.append(" and a.pay_time <= '");
			yunyingQueryListSql.append(endDate);
			yunyingQueryListSql.append("'");
		}

		yunyingQueryListSql.append(
				" ) s JOIN (( SELECT count(a.id) newTwoInvestCount, count(DISTINCT a.users_id) newTwoInvestUserSum, sum(a.in_money) newTwoInvestCentSum FROM investors a LEFT JOIN ( SELECT users_id, MIN(pay_time) pay_time FROM investors WHERE investor_status IN (1, 2, 3) GROUP BY users_id ) b ON a.users_id = b.users_id AND DATE_FORMAT(a.pay_time, '%Y-%m-%d') = DATE_FORMAT(b.pay_time, '%Y-%m-%d') LEFT JOIN users c ON a.users_id = c.id WHERE a.investor_status IN (1, 2, 3) AND a.pay_time > b.pay_time ");
		if (StringUtils.isNotBlank(beginDate)) {
			yunyingQueryListSql.append(" and a.pay_time >= '");
			yunyingQueryListSql.append(beginDate);
			yunyingQueryListSql.append("'");
		}
		if (StringUtils.isNotBlank(endDate)) {
			yunyingQueryListSql.append(" and a.pay_time <= '");
			yunyingQueryListSql.append(endDate);
			yunyingQueryListSql.append("'");
		}

		yunyingQueryListSql.append(
				" )) ss JOIN ( SELECT sum(a.pay_money) repayPrincipalCentSum, sum(a.pay_interest) repayInterestCentSum, sum(a.already_pay) repayAlreadyAllCentSum FROM interest_details a LEFT JOIN users b ON a.users_id = b.id WHERE a. STATUS = 2 ");
		if (StringUtils.isNotBlank(beginDate)) {
			yunyingQueryListSql.append(" and a.return_time >= '");
			yunyingQueryListSql.append(beginDate);
			yunyingQueryListSql.append("'");
		}
		if (StringUtils.isNotBlank(endDate)) {
			yunyingQueryListSql.append(" and a.return_time <= '");
			yunyingQueryListSql.append(endDate);
			yunyingQueryListSql.append("'");
		}
		yunyingQueryListSql.append(") sss join (select count(1) activateUserSum from activity a where 1 = 1 ");
		if (StringUtils.isNotBlank(beginDate)) {
			yunyingQueryListSql.append(" and a.insert_time >= '");
			yunyingQueryListSql.append(beginDate);
			yunyingQueryListSql.append("'");
		}
		if (StringUtils.isNotBlank(endDate)) {
			yunyingQueryListSql.append(" and a.insert_time <= '");
			yunyingQueryListSql.append(endDate);
			yunyingQueryListSql.append("'");
		}
		yunyingQueryListSql.append(") ssss");
	}

	public void createYunyingQueryCountSql(StringBuffer yunyingQueryCountSql, String beginDate, String endDate) {
		yunyingQueryCountSql.append(" select * from (");
		yunyingQueryCountSql.append(" select ");
		// 投资人数
		yunyingQueryCountSql.append(" (SELECT COUNT(*) AS touzirenshu FROM ");
		yunyingQueryCountSql.append(" (");
		yunyingQueryCountSql.append(" SELECT DISTINCT users_id FROM investors WHERE investor_status IN ('1','2','3')");
		if (!QwyUtil.isNullAndEmpty(beginDate) && !QwyUtil.isNullAndEmpty(endDate)) {
			yunyingQueryCountSql.append(" and DATE_FORMAT(pay_time,'%Y-%m-%d') between '" + beginDate + "' and '" + endDate + "'");
		}
		yunyingQueryCountSql.append(" ) tab1) as touzirenshu,");
		// 投资人次
		yunyingQueryCountSql.append(" (SELECT COUNT(*) AS touzirenci FROM investors ");
		yunyingQueryCountSql.append(" WHERE investor_status IN ('1','2','3')");
		if (!QwyUtil.isNullAndEmpty(beginDate) && !QwyUtil.isNullAndEmpty(endDate)) {
			yunyingQueryCountSql.append(" and DATE_FORMAT(pay_time,'%Y-%m-%d') between '" + beginDate + "' and '" + endDate + "'");
		}
		yunyingQueryCountSql.append(" ) as touzirenci,");
		// 复投次数
		yunyingQueryCountSql.append(" (SELECT COUNT(*) AS futoucishu FROM");
		yunyingQueryCountSql.append(" (");
		yunyingQueryCountSql.append(" SELECT DATE_FORMAT(investors1.pay_time,'%Y-%m-%d') AS futoucishuriqi,investors1.users_id FROM investors investors1 LEFT JOIN ");
		yunyingQueryCountSql.append(" (");
		yunyingQueryCountSql.append(" SELECT MIN(t.pay_time) minDate,users_id FROM investors t WHERE t.investor_status IN ('1','2','3') GROUP BY t.users_id");
		yunyingQueryCountSql.append(" ) investors2 ON investors1.users_id=investors2.users_id ");
		yunyingQueryCountSql.append(" WHERE investors1.pay_time>minDate AND investors1.investor_status IN ('1','2','3')");
		if (!QwyUtil.isNullAndEmpty(beginDate) && !QwyUtil.isNullAndEmpty(endDate)) {
			yunyingQueryCountSql.append(" and DATE_FORMAT(investors1.pay_time,'%Y-%m-%d') between '" + beginDate + "' and '" + endDate + "'");
		}
		yunyingQueryCountSql.append(" ) tab5) as futoucishu,");
		// 复投人数
		yunyingQueryCountSql.append(" (SELECT COUNT(*) AS futourenshu FROM");
		yunyingQueryCountSql.append(" (");
		yunyingQueryCountSql.append(" SELECT DISTINCT investors1.users_id FROM investors investors1 LEFT JOIN ");
		yunyingQueryCountSql.append(" (");
		yunyingQueryCountSql.append(" SELECT MIN(t.pay_time) minDate,users_id FROM investors t WHERE t.investor_status IN ('1','2','3') GROUP BY t.users_id");
		yunyingQueryCountSql.append(" ) investors2 ON investors1.users_id=investors2.users_id ");
		yunyingQueryCountSql.append(" WHERE investors1.pay_time>minDate AND investors1.investor_status IN ('1','2','3')");
		if (!QwyUtil.isNullAndEmpty(beginDate) && !QwyUtil.isNullAndEmpty(endDate)) {
			yunyingQueryCountSql.append(" and DATE_FORMAT(investors1.pay_time,'%Y-%m-%d') between '" + beginDate + "' and '" + endDate + "'");
		}
		yunyingQueryCountSql.append(" ) tab5) as futourenshu,");
		// 投资金额
		yunyingQueryCountSql.append(" (SELECT SUM(in_money) touzijine FROM investors");
		yunyingQueryCountSql.append(" WHERE investor_status IN ('1','2','3')");
		if (!QwyUtil.isNullAndEmpty(beginDate) && !QwyUtil.isNullAndEmpty(endDate)) {
			yunyingQueryCountSql.append(" and DATE_FORMAT(pay_time,'%Y-%m-%d') between '" + beginDate + "' and '" + endDate + "'");
		}
		yunyingQueryCountSql.append(" ) as touzijine,");
		// 首投人数
		yunyingQueryCountSql.append(" (SELECT COUNT(*) shourourenshu FROM(");
		yunyingQueryCountSql.append(" SELECT DISTINCT DATE_FORMAT(investors1.pay_time,'%Y-%m-%d') AS shourourenshuriqi,investors1.users_id FROM investors investors1 INNER JOIN ");
		yunyingQueryCountSql.append(" (");
		yunyingQueryCountSql.append(" SELECT MIN(t.pay_time) minDate,users_id FROM investors t WHERE t.investor_status IN ('1','2','3') GROUP BY t.users_id");
		yunyingQueryCountSql.append(" ) investors2 ON investors1.users_id=investors2.users_id and investors1.pay_time=investors2.minDate WHERE investors1.investor_status IN ('1','2','3')");
		if (!QwyUtil.isNullAndEmpty(beginDate) && !QwyUtil.isNullAndEmpty(endDate)) {
			yunyingQueryCountSql.append(" and DATE_FORMAT(investors1.pay_time,'%Y-%m-%d') between '" + beginDate + "' and '" + endDate + "'");
		}
		yunyingQueryCountSql.append(" ) tab) as shourourenshu,");
		// 激活用户
		yunyingQueryCountSql.append(" (SELECT COUNT(*) AS jihuoYonghu FROM activity where 1=1");
		if (!QwyUtil.isNullAndEmpty(beginDate) && !QwyUtil.isNullAndEmpty(endDate)) {
			yunyingQueryCountSql.append(" and DATE_FORMAT(insert_time,'%Y-%m-%d') between '" + beginDate + "' and '" + endDate + "'");
		}
		yunyingQueryCountSql.append(" ) as jihuoYonghu,");
		// 还款金额
		yunyingQueryCountSql.append(" (SELECT SUM(ids.pay_money) AS huankuanjine FROM interest_details ids where STATUS='2'");
		if (!QwyUtil.isNullAndEmpty(beginDate) && !QwyUtil.isNullAndEmpty(endDate)) {
			yunyingQueryCountSql.append(" and DATE_FORMAT(return_time,'%Y-%m-%d') between '" + beginDate + "' and '" + endDate + "'");
		}
		yunyingQueryCountSql.append(" ) as huankuanjine,");
		// 支付利息
		yunyingQueryCountSql.append(" (SELECT SUM(pay_interest) AS zhifulixi FROM interest_details where STATUS='2'");
		if (!QwyUtil.isNullAndEmpty(beginDate) && !QwyUtil.isNullAndEmpty(endDate)) {
			yunyingQueryCountSql.append(" and DATE_FORMAT(return_time,'%Y-%m-%d') between '" + beginDate + "' and '" + endDate + "'");
		}
		yunyingQueryCountSql.append(" ) as zhifulixi,");
		// 首投总额
		yunyingQueryCountSql.append(" (SELECT SUM(shoutouzonge) AS shoutouzonge FROM (");
		yunyingQueryCountSql.append(" SELECT DATE_FORMAT(investors1.pay_time,'%Y-%m-%d') AS shoutouriqi,investors1.in_money AS shoutouzonge FROM investors investors1 INNER JOIN ");
		yunyingQueryCountSql.append(" (");
		yunyingQueryCountSql.append(" SELECT MIN(t.pay_time) minDate,users_id FROM investors t WHERE t.investor_status IN ('1','2','3') GROUP BY t.users_id");
		yunyingQueryCountSql.append(" ) investors2 ON investors1.users_id=investors2.users_id and investors1.pay_time=investors2.minDate WHERE investors1.investor_status IN ('1','2','3') ");
		if (!QwyUtil.isNullAndEmpty(beginDate) && !QwyUtil.isNullAndEmpty(endDate)) {
			yunyingQueryCountSql.append(" and DATE_FORMAT(investors1.pay_time,'%Y-%m-%d') between '" + beginDate + "' and '" + endDate + "'");
		}
		yunyingQueryCountSql.append(" )tab) as shoutouzonge,");
		// 二次投资人数
		yunyingQueryCountSql.append(" (SELECT COUNT(*) ercitouzirenshu FROM (");
		yunyingQueryCountSql.append(" SELECT DATE_FORMAT( ins.pay_time, '%Y-%m-%d' ) AS ercitouziriqi FROM investors ins");
		yunyingQueryCountSql.append(" LEFT JOIN (");
		yunyingQueryCountSql.append(" SELECT MIN(ins.pay_time) AS DATE,ins.users_id AS users_id FROM investors ins  GROUP BY users_id");
		yunyingQueryCountSql.append(" ) t ON DATE_FORMAT( t.date, '%Y-%m-%d' ) = DATE_FORMAT( ins.pay_time, '%Y-%m-%d' ) AND ins.users_id = t.users_id");
		yunyingQueryCountSql.append(" WHERE ins.pay_time !=t.date and ins.investor_status IN ('1','2','3') ");
		if (!QwyUtil.isNullAndEmpty(beginDate) && !QwyUtil.isNullAndEmpty(endDate)) {
			yunyingQueryCountSql.append(" and DATE_FORMAT(ins.pay_time,'%Y-%m-%d') between '" + beginDate + "' and '" + endDate + "'");
		}
		yunyingQueryCountSql.append(" ) tab) as ercitouzirenshu");

		yunyingQueryCountSql.append(" )tab");
	}

	/**
	 * 合计
	 * 
	 * @param insertTime
	 *            投资时间
	 * @param registPlatform
	 *            平台
	 * @return
	 * @throws Exception
	 */
	public Operation tjOperation(String insertTime, String registPlatform) throws Exception {
		Operation or = new Operation();
		StringBuffer yunyingQueryCountSql = new StringBuffer();
		String beginDate = "";
		String endDate = "";
		if (!QwyUtil.isNullAndEmpty(insertTime)) {
			String[] time = QwyUtil.splitTime(insertTime);
			if (time.length == 2) {
				beginDate = QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[0].trim()));
				endDate = QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[1].trim()));
			} else {
				endDate = QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[0].trim()));
				beginDate = QwyUtil.fmyyyyMMdd.format(QwyUtil.addDaysFromOldDate(new Date(QwyUtil.fmMMddyyyy.parse(time[0].trim()).getTime()), -30).getTime());
			}
		}
		createYunyingQueryCountSql(yunyingQueryCountSql, beginDate, endDate);

		List objList = dao.LoadAllSql(yunyingQueryCountSql.toString(), null);
		yunyingQueryCountSql.setLength(0);
		Object[] array = (Object[]) objList.get(0);

		// 激活用户//
		or.setJhUser(array[6] == null ? "0" : array[6] + "");
		// 还款金额
		or.setHkje(QwyUtil.calcNumber(array[7] == null ? "0" : array[7] + "", 100, "/", 2).toString());
		// 支付利息
		or.setPayLx(QwyUtil.calcNumber(array[8] == null ? "0" : array[8] + "", 100, "/", 2).toString());
		// 新增二次投资
		or.setXzectz(array[10] == null ? "0" : array[10] + "");
		// 购买的总份数
		or.setAllcopies(QwyUtil.calcNumber(array[4] == null ? "0" : array[4] + "", 100, "/", 2).toString());
		// 投资人数
		or.setInsusercount(array[0] == null ? "0" : array[0] + "");
		// 投资次数
		or.setInscount(array[1] == null ? "0" : array[1] + "");
		// 复投次数
		or.setFtinscs(array[2] == null ? "0" : array[2] + "");
		// 复投人数
		or.setFtinsrs(array[3] == null ? "0" : array[3] + "");
		// 首投人数
		or.setStinsrs(array[5] == null ? "0" : array[5] + "");
		// 首投总额
		or.setStze(QwyUtil.calcNumber(array[9] == null ? "0" : array[9] + "", 100, "/", 2).toString());
		// 人均投资
		if (or.getInsusercount().equals("0") || or.getAllcopies().equals("0")) {
			or.setRjtz("0.00");
		} else {
			or.setRjtz(QwyUtil.calcNumber(or.getAllcopies(), or.getInsusercount(), "/", 2).toString());
		}
		// 首投次数
		or.setStcs(or.getStinsrs());
		// 新增二次投资率
		if (or.getStinsrs().equals("0") || or.getXzectz().equals("0")) {
			or.setXzectzPercentage("0.00");
		} else {
			BigDecimal zhl = QwyUtil.calcNumber(or.getXzectz(), or.getStinsrs(), "/");
			or.setXzectzPercentage(QwyUtil.calcNumber(zhl, 0.01, "/", 2).toString());
		}
		// 复投率
		if (or.getInsusercount().equals("0") || or.getFtinsrs().equals("0")) {
			or.setFtl("0");
		} else {
			BigDecimal zhl = QwyUtil.calcNumber(or.getFtinsrs(), or.getInsusercount(), "/");
			or.setFtl(QwyUtil.calcNumber(zhl, 100, "*", 2).toString());
		}
		return or;
	}

	// public Operation tjOperation(List<Operation> converts) throws Exception{
	// Operation or=new Operation();
	// if(!QwyUtil.isNullAndEmpty(converts)){
	// for (Operation op : converts) {
	// //激活用户
	// if(QwyUtil.isNullAndEmpty(or.getJhUser()))
	// or.setJhUser("0");
	// //还款金额
	// if(QwyUtil.isNullAndEmpty(or.getHkje()))
	// or.setHkje("0");
	// //支付利息
	// if(QwyUtil.isNullAndEmpty(or.getPayLx()))
	// or.setPayLx("0");
	// //新增二次投资
	// if(QwyUtil.isNullAndEmpty(or.getXzectz()))
	// or.setXzectz("0");
	// //购买的总份数
	// if(QwyUtil.isNullAndEmpty(or.getAllcopies()))
	// or.setAllcopies("0");
	// //投资人数
	// if(QwyUtil.isNullAndEmpty(or.getInsusercount()))
	// or.setInsusercount("0");
	// //投资次数
	// if(QwyUtil.isNullAndEmpty(or.getInscount()))
	// or.setInscount("0");
	// //复投次数
	// if(QwyUtil.isNullAndEmpty(or.getFtinscs()))
	// or.setFtinscs("0");
	// //复投人数
	// if(QwyUtil.isNullAndEmpty(or.getFtinsrs()))
	// or.setFtinsrs("0");
	// //首投人数
	// if(QwyUtil.isNullAndEmpty(or.getStinsrs()))
	// or.setStinsrs("0");
	// //首投总额
	// if(QwyUtil.isNullAndEmpty(or.getStze()))
	// or.setStze("0");
	// or.setJhUser(QwyUtil.calcNumber(or.getJhUser(), op.getJhUser(), "+")+"");
	// or.setHkje(QwyUtil.calcNumber(or.getHkje(), op.getHkje(), "+")+"");
	// or.setPayLx(QwyUtil.calcNumber(or.getPayLx(), op.getPayLx(), "+")+"");
	// or.setXzectz(QwyUtil.calcNumber(or.getXzectz(), op.getXzectz(), "+")+"");
	// or.setStze(QwyUtil.calcNumber(or.getStze(), op.getStze(), "+")+"");
	// or.setAllcopies(QwyUtil.calcNumber(or.getAllcopies(), op.getAllcopies(),
	// "+")+"");
	// or.setInsusercount(QwyUtil.calcNumber(or.getInsusercount(),
	// op.getInsusercount(), "+")+"");
	// or.setInscount(QwyUtil.calcNumber(or.getInscount(), op.getInscount(),
	// "+")+"");
	// or.setFtinscs(QwyUtil.calcNumber(or.getFtinscs(), op.getFtinscs(),
	// "+")+"");
	// or.setFtinsrs(QwyUtil.calcNumber(or.getFtinsrs(), op.getFtinsrs(),
	// "+")+"");
	// or.setStinsrs(QwyUtil.calcNumber(or.getStinsrs(), op.getStinsrs(),
	// "+")+"");
	// if(or.getInsusercount().equals("0")||or.getAllcopies().equals("0")){
	// or.setRjtz("0.00");
	// }else{
	// or.setRjtz(QwyUtil.calcNumber(or.getAllcopies(), or.getInsusercount(),
	// "/", 2).toString());
	// }
	// }
	// or.setStcs(or.getStinsrs());
	// //新增二次投资率
	// if(or.getStinsrs().equals("0")||or.getXzectz().equals("0")){
	// or.setXzectzPercentage("0.00");
	// }else{
	// or.setXzectzPercentage(QwyUtil.calcNumber(or.getXzectz(),
	// or.getStinsrs(), "/", 2).toString());
	// }
	//
	// if(or.getInsusercount().equals("0")||or.getFtinsrs().equals("0")){
	// or.setFtl("0");
	// }else{
	// BigDecimal zhl=QwyUtil.calcNumber(or.getFtinsrs(), or.getInsusercount(),
	// "/");
	// or.setFtl(QwyUtil.calcNumber(zhl, 100, "*", 2).toString());
	// }
	// }
	// return or;
	// }

	private List<BackStatsOperateDay> toBackStatsOperateDay(List<Map<String, Object>> list) throws Exception {
		List<BackStatsOperateDay> returnList = new ArrayList<BackStatsOperateDay>();

		if (QwyUtil.isNullAndEmpty(list)) {
			return returnList;
		}

		for (Map<String, Object> map : list) {
			BackStatsOperateDay backStatsOperateDay = new BackStatsOperateDay();
			backStatsOperateDay = (BackStatsOperateDay) ObjectUtil.mapToObject(map, backStatsOperateDay);
			backStatsOperateDay.setDates(QwyUtil.fmyyyyMMdd.format(backStatsOperateDay.getDate()));

			// 人均投资
			backStatsOperateDay.setAvgInvestCentSum();
			// 新增二次投资率
			backStatsOperateDay.setNewTwoInvestRate();
			// 复投
			backStatsOperateDay.setReInvestRate();

			returnList.add(backStatsOperateDay);
		}

		return returnList;
	}

	/**
	 * 激活数
	 * 
	 * @param insertTime
	 *            激活时间
	 * @return
	 * @throws Exception
	 */
	public String activityCount(String insertTime) throws Exception {
		ArrayList<Object> list = new ArrayList<Object>();
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT COUNT(DISTINCT id) FROM activity ");
		if (!QwyUtil.isNullAndEmpty(insertTime)) {
			String[] time = QwyUtil.splitTime(insertTime);
			if (time.length > 1) {
				buffer.append(" WHERE DATE_FORMAT(insert_time, '%Y-%m-%d' )>= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[0])));
				buffer.append(" AND DATE_FORMAT(insert_time, '%Y-%m-%d' )< ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[1])));
			} else {
				buffer.append(" WHERE DATE_FORMAT(insert_time, '%Y-%m-%d' )>= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 00:00:00")));
				buffer.append(" AND DATE_FORMAT(insert_time, '%Y-%m-%d' )<= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 23:59:59")));
			}
		}
		Object object = dao.getSqlCount(buffer.toString(), list.toArray());
		if (!QwyUtil.isNullAndEmpty(object)) {
			return object + "";
		}
		return "0";

	}

	/**
	 * 投资人数
	 * 
	 * @param insertTime
	 *            投资时间
	 * @param registPlatform
	 *            平台
	 * @return
	 * @throws Exception
	 */
	public String insUsersCount(String insertTime, String registPlatform) throws Exception {
		ArrayList<Object> list = new ArrayList<Object>();
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT COUNT(DISTINCT ins.users_id) FROM  ");
		buffer.append(" investors ins   WHERE ins.investor_status IN ('1','2','3') ");
		if (!QwyUtil.isNullAndEmpty(insertTime)) {
			String[] time = QwyUtil.splitTime(insertTime);
			if (time.length > 1) {
				buffer.append(" AND DATE_FORMAT(ins.pay_time, '%Y-%m-%d' )>= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[0])));
				buffer.append(" AND DATE_FORMAT(ins.pay_time, '%Y-%m-%d' )<= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[1])));
			} else {
				buffer.append(" AND DATE_FORMAT(ins.pay_time, '%Y-%m-%d' )>= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 00:00:00")));
				buffer.append(" AND DATE_FORMAT(ins.pay_time, '%Y-%m-%d' )<= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 23:59:59")));
			}
		}
		buffer.append(" AND EXISTS (  ");
		buffer.append(" SELECT us.id FROM  Users us WHERE us.id=ins.users_id");
		if (!QwyUtil.isNullAndEmpty(registPlatform)) {
			buffer.append(" AND  us.regist_platform = ? ");
			list.add(registPlatform);
		}
		buffer.append(" )  ");
		Object object = dao.getSqlCount(buffer.toString(), list.toArray());
		if (!QwyUtil.isNullAndEmpty(object)) {
			return object + "";
		}
		return "0";
	}
	/*
	 * public String insUsersCount(String insertTime,String registPlatform)
	 * throws Exception{ ArrayList<Object> list=new ArrayList<Object>();
	 * StringBuffer buffer=new StringBuffer(); buffer.append(
	 * " SELECT COUNT(DISTINCT ins.users_id) FROM Users us "); buffer.append(
	 * " LEFT JOIN investors ins on ins.users_id = us.id   AND ins.investor_status IN ('1','2','3') "
	 * ); if(!QwyUtil.isNullAndEmpty(insertTime)){ String []
	 * time=QwyUtil.splitTime(insertTime); if(time.length>1){ buffer.append(
	 * " AND ins.pay_time>= ? "); list.add(QwyUtil.fmMMddyyyy.parse(time[0]));
	 * buffer.append(" AND ins.pay_time<= ? ");
	 * list.add(QwyUtil.fmMMddyyyy.parse(time[1])); }else{ buffer.append(
	 * " AND ins.pay_time>= ? ");
	 * list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
	 * buffer.append(" AND ins.pay_time<= ? ");
	 * list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59")); } }
	 * 
	 * if(!QwyUtil.isNullAndEmpty(registPlatform)){ buffer.append(
	 * " WHERE  us.regist_platform = ? "); list.add(registPlatform); } Object
	 * object=dao.getSqlCount(buffer.toString(),list.toArray());
	 * if(!QwyUtil.isNullAndEmpty(object)){ return object+""; } return "0"; }
	 */

	/**
	 * 绑定人数
	 * 
	 * @param insertTime
	 *            绑定时间
	 * @param registPlatform
	 *            平台
	 * @throws Exception
	 */
	public String bindUsersCount(String insertTime, String registPlatform) throws Exception {
		ArrayList<Object> list = new ArrayList<Object>();
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT COUNT(DISTINCT ac.users_id) FROM  ");
		buffer.append(" account ac WHERE ac.status ='0' ");
		if (!QwyUtil.isNullAndEmpty(insertTime)) {
			String[] time = QwyUtil.splitTime(insertTime);
			if (time.length > 1) {
				buffer.append(" AND DATE_FORMAT(ac.insert_time, '%Y-%m-%d' )>= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[0])));
				buffer.append(" AND DATE_FORMAT(ac.insert_time, '%Y-%m-%d' )<= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[1])));
			} else {
				buffer.append(" AND DATE_FORMAT(ac.insert_time, '%Y-%m-%d' )>= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 00:00:00")));
				buffer.append(" AND DATE_FORMAT(ac.insert_time, '%Y-%m-%d' )<= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 23:59:59")));
			}
		}
		buffer.append(" AND EXISTS (  ");
		buffer.append(" SELECT us.id FROM  Users us WHERE us.id=ac.users_id");
		if (!QwyUtil.isNullAndEmpty(registPlatform)) {
			buffer.append(" AND  us.regist_platform = ? ");
			list.add(registPlatform);
		}
		buffer.append(" )  ");
		Object object = dao.getSqlCount(buffer.toString(), list.toArray());
		if (!QwyUtil.isNullAndEmpty(object)) {
			return object + "";
		}
		return "0";

	}
	/*
	 * public String bindUsersCount(String insertTime,String registPlatform)
	 * throws Exception{ ArrayList<Object> list=new ArrayList<Object>();
	 * StringBuffer buffer=new StringBuffer(); buffer.append(
	 * " SELECT COUNT(DISTINCT ac.users_id) FROM Users us  "); buffer.append(
	 * " LEFT JOIN account ac on ac.users_id=us.id AND ac.status ='0' ");
	 * if(!QwyUtil.isNullAndEmpty(insertTime)){ String []
	 * time=QwyUtil.splitTime(insertTime); if(time.length>1){ buffer.append(
	 * " AND ac.insert_time>= ? "); list.add(QwyUtil.fmMMddyyyy.parse(time[0]));
	 * buffer.append(" AND ac.insert_time<= ? ");
	 * list.add(QwyUtil.fmMMddyyyy.parse(time[1])); }else{ buffer.append(
	 * " AND ac.insert_time>= ? ");
	 * list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
	 * buffer.append(" AND ac.insert_time<= ? ");
	 * list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59")); } }
	 * if(!QwyUtil.isNullAndEmpty(registPlatform)){ buffer.append(
	 * " WHERE  us.regist_platform = ? "); list.add(registPlatform); } Object
	 * object=dao.getSqlCount(buffer.toString(),list.toArray());
	 * if(!QwyUtil.isNullAndEmpty(object)){ return object+""; } return "0";
	 * 
	 * }
	 */

	/**
	 * 复投人数
	 * 
	 * @param insertTime
	 *            投资时间
	 * @param registPlatform
	 *            平台
	 * @throws Exception
	 */
	public String againInsUsersCount(String insertTime, String registPlatform) throws Exception {
		ArrayList<Object> list = new ArrayList<Object>();
		StringBuffer buffer = new StringBuffer();
		/*
		 * buffer.append(
		 * " SELECT COUNT(DISTINCT users_id) FROM investors WHERE investor_status IN ('1','2','3') AND pay_time NOT IN ( "
		 * ); buffer.append(
		 * " SELECT MIN(ins.pay_time) FROM investors ins  GROUP BY users_id ");
		 * buffer.append(" ) ");
		 */
		buffer.append(" SELECT COUNT(DISTINCT ins.users_id) FROM ");
		buffer.append(" investors ins WHERE ins.investor_status IN ('1','2','3') ");
		buffer.append(" AND ins.pay_time NOT IN (  ");
		buffer.append(" SELECT MIN(ins.pay_time) FROM investors ins  GROUP BY users_id ");
		buffer.append(" ) ");
		if (!QwyUtil.isNullAndEmpty(insertTime)) {
			String[] time = QwyUtil.splitTime(insertTime);
			if (time.length > 1) {
				buffer.append(" AND DATE_FORMAT(ins.pay_time, '%Y-%m-%d' )>= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[0])));
				buffer.append(" AND DATE_FORMAT(ins.pay_time, '%Y-%m-%d' )<= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[1])));
			} else {
				buffer.append(" AND DATE_FORMAT(ins.pay_time, '%Y-%m-%d' )>= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 00:00:00")));
				buffer.append(" AND DATE_FORMAT(ins.pay_time, '%Y-%m-%d' )<= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 23:59:59")));
			}
		}
		buffer.append(" AND EXISTS (  ");
		buffer.append(" SELECT us.id FROM  Users us WHERE us.id=ins.users_id");
		if (!QwyUtil.isNullAndEmpty(registPlatform)) {
			buffer.append(" AND  us.regist_platform = ? ");
			list.add(registPlatform);
		}
		buffer.append(" )  ");
		Object object = dao.getSqlCount(buffer.toString(), list.toArray());
		if (!QwyUtil.isNullAndEmpty(object)) {
			return object + "";
		}
		return "0";

	}

	// public String againInsUsersCount(String insertTime,String registPlatform)
	// throws Exception{
	// ArrayList<Object> list=new ArrayList<Object>();
	// StringBuffer buffer=new StringBuffer();
	/// * buffer.append(" SELECT COUNT(DISTINCT users_id) FROM investors WHERE
	// investor_status IN ('1','2','3') AND pay_time NOT IN ( ");
	// buffer.append(" SELECT MIN(ins.pay_time) FROM investors ins GROUP BY
	// users_id ");
	// buffer.append(" ) ");*/
	// buffer.append(" SELECT COUNT(DISTINCT ins.users_id) FROM Users us ");
	// buffer.append(" LEFT JOIN investors ins on ins.users_id=us.id AND
	// ins.investor_status IN ('1','2','3') ");
	// buffer.append(" AND pay_time NOT IN ( ");
	// buffer.append(" SELECT MIN(ins.pay_time) FROM investors ins GROUP BY
	// users_id ");
	// buffer.append(" ) ");
	// if(!QwyUtil.isNullAndEmpty(insertTime)){
	// String [] time=QwyUtil.splitTime(insertTime);
	// if(time.length>1){
	// buffer.append(" AND ins.pay_time>= ? ");
	// list.add(QwyUtil.fmMMddyyyy.parse(time[0]));
	// buffer.append(" AND ins.pay_time<= ? ");
	// list.add(QwyUtil.fmMMddyyyy.parse(time[1]));
	// }else{
	// buffer.append(" AND ins.pay_time>= ? ");
	// list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
	// buffer.append(" AND ins.pay_time<= ? ");
	// list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59"));
	// }
	// }
	// if(!QwyUtil.isNullAndEmpty(registPlatform)){
	// buffer.append(" WHERE us.regist_platform = ? ");
	// list.add(registPlatform);
	// }
	// Object object=dao.getSqlCount(buffer.toString(),list.toArray());
	// if(!QwyUtil.isNullAndEmpty(object)){
	// return object+"";
	// }
	// return "0";
	//
	// }
	/**
	 * 复投次数
	 * 
	 * @param insertTime
	 *            投资时间
	 * @param registPlatform
	 *            平台
	 * @throws Exception
	 */
	public String againInsCount(String insertTime, String registPlatform) throws Exception {
		ArrayList<Object> list = new ArrayList<Object>();
		StringBuffer buffer = new StringBuffer();
		/*
		 * buffer.append(
		 * " SELECT COUNT(DISTINCT users_id) FROM investors WHERE investor_status IN ('1','2','3') AND pay_time NOT IN ( "
		 * ); buffer.append(
		 * " SELECT MIN(ins.pay_time) FROM investors ins  GROUP BY users_id ");
		 * buffer.append(" ) ");
		 */
		buffer.append(" SELECT COUNT(DISTINCT ins.id) FROM ");
		buffer.append(" investors ins WHERE ins.investor_status IN ('1','2','3') ");
		buffer.append(" AND ins.pay_time NOT IN (  ");
		buffer.append(" SELECT MIN(ins.pay_time) FROM investors ins  GROUP BY users_id ");
		buffer.append(" ) ");
		if (!QwyUtil.isNullAndEmpty(insertTime)) {
			String[] time = QwyUtil.splitTime(insertTime);
			if (time.length > 1) {
				buffer.append(" AND DATE_FORMAT(ins.pay_time, '%Y-%m-%d' )>= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[0])));
				buffer.append(" AND DATE_FORMAT(ins.pay_time, '%Y-%m-%d' )<= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[1])));
			} else {
				buffer.append(" AND DATE_FORMAT(ins.pay_time, '%Y-%m-%d' )>= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 00:00:00")));
				buffer.append(" AND DATE_FORMAT(ins.pay_time, '%Y-%m-%d' )<= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 23:59:59")));
			}
		}
		buffer.append(" AND EXISTS (  ");
		buffer.append(" SELECT us.id FROM  Users us WHERE us.id=ins.users_id");
		if (!QwyUtil.isNullAndEmpty(registPlatform)) {
			buffer.append(" AND  us.regist_platform = ? ");
			list.add(registPlatform);
		}
		buffer.append(" )  ");
		Object object = dao.getSqlCount(buffer.toString(), list.toArray());
		if (!QwyUtil.isNullAndEmpty(object)) {
			return object + "";
		}
		return "0";

	}

	// public String againInsCount(String insertTime,String registPlatform)
	// throws Exception{
	// ArrayList<Object> list=new ArrayList<Object>();
	// StringBuffer buffer=new StringBuffer();
	// buffer.append(" SELECT COUNT(DISTINCT ins.id) FROM Users us ");
	// buffer.append(" LEFT JOIN investors ins on ins.users_id=us.id AND
	// ins.investor_status IN ('1','2','3') ");
	// buffer.append(" AND pay_time NOT IN ( ");
	// buffer.append(" SELECT MIN(ins.pay_time) FROM investors ins GROUP BY
	// users_id ");
	// buffer.append(" ) ");
	// if(!QwyUtil.isNullAndEmpty(insertTime)){
	// String [] time=QwyUtil.splitTime(insertTime);
	// if(time.length>1){
	// buffer.append(" AND ins.pay_time>= ? ");
	// list.add(QwyUtil.fmMMddyyyy.parse(time[0]));
	// buffer.append(" AND ins.pay_time<= ? ");
	// list.add(QwyUtil.fmMMddyyyy.parse(time[1]));
	// }else{
	// buffer.append(" AND ins.pay_time>= ? ");
	// list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
	// buffer.append(" AND ins.pay_time<= ? ");
	// list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59"));
	// }
	// }
	// if(!QwyUtil.isNullAndEmpty(registPlatform)){
	// buffer.append(" WHERE us.regist_platform = ? ");
	// list.add(registPlatform);
	// }
	// Object object=dao.getSqlCount(buffer.toString(),list.toArray());
	// if(!QwyUtil.isNullAndEmpty(object)){
	// return object+"";
	// }
	// return "0";
	//
	// }
	// public String againInsCount(String insertTime,String registPlatform)
	// throws Exception{
	// ArrayList<Object> list=new ArrayList<Object>();
	// StringBuffer buffer=new StringBuffer();
	// /*buffer.append(" SELECT COUNT(DISTINCT id) FROM investors WHERE
	// investor_status IN ('1','2','3') AND pay_time NOT IN ( ");
	// buffer.append(" SELECT MIN(ins.pay_time) FROM investors ins GROUP BY
	// users_id ");
	// buffer.append(" ) ");*/
	// buffer.append(" SELECT COUNT(DISTINCT ins.id) FROM Users us ");
	// buffer.append(" LEFT JOIN investors ins on ins.users_id=us.id AND
	// ins.investor_status IN ('1','2','3') ");
	// buffer.append(" AND pay_time NOT IN ( ");
	// buffer.append(" SELECT MIN(ins.pay_time) FROM investors ins GROUP BY
	// users_id ");
	// buffer.append(" ) ");
	// if(!QwyUtil.isNullAndEmpty(insertTime)){
	// String [] time=QwyUtil.splitTime(insertTime);
	// if(time.length>1){
	// buffer.append(" AND ins.pay_time>= ? ");
	// list.add(QwyUtil.fmMMddyyyy.parse(time[0]));
	// buffer.append(" AND ins.pay_time<= ? ");
	// list.add(QwyUtil.fmMMddyyyy.parse(time[1]));
	// }else{
	// buffer.append(" AND ins.pay_time>= ? ");
	// list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
	// buffer.append(" AND ins.pay_time<= ? ");
	// list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59"));
	// }
	// }
	// if(!QwyUtil.isNullAndEmpty(registPlatform)){
	// buffer.append(" WHERE us.regist_platform = ? ");
	// list.add(registPlatform);
	// }
	// Object object=dao.getSqlCount(buffer.toString(),list.toArray());
	// if(!QwyUtil.isNullAndEmpty(object)){
	// return object+"";
	// }
	// return "0";
	//
	// }
	/**
	 * 投资金额
	 * 
	 * @param insertTime
	 *            投资时间
	 * @param registPlatform
	 *            平台
	 * @throws Exception
	 */
	public String insMoney(String insertTime, String registPlatform) throws Exception {
		ArrayList<Object> list = new ArrayList<Object>();
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT SUM(ins.in_money) FROM investors ins  ");
		buffer.append(" WHERE ins.investor_status IN ('1','2','3') ");
		if (!QwyUtil.isNullAndEmpty(insertTime)) {
			String[] time = QwyUtil.splitTime(insertTime);
			if (time.length > 1) {
				buffer.append(" AND DATE_FORMAT(ins.pay_time, '%Y-%m-%d' )>= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[0])));
				buffer.append(" AND DATE_FORMAT(ins.pay_time, '%Y-%m-%d' )<= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[1])));
			} else {
				buffer.append(" AND DATE_FORMAT(ins.pay_time, '%Y-%m-%d' )>= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 00:00:00")));
				buffer.append(" AND DATE_FORMAT(ins.pay_time, '%Y-%m-%d' )<= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 23:59:59")));
			}
		}
		buffer.append(" AND EXISTS (  ");
		buffer.append(" SELECT us.id FROM  Users us WHERE us.id=ins.users_id");
		if (!QwyUtil.isNullAndEmpty(registPlatform)) {
			buffer.append(" AND  us.regist_platform = ? ");
			list.add(registPlatform);
		}
		buffer.append(" )  ");
		Object object = dao.getSqlCount(buffer.toString(), list.toArray());
		if (!QwyUtil.isNullAndEmpty(object)) {
			return object + "";
		}
		return "0";

	}

	// public String insMoney(String insertTime,String registPlatform) throws
	// Exception{
	// ArrayList<Object> list=new ArrayList<Object>();
	// StringBuffer buffer=new StringBuffer();
	//// buffer.append(" SELECT SUM(in_money) FROM investors WHERE
	// investor_status IN ('1','2','3') ");
	// buffer.append(" SELECT SUM(ins.in_money) FROM Users us ");
	// buffer.append(" LEFT JOIN investors ins on ins.users_id=us.id AND
	// ins.investor_status IN ('1','2','3') ");
	// if(!QwyUtil.isNullAndEmpty(insertTime)){
	// String [] time=QwyUtil.splitTime(insertTime);
	// if(time.length>1){
	// buffer.append(" AND ins.pay_time>= ? ");
	// list.add(QwyUtil.fmMMddyyyy.parse(time[0]));
	// buffer.append(" AND ins.pay_time<= ? ");
	// list.add(QwyUtil.fmMMddyyyy.parse(time[1]));
	// }else{
	// buffer.append(" AND ins.pay_time>= ? ");
	// list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
	// buffer.append(" AND ins.pay_time<= ? ");
	// list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59"));
	// }
	// }
	// if(!QwyUtil.isNullAndEmpty(registPlatform)){
	// buffer.append(" WHERE us.regist_platform = ? ");
	// list.add(registPlatform);
	// }
	// Object object=dao.getSqlCount(buffer.toString(),list.toArray());
	// if(!QwyUtil.isNullAndEmpty(object)){
	// return object+"";
	// }
	// return "0";
	//
	// }
	/**
	 * 投资次数
	 * 
	 * @param insertTime
	 *            投资时间
	 * @param registPlatform
	 *            平台
	 * @throws Exception
	 */
	public String insCount(String insertTime, String registPlatform) throws Exception {
		ArrayList<Object> list = new ArrayList<Object>();
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT COUNT(DISTINCT id) FROM investors ins  ");
		buffer.append(" WHERE ins.investor_status IN ('1','2','3') ");
		if (!QwyUtil.isNullAndEmpty(insertTime)) {
			String[] time = QwyUtil.splitTime(insertTime);
			if (time.length > 1) {
				buffer.append(" AND DATE_FORMAT(ins.pay_time, '%Y-%m-%d' )>= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[0])));
				buffer.append(" AND DATE_FORMAT(ins.pay_time, '%Y-%m-%d' )<= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[1])));
			} else {
				buffer.append(" AND DATE_FORMAT(ins.pay_time, '%Y-%m-%d' )>= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 00:00:00")));
				buffer.append(" AND DATE_FORMAT(ins.pay_time, '%Y-%m-%d' )<= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 23:59:59")));
			}
		}
		buffer.append(" AND EXISTS (  ");
		buffer.append(" SELECT us.id FROM  Users us WHERE us.id=ins.users_id");
		if (!QwyUtil.isNullAndEmpty(registPlatform)) {
			buffer.append(" AND  us.regist_platform = ? ");
			list.add(registPlatform);
		}
		buffer.append(" )  ");
		Object object = dao.getSqlCount(buffer.toString(), list.toArray());
		if (!QwyUtil.isNullAndEmpty(object)) {
			return object + "";
		}
		return "0";

	}

	// public String insCount(String insertTime,String registPlatform) throws
	// Exception{
	// ArrayList<Object> list=new ArrayList<Object>();
	// StringBuffer buffer=new StringBuffer();
	// //buffer.append(" SELECT COUNT(DISTINCT id) FROM investors WHERE
	// investor_status IN ('1','2','3') ");
	// buffer.append(" SELECT COUNT(DISTINCT ins.id) FROM Users us ");
	// buffer.append(" LEFT JOIN investors ins on ins.users_id=us.id AND
	// ins.investor_status IN ('1','2','3') ");
	// if(!QwyUtil.isNullAndEmpty(insertTime)){
	// String [] time=QwyUtil.splitTime(insertTime);
	// if(time.length>1){
	// buffer.append(" AND ins.pay_time>= ? ");
	// list.add(QwyUtil.fmMMddyyyy.parse(time[0]));
	// buffer.append(" AND ins.pay_time<= ? ");
	// list.add(QwyUtil.fmMMddyyyy.parse(time[1]));
	// }else{
	// buffer.append(" AND ins.pay_time>= ? ");
	// list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
	// buffer.append(" AND ins.pay_time<= ? ");
	// list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59"));
	// }
	// }
	// if(!QwyUtil.isNullAndEmpty(registPlatform)){
	// buffer.append(" WHERE us.regist_platform = ? ");
	// list.add(registPlatform);
	// }
	// Object object=dao.getSqlCount(buffer.toString(),list.toArray());
	// if(!QwyUtil.isNullAndEmpty(object)){
	// return object+"";
	// }
	// return "0";
	//
	// }
	/**
	 * 注册投资人数
	 * 
	 * @param insertTime
	 *            投资时间
	 * @param registPlatform
	 *            平台
	 * @throws Exception
	 */
	public String regInsUsersCount(String insertTime, String registPlatform) throws Exception {
		ArrayList<Object> list = new ArrayList<Object>();
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT COUNT(DISTINCT ins.users_id) FROM users us ");
		buffer.append(" LEFT JOIN investors as ins  ON ins.users_id = us.id ");
		buffer.append(" AND DATE_FORMAT( us.insert_time, '%Y-%m-%d' ) = DATE_FORMAT( ins.pay_time, '%Y-%m-%d' ) ");
		buffer.append(" AND ins.investor_status in ('1','2','3') ");
		buffer.append(" WHERE 1=1 ");
		if (!QwyUtil.isNullAndEmpty(insertTime)) {
			String[] time = QwyUtil.splitTime(insertTime);
			if (time.length > 1) {
				buffer.append(" AND DATE_FORMAT(us.insert_time, '%Y-%m-%d' )>= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[0])));
				buffer.append(" AND DATE_FORMAT(us.insert_time, '%Y-%m-%d' )<= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[1])));
			} else {
				buffer.append(" AND DATE_FORMAT(us.insert_time, '%Y-%m-%d' )>= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 00:00:00")));
				buffer.append(" AND DATE_FORMAT(us.insert_time, '%Y-%m-%d' )<= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 23:59:59")));
			}
		}
		if (!QwyUtil.isNullAndEmpty(registPlatform)) {
			buffer.append(" AND  us.regist_platform = ? ");
			list.add(registPlatform);
		}
		Object object = dao.getSqlCount(buffer.toString(), list.toArray());
		if (!QwyUtil.isNullAndEmpty(object)) {
			return object + "";
		}
		return "0";

	}

	/**
	 * 注册投资金额
	 * 
	 * @param insertTime
	 *            投资时间
	 * @param registPlatform
	 *            平台
	 * @throws Exception
	 */
	public String regInsMoney(String insertTime, String registPlatform) throws Exception {
		ArrayList<Object> list = new ArrayList<Object>();
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT SUM(ins.in_money) FROM users us ");
		buffer.append(" LEFT JOIN investors as ins  ON ins.users_id = us.id ");
		buffer.append(" AND DATE_FORMAT( us.insert_time, '%Y-%m-%d' ) = DATE_FORMAT( ins.pay_time, '%Y-%m-%d' ) ");
		buffer.append(" AND ins.investor_status in ('1','2','3') ");
		buffer.append(" WHERE 1=1 ");
		if (!QwyUtil.isNullAndEmpty(insertTime)) {
			String[] time = QwyUtil.splitTime(insertTime);
			if (time.length > 1) {
				buffer.append(" AND DATE_FORMAT(us.insert_time, '%Y-%m-%d' )>= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[0])));
				buffer.append(" AND DATE_FORMAT(us.insert_time, '%Y-%m-%d' )<= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[1])));
			} else {
				buffer.append(" AND DATE_FORMAT(us.insert_time, '%Y-%m-%d' )>= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 00:00:00")));
				buffer.append(" AND DATE_FORMAT(us.insert_time, '%Y-%m-%d' )<= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 23:59:59")));
			}
		}
		if (!QwyUtil.isNullAndEmpty(registPlatform)) {
			buffer.append(" AND  us.regist_platform = ? ");
			list.add(registPlatform);
		}
		Object object = dao.getSqlCount(buffer.toString(), list.toArray());
		if (!QwyUtil.isNullAndEmpty(object)) {
			return object + "";
		}
		return "0";

	}

	/**
	 * 还款金额
	 * 
	 * @param insertTime
	 *            还款时间
	 * @throws Exception
	 */
	public String payMoney(String insertTime) throws Exception {
		ArrayList<Object> list = new ArrayList<Object>();
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT SUM(pay_money) FROM interest_details WHERE return_time <DATE_FORMAT( DATE_ADD(NOW(),INTERVAL 1 DAY), '%Y-%m-%d' ) ");
		if (!QwyUtil.isNullAndEmpty(insertTime)) {
			String[] time = QwyUtil.splitTime(insertTime);
			if (time.length > 1) {
				buffer.append(" AND DATE_FORMAT(return_time, '%Y-%m-%d' )>= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[0])));
				buffer.append(" AND DATE_FORMAT(return_time, '%Y-%m-%d' )<= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[1])));
			} else {
				buffer.append(" AND DATE_FORMAT(return_time, '%Y-%m-%d' )>= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 00:00:00")));
				buffer.append(" AND DATE_FORMAT(return_time, '%Y-%m-%d' )<= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 23:59:59")));
			}
		}
		Object object = dao.getSqlCount(buffer.toString(), list.toArray());
		if (!QwyUtil.isNullAndEmpty(object)) {
			return object + "";
		}
		return "0";

	}

	/**
	 * 支付利息
	 * 
	 * @param insertTime
	 *            还款时间
	 * @throws Exception
	 */
	public String payInterest(String insertTime) throws Exception {
		ArrayList<Object> list = new ArrayList<Object>();
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT SUM(pay_interest) FROM interest_details WHERE return_time <DATE_FORMAT( DATE_ADD(NOW(),INTERVAL 1 DAY), '%Y-%m-%d' ) ");
		if (!QwyUtil.isNullAndEmpty(insertTime)) {
			String[] time = QwyUtil.splitTime(insertTime);
			if (time.length > 1) {
				buffer.append(" AND DATE_FORMAT(return_time, '%Y-%m-%d' )>= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[0])));
				buffer.append(" AND DATE_FORMAT(return_time, '%Y-%m-%d' )<= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[1])));
			} else {
				buffer.append(" AND DATE_FORMAT(return_time, '%Y-%m-%d' )>= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 00:00:00")));
				buffer.append(" AND DATE_FORMAT(return_time, '%Y-%m-%d' )<= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 23:59:59")));
			}
		}
		Object object = dao.getSqlCount(buffer.toString(), list.toArray());
		if (!QwyUtil.isNullAndEmpty(object)) {
			return object + "";
		}
		return "0";

	}

	/**
	 * 新增二次投资
	 * 
	 * @param insertTime
	 *            投资时间
	 * @throws Exception
	 */
	public String newAgainIns(String insertTime) throws Exception {
		ArrayList<Object> list = new ArrayList<Object>();
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT COUNT(*) FROM investors ins ");
		buffer.append(" LEFT JOIN ( ");
		buffer.append(" SELECT MIN(ins.pay_time) as date,ins.users_id as users_id FROM investors ins  GROUP BY users_id ");
		buffer.append(" ) t ON DATE_FORMAT( t.date, '%Y-%m-%d' ) = DATE_FORMAT( ins.pay_time, '%Y-%m-%d' ) AND ins.users_id = t.users_id  ");
		buffer.append(" WHERE ins.pay_time not in (t.date) ");
		if (!QwyUtil.isNullAndEmpty(insertTime)) {
			String[] time = QwyUtil.splitTime(insertTime);
			if (time.length > 1) {
				buffer.append(" AND DATE_FORMAT(ins.pay_time, '%Y-%m-%d' )>= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[0])));
				buffer.append(" AND DATE_FORMAT(ins.pay_time, '%Y-%m-%d' )<= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[1])));
			} else {
				buffer.append(" AND DATE_FORMAT(ins.pay_time, '%Y-%m-%d' )>= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 00:00:00")));
				buffer.append(" AND DATE_FORMAT(ins.pay_time, '%Y-%m-%d' )<= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 23:59:59")));
			}
		}
		Object object = dao.getSqlCount(buffer.toString(), list.toArray());
		if (!QwyUtil.isNullAndEmpty(object)) {
			return object + "";
		}
		return "0";

	}

	/**
	 * 首投人数
	 * 
	 * @param insertTime
	 *            首投时间
	 * @param registPlatform
	 *            平台
	 * @throws Exception
	 */
	public String firstInsUserCount(String insertTime, String registPlatform) throws Exception {
		ArrayList<Object> list = new ArrayList<Object>();
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT COUNT(DISTINCT ivs.users_id) FROM users us  ");
		buffer.append(" LEFT JOIN investors ivs on us.id = ivs.users_id ");
		buffer.append(" AND ivs.investor_status in ('1','2','3') ");
		buffer.append(" AND ivs.pay_time in ( ");
		buffer.append("  SELECT MIN(ins.pay_time) as date FROM investors ins  WHERE ins.investor_status in ('1','2','3') GROUP BY users_id ) ");
		if (!QwyUtil.isNullAndEmpty(insertTime)) {
			String[] time = QwyUtil.splitTime(insertTime);
			if (time.length > 1) {
				buffer.append(" AND DATE_FORMAT(ivs.pay_time, '%Y-%m-%d' )>= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[0])));
				buffer.append(" AND DATE_FORMAT(ivs.pay_time, '%Y-%m-%d' )<= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[1])));
			} else {
				buffer.append(" AND DATE_FORMAT(ivs.pay_time, '%Y-%m-%d' )>= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 00:00:00")));
				buffer.append(" AND DATE_FORMAT(ivs.pay_time, '%Y-%m-%d' )<= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 23:59:59")));
			}
		}
		if (!QwyUtil.isNullAndEmpty(registPlatform)) {
			buffer.append(" AND  regist_platform = ? ");
			list.add(registPlatform);
		}
		Object object = dao.getSqlCount(buffer.toString(), list.toArray());
		if (!QwyUtil.isNullAndEmpty(object)) {
			return object + "";
		}
		return "0";
	}
	// public String firstInsUserCount(String insertTime,String registPlatform)
	// throws Exception{
	// ArrayList<Object> list=new ArrayList<Object>();
	// StringBuffer buffer=new StringBuffer();
	// buffer.append(" SELECT COUNT(DISTINCT ivs.users_id) FROM users us ");
	// buffer.append(" LEFT JOIN investors ivs on us.id = ivs.users_id ");
	// buffer.append(" AND ivs.investor_status in ('1','2','3') ");
	// buffer.append(" AND ivs.pay_time in ( ");
	// buffer.append(" SELECT MIN(ins.pay_time) as date FROM investors ins WHERE
	// ins.investor_status in ('1','2','3') GROUP BY users_id ) ");
	// if(!QwyUtil.isNullAndEmpty(insertTime)){
	// String [] time=QwyUtil.splitTime(insertTime);
	// if(time.length>1){
	// buffer.append(" AND ivs.pay_time>= ? ");
	// list.add(QwyUtil.fmMMddyyyy.parse(time[0]));
	// buffer.append(" AND ivs.pay_time<= ? ");
	// list.add(QwyUtil.fmMMddyyyy.parse(time[1]));
	// }else{
	// buffer.append(" AND ivs.pay_time>= ? ");
	// list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
	// buffer.append(" AND ivs.pay_time<= ? ");
	// list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59"));
	// }
	// }
	// if(!QwyUtil.isNullAndEmpty(registPlatform)){
	// buffer.append(" AND regist_platform = ? ");
	// list.add(registPlatform);
	// }
	// Object object=dao.getSqlCount(buffer.toString(),list.toArray());
	// if(!QwyUtil.isNullAndEmpty(object)){
	// return object+"";
	// }
	// return "0";
	// }

	/**
	 * 首投金额
	 * 
	 * @param insertTime
	 *            首投时间
	 * @throws Exception
	 */
	public String firstInsMoney(String insertTime) throws Exception {
		ArrayList<Object> list = new ArrayList<Object>();
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT SUM(q.copies) from ( ");
		buffer.append(" SELECT MIN(ins.pay_time) as date,ins.in_money as copies FROM investors ins  GROUP BY users_id ");
		buffer.append(" )q ");
		if (!QwyUtil.isNullAndEmpty(insertTime)) {
			String[] time = QwyUtil.splitTime(insertTime);
			if (time.length > 1) {
				buffer.append(" WHERE DATE_FORMAT(q.date, '%Y-%m-%d' )>= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[0])));
				buffer.append(" AND DATE_FORMAT(q.date, '%Y-%m-%d' )<= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[1])));
			} else {
				buffer.append(" WHERE DATE_FORMAT(q.date, '%Y-%m-%d' )>= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 00:00:00")));
				buffer.append(" AND DATE_FORMAT(q.date, '%Y-%m-%d' )<= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 23:59:59")));
			}
		}
		Object object = dao.getSqlCount(buffer.toString(), list.toArray());
		if (!QwyUtil.isNullAndEmpty(object)) {
			return object + "";
		}
		return "0";
	}

	/**
	 * 注册人数
	 * 
	 * @param insertTime
	 *            注册时间
	 * @param registPlatform
	 *            平台
	 * @return
	 * @throws Exception
	 */
	public String regUsersCount(String insertTime, String registPlatform) throws Exception {
		ArrayList<Object> list = new ArrayList<Object>();
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT COUNT(DISTINCT id) FROM users ");
		buffer.append(" WHERE 1=1 ");
		if (!QwyUtil.isNullAndEmpty(insertTime)) {
			String[] time = QwyUtil.splitTime(insertTime);
			if (time.length > 1) {
				buffer.append(" AND DATE_FORMAT(insert_time, '%Y-%m-%d' )>= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[0])));
				buffer.append(" AND DATE_FORMAT(insert_time, '%Y-%m-%d' )<= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[1])));
			} else {
				buffer.append(" AND DATE_FORMAT(insert_time, '%Y-%m-%d' )>= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 00:00:00")));
				buffer.append(" AND DATE_FORMAT(insert_time, '%Y-%m-%d' )<= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 23:59:59")));
			}
		}
		if (!QwyUtil.isNullAndEmpty(registPlatform)) {
			buffer.append(" AND  regist_platform = ? ");
			list.add(registPlatform);
		}
		Object object = dao.getSqlCount(buffer.toString(), list.toArray());
		if (!QwyUtil.isNullAndEmpty(object)) {
			return object + "";
		}
		return "0";
	}

	/**
	 * 获取移动手机号码
	 * 
	 * @param pageSize
	 * @param currentPage
	 * @param pageUtil
	 */
	public PageUtil<Users> findByUsers(PageUtil<Users> pageUtil) {
		List<Users> ydlist = new ArrayList<Users>();
		StringBuffer buffer = new StringBuffer();
		buffer.append(" FROM Users ORDER BY insertTime DESC");
		pageUtil = dao.getByHqlAndHqlCount(pageUtil, buffer.toString(), buffer.toString(), null);
		// for (Users users : pageUtil.getList()) {
		// if(users.getUsernameJM().substring(0, 3).equals("134")
		// ||users.getUsernameJM().substring(0, 3).equals("135")
		// ||users.getUsernameJM().substring(0, 3).equals("136")
		// ||users.getUsernameJM().substring(0, 3).equals("137")
		// ||users.getUsernameJM().substring(0, 3).equals("138")
		// ||users.getUsernameJM().substring(0, 3).equals("139")
		// ||users.getUsernameJM().substring(0, 3).equals("159")
		// ||users.getUsernameJM().substring(0, 3).equals("150")
		// ||users.getUsernameJM().substring(0, 3).equals("151")
		// ||users.getUsernameJM().substring(0, 3).equals("158")
		// ||users.getUsernameJM().substring(0, 3).equals("157")
		// ||users.getUsernameJM().substring(0, 3).equals("178")
		// ||users.getUsernameJM().substring(0, 3).equals("188")
		// ||users.getUsernameJM().substring(0, 3).equals("187")
		// ||users.getUsernameJM().substring(0, 3).equals("152")
		// ||users.getUsernameJM().substring(0, 3).equals("182")
		// ||users.getUsernameJM().substring(0, 3).equals("147")
		//
		// ){
		// ydlist.add(users);
		// }
		// }
		// pageUtil.setList(ydlist);
		return pageUtil;
	}

	/**
	 * 获取移动手机号码
	 * 
	 * @param isBank
	 *            是否绑定银行卡 1绑定银行卡 2未绑定银行卡
	 * @param type
	 *            是否投资 1投资 2未投资
	 * @param pageSize
	 * @param currentPage
	 * @param pageUtil
	 */
	public List<String> findByUsers(String insertTime, String type, String isBank) {
		List<String> list = new ArrayList<String>();
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT phone FROM users_info WHERE (phone != '' AND  phone is NOT  NULL)");
		if (!QwyUtil.isNullAndEmpty(insertTime)) {
			buffer.append(" AND insert_time>='" + insertTime + "'");
		}
		if ("1".equals(type)) {
			buffer.append(" AND invest_count > 0");
		} else if ("2".equals(type)) {
			buffer.append(" AND invest_count = 0");
		}

		if ("2".equals(isBank)) {
			buffer.append(" AND is_bind_bank = '0'");
		} else if ("1".equals(isBank)) {
			buffer.append(" AND is_bind_bank = '1'");
		}

		buffer.append(" ORDER BY insert_time DESC");
		List<String> objects = dao.LoadAllSql(buffer.toString(), null);
		if (!QwyUtil.isNullAndEmpty(objects)) {
			for (String obj : objects) {
				list.add(DESEncrypt.jieMiUsername(obj));
			}
		}

		return list;
	}

	/**
	 * 获取移动手机号码
	 * 
	 * @param isBank
	 *            是否绑定银行卡 1绑定银行卡 2未绑定银行卡
	 * @param type
	 *            是否投资 1投资 2未投资
	 * @param pageSize
	 * @param currentPage
	 * @param pageUtil
	 */
	public List<Object[]> findByUsersInfo(String insertTime, String type, String isBank) {
		List<String> list = new ArrayList<String>();
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT ui.real_name,ui.phone,ui.sex,ui.age,us.province,us.city ");
		if ("1".equals(type)) {
			buffer.append(" ,SUM(ivs.in_money)  ");
		}

		buffer.append(" FROM users_info ui   ");
		buffer.append(" INNER JOIN users us on us.id=ui.users_id  ");
		if ("1".equals(type)) {
			buffer.append(" INNER JOIN investors ivs  on ivs.users_id=ui.users_id AND ivs.investor_status IN ('1','2','3') ");
		}
		buffer.append(" WHERE 1=1  ");

		if (!QwyUtil.isNullAndEmpty(insertTime)) {
			buffer.append(" AND ui.insert_time>='" + insertTime + "'");
		}
		if ("1".equals(type)) {
			buffer.append(" AND ui.invest_count > 0");
		} else if ("2".equals(type)) {
			buffer.append(" AND ui.invest_count = 0");
		}

		if ("2".equals(isBank)) {
			buffer.append(" AND ui.is_bind_bank = '0'");
		} else if ("1".equals(isBank)) {
			buffer.append(" AND ui.is_bind_bank = '1'");
		}
		if ("1".equals(type)) {
			buffer.append(" GROUP BY ivs.users_id  ");
		}
		List<Object[]> objects = dao.LoadAllSql(buffer.toString(), null);
		if (!QwyUtil.isNullAndEmpty(objects)) {
			return objects;
		}

		return objects;
	}

}
