package com.epay.scanposp.common.utils.ms;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <strong>Title : MSCommonUtil</strong><br>
 * <strong>Description : 通用工具类</strong><br>
 * <strong>Create on : 2013-4-25</strong><br>
 * 
 * @author linda1@cmbc.com.cn<br>
 */
public final class MSCommonUtil {

	private static final Logger logger = LoggerFactory.getLogger(MSCommonUtil.class);

	
	/**
	 * 生成指定长度的随机字符串
	 * 
	 * @param length
	 *            指定字符串长度
	 * @return
	 */
	public static String generateLenString(int length) {
		char[] cResult = new char[length];
		int[] flag = { 0, 0, 0 }; // A-Z, a-z, 0-9
		int i = 0;
		while (flag[0] == 0 || flag[1] == 0 || flag[2] == 0 || i < length) {
			i = i % length;
			int f = (int) (Math.random() * 3 % 3);
			if (f == 0)
				cResult[i] = (char) ('A' + Math.random() * 26);
			else if (f == 1)
				cResult[i] = (char) ('a' + Math.random() * 26);
			else
				cResult[i] = (char) ('0' + Math.random() * 10);
			flag[f] = 1;
			i++;
		}
		return new String(cResult);
	}
	/**
     * 读取request流
     * @param req
     * @return
     * @author guoyx
     */
    public static String readReqStr(HttpServletRequest request)
    {
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try
        {
            reader = new BufferedReader(new InputStreamReader(request
                    .getInputStream(), "utf-8"));
            String line = null;

            while ((line = reader.readLine()) != null)
            {
                sb.append(line);
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                if (null != reader)
                {
                    reader.close();
                }
            } catch (IOException e)
            {

            }
        }
        return sb.toString();
    }
    /**
     * str空判断
     * @param str
     * @return
     * @author guoyx
     */
    public static boolean isnull(String str)
    {
        if (null == str || str.equalsIgnoreCase("null") || str.trim().equals(""))
        {
            return true;
        } else
            return false;
    }
    public static void main(String[] args) {
		String a="null";
		System.out.println(isnull(a));
	}
    
    /**
     * 生成待签名串
     * @param paramMap
     * @return
     * @author guoyx
     */
    public static String getPlainText(Map<String, Object> params)
    {
        StringBuffer content = new StringBuffer();

        // 按照key做首字母升序排列
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys, String.CASE_INSENSITIVE_ORDER);
        for (int i = 0; i < keys.size(); i++)
        {
            String key = (String) keys.get(i);
            if ("sign".equals(key))
            {
                continue;
            }
            String value = (String)params.get(key);
            // 空串不参与签名
            if (isnull(value))
            {
                continue;
            }
            content.append((i == 0 ? "" : "&") + key + "=" + value);

        }
        String signSrc = content.toString();
        if (signSrc.startsWith("&"))
        {
            signSrc = signSrc.replaceFirst("&", "");
        }
        return signSrc;
    }
    public static String readContentFromPost(JSONObject req,String url) throws IOException {

		// Post请求的url，与get不同的是不需要带参数

		
		URL postUrl = new URL(url);

		// 打开连接

		HttpURLConnection connection = (HttpURLConnection) postUrl
				.openConnection();

		// 打开读写属性，默认均为false

		connection.setDoOutput(true);

		connection.setDoInput(true);

		// 设置请求方式，默认为GET

		connection.setRequestMethod("POST");

		// Post 请求不能使用缓存

		connection.setUseCaches(false);
		connection.setInstanceFollowRedirects(true);

		// 配置连接的Content-type，配置为application/x-
		// www-form-urlencoded的意思是正文是urlencoded编码过的form参数，下面我们可以看到我们对正文内容使用URLEncoder.encode进行编码
		connection.setRequestProperty("Content-Type",
				"application/json;charset=utf-8");
//		connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
		// 连接，从postUrl.openConnection()至此的配置必须要在 connect之前完成，
		// 要注意的是connection.getOutputStream()会隐含的进行调用 connect()，所以这里可以省略
		DataOutputStream out = new DataOutputStream(
				connection.getOutputStream());

		// 正文内容其实跟get的URL中'?'后的参数字符串一致

		String content = EncodeChanger.unicode2UnicodeEsc(req.toString());
		// DataOutputStream.writeBytes将字符串中的16位的 unicode字符以8位的字符形式写道流里面
		out.writeBytes(content);
		out.flush();
		out.close(); // flush and close
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connection.getInputStream(),"utf-8"));
		String line;
		String res = "";
		while ((line = reader.readLine()) != null) {
			res = res + line;
		}
		reader.close();

		// connection.disconnect();
		return res;
	}
    /**
     * 生成待签名串
     * @param paramMap
     * @return
     * @author guoyx
	 * @throws UnsupportedEncodingException 
     */
    public static String genSignData(Map<String, String> params) throws UnsupportedEncodingException
    {
        StringBuffer content = new StringBuffer();
        String charset = "UTF-8";
        // 按照key做首字母升序排列
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys, String.CASE_INSENSITIVE_ORDER);
        for (int i = 0; i < keys.size(); i++)
        {
            String key = (String) keys.get(i);
            if ("sign".equals(key))
            {
                continue;
            }
            String value = params.get(key);
            // 空串不参与签名
            if (MSCommonUtil.isnull(value))
            {
                continue;
            }
            content.append((i == 0 ? "" : "&") + key + "=" + value);

        }
        String signSrc = content.toString();
        if (signSrc.startsWith("&"))
        {
            signSrc = signSrc.replaceFirst("&", "");
        }
        return signSrc;
    }
	public static JSONObject formStr2Json(String content){
		JSONObject reqObj = new JSONObject();
		String splitStr = "&";
		String data[] = content.split(splitStr);
		for(int i=0;i<data.length;i++){
			String value = data[i];
			String[] temp = value.split("=");
			if(temp.length>=2){
				reqObj.put(temp[0], value.substring(value.indexOf("=")+1, value.length()));
			}else if(temp.length==1){
				reqObj.put(temp[0], "");
			}
		}
		return reqObj;
	}
}