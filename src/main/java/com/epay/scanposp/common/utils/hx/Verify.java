package com.epay.scanposp.common.utils.hx;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.openssl.PEMReader;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.Security;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
* Created by IH1334 on 2016/11/16.
*/
public class Verify {

	public static boolean verifyMD5withRSA(String publicKey, String data, String sign)
	{
		return verifyMD5withRSA(publicKey, data, sign, "UTF-8");
	}

	public static boolean verifyMD5withRSA(String publicKey, String data, String sign, String charset)
	{
		try
		{
			PublicKey publicKeyObj = loadPublicKey(publicKey);
			return verify(publicKeyObj, getBytes(data, charset), Hex.decodeHex(sign.toCharArray()));
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static PublicKey loadPublicKey(String publicKey)
	{
		try
		{
			PEMReader reader = new PEMReader(new StringReader(publicKey));
			return (PublicKey)reader.readObject();
		} catch (Exception ex) {
			throw new RuntimeException("load publicKey error:", ex);
		}
	}

	public static boolean verify(PublicKey publicKey, byte[] data, byte[] sign)
	{
		try
		{
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			Signature signature = Signature.getInstance("MD5withRSA");
			signature.initVerify(publicKey);
			signature.update(data);
			return signature.verify(sign);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static byte[] getBytes(String content, String charset) {
		if (isNULL(content)) {
			content = "";
		}
		if (isBlank(charset))
			throw new IllegalArgumentException("charset can not null");
		try {
			return content.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
		}
		throw new RuntimeException("charset is not valid,charset is:" + charset);
	}

	public static boolean isNULL(String str) {
		return str == null;
	}

	public static boolean isBlank(String str) {
		int strLen;
		if ((str == null) || ((strLen = str.length()) == 0))
			return true;

		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	static
	{
		Security.addProvider(new BouncyCastleProvider());
	}
}
