package com.zfgt.account.bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.alibaba.fastjson.JSONObject;
import com.zfgt.account.dao.ConfirmInvestDAO;
import com.zfgt.common.ApplicationContexts;
import com.zfgt.common.bean.MyWalletBean;
import com.zfgt.common.bean.PlatformBean;
import com.zfgt.common.util.LockHolder;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.Coupon;
import com.zfgt.orm.CouponRecord;
import com.zfgt.orm.InterestDetails;
import com.zfgt.orm.Investors;
import com.zfgt.orm.Product;
import com.zfgt.orm.Users;
import com.zfgt.orm.UsersInfo;

/**确认基金产品的投资
 * @author 覃文勇
 * @createTime 2015-8-31下午3:34:54
 */
@Service
public class ConfirmFundInvestBean {

	
	private static Logger log = Logger.getLogger(ConfirmFundInvestBean.class); 
	@Resource
	private ConfirmInvestDAO dao;
	@Resource
	private MyWalletBean myWalletBean;
	@Resource
	private BuyCarBean buyCarBean;
	@Resource
	private PlatformBean platformBean;
	@Resource
	private UserRechargeBean userRechargeBean;
	/**支付购买;确认支付;<br>基金产品
	 * 
	 * @param usersId 用户id
	 * @param productId 产品id
	 * @param copies 份数(一元一份)
	 * @param inMoney 自己支付的金额(分)
	 * @param coupon 红包金额;单位(分)
	 * @param couponId 红包Id
	 * @return
	 */
		//锁定产品;
	public String confirmPay(long usersId,String productId,long copies,double inMoney,double coupon,String couponId){
		//产品ID+用户ID作为同步锁;
		synchronized (LockHolder.getLock(productId)) {
			log.info("先锁定产品: "+productId);
			synchronized (LockHolder.getLock(usersId)) {
				log.info("再锁定用户: "+usersId);
				if(inMoney<0 || coupon<0){
					return "支付金额有误";
				}
				Users users = (Users)dao.findById(new Users(), usersId);
				UsersInfo usersInfo = users.getUsersInfo();
				Product pro = (Product)dao.findById(new Product(), productId);
				try {
					if("0".equals(pro.getProductType())){
						return "该理财产品为常规产品,请在常规产品区购买";
					}
					if("1".equals(pro.getProductType())){
						return "该理财产品为新手专享,请在新手专享区购买";
					}
					if(!QwyUtil.isNullAndEmpty(pro)){
						Date endTime=pro.getEndTime();
						String endDate=QwyUtil.fmyyyyMMdd.format(endTime);
						if(new Date().getTime()>=QwyUtil.fmyyyyMMdd.parse(endDate).getTime()){
							return "项目到期前3天不能购买!";
						}
					}
					if(copies*100<pro.getQtje()){
						return "投资金额必须大于等于起投金额";
					}
					double copiesMoney = QwyUtil.calcNumber(copies, 100, "*").doubleValue();
					if(copiesMoney>QwyUtil.calcNumber(inMoney, coupon, "+").doubleValue()){
						return "支付金额不足";
					}
					//除去投资券之后,需要支付的金额;
					double newInMoney = QwyUtil.calcNumber(copiesMoney, coupon, "-").doubleValue();
					if(newInMoney>=0 && newInMoney>inMoney){
						return "支付金额不足";
					}
					double myInMoney = newInMoney<0?0:newInMoney;
					Investors inv = buyCarBean.createNewInvestors(copies,myInMoney,coupon,pro,users.getId());
					String id = buyCarBean.saveObject(inv);
					if(QwyUtil.isNullAndEmpty(id))
						return "缺少支付数据";
					String temp = "支付失败";
					//更新理财产品 
					temp = updateFundProduct(pro, copies);
					if(!QwyUtil.isNullAndEmpty(temp)){
						return temp;
					}
					//更新投资列表
					temp = updateFundInvestors(inv,coupon);
					if(!QwyUtil.isNullAndEmpty(temp)){
						return temp;
					}
					//查看用户钱包可用金额
					temp = isEnoughMoney(usersInfo, inv.getInMoney());
					if(!QwyUtil.isNullAndEmpty(temp)){
						return temp;
					}
					
					Coupon cp = null;
					CouponRecord couRecord = null ;
					//是否使用投资券
					if(!QwyUtil.isNullAndEmpty(couponId)){
						//需要扣除的投资券记录
						cp=getCouponById(couponId);
						//投资券使用记录;
						couRecord = new CouponRecord();
						couRecord.setCouponId(cp.getId()); 
						couRecord.setInsertTime(new Date());
						couRecord.setInvestorsId(inv.getId());
						couRecord.setProductId(pro.getId());
						couRecord.setStatus("0");
						couRecord.setUsersId(usersId);
						couRecord.setNote("购买 "+pro.getTitle()+" 理财产品");
						couRecord.setMoney(cp.getInitMoney());
						//修改用户所持的该投资券记录;
						cp.setStatus("2");
						cp.setUpdateTime(new Date());
						cp.setUseTime(new Date());
						cp.setMoney(0d);
					}
					//生成利息表;
					List<InterestDetails> listInterest = buyCarBean.confirmInvest(inv, pro);
					inv.setFinishTime(pro.getFinishTime());
					ApplicationContext context = ApplicationContexts.getContexts();
					PlatformTransactionManager tm = (PlatformTransactionManager) context.getBean("transactionManager");
					TransactionStatus ts = tm.getTransaction(new DefaultTransactionDefinition());
					try {
						String note = "购买 "+pro.getTitle()+" 理财产品用了"+inv.getCopies()+" 元";
						if(coupon>0){
							note+=";其中投资券用了 "+inv.getCoupon()*0.01+" 元;";
						}
						boolean isPay = myWalletBean.buyProduct(usersId, inv.getInMoney(), note);
						if(isPay){
							dao.saveOrUpdate(pro);
							dao.saveOrUpdate(inv);
							dao.saveList(listInterest);
							if(!QwyUtil.isNullAndEmpty(couponId)&&!QwyUtil.isNullAndEmpty(cp)&&!QwyUtil.isNullAndEmpty(couRecord)){
								dao.saveOrUpdate(cp);
								dao.saveOrUpdate(couRecord);
							}
							platformBean.updateCollectMoney(inv.getCopies()*100);
							platformBean.updateUseCoupon(inv.getCoupon());
							tm.commit(ts);
							//是否购买过新手产品
							if("1".equals(pro.getProductType())){
									//用户购买了新手产品;
								users.setBuyFreshmanProduct("1");
								dao.update(users);
							}
						}else{
							tm.rollback(ts);
						}
						return "";
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					tm.rollback(ts);
					return "支付失败";
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return "支付失败";
	}
	/**支付购买;确认支付(涉及银行卡支付 );<br>基金产品
	 * 
	 * @param usersId 用户id
	 * @param productId 产品id
	 * @param copies 份数(一元一份)
	 * @param inMoney 自己支付的金额(分) 
	 * @param coupon 红包金额;单位(分)
	 * @param bankMoney 银行支付的金额;
	 * @param ip 支付者ip
	 * @param couponId 红包Id
	 * @return
	 * @throws Exception
	 */
	public String confirmPay(long usersId,String productId,long copies,double inMoney,double coupon,double bankMoney,String ip,String couponId) throws Exception{
		Product pro = (Product)dao.findById(new Product(), productId);
		if("0".equals(pro.getProductType())){
			return "该理财产品为常规产品,请在常规产品区购买";
		}
		if("1".equals(pro.getProductType())){
			return "该理财产品为新手专享,请在新手专享区购买";
		}
		if(!QwyUtil.isNullAndEmpty(pro)){
			Date endTime=pro.getEndTime();
			String endDate=QwyUtil.fmyyyyMMdd.format(endTime);
			if(new Date().getTime()>=QwyUtil.fmyyyyMMdd.parse(endDate).getTime()){
				return "项目到期前3天不能购买!";
			}
		}
		if(copies*100<pro.getQtje()){
			return "投资金额必须大于等于起投金额";
		}
		//自己支付,投资券,银行支付三个合并加起来;
		double allMoney = QwyUtil.calcNumber(inMoney+bankMoney, coupon, "+").doubleValue();
		if(copies*100>allMoney){
			//支付金额不足;
			return "支付总额不足";
		}
		String json = userRechargeBean.directBindPayToBuyProduct(UUID.randomUUID().toString(), (int)bankMoney, "中泰理财在线充值", "中泰理财在线充值", usersId, "www.baidu.com", ip);
		JSONObject result = JSONObject.parseObject(json.replaceAll("'", "\""));
		String temp = result.getString("status");
		if("ok".equalsIgnoreCase(temp)){
			double newInMoney = QwyUtil.calcNumber(inMoney, bankMoney, "+").doubleValue();
			return confirmPay(usersId, productId, copies,newInMoney , coupon, couponId);
		}else{
			return result.getString("json");
		}
		
	}

	
	/**是否符合支付;返回空字符串,代表符合;
	 * @param users Users
	 * @param usersInfo UsersInfo
	 * @param inv Investors
	 * @param pro Product
	 * @return 返回空字符串,代表符合;
	 * @throws Exception 
	 */
	public String isAccordPay(Users users,UsersInfo usersInfo,Investors inv,Product pro) throws Exception{
		if(null==users || null==inv || null==pro){
			//缺少支付数据
			log.info("缺少支付数据");
			return "缺少支付数据";
		}
		if(!"0".equals(pro.getProductStatus())){
			log.info("支付失败:产品不在营销中;id: "+pro.getId());
			return "支付失败:产品不在营销中";
		}
		if(!"0".equals(inv.getInvestorStatus())){
			log.info("支付失败:非【待付款】状态下不能支付;id: "+inv.getId());
			return "支付失败:非【待付款】下不能支付";
		}
		long leftCopies = pro.getLeftCopies();//剩余份数;1元1份;
		if(inv.getCopies().longValue()>leftCopies){
			//理财产品剩余的余额不足;
			log.info("理财产品余额剩余不足;id: "+pro.getId());
			return "理财产品余额剩余不足";
		}
		//double totalMoney = usersInfo.getTotalMoney();//总资产
		double leftMoney = usersInfo.getLeftMoney();//可用余额
		//购买产品所花的金额;
		double payMoney = inv.getInMoney();
		if(payMoney>leftMoney){
			log.info("用户账户余额不足");
			return "用户账户余额不足";
		}
		return "";
	}
	
	
	
	/**更新理财产品;
	 * @param pro Product
	 * @param copies 购买份数;(一元一份)
	 */
	public String updateFundProduct(Product pro,double copies){
		//更新产品;
		try {
			
			if(QwyUtil.isNullAndEmpty(pro)){
				return "支付失败:缺少支付数据;PRO";
			}
			Product newPro = (Product)dao.findById(new Product(), pro.getId());
			System.out.println("产品剩余余额:"+newPro.getLeftCopies());
			System.out.println("购买产品的份数:"+copies);
			if(!"0".equals(pro.getProductStatus())){
				log.info("支付失败:产品不在营销中;id: "+pro.getId());
				return "支付失败:产品不在营销中";
			}
			
			long leftCopies = newPro.getLeftCopies();//剩余份数;1元1份;
			if(copies>leftCopies){
				//理财产品剩余的余额不足;
				log.info("理财产品余额剩余不足;id: "+pro.getId());
				return "理财产品余额剩余不足";
			}
			long newLeftCopies = QwyUtil.calcNumber(pro.getLeftCopies(), copies, "-").longValue();
			pro.setLeftCopies(newLeftCopies);//剩余未卖出的余额;
			if(newLeftCopies==0){
				pro.setProductStatus("1");
				if("1".equals(pro.getProductType())){
					pro.setClearingTime(new Date());
				}
			}
			long hasCopies = QwyUtil.calcNumber(pro.getHasCopies(), copies, "+").longValue();
			pro.setHasCopies(hasCopies);//已卖出的余额
			long usersCount = pro.getUserCount()==null?1:QwyUtil.calcNumber(pro.getUserCount(), 1,"+").longValue();
			pro.setUserCount(usersCount);//被购买次数;一人可多次购买;
			pro.setUpdateTime(new Date());
			return "";
		} catch (Exception e) {
			log.error("操作异常: ",e);
		}
		return "操作异常";
	}
	
	/**更新投资列表
	 * @param inv 投资列表;
	 * @param coupon 支付购买时,使用的投资券总额;
	 */
	public String updateFundInvestors(Investors inv,double coupon){
		try {
			if(QwyUtil.isNullAndEmpty(inv))
				return "支付失败:缺少支付数据;INV";
			if(!"0".equals(inv.getInvestorStatus())){
				log.info("支付失败:非【0】状态下不能支付;id: "+inv.getId());
				return "支付失败:非【0】下不能支付";
			}
			inv.setInvestorStatus("1");
			double myCoupon = QwyUtil.calcNumber(inv.getCopies()*100, inv.getInMoney(), "-").doubleValue();
			inv.setCoupon(myCoupon);//支付购买时,使用的投资券总额;如果有多的话,则使用购买份数的金额
			inv.setPayTime(new Date());
			inv.setUpdateTime(inv.getPayTime());
			return "";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
		return "支付失败";
	}
	
	
	/**查看用户的钱包可用金额;
	 * @param usersInfo 用户信息表;
	 * @param payMoney 支付金额;
	 * @return 字符串,空字符串代表成功;
	 * @throws Exception
	 */
	public String isEnoughMoney(UsersInfo usersInfo,double payMoney){
		try {
			if(QwyUtil.isNullAndEmpty(usersInfo))
				return "支付失败:缺少支付数据;UIN";
			//用户资金状况;
			double leftMoney = usersInfo.getLeftMoney();//可用余额			
			if(payMoney>leftMoney){
				log.info("用户账户余额不足");
				return "用户账户余额不足";
			}
			return "";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
		return "支付失败";
	}
	
	
	/**根据用户ID,获取用户的可用投资券;<br>
	 * 查找u.status IN ('0','1')的状态;"未使用"和"未用完"的投资券;
	 * @param usersId 用户Id
	 * @param types 类别 如:0:常规投资券; 1:新手投资券;查找全部类型,请写null
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Coupon> getCoupon(long usersId,String[] types){
		StringBuffer hql = new StringBuffer();
		hql.append("FROM Coupon cu ");
		hql.append(" WHERE cu.status IN ('0','1') ");
		hql.append(" AND cu.usersId = ? ");
		if(!QwyUtil.isNullAndEmpty(types)){
			String type = QwyUtil.packString(types);
			hql.append(" AND cu.type IN ("+type+") ");
		}
		hql.append("ORDER BY cu.insertTime ASC");
		return (List<Coupon>)dao.LoadAll(hql.toString(),new Object[]{usersId});
	}
	
	/**
	 * @param listCoupon
	 * @param payCoupon
	 * @return true:有足够的投资券; false:投资券不足;
	 * @throws Exception 
	 */
	public boolean isEnoughCoupon(List<Coupon> listCoupon,double payCoupon) throws Exception{
		if(QwyUtil.isNullAndEmpty(listCoupon)){
			return false;
		}
		double money = 0;
		for (Coupon coupon : listCoupon) {
			money = QwyUtil.calcNumber(coupon.getMoney(), money, "+").doubleValue();
		}
		if(payCoupon>money){
			log.info("个人投资券总额不足");
			return false;
		}
		return true;
	}
/*	*//**
	 * 根据投资情况发送红包
	 * @param userId 用户ID
	 * @param copies 份数
	 * @return 红包
	 *//*
	public Double sendInsCoupon(Long userId,Long copies){
		Double coupon = 0D;
		try {
			if (copies >= 500 && copies < 1000) {
				coupon = 5000D;
			}else if (copies >= 1000 && copies < 3000) {
				coupon = 10000D;
			}else if (copies >= 3000 && copies < 5000) {
				coupon = 30000D;
			}else if (copies >= 5000 && copies < 10000) {
				coupon = 60000D;
			}else if (copies >= 10000) {
				coupon = 100000D;
			}else{
				coupon=0D;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return coupon;
	}*/
	
	/**
	 * 根据Id查询投资券
	 * @param args
	 */
	public Coupon getCouponById(String id) throws Exception{
		if(!QwyUtil.isNullAndEmpty(id)){
			Object object=dao.findById(new Coupon(), id);
			if(!QwyUtil.isNullAndEmpty(object)){
				return (Coupon) object;
			}
		}
		return null;
	}
}
