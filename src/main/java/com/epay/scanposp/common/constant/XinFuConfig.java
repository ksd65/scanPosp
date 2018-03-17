package com.epay.scanposp.common.constant;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import com.epay.scanposp.common.utils.EnvironmentUtil;
/**
 * 芯富配置
 * @author linxf
 *
 */
public class XinFuConfig {
	public static String msServerUrl;
	public static String frontUrl;
	public static String orgNo;
	public static String md5Key;
	public static String ptform;
	static {
		
		ResourceBundle bundle = PropertyResourceBundle.getBundle(EnvironmentUtil.propertyPath + "xinfu_config");
		msServerUrl = bundle.getString("msServerUrl");
		frontUrl = bundle.getString("frontUrl");
		orgNo = bundle.getString("orgNo");
		md5Key = bundle.getString("md5Key");
		ptform = bundle.getString("ptform");
	}
}
