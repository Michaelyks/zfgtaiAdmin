package com.zfgt.admin.product.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

import com.zfgt.admin.product.dao.CZProcedures;
import com.zfgt.common.dao.ObjectDAO;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
@Service
public class CZProceduresBean {
	@Resource(name="objectDAO")
	private ObjectDAO dao;
	private static Logger log = Logger.getLogger(CZProceduresBean.class); 
	private List<CZProcedures> toDateMoney(List<Object [] > list) throws ParseException{
		List<CZProcedures> platInverstors=new ArrayList<CZProcedures>();
			if(!QwyUtil.isNullAndEmpty(list)){
			for (Object [] object : list) {
				CZProcedures cz=new CZProcedures();
				cz.setMoney(object[1]==null?"0":object[1]+"");
				cz.setInsertTime(object[0]+"");
				platInverstors.add(cz);
			}
		}
		return platInverstors;
	}
	@SuppressWarnings("unchecked")
	public   PageUtil<CZProcedures> loadCZProcedures(String insertTime,PageUtil pageUtil){
		String total ="0";
		try {
		ArrayList<Object> ob = new ArrayList<Object>();
		StringBuffer buff = new StringBuffer();
		
	//	buff.append("SELECT	DATE_FORMAT(dd.insert_time, '%Y-%m-%d') AS date,(");
		//buff.append("SELECT	SUM(cr.money) AS allmoney	FROM cz_record AS cr");
		//buff.append(" WHERE	cr. STATUS = 1	AND DATE_FORMAT(cr.insert_time, '%Y-%m-%d') = DATE_FORMAT(dd.insert_time, '%Y-%m-%d')");
		//buff.append(") AS cz_money FROM	dateday dd WHERE 1=1 and datediff(dd.insert_time, now()) <= 0");
		buff.append(" SELECT 	DATE_FORMAT(dd.insert_time, '%Y-%m-%d') AS date,");
		buff.append(" cz_money.allmoney as allmoney FROM	dateday dd");
		buff.append(" LEFT JOIN (	SELECT	SUM(CASE WHEN cr.money > 50000 THEN cr.money * 0.002	ELSE	100	END) AS allmoney, DATE_FORMAT(cr.check_time, '%Y-%m-%d') as check_time");
		buff.append(" FROM cz_record AS cr	WHERE	cr. STATUS = 1	GROUP BY	DATE_FORMAT(cr.check_time, '%Y-%m-%d')");
		buff.append("	)  cz_money on DATE_FORMAT(cz_money.check_time, '%Y-%m-%d') = DATE_FORMAT(dd.insert_time, '%Y-%m-%d')");
		buff.append("	WHERE	1 = 1 AND datediff(dd.insert_time, now()) <= 0");
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
		 List<CZProcedures> platUsers=toDateMoney(pageUtil.getList());
			pageUtil.setList(platUsers);
			if(platUsers.size()>0){
				StringBuffer sql = new StringBuffer();
				sql.append(" SELECT sum(t.allmoney)  ");
				sql.append(" FROM (");
				sql.append(buff);
				sql.append(") t");
				List lists = dao.LoadAllSql(sql.toString(), ob.toArray());
				total =lists.get(0)==null?"0": lists.get(0)+"";
//				HttpServletRequest request = ServletActionContext.getRequest();
//				request.getSession().setAttribute("czTotalAmount",  lists.get(0)==null?"0": lists.get(0)+"");
			}
		//	else{
				
			//}
			//return pageUtil;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		request.getSession().setAttribute("czTotalAmount",  total);
		return pageUtil;
		
	}
}
