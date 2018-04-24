package com.epay.scanposp.common.utils.sand;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.epay.scanposp.common.utils.ms.HttpClient4Util;
import com.epay.scanposp.common.utils.sand.util.CertUtil;
import com.epay.scanposp.common.utils.sand.util.CryptoUtil;
import com.epay.scanposp.common.utils.sand.util.SDKUtil;
import com.epay.scanposp.response.SandOrderPayResponse;

public class SandpayClient {
	private static Logger logger = LoggerFactory.getLogger(SandpayClient.class);

	  private static int connectTimeout = 3000;
	  private static int readTimeout = 15000;

	  public static SandOrderPayResponse  execute(SandpayRequest req, String serverUrl) throws Exception {
	    return execute(req, serverUrl, connectTimeout, readTimeout);
	  }

	  public static SandOrderPayResponse execute(SandpayRequest request, String serverUrl, int connectTimeout, int readTimeout) throws Exception
	  {
	    Map reqMap = new HashMap();

	    String reqData = JSON.toJSONString(request);
	    
	    String reqSign = new String(Base64.encodeBase64(CryptoUtil.digitalSign(reqData.getBytes("UTF-8"), CertUtil.getPrivateKey(), "SHA1WithRSA")));
	    reqMap.put("charset", "UTF-8");
	    reqMap.put("data", reqData);
	    reqMap.put("signType", "01");
	    reqMap.put("sign", reqSign);
	    reqMap.put("extend", "");

	    List<NameValuePair> nvps = new LinkedList<NameValuePair>();
		nvps.add(new BasicNameValuePair("charset", "UTF-8"));
		nvps.add(new BasicNameValuePair("data", reqData));
		nvps.add(new BasicNameValuePair("signType", "01"));
		nvps.add(new BasicNameValuePair("sign", reqSign));
		nvps.add(new BasicNameValuePair("extend", ""));
		
	    logger.info("[sandpay][{}][req]==>{}", request.getTxnDesc(), reqMap);
	    //String result = HttpClient.doPost(serverUrl, reqMap, connectTimeout, readTimeout);
	    byte[] b = HttpClient4Util.getInstance().doPost(serverUrl, null, nvps);
		String result = new String(b, "UTF-8");
	    logger.debug("[sandpay][{}][resp]<=={}", request.getTxnDesc(), result);
	    result = URLDecoder.decode(result, "utf-8");
	    logger.info("[sandpay][{}][resp]<=={}", request.getTxnDesc(), result);

	    Map respMap = SDKUtil.convertResultStringToMap(result);

	    String respData = (String)respMap.get("data");
	    String respSign = (String)respMap.get("sign");

	    boolean valid = CryptoUtil.verifyDigitalSign(respData.getBytes("UTF-8"), Base64.decodeBase64(respSign), CertUtil.getPublicKey(), "SHA1WithRSA");
	    if (!(valid)) {
	      logger.error("verify sign fail.");
	      throw new RuntimeException("verify sign fail.");
	    }

	    logger.info("verify sign success");

	    return ((SandOrderPayResponse)JSON.parseObject(respData, request.getResponseClass()));
	  }

}
