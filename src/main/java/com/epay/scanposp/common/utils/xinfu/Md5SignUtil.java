package com.epay.scanposp.common.utils.xinfu;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;



public class Md5SignUtil {
	private static final char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6',
		'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	
	public static String md5Sign(Map<String, String> params,String mkey) {
		
		String signText = getRequestQueryString(params, mkey);
		
		MessageDigest msgDigest = null;

		try {
			msgDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException(
					"System doesn't support MD5 algorithm.");
		}

		try {
			msgDigest.update(signText.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException(
					"System doesn't support your  EncodingException.");
		}

		byte[] bytes = msgDigest.digest();
		String md5Str = new String(encodeHex(bytes));

		return md5Str.toUpperCase();
	}
	
	public static char[] encodeHex(byte[] data) {

		int l = data.length;
		char[] out = new char[l << 1];
		// two characters form the hex value.
		for (int i = 0, j = 0; i < l; i++) {
			out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
			out[j++] = DIGITS[0x0F & data[i]];
		}

		return out;
	}
	
	public static String getRequestQueryString(Map<String, String> params,String mkey) {
		
		Map<String, String> resultMap = sortMapByKey(params);    //��Key��������
		
		StringBuffer buffer = new StringBuffer();
		for (Iterator<String> it = resultMap.keySet().iterator(); it.hasNext();) {
			String key = it.next();
			if(StringUtils.equals("SIGN", key) || StringUtils.equalsIgnoreCase("callback", key)){
				continue;
			}
			if(StringUtils.isEmpty(resultMap.get(key))){
				continue;
			}
			buffer.append(key + "=" + resultMap.get(key) + "&");
		}
		
		return buffer.substring(0, buffer.length()-1) + mkey;
	}
	
	public static Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
 
        Map<String, String> sortMap = new TreeMap<String, String>(
                new MapKeyComparator());
        sortMap.putAll(map);
 
        return sortMap;
    }
}
