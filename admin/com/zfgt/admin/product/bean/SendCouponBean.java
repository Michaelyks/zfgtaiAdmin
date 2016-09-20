package com.zfgt.admin.product.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.account.bean.UserRechargeBean;
import com.zfgt.admin.product.dao.SendCouponDAO;
import com.zfgt.common.bean.PlatformBean;
import com.zfgt.common.util.DESEncrypt;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.Coupon;
import com.zfgt.orm.Users;

/**
 * 后天管理--发送红包;
 * 
 * @author qwy
 *
 * @createTime 2015-4-27上午11:44:54
 */
@Service
public class SendCouponBean {
	private static Logger log = Logger.getLogger(SendCouponBean.class);
	@Resource
	private SendCouponDAO dao;
	@Resource
	private UserRechargeBean userRechargeBean;
	@Resource
	private PlatformBean platformBean;

	/**
	 * 发放红包;
	 * 
	 * @param usersId
	 *            红包所属用户
	 * @param money
	 *            红包金额,单位(分)
	 * @param overTime
	 *            过期时间;//如果过期时间为null,则红包属于长期有效;否则,有有效时间限制;
	 * @param type
	 *            类别 "新手红包"
	 * @param fromId
	 *            发红包者的id;一般为管理员账户的id; -1为线程自动发放id;
	 * @param note
	 *            备注
	 * @return boolean; true:发放成功; false:发放失败;
	 */
	public boolean sendHongBao(long usersId, double money, Date overTime, String type, long fromId, String note) {
		return userRechargeBean.sendHongBao(usersId, money, overTime, type, fromId, note);
	}

	/**
	 * 发送红包
	 * 
	 * @param money
	 *            红包金额,单位(分)
	 * @param overTime
	 *            过期时间;//如果过期时间为null,则红包属于长期有效;否则,有有效时间限制;
	 * @param type
	 *            类别 "新手红包"
	 * @param fromId
	 *            发红包者的id;一般为管理员账户的id; -1为线程自动发放id;
	 * @param note
	 *            备注
	 * @return boolean; true:发放成功; false:发放失败;
	 */
	public boolean sendHongBao(String isBindBank, double money, Date overTime, String type, long fromId, String note) {
		try {
			List<Users> errorlist = new ArrayList<Users>();
			List<Users> list = getUsersByType(isBindBank);
			if (!QwyUtil.isNullAndEmpty(list)) {
				for (Users users : list) {
					if (!userRechargeBean.sendHongBao(users.getId(), money, overTime, type, fromId, note)) {
						errorlist.add(users);
					}
				}
				// TODO 目测一下代码实际过程中会报错
				if (!QwyUtil.isNullAndEmpty(errorlist)) {
					for (Users users : errorlist) {
						errorlist.remove(users);
						if (!userRechargeBean.sendHongBao(users.getId(), money, overTime, type, fromId, note)) {
							errorlist.add(users);
						}
					}

				}
				if (!QwyUtil.isNullAndEmpty(list)) {
					return true;
				}
			}
		} catch (Exception e) {
			log.error("操作异常: ", e);
		}
		return false;
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

	/**
	 * 根据用户名来查找用户;<br>
	 * 对传进来的值,已加密处理;
	 * 
	 * @param username
	 *            用户名;
	 * @return Usres
	 */
	public Users getUsersByUsername(String username) {
		try {
			StringBuffer hql = new StringBuffer();
			hql.append("FROM Users us ");
			hql.append("WHERE us.username = ? ");
			hql.append("OR us.phone = ? ");
			return (Users) dao.findUniqueResult(hql.toString(), new Object[] { DESEncrypt.jiaMiUsername(username.toLowerCase()), DESEncrypt.jiaMiUsername(username.toLowerCase()) });
		} catch (Exception e) {
			log.error("操作异常: ", e);
		}
		return null;
	}

	/**
	 * 获取红包
	 * 
	 * @param pageUtil
	 * @param insertTime
	 *            发送红包时间
	 * @param useTime
	 *            使用时间
	 * @param usersname
	 *            用户名
	 * @param status
	 *            状态 0未使用,1未用完,2已用完,3已过期
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageUtil<Coupon> findCoupons(PageUtil<Coupon> pageUtil, String insertTime, String useTime, String usersname, String status) {
		try {
			ArrayList<Object> list = new ArrayList<Object>();
			StringBuffer hql = new StringBuffer();
			hql.append("FROM Coupon cp WHERE 1=1 ");
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
			if (!QwyUtil.isNullAndEmpty(status)) {
				hql.append(" AND cp.status = ? ");
				list.add(status);
			}
			hql.append(" ORDER BY cp.insertTime DESC ");
			return dao.getByHqlAndHqlCount(pageUtil, hql.toString(), hql.toString(), list.toArray());
		} catch (Exception e) {
			log.error("操作异常: ", e);
		}
		return null;

	}
}
