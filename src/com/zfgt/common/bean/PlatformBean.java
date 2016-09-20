package com.zfgt.common.bean;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.account.bean.UserInfoBean;
import com.zfgt.common.dao.MyWalletDAO;
import com.zfgt.common.dao.ObjectDAO;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.Platform;

/**对平台融资情况的一个修改;
 * Bean层;
 * @author qwy  
 *
 * @createTime 2015-5-18下午11:36:35
 */
@Service
public class PlatformBean{
	private static Logger log = Logger.getLogger(PlatformBean.class); 
	@Resource
	private MyWalletDAO dao;
	
	/**获取平台融资情况;
	 * @return
	 */
	public Platform getPlatform(){
		Object obj = dao.findById(new Platform(), 1L);
		Platform plat = null;
		if(obj==null){
			plat = new Platform();
			plat.setId(1L);
			plat.setInsertTime(new Date());
			plat.setTotalMoney(0D);
			plat.setTotalProfit(0D);
			plat.setTotalCoupon(0D);
			plat.setUseCoupon(0D);
			plat.setCollectMoney(0D);
			plat.setRegisterCount(0);
			plat.setFreshmanCoupon(0D);
			dao.saveOrUpdate(plat);
			
		}else{
			plat = (Platform)obj;
		}
		return plat;
	}
	
	/**更新平台融资的所有项目的总金额;
	 * @param financingAmount 发布产品时,理财产品融资的总金额;(分)
	 */
	public void updateTotalMoney(double financingAmount){
		try {
			Platform plat = getPlatform();
			double oldTotal = plat.getTotalMoney();
			plat.setTotalMoney(QwyUtil.calcNumber(oldTotal, financingAmount, "+").doubleValue());
			plat.setUpdateTime(new Date());
			dao.saveOrUpdate(plat);
		} catch (Exception e) {
			log.error("操作异常: ",e);
		}
	}
	
	/**更新平台融资的所有用户的总收益
	 * @param totalProfit 用户的总收益
	 */
	public void updateTotalProfit(double totalProfit){
		try {
			Platform plat = getPlatform();
			double oldTotalProfit = plat.getTotalProfit();
			plat.setTotalProfit(QwyUtil.calcNumber(oldTotalProfit, totalProfit, "+").doubleValue());
			plat.setUpdateTime(new Date());
			dao.saveOrUpdate(plat);
		} catch (Exception e) {
			log.error("操作异常: ",e);
		}
	}
	
	/**更新平台融资的所有产品的已募集总金额;
	 * @param collectMoney 已募集产品的总额(分)
	 */
	public void updateCollectMoney(double collectMoney){
		try {
			Platform plat = getPlatform();
			double oldCollectMoney = plat.getCollectMoney();
			plat.setCollectMoney(QwyUtil.calcNumber(oldCollectMoney, collectMoney, "+").doubleValue());
			plat.setUpdateTime(new Date());
			dao.saveOrUpdate(plat);
		} catch (Exception e) {
			log.error("操作异常: ",e);
		}
	}
	
	
	/**更新平台融资的所有赠送的投资券总金额
	 * @param totalCoupon 赠送的投资券总金额;
	 */
	public void updateTotalCoupon(double totalCoupon){
		try {
			Platform plat = getPlatform();
			double oldTotalCoupon = plat.getTotalCoupon();
			plat.setTotalCoupon(QwyUtil.calcNumber(oldTotalCoupon, totalCoupon, "+").doubleValue());
			plat.setUpdateTime(new Date());
			dao.saveOrUpdate(plat);
		} catch (Exception e) {
			log.error("操作异常: ",e);
		}
	}
	
	/**更新平台融资的所有赠送的投资券总金额
	 * @param totalCoupon 赠送的投资券总金额;
	 */
	public void updatePayCoin(Long totalCoupon){
		try {
			Platform plat = getPlatform();
			Long oldTotalCoupon = 0L;
			if(plat.getTotalCoin() !=null){
				oldTotalCoupon = plat.getTotalCoin();
			}
			plat.setTotalCoin(QwyUtil.calcNumber(oldTotalCoupon, totalCoupon, "+").longValue());
			plat.setUpdateTime(new Date());
			dao.saveOrUpdate(plat);
		} catch (Exception e) {
			log.error("操作异常: ",e);
		}
	}
	
	
	/**更新平台融资的所有有效使用投资券的总金额;
	 * @param useCoupon 已使用的投资券金额;
	 */
	public void updateUseCoupon(double useCoupon){
		try {
			Platform plat = getPlatform();
			double oldUseCoupon = plat.getUseCoupon();
			plat.setUseCoupon(QwyUtil.calcNumber(oldUseCoupon, useCoupon, "+").doubleValue());
			plat.setUpdateTime(new Date());
			dao.saveOrUpdate(plat);
		} catch (Exception e) {
			log.error("操作异常: ",e);
		}
	}
	
	/**更新平台融资的新手投资券总额;
	 * @param freshmanCoupon 新手仅有一次投资机会的投资券
	 */
	public void updateFreshmanCoupon(double freshmanCoupon){
		try {
			Platform plat = getPlatform();
			double oldFreshmanCoupon = plat.getFreshmanCoupon();
			plat.setFreshmanCoupon(QwyUtil.calcNumber(oldFreshmanCoupon, freshmanCoupon, "+").doubleValue());
			plat.setUpdateTime(new Date());
			dao.saveOrUpdate(plat);
		} catch (Exception e) {
			log.error("操作异常: ",e);
		}
	}
	
	/**更新平台的注册人数;<br>
	 * 用户注册成功之后调用此方法;
	 */
	public void updateRegisterCount(){
		try {
			Platform plat = getPlatform();
			double oldRegisterCount = plat.getRegisterCount();
			plat.setRegisterCount(QwyUtil.calcNumber(oldRegisterCount, 1, "+").intValue());
			plat.setUpdateTime(new Date());
			dao.saveOrUpdate(plat);
		} catch (Exception e) {
			log.error("操作异常: ",e);
		}
	}
	
	
	/**更新平台的虚拟投资;<br>
	 * 虚拟投资成功之后调用此方法;
	 * @param newVirtualMoney 虚拟投资金额
	 */
	public void updateVirtualMoney(Double newVirtualMoney){
		try {
			Platform plat = getPlatform();
			double oldVirtualMoney = plat.getVirtualMoney();
			if(QwyUtil.isNullAndEmpty(oldVirtualMoney)){
				oldVirtualMoney=0;
			}
			plat.setVirtualMoney(QwyUtil.calcNumber(oldVirtualMoney, newVirtualMoney, "+").doubleValue());
			plat.setUpdateTime(new Date());
			dao.saveOrUpdate(plat);
		} catch (Exception e) {
			log.error("操作异常: ",e);
		}
	}
	
	/**
	 * 修改平台发放总喵币
	 * @param coin 喵币
	 */
	public void updateTotalCoin(Long coin){
		try {
			Platform plat = getPlatform();
			long totalCoin=plat.getTotalCoin();
			long leftCoin=plat.getLeftCoin();
			plat.setTotalCoin(QwyUtil.calcNumber(totalCoin, coin, "+").longValue());
			plat.setLeftCoin(QwyUtil.calcNumber(leftCoin, coin, "+").longValue());
			plat.setUpdateTime(new Date());
			dao.saveOrUpdate(plat);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
