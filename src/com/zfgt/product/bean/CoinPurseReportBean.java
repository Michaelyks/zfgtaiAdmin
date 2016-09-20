package com.zfgt.product.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.common.util.DESEncrypt;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.CoinPurseReport;
import com.zfgt.product.dao.ProductCategoryDAO;

/**
 * 零钱包报表业务层
 * @author liuchao
 *
 */
@Service
public class CoinPurseReportBean {
	private static Logger log = Logger.getLogger(CoinPurseReportBean.class);
	
	@Resource
	private ProductCategoryDAO dao;
	/**
	 * 一周不可动金额报表
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void aWeekWithoutMoving(PageUtil<CoinPurseReport> pageUtil,String insertTime) throws Exception{
		List list = new ArrayList();
		String beginDate = "";
		String endDate = "";
		try{
			Calendar calendar = Calendar.getInstance();
			String dayOfMonth = "";
			if(calendar.get(Calendar.DAY_OF_MONTH)<10){
				dayOfMonth = "0"+calendar.get(Calendar.DAY_OF_MONTH);
			}else {
				dayOfMonth = ""+calendar.get(Calendar.DAY_OF_MONTH);
			}
			String carrentDate = calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+dayOfMonth;
			if(!QwyUtil.isNullAndEmpty(insertTime)){
				String [] time=QwyUtil.splitTime(insertTime);
				if(time.length==2){
					beginDate = QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[0].trim()));
					endDate = QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[1].trim()));
				}else {
					beginDate = QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[0].trim()));
					endDate = beginDate;
				}
				if(QwyUtil.fmyyyyMMdd.parse(beginDate).compareTo(QwyUtil.fmyyyyMMdd.parse("2015-09-18"))==-1){
					beginDate = "2015-09-18";
				}
				if(QwyUtil.fmyyyyMMdd.parse(endDate).compareTo(QwyUtil.fmyyyyMMdd.parse(carrentDate))==1){
					endDate = carrentDate;
				}
			}else {
				beginDate = "2015-09-18";
				endDate = carrentDate;
			}
			StringBuffer sql = createAWeekWithoutMovingSql(beginDate,endDate,pageUtil);
			
			List listDate = dao.LoadAllSql(sql.toString(), null);
			CoinPurseReport coinPurseReport = null;
			Object[] data = null;
			double aWeekOutSumMoney;//7天转出总金额
			double aWeekInSumMoney;//7天转入总金额
			double cunliang;//存量
			double inMoney;//每天转入金额
			for(int i=0;i<listDate.size();i++){
				coinPurseReport = new CoinPurseReport();
				data = (Object[])listDate.get(i);
				coinPurseReport.setDate(data[0].toString());//日期
				inMoney = Double.valueOf((data[1]==null ? "0" : data[1].toString()));
				coinPurseReport.setInMoney(new BigDecimal(inMoney).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());//转入金额
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, -7);

                Date date1 = QwyUtil.fmyyyyMMdd.parse(c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DAY_OF_MONTH));
                Date date2 = QwyUtil.fmyyyyMMdd.parse(coinPurseReport.getDate());
                if(date2.compareTo(date1)==1){
                	list.add(coinPurseReport);
					continue;
                }
                aWeekOutSumMoney = Double.valueOf(data[3]==null ? "0" : data[3].toString());//7天转出总金额
                aWeekInSumMoney = Double.valueOf(data[4]==null ? "0" : data[4].toString());//7天转入总金额
				cunliang = Double.valueOf((data[2]==null ? "0":data[2].toString()));//存量
				double aWeekWithoutMovingMoneyRate = aWeekOutSumMoney/(cunliang+aWeekInSumMoney);
//				if(sumMoney>=aWeekOutSumMoney){//1、如果当日总金额大于未来七天转出金额，则表示未来7天没有使用当日转入金额
//					aWeekRetentionRate = 100;
//				}else if(sumMoney<aWeekOutSumMoney && (aWeekOutSumMoney-sumMoney)<Double.valueOf(inMoney)){
//					//2、如果当日总金额小于未来七天转出金额，则表示消费了当日转入金额，并且如果当日总金额减去未来七天转出金额小于当日转入金额，则表示当日转入金额有剩余
//					aWeekRetentionRate = (1-((aWeekOutSumMoney-sumMoney)/inMoney))*100;
//				}else {
//					//3、排除上面两种情况，则存入金额全部消费完
//					aWeekRetentionRate=0;
//				}
				coinPurseReport.setaWeekWithoutMovingMoneyRate(new BigDecimal(aWeekWithoutMovingMoneyRate*100).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()+"%");//七日不可动比
				coinPurseReport.setaWeekWithoutMovingMoney(new BigDecimal(aWeekWithoutMovingMoneyRate*(cunliang+aWeekInSumMoney)).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());//七日不可动金额
				list.add(coinPurseReport);
			}
		}catch (Exception e) {
			log.error("操作异常: ",e);
			throw e;
		}
		pageUtil.setList(list);
		int count = queryCount(beginDate,endDate);
		if(count%pageUtil.getPageSize()==0){
			pageUtil.setPageCount(count/pageUtil.getPageSize());
		}else {
			pageUtil.setPageCount((count/pageUtil.getPageSize())+1);
		}
		List listCount = new ArrayList();
		for(int i=1;i<=pageUtil.getPageCount();i++){
			listCount.add(i);
		}
		pageUtil.setListCount(listCount);
	}
	
	/**
	 * 一周不可动金额报表SQL
	 * @return
	 */
	public StringBuffer createAWeekWithoutMovingSql(String beginDate,String endDate,PageUtil<CoinPurseReport> pageUtil){
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ( ");
		sql.append("SELECT riqis.riqi,in_money_every_day_tab.in_money*0.01,cunliang_tab.cunliang*0.01 ");
		sql.append(",out_money_7_day_tab.out_money_7_day*0.01,in_money_7_day_tab.in_money_7_day*0.01 FROM (SELECT DATE_FORMAT(insert_time, '%Y-%m-%d') AS riqi FROM weeks) riqis "); 
		sql.append("LEFT JOIN ( ");
//		#每天转入
		sql.append("SELECT DATE_FORMAT(insert_time, '%Y-%m-%d') AS riqi,SUM(in_money) AS in_money FROM shift_to GROUP BY DATE_FORMAT(insert_time, '%Y-%m-%d') "); 
		sql.append("ORDER BY DATE_FORMAT(insert_time, '%Y-%m-%d') ");
		sql.append(") in_money_every_day_tab ON riqis.riqi=in_money_every_day_tab.riqi ");
		sql.append("LEFT JOIN( ");
//		#前一天的存量
		sql.append("SELECT riqi,SUM(in_money) AS cunliang FROM(SELECT DATE_FORMAT(insert_time, '%Y-%m-%d') AS riqi,MAX(in_money) AS in_money FROM send_rates GROUP BY DATE_FORMAT(insert_time, '%Y-%m-%d'),users_id) tt GROUP BY riqi ");
//		sql.append(") cunliang_tab ON riqis.riqi=DATE_SUB(cunliang_tab.riqi,INTERVAL 2 DAY) "); 
		sql.append(") cunliang_tab ON riqis.riqi=cunliang_tab.riqi "); 
		sql.append("LEFT JOIN( ");
//		#7天后转出金额
		sql.append("SELECT riqi,( ");
		sql.append("SELECT SUM(sum_money) AS sum_money FROM (SELECT DATE_FORMAT(insert_time, '%Y-%m-%d') AS riqi,SUM(in_money) AS sum_money FROM roll_out "); 
		sql.append("GROUP BY DATE_FORMAT(insert_time, '%Y-%m-%d'))tab1 "); 
		sql.append("WHERE tab1.riqi BETWEEN tab2.riqi AND DATE_SUB(tab2.riqi,INTERVAL -6 DAY) ");
		sql.append(") AS out_money_7_day FROM (SELECT DATE_FORMAT(insert_time, '%Y-%m-%d') AS riqi,SUM(in_money) AS sum_money FROM roll_out "); 
		sql.append("GROUP BY DATE_FORMAT(insert_time, '%Y-%m-%d'))tab2 ");
		sql.append(") out_money_7_day_tab ON riqis.riqi=out_money_7_day_tab.riqi ");
		sql.append("LEFT JOIN( ");
//		#7天后转入金额
		sql.append("SELECT riqi,( ");
		sql.append("SELECT SUM(sum_money) AS sum_money FROM (SELECT DATE_FORMAT(insert_time, '%Y-%m-%d') AS riqi,SUM(in_money) AS sum_money FROM shift_to "); 
		sql.append("GROUP BY DATE_FORMAT(insert_time, '%Y-%m-%d'))tab1 "); 
		sql.append("WHERE tab1.riqi BETWEEN tab2.riqi AND DATE_SUB(tab2.riqi,INTERVAL -6 DAY) ");
		sql.append(") AS in_money_7_day FROM (SELECT DATE_FORMAT(insert_time, '%Y-%m-%d') AS riqi,SUM(in_money) AS sum_money FROM shift_to "); 
		sql.append("GROUP BY DATE_FORMAT(insert_time, '%Y-%m-%d'))tab2 ");
		sql.append(") in_money_7_day_tab ON riqis.riqi=in_money_7_day_tab.riqi ");
		
		if(!QwyUtil.isNullAndEmpty(beginDate) && !QwyUtil.isNullAndEmpty(endDate)){
			sql.append("WHERE riqis.riqi between '"+beginDate+"' and '"+endDate+"' ");
		}
		sql.append("ORDER BY riqis.riqi desc");
		sql.append(")tab limit "+((pageUtil.getCurrentPage()-1)*pageUtil.getPageSize())+","+pageUtil.getPageSize());
		return sql;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void coinPurseDataReport(PageUtil<CoinPurseReport> pageUtil,String insertTime) throws Exception{
		List list = new ArrayList();
		String beginDate = "";
		String beginDate1 = "";
		String endDate = "";
		try{
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, -1);
			int days = 0;
			String dayOfMonth = "";
			if(calendar.get(Calendar.DAY_OF_MONTH)<10){
				dayOfMonth = "0"+calendar.get(Calendar.DAY_OF_MONTH);
			}else {
				dayOfMonth = ""+calendar.get(Calendar.DAY_OF_MONTH);
			}
			String carrentDate = calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+dayOfMonth;
			if(!QwyUtil.isNullAndEmpty(insertTime)){
				String [] time=QwyUtil.splitTime(insertTime);
				if(time.length==2){
					beginDate = QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[0].trim()));
					endDate = QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[1].trim()));
					int eq = QwyUtil.fmyyyyMMdd.parse(beginDate).compareTo(QwyUtil.fmyyyyMMdd.parse(QwyUtil.fmyyyyMMdd.format(new Date())));
					if(eq==1 || eq ==0){
						return;
					}
				}else {
					int eq = QwyUtil.fmMMddyyyy.parse(insertTime).compareTo(QwyUtil.fmMMddyyyy.parse(QwyUtil.fmMMddyyyy.format(new Date())));
					if(eq==0 || eq==1){
						return;
					}
					beginDate = QwyUtil.fmyyyyMMdd.format(QwyUtil.fmMMddyyyy.parse(time[0].trim()));
					endDate = beginDate;
				}
				
				if(QwyUtil.fmyyyyMMdd.parse(beginDate).compareTo(QwyUtil.fmyyyyMMdd.parse("2015-09-18"))==-1){//输入的开始时间小于2015-09-18
					beginDate = "2015-09-18";
					beginDate1 = "2015-09-18";
				}else {
					beginDate1 = beginDate;
					Calendar rightNow = Calendar.getInstance();
					Date dt=QwyUtil.fmyyyyMMdd.parse(beginDate);
					rightNow.setTime(dt);
					days = 13;
					rightNow.add(Calendar.DAY_OF_YEAR,-days);
					Date dt1=rightNow.getTime();
					beginDate = QwyUtil.fmyyyyMMdd.format(dt1);
					if(QwyUtil.fmyyyyMMdd.parse(beginDate).compareTo(QwyUtil.fmyyyyMMdd.parse("2015-09-18"))==-1){
						days = (int)(QwyUtil.fmyyyyMMdd.parse("2015-09-18").getTime()-dt.getTime())/(1000*60*60*24);
						if(days<0){
							days = -days;
						}
						beginDate = "2015-09-18";
					}
				}
				if(QwyUtil.fmyyyyMMdd.parse(endDate).compareTo(QwyUtil.fmyyyyMMdd.parse(carrentDate))==1){
					endDate = carrentDate;
				}
			}else {
				beginDate = "2015-09-18";
				beginDate1 = "2015-09-18";
				endDate = carrentDate;
			}
			StringBuffer sql = createCoinPurseDataReportSql(beginDate,endDate,pageUtil);
			int count = queryCount(beginDate,endDate);
			if((count-days)%pageUtil.getPageSize()==0){
				pageUtil.setPageCount((count-days)/pageUtil.getPageSize());
			}else {
				pageUtil.setPageCount(((count-days)/pageUtil.getPageSize())+1);
			}
			List listCount = new ArrayList();
			for(int i=1;i<=pageUtil.getPageCount();i++){
				listCount.add(i);
			}
			pageUtil.setListCount(listCount);
			List listData = dao.LoadAllSql(sql.toString(), null);
			CoinPurseReport coinPurseReport = null;
			Object[] data = null;
			double aWeekOutSumMoney;//7天转出总金额
			double aWeekInSumMoney;//七天转入总金额

			int isBreak = 0;
			for(int i=0;i<pageUtil.getPageSize() && i<listData.size();i++){
				if(isBreak==1){
					break;
				}
				coinPurseReport = new CoinPurseReport();
				data = (Object[])listData.get(i);
				
				if(QwyUtil.isNullAndEmpty(data)){
					break;
				}
				
				if(data[0].toString().equals(beginDate1)){
					isBreak = 1;
				}
				coinPurseReport.setDate(data[0].toString());//日期
				coinPurseReport.setInMoney(new BigDecimal(Double.valueOf((data[1]==null ? "0" : data[1].toString()))*0.01).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());//转入金额
				double inmoney = Double.valueOf((data[1]==null ? "0" : data[1].toString()));
                Date date1 = QwyUtil.fmyyyyMMdd.parse("2015-09-24");
                Date date2 = QwyUtil.fmyyyyMMdd.parse(coinPurseReport.getDate());
                if(date2.compareTo(date1)==1 || date2.compareTo(date1)==0){
                	double rate = 0;
                	int index =0;
                	double yizhouzhuanrujine = 0;
                	for(int j=6;j<13;j++){
                		Object[] data1 = null;
                		try{
                			data1 = (Object[])listData.get(i+j);
                			if(j==6){
                				yizhouzhuanrujine = Double.valueOf(data1[10]==null ? "0" : data1[10].toString())*0.01;//一周转入金额
                			}
                			index ++;
                		}catch (Exception e) {
							break;
						}
                		aWeekOutSumMoney = Double.valueOf(data1[3]==null ? "0" : data1[3].toString())*0.01;//7天转出总金额
                		aWeekInSumMoney = Double.valueOf(data1[10]==null ? "0" : data1[10].toString())*0.01;//7天转入总金额
                		double cunliang2 = Double.valueOf(data1[9]==null ? "0" : data1[9].toString())*0.01;//前日存量
                		if(cunliang2+aWeekInSumMoney>0)
                		rate = rate+aWeekOutSumMoney/(cunliang2+aWeekInSumMoney);
                		else if(cunliang2+aWeekInSumMoney == 0 && aWeekOutSumMoney > 0){
                			rate = rate +1;
                		}
//                		sumMoney = Double.valueOf((data1[2]==null ? "0":data1[2].toString()).toString());//当前日转入金额之前的总额
//        				double inMoney = Double.valueOf(data1[1]==null ? "0" : data1[1].toString());
//        				if(sumMoney>=aWeekOutSumMoney){//1、如果当日转入之前的总金额大于未来七天转出金额，则表示未来7天没有使用当日转入金额，7日不可动金额比为0
//        					
//        				}else if(sumMoney<aWeekOutSumMoney && (aWeekOutSumMoney-sumMoney)<inMoney){
//        					//2、如果当日总金额小于未来七天转出金额，则表示消费了当日转入金额，并且如果总金额减去未来七天转出金额小于当日转入金额，则表示当日转入金额有剩余
//        					rate = rate +(aWeekOutSumMoney-sumMoney)/inMoney;
//        				}else {
//        					//3、排除上面两种情况，则存入金额全部消费完,7日不可动金额比为1
//        					rate = rate +1;
//        				}
                	}
                	if(index!=0){
                		double qianricunliang = Double.valueOf(data[9]==null ? "0" : data[9].toString())*0.01;//前日存量
//                		double yizhouzhuanrujine = Double.valueOf(data[10]==null ? "0" : data[10].toString())*0.01;//一周转入金额
                		coinPurseReport.setaWeekWithoutMovingMoney(new BigDecimal((rate/index)*(qianricunliang+yizhouzhuanrujine)).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());
                	}
                	
                }
                if(data[8]!=null){
                	coinPurseReport.setOutMoney(new BigDecimal(Double.valueOf(data[8].toString())*0.01).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());
                }else {
                	coinPurseReport.setOutMoney("");
                }
                if(data[4]!=null){
                	coinPurseReport.setFuxi(new BigDecimal(Double.valueOf(data[4].toString())*0.01).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());
                }else {
                	coinPurseReport.setFuxi("");
                }
                double cunliang = Double.valueOf(data[7]==null ? "0": data[7].toString());
                coinPurseReport.setCunliang(new BigDecimal(cunliang*0.01).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());
                if(data[5]!=null){
                	coinPurseReport.setChongzhiRate(new BigDecimal((inmoney/Double.valueOf(data[5].toString()))*100).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()+"%");
                }else {
                	coinPurseReport.setChongzhiRate("");
                }
                if(data[6]!=null){
                	if(Double.valueOf(data[6].toString())!=0){
                		coinPurseReport.setTouziRate(new BigDecimal((inmoney/Double.valueOf(data[6].toString()))*100).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()+"%");
                	}
                	
                }else {
                	coinPurseReport.setTouziRate("");
                }
                
                list.add(coinPurseReport);
			}
		}catch (Exception e) {
			log.error("操作异常: ",e);
			throw e;
		}
		pageUtil.setList(list);
	}
	
	/**
	 * 零钱包数据报表sql
	 * @param beginDate
	 * @param endDate
	 * @param pageUtil
	 * @return
	 */
	public StringBuffer createCoinPurseDataReportSql(String beginDate,String endDate,PageUtil<CoinPurseReport> pageUtil){
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ( ");
		sql.append("SELECT riqis.riqi,in_money_every_day_tab.in_money,sum_money_every_day_tab.sum_money_every_day,out_money_7_day_tab.out_money_7_day,shouyi_tab.shouyi_money, ");
		sql.append("chongzhi_tab.chongzhi_money,touzi_tab.touzi_money,cunliang_tab.cunliang,out_money,cunliang2_tab.cunliang2,in_money_7_day_tab.in_money_7_day ");
		sql.append("FROM (SELECT DATE_FORMAT(insert_time, '%Y-%m-%d') AS riqi FROM weeks) riqis  ");
		sql.append("LEFT JOIN (  ");
		//#每天转入金额
		sql.append("SELECT DATE_FORMAT(insert_time, '%Y-%m-%d') AS riqi,SUM(in_money) AS in_money FROM shift_to GROUP BY DATE_FORMAT(insert_time, '%Y-%m-%d')  ");
		sql.append("ORDER BY DATE_FORMAT(insert_time, '%Y-%m-%d')  ");
		sql.append(") in_money_every_day_tab ON riqis.riqi=in_money_every_day_tab.riqi  ");
		sql.append("LEFT JOIN(  ");
		//当天总金额
		sql.append("SELECT riqi,SUM(in_money) AS sum_money_every_day FROM(SELECT DATE_FORMAT(insert_time, '%Y-%m-%d') AS riqi,MAX(in_money) AS in_money FROM send_rates GROUP BY DATE_FORMAT(insert_time, '%Y-%m-%d'),users_id) tt GROUP BY riqi  ");
		sql.append(") sum_money_every_day_tab ON riqis.riqi=sum_money_every_day_tab.riqi   ");
		sql.append("LEFT JOIN(  ");
		//#未来七天转出金额
		sql.append("SELECT riqi,(  ");
		sql.append("SELECT SUM(sum_money) AS sum_money FROM (SELECT DATE_FORMAT(insert_time, '%Y-%m-%d') AS riqi,SUM(in_money) AS sum_money FROM roll_out   ");
		sql.append("GROUP BY DATE_FORMAT(insert_time, '%Y-%m-%d'))tab1  ");
		sql.append("WHERE tab1.riqi BETWEEN tab2.riqi AND DATE_SUB(tab2.riqi,INTERVAL -6 DAY)  ");
		sql.append(") AS out_money_7_day FROM (SELECT DATE_FORMAT(insert_time, '%Y-%m-%d') AS riqi,SUM(in_money) AS sum_money FROM roll_out  ");
		sql.append("GROUP BY DATE_FORMAT(insert_time, '%Y-%m-%d'))tab2  ");
		sql.append(") out_money_7_day_tab ON riqis.riqi=out_money_7_day_tab.riqi  ");
		sql.append("LEFT JOIN ( ");
		//#收益
		sql.append("SELECT DATE_FORMAT(insert_time, '%Y-%m-%d') AS riqi,SUM(money) AS shouyi_money FROM coin_purse_funds_record  ");
		sql.append("WHERE STATUS='0' AND TYPE='shouyi' GROUP BY DATE_FORMAT(insert_time, '%Y-%m-%d') ");
		sql.append(") shouyi_tab ON riqis.riqi=shouyi_tab.riqi  ");
		sql.append("LEFT JOIN( ");
		//#充值金额
		sql.append("SELECT DATE_FORMAT(check_time, '%Y-%m-%d') AS riqi,SUM(money) AS chongzhi_money FROM cz_record WHERE STATUS='1' GROUP BY DATE_FORMAT(check_time, '%Y-%m-%d') ");
		sql.append(") chongzhi_tab ON riqis.riqi=chongzhi_tab.riqi  ");
		sql.append("LEFT JOIN( ");
		//#总投资金额
		sql.append("SELECT DATE_FORMAT(pay_time, '%Y-%m-%d') AS riqi,SUM(in_money) AS touzi_money FROM investors ");
		sql.append("WHERE investor_status IN ('1','2','3') GROUP BY DATE_FORMAT(pay_time, '%Y-%m-%d') ");
		sql.append(") touzi_tab ON riqis.riqi=touzi_tab.riqi ");
		sql.append("LEFT JOIN ( ");
		//#存量
		sql.append("SELECT riqi,SUM(in_money) AS cunliang FROM(SELECT DATE_FORMAT(insert_time, '%Y-%m-%d') AS riqi,MAX(in_money) AS in_money FROM send_rates GROUP BY DATE_FORMAT(insert_time, '%Y-%m-%d'),users_id) tt GROUP BY riqi ");
		sql.append(") cunliang_tab ON riqis.riqi=DATE_SUB(cunliang_tab.riqi,INTERVAL 1 DAY) ");
		sql.append("LEFT JOIN ( ");
		//#每天转出总金额
		sql.append("SELECT DATE_FORMAT(insert_time, '%Y-%m-%d') AS riqi,SUM(money) AS out_money FROM coin_purse_funds_record WHERE STATUS='0' AND TYPE='out' GROUP BY DATE_FORMAT(insert_time, '%Y-%m-%d') ");
		sql.append(") out_money_tab ON riqis.riqi=out_money_tab.riqi ");
		sql.append("LEFT JOIN( ");
		//#前一天的存量
		sql.append("SELECT riqi,SUM(in_money) AS cunliang2 FROM(SELECT DATE_FORMAT(insert_time, '%Y-%m-%d') AS riqi,MAX(in_money) AS in_money FROM send_rates GROUP BY DATE_FORMAT(insert_time, '%Y-%m-%d'),users_id) tt GROUP BY riqi ");
//		sql.append(") cunliang2_tab ON riqis.riqi=DATE_SUB(cunliang2_tab.riqi,INTERVAL 2 DAY) ");
		sql.append(") cunliang2_tab ON riqis.riqi=cunliang2_tab.riqi ");
		sql.append("LEFT JOIN(  ");
		//#未来七天转入金额
		sql.append("SELECT riqi,(  ");
		sql.append("SELECT SUM(sum_money) AS sum_money FROM (SELECT DATE_FORMAT(insert_time, '%Y-%m-%d') AS riqi,SUM(in_money) AS sum_money FROM shift_to   ");
		sql.append("GROUP BY DATE_FORMAT(insert_time, '%Y-%m-%d'))tab1  ");
		sql.append("WHERE tab1.riqi BETWEEN tab2.riqi AND DATE_SUB(tab2.riqi,INTERVAL -6 DAY)  ");
		sql.append(") AS in_money_7_day FROM (SELECT DATE_FORMAT(insert_time, '%Y-%m-%d') AS riqi,SUM(in_money) AS sum_money FROM shift_to  ");
		sql.append("GROUP BY DATE_FORMAT(insert_time, '%Y-%m-%d'))tab2  ");
		sql.append(") in_money_7_day_tab ON riqis.riqi=in_money_7_day_tab.riqi  ");
		sql.append(" where riqis.riqi between '"+beginDate+"' and '"+endDate+"' ");
		sql.append("ORDER BY riqis.riqi desc");
		sql.append(")tab limit "+((pageUtil.getCurrentPage()-1)*pageUtil.getPageSize())+","+(pageUtil.getPageSize()+14));
		return sql;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void coinPurseDetailReport(PageUtil<CoinPurseReport> pageUtil,String insertTime,String username){
		Calendar calendar = Calendar.getInstance();
		String carrentDate = calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH);
		if(insertTime==null || "".equals(insertTime)){
			insertTime = carrentDate;
		}
		List list = dao.LoadAllSql(createCoinPurseDetailReportSql(insertTime,username,pageUtil).toString(), null);
		List<CoinPurseReport> dataList = new ArrayList<CoinPurseReport>();
		CoinPurseReport vo = null;
		Object[] obj = null;
		for(int i=0;i<list.size();i++){
			obj = (Object[])list.get(i);
			vo = new CoinPurseReport();
			vo.setId(i+1);
			vo.setMobileNum(obj[0]==null ? "" : DESEncrypt.jieMiUsername(obj[0].toString()));
			vo.setInTime(obj[1]==null ? "" : obj[1].toString());
			vo.setInMoney(obj[2]==null ? "" : new BigDecimal(obj[2].toString()).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());
			vo.setOutTime(obj[3]==null ? "" : obj[3].toString());
			vo.setOutMoney(obj[4]==null ? "" : new BigDecimal(obj[4].toString()).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());
			vo.setShouyiTime(obj[5]==null ? "" : obj[5].toString());
			vo.setShouyiMoney(obj[6]==null ? "" : new BigDecimal(obj[6].toString()).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());
			dataList.add(vo);
		}
		pageUtil.setList(dataList);
		String countsql = "select count(*) from(SELECT users.username,users_id FROM (SELECT DISTINCT users_id FROM coin_purse_funds_record where DATE_FORMAT(insert_time, '%Y-%m-%d')='"+insertTime+"') tt LEFT JOIN users ON users.id=tt.users_id ";
		if(username!=null && !username.equals("")){
			countsql = countsql + " where username='"+DESEncrypt.jiaMiUsername(username)+"'";
		}
		countsql = countsql + ") tab";
		int count = Integer.valueOf(dao.LoadAllSql(countsql, null).get(0).toString());
		if(count%pageUtil.getPageSize()==0){
			pageUtil.setPageCount(count/pageUtil.getPageSize());
		}else {
			pageUtil.setPageCount((count/pageUtil.getPageSize())+1);
		}
		List listCount = new ArrayList();
		for(int i=1;i<=pageUtil.getPageCount();i++){
			listCount.add(i);
		}
		pageUtil.setListCount(listCount);
	}
	
	/**
	 * 零钱包明细sql
	 * @param insertTime
	 * @param pageUtil
	 * @return
	 */
	public StringBuffer createCoinPurseDetailReportSql(String insertTime,String username,PageUtil<CoinPurseReport> pageUtil){
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ( ");
		sql.append("SELECT username,in_time,in_money*0.01,out_time,out_money*0.01,shouyi_time,shouyi_money*0.01 FROM ( ");
		sql.append("SELECT DISTINCT users_id FROM coin_purse_funds_record WHERE DATE_FORMAT(insert_time, '%Y-%m-%d')='"+insertTime+"' ");
		sql.append(") us LEFT JOIN users u ON us.users_id=u.id ");
		sql.append("LEFT JOIN ( ");
		sql.append("SELECT insert_time AS in_time,money AS in_money,users_id FROM coin_purse_funds_record WHERE STATUS='0' AND TYPE='to' AND DATE_FORMAT(insert_time, '%Y-%m-%d')='"+insertTime+"' ");
		sql.append(") in_money_tab ON us.users_id=in_money_tab.users_id  ");
		sql.append("LEFT JOIN ( ");
		sql.append("SELECT insert_time AS out_time,users_id,SUM(money) AS out_money FROM coin_purse_funds_record WHERE STATUS='0' AND TYPE='out' AND DATE_FORMAT(insert_time, '%Y-%m-%d')='"+insertTime+"' GROUP BY users_id ");
		sql.append(") out_money_tab ON us.users_id=out_money_tab.users_id  ");
		sql.append("LEFT JOIN ( ");
		sql.append("SELECT insert_time AS shouyi_time,users_id,SUM(money) AS shouyi_money FROM coin_purse_funds_record WHERE STATUS='0' AND TYPE='shouyi' AND DATE_FORMAT(insert_time, '%Y-%m-%d')='"+insertTime+"' GROUP BY users_id ");
		sql.append(") shouyi_money_tab ON us.users_id=shouyi_money_tab.users_id ");
		if(username!=null && !username.equals("")){
			sql.append(" where username='"+DESEncrypt.jiaMiUsername(username)+"'");
		}
		sql.append(")tab limit "+((pageUtil.getCurrentPage()-1)*pageUtil.getPageSize())+","+pageUtil.getPageSize());
		return sql;
	}
	
	public int queryCount(String beginDate,String endDate){
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from weeks where 1=1 ");
		if(!QwyUtil.isNullAndEmpty(beginDate) && !QwyUtil.isNullAndEmpty(endDate)){
			sql.append("and insert_time between '"+beginDate+"' and '"+endDate+"' ");
		}
		return Integer.valueOf(dao.LoadAllSql(sql.toString(), null).get(0).toString());
	}
	
	@SuppressWarnings({ "rawtypes"})
	public StringBuffer lqbqxt(String insertDate){
		StringBuffer inSql = new StringBuffer();
		StringBuffer outSql = new StringBuffer();
		inSql.append("SELECT DATE_FORMAT(insert_time, '%Y-%m-%d %H') AS shijian,SUM(money) AS in_money FROM coin_purse_funds_record WHERE DATE_FORMAT(insert_time, '%Y-%m-%d')='"+insertDate+"' and STATUS='0' AND TYPE='to' GROUP BY DATE_FORMAT(insert_time, '%Y-%m-%d %H') ORDER BY DATE_FORMAT(insert_time, '%Y-%m-%d %H')");
		outSql.append("SELECT DATE_FORMAT(insert_time, '%Y-%m-%d %H') AS shijian,SUM(money) AS out_money FROM coin_purse_funds_record WHERE DATE_FORMAT(insert_time, '%Y-%m-%d')='"+insertDate+"' and STATUS='0' AND TYPE='out' GROUP BY DATE_FORMAT(insert_time, '%Y-%m-%d %H') ORDER BY DATE_FORMAT(insert_time, '%Y-%m-%d %H')");
		List inList = dao.LoadAllSql(inSql.toString(), null);
		List outList = dao.LoadAllSql(outSql.toString(), null);
		StringBuffer inJson = new StringBuffer();
		StringBuffer outJson = new StringBuffer();
		inJson.append("[");
		Object[] objData = null;
		int hour;
		int index = 0;
		for(int i=0;i<24;i++){
			objData = (Object[])inList.get(index);
			hour = Integer.valueOf(objData[0].toString().split(" ")[1]);
			if(hour==i){
				if(i==23){
					inJson.append(new BigDecimal(Double.valueOf(objData[1].toString())*0.01).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());
				}else {
					inJson.append(new BigDecimal(Double.valueOf(objData[1].toString())*0.01).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()+",");
				}
				if(inList.size()!=index+1){
					index++;
				}
			}else {
				if(i==23){
					inJson.append(0);
				}else {
					inJson.append(0+",");
				}
			}
		}
		inJson.append("]");
		outJson.append("[");
		index = 0;
		for(int i=0;i<24;i++){
			objData = (Object[])outList.get(index);
			hour = Integer.valueOf(objData[0].toString().split(" ")[1]);
			if(hour==i){
				if(i==23){
					outJson.append(new BigDecimal(Double.valueOf(objData[1].toString())*0.01).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());
				}else {
					outJson.append(new BigDecimal(Double.valueOf(objData[1].toString())*0.01).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()+",");
					
				}
				if(outList.size()!=index+1){
					index++;
				}
			}else {
				if(i==23){
					outJson.append(0);
				}else {
					outJson.append(0+",");
				}
			}
		}
		outJson.append("]");
		StringBuffer json = new StringBuffer();
		json.append("{");
		json.append("\"inMoney\":"+inJson+",");
		json.append("\"outMoney\":"+outJson);
		json.append("}");
		return json;
	}
}
