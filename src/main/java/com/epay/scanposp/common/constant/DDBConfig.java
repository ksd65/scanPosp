package com.epay.scanposp.common.constant;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import com.epay.scanposp.common.utils.EnvironmentUtil;

public class DDBConfig {
	public static String msServerUrl;
	public static String queryServerUrl;
	
	static {
		//生产环境参数
		ResourceBundle bundle = PropertyResourceBundle.getBundle(EnvironmentUtil.propertyPath + "ddb_config");
		msServerUrl = bundle.getString("msServerUrl");
		queryServerUrl = bundle.getString("queryServerUrl");
		
	}
}
