package com.zfgt.thread.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.login.bean.RegisterUserBean;
import com.zfgt.orm.CoinPurse;
import com.zfgt.orm.SendRates;
import com.zfgt.orm.SystemConfig;

/**
 * 后台线程自动发放收益<br>
 * 
 * @author qwy
 *
 * @createTime 2015-4-28上午9:42:57
 */
@Service(value = "sendCoinPurseRatesThread")
public class SendCoinPurseRatesThread implements Runnable {
	private Logger log = Logger.getLogger(SendCoinPurseRatesThread.class);
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
		int currentPage = 0;
		for (;;) {
			log.info("发放收益的线程");
			SystemConfig systemConfig = systemConfigBean.findSystemConfig();
			synchronized (this) {
				// 查询的50个人的ID
				String usersIds = "";
				try {
					SimpleDateFormat fmyyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
					Date time = fmyyyyMMdd.parse(fmyyyyMMdd.format(new Date()));
					currentPage++;
					PageUtil<SendRates> pageUtil = new PageUtil<SendRates>();
					pageUtil.setCurrentPage(currentPage);
					pageUtil.setPageSize(pageSize);
					pageUtil = sendRatesBean.findPageUtil(pageUtil, null, "0", time);
					if (!QwyUtil.isNullAndEmpty(pageUtil) && !QwyUtil.isNullAndEmpty(pageUtil.getList())) {
						ApplicationContext context = ApplicationContexts.getContexts();
						// SessionFactory sf = (SessionFactory)
						// context.getBean("sessionFactory");
						PlatformTransactionManager tm = (PlatformTransactionManager) context.getBean("transactionManager");
						TransactionStatus ts = tm.getTransaction(new DefaultTransactionDefinition());
						try {
							List<SendRates> list = pageUtil.getList();
							// 得到用户ID
							for (SendRates sendRates : list) {
								usersIds += sendRates.getUsersId() + ",";
							}
							usersIds = usersIds.substring(0, usersIds.length() - 1);
							// 获取用户零钱包
							/*
							 * List<CoinPurse> coinPurses=coinPurseBean.find
							 * CoinPurseListByUsersId(usersIds); Map<String,
							 * Object>
							 * mapcoinPurses=QwyUtil.ListToMap("usersId",
							 * coinPurses);
							 */
							for (SendRates sendRates : list) {
								CoinPurse coinPurse = coinPurseBean.findCoinPurseByUsersId(sendRates.getUsersId());
								coinPurse.setEarnings(systemConfig.getEarnings());
								// 总收益
								Double bcsy = sendRates.getPayInterest();

								coinPurse.setInMoney(QwyUtil.calcNumber(coinPurse.getInMoney(), bcsy, "+").doubleValue());
								coinPurse.setInvestDay(coinPurse.getInvestDay() + 1);
								coinPurse.setUpdateTime(new Date());
								coinPurse.setPayInterest(QwyUtil.calcNumber(coinPurse.getPayInterest(), bcsy, "+").doubleValue());
								dao.saveOrUpdate(coinPurse);
								// Double addbcsy=QwyUtil.calcNumber(bcsy, 1,
								// "/",2).doubleValue();
								// 改变总资产
								walletBean.addTotalMoney(coinPurse.getUsersId(), bcsy.doubleValue());

								String cpfrId = cpfrBean.saveCoinPurseFundsRecord(coinPurse.getUsersId(), bcsy, sendRates.getId(), "shouyi", coinPurse.getInMoney());
								if (QwyUtil.isNullAndEmpty(cpfrId)) {
									continue;
								}
								sendRates.setStatus("1");
								sendRates.setUpdateTime(new Date());
								dao.saveOrUpdate(sendRates);
							}
							if (!QwyUtil.isNullAndEmpty(list)) {
								tm.commit(ts);
							}

						} catch (Exception e) {
							tm.rollback(ts);
							log.error("操作异常: ", e);
							log.error("进入修改产品状态的后台线程异常: ", e);
						}
					} else {
						log.info("退出发放收益线程");
						return;
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					log.error("操作异常: ", e);
					log.error("进入修改产品状态的后台线程异常: ", e);
				}

			}
		}
	}

	/*
	 * public static void main(String[] args) {
	 * System.out.println(QwyUtil.addDaysFromOldDate(new Date(),
	 * -2).getTime().toLocaleString()); }
	 */

}
