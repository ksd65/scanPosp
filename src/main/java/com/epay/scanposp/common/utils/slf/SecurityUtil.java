package com.epay.scanposp.common.utils.slf;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Enumeration;

import com.sun.org.apache.xml.internal.security.utils.Base64;

public class SecurityUtil {
	public static String merchantCertPath = "";
	protected static String transCertPath = null;
	private static RSAPrivateKey privateKey = null;
	private static RSAPublicKey publicKey = null;
	private static Certificate certificate = null;
	/**
	 * 接口数据初始化
	 * @param certPath 证书路径
	 * @param certPassword 证书密码
	 */
	public static void init(String certPath, String certPassword) {
		try {
			KeyStore ks = KeyStore.getInstance("PKCS12");
			FileInputStream fis = new FileInputStream(certPath);
			char[] nPassword = null;
			if ((certPassword == null) || certPassword.trim().equals("")) nPassword = null;
			else nPassword = certPassword.toCharArray();
			ks.load(fis, nPassword);
			fis.close();
			Enumeration enumas = ks.aliases();
			String keyAlias = null;
			if (enumas.hasMoreElements()) keyAlias = (String) enumas.nextElement();
			privateKey = (RSAPrivateKey) ks.getKey(keyAlias, nPassword);
			Certificate tmpCert = ks.getCertificate(keyAlias);
			publicKey = (RSAPublicKey) tmpCert.getPublicKey();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 网上支付平台证书
		InputStream inStream = null;
		try {
			inStream = new FileInputStream(transCertPath);
			CertificateFactory certFactory = CertificateFactory
					.getInstance("X.509");
			certificate = certFactory.generateCertificate(inStream);
			publicKey = (RSAPublicKey) certificate.getPublicKey();
		} catch (Exception e) {
			System.out.println("初始化网上支付平台证书出错：");
			e.printStackTrace();
		} finally {
			if (inStream != null) try {inStream.close();} catch (Exception e) {}
		}
	}
	/**
	 * 签名
	 * @param sourceData 签名源数据
	 * @return 签名数据
	 * @throws Exception
	 */
	protected static byte[] signWithRSA(byte sourceData[]) throws Exception {
		byte signMessage[] = null;
		try {
			//实例化系统签名工具类
			Signature signature = Signature.getInstance("SHA1withRSA");
			//私钥签名
			signature.initSign(privateKey);
			signature.update(sourceData);
			//签名
			signMessage = signature.sign();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("签名失败");
		}
		return signMessage;
	}
	/**
	 * 验签
	 * @param sourceData 签名源数据
	 * @param signMessage 签名数据
	 * @return 验签结果
	 * @throws Exception
	 */
	protected static boolean verifyWithRSA(byte sourceData[], byte signMessage[])
			throws Exception {
		boolean verifySuccessed = false;
		try {
			Signature signature = Signature.getInstance("SHA1withRSA");
			signature.initVerify(certificate);
			signature.update(sourceData);
			verifySuccessed = signature.verify(signMessage);
		} catch (Exception e) {
			throw new Exception("签名不合法");
		}
		return verifySuccessed;
	}
	public static void main(String [] args) throws Exception{
		transCertPath = "server_cert.cer";
		init("merchant_cert.pfx","11111111");
		byte [] md5 = MD5.getDigest("123456".getBytes("utf-8"));
		String xmlBase64Sign = new String(Base64.encode(
				SecurityUtil.signWithRSA(md5)));
		System.out.println(xmlBase64Sign);
	}
}