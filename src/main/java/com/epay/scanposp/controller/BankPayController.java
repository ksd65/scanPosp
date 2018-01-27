package com.epay.scanposp.controller;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.epay.scanposp.common.constant.RFConfig;
import com.epay.scanposp.common.constant.SLFConfig;
import com.epay.scanposp.common.constant.SysConfig;
import com.epay.scanposp.common.constant.YZFConfig;
import com.epay.scanposp.common.excep.ArgException;
import com.epay.scanposp.common.utils.CommonUtil;
import com.epay.scanposp.common.utils.DateUtil;
import com.epay.scanposp.common.utils.HttpUtil;
import com.epay.scanposp.common.utils.StringUtil;
import com.epay.scanposp.common.utils.ValidateUtil;
import com.epay.scanposp.common.utils.constant.PayTypeConstant;
import com.epay.scanposp.common.utils.constant.RouteCodeConstant;
import com.epay.scanposp.common.utils.epaySecurityUtil.EpaySignUtil;
import com.epay.scanposp.common.utils.ms.HttpClient4Util;
import com.epay.scanposp.common.utils.ms.MSCommonUtil;
import com.epay.scanposp.common.utils.slf.MerchantClient;
import com.epay.scanposp.common.utils.slf.SecurityUtil;
import com.epay.scanposp.common.utils.slf.vo.DeductInfo;
import com.epay.scanposp.common.utils.slf.vo.OrderRequest;
import com.epay.scanposp.common.utils.slf.vo.PaymentNotifyResponse;
import com.epay.scanposp.common.utils.slf.vo.ReceivePay;
import com.epay.scanposp.common.utils.slf.vo.ReceivePayQueryRequest;
import com.epay.scanposp.common.utils.slf.vo.ReceivePayQueryResponse;
import com.epay.scanposp.common.utils.slf.vo.ReceivePayRequest;
import com.epay.scanposp.common.utils.slf.vo.ReceivePayResponse;
import com.epay.scanposp.common.utils.yzf.AESTool;
import com.epay.scanposp.common.utils.yzf.Base64Utils;
import com.epay.scanposp.common.utils.yzf.EncryptUtil;
import com.epay.scanposp.common.utils.yzf.RSATool;
import com.epay.scanposp.entity.Account;
import com.epay.scanposp.entity.AccountExample;
import com.epay.scanposp.entity.DebitNote;
import com.epay.scanposp.entity.DebitNoteExample;
import com.epay.scanposp.entity.EpayCode;
import com.epay.scanposp.entity.EpayCodeExample;
import com.epay.scanposp.entity.MemberBank;
import com.epay.scanposp.entity.MemberBankExample;
import com.epay.scanposp.entity.MemberInfo;
import com.epay.scanposp.entity.MemberInfoExample;
import com.epay.scanposp.entity.MemberMerchantCode;
import com.epay.scanposp.entity.MemberMerchantCodeExample;
import com.epay.scanposp.entity.MemberMerchantKey;
import com.epay.scanposp.entity.MemberMerchantKeyExample;
import com.epay.scanposp.entity.MsResultNotice;
import com.epay.scanposp.entity.MsResultNoticeExample;
import com.epay.scanposp.entity.PayResultNotice;
import com.epay.scanposp.entity.PayResultNoticeExample;
import com.epay.scanposp.entity.PayType;
import com.epay.scanposp.entity.PayTypeExample;
import com.epay.scanposp.entity.RoutewayDraw;
import com.epay.scanposp.entity.RoutewayDrawExample;
import com.epay.scanposp.entity.SysCommonConfig;
import com.epay.scanposp.entity.SysCommonConfigExample;
import com.epay.scanposp.entity.SysOffice;
import com.epay.scanposp.entity.SysOfficeExample;
import com.epay.scanposp.entity.TradeDetail;
import com.epay.scanposp.entity.TradeDetailDaily;
import com.epay.scanposp.entity.TradeDetailExample;
import com.epay.scanposp.service.AccountService;
import com.epay.scanposp.service.CommonService;
import com.epay.scanposp.service.DebitNoteIpService;
import com.epay.scanposp.service.DebitNoteService;
import com.epay.scanposp.service.EpayCodeService;
import com.epay.scanposp.service.MemberBankService;
import com.epay.scanposp.service.MemberInfoService;
import com.epay.scanposp.service.MemberMerchantCodeService;
import com.epay.scanposp.service.MemberMerchantKeyService;
import com.epay.scanposp.service.MsResultNoticeService;
import com.epay.scanposp.service.PayResultNoticeService;
import com.epay.scanposp.service.PayResultNotifyService;
import com.epay.scanposp.service.PayTypeService;
import com.epay.scanposp.service.RoutewayDrawService;
import com.epay.scanposp.service.SysCommonConfigService;
import com.epay.scanposp.service.SysOfficeExtendService;
import com.epay.scanposp.service.SysOfficeService;
import com.epay.scanposp.service.TradeDetailDailyService;
import com.epay.scanposp.service.TradeDetailService;

@Controller
public class BankPayController {
	
	private static Logger logger = LoggerFactory.getLogger(CommomServiceController.class);
	
	@Resource
	ThreadPoolTaskExecutor threadPoolTaskExecutor;
	
	@Resource
	private SysOfficeExtendService sysOfficeExtendService;
	
	@Resource
	private MemberInfoService memberInfoService;
	
	@Resource
	private AccountService accountService;

	@Resource
	private DebitNoteService debitNoteService;
	
	@Resource
	private DebitNoteIpService debitNoteIpService;
	
	@Resource
	private TradeDetailService tradeDetailService;
	
	@Resource
	private TradeDetailDailyService tradeDetailDailyService;
	
	@Resource
	private EpayCodeService epayCodeService;
	
	@Autowired
	private PayTypeService payTypeService;
	
	@Autowired
	private MemberMerchantCodeService memberMerchantCodeService;
	
	@Resource
	private SysOfficeService sysOfficeService;
	
	@Resource
	private PayResultNoticeService payResultNoticeService;
	
	@Resource
	private MsResultNoticeService msResultNoticeService;
	
	@Autowired
	private MemberMerchantKeyService memberMerchantKeyService;
	
	@Autowired
	private MemberBankService memberBankService;
	
	@Autowired
	private RoutewayDrawService routewayDrawService;
	
	@Autowired
	private CommonService commonService;
	
	@Resource
	private PayResultNotifyService payResultNotifyService;
	
	@Autowired
	private SysCommonConfigService sysCommonConfigService;
	
	@ResponseBody
	@RequestMapping("/api/bankPay/toPay")
	public JSONObject toPay(HttpServletRequest request,HttpServletResponse response){
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		System.out.println("请求参数==="+reqDataJson.toString());
		String bankCode = "";
		if(reqDataJson.containsKey("bankCode")){
			bankCode = reqDataJson.getString("bankCode");
		}
		String payMoney = "";
		if(reqDataJson.containsKey("payMoney")){
			payMoney = reqDataJson.getString("payMoney");
		}
		String orderNum = "";
		if(reqDataJson.containsKey("orderNum")){
			orderNum = reqDataJson.getString("orderNum");
		}
		String memberCode = "";
		if(reqDataJson.containsKey("memberCode")){
			memberCode = reqDataJson.getString("memberCode");
		}
		String callbackUrl = "";
		if(reqDataJson.containsKey("callbackUrl")){
			callbackUrl = reqDataJson.getString("callbackUrl");
		}
	//	String gateWayType = reqDataJson.getString("gateWayType");
	//	String goodsName = reqDataJson.getString("goodsName");
		
		String signStr = "";
		if(reqDataJson.containsKey("signStr")){
			signStr = reqDataJson.getString("signStr");
		}
		
		StringBuilder srcStr = new StringBuilder();
		JSONObject result = new JSONObject();
		if(null == payMoney || !ValidateUtil.isDoubleT(payMoney) || Double.parseDouble(payMoney)<=0){
			result.put("returnCode", "0005");
			result.put("returnMsg", "支付金额输入不正确");
			return result;
		}
		
		if(bankCode == null || "".equals(bankCode)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "银行编码缺失");
			return result;
		}
		srcStr.append("bankCode="+bankCode);
		
		if(callbackUrl == null || "".equals(callbackUrl)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "平台支付结果通知回调地址缺失");
			return result;
		}
		srcStr.append("&callbackUrl="+callbackUrl);
		
	/*	if(gateWayType == null || "".equals(gateWayType)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "支付方式缺失");
			return result;
		}
		srcStr.append("&gateWayType="+gateWayType);
		
		if(goodsName == null || "".equals(goodsName)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "商品名称缺失");
			return result;
		}
		srcStr.append("&goodsName="+goodsName);
		*/
		if(memberCode == null || "".equals(memberCode)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "商户号缺失");
			return result;
		}
		srcStr.append("&memberCode="+memberCode);
		
		if(orderNum == null || "".equals(orderNum)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "平台订单号缺失");
			return result;
		}
		if(orderNum.length() > 32){
			result.put("returnCode", "0007");
			result.put("returnMsg", "订单号长度不能超过32位");
			return result;
		}
		srcStr.append("&orderNum="+orderNum);
		srcStr.append("&payMoney="+payMoney);
		
		if(signStr == null || "".equals(signStr)){ 
			result.put("returnCode", "0007");
			result.put("returnMsg", "缺少签名信息");
			return result;
		}
		
		
		
		result = validMemberInfoForQrcode(memberCode, orderNum, payMoney, "3",  srcStr.toString(), signStr, callbackUrl ,bankCode);
		
		return result;
	}
	
	
	
	public JSONObject validMemberInfoForQrcode(String memberCode,String orderNum,String payMoney,String platformType,String signOrginalStr,String signedStr,String callbackUrl,String bankCode){
		JSONObject result = new JSONObject();
		
		MemberInfoExample memberInfoExample = new MemberInfoExample();
		memberInfoExample.or().andCodeEqualTo(memberCode).andDelFlagEqualTo("0");
		List<MemberInfo> memberInfoList = memberInfoService.selectByExample(memberInfoExample);
		if(null == memberInfoList || memberInfoList.size() == 0){
			result.put("returnCode", "0001");
			result.put("returnMsg", "商户信息不存在");
			return result;
		}
		MemberInfo memberInfo = memberInfoList.get(0);
		if(!"0".equals(memberInfo.getStatus())){
			if("4".equals(memberInfo.getStatus())){
				result.put("returnCode", "0011");
				result.put("returnMsg", "该商户未进行认证，暂时无法交易");
				return result;
			}
			result.put("returnCode", "0008");
			result.put("returnMsg", "对不起，该商户暂不可用");
			return result;
		}
		
		AccountExample accountExample = new AccountExample();
		accountExample.createCriteria().andMemberIdEqualTo(memberInfo.getId()).andDelFlagEqualTo("0");
		List<Account> accounts = accountService.selectByExample(accountExample);
		if (accounts == null || accounts.size() != 1) {
			result.put("returnCode", "0008");
			result.put("returnMsg", "会员账户不存在");
			return result;
		}
		
		EpayCodeExample epayCodeExample = new EpayCodeExample();
		List<String> values = new ArrayList<String>();
		values.add("5");
		values.add("7");
		epayCodeExample.or().andMemberIdEqualTo(memberInfo.getId()).andStatusIn(values);
		List<EpayCode> epayCodeList = epayCodeService.selectByExample(epayCodeExample);
		if(null == epayCodeList || epayCodeList.size() == 0){
			result.put("returnCode", "0008");
			result.put("returnMsg", "对不起，该商户暂不可用");
			return result;
		}
		
		
		Map<String,String> rtMap  = getRouteCodeAndAisleType(memberInfo.getId(),PayTypeConstant.PAY_METHOD_YL,PayTypeConstant.PAY_TYPE_YL);
		String routeCode = rtMap.get("routeCode");
		String aisleType = rtMap.get("aisleType");
		MemberMerchantCodeExample memberMerchantCodeExample = new MemberMerchantCodeExample();
		if(aisleType !=null &&!"".equals(aisleType)){
			memberMerchantCodeExample.createCriteria().andMemberIdEqualTo(memberInfo.getId()).andRouteCodeEqualTo(routeCode).andAisleTypeEqualTo(aisleType).andDelFlagEqualTo("0");
		}else{
			memberMerchantCodeExample.createCriteria().andMemberIdEqualTo(memberInfo.getId()).andRouteCodeEqualTo(routeCode).andDelFlagEqualTo("0");
		}
		List<MemberMerchantCode> merchantCodes = memberMerchantCodeService.selectByExample(memberMerchantCodeExample);
		if (merchantCodes == null || merchantCodes.size() != 1) {
			result.put("returnCode", "0008");
			result.put("returnMsg", "对不起，商户编码不存在");
			return result;
		}
		
		MemberMerchantCode merchantCode = merchantCodes.get(0);
		
		SysOfficeExample sysOfficeExample = new SysOfficeExample();
		sysOfficeExample.or().andIdEqualTo(epayCodeList.get(0).getOfficeId()).andAgtTypeEqualTo("3");
		List<SysOffice> sysOfficeList = sysOfficeService.selectByExample(sysOfficeExample);
		if(null == sysOfficeList || sysOfficeList.size() == 0){
			result.put("returnCode", "0008");
			result.put("returnMsg", "该商户暂未支持收银台功能，请确认后重试");
			return result;
		}
		
		PayResultNoticeExample payResultNoticeExample = new PayResultNoticeExample();
		payResultNoticeExample.or().andMemberCodeEqualTo(memberCode).andOrderNumOuterEqualTo(orderNum).andStatusNotEqualTo("1");
		List<PayResultNotice> PayResultNoticeList = payResultNoticeService.selectByExample(payResultNoticeExample);
		if(null != PayResultNoticeList && PayResultNoticeList.size() > 0){
			result.put("returnCode", "0002");
			result.put("returnMsg", "该笔订单已支付，请勿重复支付");
			return result;
		}
		
		SysOffice sysOffice = sysOfficeList.get(0);
//		String singedStr = EpaySignUtil.sign(sysOffice.getPrivateKeyRsa(), signOrginalStr);
//		System.out.println(singedStr);  
		if(!EpaySignUtil.checksign(sysOffice.getPublicKeyRsa(), signOrginalStr, signedStr)){//by linxf 测试屏蔽
			result.put("returnCode", "0004");
			result.put("returnMsg", "签名校验错误，请检查签名参数是否正确");
			return result;
		}
		
		//校验交易额是否超出限制
	    /*
		JSONObject checkResult = checkPayLimit(memberInfo.getId(), new BigDecimal(payMoney), memberInfo.getSingleLimit(), memberInfo.getDayLimit());
		if(null != checkResult){
			return checkResult;
		}
		*/
		
		if(RouteCodeConstant.SLF_ROUTE_CODE.equals(routeCode)){
			result = bankPay(platformType,memberInfo, payMoney, orderNum, callbackUrl , merchantCode , bankCode);
			result.put("routeCode", routeCode);
		}
		
		return result;
	}
	
	public JSONObject bankPay(String platformType,MemberInfo memberInfo,String payMoney,String orderNumOuter,String callbackUrl,MemberMerchantCode merchantCode,String bankCode) {
		JSONObject result = new JSONObject();
		try {
			// 插入一条收款记录
			String orderCode = CommonUtil.getOrderCode();
			
			DebitNote debitNote = new DebitNote();
			debitNote.setCreateDate(new Date());
			debitNote.setMemberId(memberInfo.getId());
			debitNote.setMoney(new BigDecimal(payMoney));
			debitNote.setOrderCode(orderCode);
			debitNote.setOrderNumOuter(orderNumOuter);
			debitNote.setRouteId(RouteCodeConstant.SLF_ROUTE_CODE);
			debitNote.setStatus("0");
			debitNote.setTxnMethod(PayTypeConstant.PAY_METHOD_YL);
			debitNote.setTxnType("8");
			debitNote.setMemberCode(memberInfo.getWxMemberCode());
			debitNote.setMerchantCode(merchantCode.getWxMerchantCode());
			
			debitNote.setSettleType(memberInfo.getSettleType());
			if("0".equals(memberInfo.getSettleType())){
				debitNote.setTradeRate(merchantCode.getT0TradeRate());
			}else{
				debitNote.setTradeRate(merchantCode.getT1TradeRate());
			}
			
			String configName = "SINGLE_MIN_1006_005_YL";
			JSONObject checkResult = checkMinMoney(configName, new BigDecimal(payMoney));
			if(null != checkResult){
				debitNote.setStatus("5");
				debitNoteService.insertSelective(debitNote);
				return checkResult;
			}
			
			configName = "SINGLE_LIMIT_1006_005_YL";
			JSONObject limitResult = checkLimitMoney(configName, new BigDecimal(payMoney));
			if(null != limitResult){
				debitNote.setStatus("4");
				debitNoteService.insertSelective(debitNote);
				return limitResult;
			}
			
			JSONObject tResult = checkLimitCounts(RouteCodeConstant.ESK_ROUTE_CODE);
			if(null != tResult){
				debitNote.setStatus("8");
				debitNoteService.insertSelective(debitNote);
				return tResult;
			}
			
			debitNoteService.insertSelective(debitNote);
			
			PayResultNotice payResultNotice = new PayResultNotice();
			payResultNotice.setOrderCode(debitNote.getOrderCode());
			payResultNotice.setOrderNumOuter(orderNumOuter);
			payResultNotice.setPayMoney(debitNote.getMoney());
			payResultNotice.setMemberCode(memberInfo.getCode());
			payResultNotice.setPayType("8");
			
			payResultNotice.setReturnUrl(callbackUrl);
			payResultNotice.setStatus("1");
			payResultNotice.setCreateDate(new Date());
			payResultNotice.setPlatformType(platformType);
			if("3".equals(platformType)){
				payResultNotice.setInterfaceType("2");
			}else{
				payResultNotice.setInterfaceType("1");
			}
			
			
			
			payResultNoticeService.insertSelective(payResultNotice);
			
			// String callBack = "http://" + request.getServerName() + ":" +
			// request.getServerPort() + request.getContextPath()+
			// "/debitNote/msNotify";
			String callBack = SysConfig.serverUrl + "/bankPay/slfPayNotify";
			
			
			//System.out.println("-------------通知地址------" + callBack + "------------");
			
			
			MemberMerchantKeyExample memberMerchantKeyExample = new MemberMerchantKeyExample();
	        memberMerchantKeyExample.createCriteria().andRouteCodeEqualTo(RouteCodeConstant.SLF_ROUTE_CODE).andMerchantCodeEqualTo(merchantCode.getWxMerchantCode()).andDelFlagEqualTo("0");
	        List<MemberMerchantKey> keyList = memberMerchantKeyService.selectByExample(memberMerchantKeyExample);
	        if(keyList == null || keyList.size()!=1){
	            result.put("returnCode", "0003");
				result.put("returnMsg", "商户私钥未配置");
				return result;
	        }
	        MemberMerchantKey merchantKey = keyList.get(0);
			
			
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			
			OrderRequest orderRequest = new OrderRequest();
			orderRequest.setMerchantId(merchantCode.getWxMerchantCode());
			orderRequest.setMerchantOrderId(orderCode);
			orderRequest.setMerchantOrderAmt(String.valueOf((int)((new BigDecimal(payMoney)).floatValue()*100)));
			orderRequest.setMerchantOrderDesc(memberInfo.getName() + " 收款");
			orderRequest.setMerchantPayNotifyUrl(callBack);
			orderRequest.setMerchantFrontEndUrl(SLFConfig.merchantFrontEndUrl);
			orderRequest.setAccountType("0");//B2C：0 借记卡，1 贷记卡  B2B：4 对公账户
			orderRequest.setOrderTime(format.format(new Date()));
			orderRequest.setRptType("1");//收款方式 定值1
			orderRequest.setPayMode("0");//付款类型 0 初次支付 1 重复支付，当前支持 0
			orderRequest.setBankId(bankCode);
			
			MerchantClient client = new MerchantClient(merchantCode.getWxMerchantCode());
			String merchantCertPath = MerchantClient.configPath + merchantKey.getPrivateKey();
			SecurityUtil.merchantCertPath = merchantCertPath;
			SecurityUtil.init(merchantCertPath,merchantKey.getPrivateKeyPassword());
			String msg = client.sendOrderRequest(orderRequest);
			
			result.put("msg", msg);
			result.put("payUrl", SLFConfig.payURL);
			result.put("returnCode", "0000");
			result.put("returnMsg", "成功");
			
        } catch (Exception e) {
			logger.error(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
			return result;
		}
		return result;
	}
	
	
	@RequestMapping("/bankPay/slfPayNotify")
	public void slfPayNotify(HttpServletRequest request,HttpServletResponse response) {
		String respString = "success";
		MerchantClient merchant = new MerchantClient();
		InputStream is = null;
		try {
			StringBuffer notifyResultStr = new StringBuffer("");
			is = request.getInputStream();
			byte[] b = new byte[1024];
			int len = -1;
			while((len = is.read(b)) != -1){
				notifyResultStr.append(new String(b,0,len,"utf-8"));
			}
			logger.info("slfPayNotify回调通知返回报文[{}]",  notifyResultStr );
			
			PaymentNotifyResponse pnResponse = merchant
				.parsePaymentNotify(notifyResultStr.toString());
			
			logger.info("slfPayNotify解密回调通知报文[{}]",  pnResponse );
			
			if(pnResponse != null){
				String reqMsgId = pnResponse.getMerchantOrderId();
            	TradeDetailExample tradeDetailExample = new TradeDetailExample();
            	tradeDetailExample.or().andOrderCodeEqualTo(reqMsgId);
            	List<TradeDetail> tradeDetailList = tradeDetailService.selectByExample(tradeDetailExample);
            	if(tradeDetailList!=null && tradeDetailList.size()>0){
            		response.getWriter().write(respString);
            		return;
            	}
            	List<DeductInfo> deductList = pnResponse.getDeductList();
				if(deductList !=null && deductList.size()>0){
					DeductInfo deductInfo = deductList.get(0);
					String result_code = deductInfo.getPayStatus();//00 等待付款，01 付款成功，02 付款失败
					if(result_code != null ){
                    	MsResultNoticeExample msResultNoticeExample = new MsResultNoticeExample();
            			msResultNoticeExample.or().andOrderCodeEqualTo(reqMsgId);
            			List<MsResultNotice> msResultNoticeList = msResultNoticeService.selectByExample(msResultNoticeExample);
            			if(null == msResultNoticeList || msResultNoticeList.size() == 0){
            				DebitNoteExample debitNoteExample = new DebitNoteExample();
            				debitNoteExample.createCriteria().andOrderCodeEqualTo(reqMsgId);
            				List<DebitNote> debitNotes = debitNoteService.selectByExample(debitNoteExample);
            				if (debitNotes != null && debitNotes.size() > 0) {
            					DebitNote debitNote = debitNotes.get(0);
            					debitNote.setRespCode(result_code);
            					debitNote.setRespMsg(deductInfo.getPayDesc());
            					
            					//新增一条交易明细记录
            					TradeDetail tradeDetail=new TradeDetail();
            					tradeDetail.setTxnDate(DateUtil.getDateFormat(new Date(), "yyyyMMdd"));
            					tradeDetail.setMemberId(debitNote.getMemberId());
            					tradeDetail.setMerchantCode(debitNote.getMerchantCode());
            					
            					tradeDetail.setChannelNo(deductInfo.getPayOrderId());
            					
            					tradeDetail.setMemberCode(debitNote.getMemberCode());
            					tradeDetail.setMoney(debitNote.getMoney());
            					if(deductInfo.getPayTime()!=null){
            						tradeDetail.setPayTime(deductInfo.getPayTime());
            					}else{
            						tradeDetail.setPayTime(DateUtil.getDateTimeStr(debitNote.getCreateDate()));
            					}
            					tradeDetail.setPtSerialNo(debitNote.getOrderCode());
            					tradeDetail.setOrderCode(debitNote.getOrderCode());
            					tradeDetail.setReqDate(DateUtil.getDateTimeStr(debitNote.getCreateDate()));
            					//tradeDetail.setRespCode(respJSONObject.get("respCode").toString());
            					tradeDetail.setRespDate(DateUtil.getDateTimeStr(new Date()));
            					tradeDetail.setRespMsg(deductInfo.getPayDesc());
            					tradeDetail.setRouteId(debitNote.getRouteId());
            					tradeDetail.setTxnMethod(debitNote.getTxnMethod());
            					tradeDetail.setTxnType(debitNote.getTxnType());
            					tradeDetail.setMemberTradeRate(debitNote.getTradeRate());
            					tradeDetail.setDelFlag("0");
            					tradeDetail.setCreateDate(new Date());
            					if ("01".equals(result_code)) {
            						debitNote.setStatus("1");
            						tradeDetail.setRespType("S");
            						tradeDetail.setRespCode("000000");
            					}else if ("00".equals(result_code)) {
            						debitNote.setStatus("3");
            						tradeDetail.setRespType("R");
            						tradeDetail.setRespCode(result_code);
            					}else{
            						debitNote.setStatus("2");
            						tradeDetail.setRespType("E");
            						tradeDetail.setRespCode(result_code);
            					}
            					debitNote.setUpdateDate(new Date());
            					debitNoteService.updateByPrimaryKey(debitNote);
            					
            					MsResultNotice msResultNotice = new MsResultNotice();
            					msResultNotice.setMoney(new BigDecimal(Integer.parseInt(deductInfo.getPayAmt())/100));
            					msResultNotice.setOrderCode(reqMsgId);
            					msResultNotice.setPtSerialNo(deductInfo.getPayOrderId());
            					msResultNotice.setRespDate(DateUtil.getDateTimeStr(new Date()));
            					msResultNotice.setRespType("0");
            					msResultNotice.setRespCode(deductInfo.getPayStatus());
            					msResultNotice.setRespMsg(deductInfo.getPayDesc());
            					
            					if(deductInfo.getPayTime() != null){
            						msResultNotice.setPayTime(deductInfo.getPayTime());
            					}else{
            						msResultNotice.setPayTime(DateUtil.getDateTimeStr(new Date()));
            					}
            					
            					
            					msResultNotice.setCreateDate(new Date());
            					msResultNotice.setUpdateDate(new Date());
            					msResultNoticeService.insertSelective(msResultNotice);
            					
            					
        						if("0".equals(debitNote.getSettleType())){
        							tradeDetail.setSettleType("0");
        						}else{
        							tradeDetail.setSettleType("1");
        						}
        						PayResultNoticeExample payResultNoticeExample = new PayResultNoticeExample();
            					payResultNoticeExample.or().andOrderCodeEqualTo(reqMsgId);
            					List<PayResultNotice> payResultNoticeList = payResultNoticeService.selectByExample(payResultNoticeExample);
            					PayResultNotice payResultNotice = null;
            					if(payResultNoticeList.size()>0){
            						payResultNotice = payResultNoticeList.get(0);
            						payResultNotice.setResultMessage(debitNote.getRespMsg());
            						payResultNotice.setStatus("2");
            						payResultNotice.setPayTime(msResultNotice.getPayTime());
            						payResultNotice.setUpdateDate(new Date());
            						if ("01".equals(result_code)) {
            							payResultNotice.setRespType("2");
            							payResultNotice.setResultCode("0000");
            							payResultNotice.setResultMessage("交易成功");
            						}else if("00".equals(result_code)){
            							payResultNotice.setRespType("1");
            							payResultNotice.setResultCode("0009");   
            							payResultNotice.setResultMessage("交易正在处理中...");
            						}else{
            							payResultNotice.setRespType("3");
            							payResultNotice.setResultCode("0003");   
            							payResultNotice.setResultMessage("交易失败："+deductInfo.getPayDesc());
            						}
            						
            						tradeDetail.setInterfaceType(payResultNotice.getInterfaceType());
            						tradeDetail.setPlatformType(payResultNotice.getPlatformType());
            						tradeDetail.setOrderNumOuter(payResultNotice.getOrderNumOuter());
            					}
            					//更改逻辑  只有交易成功的记录才写进交易记录表  单独写在此处防止后面再次更改逻辑会不理解
            					if ("01".equals(result_code)) {
            						tradeDetail.setCardType(msResultNotice.getCardType());
            						tradeDetailService.insertSelective(tradeDetail);
            						
            						TradeDetailDaily tradeDetailDaily = new TradeDetailDaily();
            						
            						tradeDetailDaily.setTxnDate(DateUtil.getDateFormat(new Date(), "yyyyMMdd"));
            						tradeDetailDaily.setMemberId(debitNote.getMemberId());
            						tradeDetailDaily.setMerchantCode(debitNote.getMerchantCode());
            						tradeDetailDaily.setMoney(debitNote.getMoney());
            						tradeDetailDaily.setOrderCode(debitNote.getOrderCode());
            						tradeDetailDaily.setRouteId(debitNote.getRouteId());
            						tradeDetailDaily.setDelFlag("0");
            						tradeDetailDaily.setCreateDate(new Date());
            						tradeDetailDailyService.insertSelective(tradeDetailDaily);
            					}
            					
            					if(payResultNoticeList.size() > 0){
            						payResultNotifyService.notify(payResultNotice);
            						//更新状态是触发器可以读取该条数据以执行通知任务
            					/*	payResultNoticeService.updateByPrimaryKeySelective(payResultNotice);
            						//获取民生通知后即向商户提供结果通知 (后续若因其他因素需多次通知商户,则由相应的定时任务完成)
            						System.out.println("getPoolSize====" + threadPoolTaskExecutor.getPoolSize());
            						PayResultNoticeThread payResultNoyiceThread = new PayResultNoticeThread(payResultNoticeService, sysOfficeExtendService, payResultNotice);
            						threadPoolTaskExecutor.execute(payResultNoyiceThread);
            						System.out.println("getActiveCount====" + threadPoolTaskExecutor.getActiveCount());
            						//new Thread(payResultNoyiceThread).start();
								*/
            					}
            					
            				}
            			}	
                    }
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		try {
			response.getWriter().write(respString);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

	}
	
	
	@ResponseBody
	@RequestMapping("/api/bankPay/draw")
	public JSONObject draw(HttpServletRequest request,HttpServletResponse response){
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		
		
		String drawId = reqDataJson.getString("drawId");
		String memberId = reqDataJson.getString("memberId");
		JSONObject result = new JSONObject();
		
		try{
			if(memberId == null || "".equals(memberId)){
				result.put("returnCode", "0007");
				result.put("returnMsg", "商户Id缺失");
				return result;
			}
			if(drawId == null || "".equals(drawId)){
				result.put("returnCode", "0007");
				result.put("returnMsg", "提现记录Id缺失");
				return result;
			}
			RoutewayDrawExample routewayDrawExample = new RoutewayDrawExample();
			routewayDrawExample.createCriteria().andIdEqualTo(Integer.parseInt(drawId)).andMemberIdEqualTo(Integer.parseInt(memberId)).andDelFlagEqualTo("0");
			List<RoutewayDraw> drawList = routewayDrawService.selectByExample(routewayDrawExample);
			if(drawList==null || drawList.size()!=1){
				result.put("returnCode", "0007");
				result.put("returnMsg", "提现记录不存在");
				return result;
			}
			RoutewayDraw draw = drawList.get(0);
			String routeCode = draw.getRouteCode();
			JSONObject obj = null;
			if(RouteCodeConstant.SLF_ROUTE_CODE.equals(routeCode)){
				obj = receivePay(memberId,String.valueOf(draw.getMoney().doubleValue()-1));
			}else if(RouteCodeConstant.RF_ROUTE_CODE.equals(routeCode)){
				obj = receivePayRf(memberId, String.valueOf(draw.getMoney()), draw);
			}
			
			if("0000".equals(obj.getString("returnCode"))){
				draw.setRespType("R");
				result.put("returnCode", "0000");
				result.put("returnMsg", "成功");
			}else{
				draw.setRespType("E");
				draw.setRespMsg(obj.getString("returnMsg"));
				if(obj.containsKey("respCode")){
					draw.setRespCode(obj.getString("respCode"));
				}
				result.put("returnCode", "0007");
				result.put("returnMsg", obj.getString("returnMsg"));
			}
			if(obj.containsKey("orderCode")){
				draw.setOrderCode(obj.getString("orderCode"));
				draw.setPtSerialNo(obj.getString("orderCode"));
			}
			if(obj.containsKey("merchantCode")){
				draw.setMerchantCode(obj.getString("merchantCode"));
			}
			
			if(obj.containsKey("reqDate")){
				draw.setReqDate(obj.getString("reqDate"));
			}
			draw.setUpdateDate(new Date());
			routewayDrawService.updateByPrimaryKey(draw);
		}catch(Exception e){
			logger.error(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
		}
		return result;
	}
	
	
	public JSONObject receivePay(String memberId,String payMoney){
		JSONObject result = new JSONObject();
		if(null == payMoney || !ValidateUtil.isDoubleT(payMoney) || Double.parseDouble(payMoney)<=0){
			result.put("returnCode", "0005");
			result.put("returnMsg", "支付金额输入不正确");
			return result;
		}
		
		if(memberId == null || "".equals(memberId)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "商户Id缺失");
			return result;
		}
		
		try{
			MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(Integer.parseInt(memberId));
			if(memberInfo == null){
				result.put("returnCode", "0008");
				result.put("returnMsg", "对不起，商户不存在");
				return result;
			}
			MemberMerchantCodeExample memberMerchantCodeExample = new MemberMerchantCodeExample();
			memberMerchantCodeExample.createCriteria().andMemberIdEqualTo(memberInfo.getId()).andRouteCodeEqualTo(RouteCodeConstant.SLF_ROUTE_CODE).andDelFlagEqualTo("0");
			
			List<MemberMerchantCode> merchantCodes = memberMerchantCodeService.selectByExample(memberMerchantCodeExample);
			if (merchantCodes == null || merchantCodes.size() != 1) {
				result.put("returnCode", "0008");
				result.put("returnMsg", "对不起，商户编码不存在");
				return result;
			}
			MemberMerchantCode merchantCode = merchantCodes.get(0);
			
			MemberBankExample memberBankExample = new MemberBankExample();
			memberBankExample.createCriteria().andMemberIdEqualTo(memberInfo.getId()).andDelFlagEqualTo("0");
			List<MemberBank> memberBanks = memberBankService.selectByExample(memberBankExample);
			if (memberBanks == null || memberBanks.size() != 1) {
				result.put("returnCode", "0008");
				result.put("returnMsg", "对不起，银行卡号未配置");
				return result;
			}
			MemberBank memberBank = memberBanks.get(0);
			
			MemberMerchantKeyExample memberMerchantKeyExample = new MemberMerchantKeyExample();
	        memberMerchantKeyExample.createCriteria().andRouteCodeEqualTo(RouteCodeConstant.SLF_ROUTE_CODE).andMerchantCodeEqualTo(merchantCode.getWxMerchantCode()).andDelFlagEqualTo("0");
	        List<MemberMerchantKey> keyList = memberMerchantKeyService.selectByExample(memberMerchantKeyExample);
	        if(keyList == null || keyList.size()!=1){
	            result.put("returnCode", "0003");
				result.put("returnMsg", "商户私钥未配置");
				return result;
	        }
	        MemberMerchantKey merchantKey = keyList.get(0);
			
			String orderCode = CommonUtil.getOrderCode();
			String callBack = SysConfig.serverUrl + "/bankPay/receivePayNotify";
			
			String reqDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			ReceivePayRequest receivePayRequest = new ReceivePayRequest();
			receivePayRequest.setApplication("ReceivePay");
			receivePayRequest.setVersion("1.0.1");
			receivePayRequest.setMerchantId(merchantCode.getWxMerchantCode());
			receivePayRequest.setTranId(orderCode);
			receivePayRequest.setReceivePayNotifyUrl(callBack); 
			receivePayRequest.setReceivePayType("1");//0 收款，1 付款
			receivePayRequest.setTimestamp(reqDate);
			receivePayRequest.setAccountProp("0");//0 对私，4 对公
			receivePayRequest.setBankGeneralName(memberBank.getBankOpen());
			receivePayRequest.setAccNo(memberBank.getAccountNumber());
			receivePayRequest.setAccName(memberBank.getAccountName());
			receivePayRequest.setAmount(String.valueOf((int)((new BigDecimal(payMoney)).floatValue()*100)));
			receivePayRequest.setCredentialType("01");//证件类型  01 身份证，对私必填
	
			receivePayRequest.setCredentialNo(memberInfo.getCertNbr());
			receivePayRequest.setTel(memberInfo.getMobilePhone());
			//receivePayRequest.setSummary(new String(request.getParameter("summary").getBytes("ISO8859-1"),"utf-8"));
			
			MerchantClient client = new MerchantClient(merchantCode.getWxMerchantCode());
			String merchantCertPath = MerchantClient.configPath + merchantKey.getPrivateKey();
			SecurityUtil.merchantCertPath = merchantCertPath;
			SecurityUtil.init(merchantCertPath,merchantKey.getPrivateKeyPassword());
			System.out.println(receivePayRequest.toString());
			//ReceivePayResponse receivePayResponse = new ReceivePayResponse();
			ReceivePayResponse receivePayResponse = client.receivePayRequest(receivePayRequest);
			System.out.println(receivePayResponse.toString());
			if("000".equals(receivePayResponse.getRespCode())){
				result.put("returnCode", "0000");
				result.put("returnMsg", "请求成功");
			}else{
				result.put("returnCode", "0001");
				result.put("returnMsg", receivePayResponse.getRespDesc());
				result.put("respCode", receivePayResponse.getRespCode());
				result.put("respMsg", receivePayResponse.getRespDesc());
			}
			result.put("orderCode", orderCode);
			result.put("merchantCode", merchantCode.getWxMerchantCode());
			result.put("reqDate", reqDate);
		}catch(Exception e){
			logger.error(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
		}
		return result;
	}
	
	
	public JSONObject receivePayRf(String memberId,String payMoney,RoutewayDraw draw){
		JSONObject result = new JSONObject();
		if(null == payMoney || !ValidateUtil.isDoubleT(payMoney) || Double.parseDouble(payMoney)<=0){
			result.put("returnCode", "0005");
			result.put("returnMsg", "支付金额输入不正确");
			return result;
		}
		
		if(memberId == null || "".equals(memberId)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "商户Id缺失");
			return result;
		}
		
		try{
			MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(Integer.parseInt(memberId));
			if(memberInfo == null){
				result.put("returnCode", "0008");
				result.put("returnMsg", "对不起，商户不存在");
				return result;
			}
			MemberMerchantCodeExample memberMerchantCodeExample = new MemberMerchantCodeExample();
			memberMerchantCodeExample.createCriteria().andMemberIdEqualTo(memberInfo.getId()).andRouteCodeEqualTo(draw.getRouteCode()).andDelFlagEqualTo("0");
			
			List<MemberMerchantCode> merchantCodes = memberMerchantCodeService.selectByExample(memberMerchantCodeExample);
			if (merchantCodes == null || merchantCodes.size() != 1) {
				result.put("returnCode", "0008");
				result.put("returnMsg", "对不起，商户编码不存在");
				return result;
			}
			MemberMerchantCode merchantCode = merchantCodes.get(0);
			
			MemberMerchantKeyExample memberMerchantKeyExample = new MemberMerchantKeyExample();
	        memberMerchantKeyExample.createCriteria().andRouteCodeEqualTo(draw.getRouteCode()).andMerchantCodeEqualTo(merchantCode.getWxMerchantCode()).andDelFlagEqualTo("0");
	        List<MemberMerchantKey> keyList = memberMerchantKeyService.selectByExample(memberMerchantKeyExample);
	        if(keyList == null || keyList.size()!=1){
	            result.put("returnCode", "0003");
				result.put("returnMsg", "商户私钥未配置");
				return result;
	        }
	        
	        double amount = (new BigDecimal(payMoney)).doubleValue()-merchantCode.getT0DrawFee().doubleValue();
				
	        MemberMerchantKey merchantKey = keyList.get(0);
			
			String orderCode = CommonUtil.getOrderCode();
			
			JSONObject reqData = new JSONObject();
			reqData.put("AppKey", merchantCode.getWxMerchantCode());
			reqData.put("OrderNum", orderCode);
			reqData.put("Amount", new DecimalFormat("0.00").format(amount));
			reqData.put("Account_no", draw.getBankAccount());//收款账号
			reqData.put("Account_name", draw.getAccountName());//开户人
			reqData.put("Bank_general_name", draw.getBankName());
			reqData.put("Bank_name", draw.getSubName());
			reqData.put("Bank_code", draw.getSubId());
			reqData.put("Code", draw.getBankCode());
			
			
			String srcStr = StringUtil.orderedKey(reqData);
			String privateKey = merchantKey.getPrivateKey();
			String signData = EpaySignUtil.signSha1(privateKey, srcStr);
			reqData.put("SignStr", signData);
			
			logger.info("瑞付提现请求报文[{}]", new Object[] { JSON.toJSONString(reqData) });
			
			String url = RFConfig.msServerUrl + "/PayNew";
			
			String resultMsg = HttpUtil.sendPostRequest(url, reqData.toString());
			logger.info("瑞付提现返回报文[{}]", new Object[] { resultMsg });
			
			JSONObject resultObj = JSONObject.fromObject(resultMsg);
	        String result_code = resultObj.getString("ReturnCode");
	        String result_msg = resultObj.getString("ReturnMsg");
			
			String reqDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			
			boolean signCheck = true;
	        if("0000".equals(result_code)){
	        	String SignStr = resultObj.getString("SignStr");
		        resultObj.remove("SignStr");
		        String rtSrcStr = StringUtil.orderedKey(resultObj);
		        signCheck = EpaySignUtil.checksignSha1(merchantKey.getPublicKey(), rtSrcStr, SignStr);
	        }
			
			if(signCheck && "0000".equals(result_code)){
				result.put("returnCode", "0000");
				result.put("returnMsg", "请求成功");
			}else{
				if(!signCheck){
	        		result_msg = "出参验签失败";
	        	}
				result.put("returnCode", "0001");
				result.put("returnMsg", result_msg);
				result.put("respCode", result_code);
				result.put("respMsg", result_msg);
			}
			result.put("orderCode", orderCode);
			result.put("merchantCode", merchantCode.getWxMerchantCode());
			result.put("reqDate", reqDate);
		}catch(Exception e){
			logger.error(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
		}
		return result;
	}
	
	
	
	
	
	
	@RequestMapping("/bankPay/receivePayNotify")
	public void receivePayNotify(HttpServletRequest request,HttpServletResponse response) {
		String respString = "success";
		MerchantClient merchant = new MerchantClient();
		InputStream is = null;
		try {
			StringBuffer notifyResultStr = new StringBuffer("");
			is = request.getInputStream();
			byte[] b = new byte[1024];
			int len = -1;
			while((len = is.read(b)) != -1){
				notifyResultStr.append(new String(b,0,len,"utf-8"));
			}
			logger.info("receivePayNotify回调通知返回报文[{}]",  notifyResultStr );
			
			ReceivePayResponse pnResponse = merchant
					.parseReceivePayNotify(notifyResultStr.toString());
			
			
			logger.info("receivePayNotify解密回调通知报文[{}]",  pnResponse );
			
			String orderCode = pnResponse.getTranId();
			if(orderCode != null && !"".equals(orderCode)){
				RoutewayDrawExample routewayDrawExample = new RoutewayDrawExample();
				routewayDrawExample.createCriteria().andOrderCodeEqualTo(orderCode).andDelFlagEqualTo("0");
				List<RoutewayDraw> drawList = routewayDrawService.selectByExample(routewayDrawExample);
				if(drawList==null || drawList.size()!=1){
					return;
				}
				RoutewayDraw draw = drawList.get(0);
				List<ReceivePay> list =  pnResponse.getTranList();
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
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		try {
			response.getWriter().write(respString);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
	
	
	@ResponseBody
	@RequestMapping("/bankPay/agentPay")
	public JSONObject toAgentPay(HttpServletRequest request,HttpServletResponse response){
		String accountName = request.getParameter("accountName");
		String bankAccount = request.getParameter("bankAccount");
		String bankName = request.getParameter("bankName");
		String certNo = request.getParameter("certNo");
		String memberCode = request.getParameter("memberCode");
		String orderNum = request.getParameter("orderNum");
		String payMoney = request.getParameter("payMoney");
		String tel = request.getParameter("tel");
		
		
		String signStr = request.getParameter("signStr");
		
		StringBuilder srcStr = new StringBuilder();
		JSONObject result = new JSONObject();
		
		if(accountName == null || "".equals(accountName)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "账户名称缺失");
			return signReturn(result);
		}
		srcStr.append("accountName="+accountName);
		
		if(bankAccount == null || "".equals(bankAccount)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "银行卡号缺失");
			return signReturn(result);
		}
		srcStr.append("&bankAccount="+bankAccount);
		
		if(bankName == null || "".equals(bankName)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "银行名称缺失");
			return signReturn(result);
		}
		srcStr.append("&bankName="+bankName);
		
		if(certNo == null || "".equals(certNo)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "身份证号码缺失");
			return signReturn(result);
		}
		srcStr.append("&certNo="+certNo);
		
		if(memberCode == null || "".equals(memberCode)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "商户号缺失");
			return signReturn(result);
		}
		srcStr.append("&memberCode="+memberCode);
		
		if(orderNum == null || "".equals(orderNum)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "商户订单号缺失");
			return signReturn(result);
		}
		if(orderNum.length() > 32){
			result.put("returnCode", "0007");
			result.put("returnMsg", "订单号长度不能超过32位");
			return signReturn(result);
		}
		srcStr.append("&orderNum="+orderNum);
		
		if(null == payMoney || !ValidateUtil.isDoubleT(payMoney) || Double.parseDouble(payMoney)<=0){
			result.put("returnCode", "0007");
			result.put("returnMsg", "支付金额输入不正确");
			return signReturn(result);
		}
		srcStr.append("&payMoney="+payMoney);
		
		if(tel == null || "".equals(tel)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "手机号缺失");
			return signReturn(result);
		}
		srcStr.append("&tel="+tel);
		
		if(signStr == null || "".equals(signStr)){ 
			result.put("returnCode", "0007");
			result.put("returnMsg", "缺少签名信息");
			return signReturn(result);
		}
		result = validMemberInfoForAgentPay(memberCode, orderNum, payMoney, bankName,accountName,bankAccount,certNo,tel,  srcStr.toString(), signStr);
		
		return signReturn(result);
	}
	
	
	public JSONObject validMemberInfoForAgentPay(String memberCode,String orderNum,String payMoney,String bankName,String accountName,String bankAccount,String certNo ,String tel,String signOrginalStr,String signedStr){
		JSONObject result = new JSONObject();
		SimpleDateFormat dateFormatLong = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat dateFormatShort = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		try{
			MemberInfoExample memberInfoExample = new MemberInfoExample();
			memberInfoExample.or().andCodeEqualTo(memberCode).andDelFlagEqualTo("0");
			List<MemberInfo> memberInfoList = memberInfoService.selectByExample(memberInfoExample);
			if(null == memberInfoList || memberInfoList.size() == 0){
				result.put("returnCode", "0001");
				result.put("returnMsg", "商户信息不存在");
				return result;
			}
			MemberInfo memberInfo = memberInfoList.get(0);
			if(!"0".equals(memberInfo.getStatus())){
				if("4".equals(memberInfo.getStatus())){
					result.put("returnCode", "0008");
					result.put("returnMsg", "该商户未进行认证，暂时无法交易");
					return result;
				}
				result.put("returnCode", "0008");
				result.put("returnMsg", "对不起，该商户暂不可用");
				return result;
			}
			
			AccountExample accountExample = new AccountExample();
			accountExample.createCriteria().andMemberIdEqualTo(memberInfo.getId()).andDelFlagEqualTo("0");
			List<Account> accounts = accountService.selectByExample(accountExample);
			if (accounts == null || accounts.size() != 1) {
				result.put("returnCode", "0008");
				result.put("returnMsg", "会员账户不存在");
				return result;
			}
			
			EpayCodeExample epayCodeExample = new EpayCodeExample();
			List<String> values = new ArrayList<String>();
			values.add("5");
			values.add("7");
			epayCodeExample.or().andMemberIdEqualTo(memberInfo.getId()).andStatusIn(values);
			List<EpayCode> epayCodeList = epayCodeService.selectByExample(epayCodeExample);
			if(null == epayCodeList || epayCodeList.size() == 0){
				result.put("returnCode", "0008");
				result.put("returnMsg", "对不起，该商户暂不可用");
				return result;
			}
			
			
			MemberMerchantCodeExample memberMerchantCodeExample = new MemberMerchantCodeExample();
			memberMerchantCodeExample.createCriteria().andMemberIdEqualTo(memberInfo.getId()).andRouteCodeEqualTo(RouteCodeConstant.SLF_ROUTE_CODE).andDelFlagEqualTo("0");
			List<MemberMerchantCode> merchantCodes = memberMerchantCodeService.selectByExample(memberMerchantCodeExample);
			if (merchantCodes == null || merchantCodes.size() != 1) {
				result.put("returnCode", "0008");
				result.put("returnMsg", "对不起，商户编码不存在");
				return result;
			}
			
			MemberMerchantCode merchantCode = merchantCodes.get(0);
			
			SysOfficeExample sysOfficeExample = new SysOfficeExample();
			sysOfficeExample.or().andIdEqualTo(epayCodeList.get(0).getOfficeId()).andAgtTypeEqualTo("3");
			List<SysOffice> sysOfficeList = sysOfficeService.selectByExample(sysOfficeExample);
			if(null == sysOfficeList || sysOfficeList.size() == 0){
				result.put("returnCode", "0008");
				result.put("returnMsg", "该商户暂未支持收银台功能，请确认后重试");
				return result;
			}
			
			
			RoutewayDrawExample routeWayDrawExample = new RoutewayDrawExample();
			routeWayDrawExample.createCriteria().andOrderNumOuterEqualTo(orderNum).andDelFlagEqualTo("0");
			List<RoutewayDraw> routeWayDrawList = routewayDrawService.selectByExample(routeWayDrawExample);
			if(null != routeWayDrawList && routeWayDrawList.size() > 0){
				result.put("returnCode", "0002");
				result.put("returnMsg", "该订单号已存在，请勿重复支付");
				return result;
			}
			
			MemberMerchantKeyExample memberMerchantKeyExample = new MemberMerchantKeyExample();
	        memberMerchantKeyExample.createCriteria().andRouteCodeEqualTo(RouteCodeConstant.SLF_ROUTE_CODE).andMerchantCodeEqualTo(merchantCode.getWxMerchantCode()).andDelFlagEqualTo("0");
	        List<MemberMerchantKey> keyList = memberMerchantKeyService.selectByExample(memberMerchantKeyExample);
	        if(keyList == null || keyList.size()!=1){
	            result.put("returnCode", "0008");
				result.put("returnMsg", "商户私钥未配置");
				return result;
	        }
	        MemberMerchantKey merchantKey = keyList.get(0);
			
			SysOffice sysOffice = sysOfficeList.get(0);
//			String singedStr = EpaySignUtil.sign(sysOffice.getPrivateKeyRsa(), signOrginalStr);
			System.out.println(signedStr); 
			System.out.println(signOrginalStr);
			if(!EpaySignUtil.checksign(sysOffice.getPublicKeyRsa(), signOrginalStr, signedStr)){//by linxf 测试屏蔽
				result.put("returnCode", "0004");
				result.put("returnMsg", "签名校验错误，请检查签名参数是否正确");
				return result;
			}
		
			
			double tradeRate = 0 , drawFee = 0;
			if("0".equals(memberInfo.getSettleType())){
				tradeRate = merchantCode.getT0TradeRate().doubleValue();
				drawFee = merchantCode.getT0DrawFee().doubleValue();
			}else{
				tradeRate = merchantCode.getT1TradeRate().doubleValue();
				drawFee = merchantCode.getT1DrawFee().doubleValue();
			}
			
			if(Double.parseDouble(payMoney)<=drawFee){
				result.put("returnCode", "0005");
				result.put("returnMsg", "代付金额必须大于代付手续费");
				return result;
			}
			
			// 今天开始时间
			Date begin = dateFormatLong.parse(dateFormatShort.format(new Date()) + " 00:00:00");
			// 今天结束时间
			Date end = dateFormatLong.parse(dateFormatShort.format(new Date()) + " 23:59:59");
						
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("memberId", memberInfo.getId());
			paramMap.put("respType", "S");
			//当天成功提现金额
			paramMap.put("respDate", df.format(new Date()));
			Double drawMoneyCountToday = commonService.countDrawMoneyByCondition(paramMap);
			drawMoneyCountToday = drawMoneyCountToday == null ? 0 : drawMoneyCountToday;
			
			paramMap = new HashMap<String, Object>();
			paramMap.put("memberId", memberInfo.getId());
			//待审核提现金额
			paramMap.put("auditStatus", "1");
			Double waitAuditMoneyCountAll = commonService.countMoneyByCondition(paramMap);
			waitAuditMoneyCountAll = waitAuditMoneyCountAll == null ? 0 : waitAuditMoneyCountAll;
			//提现中的金额
			paramMap.put("auditStatus", "2");
			paramMap.put("respType", "R");
			Double ingMoneyCountAll = commonService.countMoneyByCondition(paramMap);
			ingMoneyCountAll = ingMoneyCountAll == null ? 0 : ingMoneyCountAll;
			//当天提现失败的金额
			paramMap.put("auditStatus", "2");
			paramMap.put("respType", "E");
			paramMap.put("respDate", df.format(new Date()));
			Double drawFailMoneyCountToday = commonService.countMoneyByCondition(paramMap);
			drawFailMoneyCountToday = drawFailMoneyCountToday == null ? 0 : drawFailMoneyCountToday;
			
			Double drawPercent = 0.7;//D0 70%
			
			
			Account account = accounts.get(0);
			
			//随乐付当天交易总额
			paramMap = new HashMap<String, Object>();
			paramMap.put("memberId", memberInfo.getId());
			paramMap.put("routeId", RouteCodeConstant.SLF_ROUTE_CODE);
			paramMap.put("startDate", df.format(begin));
			paramMap.put("endDate", df.format(end));
			Double tradeMoneyCountSLF = commonService.countTransactionMoneyByCondition(paramMap);
			tradeMoneyCountSLF = tradeMoneyCountSLF == null ? 0 : tradeMoneyCountSLF;
			
			Double canDrawToday = tradeMoneyCountSLF * drawPercent * (1-tradeRate);//当天可提现的金额
			
			//可提现总额
			Double canDrawMoneyCount = Double.valueOf(new DecimalFormat("#.00").format(account.getBalance().doubleValue() + canDrawToday - drawMoneyCountToday - waitAuditMoneyCountAll - ingMoneyCountAll - drawFailMoneyCountToday));
			
			if(Double.parseDouble(payMoney)>canDrawMoneyCount){
				result.put("returnCode", "0005");
				result.put("returnMsg", "代付金额大于可代付金额");
				return result;
			}
			
			String orderCode = CommonUtil.getOrderCode();
			String callBack = SysConfig.serverUrl + "/bankPay/receivePayNotify";
			
			String reqDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			
			RoutewayDraw routewayDraw=new RoutewayDraw();
			routewayDraw.setCreateDate(new Date());
			routewayDraw.setDelFlag("0");
			routewayDraw.setMemberCode(memberInfo.getCode());
			routewayDraw.setMemberId(memberInfo.getId());
			routewayDraw.setMerchantCode(merchantCode.getWxMerchantCode());
			routewayDraw.setDrawType("2");
			routewayDraw.setMoney(new BigDecimal(payMoney));
			routewayDraw.setOrderCode(orderCode);
			routewayDraw.setPtSerialNo(orderCode);
			routewayDraw.setOrderNumOuter(orderNum);
			routewayDraw.setDrawamount(new BigDecimal(Double.parseDouble(payMoney)-drawFee));
			routewayDraw.setDrawfee(new BigDecimal(drawFee));
			routewayDraw.setTradefee(new BigDecimal(0));
			routewayDraw.setReqDate(reqDate);
			routewayDraw.setAuditStatus("2");
			routewayDraw.setAuditDate(new Date());
			routewayDraw.setBankName(bankName);
			routewayDraw.setBankAccount(bankAccount);
			routewayDraw.setAccountName(accountName);
			routewayDraw.setCertNo(certNo);
			routewayDraw.setTel(tel);
			
			
			ReceivePayRequest receivePayRequest = new ReceivePayRequest();
			receivePayRequest.setApplication("ReceivePay");
			receivePayRequest.setVersion("1.0.1");
			receivePayRequest.setMerchantId(merchantCode.getWxMerchantCode());
			receivePayRequest.setTranId(orderCode);
			receivePayRequest.setReceivePayNotifyUrl(callBack); 
			receivePayRequest.setReceivePayType("1");//0 收款，1 付款
			receivePayRequest.setTimestamp(reqDate);
			receivePayRequest.setAccountProp("0");//0 对私，4 对公
			receivePayRequest.setBankGeneralName(bankName);
			receivePayRequest.setAccNo(bankAccount);
			receivePayRequest.setAccName(accountName);
			receivePayRequest.setAmount(String.valueOf((int)(((new BigDecimal(payMoney)).floatValue()-1)*100)));
			receivePayRequest.setCredentialType("01");//证件类型  01 身份证，对私必填
	
			receivePayRequest.setCredentialNo(certNo);
			receivePayRequest.setTel(tel);
			//receivePayRequest.setSummary(new String(request.getParameter("summary").getBytes("ISO8859-1"),"utf-8"));
			
			MerchantClient client = new MerchantClient(merchantCode.getWxMerchantCode());
			String merchantCertPath = MerchantClient.configPath + merchantKey.getPrivateKey();
			SecurityUtil.merchantCertPath = merchantCertPath;
			SecurityUtil.init(merchantCertPath,merchantKey.getPrivateKeyPassword());
			System.out.println(receivePayRequest.toString());
			//ReceivePayResponse receivePayResponse = new ReceivePayResponse();
			//receivePayResponse.setRespCode("000");
			ReceivePayResponse receivePayResponse = client.receivePayRequest(receivePayRequest);
			System.out.println(receivePayResponse.toString());
			if("000".equals(receivePayResponse.getRespCode())){
				routewayDraw.setRespType("R");
				result.put("returnCode", "0000");
				result.put("returnMsg", "提交成功");
			}else{
				routewayDraw.setRespType("E");
				routewayDraw.setRespCode(receivePayResponse.getRespCode());
				routewayDraw.setRespMsg(receivePayResponse.getRespDesc());
				result.put("returnCode", "0003");
				result.put("returnMsg", receivePayResponse.getRespDesc());
			}
			
			routewayDrawService.insertSelective(routewayDraw);
			

		}catch(Exception e){
			logger.info(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
		}
		return result;
	}
	
	
	
	@ResponseBody
	@RequestMapping("/bankPay/queryAgentPayResult")
	public JSONObject queryAgentPayResult(HttpServletRequest request,HttpServletResponse response) {
		JSONObject result = new JSONObject();
		try {
			String orderNum = request.getParameter("orderNum");
			String memberCode = request.getParameter("memberCode");
			String signStr = request.getParameter("signStr");
			
			StringBuilder srcStr = new StringBuilder();
			
			if(memberCode == null || "".equals(memberCode)){
				result.put("returnCode", "0007");
				result.put("returnMsg", "商户号缺失");
				return signReturn(result);
			}
			srcStr.append("memberCode="+memberCode);
			if(orderNum == null || "".equals(orderNum)){
				result.put("returnCode", "0007");
				result.put("returnMsg", "商户订单号缺失");
				return signReturn(result);
			}
			if(orderNum.length() > 32){
				result.put("returnCode", "0007");
				result.put("returnMsg", "订单号长度不能超过32位");
				return signReturn(result);
			}
			srcStr.append("&orderNum="+orderNum);
			
			if(signStr == null || "".equals(signStr)){ 
				result.put("returnCode", "0007");
				result.put("returnMsg", "缺少签名信息");
				return signReturn(result);
			}
			MemberInfoExample memberInfoExample = new MemberInfoExample();
			memberInfoExample.or().andCodeEqualTo(memberCode).andDelFlagEqualTo("0");
			List<MemberInfo> memberInfoList = memberInfoService.selectByExample(memberInfoExample);
			if(null == memberInfoList || memberInfoList.size() == 0){
				result.put("returnCode", "0001");
				result.put("returnMsg", "商户信息不存在");
				return signReturn(result);
			}
			MemberInfo memberInfo = memberInfoList.get(0);
			if(!"0".equals(memberInfo.getStatus())){
				if("4".equals(memberInfo.getStatus())){
					result.put("returnCode", "0008");
					result.put("returnMsg", "该商户未进行认证，暂时无法交易");
					return signReturn(result);
				}
				result.put("returnCode", "0008");
				result.put("returnMsg", "对不起，该商户暂不可用");
				return signReturn(result);
			}
			
			EpayCodeExample epayCodeExample = new EpayCodeExample();
			List<String> values = new ArrayList<String>();
			values.add("5");
			values.add("7");
			epayCodeExample.or().andMemberIdEqualTo(memberInfo.getId()).andStatusIn(values);
			List<EpayCode> epayCodeList = epayCodeService.selectByExample(epayCodeExample);
			if(null == epayCodeList || epayCodeList.size() == 0){
				result.put("returnCode", "0008");
				result.put("returnMsg", "对不起，该商户暂不可用");
				return signReturn(result);
			}
			
			
			SysOfficeExample sysOfficeExample = new SysOfficeExample();
			sysOfficeExample.or().andIdEqualTo(epayCodeList.get(0).getOfficeId()).andAgtTypeEqualTo("3");
			List<SysOffice> sysOfficeList = sysOfficeService.selectByExample(sysOfficeExample);
			if(null == sysOfficeList || sysOfficeList.size() == 0){
				result.put("returnCode", "0008");
				result.put("returnMsg", "该商户暂未支持收银台功能，请确认后重试");
				return signReturn(result);
			}
			
			SysOffice sysOffice = sysOfficeList.get(0);
		/*	if(!EpaySignUtil.checksign(sysOffice.getPublicKeyRsa(), srcStr.toString(), signStr)){
				result.put("returnCode", "0004");
				result.put("returnMsg", "签名校验错误，请检查签名参数是否正确");
				return signReturn(result);
			}*/
			
			
			RoutewayDrawExample routewayDrawExample = new RoutewayDrawExample();
			routewayDrawExample.createCriteria().andOrderNumOuterEqualTo(orderNum).andMemberIdEqualTo(memberInfo.getId()).andDelFlagEqualTo("0");
			List<RoutewayDraw> routeWayDrawList = routewayDrawService.selectByExample(routewayDrawExample);
			if(routeWayDrawList==null || routeWayDrawList.size()==0){
				throw new ArgException("代付单不存在!");
			}
			result.put("returnCode", "0000");
			result.put("returnMsg", "查询成功");
			RoutewayDraw draw = routeWayDrawList.get(0);
			String routeCode = draw.getRouteCode();
			if("R".equals(draw.getRespType())){//不确定
				if(RouteCodeConstant.SLF_ROUTE_CODE.equals(routeCode)){
					ReceivePayQueryRequest queryRequest = new ReceivePayQueryRequest();
					queryRequest.setApplication("ReceivePayQuery");
					queryRequest.setVersion("1.0.1");
					queryRequest.setMerchantId(draw.getMerchantCode());
					queryRequest.setTimestamp(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
					queryRequest.setQueryTranId(draw.getOrderCode());
					
					MemberMerchantKeyExample memberMerchantKeyExample = new MemberMerchantKeyExample();
			        memberMerchantKeyExample.createCriteria().andRouteCodeEqualTo(routeCode).andMerchantCodeEqualTo(draw.getMerchantCode()).andDelFlagEqualTo("0");
			        List<MemberMerchantKey> keyList = memberMerchantKeyService.selectByExample(memberMerchantKeyExample);
			        if(keyList == null || keyList.size()!=1){
			        	result.put("returnCode", "0008");
						result.put("returnMsg", "商户私钥未配置");
						return signReturn(result);
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
				}else if(RouteCodeConstant.YZF_ROUTE_CODE.equals(routeCode)){
					
					MemberMerchantKeyExample memberMerchantKeyExample = new MemberMerchantKeyExample();
			        memberMerchantKeyExample.createCriteria().andRouteCodeEqualTo(routeCode).andMerchantCodeEqualTo(draw.getMerchantCode()).andDelFlagEqualTo("0");
			        List<MemberMerchantKey> keyList = memberMerchantKeyService.selectByExample(memberMerchantKeyExample);
			        if(keyList == null || keyList.size()!=1){
			        	result.put("returnCode", "0008");
						result.put("returnMsg", "商户私钥未配置");
						return signReturn(result);
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
					
					logger.info("代付订单查询未加密参数[{}]",contextjson.toString() );
					
					
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
					logger.info("代付订单查询返回报文[{}]", new Object[] { respStr });
					
					JSONObject jsonObj = JSONObject.fromObject(respStr);
					byte[] encryptkey_RES=EncryptUtil.decodeBase64(jsonObj.get("encryptkey").toString());
					
					byte[] key_RES_B=RSATool.decrypt(encryptkey_RES,privateK);
					String aes_res_key=new String (key_RES_B);
					//logger.info("代付联行号查询返回报文解密AES key[{}]", new Object[] { aes_res_key });
					String data_RES=jsonObj.get("data").toString();
					String resData=AESTool.decrypt(data_RES, aes_res_key);
					logger.info("代付订单查询返回报文解密结果[{}]", new Object[] { resData });
					
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
					
					
					
				}
				
			}
			result.put("orderCode", draw.getOrderCode());
			result.put("oriRespType", draw.getRespType());
			if(!"R".equals(draw.getRespType())){
				result.put("oriRespCode", draw.getRespCode());
				result.put("oriRespMsg", draw.getRespMsg());
			}
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			result.put("orderTime", format.format(draw.getCreateDate()));
			
			DecimalFormat dformat = new DecimalFormat("0.00");
			result.put("money",dformat.format(draw.getMoney()));
			result.put("drawAmount",dformat.format(draw.getDrawamount()));
			result.put("drawFee",dformat.format(draw.getDrawfee()));
		} catch (ArgException e) {
			result.put("returnCode", "4004");
			result.put("returnMsg", e.getMessage());
			logger.info(e.getMessage());
			return signReturn(result);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
			return signReturn(result);
		}
		result.put("returnCode", "0000");
		result.put("returnMsg", "成功");
		return signReturn(result);
	}
	
	private JSONObject checkLimitMoney(String configName, BigDecimal tradeMoney){
		
		JSONObject result = new JSONObject();
		
		String value = "";
		SysCommonConfigExample sysCommonConfigExample = new SysCommonConfigExample();
		sysCommonConfigExample.or().andNameEqualTo(configName).andDelFlagEqualTo("0");
		List<SysCommonConfig> sysCommonConfig = sysCommonConfigService.selectByExample(sysCommonConfigExample);
		if (sysCommonConfig != null && sysCommonConfig.size() > 0) {
			value = sysCommonConfig.get(0).getValue();
		}
		if(!"".equals(value)){
			BigDecimal singleLimit = new BigDecimal(value);
			if (null != singleLimit && singleLimit.compareTo(BigDecimal.ZERO) > 0){
				if (tradeMoney.compareTo(singleLimit) > 0) {
					result.put("returnCode", "4004");
					result.put("returnMsg", "单笔交易限额为"+singleLimit+"元,当前交易已超出限额");
					return result;
				}
			}
		}
		return null;
	}
	
	private JSONObject checkMinMoney(String configName, BigDecimal tradeMoney){
		
		JSONObject result = new JSONObject();
		
		String value = "";
		SysCommonConfigExample sysCommonConfigExample = new SysCommonConfigExample();
		sysCommonConfigExample.or().andNameEqualTo(configName).andDelFlagEqualTo("0");
		List<SysCommonConfig> sysCommonConfig = sysCommonConfigService.selectByExample(sysCommonConfigExample);
		if (sysCommonConfig != null && sysCommonConfig.size() > 0) {
			value = sysCommonConfig.get(0).getValue();
		}
		if(!"".equals(value)){
			BigDecimal singleMin = new BigDecimal(value);
			if (null != singleMin && singleMin.compareTo(BigDecimal.ZERO) > 0){
				if (tradeMoney.compareTo(singleMin) < 0) {
					result.put("returnCode", "4004");
					result.put("returnMsg", "单笔交易最小金额为"+singleMin+"元,当前交易已小于最小金额");
					return result;
				}
			}
		}
		return null;
	}
	
	private JSONObject checkLimitCounts(String routeId){
		
		JSONObject result = new JSONObject();
		String configName = "LIMIT_ROUTE_SECONDS_"+routeId;
		String value = "",value1 = "";
		SysCommonConfigExample sysCommonConfigExample = new SysCommonConfigExample();
		sysCommonConfigExample.or().andNameEqualTo(configName).andDelFlagEqualTo("0");
		List<SysCommonConfig> sysCommonConfig = sysCommonConfigService.selectByExample(sysCommonConfigExample);
		if (sysCommonConfig != null && sysCommonConfig.size() > 0) {
			value = sysCommonConfig.get(0).getValue();
		}
		
		configName = "LIMIT_ROUTE_TIMES_"+routeId;
		sysCommonConfigExample = new SysCommonConfigExample();
		sysCommonConfigExample.or().andNameEqualTo(configName).andDelFlagEqualTo("0");
		sysCommonConfig = sysCommonConfigService.selectByExample(sysCommonConfigExample);
		if (sysCommonConfig != null && sysCommonConfig.size() > 0) {
			value1 = sysCommonConfig.get(0).getValue();
		}
		if(!"".equals(value) && !"".equals(value1)){
			
			Map<String,Object> param = new HashMap<String, Object>();
			param.put("routeId", routeId);
			param.put("seconds", value);
			Integer count = debitNoteIpService.countDebitNoteIpByCondition(param);
			count = count == null ? 0 : count;
			
			BigDecimal limitCount = new BigDecimal(value1);
			if (null != limitCount && limitCount.compareTo(BigDecimal.ZERO) > 0){
				if (new BigDecimal(count).compareTo(limitCount) > 0) {
					result.put("returnCode", "4004");
					result.put("returnMsg", "交易过于频繁，已超过商户限制交易次数，请稍后再试");
					return result;
				}
			}
		}
		return null;
	}
	
	private Map<String,String> getRouteCodeAndAisleType(Integer memberId,String payMethod, String payType  ){
		Map<String,String> map = new HashMap<String, String>();
		String routeCode = SysConfig.passCode;
		String aisleType = "";
		PayTypeExample payTypeExample = new PayTypeExample();
		payTypeExample.createCriteria().andMemberIdEqualTo(memberId).andPayMethodEqualTo(payMethod).andPayTypeEqualTo(payType).andDelFlagEqualTo("0");
		List<PayType> payTypeList = payTypeService.selectByExample(payTypeExample);
		if(payTypeList == null || payTypeList.size() == 0){
			payTypeExample = new PayTypeExample();
			payTypeExample.createCriteria().andMemberIdEqualTo(0).andPayMethodEqualTo(payMethod).andPayTypeEqualTo(payType).andDelFlagEqualTo("0");
			payTypeList = payTypeService.selectByExample(payTypeExample);
			
		}
		if(payTypeList != null && payTypeList.size() > 0){
			routeCode = payTypeList.get(0).getRouteCode();
			aisleType = payTypeList.get(0).getAisleType();
		}
		map.put("routeCode", routeCode);
		map.put("aisleType", aisleType);
		return map;
	}
	
	private JSONObject signReturn(JSONObject result){
		String rtSrc = StringUtil.orderedKey(result);
		result.put("signStr", EpaySignUtil.sign(SysConfig.platPrivateKey, rtSrc));
		return result;
	}
}
