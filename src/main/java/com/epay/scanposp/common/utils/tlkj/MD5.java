package com.epay.scanposp.common.utils.tlkj;

import java.security.MessageDigest;

public class MD5 {
	
private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
    
    /**
     * MD5����
     * @param origin ԭʼ�ַ�
     * @return ����MD5����֮��Ľ��
     */
	public static String MD5Encode(String origin) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		return byteArrayToHexString(md.digest(origin.getBytes("utf-8")));
	}

    /**
     * ת���ֽ�����Ϊ16�����ִ�
     * @param b �ֽ�����
     * @return 16�����ִ�
     */
    public static String byteArrayToHexString(byte[] b) {
        StringBuilder resultSb = new StringBuilder();
        for (byte aB : b) {
            resultSb.append(byteToHexString(aB));
        }
        return resultSb.toString();
    }

    /**
     * ת��byte��16����
     * @param b Ҫת����byte
     * @return 16���Ƹ�ʽ
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }
	
}
