package com.zfgt.thread.action;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.common.util.QwyUtil;
import com.zfgt.thread.bean.AutoStatsOperateThreadBean;

/**
 * 
 * 每日运营统计线程
 * 
 * @author chenjinhao
 *
 */
@Service(value = "autoStatsOperateThread")
public class AutoStatsOperateThread implements Runnable {

	private Logger log = Logger.getLogger(AutoStatsOperateThread.class);

	@Resource
	private AutoStatsOperateThreadBean bean;

	private String inDayStr = "yesterday";

	public String getInDayStr() {
		return inDayStr;
	}

	public void setInDayStr(String inDayStr) {
		this.inDayStr = inDayStr;
	}

	@Override
	public synchronized void run() {
		long startTime = System.currentTimeMillis();
		log.info("==========每日运营数据统计线程开始==========");
		try {
			String dayStr = null;

			if (StringUtils.isBlank(inDayStr)) {
				inDayStr = "all";
			}

			if (inDayStr.equals("all")) {
				dayStr = null;
			} else if (inDayStr.equals("yesterday")) {
				Calendar sss = QwyUtil.addDaysFromOldDate(new Date(), -1);
				Date yesterday = sss.getTime();
				dayStr = QwyUtil.fmyyyyMMdd.format(yesterday);
			} else if (inDayStr.equals("today")) {
				dayStr = QwyUtil.fmyyyyMMdd.format(new Date());
			} else if (StringUtils.isNotBlank(inDayStr)) {
				dayStr = inDayStr;
			}

			bean.addStatsOperateRegist(dayStr);
			bean.addStatsOperateRecharge(dayStr);
			bean.addStatsOperateWithdraw(dayStr);
			bean.addStatsOperateInvest(dayStr);
			bean.addStatsOperateRepayment(dayStr);
			log.info("==========每日运营数据统计线程正常结束==========");
		} catch (Exception e) {
			log.info("==========每日运营数据统计线程异常结束==========");
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("autoStatsOperateThread:执行时间" + (endTime - startTime) + "ms");
		inDayStr = "yesterday";
	}

}
