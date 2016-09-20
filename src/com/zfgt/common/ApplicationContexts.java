
package com.zfgt.common;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Administrator
 * Description:
 */
public class ApplicationContexts {

	private static ApplicationContext contexts = new ClassPathXmlApplicationContext(new String[] {"applicationContext.xml"});

	/**
	 * @return contexts
	 */
	public static ApplicationContext getContexts() {
		return contexts;
	}

	/**
	 * @param contexts 要设置的 contexts
	 */
	public static void setContexts(ApplicationContext contexts) {
		ApplicationContexts.contexts = contexts;
	}
	
	public static void main(String[] args) {
			//在main方法里面加载spring容器;
			ApplicationContext context = ApplicationContexts.getContexts();
			
	}

}
