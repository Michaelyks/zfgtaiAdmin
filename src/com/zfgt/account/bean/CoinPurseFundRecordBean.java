package com.zfgt.account.bean;

import java.util.ArrayList;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.account.dao.SendRatesDAO;
import com.zfgt.common.dao.ObjectDAO;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.CoinPurseFundsRecord;
import com.zfgt.orm.FundRecord;


/**零钱包资金流水的Bean层;<br>
 * @method 获取用户的资金流水;
 * @author qwy
 *
 * @createTime 2015-05-06 18:42:33
 */
@Service
public class CoinPurseFundRecordBean {
	@Resource
	SendRatesDAO dao;
	private static Logger log = Logger.getLogger(CoinPurseFundRecordBean.class);
	/**
	 * 保存零钱包资金流水
	 */
	public String saveCoinPurseFundsRecord(Long usersId, Double money, String recordId, String type, Double usersCost){
		CoinPurseFundsRecord coinPurseFundsRecord=new CoinPurseFundsRecord();
		coinPurseFundsRecord.setInsertTime(new Date());
		coinPurseFundsRecord.setMoney(money);
		coinPurseFundsRecord.setRecordId(recordId);
		coinPurseFundsRecord.setUsersId(usersId);
		coinPurseFundsRecord.setStatus("0");
		coinPurseFundsRecord.setType(type);
		coinPurseFundsRecord.setUsersCost(usersCost);
		String id=dao.saveAndReturnId(coinPurseFundsRecord);
		return id;
	}
	
	
	/**获取用户的零钱包资金流水;
	 * @param uid 用户id
	 * @param type 操作类别  cz:用户充值   tx:提现  zf:在线支付,buy:购买理财产品
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @param pageUtil 分页对象;
	 * @return 资金流水分页对象;
	 */
	@SuppressWarnings("unchecked")
	public PageUtil<CoinPurseFundsRecord> loadFundRecord(long uid,String type,String startDate,String endDate,PageUtil<FundRecord> pageUtil) {
		try {
			ArrayList<Object> ob = new ArrayList<Object>();
			StringBuffer buff = new StringBuffer();
			buff.append("FROM CoinPurseFundsRecord cpfr ");
			buff.append("WHERE cpfr.usersId = ? ");
			ob.add(uid);
			
			if(!QwyUtil.isNullAndEmpty(type) && !"all".equalsIgnoreCase(type)){
				buff.append("AND cpfr.type = ? ");
				ob.add(type);
			}
			if(!QwyUtil.isNullAndEmpty(startDate)){
				buff.append("AND cpfr.insertTime >= ? ");
				ob.add(QwyUtil.fmyyyyMMdd.parse(startDate+" 00:00:00"));
			}
			if(!QwyUtil.isNullAndEmpty(endDate)){
				buff.append("AND cpfr.insertTime <= ? ");
				ob.add(QwyUtil.fmyyyyMMddHHmmss.parse(endDate+" 23:59:59"));
			}
			buff.append("ORDER BY cpfr.insertTime DESC");
			return (PageUtil<CoinPurseFundsRecord>)dao.getPage(pageUtil, buff.toString(), ob.toArray());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
		}
		return null;
	}
}
