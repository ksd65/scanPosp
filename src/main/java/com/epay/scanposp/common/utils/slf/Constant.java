package com.epay.scanposp.common.utils.slf;

import java.util.HashMap;
import java.util.Map;

public class Constant {
	/**
	 * 证件类型映射
	 */
	public static Map credentialTypeMap = new HashMap();
	/**
	 * 商户业务类型映射
	 */
	public static Map bizTypeMap = new HashMap();
	static {
		credentialTypeMap.put("01","身份证");
		credentialTypeMap.put("02","军官证");
		credentialTypeMap.put("03","护照");
		credentialTypeMap.put("04","回乡证");
		credentialTypeMap.put("05","台胞证");
		credentialTypeMap.put("06","警官证");
		credentialTypeMap.put("07","士兵证");
		credentialTypeMap.put("99","其他");
		
		bizTypeMap.put("01","团购");
		bizTypeMap.put("02","网络游戏");
		bizTypeMap.put("03","医疗保健");
		bizTypeMap.put("04","教育培训");
		bizTypeMap.put("05","日常用品");
		bizTypeMap.put("06","鲜花礼品");
		bizTypeMap.put("07","旅游票务");
		bizTypeMap.put("08","影视娱乐");
		bizTypeMap.put("09","支付宝业务");
		bizTypeMap.put("10","图书音像");
		bizTypeMap.put("11","全国公共事业缴费");
		bizTypeMap.put("12","电子产品");
		bizTypeMap.put("13","交通违章罚款");
		bizTypeMap.put("14","网站建设");
		bizTypeMap.put("15","信用卡还款");
		bizTypeMap.put("16","会员论坛");
		bizTypeMap.put("17","软件产品");
		bizTypeMap.put("18","身份证查询");
		bizTypeMap.put("19","购买支付通业务");
		bizTypeMap.put("20","运动休闲");
		bizTypeMap.put("21","彩票");
		bizTypeMap.put("22","数字点卡");
		bizTypeMap.put("23","其它");
	}
}
