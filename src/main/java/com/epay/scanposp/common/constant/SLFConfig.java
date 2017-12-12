package com.epay.scanposp.common.constant;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import com.epay.scanposp.common.utils.EnvironmentUtil;
/**
 * 随乐付配置
 * @author linxf
 *
 */
public class SLFConfig {
	public static String payURL;
	public static String merchantFrontEndUrl;
	static {
		//生产环境参数
		ResourceBundle bundle = PropertyResourceBundle.getBundle(EnvironmentUtil.propertyPath + "slf_config");
		//测试环境参数
//		ResourceBundle bundle = PropertyResourceBundle.getBundle("mskeytest/ms_config");
		payURL = bundle.getString("PayURL");
		merchantFrontEndUrl = bundle.getString("MerchantFrontEndUrl");
		
	}
}
