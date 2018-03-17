package com.epay.scanposp.common.constant;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import com.epay.scanposp.common.utils.EnvironmentUtil;
/**
 * 易生
 * @author linxf
 *
 */
public class YSConfig {
	public static String msServerUrl;
	public static String orgNo;
	public static String privateKey;
	public static String defaultMerNo;
	public static String defaultMerNoQpay;
	public static String defaultMerNoApay;
	static {
		//生产环境参数
		ResourceBundle bundle = PropertyResourceBundle.getBundle(EnvironmentUtil.propertyPath + "ys_config");
		msServerUrl = bundle.getString("msServerUrl");
		orgNo = bundle.getString("orgNo");
		privateKey = bundle.getString("privateKey");
		defaultMerNo = bundle.getString("defaultMerNo");
		defaultMerNoQpay = bundle.getString("defaultMerNoQpay");
		defaultMerNoApay = bundle.getString("defaultMerNoApay");
	}
}
