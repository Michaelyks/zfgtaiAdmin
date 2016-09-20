package com.zfgt.mshop.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zfgt.common.bean.PlatformBean;
import com.zfgt.common.util.OrderNumerUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.mshop.dao.MCoinIncomeDAO;
import com.zfgt.orm.Investors;
import com.zfgt.orm.MCoinIncome;
import com.zfgt.orm.MCoinRecord;
import com.zfgt.orm.MProduct;

/**喵币收入的Bean层;<br>
 * @author 覃文勇
 * @createTime 2015-10-28下午3:49:07
 */
@Service
public class MCoinIncomeBean {
	@Resource
	private MCoinIncomeDAO dao;
	@Resource
	private PlatformBean platformBean;

	
	
	/**
	 * 获得喵币收入
	 * @param uid 用户id
	 * @param type 类型 1:签到获得 2:邀请好友(注册) 3:被邀请(注册) 4:被邀请(第一笔投资) 5:投资获得 6:分享活动获得 7:手动赠送
	                    //如果该用户被邀请注册的,那么首次购买产品,则会赠送2次喵币
	 * @param note 备注
	 * @param coin  喵币
	 * @return
	 * @throws Exception
	 */
	public MCoinIncome saveMCoinIncome(long uid,String type,String note,long coin)  throws Exception{
		MCoinIncome mCoinIncome=new MCoinIncome();
		mCoinIncome.setUsersId(uid);
		mCoinIncome.setRecordNumber(OrderNumerUtil.generateRequestId());//生成流水号
		mCoinIncome.setNote(note);
		mCoinIncome.setCoin(coin);
		mCoinIncome.setType(type);
		mCoinIncome.setStatus("0");
		mCoinIncome.setInsertTime(new Date());
		dao.saveOrUpdate(mCoinIncome);
		return mCoinIncome;
	}
	
	/**
	 * 保存用户收入流水记录	
	 * @param mCoinIncome
	 * @return
	 */
	public String saveMCoinRecord(MCoinIncome mCoinIncome,Long totalCoin){
		MCoinRecord record=new MCoinRecord();
		record.setCoinType("2");//用户收入
		record.setRecordId(mCoinIncome.getRecordNumber());
		record.setType(mCoinIncome.getType());
		record.setStatus(mCoinIncome.getStatus());
		record.setCoin(mCoinIncome.getCoin());
		record.setUsersId(mCoinIncome.getUsersId());
		record.setNote(mCoinIncome.getNote());
		record.setTotalCoin(totalCoin);
		record.setInsertTime(mCoinIncome.getInsertTime());
		//修改平台发放总喵币
		platformBean.updateTotalCoin(mCoinIncome.getCoin());
		return dao.saveAndReturnId(record);
	}
	/**
	 * 	计算投资获得的喵币
	 * @param investors 投资记录
	 * @return
	 * @throws Exception
	 */
	public Long countCoin(Investors investors) throws Exception{
		long coin=0l;
		//个人支付金额
		Double inMoney=QwyUtil.calcNumber(investors.getInMoney(), 100, "/").doubleValue();//最小单位为元
		//投资天数
		int day=QwyUtil.getDifferDays(investors.getStartTime(), investors.getFinishTime());		
		coin=QwyUtil.calcNumber(inMoney*day, 100*30, "/", 2).longValue();
		if(QwyUtil.isNullAndEmpty(coin)){
			return 0L;
		}
		return coin;
	}


}
