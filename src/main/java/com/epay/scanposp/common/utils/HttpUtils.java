package com.epay.scanposp.common.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
/**
 * HTTP 客户端请求
 * @author ghq
 * @since 2015.08.11
 */
public class HttpUtils {
	public static void HttpPost(String url, List<NameValuePair> params)
			throws Exception {		
		HttpClient client = HttpConnectionManager.getInstance().getHttpClient();
		HttpPost post = new HttpPost(url);
		HttpEntity formEntity = new UrlEncodedFormEntity(params);
		post.setEntity(formEntity);
		HttpResponse response = client.execute(post);
		int code = response.getStatusLine().getStatusCode();
		HttpEntity entity = response.getEntity();
		String last = "";
		if ((code >= 200) && (code < 300)) {
			String line = null;
			if (entity != null) {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						entity.getContent(), "UTF-8"));
				while ((line = br.readLine()) != null)
					last = last + line + " ";
			}
		} else {
			last = "fail[调用失败：" + code + "]";
		}
		System.out.println(last);
	}

}
