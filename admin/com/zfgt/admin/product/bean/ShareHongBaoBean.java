package com.zfgt.admin.product.bean;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.admin.product.dao.ShareHongBaoDao;
import com.zfgt.common.dao.ObjectDAO;
import com.zfgt.common.util.DESEncrypt;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.PlatformInvestDetail;
@Service
public class ShareHongBaoBean {
	@Resource(name="objectDAO")
	private ObjectDAO dao;
	private static Logger log = Logger.getLogger(ShareHongBaoBean.class); 
	/**
	 * 加载红包列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageUtil<ShareHongBaoDao> loadHongBao(String username,String insertTime,PageUtil pageUtil) {
		try {
		ArrayList<Object> ob = new ArrayList<Object>();
		StringBuffer buff = new StringBuffer();
		buff.append(" SELECT us.username as username,	IFNULL(s.in_money,0) AS in_money,	IFNULL(s.invite_in_money,0) AS invite_in_money,");
		buff.append(" s.insert_time AS insert_time,	IFNULL(t.in_moneys,0) AS in_moneys,	IFNULL(t.invite_in_moneys,0) AS invite_in_moneys");
		buff.append(" FROM send_red_packets s JOIN users us on us.id = s.users_id LEFT JOIN (SELECT a.users_id AS users_id,");
		buff.append(" b.in_moneys AS in_moneys,	a.invite_in_money AS invite_in_moneys	FROM");
		buff.append(" red_packets_investors a,(SELECT	users_id,	MIN(pay_time) AS pay_time,");
		buff.append(" in_money AS in_moneys	FROM investors	WHERE	investor_status IN (1, 2, 3)");
		buff.append(" GROUP BY users_id	) b	WHERE a.users_id = b.users_id) t ON t.users_id = s.users_id");
		buff.append(" where 1=1 ");
		if(!QwyUtil.isNullAndEmpty(username)){
			buff.append("AND us.username = ? ");
			ob.add(DESEncrypt.jiaMiUsername(username));
		}
		if(!QwyUtil.isNullAndEmpty(insertTime)){
			String [] time=QwyUtil.splitTime(insertTime);
			if(time.length > 1)
			{
				buff.append(" AND s.insert_time >= ? ");
				ob.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
				buff.append(" AND s.insert_time <= ? ");
				ob.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[1]+" 23:59:59"));
			}
			else{

				buff.append(" AND s.insert_time >= ? ");
				ob.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
				buff.append(" AND s.insert_time <= ? ");
				ob.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59"));
			}
		
		}
		buff.append(" ORDER BY DATE_FORMAT(s.insert_time, '%Y-%m-%d') DESC ");
		
		StringBuffer bufferCount=new StringBuffer();
		bufferCount.append(" SELECT COUNT(*)  ");
		bufferCount.append(" FROM (");
		bufferCount.append(buff);
		bufferCount.append(") t");
		 pageUtil=dao.getBySqlAndSqlCount(pageUtil, buff.toString(), bufferCount.toString(), ob.toArray());
		
		 List<ShareHongBaoDao> platUsers=toDateMoney(pageUtil.getList());
			pageUtil.setList(platUsers);
			return pageUtil;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		log.error("操作异常: ",e);
	}
		return null;
		}
	/**
	 * 加载红包总和
	 * @return
	 */
	public  ShareHongBaoDao totalAllOperate(String username,String insertTime){
		ShareHongBaoDao red = null;
		try {
		StringBuffer hql= new StringBuffer();
	
		List<Object> ob = new ArrayList<Object>();
		hql.append(" SELECT	IFNULL(sum(s.in_money), 0) AS in_money,");
		hql.append(" IFNULL(sum(s.invite_in_money), 0) AS invite_in_money,");
		hql.append(" IFNULL(sum(t.in_moneys), 0) AS in_moneys,");
		hql.append(" IFNULL(sum(t.invite_in_moneys), 0) AS invite_in_moneys , COUNT(DISTINCT s.users_id) as numbers ");
		hql.append(" FROM	send_red_packets s JOIN users us on us.id = s.users_id  LEFT JOIN (");
		hql.append(" SELECT a.users_id AS users_id, b.in_moneys AS in_moneys,");
		hql.append(" a.invite_in_money AS invite_in_moneys");
		hql.append(" FROM red_packets_investors a,");
		hql.append(" (SELECT	users_id,	MIN(pay_time) AS pay_time,in_money AS in_moneys");
		hql.append(" FROM	investors	WHERE	investor_status IN (1, 2, 3)");
		hql.append(" GROUP BY users_id	) b");
		hql.append(" WHERE a.users_id = b.users_id");
		hql.append(" ) t ON t.users_id = s.users_id WHERE 1 = 1");
		if(!QwyUtil.isNullAndEmpty(username)){
			hql.append(" AND us.username = ? ");
			ob.add(DESEncrypt.jiaMiUsername(username));
		}
		if(!QwyUtil.isNullAndEmpty(insertTime)){
			String [] time=QwyUtil.splitTime(insertTime);
			if(time.length > 1)
			{
				hql.append(" AND s.insert_time >= ? ");
				ob.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
				hql.append(" AND s.insert_time <= ? ");
				ob.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[1]+" 23:59:59"));
			}
			else{

				hql.append(" AND s.insert_time >= ? ");
				ob.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
				hql.append(" AND s.insert_time <= ? ");
				ob.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59"));
			}
		}
		List<Object []> list=dao.LoadAllSql(hql.toString(), ob.toArray());
		if(!QwyUtil.isNullAndEmpty(list)){
			 red = parsePlatformInvestDetail(list);
		}
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		log.error("操作异常: ",e);
	}
		return  red;
	}
	
	/**
	 * 转换为红包的所有金额
	 * @param list
	 * @return
	 */
	private ShareHongBaoDao parsePlatformInvestDetail(List<Object []> list){
		ShareHongBaoDao dao = new ShareHongBaoDao();
		if(!QwyUtil.isNullAndEmpty(list)){
			for (Object [] object : list) {
				dao.setInMoney(Double.parseDouble(object[0]+""));
				dao.setInviteInMoney(Double.parseDouble(object[1]+""));
				dao.setInMoneys(Double.parseDouble(object[2]+""));
				dao.setInviteInMoneys(Double.parseDouble(object[3]+""));
				dao.setNumner(object[4]+"");	
			}
		}
		return dao;
	}
	
	/**
	 * 将数据转换为ShareHongBaoDao
	 */
	private List<ShareHongBaoDao> toDateMoney(List<Object [] > list) throws ParseException{
		List<ShareHongBaoDao> meowIncome=new ArrayList<ShareHongBaoDao>();
		if(!QwyUtil.isNullAndEmpty(list)){
			for (Object [] object : list) {
				ShareHongBaoDao plat=new ShareHongBaoDao();
				plat.setUsername(object[0]+"");
			    plat.setInMoney(Double.parseDouble(object[1]+""));
			    plat.setInviteInMoney(Double.parseDouble(object[2]+""));
				plat.setInsertTime(QwyUtil.fmyyyyMMddHHmmss.parse(QwyUtil.fmyyyyMMddHHmmss.format(object[3])));
				plat.setInMoneys(Double.parseDouble(object[4]+""));
				plat.setInviteInMoneys(Double.parseDouble(object[5]+""));
			
				meowIncome.add(plat);
			}
		}
		return meowIncome;
	}
}
