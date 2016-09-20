package com.zfgt.admin.product.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.stereotype.Service;

import com.zfgt.admin.product.dao.ActivityDAO;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.ActivityStat;
import com.zfgt.orm.BackStatsOperateDay;
import com.zfgt.orm.ChannelOperateDay;
import com.zfgt.orm.PlatChannel;
import com.zfgt.orm.Qdcb;
import com.zfgt.orm.Qdtj;
/**
 * 入口统计
 * @author 曾礼强
 *2015年6月10日 10:55:05
 */
@Service
public class ActivityBean{
	@Resource
	ActivityDAO dao;
	@Resource
	UsersConvertBean bean;
	/**
	 * 获取Activity统计的数据
	 * @param insertTime 激活时间
	 * @return
	 */
	public List<ActivityStat> findTjActivitys(String insertTime) throws Exception{
		List<Object> arrayList=new ArrayList<Object>();
		StringBuffer sql=new StringBuffer(); 
		sql.append(" SELECT  aty.channel,COUNT(aty.channel)'激活人数',");
		sql.append(" (SELECT COUNT(*) FROM users us WHERE us.regist_channel = aty.channel AND us.regist_platform = '1') '注册人数' ,");
		sql.append(" (SELECT COUNT(*) FROM account ac LEFT JOIN users us ON ac.users_id = us.id WHERE ac.status = '0' AND us.regist_channel = aty.channel AND us.regist_platform = '1')'绑定银行卡人数' ");
		sql.append(" FROM Activity as aty   ");
		sql.append(" WHERE aty .channel is not NULL ");
		//发布时间
		if(!QwyUtil.isNullAndEmpty(insertTime)){
			String [] time=QwyUtil.splitTime(insertTime);
			if(time.length>1){
				sql.append(" AND aty.insert_time >= ? ");
				arrayList.add(QwyUtil.fmMMddyyyy.parse(time[0]));
				sql.append(" AND aty.insert_time <= ? ");
				arrayList.add(QwyUtil.fmMMddyyyy.parse(time[1]));
			}else{
				sql.append(" AND aty.insert_time >= ? ");
				arrayList.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
				sql.append(" AND aty.insert_time <= ? ");
				arrayList.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59"));
			}
		}
		sql.append(" GROUP BY aty.channel ");
		List<Object []> list=dao.LoadAllSql(sql.toString(), arrayList.toArray());
		return parseActivityStat(list);
	}
	/**
	 * 获取渠道报表数据
	 * @param insertTime
	 * @param sourceFileName
	 * @return
	 * @throws Exception
	 */
	public List<JasperPrint> getQdtjJasperPrintList(String insertTime,String sourceFileName) throws Exception {
		List<JasperPrint> list=new ArrayList<JasperPrint>();
		List<Qdtj> qdtjs=getQdtj(insertTime);
		if(!QwyUtil.isNullAndEmpty(qdtjs)){
			Qdtj qdtj=tjQdtj(qdtjs);
			Map<String, String> map=QwyUtil.getValueMap(qdtj);
			JRBeanCollectionDataSource ds=new JRBeanCollectionDataSource(qdtjs);	
			//JasperPrint 	 js=JasperFillManager.fillReport(context.getRealPath(path) +File.separator+getJxmlStr(), map, ds);"D:\\table"+File.separator+"releaseProduct.jasper"
			JasperPrint 	 js=JasperFillManager.fillReport(sourceFileName, map, ds);
			list.add(js);
		}
		return list;
	}
	
	/**
	 * 获取单个渠道详情报表数据
	 * @param registChannel
	 * @param insertTime
	 * @param sourceFileName
	 * @return
	 * @throws Exception
	 */
	public List<JasperPrint> getQdtjDetailJasperPrintList(String registChannel,String insertTime,String sourceFileName) throws Exception {
		List<JasperPrint> list=new ArrayList<JasperPrint>();
		List<ChannelOperateDay> details=new ArrayList<ChannelOperateDay>();
		List<ChannelOperateDay> ChannelOperateDays=getQdtjDetails(registChannel,insertTime);
		if(!QwyUtil.isNullAndEmpty(ChannelOperateDays)){
			for(Object obj:ChannelOperateDays){
				ChannelOperateDay channelOperateDay=(ChannelOperateDay)obj;
				channelOperateDay.setRegActivityRate(channelOperateDay.getRegActivityRate()*100);
				channelOperateDay.setReInvestRate(channelOperateDay.getReInvestRate()*100);
				details.add(channelOperateDay);
			}
			Map<String, String> map=QwyUtil.getValueMap(details);
			JRBeanCollectionDataSource ds=new JRBeanCollectionDataSource(details);	
			//JasperPrint 	 js=JasperFillManager.fillReport(context.getRealPath(path) +File.separator+getJxmlStr(), map, ds);"D:\\table"+File.separator+"releaseProduct.jasper"
			JasperPrint 	 js=JasperFillManager.fillReport(sourceFileName, map, ds);
			list.add(js);
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<ChannelOperateDay> getQdtjDetails(String registChannel,String insertTime) throws Exception{
		List<Object> list = new ArrayList<Object>(); 
		StringBuffer buffer = new StringBuffer(" FROM ChannelOperateDay cd  where 1=1 ");
		if (!QwyUtil.isNullAndEmpty(registChannel)) {
			buffer.append("  and cd.registChannel = ");
			buffer.append(Integer.valueOf(registChannel));
		}
		if (!QwyUtil.isNullAndEmpty(insertTime)) {
			String[] time = QwyUtil.splitTime(insertTime);
			if (time.length > 1) {
				buffer.append(" AND cd.date >= ? ");
				list.add(QwyUtil.fmMMddyyyy.parse(time[0]));
				buffer.append(" AND cd.date  <= ? ");
				list.add(QwyUtil.fmMMddyyyy.parse(time[1]));
			} else {
				buffer.append(" AND cd.date  >= ? ");
				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 00:00:00"));
				buffer.append(" AND cd.date  <= ? ");
				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 23:59:59"));
			}
		}
		List<ChannelOperateDay> details = dao.LoadAll(buffer.toString(), list.toArray());
		
		return details;
	}
	/**
	 * 将数据转换为ActivityStat
	 * @param list 
	 * @return
	 */
	public List<ActivityStat> parseActivityStat(List<Object []> list) throws Exception{
		List<ActivityStat> activityStats=new ArrayList<ActivityStat>();
		if(!QwyUtil.isNullAndEmpty(list)){
			for (Object [] object : list) {
				ActivityStat activityStat=new ActivityStat();
				activityStat.setChannel(object[0]+"");
				activityStat.setChannelCount(object[1]+"");
				activityStat.setRegCount(!QwyUtil.isNullAndEmpty(object[2])?object[2]+"":"0");
				activityStat.setBindCount(!QwyUtil.isNullAndEmpty(object[3])?object[3]+"":"0");
				activityStats.add(activityStat);
			}
		}
		return activityStats;
	}
	
	
	/**
	 * 以平台分组获取绑定人数
	 * @return
	 * @throws Exception 
	 */
	public String bindjl(String insertTime,List<Object> arrayList) throws Exception{
		StringBuffer buffer=new StringBuffer();
		buffer.append(" SELECT COUNT(DISTINCT acc.users_id) ,us.regist_channel ");
		buffer.append(" FROM account acc ");
		buffer.append("	INNER JOIN users us on us.id = acc.users_id ");
		buffer.append(" AND us.regist_platform = '1' ");
		buffer.append(" WHERE acc.`status` = '0'  ");
		if(!QwyUtil.isNullAndEmpty(insertTime)){
			String [] time=QwyUtil.splitTime(insertTime);
			if(time.length>1){
				buffer.append(" AND acc.insert_time >= ? ");
				arrayList.add(QwyUtil.fmMMddyyyy.parse(time[0]));
				buffer.append(" AND acc.insert_time < ? ");
				arrayList.add(QwyUtil.fmMMddyyyy.parse(time[1]));
			}else{
				buffer.append(" AND acc.insert_time >= ? ");
				arrayList.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
				buffer.append(" AND acc.insert_time <= ? ");
				arrayList.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59"));
			}
		}
		buffer.append(" GROUP BY us.regist_channel  ");
		return buffer.toString();
	}
	
	/**
	 * 以平台分组获取投资记录
	 * @return
	 * @throws Exception 
	 */
	public String tzjl(String insertTime,List<Object> arrayList) throws Exception{
		StringBuffer buffer=new StringBuffer();
		buffer.append(" SELECT COUNT(DISTINCT ivs.users_id) ,SUM(ivs.in_money) ,us.regist_channel ");
		buffer.append(" FROM investors ivs  ");
		buffer.append("	INNER  JOIN users us on us.id = ivs.users_id ");
		buffer.append(" AND us.regist_platform = '1' ");
		buffer.append(" WHERE ivs.investor_status in ('1','2','3')  ");
		if(!QwyUtil.isNullAndEmpty(insertTime)){
			String [] time=QwyUtil.splitTime(insertTime);
			if(time.length>1){
				buffer.append(" AND ivs.pay_time >= ? ");
				arrayList.add(QwyUtil.fmMMddyyyy.parse(time[0]));
				buffer.append(" AND ivs.pay_time < ? ");
				arrayList.add(QwyUtil.fmMMddyyyy.parse(time[1]));
			}else{
				buffer.append(" AND ivs.pay_time >= ? ");
				arrayList.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
				buffer.append(" AND ivs.pay_time <= ? ");
				arrayList.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59"));
			}
		}
		buffer.append(" GROUP BY us.regist_channel  ");
		return buffer.toString();
	}
	
	/**
	 * 以平台分组获取首投人数和金额
	 * @return
	 * @throws Exception 
	 */
	public String stjl(String insertTime,List<Object> arrayList) throws Exception{
	StringBuffer buffer=new StringBuffer();
	buffer.append(" SELECT COUNT(DISTINCT d.users_id1),SUM(d.in_money1),	c.regist_channel");
	buffer.append(" FROM users c,( SELECT	a.id,	a.users_id AS users_id1, a.in_money AS in_money1,");
	buffer.append(" a.copies AS copies,	a.pay_time AS pay_time1	FROM investors a, ");
	buffer.append(" (SELECT	users_id, MIN(pay_time) AS pay_time FROM investors WHERE investor_status IN (1, 2, 3) GROUP BY users_id) b");
	buffer.append("	WHERE	a.users_id = b.users_id	AND a.pay_time = b.pay_time		AND a.investor_status IN (1, 2, 3) ");
	buffer.append(" ) d");
	buffer.append(" WHERE c.id = d.users_id1 AND c.regist_platform = 1");
	if(!QwyUtil.isNullAndEmpty(insertTime)){
	String [] time=QwyUtil.splitTime(insertTime);
	if(time.length>1){
		buffer.append(" AND  d.pay_time1 >= ? ");
		arrayList.add(QwyUtil.fmMMddyyyy.parse(time[0]));
		buffer.append(" AND  d.pay_time1 < ? ");
		arrayList.add(QwyUtil.fmMMddyyyy.parse(time[1]));
	}else{
		buffer.append(" AND  d.pay_time1 >= ? ");
		arrayList.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
		buffer.append(" AND  d.pay_time1 <= ? ");
		arrayList.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59"));
	}
}
	buffer.append(" GROUP BY c.regist_channel");
	return buffer.toString();
}
//	public String stjl(String insertTime,List<Object> arrayList) throws Exception{
//		StringBuffer buffer=new StringBuffer();
//		buffer.append(" SELECT	COUNT(DISTINCT t.users_id),	SUM(t.in_money),	us.regist_channel FROM users us  ");
//		buffer.append(" LEFT JOIN (  ");
//		buffer.append("	SELECT	MIN(ins.pay_time) AS date,ins.users_id,ins.in_money ");
//		buffer.append(" FROM	investors ins ");
//		buffer.append(" WHERE ins.investor_status in ('1','2','3')  ");
//		if(!QwyUtil.isNullAndEmpty(insertTime)){
//			String [] time=QwyUtil.splitTime(insertTime);
//			if(time.length>1){
//				buffer.append(" AND ins.pay_time >= ? ");
//				arrayList.add(QwyUtil.fmMMddyyyy.parse(time[0]));
//				buffer.append(" AND ins.pay_time <= ? ");
//				arrayList.add(QwyUtil.fmMMddyyyy.parse(time[1]));
//			}else{
//				buffer.append(" AND ins.pay_time >= ? ");
//				arrayList.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
//				buffer.append(" AND ins.pay_time <= ? ");
//				arrayList.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59"));
//			}
//		}
//		buffer.append(" GROUP BY ins.users_id  ");
//		buffer.append(" )t  ");
//		buffer.append(" ON us.id = t.users_id  ");
//		buffer.append(" WHERE us.regist_platform = '1'  ");
//		buffer.append(" GROUP BY us.regist_channel  ");
//		return buffer.toString();
//	}
//	public String stjl(String insertTime,List<Object> arrayList) throws Exception{
//		StringBuffer buffer=new StringBuffer();
//		buffer.append(" SELECT COUNT(DISTINCT ivs.users_id) ,SUM(ivs.in_money) ,us.regist_channel ");
//		buffer.append(" FROM investors ivs ");
//		buffer.append("	LEFT JOIN users us on us.id = ivs.users_id ");
//		buffer.append(" AND us.regist_platform = '1' ");
//		buffer.append(" WHERE ivs.investor_status in ('1','2','3')  ");
//		buffer.append(" AND ivs.pay_time in ( SELECT MIN(ins.pay_time) as date FROM investors ins  WHERE ins.investor_status in ('1','2','3') GROUP BY users_id ) ");
//		if(!QwyUtil.isNullAndEmpty(insertTime)){
//			String [] time=QwyUtil.splitTime(insertTime);
//			if(time.length>1){
//				buffer.append(" AND ivs.pay_time >= ? ");
//				arrayList.add(QwyUtil.fmMMddyyyy.parse(time[0]));
//				buffer.append(" AND ivs.pay_time <= ? ");
//				arrayList.add(QwyUtil.fmMMddyyyy.parse(time[1]));
//			}else{
//				buffer.append(" AND ivs.pay_time >= ? ");
//				arrayList.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
//				buffer.append(" AND ivs.pay_time <= ? ");
//				arrayList.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59"));
//			}
//		}
//		buffer.append(" GROUP BY us.regist_channel  ");
//		return buffer.toString();
//	}
	
	/**
	 * 以平台分组获取复投人数和金额
	 * @return
	 * @throws Exception 
	 */
	public String ftjl(String insertTime,List<Object> arrayList) throws Exception{
		StringBuffer buffer=new StringBuffer();
		buffer.append(" SELECT count(	DISTINCT CASE	WHEN a.pay_time > b.minPayTime THEN	a.users_id	ELSE NULL	END	) reInvestUserSum ,c.regist_channel");
		buffer.append(" FROM investors a INNER JOIN (	SELECT	users_id,	MIN(DATE_FORMAT(pay_time, '%Y-%m-%d')) minPayDate,");
		buffer.append(" MIN(pay_time) minPayTime FROM	investors a	WHERE	a.investor_status IN (1, 2, 3)");
		buffer.append(" GROUP BY	users_id) b ON a.users_id = b.users_id");
		buffer.append(" INNER JOIN users c ON b.users_id = c.id	WHERE	a.investor_status IN (1, 2, 3)");
		buffer.append(" and c.regist_platform = '1'");
		if(!QwyUtil.isNullAndEmpty(insertTime)){
		String [] time=QwyUtil.splitTime(insertTime);
		if(time.length>1){
			buffer.append(" AND a.pay_time >= ? ");
			arrayList.add(QwyUtil.fmMMddyyyy.parse(time[0]));
			buffer.append(" AND a.pay_time < ? ");
			arrayList.add(QwyUtil.fmMMddyyyy.parse(time[1]));
		}else{
			buffer.append(" AND a.pay_time >= ? ");
			arrayList.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
			buffer.append(" AND a.pay_time <= ? ");
			arrayList.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59"));
		}
	}
		buffer.append("GROUP BY	c.regist_channel");
//		buffer.append(" SELECT COUNT(DISTINCT ivs.users_id) ,us.regist_channel  ");
//		buffer.append(" FROM investors ivs ");
//		buffer.append("	INNER JOIN users us on us.id = ivs.users_id ");
//		buffer.append(" AND us.regist_platform = '1' ");
//		buffer.append(" WHERE ivs.investor_status in ('1','2','3')  ");
//		buffer.append(" AND  NOT EXISTS ( SELECT t.id FROM  ( SELECT MIN(ins.pay_time) as date,id FROM investors ins  WHERE ins.investor_status in ('1','2','3') GROUP BY users_id )t WHERE t.id=ivs.id) ");
//		if(!QwyUtil.isNullAndEmpty(insertTime)){
//			String [] time=QwyUtil.splitTime(insertTime);
//			if(time.length>1){
//				buffer.append(" AND ivs.pay_time >= ? ");
//				arrayList.add(QwyUtil.fmMMddyyyy.parse(time[0]));
//				buffer.append(" AND ivs.pay_time < ? ");
//				arrayList.add(QwyUtil.fmMMddyyyy.parse(time[1]));
//			}else{
//				buffer.append(" AND ivs.pay_time >= ? ");
//				arrayList.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
//				buffer.append(" AND ivs.pay_time <= ? ");
//				arrayList.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59"));
//			}
//		}
//		buffer.append(" GROUP BY us.regist_channel  ");
		return buffer.toString();
	}
	/*public String ftjl(String insertTime,List<Object> arrayList) throws Exception{
		StringBuffer buffer=new StringBuffer();
		buffer.append(" SELECT COUNT(DISTINCT ivs.users_id) ,us.regist_channel  ");
		buffer.append(" FROM investors ivs ");
		buffer.append("	LEFT JOIN users us on us.id = ivs.users_id ");
		buffer.append(" AND us.regist_platform = '1' ");
		buffer.append(" WHERE ivs.investor_status in ('1','2','3')  ");
		buffer.append(" AND ivs.pay_time not in ( SELECT MIN(ins.pay_time) as date FROM investors ins  WHERE ins.investor_status in ('1','2','3') GROUP BY users_id ) ");
		if(!QwyUtil.isNullAndEmpty(insertTime)){
			String [] time=QwyUtil.splitTime(insertTime);
			if(time.length>1){
				buffer.append(" AND ivs.pay_time >= ? ");
				arrayList.add(QwyUtil.fmMMddyyyy.parse(time[0]));
				buffer.append(" AND ivs.pay_time <= ? ");
				arrayList.add(QwyUtil.fmMMddyyyy.parse(time[1]));
			}else{
				buffer.append(" AND ivs.pay_time >= ? ");
				arrayList.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
				buffer.append(" AND ivs.pay_time <= ? ");
				arrayList.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59"));
			}
		}
		buffer.append(" GROUP BY us.regist_channel  ");
		return buffer.toString();
	}*/
	

	/**
	 * 以平台分组获取充值次数和金额
	 * @return
	 * @throws Exception 
	 */
	public String czjl(String insertTime,List<Object> arrayList) throws Exception{
		StringBuffer buffer=new StringBuffer();
		buffer.append(" SELECT COUNT(DISTINCT cr.id) ,SUM(cr.money) ,us.regist_channel ");
		buffer.append(" FROM cz_record cr ");
		buffer.append("	INNER JOIN users us on us.id = cr.users_id ");
		buffer.append(" AND us.regist_platform = '1' ");
		buffer.append(" WHERE cr.`STATUS` = '1'  ");
		if(!QwyUtil.isNullAndEmpty(insertTime)){
			String [] time=QwyUtil.splitTime(insertTime);
			if(time.length>1){
				buffer.append(" AND cr.insert_time >= ? ");
				arrayList.add(QwyUtil.fmMMddyyyy.parse(time[0]));
				buffer.append(" AND cr.insert_time < ? ");
				arrayList.add(QwyUtil.fmMMddyyyy.parse(time[1]));
			}else{
				buffer.append(" AND cr.insert_time >= ? ");
				arrayList.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
				buffer.append(" AND cr.insert_time <= ? ");
				arrayList.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59"));
			}
		}
		buffer.append(" GROUP BY us.regist_channel  ");
		return buffer.toString();
	}
	
	public String insUsersCountSQL(String insertTime) throws Exception{
		ArrayList<Object> list=new ArrayList<Object>();
		StringBuffer buffer=new StringBuffer();
		buffer.append(" SELECT COUNT(DISTINCT users_id) FROM investors ivs  ");
		buffer.append(" WHERE ivs.investor_status in ('1','2','3') ");
		buffer.append(" AND EXISTS ( ");
		buffer.append(" SELECT id FROM  users  us ");
		buffer.append("  WHERE us.regist_platform = '1' ");
		buffer.append("  AND us.regist_channel != '' ");
		buffer.append("  AND us.regist_channel IS NOT NULL ");
		buffer.append("  AND us.id=ivs.users_id ");
		buffer.append("  ) ");
		if(!QwyUtil.isNullAndEmpty(insertTime)){
			String [] time=QwyUtil.splitTime(insertTime);
			if(time.length>1){
				buffer.append(" AND DATE_FORMAT(ivs.pay_time, '%Y-%m-%d' )>= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[0])));
				buffer.append(" AND DATE_FORMAT(ivs.pay_time, '%Y-%m-%d' )<= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyyHHmmss.parse(time[1]+" 23:59:59")));
			}else{
				buffer.append(" AND DATE_FORMAT(ivs.pay_time, '%Y-%m-%d' )>= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00")));
				buffer.append(" AND DATE_FORMAT(ivs.pay_time, '%Y-%m-%d' )<= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59")));
			}
		}
		Object object=dao.getSqlCount(buffer.toString(),list.toArray());
		if(!QwyUtil.isNullAndEmpty(object)){
			return object+"";
		}
		return "0";
	}
	/**
	 * 投资金额
	 * @param insertTime 投资时间
	 * @throws Exception 
	 */
	public String insMoney(String insertTime) throws Exception{
		ArrayList<Object> list=new ArrayList<Object>();
		StringBuffer buffer=new StringBuffer();
//		buffer.append(" SELECT SUM(ivs.in_money) FROM investors ivs  ");
//		buffer.append(" AND EXISTS ( ");
//		buffer.append(" SELECT id FROM  users  us ");
//		buffer.append("  WHERE us.regist_platform = '1' ");
//		buffer.append("  AND us.regist_channel != '' ");
//		buffer.append("  AND us.regist_channel IS NOT NULL ");
//		buffer.append("  AND us.id=ivs.users_id ");
//		buffer.append("  ) ");
//		if(!QwyUtil.isNullAndEmpty(insertTime)){
//			String [] time=QwyUtil.splitTime(insertTime);
//			if(time.length>1){
//				buffer.append(" AND ivs.pay_time>= ? ");
//				list.add(QwyUtil.fmMMddyyyy.parse(time[0]));
//				buffer.append(" AND ivs.pay_time<= ? ");
//				list.add(QwyUtil.fmMMddyyyy.parse(time[1]));
//			}else{
//				buffer.append(" AND ivs.pay_time>= ? ");
//				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
//				buffer.append(" AND ivs.pay_time<= ? ");
//				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59"));
//			}
//		}
		buffer.append(insMoneySQL(insertTime, list));
		Object object=dao.getSqlCount(buffer.toString(),list.toArray());
		if(!QwyUtil.isNullAndEmpty(object)){
			return object+"";
		}
		return "0";
	}
	public String insMoneySQL(String insertTime,ArrayList<Object> list) throws Exception{
		StringBuffer buffer=new StringBuffer();
		buffer.append(" SELECT SUM(ivs.in_money) FROM investors ivs  ");
		buffer.append(" WHERE ivs.investor_status in ('1','2','3') ");
		buffer.append(" AND EXISTS ( ");
		buffer.append(" SELECT id FROM  users  us ");
		buffer.append("  WHERE us.regist_platform = '1' ");
		buffer.append("  AND us.regist_channel != '' ");
		buffer.append("  AND us.regist_channel IS NOT NULL ");
		buffer.append("  AND us.id=ivs.users_id ");
		buffer.append("  ) ");
		if(!QwyUtil.isNullAndEmpty(insertTime)){
			String [] time=QwyUtil.splitTime(insertTime);
			if(time.length>1){
				buffer.append(" AND DATE_FORMAT(ivs.pay_time, '%Y-%m-%d' )>= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[0])));
				buffer.append(" AND DATE_FORMAT(ivs.pay_time, '%Y-%m-%d' )< ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyyHHmmss.parse(time[1]+" 23:59:59")));
			}else{
				
				buffer.append(" AND DATE_ADD( DATE_FORMAT( ivs.pay_time  , '%Y-%m-%d'),INTERVAL 31 DAY)  > ?  ");
				list.add(QwyUtil.fmyyyyMMdd.format(new Date(time[0]+"")));
				buffer.append(" AND  ivs.pay_time <= ? ");
				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59"));
			}
		}
		return buffer.toString();
	}
	
	/**
	 * 首投人数
	 * @param insertTime
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public String firstInsUsersCountSQL(String insertTime,ArrayList<Object> list) throws Exception{
//		StringBuffer buffer=new StringBuffer();
//		buffer.append(" SELECT COUNT(DISTINCT t.users_id) FROM (   ");
//		buffer.append("  SELECT  MIN(ins.pay_time) AS date,users_id  FROM   investors ins  ");
//		buffer.append(" WHERE		ins.investor_status IN ('1', '2', '3')  ");
//		buffer.append(" AND EXISTS ( ");
//		buffer.append(" SELECT id FROM  users  us ");
//		buffer.append("  WHERE us.regist_platform = '1' ");
//		buffer.append("  AND us.regist_channel != '' ");
//		buffer.append("  AND us.regist_channel IS NOT NULL ");
//		buffer.append("  AND us.id=ins.users_id ");
//		buffer.append("  ) ");
//		buffer.append("  GROUP BY	users_id ");
//		buffer.append(" )t ");
//		if(!QwyUtil.isNullAndEmpty(insertTime)){
//			String [] time=QwyUtil.splitTime(insertTime);
//			if(time.length>1){
//				buffer.append(" WHERE DATE_FORMAT(t.date, '%Y-%m-%d' )>= ? ");
//				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[0])));
//				buffer.append(" AND DATE_FORMAT(t.date, '%Y-%m-%d' )<= ? ");
//				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[1])));
//			}else{
//				buffer.append(" WHERE DATE_FORMAT(t.date, '%Y-%m-%d' )>= ? ");
//				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00")));
//				buffer.append(" AND DATE_FORMAT(t.date, '%Y-%m-%d' )<= ? ");
//				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59")));
//			}
//		}
		StringBuffer buffer=new StringBuffer();
		buffer.append(" SELECT SUM(m.usercount) FROM (   ");
		buffer.append(" SELECT	COUNT(DISTINCT t.users_id) as usercount,	SUM(t.in_money),	us.regist_channel FROM users us  ");
		buffer.append(" INNER JOIN (  ");
		buffer.append(" SELECT	ivs.users_id as users_id ,	ivs.in_money AS in_money,	ivs.pay_time FROM investors ivs  ");
		buffer.append(" WHERE ivs.investor_status in ('1','2','3')  ");
		buffer.append(" AND EXISTS ( SELECT	w.date	FROM	( ");
		buffer.append("	SELECT	MIN(ins.pay_time) AS date,ins.users_id,ins.in_money ");
		buffer.append(" FROM	investors ins ");
		buffer.append(" WHERE ins.investor_status in ('1','2','3')  ");
		buffer.append(" GROUP BY ins.users_id  ");
		buffer.append(" ) w  ");
		buffer.append(" WHERE	w.date = ivs.pay_time ");
		buffer.append(" ) ");
		if(!QwyUtil.isNullAndEmpty(insertTime)){
			String [] time=QwyUtil.splitTime(insertTime);
			if(time.length>1){
				buffer.append(" AND ivs.pay_time >= ? ");
				list.add(QwyUtil.fmMMddyyyy.parse(time[0]));
				buffer.append(" AND ivs.pay_time < ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyyHHmmss.parse(time[1]+" 23:59:59")));
			}else{
				buffer.append(" AND ivs.pay_time >= ? ");
				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
				buffer.append(" AND ivs.pay_time <= ? ");
				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59"));
			}
		}
		
		buffer.append(" )t ");

		buffer.append(" ON us.id = t.users_id  ");
		buffer.append(" WHERE us.regist_platform = '1'  ");
		buffer.append("  AND us.regist_channel != '' ");
		buffer.append("  AND us.regist_channel IS NOT NULL ");
		buffer.append(" GROUP BY us.regist_channel  ");
		buffer.append(" )m ");
		return buffer.toString();
		
	}
	/**
	 * 首投金额
	 * @param insertTime 投资时间
	 * @throws Exception 
	 */
	public String firstInsMoney(String insertTime) throws Exception{
		ArrayList<Object> list=new ArrayList<Object>();
		StringBuffer buffer=new StringBuffer();
//		buffer.append(" SELECT SUM(ivs.in_money) FROM investors ivs ");
//		buffer.append(" WHERE ivs.investor_status IN ('1','2','3') ");
//		buffer.append(" AND ivs.pay_time in ( ");
//		buffer.append("  SELECT MIN(ins.pay_time) as date FROM investors ins  WHERE ins.investor_status in ('1','2','3') GROUP BY users_id ) ");
//		buffer.append(" AND EXISTS ( ");
//		buffer.append(" SELECT id FROM  users  us ");
//		buffer.append("  WHERE us.regist_platform = '1' ");
//		buffer.append("  AND us.regist_channel != '' ");
//		buffer.append("  AND us.regist_channel IS NOT NULL ");
//		buffer.append("  AND us.id=ivs.users_id ");
//		buffer.append("  ) ");
//		if(!QwyUtil.isNullAndEmpty(insertTime)){
//			String [] time=QwyUtil.splitTime(insertTime);
//			if(time.length>1){
//				buffer.append(" AND ivs.pay_time>= ? ");
//				list.add(QwyUtil.fmMMddyyyy.parse(time[0]));
//				buffer.append(" AND ivs.pay_time<= ? ");
//				list.add(QwyUtil.fmMMddyyyy.parse(time[1]));
//			}else{
//				buffer.append(" AND ivs.pay_time>= ? ");
//				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
//				buffer.append(" AND ivs.pay_time<= ? ");
//				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59"));
//			}
//		}
		//buffer.append(firstInsMoneySQL(insertTime, list));
		buffer.append(" SELECT  sum(t.stje) FROM ( ");
		buffer.append(" SELECT DATE_FORMAT( dd.insert_time, '%Y-%m-%d' ) as date,  ");
		buffer.append("  q.strs , ");
		buffer.append("  q.stje");
		buffer.append(" FROM dateday dd  ");
		//首投人数，首投金额
		buffer.append(" LEFT JOIN (  ");
		buffer.append(" SELECT DATE_FORMAT(shoutouDate, '%Y-%m-%d') as date,COUNT(*) AS strs,SUM(in_money) as stje FROM ( ");
		buffer.append(" SELECT DATE_FORMAT(investors1.pay_time,'%Y-%m-%d') AS shoutouDate,investors1.users_id ,investors1.in_money FROM investors investors1 ");
		buffer.append(" INNER JOIN ( ");
		buffer.append(" SELECT MIN(t.pay_time) as minDate,users_id FROM investors t WHERE t.investor_status IN ('1','2','3') GROUP BY t.users_id ");
		buffer.append(" ) investors2 ON investors1.pay_time=investors2.minDate  ");
		buffer.append(" INNER JOIN users u ON u.id=investors1.users_id   ");
		buffer.append(" AND u.regist_platform = '1'  AND u.regist_channel != ''  AND u.regist_channel IS NOT NULL ");
		buffer.append(" WHERE DATE_FORMAT(investors1.pay_time,'%Y-%m-%d')=DATE_FORMAT(investors2.minDate,'%Y-%m-%d') GROUP BY investors1.users_id ");
		buffer.append(" ) tab4 GROUP BY shoutouDate ");
		buffer.append(" ) q ON q.date = DATE_FORMAT(dd.insert_time, '%Y-%m-%d')  ");
		if(!QwyUtil.isNullAndEmpty(insertTime)){
			String [] time=QwyUtil.splitTime(insertTime);
			if(time.length>1){
				buffer.append(" WHERE dd.insert_time>= ? ");
				list.add(QwyUtil.fmMMddyyyy.parse(time[0]));
				buffer.append(" AND dd.insert_time< ? ");
				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[1]+" 23:59:59"));
			}else{
				buffer.append("  WHERE DATE_ADD( DATE_FORMAT(  dd.insert_time  , '%Y-%m-%d'),INTERVAL 31 DAY)  > ?  ");
				list.add(QwyUtil.fmyyyyMMdd.format(new Date(time[0]+"")));
				buffer.append(" AND   dd.insert_time  <= ? ");
				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59"));
			}
		}
		buffer.append("  GROUP BY DATE_FORMAT(dd.insert_time, '%Y-%m-%d' )");
		buffer.append("  ORDER BY DATE_FORMAT(dd.insert_time, '%Y-%m-%d' ) DESC");
		buffer.append(" )t ");
		Object object=dao.getSqlCount(buffer.toString(),list.toArray());
		if(!QwyUtil.isNullAndEmpty(object)){
			return object+"";
		}
		return "0";
	}
	/**
	 * 手机金额
	 * @param insertTime
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public String firstInsMoneySQL(String insertTime,ArrayList<Object> list) throws Exception{
//		StringBuffer buffer=new StringBuffer();
//		buffer.append(" SELECT SUM(t.in_money) FROM (   ");
//		buffer.append(" SELECT	ivs.users_id as users_id ,	ivs.in_money AS in_money,	ivs.pay_time FROM investors ivs  ");
//		buffer.append(" WHERE ivs.investor_status in ('1','2','3')  ");
//		buffer.append(" AND EXISTS ( SELECT	w.date	FROM	( ");
//		buffer.append("	SELECT	MIN(ins.pay_time) AS date,ins.users_id,ins.in_money ");
//		buffer.append(" FROM	investors ins ");
//		buffer.append(" WHERE ins.investor_status in ('1','2','3')  ");
//		buffer.append(" GROUP BY ins.users_id  ");
//		buffer.append(" ) w  ");
//		buffer.append(" WHERE	w.date = ivs.pay_time ");
//		buffer.append(" ) )t ");
//		if(!QwyUtil.isNullAndEmpty(insertTime)){
//			String [] time=QwyUtil.splitTime(insertTime);
//			if(time.length>1){
//				buffer.append(" WHERE DATE_FORMAT(t.pay_time, '%Y-%m-%d' )>= ? ");
//				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[0])));
//				buffer.append(" AND DATE_FORMAT(t.pay_time, '%Y-%m-%d' )<= ? ");
//				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[1])));
//			}else{
//				buffer.append(" WHERE DATE_FORMAT(t.pay_time, '%Y-%m-%d' )>= ? ");
//				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00")));
//				buffer.append(" AND DATE_FORMAT(t.pay_time, '%Y-%m-%d' )<= ? ");
//				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59")));
//			}
//		}
		StringBuffer buffer=new StringBuffer();
		buffer.append(" SELECT SUM(m.in_money) FROM (   ");
		buffer.append(" SELECT	COUNT(DISTINCT t.users_id) as usercount,	SUM(t.in_money) as in_money,	us.regist_channel FROM users us  ");
		buffer.append(" INNER JOIN (  ");
		buffer.append(" SELECT	ivs.users_id as users_id ,	ivs.in_money AS in_money,	ivs.pay_time FROM investors ivs  ");
		buffer.append(" WHERE ivs.investor_status in ('1','2','3')  ");
		buffer.append(" AND EXISTS ( SELECT	w.date	FROM	( ");
		buffer.append("	SELECT	MIN(ins.pay_time) AS date,ins.users_id,ins.in_money ");
		buffer.append(" FROM	investors ins ");
		buffer.append(" WHERE ins.investor_status in ('1','2','3')  ");
		buffer.append(" GROUP BY ins.users_id  ");
		buffer.append(" ) w  ");
		buffer.append(" WHERE	w.date = ivs.pay_time ");
		buffer.append(" ) ");
		if(!QwyUtil.isNullAndEmpty(insertTime)){
			String [] time=QwyUtil.splitTime(insertTime);
			if(time.length>1){
				buffer.append(" AND ivs.pay_time >= ? ");
				list.add(QwyUtil.fmMMddyyyy.parse(time[0]));
				buffer.append(" AND ivs.pay_time < ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyyHHmmss.parse(time[1]+" 23:59:59")));
			}else{
				buffer.append(" AND ivs.pay_time >= ? ");
				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
				buffer.append(" AND ivs.pay_time <= ? ");
				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59"));
			}
		}
		
		buffer.append(" )t ");

		buffer.append(" ON us.id = t.users_id  ");
		buffer.append(" WHERE us.regist_platform = '1'  ");
		buffer.append("  AND us.regist_channel != '' ");
		buffer.append("  AND us.regist_channel IS NOT NULL ");
		buffer.append(" GROUP BY us.regist_channel  ");
		buffer.append(" )m ");
		return buffer.toString();
		
	}
	/**
	 * 绑定人数
	 * @param insertTime 绑定时间
	 * @throws Exception 
	 */
	public String bindUserCount(String insertTime) throws Exception{
		ArrayList<Object> list=new ArrayList<Object>();
		StringBuffer buffer=new StringBuffer();
		buffer.append("  SELECT  COUNT(DISTINCT acc.users_id) FROM account acc  ");
		buffer.append("  WHERE  acc.`status` = '0' ");
		if(!QwyUtil.isNullAndEmpty(insertTime)){
			String [] time=QwyUtil.splitTime(insertTime);
			if(time.length>1){
				buffer.append(" AND DATE_FORMAT(acc.insert_time, '%Y-%m-%d' )>= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[0])));
				buffer.append(" AND DATE_FORMAT(acc.insert_time, '%Y-%m-%d' )< ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[1])));
			}else{
				buffer.append(" AND DATE_FORMAT(acc.insert_time, '%Y-%m-%d' )>= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00")));
				buffer.append(" AND DATE_FORMAT(acc.insert_time, '%Y-%m-%d' )<= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59")));
			}
		}
		buffer.append(" AND EXISTS ( ");
		buffer.append(" SELECT id FROM  users  us ");
		buffer.append("  WHERE us.regist_platform = '1' ");
		buffer.append("  AND us.regist_channel != '' ");
		buffer.append("  AND us.regist_channel IS NOT NULL ");
		buffer.append("  AND us.id=acc.users_id ");
		buffer.append("  ) ");
		Object object=dao.getSqlCount(buffer.toString(),list.toArray());
		if(!QwyUtil.isNullAndEmpty(object)){
			return object+"";
		}
		return "0";
	}

	public String regUserCountSQL(String insertTime,ArrayList<Object> list) throws Exception{
		StringBuffer buffer=new StringBuffer();
		buffer.append("  SELECT  COUNT(DISTINCT us.id) FROM  users us ");
		buffer.append("  WHERE us.regist_platform = '1' ");
		buffer.append("  AND us.regist_channel != '' ");
		buffer.append("  AND us.regist_channel IS NOT NULL ");
		if(!QwyUtil.isNullAndEmpty(insertTime)){
			String [] time=QwyUtil.splitTime(insertTime);
			if(time.length>1){
				buffer.append(" AND DATE_FORMAT(us.insert_time, '%Y-%m-%d' )>= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[0])));
				buffer.append(" AND DATE_FORMAT(us.insert_time, '%Y-%m-%d' )<= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyyHHmmss.parse(time[1]+" 23:59:59")));
			}else{
				buffer.append(" AND DATE_FORMAT(us.insert_time, '%Y-%m-%d' )>= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00")));
				buffer.append(" AND DATE_FORMAT(us.insert_time, '%Y-%m-%d' )<= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59")));
			}
		}
		return buffer.toString();
	}
	
	
	
	/**
	 * 充值次数
	 * @param insertTime 充值时间
	 * @throws Exception 
	 */
	public String czCount(String insertTime) throws Exception{
		ArrayList<Object> list=new ArrayList<Object>();
		StringBuffer buffer=new StringBuffer();
		buffer.append("  SELECT COUNT(DISTINCT cr.id) FROM cz_record cr  ");
		buffer.append("  WHERE cr.`STATUS` = '1' ");
		if(!QwyUtil.isNullAndEmpty(insertTime)){
			String [] time=QwyUtil.splitTime(insertTime);
			if(time.length>1){
				buffer.append(" AND DATE_FORMAT(cr.insert_time, '%Y-%m-%d' )>= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[0])));
				buffer.append(" AND DATE_FORMAT(cr.insert_time, '%Y-%m-%d' )< ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[1])));
			}else{
				buffer.append(" AND DATE_FORMAT(cr.insert_time, '%Y-%m-%d' )>= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00")));
				buffer.append(" AND DATE_FORMAT(cr.insert_time, '%Y-%m-%d' )<= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59")));
			}
		}
		buffer.append(" AND EXISTS ( ");
		buffer.append(" SELECT id FROM  users  us ");
		buffer.append("  WHERE us.regist_platform = '1' ");
		buffer.append("  AND us.regist_channel != '' ");
		buffer.append("  AND us.regist_channel IS NOT NULL ");
		buffer.append("  AND us.id=cr.users_id ");
		buffer.append("  ) ");
		Object object=dao.getSqlCount(buffer.toString(),list.toArray());
		if(!QwyUtil.isNullAndEmpty(object)){
			return object+"";
		}
		return "0";
	}
	
	/**
	 * 充值金额
	 * @param insertTime 充值时间
	 * @throws Exception 
	 */
	public String czMoney(String insertTime) throws Exception{
		ArrayList<Object> list=new ArrayList<Object>();
		StringBuffer buffer=new StringBuffer();
		buffer.append("  SELECT SUM(cr.money) FROM cz_record cr  ");
		buffer.append("  WHERE cr.`STATUS` = '1' ");
		if(!QwyUtil.isNullAndEmpty(insertTime)){
			String [] time=QwyUtil.splitTime(insertTime);
			if(time.length>1){
				buffer.append(" AND DATE_FORMAT(cr.insert_time, '%Y-%m-%d' )>= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[0])));
				buffer.append(" AND DATE_FORMAT(cr.insert_time, '%Y-%m-%d' )< ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[1])));
			}else{
				buffer.append(" AND DATE_FORMAT(cr.insert_time, '%Y-%m-%d' )>= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00")));
				buffer.append(" AND DATE_FORMAT(cr.insert_time, '%Y-%m-%d' )<= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59")));
			}
		}
		buffer.append(" AND EXISTS ( ");
		buffer.append(" SELECT id FROM  users  us ");
		buffer.append("  WHERE us.regist_platform = '1' ");
		buffer.append("  AND us.regist_channel != '' ");
		buffer.append("  AND us.regist_channel IS NOT NULL ");
		buffer.append("  AND us.id=cr.users_id ");
		buffer.append("  ) ");
		Object object=dao.getSqlCount(buffer.toString(),list.toArray());
		if(!QwyUtil.isNullAndEmpty(object)){
			return object+"";
		}
		return "0";
	}
	
	
	
	/**
	 * 将数据转换为渠道成本
	 * @param list 
	 * @return
	 */
	public List<Qdcb> parseQdcb(List<Object []> list) throws Exception{
		List<Qdcb> qdcbs=new ArrayList<Qdcb>();
		if(!QwyUtil.isNullAndEmpty(list)){
			for (int i = 0; i < list.size(); i++) {
				Object [] object=list.get(i);
				Qdcb qdcb=new Qdcb();
				qdcb.setDate(object[0]+"");
				qdcb.setRegUsersCount(!QwyUtil.isNullAndEmpty(object[1])?object[1]+"":"0");
				qdcb.setInsUsersCount(!QwyUtil.isNullAndEmpty(object[2])?object[2]+"":"0");
				qdcb.setInsMoney(!QwyUtil.isNullAndEmpty(object[3])?QwyUtil.calcNumber(object[3], 100, "/", 2)+"":"0");
				qdcb.setStrs(!QwyUtil.isNullAndEmpty(object[4])?object[4]+"":"0");
				qdcb.setStje(!QwyUtil.isNullAndEmpty(object[5])?QwyUtil.calcNumber(object[5], 100, "/", 2)+"":"0");
				qdcbs.add(qdcb);
			}
		}
		return qdcbs;
	}
	
	
	/**
	 * 合计
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public Qdcb tjQdcb(String insertTime) {
		List<Object> list=new ArrayList<Object>();
		Qdcb cr=new Qdcb();
		try {
		StringBuffer buffer = new StringBuffer("FROM BackStatsOperateDay rr ");
		buffer.append(" WHERE 1=1 and rr.registPlatform =1 ");
		if(!QwyUtil.isNullAndEmpty(insertTime)){
			String [] time=QwyUtil.splitTime(insertTime);
			if(time.length>1){
				buffer.append(" AND rr.date >= ? ");
				list.add(QwyUtil.fmMMddyyyy.parse(time[0]));
				buffer.append(" AND rr.date  <= ? ");
				list.add(QwyUtil.fmMMddyyyy.parse(time[1]));
			}else{
				buffer.append(" AND rr.date  >= ? ");
				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
				buffer.append(" AND rr.date  <= ? ");
				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59"));
			}
		}
		List<BackStatsOperateDay> backQdtj = dao.LoadAll(buffer.toString(),list.toArray());
		if(!QwyUtil.isNullAndEmpty(backQdtj)){
			//投资人数
			 cr.setInsUsersCount(insUsersCountSQL(insertTime));
			for (BackStatsOperateDay q : backQdtj) {
				//注册人数
				if(QwyUtil.isNullAndEmpty(cr.getRegUsersCount()))
					cr.setRegUsersCount("0");
				//投资人数
				//if(QwyUtil.isNullAndEmpty(cr.getInsUsersCount()))
					//cr.setInsUsersCount("0");
				//投资金额
				if(QwyUtil.isNullAndEmpty(cr.getInsMoney()))
					cr.setInsMoney("0");
				//首投人数
				if(QwyUtil.isNullAndEmpty(cr.getStrs()))
					cr.setStrs("0");
				//首投金额
				if(QwyUtil.isNullAndEmpty(cr.getStje()))
					cr.setStje("0");
				
				cr.setRegUsersCount(QwyUtil.calcNumber(cr.getRegUsersCount(), q.getRegUserSum(), "+")+"");
			//	cr.setInsUsersCount(QwyUtil.calcNumber(cr.getInsUsersCount(), q.getInvestUserSum(), "+")+"");
		        cr.setInsMoney(QwyUtil.calcNumber(cr.getInsMoney(), q.getInvestCentSum(), "+")+"");
		        cr.setStrs(QwyUtil.calcNumber( cr.getStrs(), q.getFirstInvestUserSum(), "+")+"");
		        cr.setStje(QwyUtil.calcNumber( cr.getStje(), q.getFirstInvestCentSum(), "+")+"");
				
		}
			
		}
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cr;
	}
	
	
	
	
	
	@SuppressWarnings("unchecked")
	public PageUtil<BackStatsOperateDay> findQdcb(PageUtil pageUtil,String insertTime) throws Exception{
		List<Object> list=new ArrayList<Object>();
		StringBuffer buffer=new StringBuffer();
		buffer.append(" FROM BackStatsOperateDay us");
		buffer.append(" WHERE 1=1 and us.registPlatform =1 ");

		//发布时间
		if(!QwyUtil.isNullAndEmpty(insertTime)){
			String [] time=QwyUtil.splitTime(insertTime);
			if(time.length>1){
				buffer.append(" AND us.date >= ? ");
				list.add(QwyUtil.fmMMddyyyy.parse(time[0]));
				buffer.append(" AND us.date <= ? ");
				list.add(QwyUtil.fmMMddyyyy.parse(time[1]));
			}else{
				buffer.append(" AND us.date >= ? ");
				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
				buffer.append(" AND us.date <= ? ");
				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59"));
			}
		}
		buffer.append(" ORDER BY us.date DESC ");
		return dao.getByHqlAndHqlCount(pageUtil, buffer.toString(), buffer.toString(), list.toArray());
	}
	/**
	 * 获取渠道成本报表数据
	 * @param insertTime
	 * @param sourceFileName
	 * @return
	 * @throws Exception
	 */
	public List<JasperPrint> getQdcbJasperPrintList(PageUtil pageUtil,String insertTime,String sourceFileName) throws Exception {
		List<JasperPrint> list=new ArrayList<JasperPrint>();
		List<BackStatsOperateDay> qdcbs=findQdcb(pageUtil,insertTime).getList();
		if(!QwyUtil.isNullAndEmpty(qdcbs)){
			Qdcb qdcb=tjQdcb(insertTime);
			Map<String, String> map=QwyUtil.getValueMap(qdcb);
			JRBeanCollectionDataSource ds=new JRBeanCollectionDataSource(qdcbs);	
			JasperPrint  js=JasperFillManager.fillReport(sourceFileName, map, ds);
			list.add(js);
		}
		return list;
	}
	
	
	
	/**
	 * 查询渠道统计的绑定记录
	 * @param insertTime
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> findQdBindjl(String insertTime){
		Map<String, String> map = new HashMap<String, String>();
		try {
			StringBuffer buffer = new StringBuffer();
			List<Object> arrayList = new ArrayList<Object>();
			buffer.append(bindjl(insertTime, arrayList));
			List<Object []> list = dao.LoadAllSql(buffer.toString(), arrayList.toArray());
			if(!QwyUtil.isNullAndEmpty(list)){
				for (Object[] obj : list) {
					if(!QwyUtil.isNullAndEmpty(obj[0])){
						map.put(obj[1]+"bind",obj[0]+"");
					}else{
						map.put(obj[1]+"bind","0");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 查询渠道统计的投资记录
	 * @param insertTime
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> findQdTzjl(String insertTime){
		Map<String, String> map = new HashMap<String, String>();
		try {
			StringBuffer buffer = new StringBuffer();
			List<Object> arrayList = new ArrayList<Object>();
			buffer.append(tzjl(insertTime, arrayList));
			List<Object []> list = dao.LoadAllSql(buffer.toString(), arrayList.toArray());
			if(!QwyUtil.isNullAndEmpty(list)){
				for (Object[] obj : list) {
					if(!QwyUtil.isNullAndEmpty(obj[0])){
						map.put(obj[2]+"tzCount",obj[0]+"");
					}else{
						map.put(obj[2]+"tzCount","0");
					}
					if(!QwyUtil.isNullAndEmpty(obj[1])){
						map.put(obj[2]+"tzCopies",obj[1]+"");
					}else{
						map.put(obj[2]+"tzCopies","0");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	

	/**
	 * 查询渠道统计的首投记录
	 * @param insertTime
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> findQdStjl(String insertTime){
		Map<String, String> map = new HashMap<String, String>();
		try {
			StringBuffer buffer = new StringBuffer();
			List<Object> arrayList = new ArrayList<Object>();
			buffer.append(stjl(insertTime, arrayList));
			List<Object []> list = dao.LoadAllSql(buffer.toString(), arrayList.toArray());
			if(!QwyUtil.isNullAndEmpty(list)){
				for (Object[] obj : list) {
					if(!QwyUtil.isNullAndEmpty(obj[0])){
						map.put(obj[2]+"stCount",obj[0]+"");
					}else{
						map.put(obj[2]+"stCount","0");
					}
					if(!QwyUtil.isNullAndEmpty(obj[1])){
						map.put(obj[2]+"stCopies",obj[1]+"");
					}else{
						map.put(obj[2]+"stCopies","0");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	
	/**
	 * 查询渠道统计的复投记录
	 * @param insertTime
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> findQdFtjl(String insertTime){
		Map<String, String> map = new HashMap<String, String>();
		try {
			StringBuffer buffer = new StringBuffer();
			List<Object> arrayList = new ArrayList<Object>();
			buffer.append(ftjl(insertTime, arrayList));
			List<Object []> list = dao.LoadAllSql(buffer.toString(), arrayList.toArray());
			if(!QwyUtil.isNullAndEmpty(list)){
				for (Object[] obj : list) {
					if(!QwyUtil.isNullAndEmpty(obj[0])){
						map.put(obj[1]+"ftCount",obj[0]+"");
					}else{
						map.put(obj[1]+"ftCount","0");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * 查询渠道统计的充值记录
	 * @param insertTime
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> findQdCzjl(String insertTime){
		Map<String, String> map = new HashMap<String, String>();
		List<Object []> list = null;
		try {
			StringBuffer buffer = new StringBuffer();
			List<Object> arrayList = new ArrayList<Object>();
			buffer.append(czjl(insertTime, arrayList));
			list = dao.LoadAllSql(buffer.toString(), arrayList.toArray());
			if(!QwyUtil.isNullAndEmpty(list)){
				for (Object[] obj : list) {
					if(!QwyUtil.isNullAndEmpty(obj[0])){
						map.put(obj[2]+"czCount",obj[0]+"");
					}else{
						map.put(obj[2]+"czCount","0");
					}
					if(!QwyUtil.isNullAndEmpty(obj[1])){
						map.put(obj[2]+"czMoney",obj[1]+"");
					}else{
						map.put(obj[2]+"czMoney","0");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 查询渠道统计的激活人数
	 * @param insertTime
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> findQdJhrs(String insertTime){

		Map<String, String> map = new HashMap<String, String>();
		List<Object []> list = null;
		try {
			StringBuffer buffer = new StringBuffer();
			List<Object> arrayList = new ArrayList<Object>();
			buffer.append(" SELECT	COUNT(DISTINCT ac.id),ac.channel FROM activity ac  ");
			if(!QwyUtil.isNullAndEmpty(insertTime)){
				String [] time=QwyUtil.splitTime(insertTime);
				if(time.length>1){
					buffer.append(" WHERE ac.insert_time >= ? ");
					arrayList.add(QwyUtil.fmMMddyyyy.parse(time[0]));
					buffer.append(" AND ac.insert_time < ? ");
					arrayList.add(QwyUtil.fmMMddyyyy.parse(time[1]));
				}else{
					buffer.append(" WHERE ac.insert_time >= ? ");
					arrayList.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
					buffer.append(" AND ac.insert_time <= ? ");
					arrayList.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59"));
				}
			}
			buffer.append(" GROUP BY ac.channel ");
			list = dao.LoadAllSql(buffer.toString(), arrayList.toArray());
			if(!QwyUtil.isNullAndEmpty(list)){
				for (Object[] obj : list) {
					map.put(obj[1]+"jhCount",obj[0]+"");
				}
			}
			buffer.append(" GROUP BY ac.channel ");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	
	/**
	 * 查询渠道统计的注册人数
	 * @param insertTime
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> findQdZcrs(String insertTime){
		Map<String, String> map = new HashMap<String, String>();
		List<Object []> list = null;
		try {
			StringBuffer buffer = new StringBuffer();
			List<Object> arrayList = new ArrayList<Object>();
			buffer.append(" SELECT	COUNT(DISTINCT us.id),us.regist_channel	FROM users us WHERE  us.regist_platform = '1' ");
			if(!QwyUtil.isNullAndEmpty(insertTime)){
				String [] time=QwyUtil.splitTime(insertTime);
				if(time.length>1){
					buffer.append(" AND us.insert_time >= ? ");
					arrayList.add(QwyUtil.fmMMddyyyy.parse(time[0]));
					buffer.append(" AND us.insert_time < ? ");
					arrayList.add(QwyUtil.fmMMddyyyy.parse(time[1]));
				}else{
					buffer.append(" AND us.insert_time >= ? ");
					arrayList.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
					buffer.append(" AND us.insert_time <= ? ");
					arrayList.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59"));
				}
			}
			buffer.append(" GROUP BY us.regist_channel ");
			list = dao.LoadAllSql(buffer.toString(), arrayList.toArray());
			if(!QwyUtil.isNullAndEmpty(list)){
				for (Object[] obj : list) {
					map.put(obj[1]+"zcCount",obj[0]+"");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public List<PlatChannel> getPlatChannel(String beginDate,String endDate) {
		List<Object> list=new ArrayList<Object>();
		try {
		StringBuffer buffer = new StringBuffer("FROM PlatChannel rr ");
		buffer.append("WHERE 1=1 ");
		if(!QwyUtil.isNullAndEmpty(beginDate) && !QwyUtil.isNullAndEmpty(endDate)){
			buffer.append(" AND rr.insertTime >= ? ");
			list.add(QwyUtil.fmyyyyMMdd.parse(beginDate));
			buffer.append(" AND rr.insertTime <= ? ");
			list.add(QwyUtil.fmyyyyMMdd.parse(endDate));
		//	buffer.append(" and DATE_FORMAT(AND rr.insertTime,'%Y-%m-%d') BETWEEN DATE_FORMAT('"+beginDate+"','%Y-%m-%d')  AND DATE_FORMAT('"+endDate+"','%Y-%m-%d')");
		}
		else{
			buffer.append(" AND rr.insertTime >= ? ");
			list.add(QwyUtil.fmMMddyyyy.parse(QwyUtil.fmMMddyyyy.format(QwyUtil.addDaysFromOldDate(new Date(),-31).getTime())));
			buffer.append(" AND rr.insertTime < ? ");
			list.add(QwyUtil.fmMMddyyyy.parse(QwyUtil.fmMMddyyyy.format(new Date().getTime())));
		}
		buffer.append("ORDER BY  rr.insertTime ASC");
		return (List<PlatChannel>)dao.LoadAll(buffer.toString(),list.toArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取渠道统计
	 * @param insertTime 
	 * @throws Exception 
	 */
	public List<Qdtj> getQdtj(String insertTime) throws Exception{
		List<Qdtj> list=new ArrayList<Qdtj>();
		Map<String, String> map=new HashMap<String, String>();
		map.putAll(findQdBindjl(insertTime));
		map.putAll(findQdTzjl(insertTime));
		map.putAll(findQdStjl(insertTime));
		map.putAll(findQdFtjl(insertTime));
		map.putAll(findQdCzjl(insertTime));
		map.putAll(findQdJhrs(insertTime));
		map.putAll(findQdZcrs(insertTime));
		
		List<PlatChannel> channels=dao.LoadAll("PlatChannel");
		if(!QwyUtil.isNullAndEmpty(channels)){
			for (int i = 0; i < channels.size(); i++) {
				PlatChannel platChannel=channels.get(i);
				Qdtj qdtj=new Qdtj();
				//序号
				qdtj.setIndex((i+1)+"");
				if(!QwyUtil.isNullAndEmpty(platChannel.getChannelName())){
					qdtj.setDate(platChannel.getDate());
				}else{
					qdtj.setDate("");
				}
				if(!QwyUtil.isNullAndEmpty(platChannel.getChannelName())){
					qdtj.setChannelName(platChannel.getChannelName());
				}else{
					qdtj.setChannelName("");
				}
				

				//激活人数
				String activityCount=map.get(platChannel.getChannel()+"jhCount");
				if(QwyUtil.isNullAndEmpty(activityCount)){
					activityCount="0";
				}
				qdtj.setActivityCount(activityCount);
				//绑定人数
				String bindCount = map.get(platChannel.getChannel()+"bind");
				if(QwyUtil.isNullAndEmpty(bindCount)){
					bindCount="0";
				}
				qdtj.setBindCount(bindCount);
				//渠道
				qdtj.setChannel(platChannel.getChannel()+"");
				//充值人数
				String czcount = map.get(platChannel.getChannel()+"czCount");
				if(QwyUtil.isNullAndEmpty(czcount)){
					czcount="0";
				}
				qdtj.setCzcount(czcount);
				//充值金额
				String czje = map.get(platChannel.getChannel()+"czMoney");
				if(QwyUtil.isNullAndEmpty(czje)){
					czje="0";
				}
				qdtj.setCzje(QwyUtil.calcNumber(czje, "100", "/",2).toString());
				//复投人数
				String ftrs = map.get(platChannel.getChannel()+"ftCount");
				if(QwyUtil.isNullAndEmpty(ftrs)){
					ftrs="0";
				}
				qdtj.setFtrs(ftrs);
				//注册人数
				String regCount = map.get(platChannel.getChannel()+"zcCount");
				if(QwyUtil.isNullAndEmpty(regCount)){
					regCount="0";
				}
				qdtj.setRegCount(regCount);
				//首投金额
				String stje = map.get(platChannel.getChannel()+"stCopies");
				if(QwyUtil.isNullAndEmpty(stje)){
					stje="0";
				}
				qdtj.setStje(QwyUtil.calcNumber(stje, "100", "/",2).toString());
				//首投人数
				String strs = map.get(platChannel.getChannel()+"stCount");
				if(QwyUtil.isNullAndEmpty(strs)){
					strs="0";
				}
				qdtj.setStrs(strs);
				//投资金额
				String tzje = map.get(platChannel.getChannel()+"tzCopies");
				if(QwyUtil.isNullAndEmpty(tzje)){
					tzje="0";
				}
				qdtj.setTzje(QwyUtil.calcNumber(tzje, "100", "/",2).toString());
				//投资人数
				String tzrs = map.get(platChannel.getChannel()+"tzCount");
				if(QwyUtil.isNullAndEmpty(tzrs)){
					tzrs="0";
				}
				qdtj.setTzrs(tzrs);
				
				if(qdtj.getBindCount().equals("0")||qdtj.getActivityCount().equals("0")){
					qdtj.setQdzhl("0.00");
				}else{
					String zhl=QwyUtil.calcNumber(qdtj.getBindCount(), qdtj.getActivityCount(), "/").toString();
					qdtj.setQdzhl(QwyUtil.calcNumber(zhl, 0.01, "/", 2)+"");
				}
				if(qdtj.getFtrs().equals("0")||qdtj.getStrs().equals("0")){
					qdtj.setCftzl("0.00");
				}else{
					String zhl=QwyUtil.calcNumber(qdtj.getFtrs(), qdtj.getStrs(), "/").toString();
					qdtj.setCftzl(QwyUtil.calcNumber(zhl,0.01, "/", 2)+"");
				}
				list.add(qdtj);
			}
		}
		return list;
	}
	/**
	 * 注册人数
	 * @param insertTime 注册时间
	 * @throws Exception 
	 */
	public String regUserCount(String insertTime) throws Exception{
		ArrayList<Object> list=new ArrayList<Object>();
		StringBuffer buffer=new StringBuffer();
//		buffer.append("  SELECT  COUNT(DISTINCT us.id) FROM  users us ");
//		buffer.append("  WHERE us.regist_platform = '1' ");
//		buffer.append("  AND us.regist_channel != '' ");
//		buffer.append("  AND us.regist_channel IS NOT NULL ");
//		if(!QwyUtil.isNullAndEmpty(insertTime)){
//			String [] time=QwyUtil.splitTime(insertTime);
//			if(time.length>1){
//				buffer.append(" AND us.insert_time>= ? ");
//				list.add(QwyUtil.fmMMddyyyy.parse(time[0]));
//				buffer.append(" AND us.insert_time<= ? ");
//				list.add(QwyUtil.fmMMddyyyy.parse(time[1]));
//			}else{
//				buffer.append(" AND us.insert_time>= ? ");
//				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
//				buffer.append(" AND us.insert_time<= ? ");
//				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59"));
//			}
//		}
		buffer.append(regUserCountSQL(insertTime, list));
		Object object=dao.getSqlCount(buffer.toString(),list.toArray());
		if(!QwyUtil.isNullAndEmpty(object)){
			return object+"";
		}
		return "0";
	}
	
	/**
	 * 投资人数
	 * @param insertTime 投资时间
	 * @throws Exception 
	 */
	public String insUsersCount(String insertTime) throws Exception{
		ArrayList<Object> list=new ArrayList<Object>();
		StringBuffer buffer=new StringBuffer();
//		buffer.append(" SELECT COUNT(DISTINCT users_id) FROM investors ivs  ");
//		buffer.append(" AND EXISTS ( ");
//		buffer.append(" SELECT id FROM  users  us ");
//		buffer.append("  WHERE us.regist_platform = '1' ");
//		buffer.append("  AND us.regist_channel != '' ");
//		buffer.append("  AND us.regist_channel IS NOT NULL ");
//		buffer.append("  AND us.id=ivs.users_id ");
//		buffer.append("  ) ");
//		if(!QwyUtil.isNullAndEmpty(insertTime)){
//			String [] time=QwyUtil.splitTime(insertTime);
//			if(time.length>1){
//				buffer.append(" AND ivs.pay_time>= ? ");
//				list.add(QwyUtil.fmMMddyyyy.parse(time[0]));
//				buffer.append(" AND ivs.pay_time<= ? ");
//				list.add(QwyUtil.fmMMddyyyy.parse(time[1]));
//			}else{
//				buffer.append(" AND ivs.pay_time>= ? ");
//				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
//				buffer.append(" AND ivs.pay_time<= ? ");
//				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59"));
//			}
//		}
		buffer.append(insUsersCountSQL(insertTime, list));
		Object object=dao.getSqlCount(buffer.toString(),list.toArray());
		if(!QwyUtil.isNullAndEmpty(object)){
			return object+"";
		}
		return "0";
	}
	
	public String insUsersCountSQL(String insertTime,ArrayList<Object> list) throws Exception{
		StringBuffer buffer=new StringBuffer();
		buffer.append(" SELECT COUNT(DISTINCT users_id) FROM investors ivs  ");
		buffer.append(" WHERE ivs.investor_status in ('1','2','3') ");
		buffer.append(" AND EXISTS ( ");
		buffer.append(" SELECT id FROM  users  us ");
		buffer.append("  WHERE us.regist_platform = '1' ");
		buffer.append("  AND us.regist_channel != '' ");
		buffer.append("  AND us.regist_channel IS NOT NULL ");
		buffer.append("  AND us.id=ivs.users_id ");
		buffer.append("  ) ");
		if(!QwyUtil.isNullAndEmpty(insertTime)){
			String [] time=QwyUtil.splitTime(insertTime);
			if(time.length>1){
				buffer.append(" AND DATE_FORMAT(ivs.pay_time, '%Y-%m-%d' )>= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[0])));
				buffer.append(" AND DATE_FORMAT(ivs.pay_time, '%Y-%m-%d' )<= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyyHHmmss.parse(time[1]+" 23:59:59")));
			}else{
				buffer.append(" AND DATE_FORMAT(ivs.pay_time, '%Y-%m-%d' )>= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00")));
				buffer.append(" AND DATE_FORMAT(ivs.pay_time, '%Y-%m-%d' )<= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59")));
			}
		}
		return buffer.toString();
	}
	
	/**
	 * 合计
	 */
	public Qdtj tjQdtj(List<Qdtj> qdtjs) throws Exception{
		Qdtj cr=new Qdtj();
		
		//激活次数
		if(QwyUtil.isNullAndEmpty(cr.getActivityCount()))
			cr.setActivityCount("0");
		//注册人数
		if(QwyUtil.isNullAndEmpty(cr.getRegCount()))
			cr.setRegCount("0");
		//绑定人数
		if(QwyUtil.isNullAndEmpty(cr.getBindCount()))
			cr.setBindCount("0");
			//充值金额
		if(QwyUtil.isNullAndEmpty(cr.getTzrs()))
			cr.setTzrs("0");
		//首投人数
		if(QwyUtil.isNullAndEmpty(cr.getStrs()))
			cr.setStrs("0");
		//首投金额
		if(QwyUtil.isNullAndEmpty(cr.getStje()))
			cr.setStje("0");
		//复投人数
		if(QwyUtil.isNullAndEmpty(cr.getFtrs()))
			cr.setFtrs("0");
		
		
		//投资金额(元)
		if(QwyUtil.isNullAndEmpty(cr.getTzje()))
			cr.setTzje("0");
		
		//重复投资率(%)
		if(QwyUtil.isNullAndEmpty(cr.getCftzl()))
			cr.setCftzl("0");
		
		//充值次数
		if(QwyUtil.isNullAndEmpty(cr.getCzcount()))
			cr.setCzcount("0");
		//充值金额(元)
		if(QwyUtil.isNullAndEmpty(cr.getCzje()))
			cr.setCzje("0");
		
		if(!QwyUtil.isNullAndEmpty(qdtjs)){
			for (Qdtj qdtj : qdtjs) {
				if(!QwyUtil.isNullAndEmpty(qdtj))
					//激活用户
					cr.setActivityCount(QwyUtil.calcNumber(qdtj.getActivityCount(), cr.getActivityCount(),"+")+"");
				   //注册人数
					cr.setRegCount(QwyUtil.calcNumber(qdtj.getRegCount(), cr.getRegCount(), "+")+"");
					//绑定人数
					cr.setBindCount(QwyUtil.calcNumber(qdtj.getBindCount(), cr.getBindCount(), "+")+"");
					//投资人数
					cr.setTzrs(QwyUtil.calcNumber(qdtj.getTzrs(), cr.getTzrs(), "+")+"");
					//首投人数
					cr.setStrs(QwyUtil.calcNumber(qdtj.getStrs(), cr.getStrs(), "+")+"");
					//首投金额
					cr.setStje(QwyUtil.calcNumber(qdtj.getStje(), cr.getStje(), "+")+"");
					//复投人数
					cr.setFtrs(QwyUtil.calcNumber(qdtj.getFtrs(), cr.getFtrs(), "+")+"");
					//投资金额
					cr.setTzje(QwyUtil.calcNumber(qdtj.getTzje(), cr.getTzje(), "+")+"");
					//充值次数
					cr.setCzcount(QwyUtil.calcNumber(qdtj.getCzcount(), cr.getCzcount(), "+")+"");
					//充值金额
					cr.setCzje(QwyUtil.calcNumber(qdtj.getCzje(), cr.getCzje(), "+")+"");
			}
			
			//渠道转化率(%)
		}
		if(cr.getBindCount().equals("0")||cr.getActivityCount().equals("0")){
			cr.setQdzhl("0.00");
		}else{
			String zhl=QwyUtil.calcNumber(cr.getBindCount(), cr.getActivityCount(), "/").toString();
			cr.setQdzhl(QwyUtil.calcNumber(zhl, 0.01, "/", 2)+"");
		}
		
		//重复投资率
		if(cr.getFtrs().equals("0")||cr.getStrs().equals("0")){
			cr.setCftzl("0.00");
		}else{
			String zhl=QwyUtil.calcNumber(cr.getFtrs(), cr.getStrs(), "/").toString();
			cr.setCftzl(QwyUtil.calcNumber(zhl, 0.01, "/", 2)+"");
		}
		return cr;
	}
	
	/**
	 * 合计
	 * @param qdtjs
	 * @param insertTime  时间
	 * @return
	 * @throws Exception
	 */
//	public Qdtj tjQdtj(String insertTime) throws Exception{
//		Qdtj or=new Qdtj();
//			//激活用户
//			or.setActivityCount(bean.activityCount(insertTime));
//			//注册人数
//			or.setRegCount(regUserCount(insertTime));
//			//绑定人数
//			or.setBindCount(bindUserCount(insertTime));
//			//投资人数
//			or.setTzrs(insUsersCount(insertTime));
//			//首投人数
//			or.setStrs(firstInsUsersCount(insertTime));
//			//首投金额
//			or.setStje(QwyUtil.calcNumber(firstInsMoney(insertTime), 100, "/", 2)+"");
//			//复投人数
//			or.setFtrs(againInsUsersCount(insertTime));
//			//投资金额
//			or.setTzje(QwyUtil.calcNumber(insMoney(insertTime), 100, "/", 2)+"");
//			//充值次数
//			or.setCzcount(czCount(insertTime));
//			//充值金额
//			or.setCzje(QwyUtil.calcNumber(czMoney(insertTime), 100, "/", 2)+"");
//			//渠道转换率
//			if(or.getBindCount().equals("0")||or.getActivityCount().equals("0")){
//				or.setQdzhl("0.00");
//			}else{
//				String zhl=QwyUtil.calcNumber(or.getBindCount(), or.getActivityCount(), "/").toString();
//				or.setQdzhl(QwyUtil.calcNumber(zhl, 0.01, "/", 2)+"");
//			}
//			//重复投资率
//			if(or.getFtrs().equals("0")||or.getStrs().equals("0")){
//				or.setCftzl("0.00");
//			}else{
//				String zhl=QwyUtil.calcNumber(or.getFtrs(), or.getStrs(), "/").toString();
//				or.setCftzl(QwyUtil.calcNumber(zhl, 0.01, "/", 2)+"");
//			}
//		return or;
//	}
	
	/**
	 * 首投人数
	 * @param insertTime 投资时间
	 * @throws Exception 
	 */
	public String firstInsUsersCount(String insertTime) throws Exception{
		ArrayList<Object> list=new ArrayList<Object>();
		StringBuffer buffer=new StringBuffer();
//		buffer.append(" SELECT COUNT(DISTINCT ivs.users_id) FROM investors ivs  ");
//		buffer.append(" WHERE ivs.investor_status IN ('1','2','3') ");
//		buffer.append(" AND ivs.pay_time in ( ");
//		buffer.append("  SELECT MIN(ins.pay_time) as date FROM investors ins  WHERE ins.investor_status in ('1','2','3') GROUP BY users_id ) ");
//		buffer.append(" AND EXISTS ( ");
//		buffer.append(" SELECT id FROM  users  us ");
//		buffer.append("  WHERE us.regist_platform = '1' ");
//		buffer.append("  AND us.regist_channel != '' ");
//		buffer.append("  AND us.regist_channel IS NOT NULL ");
//		buffer.append("  AND us.id=ivs.users_id ");
//		buffer.append("  ) ");
//		if(!QwyUtil.isNullAndEmpty(insertTime)){
//			String [] time=QwyUtil.splitTime(insertTime);
//			if(time.length>1){
//				buffer.append(" AND ivs.pay_time>= ? ");
//				list.add(QwyUtil.fmMMddyyyy.parse(time[0]));
//				buffer.append(" AND ivs.pay_time<= ? ");
//				list.add(QwyUtil.fmMMddyyyy.parse(time[1]));
//			}else{
//				buffer.append(" AND ivs.pay_time>= ? ");
//				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
//				buffer.append(" AND ivs.pay_time<= ? ");
//				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59"));
//			}
//		}
		//buffer.append(firstInsUsersCountSQL(insertTime, list));
		buffer.append(" SELECT  sum(t.strs) FROM ( ");
		buffer.append(" SELECT DATE_FORMAT( dd.insert_time, '%Y-%m-%d' ) as date,  ");
		buffer.append("  q.strs , ");
		buffer.append("  q.stje");
		buffer.append(" FROM dateday dd  ");
		//首投人数，首投金额
		buffer.append(" LEFT JOIN (  ");
		buffer.append(" SELECT DATE_FORMAT(shoutouDate, '%Y-%m-%d') as date,COUNT(*) AS strs,SUM(in_money) as stje FROM ( ");
		buffer.append(" SELECT DATE_FORMAT(investors1.pay_time,'%Y-%m-%d') AS shoutouDate,investors1.users_id ,investors1.in_money FROM investors investors1 ");
		buffer.append(" INNER JOIN ( ");
		buffer.append(" SELECT MIN(t.pay_time) as minDate,users_id FROM investors t WHERE t.investor_status IN ('1','2','3') GROUP BY t.users_id ");
		buffer.append(" ) investors2 ON investors1.pay_time=investors2.minDate AND investors1.users_id = investors2.users_id  ");
		buffer.append(" INNER JOIN users u ON u.id=investors1.users_id   ");
		buffer.append(" AND u.regist_platform = '1'  AND u.regist_channel != ''  AND u.regist_channel IS NOT NULL ");
		buffer.append(" WHERE DATE_FORMAT(investors1.pay_time,'%Y-%m-%d')=DATE_FORMAT(investors2.minDate,'%Y-%m-%d') GROUP BY investors1.users_id ");
		buffer.append(" ) tab4 GROUP BY shoutouDate ");
		buffer.append(" ) q ON q.date = DATE_FORMAT(dd.insert_time, '%Y-%m-%d')  ");
		if(!QwyUtil.isNullAndEmpty(insertTime)){
			String [] time=QwyUtil.splitTime(insertTime);
			if(time.length>1){
				buffer.append(" WHERE dd.insert_time>= ? ");
				list.add(QwyUtil.fmMMddyyyy.parse(time[0]));
				buffer.append(" AND dd.insert_time< ? ");
				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[1]+" 23:59:59"));
			}else{
				buffer.append(" WHERE dd.insert_time>= ? ");
				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
				buffer.append(" AND dd.insert_time<= ? ");
				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59"));
			}
		}
		buffer.append("  GROUP BY DATE_FORMAT(dd.insert_time, '%Y-%m-%d' )");
		buffer.append("  ORDER BY DATE_FORMAT(dd.insert_time, '%Y-%m-%d' ) DESC");
		buffer.append(" )t ");
		Object object=dao.getSqlCount(buffer.toString(),list.toArray());
		if(!QwyUtil.isNullAndEmpty(object)){
			return object+"";
		}
		return "0";
	}

	/**
	 * 复投人数
	 * @param insertTime 投资时间
	 * @throws Exception 
	 */
	public String againInsUsersCount(String insertTime) throws Exception{
		ArrayList<Object> list=new ArrayList<Object>();
		StringBuffer buffer=new StringBuffer();
		buffer.append("  SELECT COUNT(DISTINCT ivs.users_id) FROM investors ivs ");
		buffer.append(" WHERE ivs.investor_status  in ('1','2','3') ");
//		buffer.append(" AND ivs.pay_time not in ( ");
//		buffer.append(" SELECT MIN(ins.pay_time) as date FROM investors ins  WHERE ins.investor_status in ('1','2','3') GROUP BY users_id ) ");
		buffer.append(" AND  NOT EXISTS ( SELECT t.id FROM  ( SELECT MIN(ins.pay_time) as date,id FROM investors ins  WHERE ins.investor_status in ('1','2','3') GROUP BY users_id )t WHERE t.id=ivs.id) ");
		if(!QwyUtil.isNullAndEmpty(insertTime)){
			String [] time=QwyUtil.splitTime(insertTime);
			if(time.length>1){
				buffer.append(" AND DATE_FORMAT(ivs.pay_time, '%Y-%m-%d' )>= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[0])));
				buffer.append(" AND DATE_FORMAT(ivs.pay_time, '%Y-%m-%d' )< ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[1])));
			}else{
				buffer.append(" AND DATE_FORMAT(ivs.pay_time, '%Y-%m-%d' )>= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00")));
				buffer.append(" AND DATE_FORMAT(ivs.pay_time, '%Y-%m-%d' )<= ? ");
				list.add(QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59")));
			}
		}
		buffer.append(" AND EXISTS ( ");
		buffer.append(" SELECT id FROM  users  us ");
		buffer.append("  WHERE us.regist_platform = '1' ");
		buffer.append("  AND us.regist_channel != '' ");
		buffer.append("  AND us.regist_channel IS NOT NULL ");
		buffer.append("  AND us.id=ivs.users_id ");
		buffer.append("  ) ");
		
		Object object=dao.getSqlCount(buffer.toString(),list.toArray());
		if(!QwyUtil.isNullAndEmpty(object)){
			return object+"";
		}
		return "0";
	}
	
	@SuppressWarnings("unchecked")
	public PageUtil<ChannelOperateDay> getQdtjDetail(PageUtil<ChannelOperateDay> pageUtil, String registChannel, String insertTime) throws Exception {
		List<Object> list = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer();
		hql.append(" FROM ChannelOperateDay c WHERE 1 = 1 ");

		if (!QwyUtil.isNullAndEmpty(registChannel)) {
			hql.append(" AND c.registChannel = ?");
			list.add(Integer.valueOf(registChannel));
		}

		// 插入时间
		if (!QwyUtil.isNullAndEmpty(insertTime)) {
			String[] time = QwyUtil.splitTime(insertTime);
			if (time.length > 1) {
				hql.append(" AND c.date >= ? ");
				list.add(QwyUtil.fmMMddyyyy.parse(time[0] + " 00:00:00"));
				hql.append(" AND c.date <= ? ");
				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[1] + " 23:59:59"));
			} else {
				hql.append(" AND c.date >= ? ");
				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 00:00:00"));
				hql.append(" AND c.date <= ? ");
				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 23:59:59"));
			}
		}

		hql.append(" ORDER BY c.date DESC ");
		return dao.getByHqlAndHqlCount(pageUtil, hql.toString(), hql.toString(), list.toArray());
	}
	/**
	 * 	查询渠道
	 * @param channel
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public PlatChannel loadPlatChannel(String channel) throws Exception{
		PlatChannel platChannel=null;
		StringBuffer buff=new StringBuffer();
		buff.append(" FROM PlatChannel where 1=1 ");
		if(!QwyUtil.isNullAndEmpty(channel)&&QwyUtil.isOnlyNumber(channel)){
			buff.append(" AND channel=?");	
			Object[] ob = new Object[]{Integer.valueOf(channel)};
			List<PlatChannel> list=dao.LoadAll(buff.toString(),ob);
			if(!QwyUtil.isNullAndEmpty(list)){
				if(list.size()>0){
					platChannel=list.get(0);
				}			
			}
		}

		return platChannel;
	}
	
}
