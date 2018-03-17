package com.epay.scanposp.common.utils.xinfu;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class HttpUtils {

	private static Log logger = LogFactory.getLog(HttpUtils.class);

	public static HttpClient getClient() {
		RequestConfig.Builder requestBuilder = RequestConfig.custom();
		requestBuilder = requestBuilder.setSocketTimeout(200000);
		SSLContext sslContext = null;
		try {
			sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null, new TrustManager[] { new X509TrustManager() {
				public void checkClientTrusted(X509Certificate[] arg0,
						String arg1) throws CertificateException {

				}

				public void checkServerTrusted(X509Certificate[] arg0,
						String arg1) throws CertificateException {

				}

				public X509Certificate[] getAcceptedIssuers() {
					return new X509Certificate[] {};
				}
			} }, new java.security.SecureRandom());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}

		CloseableHttpClient client = HttpClientBuilder
				.create()
				.setSSLSocketFactory(new SSLConnectionSocketFactory(sslContext))
				.setDefaultRequestConfig(requestBuilder.build())
				.setHostnameVerifier(new AllowAllHostnameVerifier()).build();
		return client;

	}

	public static String exctueRequest(HttpRequestBase request) {
		HttpResponse response = null;
		String result = null;
		try {
			response = getClient().execute(request);
			int statuscode = response.getStatusLine().getStatusCode();
			if (statuscode == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				result = EntityUtils.toString(entity, "UTF-8");
			} else {
				logger.error("调用http返回码不正常:code:" + statuscode + ",返回值："
						+ EntityUtils.toString(response.getEntity(), "UTF-8"));
				result = null;
			}
		} catch (Exception e) {
			logger.error("", e);
			throw new RuntimeException("HTTP网络执行异常:" + e.toString());
		} finally {
			releaseConnection(request);
		}
		return result;
	}

	private static void releaseConnection(HttpRequestBase request) {
		if (request != null) {
			request.releaseConnection();
		}
	}
}
