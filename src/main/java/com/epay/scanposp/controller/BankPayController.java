package com.epay.scanposp.controller;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.epay.scanposp.common.constant.SLFConfig;
import com.epay.scanposp.common.constant.SysConfig;
import com.epay.scanposp.common.utils.CommonUtil;
import com.epay.scanposp.common.utils.DateUtil;
import com.epay.scanposp.common.utils.ValidateUtil;
import com.epay.scanposp.common.utils.constant.PayTypeConstant;
import com.epay.scanposp.common.utils.constant.RouteCodeConstant;
import com.epay.scanposp.common.utils.epaySecurityUtil.EpaySignUtil;
import com.epay.scanposp.common.utils.slf.MerchantClient;
import com.epay.scanposp.common.utils.slf.SecurityUtil;
import com.epay.scanposp.common.utils.slf.vo.DeductInfo;
import com.epay.scanposp.common.utils.slf.vo.OrderRequest;
import com.epay.scanposp.common.utils.slf.vo.PaymentNotifyResponse;
import com.epay.scanposp.common.utils.swift.SignUtils;
import com.epay.scanposp.entity.Account;
import com.epay.scanposp.entity.AccountExample;
import com.epay.scanposp.entity.DebitNote;
import com.epay.scanposp.entity.DebitNoteExample;
import com.epay.scanposp.entity.EpayCode;
import com.epay.scanposp.entity.EpayCodeExample;
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
import com.epay.scanposp.entity.SysOffice;
import com.epay.scanposp.entity.SysOfficeExample;
import com.epay.scanposp.entity.TradeDetail;
import com.epay.scanposp.entity.TradeDetailExample;
import com.epay.scanposp.service.AccountService;
import com.epay.scanposp.service.DebitNoteService;
import com.epay.scanposp.service.EpayCodeService;
import com.epay.scanposp.service.MemberInfoService;
import com.epay.scanposp.service.MemberMerchantCodeService;
import com.epay.scanposp.service.MemberMerchantKeyService;
import com.epay.scanposp.service.MsResultNoticeService;
import com.epay.scanposp.service.PayResultNoticeService;
import com.epay.scanposp.service.PayTypeService;
import com.epay.scanposp.service.SysOfficeExtendService;
import com.epay.scanposp.service.SysOfficeService;
import com.epay.scanposp.service.TradeDetailService;
import com.epay.scanposp.thread.PayResultNoticeThread;

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
	private TradeDetailService tradeDetailService;
	
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
	
	@ResponseBody
	@RequestMapping("/api/bankPay/toPay")
	public JSONObject getQrcodePay(HttpServletRequest request,HttpServletResponse response){
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		String payMoney = reqDataJson.getString("payMoney");
		String orderNum = reqDataJson.getString("orderNum");
		String memberCode = reqDataJson.getString("memberCode");
		String callbackUrl = reqDataJson.getString("callbackUrl");
	//	String gateWayType = reqDataJson.getString("gateWayType");
	//	String goodsName = reqDataJson.getString("goodsName");
		
		String signStr = reqDataJson.getString("signStr");
		
		StringBuilder srcStr = new StringBuilder();
		JSONObject result = new JSONObject();
		if(null == payMoney || !ValidateUtil.isDoubleT(payMoney) || Double.parseDouble(payMoney)<=0){
			result.put("returnCode", "0005");
			result.put("returnMsg", "支付金额输入不正确");
			return result;
		}
		if(callbackUrl == null || "".equals(callbackUrl)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "平台支付结果通知回调地址缺失");
			return result;
		}
		srcStr.append("callbackUrl="+callbackUrl);
		
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
		
		
		
		result = validMemberInfoForQrcode(memberCode, orderNum, payMoney, "3",  srcStr.toString(), signStr, callbackUrl);
		
		return result;
	}
	
	
	
	public JSONObject validMemberInfoForQrcode(String memberCode,String orderNum,String payMoney,String platformType,String signOrginalStr,String signedStr,String callbackUrl){
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
/*		if(!EpaySignUtil.checksign(sysOffice.getPublicKeyRsa(), signOrginalStr, signedStr)){//by linxf 测试屏蔽
			result.put("returnCode", "0004");
			result.put("returnMsg", "签名校验错误，请检查签名参数是否正确");
			return result;
		}*/
		
		//校验交易额是否超出限制
	    /*
		JSONObject checkResult = checkPayLimit(memberInfo.getId(), new BigDecimal(payMoney), memberInfo.getSingleLimit(), memberInfo.getDayLimit());
		if(null != checkResult){
			return checkResult;
		}
		*/
		
		if(RouteCodeConstant.SLF_ROUTE_CODE.equals(routeCode)){
			result = bankPay(platformType,memberInfo, payMoney, orderNum, callbackUrl , merchantCode );
			result.put("routeCode", routeCode);
		}
		
		return result;
	}
	
	public JSONObject bankPay(String platformType,MemberInfo memberInfo,String payMoney,String orderNumOuter,String callbackUrl,MemberMerchantCode merchantCode) {
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
			debitNote.setRouteId(RouteCodeConstant.FT_ROUTE_CODE);
			debitNote.setStatus("0");
			debitNote.setTxnType("6");
			debitNote.setMemberCode(memberInfo.getWxMemberCode());
			debitNote.setMerchantCode(merchantCode.getWxMerchantCode());
			
			debitNote.setSettleType(memberInfo.getSettleType());
			if("0".equals(memberInfo.getSettleType())){
				debitNote.setTradeRate(merchantCode.getT0TradeRate());
			}else{
				debitNote.setTradeRate(merchantCode.getT1TradeRate());
			}
			
			debitNoteService.insertSelective(debitNote);
			
			PayResultNotice payResultNotice = new PayResultNotice();
			payResultNotice.setOrderCode(debitNote.getOrderCode());
			payResultNotice.setOrderNumOuter(orderNumOuter);
			payResultNotice.setPayMoney(debitNote.getMoney());
			payResultNotice.setMemberCode(memberInfo.getCode());
			payResultNotice.setPayType("6");
			
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
            					}
            					
            					if(payResultNoticeList.size() > 0){
            						//更新状态是触发器可以读取该条数据以执行通知任务
            						payResultNoticeService.updateByPrimaryKeySelective(payResultNotice);
            						//获取民生通知后即向商户提供结果通知 (后续若因其他因素需多次通知商户,则由相应的定时任务完成)
            						System.out.println("getPoolSize====" + threadPoolTaskExecutor.getPoolSize());
            						PayResultNoticeThread payResultNoyiceThread = new PayResultNoticeThread(payResultNoticeService, sysOfficeExtendService, payResultNotice);
            						threadPoolTaskExecutor.execute(payResultNoyiceThread);
            						System.out.println("getActiveCount====" + threadPoolTaskExecutor.getActiveCount());
            						//new Thread(payResultNoyiceThread).start();

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
	

}
