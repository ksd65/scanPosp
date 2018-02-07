package com.epay.scanposp.tigger;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.epay.scanposp.common.constant.HLBConfig;
import com.epay.scanposp.common.constant.RFConfig;
import com.epay.scanposp.common.constant.YZFConfig;
import com.epay.scanposp.common.utils.HttpUtil;
import com.epay.scanposp.common.utils.StringUtil;
import com.epay.scanposp.common.utils.constant.RouteCodeConstant;
import com.epay.scanposp.common.utils.epaySecurityUtil.EpaySignUtil;
import com.epay.scanposp.common.utils.hlb.Disguiser;
import com.epay.scanposp.common.utils.hlb.RSA;
import com.epay.scanposp.common.utils.ms.HttpClient4Util;
import com.epay.scanposp.common.utils.ms.MSCommonUtil;
import com.epay.scanposp.common.utils.slf.MerchantClient;
import com.epay.scanposp.common.utils.slf.SecurityUtil;
import com.epay.scanposp.common.utils.slf.vo.ReceivePay;
import com.epay.scanposp.common.utils.slf.vo.ReceivePayQueryRequest;
import com.epay.scanposp.common.utils.slf.vo.ReceivePayQueryResponse;
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
				    }
					
				}
			}
		}catch(Exception e){
			logger.error(e.getMessage());
		}
	}
}
