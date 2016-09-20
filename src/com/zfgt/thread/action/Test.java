package com.zfgt.thread.action;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
	public static void updateDB(){
		System.out.println("1");
		ApplicationContext context=new ClassPathXmlApplicationContext(new String[] {"applicationContext.xml"});
		ClearingProductThread clearingProductThread = (ClearingProductThread) context.getBean("clearingProductThread");
		clearingProductThread.run();
		System.out.println("ok");
	}
}
