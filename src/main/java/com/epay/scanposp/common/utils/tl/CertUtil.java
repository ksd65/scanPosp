package com.epay.scanposp.common.utils.tl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Date;

import javax.crypto.Cipher;

import com.epay.scanposp.common.utils.EnvironmentUtil;
import com.epay.scanposp.common.utils.slf.MerchantClient;

/**
 * 证书操作类
 * @author hy
 * @date 2012-12-13
 */
public class CertUtil {
	/**  
     * Java密钥库(Java Key Store，JKS)KEY_STORE  
     */  
//    public static final String KEY_STORE = "JKS";   
    public static final String KEY_STORE = "PKCS12";   
  
    public static final String X509 = "X.509";   
  
    /**  
     * 由 KeyStore获得私钥  
     *   
     * @param keyStorePath  
     * @param keyStorePassword  
     * @param alias  
     * @param aliasPassword  
     * @return  
     * @throws Exception  
     */  
    private static PrivateKey getPrivateKey(String keyStorePath,   
            String keyStorePassword, String alias, String aliasPassword)   
            throws Exception {   
        KeyStore ks = getKeyStore(keyStorePath, keyStorePassword);   
        PrivateKey key = (PrivateKey) ks.getKey(alias,   
                aliasPassword.toCharArray());   
        return key;   
    }   
  
    /**  
     * 由 Certificate获得公钥  
     *   
     * @param certificatePath  
     * @return  
     * @throws Exception  
     */  
    private static PublicKey getPublicKey(String certificatePath)   
            throws Exception {   
        Certificate certificate = getCertificate(certificatePath);   
        PublicKey key = certificate.getPublicKey();   
        return key;   
    }  
    
    /**
     * 由 keystore获得公钥  
     * @param keyStoreFile
     * @param keystorePassword
     * @param keyAlias
     * @return
     */
    private static PublicKey getPublicKey(String keyStoreFile, String keystorePassword, String keyAlias) {
		//读取密钥是所要用到的工具类
	 	KeyStore ks;
		//公钥类所对应的类
	 	PublicKey pubkey = null;
	 	
	 	try {
	 		//得到实例对象
	 		ks = KeyStore.getInstance(KEY_STORE);
	 		FileInputStream fin;
	 		try {
	 			//读取JKS文件
	 			fin = new FileInputStream(keyStoreFile);
	 			try {
	 				//读取公钥
	 				ks.load(fin, keystorePassword.toCharArray());
	 				Certificate cert = ks.getCertificate(keyAlias);
	 				pubkey = cert.getPublicKey();
	 			} catch (NoSuchAlgorithmException e) {
	 				e.printStackTrace();
	 			} catch (CertificateException e) {
	 				e.printStackTrace();
	 			} catch (IOException e) {
	 				e.printStackTrace();
	 			}
	 		} catch (FileNotFoundException e) {
	 			e.printStackTrace();
	 		}
 		} catch (KeyStoreException e) {
 			e.printStackTrace();
 		}
 		return pubkey;
	 }
  
    /**  
     * 获得Certificate  
     *   
     * @param certificatePath  
     * @return  
     * @throws Exception  
     */  
    private static Certificate getCertificate(String certificatePath)   
            throws Exception {   
        CertificateFactory certificateFactory = CertificateFactory   
                .getInstance(X509);   
        FileInputStream in = new FileInputStream(certificatePath);   
  
        Certificate certificate = certificateFactory.generateCertificate(in);   
        in.close();   
  
        return certificate;   
    }   
  
    /**  
     * 获得Certificate  
     *   
     * @param keyStorePath  
     * @param keyStorePassword  
     * @param alias  
     * @return  
     * @throws Exception  
     */  
    private static Certificate getCertificate(String keyStorePath,   
            String keyStorePassword, String alias) throws Exception {   
        KeyStore ks = getKeyStore(keyStorePath, keyStorePassword);   
        Certificate certificate = ks.getCertificate(alias);   
  
        return certificate;   
    }   
  
    /**  
     * 获得KeyStore  
     *   
     * @param keyStorePath  
     * @param password  
     * @return  
     * @throws Exception  
     */  
    private static KeyStore getKeyStore(String keyStorePath, String password)   
            throws Exception {   
        FileInputStream is = new FileInputStream(keyStorePath);   
        KeyStore ks = KeyStore.getInstance(KEY_STORE);   
        ks.load(is, password.toCharArray());   
        is.close();   
        return ks;   
    }   
  
    /**  
     * 私钥加密  
     *   
     * @param data  
     * @param keyStorePath  
     * @param keyStorePassword  
     * @param alias  
     * @param aliasPassword  
     * @return  
     * @throws Exception  
     */  
    public static byte[] encryptByPrivateKey(byte[] data, String keyStorePath,   
            String keyStorePassword, String alias, String aliasPassword)   
            throws Exception {   
        // 取得私钥   
        PrivateKey privateKey = getPrivateKey(keyStorePath, keyStorePassword,   
                alias, aliasPassword);   
  
        // 对数据加密   
        Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());   
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);   
  
        return cipher.doFinal(data);   
  
    }   
  
    /**  
     * 私钥解密  
     *   
     * @param data  
     * @param keyStorePath  
     * @param alias  
     * @param keyStorePassword  
     * @param aliasPassword  
     * @return  
     * @throws Exception  
     */  
    public static byte[] decryptByPrivateKey(byte[] data, String keyStorePath,   
            String alias, String keyStorePassword, String aliasPassword)   
            throws Exception {   
        // 取得私钥   
        PrivateKey privateKey = getPrivateKey(keyStorePath, keyStorePassword,   
                alias, aliasPassword);   
  
        // 对数据加密   
        Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());   
        cipher.init(Cipher.DECRYPT_MODE, privateKey);   
  
        return cipher.doFinal(data);   
  
    }   
  
    /**  
     * 公钥加密  
     *   
     * @param data  
     * @param certificatePath  
     * @return  
     * @throws Exception  
     */  
    public static byte[] encryptByPublicKey(byte[] data, String certificatePath)   
            throws Exception {   
  
        // 取得公钥   
        PublicKey publicKey = getPublicKey(certificatePath);   
        // 对数据加密   
        Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());   
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);   
  
        return cipher.doFinal(data);   
  
    } 
    
    /**
     * 公钥加密 
     * @param str 需加密串
     * @param keyStoreFile jks文件路径
     * @param keystorePassword jks文件密码
     * @param keyAlias 别名
     * @return
     * @throws Exception
     */
    public static String encryptByPublicKey(String str, String keyStoreFile, String keystorePassword, String keyAlias)   
            throws Exception {   
  
        // 取得公钥   
        PublicKey publicKey = getPublicKey(keyStoreFile, keystorePassword, keyAlias);
        // 对数据加密   
        Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());   
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);   
  
        return StringUtil.byteArrayToHexString(cipher.doFinal(str.getBytes()));   
  
    }
    
    /**  
     * 公钥加密  
     *   
     * @param str  
     * @param certificatePath  
     * @return  
     * @throws Exception  
     */  
    public static String encryptByPublicKey(String str, String certificatePath)   
            throws Exception {   
  
        // 取得公钥   
        PublicKey publicKey = getPublicKey(certificatePath);   
        // 对数据加密   
        Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());   
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);   
  
        return StringUtil.byteArrayToHexString(cipher.doFinal(str.getBytes()));   
  
    }
  
    /**  
     * 公钥解密  
     *   
     * @param data  
     * @param certificatePath  
     * @return  
     * @throws Exception  
     */  
    public static byte[] decryptByPublicKey(byte[] data, String certificatePath)   
            throws Exception {   
        // 取得公钥   
        PublicKey publicKey = getPublicKey(certificatePath);   
  
        // 对数据加密   
        Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());   
        cipher.init(Cipher.DECRYPT_MODE, publicKey);   
  
        return cipher.doFinal(data);   
  
    }   
  
    /**  
     * 验证Certificate  
     *   
     * @param certificatePath  
     * @return  
     */  
    public static boolean verifyCertificate(String certificatePath) {   
        return verifyCertificate(new Date(), certificatePath);   
    }   
  
    /**  
     * 验证Certificate是否过期或无效  
     *   
     * @param date  
     * @param certificatePath  
     * @return  
     */  
    public static boolean verifyCertificate(Date date, String certificatePath) {   
        boolean status = true;   
        try {   
            // 取得证书   
            Certificate certificate = getCertificate(certificatePath);   
            // 验证证书是否过期或无效   
            status = verifyCertificate(date, certificate);   
        } catch (Exception e) {   
            status = false;   
        }   
        return status;   
    }   
  
    /**  
     * 验证证书是否过期或无效  
     *   
     * @param date  
     * @param certificate  
     * @return  
     */  
    private static boolean verifyCertificate(Date date, Certificate certificate) {   
        boolean status = true;   
        try {   
            X509Certificate x509Certificate = (X509Certificate) certificate;   
            x509Certificate.checkValidity(date);   
        } catch (Exception e) {   
            status = false; 
            e.printStackTrace();
        }   
        return status;   
    }   
  
    /**  
     * 签名  
     *   
     * @param keyStorePath  
     * @param alias  
     * @param keyStorePassword  
     * @param aliasPassword  
     * @return  
     * @throws Exception  
     */  
    public static byte[] sign(byte[] sign, String keyStorePath, String alias,   
            String keyStorePassword, String aliasPassword) throws Exception {   
        // 获得证书   
        X509Certificate x509Certificate = (X509Certificate) getCertificate(   
                keyStorePath, keyStorePassword, alias);   
  
        // 取得私钥   
        PrivateKey privateKey = getPrivateKey(keyStorePath, keyStorePassword,   
                alias, aliasPassword);   
  
        // 构建签名   
        Signature signature = Signature.getInstance(x509Certificate   
                .getSigAlgName());   
        signature.initSign(privateKey);   
        signature.update(sign);   
        return signature.sign();   
    }   
  
    /**  
     * 验证签名  
     *   
     * @param data  
     * @param sign  
     * @param certificatePath  
     * @return  
     * @throws Exception  
     */  
    public static boolean verify(byte[] data, byte[] sign,   
            String certificatePath) throws Exception {   
        // 获得证书   
        X509Certificate x509Certificate = (X509Certificate) getCertificate(certificatePath);   
        // 获得公钥   
        PublicKey publicKey = x509Certificate.getPublicKey();   
        // 构建签名   
        Signature signature = Signature.getInstance(x509Certificate   
                .getSigAlgName());   
        signature.initVerify(publicKey);   
        signature.update(data);   
  
        return signature.verify(sign);   
  
    }   
  
    /**  
     * 验证Certificate  
     *   
     * @param keyStorePath  
     * @param keyStorePassword  
     * @param alias  
     * @return  
     */  
    public static boolean verifyCertificate(Date date, String keyStorePath,   
            String keyStorePassword, String alias) {   
        boolean status = true;   
        try {   
            Certificate certificate = getCertificate(keyStorePath,   
                    keyStorePassword, alias);   
            status = verifyCertificate(date, certificate);   
        } catch (Exception e) {   
            status = false;   
        }   
        return status;   
    }   
  
    /**  
     * 验证Certificate  
     *   
     * @param keyStorePath  
     * @param keyStorePassword  
     * @param alias  
     * @return  
     */  
    public static boolean verifyCertificate(String keyStorePath,   
            String keyStorePassword, String alias) {   
        return verifyCertificate(new Date(), keyStorePath, keyStorePassword,   
                alias);   
    }
    
    /**
     * 加签
     * @param content 加签内容
     * @param keyStorePath pfx文件路径
     * @param keyStorePassword pfx密码
     * @param alias 别名
     * @param aliasPassword 别名密码，一般和pfx密码相同
     * @return 签名串
     * @throws Exception
     */
    public static String sign(String content, String keyStorePath,   
            String keyStorePassword, String alias, String aliasPassword){
		try {
			PrivateKey privateKey = getPrivateKey(keyStorePath,keyStorePassword,alias,aliasPassword);
			Signature dsa = Signature.getInstance("SHA1withRSA"); // 采用SHA1withRSA加密
			dsa.initSign(privateKey);
			dsa.update(content.getBytes("UTF-8")); // voucher需要加密的String必须变成byte类型的
			byte[] sig = dsa.sign();

			return new String(Base64.encode(sig));

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }
    
    /**
     * 验证签名
     * @param content 加签内容
     * @param sign	签名结果
     * @param certificatePath cer证书路径
     * @return 是否一致
     * @throws Exception
     */
    public static boolean verifySign(String content, String sign,
			String certificatePath)  {
		// 获得证书
		try {
			X509Certificate x509Certificate = (X509Certificate) getCertificate(certificatePath);
			return verifySign(content.getBytes(), sign, x509Certificate);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
    
    private static boolean verifySign(byte[] data, String sign,
			X509Certificate x509Certificate) throws Exception {
		PublicKey publicKey = x509Certificate.getPublicKey();
		Signature signature = Signature.getInstance(x509Certificate
				.getSigAlgName());
		signature.initVerify(publicKey);
		signature.update(data);
		return signature.verify(Base64.decode(sign.toCharArray()));
	}
    
    public static void main(String[] args) throws Exception{
		String pfxPath = "e:/bak/certs/10000009.pfx";
		String certPath = "e:/bak/certs/10000009.cer";
		String content = "ACCOUNT_NAME=王欢&AMOUNT=1.00&BANK_ACCOUNT=6222032013001170314&COM_ID=10000009&NONCE_STR=428897";
		String sign = sign(content, pfxPath, "999999","njxzkm", "999999");
		System.out.println(sign);
		boolean result = verifySign(content, sign, certPath);
		System.out.println(result);
	}
    
    public  String getConfigPath(){
    	String configPath = "";
    	try {
			configPath = new java.io.File(java.net.URLDecoder.decode(
					CertUtil.class.getProtectionDomain().getCodeSource().getLocation().getFile(),
					"utf-8")).getParentFile().getAbsolutePath()+ File.separator+"config"+File.separator;
			if(!new File(configPath).exists()){
				File file = new File((this.getClass().getClassLoader().getResource("").getPath()));
				String p = file.getParent();
				if(p.endsWith(File.separator))configPath = p+"classes"+File.separator+EnvironmentUtil.propertyPath+"tlkey"+File.separator;
				else configPath = p +File.separator+"classes"+File.separator+EnvironmentUtil.propertyPath+"tlkey"+File.separator;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			
		}
    	return configPath;
    }

	
}
