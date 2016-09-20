package com.zfgt.account.bean;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;











import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.common.dao.ObjectDAO;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.ActivityStat;
import com.zfgt.orm.Investors;
import com.zfgt.orm.PlatformInvestDetail;
import com.zfgt.orm.Users;


@Service
public class InvestorsRecordBean {
	@Resource(name="objectDAO")
	private ObjectDAO dao;
	private static Logger log = Logger.getLogger(InvestorsRecordBean.class); 
	/**加载投资记录,根据分页 用户;
	 * @param pageUtil 分页对象;
	 * @return
	 */

	@SuppressWarnings("unchecked")
	public PageUtil<Investors> getInvestorsByPageUtil(
			PageUtil<Investors> pageUtil, String[] status,long uid) {
		try {
			String st = "";
			if(QwyUtil.isNullAndEmpty(status)){
				st = "'1','2','3'";
			}else{
				st = QwyUtil.packString(status);
			}
			StringBuffer buff = new StringBuffer();
			buff.append("FROM Investors ivs ");
			buff.append("WHERE ivs.investorStatus IN ("+st+") ");
			buff.append("AND ivs.usersId = "+uid );
			buff.append(" ORDER BY  ivs.investorStatus ASC, ivs.payTime DESC,ivs.id ASC");
			return (PageUtil<Investors>)dao.getPage(pageUtil, buff.toString(), null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
		return null;
	}
	
	/**
	 * 投资情况
	 * @param payTime 支付时间
	 * @param status 投资状态
	 * @throws Exception 
	 */
	public List<PlatformInvestDetail> findPlatformInvestDetail(String payTime, String status) throws Exception{
		List<Object> arrayList=new ArrayList<Object>();
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT us.regist_platform,SUM(ins.copies)  FROM USERS as us ");
		sql.append(" LEFT JOIN investors as ins ON us.id =  ins.users_id ");
		sql.append(" WHERE us.regist_platform is not NULL ");
		if(!QwyUtil.isNullAndEmpty(payTime)){
			String [] time=QwyUtil.splitTime(payTime);
			if(time.length>1){
				sql.append(" AND ins.pay_time >= ? ");
				arrayList.add(QwyUtil.fmMMddyyyy.parse(time[0]));
				sql.append(" AND ins.pay_time <= ? ");
				arrayList.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[1]+" 23:59:59"));
			}else{
				sql.append(" AND ins.pay_time >= ? ");
				arrayList.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
				sql.append(" AND ins.pay_time <= ? ");
				arrayList.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59"));
			}
		}
		if(!QwyUtil.isNullAndEmpty(status)){
			sql.append(" AND ins.investor_status = ?  ");
			arrayList.add(status);
		}else{
			sql.append(" AND ins.investor_status in ('1','3') ");
		}
		sql.append(" GROUP BY us.regist_platform ");
		List<Object []> list=dao.LoadAllSql(sql.toString(), arrayList.toArray());
		return parsePlatformInvestDetail(list);
	}
	
	
	/**
	 * 投资情况
	 * @param payTime 支付时间
	 * @param status 投资状态
	 * @throws Exception 
	 */
	public String findAllInvestDetail(String payTime, String status) throws Exception{
		List<Object> arrayList=new ArrayList<Object>();
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT SUM(ins.copies)  FROM USERS as us ");
		sql.append(" LEFT JOIN investors as ins ON us.id =  ins.users_id ");
		sql.append(" WHERE us.regist_platform is not NULL ");
		if(!QwyUtil.isNullAndEmpty(payTime)){
			String [] time=QwyUtil.splitTime(payTime);
			if(time.length>1){
				sql.append(" AND ins.pay_time >= ? ");
				arrayList.add(QwyUtil.fmMMddyyyy.parse(time[0]));
				sql.append(" AND ins.pay_time <= ? ");
				arrayList.add(QwyUtil.fmMMddyyyy.parse(time[1]));
			}else{
				sql.append(" AND ins.pay_time >= ? ");
				arrayList.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
				sql.append(" AND ins.pay_time <= ? ");
				arrayList.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59"));
			}
		}
		if(!QwyUtil.isNullAndEmpty(status)){
			sql.append(" AND ins.investor_status = ?  ");
			arrayList.add(status);
		}else{
			sql.append(" AND ins.investor_status in ('1','3') ");
		}
		Object object=dao.getSqlCount(sql.toString(),arrayList.toArray());
		if(!QwyUtil.isNullAndEmpty(object)){
			return object+"";
		}
		return "0";
	}
	
	/**
	 * 转换为平台投资情况
	 * @param list
	 * @return
	 */
	private List<PlatformInvestDetail> parsePlatformInvestDetail(List<Object []> list){
		List<PlatformInvestDetail> platformInvestDetails=new ArrayList<PlatformInvestDetail>();
		if(!QwyUtil.isNullAndEmpty(list)){
			for (Object [] object : list) {
				PlatformInvestDetail platformInvestDetail=new PlatformInvestDetail();
				platformInvestDetail.setRegistPlatform(object[0]+"");
				platformInvestDetail.setPlatformInvest(object[1]+"");
				platformInvestDetails.add(platformInvestDetail);
			}
		}
		return platformInvestDetails;
	}
}
