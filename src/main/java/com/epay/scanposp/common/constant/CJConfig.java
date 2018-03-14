package com.epay.scanposp.common.constant;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import com.epay.scanposp.common.utils.EnvironmentUtil;
/**
 * 畅捷配置
 * @author linxf
 *
 */
public class CJConfig {
	public static String msServerUrl;
	public static String gateServerUrl;
	public static String frontUrl;
	static {
		
		ResourceBundle bundle = PropertyResourceBundle.getBundle(EnvironmentUtil.propertyPath + "cj_config");
		msServerUrl = bundle.getString("msServerUrl");
		gateServerUrl = bundle.getString("gateServerUrl");
		frontUrl = bundle.getString("frontUrl");
		
	}
}
