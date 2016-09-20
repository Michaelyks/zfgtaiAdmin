package com.zfgt.thread.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.account.bean.MyAccountBean;
import com.zfgt.account.bean.UserRechargeBean;
import com.zfgt.account.bean.ValidateBean;
import com.zfgt.common.bean.YiBaoPayBean;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.common.yeepay.PaymentForOnlineService;
import com.zfgt.common.yeepay.QueryResult;
import com.zfgt.orm.CzRecord;
import com.zfgt.orm.InterestDetails;
import com.zfgt.orm.Investors;
import com.zfgt.orm.Product;
import com.zfgt.thread.bean.ClearingProductFreshmanThreadBean;
import com.zfgt.thread.bean.ClearingProductThreadBean;

/**
 * 
 * 线程扫描网银充值的
 * @author 曾礼强
 *
 * @createTime 2015年6月26日 10:39:32
 */
@Service
public class WyCzRecordThread extends TimerTask {
	private Logger log = Logger.getLogger(WyCzRecordThread.class);
	private Integer pageSize = 50;
	@Resource
	private UserRechargeBean bean;
	@Resource
	private ValidateBean validateBean;
	@Resource
	private MyAccountBean myAccountBean;
	@Resource
	private YiBaoPayBean yiBaoPayBean;
	private Integer currentPage=1;
	@Override
	public void run() {
		try {
			log.info("进入后台线程----充值结算");
			PageUtil<CzRecord> pageUtil = new PageUtil<CzRecord>();
			pageUtil.setPageSize(pageSize);
			pageUtil.setCurrentPage(currentPage);
			pageUtil=bean.findWyCzRecord(pageUtil);
			if(!QwyUtil.isNullAndEmpty(pageUtil)&&!QwyUtil.isNullAndEmpty(pageUtil.getList())){
				List<CzRecord> records=pageUtil.getList();
				if(!QwyUtil.isNullAndEmpty(records)){
					for (CzRecord czRecord : records) {
						if(!czRecord.getStatus().equals("1")){
							QueryResult queryResult=PaymentForOnlineService.queryByOrder(czRecord.getOrderId());
							log.info("进入网银后台线程----充值结算结果"+queryResult);
							if(queryResult.getR1_Code().equals("1")){
								if(queryResult.getRb_PayStatus().equalsIgnoreCase("INIT")){
									CzRecord record=bean.getCzRecordByOrderId(czRecord.getOrderId());
									if(record.getStatus().equals("0")){
										record.setStatus("2");
										record.setYbOrderId(queryResult.getR2_TrxId());
										record.setCheckTime(new Date());
										record.setNote("订单未支付");
										bean.updateRecord(record);
										continue;
									}
								}else if(queryResult.getRb_PayStatus().equalsIgnoreCase("CANCELED")){
									CzRecord record=bean.getCzRecordByOrderId(czRecord.getOrderId());
									if(record.getStatus().equals("0")){
										record.setStatus("2");
										record.setYbOrderId(queryResult.getR2_TrxId());
										record.setCheckTime(new Date());
										record.setNote("订单被取消");
										bean.updateRecord(record);
										continue;
									}
								}else if(queryResult.getRb_PayStatus().equalsIgnoreCase("SUCCESS")){
									CzRecord record=bean.getCzRecordByOrderId(czRecord.getOrderId());
									if(record.getStatus().equals("0")){
										if(bean.usreRecharge(record.getUsersId(), record.getMoney(), " cz", "在线充值", "")){
											// 产通用接口支付成功返回-电话支付返回	
											record.setStatus("1");
											record.setYbOrderId(queryResult.getR2_TrxId());
											record.setCheckTime(new Date());
											record.setNote("充值成功");
											bean.updateRecord(record);
											continue;
										}
									}
								}
							}else if(queryResult.getR1_Code().equals("50")) {
								CzRecord record=bean.getCzRecordByOrderId(czRecord.getOrderId());
								if(record.getStatus().equals("0")){
									record.setStatus("2");
									record.setYbOrderId("");
									record.setCheckTime(new Date());
									record.setNote("订单不存在");
									bean.updateRecord(record);
									continue;
								}
							}
						}
					} 
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
			log.error("进入结算新手专享理财产品的后台线程异常: ",e);
		}
	}
	
	

}
