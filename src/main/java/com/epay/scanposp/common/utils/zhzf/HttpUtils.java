package com.epay.scanposp.common.utils.zhzf;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class HttpUtils {
	/**
	 * 发送HTTP--GET请求
	 * 
	 * @param url
	 * @param propsMap
	 *            发送的参数
	 */
	public static String httpGetSend(String url) {
		String responseMsg = "";
		HttpClient httpClient = new HttpClient();
		GetMethod getMethod = new GetMethod(url);// GET请求
		try {
			// http超时5秒
			httpClient.getHttpConnectionManager().getParams()
					.setConnectionTimeout(5000);
			// 设置 get 请求超时为 5 秒
			getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,
					5000);
			getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
					new DefaultHttpMethodRetryHandler());
			httpClient.executeMethod(getMethod);// 发送请求
			// 读取内容
			byte[] responseBody = getMethod.getResponseBody();
			// 处理返回的内容
			responseMsg = new String(responseBody, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getMethod.releaseConnection();// 关闭连接
		}
		return responseMsg;
	}

	/**
	 * 发送HTTP请求
	 * 
	 * @param url
	 * @param propsMap
	 *            发送的参数
	 */
	public static String httpSend(String url, Map<String, Object> propsMap) {
		String responseMsg = "";

		HttpClient httpClient = new HttpClient();

		PostMethod postMethod = new PostMethod(url);// POST请求
		postMethod.getParams().setParameter(
				HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");

		// postMethod.
		// 参数设置
		Set<String> keySet = propsMap.keySet();
		NameValuePair[] postData = new NameValuePair[keySet.size()];
		int index = 0;
		for (String key : keySet) {
			postData[index++] = new NameValuePair(key, propsMap.get(key)
					.toString());
		}
		postMethod.addParameters(postData);
		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8"); 
		try {
			httpClient.executeMethod(postMethod);// 发送请求
			// Log.info(postMethod.getStatusCode());
			// 读取内容
			byte[] responseBody = postMethod.getResponseBody();
			// 处理返回的内容
			responseMsg = new String(responseBody,"utf-8");

		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			postMethod.releaseConnection();// 关闭连接
		}
		return responseMsg;
	}
	/**
	 * 发送HTTP请求
	 * 
	 * @param url
	 * @param propsMap
	 *            发送的参数
	 */
	public static String httpSend2(String url, Map<String, String> propsMap) {
		String responseMsg = "";
		
		HttpClient httpClient = new HttpClient();
		
		PostMethod postMethod = new PostMethod(url);// POST请求
		postMethod.getParams().setParameter(
				HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		
		// postMethod.
		// 参数设置
		Set<String> keySet = propsMap.keySet();
		NameValuePair[] postData = new NameValuePair[keySet.size()];
		int index = 0;
		for (String key : keySet) {
			postData[index++] = new NameValuePair(key, propsMap.get(key)
					.toString());
		}
		postMethod.addParameters(postData);
		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8"); 
		try {
			httpClient.executeMethod(postMethod);// 发送请求
			// Log.info(postMethod.getStatusCode());
			// 读取内容
			byte[] responseBody = postMethod.getResponseBody();
			// 处理返回的内容
			responseMsg = new String(responseBody,"utf-8");
			
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			postMethod.releaseConnection();// 关闭连接
		}
		return responseMsg;
	}

	/**
	 * 发送Post HTTP请求
	 * 
	 * @param url
	 * @param propsMap
	 *            发送的参数
	 * @throws IOException
	 * @throws HttpException
	 */
	public static PostMethod httpSendPost(String url,
			Map<String, Object> propsMap, String authrition) throws Exception {
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(url);// POST请求
		postMethod.addRequestHeader("Authorization", authrition);
		postMethod.getParams().setParameter(
				HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		// 参数设置
		Set<String> keySet = propsMap.keySet();
		NameValuePair[] postData = new NameValuePair[keySet.size()];
		int index = 0;
		for (String key : keySet) {
			postData[index++] = new NameValuePair(key, propsMap.get(key)
					.toString());
		}
		postMethod.addParameters(postData);
		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8"); 
		httpClient.executeMethod(postMethod);// 发送请求
		return postMethod;
	}

	/**
	 * 发送Post HTTP请求
	 * 
	 * @param url
	 * @param propsMap
	 *            发送的参数
	 * @throws IOException
	 * @throws HttpException
	 */
	public static PostMethod httpSendPost(String url,
			Map<String, Object> propsMap) throws Exception {
		String responseMsg = "";
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(url);// POST请求
		postMethod.getParams().setParameter(
				HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		// 参数设置
		Set<String> keySet = propsMap.keySet();
		NameValuePair[] postData = new NameValuePair[keySet.size()];
		int index = 0;
		for (String key : keySet) {
			postData[index++] = new NameValuePair(key, propsMap.get(key)
					.toString());
		}
		postMethod.addParameters(postData);
		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8"); 
		httpClient.executeMethod(postMethod);// 发送请求
		return postMethod;
	}

	/**
	 * 发送Get HTTP请求
	 * 
	 * @param url
	 * @param propsMap
	 *            发送的参数
	 * @throws IOException
	 * @throws HttpException
	 */
	public static GetMethod httpSendGet(String url, Map<String, Object> propsMap)
			throws Exception {
		String responseMsg = "";
		HttpClient httpClient = new HttpClient();
		GetMethod getMethod = new GetMethod(url);// GET请求
		getMethod.getParams().setParameter(
				HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		// 参数设置
		Set<String> keySet = propsMap.keySet();
		NameValuePair[] postData = new NameValuePair[keySet.size()];
		int index = 0;
		for (String key : keySet) {
			getMethod.getParams().setParameter(key,
					propsMap.get(key).toString());
		}

		httpClient.executeMethod(getMethod);// 发送请求
		return getMethod;
	}

}
