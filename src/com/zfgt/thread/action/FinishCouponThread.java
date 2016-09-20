package com.zfgt.thread.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.Coupon;
import com.zfgt.thread.bean.FinishCouponThreadBean;

/**
 * 后台线程扫描过期投资券;Thread;入口<br>
 * 状态 0未使用,1未用完,2已用完,3已过期<br>
 * 把到期的投资券更改状态为 3;
 * 
 * @author qwy
 *
 * @createTime 2015-05-23 12:12:16
 */
@Service
public class FinishCouponThread implements Runnable {
	private Logger log = Logger.getLogger(FinishCouponThread.class);
	@Resource
	private FinishCouponThreadBean bean;

	@Override
	public void run() {
		try {
			log.info("进入后台线程....扫描过期的投资券...");
			PageUtil<Coupon> pageUtil = new PageUtil<Coupon>();
			pageUtil.setPageSize(50);
			int currentPage = 0;
			for (;;) {
				currentPage++;
				pageUtil.setCurrentPage(currentPage);
				pageUtil = bean.getInterestDetailsByPageUtil(pageUtil);
				List<Coupon> listCoupon = pageUtil.getList();
				if (QwyUtil.isNullAndEmpty(listCoupon)) {
					log.info("没有已过期的投资券: " + currentPage);
					break;
				}
				if (!QwyUtil.isNullAndEmpty(listCoupon)) {
					log.info("进入后台线程....有需要清理的过期投资券..." + listCoupon.size() + "个");
					bean.finishCoupon(listCoupon);
				}
			}
		} catch (Exception e) {
			log.error("操作异常: ", e);
			log.error("进入后台线程....异常", e);
		} catch (Error e) {
			log.error("操作异常: ", e);
			log.error("进入后台线程....error异常", e);
		}
	}

}
