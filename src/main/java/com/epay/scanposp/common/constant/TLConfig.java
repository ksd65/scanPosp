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
	public static String agentId;
	public static String keyStorePath;
	public static String keyStorePassword;
	public static String alias;
	public static String aliasPassword;
	public static String msServerUrlNew;
	public static String agentPayUrl;
	public static String pfxFileName;
	public static String pfxPassword;
	public static String cerFileName;
	static {
		//生产环境参数
		ResourceBundle bundle = PropertyResourceBundle.getBundle(EnvironmentUtil.propertyPath + "tl_config");
		msServerUrl = bundle.getString("msServerUrl");
		agentId = bundle.getString("agentId");
		keyStorePath = bundle.getString("keyStorePath");
		keyStorePassword = bundle.getString("keyStorePassword");
		alias = bundle.getString("alias");
		aliasPassword = bundle.getString("aliasPassword");
		msServerUrlNew = bundle.getString("msServerUrlNew");
		agentPayUrl = bundle.getString("agentPayUrl");
		pfxFileName = bundle.getString("pfxFileName");
		pfxPassword = bundle.getString("pfxPassword");
		cerFileName = bundle.getString("cerFileName");
	}
}
