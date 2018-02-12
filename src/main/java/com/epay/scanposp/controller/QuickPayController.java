package com.epay.scanposp.controller;

import java.io.IOException;
import java.math.BigDecimal;
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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.epay.scanposp.common.constant.CJConfig;
import com.epay.scanposp.common.constant.SysConfig;
import com.epay.scanposp.common.constant.YZFConfig;
import com.epay.scanposp.common.excep.ArgException;
import com.epay.scanposp.common.utils.CommonUtil;
import com.epay.scanposp.common.utils.DateUtil;
import com.epay.scanposp.common.utils.ValidateUtil;
import com.epay.scanposp.common.utils.cj.entity.ConsumeRequestEntity;
import com.epay.scanposp.common.utils.cj.entity.ConsumeResponseEntity;
import com.epay.scanposp.common.utils.cj.entity.MessAgeResponseEntity;
import com.epay.scanposp.common.utils.cj.entity.MessageRequestEntity;
import com.epay.scanposp.common.utils.cj.util.Bean2QueryStrUtil;
import com.epay.scanposp.common.utils.cj.util.BeanToMapUtil;
import com.epay.scanposp.common.utils.cj.util.HttpURLConection;
import com.epay.scanposp.common.utils.cj.util.SignatureUtil;
import com.epay.scanposp.common.utils.constant.PayTypeConstant;
import com.epay.scanposp.common.utils.constant.RouteCodeConstant;
import com.epay.scanposp.common.utils.epaySecurityUtil.EpaySignUtil;
import com.epay.scanposp.common.utils.ms.HttpClient4Util;
import com.epay.scanposp.common.utils.ms.MSCommonUtil;
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
import com.epay.scanposp.entity.QuickPaySms;
import com.epay.scanposp.entity.QuickPaySmsExample;
import com.epay.scanposp.entity.SysOffice;
import com.epay.scanposp.entity.SysOfficeExample;
import com.epay.scanposp.entity.TradeDetail;
import com.epay.scanposp.entity.TradeDetailDaily;
import com.epay.scanposp.entity.TradeDetailExample;
import com.epay.scanposp.service.AccountService;
import com.epay.scanposp.service.CommonService;
import com.epay.scanposp.service.CommonUtilService;
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
import com.epay.scanposp.service.QuickPaySmsService;
import com.epay.scanposp.service.RoutewayDrawService;
import com.epay.scanposp.service.SysCommonConfigService;
import com.epay.scanposp.service.SysOfficeExtendService;
import com.epay.scanposp.service.SysOfficeService;
import com.epay.scanposp.service.TradeDetailDailyService;
import com.epay.scanposp.service.TradeDetailService;
import com.google.gson.Gson;
@Controller
public class QuickPayController {
	
	private static Logger logger = LoggerFactory.getLogger(QuickPayController.class);
	
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
	
	@Autowired
	private CommonUtilService commonUtilService;
	
	@Autowired
	private QuickPaySmsService quickPaySmsService;
	
	@Resource
	private TradeDetailService tradeDetailService;

	
	
	@ResponseBody
	@RequestMapping("/quickPay/sendSms")
	public JSONObject sendSms(HttpServletRequest request,HttpServletResponse response){
		String accountName = request.getParameter("accountName");
		String accountType = request.getParameter("accountType");
		String bankAccount = request.getParameter("bankAccount");
		String bankCvv = request.getParameter("bankCvv");
		String bankYxq = request.getParameter("bankYxq");
		String callbackUrl = request.getParameter("callbackUrl");
		//String certNo = request.getParameter("certNo");
		String goodsName = request.getParameter("goodsName");
		String memberCode = request.getParameter("memberCode");
		String orderNum = request.getParameter("orderNum");
		String payMoney = request.getParameter("payMoney");
		String tel = request.getParameter("tel");
		String signStr = request.getParameter("signStr");
		Map<String,String> obj = new HashMap<String, String>();
		obj.put("accountName", accountName);
		obj.put("accountType", accountType);
		obj.put("bankAccount", bankAccount);
		obj.put("bankCvv", bankCvv);
		obj.put("bankYxq", bankYxq);
		obj.put("callbackUrl", callbackUrl);
		obj.put("goodsName", goodsName);
		obj.put("memberCode", memberCode);
		obj.put("orderNum", orderNum);
		obj.put("payMoney", payMoney);
		obj.put("tel", tel);
		obj.put("signStr", signStr);
		logger.info("快捷支付短信发送接口接收参数[{}]",JSONObject.fromObject(obj).toString() );
		
		StringBuilder srcStr = new StringBuilder();
		JSONObject result = new JSONObject();
		
		if(accountName == null || "".equals(accountName)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "账户名称缺失");
			return CommonUtil.signReturn(result);
		}
		srcStr.append("accountName="+accountName);
		
		if(accountType == null || "".equals(accountType)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "账户类型缺失");
			return CommonUtil.signReturn(result);
		}
		if(!"1".equals(accountType)&&!"2".equals(accountType)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "账户类型不正确");
			return CommonUtil.signReturn(result);
		}
		
		srcStr.append("&accountType="+accountType);
		
		if(bankAccount == null || "".equals(bankAccount)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "银行卡号缺失");
			return CommonUtil.signReturn(result);
		}
		srcStr.append("&bankAccount="+bankAccount);
		
		if("2".equals(accountType)){//信用卡
			if(bankCvv == null || "".equals(bankCvv)){
				result.put("returnCode", "0007");
				result.put("returnMsg", "账户类型为信用卡，卡末三位缺失");
				return CommonUtil.signReturn(result);
			}
			if(bankYxq == null || "".equals(bankYxq)){
				result.put("returnCode", "0007");
				result.put("returnMsg", "账户类型为信用卡，卡有效期缺失");
				return CommonUtil.signReturn(result);
			}
		}
		
		if(bankCvv != null && !"".equals(bankCvv)){
			srcStr.append("&bankCvv="+bankCvv);
		}
		
		if(bankYxq != null && !"".equals(bankYxq)){
			srcStr.append("&bankYxq="+bankYxq);
		}
		
		if(callbackUrl == null || "".equals(callbackUrl)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "支付结果通知回调地址缺失");
			return result;
		}
		srcStr.append("&callbackUrl="+callbackUrl);
		
		if(goodsName != null && !"".equals(goodsName)){
			srcStr.append("&goodsName="+goodsName);
		}
		
		if(memberCode == null || "".equals(memberCode)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "商户号缺失");
			return CommonUtil.signReturn(result);
		}
		srcStr.append("&memberCode="+memberCode);
		
		if(orderNum == null || "".equals(orderNum)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "商户订单号缺失");
			return CommonUtil.signReturn(result);
		}
		if(orderNum.length() > 32){
			result.put("returnCode", "0007");
			result.put("returnMsg", "订单号长度不能超过32位");
			return CommonUtil.signReturn(result);
		}
		srcStr.append("&orderNum="+orderNum);
		
		if(null == payMoney || !ValidateUtil.isDoubleT(payMoney) || Double.parseDouble(payMoney)<=0){
			result.put("returnCode", "0007");
			result.put("returnMsg", "支付金额输入不正确");
			return CommonUtil.signReturn(result);
		}
		srcStr.append("&payMoney="+payMoney);
		
		if(tel == null || "".equals(tel)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "手机号缺失");
			return CommonUtil.signReturn(result);
		}
		srcStr.append("&tel="+tel);
		
		if(signStr == null || "".equals(signStr)){ 
			result.put("returnCode", "0007");
			result.put("returnMsg", "缺少签名信息");
			return CommonUtil.signReturn(result);
		}
		result = validMemberInfoForQuickSms(memberCode, orderNum, payMoney,accountType, accountName,bankAccount,bankCvv,bankYxq, tel,goodsName,callbackUrl,  srcStr.toString(), signStr);
		
		return CommonUtil.signReturn(result);
	}
	
	
	public JSONObject validMemberInfoForQuickSms(String memberCode,String orderNum,String payMoney,String accountType,String accountName,String bankAccount,String bankCvv,String bankYxq,String tel,String goodsName,String callbackUrl,String signOrginalStr,String signedStr){
		JSONObject result = new JSONObject();
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
			
			Map<String,String> rtMap  = commonUtilService.getRouteCodeAndAisleType(memberInfo.getId(),PayTypeConstant.PAY_METHOD_YL,PayTypeConstant.PAY_TYPE_KJ);
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
				result.put("returnMsg", "对不起，该商户未开通快捷支付的权限");
				return result;
			}
			MemberMerchantCode merchantCode = merchantCodes.get(0);
			if (StringUtils.isBlank(merchantCode.getKjMerchantCode())) {
				result.put("returnCode", "0008");
				result.put("returnMsg", "对不起，该商户未开通快捷支付的权限");
				return result;
			}
			
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
			
			System.out.println(signOrginalStr);
			if(!EpaySignUtil.checksign(sysOffice.getPublicKeyRsa(), signOrginalStr, signedStr)){//by linxf 测试屏蔽
				result.put("returnCode", "0004");
				result.put("returnMsg", "签名校验错误，请检查签名参数是否正确");
				return result;
			}
		
			
			if(RouteCodeConstant.CJ_ROUTE_CODE.equals(routeCode)){
				memberInfo.setSettleType("1");
				result = cjQuickPaySms("3",memberInfo,merchantCode,payMoney,orderNum,accountType,accountName,bankAccount,bankCvv,bankYxq,tel,callbackUrl,goodsName,routeCode,aisleType);
			}
		}catch(Exception e){
			logger.info(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
		}
		return result;
	}
	
	/**
	 * 畅捷快捷支付短信发送
	 * @param platformType
	 * @param memberInfo
	 * @param merchantCode
	 * @param payMoney
	 * @param orderNumOuter
	 * @param accountName
	 * @param bankAccount
	 * @param bankCvv
	 * @param bankYxq
	 * @param tel
	 * @param callbackUrl
	 * @param goodsName
	 * @param routeCode
	 * @param aisleType
	 * @return
	 */
	public JSONObject cjQuickPaySms(String platformType,MemberInfo memberInfo,MemberMerchantCode merchantCode,String payMoney,String orderNumOuter,String accountType,String accountName,String bankAccount,String bankCvv,String bankYxq,String tel,String callbackUrl,String goodsName,String routeCode,String aisleType) {
		JSONObject result = new JSONObject();
		try {
			String payType = "9";
			String payTypeStr = PayTypeConstant.PAY_TYPE_KJ;
			String payMethod = PayTypeConstant.PAY_METHOD_YL;
			String merCode = merchantCode.getKjMerchantCode();
			
			
			MemberMerchantKeyExample memberMerchantKeyExample = new MemberMerchantKeyExample();
	        memberMerchantKeyExample.createCriteria().andRouteCodeEqualTo(routeCode).andMerchantCodeEqualTo(merCode).andDelFlagEqualTo("0");
	        List<MemberMerchantKey> keyList = memberMerchantKeyService.selectByExample(memberMerchantKeyExample);
	        if(keyList == null || keyList.size()!=1){
	            result.put("returnCode", "0008");
				result.put("returnMsg", "商户私钥未配置");
				return result;
	        }
	        MemberMerchantKey merchantKey = keyList.get(0);
			// 插入一条收款记录
			String orderCode = CommonUtil.getOrderCode();
			
			QuickPaySms quickPaySms = new QuickPaySms();
			quickPaySms.setCreateDate(new Date());
			quickPaySms.setMemberId(memberInfo.getId());
			quickPaySms.setMoney(new BigDecimal(payMoney));
			quickPaySms.setOrderCode(orderCode);
			quickPaySms.setOrderNumOuter(orderNumOuter);
			quickPaySms.setRouteId(routeCode);
			quickPaySms.setStatus("0");
			quickPaySms.setTxnMethod(payMethod);
			quickPaySms.setTxnType(payType);
			quickPaySms.setMerchantCode(merCode);
			quickPaySms.setMemberCode(memberInfo.getWxMemberCode());
			quickPaySms.setSettleType(memberInfo.getSettleType());
			if("0".equals(memberInfo.getSettleType())){
				quickPaySms.setTradeRate(merchantCode.getKjT0TradeRate());
			}else{
				quickPaySms.setTradeRate(merchantCode.getKjT1TradeRate());
			}
			quickPaySms.setAccountType(accountType);
			quickPaySms.setBankAccount(bankAccount);
			quickPaySms.setAccountName(accountName);
			quickPaySms.setTel(tel);
			quickPaySms.setReturnUrl(callbackUrl);
			
			String configName = "SINGLE_MEMBER_LIMIT_"+memberInfo.getId()+"_"+payMethod+"_"+payTypeStr;
			JSONObject memResult = commonUtilService.checkLimitMoney(configName, new BigDecimal(payMoney));
			if(null != memResult){
				quickPaySms.setStatus("9");
				quickPaySmsService.insertSelective(quickPaySms);
				return memResult;
			} 
			
			configName = "SINGLE_MIN_"+routeCode+"_"+payMethod+"_"+payTypeStr;
			JSONObject checkResult = commonUtilService.checkMinMoney(configName, new BigDecimal(payMoney));
			if(null != checkResult){
				quickPaySms.setStatus("5");
				quickPaySmsService.insertSelective(quickPaySms);
				return checkResult;
			}
			
			configName = "SINGLE_LIMIT_"+routeCode+"_"+payMethod+"_"+payTypeStr;
			JSONObject limitResult = commonUtilService.checkLimitMoney(configName, new BigDecimal(payMoney));
			if(null != limitResult){
				quickPaySms.setStatus("4");
				quickPaySmsService.insertSelective(quickPaySms);
				return limitResult;
			}
			
			quickPaySmsService.insertSelective(quickPaySms);
			
			String callBack = SysConfig.serverUrl + "/quickPay/cjPayNotify";
			String serverUrl = CJConfig.msServerUrl+"/quickPayAction/message.action";
			String version = "1.0.0.0";	
			if(StringUtils.isBlank(goodsName)){
				goodsName = memberInfo.getName()+" 收款";
			}
			
			MessageRequestEntity reqEntity = new MessageRequestEntity();
			reqEntity.setV_version(version);
			reqEntity.setV_mid(merCode);
			reqEntity.setV_oid(orderCode);
			reqEntity.setV_time(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
			reqEntity.setV_txnAmt(payMoney);
			reqEntity.setV_realName(accountName);
			reqEntity.setV_cardNo(bankAccount);
			reqEntity.setV_accountType(accountType);
			reqEntity.setV_type("1");//D0:0    T1：1
			reqEntity.setV_productDesc(goodsName);
			reqEntity.setV_phone(tel);
			reqEntity.setV_notify_url(callBack);
			reqEntity.setV_url(CJConfig.frontUrl+"?orderCode="+orderCode);
			reqEntity.setV_cvn2(bankCvv);
			reqEntity.setV_expired(bankYxq);
			reqEntity.setV_attach(goodsName);
			reqEntity.setV_userFee("0.3");//先写死
			String sign = SignatureUtil.getSign(CommonUtil.beanToMap(reqEntity), merchantKey.getPrivateKey(), logger);
			reqEntity.setV_sign(sign);
			Bean2QueryStrUtil bean = new Bean2QueryStrUtil();
			String postStr = bean.bean2QueryStr(reqEntity);
			logger.info("畅捷快捷支付短信发送请求参数[{}]",postStr );
			String respStr = HttpURLConection.httpURLConnectionPOST(serverUrl, postStr);
			logger.info("畅捷快捷支付短信发送返回报文[{}]", new Object[] { respStr });
			String resultMsg = "";
			String code = "";
			boolean flag = false;
			if(StringUtils.isNotEmpty(respStr)){
				Gson gson = new Gson();
				MessAgeResponseEntity respEntity = gson.fromJson(respStr, MessAgeResponseEntity.class);
				code = respEntity.getV_code();
				Map map = BeanToMapUtil.convertBean(respEntity);
				if("00".equals(code)){
					if(SignatureUtil.checkSign(map, merchantKey.getPrivateKey(), logger)) {
						result.put("returnCode", "0000");
						result.put("returnMsg", "成功");
						flag = true;
					}else{
						resultMsg = "出参验签失败";
					}
				}else{
					resultMsg = respEntity.getV_msg();
				}
			}else{
				resultMsg = "接口调用失败";
			}
			if(!flag)
			{
				result.put("returnCode", "0003");
				result.put("returnMsg", resultMsg);
				
				QuickPaySmsExample quickPaySmsExample = new QuickPaySmsExample();
				quickPaySmsExample.createCriteria().andOrderCodeEqualTo(orderCode);
				List<QuickPaySms> quickPays = quickPaySmsService.selectByExample(quickPaySmsExample);
				if (quickPays != null && quickPays.size() > 0) {
					QuickPaySms quickPay_1 = quickPays.get(0);
					quickPay_1.setStatus("2");
					quickPay_1.setUpdateDate(new Date());
					quickPay_1.setRespCode(code);
					if(!"".equals(resultMsg)){
						quickPay_1.setRespMsg(resultMsg.length()>250?resultMsg.substring(0, 250):resultMsg);
					}
					quickPaySmsService.updateByPrimaryKey(quickPay_1);
				}
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
			return result;
		}
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping("/api/quickPay/toPay")
	public JSONObject toPay(HttpServletRequest request,HttpServletResponse response){
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		System.out.println("请求参数==="+reqDataJson.toString());
		String orderNum = "";
		if(reqDataJson.containsKey("orderNum")){
			orderNum = reqDataJson.getString("orderNum");
		}
		String memberCode = "";
		if(reqDataJson.containsKey("memberCode")){
			memberCode = reqDataJson.getString("memberCode");
		}
		String smsCode = "";
		if(reqDataJson.containsKey("smsCode")){
			smsCode = reqDataJson.getString("smsCode");
		}
		
		String signStr = "";
		if(reqDataJson.containsKey("signStr")){
			signStr = reqDataJson.getString("signStr");
		}
		
		StringBuilder srcStr = new StringBuilder();
		JSONObject result = new JSONObject();
		
		if(memberCode == null || "".equals(memberCode)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "商户号缺失");
			return result;
		}
		srcStr.append("memberCode="+memberCode);
		
		if(orderNum == null || "".equals(orderNum)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "商户订单号缺失");
			return result;
		}
		if(orderNum.length() > 32){
			result.put("returnCode", "0007");
			result.put("returnMsg", "订单号长度不能超过32位");
			return result;
		}
		srcStr.append("&orderNum="+orderNum);
		
		if(smsCode == null || "".equals(smsCode)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "短信验证码缺失");
			return result;
		}
		srcStr.append("&smsCode="+smsCode);
		
		if(signStr == null || "".equals(signStr)){ 
			result.put("returnCode", "0007");
			result.put("returnMsg", "缺少签名信息");
			return result;
		}
		
		
		
		result = validMemberInfoToQuickPay(memberCode, orderNum,smsCode, srcStr.toString(), signStr);
		
		return result;
	}
	
	public JSONObject validMemberInfoToQuickPay(String memberCode,String orderNum,String smsCode,String signOrginalStr,String signedStr){
		JSONObject result = new JSONObject();
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
			
			QuickPaySmsExample quickPaySmsExample = new QuickPaySmsExample();
			quickPaySmsExample.createCriteria().andOrderNumOuterEqualTo(orderNum).andStatusEqualTo("0");
			quickPaySmsExample.setOrderByClause(" create_date desc ");
			List<QuickPaySms> quickPaySmsList = quickPaySmsService.selectByExample(quickPaySmsExample);
			if(quickPaySmsList == null || quickPaySmsList.size()==0){
				result.put("returnCode", "0002");
				result.put("returnMsg", "订单号不存在");
				return result;
			}
			
			QuickPaySms quickPaySms = quickPaySmsList.get(0);
			String routeCode = quickPaySms.getRouteId();
			
			SysOffice sysOffice = sysOfficeList.get(0);
			if(!EpaySignUtil.checksign(sysOffice.getPublicKeyRsa(), signOrginalStr, signedStr)){//by linxf 测试屏蔽
				result.put("returnCode", "0004");
				result.put("returnMsg", "签名校验错误，请检查签名参数是否正确");
				return result;
			}
		
			
			if(RouteCodeConstant.CJ_ROUTE_CODE.equals(routeCode)){
				result = cjQuickPay("3",memberInfo,quickPaySms,smsCode);
			}
		}catch(Exception e){
			logger.info(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
		}
		return result;
	}
	
	public JSONObject cjQuickPay(String platformType,MemberInfo memberInfo,QuickPaySms quickPaySms,String smsCode) {
		JSONObject result = new JSONObject();
		try {
			String merCode = quickPaySms.getMerchantCode();
			String routeCode = quickPaySms.getRouteId();
			
			MemberMerchantKeyExample memberMerchantKeyExample = new MemberMerchantKeyExample();
	        memberMerchantKeyExample.createCriteria().andRouteCodeEqualTo(routeCode).andMerchantCodeEqualTo(merCode).andDelFlagEqualTo("0");
	        List<MemberMerchantKey> keyList = memberMerchantKeyService.selectByExample(memberMerchantKeyExample);
	        if(keyList == null || keyList.size()!=1){
	            result.put("returnCode", "0008");
				result.put("returnMsg", "商户私钥未配置");
				return result;
	        }
	        MemberMerchantKey merchantKey = keyList.get(0);
	        
	        
	        DebitNote debitNote = new DebitNote();
			debitNote.setCreateDate(new Date());
			debitNote.setMemberId(quickPaySms.getMemberId());
			debitNote.setMoney(quickPaySms.getMoney());
			debitNote.setOrderCode(quickPaySms.getOrderCode());
			debitNote.setOrderNumOuter(quickPaySms.getOrderNumOuter());
			debitNote.setRouteId(routeCode);
			debitNote.setStatus("0");
			debitNote.setTxnMethod(quickPaySms.getTxnMethod());
			debitNote.setTxnType(quickPaySms.getTxnType());
			debitNote.setMerchantCode(merCode);
			debitNote.setMemberCode(quickPaySms.getMemberCode());
			debitNote.setSettleType(quickPaySms.getSettleType());
			debitNote.setTradeRate(quickPaySms.getTradeRate());
			
			debitNoteService.insertSelective(debitNote);
			
			PayResultNotice payResultNotice = new PayResultNotice();
			payResultNotice.setOrderCode(debitNote.getOrderCode());
			payResultNotice.setOrderNumOuter(debitNote.getOrderNumOuter());
			payResultNotice.setPayMoney(debitNote.getMoney());
			payResultNotice.setMemberCode(memberInfo.getCode());
			payResultNotice.setPayType(quickPaySms.getTxnType());
			payResultNotice.setReturnUrl(quickPaySms.getReturnUrl());
			payResultNotice.setStatus("1");
			payResultNotice.setCreateDate(new Date());
			payResultNotice.setPlatformType(platformType);
			if("3".equals(platformType)){
				payResultNotice.setInterfaceType("2");
			}else{
				payResultNotice.setInterfaceType("1");
			}
			payResultNoticeService.insertSelective(payResultNotice);
	        
	        String reqTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			ConsumeRequestEntity consume = new ConsumeRequestEntity();
			consume.setV_version("1.0.0.0");
			consume.setV_mid(merCode);
			consume.setV_oid(quickPaySms.getOrderCode());
			consume.setV_smsCode(smsCode);
			consume.setV_time(reqTime);
			consume.setV_type("1");
			String sign = SignatureUtil.getSign(CommonUtil.beanToMap(consume), merchantKey.getPrivateKey(), logger);
			result.put("version", "1.0.0.0");
			result.put("merchantCode", merCode);
			result.put("orderCode", quickPaySms.getOrderCode());
			result.put("time", reqTime);
			result.put("type", "1");
			result.put("signStr", sign);
			result.put("payUrl", CJConfig.msServerUrl+"/quickPayAction/pay.action");
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
	
	
	
	@RequestMapping("/quickPay/cjPayNotify")
	public void cjPayNotify(HttpServletRequest request,HttpServletResponse response) {
		JSONObject rtObj = new JSONObject();
		try {
			String v_code = request.getParameter("v_code");
			String v_status = request.getParameter("v_status");
			String v_msg = request.getParameter("v_msg");
			String v_txnAmt = request.getParameter("v_txnAmt");
			String v_mid = request.getParameter("v_mid");
			String v_oid = request.getParameter("v_oid");
			String v_attach =  request.getParameter("v_attach");
			String v_sign =  request.getParameter("v_sign");
			
			Map<String,String> rtMap = new HashMap<String, String>();
			rtMap.put("v_code", v_code);
			rtMap.put("v_status", v_status);
			rtMap.put("v_msg", v_msg);
			rtMap.put("v_txnAmt", v_txnAmt);
			rtMap.put("v_mid", v_mid);
			rtMap.put("v_oid", v_oid);
			rtMap.put("v_attach", v_attach);
			rtMap.put("v_sign", v_sign);
			
			logger.info("cjPayNotify回调返回报文[{}]",  JSONObject.fromObject(rtMap).toString() );
			
			String reqMsgId = v_oid;
        	
        	TradeDetailExample tradeDetailExample = new TradeDetailExample();
        	tradeDetailExample.or().andOrderCodeEqualTo(reqMsgId);
        	List<TradeDetail> tradeDetailList = tradeDetailService.selectByExample(tradeDetailExample);
        	if(tradeDetailList!=null && tradeDetailList.size()>0){
        		rtObj.put("success", "true");
        		response.getWriter().write(rtObj.toString());
        		return;
        	}
        	
        	DebitNoteExample debitNoteExample = new DebitNoteExample();
			debitNoteExample.createCriteria().andOrderCodeEqualTo(reqMsgId);
			List<DebitNote> debitNotes_tmp = debitNoteService.selectByExample(debitNoteExample);
			if (debitNotes_tmp == null || debitNotes_tmp.size() == 0) {
				rtObj.put("success", "false");
				logger.info(rtObj.toString()+" 订单不存在");
                response.getWriter().write(rtObj.toString());
        		return;
				
			}
			DebitNote debitNote = debitNotes_tmp.get(0);
        	MemberMerchantKeyExample memberMerchantKeyExample = new MemberMerchantKeyExample();
            memberMerchantKeyExample.createCriteria().andRouteCodeEqualTo(RouteCodeConstant.CJ_ROUTE_CODE).andMerchantCodeEqualTo(debitNote.getMerchantCode()).andDelFlagEqualTo("0");
            List<MemberMerchantKey> keyList = memberMerchantKeyService.selectByExample(memberMerchantKeyExample);
            if(keyList == null || keyList.size()!=1){
            	rtObj.put("success", "false");
        		logger.info(rtObj.toString()+" 商户私钥未配置");
                response.getWriter().write(rtObj.toString());
        		return;
            }
            ConsumeResponseEntity respEntity = new ConsumeResponseEntity();
            respEntity.setV_code(v_code);
            respEntity.setV_status(v_status);
            respEntity.setV_msg(v_msg);
            respEntity.setV_txnAmt(v_txnAmt);
            respEntity.setV_mid(v_mid);
            respEntity.setV_oid(v_oid);
            respEntity.setV_attach(v_attach);
            respEntity.setV_sign(v_sign);
            
            Map map = BeanToMapUtil.convertBean(respEntity);
			if(SignatureUtil.checkSign(map, keyList.get(0).getPrivateKey(), logger)) {
				rtObj.put("success", "false");
				logger.info(rtObj.toString()+" 验证签名不通过");
                response.getWriter().write(rtObj.toString());
        		return;
			}
            
            
        	
        	String result_message = v_msg;
        	MsResultNoticeExample msResultNoticeExample = new MsResultNoticeExample();
			msResultNoticeExample.or().andOrderCodeEqualTo(reqMsgId);
			List<MsResultNotice> msResultNoticeList = msResultNoticeService.selectByExample(msResultNoticeExample);
			if(null == msResultNoticeList || msResultNoticeList.size() == 0){
				if (debitNote != null ) {
					debitNote.setRespCode(v_status);
					debitNote.setRespMsg(result_message);
					
					//新增一条交易明细记录
					TradeDetail tradeDetail=new TradeDetail();
					tradeDetail.setTxnDate(DateUtil.getDateFormat(new Date(), "yyyyMMdd"));
					tradeDetail.setMemberId(debitNote.getMemberId());
					tradeDetail.setMerchantCode(debitNote.getMerchantCode());
					
					tradeDetail.setMemberCode(debitNote.getMemberCode());
					tradeDetail.setMoney(debitNote.getMoney());
					tradeDetail.setPayTime(DateUtil.getDateTimeStr(debitNote.getCreateDate()));
					tradeDetail.setPtSerialNo(debitNote.getOrderCode());
					tradeDetail.setOrderCode(debitNote.getOrderCode());
					tradeDetail.setReqDate(DateUtil.getDateTimeStr(debitNote.getCreateDate()));
					//tradeDetail.setRespCode(respJSONObject.get("respCode").toString());
					tradeDetail.setRespDate(DateUtil.getDateTimeStr(new Date()));
					tradeDetail.setRespMsg(result_message);
					tradeDetail.setRouteId(debitNote.getRouteId());
					tradeDetail.setTxnMethod(debitNote.getTxnMethod());
					tradeDetail.setTxnType(debitNote.getTxnType());
					tradeDetail.setMemberTradeRate(debitNote.getTradeRate());
					tradeDetail.setDelFlag("0");
					tradeDetail.setCreateDate(new Date());
					if ("00".equals(v_code)&&"0000".equals(v_status)) {
						debitNote.setStatus("1");
						tradeDetail.setRespType("S");
						tradeDetail.setRespCode("000000");
					}else if("1002".equals(v_status)){
						debitNote.setStatus("3");
						tradeDetail.setRespType("R");
						tradeDetail.setRespCode(v_status);
					}else{
						debitNote.setStatus("2");
						tradeDetail.setRespType("E");
						tradeDetail.setRespCode(v_status);
					}
					debitNote.setUpdateDate(new Date());
					debitNoteService.updateByPrimaryKey(debitNote);
					
					MsResultNotice msResultNotice = new MsResultNotice();
					msResultNotice.setMoney(new BigDecimal(v_txnAmt));
					msResultNotice.setOrderCode(reqMsgId);
					
					//SimpleDateFormat dateSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					msResultNotice.setRespDate(DateUtil.getDateTimeStr(new Date()));
					msResultNotice.setRespType("");
					msResultNotice.setRespCode(v_status);
					msResultNotice.setRespMsg(result_message);
					msResultNotice.setPayTime(DateUtil.getDateTimeStr(new Date()));
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
						if ("00".equals(v_code)&&"0000".equals(v_status)) {
							payResultNotice.setRespType("2");
							payResultNotice.setResultCode("0000");
							payResultNotice.setResultMessage("交易成功");
						}else if("1002".equals(v_status)){
							payResultNotice.setRespType("1");
							payResultNotice.setResultCode("0009");   
							payResultNotice.setResultMessage("交易正在处理中...");
						}else{
							payResultNotice.setRespType("3");
							payResultNotice.setResultCode("0003");   
							payResultNotice.setResultMessage("交易失败："+result_message);
						}
						
						tradeDetail.setInterfaceType(payResultNotice.getInterfaceType());
						tradeDetail.setPlatformType(payResultNotice.getPlatformType());
						tradeDetail.setOrderNumOuter(payResultNotice.getOrderNumOuter());
					}
					//更改逻辑  只有交易成功的记录才写进交易记录表  单独写在此处防止后面再次更改逻辑会不理解
					if ("00".equals(v_code)&&"0000".equals(v_status)) {
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
						
					}
					
				}
			}	
            
        } catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		try {
			rtObj.put("code", 0);
    		rtObj.put("message", "接收成功");
    		response.getWriter().write(rtObj.toString());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

	}
	
	
	@ResponseBody
	@RequestMapping("/quickPay/toPay")
	public JSONObject toQuickPay(HttpServletRequest request,HttpServletResponse response){
		String accountName = request.getParameter("accountName");
		String bankAccount = request.getParameter("bankAccount");
		String bankCvv = request.getParameter("bankCvv");
		String bankYxq = request.getParameter("bankYxq");
		String callbackUrl = request.getParameter("callbackUrl");
		String certNo = request.getParameter("certNo");
		String goodsName = request.getParameter("goodsName");
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
			return CommonUtil.signReturn(result);
		}
		srcStr.append("accountName="+accountName);
		
		if(bankAccount == null || "".equals(bankAccount)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "银行卡号缺失");
			return CommonUtil.signReturn(result);
		}
		srcStr.append("&bankAccount="+bankAccount);
		
		if(bankCvv != null && !"".equals(bankCvv)){
			srcStr.append("&bankCvv="+bankCvv);
		}
		
		if(bankYxq != null && !"".equals(bankYxq)){
			srcStr.append("&bankYxq="+bankYxq);
		}
		
		if(callbackUrl == null || "".equals(callbackUrl)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "支付结果通知回调地址缺失");
			return result;
		}
		srcStr.append("callbackUrl="+callbackUrl);
		
		if(certNo == null || "".equals(certNo)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "身份证号码缺失");
			return CommonUtil.signReturn(result);
		}
		srcStr.append("&certNo="+certNo);
		
		if(goodsName != null && !"".equals(goodsName)){
			srcStr.append("&goodsName="+goodsName);
		}
		
		if(memberCode == null || "".equals(memberCode)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "商户号缺失");
			return CommonUtil.signReturn(result);
		}
		srcStr.append("&memberCode="+memberCode);
		
		if(orderNum == null || "".equals(orderNum)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "商户订单号缺失");
			return CommonUtil.signReturn(result);
		}
		if(orderNum.length() > 32){
			result.put("returnCode", "0007");
			result.put("returnMsg", "订单号长度不能超过32位");
			return CommonUtil.signReturn(result);
		}
		srcStr.append("&orderNum="+orderNum);
		
		if(null == payMoney || !ValidateUtil.isDoubleT(payMoney) || Double.parseDouble(payMoney)<=0){
			result.put("returnCode", "0007");
			result.put("returnMsg", "支付金额输入不正确");
			return CommonUtil.signReturn(result);
		}
		srcStr.append("&payMoney="+payMoney);
		
		if(tel == null || "".equals(tel)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "手机号缺失");
			return CommonUtil.signReturn(result);
		}
		srcStr.append("&tel="+tel);
		
		if(signStr == null || "".equals(signStr)){ 
			result.put("returnCode", "0007");
			result.put("returnMsg", "缺少签名信息");
			return CommonUtil.signReturn(result);
		}
		result = validMemberInfoForQuickPay(memberCode, orderNum, payMoney, accountName,bankAccount,bankCvv,bankYxq, certNo,tel,goodsName,callbackUrl,  srcStr.toString(), signStr);
		
		return CommonUtil.signReturn(result);
	}
	
	
	public JSONObject validMemberInfoForQuickPay(String memberCode,String orderNum,String payMoney,String accountName,String bankAccount,String bankCvv,String bankYxq,String certNo ,String tel,String goodsName,String callbackUrl,String signOrginalStr,String signedStr){
		JSONObject result = new JSONObject();
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
			
			Map<String,String> rtMap  = commonUtilService.getRouteCodeAndAisleType(memberInfo.getId(),PayTypeConstant.PAY_METHOD_YL,PayTypeConstant.PAY_TYPE_KJ);
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
//			String singedStr = EpaySignUtil.sign(sysOffice.getPrivateKeyRsa(), signOrginalStr);
			System.out.println(signedStr); 
			System.out.println(signOrginalStr);
	/*		if(!EpaySignUtil.checksign(sysOffice.getPublicKeyRsa(), signOrginalStr, signedStr)){//by linxf 测试屏蔽
				result.put("returnCode", "0004");
				result.put("returnMsg", "签名校验错误，请检查签名参数是否正确");
				return result;
			}
		*/
			
			if(RouteCodeConstant.YZF_ROUTE_CODE.equals(routeCode)){
				result = yzfQuickPay("3",memberInfo,merchantCode,payMoney,orderNum,accountName,bankAccount,bankCvv,bankYxq,certNo,tel,callbackUrl,goodsName,routeCode,aisleType);
			}
			

		}catch(Exception e){
			logger.info(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
		}
		return result;
	}
	
	public JSONObject yzfQuickPay(String platformType,MemberInfo memberInfo,MemberMerchantCode merchantCode,String payMoney,String orderNumOuter,String accountName,String bankAccount,String bankCvv,String bankYxq,String certNo,String tel,String callbackUrl,String goodsName,String routeCode,String aisleType) {
		JSONObject result = new JSONObject();
		try {
			String payType = "9";
			String payTypeStr = PayTypeConstant.PAY_TYPE_KJ;
			String payMethod = PayTypeConstant.PAY_METHOD_YL;
			String merCode = merchantCode.getKjMerchantCode();
			
			
			MemberMerchantKeyExample memberMerchantKeyExample = new MemberMerchantKeyExample();
	        memberMerchantKeyExample.createCriteria().andRouteCodeEqualTo(routeCode).andMerchantCodeEqualTo(merCode).andDelFlagEqualTo("0");
	        List<MemberMerchantKey> keyList = memberMerchantKeyService.selectByExample(memberMerchantKeyExample);
	        if(keyList == null || keyList.size()!=1){
	            result.put("returnCode", "0008");
				result.put("returnMsg", "商户私钥未配置");
				return result;
	        }
	        MemberMerchantKey merchantKey = keyList.get(0);
			// 插入一条收款记录
			String orderCode = CommonUtil.getOrderCode();
			
			DebitNote debitNote = new DebitNote();
			debitNote.setCreateDate(new Date());
			debitNote.setMemberId(memberInfo.getId());
			debitNote.setMoney(new BigDecimal(payMoney));
			debitNote.setOrderCode(orderCode);
			debitNote.setOrderNumOuter(orderNumOuter);
			debitNote.setRouteId(routeCode);
			debitNote.setStatus("0");
			debitNote.setTxnMethod(payMethod);
			debitNote.setTxnType(payType);
			debitNote.setMerchantCode(merCode);
			debitNote.setMemberCode(memberInfo.getWxMemberCode());
			debitNote.setSettleType(memberInfo.getSettleType());
			if("0".equals(memberInfo.getSettleType())){
				debitNote.setTradeRate(merchantCode.getKjT0TradeRate());
			}else{
				debitNote.setTradeRate(merchantCode.getKjT1TradeRate());
			}
			
			
			String configName = "SINGLE_MEMBER_LIMIT_"+memberInfo.getId()+"_"+payMethod+"_"+payTypeStr;
			JSONObject memResult = commonUtilService.checkLimitMoney(configName, new BigDecimal(payMoney));
			if(null != memResult){
				debitNote.setStatus("9");
				debitNoteService.insertSelective(debitNote);
				return memResult;
			} 
			
			configName = "SINGLE_MIN_"+routeCode+"_"+payMethod+"_"+payTypeStr;
			JSONObject checkResult = commonUtilService.checkMinMoney(configName, new BigDecimal(payMoney));
			if(null != checkResult){
				debitNote.setStatus("5");
				debitNoteService.insertSelective(debitNote);
				return checkResult;
			}
			
			configName = "SINGLE_LIMIT_"+routeCode+"_"+payMethod+"_"+payTypeStr;
			JSONObject limitResult = commonUtilService.checkLimitMoney(configName, new BigDecimal(payMoney));
			if(null != limitResult){
				debitNote.setStatus("4");
				debitNoteService.insertSelective(debitNote);
				return limitResult;
			}
			
			debitNoteService.insertSelective(debitNote);
			
			PayResultNotice payResultNotice = new PayResultNotice();
			payResultNotice.setOrderCode(debitNote.getOrderCode());
			payResultNotice.setOrderNumOuter(orderNumOuter);
			payResultNotice.setPayMoney(debitNote.getMoney());
			payResultNotice.setMemberCode(memberInfo.getCode());
			payResultNotice.setPayType(payType);
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
			
			String action = "SdkNocardOrderPayNoSms";
			String callBack = SysConfig.serverUrl + "/quickPay/yzfPayNotify";
			
			String serverUrl = YZFConfig.msServerUrl;
			String charset = "UTF-8";
			String version = "2.0";	
			String merNo = merCode;//商户号	
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
	     	
			contextjson.put("linkId",orderCode);//订单流水号
	     	contextjson.put("orderType","10");//订单类型
	     	contextjson.put("amount",String.valueOf((int)(((new BigDecimal(payMoney)).floatValue())*100)));//消费金额
	     	contextjson.put("bankNo",bankAccount);//银行卡号
	     	contextjson.put("bankAccount",accountName);//银行账户
	     	contextjson.put("bankPhone",tel);//绑定手机号码
	     	contextjson.put("bankCert",certNo);//绑定身份证号
	     	contextjson.put("bankCvv",bankCvv);//信用卡后三位
	     	contextjson.put("bankYxq",bankYxq);//信用卡有效期
	     	contextjson.put("notifyUrl",callBack);//异步通知地址
	     	contextjson.put("goodsName",goodsName);//商品名称
	     	
	     	String aesData=AESTool.encrypt(contextjson.toString(),aesKey);
			
			logger.info("快捷支付请求未加密参数[{}]",contextjson.toString() );
			
			
			String data	= aesData;//EncryptUtil.getBase64(aesData);			
	     	
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
			logger.info("快捷支付返回报文[{}]", new Object[] { respStr });
			
			JSONObject jsonObj = JSONObject.fromObject(respStr);
			byte[] encryptkey_RES=EncryptUtil.decodeBase64(jsonObj.get("encryptkey").toString());
			
			byte[] key_RES_B=RSATool.decrypt(encryptkey_RES,privateK);
			String aes_res_key=new String (key_RES_B);
			logger.info("快捷支付返回报文解密AES key[{}]", new Object[] { aes_res_key });
			String data_RES=jsonObj.get("data").toString();
			String resData=AESTool.decrypt(data_RES, aes_res_key);
			logger.info("快捷支付返回报文解密结果[{}]", new Object[] { resData });
			
			JSONObject resObj = JSONObject.fromObject(resData);
			String code = resObj.getString("code");
			
			if("0000".equals(code)||"0100".equals(code)){
				result.put("orderNo", resObj.getString("orderNo"));
				result.put("returnCode", "0000");
				result.put("returnMsg", "成功");
			}else{
				String resultMsg = resObj.getString("msg");
				result.put("returnCode", "0003");
				result.put("returnMsg", resultMsg);
				
				DebitNoteExample debitNoteExample = new DebitNoteExample();
				debitNoteExample.createCriteria().andOrderCodeEqualTo(orderCode);
				List<DebitNote> debitNotes = debitNoteService.selectByExample(debitNoteExample);
				if (debitNotes != null && debitNotes.size() > 0) {
					DebitNote debitNote_1 = debitNotes.get(0);
					debitNote_1.setStatus("2");
					debitNote_1.setUpdateDate(new Date());
					debitNote_1.setRespCode(code);
					if(!"".equals(resultMsg)){
						debitNote_1.setRespMsg(resultMsg.length()>250?resultMsg.substring(0, 250):resultMsg);
					}
					debitNoteService.updateByPrimaryKey(debitNote_1);
				}
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
			return result;
		}
		return result;
	}
	
	
	

	public JSONObject orderQuery(HttpServletRequest request,HttpServletResponse response) {
		JSONObject result = new JSONObject();
		try {
			String orderNum = request.getParameter("orderNum");
			String memberCode = request.getParameter("memberCode");
			String signStr = request.getParameter("signStr");
			
			StringBuilder srcStr = new StringBuilder();
			
			if(memberCode == null || "".equals(memberCode)){
				result.put("returnCode", "0007");
				result.put("returnMsg", "商户号缺失");
				return CommonUtil.signReturn(result);
			}
			srcStr.append("memberCode="+memberCode);
			if(orderNum == null || "".equals(orderNum)){
				result.put("returnCode", "0007");
				result.put("returnMsg", "商户订单号缺失");
				return CommonUtil.signReturn(result);
			}
			if(orderNum.length() > 32){
				result.put("returnCode", "0007");
				result.put("returnMsg", "订单号长度不能超过32位");
				return CommonUtil.signReturn(result);
			}
			srcStr.append("&orderNum="+orderNum);
			
			if(signStr == null || "".equals(signStr)){ 
				result.put("returnCode", "0007");
				result.put("returnMsg", "缺少签名信息");
				return CommonUtil.signReturn(result);
			}
			MemberInfoExample memberInfoExample = new MemberInfoExample();
			memberInfoExample.or().andCodeEqualTo(memberCode).andDelFlagEqualTo("0");
			List<MemberInfo> memberInfoList = memberInfoService.selectByExample(memberInfoExample);
			if(null == memberInfoList || memberInfoList.size() == 0){
				result.put("returnCode", "0001");
				result.put("returnMsg", "商户信息不存在");
				return CommonUtil.signReturn(result);
			}
			MemberInfo memberInfo = memberInfoList.get(0);
			if(!"0".equals(memberInfo.getStatus())){
				if("4".equals(memberInfo.getStatus())){
					result.put("returnCode", "0008");
					result.put("returnMsg", "该商户未进行认证，暂时无法交易");
					return CommonUtil.signReturn(result);
				}
				result.put("returnCode", "0008");
				result.put("returnMsg", "对不起，该商户暂不可用");
				return CommonUtil.signReturn(result);
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
				return CommonUtil.signReturn(result);
			}
			
			
			SysOfficeExample sysOfficeExample = new SysOfficeExample();
			sysOfficeExample.or().andIdEqualTo(epayCodeList.get(0).getOfficeId()).andAgtTypeEqualTo("3");
			List<SysOffice> sysOfficeList = sysOfficeService.selectByExample(sysOfficeExample);
			if(null == sysOfficeList || sysOfficeList.size() == 0){
				result.put("returnCode", "0008");
				result.put("returnMsg", "该商户暂未支持收银台功能，请确认后重试");
				return CommonUtil.signReturn(result);
			}
			
			
			SysOffice sysOffice = sysOfficeList.get(0);
			
		/*	if(!EpaySignUtil.checksign(sysOffice.getPublicKeyRsa(), srcStr.toString(), signStr)){
				result.put("returnCode", "0004");
				result.put("returnMsg", "签名校验错误，请检查签名参数是否正确");
				return CommonUtil.signReturn(result);
			}*/
			
			DebitNoteExample debitNoteExample=new DebitNoteExample();
			debitNoteExample.createCriteria().andOrderNumOuterEqualTo(orderNum);
			List<DebitNote> debitNotes=debitNoteService.selectByExample(debitNoteExample);
			if(debitNotes==null||debitNotes.size()==0){
				throw new ArgException("支付单不存在!");
			}
			
			DebitNote debitNote=debitNotes.get(0);
			
			result.put("orderCode", debitNote.getOrderCode());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			result.put("orderTime", format.format(debitNote.getCreateDate()));
			
			String routeCode = debitNote.getRouteId();
			
			if(RouteCodeConstant.YZF_ROUTE_CODE.equals(routeCode)){
				MemberMerchantKeyExample memberMerchantKeyExample = new MemberMerchantKeyExample();
		        memberMerchantKeyExample.createCriteria().andRouteCodeEqualTo(routeCode).andMerchantCodeEqualTo(debitNote.getMerchantCode()).andDelFlagEqualTo("0");
		        List<MemberMerchantKey> keyList = memberMerchantKeyService.selectByExample(memberMerchantKeyExample);
		        if(keyList == null || keyList.size()!=1){
		        	result.put("returnCode", "0008");
					result.put("returnMsg", "商户私钥未配置");
					return CommonUtil.signReturn(result);
		        }
		        MemberMerchantKey merchantKey = keyList.get(0);
		        
		        
		        String serverUrl = YZFConfig.msServerUrl;
				String charset = "UTF-8";
				String version = "2.0";	
				String action = "SdkOrderQuery";
				String merNo = debitNote.getMerchantCode();//商户号	
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
		     	contextjson.put("linkId",debitNote.getOrderCode());
		     	
		     	String aesData=AESTool.encrypt(contextjson.toString(),aesKey);
				
				logger.info("订单查询未加密参数[{}]",contextjson.toString() );
				
				
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
				logger.info("订单查询返回报文[{}]", new Object[] { respStr });
				
				JSONObject jsonObj = JSONObject.fromObject(respStr);
				byte[] encryptkey_RES=EncryptUtil.decodeBase64(jsonObj.get("encryptkey").toString());
				
				byte[] key_RES_B=RSATool.decrypt(encryptkey_RES,privateK);
				String aes_res_key=new String (key_RES_B);
				String data_RES=jsonObj.get("data").toString();
				String resData=AESTool.decrypt(data_RES, aes_res_key);
				logger.info("订单查询返回报文解密结果[{}]", new Object[] { resData });
				
				JSONObject resObj = JSONObject.fromObject(resData);
				String code = resObj.getString("code");
			}
		} catch (ArgException e) {
			result.put("returnCode", "4004");
			result.put("returnMsg", e.getMessage());
			logger.info(e.getMessage());
			return CommonUtil.signReturn(result);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
			return CommonUtil.signReturn(result);
		}
		result.put("returnCode", "0000");
		result.put("returnMsg", "成功");
		return CommonUtil.signReturn(result);
	}

}
