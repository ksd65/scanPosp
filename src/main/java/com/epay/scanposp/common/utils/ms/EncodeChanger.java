package com.epay.scanposp.common.utils.ms;

/**
 * @Title: EncodeChanger.java
 * @Package cn.ipays.gateway.utils
 * @Description: TODO
 * Copyright: Copyright (c) 2015 
 * Company:北京乐付通科技有限公司
 * 
 * @author Comsys-hanchao
 * @date 2015-1-16 下午3:34:53
 * @version V1.0
 */

public class EncodeChanger {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String str = "0000||610422378899|李明|889.91|610422378859|李明|337.00|610422378822|李明|509.26";
		System.out.println(unicode2UnicodeEsc(str));
	}

	public static String unicode2UnicodeEsc(String uniStr) {
		StringBuffer ret = new StringBuffer();
		if (uniStr == null) {
			return null;
		}
		int maxLoop = uniStr.length();
		for (int i = 0; i < maxLoop; ++i) {
			char character = uniStr.charAt(i);
			if (character <= '') {
				ret.append(character);
			} else {
				ret.append("\\u");
				String hexStr = Integer.toHexString(character);
				int zeroCount = 4 - hexStr.length();
				for (int j = 0; j < zeroCount; ++j) {
					ret.append('0');
				}
				ret.append(hexStr);
			}
		}
		return ret.toString();
	}

	public static String unicodeEsc2Unicode(String unicodeStr) {
		if (unicodeStr == null) {
			return null;
		}

		StringBuffer retBuf = new StringBuffer();
		int maxLoop = unicodeStr.length();
		for (int i = 0; i < maxLoop; ++i) {
			if (unicodeStr.charAt(i) == '\\') {
				if ((i < maxLoop - 5)
						&& (((unicodeStr.charAt(i + 1) == 'u') || (unicodeStr
								.charAt(i + 1) == 'U'))))
					try {
						retBuf.append((char) Integer.parseInt(
								unicodeStr.substring(i + 2, i + 6), 16));
						i += 5;
					} catch (NumberFormatException e) {
						retBuf.append(unicodeStr.charAt(i));
					}
				else
					retBuf.append(unicodeStr.charAt(i));
			} else {
				retBuf.append(unicodeStr.charAt(i));
			}
		}

		return retBuf.toString();
	}
}
