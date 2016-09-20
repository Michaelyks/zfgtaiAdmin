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
import com.zfgt.admin.product.dao.IphoneActivityDAO;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.ActivityStat;
import com.zfgt.orm.BackStatsOperateDay;
import com.zfgt.orm.ChannelOperateDay;
import com.zfgt.orm.IphoneOperateDay;
import com.zfgt.orm.PlatChannel;
import com.zfgt.orm.Qdcb;
import com.zfgt.orm.Qdtj;

/**IOS渠道统计Bean
 * @author 覃文勇
 * @createTime 2016-3-18上午10:39:38
 */
@Service
public class IphoneActivityBean{
	@Resource
	IphoneActivityDAO dao;
	@Resource
	UsersConvertBean bean;
				
	/**
	 * 获取渠道统计(IOS)
	 * @param insertTime 
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public List<Qdtj> getIphoneQdtj(String registChannel) throws Exception{		
		List<Object[]> list=new ArrayList<Object[]>();
		List<Qdtj> qdtjlist=new ArrayList<Qdtj>();
		StringBuffer buffer=new StringBuffer();
		List<Object> arrayList = new ArrayList<Object>();
		buffer.append(" SELECT registChannel, SUM(activateUserSum), SUM(regUserSum), SUM(bindBankUserSum),SUM(regActivityRate),SUM(investUserSum),SUM(firstInvestUserSum),SUM(firstInvestCentSum), SUM(reInvestUserSum),SUM(investCentSum), SUM(reInvestRate), SUM(rechargeCount), SUM(rechargeCentSum) FROM iphone_operate_day WHERE registChannel IS NOT NULL ");
		if( registChannel!=null){
			buffer.append(" AND registChannel=? ");
			arrayList.add(registChannel);
		}
		buffer.append(" GROUP BY registChannel ");
		
		list=dao.LoadAllSql(buffer.toString(),arrayList.toArray());
		if(!QwyUtil.isNullAndEmpty(list)&&list.size()>0){
			for(int i = 0; i < list.size(); i++){
				Object[] objects = list.get(i);
				Qdtj qdtj=new Qdtj();
				if(!QwyUtil.isNullAndEmpty(objects[0]+"")){
					qdtj.setChannel(objects[0]+"");
				}
				qdtj.setActivityCount(objects[1]+"");//激活人数
				qdtj.setRegCount(objects[2]+"");//注册人数
				qdtj.setBindCount(objects[3]+"");//绑定人数
				qdtj.setQdzhl(objects[4]+"");//渠道转换率
				qdtj.setTzrs(objects[5]+"");//投资人数
				qdtj.setStrs(objects[6]+"");//首投人数
				qdtj.setStje(objects[7]+"");//首投金额
				qdtj.setFtrs(objects[8]+"");//复投人数
				qdtj.setTzje(objects[9]+"");//投资金额
				qdtj.setCftzl(objects[10]+"");//重复投资率
				qdtj.setCzcount(objects[11]+"");//充值次数
				qdtj.setCzje(objects[12]+"");//充值金额
				qdtjlist.add(qdtj);
			}			
		}
		return qdtjlist;
	}
	
	/**
	 * 获取渠道统计总计(IOS)
	 * @param insertTime 
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public List<Qdtj> getTotal() throws Exception{
		List<Qdtj> qdtjlist=new ArrayList<Qdtj>();
		StringBuffer buffer=new StringBuffer();
		buffer.append(" SELECT SUM(activateUserSum), SUM(regUserSum), SUM(bindBankUserSum),SUM(regActivityRate),SUM(investUserSum),SUM(firstInvestUserSum),SUM(firstInvestCentSum), SUM(reInvestUserSum),SUM(investCentSum), SUM(reInvestRate), SUM(rechargeCount), SUM(rechargeCentSum)  ");
		buffer.append("  FROM iphone_operate_day WHERE 1=1 ");
		List<Object[]> list=dao.LoadAllSql(buffer.toString(),null);
		if(!QwyUtil.isNullAndEmpty(list)&&list.size()>0){
			for(int i = 0; i < list.size(); i++){
				Object[] objects = list.get(i);
				Qdtj qdtj=new Qdtj();
				qdtj.setActivityCount(objects[0]+"");//激活人数
				qdtj.setRegCount(objects[1]+"");//注册人数
				qdtj.setBindCount(objects[2]+"");//绑定人数
				qdtj.setQdzhl(objects[3]+"");//渠道转换率
				qdtj.setTzrs(objects[4]+"");//投资人数
				qdtj.setStrs(objects[5]+"");//首投人数
				qdtj.setStje(objects[6]+"");//首投金额
				qdtj.setFtrs(objects[7]+"");//复投人数
				qdtj.setTzje(objects[8]+"");//投资金额
				qdtj.setCftzl(objects[9]+"");//重复投资率
				qdtj.setCzcount(objects[10]+"");//充值次数
				qdtj.setCzje(objects[11]+"");//充值金额
				qdtjlist.add(qdtj);
			}			
		}
		return qdtjlist;
	}
	/**
	 * 分页查询单个渠道的数据统计（IOS）
	 * @param pageUtil
	 * @param registChannel
	 * @param insertTime
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public PageUtil<IphoneOperateDay> getIphoneQdtjDetail(PageUtil<IphoneOperateDay> pageUtil, String registChannel, String insertTime) throws Exception {
		List<Object> list = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer();
		hql.append(" FROM IphoneOperateDay i WHERE 1 = 1 ");

		if (!QwyUtil.isNullAndEmpty(registChannel)) {
			hql.append(" AND i.registChannel = ? ");
			list.add(registChannel);
		}

		// 插入时间
		if (!QwyUtil.isNullAndEmpty(insertTime)) {
			String[] time = QwyUtil.splitTime(insertTime);
			if (time.length > 1) {
				hql.append(" AND i.date >= ? ");
				list.add(QwyUtil.fmMMddyyyy.parse(time[0] + " 00:00:00"));
				hql.append(" AND i.date <= ? ");
				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[1] + " 23:59:59"));
			} else {
				hql.append(" AND i.date >= ? ");
				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 00:00:00"));
				hql.append(" AND i.date <= ? ");
				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 23:59:59"));
			}
		}

		hql.append(" ORDER BY i.date DESC ");
		return dao.getPage(pageUtil, hql.toString(),list.toArray());
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
		List<IphoneOperateDay> details=new ArrayList<IphoneOperateDay>();
		List<IphoneOperateDay> IphoneOperateDays=getIphoneQdtjDetails(registChannel,insertTime);
		if(!QwyUtil.isNullAndEmpty(IphoneOperateDays)){
			for(Object obj:IphoneOperateDays){
				IphoneOperateDay iphoneOperateDay=(IphoneOperateDay)obj;
				iphoneOperateDay.setRegActivityRate(iphoneOperateDay.getRegActivityRate()*100);
				iphoneOperateDay.setReInvestRate(iphoneOperateDay.getReInvestRate()*100);
				iphoneOperateDay.setFirstInvestCentSum(iphoneOperateDay.getFirstInvestCentSum()*0.01);
				iphoneOperateDay.setInvestCentSum(iphoneOperateDay.getInvestCentSum()*0.01);
				iphoneOperateDay.setRechargeCentSum(iphoneOperateDay.getRechargeCentSum()*0.01);
				details.add(iphoneOperateDay);
			}
			Map<String, String> map=QwyUtil.getValueMap(details);
			JRBeanCollectionDataSource ds=new JRBeanCollectionDataSource(details);	
			JasperPrint 	 js=JasperFillManager.fillReport(sourceFileName, map, ds);
			list.add(js);
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<IphoneOperateDay> getIphoneQdtjDetails(String registChannel, String insertTime) throws Exception {
		List<Object> list = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer();
		hql.append(" FROM IphoneOperateDay i WHERE 1 = 1 ");

		if (!QwyUtil.isNullAndEmpty(registChannel)) {
			hql.append(" AND i.registChannel = ? ");
			list.add(registChannel);
		}

		// 插入时间
		if (!QwyUtil.isNullAndEmpty(insertTime)) {
			String[] time = QwyUtil.splitTime(insertTime);
			if (time.length > 1) {
				hql.append(" AND i.date >= ? ");
				list.add(QwyUtil.fmMMddyyyy.parse(time[0] + " 00:00:00"));
				hql.append(" AND i.date <= ? ");
				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[1] + " 23:59:59"));
			} else {
				hql.append(" AND i.date >= ? ");
				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 00:00:00"));
				hql.append(" AND i.date <= ? ");
				list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 23:59:59"));
			}
		}

		hql.append(" ORDER BY i.date DESC ");
		List<IphoneOperateDay> details = dao.LoadAll(hql.toString(), list.toArray());
		return details;
	}
}

