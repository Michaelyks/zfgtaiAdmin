package com.zfgt.admin.product.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.admin.product.dao.SendCouponDAO;
import com.zfgt.common.util.DESEncrypt;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.InterestCoupon;
import com.zfgt.orm.Users;

/**
 * 加息券后台管理
 * 
 * @author ChenJinHao
 *
 */
@Service
public class SendInterestCouponBean {
	private static Logger log = Logger.getLogger(SendInterestCouponBean.class);
	@Resource
	private SendCouponDAO dao;

	/**
	 * 
	 * @param interestCoupon
	 * @return
	 */
	public boolean sendInterestCoupon(InterestCoupon interestCoupon) {
		try {
			dao.saveOrUpdate(interestCoupon);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 
	 * @param pageUtil
	 * @param insertTime
	 * @param useTime
	 * @param username
	 * @param couponStatus
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageUtil<InterestCoupon> findInterestCoupons(PageUtil<InterestCoupon> pageUtil, String insertTime, String useTime, String usersname, String couponStatus) {
		try {
			ArrayList<Object> list = new ArrayList<Object>();
			StringBuffer hql = new StringBuffer();
			hql.append("FROM InterestCoupon cp WHERE 1=1 ");
			if (!QwyUtil.isNullAndEmpty(insertTime)) {
				String[] time = QwyUtil.splitTime(insertTime);
				if (time.length > 1) {
					hql.append(" AND cp.insertTime >= ? ");
					list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 00:00:00"));
					hql.append(" AND cp.insertTime <= ? ");
					list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[1] + " 23:59:59"));
				} else {
					hql.append(" AND cp.insertTime >= ? ");
					list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 00:00:00"));
					hql.append(" AND cp.insertTime <= ? ");
					list.add(QwyUtil.fmMMddyyyyHHmmss.parse(time[0] + " 23:59:59"));
				}
			}
			if (!QwyUtil.isNullAndEmpty(useTime)) {
				String[] userstime = QwyUtil.splitTime(useTime);
				if (userstime.length > 1) {
					hql.append(" AND cp.useTime >= ? ");
					list.add(QwyUtil.fmMMddyyyyHHmmss.parse(userstime[0] + " 00:00:00"));
					hql.append(" AND cp.useTime <= ? ");
					list.add(QwyUtil.fmMMddyyyyHHmmss.parse(userstime[1] + " 23:59:59"));
				} else {
					hql.append(" AND cp.useTime >= ? ");
					list.add(QwyUtil.fmMMddyyyyHHmmss.parse(userstime[0] + " 00:00:00"));
					hql.append(" AND cp.useTime <= ? ");
					list.add(QwyUtil.fmMMddyyyyHHmmss.parse(userstime[0] + " 23:59:59"));
				}
			}
			if (!QwyUtil.isNullAndEmpty(usersname)) {
				hql.append(" AND cp.users.username = ? ");
				list.add(DESEncrypt.jiaMiUsername(usersname));
			}
			if (!QwyUtil.isNullAndEmpty(couponStatus)) {
				hql.append(" AND cp.couponStatus = ? ");
				list.add(QwyUtil.converStrToInt(couponStatus, 0));
			}
			hql.append(" ORDER BY cp.insertTime DESC ");
			return dao.getByHqlAndHqlCount(pageUtil, hql.toString(), hql.toString(), list.toArray());
		} catch (Exception e) {
			log.error("操作异常: ", e);
		}
		return null;

	}

	/**
	 * 
	 * @param isBindBank
	 * @param couponType
	 * @param interestRate
	 * @param overTime
	 * @param isAlways
	 * @param note
	 * @param fromId
	 * @return
	 */
	public boolean sendInterestCouponByType(Integer isBindBank, Integer couponType, Double interestRate, Date overTime, int isAlways, String note, Long fromId) {
		StringBuffer sb = new StringBuffer();

		sb.append("insert into coupon_interest (id, usersId, couponType, interestRate, insertTime, isAlways, note, fromId)");
		sb.append("select uuid(), a.id, ");
		sb.append(couponType);
		sb.append(", ");
		sb.append(interestRate);
		sb.append(", ");
		if (!QwyUtil.isNullAndEmpty(overTime)) {
			sb.append(" '");
			sb.append(overTime);
			sb.append("' ");
		} else {
			sb.append(" null ");
		}
		sb.append(", ");
		sb.append(isAlways);
		sb.append(", '");
		sb.append(note);
		sb.append("', ");
		sb.append(fromId);
		sb.append(" from users a inner join users_info b on a.id = b.users_id where 1 = 1 ");

		if (!QwyUtil.isNullAndEmpty(isBindBank)) {
			sb.append("WHERE b.is_bind_bank =  ");
			sb.append(isBindBank);
		}

		String rets = dao.excuteSql(sb.toString(), null) + "";

		int ret = Integer.valueOf(rets);
		log.info("rets:" + rets);
		if (ret == 0) {

			return false;
		} else {
			return true;
		}
	}

	/**
	 * 根据用户名来查找用户;<br>
	 * 对传进来的值,已加密处理;
	 * 
	 * @param type
	 *            是否绑定银行卡;
	 * @return Usres
	 */
	@SuppressWarnings("unchecked")
	public List<Users> getUsersByType(String type) {
		try {
			ArrayList<Object> list = new ArrayList<Object>();
			StringBuffer hql = new StringBuffer();
			hql.append(" FROM Users us ");
			if (!QwyUtil.isNullAndEmpty(type)) {
				hql.append("WHERE us.usersInfo.isBindBank = ? ");
				list.add(type);
			}
			hql.append(" ORDER BY us.insertTime ");
			List<Users> usersList = dao.LoadAll(hql.toString(), list.toArray());
			return usersList;
		} catch (Exception e) {
			log.error("操作异常: ", e);
		}
		return null;

	}
}
