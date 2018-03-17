package com.epay.scanposp.common.utils.xinfu;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

public class HttpPostRequest {

	public static JSONObject sendRequest(Map<String, String> paramMap, String reqUrl) throws Exception {
		HttpEntity reqEntity = null;
		List<NameValuePair> formParams = convertNameValuePairToList(paramMap);
		
		reqEntity = new UrlEncodedFormEntity(formParams, "utf-8");
		HttpPost httpPost = new HttpPost(reqUrl);
		httpPost.setEntity(reqEntity);
		
		String result = HttpUtils.exctueRequest(httpPost);
		if (result == null) {
			return null;
		}
		
		JSONObject retJson = JSONObject.fromObject(result);
		return retJson;
	}
	
	private static List<NameValuePair> convertNameValuePairToList(Map<String, String> requestParams) {
        if (requestParams == null || requestParams.isEmpty()) {
            return null;
        }
        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : requestParams.entrySet()) {
            formParams.add(new BasicNameValuePair(entry.getKey(),  entry.getValue()));
        }
        return formParams;
    }
	
}
