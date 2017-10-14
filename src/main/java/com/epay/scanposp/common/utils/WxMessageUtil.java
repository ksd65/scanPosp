package com.epay.scanposp.common.utils;

import java.util.Date;

import org.junit.Test;

import com.epay.scanposp.common.constant.WxConfig;
import com.google.common.cache.Weigher;

import net.sf.json.JSONObject;

public class WxMessageUtil {
	private static String accessToken;
	private static Date updateDate;
	//accessToken过期时间 一般为7200秒，此处从接口中获取
	private static Integer expiresSeconds;

	/**
	 * 根据appId和secret获取accessToken
	 * @param appId
	 * @param appSecret
	 * @return
	 */
	private static String getAccessToken(String appId, String appSecret) {
		if (accessToken == null) {// 从数据库查询
			generateAccessToken(appId,appSecret);
			return accessToken;
		}
		if ((new Date().getTime()) > (updateDate.getTime() + expiresSeconds * 1000)) {// token的过期时间为两小时
			generateAccessToken(appId,appSecret);
		}
		return accessToken;
	}
	
	/**
	 * 接口获取accessToken
	 * @param appId
	 * @param appSecret
	 */
	private static void generateAccessToken(String appId, String appSecret){
		String result = HttpUtil.sendGetRequest("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId + "&secret=" + appSecret);
		JSONObject resultJson = JSONObject.fromObject(result);
		if (resultJson.has("access_token")) {
			accessToken = resultJson.getString("access_token");
			updateDate = new Date();
			expiresSeconds = resultJson.getInt("expires_in");
		}
	}
	
	
	/**
	 * 收款通知
	 * @param appId
	 * @param appSecret
	 * @param opendId
	 * @param title
	 * @param merchantName
	 * @param money
	 * @param payTime
	 * @param remark
	 * @return
	 */
	public static boolean sendTmpMsgSKTZ(String opendId,String title,String merchantName,String money,String payTime,String remark){
		String appId = WxConfig.appid;
		String appSecret = WxConfig.appSecret;
		String defaultTemplateId = WxConfig.defaultTemplateId;
		JSONObject msgPackage=new JSONObject();
		msgPackage.put("touser", opendId);
		msgPackage.put("template_id", defaultTemplateId);
		msgPackage.put("topcolor", "#FF0000");
		
		JSONObject datasJson=new JSONObject();
		JSONObject titleJson=new JSONObject();
		titleJson.put("value", title);
		titleJson.put("color", "#173177");
		
		JSONObject merchantJson=new JSONObject();
		merchantJson.put("value", merchantName);
		merchantJson.put("color", "#173177");
		
		JSONObject moneyJson=new JSONObject();
		moneyJson.put("value", money);
		moneyJson.put("color", "#173177");
		
		JSONObject timeJson=new JSONObject();
		timeJson.put("value", payTime);
		timeJson.put("color", "#173177");
		
		JSONObject remarkJson=new JSONObject();
		remarkJson.put("value", remark);
		remarkJson.put("color", "#173177");
		
		datasJson.put("first", titleJson);
		datasJson.put("keyword1", merchantJson);
		datasJson.put("keyword2", moneyJson);
		datasJson.put("keyword3", timeJson);
		datasJson.put("remark", remarkJson);
		msgPackage.put("data", datasJson);
		String result=HttpUtil.sendPostRequest("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+WxMessageUtil.getAccessToken(appId, appSecret), msgPackage.toString());
		JSONObject resultObject=JSONObject.fromObject(result);
		if(resultObject.has("errcode")&&"0".equals(resultObject.getString("errcode"))){
			return true;
		}else{
			System.out.println("发送微信消息失败----------------------------------------"+result);
			return false;
		}
	}
	
	/**
	 * OEM厂商模板消息
	 * @param sysOfficeId
	 * @param opendId
	 * @param title
	 * @param merchantName
	 * @param money
	 * @param payTime
	 * @param remark
	 * @return
	 */
	public static boolean sendTmpMsgSKTZOEM(String accessToken,String templateId,String opendId,String title,String merchantName,String money,String payTime,String remark){
		JSONObject msgPackage=new JSONObject();
		msgPackage.put("touser", opendId);
		msgPackage.put("template_id", templateId);
		msgPackage.put("topcolor", "#FF0000");
		
		JSONObject datasJson=new JSONObject();
		JSONObject titleJson=new JSONObject();
		titleJson.put("value", title);
		titleJson.put("color", "#173177");
		
		JSONObject merchantJson=new JSONObject();
		merchantJson.put("value", merchantName);
		merchantJson.put("color", "#173177");
		
		JSONObject moneyJson=new JSONObject();
		moneyJson.put("value", money);
		moneyJson.put("color", "#173177");
		
		JSONObject timeJson=new JSONObject();
		timeJson.put("value", payTime);
		timeJson.put("color", "#173177");
		
		JSONObject remarkJson=new JSONObject();
		remarkJson.put("value", remark);
		remarkJson.put("color", "#173177");
		
		datasJson.put("first", titleJson);
		datasJson.put("keyword1", merchantJson);
		datasJson.put("keyword2", moneyJson);
		datasJson.put("keyword3", timeJson);
		datasJson.put("remark", remarkJson);
		msgPackage.put("data", datasJson);
		String result=HttpUtil.sendPostRequest("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+accessToken, msgPackage.toString());
		JSONObject resultObject=JSONObject.fromObject(result);
		if(resultObject.has("errcode")&&"0".equals(resultObject.getString("errcode"))){
			return true;
		}else{
			System.out.println("发送微信消息失败----------------------------------------"+result);
			return false;
		}
	}
	
	@Test
	public void test(){
		String opendId = "o2Zl1wY-ou8Gbvrcm1KGJMDiSpZA";
		String appId = "wx21453c4a598edd93";
		String appSecret = "a4bac8df717d1844c4112ff632a71a5b";
		String title = "测试商户收款通知"; 
		String merchantName = "测试商户"; 
		String money = "0.01"; 
		String payTime = "2017-04-20 00:28:59"; 
		String remark = "备注信息"; 
		sendTmpMsgSKTZ(opendId, title, merchantName, money, payTime, remark);
	}
}
