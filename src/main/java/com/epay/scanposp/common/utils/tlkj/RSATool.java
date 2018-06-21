package com.epay.scanposp.common.utils.tlkj;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Linwei on 2018/6/14.
 */

public class RSATool {

    public static String  ENCODING = "UTF-8";
    
	private static Logger logger = LoggerFactory.getLogger(RSATool.class);


    static  {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }
    
    private static PrivateKey getPrivateKey(String pfxKeyFile, String keyPwd) throws Exception {
    	KeyStore keyStore = getKeyInfo(pfxKeyFile, keyPwd, "PKCS12");
        Enumeration<String> aliasenum = keyStore.aliases();
        String keyAlias = null;
        if (aliasenum.hasMoreElements()) {
            keyAlias = aliasenum.nextElement();
        }
        PrivateKey privateKey = (PrivateKey) keyStore
                .getKey(keyAlias, keyPwd.toCharArray());
        return privateKey;
    }

    public static String signOrigByPrivateKey(String pfxKeyFile, String keyPwd, String srcData) throws Exception {
        byte[] bytes = signBySoft(getPrivateKey(pfxKeyFile, keyPwd), srcData.getBytes("UTF-8"));
        String s = new String(Base64.encodeBase64(bytes));
        return s;
    }
    
    public static String signByPrivateKey(String pfxKeyFile, String keyPwd, String srcData) throws Exception {
        byte[] bytes = signBySoft(getPrivateKey(pfxKeyFile, keyPwd), sha1X16(srcData, ENCODING));
        return new String(Base64.encodeBase64(bytes));
    }

    public static boolean validateByPublicKey(String cerKeyFile, String signData, String srcData) throws Exception {
        X509Certificate x509Certif = getX509Certif(cerKeyFile);
        PublicKey publicKey = x509Certif.getPublicKey();
        byte[] bytes = Base64.decodeBase64(signData.getBytes());
        return validateSignBySoft(publicKey, bytes, sha1X16(srcData, ENCODING));
    }

    public static boolean validateSignBySoft(PublicKey publicKey,
                                             byte[] signData, byte[] srcData) throws Exception {
        Signature st = Signature.getInstance("SHA1withRSA", "BC");
        st.initVerify(publicKey);
        st.update(srcData);
        return st.verify(signData);
    }

    private static X509Certificate getX509Certif(String cerKeyFile) throws IOException {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(cerKeyFile);
            CertificateFactory cf = CertificateFactory.getInstance("X.509", "BC");
            X509Certificate validateCert = (X509Certificate) cf.generateCertificate(fis);
            return  validateCert;
        } catch (Exception e)
        {
        	logger.info("getX509Certif Error", e);
        } finally {
            if(null!=fis)
            {
                fis.close();
            }
        }
        return  null;
    }

    /**
     * 将签名私钥证书文件读取为证书存储对象
     *
     * @param pfxkeyfile
     *            证书文件名
     * @param keypwd
     *            证书密码
     * @param type
     *            证书类型
     * @return 证书对象
     * @throws IOException
     */
    private static KeyStore getKeyInfo(String pfxkeyfile, String keypwd,
                                       String type) throws IOException {
    	logger.info("加载签名证书==>" + pfxkeyfile);
        FileInputStream fis = null;
        try {
            KeyStore ks = KeyStore.getInstance(type, "BC");
            logger.info("Load RSA CertPath=[" + pfxkeyfile + "],Pwd=["+ keypwd + "],type=["+type+"]");
            fis = new FileInputStream(pfxkeyfile);
            char[] nPassword = null;
            nPassword = null == keypwd || "".equals(keypwd.trim()) ? null: keypwd.toCharArray();
            if (null != ks) {
                ks.load(fis, nPassword);
            }
            return ks;
        } catch (Exception e) {
        	logger.info("getKeyInfo Error", e);
            return null;
        } finally {
            if(null!=fis)
                fis.close();
        }
    }

    /**
     * sha1计算后进行16进制转换
     *
     * @param data
     *            待计算的数据
     * @param encoding
     *            编码
     * @return 计算结果
     */
    public static byte[] sha1X16(String data, String encoding) {
        byte[] bytes = sha1(data, encoding);
        StringBuilder sha1StrBuff = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            if (Integer.toHexString(0xFF & bytes[i]).length() == 1) {
                sha1StrBuff.append("0").append(
                        Integer.toHexString(0xFF & bytes[i]));
            } else {
                sha1StrBuff.append(Integer.toHexString(0xFF & bytes[i]));
            }
        }
        try {
            return sha1StrBuff.toString().getBytes(encoding);
        } catch (UnsupportedEncodingException e) {
        	logger.info(e.getMessage(), e);
            return null;
        }
    }

    /**
     * sha1计算
     *
     * @param datas
     *            待计算的数据
     * @param encoding
     *            字符集编码
     * @return
     */
    private static byte[] sha1(String datas, String encoding) {
        try {
            return sha1(datas.getBytes(encoding));
        } catch (UnsupportedEncodingException e) {
        	logger.info("SHA1计算失败", e);
            return null;
        }
    }

    /**
     * sha1计算.
     *
     * @param data
     *            待计算的数据
     * @return 计算结果
     */
    private static byte[] sha1(byte[] data) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
            md.reset();
            md.update(data);
            return md.digest();
        } catch (Exception e) {
        	logger.info("SHA1计算失败", e);
            return null;
        }
    }

    /**
     *
     * @param privateKey
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] signBySoft(PrivateKey privateKey, byte[] data)
            throws Exception {
        byte[] result = null;
        Signature st = Signature.getInstance("SHA1withRSA", "BC");
        st.initSign(privateKey);
        st.update(data);
        result = st.sign();
        return result;
    }
}
