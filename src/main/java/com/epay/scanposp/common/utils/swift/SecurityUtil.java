package com.epay.scanposp.common.utils.swift;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;

public class SecurityUtil {
	
	static final String PARAM_SIGN_TYPE = "signType";
	static final String SIGN_TYPE_MD5 = "md5";
	static final String PARAM_SIGN = "dataSign";

	public static String sign(Map<String, String> params, String signType, String secrtkey, String charset)
			throws Exception {
		params = paraFilter(params);
		String prestr = createLinkString(params); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
		prestr = prestr + secrtkey; // 把拼接后的字符串再与安全校验码直接连接起来
		String sign = "";
		if (SIGN_TYPE_MD5.equalsIgnoreCase(signType)) {
			sign = md5(prestr, charset);
		}
		return sign;
	}
	
	public static Map<String, String> paraFilter(Map<String, String> sArray) {

		Map<String, String> result = new HashMap<String, String>();
		if (sArray == null || sArray.size() <= 0) {
			return result;
		}
		for (String key : sArray.keySet()) {
			String value = sArray.get(key);
			if (StringUtil.isEmpty(value) || key.equalsIgnoreCase(PARAM_SIGN)
					|| key.equalsIgnoreCase(PARAM_SIGN_TYPE)) {
				continue;
			}
			result.put(key, value);
		}
		return result;
	}
	
	public static String createLinkString(Map<String, String> params) {
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		String prestr = "";
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);

			if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + key + "=" + value + "&";
			}
		}
		return prestr;
	}
	
	public static String md5(String text, String charset) throws Exception {
		return DigestUtils.md5Hex(getContentBytes(text, charset));
	}

	private static byte[] getContentBytes(String content, String charset) throws Exception {
		if (charset == null || "".equals(charset)) {
			return content.getBytes();
		}
		try {
			return content.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			throw new Exception(e);
		}
	}
}
