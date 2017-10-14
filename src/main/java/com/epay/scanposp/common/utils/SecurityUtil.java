package com.epay.scanposp.common.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.util.StringUtils;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 工具类
 * 
 * @author zjc
 * 
 */
public class SecurityUtil {

	/**
	 * 将字符串编码为md5格式
	 * 
	 * @param value
	 * @return
	 */
	public static String md5Encode(String value) {
		return md5Encode(value, null);
	}

	/**
	 * 将字符串编码为md5格式
	 * 
	 * @param value
	 * @param encode
	 *            default utf8
	 * @return
	 */
	public static String md5Encode(String value, String encode) {
		String tmp = null;
		encode = StringUtils.isEmpty(encode) ? "utf8" : encode;
		try {
			tmp = md5Encode(value.getBytes(encode));

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return tmp;
	}

	/**
	 * 将字符串编码为md5格式
	 * 
	 * @param value
	 * @param encode
	 *            default utf8
	 * @return
	 */
	public static String md5Encode(byte[] datas) {
		String tmp = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(datas);
			byte[] md = md5.digest();
			tmp = binToHex(md);

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return tmp;
	}

	public static String binToHex(byte[] md) {
		StringBuffer sb = new StringBuffer("");
		int read = 0;
		for (int i = 0; i < md.length; i++) {
			read = md[i];
			if (read < 0)
				read += 256;
			if (read < 16)
				sb.append("0");
			sb.append(Integer.toHexString(read));
		}

		return sb.toString();
	}

	/**
	 * 计算给定路径的文件的md5值
	 * 
	 * @param path
	 *            APK文件路径
	 * @return
	 */
	public static String getMessageMd5(String path) {
		return getMessageDigest(path, "md5");
	}

	/**
	 * 计算给定路径的文件的SHA-1值
	 * 
	 * @param path
	 *            APK文件路径
	 * @return
	 */
	public static String getMessageSha1(String path) {
		return getMessageDigest(path, "sha-1");
	}

	/**
	 * 计算给定路径的文件的SHA-1值
	 * 
	 * @param path
	 *            APK文件路径
	 * @param algorithm
	 *            算法
	 * @return
	 */
	public static String getMessageDigest(String path, String algorithm) {
		File file = new File(path);
		if (!file.exists()) {
			return null;
		}

		// 该对象通过使用 update() 方法处理数据
		BufferedInputStream in = null;
		MessageDigest messagedigest = null;
		try {
			messagedigest = MessageDigest.getInstance(algorithm);
			in = new BufferedInputStream(new FileInputStream(file));
			byte[] buffer = new byte[8192];
			int len = 0;

			while ((len = in.read(buffer)) != -1) {
				messagedigest.update(buffer, 0, len);
			}

			// 对于给定数量的更新数据，digest 方法只能被调用一次。在调用 digest 之后，MessageDigest
			// 对象被重新设置成其初始状态。
			return binToHex(messagedigest.digest());

		} catch (Throwable e) {
			e.printStackTrace();

		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * base64编码
	 */
	public static String base64Encode(String str) {
		BASE64Encoder base64Encoder = new BASE64Encoder();
		String result = null;
		try {
			result = base64Encoder.encode(str.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * base64编码
	 */
	public static String base64Decode(String str) {
		BASE64Decoder base64Decoder = new BASE64Decoder();
		String result = null;
		try {
			result = new String(base64Decoder.decodeBuffer(str));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public static void main(String[] args) {
		String a="http://www.baidu.com";
		String b=base64Encode(a);
		System.out.println(b);
		System.out.println(base64Decode(b));
	}

}
