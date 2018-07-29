package com.epay.scanposp.common.constant;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import com.epay.scanposp.common.utils.EnvironmentUtil;

public class KQConfig {
	public static String payServerUrl;
	public static String queryServerUrl;
	
	static {
		//生产环境参数
		ResourceBundle bundle = PropertyResourceBundle.getBundle(EnvironmentUtil.propertyPath + "kq_config");
		//测试环境参数
		queryServerUrl = bundle.getString("queryServerUrl");
		payServerUrl = bundle.getString("payServerUrl");
		
		
	}
}
