package com.epay.scanposp.common.constant;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;

import com.epay.scanposp.common.utils.EnvironmentUtil;

public class SysConfig {
 public static float rate;
 public static String serverUrl;
 public static String epayCodeUrl;
 public static String smsUsername;
 public static String smsPassword;
 public static String smsFrom;
 public static String smsFromName;
 public static String defaultQrcodeBasePath;
 public static String prefixMemberCode;
 public static String passCode;
 public static String baseUploadFilePath;
 public static String frontUrl;
 public static String platPrivateKey;
 public static String platPublicKey;
 public static String kjRouteCode;
	/**
	 * 初始化系统配置
	 */
	static  {
		try {
			Configuration configuration = new PropertiesConfiguration(EnvironmentUtil.propertyPath + "sys_config.properties");
			rate = configuration.getFloat("rate");
			serverUrl = configuration.getString("serverUrl");
			epayCodeUrl = configuration.getString("epayCodeUrl");
			smsUsername = configuration.getString("smsUsername");
			smsPassword = configuration.getString("smsPassword");
			smsFrom = configuration.getString("smsFrom");
			smsFromName = configuration.getString("smsFromName");
			defaultQrcodeBasePath = configuration.getString("defaultQrcodeBasePath");
			prefixMemberCode = configuration.getString("prefixMemberCode");
			passCode = configuration.getString("passCode");
			baseUploadFilePath = configuration.getString("baseUploadFilePath");
			frontUrl = configuration.getString("frontUrl");
			platPrivateKey = configuration.getString("platPrivateKey");
			platPublicKey = configuration.getString("platPublicKey");
			kjRouteCode = configuration.getString("kjRouteCode");
		} catch (Exception e) {
			throw new RuntimeException("[加载配置文件失败]", e);
		}
	}
}
