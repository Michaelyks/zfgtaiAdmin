package com.zfgt.mshop.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.zfgt.account.bean.UserRechargeBean;
import com.zfgt.common.ApplicationContexts;
import com.zfgt.common.bean.PlatformBean;
import com.zfgt.common.bean.SystemConfigBean;
import com.zfgt.common.util.LockHolder;
import com.zfgt.common.util.OrderNumerUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.login.bean.RegisterUserBean;
import com.zfgt.mshop.dao.MCoinIncomeDAO;
import com.zfgt.orm.MCoinPay;
import com.zfgt.orm.MCoinRecord;
import com.zfgt.orm.MProduct;
import com.zfgt.orm.MUsersAddress;
import com.zfgt.orm.SystemConfig;
import com.zfgt.orm.UsersInfo;


/**喵币兑换支付的Bean层;<br>
 * @author 覃文勇
 * @createTime 2015-10-28下午3:49:07
 */
@Service
public class MCoinPayBean {
	private Logger log = Logger.getLogger(MCoinPayBean.class);
	@Resource
	private MCoinIncomeDAO dao;
	@Resource
	private RegisterUserBean registerUserBean;
	@Resource
	private MProductBean mProductBean;
	@Resource
	private SystemConfigBean systemConfigBean;
	@Resource
	private PlatformBean platformBean;
	@Resource
	private MUsersAddressBean mUsersAddressBean;
	@Resource
	private UserRechargeBean userRechargeBean;
	/**
	 * 	喵币兑换支出列表
	 * @param pageSize
	 * @param currentPage
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<MCoinPay> loadMCoinPayList(long uid,String type,String status,Integer pageSize,Integer currentPage)  throws Exception{
		try {
			ArrayList<Object> ob = new ArrayList<Object>();
			StringBuffer buff = new StringBuffer();	
			buff.append("FROM MCoinPay mc where 1=1 ");
			
			if(!QwyUtil.isNullAndEmpty(uid)){
				buff.append("AND mc.usersId=? ");
	            ob.add(uid);	
			}
			if(!QwyUtil.isNullAndEmpty(type)){
				buff.append(" AND mc.type=? ");
	            ob.add(type);	
			}	
			if(!QwyUtil.isNullAndEmpty(status)){
				buff.append(" AND mc.status=? ");
	            ob.add(status);	
			}
			if(QwyUtil.isNullAndEmpty(currentPage)){
				currentPage=1;
			}
			if(QwyUtil.isNullAndEmpty(pageSize)){
				pageSize=20;
			}
			buff.append(" ORDER BY mc.insertTime DESC ");
			return dao.findAdvList(buff.toString(), ob.toArray(), currentPage, pageSize);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
		
	}
	
	/**
	 * 筛选获取喵币兑换列表组
	 */
	public List<Map<String,Object>> filterMCoinPayGroup(List<MCoinPay> mCoinPays)  throws Exception{
		//以能否兑换为key,List为集合
		List<Map<String,Object>> listMap =new ArrayList<>();
		if(!QwyUtil.isNullAndEmpty(mCoinPays)){
			if(mCoinPays==null||mCoinPays.size()==0){
				return null;
			}
			SystemConfig config=systemConfigBean.findSystemConfig();
			for (int i = 0; i < mCoinPays.size(); i++) {
				MCoinPay mCoinPay=mCoinPays.get(i);
				listMap.add(filterMCoinPayList(mCoinPay,config));				
			}
		}
		return listMap;
	}
	
	/**
	 * 筛选获取喵币兑换支出字段
	 */
	public Map<String, Object> filterMCoinPayList(MCoinPay mCoinPay,SystemConfig config)  throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", mCoinPay.getId());
		map.put("coin",mCoinPay.getCoin());
		map.put("type", mCoinPay.getType());
		map.put("copies", mCoinPay.getCopies());
		map.put("insertTime", mCoinPay.getInsertTime());
		map.put("note", mCoinPay.getNote());
		if(!QwyUtil.isNullAndEmpty(mCoinPay.getMProduct())){
			map.put("productId", mCoinPay.getMProduct().getId());
			map.put("vip",mCoinPay.getMProduct().getVip());
			map.put("title",mCoinPay.getMProduct().getTitle());
			map.put("img",config.getHttpUrl()+config.getFileName()+"/mobile_img/mShop/"+mCoinPay.getMProduct().getImg());
			map.put("price",mCoinPay.getMProduct().getPrice());
		}else{
			map.put("productId", "");
			map.put("title","");
			map.put("img","");
			map.put("price","");
		}
		return map;
	}
	/**
	 * 保存喵商品兑换
	 * @param uid 用户id
	 * @param mProduct 喵商品
	 * @param copies  购买份数
	 * @param coin  兑换喵币
	 * @param pageSize 当前页数
	 * @param currentPage 当前页
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object>  SaveMCoinPay(long uid,MProduct mProduct,long copies,long coin,Long mUsersAddressId)  throws Exception{
		log.info("MCoinPayBean.SaveMCoinPay 进入喵币兑换");
		ApplicationContext context = ApplicationContexts.getContexts();
		PlatformTransactionManager tm = (PlatformTransactionManager) context.getBean("transactionManager");
		TransactionStatus ts = tm.getTransaction(new DefaultTransactionDefinition());
		synchronized (LockHolder.getLock(mProduct.getId())) {
			log.info("先锁定产品: "+mProduct.getId());
			synchronized (LockHolder.getLock(uid)) {
				log.info("再锁定用户: "+uid);
			try {
				MCoinPay mCoinPay=new MCoinPay();
				mCoinPay.setCoin(coin);
				mCoinPay.setRecordNumber(OrderNumerUtil.generateRequestId());//生成流水号
				mCoinPay.setCopies(copies);
				mCoinPay.setMProductId(mProduct.getId());
				mCoinPay.setUsersId(uid);
				mCoinPay.setNote("购买商品");
				mCoinPay.setStatus("0");
				mCoinPay.setInsertTime(new Date());
				mCoinPay.setType(mProduct.getType());
				if(!QwyUtil.isNullAndEmpty(mUsersAddressId)){
					MUsersAddress mUsersAddress=mUsersAddressBean.findById(mUsersAddressId);
					if(!QwyUtil.isNullAndEmpty(mUsersAddress)){
						if(!"2".equals(mProduct.getType())){
							mCoinPay.setMUsersAddressId(mUsersAddressId);
						}
					}else{
						tm.rollback(ts);
						log.info("MCoinPayBean.SaveMCoinPay 数据回滚: 收货地址有误");
						return null;
					}
				}else{
					if(!"2".equals(mProduct.getType())){//喵商品是投资券时，传值mUsersAddressId为空
						tm.rollback(ts);
						log.info("MCoinPayBean.SaveMCoinPay 数据回滚: 投资券类型喵商品的收货地址不为空");
						return null;
					}
					
				}
				
				if(!QwyUtil.isNullAndEmpty(dao.saveAndReturnId(mCoinPay))){
					UsersInfo usersInfo=registerUserBean.updateCoinByUid(uid,-mCoinPay.getCoin());
					if(usersInfo==null){
						tm.rollback(ts);
						log.info("MCoinPayBean.SaveMCoinPay 数据回滚: 剩余喵币不足");
						return null;
					}
					if(QwyUtil.isNullAndEmpty(usersInfo.getId())||QwyUtil.isNullAndEmpty(mCoinPay)){
						tm.rollback(ts);
						log.info("MCoinPayBean.SaveMCoinPay 数据回滚: 扣除总喵币失败");
						return null;
					}
					Boolean isOk=mProductBean.updateByleftStock(mProduct, copies);
					if(!isOk){
						tm.rollback(ts);
						log.info("MCoinPayBean.SaveMCoinPay 数据回滚: 扣除喵商品剩余库存");
						return null;
					}
					if(QwyUtil.isNullAndEmpty(usersInfo.getTotalPoint())){
						usersInfo.setTotalPoint(0l);
					}
					saveMCoinRecord(mCoinPay,usersInfo.getTotalPoint());
					//购买投资券类型产品，发放投资券
					if("2".equals(mProduct.getType())&&!QwyUtil.isNullAndEmpty(mProduct.getMoney())&&Double.parseDouble(mProduct.getMoney())>0D){
						for(int i=0;i<copies;i++){
							double money=QwyUtil.calcNumber(mProduct.getMoney(), 100, "100").doubleValue();
							userRechargeBean.sendHongBao(uid, money, null, "0", 10001, "兑换投资券"); 	
						}							
					}						   

					tm.commit(ts);
					Map<String,Object> map=new HashMap<String,Object>();
					map.put("totalPoint", usersInfo.getTotalPoint()+"");
					return map;
				}				
			} catch (Exception e) {
				// TODO: handle exception
				log.error("操作异常: ",e);
				tm.rollback(ts);
				log.info("MCoinPayBean.SaveMCoinPay 数据回滚: 扣除喵币异常;");
				return null;
			}
		 }
		}
		return null;
		
	}
	
	/**
	 * 保存兑换流水记录	
	 * @param mCoinIncome
	 * @return
	 */
	public String saveMCoinRecord(MCoinPay mCoinPay,long totalCoin) throws Exception{
		MCoinRecord record=new MCoinRecord();
		record.setCoinType("1");
		record.setRecordId(mCoinPay.getRecordNumber());
		record.setType(mCoinPay.getType());
		record.setStatus(mCoinPay.getStatus());
		record.setCoin(mCoinPay.getCoin());
		record.setUsersId(mCoinPay.getUsersId());
		record.setNote("购买商品");
		record.setTotalCoin(totalCoin);
		record.setInsertTime(mCoinPay.getInsertTime());
		dao.saveAndReturnId(record);
		//修改平台兑换总喵币
		platformBean.updatePayCoin(mCoinPay.getCoin());
		return null;
	}

}
