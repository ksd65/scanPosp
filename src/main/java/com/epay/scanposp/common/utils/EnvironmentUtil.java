package com.epay.scanposp.common.utils;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class EnvironmentUtil {
	public static String propertyPath = "";
	private static String environmentConfig = "";

	private static final String PRODUCT_ENVIRONMENT = "develop";
	private static final String EPAY_PRODUCT_ENVIRONMENT = "epayPro";
	private static final String JOHGU_PRODUCT_ENVIRONMENT = "johuPro";
	
	static{
		ResourceBundle bundle = PropertyResourceBundle.getBundle("sys_environment");
		environmentConfig = bundle.getString("environmentConfig");
		if(EPAY_PRODUCT_ENVIRONMENT.equals(environmentConfig)){
			propertyPath = bundle.getString("configPathEpayPro");
		}else if(JOHGU_PRODUCT_ENVIRONMENT.equals(environmentConfig)){
			propertyPath = bundle.getString("configPathJohuPro");
		}else if(PRODUCT_ENVIRONMENT.equals(environmentConfig)){
			propertyPath = bundle.getString("configPathDevelop");
		}
		
	}

}
