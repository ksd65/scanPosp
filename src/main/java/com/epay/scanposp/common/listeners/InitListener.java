package com.epay.scanposp.common.listeners;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;



public class InitListener implements ServletContextListener {
	/**web实际路径*/
	private static String webRealPath;
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("=========微信线程池已注销============");
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		WebApplicationContext context = WebApplicationContextUtils
				.getWebApplicationContext(sce.getServletContext());

		
		// 初始化web实际路径
		try {
			webRealPath = context.getResource("").getFile().getAbsolutePath();
		    ServletContext servletContext = sce.getServletContext(); 
		    WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext); 
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		
	}
	
	/**
	 * 获取web的实际路径
	 * @return
	 */
	public static String getWebRealPath(){
		return webRealPath;
	}
}
