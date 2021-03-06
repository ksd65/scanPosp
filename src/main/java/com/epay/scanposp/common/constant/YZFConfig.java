package com.epay.scanposp.common.constant;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import com.epay.scanposp.common.utils.EnvironmentUtil;

public class YZFConfig {
	public static String msServerUrl;
	public static String orgNo;
	public static String rsaPrivateKey;
	public static String defaultMerNo;
	public static String defaultMd5Key;
	public static String branchId;
	
	static {
		//生产环境参数
		ResourceBundle bundle = PropertyResourceBundle.getBundle(EnvironmentUtil.propertyPath + "yzf_config");
		msServerUrl = bundle.getString("msServerUrl");
		orgNo = bundle.getString("orgNo");
		rsaPrivateKey = bundle.getString("rsaPrivateKey");
		defaultMerNo = bundle.getString("defaultMerNo");
		defaultMd5Key = bundle.getString("defaultMd5Key");
		branchId = bundle.getString("branchId");
	}
}
