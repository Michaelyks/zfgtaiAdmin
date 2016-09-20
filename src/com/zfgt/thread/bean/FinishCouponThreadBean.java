package com.zfgt.thread.bean;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.Coupon;
import com.zfgt.thread.dao.ThreadDAO;

/**
 * 后台线程扫描过期投资券;Bean层;过期时间,精确到秒<br>
 * 状态 0未使用,1未用完,2已用完,3已过期<br>
 * 把到期的投资券更改状态为 3;
 * 
 * @author qwy
 *
 * @createTime 2015-05-23 12:32:58
 */
@Service
public class FinishCouponThreadBean {
	private Logger log = Logger.getLogger(FinishCouponThreadBean.class);
	@Resource
	private ThreadDAO dao;

	@SuppressWarnings("unchecked")
	public PageUtil<Coupon> getInterestDetailsByPageUtil(PageUtil<Coupon> pageUtil) {
		StringBuffer hql = new StringBuffer();
		hql.append(" FROM Coupon cou ");
		hql.append(" WHERE cou.status IN ('0','1') AND ");
		hql.append(" cou.overTime <= DATE_FORMAT(NOW(),'%Y-%m-%d 23:59:59')");
		hql.append(" ORDER BY cou.insertTime ASC,cou.id ASC ");
		return (PageUtil<Coupon>) dao.getPage(pageUtil, hql.toString(), null);

	}

	/**
	 * 把投资券设置为已过期;
	 * 
	 * @param list
	 *            投资券集合;
	 */
	public void finishCoupon(List<Coupon> list) {
		if (!QwyUtil.isNullAndEmpty(list)) {
			for (Coupon coupon : list) {
				coupon.setStatus("3");
				coupon.setUpdateTime(new Date());
			}
			dao.updateList(list);
		}
		log.debug("finishCoupon_" + list.size());
	}
}
