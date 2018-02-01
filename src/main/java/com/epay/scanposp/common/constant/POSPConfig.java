package com.epay.scanposp.common.constant;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import com.epay.scanposp.common.utils.EnvironmentUtil;

public class POSPConfig {
	public static String msServerUrl;
	
	static {
		//生产环境参数
		ResourceBundle bundle = PropertyResourceBundle.getBundle(EnvironmentUtil.propertyPath + "posp_config");
		//测试环境参数
//		ResourceBundle bundle = PropertyResourceBundle.getBundle("mskeytest/ms_config");
		msServerUrl = bundle.getString("msServerUrl");
		
	}
}
