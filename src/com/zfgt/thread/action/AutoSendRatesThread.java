package com.zfgt.thread.action;

import java.text.SimpleDateFormat;
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

import com.zfgt.account.bean.CoinPurseFundRecordBean;
import com.zfgt.account.bean.CoinpPurseBean;
import com.zfgt.account.bean.SendRatesBean;
import com.zfgt.account.bean.SendRatesDetailBean;
import com.zfgt.account.bean.ShiftToBean;
import com.zfgt.account.dao.ShiftToDAO;
import com.zfgt.common.ApplicationContexts;
import com.zfgt.common.bean.MyWalletBean;
import com.zfgt.common.bean.SystemConfigBean;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.login.bean.RegisterUserBean;
import com.zfgt.orm.SendRatesDetail;
import com.zfgt.orm.ShiftTo;
import com.zfgt.orm.SystemConfig;

/**
 * 后台线程自动计算收益<br>
 * 
 * @author qwy
 *
 * @createTime 2015-4-28上午9:42:57
 */
@Service(value = "autoSendRatesThread")
public class AutoSendRatesThread implements Runnable {
	private Logger log = Logger.getLogger(AutoSendRatesThread.class);
	@Resource
	ShiftToBean shiftToBean;
	@Resource
	ShiftToDAO dao;
	@Resource
	SendRatesBean sendRatesBean;
	@Resource
	SendRatesDetailBean sendRatesDetailBean;
	@Resource
	private SystemConfigBean systemConfigBean;
	@Resource
	RegisterUserBean registerUserBean;
	@Resource
	MyWalletBean walletBean;
	@Resource
	CoinpPurseBean coinPurseBean;
	@Resource
	CoinPurseFundRecordBean cpfrBean;

	private Integer pageSize = 50;

	@Override
	public void run() {
		log.info("计算收益的线程");
		int currentPage = 0;
		synchronized (this) {
			for (;;) {
				// 查询的50个人的ID
				String usersIds = "";
				try {
					currentPage++;
					System.out.println(currentPage);
					log.info(currentPage);
					// Date
					// time=QwyUtil.fmyyyyMMdd.parse(QwyUtil.fmyyyyMMdd.format(QwyUtil.addDaysFromOldDate(new
					// Date(), -1).getTime()));
					SimpleDateFormat fmyyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
					Date time = fmyyyyMMdd.parse(fmyyyyMMdd.format(new Date()));
					System.out.println(QwyUtil.fmyyyyMMdd.format(new Date()));
					System.out.println("000000" + QwyUtil.fmyyyyMMdd.format(time));
					// 需要发息的金额和用户
					// 查询一天前（今天以前的不包含今天）转入的记录
					List<Object[]> objects = shiftToBean.findSendMoney(currentPage, pageSize);
					// 无用户直接退出
					if (QwyUtil.isNullAndEmpty(objects)) {
						log.info("退出计算收益线程");
						break;
					}
					// 得到所有需要发息的用户ID
					for (Object[] obj : objects) {
						usersIds += obj[1] + ",";
					}
					usersIds = usersIds.substring(0, usersIds.length() - 1);
					String alreadySendIds = "";
					// 获取今天发送利息的明细
					List<SendRatesDetail> details = sendRatesDetailBean.findToDaySendRatesDetails(usersIds);
					List<ShiftTo> alreadySendList = new ArrayList<ShiftTo>();
					if (!QwyUtil.isNullAndEmpty(details)) {
						// 获取已经发息的ID
						for (SendRatesDetail sendRatesDetail : details) {
							alreadySendIds += "'" + sendRatesDetail.getShiftToId() + "',";
						}
						alreadySendIds = alreadySendIds.substring(0, alreadySendIds.length() - 1);
						// 发息的记录
						alreadySendList = shiftToBean.findShiftTosByIds(alreadySendIds);
					}
					// 以userId为key存储已经发息
					Map<String, List<ShiftTo>> mapAlready = new HashMap<String, List<ShiftTo>>();
					// 以userId为key需要存储需要发息
					Map<String, List<ShiftTo>> mapPlan = new HashMap<String, List<ShiftTo>>();
					// //获取用户零钱包
					// List<CoinPurse>
					// coinPurses=coinPurseBean.findCoinPurseListByUsersId(usersIds);
					// Map<String, Object>
					// mapcoinPurses=QwyUtil.ListToMap("usersId", coinPurses);
					// 查询需要发息的记录
					List<ShiftTo> list = shiftToBean.findShiftTosByUsersId("'0','1'", time, "<", "insertTime", "ASC", usersIds);
					// 保存发息明细记录
					List<SendRatesDetail> sendRatesDetails = new ArrayList<SendRatesDetail>();
					// 修改的记录
					List<ShiftTo> shiftTos = new ArrayList<ShiftTo>();
					// //需要修改的零钱包
					// List<CoinPurse> cps=new ArrayList<CoinPurse>();
					// 保存发息记录
					// List<SendRates> sendRatesList=new ArrayList<SendRates>();
					// 无发息记录
					if (QwyUtil.isNullAndEmpty(list)) {
						usersIds = "";
						continue;
					}
					// 准备发息的
					for (ShiftTo shiftTo : list) {
						List<ShiftTo> sfs = mapPlan.get(shiftTo.getUsersId() + "");
						if (!QwyUtil.isNullAndEmpty(sfs)) {
							sfs.add(shiftTo);
						} else {
							sfs = new ArrayList<ShiftTo>();
							sfs.add(shiftTo);
						}
						mapPlan.put(shiftTo.getUsersId() + "", sfs);
					}
					// 发息了直接退出
					if (alreadySendList.size() == list.size()) {
						usersIds = "";
						continue;
					}

					if (!QwyUtil.isNullAndEmpty(alreadySendList)) {
						// 已经发息的
						for (ShiftTo shiftTo : alreadySendList) {
							List<ShiftTo> sfs = mapAlready.get(shiftTo.getUsersId() + "");
							if (!QwyUtil.isNullAndEmpty(sfs)) {
								sfs.add(shiftTo);
							} else {
								sfs = new ArrayList<ShiftTo>();
								sfs.add(shiftTo);
							}
							mapAlready.put(shiftTo.getUsersId() + "", sfs);
						}
					}
					ApplicationContext context = ApplicationContexts.getContexts();
					// SessionFactory sf = (SessionFactory)
					// context.getBean("sessionFactory");
					PlatformTransactionManager tm = (PlatformTransactionManager) context.getBean("transactionManager");
					TransactionStatus ts = tm.getTransaction(new DefaultTransactionDefinition());
					SystemConfig systemConfig = systemConfigBean.findSystemConfig();
					try {
						// 得到用户ID
						for (Object[] obj : objects) {
							if (!QwyUtil.isNullAndEmpty(mapAlready.get(obj[1] + ""))) {
								usersIds = "";
								continue;
							}
							// CoinPurse coinPurse=(CoinPurse)
							// mapcoinPurses.get(obj[1]+"");
							List<ShiftTo> tos = mapPlan.get(obj[1] + "");
							// 无记录跳过
							if (QwyUtil.isNullAndEmpty(tos)) {
								usersIds = "";
								continue;
							}

							// coinPurse.setEarnings(systemConfig.getEarnings());
							// 总收益
							Double bcsy = QwyUtil.calcNumber(systemConfig.getEarnings(), obj[0], "*").doubleValue();
							bcsy = QwyUtil.calcNumber(bcsy, 100, "/", 2).doubleValue();
							bcsy = QwyUtil.calcNumber(bcsy, 365, "/", 7).doubleValue();

							// coinPurse.setInMoney(QwyUtil.calcNumber(coinPurse.getInMoney(),
							// bcsy, "+").doubleValue());
							// coinPurse.setInvestDay(coinPurse.getInvestDay()+1);
							// coinPurse.setUpdateTime(new Date());
							// coinPurse.setPayInterest(QwyUtil.calcNumber(coinPurse.getPayInterest(),
							// bcsy, "+").doubleValue());
							// cps.add(coinPurse);
							// dao.saveOrUpdate(coinPurse);
							// Double addbcsy=QwyUtil.calcNumber(bcsy, 1,
							// "/",2).doubleValue();
							// 改变总资产
							// walletBean.addTotalMoney(coinPurse.getUsersId(),
							// addbcsy.longValue());

							// 发放利息记录
							String sendRatesId = sendRatesBean.saveSendRates(Long.valueOf(obj[1] + ""), "0", Double.valueOf(obj[0] + ""), systemConfig.getEarnings(), bcsy);
							// String
							// cpfrId=cpfrBean.saveCoinPurseFundsRecord(coinPurse.getUsersId(),
							// bcsy, sendRatesId, "shouyi",
							// coinPurse.getInMoney());
							// if(QwyUtil.isNullAndEmpty(sendRatesId)&&QwyUtil.isNullAndEmpty(cpfrId)){
							// return;
							// }
							if (QwyUtil.isNullAndEmpty(sendRatesId)) {
								usersIds = "";
								continue;
							}
							// 发放收益明细
							for (ShiftTo shiftTo : tos) {
								shiftTo = shiftToBean.findShiftToById(shiftTo.getId());
								if (shiftTo.getLeftMoney() == 0) {
									usersIds = "";
									continue;
								}
								SendRatesDetail sendRatesDetail = new SendRatesDetail();
								sendRatesDetail.setEarnings(systemConfig.getEarnings());
								sendRatesDetail.setInMoney(shiftTo.getLeftMoney());
								sendRatesDetail.setInsertTime(new Date());
								Double dbsy = QwyUtil.calcNumber(systemConfig.getEarnings(), shiftTo.getLeftMoney(), "*").doubleValue();
								dbsy = QwyUtil.calcNumber(dbsy, 100, "/", 2).doubleValue();
								dbsy = QwyUtil.calcNumber(dbsy, 365, "/", 7).doubleValue();
								sendRatesDetail.setPayInterest(dbsy);
								sendRatesDetail.setShiftToId(shiftTo.getId());
								sendRatesDetail.setStatus("1");
								sendRatesDetail.setType("0");
								sendRatesDetail.setUsersId(shiftTo.getUsersId());
								sendRatesDetail.setSendRatesId(sendRatesId);
								sendRatesDetails.add(sendRatesDetail);
								dao.saveOrUpdate(sendRatesDetail);
								// 转入剩余金额改变
								shiftTo.setLeftMoney(QwyUtil.calcNumber(shiftTo.getLeftMoney(), sendRatesDetail.getPayInterest(), "+").doubleValue());
								shiftTo.setSendInterest("1");
								shiftTo.setUpdateTime(new Date());
								shiftTo.setStatus("1");
								shiftTos.add(shiftTo);
								dao.saveOrUpdate(shiftTo);
							}
						}

						if (!QwyUtil.isNullAndEmpty(sendRatesDetails) && !QwyUtil.isNullAndEmpty(shiftTos)) {
							usersIds = "";
							tm.commit(ts);
						}

					} catch (Exception e) {
						tm.rollback(ts);
						log.error("操作异常: ", e);
						log.error("进入零钱包计算收益的后台线程异常: ", e);

					}
					objects = null;
				} catch (Exception e) {
					log.error("操作异常: ", e);
					log.error("进入零钱包计算收益的后台线程异常:", e);
				}
			}
		}
	}

}
