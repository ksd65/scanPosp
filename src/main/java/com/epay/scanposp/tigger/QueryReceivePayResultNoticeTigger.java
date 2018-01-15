package com.epay.scanposp.tigger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.epay.scanposp.common.constant.RFConfig;
import com.epay.scanposp.common.utils.HttpUtil;
import com.epay.scanposp.common.utils.StringUtil;
import com.epay.scanposp.common.utils.constant.RouteCodeConstant;
import com.epay.scanposp.common.utils.epaySecurityUtil.EpaySignUtil;
import com.epay.scanposp.common.utils.slf.MerchantClient;
import com.epay.scanposp.common.utils.slf.SecurityUtil;
import com.epay.scanposp.common.utils.slf.vo.ReceivePay;
import com.epay.scanposp.common.utils.slf.vo.ReceivePayQueryRequest;
import com.epay.scanposp.common.utils.slf.vo.ReceivePayQueryResponse;
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
							routewayDrawService.updateByPrimaryKey(draw);
				        }
				        
					}
					
				}
			}
		}catch(Exception e){
			logger.error(e.getMessage());
		}
	}
}
