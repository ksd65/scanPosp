package com.epay.scanposp.common.utils.hx;

import org.apache.commons.codec.digest.DigestUtils;


public class HxUtils {
	
	/**
	 * 验签
	 *
	 * @param xml
	 * @return
	 */
	public static boolean checkSign(String xml, String merCode, String directStr, String ipsRsaPub) {

		if (xml == null){
			return false;
		}
		String OldSign = getSign(xml); // 返回签名
		String text = getBodyXml(xml); // body
		System.out.println("MD5验签，验签文：" + text + "\n待比较签名值:" + OldSign);
		String retEncodeType =  getRetEncodeType(xml); //加密方式
		System.out.println("加密方式 ：" + retEncodeType);
		String result = null;
		if (OldSign == null || retEncodeType == null) {
			return false;
		}
		// 根据验签方式进行验签
		if (retEncodeType.equals("16")){
			return Verify.verifyMD5withRSA(ipsRsaPub, text + merCode, OldSign);
		} else if (retEncodeType.equals("17")){
			result = DigestUtils
					.md5Hex(Verify.getBytes(text + merCode + directStr,
							"UTF-8"));
		} else {
			return false;
		}
		if (result == null || !OldSign.equals(result)) {
			return false;
		}
		return true;
	}
	
	/**
	 * 获取报文中<Signature></Signature>部分
	 * @param xml
	 * @return
	 */
	public static String getSign(String xml) {
		int s_index = xml.indexOf("<Signature>");
		int e_index = xml.indexOf("</Signature>");
		String sign = null;
		if (s_index > 0) {
			sign = xml.substring(s_index + 11, e_index);
		}
		return sign;
	}

	/**
	 * 获取body部分
	 * @param xml
	 * @return
	 */
	public static String getBodyXml(String xml) {
		int s_index = xml.indexOf("<body>");
		int e_index = xml.indexOf("</body>");
		String sign = null;
		if (s_index > 0) {
			sign = xml.substring(s_index, e_index + 7);
		}
		return sign;
	}

	/**
	 * 获取报文中<RspCode></RspCode>部分
	 * @param xml
	 * @return
	 */
	public static String getRspCode(String xml) {
		int s_index = xml.indexOf("<RspCode>");
		int e_index = xml.indexOf("</RspCode>");
		String sign = null;
		if (s_index > 0) {
			sign = xml.substring(s_index + 9, e_index);
		}
		return sign;
	}

	/**
	 * 获取报文中<Status></Status>部分
	 * @param xml
	 * @return
	 */
	public static String getStatus(String xml) {
		int s_index = xml.indexOf("<Status>");
		int e_index = xml.indexOf("</Status>");
		String sign = null;
		if (s_index > 0) {
			sign = xml.substring(s_index + 8, e_index);
		}
		return sign;
	}

	/**
	 * 获取报文中<RetEncodeType></RetEncodeType>部分
	 * @param xml
	 * @return
	 */
	public static String getRetEncodeType(String xml) {
		int s_index = xml.indexOf("<RetEncodeType>");
		int e_index = xml.indexOf("</RetEncodeType>");
		String sign = null;
		if (s_index > 0) {
			sign = xml.substring(s_index + 15, e_index);
		}
		return sign;
	}

	/**
	 * 获取报文中<Amount></Amount>部分
	 * @param xml
	 * @return
	 */
	public static String getAmount(String xml) {
		int s_index = xml.indexOf("<Amount>");
		int e_index = xml.indexOf("</Amount>");
		String sign = null;
		if (s_index > 0) {
			sign = xml.substring(s_index + 8, e_index);
		}
		return sign;
	}

	/**
	 * 获取报文中<Date></Date>部分
	 * @param xml
	 * @return
	 */
	public static String getDate(String xml) {
		int s_index = xml.indexOf("<Date>");
		int e_index = xml.indexOf("</Date>");
		String sign = null;
		if (s_index > 0) {
			sign = xml.substring(s_index + 6, e_index);
		}
		return sign;
	}
	
	public static String getNodeText(String xml,String node){
		int s_index = xml.indexOf("<"+node+">");
		int e_index = xml.indexOf("</"+node+">");
		String text = null;
		if (s_index > 0) {
			text = xml.substring(s_index + node.length() + 2, e_index);
		}
		return text;
	}

}
