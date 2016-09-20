package com.zfgt.filter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.zfgt.common.util.PropertiesUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.Modul;
import com.zfgt.orm.RolesRight;
import com.zfgt.orm.SystemConfig;
import com.zfgt.thread.action.AutoSendRatesThread;
import com.zfgt.thread.action.AutoShiftTohread;
import com.zfgt.thread.action.AutoStatsOperateThread;
import com.zfgt.thread.action.ChannelOperateThread;
import com.zfgt.thread.action.FinishCouponThread;
import com.zfgt.thread.action.FinishInterestCouponThread;
import com.zfgt.thread.action.IphoneOperateThread;
import com.zfgt.thread.action.LianLianCzThread;
import com.zfgt.thread.action.MeowCurrencyReportThread;
import com.zfgt.thread.action.QieThread;
import com.zfgt.thread.action.SendCoinPurseRatesThread;
import com.zfgt.thread.action.SendProfitThread;
import com.zfgt.thread.action.UpdateProductThread;
import com.zfgt.thread.action.WyCzRecordThread;
import com.zfgt.thread.dao.ThreadDAO;

public class MyServletContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("MyServletContextListener_contextDestroyed");
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		try {
			System.out.println("初始化监听......");
			WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(arg0.getServletContext());
			ThreadDAO threadDAO = (ThreadDAO) context.getBean("threadDAO");
			SystemConfig systemConfig = threadDAO.getSystemConfig();
			arg0.getServletContext().setAttribute("systemConfig", systemConfig);

			// 获取所有权限;
			List<Modul> listModul = threadDAO.getModul();
			arg0.getServletContext().setAttribute("listModul", listModul);

			// 获取所有用户的一级标题权限;
			List<RolesRight> firstRolesRight = threadDAO.getFirstRolesRight();
			arg0.getServletContext().setAttribute("firstRolesRight", firstRolesRight);

			// 获取用户的权限;
			List<RolesRight> listRolesRight = threadDAO.getRolesRight();
			arg0.getServletContext().setAttribute("listRolesRight", listRolesRight);

			Object isStartThread = PropertiesUtil.getProperties("isStartThread");
			if (!QwyUtil.isNullAndEmpty(isStartThread) && "1".equals(isStartThread.toString())) {
				System.out.println("启动后台线程;");
				/*
				 * 后台线程启动;
				 */
				// 创建线程池50个大小;
				ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(50);

				/*
				 * 后台线程自动更新理财产品;改变理财产品的状态; 使项目(常规产品,product.productType
				 * ==0)进入【已售罄】或【结算中】 使项目(新手专享产品,product.productType == 1)进入【已售罄】
				 */
				UpdateProductThread updateProductThread = (UpdateProductThread) context.getBean("updateProductThread");

				// 对用户进行收益的发放;对利息表进行按要求发放收益;
				//
				SendProfitThread sendProfitThread = (SendProfitThread) context.getBean("sendProfitThread");

				// 后台线程扫描过期投资券;Thread;入口 状态 0未使用,1未用完,2已用完,3已过期 把到期的投资券更改状态为 3;
				FinishCouponThread finishCouponThread = (FinishCouponThread) context.getBean("finishCouponThread");

				// 后台线程扫描充值记录;Thread
				WyCzRecordThread wyCzRecordThread = (WyCzRecordThread) context.getBean("wyCzRecordThread");

				// 自动算息
				//
				AutoSendRatesThread autoSendRatesThread = (AutoSendRatesThread) context.getBean("autoSendRatesThread");
				// 发息
				//
				SendCoinPurseRatesThread sendCoinPurseRatesThread = (SendCoinPurseRatesThread) context.getBean("sendCoinPurseRatesThread");

				// 发息
				QieThread qieThread = (QieThread) context.getBean("qieThread");
				MeowCurrencyReportThread meowCurrencyReportThread = (MeowCurrencyReportThread) context.getBean("meowCurrencyReportThread");

				// 后台每日投资统计线程（平台）
				AutoStatsOperateThread autoStatsOperateThread = (AutoStatsOperateThread) context.getBean("autoStatsOperateThread");

				// 后台每日投资统计线程（安卓渠道）
				ChannelOperateThread channelOperateThread = (ChannelOperateThread) context.getBean("channelOperateThread");
				// 后台每日投资统计线程（安卓渠道）
				IphoneOperateThread iphoneOperateThread = (IphoneOperateThread) context.getBean("iphoneOperateThread");
				//
				FinishInterestCouponThread finishInterestCouponThread = (FinishInterestCouponThread) context.getBean("finishInterestCouponThread");

				scheduler.scheduleAtFixedRate(updateProductThread, 5, 30, TimeUnit.MINUTES);
				//
				scheduler.scheduleAtFixedRate(sendProfitThread, 10, 60, TimeUnit.MINUTES);
				scheduler.scheduleAtFixedRate(finishCouponThread, 15, 60, TimeUnit.MINUTES);
				scheduler.scheduleAtFixedRate(wyCzRecordThread, 0, 5, TimeUnit.MINUTES);
				//
				//
				scheduler.scheduleAtFixedRate(autoSendRatesThread, 5, 10, TimeUnit.MINUTES);
				//
				scheduler.scheduleAtFixedRate(sendCoinPurseRatesThread, 4, 10, TimeUnit.MINUTES);
				long oneDay = 24 * 60 * 60 * 1000;
				long initDelay = getTimeMilli("00:00:00") - System.currentTimeMillis();
				initDelay = initDelay > 0 ? initDelay : oneDay + initDelay;
				scheduler.scheduleAtFixedRate(meowCurrencyReportThread, initDelay, oneDay, TimeUnit.MILLISECONDS);
				scheduler.scheduleAtFixedRate(qieThread, 0, 5, TimeUnit.MINUTES);
				LianLianCzThread lianLianCzThread = (LianLianCzThread) context.getBean("lianLianCzThread");

				scheduler.scheduleAtFixedRate(lianLianCzThread, 0, 31, TimeUnit.MINUTES);

				scheduler.scheduleAtFixedRate(autoStatsOperateThread, initDelay, oneDay, TimeUnit.MILLISECONDS);

				scheduler.scheduleAtFixedRate(channelOperateThread, initDelay, oneDay, TimeUnit.MILLISECONDS);
				
				scheduler.scheduleAtFixedRate(iphoneOperateThread, initDelay, oneDay, TimeUnit.MILLISECONDS);

				scheduler.scheduleAtFixedRate(finishInterestCouponThread, 15, 60, TimeUnit.MINUTES);

			} else {
				System.out.println("不启动后台线程;");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static long getTimeMilli(String time) {
		try {
			DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
			DateFormat dayFormat = new SimpleDateFormat("yy-MM-dd");
			Date curDate = dateFormat.parse(dayFormat.format(new Date()) + " " + time);
			return curDate.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static void main(String[] args) throws ParseException {
		/*
		 * TimerTask task = new TimerTask() {
		 * 
		 * @Override public void run() { System.out.println("进来了_当前系统时间: "
		 * +QwyUtil.fmyyyyMMddHHmmss.format(new Date())); try {
		 * Thread.sleep(7000); } catch (Exception e) { // TODO Auto-generated
		 * catch block e.printStackTrace(); } System.out.println(
		 * "暂停5秒之后当前系统时间: "+QwyUtil.fmyyyyMMddHHmmss.format(new Date())); } };
		 * 
		 * Timer time = new Timer(); time.schedule(task,
		 * QwyUtil.fmyyyyMMddHHmmss.parse("2015-06-11 14:45:00"),3000);
		 */
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(50);
		scheduler.scheduleAtFixedRate(new RunTexst(), 0, 2, TimeUnit.SECONDS);
	}

}

class RunTexst implements Runnable {
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("进来了_当前系统时间: " + QwyUtil.fmyyyyMMddHHmmss.format(new Date()));
		try {
			// Thread.sleep(5000);
			System.out.println("中间");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("暂停5秒之后当前系统时间: " + QwyUtil.fmyyyyMMddHHmmss.format(new Date()));
	}

}