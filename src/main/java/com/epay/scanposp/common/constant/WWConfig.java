package com.epay.scanposp.common.constant;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import com.epay.scanposp.common.utils.EnvironmentUtil;

public class WWConfig {
	public static String msServerUrl;
	public static String officeId;
	public static String privateKey;
	public static String platPublicKey;
	static {
		//生产环境参数
		ResourceBundle bundle = PropertyResourceBundle.getBundle(EnvironmentUtil.propertyPath + "ww_config");
		msServerUrl = bundle.getString("msServerUrl");
		officeId = bundle.getString("officeId");
		privateKey = bundle.getString("privateKey");
		platPublicKey = bundle.getString("platPublicKey");
	}
}
