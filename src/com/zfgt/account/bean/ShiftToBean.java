/**
 * 
 */
package com.zfgt.account.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.zfgt.account.dao.ShiftToDAO;
import com.zfgt.common.ApplicationContexts;
import com.zfgt.common.bean.MyWalletBean;
import com.zfgt.common.bean.SystemConfigBean;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.login.bean.RegisterUserBean;
import com.zfgt.orm.CoinPurse;
import com.zfgt.orm.ShiftTo;
import com.zfgt.orm.SystemConfig;
import com.zfgt.orm.Users;
import com.zfgt.orm.UsersInfo;

/**
 * @author 曾礼强 2015年8月17日下午3:06:27 转入操作
 */
@Service
public class ShiftToBean {
	private static Logger log = Logger.getLogger(ShiftToBean.class);
	@Resource
	ShiftToDAO dao;
	@Resource
	CoinpPurseBean coinpPurseBean;
	@Resource
	CoinPurseFundRecordBean cpfrBean;
	@Resource
	private SystemConfigBean systemConfigBean;
	@Resource
	RegisterUserBean registerUserBean;
	@Resource
	MyWalletBean walletBean;

	/**
	 * 转入进来时，插入一条记录 保存转入记录
	 * 
	 * @param usersId
	 *            用户ID
	 * @param status
	 *            状态 -1:无效;0:未发息,1:发息中,2:已转出
	 * @param inMoney
	 *            转入金额
	 * @param leftMoney
	 *            剩余金额
	 * @return
	 */
	public String saveShiftTo(Long usersId, String status, Double inMoney, Double leftMoney) {
		ShiftTo shiftTo = new ShiftTo();
		shiftTo.setInsertTime(new Date());
		shiftTo.setInMoney(inMoney);
		shiftTo.setLeftMoney(leftMoney);
		shiftTo.setStatus(status);
		shiftTo.setType("0");
		shiftTo.setUsersId(usersId);
		String id = dao.saveAndReturnId(shiftTo);
		return id;
	}

	/**
	 * 转入到零钱包，扣除账户里面的钱 判断是否存在零钱包记录
	 * 
	 */
	public String shift(Long usersId, Double inMoney) {
		synchronized (usersId + "") {
			SystemConfig systemConfig = systemConfigBean.findSystemConfig();
			Users users = registerUserBean.getUsersById(usersId);
			UsersInfo usersInfo = users.getUsersInfo();
			ApplicationContext context = ApplicationContexts.getContexts();
			// SessionFactory sf = (SessionFactory)
			// context.getBean("sessionFactory");
			PlatformTransactionManager tm = (PlatformTransactionManager) context.getBean("transactionManager");
			TransactionStatus ts = tm.getTransaction(new DefaultTransactionDefinition());
			boolean flag = false;
			try {
				if (usersInfo.getLeftMoney() < inMoney) {
					return "您的账户余额不足";
				}
				if (walletBean.subLeftMoney(users.getId(), inMoney, "to", "零钱包转入", "零钱包转入")) {
					String shiftId = saveShiftTo(usersId, "0", inMoney, inMoney);
					if (!QwyUtil.isNullAndEmpty(shiftId)) {
						CoinPurse coinPurse = coinpPurseBean.findCoinPurseByUsersId(usersId);
						if (!QwyUtil.isNullAndEmpty(coinPurse)) {
							Double dou = coinPurse.getInMoney();
							cpfrBean.saveCoinPurseFundsRecord(usersId, inMoney, shiftId, "to", dou + inMoney);
							flag = coinpPurseBean.updateCoinPurse(systemConfig.getEarnings(), usersId, dou + inMoney, coinPurse.getInvestDay(), coinPurse.getPayInterest());
							flag = true;
						} else {
							String id = coinpPurseBean.saveCoinPurse(systemConfig.getEarnings(), usersId, inMoney);
							cpfrBean.saveCoinPurseFundsRecord(usersId, inMoney, shiftId, "to", inMoney);
							if (!QwyUtil.isNullAndEmpty(id)) {
								flag = true;
							}
						}

					}

				}
				if (flag) {
					tm.commit(ts);
					return "ok";
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage(), e);
			}
			tm.rollback(ts);
			return "error";
		}

	}

	/**
	 * 根据ID查询
	 * 
	 * @param id
	 * @return
	 */
	public ShiftTo findShiftToById(String id) {
		Object object = dao.findById(new ShiftTo(), id);
		if (!QwyUtil.isNullAndEmpty(object)) {
			return (ShiftTo) object;
		}
		return null;
	}

	/**
	 * 有金额转出时，修改记录 修改转入记录
	 * 
	 * @param id
	 *            转入ID
	 * @param status
	 *            状态 0:未发息,1:发息中,2:已转出
	 * @param leftMoney
	 *            剩余金额
	 * @return
	 */
	public boolean updateShiftTo(String id, String status, Double leftMoney) {
		ShiftTo shiftTo = findShiftToById(id);
		shiftTo.setUpdateTime(new Date());
		shiftTo.setLeftMoney(leftMoney);
		shiftTo.setStatus(status);
		dao.update(shiftTo);
		return true;
	}

	/**
	 * 根据用户ID查询转入记录
	 * 
	 * @param usersId
	 *            用户ID
	 * @param status
	 *            状态 0:未发息,1:发息中,2:已转出 多个值用","隔开
	 * @param insertTime
	 *            插入时间
	 * @param fx
	 *            与插入时间比较的方向 > 或< 或 =
	 * @param usersIds
	 *            用户ID集合
	 */
	public List<ShiftTo> findShiftTosByUsersId(Long usersId, String status, Date insertTime, String fx, String column, String order) {
		return findShiftTosByUsersId(usersId, status, insertTime, fx, column, order, null);

	}

	/**
	 * 根据用户ID查询转入记录
	 * 
	 * @param status
	 *            状态 0:未发息,1:发息中,2:已转出 多个值用","隔开
	 * @param insertTime
	 *            插入时间
	 * @param fx
	 *            与插入时间比较的方向 > 或< 或 =
	 * @param usersIds
	 *            用户ID集合
	 */
	public List<ShiftTo> findShiftTosByUsersId(String status, Date insertTime, String fx, String column, String order, String usersIds) {
		return findShiftTosByUsersId(null, status, insertTime, fx, column, order, usersIds);

	}

	/**
	 * 根据用户ID查询转入记录
	 * 
	 * @param usersId
	 *            用户ID
	 * @param status
	 *            状态 0:未发息,1:发息中,2:已转出 多个值用","隔开
	 * @param insertTime
	 *            插入时间
	 * @param fx
	 *            与插入时间比较的方向 > 或< 或 =
	 * @param usersIds
	 *            用户ID集合
	 */
	public List<ShiftTo> findShiftTosByUsersId(Long usersId, String status, Date insertTime, String fx, String column, String order, String usersIds) {
		StringBuffer hql = new StringBuffer();
		List<Object> obs = new ArrayList<Object>();
		hql.append(" FROM ShiftTo st WHERE 1=1 ");
		if (!QwyUtil.isNullAndEmpty(usersId)) {
			hql.append(" AND st.usersId = ? ");
			obs.add(usersId);
		}
		if (!QwyUtil.isNullAndEmpty(usersIds)) {
			hql.append(" AND st.usersId in (" + usersIds + ") ");
		}
		if (!QwyUtil.isNullAndEmpty(status)) {
			hql.append("  AND st.status in (" + status + ") ");
		}
		if (!QwyUtil.isNullAndEmpty(insertTime)) {
			hql.append("  AND st.insertTime  ");
			hql.append(fx);
			hql.append("?");
			obs.add(insertTime);
		}
		if (!QwyUtil.isNullAndEmpty(column) && !QwyUtil.isNullAndEmpty(order)) {
			hql.append(" ORDER BY st." + column + " " + order);
		} else {
			hql.append(" ORDER BY st.insertTime DESC ");
		}
		return dao.LoadAll(hql.toString(), obs.toArray());
	}

	/**
	 * 根据usersId查询需要发息的金额 发息的人是在1天前转入的记录
	 */
	public List<Object[]> findSendMoney(Integer currentPage, Integer pageSize) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT SUM(left_money),users_id FROM shift_to WHERE  DATE_FORMAT(insert_time, '%Y-%m-%d') <= DATE_SUB(DATE_FORMAT(NOW(), '%Y-%m-%d'),INTERVAL 1 DAY) GROUP BY users_id ");
		return dao.findAdvListSql(buffer.toString(), null, currentPage, pageSize);
	}

	/**
	 * 根据ID查询转入记录
	 * 
	 * @param Ids
	 *            ID集合
	 */
	public List<ShiftTo> findShiftTosByIds(String Ids) {
		StringBuffer hql = new StringBuffer();
		hql.append(" FROM ShiftTo st WHERE id in (" + Ids + ") ");
		return dao.LoadAll(hql.toString(), null);
	}
}
