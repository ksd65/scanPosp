package com.epay.scanposp.common.constant;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import com.epay.scanposp.common.utils.EnvironmentUtil;
/**
 * 商盟配置
 * @author linxf
 *
 */
public class SMConfig {
	public static String msServerUrl;
	
	public static String agentServerUrl;
	
	static {
		//生产环境参数
		ResourceBundle bundle = PropertyResourceBundle.getBundle(EnvironmentUtil.propertyPath + "sm_config");
		//测试环境参数
		msServerUrl = bundle.getString("msServerUrl");
		agentServerUrl = bundle.getString("agentServerUrl");
	}
}
