package com.zfgt.thread.bean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.zfgt.common.util.PageUtil;
import com.zfgt.orm.MCoinDayDetail;
import com.zfgt.thread.dao.ThreadDAO;

import java.text.ParseException; 
@Service
public class meowCurrencyReportBean {
	@Resource
	private ThreadDAO dao;
	@SuppressWarnings("unchecked")
	public String setCoinDayDetail(PageUtil pageUtil){
		ArrayList<Object> ob = new ArrayList<Object>();
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT b.income AS income , m.expenditure AS expenditure,c.total_point AS total_point FROM ");
		buffer.append(" (SELECT SUM(m.coin) AS expenditure  , DATE_FORMAT(m.insert_time, '%Y-%m-%d') AS insert_time  FROM  `m_coin_record` m WHERE m.`coin_type`=1    ");
		buffer.append("  AND DATEDIFF(DATE_FORMAT(NOW(), '%Y-%m-%d'), DATE_FORMAT(m.insert_time, '%Y-%m-%d'))=1 )m , ");
		buffer.append("    (SELECT SUM(m.coin) AS income ,DATE_FORMAT(m.insert_time, '%Y-%m-%d') AS insert_time FROM   `m_coin_record` m WHERE m.`coin_type`=2  ");
		buffer.append("   AND DATEDIFF(DATE_FORMAT(NOW(), '%Y-%m-%d'), DATE_FORMAT(m.insert_time, '%Y-%m-%d'))=1 )b ,  ");
		buffer.append("   (SELECT SUM(u.total_point) AS total_point FROM users_info u)c");
		StringBuffer bufferCount=new StringBuffer();
		bufferCount.append(" SELECT COUNT(*)  ");
		bufferCount.append(" FROM (");
		bufferCount.append(buffer);
		bufferCount.append(") t");
		 pageUtil=dao.getBySqlAndSqlCount(pageUtil, buffer.toString(), bufferCount.toString(), ob.toArray());
		 List<Object [] > list = pageUtil.getList();
		if(list.size()>0){
			Object [] objects=list.get(0);
			String add = objects[0]==null?"0":objects[0]+"";
			String pay = objects[1]==null?"0":objects[1]+"";
			return saveCoinDetail(add+"",pay+"",objects[2]+"");
			}
		return null;
		
	}
	
	
	public String saveCoinDetail(String coin_add,String coin_pay,String leftCoin){
		Date date = new Date();  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        String specifiedDay = sdf.format(date);  
		MCoinDayDetail m=new MCoinDayDetail();
		m.setInsertTime(getSpecifiedDayBefore(specifiedDay));
		//m.setInsertTime(new Date());
		m.setCoinAdd(Long.valueOf(coin_add));
		m.setCoinPay(Long.valueOf(coin_pay));
		m.setLeftCoin(Long.valueOf(leftCoin));
		String id=dao.saveAndReturnId(m);
		return id;
	}

	 public static Date getSpecifiedDayBefore(String specifiedDay) {//可以用new Date().toLocalString()传递参数  
	        Calendar c = Calendar.getInstance();  
	        Date date = null;  
	        try {  
	            date = new SimpleDateFormat("yyyy-MM-dd").parse(specifiedDay);  
	            c.setTime(date);  
		        int day = c.get(Calendar.DATE);  
		        c.set(Calendar.DATE, day - 1);  
		
		        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c  
		                .getTime()); 
		        date = new SimpleDateFormat("yyyy-MM-dd").parse(dayBefore.toString());
	        } catch (ParseException e) {  
	            e.printStackTrace();  
	        }  
	        
	        return date;  
	    }  
}
