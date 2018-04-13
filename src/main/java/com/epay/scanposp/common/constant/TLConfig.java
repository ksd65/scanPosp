package com.epay.scanposp.common.constant;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import com.epay.scanposp.common.utils.EnvironmentUtil;
/**
 * 通联
 * @author linxf
 *
 */
public class TLConfig {
	public static String msServerUrl;
	static {
		//生产环境参数
		ResourceBundle bundle = PropertyResourceBundle.getBundle(EnvironmentUtil.propertyPath + "tl_config");
		msServerUrl = bundle.getString("msServerUrl");
	}
}
