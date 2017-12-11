package com.epay.scanposp.common.utils.swift;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;



public class HttpClientUtil {

	//private static final Log log = LogFactory.getLog(SendMessageUtil.class);
	
	private static final String CHARSET = "GBK";
	private static final int TIMEOUT=5*1000*60;
	private static final int CONNECTION_TIMEOUT=5*1000*60;
	private static final int SO_TIMEOUT=5*1000*60;
	
	private HttpClientUtil(){}
	
	private static HttpClient httpClient ;
	
	private static synchronized HttpClient getHttpClient(){
		if(null == httpClient){
			HttpParams httpParams = new BasicHttpParams();
			httpParams.setParameter("http.method.retry-handler", new DefaultHttpRequestRetryHandler());
			httpParams.setBooleanParameter("http.connection.stalecheck", false);
			//参数设置
			HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(httpParams, CHARSET);
			HttpProtocolParams.setUseExpectContinue(httpParams, true);
			//超时设置
			ConnManagerParams.setTimeout(httpParams, TIMEOUT);
			//连接超时
			HttpConnectionParams.setConnectionTimeout(httpParams, CONNECTION_TIMEOUT);
			//请求超时
			HttpConnectionParams.setSoTimeout(httpParams, SO_TIMEOUT);
			
			//设置支持HTTP和HTTPS
			SchemeRegistry schemeReg = new SchemeRegistry();
			schemeReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
			schemeReg.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
			//使用线程安全的连接创建HttpClient
			ClientConnectionManager conManager = new ThreadSafeClientConnManager(httpParams, schemeReg);
			httpClient = new DefaultHttpClient(conManager,httpParams);
		}
		return httpClient;
	}

	public static String sendHttpMessage(String url,Map<String, String> map) throws ClientException{
		try {
			if(StringUtil.isEmpty(url) || null == map || map.isEmpty()){
				return null;
			}
			//创建POST请求
			HttpPost post = new HttpPost(url);
			post.setHeader("Content-type", (new StringBuilder()).append("application/x-www-form-urlencoded; charset=").append(CHARSET).toString());
	        post.setHeader("Accept",new StringBuffer("text/xml;charset=").append(CHARSET).toString());
	        post.setHeader("Cache-Control", "no-cache");
	        
	        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
	        //请求参数
			for(String key : map.keySet()){
				params.add(new BasicNameValuePair(key,map.get(key))); 
			}
	        post.setEntity(new UrlEncodedFormEntity(params, CHARSET));
	        
			//发送请求
			HttpClient client = getHttpClient();
			HttpResponse response = client.execute(post);
			if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK){
				throw new ClientException("请求失败！");
			}
			
			HttpEntity resEntity = response.getEntity();
			return null == resEntity ? "" : EntityUtils.toString(resEntity,CHARSET);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			throw new ClientException(e.getMessage());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new ClientException(e.getMessage());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			throw new ClientException(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			throw new ClientException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ClientException(e.getMessage());
		}
	}
	
	public static String sendHttpMessage(String url,Map<String, String> map, File file) throws ClientException{
		try {
			if(StringUtil.isEmpty(url) || null == map || map.isEmpty()){
				return null;
			}
			//创建POST请求
			HttpPost post = new HttpPost(url);
//			post.setHeader("Content-type", (new StringBuilder()).append("multipart/form-data;boundary=BOUNDARY; charset=").append(CHARSET).toString());
//	        post.setHeader("Accept",new StringBuffer("text/xml;charset=").append(CHARSET).toString());
//	        post.setHeader("Cache-Control", "no-cache");
	        
	        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();

	        MultipartEntity entity = new MultipartEntity();  
	        //请求参数
			for(String key : map.keySet()){
//				params.add(new BasicNameValuePair(key,map.get(key))); 
				entity.addPart(key, new StringBody(map.get(key), Charset.forName("UTF-8")));  
			}
//	        post.setEntity(new UrlEncodedFormEntity(params, CHARSET));
		      
//		    entity.addPart("param1",new StringBody("中国", Charset.forName("UTF-8")));  
		      
//		    entity.addPart("param2",new StringBody("value2", Charset.forName("UTF-8")));  
		      
		    entity.addPart("param3",new FileBody(file));  
		      
		    post.setEntity(entity);  
		    
			//发送请求
			HttpClient client = getHttpClient();
			HttpResponse response = client.execute(post);
			if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK){
				throw new ClientException("请求失败！");
			}
			
			HttpEntity resEntity = response.getEntity();
			return null == resEntity ? "" : EntityUtils.toString(resEntity,CHARSET);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			throw new ClientException(e.getMessage());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new ClientException(e.getMessage());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			throw new ClientException(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			throw new ClientException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ClientException(e.getMessage());
		}
	}
	
}
