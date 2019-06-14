package com.gdp.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * springInt 辅助类
 * 使用该类可以在普通类中调用 service 层的方法
 * 
 * @author Jashon
 * @since 2018-06-27
 */
public class SpringInit implements ServletContextListener {
	
	private static WebApplicationContext springContext;
	
	public SpringInit(){
		super();
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		springContext = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext()); 
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}
	
	/**
	 * 提供一个静态方法供调用 springContext
	 * @return
	 */
	public static ApplicationContext getSpringContext() {
		return springContext;
	}

}
