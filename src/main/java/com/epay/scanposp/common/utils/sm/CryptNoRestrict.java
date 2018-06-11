package com.epay.scanposp.common.utils.sm;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Enumeration;




/**
 * @author Administrator
 * 
 */
public class CryptNoRestrict implements CryptInf {

	private static String currentDir = null;

	static {
		currentDir = CryptNoRestrict.class.getResource("").getPath();
//		if(currentDir.startsWith("/")){
//			currentDir = currentDir.substring(1);
//		}
		if(!currentDir.endsWith("/")){
			currentDir += "/";
		}
		System.out.println(currentDir);
	}

	/**
	 * 构造函数
	 */
	public CryptNoRestrict() {
	}

	public CryptNoRestrict(String encoding) {
		this.encoding = encoding;
	}

	private String encoding = "utf-8";

	/**
	 * 对字符串进行签名
	 * 
	 * @param TobeSigned
	 *            需要进行签名的字符串
	 * @param KeyFile
	 *            PFX证书文件路径
	 * @param PassWord
	 *            私钥保护密码
	 * @return 签名成功返回true(从LastResult属性获取结果)，失败返回false(从LastErrMsg属性获取失败原因)
	 */
	@SuppressWarnings("unchecked")
	public String SignMsg(final String TobeSigned, final String KeyFile, final String PassWord) throws Exception {

		InputStream fiKeyFile = new FileInputStream(KeyFile);
		// 传入绝对路径
		// FileInputStream fiKeyFile = null;
		// fiKeyFile = new FileInputStream(KeyFile);

		KeyStore ks = KeyStore.getInstance("PKCS12");

		try {
			ks.load(fiKeyFile, PassWord.toCharArray());
		} catch (Exception ex) {
			if (fiKeyFile != null)
				fiKeyFile.close();
			throw ex;
		}
		Enumeration myEnum = ks.aliases();
		String keyAlias = null;
		RSAPrivateCrtKey prikey = null;
		// keyAlias = (String) myEnum.nextElement();
		/* IBM JDK必须使用While循环取最后一个别名，才能得到个人私钥别名 */
		while (myEnum.hasMoreElements()) {
			keyAlias = (String) myEnum.nextElement();
			// System.out.println("keyAlias==" + keyAlias);
			if (ks.isKeyEntry(keyAlias)) {
				prikey = (RSAPrivateCrtKey) ks.getKey(keyAlias, PassWord.toCharArray());
				break;
			}
		}
		if (prikey == null) {
			throw new Exception("没有找到匹配私钥");
		} else {
			Signature sign = Signature.getInstance("SHA256withRSA");
			sign.initSign(prikey);
			sign.update(TobeSigned.getBytes(encoding));
			return Base64.encode(sign.sign());
		}
	}

	/**
	 * 验证签名
	 * 
	 * @param TobeVerified
	 *            待验证签名的密文
	 * @param PlainText
	 *            待验证签名的明文
	 * @param CertFile
	 *            签名者公钥证书
	 * @return 验证成功返回true，失败返回false(从LastErrMsg属性获取失败原因)
	 */
	public boolean VerifyMsg(String TobeVerified, String PlainText, String CertFile) throws Exception {
		InputStream certfile = new FileInputStream(new File(currentDir + CertFile));
		CertificateFactory cf = CertificateFactory.getInstance("X.509");

		X509Certificate x509cert = null;
		try {
			x509cert = (X509Certificate) cf.generateCertificate(certfile);
		} catch (Exception ex) {
			if (certfile != null)
				certfile.close();
			throw ex;
		}

		RSAPublicKey pubkey = (RSAPublicKey) x509cert.getPublicKey();
		Signature verify = Signature.getInstance("SHA256withRSA");
		verify.initVerify(pubkey);
		verify.update(PlainText.getBytes(encoding));
		if (verify.verify(Base64.decode(TobeVerified))) {
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		CryptNoRestrict cryptNoRestrict = new CryptNoRestrict();
		try {
			System.out.println(cryptNoRestrict.SignMsg("123", "yixuntiankong.pfx", "sumpay"));	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
