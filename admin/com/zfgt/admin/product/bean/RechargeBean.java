package com.zfgt.admin.product.bean;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.account.bean.UserRechargeBean;
import com.zfgt.admin.product.dao.RechargeDAO;
import com.zfgt.common.bean.YiBaoPayBean;
import com.zfgt.common.util.DESEncrypt;
import com.zfgt.common.util.ObjectUtil;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.Account;
import com.zfgt.orm.BackStatsOperateDay;
import com.zfgt.orm.CapitalRecord;
import com.zfgt.orm.CzRecord;
import com.zfgt.orm.DateMoney;
import com.zfgt.orm.RootTxRecord;
import com.zfgt.orm.UserCz;
import com.zfgt.orm.UserCzTx;
import com.zfgt.orm.Users;
import com.zfgt.orm.WeekLeftMoney;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.json.JSONObject;
import yjpay.api.service.impl.PayAPIServiceImpl;

/**
 * 后天管理--发送红包;
 * 
 * @author qwy
 *
 * @createTime 2015-4-27上午11:44:54
 */
@Service
public class RechargeBean {
	protected static Logger log = Logger.getLogger(RechargeBean.class);
	@Resource
	private RechargeDAO dao;
	@Resource
	private UserRechargeBean userRechargeBean;
	@Resource
	private SendCouponBean sendCouponBean;
	@Resource
	private YiBaoPayBean yiBaoPayBean;
	private PayAPIServiceImpl payAPIService = new PayAPIServiceImpl();

	/**
	 * 用户充值;
	 * 
	 * @param usersId
	 *            用户id
	 * @param money
	 *            操作金额;单位(分)
	 * @param operatedType
	 *            操作类别 cz:用户充值 tx:充值 zf:在线支付
	 * @param operatedWay
	 *            操作途径; 第三方支付;借记卡.
	 * @param note
	 *            如: "用户从第三方充值金额到平台账户"
	 * @return true:充值成功; false:充值失败;
	 */
	public boolean usreRecharge(long usersId, double money, String operatedType, String operatedWay, String note) {
		return userRechargeBean.usreRecharge(usersId, money, operatedType, operatedWay, note);
	}

	/**
	 * 根据用户名来查找用户;<br>
	 * 对传进来的值,已加密处理;
	 * 
	 * @param username
	 *            用户名;
	 * @return Usres
	 */
	public Users getUsersByUsername(String username) {
		return sendCouponBean.getUsersByUsername(username);

	}

	/**
	 * 获得充值的记录;根据状态来查找;
	 * 
	 * @param pageUtil
	 *            分页对象;
	 * @param status
	 *            状态; all:全部; 0:待充值;1:充值成功;2充值失败;3:易宝充值成功,数据库插入失败;
	 * @param name
	 *            姓名
	 * @param insertTime
	 *            充值时间
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public PageUtil<CzRecord> loadCzRecord(PageUtil<CzRecord> pageUtil, String status, String name, String insertTime) throws Exception {
		ArrayList<Object> ob = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer();
		hql.append(" FROM CzRecord cz WHERE 1=1 ");
		if (!"all".equals(status)) {
			hql.append(" AND cz.status = ? ");
			ob.add(status);
		}
		if (!QwyUtil.isNullAndEmpty(name)) {
			hql.append(" AND cz.users.username = ? ");
			ob.add(DESEncrypt.jiaMiUsername(name));
		}
		// 充值时间
		if (!QwyUtil.isNullAndEmpty(insertTime)) {
			String[] time = QwyUtil.splitTime(insertTime);
			if (time.length > 1) {
				hql.append(" AND cz.insertTime >= ? ");
				ob.add(QwyUtil.fmMMddyyyy.parse(time[0]));
				hql.append(" AND cz.insertTime <= ? ");
				ob.add(QwyUtil.fmMMddyyyy.parse(time[1]));
			} else {
				hql.append(" AND cz.insertTime >= ? ");
				ob.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 00:00:00"));
				hql.append(" AND cz.insertTime <= ? ");
				ob.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 23:59:59"));
			}
		}
		hql.append(" ORDER BY  cz.insertTime DESC, cz.checkTime DESC ");
		return (PageUtil<CzRecord>) dao.getPage(pageUtil, hql.toString(), ob.toArray());
	}

	/**
	 * 以日期分组查询充值记录
	 * 
	 * @param pageUtil
	 *            分页
	 * @param insertTime
	 *            时间段
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public PageUtil findCzRecordGroupByDate(PageUtil pageUtil, String insertTime) throws Exception {
		ArrayList<Object> list = new ArrayList<Object>();
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT DATE_FORMAT(dd.insert_time, '%Y-%m-%d') AS date, record.money from dateday dd ");
		buffer.append(" LEFT JOIN (select DATE_FORMAT(cr.insert_time, '%Y-%m-%d') as insert_time, SUM(cr.money) as money  from ");
		buffer.append(" cz_record  cr WHERE	cr. STATUS IN ('1', '3') GROUP BY DATE_FORMAT(cr.insert_time, '%Y-%m-%d') )");
		buffer.append(" record on record.insert_time =  DATE_FORMAT(dd.insert_time, '%Y-%m-%d ') where 1=1 ");

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
		buffer.append(" ORDER BY DATE_FORMAT( dd.insert_time, '%Y-%m-%d ' ) DESC  ");
		StringBuffer bufferCount = new StringBuffer();
		bufferCount.append(" SELECT COUNT(t.date)  ");
		bufferCount.append(" FROM (");
		bufferCount.append(buffer);
		bufferCount.append(") t");
		pageUtil = dao.getBySqlAndSqlCount(pageUtil, buffer.toString(), bufferCount.toString(), list.toArray());
		List<DateMoney> dateMoneys = toDateMoney(pageUtil.getList());
		pageUtil.setList(dateMoneys);
		return pageUtil;
	}

	/**
	 * 以日期分组查询资金流水
	 * 
	 * @param pageUtil
	 *            分页
	 * @param insertTime
	 *            时间段
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public PageUtil<CapitalRecord> findCapitalRecordGroupByDate(PageUtil pageUtil, String insertTime) throws Exception {
		List<Object> list = new ArrayList<Object>();
		StringBuffer buffer = new StringBuffer();
		buffer.append(" select us.date as date, sum(regUserSum ) as regUserSum,");
		buffer.append(" sum(investUserSum ) as investUserSum,sum(investCentSum+couponCentSum ) as investCentSums,");
		buffer.append(" sum(rechargeCentSum ) as rechargeCentSum, sum(successWithdrawCentSum ) as successWithdrawCentSum ,");
		buffer.append(" sum(withdrawCentSum ) as withdrawCentSum");
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
		List<CapitalRecord> platUsers = toCapital(pageUtil.getList());
		pageUtil.setList(platUsers);
		return pageUtil;
	}

	/**
	 * 将数据转换为资金流水记录
	 * 
	 * @throws ParseException
	 */
	public List<CapitalRecord> toCapital(List<Object[]> list) throws ParseException {
		List<CapitalRecord> capitalRecords = new ArrayList<CapitalRecord>();
		if (!QwyUtil.isNullAndEmpty(list)) {
			for (Object[] object : list) {
				CapitalRecord dateMoney = new CapitalRecord();
				dateMoney.setDate(QwyUtil.fmyyyyMMddHHmmss.parse(QwyUtil.fmyyyyMMddHHmmss.format(object[0])));
				dateMoney.setRegCount(!QwyUtil.isNullAndEmpty(object[1]) ? object[1] + "" : "0");
				dateMoney.setIvsCount(!QwyUtil.isNullAndEmpty(object[2]) ? object[2] + "" : "0");
				dateMoney.setIvsMoney(!QwyUtil.isNullAndEmpty(object[3]) ? object[3] + "" : "0");
				dateMoney.setCzMoney(!QwyUtil.isNullAndEmpty(object[4]) ? object[4] + "" : "0");
				dateMoney.setCtxMoney(!QwyUtil.isNullAndEmpty(object[5]) ? object[5] + "" : "0");
				dateMoney.setTxMoney(!QwyUtil.isNullAndEmpty(object[6]) ? object[6] + "" : "0");
				capitalRecords.add(dateMoney);
			}
		}
		return capitalRecords;

	}

	// @SuppressWarnings("unchecked")
	// public PageUtil findCapitalRecordGroupByDate(PageUtil pageUtil,String
	// insertTime) throws Exception{
	// ArrayList<Object> list=new ArrayList<Object>();
	// StringBuffer buffer=new StringBuffer();
	// buffer.append(" SELECT DATE_FORMAT(dd.insert_time,'%Y-%m-%d') as date,
	// ");
	// buffer.append(" (SELECT COUNT(*) FROM users us WHERE
	// DATE_FORMAT(us.insert_time,'%Y-%m-%d') =
	// DATE_FORMAT(dd.insert_time,'%Y-%m-%d')");
	// if(!QwyUtil.isNullAndEmpty(insertTime)){
	// String [] time=QwyUtil.splitTime(insertTime);
	// if(time.length>1){
	// buffer.append(" AND DATE_FORMAT(us.insert_time , '%Y-%m-%d') >= ? ");
	// list.add(QwyUtil.fmyyyyMMdd.format(new Date(time[0]+"")));
	// buffer.append(" AND DATE_FORMAT(us.insert_time , '%Y-%m-%d') <= ? ");
	// list.add(QwyUtil.fmyyyyMMdd.format(new Date(time[1]+"")));
	// }else{
	// buffer.append(" AND DATE_ADD( DATE_FORMAT(us.insert_time ,
	// '%Y-%m-%d'),INTERVAL 31 DAY) > ? ");
	// list.add(QwyUtil.fmyyyyMMdd.format(new Date(time[0]+"")));
	// buffer.append(" AND us.insert_time <= ? ");
	// list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59"));
	// }
	// }
	// else{
	// buffer.append(" AND DATE_ADD( DATE_FORMAT(us.insert_time ,
	// '%Y-%m-%d'),INTERVAL 31 DAY) >= ? ");
	// list.add(QwyUtil.fmyyyyMMdd.format(new Date()));
	// buffer.append(" AND DATE_FORMAT(us.insert_time , '%Y-%m-%d') < ? ");
	// list.add(QwyUtil.fmyyyyMMdd.format(new Date()));
	// }
	//
	// buffer.append(") as 'regcount' , ");
	// buffer.append(" (SELECT COUNT(DISTINCT ivs.users_id) FROM investors ivs
	// WHERE ivs.investor_status in ('1','2','3') AND
	// DATE_FORMAT(ivs.pay_time,'%Y-%m-%d') =
	// DATE_FORMAT(dd.insert_time,'%Y-%m-%d') ");
	// if(!QwyUtil.isNullAndEmpty(insertTime)){
	// String [] time=QwyUtil.splitTime(insertTime);
	// if(time.length>1){
	// buffer.append(" AND DATE_FORMAT(ivs.pay_time , '%Y-%m-%d') >= ? ");
	// list.add(QwyUtil.fmyyyyMMdd.format(new Date(time[0]+"")));
	// buffer.append(" AND DATE_FORMAT(ivs.pay_time , '%Y-%m-%d') <= ? ");
	// list.add(QwyUtil.fmyyyyMMdd.format(new Date(time[1]+"")));
	// }else{
	// buffer.append(" AND DATE_ADD( DATE_FORMAT(ivs.pay_time ,
	// '%Y-%m-%d'),INTERVAL 31 DAY) > ? ");
	// list.add(QwyUtil.fmyyyyMMdd.format(new Date(time[0]+"")));
	// buffer.append(" AND ivs.pay_time <= ? ");
	// list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59"));
	// }
	// }
	// else{
	// buffer.append(" AND DATE_ADD( DATE_FORMAT(ivs.pay_time ,
	// '%Y-%m-%d'),INTERVAL 31 DAY) >= ? ");
	// list.add(QwyUtil.fmyyyyMMdd.format(new Date()));
	// buffer.append(" AND DATE_FORMAT(ivs.pay_time , '%Y-%m-%d') < ? ");
	// list.add(QwyUtil.fmyyyyMMdd.format(new Date()));
	// }
	// buffer.append(" ) as 'ivscount' , ");
	// buffer.append(" (SELECT SUM(ivs.copies) FROM investors ivs WHERE
	// ivs.investor_status in ('1','2','3') AND
	// DATE_FORMAT(ivs.pay_time,'%Y-%m-%d') =
	// DATE_FORMAT(dd.insert_time,'%Y-%m-%d')");
	// if(!QwyUtil.isNullAndEmpty(insertTime)){
	// String [] time=QwyUtil.splitTime(insertTime);
	// if(time.length>1){
	// buffer.append(" AND DATE_FORMAT(ivs.pay_time , '%Y-%m-%d') >= ? ");
	// list.add(QwyUtil.fmyyyyMMdd.format(new Date(time[0]+"")));
	// buffer.append(" AND DATE_FORMAT(ivs.pay_time, '%Y-%m-%d') <= ? ");
	// list.add(QwyUtil.fmyyyyMMdd.format(new Date(time[1]+"")));
	// }else{
	// buffer.append(" AND DATE_ADD( DATE_FORMAT(ivs.pay_time ,
	// '%Y-%m-%d'),INTERVAL 31 DAY) > ? ");
	// list.add(QwyUtil.fmyyyyMMdd.format(new Date(time[0]+"")));
	// buffer.append(" AND ivs.pay_time <= ? ");
	// list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59"));
	// }
	// }
	// else{
	// buffer.append(" AND DATE_ADD( DATE_FORMAT(ivs.pay_time ,
	// '%Y-%m-%d'),INTERVAL 31 DAY) >= ? ");
	// list.add(QwyUtil.fmyyyyMMdd.format(new Date()));
	// buffer.append(" AND DATE_FORMAT(ivs.pay_time , '%Y-%m-%d') < ? ");
	// list.add(QwyUtil.fmyyyyMMdd.format(new Date()));
	// }
	// buffer.append(") as 'ivsmoney' ,");
	// buffer.append(" (SELECT SUM(cz.money) FROM cz_record cz WHERE cz.`STATUS`
	// = '1' AND DATE_FORMAT(cz.insert_time,'%Y-%m-%d') =
	// DATE_FORMAT(dd.insert_time,'%Y-%m-%d')");
	// if(!QwyUtil.isNullAndEmpty(insertTime)){
	// String [] time=QwyUtil.splitTime(insertTime);
	// if(time.length>1){
	// buffer.append(" AND DATE_FORMAT(cz.insert_time , '%Y-%m-%d') >= ? ");
	// list.add(QwyUtil.fmyyyyMMdd.format(new Date(time[0]+"")));
	// buffer.append(" AND DATE_FORMAT(cz.insert_time, '%Y-%m-%d') <= ? ");
	// list.add(QwyUtil.fmyyyyMMdd.format(new Date(time[1]+"")));
	// }else{
	// buffer.append(" AND DATE_ADD( DATE_FORMAT(cz.insert_time ,
	// '%Y-%m-%d'),INTERVAL 31 DAY) > ? ");
	// list.add(QwyUtil.fmyyyyMMdd.format(new Date(time[0]+"")));
	// buffer.append(" AND cz.insert_time <= ? ");
	// list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59"));
	// }
	// }
	// else{
	// buffer.append(" AND DATE_ADD( DATE_FORMAT(cz.insert_time ,
	// '%Y-%m-%d'),INTERVAL 31 DAY) >= ? ");
	// list.add(QwyUtil.fmyyyyMMdd.format(new Date()));
	// buffer.append(" AND DATE_FORMAT(cz.insert_time , '%Y-%m-%d') < ? ");
	// list.add(QwyUtil.fmyyyyMMdd.format(new Date()));
	// }
	// buffer.append(") as 'czmoney' , ");
	// buffer.append(" (SELECT SUM(tx.money) FROM tx_record tx WHERE
	// DATE_FORMAT(tx.insert_time,'%Y-%m-%d') =
	// DATE_FORMAT(dd.insert_time,'%Y-%m-%d')");
	//
	// if(!QwyUtil.isNullAndEmpty(insertTime)){
	// String [] time=QwyUtil.splitTime(insertTime);
	// if(time.length>1){
	// buffer.append(" AND DATE_FORMAT(tx.insert_time , '%Y-%m-%d') >= ? ");
	// list.add(QwyUtil.fmyyyyMMdd.format(new Date(time[0]+"")));
	// buffer.append(" AND DATE_FORMAT(tx.insert_time, '%Y-%m-%d') <= ? ");
	// list.add(QwyUtil.fmyyyyMMdd.format(new Date(time[1]+"")));
	// }else{
	// buffer.append(" AND DATE_ADD( DATE_FORMAT(tx.insert_time,
	// '%Y-%m-%d'),INTERVAL 31 DAY) > ? ");
	// list.add(QwyUtil.fmyyyyMMdd.format(new Date(time[0]+"")));
	// buffer.append(" AND tx.insert_time <= ? ");
	// list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59"));
	// }
	// }
	// else{
	// buffer.append(" AND DATE_ADD( DATE_FORMAT(tx.insert_time ,
	// '%Y-%m-%d'),INTERVAL 31 DAY) >= ? ");
	// list.add(QwyUtil.fmyyyyMMdd.format(new Date()));
	// buffer.append(" AND DATE_FORMAT(tx.insert_time , '%Y-%m-%d') < ? ");
	// list.add(QwyUtil.fmyyyyMMdd.format(new Date()));
	// }
	// buffer.append(" ) as 'txmoney' ");
	// buffer.append(" FROM dateday dd where 1=1 ");
	// //充值时间
	// if(!QwyUtil.isNullAndEmpty(insertTime)){
	// String [] time=QwyUtil.splitTime(insertTime);
	// if(time.length>1){
	// buffer.append(" AND dd.insert_time >= ? ");
	// list.add(QwyUtil.fmMMddyyyy.parse(time[0]));
	// buffer.append(" AND dd.insert_time <= ? ");
	// list.add(QwyUtil.fmMMddyyyy.parse(time[1]));
	// }else{
	// buffer.append(" AND dd.insert_time >= ? ");
	// list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
	// buffer.append(" AND dd.insert_time <= ? ");
	// list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59"));
	// }
	// }
	// else{
	// buffer.append(" AND DATE_ADD( DATE_FORMAT(dd.insert_time ,
	// '%Y-%m-%d'),INTERVAL 31 DAY) >= ? ");
	// list.add(QwyUtil.fmyyyyMMdd.format(new Date()));
	// buffer.append(" AND DATE_FORMAT(dd.insert_time , '%Y-%m-%d') < ? ");
	// list.add(QwyUtil.fmyyyyMMdd.format(new Date()));
	// }
	// buffer.append(" GROUP BY DATE_FORMAT( dd.insert_time, '%Y-%m-%d' ) ");
	// buffer.append(" ORDER BY DATE_FORMAT( dd.insert_time, '%Y-%m-%d ' ) DESC
	// ");
	// StringBuffer bufferCount=new StringBuffer();
	// bufferCount.append(" SELECT COUNT(t.date) ");
	// bufferCount.append(" FROM (");
	// bufferCount.append(buffer);
	// bufferCount.append(") t");
	// pageUtil=dao.getBySqlAndSqlCount(pageUtil, buffer.toString(),
	// bufferCount.toString(), list.toArray());
	// List<CapitalRecord> dateMoneys=toCapital(pageUtil.getList());
	// pageUtil.setList(dateMoneys);
	// return pageUtil;
	// }
	// /**
	// * 将数据转换为资金流水记录
	// */
	// public List<CapitalRecord> toCapital(List<Object [] > list){
	// List<CapitalRecord> capitalRecords=new ArrayList<CapitalRecord>();
	// if(!QwyUtil.isNullAndEmpty(list)){
	// for (Object [] object : list) {
	// CapitalRecord dateMoney=new CapitalRecord();
	// //dateMoney.setInsertTime(object[0]+"");
	// dateMoney.setRegCount(!QwyUtil.isNullAndEmpty(object[1])?Double.parseDouble(object[1]+""):Double.parseDouble("0"));
	// dateMoney.setIvsCount(!QwyUtil.isNullAndEmpty(object[2])?Double.parseDouble(object[2]+""):Double.parseDouble("0"));
	// dateMoney.setIvsMoney(!QwyUtil.isNullAndEmpty(object[3])?Double.parseDouble(object[3]+""):Double.parseDouble("0"));
	// dateMoney.setCzMoney(!QwyUtil.isNullAndEmpty(object[4])?Double.parseDouble(object[4]+""):Double.parseDouble("0"));
	// dateMoney.setTxMoney(!QwyUtil.isNullAndEmpty(object[5])?Double.parseDouble(object[5]+""):Double.parseDouble("0"));
	// capitalRecords.add(dateMoney);
	// }
	// }
	// return capitalRecords;
	//
	// }

	/**
	 * 合计
	 */
	public CapitalRecord tjCapital(String insertTime) throws Exception {
		CapitalRecord cr = new CapitalRecord();

		List<Object> list = new ArrayList<Object>();
		try {

			StringBuffer buffer = new StringBuffer("FROM BackStatsOperateDay rr  where 1=1 ");

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
			List<BackStatsOperateDay> capitalRecords = dao.LoadAll(buffer.toString(), list.toArray());
			// 注册人数

			if (!QwyUtil.isNullAndEmpty(capitalRecords)) {
				for (BackStatsOperateDay capitalRecord : capitalRecords) {

					// 充值金额
					if (QwyUtil.isNullAndEmpty(cr.getCzMoney()))
						cr.setCzMoney("0");
					if (QwyUtil.isNullAndEmpty(cr.getIvsCount()))
						cr.setIvsCount("0");
					if (QwyUtil.isNullAndEmpty(cr.getIvsMoney()))
						cr.setIvsMoney("0");
					// 提现金额
					if (QwyUtil.isNullAndEmpty(cr.getTxMoney()))
						cr.setTxMoney("0");
					// 成功提现金额
					if (QwyUtil.isNullAndEmpty(cr.getCtxMoney()))
						cr.setCtxMoney("0");

					// 注册人数
					if (QwyUtil.isNullAndEmpty(cr.getRegCount()))
						cr.setRegCount("0");
					cr.setCzMoney(QwyUtil.calcNumber(cr.getCzMoney(), capitalRecord.getRechargeCentSum(), "+") + "");
					cr.setIvsCount(QwyUtil.calcNumber(cr.getIvsCount(), capitalRecord.getInvestUserSum(), "+") + "");
					cr.setIvsMoney(QwyUtil.calcNumber(cr.getIvsMoney(), capitalRecord.getInvestCentSum() + capitalRecord.getCouponCentSum(), "+") + "");
					cr.setTxMoney(QwyUtil.calcNumber(cr.getTxMoney(), capitalRecord.getWithdrawCentSum(), "+") + "");
					cr.setCtxMoney(QwyUtil.calcNumber(cr.getCtxMoney(), capitalRecord.getSuccessWithdrawCentSum(), "+") + "");
					cr.setRegCount(QwyUtil.calcNumber(cr.getRegCount(), capitalRecord.getRegUserSum(), "+") + "");

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cr;

	}

	/**
	 * 以日期分组查询提现记录
	 * 
	 * @param pageUtil
	 *            分页
	 * @param insertTime
	 *            时间段
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public PageUtil findTxRecordGroupByDate(PageUtil pageUtil, String insertTime) throws Exception {
		ArrayList<Object> list = new ArrayList<Object>();
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT DATE_FORMAT(dd.insert_time, '%Y-%m-%d') AS date,txmoney.allmoney as allmoney");
		buffer.append(" FROM dateday dd LEFT JOIN");
		buffer.append(" (SELECT	SUM(tx.money) AS allmoney,DATE_FORMAT(tx.insert_time, '%Y-%m-%d') as insert_time");
		buffer.append(" FROM tx_record AS tx WHERE tx.`STATUS` = '1'");
		buffer.append(" GROUP BY  DATE_FORMAT(tx.insert_time, '%Y-%m-%d')");
		buffer.append("	) AS txmoney");
		buffer.append(" on DATE_FORMAT(txmoney.insert_time, '%Y-%m-%d') = DATE_FORMAT(dd.insert_time, '%Y-%m-%d') where 1=1 ");

		// 提现时间
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
		buffer.append(" ORDER BY DATE_FORMAT(dd.insert_time, '%Y-%m-%d ') DESC ");

		StringBuffer bufferCount = new StringBuffer();
		bufferCount.append(" SELECT COUNT(t.date)  ");
		bufferCount.append(" FROM (");
		bufferCount.append(buffer);
		bufferCount.append(") t");
		pageUtil = dao.getBySqlAndSqlCount(pageUtil, buffer.toString(), bufferCount.toString(), list.toArray());
		List<DateMoney> dateMoneys = toDateMoney(pageUtil.getList());
		pageUtil.setList(dateMoneys);
		return pageUtil;
	}

	/**
	 * 将数据转换为DateMoney
	 */
	public List<DateMoney> toDateMoney(List<Object[]> list) {
		List<DateMoney> dateMoneys = new ArrayList<DateMoney>();
		if (!QwyUtil.isNullAndEmpty(list)) {
			for (Object[] object : list) {
				DateMoney dateMoney = new DateMoney();
				dateMoney.setDate(object[0] + "");
				dateMoney.setMoney(!QwyUtil.isNullAndEmpty(object[1]) ? object[1] + "" : "0");
				dateMoneys.add(dateMoney);
			}
		}
		return dateMoneys;

	}

	/**
	 * 查看平台所有用户的可用余额
	 */
	// public
	public String findAllLeftMoney() throws Exception {
		List<Object> arrayList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT sum(ui.left_money) from users_info  ui ");
		sql.append(" WHERE ui.users_id not in (SELECT users_id FROM(SELECT tr.users_id FROM tx_record tr WHERE DATE_FORMAT( tr.insert_time, '%Y-%m-%d' ) = DATE_FORMAT( NOW(), '%Y-%m-%d' )) tab ) ");
		Object object = dao.getSqlCount(sql.toString(), arrayList.toArray());
		if (!QwyUtil.isNullAndEmpty(object)) {
			return object + "";
		}
		return "0";
	}

	/**
	 * 充值提现数据
	 * 
	 * @param insertTime
	 * @param pageUtil
	 * @param registPlatform
	 *            注册平台
	 * @throws Exception
	 */
	public PageUtil<BackStatsOperateDay> findUserCzTx(PageUtil pageUtil, String insertTime, String registPlatform) throws Exception {
		try {

			StringBuffer queryListSql = new StringBuffer();
			createCzTxQueryListSql(queryListSql, registPlatform, insertTime, true);

			StringBuffer bufferCount = new StringBuffer();
			bufferCount.append(" SELECT COUNT(*)  ");
			bufferCount.append(" FROM (");
			bufferCount.append(queryListSql);
			bufferCount.append(") t");

			pageUtil = dao.getListMapBySqlAndSqlCount(pageUtil, queryListSql.toString(), bufferCount.toString(), null);

			List<Map<String, Object>> list_1 = pageUtil.getList();
			List<BackStatsOperateDay> list_2 = toBackStatsOperateDay(list_1);
			pageUtil.setList(list_2);

			return pageUtil;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	private List<BackStatsOperateDay> toBackStatsOperateDay(List<Map<String, Object>> list) throws Exception {
		List<BackStatsOperateDay> returnList = new ArrayList<BackStatsOperateDay>();

		if (QwyUtil.isNullAndEmpty(list)) {
			return returnList;
		}

		for (Map<String, Object> map : list) {
			BackStatsOperateDay backStatsOperateDay = new BackStatsOperateDay();
			backStatsOperateDay = (BackStatsOperateDay) ObjectUtil.mapToObject(map, backStatsOperateDay);
			backStatsOperateDay.setDates(QwyUtil.fmyyyyMMdd.format(backStatsOperateDay.getDate()));
			returnList.add(backStatsOperateDay);
		}

		return returnList;
	}

	/**
	 * 将数据转换为需要的数据
	 * 
	 * @throws Exception
	 */
	public List<UserCzTx> findUserCzTxs(List<Object[]> objects) throws Exception {
		List<UserCzTx> list = new ArrayList<UserCzTx>();
		if (!QwyUtil.isNullAndEmpty(objects)) {
			for (Object[] obj : objects) {
				UserCzTx userCzTx = new UserCzTx();
				userCzTx.setDate(QwyUtil.fmyyyyMMdd.format(obj[0]));
				userCzTx.setCzMoney(!QwyUtil.isNullAndEmpty(obj[1]) ? QwyUtil.calcNumber(obj[1], 100, "/", 2) + "" : "0");
				userCzTx.setCzCount(!QwyUtil.isNullAndEmpty(obj[2]) ? obj[2] + "" : "0");
				userCzTx.setTxMoney(!QwyUtil.isNullAndEmpty(obj[3]) ? QwyUtil.calcNumber(obj[3], 100, "/", 2) + "" : "0");
				userCzTx.setTxCount(!QwyUtil.isNullAndEmpty(obj[4]) ? obj[4] + "" : "0");
				userCzTx.setCtxMoney(!QwyUtil.isNullAndEmpty(obj[5]) ? QwyUtil.calcNumber(obj[5], 100, "/", 2) + "" : "0");
				userCzTx.setCtxCount(!QwyUtil.isNullAndEmpty(obj[6]) ? obj[6] + "" : "0");

				list.add(userCzTx);
			}
		}
		return list;
	}

	/**
	 * 合计
	 */
	public UserCzTx tjUserCzTx(String insertTime, String registPlatform) throws Exception {
		UserCzTx uc = new UserCzTx();
		List<Object> list = new ArrayList<Object>();
		StringBuffer buffer = new StringBuffer("FROM BackStatsOperateDay rr  where 1=1 ");
		if (!QwyUtil.isNullAndEmpty(registPlatform)) {
			buffer.append("  and rr.registPlatform = ");
			buffer.append(registPlatform);
		}
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
		List<BackStatsOperateDay> userCzTxs = dao.LoadAll(buffer.toString(), list.toArray());
		if (!QwyUtil.isNullAndEmpty(userCzTxs)) {
			for (BackStatsOperateDay czTx : userCzTxs) {
				// 充值次数
				if (QwyUtil.isNullAndEmpty(uc.getCzCount()))
					uc.setCzCount("0");
				// 充值金额
				if (QwyUtil.isNullAndEmpty(uc.getCzMoney()))
					uc.setCzMoney("0");
				// 待结算用户总额
				if (QwyUtil.isNullAndEmpty(uc.getDjsUserZe()))
					uc.setDjsUserZe("0");
				// 待结算项目总额
				if (QwyUtil.isNullAndEmpty(uc.getDjsxmze()))
					uc.setDjsxmze("0");
				// 3天内到期用户总额
				if (QwyUtil.isNullAndEmpty(uc.getDqUserZe()))
					uc.setDqUserZe("0");
				// 3天内到期项目总额
				if (QwyUtil.isNullAndEmpty(uc.getDqxmze()))
					uc.setDqxmze("0");
				// 已到期未结算金额
				if (QwyUtil.isNullAndEmpty(uc.getYdqsxmze()))
					uc.setYdqsxmze("0");

				// 提现次数
				if (QwyUtil.isNullAndEmpty(uc.getTxCount()))
					uc.setTxCount("0");
				// 提现金额
				if (QwyUtil.isNullAndEmpty(uc.getTxMoney()))
					uc.setTxMoney("0");
				// c提现次数
				if (QwyUtil.isNullAndEmpty(uc.getCtxCount()))
					uc.setCtxCount("0");
				// c提现金额
				if (QwyUtil.isNullAndEmpty(uc.getCtxMoney()))
					uc.setCtxMoney("0");

				// 充值次数
				uc.setCzCount(QwyUtil.calcNumber(uc.getCzCount(), czTx.getRechargeCount(), "+") + "");
				// 充值金额
				uc.setCzMoney(QwyUtil.calcNumber(uc.getCzMoney(), QwyUtil.calcNumber(czTx.getRechargeCentSum(), 100, "/", 2) + "", "+") + "");
				// 待结算用户总额
				uc.setDjsUserZe(QwyUtil.calcNumber(uc.getDjsUserZe(), czTx.getStillBalanceUserCentSum(), "+") + "");
				// 待结算项目总额
				uc.setDjsxmze(QwyUtil.calcNumber(uc.getDjsxmze(), czTx.getStillBalanceProductCentSum(), "+") + "");
				// 3天内到期用户总额
				uc.setDqUserZe(QwyUtil.calcNumber(uc.getDqUserZe(), czTx.getDueThreeDayUserCentSum(), "+") + "");
				// 3天内到期项目总额
				uc.setDqxmze(QwyUtil.calcNumber(uc.getDqxmze(), czTx.getDueThreeDayProductCentSum(), "+") + "");
				// 提现次数
				uc.setTxCount(QwyUtil.calcNumber(uc.getTxCount(), czTx.getWithdrawCount(), "+") + "");
				// 提现金额
				uc.setTxMoney(QwyUtil.calcNumber(uc.getTxMoney(), QwyUtil.calcNumber(czTx.getWithdrawCentSum(), 100, "/", 2) + "", "+") + "");
				// c提现次数
				uc.setCtxCount(QwyUtil.calcNumber(uc.getCtxCount(), czTx.getSuccessWithdrawCount(), "+") + "");
				// c提现金额
				uc.setCtxMoney(QwyUtil.calcNumber(uc.getCtxMoney(), QwyUtil.calcNumber(czTx.getSuccessWithdrawCentSum(), 100, "/", 2) + "", "+") + "");
				// 已到期未结算金额
				uc.setYdqsxmze(QwyUtil.calcNumber(uc.getYdqsxmze(), czTx.getDueNoBalanceCentSum(), "+") + "");
			}
		}
		return uc;
	}

	/**
	 * 用户投资的待结算的余额
	 * 
	 * @param registPlatform
	 *            注册平台
	 */
	public String userDjsye(String registPlatform, String beginDate, String endDate) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT SUM(ivs.copies) FROM  (");
		buffer.append(" SELECT * FROM  investors iv WHERE 1=1 ");
		if (!QwyUtil.isNullAndEmpty(beginDate) && !QwyUtil.isNullAndEmpty(endDate)) {
			buffer.append(" and DATE_FORMAT(iv.pay_time,'%Y-%m-%d') BETWEEN DATE_FORMAT('" + beginDate + "','%Y-%m-%d')  AND DATE_FORMAT('" + endDate + "','%Y-%m-%d')");
		} else {
			buffer.append(" and DATE_ADD( DATE_FORMAT(iv.pay_time  , '%Y-%m-%d') ,INTERVAL 31 DAY)>'" + QwyUtil.fmyyyyMMdd.format(new Date()) + "'");

		}
		buffer.append(") ivs");
		buffer.append(" WHERE ivs.investor_status in ('1')  ");
		buffer.append(" AND  DATE_FORMAT(ivs.pay_time, '%Y-%m-%d')  =  DATE_FORMAT( dd.insert_time, '%Y-%m-%d') ");
		if (!QwyUtil.isNullAndEmpty(registPlatform)) {
			buffer.append(" AND ivs.users_id in (");
			buffer.append(" SELECT id FROM users us  ");
			buffer.append(" WHERE us.regist_platform = '" + registPlatform + "' ");
			buffer.append(" )  ");
		}
		buffer.append(" GROUP BY DATE_FORMAT( ivs.pay_time, '%Y-%m-%d')  ");
		buffer.append(" ORDER BY DATE_FORMAT( ivs.pay_time, '%Y-%m-%d') DESC  ");
		return buffer.toString();
	}

	/**
	 * 产品待结算的余额
	 * 
	 * @param registPlatform
	 *            yy
	 */
	public String productDjsye(String beginDate, String endDate) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT SUM(pd.all_copies) FROM product pd WHERE pd.product_status in ('0','1') ");
		if (!QwyUtil.isNullAndEmpty(beginDate)) {
			buffer.append(" and DATE_FORMAT(pd.insert_time,'%Y-%m-%d') BETWEEN DATE_FORMAT('" + beginDate + "','%Y-%m-%d')  AND DATE_FORMAT('" + endDate + "','%Y-%m-%d')");
		} else {
			buffer.append(" and DATE_ADD( DATE_FORMAT(pd.insert_time, '%Y-%m-%d') ,INTERVAL 31 DAY)>'" + QwyUtil.fmyyyyMMdd.format(new Date()) + "'");

		}
		buffer.append(" AND  DATE_FORMAT(pd.insert_time, '%Y-%m-%d')  =  DATE_FORMAT( dd.insert_time, '%Y-%m-%d')  ");
		buffer.append(" GROUP BY DATE_FORMAT( pd.insert_time, '%Y-%m-%d') ");
		buffer.append(" ORDER BY DATE_FORMAT( pd.insert_time, '%Y-%m-%d') DESC  ");
		return buffer.toString();
	}

	/**
	 * 用户投资3日内到期项目总额
	 * 
	 * @param registPlatform
	 *            注册平台
	 */
	public String userDqye(String registPlatform) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT SUM(ivs.copies) FROM investors ivs WHERE ivs.investor_status in ('1') ");
		buffer.append(" AND DATE_ADD(NOW() ,INTERVAL 3 DAY) >= ivs.finish_time ");
		buffer.append(" AND  DATE_FORMAT(ivs.pay_time, '%Y-%m-%d')  =  DATE_FORMAT( dd.insert_time, '%Y-%m-%d')  ");
		if (!QwyUtil.isNullAndEmpty(registPlatform)) {
			buffer.append(" AND ivs.users_id in (");
			buffer.append(" SELECT id FROM users us  ");
			buffer.append(" WHERE us.regist_platform = '" + registPlatform + "' ");
			buffer.append(" )  ");
		}
		buffer.append(" GROUP BY DATE_FORMAT( ivs.pay_time, '%Y-%m-%d') ");
		buffer.append(" ORDER BY DATE_FORMAT( ivs.pay_time, '%Y-%m-%d') DESC ");
		return buffer.toString();
	}

	/**
	 * 产品3日内到期项目总额
	 */
	public String productDqye() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT SUM(pd.all_copies) FROM product pd WHERE pd.product_status in ('0','1') ");
		buffer.append(" AND DATE_ADD(NOW() ,INTERVAL 3 DAY) >= pd.finish_time ");
		buffer.append(" AND  DATE_FORMAT(pd.insert_time, '%Y-%m-%d')  =  DATE_FORMAT( dd.insert_time, '%Y-%m-%d')  ");
		buffer.append(" GROUP BY DATE_FORMAT( pd.insert_time, '%Y-%m-%d') ");
		buffer.append(" ORDER BY DATE_FORMAT( pd.insert_time, '%Y-%m-%d') DESC ");
		return buffer.toString();
	}

	/**
	 * 已到期未结算的金额
	 * 
	 * @param registPlatform
	 *            注册平台
	 */
	public String ydqye(String registPlatform, String beginDate, String endDate) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT (SUM(ids.pay_money)+SUM(ids.pay_interest)) FROM interest_details ids ");
		buffer.append(" WHERE ids.investors_id in ( SELECT ivs.id FROM investors ivs WHERE ivs.investor_status in ('2','3')");

		if (!QwyUtil.isNullAndEmpty(beginDate) && !QwyUtil.isNullAndEmpty(beginDate)) {
			buffer.append(" and DATE_FORMAT(ivs.pay_time,'%Y-%m-%d') BETWEEN DATE_FORMAT('" + beginDate + "','%Y-%m-%d')  AND DATE_FORMAT('" + endDate + "','%Y-%m-%d')");
		} else {
			buffer.append(" and DATE_ADD( DATE_FORMAT(ivs.pay_time, '%Y-%m-%d') ,INTERVAL 31 DAY)>'" + QwyUtil.fmyyyyMMdd.format(new Date()) + "'");

		}

		buffer.append(") ");

		buffer.append(" AND ids.status = '0' ");
		if (!QwyUtil.isNullAndEmpty(beginDate) && !QwyUtil.isNullAndEmpty(beginDate)) {
			buffer.append(" and DATE_FORMAT(ids.insert_time,'%Y-%m-%d') BETWEEN DATE_FORMAT('" + beginDate + "','%Y-%m-%d')  AND DATE_FORMAT('" + endDate + "','%Y-%m-%d')");
		} else {
			buffer.append(" and DATE_ADD( DATE_FORMAT(ids.insert_time, '%Y-%m-%d') ,INTERVAL 31 DAY)>'" + QwyUtil.fmyyyyMMdd.format(new Date()) + "'");

		}
		buffer.append(" AND  DATE_FORMAT(ids.insert_time, '%Y-%m-%d')  =  DATE_FORMAT( dd.insert_time, '%Y-%m-%d') ");
		if (!QwyUtil.isNullAndEmpty(registPlatform)) {
			buffer.append(" AND ids.users_id in (");
			buffer.append(" SELECT id FROM users us  ");
			buffer.append(" WHERE us.regist_platform = '" + registPlatform + "' ");
			buffer.append(" )  ");
		}
		buffer.append(" GROUP BY DATE_FORMAT(ids.insert_time, '%Y-%m-%d') ");
		buffer.append(" ORDER BY DATE_FORMAT(ids.insert_time, '%Y-%m-%d') DESC ");
		return buffer.toString();
	}

	/**
	 * 充值提现数据
	 * 
	 * @param insertTime
	 * @param pageUtil
	 * @param registPlatform
	 *            注册平台
	 * @throws Exception
	 */
	public List<UserCzTx> findUserCzTx(String insertTime, String registPlatform) throws Exception {
		String beginDate = "";
		String endDate = "";
		if (!QwyUtil.isNullAndEmpty(insertTime)) {
			String[] time = QwyUtil.splitTime(insertTime);
			if (time.length == 2) {
				beginDate = QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[0].trim()));
				// endDate = QwyUtil.fmyyyyMMdd.format(new
				// Date(QwyUtil.fmMMddyyyy.parse(time[1].trim()).getTime() - 24
				// * 60 * 60 * 1000));
				endDate = QwyUtil.fmyyyyMMdd.format(new Date(QwyUtil.fmMMddyyyy.parse(time[1].trim()).getTime()));
			} else {
				endDate = QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[0].trim()));
				beginDate = QwyUtil.fmyyyyMMdd.format(QwyUtil.addDaysFromOldDate(new Date(QwyUtil.fmMMddyyyy.parse(time[0].trim()).getTime()), -31).getTime());
				// list.add(QwyUtil.fmMMddyyyy.parse(QwyUtil.fmMMddyyyy.format(QwyUtil.addDaysFromOldDate(new
				// Date(),-81).getTime())));
				// endDate = QwyUtil.fmyyyyMMdd.format(new
				// Date(QwyUtil.fmMMddyyyy.parse(time[0].trim()).getTime()));
			}
		}
		ArrayList<Object> list = new ArrayList<Object>();
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT DATE_FORMAT( dd.insert_time, '%Y-%m-%d') as date, ");
		buffer.append(" ( ");
		buffer.append(userDjsye(registPlatform, beginDate, endDate));
		buffer.append(" ) as '用户投资的待结算的余额',");
		buffer.append(" ( ");
		buffer.append(productDjsye(beginDate, endDate));
		buffer.append(" ) as '产品待结算的余额',");
		buffer.append(" ( ");
		buffer.append(userDqye(registPlatform));
		buffer.append(" ) as '用户投资3日内到期项目总额',");
		buffer.append(" ( ");
		buffer.append(productDqye());
		buffer.append(" ) as '产品3日内到期项目总额',");
		buffer.append(" ( ");
		buffer.append(ydqye(registPlatform, beginDate, endDate));
		buffer.append(" ) as '已到期未结算的金额',");
		buffer.append(" SUM(CASE WHEN (fr.type = 'cz') then 1 else 0 end ) as czcount, ");
		buffer.append(" SUM(CASE WHEN (fr.type = 'tx') then 1 else 0 end ) as txcount, ");
		buffer.append(" SUM(CASE WHEN (fr.type = 'cz') then fr.money else 0 end ) as czmoney, ");
		buffer.append(" SUM(CASE WHEN (fr.type = 'tx') then fr.money else 0 end ) as txmoney ");
		buffer.append(" FROM dateday dd ");
		buffer.append(" LEFT JOIN fund_record fr ");
		buffer.append(" ON DATE_FORMAT(dd.insert_time, '%Y-%m-%d' ) = DATE_FORMAT( fr.insert_time, '%Y-%m-%d')  ");
		buffer.append(" AND fr.type in ('cz','tx') ");
		if (!QwyUtil.isNullAndEmpty(registPlatform)) {
			buffer.append(" AND fr.users_id in (");
			buffer.append(" SELECT id FROM users us  ");
			buffer.append(" WHERE us.regist_platform = '" + registPlatform + "' ");
			buffer.append(" )  ");
		}
		buffer.append(" WHERE 1=1 ");
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
		buffer.append(" GROUP BY DATE_FORMAT( dd.insert_time, '%Y-%m-%d') ");
		buffer.append(" ORDER BY DATE_FORMAT( dd.insert_time, '%Y-%m-%d') DESC ");
		StringBuffer bufferCount = new StringBuffer();
		bufferCount.append(" SELECT COUNT(t.date)  ");
		bufferCount.append(" FROM (");
		bufferCount.append(buffer);
		bufferCount.append(") t");
		List<Object[]> obs = dao.LoadAllSql(buffer.toString(), list.toArray());
		List<UserCzTx> userCzTxs = findUserCzTxs(obs);
		return userCzTxs;
	}

	// public List<String> getTxMaxMoney() {
	// List<String> list = new ArrayList<String>();
	// String oneDayTx = txMAXCount(1);
	// String twoDayTx = txMAXCount(2);
	// String threeDayTx = txMAXCount(3);
	// String fourDayTx = txMAXCount(4);
	// String fiveDayTx = txMAXCount(5);
	// String sixDayTx = txMAXCount(6);
	// String sevenDayTx = txMAXCount(7);
	// list.add(sevenDayTx);
	// list.add(sixDayTx);
	// list.add(fiveDayTx);
	// list.add(fourDayTx);
	// list.add(threeDayTx);
	// list.add(twoDayTx);
	// list.add(oneDayTx);
	// return list;
	// }

	/**
	 * 获取一周不动金额
	 * 
	 * @throws Exception
	 */
	public List<WeekLeftMoney> findWeekLeftMoneys() throws Exception {
		List<String> strList = txMAXCount();
		String allCzRecord = allCzRecord();
		String allLeftMoney = allLeftMoney();
		String avgCzMoney = QwyUtil.calcNumber(QwyUtil.calcNumber(allCzRecord, 0.01, "*"), 30, "/", 2) + "";
		ArrayList<Object> list = new ArrayList<Object>();
		StringBuffer buffer = new StringBuffer();
		buffer.append("select * from (");
		buffer.append(" SELECT DATE_FORMAT(w.insert_time, '%Y-%m-%d' ) as insert_time,(SUM(ids.pay_money)+SUM(ids.pay_interest)) FROM weeks w  ");
		buffer.append(" LEFT JOIN interest_details ids ON DATE_FORMAT(w.insert_time, '%Y-%m-%d' ) = DATE_FORMAT(ids.return_time, '%Y-%m-%d' ) ");
		buffer.append(" AND ids.return_time < DATE_ADD(DATE_ADD(DATE_FORMAT(NOW(), '%Y-%m-%d' ),INTERVAL 1 DAY),INTERVAL 12 DAY) ");
		buffer.append(" AND ids.return_time >= DATE_ADD(DATE_FORMAT(NOW(), '%Y-%m-%d' ),INTERVAL 1 DAY) ");
		buffer.append(" GROUP BY DATE_FORMAT(w.insert_time, '%Y-%m-%d' ) ");
		buffer.append(" ORDER BY DATE_FORMAT(w.insert_time, '%Y-%m-%d' ) DESC ");
		buffer.append(" LIMIT 0,7 ");
		buffer.append(" ) st ORDER BY	st.insert_time asc");
		List<Object[]> obs = dao.LoadAllSql(buffer.toString(), list.toArray());
		List<WeekLeftMoney> weekLeftMoneys = toWeekLeftMoney(obs, strList, avgCzMoney, allLeftMoney);
		return weekLeftMoneys;
	}
	/**
	 * 获取一周不动金额
	 * 
	 * @throws Exception
	 */
	public List<WeekLeftMoney> findWeekRemainMoneys() throws Exception {
		String allLeftMoney = allLeftMoney();
		String lqgMoney = lqgMoney();
		ArrayList<Object> list = new ArrayList<Object>();
		StringBuffer buffer = new StringBuffer();
		buffer.append("select * from (");
		buffer.append(" SELECT DATE_FORMAT(w.insert_time, '%Y-%m-%d' ) as insert_time,SUM(ids.pay_money),SUM(ids.pay_interest) FROM weeks w  ");
		buffer.append(" LEFT JOIN interest_details ids ON DATE_FORMAT(w.insert_time, '%Y-%m-%d' ) = DATE_FORMAT(ids.return_time, '%Y-%m-%d' ) ");
		buffer.append(" AND ids.return_time < DATE_ADD(DATE_ADD(DATE_FORMAT(NOW(), '%Y-%m-%d' ),INTERVAL 1 DAY),INTERVAL 12 DAY) ");
		buffer.append(" AND ids.return_time >= DATE_ADD(DATE_FORMAT(NOW(), '%Y-%m-%d' ),INTERVAL 1 DAY) ");
		buffer.append(" GROUP BY DATE_FORMAT(w.insert_time, '%Y-%m-%d' ) ");
		buffer.append(" ORDER BY DATE_FORMAT(w.insert_time, '%Y-%m-%d' ) DESC ");
		buffer.append(" LIMIT 0,7 ");
		buffer.append(" ) st ORDER BY	st.insert_time asc");
		List<Object[]> obs = dao.LoadAllSql(buffer.toString(), list.toArray());
		List<WeekLeftMoney> weekLeftMoneys = toWeekRemainMoney(obs,lqgMoney,allLeftMoney);
		return weekLeftMoneys;
	}
	
	
	

	/**
	 * 将数据转换为WeekLeftMoney
	 * 
	 * @param list
	 *            到期还本数据
	 * @param strList
	 *            每天提现最大次数
	 * @param avgCzMoney
	 *            平均充值金额
	 * @param allLeftMoney
	 *            账户剩余金额
	 * @return
	 * @throws Exception
	 */
	public List<WeekLeftMoney> toWeekLeftMoney(List<Object[]> list, List<String> strList, String avgCzMoney, String allLeftMoney) throws Exception {
		List<WeekLeftMoney> weekLeftMoneys = new ArrayList<WeekLeftMoney>();
		// BigDecimal avgMoney ;
		String dqhbfxjeAll = "0";
		if (!QwyUtil.isNullAndEmpty(list)) {
			for (int i = 0; i< list.size(); i++) {
				WeekLeftMoney weekLeftMoney = new WeekLeftMoney();
				Object[] object = list.get(i);
				weekLeftMoney.setDate(object[0] + "");
				weekLeftMoney.setAllAccountLeftMoney(QwyUtil.calcNumber(allLeftMoney, 100, "/", 2) + "");
				weekLeftMoney.setYjtxsxf(strList.get(i) + "");
				String dqhbfxje = "0";
				if (!QwyUtil.isNullAndEmpty(object[1])) {
					dqhbfxje = QwyUtil.calcNumber(object[1], 100, "/", 2) + "";
				}
				dqhbfxjeAll = QwyUtil.calcNumber(dqhbfxjeAll, dqhbfxje, "+") + "";
				weekLeftMoney.setDqhbfxje(dqhbfxjeAll);
				weekLeftMoney.setYjczsxf(QwyUtil.calcNumber(avgCzMoney,  i+1, "*") + "");
				String sum = QwyUtil.calcNumber(weekLeftMoney.getAllAccountLeftMoney(), weekLeftMoney.getYjczsxf(), "+") + "";
				sum = QwyUtil.calcNumber(weekLeftMoney.getDqhbfxje(), sum, "+") + "";
				sum = QwyUtil.calcNumber(weekLeftMoney.getYjtxsxf(), sum, "+") + "";
				weekLeftMoney.setSum(sum);
				weekLeftMoneys.add(weekLeftMoney);
			}
		}
		return weekLeftMoneys;

	}
	
	/**
	 * 将数据转换为WeekLeftMoney
	 * 
	 * @param list
	 *            到期还本数据
	 * @param avgCzMoney
	 *            平均充值金额
	 * @param allLeftMoney
	 *            账户剩余金额
	 * @return
	 * @throws Exception
	 */
	public List<WeekLeftMoney> toWeekRemainMoney(List<Object[]> list,String lqgMoney,String allLeftMoney) throws Exception {
		List<WeekLeftMoney> weekLeftMoneys = new ArrayList<WeekLeftMoney>();
		// BigDecimal avgMoney ;
		String dqhbjeAll = "0";
		String dqfxjeAll = "0";
		if (!QwyUtil.isNullAndEmpty(list)) {
			for (int i = 0; i< list.size(); i++) {
				WeekLeftMoney weekLeftMoney = new WeekLeftMoney();
				Object[] object = list.get(i);
				weekLeftMoney.setDate(object[0] + "");
				weekLeftMoney.setAllAccountLeftMoney(QwyUtil.calcNumber(allLeftMoney, 100, "/", 2) + "");
				weekLeftMoney.setLqgMoney(QwyUtil.calcNumber(lqgMoney, 100, "/", 2) + "");
				String dqhbje = "0";//到期还本
				String dqfxje = "0";//到期付
				if (!QwyUtil.isNullAndEmpty(object[1])) {
					dqhbje = QwyUtil.calcNumber(object[1], 100, "/", 2) + "";
				}
				if (!QwyUtil.isNullAndEmpty(object[2])) {
					dqfxje = QwyUtil.calcNumber(object[2], 100, "/", 2) + "";
				}
				dqhbjeAll = QwyUtil.calcNumber(dqhbjeAll, dqhbje, "+") + "";
				dqfxjeAll = QwyUtil.calcNumber(dqfxjeAll, dqfxje, "+") + "";
				weekLeftMoney.setDqhbfxje(dqhbje);
				weekLeftMoney.setDqfxje(dqfxje);//到期还本
				String sum = QwyUtil.calcNumber(weekLeftMoney.getAllAccountLeftMoney(), weekLeftMoney.getLqgMoney(), "+") + "";
				sum = QwyUtil.calcNumber(weekLeftMoney.getDqhbfxje(), sum, "+") + "";
				sum = QwyUtil.calcNumber(weekLeftMoney.getDqfxje(), sum, "+") + "";
				weekLeftMoney.setSum(sum);
				weekLeftMoneys.add(weekLeftMoney);
			}
		}
		return weekLeftMoneys;

	}

	/**
	 * 获取总共充值金额
	 * 
	 * @return
	 */
	public String allCzRecord() {
		ArrayList<Object> list = new ArrayList<Object>();
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT	SUM(CASE WHEN cz.money > 50000 THEN	cz.money * 0.002 ELSE 100	END	) AS uid ");
		buffer.append(" FROM cz_record cz WHERE	STATUS = '1'");
		buffer.append(" AND DATE_FORMAT(cz.check_time, '%Y-%m-%d') >=");
		buffer.append(" DATE_ADD(DATE_FORMAT(NOW(), '%Y-%m-%d'),INTERVAL -30 DAY)");
		buffer.append(" AND DATE_FORMAT(cz.check_time, '%Y-%m-%d') <		DATE_FORMAT(NOW(), '%Y-%m-%d')");
		buffer.append(" ORDER BY	DATE_FORMAT(cz.check_time, '%Y-%m-%d') asc");
		Object object = dao.getSqlCount(buffer.toString(), list.toArray());
		if (!QwyUtil.isNullAndEmpty(object)) {
			return object + "";
		}
		return "0";
	}

	/**
	 * 获取账户可用余额
	 * 
	 * @return
	 */
	public String allLeftMoney() {
		ArrayList<Object> list = new ArrayList<Object>();
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT SUM(left_money) FROM users_info ");
		Object object = dao.getSqlCount(buffer.toString(), list.toArray());
		if (!QwyUtil.isNullAndEmpty(object)) {
			return object + "";
		}
		return "0";
	}
	/**
	 * 获取账户可用余额
	 * 
	 * @return
	 */
	public String lqgMoney() {
		ArrayList<Object> list = new ArrayList<Object>();
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT SUM(in_money) FROM coin_purse ");
		Object object = dao.getSqlCount(buffer.toString(), list.toArray());
		if (!QwyUtil.isNullAndEmpty(object)) {
			return object + "";
		}
		return "0";
	}

	/**
	 * 获取所有的天数
	 * 
	 * @return
	 */
	public String allDay() {
		ArrayList<Object> list = new ArrayList<Object>();
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT COUNT(*) FROM dateday ");
		Object object = dao.getSqlCount(buffer.toString(), list.toArray());
		if (!QwyUtil.isNullAndEmpty(object)) {
			return object + "";
		}
		return "1";
	}

	/**
	 * 第N天提现最大次数
	 * 
	 * @return
	 */
	/*
	 * public String txMAXCount(Integer day){ ArrayList<Object> list=new
	 * ArrayList<Object>(); StringBuffer buffer=new StringBuffer();
	 * buffer.append(
	 * " SELECT (COUNT(DISTINCT t.uid) + (SELECT COUNT(DISTINCT users_id) AS uid FROM users_info WHERE left_money > 100)) FROM( "
	 * ); buffer.append(
	 * " SELECT CASE WHEN ((SUM(pay_money)+SUM(pay_interest))>100) THEN users_id ELSE NULL END  as uid "
	 * ); buffer.append(" FROM interest_details "); buffer.append(
	 * " WHERE return_time < DATE_ADD(DATE_ADD(DATE_FORMAT(NOW(), '%Y-%m-%d' ),INTERVAL 1 DAY),INTERVAL "
	 * +day+" DAY)  "); buffer.append(
	 * " AND return_time >= DATE_ADD(DATE_FORMAT(NOW(), '%Y-%m-%d' ),INTERVAL 1 DAY) "
	 * ); buffer.append(
	 * " AND users_id not IN (SELECT DISTINCT users_id AS uid FROM users_info WHERE left_money > 100 )"
	 * ); buffer.append(" )t "); Object
	 * object=dao.getSqlCount(buffer.toString(),list.toArray());
	 * if(!QwyUtil.isNullAndEmpty(object)){ return object+""; } return "0"; }
	 */
	public String leftMoney() {
		StringBuffer buffer = new StringBuffer();
		ArrayList<Object> list = new ArrayList<Object>();
		buffer.append("	SELECT	COUNT(DISTINCT users_id) AS uid,users_id	FROM	users_info	WHERE	left_money >= 100");
		List<Object[]> obs = dao.LoadAllSql(buffer.toString(), list.toArray());
		if (!QwyUtil.isNullAndEmpty(obs)) {
			for (Object[] object : obs) {
				return object[0] + "";
			}
		}
		return "0";

	}

	public List<String> txMAXCount() {
		List<String> str = new ArrayList<String>();
		try {
			String leftMoney = leftMoney();
			Long  Yjtxsxf =0L;
			StringBuffer buffer = new StringBuffer();
			buffer.append("select * from (");
			buffer.append(" SELECT IFNULL(cs.total,0),	w.insert_time");
			buffer.append(" FROM weeks w LEFT JOIN (SELECT");
			buffer.append(" COUNT(*) as total,	DATE_FORMAT(t.return_time, '%Y-%m-%d') AS return_time");
			buffer.append(" FROM(SELECT	sum(ins.pay_money) AS pay_money, sum(ins.pay_interest) AS pay_interest,");
			buffer.append(" us.left_money,DATE_FORMAT(ins.return_time, '%Y-%m-%d') AS return_time");
			buffer.append(" FROM interest_details ins JOIN (SELECT	us.users_id AS users_id,");
			buffer.append(" us.left_money	FROM users_info us");
			buffer.append(" WHERE	us.left_money >= 0 AND us.left_money < 100");
			buffer.append(" ) us ON us.users_id = ins.users_id WHERE DATE_FORMAT(ins.return_time, '%Y-%m-%d') < DATE_ADD(");
			buffer.append(" DATE_ADD(DATE_FORMAT(NOW(), '%Y-%m-%d'),INTERVAL 1 DAY");
			buffer.append(" ),INTERVAL 7 DAY)" );
			buffer.append(" AND DATE_FORMAT(ins.return_time, '%Y-%m-%d') >= DATE_ADD(DATE_FORMAT(NOW(), '%Y-%m-%d'),");
			buffer.append(" INTERVAL 1 DAY)GROUP BY	ins.users_id,	DATE_FORMAT(ins.return_time, '%Y-%m-%d')");
			buffer.append(" ) t	WHERE	t.pay_interest + t.left_money + t.pay_money >= 100");
			buffer.append(" GROUP BY DATE_FORMAT(t.return_time, '%Y-%m-%d')");
			buffer.append(" ORDER BY	DATE_FORMAT(t.return_time, '%Y-%m-%d') DESC");
			buffer.append(" ) cs ON DATE_FORMAT(w.insert_time, '%Y-%m-%d') = cs.return_time");
			buffer.append(" ORDER BY w.insert_time DESC LIMIT 0, 7");
			buffer.append(" ) st ORDER BY	st.insert_time asc");
			List<Object[]> obs = dao.LoadAllSql(buffer.toString(), null);
			if (!QwyUtil.isNullAndEmpty(obs)) {
				for (int i =  0; i < obs.size(); i++) {
					Object[] object = obs.get(i);
					Yjtxsxf +=Long.parseLong(object[0]+"");
					str.add(Long.parseLong(leftMoney)+Yjtxsxf+"");
				}
			}
//			for (Object [] object : obs) {
//				Yjtxsxf +=Long.parseLong(object[0]+"");
//				str.add(Long.parseLong(leftMoney)+Yjtxsxf+"");
//			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
		return str;
	}

	/**
	 * 当天提现最大次数
	 * 
	 * @return
	 */
	public String txMaxCount() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT COUNT(*) FROM users_info WHERE left_money >=100");
		Object object = dao.getSqlCount(buffer.toString(), null);
		if (!QwyUtil.isNullAndEmpty(object)) {
			return object + "";
		}
		return "0";
	}

	/**
	 * 导出充值记录报表
	 * 
	 * @param insertTime
	 *            充值时间
	 * @param username
	 *            用户名
	 * @param status
	 *            状态 all:全部 ；0：待充值 ；1： 充值成功 ；2：充值失败 ；3：易宝充值成功,数据库插入失败
	 * 
	 * @param sourceFileName
	 *            文件地址
	 * @return
	 * @throws Exception
	 */
	public List<JasperPrint> getUserCzJasperPrintList(String username, String insertTime, String status, PageUtil pageUtil, String sourceFileName) throws Exception {
		List<JasperPrint> list = new ArrayList<JasperPrint>();
		List<UserCz> UserCzList = findUserCz(username, insertTime, status, pageUtil);
		if (!QwyUtil.isNullAndEmpty(UserCzList)) {
			Map<String, String> map = QwyUtil.getValueMap(UserCzList);
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(UserCzList);
			JasperPrint js = JasperFillManager.fillReport(sourceFileName, map, ds);
			list.add(js);
		}
		return list;
	}

	/**
	 * 获取当前条件下充值数据(用于报表查询)
	 * 
	 * @param username
	 * @param insertTime
	 * @param status
	 * @param pageUtil
	 * @throws Exception
	 */
	public List<UserCz> findUserCz(String username, String insertTime, String status, PageUtil pageUtil) throws Exception {
		ArrayList<Object> ob = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer();
		hql.append(" select cz.record_number,u.username,ui.real_name,cz.type,cz.money,cz.insert_time,cz.check_time,cz.order_id,cz.yb_order_id,cz.STATUS,cz.note ");
		hql.append(" from cz_record cz,users u,users_info ui WHERE 1=1 ");
		if (!"all".equals(status)) {
			hql.append(" AND cz.`STATUS` = ? ");
			ob.add(status);
		}
		hql.append(" AND cz.users_id=u.id AND cz.users_id=ui.users_id ");
		if (!QwyUtil.isNullAndEmpty(username)) {
			hql.append("  And u.username= ? ");
			ob.add(DESEncrypt.jiaMiUsername(username));
		}
		// 充值时间
		if (!QwyUtil.isNullAndEmpty(insertTime)) {
			String[] time = QwyUtil.splitTime(insertTime);
			if (time.length > 1) {
				hql.append(" AND cz.insert_time >= ? ");
				ob.add(QwyUtil.fmMMddyyyy.parse(time[0]));
				hql.append(" AND cz.insert_time <= ? ");
				ob.add(QwyUtil.fmMMddyyyy.parse(time[1]));
			} else {
				hql.append(" AND cz.insert_time >= ? ");
				ob.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 00:00:00"));
				hql.append(" AND cz.insert_time <= ? ");
				ob.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 23:59:59"));
			}
		}
		hql.append(" ORDER BY  cz.insert_time DESC, cz.check_time DESC ");
		StringBuffer hqlCount = new StringBuffer();
		hqlCount.append(" SELECT COUNT(t.record_number)  ");
		hqlCount.append(" FROM (");
		hqlCount.append(hql);
		hqlCount.append(") t");
		pageUtil = dao.getBySqlAndSqlCount(pageUtil, hql.toString(), hqlCount.toString(), ob.toArray());
		@SuppressWarnings("unchecked")

		List<UserCz> userCzList = findUserCz(pageUtil.getList());
		return userCzList;
	}

	/**
	 * 将充值数据转换为导出报表需要的数据
	 * 
	 * @throws Exception
	 */
	public List<UserCz> findUserCz(List<Object[]> objects) throws Exception {
		List<UserCz> list = new ArrayList<UserCz>();
		if (!QwyUtil.isNullAndEmpty(objects)) {
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				UserCz userCz = new UserCz();
				userCz.setId(i + 1 + "");
				userCz.setRecordNumber(object[0] + "");
				userCz.setUsername(!QwyUtil.isNullAndEmpty(object[1]) ? DESEncrypt.jieMiUsername(object[1] + "") : "");
				userCz.setRealname(!QwyUtil.isNullAndEmpty(object[2]) ? object[2] + "" : "");
				if ("1".equals(object[3] + "")) { // 充值状态 0 快捷支付 1 网银充值
					userCz.setType(" 网银充值");
				} else {
					userCz.setType("快捷支付");
				}
				userCz.setMoney(!QwyUtil.isNullAndEmpty(QwyUtil.calcNumber(object[4], "100", "/", 2)) ? QwyUtil.calcNumber(object[4], "100", "/", 2) + "" : "0");
				userCz.setInsertTime(!QwyUtil.isNullAndEmpty(object[5]) ? QwyUtil.fmyyyyMMddHHmmss.format(QwyUtil.fmyyyyMMddHHmmss.parse(object[5] + "")) : "");
				userCz.setCheckTime(!QwyUtil.isNullAndEmpty(object[6]) ? QwyUtil.fmyyyyMMddHHmmss.format(QwyUtil.fmyyyyMMddHHmmss.parse(object[6] + "")) : "");
				userCz.setOrderId(!QwyUtil.isNullAndEmpty(object[7]) ? object[7] + "" : "");
				userCz.setYbOrderId(!QwyUtil.isNullAndEmpty(object[8]) ? object[8] + "" : "");
				if ("0".equals(object[9] + "")) {// 提现状态;0:待充值;1:充值成功;2充值失败;3:易宝充值成功,数据库插入失败;
					userCz.setStatus("待充值");
				} else if ("1".equals(object[9] + "")) {
					userCz.setStatus("充值成功");
				} else if ("2".equals(object[9] + "")) {
					userCz.setStatus("充值失败");
				} else if ("3".equals(object[9] + "")) {
					userCz.setStatus("易宝充值成功,数据库插入失败");
				}

				userCz.setNote(!QwyUtil.isNullAndEmpty(object[10]) ? object[10] + "" : "");
				list.add(userCz);
			}
		}
		return list;
	}

	/**
	 * 导出提现记录报表
	 * 
	 * @param insertTime
	 *            提现时间
	 * @param username
	 *            用户名
	 * @param status
	 *            提现状态状态 all:全部 ;0:待审核;1:提现成功;2提现失败;3:正在审核
	 * 
	 * @param sourceFileName
	 *            文件地址
	 * @return
	 * @throws Exception
	 */
	public List<JasperPrint> getUserTxJasperPrintList(String username, String insertTime, String status, PageUtil pageUtil, String sourceFileName) throws Exception {
		List<JasperPrint> list = new ArrayList<JasperPrint>();
		List<UserCz> UserCzList = findUserTx(username, insertTime, status, pageUtil);
		if (!QwyUtil.isNullAndEmpty(UserCzList)) {
			Map<String, String> map = QwyUtil.getValueMap(UserCzList);
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(UserCzList);
			JasperPrint js = JasperFillManager.fillReport(sourceFileName, map, ds);
			list.add(js);
		}
		return list;
	}

	/**
	 * 获取当前条件下提现数据(用于报表查询)
	 * 
	 * @param username
	 * @param insertTime
	 * @param status
	 * @param pageUtil
	 * @throws Exception
	 */
	public List<UserCz> findUserTx(String username, String insertTime, String status, PageUtil pageUtil) throws Exception {
		ArrayList<Object> ob = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer();
		hql.append(" select tx.record_number,u.username,ui.real_name,tx.draw_type,tx.money,tx.insert_time,tx.check_time,tx.request_id,tx.yb_order_id,tx.note ");
		hql.append(" from tx_record tx,users u,users_info ui WHERE 1=1 ");
		if (!"all".equals(status)) {
			hql.append(" AND tx.`STATUS` = ? ");
			ob.add(status);
		}
		hql.append(" AND tx.users_id=u.id AND tx.users_id=ui.users_id ");
		if (!QwyUtil.isNullAndEmpty(username)) {
			hql.append("  And u.username= ? ");
			ob.add(DESEncrypt.jiaMiUsername(username));
		}
		// 提现时间
		if (!QwyUtil.isNullAndEmpty(insertTime)) {
			String[] time = QwyUtil.splitTime(insertTime);
			if (time.length > 1) {
				hql.append(" AND tx.insert_time >= ? ");
				ob.add(QwyUtil.fmMMddyyyy.parse(time[0]));
				hql.append(" AND tx.insert_time <= ? ");
				ob.add(QwyUtil.fmMMddyyyy.parse(time[1]));
			} else {
				hql.append(" AND tx.insert_time >= ? ");
				ob.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 00:00:00"));
				hql.append(" AND tx.insert_time <= ? ");
				ob.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 23:59:59"));
			}
		}
		hql.append(" ORDER BY  tx.insert_time DESC, tx.check_time DESC ");
		StringBuffer hqlCount = new StringBuffer();
		hqlCount.append(" SELECT COUNT(t.record_number)  ");
		hqlCount.append(" FROM (");
		hqlCount.append(hql);
		hqlCount.append(") t");
		pageUtil = dao.getBySqlAndSqlCount(pageUtil, hql.toString(), hqlCount.toString(), ob.toArray());
		@SuppressWarnings("unchecked")
		List<UserCz> userCzList = findUserTx(pageUtil.getList());
		return userCzList;
	}

	/**
	 * 将提现数据转换为导出报表需要的数据
	 * 
	 * @throws Exception
	 */
	public List<UserCz> findUserTx(List<Object[]> objects) throws Exception {
		List<UserCz> list = new ArrayList<UserCz>();
		if (!QwyUtil.isNullAndEmpty(objects)) {
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				UserCz userCz = new UserCz();
				userCz.setId(i + 1 + "");
				userCz.setRecordNumber(object[0] + "");
				userCz.setUsername(!QwyUtil.isNullAndEmpty(object[1]) ? DESEncrypt.jieMiUsername(object[1] + "") : "");
				userCz.setRealname(!QwyUtil.isNullAndEmpty(object[2]) ? object[2] + "" : "");
				if ("1".equals(object[3] + "")) {// 提现类型; 0:T+0到账; 1:T+1到账;
					userCz.setDrawType("T+1");
				} else {
					userCz.setDrawType("T+0");
				}
				userCz.setMoney(!QwyUtil.isNullAndEmpty(QwyUtil.calcNumber(object[4], "100", "/", 2)) ? QwyUtil.calcNumber(object[4], "100", "/", 2) + "" : "0");
				userCz.setInsertTime(!QwyUtil.isNullAndEmpty(object[5]) ? QwyUtil.fmyyyyMMddHHmmss.format(QwyUtil.fmyyyyMMddHHmmss.parse(object[5] + "")) : "");
				userCz.setCheckTime(!QwyUtil.isNullAndEmpty(object[6]) ? QwyUtil.fmyyyyMMddHHmmss.format(QwyUtil.fmyyyyMMddHHmmss.parse(object[6] + "")) : "");

				userCz.setRequestId(!QwyUtil.isNullAndEmpty(object[7]) ? object[7] + "" : "");
				userCz.setYbOrderId(!QwyUtil.isNullAndEmpty(object[8]) ? object[8] + "" : "");
				userCz.setNote(!QwyUtil.isNullAndEmpty(object[9]) ? object[9] + "" : "");
				list.add(userCz);
			}
		}
		return list;
	}

	/**
	 * 用户提现;
	 * 
	 * @param usersId
	 *            用户id
	 * @param amount
	 *            提现金额(分)
	 * @param userip
	 *            用户支付时使用的网络终端IP
	 * @param userAdminId
	 *            管理员ID
	 * @return JOSN格式;
	 * @throws Exception
	 */
	public String withdraw(long usersId, int amount, String userip, Long userAdminId, String note) {
		String json = "";
		try {
			Account account = yiBaoPayBean.getAccountByUsersId(usersId, "0");
			if (QwyUtil.isNullAndEmpty(account)) {
				json = QwyUtil.getJSONString("error", "提现失败,没有找到绑定的银行卡.");
			} else {
				String drawtype = "0";
				String ybdrawType = "NATRALDAY_URGENT";
				String requestId = UUID.randomUUID().toString();
				String tempJson = addRootTxRecord(usersId, amount, note, drawtype, userip, requestId, userAdminId);
				JSONObject tempJb = JSONObject.fromObject(tempJson);
				String tempStatus = tempJb.getString("status");
				if ("ok".equals(tempStatus)) {
					String txId = tempJb.getString("json");
					RootTxRecord tx = (RootTxRecord) dao.findById(new RootTxRecord(), txId);
					json = QwyUtil.getJSONString("ok", "提现请求成功，正在处理中");
					String myBankJson = payAPIService.withdraw(tx.getRequestId(), usersId + "", 2, account.getCardTop(), account.getCardLast(), 156, amount, ybdrawType, "", userip, "");
					JSONObject jb = JSONObject.fromObject(myBankJson);
					Object ob = jb.get("error_code");
					Object ybdrawflowid = jb.get("ybdrawflowid");
					if (!QwyUtil.isNullAndEmpty(ob)) {
						Object obj = jb.get("error_msg");
						tx.setErrorCode(ob + "");
						tx.setErrorMessage(obj + "");
						dao.saveOrUpdate(tx);
						json = QwyUtil.getJSONString("error", QwyUtil.isNullAndEmpty(obj) ? "提现失败" : obj.toString());
					} else if (!QwyUtil.isNullAndEmpty(ybdrawflowid)) {
						String yblsh = ybdrawflowid.toString();
						tx.setYbOrderId(yblsh);
						tx.setStatus("0");
						dao.saveOrUpdate(tx);
						json = QwyUtil.getJSONString("ok", "提现请求成功");
					} else {
						json = QwyUtil.getJSONString("error", "提现失败,提现接口访问失败");
					}
					return json;
				} else {
					json = tempJson;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ", e);
			json = QwyUtil.getJSONString("error", "提现失败,请联系客服,code: 8889");
		}
		return json;
	}

	@SuppressWarnings("unchecked")
	public PageUtil<RootTxRecord> findPageUtil(PageUtil<RootTxRecord> pageUtil, String insertTime) {
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append(" FROM  RootTxRecord rt ");
			List<Object> list = new ArrayList<Object>();
			// 提现时间
			if (!QwyUtil.isNullAndEmpty(insertTime)) {
				String[] time = QwyUtil.splitTime(insertTime);
				if (time.length > 1) {
					buffer.append(" AND rt.insert_time >= ? ");
					list.add(QwyUtil.fmMMddyyyy.parse(time[0]));
					buffer.append(" AND rt.insert_time < ? ");
					list.add(QwyUtil.fmMMddyyyy.parse(time[1]));
				} else {
					buffer.append(" AND rt.insert_time >= ? ");
					list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 00:00:00"));
					buffer.append(" AND rt.insert_time <= ? ");
					list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 23:59:59"));

				}
			}
			return dao.getByHqlAndHqlCount(pageUtil, buffer.toString(), buffer.toString(), list.toArray());
		} catch (Exception e) {
			log.error("操作异常: ", e);
		}
		return pageUtil;

	}

	/**
	 * 添加管理员提现记录; 数据插入数据库;
	 * 
	 * @param usersId
	 *            用户id
	 * @param money
	 *            充值金额(分)
	 * @param note
	 *            备注;
	 * @param drawType
	 *            //提现类型; 0:T+0到账; 1:T+1到账;
	 * @param userIp
	 *            用户提现时的IP
	 * @param requestId
	 *            用户提现时的请求的ID
	 * @param userAdminId
	 *            管理员ID
	 * @return String ""为字符串;有字符串为提现失败;
	 */
	public String addRootTxRecord(long usersId, double money, String note, String drawType, String userIp, String requestId, Long userAdminId) {
		try {
			Users users = (Users) dao.findById(new Users(), usersId);
			if (QwyUtil.isNullAndEmpty(users)) {
				return QwyUtil.getJSONString("error", "提现失败,用户不存在");
			}
			RootTxRecord record = new RootTxRecord();
			record.setDrawType(drawType);
			record.setInsertTime(new Date());
			record.setMoney(money);
			record.setRequestId(requestId);
			record.setUsersIp(userIp);
			record.setUserAdminId(userAdminId);
			record.setUsersId(usersId);
			record.setStatus("1");
			dao.save(record);
			return QwyUtil.getJSONString("ok", record.getId());
		} catch (Exception e) {
			log.error("操作异常: ", e);
		}
		return QwyUtil.getJSONString("error", "提现申请失败,请联系客服,code: 8845");
	}

	/**
	 * 零钱包现存总额
	 * 
	 * @return
	 */
	public String findInShiftMoney() {
		List<Object> arrayList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT sum(sh.left_money) from shift_to  sh where sh.status IN (0,1) ;");
		Object object = dao.getSqlCount(sql.toString(), arrayList.toArray());
		if (!QwyUtil.isNullAndEmpty(object)) {
			return object + "";
		}
		return "0";
	}

	/**
	 * 零钱包转出总额
	 * 
	 * @return
	 */
	public String findOutShiftMoney() {
		List<Object> arrayList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT SUM(in_money) FROM roll_out ;");
		Object object = dao.getSqlCount(sql.toString(), arrayList.toArray());
		if (!QwyUtil.isNullAndEmpty(object)) {
			return object + "";
		}
		return "0";
	}

	public List<Map<String, Object>> findCzTxTotal(String registPlatform, String insertTime) throws Exception {
		StringBuffer yunyingQueryListSql = new StringBuffer();
		createCzTxQueryListSql(yunyingQueryListSql, registPlatform, insertTime, false);
		List<Map<String, Object>> list = dao.LoadAllListMapSql(yunyingQueryListSql.toString(), null);
		return list;
	}

	public void createCzTxQueryListSql(StringBuffer yunyingQueryListSql, String registPlatform, String insertTime, boolean isGroupBy) throws ParseException {

		yunyingQueryListSql.append(" select us.date as date,");
		yunyingQueryListSql.append(" sum(rechargeCentSum ) as rechargeCentSum  , sum(rechargeCount ) as rechargeCount, ");
		yunyingQueryListSql.append(" sum(withdrawCentSum ) as withdrawCentSum, sum(withdrawCount ) as withdrawCount, ");
		yunyingQueryListSql.append(" sum(successWithdrawCentSum ) as successWithdrawCentSum, sum(successWithdrawCount ) as successWithdrawCount, ");

		yunyingQueryListSql.append(" sum(sameDaySuccessWithdrawCount ) as sameDaySuccessWithdrawCount, sum(sameDaySuccessWithdrawCentSum ) as sameDaySuccessWithdrawCentSum, ");
		yunyingQueryListSql.append(" sum(todayWithdrawNoArrivalCount ) as todayWithdrawNoArrivalCount, sum(todayWithdrawNoArrivalCentSum ) as todayWithdrawNoArrivalCentSum, ");
		yunyingQueryListSql
				.append(" sum(yesterdayWithdrawTodayArrivalCount ) as yesterdayWithdrawTodayArrivalCount, sum(yesterdayWithdrawTodayArrivalCentSum ) as yesterdayWithdrawTodayArrivalCentSum, ");
		yunyingQueryListSql
				.append(" sum(notSameDayWithdrawTodayArrivalCount ) as notSameDayWithdrawTodayArrivalCount, sum(notSameDayWithdrawTodayArrivalCentSum ) as notSameDayWithdrawTodayArrivalCentSum ");

		yunyingQueryListSql.append(" FROM back_stats_operate_day us WHERE 1=1 ");
		if (!QwyUtil.isNullAndEmpty(registPlatform)) {
			yunyingQueryListSql.append(" and us.registPlatform = ");
			yunyingQueryListSql.append(registPlatform);
		}

		// 发布时间
		if (!QwyUtil.isNullAndEmpty(insertTime)) {
			String[] time = QwyUtil.splitTime(insertTime);
			if (time.length > 1) {
				yunyingQueryListSql.append(" AND us.date >= '");
				yunyingQueryListSql.append(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[0])));
				yunyingQueryListSql.append("' AND us.date <= '");
				yunyingQueryListSql.append(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[1])));
				yunyingQueryListSql.append("' ");
			} else {
				yunyingQueryListSql.append(" AND us.date >= '");
				yunyingQueryListSql.append(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[0])));
				yunyingQueryListSql.append("' AND us.date <= '");
				yunyingQueryListSql.append(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[0])));
				yunyingQueryListSql.append("' ");
			}
		}
		if (isGroupBy) {
			yunyingQueryListSql.append(" GROUP BY DATE_FORMAT(us.date, '%Y-%m-%d')  ");
		}
		yunyingQueryListSql.append(" ORDER BY us.date DESC ");
	}

}
