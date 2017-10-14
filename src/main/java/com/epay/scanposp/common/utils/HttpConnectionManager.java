package com.epay.scanposp.common.utils;

import org.apache.http.HttpVersion;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

/**
 * httpclient连接工厂
 * @author ghq
 * @since 2015.09.30
 */

public class HttpConnectionManager { 

	public static PoolingClientConnectionManager clientConnectionManager = null;
	private static HttpConnectionManager poolManager = null;

    private final static int maxTotal = 1000;//最大连接数

    private final static int defaultMaxPerRoute = 250;
    private final static int REQUEST_TIMEOUT = 1*1000;  //设置请求超时10秒钟   
    private final static int SO_TIMEOUT = 1*1000;       //设置等待数据超时时间10秒钟 
    private static HttpParams params = new BasicHttpParams();   

    static{
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);   
        HttpProtocolParams.setUserAgent(params, "HttpComponents/1.1");   
        HttpProtocolParams.setUseExpectContinue(params, true);
        params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, REQUEST_TIMEOUT);    
        params.setParameter(CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT);  
        clientConnectionManager = new PoolingClientConnectionManager();
        clientConnectionManager.setMaxTotal(maxTotal);
        clientConnectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
    }

    private HttpConnectionManager(){
    }
    
    /**
     * 获取连接
     * @return
     */
    public  DefaultHttpClient getHttpClient(){
        return new DefaultHttpClient(clientConnectionManager,params);
    }
    
    /**
     * 获取单例
     * @return
     */
    public static HttpConnectionManager getInstance(){
    	if(poolManager == null){
            poolManager = new HttpConnectionManager();

        }
    	return poolManager;
    }
}