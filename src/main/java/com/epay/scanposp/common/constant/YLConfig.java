package com.epay.scanposp.common.constant;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import com.epay.scanposp.common.utils.EnvironmentUtil;

public class YLConfig {
	public static String rsaPublicKeyFilePath4httpsipay;
	public static String rsaPublicKeyFilePath;
	public static String rsaPrivateKeyFilePath;
	public static String msServerUrl;
	public static String shopServerUrl;
	public static String cooperator_t0;
	public static String cooperator_t1;
	public static String subAppid;
	public static String userIDJF;
	public static String userIDWJF;
	public static String orderId;
	public static String jfsignKey;
	public static String wjfsignKey;
	public static String bindAccUrl;
	public static String payUrl;
	public static String bgUrl;
	public static String pageUrl;
	public static String agentId;
	public static String yhPublicKey;
	public static String privateKey;
	public static String jsDomain;
	static {
		//生产环境参数
		ResourceBundle bundle = PropertyResourceBundle.getBundle(EnvironmentUtil.propertyPath + "yl_config");
		//测试环境参数
//		ResourceBundle bundle = PropertyResourceBundle.getBundle("mskeytest/ms_config");
		rsaPublicKeyFilePath = bundle.getString("rsaPublicKeyFilePath");
		rsaPublicKeyFilePath4httpsipay = bundle.getString("rsaPublicKeyFilePath4httpsipay");
		rsaPrivateKeyFilePath = bundle.getString("rsaPrivateKeyFilePath");
		msServerUrl = bundle.getString("msServerUrl");
		shopServerUrl = bundle.getString("shopServerUrl");
		cooperator_t0 = bundle.getString("cooperator_t0");
		cooperator_t1 = bundle.getString("cooperator_t1");
		subAppid = bundle.getString("subAppid");
		userIDJF=bundle.getString("userIDJF");
		userIDWJF=bundle.getString("userIDWJF");
		orderId=bundle.getString("orderId");
		jfsignKey=bundle.getString("jfsignKey");
		wjfsignKey=bundle.getString("wjfsignKey");
		bindAccUrl=bundle.getString("bindAccUrl");
		payUrl=bundle.getString("payUrl");
		bgUrl=bundle.getString("bgUrl");
		pageUrl=bundle.getString("pageUrl");
		agentId=bundle.getString("agentId");
		yhPublicKey=bundle.getString("yhPublicKey");
		privateKey=bundle.getString("privateKey");
		jsDomain=bundle.getString("jsDomain");
	}
}
