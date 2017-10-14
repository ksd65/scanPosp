package com.epay.scanposp.thread;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epay.scanposp.common.utils.HttpUtil;
import com.epay.scanposp.common.utils.StringUtil;
import com.epay.scanposp.common.utils.epaySecurityUtil.EpaySignUtil;
import com.epay.scanposp.entity.StTradeDetail;
import com.epay.scanposp.entity.SysOffice;
import com.epay.scanposp.service.StTradeDetailService;
import com.epay.scanposp.service.SysOfficeExtendService;

import net.sf.json.JSONObject;

public class MlPayResultNoticeThread implements Runnable{
	private static Logger logger = LoggerFactory.getLogger(MlPayResultNoticeThread.class);
	
	private SysOfficeExtendService sysOfficeExtendService;
	private StTradeDetailService stTradeDetailService;
	
	private StTradeDetail stTradeDetail;
	 
	public MlPayResultNoticeThread(SysOfficeExtendService sysOfficeExtendService,StTradeDetail stTradeDetail,StTradeDetailService stTradeDetailService) {
		super();
		this.sysOfficeExtendService=sysOfficeExtendService;
		this.stTradeDetail=stTradeDetail;
		this.stTradeDetailService=stTradeDetailService;
	}
	@Override
	public void run() {
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberCode", stTradeDetail.getMemberCode());
		paramMap.put("agtType", "3");
		SysOffice sysOffice = sysOfficeExtendService.getSysOfficeByMemberCode(paramMap);
		if(sysOffice != null){
			Map<String,String> params = new HashMap<String, String>();
			params.put("orderNum", stTradeDetail.getOrderNum());
			params.put("orderAmt", stTradeDetail.getOrderAmt().toString());
			params.put("payType", stTradeDetail.getPayType());
			params.put("bgUrl",stTradeDetail.getBgUrl());
			params.put("pageUrl", stTradeDetail.getPageUrl());
			params.put("memberCode", stTradeDetail.getMemberCode());
			params.put("goodsName", stTradeDetail.getGoodsName());
			params.put("goodsDesc", stTradeDetail.getGoodsDesc());
			params.put("accNo",stTradeDetail.getAccNo());
			params.put("settAmt", stTradeDetail.getSettAmt().toString());
			params.put("add1", stTradeDetail.getAdd1());
			params.put("add2", stTradeDetail.getAdd2());
			params.put("add3", stTradeDetail.getAdd3());
			params.put("add4", stTradeDetail.getAdd4());
			params.put("orderTime",stTradeDetail.getPayTime());
			params.put("returnCode", "0000");
			params.put("returnMsg", "支付成功");
			String srcStr = StringUtil.orderedKey(params);
			String signStr = EpaySignUtil.sign(sysOffice.getPrivateKeyRsa(), srcStr.toString());
			String content=srcStr+"&signStr="+signStr;
			try {
				String streq= HttpUtil.sendPostRequest(stTradeDetail.getBgUrl(), content);
			  if(streq.startsWith("\"") && streq.endsWith("\"")){
				  streq = streq.replace("\\", "");
				  streq = streq.substring(1,streq.length()-1);
				}
			  if(!streq.equals("")){
				JSONObject resData = JSONObject.fromObject(streq);
				if(resData.containsKey("resCode") && "0000".equals(resData.getString("resCode"))){
				  stTradeDetail.setCbRes("1");
			  }else{
				  if(stTradeDetail.getCounts() > 10){
					  stTradeDetail.setCbRes("2");
					}
			  }
			  }
			} catch (Exception e) {
				logger.error("回调第三方异常:"+e.getMessage());
				if(stTradeDetail.getCounts() > 10){
					stTradeDetail.setCbRes("2");
				}else{
					stTradeDetail.setCounts(stTradeDetail.getCounts()+1);
				}
			}
			 stTradeDetailService.updateByPrimaryKey(stTradeDetail);
		}
	}
}
