package com.zfgt.thread.action;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.context.ApplicationContext;

import com.zfgt.common.ApplicationContexts;
public class ThreadMain {
	/*static{
		System.out.println("加载...");
		// 开启事务;
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[]
		{"applicationContext.xml",});
		ApplicationContexts.setContexts(context);
		System.out.println("加载完");
	}*/
	
	public void startTime(){
		//在main方法里面加载spring容器;
		ApplicationContext context = ApplicationContexts.getContexts();
		//net.wwwyibu.controller.ApplicationContexts.setContexts(context);
		
		/*CalculateInterestThread interest = (CalculateInterestThread) context.getBean("calculateInterestThread");
		interest.run();*/
		
		/*CalculateInterestNewThread calculateInterestNewThread=(CalculateInterestNewThread) context.getBean("calculateInterestNewThread");
		calculateInterestNewThread.run();*/
		
		//执行多线程计划,创建5个大小的线程池;
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);
		
		//获得需要的实体类,该实体需要被注解;
		UpdateProductThread updateProductThread = (UpdateProductThread) context.getBean("updateProductThread");
		
		//获得需要的实体类,该实体需要被注解;
		ClearingProductThread clearingProductThread = (ClearingProductThread) context.getBean("clearingProductThread");
		
		//获得需要的实体类,该实体需要被注解;
		ClearingProductFreshmanThread clearingProductFreshmanThread = (ClearingProductFreshmanThread) context.getBean("clearingProductFreshmanThread");
		
		//获得需要的实体类,该实体需要被注解;
		SendProfitThread sendProfitThread = (SendProfitThread) context.getBean("sendProfitThread");
		
		//获得需要的实体类,该实体需要被注解;
		FinishCouponThread finishCouponThread = (FinishCouponThread) context.getBean("finishCouponThread");
		
		//获得需要的实体类,该实体需要被注解;
		TxQueryThread txQueryThread = (TxQueryThread) context.getBean("txQueryThread");
		
		
		/*//设置访问线程的周期时间
		//scheduleAtFixedRate(要访问的实体, 延迟长时间后访问, 多久访问一次, TimeUnit.SECONDS);
		//自动更新理财产品线程----理财产品自动进入结算中;
		scheduler.scheduleAtFixedRate(updateProductThread, 3, 60, TimeUnit.SECONDS);
		//自动结算理财产品线程----理财产品自动结算,返款给用户;
		scheduler.scheduleAtFixedRate(clearingProductThread, 3, 60, TimeUnit.SECONDS);
		//自动结算新手专享理财产品线程----新手专享理财产品自动结算,返款给用户;
		scheduler.scheduleAtFixedRate(clearingProductFreshmanThread, 3, 60, TimeUnit.SECONDS);
		//自动发放收益;对InterestDetails表进行按要求发放;
		scheduler.scheduleAtFixedRate(sendProfitThread, 3, 60, TimeUnit.SECONDS);
		//自动扫描过期投资券;把到期的投资券的状态设置为"3";已过期
		scheduler.scheduleAtFixedRate(finishCouponThread, 3, 60, TimeUnit.SECONDS);*/
		//自动查询提现记录;是否已提现成功; 查询正在审核的提现记录;状态为"3"
		scheduler.scheduleAtFixedRate(txQueryThread, 0, 60, TimeUnit.SECONDS);
		
		LianLianCzThread lianLianCzThread = (LianLianCzThread) context.getBean("lianLianCzThread");
		
		scheduler.scheduleAtFixedRate(lianLianCzThread, 0, 31, TimeUnit.MINUTES);
		//获得需要的实体类,该实体需要被注解;
		//PlatformBean PlatformBean = (PlatformBean) context.getBean("platformBean");
		//Platform plat = PlatformBean.getPlatform();
		//System.out.println("平台融资ID: "+plat.getId());
	}
//	public static void main(String[] args) {
//		//在main方法里面加载spring容器;
//			ApplicationContext context = ApplicationContexts.getContexts();
//			//net.wwwyibu.controller.ApplicationContexts.setContexts(context);
//			
//			
//			/*CalculateInterestThread interest = (CalculateInterestThread) context.getBean("calculateInterestThread");
//			interest.run();*/
//			
//			/*CalculateInterestNewThread calculateInterestNewThread=(CalculateInterestNewThread) context.getBean("calculateInterestNewThread");
//			calculateInterestNewThread.run();*/
//			
//			//执行多线程计划,创建5个大小的线程池;
//			ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);
//			
//			//获得需要的实体类,该实体需要被注解;
//			UpdateProductThread updateProductThread = (UpdateProductThread) context.getBean("updateProductThread");
//			
//			//获得需要的实体类,该实体需要被注解;
//			ClearingProductThread clearingProductThread = (ClearingProductThread) context.getBean("clearingProductThread");
//			
//			//获得需要的实体类,该实体需要被注解;
//			ClearingProductFreshmanThread clearingProductFreshmanThread = (ClearingProductFreshmanThread) context.getBean("clearingProductFreshmanThread");
//			
//			//获得需要的实体类,该实体需要被注解;
//			SendProfitThread sendProfitThread = (SendProfitThread) context.getBean("sendProfitThread");
//			
//			//获得需要的实体类,该实体需要被注解;
//			FinishCouponThread finishCouponThread = (FinishCouponThread) context.getBean("finishCouponThread");
//			
//			//获得需要的实体类,该实体需要被注解;
//			TxQueryThread txQueryThread = (TxQueryThread) context.getBean("txQueryThread");
//			
//			
//			/*//设置访问线程的周期时间
//			//scheduleAtFixedRate(要访问的实体, 延迟长时间后访问, 多久访问一次, TimeUnit.SECONDS);
//			//自动更新理财产品线程----理财产品自动进入结算中;
//			scheduler.scheduleAtFixedRate(updateProductThread, 3, 60, TimeUnit.SECONDS);
//			//自动结算理财产品线程----理财产品自动结算,返款给用户;
//			scheduler.scheduleAtFixedRate(clearingProductThread, 3, 60, TimeUnit.SECONDS);
//			//自动结算新手专享理财产品线程----新手专享理财产品自动结算,返款给用户;
//			scheduler.scheduleAtFixedRate(clearingProductFreshmanThread, 3, 60, TimeUnit.SECONDS);
//			//自动发放收益;对InterestDetails表进行按要求发放;
//			scheduler.scheduleAtFixedRate(sendProfitThread, 3, 60, TimeUnit.SECONDS);
//			//自动扫描过期投资券;把到期的投资券的状态设置为"3";已过期
//			scheduler.scheduleAtFixedRate(finishCouponThread, 3, 60, TimeUnit.SECONDS);*/
//			//自动查询提现记录;是否已提现成功; 查询正在审核的提现记录;状态为"3"
//			scheduler.scheduleAtFixedRate(txQueryThread, 3, 60, TimeUnit.SECONDS);
//			
//			//获得需要的实体类,该实体需要被注解;
//			//PlatformBean PlatformBean = (PlatformBean) context.getBean("platformBean");
//			//Platform plat = PlatformBean.getPlatform();
//			//System.out.println("平台融资ID: "+plat.getId());
//			
//			
//	}

}
