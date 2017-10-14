package com.epay.scanposp.common.utils.ms;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.URL;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Enumeration;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.pkcs.RSAPrivateKeyStructure;
import org.bouncycastle.asn1.x509.RSAPublicKeyStructure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epay.scanposp.common.constant.MSConfig;
import com.epay.scanposp.common.excep.ArgException;
import com.epay.scanposp.common.utils.EnvironmentUtil;

/**
 * <strong>Title : CryptoUtil</strong><br>
 * <strong>Description : 加解密工具类</strong><br>
 * <strong>Create on : 2015-05-04</strong><br>
 * 
 * @author linshangqing@cmbc.com.cn<br>
 */
public abstract class CryptoUtil {
	/**
	 * 日志对象
	 */
	public static Logger logger = LoggerFactory.getLogger(CryptoUtil.class);

	/**
	 * 数字签名函数入口
	 * 
	 * @param plainBytes
	 *            待签名明文字节数组
	 * @param privateKey
	 *            签名使用私钥
	 * @param signAlgorithm
	 *            签名算法
	 * @return 签名后的字节数组
	 * @throws ArgException
	 */
	public static byte[] digitalSign(byte[] plainBytes, PrivateKey privateKey, String signAlgorithm) throws ArgException {
		try {
			Signature signature = Signature.getInstance(signAlgorithm);
			signature.initSign(privateKey);
			signature.update(plainBytes);
			byte[] signBytes = signature.sign();

			return signBytes;
		} catch (NoSuchAlgorithmException e) {
			throw new ArgException(String.format("数字签名时没有[%s]此类算法", signAlgorithm));
		} catch (InvalidKeyException e) {
			throw new ArgException("数字签名时私钥无效");
		} catch (SignatureException e) {
			throw new ArgException("数字签名时出现异常");
		}
	}

	/**
	 * 验证数字签名函数入口
	 * 
	 * @param plainBytes
	 *            待验签明文字节数组
	 * @param signBytes
	 *            待验签签名后字节数组
	 * @param publicKey
	 *            验签使用公钥
	 * @param signAlgorithm
	 *            签名算法
	 * @return 验签是否通过
	 * @throws ArgException
	 */
	public static boolean verifyDigitalSign(byte[] plainBytes, byte[] signBytes, PublicKey publicKey, String signAlgorithm) throws ArgException {
		boolean isValid = false;
		try {
			Signature signature = Signature.getInstance(signAlgorithm);
			signature.initVerify(publicKey);
			signature.update(plainBytes);
			isValid = signature.verify(signBytes);
			return isValid;
		} catch (NoSuchAlgorithmException e) {
			throw new ArgException(String.format("验证数字签名时没有[%s]此类算法", signAlgorithm));
		} catch (InvalidKeyException e) {
			throw new ArgException("验证数字签名时公钥无效");
		} catch (SignatureException e) {
			throw new ArgException("验证数字签名时出现异常");
		}
	}

	/**
	 * 验证数字签名函数入口
	 * 
	 * @param plainBytes
	 *            待验签明文字节数组
	 * @param signBytes
	 *            待验签签名后字节数组
	 * @param publicKey
	 *            验签使用公钥
	 * @param signAlgorithm
	 *            签名算法
	 * @return 验签是否通过
	 * @throws ArgException
	 */
	public static boolean verifyDigitalSign(byte[] plainBytes, byte[] signBytes, X509Certificate cert, String signAlgorithm) throws ArgException {
		boolean isValid = false;
		try {
			Signature signature = Signature.getInstance(signAlgorithm);
			signature.initVerify(cert);
			signature.update(plainBytes);
			isValid = signature.verify(signBytes);
			return isValid;
		} catch (NoSuchAlgorithmException e) {
			throw new ArgException(String.format("验证数字签名时没有[%s]此类算法", signAlgorithm));
		} catch (InvalidKeyException e) {
			throw new ArgException("验证数字签名时公钥无效");
		} catch (SignatureException e) {
			throw new ArgException("验证数字签名时出现异常");
		}
	}

	/**
	 * 获取RSA公钥对象
	 * 
	 * @param filePath
	 *            RSA公钥路径
	 * @param fileSuffix
	 *            RSA公钥名称，决定编码类型
	 * @param keyAlgorithm
	 *            密钥算法
	 * @return RSA公钥对象
	 * @throws ArgException
	 */
	public static PublicKey getRSAPublicKeyByFileSuffix(String filePath, String fileSuffix, String keyAlgorithm) throws ArgException {
		InputStream in = null;
		String keyType = "";
		if ("crt".equalsIgnoreCase(fileSuffix) || "txt".equalsIgnoreCase(fileSuffix) || "cer".equalsIgnoreCase(fileSuffix)) {
			keyType = "X.509";
		} else if ("pem".equalsIgnoreCase(fileSuffix)) {
			keyType = "PKCS12";
		} else if (("yljf").equalsIgnoreCase(fileSuffix)) {
			keyType = "yljf";
		} else {
			keyType = "PKCS12";
		}

		try {
			in = new FileInputStream(filePath);
			PublicKey pubKey = null;
			if ("X.509".equals(keyType)) {
				CertificateFactory factory = CertificateFactory.getInstance(keyType);
				Certificate cert = factory.generateCertificate(in);
				pubKey = cert.getPublicKey();
			} else if ("PKCS12".equals(keyType)) {
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				StringBuilder sb = new StringBuilder();
				String readLine = null;
				while ((readLine = br.readLine()) != null) {
					if (readLine.charAt(0) == '-') {
						continue;
					} else {
						sb.append(readLine);
						sb.append('\r');
					}
				}
				X509EncodedKeySpec pubX509 = new X509EncodedKeySpec(Base64.decodeBase64(sb.toString()));
				KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm);
				pubKey = keyFactory.generatePublic(pubX509);
			} else if ("yljf".equals(keyType)) {
				BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));
				String s = br.readLine();
				ASN1InputStream ain = new ASN1InputStream(hexString2ByteArr(s));
				RSAPublicKeyStructure pStruct = RSAPublicKeyStructure.getInstance(ain.readObject());
				RSAPublicKeySpec spec = new RSAPublicKeySpec(pStruct.getModulus(), pStruct.getPublicExponent());
				KeyFactory kf = KeyFactory.getInstance("RSA");
				if (in != null)
					in.close();
				return kf.generatePublic(spec);
			}

			return pubKey;
		} catch (FileNotFoundException e) {
			throw new ArgException("公钥路径文件不存在");
		} catch (CertificateException e) {
			throw new ArgException("生成证书文件错误");
		} catch (IOException e) {
			throw new ArgException("读取公钥异常");
		} catch (NoSuchAlgorithmException e) {
			throw new ArgException(String.format("生成密钥工厂时没有[%s]此类算法", keyAlgorithm));
		} catch (InvalidKeySpecException e) {
			throw new ArgException("生成公钥对象异常");
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
			}
		}
	}

	/**
	 * 
	 * <br>
	 * description : 生成平台公钥证书对象
	 * 
	 * @param b
	 * @return
	 * @version 1.0
	 * @date 2014-7-25上午11:56:05
	 */
	private static X509Certificate getCert(String filePath) throws ArgException {
		try {
			byte[] b = null;
			InputStream in = null;
			try {
				in = new FileInputStream(filePath);
				b = new byte[20480];
				in.read(b);
			} finally {
				if (null != in)
					in.close();
			}
			ByteArrayInputStream bais = new ByteArrayInputStream(b);
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			X509Certificate x509Certificate = (X509Certificate) cf.generateCertificate(bais);
			return x509Certificate;
		} catch (Exception e) {
			logger.error("", e);
			throw new ArgException("生成公钥证书对象异常");
		}
	}

	/**
	 * 获取RSA私钥对象
	 * 
	 * @param filePath
	 *            RSA私钥路径
	 * @param fileSuffix
	 *            RSA私钥名称，决定编码类型
	 * @param password
	 *            RSA私钥保护密钥
	 * @param keyAlgorithm
	 *            密钥算法
	 * @return RSA私钥对象
	 * @throws ArgException
	 */
	@SuppressWarnings("deprecation")
	public static PrivateKey getRSAPrivateKeyByFileSuffix(String filePath, String fileSuffix, String password, String keyAlgorithm) throws ArgException {
		String keyType = "";
		if ("keystore".equalsIgnoreCase(fileSuffix)) {
			keyType = "JKS";
		} else if ("pfx".equalsIgnoreCase(fileSuffix) || "p12".equalsIgnoreCase(fileSuffix)) {
			keyType = "PKCS12";
		} else if ("jck".equalsIgnoreCase(fileSuffix)) {
			keyType = "JCEKS";
		} else if ("pem".equalsIgnoreCase(fileSuffix) || "pkcs8".equalsIgnoreCase(fileSuffix)) {
			keyType = "PKCS8";
		} else if ("pkcs1".equalsIgnoreCase(fileSuffix)) {
			keyType = "PKCS1";
		} else if ("yljf".equalsIgnoreCase(fileSuffix)) {
			keyType = "yljf";
		} else if ("ldys".equalsIgnoreCase(fileSuffix)) {
			keyType = "ldys";
		} else {
			keyType = "JKS";
		}

		InputStream in = null;
		try {
			in = new FileInputStream(filePath);
			PrivateKey priKey = null;
			if ("JKS".equals(keyType) || "PKCS12".equals(keyType) || "JCEKS".equals(keyType)) {
				KeyStore ks = KeyStore.getInstance(keyType);
				if (password != null) {
					char[] cPasswd = password.toCharArray();
					ks.load(in, cPasswd);
					Enumeration<String> aliasenum = ks.aliases();
					String keyAlias = null;
					while (aliasenum.hasMoreElements()) {
						keyAlias = (String) aliasenum.nextElement();
						priKey = (PrivateKey) ks.getKey(keyAlias, cPasswd);
						if (priKey != null)
							break;
					}
				}
			} else if ("yljf".equals(keyType)) {
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				String s = br.readLine();
				PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(hexStrToBytes(s));
				KeyFactory keyf = KeyFactory.getInstance("RSA");
				PrivateKey myprikey = keyf.generatePrivate(priPKCS8);
				return myprikey;
			} else if ("ldys".equals(keyType)) {
				byte[] b = new byte[20480];
				in.read(b);
				PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(b);
				KeyFactory keyf = KeyFactory.getInstance("RSA");
				PrivateKey myprikey = keyf.generatePrivate(priPKCS8);
				return myprikey;
			} else {
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				StringBuilder sb = new StringBuilder();
				String readLine = null;
				while ((readLine = br.readLine()) != null) {
					if (readLine.charAt(0) == '-') {
						continue;
					} else {
						sb.append(readLine);
						sb.append('\r');
					}
				}
				if ("PKCS8".equals(keyType)) {
					PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(sb.toString()));
					KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm);
					priKey = keyFactory.generatePrivate(priPKCS8);
				} else if ("PKCS1".equals(keyType)) {
					// RSAPrivateKeyStructure asn1PrivKey = new
					// RSAPrivateKeyStructure((ASN1Sequence)
					// ASN1Sequence.fromByteArray(sb.toString().getBytes()));
					RSAPrivateKeyStructure asn1PrivKey = new RSAPrivateKeyStructure((ASN1Sequence) ASN1Sequence.fromByteArray(Base64.decodeBase64(sb.toString())));
					KeySpec rsaPrivKeySpec = new RSAPrivateKeySpec(asn1PrivKey.getModulus(), asn1PrivKey.getPrivateExponent());
					KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm);
					priKey = keyFactory.generatePrivate(rsaPrivKeySpec);
				}
			}

			return priKey;
		} catch (FileNotFoundException e) {
			throw new ArgException("私钥路径文件不存在");
		} catch (KeyStoreException e) {
			throw new ArgException("获取KeyStore对象异常");
		} catch (IOException e) {
			throw new ArgException("读取私钥异常");
		} catch (NoSuchAlgorithmException e) {
			throw new ArgException("生成私钥对象异常");
		} catch (CertificateException e) {
			throw new ArgException("加载私钥密码异常");
		} catch (UnrecoverableKeyException e) {
			throw new ArgException("生成私钥对象异常");
		} catch (InvalidKeySpecException e) {
			throw new ArgException("生成私钥对象异常");
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
			}
		}
	}

	/**
	 * RSA加密
	 * 
	 * @param plainBytes
	 *            明文字节数组
	 * @param publicKey
	 *            公钥
	 * @param keyLength
	 *            密钥bit长度
	 * @param reserveSize
	 *            padding填充字节数，预留11字节
	 * @param cipherAlgorithm
	 *            加解密算法，一般为RSA/ECB/PKCS1Padding
	 * @return 加密后字节数组，不经base64编码
	 * @throws ArgException
	 */
	public static byte[] RSAEncrypt(byte[] plainBytes, PublicKey publicKey, int keyLength, int reserveSize, String cipherAlgorithm) throws ArgException {
		int keyByteSize = keyLength / 8; // 密钥字节数
		int encryptBlockSize = keyByteSize - reserveSize; // 加密块大小=密钥字节数-padding填充字节数
		int nBlock = plainBytes.length / encryptBlockSize;// 计算分段加密的block数，向上取整
		if ((plainBytes.length % encryptBlockSize) != 0) { // 余数非0，block数再加1
			nBlock += 1;
		}

		try {
			Cipher cipher = Cipher.getInstance(cipherAlgorithm);
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);

			// 输出buffer，大小为nBlock个keyByteSize
			ByteArrayOutputStream outbuf = new ByteArrayOutputStream(nBlock * keyByteSize);
			// 分段加密
			for (int offset = 0; offset < plainBytes.length; offset += encryptBlockSize) {
				int inputLen = plainBytes.length - offset;
				if (inputLen > encryptBlockSize) {
					inputLen = encryptBlockSize;
				}

				// 得到分段加密结果
				byte[] encryptedBlock = cipher.doFinal(plainBytes, offset, inputLen);
				// 追加结果到输出buffer中
				outbuf.write(encryptedBlock);
			}

			outbuf.flush();
			outbuf.close();
			return outbuf.toByteArray();
		} catch (NoSuchAlgorithmException e) {
			throw new ArgException(String.format("没有[%s]此类加密算法", cipherAlgorithm));
		} catch (NoSuchPaddingException e) {
			throw new ArgException(String.format("没有[%s]此类填充模式", cipherAlgorithm));
		} catch (InvalidKeyException e) {
			throw new ArgException("无效密钥");
		} catch (IllegalBlockSizeException e) {
			throw new ArgException("加密块大小不合法");
		} catch (BadPaddingException e) {
			throw new ArgException("错误填充模式");
		} catch (IOException e) {
			throw new ArgException("字节输出流异常");
		}
	}

	/**
	 * RSA解密
	 * 
	 * @param encryptedBytes
	 *            加密后字节数组
	 * @param privateKey
	 *            私钥
	 * @param keyLength
	 *            密钥bit长度
	 * @param reserveSize
	 *            padding填充字节数，预留11字节
	 * @param cipherAlgorithm
	 *            加解密算法，一般为RSA/ECB/PKCS1Padding
	 * @return 解密后字节数组，不经base64编码
	 * @throws ArgException
	 */
	public static byte[] RSADecrypt(byte[] encryptedBytes, PrivateKey privateKey, int keyLength, int reserveSize, String cipherAlgorithm) throws ArgException {
		int keyByteSize = keyLength / 8; // 密钥字节数
		int decryptBlockSize = keyByteSize - reserveSize; // 解密块大小=密钥字节数-padding填充字节数
		int nBlock = encryptedBytes.length / keyByteSize;// 计算分段解密的block数，理论上能整除

		try {
			Cipher cipher = Cipher.getInstance(cipherAlgorithm);
			cipher.init(Cipher.DECRYPT_MODE, privateKey);

			// 输出buffer，大小为nBlock个decryptBlockSize
			ByteArrayOutputStream outbuf = new ByteArrayOutputStream(nBlock * decryptBlockSize);
			// 分段解密
			for (int offset = 0; offset < encryptedBytes.length; offset += keyByteSize) {
				// block大小: decryptBlock 或 剩余字节数
				int inputLen = encryptedBytes.length - offset;
				if (inputLen > keyByteSize) {
					inputLen = keyByteSize;
				}

				// 得到分段解密结果
				byte[] decryptedBlock = cipher.doFinal(encryptedBytes, offset, inputLen);
				// 追加结果到输出buffer中
				outbuf.write(decryptedBlock);
			}

			outbuf.flush();
			outbuf.close();
			return outbuf.toByteArray();
		} catch (NoSuchAlgorithmException e) {
			throw new ArgException(String.format("没有[%s]此类解密算法", cipherAlgorithm));
		} catch (NoSuchPaddingException e) {
			throw new ArgException(String.format("没有[%s]此类填充模式", cipherAlgorithm));
		} catch (InvalidKeyException e) {
			throw new ArgException("无效密钥");
		} catch (IllegalBlockSizeException e) {
			throw new ArgException("解密块大小不合法");
		} catch (BadPaddingException e) {
			e.printStackTrace();
			throw new ArgException("错误填充模式");
		} catch (IOException e) {
			throw new ArgException("字节输出流异常");
		}
	}

	/**
	 * AES加密
	 * 
	 * @param plainBytes
	 *            明文字节数组
	 * @param keyBytes
	 *            密钥字节数组
	 * @param keyAlgorithm
	 *            密钥算法
	 * @param cipherAlgorithm
	 *            加解密算法
	 * @param IV
	 *            随机向量
	 * @return 加密后字节数组，不经base64编码
	 * @throws ArgException
	 */
	public static byte[] AESEncrypt(byte[] plainBytes, byte[] keyBytes, String keyAlgorithm, String cipherAlgorithm, String IV) throws ArgException {
		try {
			// AES密钥长度为128bit、192bit、256bit，默认为128bit
			if (keyBytes.length % 8 != 0 || keyBytes.length < 16 || keyBytes.length > 32) {
				throw new ArgException("AES密钥长度不合法");
			}

			Cipher cipher = Cipher.getInstance(cipherAlgorithm);
			SecretKey secretKey = new SecretKeySpec(keyBytes, keyAlgorithm);
			if (StringUtils.trimToNull(IV) != null) {
				IvParameterSpec ivspec = new IvParameterSpec(IV.getBytes());
				cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
			} else {
				cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			}

			byte[] encryptedBytes = cipher.doFinal(plainBytes);

			return encryptedBytes;
		} catch (NoSuchAlgorithmException e) {
			throw new ArgException(String.format("没有[%s]此类加密算法", cipherAlgorithm));
		} catch (NoSuchPaddingException e) {
			throw new ArgException(String.format("没有[%s]此类填充模式", cipherAlgorithm));
		} catch (InvalidKeyException e) {
			throw new ArgException("无效密钥");
		} catch (InvalidAlgorithmParameterException e) {
			throw new ArgException("无效密钥参数");
		} catch (BadPaddingException e) {
			throw new ArgException("错误填充模式");
		} catch (IllegalBlockSizeException e) {
			throw new ArgException("加密块大小不合法");
		}
	}

	/**
	 * AES解密
	 * 
	 * @param encryptedBytes
	 *            密文字节数组，不经base64编码
	 * @param keyBytes
	 *            密钥字节数组
	 * @param keyAlgorithm
	 *            密钥算法
	 * @param cipherAlgorithm
	 *            加解密算法
	 * @param IV
	 *            随机向量
	 * @return 解密后字节数组
	 * @throws ArgException
	 */
	public static byte[] AESDecrypt(byte[] encryptedBytes, byte[] keyBytes, String keyAlgorithm, String cipherAlgorithm, String IV) throws ArgException {
		try {
			// AES密钥长度为128bit、192bit、256bit，默认为128bit
			if (keyBytes.length % 8 != 0 || keyBytes.length < 16 || keyBytes.length > 32) {
				throw new ArgException("AES密钥长度不合法");
			}

			Cipher cipher = Cipher.getInstance(cipherAlgorithm);
			SecretKey secretKey = new SecretKeySpec(keyBytes, keyAlgorithm);
			if (IV != null && StringUtils.trimToNull(IV) != null) {
				IvParameterSpec ivspec = new IvParameterSpec(IV.getBytes());
				cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
			} else {
				cipher.init(Cipher.DECRYPT_MODE, secretKey);
			}

			byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

			return decryptedBytes;
		} catch (NoSuchAlgorithmException e) {
			throw new ArgException(String.format("没有[%s]此类加密算法", cipherAlgorithm));
		} catch (NoSuchPaddingException e) {
			throw new ArgException(String.format("没有[%s]此类填充模式", cipherAlgorithm));
		} catch (InvalidKeyException e) {
			throw new ArgException("无效密钥");
		} catch (InvalidAlgorithmParameterException e) {
			throw new ArgException("无效密钥参数");
		} catch (BadPaddingException e) {
			throw new ArgException("错误填充模式");
		} catch (IllegalBlockSizeException e) {
			throw new ArgException("解密块大小不合法");
		}
	}

	public static void main(String[] args) throws Exception {
//		// String qryString =
//		// java.net.URLEncoder.encode("HCowq6CiLWeR/vfyK1luyLets2qh/o1YZ1sYTQlK/kD/iKB0ba66MF4LwnDZkOmU0f92eDbCzQnoTB0mYC9jLWbDu5sZCzKyXsTMR80xnoPXDIdQXQK4smOmJCM86qtZZAJLW6FtRilD8v+L/eVzADu+k47Frsi4cZVCxMPu2eMpS5zXS2ham3borXjdVe1JDzIuYzc//dm7aPoaHzvEnClj0PQJLZa5IO4KLgu9X4eUoESGzyPMRfxaHJboYU8vhJpV2MxMu0v0UCzUnUTPb3CdiX9/iEDQFqwJyw8/WcsCqXYYS28saqJ/Ox6bCTHyUP8p90epw+QYReNkjWQDpA==","UTF-8");
//		// System.out.println(qryString);
//		// String qryString2 =
//		// java.net.URLDecoder.decode("HCowq6CiLWeR/vfyK1luyLets2qh/o1YZ1sYTQlK/kD/iKB0ba66MF4LwnDZkOmU0f92eDbCzQnoTB0mYC9jLWbDu5sZCzKyXsTMR80xnoPXDIdQXQK4smOmJCM86qtZZAJLW6FtRilD8v+L/eVzADu+k47Frsi4cZVCxMPu2eMpS5zXS2ham3borXjdVe1JDzIuYzc//dm7aPoaHzvEnClj0PQJLZa5IO4KLgu9X4eUoESGzyPMRfxaHJboYU8vhJpV2MxMu0v0UCzUnUTPb3CdiX9/iEDQFqwJyw8/WcsCqXYYS28saqJ/Ox6bCTHyUP8p90epw+QYReNkjWQDpA==","UTF-8");
//		// System.out.println(qryString2);
//
//		PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKeyByFileSuffix("/home/gwpay/my/ylsh_private_key.pem", "pkcs1", null, "RSA");
//		PublicKey hzfPubKey = CryptoUtil.getRSAPublicKeyByFileSuffix("/home/gwpay/my/ylsh_public_key.pem", "pem", "RSA");
//		// PrivateKey hzfPriKey =
//		// CryptoUtil.getRSAPrivateKeyByFileSuffix("/home/gwpay/my/pkcs8_rsa_private_key_2048.pem",
//		// "pem", null, "RSA");
//		// PublicKey hzfPubKey =
//		// CryptoUtil.getRSAPublicKeyByFileSuffix("/home/gwpay/my/rsa_public_key_2048.pem",
//		// "pem", "RSA");
//
//		String yhAESKey = null;
//		if (yhAESKey == null) {
//			yhAESKey = MSCommonUtil.generateLenString(16);
//		}
//		String charset = "utf-8";
//		String plain = "money_order=1.00&no_order=0788839312&oid_partner=GWP_XJTB&oid_paybill=2016040604229598&resp_code=00&resp_msg=交易成功&resp_type=S&settle_date=20160406";
//		System.out.println("plain:" + plain);
//		String t = "";
//		try {
//			byte[] base64SingDataBytes = Base64.encodeBase64(CryptoUtil.digitalSign(plain.getBytes(charset), hzfPriKey, "SHA1WithRSA"));
//			t = new String(base64SingDataBytes, charset);
//			System.out.println(t + "\r\n" + t.length());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		// decode(plain,sign,charset,hzfPubKey);
//		decode2(plain, t, charset, hzfPubKey);
		
		ClassLoader classLoader = CryptoUtil.class.getClassLoader();
		URL url = classLoader.getResource("mybatis/AccountLogMapper.xml");
		System.out.println(url.getFile());
		
		
	}

	public static void decode(String plain, String sign, String charset, PublicKey hzfPubKey) throws Exception {
		byte[] signData = KeyUtil.string2bytes(sign, 16);
		byte[] signBytes = Base64.decodeBase64(signData);
		// 使用商户公钥验证签名
		boolean verifySign = CryptoUtil.verifyDigitalSign(plain.getBytes(charset), signBytes, hzfPubKey, "SHA1WithRSA");
		if (verifySign) {
			System.out.println("success");
		} else {
			System.out.println("failure");
		}
	}

	public static void decode2(String plain, String sign, String charset, PublicKey hzfPubKey) throws Exception {
		System.out.println(sign.length());
		byte[] signBytes = Base64.decodeBase64(sign.getBytes(charset));
		// 使用商户公钥验证签名
		boolean verifySign = CryptoUtil.verifyDigitalSign(plain.getBytes(charset), signBytes, hzfPubKey, "SHA1WithRSA");
		if (verifySign) {
			System.out.println("success");
		} else {
			System.out.println("failure");
		}
	}

	public static byte[] hexString2ByteArr(String hexStr) {
		return new BigInteger(hexStr, 16).toByteArray();
	}

	public static final byte[] hexStrToBytes(String s) {
		byte[] bytes;
		bytes = new byte[s.length() / 2];
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2), 16);
		}
		return bytes;
	}

	/**
	 * 字符数组16进制字符
	 * 
	 * @param bytes
	 * @return
	 */
	public static String bytes2string(byte[] bytes, int radix) {
		int size = 2;
		if (radix == 2) {
			size = 8;
		}
		StringBuilder sb = new StringBuilder(bytes.length * size);
		for (int i = 0; i < bytes.length; i++) {
			int integer = bytes[i];
			while (integer < 0) {
				integer = integer + 256;
			}
			String str = Integer.toString(integer, radix);
			sb.append(StringUtils.leftPad(str.toUpperCase(), size, "0"));
		}
		return sb.toString();
	}

	/**
	 * 获取公钥
	 * 
	 * @return
	 */
	public static PublicKey getRSAPublicKey(boolean isHttpsIpay) {
		
		ClassLoader classLoader = CryptoUtil.class.getClassLoader();
		
		PublicKey publicKey=null;
		try {
			if (isHttpsIpay) {
				URL url = classLoader.getResource(EnvironmentUtil.propertyPath + "mskey/product_ms_rsa_public_key_2048.pem");
				publicKey= CryptoUtil.getRSAPublicKeyByFileSuffix(url.getFile(), "pem", "RSA");

			} else {
				URL url = classLoader.getResource(EnvironmentUtil.propertyPath + "mskey/ms_rsa_public_key_2048.pem");
				publicKey= CryptoUtil.getRSAPublicKeyByFileSuffix(url.getFile(), "pem", "RSA");
			}
		} catch (ArgException e) {
			e.printStackTrace();
		}
		return publicKey;
	}

	/**
	 * 获取私钥匙
	 * 
	 * @return
	 */
	public static PrivateKey getRSAPrivateKey() {
		
		ClassLoader classLoader = CryptoUtil.class.getClassLoader();
		
		URL url = classLoader.getResource(EnvironmentUtil.propertyPath + "mskey/pkcs8_rsa_private_key_2048.pem");
		
		PrivateKey privateKey=null;
		try {
			privateKey=CryptoUtil.getRSAPrivateKeyByFileSuffix(url.getFile(), "pem", null, "RSA");
		} catch (ArgException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return privateKey;
	}
	

}
