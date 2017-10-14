package com.epay.scanposp.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;


public class SendUrlUtil {
//	private Logger logger = Logger.getLogger(SendUrlUtil.class);
	public String sendPost(String path,String param){
//		logger.info("准备发送url"); 
		StringBuffer buffer = new StringBuffer();
		
		try {
			
		    URL url = new URL(path);
		    URLConnection rulConnection = url.openConnection();  
		    HttpURLConnection httpUrlConnection = (HttpURLConnection) rulConnection; 
		  
		  
		      // 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在   
		      // http正文内，因此需要设为true, 默认情况下是false;   
		    httpUrlConnection.setDoOutput(true);  
		      // 设置是否从httpUrlConnection读入，默认情况下是true;   
			httpUrlConnection.setDoInput(true);   
		      // Post 请求不能使用缓存   
		    httpUrlConnection.setUseCaches(false);   
		      // 设定请求的方法为"POST"，默认是GET   
		    httpUrlConnection.setRequestMethod("POST");   
		      //连接超时设置
		    httpUrlConnection.setConnectTimeout(180*1000);
              //读取超时设置
		    httpUrlConnection.setReadTimeout(180*1000);
		    
		    // 连接，从上述第2条中url.openConnection()至此的配置必须要在connect之前完成，
            httpUrlConnection.connect();
              // 此处getOutputStream会隐含的进行connect(即：如同调用上面的connect()方法，   
              // 所以在开发中不调用上述的connect()也可以)。   
          
          
            OutputStream output = httpUrlConnection.getOutputStream();
			
			output.write(param.toString().getBytes()); //把报文写进缓冲区，也可以采取以下方法：PrintWriter out = new PrintWriter(output);  out.print(sendXml);  
	        output.flush(); 
	        output.close(); 
	          
	          // 调用HttpURLConnection连接对象的getInputStream()函数,   
	          // 将内存缓冲区中封装好的完整的HTTP请求电文发送到服务端。   
	        InputStream inputStream = httpUrlConnection.getInputStream(); // <===注意，实际发送请求的代码段就在这里
	        
	        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));  
	        String strMessage = "";
	        
	        while((strMessage = reader.readLine()) != null) { 
	            buffer.append(strMessage);  
	        }
	        
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}   
		 
			return  buffer.toString() ;
		 
          
	}
	
	
}
