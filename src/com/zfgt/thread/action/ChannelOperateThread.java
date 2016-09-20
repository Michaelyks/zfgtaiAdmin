package com.zfgt.thread.action;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zfgt.common.util.QwyUtil;
import com.zfgt.thread.bean.ChannelOperateThreadBean;

/**每日单个渠道数据统计线程（安卓）
 * @author 覃文勇
 * @createTime 2016-2-25下午5:52:06
 */
@Service(value = "channelOperateThread")
public class ChannelOperateThread implements Runnable {

	private Logger log = Logger.getLogger(ChannelOperateThread.class);

	@Resource
	private ChannelOperateThreadBean bean;
	
	private String inDayStr = "yesterday";

	public String getInDayStr() {
		return inDayStr;
	}

	public void setInDayStr(String inDayStr) {
		this.inDayStr = inDayStr;
	}

	@Override
	public void run() {
		long startTime = System.currentTimeMillis();
		log.info("==========每日单个渠道数据统计线程==========");
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
			
			bean.addChannelOperateRegist(dayStr);
			bean.addChannelOperateRecharge(dayStr);
			bean.addChannelOperateWithdraw(dayStr);
			bean.addChannelOperateInvest(dayStr);
			bean.addChannelOperateRepayment(dayStr);
			log.info("==========每日单个渠道数据统计线程==========");
		} catch (Exception e) {
			log.info("==========每日单个渠道数据统计线程==========");
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("ChannelOperateThread:执行时间" + (endTime - startTime) + "ms");
	}

}
