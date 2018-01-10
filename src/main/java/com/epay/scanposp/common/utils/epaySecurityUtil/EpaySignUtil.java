package com.epay.scanposp.common.utils.epaySecurityUtil;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
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
    
    public static String signSha1(String prikeyvalue, String sign_str)
    {
        try
        {
        	PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
                    Base64.getBytesBASE64(prikeyvalue));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey myprikey = keyf.generatePrivate(priPKCS8);
            // 用私钥对信息生成数字签名
            java.security.Signature signet = java.security.Signature
                    .getInstance("SHA1withRSA");
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
    
    public static boolean checksignSha1(String pubkeyvalue, String oid_str,
            String signed_str)
    {
        try
        {
        	X509EncodedKeySpec bobPubKeySpec = new X509EncodedKeySpec(
                    Base64.getBytesBASE64(pubkeyvalue));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey pubKey = keyFactory.generatePublic(bobPubKeySpec);
            byte[] signed = Base64.getBytesBASE64(signed_str);// 这是SignatureData输出的数字签名
            java.security.Signature signetcheck = java.security.Signature
                    .getInstance("SHA1withRSA");
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
		//String privateKey = "MIIBUwIBADANBgkqhkiG9w0BAQEFAASCAT0wggE5AgEAAkEAk/FV/NCnKioBF0tWiJGhqwYRI3TZTadfoGSKU9TRp+Jub1t1LidESQGLvs28+Ydw9p1iRil6b6os51JeIhvtnQIDAQABAkAoiKLqlbB3WPLbkwSuflgxJ4Rilo1DPWxx4ZoUxeZ7fJ/clyz+rFbwJ1jgMTcNOWkE5dMYoV/IGv0N4IxB4XQBAiEAyJivc/x071uxjiVq7BbhFYG71xUUy2alNM6kV22X/MECIQC8zbtvzXqXlgbPA3xgGwtShvm5SuhshOcDQvjRHvZ73QIgVifeE772qmeDlz3S8pvRCN+zwek4CTSI+GlYhIR5pwECIDKuJvpD9fxq0TkQfnptyARHJxGOAgXfRwOhHplD7nYhAiBjbLSB3m4ApF3oVfJgGPYw08yn3rc/+jbBzVIoCnnJtQ==";
		//9010000988
		String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCIxkLM+QBKTqw9BsCSjiLw5pO186HwgMOZIXUFDVnRsYyPeiFsnPgaNQmT7L5Ur0jHtKc0yUdk5jd8BG06N40474MiBzNNNJJa4mkPQfUlsD0T0NMLesjFthKqyQxS1cMqpz8b372pwsWb16BVM/9i5ak9JgqguAtEkxR2lSNd0sn/GEqH9hdw6RvMH/Y/ePztdA3bANJL64g0hrKKBW0YcJvXJpKF8vIwuNuamOoKOzlEIPZC05331j4c8xw/EUiBbS9sELAT+MLNlYXHIWtpDDbJTNZGKXDskJpfae7ifW4TU6ZlraiW4JGabl1d5DH2t4k5NnFqiquPZWPhZ8u9AgMBAAECggEAdGdHsuq4NIWAUO/ONOyDEEMss04GJIlx7oFq9kHGj5Br8DAhAi8VeDhrTlnOIoSLjGtTYrlq6ZSE5CdgTou4xRwSnoNCRhLX/EF06GdaHBlB4ft3oe19scajXHZ+5oDG+SYdr7tbz37Uby20Zs86KxEKV+BoayA3dsU2RTXoQ5BCtMeXaIUif3JjHGy1xHkR1MPN2vXvwMqU7Femk/Qr9s8W+9IgVp5ah28Kx5qkR63TkEONyYTbI+U4El/bVnZmcaHL+/KJ9wo8+/7hHrl+mWV0T3iKa4tJWed9V+IUKrLeUpn9DOIv9WNlDFjJqBckC/GBhujG7uRKWfminGRAgQKBgQDJnQv9HAgUnGsYTayY1iW2CwgFZJn1RgaTCjo1Oc6tDTFXdP4uIFfxEfnLhGuRuItTKHW1hf1Hp4n+wiZO5q1HjjC3bLBUy1Lw+HvaJLNHRkCye58cg2Jlu81girzciazLiHqfEaZxFsUq8WsqzZIBRr0gPDDJMrSOBfkf+hWckwKBgQCtq5WSq/t5Cr1N5AOoYoBDvJOHqQ4/6Ue0JF270/u1H/qdMQ9w/OQRixhHNP7g5oiPBWKPQTs3uU9/SHpX5CHsyqlQDvOkrSnESp4pz0BUOT971er/8o79NrUE9ZQMRDhu0WHXtbPOq+7siUw9SA5HcEh8Hd/Y3xLOFhvw90x4bwKBgGBGFRZ9j0JAW0eUt8mX4RQn+mGQ44/jK3qFlLwb6ZxrQ1eO712ZZkUgn1bW2gMQy78e/+55mDPiRhwYG/DraG1V8d91EFK9cNLO5V2Kzu1HF9fi/lzARHluD6l9NqhdOd1LQ7q30/IGvIpAFDuxRHpFjERbWbSJ+Pwk0Ay8ABvvAoGAah4XFfkqfqqWQ3rY1VHiyAD5MIKXJ2w2mRdDgxqjiegRbW1l3wdXoHSakCAMwYV72dBTie807Pa5Ya/6uau3IwYucLHCJFR+2ecyP5/Y0d3tMZDjuCMRRh3gfDhGjzw8M1KTc4geZ2Fda4D1adiWiQZN9DEY715XEkAmMJYbTtcCgYEAn9BcQcpAsbV2fpmO0VU8CMFoTnbfYpmHR9N6A45r72j1nQmJxfZbvL7JiIK7jhRUuQERC+jK08FPKbFDoE5a7UxNiKDE/ZtuEwPehrf8yVenxmAWMh4llc1HoWQfjCY8BJuAm3K4jHtswB0+PQfTMG7PVisoMfdVEJ4gq8BLAv0=";
		//9010000978
	//	String privateKey = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQChcZE1LGjc72ZtqmVsTmIlQBSzVld169unAXkfy3yct+yuHdyY4Gd9gUPEMGer1W2ms8DIl50SdR/fzmvinG9PPlhcg9pH2cWcdvXPjscN9qHSwbVC5nXsR5SZOY9Jr0dyPdyGUlPbmY7QuK27laVXM++Sws0UJuEpzTwYik7sXcetyumDmTp2ckYHQe1i8e+freI6gDlDcX3C4WCxJEt3eH7QXPmjABPU+FoZ4JpsHfB0HkU85rYsr78Y2R93wrSyBKIpxsP6WQ5P1fUfTPhQHF4dY2Kef6cjCZ0ZsQVF5M0bvM/M4kjM27AMYE/og7VBPpK0mudvNZFK17DtOoRHAgMBAAECggEAFSLNI+0AfYxKGKlqRdetObs9+oyfPqz2QNMMjIETe2EI93KpYspeQiweNx0vNWvnwx9daeWPhs8WsTJRJYXdfL5oyxPoC+gS2v0oCVhj7wwO+4yosm2BmsF0TuseO0NbUzWOHXfByso7zE+hTyG0V8pQ3yHpv22npNVdvMi+tGsM8ECvjXyNd04kUVQlNaSM5QPZdkaMNg51HOpX2eAcOBCBR8p8VituztRFN8p9Z8M4mraY74Ae9UloOD+DyBkVXq/OWMlcFTLRdvXYLi4HiFzWpxv541s/uTuQXKaksF/ter6ycHU84SHvaMoHSIOTpEHIgvAu0AEEgb2z1483YQKBgQDpXMBx8MmYCC94eU6b32PaZGB4T1HZBHbpjeB0yEm4eXNwAi3GgfWDP18ZZ4+Mr9YlCVXNdol54xjDJTXoXDFZNNON7uf87t/e7X8+4G3XS2LbiYyuEgt+DJvIosb2SRo7vtCsz7xXgXOxp0O/eaHFDmW8BStBRadP/M3blqbMbQKBgQCxGs8JtYm6bHuBEZOlPgrYMs55QEaHfkXK4wc5XAZud35k2JMW8YxEInS7JSMwR2u1CpzqpMvMDxlEOg92X5HpCrPaVC48pRst5h5n1WwG7esRjqDZkPR8fzy7CYCih7FzFp8fUHZXvkDzc/yyQH2QAngGZxdHojiMaU2TTS07AwKBgBej1L72JIXpNl3e9A6oR6ZFCtzFNEZmxGEOhVFvRMCSVRZgsiBs4+Ei11BshEc9PNVNp8qDmpOlZMnICDj8tecSiefT7t5ZmIYtyvkkpRvtQBt7SKwNmepv/owJqVwjtaudvefzt2Gp9H1ns14/RRsARWARqfsXwkZBFt2K2mTVAoGAF5rqI3Il1EonP/G2yxx015IWAQU3IVIMPVl7GgMQ/GJC33AaUsGJH3+0LUlq4kFysqOifIfQffc5N+XBL9bXHMeHZz83FnAvKjP0s4Q3rMjDPwjSmYz/eSSAVLGUipUpapyGQuc3jsRV5dz8vkloMuyRUNEmwi51+QkEBPERaT8CgYAhAUKpyd7Fe5WZVYg9jbu/IGcKsnb/VZLfRshCe6M+r4wPbP6N5g6USHNzo712ubAVWfgHNMoA/zmuIbjhEcpbN9Z6knerZPYvCxldZhU15Iyia8c3WMbcE7AiG83g3vf9tSV3BkOn0ruQKOLW1sfAwg+7pWo8f/N4gIAtHXQqdQ==";
		//9010000986
	//	String privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCmztX96gD19bRe6VT+SVpMblwRpZY1AZvRPHcyTlzEVVrdKc5lzIcGBauOZwdwDixTM+lS0cuHB8qSY4wLJvZtrxOV+puXedfK8vqXayzWzYH/b7ZMA6bweVsNJ2g/m7fJjRa8I5QufaZow1Y4F/KXUnIg9eCrklfNzSOoHuLXaL7R++8hTsKZTxlc+cipKIxrwB7Y95gAjzg8t3MPogNbXVjrb6JUf8Cwg1DegbLAyWdwIu4iKn/rwZlwoCPXs92VUjsrnQXa7zBQ1OEWG3cdEcepPqALZY+aQf+fsj3Dwr3DS3PT6DtcFdP/iaAigdFcYOFx2laiy8rvKW1v5HDxAgMBAAECggEAdbz0il6LAxDEmWF0nm/EDsKutb39Abj69HsLXzS0gdgFZKnHh08P1w5VgxPxygQXATtY6x4t3EeBkkY1aQG9WroCxw+2lvpneFZ8zjfV1GAF6/g74feJ856Ux/oSuSL+XIL7kxxB0Xba6INH7hvpHSoSaHVkeXW4CAlNq7LxRj0BXAxm4FnOsu03QNugwVJFfCqKFam6uuCgy8CQkePo3XLGlb10F07ZRVizDq1z32LYc6MshbwQd331vzMCobMCTBnjoNRxPo0ohrFq5xXqKI9jrys9a6+751ICs+44IA8WVFRI5h6r3wsfFyXvHbPa3/yeWQPcyfYR1+/RC0yGqQKBgQDmE2ci24sjYBb//86oLILcoW35B9BplSfFZVucL3mNo/Cyz06H7tnSsHTtrwHcyIHqvYv02rWej45Ky3+89FLKmbssH/eHA5Lx2uH3Gkv7tFMb9aCuz+s352A6S+8qd36MItYQpzfkAB0PPtsrQPxqKPjpcvq6HPmBsfQ8UPinqwKBgQC5mnSYUEDPcbwQ6xqrnzgVVw4NxoGeYjiNxLkh+6Nb0fI4usLB4+kn2nqzPD9N5A2qum2fGacHqLxZRnoUIyKJt8uc4esanJ5LQa82Cuc3gI4q9mX3ZGynPb74xfL6mlX6gnW8G0fbJAQGO0esol7qTm1B7yqUFvHo0paTsHu90wKBgQDBWBjZ4CkjB0MMysm95fnqsvYZyolH0QJlDfJYZET/1H8XpMLX1M7UEVmRy/oCsbULsVIF6C2sG/N1w2GLRr60Z1A1mn+GxGgeOtSdtUmZMkaNPnNTtIJ0rkndc6Z+x4Be/39o6z06qyXAhA/mX2w/QkM621b8RBEg/ozCi0EKlQKBgQChGbz8fCRVKz53XOpwbyop879CG7xkcpLuEwQyyA36ZBggP5NO4nl9WzlHnBLClCF8wIboI0cE8eRShUbvdbPD4CG/EaWVGr6XSfNAMGSTm2VLS0YkXymkXIcHviHyfcYFQ/nXhcJwJynLEudapDFIpTxWj7bgha4CTc/fb79oewKBgBAjtU/7unlrh4yAfCqiGeoFJkzNb5Gmvk+Rjblxz+Eg8TbMRxsHJvRqCXlV7+PVzHV3DTplaQ1cN1RhmFnGZX8etW30OxdzXt4LZsywJzzXsl91HRwA3/XNKCCGNIdmmrLOdsE5Y0ybC+VMfseKgv35/badIumNpGM4gJ1YmkyE";
		//9010000989
	//	String privateKey = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCnxHYtcmevqK1airY3Vj0UW5ZH0rapfzzardN5CX7I2+dPWFTsv9AaZUfx3pudd5jA9yXUOGKkcajLo3v82NE5OiIEtIApwcL6ATZAeXbxmL76dra6MYQNRObUfcPmiZKKSUjqxH7TYmf+3jbDCL34MEweuxQoblt2kPrZB+IKd3bY4fiqbNb28Ia14dKRIoATW53XQYdgmAPOjO9VWg3jQi0AA/BuTOg/ffjDHTlF4zIfsvkf77aP+yIP6sa3lxzEMy5fyEJ6+6dJ62WC9r7sijTPcA9SqgpBue8Wn1WGo4y53jHp0t+eICEQxXn+NwNAaCebVURCYgxBUsr/VL3JAgMBAAECggEAOEDo+24sFDtz/TMbzExPaL8ZEfY2p3jxl8XHbgNgDsulsPZGa6gZiHBDgneJKHDMuI9mkNVjRg6Scc3cqjitVwoGFBvOCbJdxWSYo1/XIUV9bBBYTvCBfc2HtOKCyI+ConnWSFogyJJrmos6Q4Vv5YOUUo2aQcBXTNdqNVGuWqGNyMYpNP1CpSquvJxeNmmbFQ9Uj4qLp3d8ocpGjkETAh8Dn+JVsxWy5R0ZCsqwqM73t26aMLXz11k8TnOEmgH2j1UhXcS2/MqwPCEx3YuEvXZFB/pWbXg4MjExotek/EjEQfWccfcVQrUr2IYrPNa0jczk5+Zzse1U/tEBt1gUAQKBgQDux02+oWJuoVLRHXYDu5ZnhvJ8QUqCvaanmGeerNRb2IJ53FdDlW1uoZcTvUWAh7Sv87M/w2T70xrXsls3lY1QTnhMSEJGENPHt9YDJjlDoDtJd8Ls64dSWiR1WS66znI2dgocGEPsmJeqy0L7BYTITyxAA3pfVf9nbszDXaX8SQKBgQCz3gq0ECouJr7yYS1wIxnLvx3YH/n8ijWFV9Bumm437be+/PmaM/HksMNBtVXbuIk0x2ahIG2lRpXOlJh1c3xXtEHM++a3Kd2Jcn8Xv+WoZNecKEIU20xDxs8brkHVTmHc5xBv5fNgvXC2sobMJmX0qoEJbQ/YrNwXgs4obsm1gQKBgQC9IxHqFlup1g+LpRJ+EnEowDQDSJSEy0TfLFGn1q6/sdhoPqjA1/Q+1bCd6ibLkLMHTLqHn3a2Ipn4kUa/2K5/FikkA3i7l0ipwmy7QePKNXnA0CSPFmGyUcTfTy08u5qsmQOZDX+AQb6hgzAjNlPfdxdN/XNJYQ1WGOYJOnzoQQKBgQCYzyWMwMb1GpnS1diCkm+R97ZlLRV3BfxeYrKDpNPK1DztF4L/DO6O+dWI94QP/YEjw9xfO2urpIIVVY0JC6ox4MSeX8wtBHZgFVQAW1nDbJj45ALwMTdjxX2i6LKpg8QEppICXvwbzhX6QUXgHycza7/gwDVcQc9ichCvrR/VAQKBgQDeHABfXAK8AAoWhSx3JXhZlcJ2ETXphg0n5jbgIRiQzmx3/pVWdwmnnWsDzRrLiCmv4Qkao2jqLbFK4wGngoitgtatOQX9fTGEkxqyGZD+Tg62Dy/fLY6tcK/9fS7XSumjePRp56fvPifehQx0oqf7zdzvS9ukIEQ+d2sTGSmY/Q==";
		//9010000976
		//String privateKey ="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCBv1dYoyMdo8SnAMb2wVPllthkPRllK/Wsw7U6riGiV/fgfdO0xpplmexruEzh3akJCxYakwK4skBU8j81nFg2J1weC/tmcj8tnssCOA68zk/GAL3GNpdDgQ7yepjQ6AQ5R6gLlMe5kbc4dRptquAdFjaDoiEee5ThWDplNAqHHFqBnODAJswtJlSg0kQ758cmLEteuSoYDSvQuXZcm3jwEsBkFBWUNQOss2FXw3GWEF/OeaiWOkxFGXihauhiZ7bwNkIdD2wrkUoB17UEZ5ew5zVG0rFmS5rDR+dVvxaTrggDaKR930FKx9u3B/j5qsF8Tq4P9iVJGJhCwbPKrZeXAgMBAAECggEAfoIvy5g9nB/bli6W0XCNujXLxmkgwtoLEKo+ofm5h86YPcbSTti7IMflkt6/8BcuQ0gRBFZFeAst2OXfJgnMpPjZw95RAYHyAixrqz03eXb8p4Z/g64k7UgAaAOLz137CFLLEoe9+j2PVbgwza+oGBRZkR3bhxpefx6EybrlbPiOTtbxqX5H/gnznEfyeeD4NcApwXtvOP34ZCY9TabDqtQx8rSIhtYqyQBoWTIB5s5yXCE8T/NntfVHcUIMsJKA2v9oivDy7DXProlLgBsI805E3LW0dQqf01CbUpNKxzVbvxDrHT68UwYVpJja2tsGmGjnUZm3+zgyr1f1Zb8NKQKBgQD5oX9UQcZFaKmH6tfQalCVK3hrhuSnBTi6xjdZCqSH7RMCqJ/VH7QH/Maxv3Wek1pyoMAfm+7/jy2X0nQPb/UlqZLStcskujaMPOk0OJRAcZFdJC2cp1sd1RRkdpkjBPJtZOTs12Yh9jNvCIABzEReBCfk8OWDMIdbvMq0N3naGwKBgQCFDs6AIf6PmppZy9FgutmCa71wbJVeFScVeI5T3nYwYysVx+U6x+ThEsfcC5fJSc2gYKQg0NrsVQP6U2YuDFfCo2aecl6zUzV53/Yop3y4/Df4Kjr10KV/HTVMp7M0WQd6P1c0LJv2RZdJStOvUsc7iKi3ODpNpks5ePAoI5FQNQKBgQDS1gvs5gDH7rYBDeBpLuIQmz6B0tScHFTlqzRkCBEenKVJwZH9N2GevWg0CDkXTe0+k3axO8qIHi2r5RwRTcYHoUlYniKNSpl3qJpueLr+Eu1rdXrwrIjQkX8/ustsQGlg665OOz8PwBmA1fUHj7bOkUQAoVW3j+1DFhk+TPylsQKBgD0MVxiZAEf5AjqS9kTwVd2t+yP63ellwSHrxzmZxxZaWalLptP6ZPwTdtlyBDlZgFvGG2fnC5sFQqb23tnf1eA6wlmOVf7m/ZEmfbWXavUg27kj/xtLmLMivpKmGuW4t2KSbztEAGbU9R2ulENLspeREv/Fb5Ky6q66200/TGvFAoGAcDAqwR4FSYQuWyPTMHxTfHHWTNWVxp1GvA/EzYVKz0ud3f622FDEYa5t3WpwO0STtGIEN95y15oB9d141Er1qD9SIPrG2h7LsboUg1aX0XOyc32GxYZekJWB9VCEBMsdQv+nCKTuaBSGTaNtCBdPO5dXPWvSi4gGOnVaj21wb+Q=";
		//9010000977
	//	String privateKey ="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCn0Y8gCFK81rd85tgU37Whtklq7BA3G1FWV6WeTJo76mdir7SwwnbRkfDEqOXv8IpFCRTJC/hler2r4tzbfhNdUnUjWpMxKJP+o+f0PiiXtk8SETV69QYx6Tqe84atYGWCxn0D6xYqoQoGL3zTqULOrMNimwtVgI2L+CXdYGdT6YJK0QUL5TR2ebf5RjKkOIowaF2G71GZhQldnO2pdg+/OItckMDvvt2N2SzSOEDMNZ7/6EKBJbcAukBhbK53FcpIrZoCUUeASCWqpV40HfHwaTYCVYn6yQap5Di0aPjSodDx4FpLGwHydC8VHYaRWPlrBHSqBUw3g9AFo181llV9AgMBAAECggEAciOnsuO2UlW/iqX/RYM6J/ixSpWwGars0UrfjCERrbqqAob64FYDNlqKZVdyvJTflkCs8GjB01N9+ZBdIrRHO2u/3AHYptCwh7q3Inm8ZiNK+EDYHP7V7dBxXHbJnNY5dI7HrIiSFQBerwJRZixzEdqTMVe+h7Ldktt99WmCHaFPc/iNX2AB3wNXTDw50I5kTicoGZvYpwYycJz3XraPGIACfV2U/iQ43TbxKLeMyjoAM4BiTo4W1hAjyRUAsTf5t4dALHnmmqi3bcukKGSC4+YxrV+ygcwcl8WtEj5N5knQN+kFRpTWSIjKkmDVqZ0KNo4TchUWctHLxAbTB3KsyQKBgQD5omIUXOiqmC5stRvQ+D0Q4qukxkchu8psbnxopK4mvHpQZ6kTpLGJvjDz0Iqjb2TKVLCn3jDmaF+DyRG1LUdeXlYuaJVZH/lx6Dbn9wTk8akfS95ZUkRBLpvqoZFheIrB2LsfScaEOSfcV8z5HDOp4+y2A/wEjMf/8nXevSBu6wKBgQCsGRTjwAk5V4zhVw1X5G0GTCqoXk9KlCoXvkc9ob6bcpqa3HYVrylcNgaslfn9ywwcNa8NrhuBQcNy2y3syioRLL1Os7dXglKqHo8GwwbcOJQnvP5XUyW07h+C8k3vnGRTa+7hhws/hDZ4MLkdNXLwAha1/5IR4EhKHsOlxAVDNwKBgQCsacrz5XeM2660xiGAIvOKRgPmuktGsT0NxwGGBLeDaYnRHsdYGdfFGucUfmRCnR0v4W5hgsSjXlMZ4jWovK0eMGm1g+YiuELAuXGypJlsxrvYMb5QUtFxc4+cbhrLB0ZOvDIBWiMSD6fIN5wKMcA9Bn7m97mIKvO3gu6F+Hn+IQKBgDPxDgD/QMlxxy9r2rcgVleEPYu7iyrzNREdeNr9Kk44dLkwuOMqjs0p8TG9wkn7MpiBC2iRl/OXKapMxLum+LNeUXSjgT6EMvbtajWcP0HQQccMn6czjTnidNhM0YD0LsOO2/Ztj9OHz/jOm9BZBWuy9a/QhwGmM6tLRbmORBhZAoGANTOm+6gCVklQbqvZZgh9YVpoahm1aKuTSyjbrvGuDAzNWOJgg4KOjGtxzqCNMCfaPmOUb1HsRxb7eRwVI4iXZvz5nhWoBdCmqCD460z4H2wNqIaHSA8e7/d1EeDS+pzc8G4UEiQ9a892rIayR8nqUqX+7k6U/XYmVsYNMmpT3UU=";
		//9010000994
		//String privateKey="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCEz5xSsds7VgYTAT+mJ2v2zrkarzb0rNr3UESW/exM7D8aqu/zNCh6D596BZlVlVkKNJ7/oOiFhbSNZenadDh+8ubJd9j4WKsR9U+9Wcwj3KUxm+2fO60bvXUERr2TAFM9ym8get6WoKk2pVvTMDEuGylMZpnUAeZ8QPboa35c7Pqou75D4ROl5PQ7FN2744AfkanX2jucZkGnpp/UWx+q1gOPQxsd1auwFnv8WLL4ovuwcUKLNJMkQylgcru8GeQ26PscukaRlyPAzO2Diy0KT8bBnUqsUCua//5QMIJlAAkpMzPiNLgBhy/NPoRHV8vTkljUInp9OIeH28vYrC2lAgMBAAECggEAeykoF0vWOU1J7r/erY+rQEJuR7+qn/XYTTaDlN4/DwqabnmMcSWyT5dDVd3xVvtbiepIdFhcqM3YJLQYzDuw1JEe0kAw1gUsB0hNPRQUCkGoc7vc/ShTHQFxjZioXrUTfJ7ItP8cgeTVZtars8niIZaQksgm6QMmIZ4BGoHsufC2FYuy0Z46wf1PKKiNzuOYrC2dixIIbDBw0fCE2EYjax86IsEiquVxy5X1Fg83cFJSxuAG/mhwyibZg5QsRGUGLXiK52hFJcjvp44lqo/Vltbfeqcj5u7JB0j+KlnQpPAcnZh4aoQn9jGpl/w5DrB56wcb7/GwWFqGnPNz3cRxIQKBgQDJxqKyA32/Dqd2uP9U4yFzoiD5Z8C5uV/mN4h4F5vU4knZw3dhq3Y3NqzxuRxRzWkORmTLkFT9zFZ3bCM4mDntCKwdIamET1k02XnbXjJt/1YWvsVY9kCE7EAs6EfZchP8knmcpMSD1K+rg2qUBx4c2TGVX5I4UXOK7zwRNHvJCQKBgQCogHePnAVyL2hiEL8ACxfA1JLhm+Eh9LZyRnUGiYJnvMNa+5HxbXPVzonxMX0DuvMUvTMSFyCbb6QgtJfUNx2AAVyN6hF6DmOAIKiLi12OV5TsvcFz3Y1ogmcNGb8J5SP9Zo2fiLNVInXRiu3aJB+Dr8DlU2xMFiGz7sbodh0yvQKBgEE5faQvrrlRAl9FY/xLw6d+dExK9qjSvKZNs0am0w/Y6miDYBbpdzhX60XkQbxLuzcLGFAZYu/72sOV6DjGNItj70cnd4W2pK6J0nNQGtsl2+1UU7TzPV36hLA/keI/lyhhLQCu3nuDCBpYS954AoruxrFziG++HKx/sxoYrh8JAoGAHIv/NX5KUekENzD9GuXGNW4OIFjHUHhj/IJBS0U6qZaKRjy+ofdYG43tQsaOlnYyvn27TEJMzOiiFsRhwJtBzTDt3EEeit4rL0bZJyMKS5Y87crMHnQXg/Arw0VsXyHJ8iFQtwly0GaRLVnrJ/zctfOtq2xxuYY6WYCHhFSWZ8UCgYEArBKsIfRPxV8IoPfIgY8zJ3KOFuFgGGOxNH74LKZbYJ8HWowoCms+/9fymVokRFGYYYP52YZPBTtO7dRIr+QVfjJ76FeEFl0gfZBXgnFAZksIqZn5pp2XDfCwroYC9MkFPDNbrjeMAEJCHYi+lQYRAkC+rvH4AC0atDS0+CxVrVs=";
		//9010000992
		//String privateKey = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCMbKz7orbT9m4Wl5JfTatkpPijF75VO8drbYkWsqHvHlZxCf5fl/hxatKQOYsOtDW0SQb4Oaw2sc2SnFPhP4Ckt24b04CiU6TcyTGOUslf9TGOJcYDXzfh/NXTdYxHlj+09x+T4lKyW2Lo1x+d0aVG90aN57SfZLYWXGn4DeqImyhbExD564HfL5DTKHBgdlMtf9i6n83FSMGZP5qfLWBXYxoYuziSiJtxy87aFyFqiT6q4URVd9a0rTb5SUtuSaZD7/fusDGbKJA3iIl3tlzsowwfTFKYVdZbm2vyW4F+gOzKY+t3NsKjJAPwKokEvyI4SGS9ScQmRP0VOaqpDUHvAgMBAAECggEAc/qOJIcPWaF+60nkqrILjOR0Nr+1g9ALtL6TKxv+gvAblOEcJvftSj9gCZ59K3fLo0ttZja79bCEiUJ0ROWd6m1Vk8XryCYtU+0X8OSPTaHFfBHWqZJLJWsdb7j2U2nU1DIG8q1Kf+TanFSQ2Ff+mc5Bh5KHq94SRgWtwCI+QvQe5W5BBG+AuMaSUQ6uIlUF8J+VhVNub+TgsMeDhAwsnONWscm0iUffy+PD/M6J5HjON6M/lGmcaJ95RqBHBOBDxZko4DObJxozmn8G0qiE9jdA20ZWRE7SiS7kk5GzrEerpn6VLhyWztT+EFPhHbmneqMThH1mIHjF7vPpOTW5gQKBgQD3PoS7nki234zarhssRYZGuMcXFiXxKY76Lmg+Kp90ztAEs3KvDekb+TAseWCtjT9kbOBs8D/74D6YcwvaqQSCfM9Bf+Q9P1PpjkPly07Z4i33LW/qBCSr1vJdQfSdhNYCigIMOHsGVoY4A7OLpCLFf+C8RaB7DA4fx2nPvtw8dQKBgQCRZb6oW6IpxuwjE7fV69q+3W0ha63oGERVIAm9HCueTgEYK9iWcaVu4YyAGG78cGIOE2SLKC7FLUqDtRycG0Z2AY+7O0fG1DNEQ8dlKPbgDF333+R/iHBR+zt+hXdG9uPIgp99aooSNY5fwzbQCr9MWtLlC5i9Hk5K6rXI/noIUwKBgDJI69QAyEcxRkN/n5UserfNguhmH8dhrWuinp7uKkomedZK7Os/iAahcrPI5+nETX1OS4K8hjbjD7gPxfxt3bXwTnLC4Re31ogs4BBPfjUANFsOSbGtsAunBtoCvQ6AYspXaT01C4hpdpjT3+NpuYyzinCgNJhyBjleZF3VMpO1AoGAcVyLp0YyjDINQFgKWB7Y4UFQ6eLDp5bsCBa/oRNKqtWUotzktH+NBYOTgPgL6IRS3zidhqXguVlTpJbb5JabfDxBbTCOCOa/xfS2jRfAS/U4ep+0zv543GJuA3paAhlSha3aXfVoC0R4ag+RN+l5NzYFAhHll2X+kwimjTxjtwMCgYBxxbaL/kehE1vUeHe8ZK94rjeWunS7wexd5yK3ImFP4RxEkOkDTrBq0sC03ItnbLJ6G6WckrTc0C4esXTSoT4hi57W/Jt+vkQmFz3SIq42kD28p03GXhg3MgW43Uu1LJViZWF6mFBtllezfzuUiWMiXqH8CjjHN1zULPrptY6+ug==";
		//待签名字符串
		//9010001013
		//String privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCC2BswZvDu1w9mxAW5DaeWD/SaP0UXCjQRUzHEEgkEnq0zxpJ0VnitWCcj6VCD7ic+2mBuKWxwQaR4/v7Cp15pwEd4t3gi8puPGfVDYBnbfASBQo9xW2VB3Go/2L5IGccsJr7G1hQoYzFUprv9+n6N/CgZ/QAaeDCbGpwrstMDgAxrhK7K0za3c7y4cqKpXzTuIkWBAZVKnQxUG0Uy7oasPyeIc18nb4tfwdm3AF9947XWARJEluTYUh5j6eldZa1JI4I2fBqRD6iDD0YDqksvYajORUa4oKdLjXNPU+vcgibH/S6Xhk+yqvx49UNc+eEHH0U0Bd0JYk7Twjp+kHEJAgMBAAECggEAYD4xkaLGqggKzh51zy6aDKlj1GmhmlbpAa1eN8jEVV+aKzFu3KAWV/s1nxapIMH2oCblM2LI3rGKF24JWojdy4+hgswBVEN75QDFBXAU1PseHS4mcM03aACHLrtxC3vao0FEpuElJqf/yzJxqVmt6SaHIMaWYuHxbsVeBk6dJD7oRoapbunOw8Yn6tqUe0AEq5IxbhtFfN/aB/HzTA9oDxX6s/l5+WrUTu+qBakrgRvt5+pHP/7xrfeif66PawEYwTkZVatrT3FelV0aGjalZ+E8HGsDvOZ9H4WDwMepqdAl96UYoppFjVyf2dRUqUOpBjohvxyIAxEZxNzUxV2W4QKBgQDA2zwcNJFABCQh/B+ZQrSEVhAVD+Ta/9nx76UXB+cYVtrdPmiCgpv6Hkfd1jggxwZQend6eH3Z7RnBSZLBfEqeZMJUOFyEd2uGnZ8qiKoBXBFz0j5V8hjUU5zEzyDVmRMhVtYwApawyjD7LU1je0WtJS+vPCmq82qJ1ESIJonozQKBgQCtrydsppNgQUcxtYDDlu0iwGp807xi0OY44vrnyu+z7lBdvJcB4yLVghL3BGYkd9eiR+taliRFVy/oJari9h9BOUZTm8Sktc9ZdjyhOYzOrooqkSmTNv77Xv4/pZ7o7L7RkdlXm/vNY074/MhZ6NF+fFQVKOK2Bech6VUNPPCZLQKBgQC6N7a+AWAzMQx2qE0WnptRoQxcWgGAGUYn319IcsAI92zT7rvZMSZNB8obAQsnUyXctbQCVLAoqlGRGmqTW2FtuhsOWXG0Et+Hl1EmL9oAABd1/49jS6X/91BLlTi+JtWa/o9IwmchA8rx7ddo9JHd0ydNDEmroOjBd25JDPVjDQKBgHLErP6EzBnsyMi2MRINp0ExeKxnlbKOa7Lnefi1BtQVBBIMzpQ2rQv82d9UjtJGRr07XPANCoavaD60tUkobdrBqBETe0uDwDJTiRjYWI0ylKYwKhLXYoJkcQlJvcoYtrbw83eK4UcE+mhZUWLoD5bPPRiGXdj96Y9lCginXi05AoGBAIUkDeKeZLtUoPC/op8kNehENMigR1F6au6nnrcCJz66SYy2Mx6+zFTtlJ1ZaasFBfdHU9+jG5Yrun0wkEYwb8TFa9BOMnPznBj7ezq/eZ8I6Kwxy0ZnOW4GFz8PGfvKDwoy4aqW0UdR2bup8RcDCaft/CIiAS4pM5sTfUPGD6mo";
		//9010001003
	//	String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCARCXYLQTYBBp56yVAb0N9PRrD2b/HBGjJXkX4lP46ykntdsPdztQa2N5ZLSvRYvmQZnMlgaK4iM7ipqFjCvnNlh03o5OHsiA8Qm+X1ue7AZiI+ATP2wlYP0yCuhI+gCAlUXpJMdPu7D2+hpfOZqXQOb4n0UkHJQaHhF56jBIGJ/SGeNz9F+wA5TQDbrIoDoc3Oe3x2osXLjjcZdkCwHFhcl20K2F2OhbkaICcv1BwZ3yVNRIvsttAHoAWGqdqYa0tMqduGpX/R8jyNPLE5qhi2pjU/NsmZJPU0xsZ5LNZ8d9CYwO8YcCzHf1zoNUKJMeNQm/OxCw69tOCIoKjjKOJAgMBAAECggEAB+8vQB4YzJwEX0JSfZfkXtzmyPKxUEhPixVP0r60s0JMLGpKV3Ax4q9DodSPf1ZWBb6wpMNOI4gi9BZGqW/GYBdHG2f8+knkgJXDfWsHzqOcoexlr83m2BSiUPvdoQwf8O8HV3vDlmX6s8xzm4rx+t13i9MAZ3a83qkrhv0fKE4843Euvr93GOXy/EYp5LIAU5OEqdwuwhnBpFtNwiaCdkWv9pBOTfAqa00eMFvQOJHgh56TQcqc5hLzQuu3DlGIY5bRKJvAUKuxtAoZe1PjdFR65C8fnGVp+R/HeLoaJ5mHvksgxfrQKqAIW4HMZq1S4yiQjO0yWO5VTPGkpevV4QKBgQD4E0Z5/N3iBWI14o3KDFLPybcUBmWv2u4ZSoQ8tfhzHn1fkMdUhREV3AGBOCTzO5BPtXisLg7uIHuHEgrzJoAgOEIREPGPHvarppZBaTXpY+OZV3+uJIXDRl79qp4LzYbB/SWg5NoVj/Wa6jlyGkVTmqe89dVd6GmT7kIRUrF9FQKBgQCEXRc4skoWlsnZ/nQn9ZlMvDPm11J3EMXCe7hOCA9PrCa89EQLCD4Snxn9Z8Kdl7kshuF5Tm+YtxLpfV6Dg17KZEF+yHQdNC0Yw/3SvHjf0aEKT0OZJWu8kV++bcmRFjnmmIobKaXYAe35fQRggeMkmpx8MZufldLy1f7nzt4xpQKBgA4VwhWhBtQTbC0VDOng4z7K/CsV5eYjLwuekH2F83JN095+MOManwq3sBovHuH0itALxKs5/j0E+R6FJrwBfprVtuKTLLPslSLP1Y4nNBlpQ33sWuJ/Tlz0/OW4LzsKU1FmVyGYfM1mnBJMpDBXT4JcsJNo/dFUJdJxX6zuHge1AoGAYxn/xOdYKUmEn76v4Ss1OArBIVid2Z+pMU/IUkQekpxl9Js+RrRJJVAR+XSydaOfdQMX1ecwDrsjYcYH+ZdmZUpi7zXOGf9ytFQpupp6NbmSRVHLXuJxo6GEeeNhQ0vM1iXnYSbxkKUBNAayPD+DfWh1OVc7GWTnqAEo9Q4htjECgYEAxTp+r6vCOziqVku+IYxjD4XPZQfuiPMdgp7QNoeornbdZ4CKIUeZnRt5vKqhgt5faCU9SqW3xjMZwkDOefu9jR1sMENpxXFcSFHAZRm0tbXvdac5TP1QyyhPsM+IA2M9ixpZiHBzi3NiCk1eBvNbEYG85b6J9KitbkrjIruS3FY=";
		
	//	String privateKey="MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCEyiMYQceOMstvf/Eqt7VE4t8lcekTDol+8PLJTCiIE/U0i/PUotq2vXT5Q2cnn0V+qqfQYxzUh1+kljaqy0KJjg/NwTJdZ65tNVCOxGPG9/GAZZW6eNmeyPA+7r4nHTBrG8Jlcyy+HJ9308StTRShad5QKn8ijazUnLO9ETR2uJH771jG4cldZnMBgpPIILulvHonDqRymLBBAL+jxlYGwR+1h/jvK2AU2XTelo8HKJm8MeqC9thiuXQkMJI/53GAuvsujTnBXn8/HR1lk2V8V7/7viGehD2UNh0oqJfwj8Sgh/HJ4WyrugjP4qObl4NLto/RH8yV/P6vSg1N12M9AgMBAAECggEAMj24oYjh0+9sATfLK3puXy3nd+dtZARwCVNoyLjD7RiAAe2hx6YGUnXEODtEYD1msdInxDR7x/2gNkmPTXeyKRFS8WpTf/umhzNQ03OCvNk6ynWk/PSUgftMysLRt0LX+u550qCNITD7NP8uvtMXJytDLdmmWJB5/8d6SeKkkvYZ4bJ+GB6ohsZCbNVwvSFk3o0drNP10X/bXhgxVA2/Oki6HDKZGMfWJsxbDSBNchbTKCD1AT4NjVsidLeDcSW7TMKSVE3cCUijPpBuUyop9J5/ByuGmKB4wJrxb8oY8FU4stcFhx0wj6hc4UUOU9kZlPaOJ5nS8y1VG9NCwKZpkQKBgQDww9OV6MuxQgPQ9P2SG9YayR6c/jx6m/F8D1574bMWJHk6hFUsPQcbUsk29Q3jfPpOYQJiMm5Xy0GvR7XGeNCWbsl1Iew0xci2NUo0AvPJ/a/I+Pt/5JVadzbR7IFDZms25f2f223FXHeArYHIk7STC5nFpoN//3seqprBaGM2UwKBgQCNMTU+XB3vFZMvlUFYiQsNOcMch77u2fCJ0tyfjwqqzS21fBADoJSwo87kE3T8dQizhJr5vBSNhhMhU6R2iK1rgE+biQ4KPH87ULvw/PH7urJ/z3v+5AY/PFhEjtXC3C12j3hvRYTgtCONsUmG5kbSNMuxCR5w4Kx9mHubefyuLwKBgQDD+ZIjZs9OZH64uoIgSNo5CVYK0gKmhoLEybnNuoRMVLn8wLSXgsFjIzq3qjZ44kTYQaxwA76/Zan8o7E5cSPQHzOKTWd3+ToL3kqdFX4c/4rzWn9UtdpAPeN4cjsxgo9oq0KLuU+bo01+hzyp6FjGjF3JfWVybaFo79Nx9Kc1EwKBgGHiepI4FLybDYaQiFQT9mh94imWZqV6zIsQd8naxj9xrb3DO+r32f24Iwf2AUXA8X5bcYGvheQzznFtZniS+v+rXgVreCNam66IozNIeY7WeUeolcM5XKdcc6lcCRBlytMGVi12Wc/as0dZYKs4XGMBHTIoK0WATbVpEFDSun4bAoGBAJzyyM3124qSu2vQ19AHYXVYVLHu4c051pvW6+UbqaTWKr88Lwhs490sBO0V/Pyo8Au+XNfZ94lNII78VnCZ3TdE7UbyMscxyxiDsJqK2D4nUorDK8SSDJ2evn7lKghzhAZ5rxDaJDCNxKXiW3oUzuPIukOEnOXYedXjtRy1aOWO";
		
		//String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDJ6tTFzHDmNqtQUeiNddSSqwJ+XhAKjpCm0VBizGi/ShbAqGgHRZ0JWyJ8j9Ip3Md36xBbAOHSaCt9o23lVbzIO/gc7z6hP+s2rT3qTgGy9h1hgkWUrdWnfIYhRy/DFIPUvWdVtJIE6uiM/3emmGtdCqSvfYAjjL4yftI3zu3pZ/mzuy5mC4rpiawkbAJb8cGzBEiSJ+/1k1PkyPComskhtP7+Zdff49927Zupb6iIGUZHDGspskFf5O8d9C+RtoHJekhMTJVGn+rKH3YwQCM+HVeyr6Ci+/Su6ZlB99J2T/Oa9s+Zf/cT/H5mEqjI8jNZ2+3r1nwv2OpVuX5pOHt9AgMBAAECggEBAJpSkcaPh2ZcODkOMSgU+6ARdblxWN9IsQPejIrGYfR5wsQCsmeEbA5fig8buOvn4sgjb3+uP+oZVcwToYWbLsr3Ep3CuOJzQOmsJNs0PZYzBqek8ls9NUNqJ/W9O6LkxhFNpXHarZN2msBQ7Hj2m3AkF1RNHieNhTyAs1iWHj25rrAGwHhPYsz0UBXyKc39GlKb1Ld55478PHP2rB3QFQLdkXRzdAXmiVAMUZWOu2qbcuCXh9tVXlVMBrQsK7RD4jLOxX2NQ5r1pxxd/PEbvHPr+CnkBJChAzJNtFuPLMVA8YfmPd9BiwVP/nuQvzmMnJjk18zkZxs+0wb/xnLBAT0CgYEA9EMqC8dQlrVfrRG298ILuad37yJeH9gwfpJnj1Nq+6fEYFG/idQ9AUYxVsOZkzDUlaMnxvWPpG10qeb+1LH1/W2aNZv6KOH0pBxSVLILEhrQ+m3Q0+GA8baxR62A1DTJudqDIc741tef4+pf7Qs8wwaKWy26qQ2MKgUacns4lFcCgYEA057AfYWxVaq8hUi3yteFbG4I2Bvb6RUFRAh8f40RfaOht1Bgu5srEMExHdC7UqOqsi75WoaMH87rymitjCQSUdwRLVHswvsjR5vnBBboScDLMOGtTwJJ9pGmWRDpnmtSnzWHSmL6lG2DYYOjrC/jG+qUI6C5nmbH+URA/6PvaksCgYBJHUWbqvibdAUEiBXtiX76/n7u7Kmh/JNff7FR/JU8aNZmQnID7qyoEfu4zenNXkNz2xeP/XcWr9DI/qTuw6ovRrxvKZYy4Jfa+8NFKNoZfD9rhq2IjqqLL7VXED9MxdRQNnEkEJD5tiddEI3QJZn1gqKtcj26VpzLAyYMeIfSFwKBgG0815Rz/NSB7ArJPMaididAraQQzXYF+c/iqZGWGl9GpmXgQe7V7GcbsuT3KJtCDQ0pnmISbibFQ4YafuuGe0ZyWROR5KvJw65lVd0Qjhv0P28eL75HKjLzgHEdf9rc/6tbkFjfuwX/egBOWaC6n3t7nbi+cNLJr5SGnR7ZWu79AoGAK5h8Xi7pEkM5FevG/Wh0Ex2oUV/IFjlxTzr37EMCTSoPybISEk9I6KyT91NzoB0I51SXS/MW4jbS7c2Gz/WUNhOADnIOM0C8/1LSKnp3+ObSaQjY9kUxDWewKTeaSp/5Vgs6PIJwtpdejQW3Ro8OGl7eEijalrH3Bt+vFDAy4mU=";
		//9010001045
	//	String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCbFTJho/mH0LUa7wA5b5OqAvOdLUGvHWzhvBLT0CHARkkIdOFo037yuZ7pKm7KMeJtA0VVdpSR5TOtvg157DpgV0P64ih4OuwdOk+1mvs5YhAawH+Y8Z8C9LUCDIQaZE4klSovW+AjQm1Kf8jZY1bRu7+MDc3Fsrh9QUL4TOhqd0Jhov611jYFRF6De9WmcWBtfeGcVkiZeYAdf+mCkz3IVg/P7HctLC79Zkb8lWMQeQNOEeRtyhdyFrFY8H7z7o1mXNzj0RsMugHce2BS3glim8xSu+BpV82weeV+qvZhzGLtxk6zk032izobAoZDhOLk9Azd9focx7qPqIE4U7rzAgMBAAECggEAURs13TDn2OsDPJHQeTlsbdbbV9GKb64g+rHk2tgYPrnDimOuW6d41jLT9DfX93E9bSvvv/GjY+YOMAicfa5ZHXemOvi6l56hS+vqMh1a1DkAGS2hzfhX5DDc1bR8Bl19LM7CYEBZqbcc94B7J443orrhQ7sYahO9Puy5vKhkSQFBgpk6VBuMGXYB6pNBajh7d2xRg8awuejBDTemxMkJF9Pucdqg7hVjvnU05clgKkW4WFm2duF83idgfH+x3uABgHYCLpQp5jGkstw6wFBT4YwA1gdiDu+1SV6u5PfoNmSpi+X87RFKQVWV2c5ou/3Y3/6qRKrFaDDptwRWdpXtcQKBgQDnLn1PjzRP0S2OxRXzGchknY53Ajc9FPTcCvrAZTLbxDYj+KCDOt7IlRmovViV4iOLiwrhtkHwDBoBBd1300U+QeuUl4jPYQHCGoA5ekSiscK/R64EDbFoywT7Nb5W/n6Wgkk1D2MWB4Ty1oFoPaLZ9GBQzwWXHz2MHUClUBdjiQKBgQCru000BZzmovkcp7iHjvvVBsRKdlQKkIolVqtm6PXkCZlq3i68XUVfRKhXoK76/5vQUFNUivje1vgED3atLn6wLv/3JJUDeu+FPPODjBnjjvoPonqhlQGU3JVw0bDPO2m0Acp1OdAv6EYTxBKtNUyeWc+vsdZubPt6unOHi53/mwKBgF40kZfkETsdUGVHZsRjk0d89NsGKiBX89ffGYNhObJYaH+MB0yypqoyJ/yc/AZH2pg4lryXmpMJO2eDTlZP332ZOziX74YiT6cbSXkim+l4apWrzfuU4OCFKoh7xA6LMmZ+vPpEROprMgAK7TzfMQeED+OeGPcWIeyA1yEGXCGRAoGADA3BhX0/XhDiW1iKYzluA63N6UwU6gjrKWPD9B015SOnWRDvRtllHSK8jioBHi0CysRYlP6MKVf9YIuVUOKKkeM6pCY1L2FZnoiAryMOTiKi+qX05ZKH4eL5ukl9l44cBT567293ZWVwRcLlbLLpBdRX94UMAAQ2Qo8DvOfX1zECgYEAnuiE90VMoII9MSwFvgQeiifAs/pmP6R16PjnXGg6mb9zzHXxgVazFVseUtaw8HLy5vcrgfMtAkdGt42Wz4RqYWMAr+7dP2xXfnONDMBlV51n32/iNY+cPuZ9h6v7o+wUPtzNTe5+xwexCxcl2nTSHxjT57uHX/GH7Ri9cQxu1Ws=";
		Map<String,String> params = new HashMap<String,String>();
		params.put("orderNum", "2018010414350001"); 
		params.put("memberCode", "9010000988");  
		params.put("callbackUrl",  "http://web.oqurux.net/app/jhepay/result.json");
	//	params.put("payMoney", "635" );
		//params.put("bankCode", "CEB");
	//	params.put("sceneInfo", "appPay");
		params.put("ip", "140.243.84.145");
	//	params.put("payType", "3" );
	//	params.put("isMoneyFilled",  "1");
	//	params.put("platformType",  "1");
		
	/*	params.put("orderNum", "JH71685874284331037233"); 
		params.put("memberCode", "9010000992");  
		//params.put("callbackUrl",  "www.baidu.com");
		params.put("payMoney", "0.01" );
		params.put("bankName", "华夏银行" );
		params.put("bankAccount", "6230200730839151" );
		params.put("accountName", "刘铭峰" );
		params.put("certNo", "350802198705121017" );
		params.put("tel", "15960281039" );*/
		
 		String srcStr = StringUtil.orderedKey(params);
 		System.out.println("srcStr==="+srcStr);
		String signstr = sign(privateKey, srcStr);
		System.out.println(signstr);
		
		System.out.println(checksign("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiMZCzPkASk6sPQbAko4i8OaTtfOh8IDDmSF1BQ1Z0bGMj3ohbJz4GjUJk+y+VK9Ix7SnNMlHZOY3fARtOjeNOO+DIgczTTSSWuJpD0H1JbA9E9DTC3rIxbYSqskMUtXDKqc/G9+9qcLFm9egVTP/YuWpPSYKoLgLRJMUdpUjXdLJ/xhKh/YXcOkbzB/2P3j87XQN2wDSS+uINIayigVtGHCb1yaShfLyMLjbmpjqCjs5RCD2QtOd99Y+HPMcPxFIgW0vbBCwE/jCzZWFxyFraQw2yUzWRilw7JCaX2nu4n1uE1OmZa2oluCRmm5dXeQx9reJOTZxaoqrj2Vj4WfLvQIDAQAB",srcStr,signstr));
		
		
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
