package com.epay.scanposp.common.constant;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;

import com.epay.scanposp.common.utils.EnvironmentUtil;

public class WxConfig {
 public static String appid;
 public static String appidSH;
 public static String appSecret;
 public static String authDirectory;
 public static String defaultTemplateId;
 
	/**
	 * 初始化系统配置
	 */
	static  {
		try {
			Configuration configuration = new PropertiesConfiguration(EnvironmentUtil.propertyPath + "wx_config.properties");
			appid = configuration.getString("appid");
			appidSH = configuration.getString("appidSH");
			appSecret = configuration.getString("appSecret");
			authDirectory = configuration.getString("authDirectory");
			defaultTemplateId = configuration.getString("defaultTemplateId");
		} catch (Exception e) {
			throw new RuntimeException("[加载配置文件失败]", e);
		}
	}
}
