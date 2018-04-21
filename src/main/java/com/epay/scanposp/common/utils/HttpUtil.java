package com.epay.scanposp.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtil {
	private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
	
	public static JSONObject getPostJson(HttpServletRequest request) {
		return JSONObject.fromObject(getPostString(request));
	}

	public static String getPostString(HttpServletRequest request) {

		StringBuffer buffer = new StringBuffer();
		try {
			InputStream inputStream = request.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
		} catch (UnsupportedEncodingException e) {
			// SysLogger.error(HttpUtil.class, "请求字符集不支持，获取请求数据出错");
			e.printStackTrace();
		} catch (IOException e) {
			// SysLogger.error(HttpUtil.class, "IO异常，获取请求出错");
			e.printStackTrace();
		}

		return buffer.toString();
	}

	public static String sendGetRequest(String requestUrl) {

		StringBuffer buffer = null;

		try {
			// 建立连接
			URL url=null;
			if(requestUrl.contains("https")){
				url= new URL(null,requestUrl,new sun.net.www.protocol.https.Handler());
			}else{
				url = new URL(requestUrl);
			}
			HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
			httpUrlConn.setDoInput(true);
			httpUrlConn.setRequestMethod("GET");

			// 获取输入流
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			// 读取返回结果
			buffer = new StringBuffer();
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}

			// 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			httpUrlConn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buffer.toString();

	}

	public static String sendPostRequest(String requestUrl, String content) {

		StringBuffer buffer = new StringBuffer();
		try {

			URL url = null;
			if (requestUrl.contains("https")) {
				url = new URL(null, requestUrl, new sun.net.www.protocol.https.Handler());
			} else {
				url = new URL(requestUrl);
			}
			HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod("POST");
			httpUrlConn.setConnectTimeout(10000);
			httpUrlConn.setReadTimeout(60000);
			// httpUrlConn.setRequestProperty( "Cookie",
			// "JSESSIONID=62A1A46FD933C9EDC6FCB16E7E4214E9");

			
			
				httpUrlConn.connect();
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 当有数据需要提交时
				if (null != content) {
					// 注意编码格式，防止中文乱码
					outputStream.write(content.getBytes("UTF-8"));
					// outputStream.write(URLEncoder.encode(content,
					// "utf-8").getBytes());
					outputStream.flush();
					
				}
				outputStream.close();
				int responseCode = httpUrlConn.getResponseCode();
				if (responseCode == 200) {
					logger.debug("response code -----" + responseCode + "-------------");
				// 将返回的输入流转换成字符串
				InputStream inputStream = httpUrlConn.getInputStream();
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

				String str = null;
				while ((str = bufferedReader.readLine()) != null) {
					buffer.append(str);
				}
				bufferedReader.close();
				inputStreamReader.close();
				// 释放资源
				inputStream.close();
				inputStream = null;
			} else {
				logger.error("Error code -----" + responseCode + "-------------");
			}

			httpUrlConn.disconnect();
			return buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return e.getMessage();
		}
		//return buffer.toString();

	}

	public static String sendPostRequest(String requestUrl, Map<String, String> params) {
		String paramsStr = createPostParamsFromMap(params);
		// SysLogger.forceInfo(HttpUtil.class, "http请求参数:"+paramsStr);
		return sendPostRequest(requestUrl, paramsStr);
	}

	public static String createPostParamsFromMap(Map<String, String> params) {
		StringBuffer paramBuffer = new StringBuffer();
		String paramsStr = "";
		for (String key : params.keySet()) {
			// if(params.get(key)!=null&&!"".equals(params.get(key))){
			try {
				paramBuffer.append("&").append(URLEncoder.encode(key, "utf-8")).append("=");
				if (params.get(key) != null && !"".equals(params.get(key))) {
					paramBuffer.append(URLEncoder.encode(params.get(key), "utf-8"));
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return "";
			}
			// paramBuffer.append("&").append(key).append("=").append(params.get(key));
			// }
		}
		if (paramBuffer.length() > 0) {
			paramsStr = paramBuffer.toString().substring(1);
		}
		return paramsStr;
	}
	
	public static String sendPostCharsetRequest(String requestUrl, Map<String, String> params,String charsetName) {
		String paramsStr = createPostParamsFromMap(params,charsetName);
		// SysLogger.forceInfo(HttpUtil.class, "http请求参数:"+paramsStr);
		return sendPostRequest(requestUrl, paramsStr,charsetName,"POST");
	}
	
	public static String sendGetCharsetRequest(String requestUrl, Map<String, String> params,String charsetName) {
		String paramsStr = createPostParamsFromMap(params,charsetName);
		// SysLogger.forceInfo(HttpUtil.class, "http请求参数:"+paramsStr);
		return sendPostRequest(requestUrl, paramsStr,charsetName,"GET");
	}
	
	public static String createPostParamsFromMap(Map<String, String> params,String charsetName) {
		StringBuffer paramBuffer = new StringBuffer();
		String paramsStr = "";
		for (String key : params.keySet()) {
			// if(params.get(key)!=null&&!"".equals(params.get(key))){
			try {
				paramBuffer.append("&").append(URLEncoder.encode(key, charsetName)).append("=");
				if (params.get(key) != null && !"".equals(params.get(key))) {
					paramBuffer.append(URLEncoder.encode(params.get(key), charsetName));
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return "";
			}
			// paramBuffer.append("&").append(key).append("=").append(params.get(key));
			// }
		}
		if (paramBuffer.length() > 0) {
			paramsStr = paramBuffer.toString().substring(1);
		}
		return paramsStr;
	}
	
	public static String sendPostRequest(String requestUrl, String content,String charsetName,String requestMethod) {

		StringBuffer buffer = new StringBuffer();
		try {

			URL url=null;
			if(requestUrl.contains("https")){
				url= new URL(null,requestUrl,new sun.net.www.protocol.https.Handler());
			}else{
				url = new URL(requestUrl);
			}
			HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);
			// httpUrlConn.setRequestProperty( "Cookie",
			// "JSESSIONID=62A1A46FD933C9EDC6FCB16E7E4214E9");
			// 当有数据需要提交时
			if (null != content) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码

				outputStream.write(content.getBytes(charsetName));
				// outputStream.write(URLEncoder.encode(content,
				// "utf-8").getBytes());
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, charsetName);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			return buffer.toString();
		} catch (ConnectException ce) {
			ce.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buffer.toString();

	}
	
	
	public static String sendPostRequest(String requestUrl, String content ,String chartSet) {

		StringBuffer buffer = new StringBuffer();
		try {

			URL url = null;
			if (requestUrl.contains("https")) {
				url = new URL(null, requestUrl, new sun.net.www.protocol.https.Handler());
			} else {
				url = new URL(requestUrl);
			}
			HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod("POST");
			httpUrlConn.setConnectTimeout(10000);
			httpUrlConn.setReadTimeout(60000);
			// httpUrlConn.setRequestProperty( "Cookie",
			// "JSESSIONID=62A1A46FD933C9EDC6FCB16E7E4214E9");

			
			
				httpUrlConn.connect();
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 当有数据需要提交时
				if (null != content) {
					// 注意编码格式，防止中文乱码
					outputStream.write(content.getBytes(chartSet));
					// outputStream.write(URLEncoder.encode(content,
					// "utf-8").getBytes());
					outputStream.flush();
					
				}
				outputStream.close();
				int responseCode = httpUrlConn.getResponseCode();
				if (responseCode == 200) {
					logger.debug("response code -----" + responseCode + "-------------");
				// 将返回的输入流转换成字符串
				InputStream inputStream = httpUrlConn.getInputStream();
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream, chartSet);
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

				String str = null;
				while ((str = bufferedReader.readLine()) != null) {
					buffer.append(str);
				}
				bufferedReader.close();
				inputStreamReader.close();
				// 释放资源
				inputStream.close();
				inputStream = null;
			} else {
				logger.error("Error code -----" + responseCode + "-------------");
			}

			httpUrlConn.disconnect();
			return buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return e.getMessage();
		}
		//return buffer.toString();

	}
}
