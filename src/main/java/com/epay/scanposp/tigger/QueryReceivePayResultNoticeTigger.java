package com.epay.scanposp.tigger;

import java.io.File;
import java.net.URLDecoder;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.epay.scanposp.common.constant.CJConfig;
import com.epay.scanposp.common.constant.ESKConfig;
import com.epay.scanposp.common.constant.HLBConfig;
import com.epay.scanposp.common.constant.MLConfig;
import com.epay.scanposp.common.constant.RFConfig;
import com.epay.scanposp.common.constant.SMConfig;
import com.epay.scanposp.common.constant.TLConfig;
import com.epay.scanposp.common.constant.TLKJConfig;
import com.epay.scanposp.common.constant.WWConfig;
import com.epay.scanposp.common.constant.YSConfig;
import com.epay.scanposp.common.constant.YZFConfig;
import com.epay.scanposp.common.utils.CommonUtil;
import com.epay.scanposp.common.utils.HttpUtil;
import com.epay.scanposp.common.utils.StringUtil;
import com.epay.scanposp.common.utils.cj.util.HttpURLConection;
import com.epay.scanposp.common.utils.cj.util.MD5Util;
import com.epay.scanposp.common.utils.constant.RouteCodeConstant;
import com.epay.scanposp.common.utils.epaySecurityUtil.EpaySignUtil;
import com.epay.scanposp.common.utils.esk.Key;
import com.epay.scanposp.common.utils.hlb.Disguiser;
import com.epay.scanposp.common.utils.hlb.RSA;
import com.epay.scanposp.common.utils.ms.CryptoUtil;
import com.epay.scanposp.common.utils.ms.HttpClient4Util;
import com.epay.scanposp.common.utils.ms.MSCommonUtil;
import com.epay.scanposp.common.utils.slf.MerchantClient;
import com.epay.scanposp.common.utils.slf.SecurityUtil;
import com.epay.scanposp.common.utils.slf.vo.ReceivePay;
import com.epay.scanposp.common.utils.slf.vo.ReceivePayQueryRequest;
import com.epay.scanposp.common.utils.slf.vo.ReceivePayQueryResponse;
import com.epay.scanposp.common.utils.sm.CryptNoRestrict;
import com.epay.scanposp.common.utils.tl.CertUtil;
import com.epay.scanposp.common.utils.tlkj.MapUtils;
import com.epay.scanposp.common.utils.ys.SwpHashUtil;
import com.epay.scanposp.common.utils.yzf.AESTool;
import com.epay.scanposp.common.utils.yzf.Base64Utils;
import com.epay.scanposp.common.utils.yzf.EncryptUtil;
import com.epay.scanposp.common.utils.yzf.RSATool;
import com.epay.scanposp.common.utils.zhzf.HttpUtils;
import com.epay.scanposp.common.utils.zhzf.MD5;
import com.epay.scanposp.entity.MemberMerchantKey;
import com.epay.scanposp.entity.MemberMerchantKeyExample;
import com.epay.scanposp.entity.RoutewayDraw;
import com.epay.scanposp.entity.RoutewayDrawExample;
import com.epay.scanposp.service.MemberBankService;
import com.epay.scanposp.service.MemberMerchantKeyService;
import com.epay.scanposp.service.RoutewayDrawService;
import com.kspay.cert.CertVerify;
import com.kspay.cert.LoadKeyFromPKCS12;

public class QueryReceivePayResultNoticeTigger {
	
	private static Logger logger = LoggerFactory.getLogger(QueryReceivePayResultNoticeTigger.class);
	
	@Autowired
	private MemberMerchantKeyService memberMerchantKeyService;
	
	@Autowired
	private MemberBankService memberBankService;
	
	@Autowired
	private RoutewayDrawService routewayDrawService;
	
	public void dealReceivePayResultNotice(){
		
		try{
			logger.info("代付结果定时查询。。。");
			RoutewayDrawExample routewayDrawExample = new RoutewayDrawExample();
			routewayDrawExample.createCriteria().andRespTypeEqualTo("R").andDelFlagEqualTo("0");
			List<RoutewayDraw> drawlist = routewayDrawService.selectByExample(routewayDrawExample);
			if(drawlist != null && drawlist.size()>0){
				for(RoutewayDraw draw:drawlist){
					logger.info("订单号:"+draw.getOrderCode());
					String routeCode = draw.getRouteCode();
					if(RouteCodeConstant.SLF_ROUTE_CODE.equals(routeCode)){
						ReceivePayQueryRequest queryRequest = new ReceivePayQueryRequest();
						queryRequest.setApplication("ReceivePayQuery");
						queryRequest.setVersion("1.0.1");
						queryRequest.setMerchantId(draw.getMerchantCode());
						queryRequest.setTimestamp(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
						queryRequest.setQueryTranId(draw.getOrderCode());
						
						MemberMerchantKeyExample memberMerchantKeyExample = new MemberMerchantKeyExample();
				        memberMerchantKeyExample.createCriteria().andRouteCodeEqualTo(RouteCodeConstant.SLF_ROUTE_CODE).andMerchantCodeEqualTo(draw.getMerchantCode()).andDelFlagEqualTo("0");
				        List<MemberMerchantKey> keyList = memberMerchantKeyService.selectByExample(memberMerchantKeyExample);
				        if(keyList == null || keyList.size()!=1){
				            continue;
				        }
				        MemberMerchantKey merchantKey = keyList.get(0);
						
				        MerchantClient client = new MerchantClient(draw.getMerchantCode());
						String merchantCertPath = MerchantClient.configPath + merchantKey.getPrivateKey();
						SecurityUtil.merchantCertPath = merchantCertPath;
						SecurityUtil.init(merchantCertPath,merchantKey.getPrivateKeyPassword());
						ReceivePayQueryResponse queryResponse = client.receivePayQuery(queryRequest); 
						
						if("000".equals(queryResponse.getRespCode())){
							String orderCode = queryResponse.getTranId();
							if(orderCode != null && !"".equals(orderCode) && orderCode.equals(draw.getOrderCode())){
								
								List<ReceivePay> list = queryResponse.getQueryList();
								if(list!=null && list.size()>0){
									String code = list.get(0).getRespCode();
									if("000".equals(code)){
										draw.setRespType("S");
									//	draw.setDrawamount(draw.getMoney());
									}else{
										draw.setRespType("E");
									}
									draw.setRespCode(code);
									draw.setRespMsg(list.get(0).getRespDesc());
									draw.setRespDate(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
									routewayDrawService.updateByPrimaryKey(draw);
								}
								
							}
						}
					}else if(RouteCodeConstant.RF_ROUTE_CODE.equals(routeCode)){
						
						MemberMerchantKeyExample memberMerchantKeyExample = new MemberMerchantKeyExample();
				        memberMerchantKeyExample.createCriteria().andRouteCodeEqualTo(routeCode).andMerchantCodeEqualTo(draw.getMerchantCode()).andDelFlagEqualTo("0");
				        List<MemberMerchantKey> keyList = memberMerchantKeyService.selectByExample(memberMerchantKeyExample);
				        if(keyList == null || keyList.size()!=1){
				            continue;
				        }
				        MemberMerchantKey merchantKey = keyList.get(0);
				        JSONObject reqData = new JSONObject();
						reqData.put("AppKey", draw.getMerchantCode());
						reqData.put("OrderNum", draw.getOrderCode());
						
						String srcStr = StringUtil.orderedKey(reqData);
						String privateKey = merchantKey.getPrivateKey();
						String signData = EpaySignUtil.signSha1(privateKey, srcStr);
						reqData.put("SignStr", signData);
						
						logger.info("瑞付代付订单查询请求报文[{}]", new Object[] { JSON.toJSONString(reqData) });
						
						String url = RFConfig.msServerUrl + "/payout";
						
						String resultMsg = HttpUtil.sendPostRequest(url, reqData.toString());
						logger.info("瑞付代付订单查询返回报文[{}]", new Object[] { resultMsg });
						
						JSONObject resultObj = JSONObject.fromObject(resultMsg);
				        String result_code = resultObj.getString("ReturnCode");
				        if("0000".equals(result_code)){
				        	String status = resultObj.getString("Stutas");
				        	if("2".equals(status)){//成功
				        		draw.setRespType("S");
				        	}else if("1".equals(status)){//处理中
				        		draw.setRespType("R");
				        	}else{
								draw.setRespType("E");
							}
				        	draw.setRespCode(status);
							draw.setRespMsg(resultObj.getString("Res"));
							draw.setRespDate(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
							draw.setUpdateDate(new Date());
							routewayDrawService.updateByPrimaryKey(draw);
				        }
				        
					}else if(RouteCodeConstant.YZF_ROUTE_CODE.equals(routeCode)){
						MemberMerchantKeyExample memberMerchantKeyExample = new MemberMerchantKeyExample();
				        memberMerchantKeyExample.createCriteria().andRouteCodeEqualTo(routeCode).andMerchantCodeEqualTo(draw.getMerchantCode()).andDelFlagEqualTo("0");
				        List<MemberMerchantKey> keyList = memberMerchantKeyService.selectByExample(memberMerchantKeyExample);
				        if(keyList == null || keyList.size()!=1){
				        	continue;
				        }
				        MemberMerchantKey merchantKey = keyList.get(0);
				        
				        String serverUrl = YZFConfig.msServerUrl;
						String charset = "UTF-8";
						String version = "2.0";	
						String action = "SdkSettleQuery";
						String merNo = draw.getMerchantCode();//商户号	
						String orgNo = merchantKey.getAppId();//机构
						String privateKeyStr = merchantKey.getPrivateKey();//RSA私钥
						String md5Key = merchantKey.getPrivateKeyPassword();
						String aesKey = MSCommonUtil.generateLenString(16);
						
						byte[] keyBytes = Base64Utils.decode(privateKeyStr);  
					    PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);  
					    KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
					    PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);  
						byte[] RSAKeyBy=RSATool.encrypt(aesKey.getBytes("UTF-8"),privateK);
						String encryptkey= EncryptUtil.encodeBase64(RSAKeyBy);
						
						JSONObject contextjson = new JSONObject();
				     	contextjson.put("linkId",draw.getOrderCode());
				     	
				     	String aesData=AESTool.encrypt(contextjson.toString(),aesKey);
						
						logger.info("易支付代付订单查询未加密参数[{}]",contextjson.toString() );
						
						
				     	String data	= aesData;
				     	
				     	String signData = version+orgNo+merNo+action+data+md5Key;
				    	String sign	= EncryptUtil.MD5(signData, 1);
				    	
				     	List<NameValuePair> nvps = new LinkedList<NameValuePair>();
						nvps.add(new BasicNameValuePair("version", version));
						nvps.add(new BasicNameValuePair("merNo", merNo));
						nvps.add(new BasicNameValuePair("orgNo", orgNo));
						nvps.add(new BasicNameValuePair("action", action));
						nvps.add(new BasicNameValuePair("data", aesData));
						nvps.add(new BasicNameValuePair("encryptkey", encryptkey));
						nvps.add(new BasicNameValuePair("sign", sign));
						byte[] b = HttpClient4Util.getInstance().doPost(serverUrl, null, nvps);
						
						String respStr = new String(b, charset);
						logger.info("易支付代付订单查询返回报文[{}]", new Object[] { respStr });
						
						JSONObject jsonObj = JSONObject.fromObject(respStr);
						byte[] encryptkey_RES=EncryptUtil.decodeBase64(jsonObj.get("encryptkey").toString());
						
						byte[] key_RES_B=RSATool.decrypt(encryptkey_RES,privateK);
						String aes_res_key=new String (key_RES_B);
						//logger.info("代付联行号查询返回报文解密AES key[{}]", new Object[] { aes_res_key });
						String data_RES=jsonObj.get("data").toString();
						String resData=AESTool.decrypt(data_RES, aes_res_key);
						logger.info("易支付代付订单查询返回报文解密结果[{}]", new Object[] { resData });
						
						JSONObject resObj = JSONObject.fromObject(resData);
						String code = resObj.getString("code");
						
				     	
						if("0000".equals(code)){
							if("0000".equals(resObj.getString("settleStatus"))){
								draw.setRespType("S");
							}else if("0001".equals(resObj.getString("settleStatus"))){
								draw.setRespType("E");
							}
							draw.setRespCode(resObj.getString("settleStatus"));
							draw.setRespMsg(resObj.getString("settleMemo"));
							draw.setRespDate(resObj.getString("settleTime"));
							draw.setUpdateDate(new Date());
							routewayDrawService.updateByPrimaryKey(draw);
						}
					}else if(RouteCodeConstant.ZHZF_ROUTE_CODE.equals(routeCode)){
						MemberMerchantKeyExample memberMerchantKeyExample = new MemberMerchantKeyExample();
				        memberMerchantKeyExample.createCriteria().andRouteCodeEqualTo(routeCode).andMerchantCodeEqualTo(draw.getMerchantCode()).andDelFlagEqualTo("0");
				        List<MemberMerchantKey> keyList = memberMerchantKeyService.selectByExample(memberMerchantKeyExample);
				        if(keyList == null || keyList.size()!=1){
				        	continue;
				        }
				        MemberMerchantKey merchantKey = keyList.get(0);
				        
				        String serverUrl = "https://rpi.szyinfubao.com/agentpay/query";
				        TreeMap<String, Object> map = new TreeMap<>();
						TreeMap<String, Object> map2 = new TreeMap<>();
						map.put("mch_id", draw.getMerchantCode());
						map.put("order_no", draw.getChannelNo());
						
						String biz_content = JSONObject.fromObject(map).toString();
						String strPre = "biz_content=" + biz_content + "&key=" + merchantKey.getPrivateKey();
						String sign = MD5.MD5Encode(strPre).toUpperCase();
						map2.put("biz_content", biz_content);
						map2.put("signature", sign);
						map2.put("sign_type", "MD5");
						logger.info("综合支付代付查询请求报文[{}]", map2.toString());
						String respStr = HttpUtils.httpSend(serverUrl,map2);
						logger.info("综合支付代付查询返回报文[{}]", new Object[] { respStr });
				        
						JSONObject resObj = JSONObject.fromObject(respStr);
						String code = resObj.getString("ret_code");
						
				     	if("0".equals(code)){
							String result_content = resObj.getString("biz_content");
							
							String str = "biz_content="+result_content+"&key=" + merchantKey.getPrivateKey();
				        	String strSign = MD5.MD5Encode(str).toUpperCase();
				        	if(strSign.equals(resObj.getString("signature"))){
				        		JSONArray arr = JSONObject.fromObject(result_content).getJSONArray("lists");
					        	JSONObject obj = arr.getJSONObject(0);
					        	String order_status = obj.getString("order_status");
								if("2".equals(order_status)){
									draw.setRespType("S");
									draw.setRespCode("000");
								}else if("3".equals(order_status)){
									draw.setRespType("E");
									draw.setRespCode(order_status);
								}
								
								if(obj.containsKey("err_msg")){
									draw.setRespMsg(obj.getString("err_msg"));
								}
								if(obj.containsKey("pay_time")){
									draw.setRespDate(new SimpleDateFormat("yyyyMMddHHmmss").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(obj.getString("pay_time"))));
								}else{
									draw.setRespDate(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
								}
								draw.setUpdateDate(new Date());
								routewayDrawService.updateByPrimaryKey(draw);
				        	}
						}
				    }else if(RouteCodeConstant.HLB_ROUTE_CODE.equals(routeCode)){
						MemberMerchantKeyExample memberMerchantKeyExample = new MemberMerchantKeyExample();
				        memberMerchantKeyExample.createCriteria().andRouteCodeEqualTo(routeCode).andMerchantCodeEqualTo(draw.getMerchantCode()).andDelFlagEqualTo("0");
				        List<MemberMerchantKey> keyList = memberMerchantKeyService.selectByExample(memberMerchantKeyExample);
				        if(keyList == null || keyList.size()!=1){
				        	continue;
				        }
				        MemberMerchantKey merchantKey = keyList.get(0);
				        
				        String serverUrl = HLBConfig.agentPayUrl;
				        String charset = "utf-8";
						
						Map<String,String> sPara = new HashMap<String,String>();
						sPara.put("P1_bizType","TransferQuery");
						sPara.put("P2_orderId",draw.getOrderCode());
						sPara.put("P3_customerNumber",draw.getMerchantCode());
						
						String split = "&";
						StringBuffer sb = new StringBuffer();
						sb.append(split).append("TransferQuery").append(split).append(draw.getOrderCode()).append(split).append(draw.getMerchantCode());
						
						String sign = RSA.sign(sb.toString(), RSA.getPrivateKey(HLBConfig.rsaPrivateKey));
						sPara.put("sign",sign);
						logger.info("合利宝代付订单查询请求数据[{}]", new Object[] { JSONObject.fromObject(sPara).toString() });
						
						List<NameValuePair> nvps = new LinkedList<NameValuePair>();
						List<String> keys = new ArrayList<String>(sPara.keySet());
						for (int i = 0; i < keys.size(); i++) {
							 String name=(String) keys.get(i);
							 String value=(String) sPara.get(name);
							if(value!=null && !"".equals(value)){
								nvps.add(new BasicNameValuePair(name, value));
							}
						}
						
						byte[] b = HttpClient4Util.getInstance().doPost(serverUrl, null, nvps);
						String respStr = new String(b, charset);
						logger.info("合利宝代付订单查询返回报文[{}]", new Object[] { respStr });
				        
						JSONObject resObj = JSONObject.fromObject(respStr);
						String code = resObj.getString("rt2_retCode");
				        
				     	if("0000".equals(code)){
							sb = new StringBuffer();
							sb.append(split).append(resObj.getString("rt1_bizType"));
							sb.append(split).append(resObj.getString("rt2_retCode"));
							sb.append(split).append(resObj.getString("rt4_customerNumber"));
							sb.append(split).append(resObj.getString("rt5_orderId"));
							sb.append(split).append(resObj.getString("rt6_serialNumber"));
							sb.append(split).append(resObj.getString("rt7_orderStatus"));
							//sb.append(split).append(resObj.getString("rt8_reason"));
							sb.append(split).append(merchantKey.getPrivateKeyPassword());
							sign = Disguiser.disguiseMD5(sb.toString());
				     		boolean checkSign = sign.equals(resObj.getString("sign"));
							if(checkSign){
				        		String order_status = resObj.getString("rt7_orderStatus");
								if("SUCCESS".equals(order_status)){
									draw.setRespType("S");
									draw.setRespCode("000");
								}else if("FAIL".equals(order_status)||"REFUND".equals(order_status)){
									draw.setRespType("E");
									draw.setRespCode(order_status);
								}
								
								if(resObj.containsKey("rt8_reason")){
									draw.setRespMsg(resObj.getString("rt8_reason"));
								}
								draw.setRespDate(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
								draw.setUpdateDate(new Date());
								routewayDrawService.updateByPrimaryKey(draw);
				        	}
						}
				    }else if(RouteCodeConstant.CJ_ROUTE_CODE.equals(routeCode)||RouteCodeConstant.CJWG_ROUTE_CODE.equals(routeCode)){
						MemberMerchantKeyExample memberMerchantKeyExample = new MemberMerchantKeyExample();
				        memberMerchantKeyExample.createCriteria().andRouteCodeEqualTo(routeCode).andMerchantCodeEqualTo(draw.getMerchantCode()).andDelFlagEqualTo("0");
				        List<MemberMerchantKey> keyList = memberMerchantKeyService.selectByExample(memberMerchantKeyExample);
				        if(keyList == null || keyList.size()!=1){
				        	continue;
				        }
				        MemberMerchantKey merchantKey = keyList.get(0);
				        
				        String serverUrl = CJConfig.msServerUrl+"/totalPayController/merchant/virement/mer_query.action";
				        
						Map<String,String> sPara = new HashMap<String,String>();
						sPara.put("v_version","1.0.0.0");
						sPara.put("v_identity",draw.getOrderCode());
						sPara.put("v_mid",draw.getMerchantCode());
						sPara.put("v_batch_no", draw.getBatchNo());
						sPara.put("v_type","1");
						
						String src = StringUtil.orderedKey(sPara)+merchantKey.getPrivateKey();
						//logger.info("Sign Before MD5: {}", src);
						String sign = MD5Util.MD5Encode(src).toUpperCase();
						//logger.info("Sign Result: {}", sign);
						
						sPara.put("v_sign", sign);
						StringBuffer sb=new StringBuffer();
						for (Map.Entry<String, String> entry : sPara.entrySet()) {
							if (entry.getValue() != null && !StringUtil.isEmpty(String.valueOf(entry.getValue()))) {
								sb.append(entry.getKey()+"="+entry.getValue()+"&");
							}else{
								sb.append(entry.getKey()+"=&");
							}
						}
						String postStr = sb.substring(0, sb.toString().length()-1);
						
						logger.info("畅捷代付订单查询请求参数[{}]",postStr );
						String respStr = HttpURLConection.httpURLConnectionPOST(serverUrl, postStr);
						logger.info("畅捷代付订单查询返回报文[{}]", new Object[] { respStr });
				        
						JSONObject resObj = JSONObject.fromObject(respStr);
						String code = resObj.getString("v_code");
						if("00".equals(code)){
				     		String v_sign = resObj.getString("v_sign");
				     		resObj.remove("v_sign");
				     		String s = StringUtil.orderedKey(resObj)+merchantKey.getPrivateKey();
				     		sign = MD5Util.MD5Encode(s).toUpperCase();
							boolean checkSign = sign.equals(v_sign);
							if(checkSign){
				        		String order_status = resObj.getString("v_status");
								if("0000".equals(order_status)){
									draw.setRespType("S");
									draw.setRespCode("000");
								}else if("200".equals(order_status)){
									draw.setRespType("R");
								}else{
									draw.setRespType("E");
									draw.setRespCode(order_status);
								}
								
								if(resObj.containsKey("v_status_msg")){
									draw.setRespMsg(resObj.getString("v_status_msg"));
								}
								draw.setRespDate(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
								draw.setUpdateDate(new Date());
								routewayDrawService.updateByPrimaryKey(draw);
				        	}
						}
				    }else if(RouteCodeConstant.YS_ROUTE_CODE.equals(routeCode)){
				    	String serverUrl = YSConfig.msServerUrl+"/swp/dh/js_bill_query.do";
						String privateKey = YSConfig.privateKey;
				        
				        Map<String,String> param = new HashMap<String, String>();
						param.put("sp_id", YSConfig.orgNo);
						param.put("mch_id", YSConfig.defaultMerNoApay);
						param.put("out_js_trade_no", draw.getOrderCode());
						
						Date t = new Date();
						Calendar cal = Calendar.getInstance();
						cal.setTime(t);
						long sys_timestamp = cal.getTimeInMillis();
						param.put("timestamp", String.valueOf(sys_timestamp));
						
						String srcStr1 = StringUtil.orderedKey(param)+"&key="+privateKey;
						String sign = SwpHashUtil.getSign(srcStr1, privateKey, "SHA256");
						String paramStr = StringUtil.orderedKey(param) + "&sign="+sign;
						logger.info("易生代付订单查询参数[{}]",paramStr );

						HttpResponse httpResponse =com.epay.scanposp.common.utils.ys.HttpUtils.doPost(serverUrl, "", paramStr, "application/x-www-form-urlencoded; charset=UTF-8");
						String respStr = EntityUtils.toString(httpResponse.getEntity());
						logger.info("易生代付订单查询返回报文[{}]", new Object[] { respStr });
						
						JSONObject resObj = JSONObject.fromObject(respStr);
						String code = resObj.getString("status");
						if("SUCCESS".equals(code)){
							String resSign = resObj.getString("sign");
							resObj.remove("sign");
							srcStr1 = StringUtil.orderedKey(resObj)+"&key="+privateKey;
							if(resSign.equals(SwpHashUtil.getSign(srcStr1, privateKey, "SHA256"))){
								String daifu_state = resObj.getString("daifu_state");
								if("SUCCESS".equals(daifu_state)){
									draw.setRespType("S");
									draw.setRespCode("000");
								}else if("PROCESSING".equals(daifu_state)){
									draw.setRespType("R");
								}else{
									draw.setRespType("E");
									draw.setRespCode(daifu_state);
								}
								if(resObj.containsKey("daifu_state_desc")){
									draw.setRespMsg(resObj.getString("daifu_state_desc"));
								}
								if(resObj.containsKey("daifu_sys_trade_no")){
									draw.setChannelNo(resObj.getString("daifu_sys_trade_no"));
								}
								draw.setRespDate(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
								draw.setUpdateDate(new Date());
								routewayDrawService.updateByPrimaryKey(draw);
							}else{
								logger.info("查询接口出参验签失败");
							}
						}else{
							logger.info(resObj.getString("message"));
						}
					}else if(RouteCodeConstant.WW_ROUTE_CODE.equals(routeCode)){
						String serverUrl = WWConfig.msServerUrl+"/bankPay/queryAgentPayResult";
						String privateKey = WWConfig.privateKey;
				        
				        Map<String,String> param = new HashMap<String, String>();
						param.put("memberCode", draw.getMerchantCode());
						param.put("orderNum", draw.getOrderCode());
						
						String srcStr1 = StringUtil.orderedKey(param);
						String sign = EpaySignUtil.sign(privateKey, srcStr1);
						param.put("signStr", sign);
						logger.info("微微代付订单查询参数[{}]",JSONObject.fromObject(param).toString() );

						List<NameValuePair> nvps = new LinkedList<NameValuePair>();
						List<String> keys = new ArrayList<String>(param.keySet());
						for (int i = 0; i < keys.size(); i++) {
							String name=(String) keys.get(i);
							String value=(String) param.get(name);
							if(value!=null && !"".equals(value)){
								nvps.add(new BasicNameValuePair(name, value));
							}
						}
						
						byte[] b = HttpClient4Util.getInstance().doPost(serverUrl, null, nvps);
						String respStr = new String(b, "utf-8");
						JSONObject resObj = JSONObject.fromObject(respStr);
						logger.info("微微代付订单查询返回报文[{}]", new Object[] { respStr });
						
						String code = resObj.getString("returnCode");
						String resSign = resObj.getString("signStr");
						resObj.remove("signStr");
						srcStr1 = StringUtil.orderedKey(resObj);
						if(EpaySignUtil.checksign(WWConfig.platPublicKey, srcStr1, resSign)){
							if("0000".equals(code)){
								String daifu_state = resObj.getString("oriRespType");
								if("S".equals(daifu_state)){
									draw.setRespType("S");
									draw.setRespCode("000");
								}else if("R".equals(daifu_state)){
									draw.setRespType("R");
								}else{
									draw.setRespType("E");
									draw.setRespCode(daifu_state);
								}
								if(resObj.containsKey("oriRespMsg")){
									draw.setRespMsg(resObj.getString("oriRespMsg"));
								}
								if(resObj.containsKey("orderCode")){
									draw.setChannelNo(resObj.getString("orderCode"));
								}
								draw.setRespDate(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
								draw.setUpdateDate(new Date());
								routewayDrawService.updateByPrimaryKey(draw);
							}else{
								logger.info(resObj.getString("returnMsg"));
							}
						}else{
							logger.info("查询接口出参验签失败");
						}
					}else if(RouteCodeConstant.ESKHLB_ROUTE_CODE.equals(routeCode)||RouteCodeConstant.ESKXF_ROUTE_CODE.equals(routeCode)||RouteCodeConstant.ESK_ROUTE_CODE.equals(routeCode)||RouteCodeConstant.ESKKJ_ROUTE_CODE.equals(routeCode)||RouteCodeConstant.ESKWG_ROUTE_CODE.equals(routeCode)||RouteCodeConstant.ESKWGD0_ROUTE_CODE.equals(routeCode)){
						
						String serverUrl = ESKConfig.agentServerUrl;
						String tranCode = "101";
						String charset = "utf-8";
						
						JSONObject reqData = new JSONObject();
						reqData.put("oriOrderNumber", draw.getOrderCode());
						reqData.put("tranCode", tranCode);
						reqData.put("orderNumber", "Q"+CommonUtil.getOrderCode());
						
						logger.info("易收款代付订单查询请求参数[{}]",  reqData );
						
						String plainXML = reqData.toString();
						byte[] plainBytes = plainXML.getBytes(charset);
						String keyStr = MSCommonUtil.generateLenString(16);
						
						byte[] keyBytes = keyStr.getBytes(charset);
						String encryptData = new String(Base64.encodeBase64((CryptoUtil.AESEncrypt(plainBytes, keyBytes, "AES", "AES/ECB/PKCS5Padding", null))), charset);
						String signData = new String(Base64.encodeBase64(Key.rsaSign(plainBytes, ESKConfig.privateKey)), charset);
						String encrtptKey = new String(Base64.encodeBase64(Key.jdkRSA(keyBytes, ESKConfig.yhPublicKey)), charset);
						List<NameValuePair> nvps = new LinkedList<NameValuePair>();
						nvps.add(new BasicNameValuePair("Context", encryptData));
						nvps.add(new BasicNameValuePair("encrtpKey", encrtptKey));
						
						nvps.add(new BasicNameValuePair("signData", signData));
						nvps.add(new BasicNameValuePair("agentId", ESKConfig.agentId));
						byte[] b = HttpClient4Util.getInstance().doPost(serverUrl, null, nvps);
						String respStr = new String(b, charset);
					//	logger.info("返回报文[{}]", new Object[] { respStr });
						
						JSONObject jsonObject = JSONObject.fromObject(respStr);
						String resEncryptData = jsonObject.getString("Context");
						String resEncryptKey = jsonObject.getString("encrtpKey");
						byte[] decodeBase64KeyBytes = Base64.decodeBase64(resEncryptKey.getBytes(charset));
						byte[] merchantAESKeyBytes = Key.jdkRSA_(decodeBase64KeyBytes, ESKConfig.privateKey);
						// 使用base64解码商户请求报文
						byte[] decodeBase64DataBytes = Base64.decodeBase64(resEncryptData.getBytes(charset));
						// 用解密得到的merchantAESKey解密encryptData
						byte[] merchantXmlDataBytes = CryptoUtil.AESDecrypt(decodeBase64DataBytes, merchantAESKeyBytes, "AES", "AES/ECB/PKCS5Padding", null);
						String resXml = new String(merchantXmlDataBytes, charset);
						JSONObject respJSONObject = JSONObject.fromObject(resXml);
						logger.info("易收款代付订单查询返回报文[{}]",  respJSONObject );
						
						String respCode = respJSONObject.getString("respCode");
						String respType = respJSONObject.getString("respType");
						String respMsg = respJSONObject.getString("respMsg");
						if("000000".equals(respCode)&&"S".equals(respType)){
							draw.setRespType("S");
							draw.setRespCode("000");
						}else if("R".equals(respType)){
							draw.setRespType("R");
						}else{
							draw.setRespType("E");
							draw.setRespCode(respCode);
						}
						draw.setRespMsg(respMsg);
			            draw.setRespDate(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
						draw.setUpdateDate(new Date());
						routewayDrawService.updateByPrimaryKey(draw);
					}else if(RouteCodeConstant.TL_ROUTE_CODE.equals(routeCode)||RouteCodeConstant.TLH5_ROUTE_CODE.equals(routeCode)){
						MemberMerchantKeyExample memberMerchantKeyExample = new MemberMerchantKeyExample();
				        memberMerchantKeyExample.createCriteria().andRouteCodeEqualTo(routeCode).andMerchantCodeEqualTo(draw.getMerchantCode()).andDelFlagEqualTo("0");
				        List<MemberMerchantKey> keyList = memberMerchantKeyService.selectByExample(memberMerchantKeyExample);
				        if(keyList == null || keyList.size()!=1){
				        	continue;
				        }
				        MemberMerchantKey merchantKey = keyList.get(0);
						String serverUrl = TLConfig.msServerUrl;
						JSONObject reqData = new JSONObject();
						reqData.put("ACTION_NAME", "QUERY_SETTLE");
						JSONObject reqBody = new JSONObject();
						reqBody.put("COM_ID", TLConfig.agentId);
						reqBody.put("ORDER_ID", draw.getChannelNo());
						reqBody.put("NONCE_STR", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
						
						String srcStr = StringUtil.orderedKey(reqBody);
						
						CertUtil util = new CertUtil();
						String keyStorePath = util.getConfigPath()+TLConfig.keyStorePath;
						String sign = CertUtil.sign(srcStr, keyStorePath, TLConfig.keyStorePassword, TLConfig.alias, TLConfig.aliasPassword);
						reqBody.put("SIGN", sign);
						
						reqData.put("ACTION_INFO", reqBody.toString());
						logger.info("通联代付查询请求数据[{}]", new Object[] { reqData.toString() });
						String respStr = HttpUtil.sendPostRequest(serverUrl, reqData.toString());
						logger.info("通联代付查询返回报文[{}]", new Object[] { respStr });
						JSONObject resultObj = JSONObject.fromObject(respStr);
						
						String result_code = "";
						if("000000".equals(resultObj.getString("ACTION_RETURN_CODE"))){
							JSONObject respJSONObject =  JSONObject.fromObject(resultObj.get("ACTION_INFO"));
							String signed = respJSONObject.getString("SIGN");
							respJSONObject.remove("SIGN");
							srcStr = StringUtil.orderedKeyObj(respJSONObject)+"&KEY="+merchantKey.getPrivateKey();
							sign = MD5Util.MD5Encode(srcStr).toUpperCase();
							
							if(sign.equals(signed)){
								result_code = respJSONObject.getString("STATUS");//0、待出款，2、出款失败；3、出款中；6、出款成功；
								if("6".equals(result_code)){
									draw.setRespType("S");
									draw.setRespCode("000");
								}else if("0".equals(result_code)||"3".equals(result_code)){
									draw.setRespType("R");
								}else{
									draw.setRespType("E");
									draw.setRespCode(result_code);
								}
								if(respJSONObject.containsKey("MSG")){
									draw.setRespMsg(respJSONObject.getString("MSG"));
								}
								draw.setRespDate(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
								draw.setUpdateDate(new Date());
								routewayDrawService.updateByPrimaryKey(draw);
							}else{
								logger.info("查询接口出参验签失败");
							}
						}else{
							logger.info(resultObj.getString("MESSAGE"));
						}
					}else if(RouteCodeConstant.TLWD_ROUTE_CODE.equals(routeCode)){
						MemberMerchantKeyExample memberMerchantKeyExample = new MemberMerchantKeyExample();
				        memberMerchantKeyExample.createCriteria().andRouteCodeEqualTo(routeCode).andMerchantCodeEqualTo(draw.getMerchantCode()).andDelFlagEqualTo("0");
				        List<MemberMerchantKey> keyList = memberMerchantKeyService.selectByExample(memberMerchantKeyExample);
				        if(keyList == null || keyList.size()!=1){
				        	continue;
				        }
				        MemberMerchantKey merchantKey = keyList.get(0);
				        
				        
				        String serverUrl = TLConfig.agentPayUrl;
				        JSONObject transData = new JSONObject(); 
			            transData.put("orderId", draw.getOrderCode()); // 订单号  
			            transData.put("transDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())); // 交易日期
			            logger.info("新通联代付查询未加密数据[{}]", new Object[] { transData.toString() });
			            
			            CertUtil util = new CertUtil();
						PrivateKey privateKey = LoadKeyFromPKCS12.initPrivateKey(util.getConfigPath()+TLConfig.pfxFileName, TLConfig.pfxPassword);
						String  transBody=LoadKeyFromPKCS12.PrivateSign(transData.toString(),privateKey);
				        
				        JSONObject reqData = new JSONObject();
						reqData.put("versionId", "001");
						reqData.put("businessType", "460000");
						reqData.put("merId", draw.getMerchantCode());
						reqData.put("transBody", transBody);
						
						String srcStr = StringUtil.orderedKey(reqData)+"&key="+merchantKey.getPrivateKey();
						String sign = MD5Util.MD5Encode(srcStr).toUpperCase();
						reqData.put("signData", sign); 
						reqData.put("signType", "MD5"); 
						
						logger.info("新通联代付查询请求数据[{}]", new Object[] { reqData.toString() });
						String respStr = HttpUtil.sendPostRequest(serverUrl, reqData.toString(),"GBK");
						logger.info("新通联代付查询返回报文[{}]", new Object[] { respStr });
				        
				        JSONObject resultObj = JSONObject.fromObject(respStr);
				        
				        String result_message = "";
						String result_code = "";
						if("00".equals(resultObj.getString("status"))&&resultObj.containsKey("resBody")){
							String data = resultObj.getString("resBody");
							byte[]signByte=LoadKeyFromPKCS12.encryptBASE64(data);
							PublicKey publicKey = CertVerify.initPublicKey(util.getConfigPath()+TLConfig.cerFileName);
							byte[] str1=CertVerify.publicKeyDecrypt(signByte,publicKey);
							JSONObject respJSONObject =  JSONObject.fromObject(new  String(str1));
							logger.info("新通联代付查询返回报文解密[{}]", new Object[] { respJSONObject.toString() });
							result_code = respJSONObject.getString("refCode");//1:成功 2:失败 3:未知，继续轮询  4:交易不存在
							if("1".equals(result_code)){
								draw.setRespType("S");
								draw.setRespCode("000");
							}else if("3".equals(result_code)){
								draw.setRespType("R");
							}else{
								draw.setRespType("E");
								draw.setRespCode(result_code);
							}
							if(respJSONObject.containsKey("refMsg")){
								draw.setRespMsg(URLDecoder.decode(respJSONObject.getString("refMsg"),"GBK"));
							}
							draw.setRespDate(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
							draw.setUpdateDate(new Date());
							routewayDrawService.updateByPrimaryKey(draw);
						}else{
							result_message = URLDecoder.decode(resultObj.getString("refMsg"), "GBK");
							result_code = resultObj.getString("refCode");
							logger.info(result_message);
						}
				    }else if(RouteCodeConstant.ML_ROUTE_CODE.equals(routeCode)){
						MemberMerchantKeyExample memberMerchantKeyExample = new MemberMerchantKeyExample();
				        memberMerchantKeyExample.createCriteria().andRouteCodeEqualTo(routeCode).andMerchantCodeEqualTo(draw.getMerchantCode()).andDelFlagEqualTo("0");
				        List<MemberMerchantKey> keyList = memberMerchantKeyService.selectByExample(memberMerchantKeyExample);
				        if(keyList == null || keyList.size()!=1){
				        	continue;
				        }
				        MemberMerchantKey merchantKey = keyList.get(0);
				        
				        String serverUrl = MLConfig.serverUrl+"/OrderQuery";
				        
				        String orderCode = "X"+CommonUtil.getOrderCode();
				        String srcStr1 = orderCode+draw.getOrderCode()+draw.getMerchantCode();
				     //	System.out.println("加密源串："+srcStr1);
						String sign1 = com.epay.scanposp.common.utils.ml.EncryptUtil.MD5(srcStr1, 1).toUpperCase();
					///	System.out.println("第一次加密结果："+sign1);
						String sign = com.epay.scanposp.common.utils.ml.EncryptUtil.MD5(sign1+merchantKey.getPrivateKey(), 0).toUpperCase();
					//	System.out.println("第二次加密结果："+sign);
						
						Map<String,String> param = new HashMap<String, String>();
						param.put("ORDER_ID", orderCode);
						param.put("USER_TYPE", "02");
						param.put("USER_ID", draw.getMerchantCode());
						param.put("SIGN_TYPE", "03");
						param.put("SIGN", sign);
						param.put("ORG_ORDER_ID", draw.getOrderCode());
						logger.info("米联代付订单查询参数[{}]",JSONObject.fromObject(param).toString() );
						
						List<NameValuePair> nvps = new LinkedList<NameValuePair>();
						List<String> keys = new ArrayList<String>(param.keySet());
						for (int i = 0; i < keys.size(); i++) {
							String name=(String) keys.get(i);
							String value=(String) param.get(name);
							if(value!=null && !"".equals(value)){
								nvps.add(new BasicNameValuePair(name, value));
							}
						}
			            
			            byte[] b = HttpClient4Util.getInstance().doPost(serverUrl, null, nvps);
						String respStr = new String(b, "UTF-8");
						logger.info("米联代付订单查询返回报文[{}]", new Object[] { respStr });
						
						Document reqDoc = DocumentHelper.parseText(respStr);
						Element rootEl = reqDoc.getRootElement();
						String code = rootEl.elementText("RESP_CODE");
						String result_message = rootEl.elementText("RESP_DESC");
				        
				        if("0000".equals(code)){
							draw.setRespType("S");
							draw.setRespCode("000");
						}else if("0100".equals(code)){
							draw.setRespType("R");
						}else{
							draw.setRespType("E");
							draw.setRespCode(code);
						}
				        draw.setRespMsg(result_message);
				        draw.setRespDate(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
				        draw.setUpdateDate(new Date());
				        routewayDrawService.updateByPrimaryKey(draw);
				    }else if(RouteCodeConstant.SM_ROUTE_CODE.equals(routeCode)){
						MemberMerchantKeyExample memberMerchantKeyExample = new MemberMerchantKeyExample();
				        memberMerchantKeyExample.createCriteria().andRouteCodeEqualTo(routeCode).andMerchantCodeEqualTo(draw.getMerchantCode()).andDelFlagEqualTo("0");
				        List<MemberMerchantKey> keyList = memberMerchantKeyService.selectByExample(memberMerchantKeyExample);
				        if(keyList == null || keyList.size()!=1){
				        	continue;
				        }
				        MemberMerchantKey merchantKey = keyList.get(0);
				        
				        
				        String serverUrl = SMConfig.agentServerUrl;
				         
				        Map<String, String> sParaTemp = new HashMap<String, String>();
						sParaTemp.put("version", "1.0");
						sParaTemp.put("service", "sumpay.trade.queryobopage");
						sParaTemp.put("format", "JSON");
						sParaTemp.put("app_id", merchantKey.getMerchantCode());
						sParaTemp.put("terminal_type", "wap");
						sParaTemp.put("timestamp", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
						sParaTemp.put("mer_id", merchantKey.getMerchantCode());
						sParaTemp.put("cst_no", merchantKey.getMerchantCode());
						
						sParaTemp.put("obo_type", "00");
						sParaTemp.put("order_id", draw.getOrderCode());
						sParaTemp.put("page","1" );
						sParaTemp.put("rows", "10");
						sParaTemp.put("is_inner", "01");//00个人 01商户
						
						
						String signSrc = StringUtil.orderedKey(sParaTemp);
						//System.out.println(signSrc);
						CryptNoRestrict cnr = new CryptNoRestrict();
						com.epay.scanposp.common.utils.sm.CertUtil util = new com.epay.scanposp.common.utils.sm.CertUtil();
						String sign = cnr.SignMsg(signSrc, util.getConfigPath() + merchantKey.getPrivateKey(), "sumpay");
						sParaTemp.put("sign", sign);
						sParaTemp.put("sign_type", "RSA");
						
						logger.info("商盟代付订单查询请求数据[{}]", new Object[] { JSONObject.fromObject(sParaTemp).toString() });
						
						
						List<NameValuePair> nvps = new LinkedList<NameValuePair>();
						List<String> keys = new ArrayList<String>(sParaTemp.keySet());
						for (int i = 0; i < keys.size(); i++) {
							 String name=(String) keys.get(i);
							 String value=(String) sParaTemp.get(name);
							if(value!=null && !"".equals(value)){
								nvps.add(new BasicNameValuePair(name, value));
							}
						}
						
						byte[] b = HttpClient4Util.getInstance().doPost(serverUrl, null, nvps);
						String respStr = new String(b, "UTF-8");
						logger.info("商盟代付订单查询返回数据[{}]", new Object[] { respStr });
				        
				        JSONObject resultObj = JSONObject.fromObject(respStr);
				        
				        String result_message = resultObj.getString("resp_msg");
						String result_code = resultObj.getString("resp_code");
						if("000000".equals(result_code)){
							String data = resultObj.getString("listObO");
							com.alibaba.fastjson.JSONArray arr = com.alibaba.fastjson.JSONArray.parseArray(data);
							JSONObject respJSONObject =  JSONObject.fromObject(arr.get(0));
							result_code = respJSONObject.getString("oboStatus");//00 成功 01 处理中 03 失败
							if("00".equals(result_code)&&"00".equals(respJSONObject.getString("oboType"))){//代付成功
								draw.setRespType("S");
								draw.setRespCode("000");
							}else if("01".equals(result_code)){
								draw.setRespType("R");
							}else{
								draw.setRespType("E");
								draw.setRespCode(result_code);
							}
							draw.setRespMsg(result_message);
							draw.setRespDate(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
							draw.setUpdateDate(new Date());
							routewayDrawService.updateByPrimaryKey(draw);
						}else{
							result_message = resultObj.getString("resp_msg");
							logger.info(result_message);
						}
				    }else if(RouteCodeConstant.TLKJ_ROUTE_CODE.equals(routeCode)){
						MemberMerchantKeyExample memberMerchantKeyExample = new MemberMerchantKeyExample();
				        memberMerchantKeyExample.createCriteria().andRouteCodeEqualTo(routeCode).andMerchantCodeEqualTo(draw.getMerchantCode()).andDelFlagEqualTo("0");
				        List<MemberMerchantKey> keyList = memberMerchantKeyService.selectByExample(memberMerchantKeyExample);
				        if(keyList == null || keyList.size()!=1){
				        	continue;
				        }
				        MemberMerchantKey merchantKey = keyList.get(0);
				        
				        String serverUrl = TLKJConfig.serverUrl+"/df/merchantPay/req";
				        
				        Map<String ,Object> reqMap = new HashMap<String ,Object>();
				        reqMap.put("agentNo", merchantKey.getAppId());
				        reqMap.put("reqMethod", "0652000101");
				        reqMap.put("orderNo",draw.getOrderCode());
				        reqMap.put("signType", "SHAWITHRSA");
				        
				        String s = MapUtils.map2UrlParams(reqMap);
				        CommonUtil commonUtil = new CommonUtil();
				        String privateKeyFile = commonUtil.getConfigPath() + "tlkjkey"+File.separator + TLKJConfig.rsaPrivateKey;
				        String sign = com.epay.scanposp.common.utils.tlkj.RSATool.signByPrivateKey(privateKeyFile, merchantKey.getPrivateKeyPassword(), s);
				        reqMap.put("sign", sign);
				        
				     	logger.info("通联快捷代付查询请求数据[{}]", new Object[] { JSONObject.fromObject(reqMap).toString() });
				     	String respStr =  HttpUtil.sendPostRequest(serverUrl, JSON.toJSONString(reqMap));
						logger.info("通联快捷代付查询返回报文[{}]", new Object[] { respStr });
				        
				        JSONObject resultObj = JSONObject.fromObject(respStr);
				        
				        String result_message = "";
						String result_code = resultObj.getString("respCode");
						if("00".equals(result_code)){
							String status = resultObj.getString("status");//0、待出款，2、出款失败；3、出款中；大于等于6、出款成功。
							if(Integer.parseInt(status)>=6){
								draw.setRespType("S");
								draw.setRespCode("000");
							}else if("3".equals(status)||"0".equals(status)){
								draw.setRespType("R");
							}else{
								draw.setRespType("E");
								draw.setRespCode(result_code);
							}
							if(resultObj.containsKey("respMsg")){
								draw.setRespMsg(resultObj.getString("respMsg"));
							}
							if(resultObj.containsKey("origRespMsg")){
								draw.setRespMsg(resultObj.getString("origRespMsg"));
							}
							draw.setRespDate(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
							draw.setUpdateDate(new Date());
							routewayDrawService.updateByPrimaryKey(draw);
						}else{
							result_message = resultObj.getString("respMsg");
							logger.info(result_message);
						}
				    }
				}
			}
		}catch(Exception e){
			logger.error(e.getMessage());
		}
	}
}
