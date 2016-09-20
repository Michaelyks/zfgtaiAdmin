package com.zfgt.thread.action;


import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.jfree.util.Log;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.zfgt.account.bean.MyAccountBean;
import com.zfgt.account.bean.UserRechargeBean;
import com.zfgt.common.bean.LianLianPayBean;
import com.zfgt.common.dao.MyWalletDAO;
import com.zfgt.common.lianlian.pay.utils.PayDataBean;
import com.zfgt.common.util.DESEncrypt;
import com.zfgt.common.util.LockHolder;
import com.zfgt.common.util.MyRedis;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.Account;
import com.zfgt.orm.CzRecord;
import com.zfgt.orm.Users;
import com.zfgt.orm.UsersInfo;
import com.zfgt.thread.bean.LianLianCzThreadBean;

/**启动处理状态为待处理的（连连）充值记录的线程
 * @author 覃文勇
 * @createTime 2015-12-7上午11:35:15
 */
@Service
public class LianLianCzThread  implements Runnable {
	private Logger log = Logger.getLogger(LianLianCzThread.class);
	private Integer pageSize = 50;
	@Resource
	private LianLianCzThreadBean bean;
	@Resource
	private LianLianPayBean lianLianPayBean;
	@Resource
	private MyAccountBean accountBean;
	@Resource
	private MyWalletDAO dao;
	@Resource
	private UserRechargeBean userRechargeBean;
	private MyRedis redis=new MyRedis();
	@Override
	public void run() {
		try {
			Log.info("=================启动处理状态为待处理的（连连）充值记录的线程=================");
			int currentPage = 0;
			for (;;) {
				currentPage++;
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());
				calendar.add(Calendar.MINUTE,-30);
				Date insertTime=calendar.getTime();
				PageUtil<CzRecord> pageUtil = new PageUtil<CzRecord>();
				pageUtil.setCurrentPage(currentPage);
				pageUtil.setPageSize(pageSize);
				pageUtil=bean.getCzRecordList(pageUtil, "'3'", "'0'", insertTime);
				System.out.println(currentPage);
				log.info(currentPage);
					if(!QwyUtil.isNullAndEmpty(pageUtil)&&!QwyUtil.isNullAndEmpty(pageUtil.getList())){
						log.info(pageUtil.getCount());
						List<CzRecord> list=pageUtil.getList();
						if(!QwyUtil.isNullAndEmpty(list)){
							log.info("待处理的订单："+list.size());
							for (int i = 0; i < list.size(); i++) {
								CzRecord record=list.get(i);
								if(!QwyUtil.isNullAndEmpty(record.getId())){									
									synchronized (LockHolder.getLock(record.getOrderId())) {
										log.info("锁定充值订单号: "+record.getOrderId());
										if("0".equals(record.getStatus())){
											final CzRecord czRecord=record;
											//修改单个充值订单
											final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
											scheduler.scheduleAtFixedRate(new Runnable() {
												  @Override
												public void run() {
													try {
														// TODO Auto-generated method stub
														log.info("=================线程修改单个充值订单...=================");
														//记录生成时间（格式：yyMMddHHmmss）
														String dt_order=QwyUtil.fmyyyyMMddHHmmss2.format(czRecord.getInsertTime());
														System.out.println(dt_order);
													    String json=lianLianPayBean.queryPay(czRecord.getOrderId(),dt_order,"0");
												        log.info("连连认证支付查询结果"+json);
													    JSONObject jsonObject = JSONObject.fromObject(json);
												        
													    if(!QwyUtil.isNullAndEmpty(jsonObject)){
														  String ret_code=jsonObject.get("ret_code").toString();
														  if("0000".equals(ret_code)){//正常返回用户信息
															//连连平台订单号
															String oid_paybill=QwyUtil.get(jsonObject, "oid_paybill");
															String memo=QwyUtil.get(jsonObject, "memo");
															if("SUCCESS".equals(jsonObject.get("result_pay").toString())){
																PayDataBean payDataBean = JSON.parseObject(json, PayDataBean.class);
																String accountId=redis.get(payDataBean.getNo_order());
																Users users=accountBean.getUsersById(czRecord.getUsersId());
																UsersInfo usersInfo=users.getUsersInfo();
																//判断是否绑定银行或者是否首次充值
																if(!QwyUtil.isNullAndEmpty(accountId)&&!usersInfo.getIsBindBank().equals("1")){
																	Account account=accountBean.getAccountById(accountId);
																	account.setStatus("0");
																	usersInfo.setIsBindBank("1");
																	usersInfo.setIsVerifyIdcard("1");
																	usersInfo.setRealName(account.getBankAccountName());
																	usersInfo.setIdcard(account.getIdcard());
																	usersInfo.setUpdateTime(new Date());
																	Object[] objIDCard=QwyUtil.getInfoByIDCard(DESEncrypt.jieMiIdCard(account.getIdcard()));
																	if(!QwyUtil.isNullAndEmpty(objIDCard)){
																		usersInfo.setSex(objIDCard[0]+"");
																		usersInfo.setAge(objIDCard[1]+"");
																		usersInfo.setBirthday(QwyUtil.fmyyyyMM.parse(objIDCard[2]+""));
																	}
																	dao.saveOrUpdate(usersInfo);
																	dao.saveOrUpdate(account);
																	redis.del(payDataBean.getNo_order());
																	redis.del(users.getId()+"");
																}
																if(QwyUtil.isNullAndEmpty(payDataBean.getMoney_order())){
																	return;
																}
																boolean isRecharge = userRechargeBean.usreRecharge(czRecord.getUsersId(),QwyUtil.calcNumber(payDataBean.getMoney_order(), 100, "*").doubleValue(), "cz", "第三方支付", "通过【连连支付】进行充值");
																if(isRecharge){
																	czRecord.setStatus("1");
																	czRecord.setNote("充值成功");
																}else{
																	czRecord.setStatus("0");
																	czRecord.setNote("易宝支付已充值成功,数据库插入失败!");
																}
																if(!QwyUtil.isNullAndEmpty(oid_paybill)){
																	czRecord.setYbOrderId(oid_paybill);
														    	}
																czRecord.setCheckTime(new Date());
																dao.saveOrUpdate(czRecord);
															}
															if("REFUND".equals(jsonObject.get("result_pay").toString())){
																
																//bean.updateCzRecord(czRecord,oid_paybill,"2", "充值失败,退款");
																czRecord.setNote("充值失败,已退款");
																if(!QwyUtil.isNullAndEmpty(memo)){
																	czRecord.setErrCause(","+memo);
																}
																czRecord.setErrorCode(ret_code);
																czRecord.setCheckTime(new Date());
														    	czRecord.setStatus("2");
														    	dao.saveOrUpdate(czRecord);
															}
															if("FAILURE".equals(jsonObject.get("result_pay").toString())){
																//bean.updateCzRecord(czRecord,oid_paybill,"2", "充值失败");
																czRecord.setNote("充值失败");
																if(!QwyUtil.isNullAndEmpty(memo)){
																	czRecord.setErrCause(","+memo);
																}
																czRecord.setErrorCode(ret_code);
																czRecord.setCheckTime(new Date());
														    	czRecord.setStatus("2");
														    	dao.saveOrUpdate(czRecord);
															}
															log.info("充值订单号："+czRecord.getOrderId()+";返回信息："+czRecord.getNote());
															}else{
																czRecord.setNote("充值失败");
																if(!QwyUtil.isNullAndEmpty(QwyUtil.get(jsonObject, "ret_msg"))){
																	czRecord.setErrCause(","+QwyUtil.get(jsonObject, "ret_msg"));
																}
																czRecord.setErrorCode(ret_code);
																czRecord.setCheckTime(new Date());
														    	czRecord.setStatus("2");
														    	dao.saveOrUpdate(czRecord);
															}
														}
													} catch (Exception e) {
														// TODO: handle exception
														log.error("操作异常: ",e);
														log.error("线程修改单个充值订单异常",e);
													}
													scheduler.shutdown();
											}
										}, 0, 3, TimeUnit.SECONDS);
								}	
									}
						 }
					   }
					 }
				}else{
					break;
				  }
				}
			log.info("整个处理状态为待处理充值记录的后台线程结束;");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ",e);
			log.error("启动处理状态为待处理的（连连）充值记录的线程",e);
		}
	}

}
