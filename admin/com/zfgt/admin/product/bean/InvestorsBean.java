package com.zfgt.admin.product.bean;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.admin.product.dao.InvestorsDAO;
import com.zfgt.common.util.DESEncrypt;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.BackStatsOperateDay;
import com.zfgt.orm.Investors;
import com.zfgt.orm.PlatInvestors;
import com.zfgt.orm.PlatUser;

@Service
public class InvestorsBean {
	@Resource
	InvestorsDAO dao;
	private static Logger log = Logger.getLogger(InvestorsBean.class);

	/**
	 * 分页获取结算记录
	 * 
	 * @param pageUtil
	 *            分页工具类
	 * @param userame
	 *            用户名
	 * @param investorStatus
	 *            投资状态
	 * @param name
	 *            用户名
	 * @param insertTime
	 *            投资时间
	 * @param type
	 * @param productId
	 *            产品ID
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public PageUtil<Investors> findInvertorses(PageUtil<Investors> pageUtil, String productTitle, String investorStatus,
			String name, String insertTime, String type, String productId) throws Exception {
		List<Object> list = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer();
		hql.append(" FROM Investors ins WHERE 1 = 1 ");
		// hql.append(" AND in.users.usersInfo.realName = ?");
		// list.add(realName);
		if (!"all".equals(investorStatus)) {
			hql.append(" AND ins.investorStatus = ?");
			list.add(investorStatus);
		}
		if (!QwyUtil.isNullAndEmpty(name)) {
			hql.append(" AND ins.users.username = ? ");
			list.add(DESEncrypt.jiaMiUsername(name));
		}
		// 充值时间
		if (!QwyUtil.isNullAndEmpty(insertTime)) {
			String[] time = QwyUtil.splitTime(insertTime);
			if (time.length > 1) {
				hql.append(" AND ins.insertTime >= ? ");
				list.add(QwyUtil.fmMMddyyyy.parse(time[0] + " 00:00:00"));
				hql.append(" AND ins.insertTime <= ? ");
				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[1] + " 23:59:59"));
			} else {
				hql.append(" AND ins.insertTime >= ? ");
				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 00:00:00"));
				hql.append(" AND ins.insertTime <= ? ");
				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 23:59:59"));
			}
		}
		if (!QwyUtil.isNullAndEmpty(type) && type.equals("1")) {
			String[] time = QwyUtil.splitTime(insertTime);
			hql.append(" AND ins.users.insertTime >= ? ");
			list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 00:00:00"));
			hql.append(" AND ins.users.insertTime <= ? ");
			list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 23:59:59"));
		}

		if (!QwyUtil.isNullAndEmpty(productId)) {
			hql.append(" AND ins.productId = ? ");
			list.add(productId);
		}
		if (!QwyUtil.isNullAndEmpty(productTitle)) {
			hql.append(" AND ins.product.title like '%" + productTitle + "%' ");
		}
		hql.append(" ORDER BY ins.insertTime DESC ");
		return dao.getByHqlAndHqlCount(pageUtil, hql.toString(), hql.toString(), list.toArray());
	}

	/**
	 * 以日期分组查询充值记录
	 * 
	 * @param pageUtil
	 *            分页
	 * @param insertTime
	 *            时间段
	 * @return
	 * @throws ParseException
	 * @throws Exception
	 */
	public PageUtil<BackStatsOperateDay> findInverstorsRecordGroupByDate(PageUtil pageUtil, String insertTime)
			throws ParseException {

		List<Object> list = new ArrayList<Object>();
		StringBuffer buffer = new StringBuffer();
		buffer.append(" select us.date as date,sum(copiesSum) copiesSum ,sum(investCentSum ) investCentSum");
		buffer.append(", sum(couponCentSum ) couponCentSum  ");
		buffer.append(" FROM back_stats_operate_day us WHERE 1=1 ");

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
		List<BackStatsOperateDay> platUsers = toDateMoney(pageUtil.getList());
		pageUtil.setList(platUsers);

		return pageUtil;
	}

	/**
	 * 将数据转换为DateMoney
	 * 
	 * @throws ParseException
	 */
	private List<BackStatsOperateDay> toDateMoney(List<Object[]> list) throws ParseException {
		List<BackStatsOperateDay> backStatsOperateDayList = new ArrayList<BackStatsOperateDay>();
		if (!QwyUtil.isNullAndEmpty(list)) {
			for (Object[] obj : list) {
				BackStatsOperateDay dateMoney = new BackStatsOperateDay();
				dateMoney.setDate(QwyUtil.fmyyyyMMddHHmmss.parse(QwyUtil.fmyyyyMMddHHmmss.format(obj[0])));
				dateMoney.setCopiesSum(Integer.parseInt(obj[1] + ""));
				dateMoney.setInvestCentSum(Double.parseDouble(obj[2] + ""));
				dateMoney.setCouponCentSum(Double.parseDouble(obj[3] + ""));
				backStatsOperateDayList.add(dateMoney);
			}
		}
		return backStatsOperateDayList;
	}

	/**
	 * 根据日期统计用户注册并且投资人数及投资金额
	 * 
	 * @param insertTime
	 *            注册时间
	 * @return
	 */
	public PageUtil<PlatUser> findUsersCountByDate(PageUtil pageUtil, String insertTime) throws Exception {
		ArrayList<Object> list = new ArrayList<Object>();
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT DATE_FORMAT( dd.insert_time, '%Y-%m-%d' ) as date,   ");
		buffer.append(" t.userscount,t.buycopies,t.inscount ");
		buffer.append(" FROM dateday dd ");
		buffer.append(" LEFT JOIN ( ");
		buffer.append(" SELECT DATE_FORMAT( ins.insert_time, '%Y-%m-%d' ) as date ,    ");
		buffer.append(" COUNT(DISTINCT ins.users_id) as userscount, ");
		buffer.append(" SUM(ins.copies) as buycopies, ");
		buffer.append(" COUNT(ins.id) as inscount ");
		buffer.append(" FROM  investors ins ");
		buffer.append(" WHERE ins.investor_status in ('1','2','3')  ");
		buffer.append(" AND ins.id is not NULL  ");
		buffer.append(" AND ins.users_id in ( ");
		buffer.append(" SELECT id FROM  users as us ");
		buffer.append(" WHERE  DATE_FORMAT( us.insert_time, '%Y-%m-%d' ) = DATE_FORMAT( ins.pay_time, '%Y-%m-%d' ) ");
		buffer.append(" AND us.id=ins.users_id ");
		buffer.append(" ) ");
		buffer.append(" GROUP BY DATE_FORMAT( ins.pay_time, '%Y-%m-%d' )  ");
		buffer.append(" ORDER BY DATE_FORMAT( ins.pay_time, '%Y-%m-%d' ) DESC ");
		buffer.append(" ) t");
		buffer.append(" ON DATE_FORMAT( dd.insert_time, '%Y-%m-%d' ) = t.date ");
		buffer.append(" WHERE 1=1 ");
		// 充值时间
		if (!QwyUtil.isNullAndEmpty(insertTime)) {
			String[] time = QwyUtil.splitTime(insertTime);
			if (time.length > 1) {
				buffer.append(" AND dd.insert_time >= ? ");
				list.add(QwyUtil.fmMMddyyyy.parse(time[0]));
				buffer.append(" AND dd.insert_time <= ? ");
				list.add(QwyUtil.fmMMddyyyy.parse(time[1]));
			} else {
				buffer.append(" AND dd.insert_time >= ? ");
				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 00:00:00"));
				buffer.append(" AND dd.insert_time <= ? ");
				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 23:59:59"));
			}
		}
		buffer.append(" GROUP BY DATE_FORMAT( dd.insert_time, '%Y-%m-%d' ) ");
		buffer.append(" ORDER BY DATE_FORMAT( dd.insert_time, '%Y-%m-%d' ) DESC ");
		StringBuffer bufferCount = new StringBuffer();
		bufferCount.append(" SELECT COUNT(t.date)  ");
		bufferCount.append(" FROM (");
		bufferCount.append(buffer);
		bufferCount.append(") t");
		pageUtil = dao.getBySqlAndSqlCount(pageUtil, buffer.toString(), bufferCount.toString(), list.toArray());
		List<PlatUser> platUsers = toPlatUser(pageUtil.getList());
		pageUtil.setList(platUsers);
		return pageUtil;
	}

	private List<PlatInvestors> toMoney(List<Object[]> list) {
		List<PlatInvestors> platInverstors = new ArrayList<PlatInvestors>();
		if (!QwyUtil.isNullAndEmpty(list)) {
			for (Object[] object : list) {
				PlatInvestors plat = new PlatInvestors();
				plat.setUsername(object[0] == null ? "" : object[0] + "");

				plat.setCopies(!QwyUtil.isNullAndEmpty(object[1]) ? object[1] + "" : "0");
				plat.setInsMoney(!QwyUtil.isNullAndEmpty(object[2]) ? object[2] + "" : "0");
				plat.setCoupon(!QwyUtil.isNullAndEmpty(object[3]) ? object[3] + "" : "0");
				plat.setDate(object[4] + "");
				plat.setReal_name(!QwyUtil.isNullAndEmpty(object[5]) ? object[5] + "" : "");
				platInverstors.add(plat);
			}
		}
		return platInverstors;
	}

	@SuppressWarnings("unchecked")
	public PageUtil<PlatInvestors> loadInvestors(String name, String realname, String insertTime, PageUtil pageUtil) {
		try {
			ArrayList<Object> ob = new ArrayList<Object>();
			StringBuffer buff = new StringBuffer();

			buff.append(
					"SELECT	u.username  as username ,	SUM(ins.copies)  as copies ,	SUM(ins.in_money)  as in_money ,");
			buff.append(
					" SUM(ins.coupon)  as coupon ,ins.pay_time as pay_time , i.real_name AS real_name FROM	investors ins LEFT JOIN users u on ins.users_id = u.id ");
			buff.append("  LEFT JOIN users_info i ON i.users_id = u.id ");

			buff.append(" WHERE	investor_status IN ('1', '2', '3')");

			if (!QwyUtil.isNullAndEmpty(name)) {
				buff.append("AND u.username = ? ");
				// hql.append(" AND ins.product.title like '%"+productTitle+"%'
				// ");
				ob.add(DESEncrypt.jiaMiUsername(name));
			}
			if (!QwyUtil.isNullAndEmpty(realname)) {
				buff.append("AND i.real_name like '%" + realname + "%'");
			}
			if (!QwyUtil.isNullAndEmpty(insertTime)) {
				String[] time = QwyUtil.splitTime(insertTime);
				if (time.length > 1) {
					buff.append(" AND ins.pay_time >= ? ");
					ob.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 00:00:00"));
					buff.append(" AND ins.pay_time <= ? ");
					ob.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[1] + " 23:59:59"));
				} else {

					buff.append(" AND ins.pay_time >= ? ");
					ob.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 00:00:00"));
					buff.append(" AND ins.pay_time <= ? ");
					ob.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 23:59:59"));
				}

			}
			buff.append(" GROUP BY	ins.users_id");
			buff.append(" ORDER BY in_money DESC ");

			StringBuffer bufferCount = new StringBuffer();
			bufferCount.append(" SELECT COUNT(t.username)  ");
			bufferCount.append(" FROM (");
			bufferCount.append(buff);
			bufferCount.append(") t");
			// buff.append("ORDER BY fr.insert_time DESC ");
			pageUtil = dao.getBySqlAndSqlCount(pageUtil, buff.toString(), bufferCount.toString(), ob.toArray());

			List<PlatInvestors> platUsers = toMoney(pageUtil.getList());
			pageUtil.setList(platUsers);
			return pageUtil;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ", e);
		}
		return null;
	}

	/**
	 * 将数据转换为platUser形式
	 * 
	 * @param list
	 * @return
	 */
	private List<PlatUser> toPlatUser(List<Object[]> list) {
		List<PlatUser> platUsers = new ArrayList<PlatUser>();
		if (!QwyUtil.isNullAndEmpty(list)) {
			for (Object[] object : list) {
				PlatUser platUser = new PlatUser();
				platUser.setDate(object[0] + "");
				platUser.setUserscount(!QwyUtil.isNullAndEmpty(object[1]) ? object[1] + "" : "0");
				platUser.setInsMoney(!QwyUtil.isNullAndEmpty(object[2]) ? object[2] + "" : "0");
				platUsers.add(platUser);
			}
		}
		return platUsers;
	}

	/**
	 * 人数、人次、自己投入的金额、优惠券金额统计
	 * 
	 * @param targetDate
	 * @return
	 */
	public String investorsStatistics(String targetDate) {
		StringBuffer jsonData = new StringBuffer();
		// 人数统计
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(1) FROM (SELECT users_id FROM investors WHERE DATE_FORMAT('" + targetDate
				+ " 0','%Y-%m-%d %H') = DATE_FORMAT(pay_time,'%Y-%m-%d %H') GROUP BY users_id) tab1 ");
		for (int i = 1; i < 24; i++) {
			sql.append(" UNION ALL SELECT COUNT(1) FROM (SELECT users_id FROM investors WHERE DATE_FORMAT('"
					+ targetDate + " " + i
					+ "','%Y-%m-%d %H') = DATE_FORMAT(pay_time,'%Y-%m-%d %H') GROUP BY users_id) tab" + i);
		}
		jsonData.append("{");
		jsonData.append("\"personCount\":" + ListToStringArray(dao.LoadAllSql(sql.toString(), null)));

		// 人次统计
		sql.setLength(0);
		sql.append("SELECT COUNT(1) FROM investors WHERE DATE_FORMAT('" + targetDate
				+ " 0','%Y-%m-%d %H') = DATE_FORMAT(pay_time,'%Y-%m-%d %H')");
		for (int i = 1; i < 24; i++) {
			sql.append(" UNION ALL SELECT COUNT(1) FROM investors WHERE DATE_FORMAT('" + targetDate + " " + i
					+ "','%Y-%m-%d %H') = DATE_FORMAT(pay_time,'%Y-%m-%d %H')");
		}
		jsonData.append(",\"personTime\":" + ListToStringArray(dao.LoadAllSql(sql.toString(), null)));

		// 自己投入的金额统计
		sql.setLength(0);
		sql.append(
				"SELECT CASE WHEN SUM(in_money*0.01) IS NULL THEN 0 ELSE SUM(in_money*0.01) END AS sum_ FROM investors WHERE investor_status in ('1','2','3') and DATE_FORMAT('"
						+ targetDate + " 0','%Y-%m-%d %H') = DATE_FORMAT(pay_time,'%Y-%m-%d %H')");
		for (int i = 1; i < 24; i++) {
			sql.append(
					" UNION ALL SELECT CASE WHEN SUM(in_money*0.01) IS NULL THEN 0 ELSE SUM(in_money*0.01) END AS sum_ FROM investors WHERE DATE_FORMAT('"
							+ targetDate + " " + i + "','%Y-%m-%d %H') = DATE_FORMAT(pay_time,'%Y-%m-%d %H')");
		}
		jsonData.append(",\"inMoney\":" + ListToStringArray(dao.LoadAllSql(sql.toString(), null)));

		// 优惠券金额统计
		sql.setLength(0);
		sql.append(
				"SELECT CASE WHEN SUM(coupon*0.01) IS NULL THEN 0 ELSE SUM(coupon*0.01) END AS sum_ FROM investors WHERE investor_status in ('1','2','3') and DATE_FORMAT('"
						+ targetDate + " 0','%Y-%m-%d %H') = DATE_FORMAT(pay_time,'%Y-%m-%d %H')");
		for (int i = 1; i < 24; i++) {
			sql.append(
					" UNION ALL SELECT CASE WHEN SUM(coupon*0.01) IS NULL THEN 0 ELSE SUM(coupon*0.01) END AS sum_ FROM investors WHERE DATE_FORMAT('"
							+ targetDate + " " + i + "','%Y-%m-%d %H') = DATE_FORMAT(pay_time,'%Y-%m-%d %H')");
		}
		jsonData.append(",\"coupon\":" + ListToStringArray(dao.LoadAllSql(sql.toString(), null)));
		jsonData.append("}");
		return jsonData.toString();
	}

	/**
	 * 把list转成js数组
	 * 
	 * @param list
	 * @return
	 */
	public String ListToStringArray(List list) {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (int i = 0; i < list.size(); i++) {
			if (i == 0) {
				sb.append(list.get(i));
			} else {
				sb.append("," + list.get(i) + "");
			}

		}
		sb.append("]");
		return sb.toString();
	}
	
	/**查询对应渠道号的投资情况;
	 * @param channel 渠道号
	 * @param st 开始时间;
	 * @param et 结束时间;
	 * @return
	 */
	public List<Object[]> investChannel(String channel,String st,String et,int currentPage,int pageSize,String order){
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT (us.`username`), (SELECT CONCAT(usi.`real_name`,'_',usi.`invest_count`) FROM `users_info` usi WHERE usi.`users_id` = us.`id` )'username,investCount',");
		sb.append("(SELECT pro.`title` FROM `product` pro WHERE pro.`id` IN (iv.`product_id`))'title',(iv.pay_time) 'pay_time',");
		sb.append("iv.copies 'copies', iv.`in_money`*0.01 'in_money',iv.`coupon`*0.01 'coupon',us.province,us.regist_channel ");
		sb.append("FROM `users` us LEFT JOIN `investors` iv ON us.`id` =iv.`users_id`  WHERE   iv.`investor_status` IN ('1','2','3')" );
		if(!QwyUtil.isNullAndEmpty(channel))
			sb.append("AND us.`regist_channel` ='"+channel+"' ");
		if (!QwyUtil.isNullAndEmpty(st)) {
			sb.append(" AND iv.pay_time BETWEEN '");
			sb.append(st);
			sb.append("' AND '");
			sb.append(et + "' ");
		}
		if(!QwyUtil.isNullAndEmpty(order)){ 
			sb.append("ORDER BY ");
			sb.append(order);
			sb.append(", (us.regist_channel+0) DESC, us.username DESC");
		}
		else
			sb.append("ORDER BY  (us.regist_channel+0) DESC, us.username DESC, iv.pay_time DESC ");
		List<Object[]> list = (List<Object[]>)dao.findAdvListMapSql(sb.toString(), null,currentPage, pageSize);;//dao.LoadAllSql(sb.toString(), null);
		//dao.findAdvListMapSql(sb.toString(), null,1, 11);
		if(QwyUtil.isNullAndEmpty(list))
			return null;
		return list;
				
	}

}
