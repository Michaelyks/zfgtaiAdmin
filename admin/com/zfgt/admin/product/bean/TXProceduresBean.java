package com.zfgt.admin.product.bean;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

import com.zfgt.admin.product.dao.TXProcedures;
import com.zfgt.common.dao.ObjectDAO;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
@Service
public class TXProceduresBean {
	@Resource(name="objectDAO")
	private ObjectDAO dao;
	private static Logger log = Logger.getLogger(TXProceduresBean.class); 
	private List<TXProcedures> txDateMoney(List<Object [] > list) throws ParseException{
		List<TXProcedures> platInverstors=new ArrayList<TXProcedures>();
			if(!QwyUtil.isNullAndEmpty(list)){
			for (Object [] object : list) {
				TXProcedures tx =new TXProcedures();
				tx.setCount(object[1]==null?"0":object[1]+"");
				tx.setDate(object[0]+"");
				platInverstors.add(tx );
			}
		}
		return platInverstors;
	}
	@SuppressWarnings("unchecked")
	public   PageUtil<TXProcedures> loadTXProcedures(String insertTime,PageUtil pageUtil){
		String total ="0";
		try {
		ArrayList<Object> ob = new ArrayList<Object>();
		StringBuffer buff = new StringBuffer();
		buff.append(" SELECT	DATE_FORMAT(dd.insert_time, '%Y-%m-%d') AS date ,");
		buff.append(" tx_money.counts as counts FROM	dateday dd");
		buff.append(" LEFT JOIN	(	SELECT	COUNT(*) AS counts,");
		buff.append(" DATE_FORMAT(tx.check_time, '%Y-%m-%d') as check_time");
		buff.append(" FROM tx_record AS tx	WHERE	tx. STATUS = 1");
		buff.append(" GROUP BY DATE_FORMAT(tx.check_time, '%Y-%m-%d')");
		buff.append(" )  tx_money on tx_money.check_time =	DATE_FORMAT(dd.insert_time, '%Y-%m-%d') ");
		buff.append(" WHERE	1 = 1 AND datediff(dd.insert_time, now()) <= 0");
		//buff.append("SELECT DATE_FORMAT(dd.insert_time, '%Y-%m-%d') AS date,");
		//buff.append("(	SELECT COUNT(*) AS allmoney	FROM tx_record AS tx");
		//buff.append(" WHERE	tx. STATUS = 1 AND DATE_FORMAT(tx.insert_time, '%Y-%m-%d') = DATE_FORMAT(dd.insert_time, '%Y-%m-%d')");
		//buff.append(") AS tx_money FROM	dateday dd  where 1=1 and datediff(dd.insert_time, now()) <= 0");
		if(!QwyUtil.isNullAndEmpty(insertTime)){
			String [] time=QwyUtil.splitTime(insertTime);
			if(time.length>1){
				
				buff.append(" AND dd.insert_time >= ? ");
				ob.add(QwyUtil.fmMMddyyyy.parse(time[0]));
				buff.append(" AND dd.insert_time <= ? ");
				ob.add(QwyUtil.fmMMddyyyy.parse(time[1]));
			}else{
				
				buff.append(" AND dd.insert_time >= ? ");
				ob.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
				buff.append(" AND dd.insert_time <= ? ");
				ob.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59"));
			}
		}
		buff.append(" ORDER BY DATE_FORMAT( dd.insert_time, '%Y-%m-%d ' ) DESC  ");
		
		StringBuffer bufferCount=new StringBuffer();
		bufferCount.append(" SELECT COUNT(1)  ");
		bufferCount.append(" FROM (");
		bufferCount.append(buff);
		bufferCount.append(") t");
		 pageUtil=dao.getBySqlAndSqlCount(pageUtil, buff.toString(), bufferCount.toString(), ob.toArray());
		 List<TXProcedures> platUsers=txDateMoney(pageUtil.getList());
			pageUtil.setList(platUsers);
			if(platUsers.size()>0){
				StringBuffer sql = new StringBuffer();
				sql.append(" SELECT sum(t.counts)  ");
				sql.append(" FROM (");
				sql.append(buff);
				sql.append(") t");
				List lists = dao.LoadAllSql(sql.toString(), ob.toArray());
				total =lists.get(0)==null?"0": lists.get(0)+"";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		request.getSession().setAttribute("txTotalAmount",  total);
		return pageUtil;
		
	}
	
	
	private List<TXProcedures> txToCZMoney(List<Object [] > list) throws ParseException{
		List<TXProcedures> platInverstors=new ArrayList<TXProcedures>();
			if(!QwyUtil.isNullAndEmpty(list)){
			for (Object [] object : list) {
				TXProcedures tx =new TXProcedures();
				
				tx .setDate(object[0]+"");
				tx.setTxMoney(object[1]==null?"0":object[1]+"");
				tx.setCzMoney(object[2]==null?"0":object[2]+"");
				platInverstors.add(tx );
			}
		}
		return platInverstors;
	}
		
	public   PageUtil<TXProcedures> loadExtractionrate(String insertTime,PageUtil pageUtil){
		try {
		ArrayList<Object> ob = new ArrayList<Object>();
		StringBuffer buff = new StringBuffer();
		//buff.append("SELECT	DATE_FORMAT(dd.insert_time, '%Y-%m-%d') AS date,(");
		//buff.append("SELECT SUM(tx.money) AS allmoney	FROM	tx_record AS tx	WHERE");
		//buff.append(" tx. STATUS =1		AND DATE_FORMAT(tx.insert_time, '%Y-%m-%d') = DATE_FORMAT(dd.insert_time, '%Y-%m-%d')	) AS tx_money ,");
		//buff.append("(	SELECT SUM(cr.money) AS allmoney 	FROM	cz_record AS cr	WHERE");
		//buff.append(" cr. STATUS =1	AND DATE_FORMAT(cr.insert_time, '%Y-%m-%d') = DATE_FORMAT(dd.insert_time, '%Y-%m-%d')	) AS cz_money");
		buff.append(" SELECT	DATE_FORMAT(dd.insert_time, '%Y-%m-%d') AS date,");
		buff.append(" tx_money.tx_moneys as tx_moneys, cz_money.cz_moneys as cz_moneys");
		buff.append(" FROM	dateday dd LEFT JOIN ");
		buff.append(" (	SELECT		SUM(tx.money) AS tx_moneys ,");
		buff.append("  DATE_FORMAT(tx.insert_time, '%Y-%m-%d') as insert_time");
		buff.append(" FROM tx_record tx");
		buff.append(" WHERE	tx. STATUS = 1 GROUP BY   DATE_FORMAT(tx.insert_time, '%Y-%m-%d')");
		buff.append(" )  tx_money  on   DATE_FORMAT(dd.insert_time, '%Y-%m-%d') = tx_money.insert_time");
	
		buff.append(" LEFT JOIN	(	SELECT SUM(cr.money) AS cz_moneys,");
		buff.append(" DATE_FORMAT(cr.insert_time, '%Y-%m-%d') as insert_time");
		buff.append(" FROM cz_record  cr	WHERE	cr. STATUS = 1");
		buff.append(" GROUP BY   DATE_FORMAT(cr.insert_time, '%Y-%m-%d')");
		buff.append(" )cz_money on 	 DATE_FORMAT(cz_money.insert_time, '%Y-%m-%d') = DATE_FORMAT(dd.insert_time, '%Y-%m-%d')");
		buff.append(" WHERE	1 = 1 AND datediff(dd.insert_time, now()) <= 0");
		if(!QwyUtil.isNullAndEmpty(insertTime)){
			String [] time=QwyUtil.splitTime(insertTime);
			if(time.length>1){
				
				buff.append(" AND dd.insert_time >= ? ");
				ob.add(QwyUtil.fmMMddyyyy.parse(time[0]));
				buff.append(" AND dd.insert_time <= ? ");
				ob.add(QwyUtil.fmMMddyyyy.parse(time[1]));
			}else{
				
				buff.append(" AND dd.insert_time >= ? ");
				ob.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
				buff.append(" AND dd.insert_time <= ? ");
				ob.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59"));
			}
		}
		buff.append(" ORDER BY DATE_FORMAT( dd.insert_time, '%Y-%m-%d ' ) DESC  ");
		StringBuffer bufferCount=new StringBuffer();
		bufferCount.append(" SELECT COUNT(t.date)  ");
		bufferCount.append(" FROM (");
		bufferCount.append(buff);
		bufferCount.append(") t");
		 pageUtil=dao.getBySqlAndSqlCount(pageUtil, buff.toString(), bufferCount.toString(), ob.toArray());
		 List<TXProcedures> TXUsers=txToCZMoney(pageUtil.getList());
		 pageUtil.setList(TXUsers);
		 if(TXUsers.size()>0){
			 StringBuffer sql = new StringBuffer();
				sql.append(" SELECT sum(t.cz_moneys) as czMoney, sum(t.tx_moneys) as txMoney ");
				sql.append(" FROM (");
				sql.append(buff);
				sql.append(") t");
				List<Object [] > lists = dao.LoadAllSql(sql.toString(), ob.toArray());
				for(Object[] object : lists){
					HttpServletRequest request = ServletActionContext.getRequest();
					request.getSession().setAttribute("czTotalAmount",  object[0]==null?"0":object[0]+"");
					request.getSession().setAttribute("txTotalAmount",  object[1]==null?"0":object[1]+"");
				}
				
		 }
		 else{
				HttpServletRequest request = ServletActionContext.getRequest();
				request.getSession().setAttribute("czTotalAmount",  "0");
				request.getSession().setAttribute("txTotalAmount",  "0");
			}
		
		return pageUtil;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
		return pageUtil;
	}	
	
}
