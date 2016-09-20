/**
 * 
 */
package com.zfgt.account.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zfgt.account.dao.SendRatesDAO;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.SendRates;

/**
 * @author 曾礼强
 * 2015年8月17日下午3:07:19
 * 零钱包发放收益
 */
@Service
public class SendRatesBean{
	@Resource
	SendRatesDAO dao;
	/**
	 * 发息时，插入一条记录
	 * @param usersId 用户ID
	 * @param status 状态 0:未发息,1已发息
	 * @param inMoney 本金
	 * @param earnings 收益率
	 * @return
	 */
	public String saveSendRates(Long usersId,String status,Double inMoney,Double earnings,Double payInterest){
		SendRates sendRates=new SendRates();
		sendRates.setInsertTime(new Date());
		sendRates.setInMoney(inMoney);
		sendRates.setStatus(status);
		sendRates.setType("0");
		sendRates.setUsersId(usersId);
		sendRates.setEarnings(earnings);
		sendRates.setPayInterest(payInterest);
		String id=dao.saveAndReturnId(sendRates);
		return id;
	}
	/*public SendRates saveSendRates(Long usersId,String status,Double inMoney,Double earnings){
		SendRates sendRates=new SendRates();
		sendRates.setInsertTime(new Date());
		sendRates.setInMoney(inMoney);
		sendRates.setStatus(status);
		sendRates.setType("0");
		sendRates.setUsersId(usersId);
		sendRates.setEarnings(earnings);
		String id=dao.saveAndReturnId(sendRates);
		return findSendRatesById(id);
	}*/
	
	/**
	 * 根据ID查询发息记录
	 * @param id
	 * @return
	 */
	public SendRates findSendRatesById(String id){
		Object object=dao.findById(new SendRates(), id);
		if(!QwyUtil.isNullAndEmpty(object)){
			return (SendRates) object;
		}
		return null;
	}
	
	/**
	 * 根据用户ID分页查询发息记录
	 * @param pageUtil
	 * @param usersId
	 * @param status  0:未发息,1已发息
	 * @return
	 */
	public PageUtil<SendRates> findPageUtil(PageUtil<SendRates> pageUtil,Long usersId,String status){
		return findPageUtil(pageUtil, usersId, status,null);
	}
	/**
	 * 根据用户ID分页查询发息记录
	 * @param pageUtil
	 * @param usersId
	 * @param status  0:未发息,1已发息
	 * @param time  比time时间小的
	 * @return
	 */
	public PageUtil<SendRates> findPageUtil(PageUtil<SendRates> pageUtil,Long usersId,String status,Date time){
		List<Object> list=new ArrayList<Object>();
		StringBuffer buffer=new StringBuffer();
		buffer.append(" FROM SendRates sr WHERE 1=1 ");
		if(!QwyUtil.isNullAndEmpty(usersId)){
			buffer.append(" AND sr.usersId = ? ");
			list.add(usersId);
		}
		if(!QwyUtil.isNullAndEmpty(status)){
			buffer.append(" AND sr.status = ? ");
			list.add(status);
		}
		if(!QwyUtil.isNullAndEmpty(time)){
			buffer.append(" AND sr.insertTime < ? ");
			list.add(time);
		}
		return dao.getByHqlAndHqlCount(pageUtil, buffer.toString(), buffer.toString(), list.toArray());
	}
}
