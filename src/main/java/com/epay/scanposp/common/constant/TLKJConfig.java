package com.epay.scanposp.common.constant;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import com.epay.scanposp.common.utils.EnvironmentUtil;
/**
 * 通联快捷配置
 * @author linxf
 *
 */
public class TLKJConfig {
	public static String orgNo;
	public static String privateKey;
	public static String serverUrl;
	public static String rsaPrivateKey;
	public static String rsaPublicKey;
	static {
		
		ResourceBundle bundle = PropertyResourceBundle.getBundle(EnvironmentUtil.propertyPath + "tlkj_config");
		orgNo = bundle.getString("orgNo");
		privateKey = bundle.getString("privateKey");
		serverUrl = bundle.getString("serverUrl");
		rsaPrivateKey = bundle.getString("rsaPrivateKey");
		rsaPublicKey = bundle.getString("rsaPublicKey");
	}
}
