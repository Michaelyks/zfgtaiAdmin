package com.zfgt.admin.webView.bean;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.CoinPurseProfit;
import com.zfgt.product.dao.ProductCategoryDAO;

/**
 * 曲线统计图
 * @author oi
 *
 */
@Service
public class StatisticsBean {
	private static Logger log = Logger.getLogger(StatisticsBean.class);
	@Resource
	private ProductCategoryDAO dao;
	
	public String quertProfit(Long usersId){
		String money = "0";
		try{
			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT in_money*0.01 FROM coin_purse s");
			sql.append(" where  s.users_id =?"  );
			List<Object> list = dao.LoadAllSql(sql.toString(), new Object[]{usersId});
			if(!QwyUtil.isNullAndEmpty(list)){
				money = QwyUtil.jieQuFa(Double.parseDouble(list.get(0).toString()),2)+"";
			}
		
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return money;
	}
	
	@SuppressWarnings("unchecked")
	public  PageUtil<CoinPurseProfit> queryAllUserProfit(Long usersId ,String insertTime,PageUtil pageUtil){
		try{
			ArrayList<Object> ob = new ArrayList<Object>();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT sr.pay_interest*0.01,DATE_SUB(sr.insert_time,INTERVAL 1 DAY) ");
			sql.append("FROM send_rates   sr  WHERE  sr.STATUS='1' AND sr.pay_interest>=1");
			sql.append(" AND sr.users_id =?"  );
			ob.add(usersId);
			if(!QwyUtil.isNullAndEmpty(insertTime)){
				String [] time=QwyUtil.splitTime(insertTime);
				if(time.length > 1)
				{
					sql.append(" AND sr.insert_time >= ? ");
					ob.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
					sql.append(" AND sr.insert_time <= ? ");
					ob.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[1]+" 23:59:59"));
				}
				else{
					sql.append(" AND sr.insert_time >= ? ");
					ob.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 00:00:00"));
					sql.append(" AND sr.insert_time <= ? ");
					ob.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0]+" 23:59:59"));
				}

			}
			sql.append(" GROUP BY DATE_FORMAT(sr.insert_time, '%Y-%m-%d') ORDER BY  DATE_FORMAT(sr.insert_time, '%Y-%m-%d') DESC");
			StringBuffer bufferCount=new StringBuffer();
			bufferCount.append(" SELECT COUNT(*)  ");
			bufferCount.append(" FROM (");
			bufferCount.append(sql);
			bufferCount.append(") t");
			 pageUtil=dao.getBySqlAndSqlCount(pageUtil, sql.toString(), bufferCount.toString(), ob.toArray());
			 List<CoinPurseProfit> platUsers=toDateMoney(pageUtil.getList());
				pageUtil.setList(platUsers);
				return pageUtil;
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	private List<CoinPurseProfit> toDateMoney(List<Object [] > list) throws NumberFormatException, Exception{
		List<CoinPurseProfit> meowIncome=new ArrayList<CoinPurseProfit>();
		if(!QwyUtil.isNullAndEmpty(list)){
			for (Object [] object : list) {
				CoinPurseProfit plat=new CoinPurseProfit();
				plat.setInsertTime(QwyUtil.fmyyyyMMddHHmmss.parse(QwyUtil.fmyyyyMMddHHmmss.format(object[1])));
				plat.setPayInterest(object[0]==null?"0": QwyUtil.jieQuFa(Double.parseDouble(object[0]+""),2)+"");
				meowIncome.add(plat);
			}
		}
		return meowIncome;
	}
	
	/**
	 * 查询用户七天收益
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String queryUserProfit(Long usersId){
		try{
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT sr.pay_interest*0.01,DATE_FORMAT(tab.insert_time, '%m-%d') FROM ");
			sql.append("(SELECT insert_time FROM dateday  ORDER BY insert_time DESC LIMIT 2,7) tab ");
			sql.append("LEFT JOIN send_rates sr ON tab.insert_time=DATE_FORMAT(DATE_SUB(sr.insert_time,INTERVAL 1 DAY), '%Y-%m-%d') ");
			sql.append("AND sr.users_id=? AND sr.STATUS='1' AND sr.pay_interest>=1 ORDER BY tab.insert_time");
			
			List<Object> list = dao.LoadAllSql(sql.toString(), new Object[]{usersId});
			return crateJsArray(list);
		} catch (Exception e) {
			log.error("操作异常: ",e);
		}
		return "";
	}
	
	/**
	 * 将list数据转换成js数组
	 * @param list
	 * @return
	 */
	public String crateJsArray(List<Object> list){
		StringBuffer dates = new StringBuffer();
		StringBuffer profits = new StringBuffer();
		dates.append("[");
		profits.append("[");
		Object[] obj = null;
		for(int i=0;i<list.size();i++){
			obj = (Object[])list.get(i);
			if(i==0){
				dates.append("\""+obj[1]+"\"");
				if(obj[0]==null){
					profits.append(0);
				}else {
					String str = String.valueOf(obj[0]);
					str = new BigDecimal(str).toPlainString();
					if(str.contains(".")){
						int index = str.indexOf('.') + 1;
						profits.append(str.substring(0, index + 2));
					}else {
						profits.append(str);
					}
			        
					
				}
			}else {
				dates.append(",\""+obj[1]+"\"");
				if(obj[0]==null){
					profits.append(","+0);
				}else {
			        String str = String.valueOf(obj[0]);
			        str = new BigDecimal(str).toPlainString();
			        if(str.contains(".")){
			        	int index = str.indexOf('.') + 1;
						profits.append(","+str.substring(0, index + 2));
			        }else {
			        	profits.append(","+str);
			        }
			        
				}
			}
		}
		dates.append("]");
		profits.append("]");
		StringBuffer jsData = new StringBuffer();
		jsData.append("{");
		jsData.append("\"status\":\"ok\",");
		jsData.append("\"date\":");
		jsData.append(dates);
		jsData.append(",");
		jsData.append("\"profit\":");
		jsData.append(profits);
		jsData.append("}");
		return jsData.toString();
	}
}
