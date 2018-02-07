package com.epay.scanposp.common.constant;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import com.epay.scanposp.common.utils.EnvironmentUtil;

public class HLBConfig {
	public static String msServerUrl;
	public static String agentPayUrl;
	public static String rsaPublicKey;
	public static String rsaPrivateKey;
	static {
		//生产环境参数
		ResourceBundle bundle = PropertyResourceBundle.getBundle(EnvironmentUtil.propertyPath + "hlb_config");
		//测试环境参数
//		ResourceBundle bundle = PropertyResourceBundle.getBundle("mskeytest/ms_config");
		msServerUrl = bundle.getString("msServerUrl");
		agentPayUrl = bundle.getString("agentPayUrl");
		rsaPublicKey = bundle.getString("rsaPublicKey");
		rsaPrivateKey = bundle.getString("rsaPrivateKey");
	}
}
