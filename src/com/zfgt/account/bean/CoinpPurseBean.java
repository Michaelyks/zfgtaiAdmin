/**
 * 
 */
package com.zfgt.account.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zfgt.account.dao.CoinpPurseDAO;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.CoinPurse;

/**
 * @author 曾礼强
 * 2015年8月17日下午3:06:49
 * 零钱包操作
 */
@Service
public class CoinpPurseBean{
	@Resource
	CoinpPurseDAO dao;
	/**
	 * 保存零钱包
	 * @param earnings 收益率
	 * @param usersId 用户ID
	 * @param inMoney 钱包金额
	 * @param investDay 投资天数
	 * @param payInterest 支付利息
	 * @return
	 */
	public String saveCoinPurse(Double earnings,Long usersId,Double inMoney){
		CoinPurse	cp=new CoinPurse();
		cp.setEarnings(earnings);
		cp.setUsersId(usersId);
		cp.setInMoney(inMoney);
		cp.setStatus("0");
		cp.setInsertTime(new Date());
		cp.setInvestDay(0L);
		cp.setPayInterest(0D);
		return dao.saveAndReturnId(cp);
	}
	
	/**
	 * 修改零钱包
	 * @param earnings 收益率
	 * @param usersId 用户ID
	 * @param inMoney 钱包金额
	 * @param investDay 投资天数
	 * @param payInterest 支付利息
	 * @return
	 */
	public boolean updateCoinPurse(Double earnings,Long usersId,Double inMoney,Long investDay,Double payInterest){
		CoinPurse cp=findCoinPurseByUsersId(usersId);
		cp.setEarnings(earnings);
		cp.setUsersId(usersId);
		cp.setInMoney(inMoney);
		cp.setUpdateTime(new Date());
		cp.setInvestDay(investDay);
		cp.setPayInterest(payInterest);
		dao.saveOrUpdate(cp);
		return true;
	}
	
	
	
	
	/**
	 * 根据用户ID去查询零钱包
	 * @param usersId 用户ID
	 * @return
	 */
	public CoinPurse findCoinPurseByUsersId(Long usersId){
		synchronized (usersId) {
			List<Object> list=new ArrayList<Object>();
			StringBuffer buffer=new StringBuffer();
			buffer.append(" FROM CoinPurse cp WHERE cp.usersId = ? ");
			list.add(usersId);
			List<CoinPurse> coinPurses=dao.LoadAll(buffer.toString(), list.toArray());
			if(!QwyUtil.isNullAndEmpty(coinPurses)){
				return coinPurses.get(0);
			}
			return null;
		}
	}
	
	
	/**
	 * 根据用户ID去查询零钱包
	 * @param usersId 用户ID
	 * @return
	 */
	public List<CoinPurse> findCoinPurseListByUsersId(String usersIds){
		StringBuffer buffer=new StringBuffer();
		buffer.append(" FROM CoinPurse cp WHERE cp.usersId in ("+usersIds+") ");
		return dao.LoadAll(buffer.toString(),null);
	}
}

