package com.epay.scanposp.common.constant;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import com.epay.scanposp.common.utils.EnvironmentUtil;
/**
 * 米联配置
 * @author linxf
 *
 */
public class MLConfig {
	public static String shopServerUrl;
	public static String payServerUrl;
	public static String orgNo;
	public static String privateKey;
	static {
		
		ResourceBundle bundle = PropertyResourceBundle.getBundle(EnvironmentUtil.propertyPath + "ml_config");
		shopServerUrl = bundle.getString("shopServerUrl");
		payServerUrl = bundle.getString("payServerUrl");
		orgNo = bundle.getString("orgNo");
		privateKey = bundle.getString("privateKey");
		
	}
}
