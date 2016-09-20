package com.zfgt.account.bean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.account.dao.BuyCarDAO;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.InterestDetails;
import com.zfgt.orm.Investors;
import com.zfgt.orm.Product;

/**购物车Bean层
 * @author qwy
 *
 * @createTime 2015-04-20 19:44:01
 */
@Service
public class BuyCarBean {

	
	private static Logger log = Logger.getLogger(BuyCarBean.class); 
	@Resource
	private BuyCarDAO dao;
	
	
	/**获取投资列表;
	 * @param usersId 当前登录的用户id
	 * @param pageUtil 投资列表的分页对象;
	 * @param status 状态 "'0','1','2','3'"
	 * @return PageUtil
	 */
	@SuppressWarnings("unchecked")
	public PageUtil<Investors> getInvestorsByPageUtil(long usersId,PageUtil<Investors> pageUtil,String[] status){
		log.info("BuyCarBean.getInvestorsByPageUtil_____");
		String myStatus = "'0','1','2','3'"; 
		if(!QwyUtil.isNullAndEmpty(status)){
			myStatus = QwyUtil.packString(status);
		}
		StringBuffer buff = new StringBuffer();
		buff.append("FROM Investors inv ");
		buff.append("WHERE inv.investorStatus IN ("+myStatus+") ");
		buff.append("AND inv.usersId = ? ");
		buff.append("ORDER BY inv.insertTime DESC, inv.id DESC");
		return (PageUtil<Investors>)dao.getPage(pageUtil, buff.toString(), new Object[]{usersId});
	}
	
	
	/**添加投资列表;
	 * @param inv Investor
	 */
	public String saveObject(Object obj){
		log.info("BuyCarBean.saveObject");
		try {
			if(obj==null){
				return "";
			}
		return	dao.save(obj);
		} catch (Exception e) {
			log.error("操作异常: ",e);
			log.error("BuyCarBean.saveInvestors.exception: ",e);
		}
		return "";
	}
	
	/**生成新的投资列表;0状态;(为保存到数据库)没有ID
	 * @param copies 购买份数
	 * @param inMoney 自己支付的金额(分)
	 * @param coupon 投资券金额(分)
	 * @param pro Product
	 * @param usresId Users.id
	 * @return Investors
	 */
	public Investors createNewInvestors(long copies,double inMoney,Double coupon,Product pro,long usresId){
		Investors inv =null; 
		try {
			if(copies<0 || pro==null)
				return inv;
			inv = new Investors();
			inv.setPayInterestWay(pro.getPayInterestWay());
			inv.setCalcInterestWay(pro.getCalcInterestWay());
			inv.setUsersId(usresId);
			inv.setAnnualEarnings(pro.getAnnualEarnings());
			inv.setInvestorStatus("0");
			inv.setInvestorType("0");
			inv.setCopies(copies);
			inv.setCoupon(coupon);
			//double inMoney = QwyUtil.calcNumber(inv.getCopies()==null?0l:inv.getCopies(), 100, "*").doubleValue();
			//QwyUtil.calcNumber(inMoney, QwyUtil.isNullAndEmpty(coupon)?0:coupon, "-").doubleValue()
			//自己投入的金额,进去投资券
			inv.setInMoney(inMoney);
			inv.setInsertTime(new Date());
			String way = inv.getCalcInterestWay();
			if("0".equals(way)){
				//T+0的计息方式;
				inv.setStartTime(inv.getInsertTime());

			}else if("1".equals(way)){
				//T+1的计息方式;
				inv.setStartTime(QwyUtil.addDaysFromOldDate(inv.getInsertTime(), 1).getTime());
			}
			inv.setUpdateTime(inv.getInsertTime());
			inv.setProductId(pro.getId());
		} catch (Exception e) {
			log.error("操作异常: ",e);
			log.error("BuyCarBean.saveInvestors,inv: "+inv,e);
		}
		return inv;
	}
	
	/**删除投资列表;
	 * @param invId
	 */
	public void deleteInvestors(String invId){
		StringBuffer buff = new StringBuffer();
		buff.append("FROM  Investors inv ");
		buff.append("WHERE inv.investorStatus = '0' ");
		buff.append("AND inv.id = ? ");
		Investors investor = (Investors)dao.findJoinActive(buff.toString(), new Object[]{invId});
		if(!QwyUtil.isNullAndEmpty(investor)){
			investor.setInvestorStatus("4");
			investor.setUpdateTime(new Date());
			dao.saveOrUpdate(investor);
		}
	}
	
	/**根据用户id查找投资列表的总数量;(0)状态下
	 * @param usersId 用户id
	 * @return 购物车的总数量
	 */
	public int getInvestorsCount(long usersId){
		StringBuffer hql = new StringBuffer();
		hql.append("FROM Investors inv ");
		hql.append("WHERE inv.usersId = ? ");
		hql.append("AND inv.investorStatus = ? ");
		int num = dao.findCountByHql(hql.toString(), new Object[]{usersId,"0"});
		return num>0?num:0;
	}
	
	
	/**确认投资;生成利息详细表;--常规产品;
	 * @param inv
	 * @param product
	 * @return
	 */
	public List<InterestDetails> confirmInvest(Investors inv, Product product){
		//付息方式; 0: 按月付息到期还本    1:到期还本付息
		log.info("BuyCarBean.confirmInvest__确认投资,生成利息表");
		int hbDay = 15;
		try {
			if(QwyUtil.isNullAndEmpty(product) || QwyUtil.isNullAndEmpty(inv)){
				return null;
			}
			if("2".equals(product.getProductType())){
				return confirmInvestFunds(inv, product);
			}
			List<InterestDetails> listInterest = new ArrayList<InterestDetails>();
			String status = product.getPayInterestWay();
			Double expectEarnings = 0d;
			if("0".equals(status)){
				//按月付息到期还本 多条利息记录; 前面的本金为0;最后一条的本金为投资金额;
				log.info("BuyCarBean.confirmInvest__按月付息到期还本");
				Calendar qxCalendar = Calendar.getInstance();
				qxCalendar.setTime(inv.getStartTime());
				int qxMonth = qxCalendar.get(Calendar.MONTH);
				int qxYear = qxCalendar.get(Calendar.YEAR);
				Calendar finishCalendar = Calendar.getInstance();
				finishCalendar.setTime(product.getFinishTime());
				int finishMonth = finishCalendar.get(Calendar.MONTH);
				int finishYear = finishCalendar.get(Calendar.YEAR);
				if(finishYear-qxYear>0){
					finishMonth+=12*(finishYear-qxYear);
				}
				Date finishDate = QwyUtil.fmyyyyMMdd.parse(QwyUtil.fmyyyyMMdd.format(finishCalendar.getTime()));
				Date lastJxKssj = null;//上次计息开始时间;
				int finishDay = finishCalendar.get(Calendar.DATE);
				if(hbDay<finishDay){
					finishMonth=finishMonth+1;
				}
				//设置每个月的回报时间
				Calendar hbCalendar = Calendar.getInstance();
				hbCalendar.set(Calendar.MONTH, qxMonth);
				hbCalendar.set(Calendar.DAY_OF_MONTH, hbDay);
				long ordersNum = 0;
				for (int i = qxMonth; i <= finishMonth; i++) {
					if(lastJxKssj==null){
						lastJxKssj = inv.getStartTime();//第一次的时候,用起息时间来计算;
					}
					//回报日期
					Date hbDate = QwyUtil.fmyyyyMMdd.parse(QwyUtil.fmyyyyMMdd.format(hbCalendar.getTime()));
					Date qxDate = QwyUtil.fmyyyyMMdd.parse(QwyUtil.fmyyyyMMdd.format(inv.getStartTime()));
					InterestDetails interest = new InterestDetails();
					interest.setStatus("0");
					interest.setProductId(product.getId());
					interest.setUsersId(inv.getUsersId());
					interest.setInsertTime(new Date());
					interest.setPayMoney(0D);
					interest.setInvestorsId(inv.getId());
					if(!(hbDate.getTime()>=qxDate.getTime())){
						//回报时间小于起息时间;
						System.out.println("回报时间小于起息时间");
						hbCalendar.set(Calendar.MONTH, hbCalendar.get(Calendar.MONTH)+1);
						hbDate = QwyUtil.fmyyyyMMdd.parse(QwyUtil.fmyyyyMMdd.format(hbCalendar.getTime()));
					}
					if(hbDate.getTime()>=finishDate.getTime()){
						//最后一条利息记录;要归还本金;回报时间,就是项目结束时间;
						hbDate = finishDate;
						Calendar newHbDate = Calendar.getInstance();
						newHbDate.setTime(finishDate);
						newHbDate.add(Calendar.DATE, 1);
						hbDate = newHbDate.getTime();
						interest.setPayMoney(Double.parseDouble(inv.getInMoney().toString()));//金额的单位(分)
						i = finishMonth;//已经为最后一条记录;
					}
					
					interest.setReturnTime(QwyUtil.fmyyyyMMdd.parse(QwyUtil.fmyyyyMMdd.format(hbDate)));
					int payDay = QwyUtil.getDifferDays(lastJxKssj, interest.getReturnTime());
					//算好了本次的回报信息之后,把当前的回报时间,作为下一次的起息时间;
					interest.setInvestDay(Long.parseLong(product.getTzqx()+""));
					//投资天数减1,是为了不包含后面日期的天数;
					interest.setPayDay(Long.parseLong((payDay-1)+""));
					interest.setStartTime(inv.getStartTime());
					interest.setFinishTime(product.getFinishTime());
					interest.setInMoney(Double.parseDouble(inv.getInMoney().toString()));//金额的单位(分)
					interest.setCopies(inv.getCopies());
					//*****计算利息******/
					double tzje =  QwyUtil.calcNumber(interest.getCopies()*100, inv.getAnnualEarnings()*0.01, "*").doubleValue();
					double nhsy = QwyUtil.calcNumber(tzje, 365, "/", 7).doubleValue();
					//按月分期;每个月的投资天数不一样;
					double payInterest = QwyUtil.calcNumber(nhsy, interest.getPayDay(), "*").doubleValue();//算出来的结果是(分)为单位;
					interest.setPayInterest(QwyUtil.jieQuFa(payInterest, 0));
					//*****END******/
					ordersNum++;
					interest.setOrders(ordersNum);
					listInterest.add(interest);
					Calendar lastCalendar = Calendar.getInstance();
					lastCalendar.setTime(interest.getReturnTime());
					//修改于2015-06-10 22:19:49 回报日期当天不算收益;
					//lastCalendar.set(Calendar.DAY_OF_MONTH, lastCalendar.get(Calendar.DAY_OF_MONTH)+1);
					lastJxKssj = lastCalendar.getTime();
					
					//证明这个月的还款日已经过了;本月的回报时间已经过了,累积到下个月回报;
					hbCalendar.set(Calendar.MONTH, hbCalendar.get(Calendar.MONTH)+1);
				}
				
				for (InterestDetails inte : listInterest) {
					expectEarnings = QwyUtil.calcNumber(inte.getPayInterest(), expectEarnings, "+").doubleValue();
				}
				inv.setExpectEarnings(expectEarnings);
				
			}else if("1".equals(status)){
				log.info("BuyCarBean.confirmInvest__到期还本付息");
				//1:到期还本付息;一条付息记录; 支付利息=投资金额*年化收益/365*投资天数;
				//例如: 投资金额1000;投资30天;年化收益12%; 则: 1000*0.12/365*30=9.863014
				
				InterestDetails interest = new InterestDetails();
				interest.setStatus("0");
				interest.setInvestorsId(inv.getId());
				interest.setProductId(product.getId());
				interest.setUsersId(inv.getUsersId());
				interest.setInMoney(Double.parseDouble(inv.getInMoney().toString()));//金额的单位(分)
				interest.setInvestDay(Long.parseLong(product.getTzqx()+""));
				interest.setInsertTime(new Date());
				interest.setStartTime(inv.getStartTime());
				
				Calendar newFinishTime = Calendar.getInstance();
				newFinishTime.setTime(product.getFinishTime());
				newFinishTime.add(Calendar.DATE, 1);
				Date newFinishTime2 = QwyUtil.fmyyyyMMdd.parse(QwyUtil.fmyyyyMMdd.format(newFinishTime.getTime()));
				interest.setFinishTime(product.getFinishTime());
				interest.setReturnTime(newFinishTime2);
//				if("1".equals(product.getProductType())){
//					//新手项目;投入为固定的100元;
//					interest.setPayMoney(10000D);
//					inv.setInMoney(10000D);
//				}else{
//					interest.setPayMoney(interest.getInMoney());
//				}
				interest.setPayMoney(interest.getInMoney());
				interest.setPayDay(interest.getInvestDay());
				interest.setCopies(inv.getCopies());
				interest.setOrders(1L);
				double tzje =  QwyUtil.calcNumber(interest.getCopies()*100, inv.getAnnualEarnings()*0.01, "*").doubleValue();
				double nhsy = QwyUtil.calcNumber(tzje, 365, "/", 7).doubleValue();
				double payInterest = QwyUtil.calcNumber(nhsy, interest.getInvestDay(), "*").doubleValue();//算出来的结果是(分)为单位;
				interest.setPayInterest(QwyUtil.jieQuFa(payInterest, 0));
				inv.setExpectEarnings(interest.getPayInterest());//预收益总额;单位(分)
				listInterest.add(interest);
			}
			return listInterest;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
		
		return null;
	}
	
	
	/**确认投资;生成利息详细表;--基金产品
	 * @param inv
	 * @param product
	 * @return
	 */
	public List<InterestDetails> confirmInvestFunds(Investors inv, Product product){
		//付息方式; 0: 按月付息到期还本    1:到期还本付息
		log.info("BuyCarBean.confirmInvestFunds____基金确认投资,生成利息表");
		int hbDay = 15;
		try {
			if(QwyUtil.isNullAndEmpty(product) || QwyUtil.isNullAndEmpty(inv)){
				return null;
			}
			if(!"2".equals(product.getProductType())){
				log.info("BuyCarBean.confirmInvestFunds__非基金产品,不能生成基金利息表");
				return null;
			}
			List<InterestDetails> listInterest = new ArrayList<InterestDetails>();
			String status = product.getPayInterestWay();
			Double expectEarnings = 0d;
			if("0".equals(status)){
				//按月付息到期还本 多条利息记录; 前面的本金为0;最后一条的本金为投资金额;
				log.info("BuyCarBean.confirmInvest__按月付息到期还本");
				Calendar qxCalendar = Calendar.getInstance();
				qxCalendar.setTime(inv.getStartTime());
				int qxMonth = qxCalendar.get(Calendar.MONTH);
				int qxYear = qxCalendar.get(Calendar.YEAR);
				Calendar finishCalendar = Calendar.getInstance();
				finishCalendar.setTime(product.getFinishTime());
				int finishMonth = finishCalendar.get(Calendar.MONTH);
				int finishYear = finishCalendar.get(Calendar.YEAR);
				if(finishYear-qxYear>0){
					finishMonth+=12*(finishYear-qxYear);
				}
				Date finishDate = QwyUtil.fmyyyyMMdd.parse(QwyUtil.fmyyyyMMdd.format(finishCalendar.getTime()));
				Date lastJxKssj = null;//上次计息开始时间;
				int finishDay = finishCalendar.get(Calendar.DATE);
				if(hbDay<finishDay){
					finishMonth=finishMonth+1;
				}
				//设置每个月的回报时间
				Calendar hbCalendar = Calendar.getInstance();
				hbCalendar.set(Calendar.MONTH, qxMonth);
				hbCalendar.set(Calendar.DAY_OF_MONTH, hbDay);
				long ordersNum = 0;
				for (int i = qxMonth; i <= finishMonth; i++) {
					if(lastJxKssj==null){
						lastJxKssj = inv.getStartTime();//第一次的时候,用起息时间来计算;
					}
					//回报日期
					Date hbDate = QwyUtil.fmyyyyMMdd.parse(QwyUtil.fmyyyyMMdd.format(hbCalendar.getTime()));
					Date qxDate = QwyUtil.fmyyyyMMdd.parse(QwyUtil.fmyyyyMMdd.format(inv.getStartTime()));
					InterestDetails interest = new InterestDetails();
					interest.setStatus("0");
					interest.setProductId(product.getId());
					interest.setUsersId(inv.getUsersId());
					interest.setInsertTime(new Date());
					interest.setPayMoney(0D);
					interest.setInvestorsId(inv.getId());
					if(!(hbDate.getTime()>=qxDate.getTime())){
						//回报时间小于起息时间;
						System.out.println("回报时间小于起息时间");
						hbCalendar.set(Calendar.MONTH, hbCalendar.get(Calendar.MONTH)+1);
						hbDate = QwyUtil.fmyyyyMMdd.parse(QwyUtil.fmyyyyMMdd.format(hbCalendar.getTime()));
					}
					if(hbDate.getTime()>=finishDate.getTime()){
						//最后一条利息记录;要归还本金;回报时间,就是项目结束时间;
						hbDate = finishDate;
						Calendar newHbDate = Calendar.getInstance();
						newHbDate.setTime(finishDate);
						newHbDate.add(Calendar.DATE, 1);
						hbDate = newHbDate.getTime();
						interest.setPayMoney(Double.parseDouble(inv.getInMoney().toString()));//金额的单位(分)
						i = finishMonth;//已经为最后一条记录;
					}
					
					interest.setReturnTime(QwyUtil.fmyyyyMMdd.parse(QwyUtil.fmyyyyMMdd.format(hbDate)));
					int payDay = QwyUtil.getDifferDays(lastJxKssj, interest.getReturnTime());
					//算好了本次的回报信息之后,把当前的回报时间,作为下一次的起息时间;
					interest.setInvestDay(Long.parseLong(product.getTzqx()+""));
					//投资天数减1,是为了不包含后面日期的天数;
					interest.setPayDay(Long.parseLong((payDay-1)+""));
					interest.setStartTime(inv.getStartTime());
					interest.setFinishTime(product.getFinishTime());
					interest.setInMoney(Double.parseDouble(inv.getInMoney().toString()));//金额的单位(分)
					interest.setCopies(inv.getCopies());
					//*****计算利息******/
					double tzje =  QwyUtil.calcNumber(interest.getCopies()*100, inv.getAnnualEarnings()*0.01, "*").doubleValue();
					double nhsy = QwyUtil.calcNumber(tzje, 365, "/", 7).doubleValue();
					//按月分期;每个月的投资天数不一样;
					double payInterest = QwyUtil.calcNumber(nhsy, interest.getPayDay(), "*").doubleValue();//算出来的结果是(分)为单位;
					interest.setPayInterest(QwyUtil.jieQuFa(payInterest, 0));
					//*****END******/
					ordersNum++;
					interest.setOrders(ordersNum);
					listInterest.add(interest);
					Calendar lastCalendar = Calendar.getInstance();
					lastCalendar.setTime(interest.getReturnTime());
					//修改于2015-06-10 22:19:49 回报日期当天不算收益;
					//lastCalendar.set(Calendar.DAY_OF_MONTH, lastCalendar.get(Calendar.DAY_OF_MONTH)+1);
					lastJxKssj = lastCalendar.getTime();
					
					//证明这个月的还款日已经过了;本月的回报时间已经过了,累积到下个月回报;
					hbCalendar.set(Calendar.MONTH, hbCalendar.get(Calendar.MONTH)+1);
				}
				
				for (InterestDetails inte : listInterest) {
					expectEarnings = QwyUtil.calcNumber(inte.getPayInterest(), expectEarnings, "+").doubleValue();
				}
				inv.setExpectEarnings(expectEarnings);
				
			}else if("1".equals(status)){
				log.info("BuyCarBean.confirmInvest__到期还本付息");
				//1:到期还本付息;一条付息记录; 支付利息=投资金额*年化收益/365*投资天数;
				//例如: 投资金额1000;投资30天;年化收益12%; 则: 1000*0.12/365*30=9.863014
				
				InterestDetails interest = new InterestDetails();
				interest.setStatus("0");
				interest.setInvestorsId(inv.getId());
				interest.setProductId(product.getId());
				interest.setUsersId(inv.getUsersId());
				interest.setInMoney(Double.parseDouble(inv.getInMoney().toString()));//金额的单位(分)
				interest.setInvestDay(Long.parseLong(product.getTzqx()+""));
				interest.setInsertTime(new Date());
				interest.setStartTime(inv.getStartTime());
				
				Calendar newFinishTime = Calendar.getInstance();
				newFinishTime.setTime(product.getFinishTime());
				newFinishTime.add(Calendar.DATE, 1);
				Date newFinishTime2 = QwyUtil.fmyyyyMMdd.parse(QwyUtil.fmyyyyMMdd.format(newFinishTime.getTime()));
				interest.setFinishTime(product.getFinishTime());
				interest.setReturnTime(newFinishTime2);
//				if("1".equals(product.getProductType())){
//					//新手项目;投入为固定的100元;
//					interest.setPayMoney(10000D);
//					inv.setInMoney(10000D);
//				}else{
//					interest.setPayMoney(interest.getInMoney());
//				}
				interest.setPayMoney(interest.getInMoney());
				interest.setPayDay(interest.getInvestDay());
				interest.setCopies(inv.getCopies());
				interest.setOrders(1L);
				double tzje =  QwyUtil.calcNumber(interest.getCopies()*100, inv.getAnnualEarnings()*0.01, "*").doubleValue();
				double nhsy = QwyUtil.calcNumber(tzje, 365, "/", 7).doubleValue();
				double payInterest = QwyUtil.calcNumber(nhsy, interest.getInvestDay(), "*").doubleValue();//算出来的结果是(分)为单位;
				interest.setPayInterest(QwyUtil.jieQuFa(payInterest, 0));
				inv.setExpectEarnings(interest.getPayInterest());//预收益总额;单位(分)
				listInterest.add(interest);
			}else if("3".equals(status)){
				//按年付息到期还本 多条利息记录; 前面的本金为0;最后一条的本金为投资金额;
				log.info("BuyCarBean.confirmInvest__按年付息到期还本");
				//起息时间
				Calendar qxCalendar = Calendar.getInstance();
				qxCalendar.setTime(inv.getStartTime());
				int qxYear = qxCalendar.get(Calendar.YEAR);
				//项目结束时间;
				Calendar finishCalendar = Calendar.getInstance();
				finishCalendar.setTime(product.getFinishTime());
				int finishYear = finishCalendar.get(Calendar.YEAR);
				Date finishDate = QwyUtil.fmyyyyMMdd.parse(QwyUtil.fmyyyyMMdd.format(product.getFinishTime()));
				//设置每个月的回报时间
				Calendar hbCalendar = Calendar.getInstance();
				long ordersNum = 0;
				Date lastJxKssj = null;//上次计息开始时间;
				for (int i = qxYear; i <= finishYear; i++) {
					if(lastJxKssj==null){
						lastJxKssj = inv.getStartTime();//第一次的时候,用起息时间来计算;
					}
					//利息表
					InterestDetails interest = new InterestDetails();
					interest.setStatus("0");
					interest.setProductId(product.getId());
					interest.setUsersId(inv.getUsersId());
					interest.setInsertTime(new Date());
					interest.setPayMoney(0D);
					interest.setInvestorsId(inv.getId());
					//按年付息.顺延年;
					Date date = QwyUtil.addDaysFromOldDate(lastJxKssj, 365).getTime();
					//回报日期
					Date hbDate = QwyUtil.fmyyyyMMdd.parse(QwyUtil.fmyyyyMMdd.format(date));
					hbCalendar.setTime(hbDate);
					if(hbCalendar.getTime().getTime()>finishDate.getTime()){
						hbCalendar.setTime(finishDate);
					}
					if(hbDate.getTime()>=finishDate.getTime()){
						//最后一条利息记录;要归还本金;回报时间,就是项目结束时间;
						hbDate = finishDate;
						Calendar newHbDate = Calendar.getInstance();
						newHbDate.setTime(finishDate);
						newHbDate.add(Calendar.DATE, 1);
						hbDate = newHbDate.getTime();
						hbCalendar.setTime(hbDate);
						interest.setPayMoney(Double.parseDouble(inv.getInMoney().toString()));//金额的单位(分)
						i = finishYear;//已经为最后一条记录;
					}
					interest.setReturnTime(hbCalendar.getTime());
					int payDay = QwyUtil.getDifferDays(lastJxKssj, interest.getReturnTime());
					//算好了本次的回报信息之后,把当前的回报时间,作为下一次的起息时间;
					interest.setInvestDay(Long.parseLong(product.getTzqx()+""));
					//投资天数减1,是为了不包含后面日期的天数;
					interest.setPayDay(Long.parseLong((payDay-1)+""));
					interest.setStartTime(inv.getStartTime());
					interest.setFinishTime(product.getFinishTime());
					interest.setInMoney(Double.parseDouble(inv.getInMoney().toString()));//金额的单位(分)
					interest.setCopies(inv.getCopies());
					//*****计算利息******/
					double tzje =  QwyUtil.calcNumber(interest.getCopies()*100, inv.getAnnualEarnings()*0.01, "*").doubleValue();
					double nhsy = QwyUtil.calcNumber(tzje, 365, "/", 7).doubleValue();
					//按月分期;每个月的投资天数不一样;
					double payInterest = QwyUtil.calcNumber(nhsy, interest.getPayDay(), "*").doubleValue();//算出来的结果是(分)为单位;
					interest.setPayInterest(QwyUtil.jieQuFa(payInterest, 0));
					//*****END******/
					ordersNum++;
					interest.setOrders(ordersNum);
					listInterest.add(interest);
					lastJxKssj = interest.getReturnTime();
					//证明这个月的还款日已经过了;本月的回报时间已经过了,累积到下个月回报;
					hbCalendar.set(Calendar.YEAR, hbCalendar.get(Calendar.YEAR)+1);
				}
				
				for (InterestDetails inte : listInterest) {
					expectEarnings = QwyUtil.calcNumber(inte.getPayInterest(), expectEarnings, "+").doubleValue();
				}
				inv.setExpectEarnings(expectEarnings);
				
			}
			return listInterest;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
		
		return null;
	}
	public static void main(String[] args) {
		System.out.println(QwyUtil.addMonthsFromOldDate(new Date(), 12).getTime());;
		System.out.println(QwyUtil.addDaysFromOldDate(new Date(), 365).getTime());;
		System.out.println(QwyUtil.addDaysFromOldDate(new Date(), 365).getTime());;
	}
	
}
