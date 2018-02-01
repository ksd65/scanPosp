package com.epay.scanposp.common.constant;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import com.epay.scanposp.common.utils.EnvironmentUtil;
/**
 * 环迅配置
 * @author linxf
 *
 */
public class HXConfig {
	public static String payURL;
	public static String privateKey;
	public static String ipsMerCode;
	public static String ipsMerName;
	public static String ipsMerAccount;
	public static String frontUrl;
	static {
		//生产环境参数
		ResourceBundle bundle = PropertyResourceBundle.getBundle(EnvironmentUtil.propertyPath + "hx_config");
		payURL = bundle.getString("PayURL");
		privateKey = bundle.getString("privateKey");
		ipsMerCode = bundle.getString("ipsMerCode");
		ipsMerName = bundle.getString("ipsMerName");
		ipsMerAccount = bundle.getString("ipsMerAccount");
		frontUrl = bundle.getString("frontUrl");
	}
}
