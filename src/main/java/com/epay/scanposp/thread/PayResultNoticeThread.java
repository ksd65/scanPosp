package com.epay.scanposp.thread;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epay.scanposp.common.utils.HttpUtil;
import com.epay.scanposp.common.utils.epaySecurityUtil.EpaySignUtil;
import com.epay.scanposp.entity.PayResultNotice;
import com.epay.scanposp.entity.SysOffice;
import com.epay.scanposp.service.PayResultNoticeService;
import com.epay.scanposp.service.SysOfficeExtendService;

public class PayResultNoticeThread implements Runnable{
	private static Logger logger = LoggerFactory.getLogger(PayResultNoticeThread.class);
	
	private PayResultNoticeService payResultNoticeService;
	private SysOfficeExtendService sysOfficeExtendService;
	private PayResultNotice payResultNotice;
	
	public PayResultNoticeThread(PayResultNoticeService payResultNoticeService,
			SysOfficeExtendService sysOfficeExtendService, PayResultNotice payResultNotice) {
		this.payResultNoticeService = payResultNoticeService;
		this.sysOfficeExtendService = sysOfficeExtendService;
		this.payResultNotice = payResultNotice;
	}


	@Override
	public void run() {
		System.out.println("==============回调通知线程开始执行=================");
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orderCode", payResultNotice.getOrderCode());
		paramMap.put("agtType", "3");
		SysOffice sysOffice = sysOfficeExtendService.getSysOfficeByOrderCode(paramMap);
		if(sysOffice != null){
			JSONObject payNoticeJson = new JSONObject();
			StringBuilder srcStr = new StringBuilder();
			//接口类型
			payNoticeJson.put("interfaceType", payResultNotice.getInterfaceType());
			srcStr.append("interfaceType="+payResultNotice.getInterfaceType());
			//商户号
			payNoticeJson.put("memberCode", payResultNotice.getMemberCode());
			srcStr.append("&memberCode="+payResultNotice.getMemberCode());
			//第三方平台订单号
			payNoticeJson.put("orderNum", payResultNotice.getOrderNumOuter());
			srcStr.append("&orderNum="+payResultNotice.getOrderNumOuter());
			//订单支付金额
			String payMoney = payResultNotice.getPayMoney().toString();
			payNoticeJson.put("payMoney", payMoney);
			srcStr.append("&payMoney="+payMoney);
			//收银台系统支付流水号
			payNoticeJson.put("payNum", payResultNotice.getOrderCode());
			srcStr.append("&payNum="+payResultNotice.getOrderCode());
			//支付时间
			payNoticeJson.put("payTime", payResultNotice.getPayTime());
			srcStr.append("&payTime="+payResultNotice.getPayTime());
			//支付类型
			payNoticeJson.put("payType", payResultNotice.getPayType());
			srcStr.append("&payType="+payResultNotice.getPayType());
			//平台类型
			payNoticeJson.put("platformType", payResultNotice.getPlatformType());
			srcStr.append("&platformType="+payResultNotice.getPlatformType());
			//应答类型
			payNoticeJson.put("respType", payResultNotice.getRespType());
			srcStr.append("&respType="+payResultNotice.getRespType());
			//应答码
			payNoticeJson.put("resultCode", payResultNotice.getResultCode());
			srcStr.append("&resultCode="+payResultNotice.getResultCode());
			//应答描述
			payNoticeJson.put("resultMsg", payResultNotice.getResultMessage());
			srcStr.append("&resultMsg="+payResultNotice.getResultMessage());
			String signStr = EpaySignUtil.sign(sysOffice.getPrivateKeyRsa(), srcStr.toString());
			payNoticeJson.put("signStr", signStr);
			String callBackUrl = payResultNotice.getReturnUrl();
			String ret = "";
			try {
				System.out.println("payNoticeJson===="+payNoticeJson.toString());
				ret = HttpUtil.sendPostRequest(callBackUrl, payNoticeJson.toString());
				if (StringUtils.isNotBlank(ret)) {
					if(ret.startsWith("\"") && ret.endsWith("\"")){
						ret = ret.replace("\\", "");
						ret = ret.substring(1,ret.length()-1);
					}
					JSONObject resData = JSONObject.fromObject(ret);
					payResultNotice.setCounts(payResultNotice.getCounts()+1);
					if(resData.containsKey("resCode") && "0000".equals(resData.getString("resCode"))){
						payResultNotice.setStatus("3");
					}else{
						if(payResultNotice.getCounts() > 10){
							payResultNotice.setStatus("4");
						}
					}
				}
								
			} catch (Exception e) {
				logger.error(e.getMessage());
				if(payResultNotice.getCounts() > 10){
					payResultNotice.setStatus("4");
				}else{
					payResultNotice.setCounts(payResultNotice.getCounts()+1);
				}
			}finally{
				payResultNotice.setRemarks(StringUtils.isBlank(ret)?"第三方返回结果为空":(ret.length()>1024?ret.substring(0, 1024):ret));
				payResultNotice.setUpdateDate(new Date());
				payResultNoticeService.updateByPrimaryKeySelective(payResultNotice);
				System.out.println("==============回调通知线程执行结束=================");
			}
			
		}
		
	}
}
