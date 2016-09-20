/**
 * 
 */
package com.zfgt.account.bean;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zfgt.account.dao.SendRatesDAO;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.SendRates;
import com.zfgt.orm.SendRatesDetail;

/**
 * @author 曾礼强
 * 2015年8月17日下午3:07:19
 * 零钱包发放收益
 */
@Service
public class SendRatesDetailBean{
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
	public SendRatesDetail saveSendRatesDetail(Long usersId,String status,Double inMoney,Double earnings){
		SendRates sendRates=new SendRates();
		sendRates.setInsertTime(new Date());
		sendRates.setInMoney(inMoney);
		sendRates.setStatus(status);
		sendRates.setType("0");
		sendRates.setUsersId(usersId);
		sendRates.setEarnings(earnings);
		String id=dao.saveAndReturnId(sendRates);
		return findSendRatesDetailById(id);
	}
	
	/**
	 * 根据ID查询发息记录
	 * @param id
	 * @return
	 */
	public SendRatesDetail findSendRatesDetailById(String id){
		Object object=dao.findById(new SendRates(), id);
		if(!QwyUtil.isNullAndEmpty(object)){
			return (SendRatesDetail) object;
		}
		return null;
	}
	
	
	/**
	 * 根据用户ID查询当日明细记录
	 */
	public List<SendRatesDetail> findToDaySendRatesDetails(String usersIds){
		StringBuffer buffer=new StringBuffer();
		buffer.append(" FROM SendRatesDetail sd WHERE sd.usersId in ("+usersIds+") ");
		buffer.append(" AND sd.insertTime >= '"+QwyUtil.fmyyyyMMdd.format(new Date())+" 00:00:00'");
		buffer.append(" AND sd.insertTime <= '"+QwyUtil.fmyyyyMMdd.format(new Date())+" 23:59:59'");
		buffer.append(" ORDER BY sd.insertTime DESC ");
		return dao.LoadAll(buffer.toString(), null);
	}
	
}
