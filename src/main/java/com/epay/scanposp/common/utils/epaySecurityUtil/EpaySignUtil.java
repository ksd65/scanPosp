package com.epay.scanposp.common.utils.epaySecurityUtil;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import com.epay.scanposp.common.utils.StringUtil;

import sun.misc.BASE64Decoder;

/**
 * RSA签名公共类
 * @author kristain
 */
public class EpaySignUtil{

    private static EpaySignUtil instance;

    private EpaySignUtil()
    {

    }

    public static EpaySignUtil getInstance()
    {
        if (null == instance)
            return new EpaySignUtil();
        return instance;
    }

    /**
     * 签名处理
     * @param prikeyvalue：私钥文件
     * @param sign_str：签名源内容
     * @return
     */
    public static String sign(String prikeyvalue, String sign_str)
    {
        try
        {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
                    Base64.getBytesBASE64(prikeyvalue));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey myprikey = keyf.generatePrivate(priPKCS8);
            // 用私钥对信息生成数字签名
            java.security.Signature signet = java.security.Signature
                    .getInstance("MD5withRSA");
            signet.initSign(myprikey);
            signet.update(sign_str.getBytes("UTF-8"));
            byte[] signed = signet.sign(); // 对信息的数字签名
            return new String(
                    org.apache.commons.codec.binary.Base64.encodeBase64(signed));
        } catch (java.lang.Exception e)
        {
        }
        return null;
    }

    /**
     * 签名验证
     * @param pubkeyvalue：公钥
     * @param oid_str：源串
     * @param signed_str：签名结果串
     * @return
     */
    public static boolean checksign(String pubkeyvalue, String oid_str,
            String signed_str)
    {
        try
        {
        	if(signed_str.contains(" ")){
        		signed_str = signed_str.replaceAll(" ", "+");
        	}
            X509EncodedKeySpec bobPubKeySpec = new X509EncodedKeySpec(
                    Base64.getBytesBASE64(pubkeyvalue));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey pubKey = keyFactory.generatePublic(bobPubKeySpec);
            byte[] signed = Base64.getBytesBASE64(signed_str);// 这是SignatureData输出的数字签名
            java.security.Signature signetcheck = java.security.Signature
                    .getInstance("MD5withRSA");
            signetcheck.initVerify(pubKey);
            signetcheck.update(oid_str.getBytes("UTF-8"));
            return signetcheck.verify(signed);
        } catch (java.lang.Exception e)
        {
        }
        return false;
    }
    
    /**
	 * 从字符串中加载公钥
	 * 
	 * @param publicKeyStr
	 *            公钥数据字符串
	 * @throws Exception
	 *             加载公钥时产生的异常
	 * @author zhangwl
	 */
	public static PublicKey getPublicKey(String publicKeyStr) throws Exception {
		try {
			BASE64Decoder base64Decoder = new BASE64Decoder();
			byte[] buffer = base64Decoder.decodeBuffer(publicKeyStr);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
			RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
			return publicKey;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			throw new Exception("公钥非法");
		} catch (IOException e) {
			throw new Exception("公钥数据内容读取错误");
		} catch (NullPointerException e) {
			throw new Exception("公钥数据为空");
		}
	}
	
	/**
	 * 从字符串中加载私钥
	 * 
	 * @param privateKeyStr
	 *            公钥数据字符串
	 * @throws Exception
	 *             加载私钥时产生的异常
	 * @author zhangwl
	 */
	public static PrivateKey getPrivateKey(String privateKeyStr) throws Exception {
		try {
			BASE64Decoder base64Decoder = new BASE64Decoder();
			byte[] buffer = base64Decoder.decodeBuffer(privateKeyStr);
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
			return privateKey;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			throw new Exception("私钥非法");
		} catch (IOException e) {
			throw new Exception("私钥数据内容读取错误");
		} catch (NullPointerException e) {
			throw new Exception("私钥数据为空");
		}
	}
	
	public static void main(String[] args) {
		
		//私钥
		String privateKey = "MIIBUwIBADANBgkqhkiG9w0BAQEFAASCAT0wggE5AgEAAkEAk/FV/NCnKioBF0tWiJGhqwYRI3TZTadfoGSKU9TRp+Jub1t1LidESQGLvs28+Ydw9p1iRil6b6os51JeIhvtnQIDAQABAkAoiKLqlbB3WPLbkwSuflgxJ4Rilo1DPWxx4ZoUxeZ7fJ/clyz+rFbwJ1jgMTcNOWkE5dMYoV/IGv0N4IxB4XQBAiEAyJivc/x071uxjiVq7BbhFYG71xUUy2alNM6kV22X/MECIQC8zbtvzXqXlgbPA3xgGwtShvm5SuhshOcDQvjRHvZ73QIgVifeE772qmeDlz3S8pvRCN+zwek4CTSI+GlYhIR5pwECIDKuJvpD9fxq0TkQfnptyARHJxGOAgXfRwOhHplD7nYhAiBjbLSB3m4ApF3oVfJgGPYw08yn3rc/+jbBzVIoCnnJtQ==";
		//待签名字符串
		Map<String,String> params = new HashMap<String,String>();
		params.put("orderNum", "201708161957"); 
		params.put("memberCode", "1010000971");  
		params.put("callbackUrl",  "http://www.baidu.com");
		params.put("payMoney", "0.01" );
		params.put("payType",  "3");
 		String srcStr = StringUtil.orderedKey(params);
 		System.out.println("srcStr==="+srcStr);
		String signstr = sign(privateKey, srcStr);
		//System.out.println(checksign("MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAIhy6DOI+EW+Pb2WKbYTnNOpR9hQXWfAjpME9LI73S86BXP7meF/HBDb8J/S4l//UZGxsVV/Ldxad4evbjzxI2MCAwEAAQ==",srcStr,signstr));
		System.out.println(signstr);
		
		
		/*
		//公钥
		String publicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJEffxgDgQt32CEhldmz+AGgONQmj8QKepYkQ6nNBQHwJcFHL+hYto+jtmvtX+j+HcwdSC4HNzwjHt3aZIKV2dsCAwEAAQ==";
		//待验签字符串
		//String unsignStr = "interfaceType=2&memberCode=1010000118&orderNum=21490838379272438&payMoney=1.00&payNum=20170330094618362489&payTime=20170330094639&payType=1&platformType=3&respType=2&resultCode=0000&resultMsg=交易成功";
		String unsignStr = "interfaceType=2&memberCode=1010000347&orderNum=201707281516204734&payMoney=0.01&payNum=20170728151621558192&payTime=20170728151640&payType=1&platformType=3&respType=2&resultCode=0000&resultMsg=交易成功";
		String oldsignStr = "c9DL+DvJ/27SZRzvsNSajNEq7RzbdKZ2VLbVfy0OIZq/OVgYYADqzYpDxemIErQ5NXhBwQcdhF4d5opS3oPnrg==";
		boolean ret = checksign(publicKey, unsignStr,oldsignStr);
		System.out.println("ret===="+ret);
		*/
		
		
	}
}
