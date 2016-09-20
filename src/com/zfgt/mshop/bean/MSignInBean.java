package com.zfgt.mshop.bean;

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

import com.zfgt.common.ApplicationContexts;
import com.zfgt.common.bean.PlatformBean;
import com.zfgt.common.util.LockHolder;
import com.zfgt.common.util.MySynchronized;
import com.zfgt.common.util.OrderNumerUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.login.bean.RegisterUserBean;
import com.zfgt.mshop.dao.MSignInDAO;
import com.zfgt.orm.MCoinIncome;
import com.zfgt.orm.MCoinRecord;
import com.zfgt.orm.MSignIn;
import com.zfgt.orm.UsersInfo;

/**
 * 喵币签到的Bean层;<br>
 * 
 * @author 覃文勇
 * @createTime 2015-10-28上午9:27:18
 */
@Service
public class MSignInBean {
	private Logger log = Logger.getLogger(MSignInBean.class);
	@Resource
	private MSignInDAO dao;
	@Resource
	private RegisterUserBean registerUserBean;
	@Resource
	private PlatformBean platformBean;

	/**
	 * 判断用户当天是否签到
	 * 
	 * @param uid
	 * @param insertTime
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public MSignIn isValidMSignIn(long uid, Date insertTime) throws Exception {
		MSignIn mSignIn = new MSignIn();
		ArrayList<Object> ob = new ArrayList<Object>();
		StringBuffer buffer = new StringBuffer();
		buffer.append(" FROM MSignIn ms where 1=1 ");
		if (!QwyUtil.isNullAndEmpty(uid)) {
			buffer.append(" AND ms.usersId=? ");
			ob.add(uid);
		}
		if (!QwyUtil.isNullAndEmpty(insertTime)) {
			String time = QwyUtil.fmyyyyMMdd.format(insertTime);
			buffer.append(" AND ms.insertTime >= ? ");
			ob.add(QwyUtil.fmyyyyMMddHHmmss.parse(time + " 00:00:00"));
			buffer.append(" AND ms.insertTime <= ? ");
			ob.add(QwyUtil.fmyyyyMMddHHmmss.parse(time + " 23:59:59"));
		}
		List<MSignIn> list = dao.LoadAll(buffer.toString(), ob.toArray());
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				mSignIn = list.get(0);
			}
		}
		return mSignIn;
	}

	/**
	 * 保存签到
	 * 
	 * @param uid
	 * @param type
	 *            签到来源（1:PC端 2:WAP端 3:移动端）
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> saveMSignIn(long uid, String type) throws Exception {

		log.info("MSignInBean.saveMSignIn 进入签到得喵币");

		Map<String, Object> map = new HashMap<String, Object>();

		Date date = new Date();
		int day = date.getDate();
		MSignIn LastMSignIn = new MSignIn();
		if (day > 1) {
			Date lastDate = QwyUtil.addDaysFromOldDate(date, -1).getTime();
			// 昨天是否签到
			LastMSignIn = isValidMSignIn(uid, lastDate);
		}
		MySynchronized.lock("signUid" + uid);
		synchronized (LockHolder.getLock(uid)) {
			log.info("再锁定用户: " + uid);

			// 验证当天是否已签到
			MSignIn nowMSignIn = isValidMSignIn(uid, new Date());

			if (!QwyUtil.isNullAndEmpty(nowMSignIn.getId())) {
				map.put("end", "no");
				map.put("message", "您今日已完成签到");
				log.info(map);
				return map;
			}

			ApplicationContext context = ApplicationContexts.getContexts();
			PlatformTransactionManager tm = (PlatformTransactionManager) context.getBean("transactionManager");
			TransactionStatus ts = tm.getTransaction(new DefaultTransactionDefinition());

			try {
				MSignIn mSignIn = new MSignIn();
				mSignIn.setUsersId(uid);
				mSignIn.setType(type);// 签到来源（1:PC端 2:WAP端 3:移动端）
				mSignIn.setStatus("1");// 状态 0：未签到 1：已签到
				mSignIn.setInsertTime(new Date());

				if (!QwyUtil.isNullAndEmpty(LastMSignIn.getId())) {// 倒推前一天是否签到，如果签到了，即在连续天数上追加一天
					mSignIn.setLianxuDay(LastMSignIn.getLianxuDay() + 1L);
				} else {
					mSignIn.setLianxuDay(1l);
				}
				mSignIn.setNote("完成签到，获得3个喵币");
				// 获取签到喵币
				MCoinIncome mCoinIncome = saveMCoinIncome(uid, mSignIn);
				if (!QwyUtil.isNullAndEmpty(mCoinIncome.getId())) {
					// 添加用户总喵币
					UsersInfo usersInfo = registerUserBean.updateCoinByUid(uid, +mCoinIncome.getCoin());
					if (usersInfo == null) {
						tm.rollback(ts);
						log.info("MSignInBean.saveMSignIn 数据回滚: 签到时添加用户总喵币");
						return null;
					} else {
						if (QwyUtil.isNullAndEmpty(usersInfo.getTotalPoint())) {
							usersInfo.setTotalPoint(0l);
						}
						// 保存喵币流水记录
						saveMCoinRecord(mCoinIncome, usersInfo.getTotalPoint());
						// 保存签到记录
						dao.save(mSignIn);
						tm.commit(ts);
						map.put("end", "ok");
						map.put("message", "签到成功");
						map.put("note", mSignIn.getNote());
						map.put("totalPoint", usersInfo.getTotalPoint() + "");
						return map;
					}

				} else {
					tm.rollback(ts);
					log.info("MSignInBean.saveMSignIn 数据回滚: 签到增加喵币失败");
					return null;
				}

			} catch (Exception e) {
				log.error("操作异常: ", e);
				tm.rollback(ts);
				log.info("MCoinPayBean.SaveMCoinPay 数据回滚: 扣除喵币异常;");
				return null;
			} finally {
				// 解锁
				MySynchronized.unLock("signUid" + uid);

			}
		}
	}

	/**
	 * 签到获得喵币收入
	 * 
	 * @param uid
	 *            用户id
	 * @return
	 * @throws Exception
	 */
	public MCoinIncome saveMCoinIncome(long uid, MSignIn mSignIn) throws Exception {
		long coin = 3L;
		MCoinIncome mCoinIncome = new MCoinIncome();
		mCoinIncome.setUsersId(uid);
		mCoinIncome.setRecordNumber(OrderNumerUtil.generateRequestId());// 生成流水号
		mCoinIncome.setNote("签到");
		coin = countCoinBySignIn(mCoinIncome, mSignIn, coin, uid);
		mCoinIncome.setCoin(coin);
		mCoinIncome.setType("1");
		mCoinIncome.setStatus("0");
		mCoinIncome.setInsertTime(mSignIn.getInsertTime());
		dao.save(mCoinIncome);
		return mCoinIncome;
	}

	/**
	 * 计算签到获得的额外喵币
	 * 
	 * @param mCoinIncome
	 * @param date
	 * @param coin
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Long countCoinBySignIn(MCoinIncome mCoinIncome, MSignIn mSignIn, long coin, long uid) throws Exception {
		try {
			StringBuffer buffer = new StringBuffer();
			ArrayList<Object> ob = new ArrayList<Object>();
			buffer.append(" FROM MSignIn ms WHERE ms.status=1 ");
			if (!QwyUtil.isNullAndEmpty(uid)) {
				buffer.append(" AND ms.users.id=? ");
				ob.add(uid);
			}
			// 查询本月已签到列表
			if (!QwyUtil.isNullAndEmpty(mSignIn.getInsertTime())) {
				String yymm = QwyUtil.fmyyMM.format(mSignIn.getInsertTime());
				String yymmdd = QwyUtil.fmyyyyMMdd.format(mSignIn.getInsertTime());
				buffer.append(" AND ms.insertTime >= ? ");
				ob.add(QwyUtil.fmyyyyMMddHHmmss.parse(yymm + "-01 00:00:00"));
				buffer.append(" AND ms.insertTime <= ? ");
				ob.add(QwyUtil.fmyyyyMMddHHmmss.parse(yymmdd + " 00:00:00"));
			}
			List<MSignIn> list = dao.LoadAll(buffer.toString(), ob.toArray());
			if (list != null && list.size() > 0) {
				int i = list.size();
				if (i == 6) {// 本月签到7次，额外奖励10个喵币
					coin += 10;
					mCoinIncome.setNote("累计已签到7次，获得13个喵币。");
					mSignIn.setNote("累计签到7次,获得13个喵币。");
				}
				if (i == 14) {// 本月签到15次，额外奖励10个喵币
					coin += 10;
					mCoinIncome.setNote("累计已签到15次，获得13个喵币。");
					mSignIn.setNote("累计签到15次,获得13个喵币。");
				}
				if (i == 27) {// 本月签到28次，额外奖励10个喵币
					coin += 10;
					mCoinIncome.setNote("累计签到28次,获得13个喵币。");
					mSignIn.setNote("累计签到28次,获得13个喵币。");
				}
				// 本月连续签到25次，额外奖励30喵币
				if (mSignIn.getLianxuDay() == 25l) {
					coin = 33l;
					mCoinIncome.setNote("本月连续签到25次，获得33喵币。");
					mSignIn.setNote("本月连续签到25次，获得33喵币。");
				}
			}
			return coin;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 保存签到流水记录
	 * 
	 * @param mCoinIncome
	 * @param totalCoin
	 * @return
	 */
	public String saveMCoinRecord(MCoinIncome mCoinIncome, long totalCoin) {
		MCoinRecord record = new MCoinRecord();
		record.setCoinType("2");
		record.setRecordId(mCoinIncome.getRecordNumber());
		record.setType(mCoinIncome.getType());
		record.setStatus(mCoinIncome.getStatus());
		record.setCoin(mCoinIncome.getCoin());
		record.setUsersId(mCoinIncome.getUsersId());
		record.setNote("签到");
		record.setTotalCoin(totalCoin);
		record.setInsertTime(mCoinIncome.getInsertTime());
		// 修改平台发放总喵币
		platformBean.updateTotalCoin(mCoinIncome.getCoin());
		return dao.saveAndReturnId(record);
	}

	/**
	 * 加载签到列表
	 * 
	 * @param uid
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<MSignIn> loadMSignInList(long uid, int currentPage, int pageSize) {
		try {
			ArrayList<Object> ob = new ArrayList<Object>();
			StringBuffer buff = new StringBuffer();
			buff.append("FROM MSignIn ms where 1=1 ");

			if (!(QwyUtil.isNullAndEmpty(uid))) {
				buff.append("AND ms.users.id = ? ");
				ob.add(uid);
			}
			if (QwyUtil.isNullAndEmpty(currentPage)) {
				currentPage = 1;
			}
			if (QwyUtil.isNullAndEmpty(pageSize)) {
				pageSize = 20;
			}
			buff.append(" ORDER BY ms.insertTime DESC ");
			return dao.findAdvList(buff.toString(), ob.toArray(), currentPage, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 筛选获取喵币签到列表组
	 */
	public List<Map<String, Object>> filterMSignInListGroup(List<MSignIn> mSignIns) throws Exception {
		// 以能否兑换为key,List为集合
		List<Map<String, Object>> listMap = new ArrayList<>();
		if (!QwyUtil.isNullAndEmpty(mSignIns)) {
			if (mSignIns == null || mSignIns.size() == 0) {
				return null;
			}
			for (int i = 0; i < mSignIns.size(); i++) {
				MSignIn mSignIn = mSignIns.get(i);
				listMap.add(filterMSignInList(mSignIn));
			}
		}
		return listMap;
	}

	/**
	 * 筛选获取喵币签到字段
	 */
	public Map<String, Object> filterMSignInList(MSignIn mSignIn) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", mSignIn.getId());
		map.put("status", mSignIn.getStatus());
		map.put("note", mSignIn.getNote());
		map.put("insertTime", mSignIn.getInsertTime());
		return map;
	}

	/**
	 * 
	 * @param usersId
	 * @return
	 */
	public Map<String, Object> findMSignInAndMCoinByUid(Long usersId) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(
					"select a.lianxu_day, DATE_FORMAT(insert_time,'%Y-%m-%d') insertDay, DATE_FORMAT(now(),'%Y-%m-%d') today, DATE_FORMAT(DATE_SUB(now(),INTERVAL 1 day),'%Y-%m-%d') yesterday from m_sign_in a where a.users_id = ");
			sb.append(usersId);
			sb.append(" order by a.id desc limit 1");
			List<Map<String, String>> list = dao.LoadAllListMapSql(sb.toString(), null);
			if (list != null && list.size() > 0) {
				String insd = list.get(0).get("insertDay");
				String td = list.get(0).get("today");
				String yd = list.get(0).get("yesterday");

				if (insd.equals(td)) {
					returnMap.put("isSignIn", true);
					returnMap.put("lianxu_day", list.get(0).get("lianxu_day"));
				} else if (insd.equals(yd)) {
					returnMap.put("lianxu_day", list.get(0).get("lianxu_day"));
				} else {
					returnMap.put("lianxu_day", 0);
				}
			}
			sb =null;
			list = null;
			sb = new StringBuffer();
			sb.append("select count(id) count from m_sign_in where users_id = ");
			sb.append(usersId);
			list = dao.LoadAllListMapSql(sb.toString(), null);
			if (list != null && list.size() > 0) {
				returnMap.put("totalSignInCount", list.get(0).get("count"));
			}
			sb =null;
			list = null;
			sb = new StringBuffer();
			sb.append("select total_point from users_info where users_id = ");
			sb.append(usersId);
			list = dao.LoadAllListMapSql(sb.toString(), null);
			if (list != null && list.size() > 0) {
				returnMap.put("total_point", list.get(0).get("total_point"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnMap;
	}
}
