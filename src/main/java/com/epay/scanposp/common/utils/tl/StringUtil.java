package com.epay.scanposp.common.utils.tl;

import java.io.InputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringUtils;


/**
 * 字符串工具类
 *
 * @author hhb
 * @version 1.0
 */
public class StringUtil {

    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * 转换字节数组为16进制字符串
     *
     * @param b 字节数组
     * @return 16进制字符串
     */
    public static String byteArrayToHexString(byte[] b) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            sb.append(byteToHexString(b[i]));
        }

        return sb.toString().toUpperCase();
    }

    /**
     * 转换字节数为16进制字符串
     *
     * @param b byte数值
     * @return 16进制字符串
     */
    public static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;

        return hexDigits[d1] + hexDigits[d2];
    }
    
    /**
     * 将整数转为16进行数后并以指定长度返回（当实际长度大于指定长度时只返回从末位开始指定长度的值）
     *
     * @param val int 待转换整数
     * @param len int 指定长度
     * @return String
     */
    public static String Int2HexStr(int val, int len) {
        String result = Integer.toHexString(val).toUpperCase();
        int r_len = result.length();
        if (r_len > len) {
            return result.substring(r_len - len, r_len);
        }
        if (r_len == len) {
            return result;
        }
        StringBuffer strBuff = new StringBuffer(result);
        for (int i = 0; i < len - r_len; i++) {
            strBuff.insert(0, '0');
        }
        return strBuff.toString();
    }

    /**
     * 获取指定字符串的MD5编码
     *
     * @param original 字符串
     * @return MD5编码
     */
    public static String MD5Encode(String original) {
        String ret = null;

        try {
            ret = new String(original);
            MessageDigest md = MessageDigest.getInstance("MD5");
            ret = byteArrayToHexString(md.digest(ret.getBytes()));
        } catch (Exception ex) {
            // empty
        }

        return ret;
    }

    /**
     * 获得0-9的随机数字符串
     *
     * @param length 返回字符串的长度
     * @return String
     */
    public static String getRandomNumber(int length) {
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < length; i++) {
            buffer.append(random.nextInt(10));
        }
        return buffer.toString();
    }

    /**
     * 获得0-9,a-z,A-Z范围的随机字符串
     *
     * @param length 字符串长度
     * @return String
     */
    public static String getRandomChar(int length) {
        char[] chr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a',
                'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
                'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
                'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
                'X', 'Y', 'Z'};

        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            buffer.append(chr[random.nextInt(62)]);
        }

        return buffer.toString();
    }

    /**
     * 判断字符串数组中是否包含某字符串
     *
     * @param substring 某字符串
     * @param source    源字符串数组
     * @return 包含则返回true，否则返回false
     */
    public static boolean isContains(String substring, String[] source) {
        if (source == null || source.length == 0) {
            return false;
        }

        for (int i = 0; i < source.length; i++) {
            String aSource = source[i];
            if (aSource.equals(substring)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 判断字符是否为空
     *
     * @param str 某字符串
     * @return 为null或为空串则返回true，否则返回false
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * 首字母大写
     *
     * @param str 字符串
     * @return 首字符大写后的字符串
     */
    public static String upFirstChar(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * 首字母小写
     *
     * @param str 字符串
     * @return 首字符大写后的字符串
     */
    public static String lowerFirstChar(String str) {
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }
    
    /**
     * 字符串数组转成列表
     * @param arr
     * @return
     */
    public static List<String> StringsToList(String [] arr){
    	List<String> strList = null;
    	if(null == arr){
    		return strList;
    	}
    	strList = new ArrayList<String>();
    	for(int i = 0;i < arr.length;i++){
    		strList.add(arr[i]);
    	}
    	return strList;
    }
    
    /**
     * inputstream解析成string
     * @param in
     * @return
     */
    public static String inputStream2String(InputStream in) {
		try{
			StringBuffer out = new StringBuffer();
			byte[] b = new byte[4096];
			for (int n; (n = in.read(b)) != -1;) {
				out.append(new String(b, 0, n));
			}
			return out.toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
 
    /**
     * 把null转换成空字符串
     * 
     * @param object
     * @return
     */
    public static String changeNullToStr(String str){
		return str == null ? "" : str ;
	}
	
    /**
     * 将标准的BASE64编码格式中+ , = , /的字符替换成. , - , _, okpay需要
     * @param str
     * @return
     */
    public static String toy64(String str){
		String s = StringUtils.replace(str, "+", ".");
		s = StringUtils.replace(s, "/", "_");
		s = StringUtils.replace(s, "=", "-");
		
		return s;
	}
	
    /**
     * 将替换过+ , = , /的字符串转换成标准的BASE64编码格式, okpay需要
     * @param str
     * @return
     */
	public static String from64(String str){
		String s = StringUtils.replace(str, ".", "+");
		s = StringUtils.replace(s, "_", "/");
		s = StringUtils.replace(s, "-", "=");
		
		return s;
	}
    
	/**
	 * 把16进制字符串转化为字节数组
	 * 
	 * @param hex
	 * @return
	 */
	public static byte[] hexToBytes(String hex) {
		byte[] buffer = new byte[hex.length() / 2];
		String strByte;

		for (int i = 0; i < buffer.length; i++) {
			strByte = hex.substring(i * 2, i * 2 + 2);
			buffer[i] = (byte) Integer.parseInt(strByte, 16);
		}

		return buffer;
	}
	
	/**
	 * 生成指定范围的随机数字
	 * @param from
	 * @param to
	 * @return
	 */
	public static int randomInt(int from, int to) {
		return from + new Random().nextInt(to - from);
	}
	
	/**
	 * 生成指定长度的随机数字
	 * @param length 不限制长度
	 * @return
	 */
	public static String randomStr(int length){
		StringBuffer sb = new StringBuffer();
		// 生成随机数字串
		for (int i = 0; i < length; i++) {
			sb.append(randomInt(0, 10));
		}
		
		return sb.toString();
	}
	
	/**
	 * 生成指定长度的随机数字，并将数字转化为ascii
	 * @param length 不限制长度
	 * @return  ascii字符串
	 */
	public static String randomStr2Ascii(int length){
		StringBuffer sb = new StringBuffer();
		
		char[] chars = randomStr(length).toCharArray();   
	    for (int i = 0; i < chars.length; i++) {  
	    	sb.append((int)chars[i]);  
	    }
	    
	    return sb.toString();
	}
	
	/**
	 * 生成指定长度的随机数字，并将数字转化为ascii
	 * @param length 1-8位
	 * @return  ascii字符串
	 */
	public static String generalStringToAscii(int length){
		
		int num = 1;
		for (int i = 0; i < length; i++){
			num *= 10;
		}
		
		Random rand = new Random((new Date()).getTime());
		String strRandom = Integer.toString(rand.nextInt(num));
		
		strRandom = StringUtils.leftPad(strRandom, length, '0');
				
		StringBuffer sb = new StringBuffer();  
		char[] chars = strRandom.toCharArray();   
	    for (int i = 0; i < chars.length; i++) {  
	    	sb.append((int)chars[i]);  
	    }
	    
	    return sb.toString();  
	}
	
    public static void main(String[] args) {
        System.out.println(randomStr2Ascii(3));
        System.out.println(randomStr2Ascii(16));
        System.out.println(randomStr2Ascii(8));
        System.out.println(generalStringToAscii(3));
        System.out.println(generalStringToAscii(16));
        System.out.println(generalStringToAscii(8));
    }

	/**
	 * 补位
	 * 
	 * @param src
	 *            源
	 * @param length
	 *            补充到的长度
	 * @param paddingType
	 *            1:补0 2:补空格
	 * @param leftOrRight
	 *            1:左补 2:右补
	 * @return
	 */
	public static String padding(String src, int length, int paddingType, int leftOrRight) {
		String padddingStr = null;
		StringBuffer buffer = new StringBuffer(src);
		if (paddingType == 1) {
			padddingStr = "0";
		} else {
			padddingStr = " ";
		}

		while (buffer.length() < length) {
			if (leftOrRight == 1) {
				buffer.insert(0, padddingStr);
			} else {
				buffer.append(padddingStr);
			}
		}
		return buffer.toString();
	}
	
	
	/**去除字符串左边的0
	 * @param src
	 * @return
	 */
	public static String removeZero(String src) {
		int j = 0;
		for (int i = 0; i < src.length(); i++) {
			if (src.charAt(i) == '0') {
				j++;
			} else {
				break;
			}
		}
		return src.substring(j);
	}
}
