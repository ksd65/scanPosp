package com.epay.scanposp.common.constant;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import com.epay.scanposp.common.utils.EnvironmentUtil;
/**
 * 提呗配置
 * @author linxf
 *
 */
public class RFConfig {
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
		ResourceBundle bundle = PropertyResourceBundle.getBundle(EnvironmentUtil.propertyPath + "rf_config");
		//测试环境参数
//		ResourceBundle bundle = PropertyResourceBundle.getBundle("mskeytest/ms_config");
		msServerUrl = bundle.getString("msServerUrl");
		shopServerUrl = bundle.getString("shopServerUrl");
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
