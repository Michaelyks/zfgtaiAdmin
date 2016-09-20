package com.zfgt.thread.action;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.admin.product.bean.QieBean;
import com.zfgt.admin.product.bean.SendCouponBean;
import com.zfgt.admin.product.dao.QieDAO;
import com.zfgt.common.util.PageUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.login.bean.RegisterUserBean;
import com.zfgt.orm.ShangyouQie;
import com.zfgt.orm.Users;

/**
 * 后台线程自动计算收益<br>
 * 
 * @author qwy
 *
 * @createTime 2015-4-28上午9:42:57
 */
@Service(value = "qieThread")
public class QieThread implements Runnable {
	private Logger log = Logger.getLogger(QieThread.class);
	@Resource
	private SendCouponBean sendCouponBean;
	@Resource
	RegisterUserBean registerUserBean;
	@Resource
	QieBean qieBean;
	@Resource
	QieDAO dao;

	private Integer pageSize = 50;

	@Override
	public void run() {
		try {
			log.info("打企鹅自动发放投资券");
			int currentPage = 0;
			for (;;) {
				currentPage++;
				Date insertTime = QwyUtil.addDaysFromOldDate(new Date(), -1).getTime();
				PageUtil<ShangyouQie> pageUtil = new PageUtil<ShangyouQie>();
				pageUtil.setCurrentPage(currentPage);
				pageUtil.setPageSize(pageSize);
				pageUtil = qieBean.findPageUtilQie(pageUtil, insertTime);
				System.out.println(currentPage);
				log.info(currentPage);
				if (!QwyUtil.isNullAndEmpty(pageUtil) && !QwyUtil.isNullAndEmpty(pageUtil.getList())) {
					log.info(pageUtil.getCount());
					List<ShangyouQie> list = pageUtil.getList();
					if (!QwyUtil.isNullAndEmpty(list)) {
						log.info("发放投资券人数" + list.size());
						for (int i = 0; i < list.size(); i++) {
							ShangyouQie qie = list.get(i);
							Users users = registerUserBean.getUsersByUsername(qie.getPhone());
							if (!QwyUtil.isNullAndEmpty(users)) {
								Double score = 0D;
								if (!QwyUtil.isNullAndEmpty(qie.getScore())) {
									score = Double.parseDouble(qie.getScore());
								}
								Double conpon = qieBean.qieConPon(score);
								if (conpon > 0) {
									qie.setStaus("1");
									qie.setUpdateTime(new Date());
									dao.update(qie);
									Date overTime = QwyUtil.addDaysFromOldDate(new Date(), 10).getTime();
									overTime = QwyUtil.fmyyyyMMdd.parse(QwyUtil.fmyyyyMMdd.format(overTime));
									sendCouponBean.sendHongBao(users.getId(), conpon * 100, overTime, "0", -1, "打企鹅自动发送投资券");
								}
							}
						}
					}
				} else {
					break;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("操作异常: ", e);
		}
	}

	public static void main(String[] args) {
		Date time = null;
		try {
			time = QwyUtil.fmyyyyMMdd.parse(QwyUtil.fmyyyyMMdd.format(new Date()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(QwyUtil.fmyyyyMMdd.format(new Date()));
		System.out.println("000000" + QwyUtil.fmyyyyMMdd.format(time));
	}

}
