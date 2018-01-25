package com.epay.scanposp.common.utils;

public class DeviceRequestUtil {
	
	/*
	 * 获取浏览器信息
	 */
	public static String getBrowser(String agent){
		agent = agent.toLowerCase();
		if(agent.indexOf("micromessenger")>0){
			return "微信浏览器";
		}else if(agent.indexOf("mqqbrowser")>0){
			return "QQ浏览器";
		}else if(agent.indexOf("ucweb")>0 || agent.indexOf("ucbrowser")>0){
			return "UC浏览器";
		}else if(agent.indexOf("oppobrowser")>0){
			return "OPPO浏览器";
		}else if(agent.indexOf("vivobrowser")>0){
			return "Vivo浏览器";
		}else if(agent.indexOf("samsungbrowser")>0){
			return "三星浏览器";
		}else if(agent.indexOf("miuibrowser")>0){
			return "小米浏览器";
		}else if(agent.indexOf("baidubrowser")>0){
			return "百度浏览器";
		}else if(agent.indexOf("sogoumobilebrowser")>0){
			return "搜狗浏览器";
		}else if(agent.indexOf("msie 7")>0){
			return "ie7";
		}else if(agent.indexOf("msie 8")>0){
			return "ie8";
		}else if(agent.indexOf("msie 9")>0){
			return "ie9";
		}else if(agent.indexOf("msie 10")>0){
			return "ie10";
		}else if(agent.indexOf("msie")>0){
			return "ie";
		}else if(agent.indexOf("opera")>0){
			return "opera";
		}else if(agent.indexOf("firefox")>0){
			return "firefox";
		}else if(agent.indexOf("gecko")>0 && agent.indexOf("rv:11")>0){
			return "ie11";
		}else if(agent.indexOf("chrome")>0){
			return "Chrome";
		}else if(agent.indexOf("safari")>0){
			return "safari";
		}else{
			return "Others";
		}
	}
	
	public static String getDevice(String agent){
		if(agent.indexOf("iPhone")>0){
			return "iPhone";
		}else if(agent.indexOf("Android")>0){
			return "Android";
		}else if(agent.indexOf("iPad")>0){
			return "iPad";
		}else{
			return "Others";
		}
	}

}
