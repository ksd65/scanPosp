package com.epay.scanposp.common.utils.posp;

import java.io.IOException;
import java.net.InetAddress;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 
 * http://tool.chinaz.com/tools/md5.aspx
 * 
 * @author sh
 *
 */
public class ApiUtils {
    private static String CHARSET_UTF8 = "UTF-8";
    private static String intranetIp;
    
    public final static String SUCCESS = "SUCCESS";
    public final static String FAIL = "FAIL";
    
    public final static SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    /* 协议级请求参数 - 必须 */
    public final static String APP_ID = "app_id";
    public final static String METHOD = "method";
    public final static String TIMESTAMP = "timestamp";
    public final static String SIGN_TYPE = "sign_type";
    public final static String SIGN = "sign";
    /* 协议级请求参数 - 可选 */
    public final static String FORMAT = "format";
    public final static String CHARSET = "charset";
    public final static String VERSION = "version";
    public final static String PARTNER_ID = "partner_id";
    public final static String SESSION = "session";
    
    /* 公共响应参数 */
    public final static String RETURN_CODE = "return_code";
    public final static String RETURN_MSG = "return_msg";
    public final static String RESULT_CODE = "result_code";
    public final static String ERR_CODE = "err_code";
    public final static String ERR_CODE_DES = "err_code_des";
    
    private ApiUtils() {
    }
    
    // 协议级请求参数 - 必须
    private static List<String> protocalMustParams = new ArrayList<String>() {
        private static final long serialVersionUID = 1L;
        {
            add(APP_ID);
            add(METHOD);
            add(TIMESTAMP);
            add(SIGN_TYPE);
            add(SIGN);
        }
    };
    
    // 协议级请求参数 - 可选
    private static List<String> protocalOptParams = new ArrayList<String>() {
        private static final long serialVersionUID = 1L;
        {
            add(FORMAT);
            add(CHARSET);
            add(VERSION);
            add(PARTNER_ID);
            add(SESSION);
        }
    };
    
    public static boolean isProtocalMustParameter(String name) {
        return protocalMustParams.contains(name);
    }
    
    public static boolean isProtocalOptParameter(String name) {
        return protocalOptParams.contains(name);
    }
    
    public static boolean isProtocalParameter(String name) {
        return isProtocalMustParameter(name) || isProtocalOptParameter(name);
    }
    
    public static Map<String, String> stringMap(Map<String, Object> data) {
        Map<String, String> result = new HashMap<String, String>();
        
        for (Entry<String, Object> entry : data.entrySet()) {
            Object val = entry.getValue();
            if (val == null) {
                continue;
            }
            String valStr = "";
            if (val instanceof Date) {
                valStr = TIME_FORMAT.format((Date) val);
            } else {
                valStr = String.valueOf(val);
            }
            if (isEmpty(valStr)) {
                continue;
            }
            result.put(entry.getKey(), valStr);
        }
        
        return result;
    }
    
    /**
     * 给API请求签名。
     * 
     * @param params
     *            所有字符型的API请求参数
     * @param body
     *            请求主体内容
     * @param secret
     *            签名密钥
     * @param signMethod
     *            签名方法，目前支持：空（老md5)、md5, hmac_md5三种
     * @return 签名
     */
    public static String sign(Map<String, String> params, String body, String secret, String sign_type) throws IOException {
        Map<String, String> paramsCopy = new HashMap<String, String>();
        paramsCopy.putAll(params);
        
        String sign_type_ = paramsCopy.remove(SIGN_TYPE);
        String sign_ = paramsCopy.remove(SIGN);
        
        if (isEmpty(sign_type)) {
            sign_type = sign_type_;
        }
        
        // 第一步：检查参数是否已经排序
        String[] keys = paramsCopy.keySet().toArray(new String[0]);
        Arrays.sort(keys);

        // 第二步：把所有参数名和参数值串在一起
        StringBuilder query = new StringBuilder();
        
        for (String key : keys) {
            String value = paramsCopy.get(key);
            if (areNotEmpty(key, value)) {
                query.append(key).append('=').append(value).append('&');
            }
        }
        
        if (query.length() > 0) {
            query.setLength(query.length() - 1);
        }
        
        // 第三步：把请求主体拼接在参数后面
        if (body != null) {
            query.append(body);
        }
        
        // 第四步：使用MD5/HMACMD5加密
        byte[] bytes;
        if ("md5".equals(sign_type)) {
            query.append(secret);
            System.out.println(query.toString());
            bytes = md5(query.toString());
        } else {
            bytes = hmacMD5(query.toString(), secret);
        }
        
        // 第五步：把二进制转化为大写的十六进制
        return byte2hex(bytes);
    }
    
    public static boolean verify(String secret, String sign_type, String sign, Map<String, String> params) throws IOException {
        boolean result = false;
        
        if (params == null) {
            params = new HashMap<String, String>();
        }
        
        if (isEmpty(sign)) {
            sign = params.remove(SIGN);
        }
        
        String sign_ = sign(params, null, secret, sign_type);
        
        result = sign_.equals(sign);
        
        return result;
    }
    
    public static byte[] hmacMD5(String data, String secret) throws IOException {
        byte[] bytes = null;
        try {
            SecretKey secretKey = new SecretKeySpec(secret.getBytes(CHARSET_UTF8), "HmacMD5");
            Mac mac = Mac.getInstance(secretKey.getAlgorithm());
            mac.init(secretKey);
            bytes = mac.doFinal(data.getBytes(CHARSET_UTF8));
        } catch (GeneralSecurityException gse) {
            throw new IOException(gse.toString());
        }
        return bytes;
    }

    /**
     * 对字符串采用UTF-8编码后，用MD5进行摘要。
     */
    public static byte[] md5(String data) throws IOException {
        return md5(data.getBytes(CHARSET_UTF8));
    }

    /**
     * 对字节流进行MD5摘要。
     */
    public static byte[] md5(byte[] data) throws IOException {
        byte[] bytes = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            bytes = md.digest(data);
        } catch (GeneralSecurityException gse) {
            throw new IOException(gse.toString());
        }
        return bytes;
    }

    /**
     * 把字节流转换为十六进制表示方式。
     */
    public static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString().toUpperCase();
    }
    
    /**
     * 清除字典中值为空的项。
     * 
     * @param <V>
     *            泛型
     * @param map
     *            待清除的字典
     * @return 清除后的字典
     */
    public static <V> Map<String, V> cleanupMap(Map<String, V> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        Map<String, V> result = new HashMap<String, V>(map.size());
        Set<Entry<String, V>> entries = map.entrySet();

        for (Entry<String, V> entry : entries) {
            if (entry.getValue() != null) {
                result.put(entry.getKey(), entry.getValue());
            }
        }

        return result;
    }
    
    public static boolean areNotEmpty(String... values) {
        boolean result = true;
        if (values == null || values.length == 0) {
            result = false;
        } else {
            for (String value : values) {
                result &= !isEmpty(value);
            }
        }
        return result;
    }
    
    private static boolean isEmpty(String value) {
        int strLen;
        if (value == null || (strLen = value.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(value.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * 获取本机的局域网IP。
     */
    public static String getIntranetIp() {
        if (intranetIp == null) {
            try {
                intranetIp = InetAddress.getLocalHost().getHostAddress();
            } catch (Exception e) {
                intranetIp = "127.0.0.1";
            }
        }
        return intranetIp;
    }
    

    // - client
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static Map<String, String> formatRequestParams(String method, Map<String, String> params, String app_id,
			String app_secret) throws IOException {
		Map<String, String> result = new HashMap<String, String>();
		result.put(ApiUtils.APP_ID, app_id);
		result.put(ApiUtils.METHOD, method);
		result.put(ApiUtils.TIMESTAMP, sdf.format(new Date()));
		result.put(ApiUtils.SIGN_TYPE, "md5");
		
		result.putAll(params);
		
		result.put(ApiUtils.SIGN, ApiUtils.sign(result, null, app_secret, "md5"));
		
		return result;
	}
}
