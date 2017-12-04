package com.epay.scanposp.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.epay.scanposp.common.constant.ESKConfig;
import com.epay.scanposp.common.constant.MSConfig;
import com.epay.scanposp.common.constant.SysConfig;
import com.epay.scanposp.common.constant.WxConfig;
import com.epay.scanposp.common.excep.ArgException;
import com.epay.scanposp.common.utils.CommonUtil;
import com.epay.scanposp.common.utils.DateUtil;
import com.epay.scanposp.common.utils.FileUtil;
import com.epay.scanposp.common.utils.HttpUtil;
import com.epay.scanposp.common.utils.IdcardValidator;
import com.epay.scanposp.common.utils.JsonBeanReleaseUtil;
import com.epay.scanposp.common.utils.SecurityUtil;
import com.epay.scanposp.common.utils.StringUtil;
import com.epay.scanposp.common.utils.ValidateUtil;
import com.epay.scanposp.common.utils.XmlConvertUtil;
import com.epay.scanposp.common.utils.constant.MSPayWayConstant;
import com.epay.scanposp.common.utils.constant.PayTypeConstant;
import com.epay.scanposp.common.utils.constant.RouteCodeConstant;
import com.epay.scanposp.common.utils.constant.SequenseTypeConstant;
import com.epay.scanposp.common.utils.constant.TranCodeConstant;
import com.epay.scanposp.common.utils.epaySecurityUtil.EpaySignUtil;
import com.epay.scanposp.common.utils.esk.Key;
import com.epay.scanposp.common.utils.ms.CryptoUtil;
import com.epay.scanposp.common.utils.ms.HttpClient4Util;
import com.epay.scanposp.common.utils.ms.MSCommonUtil;
import com.epay.scanposp.entity.Account;
import com.epay.scanposp.entity.AccountExample;
import com.epay.scanposp.entity.BuAreaCode;
import com.epay.scanposp.entity.BuAreaCodeExample;
import com.epay.scanposp.entity.BusinessCategory;
import com.epay.scanposp.entity.BusinessCategoryExample;
import com.epay.scanposp.entity.DebitNote;
import com.epay.scanposp.entity.DebitNoteExample;
import com.epay.scanposp.entity.DrawResultNotice;
import com.epay.scanposp.entity.DrawResultNoticeExample;
import com.epay.scanposp.entity.DrawResultNoticeExample.Criteria;
import com.epay.scanposp.entity.EpayCode;
import com.epay.scanposp.entity.EpayCodeExample;
import com.epay.scanposp.entity.Kbin;
import com.epay.scanposp.entity.KbinExample;
import com.epay.scanposp.entity.MemberBank;
import com.epay.scanposp.entity.MemberBankExample;
import com.epay.scanposp.entity.MemberBindAcc;
import com.epay.scanposp.entity.MemberBindAccExample;
import com.epay.scanposp.entity.MemberInfo;
import com.epay.scanposp.entity.MemberInfoExample;
import com.epay.scanposp.entity.MemberMerchantCode;
import com.epay.scanposp.entity.MemberMerchantCodeExample;
import com.epay.scanposp.entity.MsResultNotice;
import com.epay.scanposp.entity.MsResultNoticeExample;
import com.epay.scanposp.entity.PayResultNotice;
import com.epay.scanposp.entity.PayResultNoticeExample;
import com.epay.scanposp.entity.PayType;
import com.epay.scanposp.entity.PayTypeExample;
import com.epay.scanposp.entity.RegisterTmp;
import com.epay.scanposp.entity.RouteWay;
import com.epay.scanposp.entity.RouteWayExample;
import com.epay.scanposp.entity.RoutewayDraw;
import com.epay.scanposp.entity.RoutewayDrawExample;
import com.epay.scanposp.entity.StTradeDetail;
import com.epay.scanposp.entity.StTradeDetailAll;
import com.epay.scanposp.entity.StTradeDetailExample;
import com.epay.scanposp.entity.SysOffice;
import com.epay.scanposp.entity.SysOfficeExample;
import com.epay.scanposp.entity.TradeDetail;
import com.epay.scanposp.entity.TradeDetailExample;
import com.epay.scanposp.entity.TradeVolumnDaily;
import com.epay.scanposp.entity.TradeVolumnDailyExample;
import com.epay.scanposp.service.AccountService;
import com.epay.scanposp.service.BuAreaCodeService;
import com.epay.scanposp.service.BusinessCategoryService;
import com.epay.scanposp.service.CommonService;
import com.epay.scanposp.service.DebitNoteService;
import com.epay.scanposp.service.DrawResultNoticeService;
import com.epay.scanposp.service.EpayCodeService;
import com.epay.scanposp.service.KbinService;
import com.epay.scanposp.service.MemberBankService;
import com.epay.scanposp.service.MemberBindAccService;
import com.epay.scanposp.service.MemberInfoService;
import com.epay.scanposp.service.MemberMerchantCodeService;
import com.epay.scanposp.service.MsResultNoticeService;
import com.epay.scanposp.service.PayResultNoticeService;
import com.epay.scanposp.service.PayRouteService;
import com.epay.scanposp.service.PayTypeService;
import com.epay.scanposp.service.RegisterTmpService;
import com.epay.scanposp.service.RouteWayService;
import com.epay.scanposp.service.RoutewayDrawService;
import com.epay.scanposp.service.StTradeDetailAllService;
import com.epay.scanposp.service.StTradeDetailService;
import com.epay.scanposp.service.SysCommonConfigService;
import com.epay.scanposp.service.SysOfficeExtendService;
import com.epay.scanposp.service.SysOfficeService;
import com.epay.scanposp.service.TradeDetailService;
import com.epay.scanposp.service.TradeVolumnDailyService;
import com.epay.scanposp.thread.DrawResultNoticeThread;
import com.epay.scanposp.thread.PayResultNoticeThread;

@Controller
public class CashierDeskController {
	private static Logger logger = LoggerFactory.getLogger(CommomServiceController.class);
	
	@Resource
	ThreadPoolTaskExecutor threadPoolTaskExecutor;
	
	@Resource
	private EpayCodeService epayCodeService;

	@Resource
	private MemberInfoService memberInfoService;

	@Resource
	private AccountService accountService;

	@Resource
	private DebitNoteService debitNoteService;
	
	@Resource
	private TradeDetailService tradeDetailService;
	
	@Resource
	private RoutewayDrawService routewayDrawService;
	
	@Resource
	private DrawResultNoticeService drawResultNoticeService;
	
	@Resource
	private BuAreaCodeService buAreaCodeService;
	
	@Resource
	private SysOfficeService sysOfficeService;
	
	@Resource
	private SysOfficeExtendService sysOfficeExtendService;
	
	@Resource
	private PayResultNoticeService payResultNoticeService;
	
	@Resource
	private MsResultNoticeService msResultNoticeService;
	@Autowired
	private RegisterTmpService registerTmpService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private MemberBankService memberBankService;
	
	@Autowired
	private BusinessCategoryService businessCategoryService;
	
	@Autowired
	private KbinService kbinService;
	
	@Autowired
	private RouteWayService routeWayService;

	@Resource
	private RegisterLoginController registerLoginController;
	
	@Resource
	private MemberBindAccService memberBindAccService;
	
    @Resource
	private StTradeDetailService stTradeDetailService;
	
	@Resource
	private StTradeDetailAllService stTradeDetailAllService;
	
	@Resource
	private TradeVolumnDailyService tradeVolumnDailyService;
	
	@Autowired
	private PayRouteService payRouteService;
	
	@Autowired
	private SysCommonConfigService sysCommonConfigService;
	
	@Autowired
	private MemberMerchantCodeService memberMerchantCodeService;
	
	@Autowired
	private PayTypeService payTypeService;
	
	@ResponseBody
	@RequestMapping("/api/cashierDesk/getQrcodePay")
	public JSONObject getQrcodePay(HttpServletRequest request,HttpServletResponse response){
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		String payMoney = reqDataJson.getString("payMoney");
		String payType = reqDataJson.getString("payType");
		String orderNum = reqDataJson.getString("orderNum");
		String memberCode = reqDataJson.getString("memberCode");
		String isMoneyFilled = reqDataJson.getString("isMoneyFilled");
		String callbackUrl = reqDataJson.getString("callbackUrl");
		String platformType = reqDataJson.getString("platformType");
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
		if("1".equals(isMoneyFilled) || "0".equals(isMoneyFilled)){
			srcStr.append("&isMoneyFilled="+isMoneyFilled);
		}else{
			result.put("returnCode", "0007");
			result.put("returnMsg", "订单金额传送标志isMoneyFilled参数值错误");
			return result;
		}
		
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
		if("1".equals(isMoneyFilled)){
			srcStr.append("&payMoney="+payMoney);
		}
		if(!"1".equals(platformType) && !"2".equals(platformType)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "收银台平台类型错误或缺失");
			return result;
		}
		srcStr.append("&platformType="+platformType);
		
		if(signStr == null || "".equals(signStr)){ 
			result.put("returnCode", "0007");
			result.put("returnMsg", "缺少签名信息");
			return result;
		}
		
		if(!"1".equals(payType) && !"2".equals(payType)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "支付方式异常");
			return result;
		}
		
		result = validMemberInfoForQrcode(memberCode, orderNum, payMoney, platformType, payType, srcStr.toString(), signStr, callbackUrl);
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/cashierDesk/qrcodePay")
	public JSONObject qrcodePay(HttpServletRequest request,HttpServletResponse response){
		String payMoney = request.getParameter("payMoney");
		String payType = request.getParameter("payType");
		String orderNum = request.getParameter("orderNum");
		String memberCode = request.getParameter("memberCode");
		String callbackUrl = request.getParameter("callbackUrl");
		String signStr = request.getParameter("signStr");
		
		StringBuilder srcStr = new StringBuilder();
		JSONObject result = new JSONObject();
		if(callbackUrl == null || "".equals(callbackUrl)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "平台支付结果通知回调地址缺失");
			return signReturn(result);
		}
		srcStr.append("callbackUrl="+callbackUrl);
		
		if(memberCode == null || "".equals(memberCode)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "商户号缺失");
			return signReturn(result);
		}
		srcStr.append("&memberCode="+memberCode);
		if(orderNum == null || "".equals(orderNum)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "平台订单号缺失");
			return signReturn(result);
		}
		if(orderNum.length() > 32){
			result.put("returnCode", "0007");
			result.put("returnMsg", "订单号长度不能超过32位");
			return signReturn(result);
		}
		srcStr.append("&orderNum="+orderNum);
		if(null == payMoney || !ValidateUtil.isDoubleT(payMoney) || Double.parseDouble(payMoney)<=0){
			result.put("returnCode", "0005");
			result.put("returnMsg", "支付金额输入不正确");
			return signReturn(result);
		}
		srcStr.append("&payMoney="+payMoney);
		if(!"1".equals(payType) && !"2".equals(payType) 
				&& !"3".equals(payType) && !"4".equals(payType)
				&& !"5".equals(payType)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "支付方式错误");
			return signReturn(result);
		}
		srcStr.append("&payType="+payType);
		if(signStr == null || "".equals(signStr)){ // 联调先屏蔽20171017 by linxf
			result.put("returnCode", "0007");
			result.put("returnMsg", "缺少签名信息");
			return signReturn(result);
		}
		
		result = validMemberInfoForQrcode(memberCode, orderNum, payMoney, "3", payType, srcStr.toString(), signStr, callbackUrl);
		
		return signReturn(result);
	}
	
	@RequestMapping("/cashierDesk/msPayNotify")
	public void msPayNotify(HttpServletRequest request,HttpServletResponse response) {
		try {
			String resEncryptData = request.getParameter("encryptData");

			String resEncryptKey = request.getParameter("encryptKey");
			String charset = "utf-8";
			PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKey();
			byte[] decodeBase64KeyBytes = Base64.decodeBase64(resEncryptKey.getBytes(charset));
			// 解密encryptKey得到merchantAESKey
			byte[] merchantAESKeyBytes = CryptoUtil.RSADecrypt(decodeBase64KeyBytes, hzfPriKey, 2048, 11, "RSA/ECB/PKCS1Padding");
			// 使用base64解码商户请求报文
			byte[] decodeBase64DataBytes = Base64.decodeBase64(resEncryptData.getBytes(charset));
			// 用解密得到的merchantAESKey解密encryptData
			byte[] merchantXmlDataBytes = CryptoUtil.AESDecrypt(decodeBase64DataBytes, merchantAESKeyBytes, "AES", "AES/ECB/PKCS5Padding", null);
			String resXml = new String(merchantXmlDataBytes, charset);
			JSONObject respJSONObject = XmlConvertUtil.documentToJSONObjectFilter(resXml);
			
			//生成测试数据   20170226181513649603
//			JSONObject respJSONObject = new JSONObject();
//			respJSONObject.put("respCode", "000000");
//			respJSONObject.put("smzfMsgId", "666666666666");
//			respJSONObject.put("respType", "S");
//			respJSONObject.put("respDate", "201702261815");
//			respJSONObject.put("respMsg", "测试数据啊啊啊");
//			respJSONObject.put("respType", "S");
//			respJSONObject.put("respType", "S");
//			respJSONObject.put("respType", "S");
//			respJSONObject.put("respType", "S");
//			JSONObject resEntityObject = new JSONObject();
//			resEntityObject.put("reqMsgId", "20170213002636813023");
//			resEntityObject.put("settleDate", "20170226");
//			resEntityObject.put("channelNo", "20170226181513s");
//			resEntityObject.put("payTime", "2017022618666");
//			resEntityObject.put("totalAmount", "0.01");
//			resEntityObject.put("payType", "1");
//			respJSONObject.put("resEntity", resEntityObject);
			

			JSONObject resEntity = respJSONObject.getJSONObject("resEntity");
			String reqMsgId = resEntity.getString("reqMsgId");
			
			MsResultNoticeExample msResultNoticeExample = new MsResultNoticeExample();
			msResultNoticeExample.or().andOrderCodeEqualTo(reqMsgId);
			List<MsResultNotice> msResultNoticeList = msResultNoticeService.selectByExample(msResultNoticeExample);
			if(null == msResultNoticeList || msResultNoticeList.size() == 0){
				DebitNoteExample debitNoteExample = new DebitNoteExample();
				debitNoteExample.createCriteria().andOrderCodeEqualTo(reqMsgId);
				List<DebitNote> debitNotes = debitNoteService.selectByExample(debitNoteExample);

				if (debitNotes != null && debitNotes.size() > 0) {
					DebitNote debitNote = debitNotes.get(0);
					debitNote.setRespCode(respJSONObject.get("respCode").toString());
					debitNote.setRespMsg(respJSONObject.get("respMsg").toString());
					
					//新增一条交易明细记录
					TradeDetail tradeDetail=new TradeDetail();
					tradeDetail.setTxnDate(DateUtil.getDateFormat(new Date(), "yyyyMMdd"));
					tradeDetail.setMemberId(debitNote.getMemberId());
					tradeDetail.setMerchantCode(debitNote.getMerchantCode());
					tradeDetail.setBalanceDate(resEntity.get("settleDate").toString());
					tradeDetail.setChannelNo(resEntity.get("channelNo").toString());
					tradeDetail.setMemberCode(debitNote.getMemberCode());
					tradeDetail.setMoney(debitNote.getMoney());
					tradeDetail.setPayTime(resEntity.get("payTime").toString());
					tradeDetail.setPtSerialNo(debitNote.getOrderCode());
					tradeDetail.setOrderCode(debitNote.getOrderCode());
					tradeDetail.setReqDate(DateUtil.getDateTimeStr(debitNote.getCreateDate()));
					tradeDetail.setRespCode(respJSONObject.get("respCode").toString());
					tradeDetail.setRespDate(DateUtil.getDateTimeStr(new Date()));
					tradeDetail.setRespMsg(respJSONObject.get("respMsg").toString());
					tradeDetail.setRouteId(debitNote.getRouteId());
					tradeDetail.setTxnType(debitNote.getTxnType());
					tradeDetail.setDelFlag("0");
					tradeDetail.setCreateDate(new Date());
					if ("S".equals(respJSONObject.get("respType")) && "000000".equals(respJSONObject.get("respCode"))) {
						debitNote.setStatus("1");
						tradeDetail.setRespType("S");
					}else if("200002".equals(respJSONObject.get("respCode"))){
						debitNote.setStatus("3");
						tradeDetail.setRespType("R");
					}else{
						debitNote.setStatus("2");
						tradeDetail.setRespType("E");
					}
					debitNote.setUpdateDate(new Date());
					debitNoteService.updateByPrimaryKey(debitNote);
					
					MsResultNotice msResultNotice = new MsResultNotice();
					msResultNotice.setMoney(new BigDecimal(resEntity.getString("totalAmount")));
					msResultNotice.setOrderCode(reqMsgId);
					msResultNotice.setPtSerialNo(respJSONObject.getString("smzfMsgId"));
					msResultNotice.setRespDate(respJSONObject.getString("respDate"));
					msResultNotice.setRespType(respJSONObject.getString("respType"));
					msResultNotice.setRespCode(respJSONObject.getString("respCode"));
					msResultNotice.setRespMsg(respJSONObject.getString("respMsg"));
					if(resEntity.containsKey("payType") && null != resEntity.getString("payType")){
						msResultNotice.setCardType(resEntity.getString("payType"));
					}
					if(resEntity.containsKey("payTime") && null != resEntity.getString("payTime")){
						msResultNotice.setPayTime(resEntity.getString("payTime"));
					}
					msResultNotice.setBalanceDate(resEntity.getString("settleDate"));
					if(resEntity.containsKey("payTime") && null != resEntity.getString("payTime")){
						msResultNotice.setChannelNo(resEntity.getString("channelNo"));
					}
					if(resEntity.containsKey("isClearOrCancel") && null != resEntity.getString("isClearOrCancel")){
						msResultNotice.setSettleCancelFlag(resEntity.getString("isClearOrCancel"));
					}
					msResultNotice.setCreateDate(new Date());
					msResultNotice.setUpdateDate(new Date());
					msResultNoticeService.insertSelective(msResultNotice);
					
					/**不发起提现时操作*/
					if ("S".equals(respJSONObject.get("respType")) && "000000".equals(respJSONObject.get("respCode"))) {
//						if("0".equals(debitNote.getSettleType())){
//							tradeDetail.setSettleType("0");
//						}else{
//							tradeDetail.setSettleType("1");
//						}
						tradeDetail.setSettleType("1");
						//发送微信模板消息
						/*
						EpayCodeExample epayCodeExample = new EpayCodeExample();
						epayCodeExample.or().andMemberIdEqualTo(debitNote.getMemberId()).andStatusEqualTo("5");
						List<EpayCode> epayCodeList = epayCodeService.selectByExample(epayCodeExample);
						if(epayCodeList.size() > 0){
							String memberOfficeId = epayCodeList.get(0).getOfficeId();
							SysOffice sysOffice = getTopAgentOffice(memberOfficeId);
							if(null != sysOffice){
								MemberOpenidExample memberOpenidExample = new MemberOpenidExample();
								memberOpenidExample.or().andMemberIdEqualTo(debitNote.getMemberId());
								List<MemberOpenid> memberOpenidList = memberOpenidService.selectByExample(memberOpenidExample);
								if(memberOpenidList.size()>0){
									MemberOpenid memberOpenid = memberOpenidList.get(0);
									if(null != memberOpenid.getOpenid() && !"".equals(memberOpenid.getOpenid())){
										SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
										String messageTitle = "恭喜您有一笔收款到账";
										String remark = "详细信息请查看交易记录";
										MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(debitNote.getMemberId());
										String payTime = msResultNotice.getPayTime();
										if(null != payTime && !"".equals(payTime) && payTime.length() >= 14){
											payTime = sdf.format(new SimpleDateFormat("yyyyMMddHHmmss").parse(payTime.length()>14?payTime.substring(0,14):payTime));
										}else{
											payTime = sdf.format(new Date());
										}
										if("2".equals(sysOffice.getAgtType())){
											SysOfficeConfigOemExample sysOfficeConfigOemExample = new SysOfficeConfigOemExample();
											sysOfficeConfigOemExample.or().andOfficeIdEqualTo(memberOfficeId);
											List<SysOfficeConfigOem> officeConfogList = sysOfficeConfigOemService.selectByExample(sysOfficeConfigOemExample);
											if(officeConfogList.size()>0){
												int expiresSeconds = 7200;//单位秒
												SysOfficeConfigOem officeConfigOEM = officeConfogList.get(0);
												String accessToken = officeConfigOEM.getAccessToken();
												Date updateDate = officeConfigOEM.getUpdateDate();
												if(null != accessToken && !"".equals(accessToken) && ((new Date()).getTime() < updateDate.getTime()+expiresSeconds*1000)){
													//accessToken有效
													WxMessageUtil.sendTmpMsgSKTZOEM(accessToken,officeConfigOEM.getWxTemplateid(), memberOpenid.getOpenid(), messageTitle, memberInfo.getName(), debitNote.getMoney() + "元", payTime, remark);
												}else{
													String result = HttpUtil.sendGetRequest("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + officeConfigOEM.getWxAppid() + "&secret=" + officeConfigOEM.getWxAppsecret());
													JSONObject resultJson = JSONObject.fromObject(result);
													if (resultJson.has("access_token")) {
														accessToken = resultJson.getString("access_token");
														updateDate = new Date();
														officeConfigOEM.setAccessToken(accessToken);
														officeConfigOEM.setUpdateDate(new Date());
														sysOfficeConfigOemService.updateByPrimaryKeySelective(officeConfigOEM);
														WxMessageUtil.sendTmpMsgSKTZOEM(accessToken,officeConfigOEM.getWxTemplateid(), memberOpenid.getOpenid(), messageTitle, memberInfo.getName(), debitNote.getMoney() + "元", payTime, remark);
													}
												}
											}
										}else{
											WxMessageUtil.sendTmpMsgSKTZ(memberOpenid.getOpenid(), messageTitle, memberInfo.getName(), debitNote.getMoney() + "元", payTime, remark);
										}
									}
								}
								
							}
						}
						*/
					}
					/**T0 发起提现操作*/
					/*
					String nowTime = new SimpleDateFormat("HH:mm:ss").format(new Date());
					//如果是T0交易则发起提现请求
					if ("S".equals(respJSONObject.get("respType")) && "000000".equals(respJSONObject.get("respCode"))) {
							//该段为即时提现功能的处理
						if("0".equals(debitNote.getSettleType())){
							if(DateUtil.dateCompare(nowTime,"09:00:00","HH:mm:ss")==1&&DateUtil.dateCompare(nowTime,"23:00:00","HH:mm:ss")==-1){
								logger.debug("----------T0 提现-------------");
								System.out.println("----------T0 提现-------------");
								tradeDetail.setSettleType("0");
								msWithdraw(debitNote.getMemberId(),debitNote.getMemberCode(),debitNote.getMerchantCode());
							}else{
								debitNote.setSettleType("1");
								tradeDetail.setSettleType("1");
								debitNoteService.updateByPrimaryKey(debitNote);
							}
						}else{
							tradeDetail.setSettleType("1");
						}
					}
					*/
					
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
						if ("S".equals(respJSONObject.get("respType")) && "000000".equals(respJSONObject.get("respCode"))) {
							payResultNotice.setRespType("2");
							payResultNotice.setResultCode("0000");
							payResultNotice.setResultMessage("交易成功");
						}else if("200002".equals(respJSONObject.get("respCode"))){
							payResultNotice.setRespType("1");
							payResultNotice.setResultCode("0009");   
							payResultNotice.setResultMessage("交易正在处理中...");
						}else{
							payResultNotice.setRespType("3");
							payResultNotice.setResultCode("0003");   
							payResultNotice.setResultMessage("交易失败");
						}
						
						tradeDetail.setInterfaceType(payResultNotice.getInterfaceType());
						tradeDetail.setPlatformType(payResultNotice.getPlatformType());
						tradeDetail.setOrderNumOuter(payResultNotice.getOrderNumOuter());
					}
					//更改逻辑  只有交易成功的记录才写进交易记录表  单独写在此处防止后面再次更改逻辑会不理解
					if ("S".equals(respJSONObject.get("respType")) && "000000".equals(respJSONObject.get("respCode"))) {
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
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		try {
			response.getWriter().write("000000");
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

	}
	
	
	@RequestMapping("/cashierDesk/eskPayNotify")
	public void eskPayNotify(HttpServletRequest request,HttpServletResponse response) {
		try {
			String resEncryptData = request.getParameter("Context");
			
			String resEncryptKey = request.getParameter("encrtpKey");
			logger.info("eskPayNotify回调返回报文resEncryptData{}，resEncryptKey{}",  resEncryptData, resEncryptKey);
			String charset = "utf-8";
			PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKey();
			byte[] decodeBase64KeyBytes = Base64.decodeBase64(resEncryptKey.getBytes(charset));
			// 解密encryptKey得到merchantAESKey  屏蔽by linxf 测试
			//byte[] merchantAESKeyBytes = CryptoUtil.RSADecrypt(decodeBase64KeyBytes, hzfPriKey, 2048, 11, "RSA/ECB/PKCS1Padding");
			// 使用base64解码商户请求报文
			byte[] merchantAESKeyBytes = Key.jdkRSA_(decodeBase64KeyBytes, ESKConfig.privateKey);
			byte[] decodeBase64DataBytes = Base64.decodeBase64(resEncryptData.getBytes(charset));
			// 用解密得到的merchantAESKey解密encryptData
			byte[] merchantXmlDataBytes = CryptoUtil.AESDecrypt(decodeBase64DataBytes, merchantAESKeyBytes, "AES", "AES/ECB/PKCS5Padding", null);
			String resXml = new String(merchantXmlDataBytes, charset);
			JSONObject respJSONObject = JSONObject.fromObject(resXml);
			logger.info("eskPayNotify解密回调返回报文[{}]",  respJSONObject );
			//生成测试数据   20170226181513649603
//			JSONObject respJSONObject = new JSONObject();
//			respJSONObject.put("respCode", "000000");
//			respJSONObject.put("smzfMsgId", "666666666666");
//			respJSONObject.put("respType", "S");
//			respJSONObject.put("respDate", "201702261815");
//			respJSONObject.put("respMsg", "测试数据啊啊啊");
//			respJSONObject.put("respType", "S");
//			respJSONObject.put("respType", "S");
//			respJSONObject.put("respType", "S");
//			respJSONObject.put("respType", "S");
//			JSONObject resEntityObject = new JSONObject();
//			resEntityObject.put("reqMsgId", "20170213002636813023");
//			resEntityObject.put("settleDate", "20170226");
//			resEntityObject.put("channelNo", "20170226181513s");
//			resEntityObject.put("payTime", "2017022618666");
//			resEntityObject.put("totalAmount", "0.01");
//			resEntityObject.put("payType", "1");
//			respJSONObject.put("resEntity", resEntityObject);
			

			//JSONObject resEntity = respJSONObject.getJSONObject("resEntity");
			String reqMsgId = respJSONObject.getString("orderNumber");
			
			MsResultNoticeExample msResultNoticeExample = new MsResultNoticeExample();
			msResultNoticeExample.or().andOrderCodeEqualTo(reqMsgId);
			List<MsResultNotice> msResultNoticeList = msResultNoticeService.selectByExample(msResultNoticeExample);
			if(null == msResultNoticeList || msResultNoticeList.size() == 0){
				DebitNoteExample debitNoteExample = new DebitNoteExample();
				debitNoteExample.createCriteria().andOrderCodeEqualTo(reqMsgId);
				List<DebitNote> debitNotes = debitNoteService.selectByExample(debitNoteExample);

				if (debitNotes != null && debitNotes.size() > 0) {
					DebitNote debitNote = debitNotes.get(0);
					debitNote.setRespCode(respJSONObject.get("respCode").toString());
					debitNote.setRespMsg(respJSONObject.get("respMsg").toString());
					
					//新增一条交易明细记录
					TradeDetail tradeDetail=new TradeDetail();
					tradeDetail.setTxnDate(DateUtil.getDateFormat(new Date(), "yyyyMMdd"));
					tradeDetail.setMemberId(debitNote.getMemberId());
					tradeDetail.setMerchantCode(debitNote.getMerchantCode());
					if(respJSONObject.containsKey("settleDate") && null != respJSONObject.getString("settleDate")){
						tradeDetail.setBalanceDate(respJSONObject.get("settleDate").toString());
					}
					if(respJSONObject.containsKey("OrderNo") && null != respJSONObject.getString("OrderNo")){
						tradeDetail.setChannelNo(respJSONObject.get("OrderNo").toString());
					}
					tradeDetail.setMemberCode(debitNote.getMemberCode());
					tradeDetail.setMoney(debitNote.getMoney());
					if(respJSONObject.containsKey("payTime") && null != respJSONObject.getString("payTime")){
						tradeDetail.setPayTime(respJSONObject.get("payTime").toString());
					}else{
						tradeDetail.setPayTime(DateUtil.getDateTimeStr(debitNote.getCreateDate()));
					}
					tradeDetail.setPtSerialNo(debitNote.getOrderCode());
					tradeDetail.setOrderCode(debitNote.getOrderCode());
					tradeDetail.setReqDate(DateUtil.getDateTimeStr(debitNote.getCreateDate()));
					tradeDetail.setRespCode(respJSONObject.get("respCode").toString());
					tradeDetail.setRespDate(DateUtil.getDateTimeStr(new Date()));
					tradeDetail.setRespMsg(respJSONObject.get("respMsg").toString());
					tradeDetail.setRouteId(debitNote.getRouteId());
					tradeDetail.setTxnType(debitNote.getTxnType());
					tradeDetail.setMemberTradeRate(debitNote.getTradeRate());
					tradeDetail.setDelFlag("0");
					tradeDetail.setCreateDate(new Date());
					if ("S".equals(respJSONObject.get("respType")) && "000000".equals(respJSONObject.get("respCode"))) {
						debitNote.setStatus("1");
						tradeDetail.setRespType("S");
					}else if("200002".equals(respJSONObject.get("respCode"))){
						debitNote.setStatus("3");
						tradeDetail.setRespType("R");
					}else{
						debitNote.setStatus("2");
						tradeDetail.setRespType("E");
					}
					debitNote.setUpdateDate(new Date());
					debitNoteService.updateByPrimaryKey(debitNote);
					
					MsResultNotice msResultNotice = new MsResultNotice();
					msResultNotice.setMoney(new BigDecimal(respJSONObject.getString("buyerPayAmount")));
					msResultNotice.setOrderCode(reqMsgId);
					msResultNotice.setPtSerialNo(respJSONObject.getString("OrderNo"));
					String respTime = respJSONObject.getString("respTime");
					SimpleDateFormat dateSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					msResultNotice.setRespDate(DateUtil.getDateTimeStr(dateSimpleDateFormat.parse(respTime)));
					msResultNotice.setRespType(respJSONObject.getString("respType"));
					msResultNotice.setRespCode(respJSONObject.getString("respCode"));
					msResultNotice.setRespMsg(respJSONObject.getString("respMsg"));
					if(respJSONObject.containsKey("payType") && null != respJSONObject.getString("payType")){
						msResultNotice.setCardType(respJSONObject.getString("payType"));
					}
					if(respJSONObject.containsKey("payTime") && null != respJSONObject.getString("payTime")){
						msResultNotice.setPayTime(respJSONObject.getString("payTime"));
					}
					if(respJSONObject.containsKey("settleDate") && null != respJSONObject.getString("settleDate")){
						msResultNotice.setBalanceDate(respJSONObject.getString("settleDate"));
					}
					if(respJSONObject.containsKey("payTime") && null != respJSONObject.getString("payTime")){
						msResultNotice.setChannelNo(respJSONObject.getString("channelNo"));
					}
					if(respJSONObject.containsKey("isClearOrCancel") && null != respJSONObject.getString("isClearOrCancel")){
						msResultNotice.setSettleCancelFlag(respJSONObject.getString("isClearOrCancel"));
					}
					msResultNotice.setCreateDate(new Date());
					msResultNotice.setUpdateDate(new Date());
					msResultNoticeService.insertSelective(msResultNotice);
					
					/**不发起提现时操作*/
					if ("S".equals(respJSONObject.get("respType")) && "000000".equals(respJSONObject.get("respCode"))) {
						if("0".equals(debitNote.getSettleType())){
							tradeDetail.setSettleType("0");
						}else{
							tradeDetail.setSettleType("1");
						}
					//	tradeDetail.setSettleType("1");
						//发送微信模板消息
						/*
						EpayCodeExample epayCodeExample = new EpayCodeExample();
						epayCodeExample.or().andMemberIdEqualTo(debitNote.getMemberId()).andStatusEqualTo("5");
						List<EpayCode> epayCodeList = epayCodeService.selectByExample(epayCodeExample);
						if(epayCodeList.size() > 0){
							String memberOfficeId = epayCodeList.get(0).getOfficeId();
							SysOffice sysOffice = getTopAgentOffice(memberOfficeId);
							if(null != sysOffice){
								MemberOpenidExample memberOpenidExample = new MemberOpenidExample();
								memberOpenidExample.or().andMemberIdEqualTo(debitNote.getMemberId());
								List<MemberOpenid> memberOpenidList = memberOpenidService.selectByExample(memberOpenidExample);
								if(memberOpenidList.size()>0){
									MemberOpenid memberOpenid = memberOpenidList.get(0);
									if(null != memberOpenid.getOpenid() && !"".equals(memberOpenid.getOpenid())){
										SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
										String messageTitle = "恭喜您有一笔收款到账";
										String remark = "详细信息请查看交易记录";
										MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(debitNote.getMemberId());
										String payTime = msResultNotice.getPayTime();
										if(null != payTime && !"".equals(payTime) && payTime.length() >= 14){
											payTime = sdf.format(new SimpleDateFormat("yyyyMMddHHmmss").parse(payTime.length()>14?payTime.substring(0,14):payTime));
										}else{
											payTime = sdf.format(new Date());
										}
										if("2".equals(sysOffice.getAgtType())){
											SysOfficeConfigOemExample sysOfficeConfigOemExample = new SysOfficeConfigOemExample();
											sysOfficeConfigOemExample.or().andOfficeIdEqualTo(memberOfficeId);
											List<SysOfficeConfigOem> officeConfogList = sysOfficeConfigOemService.selectByExample(sysOfficeConfigOemExample);
											if(officeConfogList.size()>0){
												int expiresSeconds = 7200;//单位秒
												SysOfficeConfigOem officeConfigOEM = officeConfogList.get(0);
												String accessToken = officeConfigOEM.getAccessToken();
												Date updateDate = officeConfigOEM.getUpdateDate();
												if(null != accessToken && !"".equals(accessToken) && ((new Date()).getTime() < updateDate.getTime()+expiresSeconds*1000)){
													//accessToken有效
													WxMessageUtil.sendTmpMsgSKTZOEM(accessToken,officeConfigOEM.getWxTemplateid(), memberOpenid.getOpenid(), messageTitle, memberInfo.getName(), debitNote.getMoney() + "元", payTime, remark);
												}else{
													String result = HttpUtil.sendGetRequest("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + officeConfigOEM.getWxAppid() + "&secret=" + officeConfigOEM.getWxAppsecret());
													JSONObject resultJson = JSONObject.fromObject(result);
													if (resultJson.has("access_token")) {
														accessToken = resultJson.getString("access_token");
														updateDate = new Date();
														officeConfigOEM.setAccessToken(accessToken);
														officeConfigOEM.setUpdateDate(new Date());
														sysOfficeConfigOemService.updateByPrimaryKeySelective(officeConfigOEM);
														WxMessageUtil.sendTmpMsgSKTZOEM(accessToken,officeConfigOEM.getWxTemplateid(), memberOpenid.getOpenid(), messageTitle, memberInfo.getName(), debitNote.getMoney() + "元", payTime, remark);
													}
												}
											}
										}else{
											WxMessageUtil.sendTmpMsgSKTZ(memberOpenid.getOpenid(), messageTitle, memberInfo.getName(), debitNote.getMoney() + "元", payTime, remark);
										}
									}
								}
								
							}
						}
						*/
					}
					/**T0 发起提现操作*/
					/*
					String nowTime = new SimpleDateFormat("HH:mm:ss").format(new Date());
					//如果是T0交易则发起提现请求
					if ("S".equals(respJSONObject.get("respType")) && "000000".equals(respJSONObject.get("respCode"))) {
							//该段为即时提现功能的处理
						if("0".equals(debitNote.getSettleType())){
							if(DateUtil.dateCompare(nowTime,"09:00:00","HH:mm:ss")==1&&DateUtil.dateCompare(nowTime,"23:00:00","HH:mm:ss")==-1){
								logger.debug("----------T0 提现-------------");
								System.out.println("----------T0 提现-------------");
								tradeDetail.setSettleType("0");
								msWithdraw(debitNote.getMemberId(),debitNote.getMemberCode(),debitNote.getMerchantCode());
							}else{
								debitNote.setSettleType("1");
								tradeDetail.setSettleType("1");
								debitNoteService.updateByPrimaryKey(debitNote);
							}
						}else{
							tradeDetail.setSettleType("1");
						}
					}
					*/
					
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
						if ("S".equals(respJSONObject.get("respType")) && "000000".equals(respJSONObject.get("respCode"))) {
							payResultNotice.setRespType("2");
							payResultNotice.setResultCode("0000");
							payResultNotice.setResultMessage("交易成功");
						}else if("200002".equals(respJSONObject.get("respCode"))){
							payResultNotice.setRespType("1");
							payResultNotice.setResultCode("0009");   
							payResultNotice.setResultMessage("交易正在处理中...");
						}else{
							payResultNotice.setRespType("3");
							payResultNotice.setResultCode("0003");   
							payResultNotice.setResultMessage("交易失败");
						}
						
						tradeDetail.setInterfaceType(payResultNotice.getInterfaceType());
						tradeDetail.setPlatformType(payResultNotice.getPlatformType());
						tradeDetail.setOrderNumOuter(payResultNotice.getOrderNumOuter());
					}
					//更改逻辑  只有交易成功的记录才写进交易记录表  单独写在此处防止后面再次更改逻辑会不理解
					if ("S".equals(respJSONObject.get("respType")) && "000000".equals(respJSONObject.get("respCode"))) {
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
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		try {
			response.getWriter().write("000000");
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

	}
	
	public JSONObject validMemberInfoForQrcode(String memberCode,String orderNum,String payMoney,String platformType,String payType,String signOrginalStr,String signedStr,String callbackUrl){
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
		String payTypeStr = "";
		if("1".equals(payType)){
			payTypeStr = "WX";
		}else if("2".equals(payType)){
			payTypeStr = "ZFB";
		}
		
		Map<String,String> rtMap  = getRouteCodeAndAisleType(memberInfo.getId(),PayTypeConstant.PAY_METHOD_SMZF,payTypeStr);
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
		
		if(RouteCodeConstant.ESK_ROUTE_CODE.equals(routeCode)){
			result = eskScanQrcodePay(platformType,payType,memberInfo, payMoney, orderNum, callbackUrl , merchantCode ,aisleType);
		}else{
			result = msScanQrcodePay(platformType,payType,memberInfo, payMoney, orderNum, callbackUrl);
		}
		
		return result;
	}
	
	
	/**
	 * 民生扫码支付
	 * 
	 * @param request
	 * @return
	 */
	public JSONObject msScanQrcodePay(String platformType,String payType,MemberInfo memberInfo,String payMoney,String orderNumOuter,String callbackUrl) {
		JSONObject result = new JSONObject();
		JSONObject resData = new JSONObject();
		try {
			// 插入一条收款记录
			String orderCode = CommonUtil.getOrderCode();
			
			DebitNote debitNote = new DebitNote();
			debitNote.setCreateDate(new Date());
			debitNote.setMemberId(memberInfo.getId());
			debitNote.setMoney(new BigDecimal(payMoney));
			debitNote.setOrderCode(orderCode);
			debitNote.setRouteId(memberInfo.getZfbRouteId());
			debitNote.setStatus("0");
			if("1".equals(payType)){
				debitNote.setTxnType("1");
				debitNote.setMemberCode(memberInfo.getWxMemberCode());
				debitNote.setMerchantCode(memberInfo.getWxMerchantCode());
			}else if("2".equals(payType)){
				debitNote.setTxnType("2");
				debitNote.setMemberCode(memberInfo.getZfbMemberCode());
				debitNote.setMerchantCode(memberInfo.getZfbMerchantCode());
			}else if("3".equals(payType)){
				debitNote.setTxnType("3");
				debitNote.setMemberCode(memberInfo.getQqMemberCode());
				debitNote.setMerchantCode(memberInfo.getQqMerchantCode());
			}else if("4".equals(payType)){
				debitNote.setTxnType("4");
				debitNote.setMemberCode(memberInfo.getBdMemberCode());
				debitNote.setMerchantCode(memberInfo.getBdMerchantCode());
			}else if("5".equals(payType)){
				debitNote.setTxnType("5");
				debitNote.setMemberCode(memberInfo.getJdMemberCode());
				debitNote.setMerchantCode(memberInfo.getJdMerchantCode());
			}
			debitNote.setSettleType(memberInfo.getSettleType());
			
			debitNoteService.insertSelective(debitNote);
			
			PayResultNotice payResultNotice = new PayResultNotice();
			payResultNotice.setOrderCode(debitNote.getOrderCode());
			payResultNotice.setOrderNumOuter(orderNumOuter);
			payResultNotice.setPayMoney(debitNote.getMoney());
			payResultNotice.setMemberCode(memberInfo.getCode());
			if("1".equals(payType)){
				payResultNotice.setPayType("1");
			}else if("2".equals(payType)){
				payResultNotice.setPayType("2");
			}else if("3".equals(payType)){
				payResultNotice.setPayType("3");
			}else if("4".equals(payType)){
				payResultNotice.setPayType("4");
			}else if("5".equals(payType)){
				payResultNotice.setPayType("5");
			}
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
			String callBack = SysConfig.serverUrl + "/cashierDesk/msPayNotify";
			
			System.out.println("-------------通知地址------" + callBack + "------------");
			
			// 调用支付通道
			
			String serverUrl = MSConfig.msServerUrl;
			PublicKey yhPubKey = null;
			if (serverUrl.startsWith("https://ipay")) {
				yhPubKey = CryptoUtil.getRSAPublicKey(true);
			} else {
				yhPubKey = CryptoUtil.getRSAPublicKey(false);
			}
			PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKey();
			String tranCode = TranCodeConstant.SMZF;
//			String tranCode = "SMZF002";
			String charset = "utf-8";
			StringBuilder sBuilder = new StringBuilder();
			sBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			sBuilder.append("<merchant>");
			sBuilder.append("<head>");
			sBuilder.append("<version>1.0.0</version>");
			sBuilder.append("<msgType>01</msgType>");
			sBuilder.append("<reqDate>" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + "</reqDate>");
			sBuilder.append("</head>");
			sBuilder.append("<body>");
			if("1".equals(payType)){
				sBuilder.append("<merchantCode>" + memberInfo.getWxMerchantCode() + "</merchantCode>");
				sBuilder.append("<subAppid>" + WxConfig.appid + "</subAppid>");
			}else if("2".equals(payType)){
				sBuilder.append("<merchantCode>" + memberInfo.getZfbMerchantCode() + "</merchantCode>");
			}else if("3".equals(payType)){
				sBuilder.append("<merchantCode>" + memberInfo.getQqMerchantCode() + "</merchantCode>");
			}else if("4".equals(payType)){
				sBuilder.append("<merchantCode>" + memberInfo.getBdMerchantCode() + "</merchantCode>");
			}else if("5".equals(payType)){
				sBuilder.append("<merchantCode>" + memberInfo.getJdMerchantCode() + "</merchantCode>");
			}
			sBuilder.append("<totalAmount>" + payMoney + "</totalAmount>");
			sBuilder.append("<subject>" + memberInfo.getName() + " 收款" + "</subject>");
			sBuilder.append("</body>");
			sBuilder.append("</merchant>");
			String plainXML = sBuilder.toString();
			byte[] plainBytes = plainXML.getBytes(charset);
			String keyStr = MSCommonUtil.generateLenString(16);
			;
			byte[] keyBytes = keyStr.getBytes(charset);
			String encryptData = new String(Base64.encodeBase64((CryptoUtil.AESEncrypt(plainBytes, keyBytes, "AES", "AES/ECB/PKCS5Padding", null))), charset);
			String signData = new String(Base64.encodeBase64(CryptoUtil.digitalSign(plainBytes, hzfPriKey, "SHA1WithRSA")), charset);
			String encrtptKey = new String(Base64.encodeBase64(CryptoUtil.RSAEncrypt(keyBytes, yhPubKey, 2048, 11, "RSA/ECB/PKCS1Padding")), charset);
			List<NameValuePair> nvps = new LinkedList<NameValuePair>();
			nvps.add(new BasicNameValuePair("encryptData", encryptData));
			nvps.add(new BasicNameValuePair("encryptKey", encrtptKey));
			
			if("0".equals(memberInfo.getSettleType())){
				nvps.add(new BasicNameValuePair("cooperator", MSConfig.cooperator_t0));
			}else{
				nvps.add(new BasicNameValuePair("cooperator", MSConfig.cooperator_t1));
			}
			
			nvps.add(new BasicNameValuePair("signData", signData));
			nvps.add(new BasicNameValuePair("tranCode", tranCode));
			nvps.add(new BasicNameValuePair("callBack", callBack));
			nvps.add(new BasicNameValuePair("reqMsgId", orderCode));
			byte[] b = HttpClient4Util.getInstance().doPost(serverUrl, null, nvps);
			String respStr = new String(b, charset);
			JSONObject jsonObject = JSONObject.fromObject(respStr);
			String resEncryptData = jsonObject.getString("encryptData");
			String resEncryptKey = jsonObject.getString("encryptKey");
			byte[] decodeBase64KeyBytes = Base64.decodeBase64(resEncryptKey.getBytes(charset));
			// 解密encryptKey得到merchantAESKey
			byte[] merchantAESKeyBytes = CryptoUtil.RSADecrypt(decodeBase64KeyBytes, hzfPriKey, 2048, 11, "RSA/ECB/PKCS1Padding");
			// 使用base64解码商户请求报文
			byte[] decodeBase64DataBytes = Base64.decodeBase64(resEncryptData.getBytes(charset));
			// 用解密得到的merchantAESKey解密encryptData
			byte[] merchantXmlDataBytes = CryptoUtil.AESDecrypt(decodeBase64DataBytes, merchantAESKeyBytes, "AES", "AES/ECB/PKCS5Padding", null);
			String resXml = new String(merchantXmlDataBytes, charset);
			JSONObject respJSONObject = XmlConvertUtil.documentToJSONObjectFilter(resXml);
			logger.info("返回报文[{}]",  respJSONObject );
//			resData.put("orderCode", orderCode);     //暂不回传我方的订单编码
			if (respJSONObject.containsKey("resEntity")) {//民生传回qrCode时 不一定是S状态
				JSONObject resEntity = respJSONObject.getJSONObject("resEntity");
				if (resEntity.containsKey("qrCode")) {
					resData.put("qrCode", SecurityUtil.base64Encode(resEntity.get("qrCode").toString()));
					result.put("resData", resData);
				}
				result.put("returnCode", "0000");
				result.put("returnMsg", "成功");
			}else{
				result.put("returnCode", "0003");
				result.put("returnMsg", "MS-"+respJSONObject.getString("respCode")+ ":" +respJSONObject.getString("respMsg"));
			}
			
		} catch (ArgException e) {
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
			logger.info(e.getMessage());
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
			return result;
		}
		return result;
	}
	
	
	/**
	 * 易收款扫码支付
	 * 
	 * @param request
	 * @return
	 */
	public JSONObject eskScanQrcodePay(String platformType,String payType,MemberInfo memberInfo,String payMoney,String orderNumOuter,String callbackUrl,MemberMerchantCode merchantCode,String aisleType) {
		JSONObject result = new JSONObject();
		JSONObject resData = new JSONObject();
		try {
			// 插入一条收款记录
			String orderCode = CommonUtil.getOrderCode();
			
			DebitNote debitNote = new DebitNote();
			debitNote.setCreateDate(new Date());
			debitNote.setMemberId(memberInfo.getId());
			debitNote.setMoney(new BigDecimal(payMoney));
			debitNote.setOrderCode(orderCode);
			debitNote.setOrderNumOuter(orderNumOuter);
			debitNote.setRouteId(RouteCodeConstant.ESK_ROUTE_CODE);
			debitNote.setStatus("0");
			if("1".equals(payType)){
				debitNote.setTxnType("1");
				debitNote.setMemberCode(memberInfo.getWxMemberCode());
				debitNote.setMerchantCode(merchantCode.getWxMerchantCode());
			}else if("2".equals(payType)){
				debitNote.setTxnType("2");
				debitNote.setMemberCode(memberInfo.getZfbMemberCode());
				debitNote.setMerchantCode(merchantCode.getZfbMerchantCode());
			}else if("3".equals(payType)){
				debitNote.setTxnType("3");
				debitNote.setMemberCode(memberInfo.getQqMemberCode());
				debitNote.setMerchantCode(merchantCode.getQqMerchantCode());
			}else if("4".equals(payType)){
				debitNote.setTxnType("4");
				debitNote.setMemberCode(memberInfo.getBdMemberCode());
				debitNote.setMerchantCode(merchantCode.getBdMerchantCode());
			}else if("5".equals(payType)){
				debitNote.setTxnType("5");
				debitNote.setMemberCode(memberInfo.getJdMemberCode());
				debitNote.setMerchantCode(merchantCode.getJdMerchantCode());
			}
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
			if("1".equals(payType)){
				payResultNotice.setPayType("1");
			}else if("2".equals(payType)){
				payResultNotice.setPayType("2");
			}else if("3".equals(payType)){
				payResultNotice.setPayType("3");
			}else if("4".equals(payType)){
				payResultNotice.setPayType("4");
			}else if("5".equals(payType)){
				payResultNotice.setPayType("5");
			}
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
			String callBack = SysConfig.serverUrl + "/cashierDesk/eskPayNotify";
			
			System.out.println("-------------通知地址------" + callBack + "------------");
			
			// 调用支付通道
			
			String serverUrl = ESKConfig.msServerUrl;
			
			String tranCode = "001";
//			String tranCode = "SMZF002";
			String charset = "utf-8";
			
			JSONObject reqData = new JSONObject();
			String eskPayType = payType;
			if("1".equals(payType)){
				reqData.put("merchantCode", merchantCode.getWxMerchantCode());
				//bodyjson.put("subAppid", WxConfig.appid);暂时不用
			}else if("2".equals(payType)){
				reqData.put("merchantCode", merchantCode.getZfbMerchantCode());
				eskPayType="0";
			}else if("3".equals(payType)){
				reqData.put("merchantCode", merchantCode.getQqMerchantCode());
			}else if("4".equals(payType)){
				reqData.put("merchantCode", merchantCode.getBdMerchantCode());
			}else if("5".equals(payType)){
				reqData.put("merchantCode", merchantCode.getJdMerchantCode());
			}
			
			
			reqData.put("scene", "1");
			reqData.put("tranCode", tranCode);
			reqData.put("totalAmount", payMoney);
			reqData.put("orderNumber", orderCode);
			reqData.put("aisleType", aisleType);
			reqData.put("subject", memberInfo.getName() + " 收款");
			reqData.put("PayType", eskPayType);
			reqData.put("desc", memberInfo.getName() + " 收款");
			reqData.put("callback", callBack);
			System.out.println("待加密数据: "+reqData);
			
			String plainXML = reqData.toString();
			byte[] plainBytes = plainXML.getBytes(charset);
			String keyStr = MSCommonUtil.generateLenString(16);
			;
			byte[] keyBytes = keyStr.getBytes(charset);
			String encryptData = new String(Base64.encodeBase64((CryptoUtil.AESEncrypt(plainBytes, keyBytes, "AES", "AES/ECB/PKCS5Padding", null))), charset);
			//先用对方给的私钥调试 by linxf
			//String signData = new String(Base64.encodeBase64(CryptoUtil.digitalSign(plainBytes, hzfPriKey, "SHA1WithRSA")), charset);
			String signData = new String(Base64.encodeBase64(Key.rsaSign(plainBytes, ESKConfig.privateKey)), charset);
			String encrtptKey = new String(Base64.encodeBase64(Key.jdkRSA(keyBytes, ESKConfig.yhPublicKey)), charset);
			List<NameValuePair> nvps = new LinkedList<NameValuePair>();
			nvps.add(new BasicNameValuePair("Context", encryptData));
			nvps.add(new BasicNameValuePair("encrtpKey", encrtptKey));
			
			nvps.add(new BasicNameValuePair("signData", signData));
			nvps.add(new BasicNameValuePair("agentId", ESKConfig.agentId));
			byte[] b = HttpClient4Util.getInstance().doPost(serverUrl, null, nvps);
			String respStr = new String(b, charset);
			logger.info("返回报文[{}]", new Object[] { respStr });
			
			JSONObject jsonObject = JSONObject.fromObject(respStr);
			String resEncryptData = jsonObject.getString("Context");
			String resEncryptKey = jsonObject.getString("encrtpKey");
			byte[] decodeBase64KeyBytes = Base64.decodeBase64(resEncryptKey.getBytes(charset));
			// 解密encryptKey得到merchantAESKey
			//先用对方给的私钥调试 by linxf
			//byte[] merchantAESKeyBytes = CryptoUtil.RSADecrypt(decodeBase64KeyBytes, hzfPriKey, 2048, 11, "RSA/ECB/PKCS1Padding");
			byte[] merchantAESKeyBytes = Key.jdkRSA_(decodeBase64KeyBytes, ESKConfig.privateKey);
			// 使用base64解码商户请求报文
			byte[] decodeBase64DataBytes = Base64.decodeBase64(resEncryptData.getBytes(charset));
			// 用解密得到的merchantAESKey解密encryptData
			byte[] merchantXmlDataBytes = CryptoUtil.AESDecrypt(decodeBase64DataBytes, merchantAESKeyBytes, "AES", "AES/ECB/PKCS5Padding", null);
			String resXml = new String(merchantXmlDataBytes, charset);
			JSONObject respJSONObject = JSONObject.fromObject(resXml);
			logger.info("返回报文[{}]",  respJSONObject );
//			resData.put("orderCode", orderCode);     //暂不回传我方的订单编码
			if (respJSONObject.containsKey("qrCode")) {//民生传回qrCode时 不一定是S状态
				result.put("qrCode", SecurityUtil.base64Encode(respJSONObject.get("qrCode").toString()));
				//result.put("resData", resData);
				result.put("returnCode", "0000");
				result.put("returnMsg", "成功");
			}else{
				result.put("returnCode", "0003");
				result.put("returnMsg", "MS-"+respJSONObject.getString("respCode")+ ":" +respJSONObject.getString("respMsg"));
			}
		} catch (ArgException e) {
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
			logger.info(e.getMessage());
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
			return result;
		}
		return result;
	}
	
	
	/**
	 *  该接口供APP端SDK支付时使用   
	 *  该接口暂时只支持微信支付，即payType只能为1
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/cashierDesk/appPay")
	public JSONObject weixinPay(HttpServletRequest request,HttpServletResponse response){
		String payMoney = request.getParameter("payMoney");
		String payType = request.getParameter("payType");
		String orderNum = request.getParameter("orderNum");
		String memberCode = request.getParameter("memberCode");
//		String callbackUrl = request.getParameter("callbackUrl");
		String signStr = request.getParameter("signStr");
		
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
			result.put("returnMsg", "平台订单号缺失");
			return result;
		}
		if(orderNum.length() > 32){
			result.put("returnCode", "0007");
			result.put("returnMsg", "订单号长度不能超过32位");
			return result;
		}
		srcStr.append("&orderNum="+orderNum);
		if(null == payMoney || !ValidateUtil.isDoubleT(payMoney) || Double.parseDouble(payMoney)<=0){
			result.put("returnCode", "0005");
			result.put("returnMsg", "支付金额输入不正确");
			return result;
		}
		srcStr.append("&payMoney="+payMoney);
		if(signStr == null || "".equals(signStr)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "缺少签名信息");
			return result;
		}
		if(!"1".equals(payType)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "支付方式错误");
			return result;
		}
		srcStr.append("&payType="+payType);
		
		result = validMemberInfoForAppPay(memberCode, orderNum, payType, payMoney, srcStr.toString(), signStr);
		
		return result;
	}
	
	public JSONObject validMemberInfoForAppPay(String memberCode,String orderNum,String payType,String payMoney,String signOrginalStr,String signedStr){
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
		
		SysOffice sysOffice = sysOfficeList.get(0);
		String singedStr = EpaySignUtil.sign(sysOffice.getPrivateKeyRsa(), signOrginalStr);
		System.out.println(singedStr);
		if(!EpaySignUtil.checksign(sysOffice.getPublicKeyRsa(), signOrginalStr, signedStr)){
			result.put("returnCode", "0004");
			result.put("returnMsg", "签名校验错误，请检查签名参数是否正确");
			return result;
		}
		
		result = msWeixinAppPay(memberInfo, payType, payMoney, orderNum);
		
		return result;
	}
	
	/**
	 * 民生微信APP支付
	 * @param platformType
	 * @param memberInfo
	 * @param payMoney
	 * @param orderNumOuter
	 * @param callbackUrl
	 * @return
	 */
	public JSONObject msWeixinAppPay(MemberInfo memberInfo,String payType,String payMoney,String orderNumOuter) {
		JSONObject result = new JSONObject();
		JSONObject resData = new JSONObject();
		try {
			// 插入一条收款记录
			String orderCode = CommonUtil.getOrderCode();
			
			DebitNote debitNote = new DebitNote();
			debitNote.setCreateDate(new Date());
			debitNote.setMemberId(memberInfo.getId());
			debitNote.setMoney(new BigDecimal(payMoney));
			debitNote.setOrderCode(orderCode);
			debitNote.setRouteId(memberInfo.getZfbRouteId());
			debitNote.setStatus("0");
			if("1".equals(payType)){
				debitNote.setTxnType("1");
				debitNote.setMemberCode(memberInfo.getWxMemberCode());
				debitNote.setMerchantCode(memberInfo.getWxMerchantCode());
			}else {
				debitNote.setTxnType("2");
				debitNote.setMemberCode(memberInfo.getZfbMemberCode());
				debitNote.setMerchantCode(memberInfo.getZfbMerchantCode());
			}
			debitNote.setMemberCode(memberInfo.getWxMemberCode());
			debitNote.setMerchantCode(memberInfo.getWxMerchantCode());
			debitNote.setSettleType(memberInfo.getSettleType());
			
			debitNoteService.insertSelective(debitNote);
			
			PayResultNotice payResultNotice = new PayResultNotice();
			payResultNotice.setOrderCode(debitNote.getOrderCode());
			payResultNotice.setOrderNumOuter(orderNumOuter);
			payResultNotice.setPayMoney(debitNote.getMoney());
			payResultNotice.setMemberCode(memberInfo.getCode());
			if("1".equals(payType)){
				payResultNotice.setPayType("1");
			}else{
				payResultNotice.setPayType("2");
			}
			payResultNotice.setReturnUrl("");
			payResultNotice.setStatus("1");
			payResultNotice.setCreateDate(new Date());
			payResultNotice.setPlatformType("3");
			payResultNotice.setInterfaceType("3");
			PayResultNoticeExample payResultNoticeExample = new PayResultNoticeExample();
			payResultNoticeExample.or().andMemberCodeEqualTo(memberInfo.getCode()).andOrderNumOuterEqualTo(orderNumOuter).andStatusEqualTo("1");
			payResultNoticeService.deleteByExample(payResultNoticeExample);
			payResultNoticeService.insertSelective(payResultNotice);
			
			// String callBack = "http://" + request.getServerName() + ":" +
			// request.getServerPort() + request.getContextPath()+
			// "/debitNote/msNotify";
//			String callBack = SysConfig.serverUrl + "/cashierDesk/msPayNotify";
			
			// 调用支付通道
			String serverUrl = MSConfig.msServerUrl;
			PublicKey yhPubKey = null;
			if (serverUrl.startsWith("https://ipay")) {
				yhPubKey = CryptoUtil.getRSAPublicKey(true);
			} else {
				yhPubKey = CryptoUtil.getRSAPublicKey(false);
			}
			PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKey();
			String tranCode = TranCodeConstant.WXAPPZF;
			String charset = "utf-8";
			StringBuilder sBuilder = new StringBuilder();
			sBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			sBuilder.append("<merchant>");
			sBuilder.append("<head>");
			sBuilder.append("<version>1.0.0</version>");
			sBuilder.append("<msgType>01</msgType>");
			sBuilder.append("<reqDate>" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + "</reqDate>");
			sBuilder.append("</head>");
			sBuilder.append("<body>");
			if("1".equals(payType)){
				sBuilder.append("<merchantCode>" + memberInfo.getWxMerchantCode() + "</merchantCode>");
				sBuilder.append("<subAppid>" + WxConfig.appid + "</subAppid>");
			}else{
				sBuilder.append("<merchantCode>" + memberInfo.getZfbMerchantCode() + "</merchantCode>");
			}
			sBuilder.append("<totalAmount>" + payMoney + "</totalAmount>");
			sBuilder.append("<subject>" + memberInfo.getName() + " 收款" + "</subject>");
			sBuilder.append("</body>");
			sBuilder.append("</merchant>");
			String plainXML = sBuilder.toString();
			byte[] plainBytes = plainXML.getBytes(charset);
			String keyStr = MSCommonUtil.generateLenString(16);
			byte[] keyBytes = keyStr.getBytes(charset);
			String encryptData = new String(Base64.encodeBase64((CryptoUtil.AESEncrypt(plainBytes, keyBytes, "AES", "AES/ECB/PKCS5Padding", null))), charset);
			String signData = new String(Base64.encodeBase64(CryptoUtil.digitalSign(plainBytes, hzfPriKey, "SHA1WithRSA")), charset);
			String encrtptKey = new String(Base64.encodeBase64(CryptoUtil.RSAEncrypt(keyBytes, yhPubKey, 2048, 11, "RSA/ECB/PKCS1Padding")), charset);
			List<NameValuePair> nvps = new LinkedList<NameValuePair>();
			nvps.add(new BasicNameValuePair("encryptData", encryptData));
			nvps.add(new BasicNameValuePair("encryptKey", encrtptKey));
			//nvps.add(new BasicNameValuePair("cooperator", MSConfig.cooperator_t0));
			
			if("0".equals(memberInfo.getSettleType())){
				nvps.add(new BasicNameValuePair("cooperator", MSConfig.cooperator_t0));
			}else{
				nvps.add(new BasicNameValuePair("cooperator", MSConfig.cooperator_t1));
			}
			
			nvps.add(new BasicNameValuePair("signData", signData));
			nvps.add(new BasicNameValuePair("tranCode", tranCode));
//			nvps.add(new BasicNameValuePair("callBack", callBack));
			nvps.add(new BasicNameValuePair("reqMsgId", orderCode));
			byte[] b = HttpClient4Util.getInstance().doPost(serverUrl, null, nvps);
			String respStr = new String(b, charset);
			logger.info("返回报文[{}]", new Object[] { respStr });
			JSONObject jsonObject = JSONObject.fromObject(respStr);
			String resEncryptData = jsonObject.getString("encryptData");
			String resEncryptKey = jsonObject.getString("encryptKey");
			byte[] decodeBase64KeyBytes = Base64.decodeBase64(resEncryptKey.getBytes(charset));
			// 解密encryptKey得到merchantAESKey
			byte[] merchantAESKeyBytes = CryptoUtil.RSADecrypt(decodeBase64KeyBytes, hzfPriKey, 2048, 11, "RSA/ECB/PKCS1Padding");
			// 使用base64解码商户请求报文
			byte[] decodeBase64DataBytes = Base64.decodeBase64(resEncryptData.getBytes(charset));
			// 用解密得到的merchantAESKey解密encryptData
			byte[] merchantXmlDataBytes = CryptoUtil.AESDecrypt(decodeBase64DataBytes, merchantAESKeyBytes, "AES", "AES/ECB/PKCS5Padding", null);
			String resXml = new String(merchantXmlDataBytes, charset);
			JSONObject respJSONObject = XmlConvertUtil.documentToJSONObjectFilter(resXml);
//			resData.put("orderCode", orderCode);     //暂不回传我方的订单编码
			if (respJSONObject.containsKey("resEntity")) {
				JSONObject resEntity = respJSONObject.getJSONObject("resEntity");
				if (resEntity.containsKey("wxjsapiStr")) {
					JSONObject wxjsapiStr=resEntity.getJSONObject("wxjsapiStr");
//					wxjsapiStr.put("package_str", wxjsapiStr.getString("package"));
					resData.put("wxjsapiStr", wxjsapiStr);
					result.put("resData", resData);
				}
				result.put("returnCode", "0000");
				result.put("returnMsg", "成功");
			}else{
				result.put("returnCode", "0003");
				result.put("returnMsg", "MS-"+respJSONObject.getString("respCode")+ ":" +respJSONObject.getString("respMsg"));
			}
			
		} catch (ArgException e) {
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
			logger.info(e.getMessage());
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
			return result;
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/cashierDesk/payResultQuery")
	public JSONObject msTransactionQuery(HttpServletRequest request,HttpServletResponse response) {
		
		String orderNum = request.getParameter("orderNum");
		String memberCode = request.getParameter("memberCode");
		String signStr = request.getParameter("signStr");
		StringBuilder srcStr = new StringBuilder();
		JSONObject result = new JSONObject();
		if(null == memberCode || "".equals(memberCode)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "商户号缺失");
			return result;
		}
		srcStr.append("memberCode="+memberCode);
		if(null == orderNum || "".equals(orderNum)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "订单号缺失");
			return result;
		}
		srcStr.append("&orderNum="+orderNum);
		if(null == signStr || "".equals(signStr)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "签名信息缺失");
			return result;
		}
		
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
		
		SysOffice sysOffice = sysOfficeList.get(0);
		String singedStr = EpaySignUtil.sign(sysOffice.getPrivateKeyRsa(), srcStr.toString());
		System.out.println(singedStr);
		if(!EpaySignUtil.checksign(sysOffice.getPublicKeyRsa(), srcStr.toString(), signStr)){
			result.put("returnCode", "0004");
			result.put("returnMsg", "签名校验错误，请检查签名参数是否正确");
			return result;
		}
		
		try {
			String reqMsgId=CommonUtil.getOrderCode();
			//查询商户订单相关信息
			PayResultNoticeExample payResultNoticeExample = new PayResultNoticeExample();
			payResultNoticeExample.or().andMemberCodeEqualTo(memberCode).andOrderNumOuterEqualTo(orderNum);
			List<PayResultNotice> payResultNoticeList = payResultNoticeService.selectByExample(payResultNoticeExample);
			if(null == payResultNoticeList || payResultNoticeList.size() == 0){
				result.put("returnCode", "0001");
				result.put("returnMsg", "当前商户下不存在该订单号");
				return result;
			}
			
			PayResultNotice payResultNotice = payResultNoticeList.get(0);
			
			DebitNoteExample debitNoteExample = new DebitNoteExample();
			debitNoteExample.or().andOrderCodeEqualTo(payResultNotice.getOrderCode());
			List<DebitNote> debitNotes = debitNoteService.selectByExample(debitNoteExample);
			if(null == debitNotes || debitNotes.size() == 0){
				result.put("returnCode", "0096");
				result.put("returnMsg", "交易信息不存在");
				return result;
			}
			if("3".equals(payResultNotice.getStatus()) && "2".equals(payResultNotice.getRespType())){
				result.put("returnCode", "0000");
				result.put("returnMsg", "交易处理成功");
				return result;
			}
			
			DebitNote debitNote=debitNotes.get(0);
			String orderCode = debitNote.getOrderCode();
			
			String serverUrl = MSConfig.msServerUrl;
			PublicKey yhPubKey = null;
			if (serverUrl.startsWith("https://ipay")) {
				yhPubKey = CryptoUtil.getRSAPublicKey(true);
			} else {
				yhPubKey = CryptoUtil.getRSAPublicKey(false);
			}
			PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKey();
			String tranCode = "SMZF006";
			String charset = "utf-8";
			StringBuilder sBuilder = new StringBuilder();
			sBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			sBuilder.append("<merchant>");
			sBuilder.append("<head>");
			sBuilder.append("<version>1.0.0</version>");
			sBuilder.append("<msgType>01</msgType>");
			sBuilder.append("<reqDate>" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + "</reqDate>");
			sBuilder.append("</head>");
			sBuilder.append("<body>");
			sBuilder.append("<oriReqMsgId>" + orderCode + "</oriReqMsgId>");
			sBuilder.append("</body>");
			sBuilder.append("</merchant>");
			String plainXML = sBuilder.toString();
			byte[] plainBytes = plainXML.getBytes(charset);
			String keyStr = MSCommonUtil.generateLenString(16);
			;
			byte[] keyBytes = keyStr.getBytes(charset);
			String encryptData = new String(Base64.encodeBase64((CryptoUtil.AESEncrypt(plainBytes, keyBytes, "AES", "AES/ECB/PKCS5Padding", null))), charset);
			String signData = new String(Base64.encodeBase64(CryptoUtil.digitalSign(plainBytes, hzfPriKey, "SHA1WithRSA")), charset);
			String encrtptKey = new String(Base64.encodeBase64(CryptoUtil.RSAEncrypt(keyBytes, yhPubKey, 2048, 11, "RSA/ECB/PKCS1Padding")), charset);
			List<NameValuePair> nvps = new LinkedList<NameValuePair>();
			nvps.add(new BasicNameValuePair("encryptData", encryptData));
			nvps.add(new BasicNameValuePair("encryptKey", encrtptKey));
			
			if("0".equals(debitNote.getSettleType())){
				nvps.add(new BasicNameValuePair("cooperator", MSConfig.cooperator_t0));
			}else{
				nvps.add(new BasicNameValuePair("cooperator", MSConfig.cooperator_t1));
			}
			nvps.add(new BasicNameValuePair("signData", signData));
			nvps.add(new BasicNameValuePair("tranCode", tranCode));
			nvps.add(new BasicNameValuePair("reqMsgId", reqMsgId));
			byte[] b = HttpClient4Util.getInstance().doPost(serverUrl, null, nvps);
			String respStr = new String(b, charset);
			logger.info("返回报文[{}]", new Object[] { respStr });
			JSONObject jsonObject = JSONObject.fromObject(respStr);
			String resEncryptData = jsonObject.getString("encryptData");
			String resEncryptKey = jsonObject.getString("encryptKey");
			byte[] decodeBase64KeyBytes = Base64.decodeBase64(resEncryptKey.getBytes(charset));
			// 解密encryptKey得到merchantAESKey
			byte[] merchantAESKeyBytes = CryptoUtil.RSADecrypt(decodeBase64KeyBytes, hzfPriKey, 2048, 11, "RSA/ECB/PKCS1Padding");
			// 使用base64解码商户请求报文
			byte[] decodeBase64DataBytes = Base64.decodeBase64(resEncryptData.getBytes(charset));
			// 用解密得到的merchantAESKey解密encryptData
			byte[] merchantXmlDataBytes = CryptoUtil.AESDecrypt(decodeBase64DataBytes, merchantAESKeyBytes, "AES", "AES/ECB/PKCS5Padding", null);
			String resXml = new String(merchantXmlDataBytes, charset);
			JSONObject respJSONObject = XmlConvertUtil.documentToJSONObjectFilter(resXml);
			System.out.println(respJSONObject);
			if (respJSONObject.containsKey("resEntity")) {
				JSONObject resEntity = respJSONObject.getJSONObject("resEntity");
				TradeDetailExample tradeDetailExample = new TradeDetailExample();
				tradeDetailExample.or().andOrderCodeEqualTo(debitNote.getOrderCode());
				List<TradeDetail> tradeDetailList = tradeDetailService.selectByExample(tradeDetailExample);
				TradeDetail tradeDetail ;
				if(null == tradeDetailList || tradeDetailList.size() == 0){
					tradeDetail = new TradeDetail();
					tradeDetail.setTxnDate(DateUtil.getDateFormat(new Date(), "yyyyMMdd"));
					tradeDetail.setMemberId(debitNote.getMemberId());
					tradeDetail.setMerchantCode(debitNote.getMerchantCode());
					tradeDetail.setBalanceDate(resEntity.get("settleDate").toString());
					tradeDetail.setChannelNo(resEntity.get("channelNo").toString());
					tradeDetail.setMemberCode(debitNote.getMemberCode());
					tradeDetail.setMoney(debitNote.getMoney());
					tradeDetail.setPayTime(resEntity.get("payTime").toString());
					tradeDetail.setPtSerialNo(debitNote.getOrderCode());
					tradeDetail.setOrderCode(debitNote.getOrderCode());
					tradeDetail.setReqDate(DateUtil.getDateTimeStr(debitNote.getCreateDate()));
					tradeDetail.setRespCode(resEntity.getString("oriRespCode"));
					tradeDetail.setRespDate(DateUtil.getDateTimeStr(new Date()));
					tradeDetail.setRespMsg(resEntity.getString("oriRespMsg"));
					tradeDetail.setRouteId(debitNote.getRouteId());
					tradeDetail.setTxnType(debitNote.getTxnType());
					tradeDetail.setDelFlag("0");
					tradeDetail.setCreateDate(new Date());
				}else{
					tradeDetail = tradeDetailList.get(0);
					tradeDetail.setUpdateDate(new Date());
				}
				debitNote.setRespCode(resEntity.getString("oriRespCode"));
				debitNote.setRespMsg(resEntity.getString("oriRespMsg"));
				
				JSONObject resData = new JSONObject();
				if("S".equals(resEntity.get("oriRespType")) && "000000".equals("oriRespCode")){//成功
					debitNote.setStatus("1");
					tradeDetail.setRespType("S");
					payResultNotice.setRespType("2");
					resData.put("resultCode", "0000");
					resData.put("resultMsg", "交易成功");
				}else if("E".equals(resEntity.get("oriRespType"))){//失败
					debitNote.setStatus("2");
					tradeDetail.setRespType("E");
					payResultNotice.setRespType("3");
					resData.put("resultCode", "0003");
					resData.put("resultMsg", resEntity.getString("oriRespMsg"));
				}else{//处理中
					debitNote.setStatus("3");
					tradeDetail.setRespType("R");
					payResultNotice.setRespType("1");
					resData.put("resultCode", "0003");
					resData.put("resultMsg", resEntity.getString("oriRespMsg"));
				}
				payResultNotice.setStatus("3");
				payResultNoticeService.updateByPrimaryKeySelective(payResultNotice);
				debitNoteService.updateByPrimaryKeySelective(debitNote);
				if(null == tradeDetailList || tradeDetailList.size() == 0){
					tradeDetailService.insertSelective(tradeDetail);
				}else{
					tradeDetailService.updateByPrimaryKeySelective(tradeDetail);
				}
				
				
				resData.put("memberCode", payResultNotice.getMemberCode());
				//第三方平台订单号
				resData.put("orderNum", payResultNotice.getOrderNumOuter());
				//订单支付金额
				resData.put("payMoney", payResultNotice.getPayMoney());
				//收银台系统支付流水号
				resData.put("payNum", payResultNotice.getOrderCode());
				//支付时间
				resData.put("payTime", payResultNotice.getPayTime());
				//支付类型
				resData.put("payType", payResultNotice.getPayType());
				//平台类型
				resData.put("platformType", payResultNotice.getPlatformType());
				
				result.put("resData", resData);
			}

		} catch (ArgException e) {
			result.put("returnCode", "4004");
			result.put("returnMsg", e.getMessage());
			logger.info(e.getMessage());
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
			return result;
		}
		result.put("returnCode", "0000");
		result.put("returnMsg", "成功");
		return result;
	}
	
	//发起提现功能
	public void msWithdrawsaasd(Integer memberId,String memberCode,String merchantCode,BigDecimal memberTradeRate) {
		try {
			
			String orderCode =CommonUtil.getOrderCode();
			//创建提现明细
			RoutewayDraw routewayDraw=new RoutewayDraw();
			routewayDraw.setCreateDate(new Date());
			routewayDraw.setDelFlag("0");
			routewayDraw.setMemberCode(memberCode);
			routewayDraw.setMemberId(memberId);
			routewayDraw.setMemberTradeRate(memberTradeRate);
			routewayDraw.setOrderCode(orderCode);
			routewayDraw.setPtSerialNo(orderCode);
			routewayDraw.setReqDate(DateUtil.getDateTimeStr(new Date()));
			routewayDrawService.insertSelective(routewayDraw);
			
			
			String callBack = SysConfig.serverUrl + "/debitNote/msWithdrawNotify";
			System.out.println("-------------通知地址------" + callBack + "------------");

			// 调用支付通道
			
			String serverUrl = MSConfig.msServerUrl;
			PublicKey yhPubKey = null;
			if (serverUrl.startsWith("https://ipay")) {
				yhPubKey = CryptoUtil.getRSAPublicKey(true);
			} else {
				yhPubKey = CryptoUtil.getRSAPublicKey(false);
			}
			PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKey();
			String tranCode = "SMZF021";
			String charset = "utf-8";
			StringBuilder sBuilder = new StringBuilder();
			sBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			sBuilder.append("<merchant>");
			sBuilder.append("<head>");
			sBuilder.append("<version>1.0.0</version>");
			sBuilder.append("<msgType>01</msgType>");
			sBuilder.append("<reqDate>" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + "</reqDate>");
			sBuilder.append("</head>");
			sBuilder.append("<body>");
			sBuilder.append("<merchantCode>" + merchantCode + "</merchantCode>");
			sBuilder.append("</body>");
			sBuilder.append("</merchant>");
			String plainXML = sBuilder.toString();
			byte[] plainBytes = plainXML.getBytes(charset);
			String keyStr = MSCommonUtil.generateLenString(16);
			;
			byte[] keyBytes = keyStr.getBytes(charset);
			String encryptData = new String(Base64.encodeBase64((CryptoUtil.AESEncrypt(plainBytes, keyBytes, "AES", "AES/ECB/PKCS5Padding", null))), charset);
			String signData = new String(Base64.encodeBase64(CryptoUtil.digitalSign(plainBytes, hzfPriKey, "SHA1WithRSA")), charset);
			String encrtptKey = new String(Base64.encodeBase64(CryptoUtil.RSAEncrypt(keyBytes, yhPubKey, 2048, 11, "RSA/ECB/PKCS1Padding")), charset);
			List<NameValuePair> nvps = new LinkedList<NameValuePair>();
			nvps.add(new BasicNameValuePair("encryptData", encryptData));
			nvps.add(new BasicNameValuePair("encryptKey", encrtptKey));
			nvps.add(new BasicNameValuePair("cooperator", MSConfig.cooperator_t0));
			nvps.add(new BasicNameValuePair("signData", signData));
			nvps.add(new BasicNameValuePair("tranCode", tranCode));
			nvps.add(new BasicNameValuePair("callBack", callBack));
			nvps.add(new BasicNameValuePair("reqMsgId", orderCode));
			byte[] b = HttpClient4Util.getInstance().doPost(serverUrl, null, nvps);
			String respStr = new String(b, charset);
			logger.info("返回报文[{}]", new Object[] { respStr });
			JSONObject jsonObject = JSONObject.fromObject(respStr);
			String resEncryptData = jsonObject.getString("encryptData");
			String resEncryptKey = jsonObject.getString("encryptKey");
			byte[] decodeBase64KeyBytes = Base64.decodeBase64(resEncryptKey.getBytes(charset));
			// 解密encryptKey得到merchantAESKey
			byte[] merchantAESKeyBytes = CryptoUtil.RSADecrypt(decodeBase64KeyBytes, hzfPriKey, 2048, 11, "RSA/ECB/PKCS1Padding");
			// 使用base64解码商户请求报文
			byte[] decodeBase64DataBytes = Base64.decodeBase64(resEncryptData.getBytes(charset));
			// 用解密得到的merchantAESKey解密encryptData
			byte[] merchantXmlDataBytes = CryptoUtil.AESDecrypt(decodeBase64DataBytes, merchantAESKeyBytes, "AES", "AES/ECB/PKCS5Padding", null);
			String resXml = new String(merchantXmlDataBytes, charset);
			JSONObject respJSONObject = XmlConvertUtil.documentToJSONObjectFilter(resXml);
			System.out.println(respJSONObject);
			
			routewayDraw.setRespCode(respJSONObject.getString("respCode"));
			routewayDraw.setRespDate(respJSONObject.getString("respDate"));
			routewayDraw.setRespMsg(respJSONObject.getString("respMsg"));
			routewayDraw.setRespType(respJSONObject.getString("respType"));
			routewayDrawService.updateByPrimaryKeySelective(routewayDraw);
			
		}  catch (Exception e) {
			e.printStackTrace();
			logger.error("商户："+memberId+"提现失败"+e.getMessage());
		}
	}
	
	
	/**
	 * 收银台提现接口
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/cashierDesk/draw")
	public String draw(HttpServletRequest request,HttpServletResponse response) {
		String callbackUrl = request.getParameter("callbackUrl");
		String memberCode = request.getParameter("memberCode");
		String orderNum = request.getParameter("orderNum");
		String txnType = request.getParameter("txnType");
		String signStr = request.getParameter("signStr");
		Map<String,Object> result = new HashMap<String, Object>();
		StringBuilder srcStr = new StringBuilder();
		if(StringUtils.isEmpty(callbackUrl)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "缺少回调地址");
			return result.toString();
		}
		srcStr.append("callbackUrl="+callbackUrl);
		if(StringUtils.isEmpty(memberCode)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "缺少商户编号");
			return result.toString();
		}
		srcStr.append("&memberCode="+memberCode);
		if(StringUtils.isEmpty(orderNum)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "缺少商户订单号");
			return result.toString();
		}
		srcStr.append("&orderNum="+orderNum);
		if(StringUtils.isEmpty(txnType)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "缺少交易类型");
			return result.toString();
		}
		srcStr.append("&txnType="+txnType);
		if(StringUtils.isEmpty(signStr)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "缺少签名");
			return result.toString();
		}
		
		MemberInfoExample memberInfoExample = new MemberInfoExample();
		memberInfoExample.or().andCodeEqualTo(memberCode).andDelFlagEqualTo("0");
		List<MemberInfo> memberInfoList = memberInfoService.selectByExample(memberInfoExample);
		if(null == memberInfoList || memberInfoList.size() == 0){
			result.put("returnCode", "0001");
			result.put("returnMsg", "商户信息不存在");
			return result.toString();
		}
		MemberInfo memberInfo = memberInfoList.get(0);
		result = validSigned(memberInfo, srcStr.toString(),signStr);
		if(!"0000".equals(result.get("returnCode"))){
			return result.toString();
		}
		//校验是否开通T0提现权限
		if ("1".equals(memberInfo.getDrawStatus())) {
			result.put("returnCode", "0015");
			result.put("returnMsg", "提现权限处于关闭状态，请联系客服。");
			return result.toString();
		}
		Integer memberId = memberInfo.getId();
//		String ret="";
		if("1".equals(txnType)){
			result = doDraw(memberId, memberInfo.getWxMemberCode(), memberInfo.getWxMerchantCode(),orderNum,callbackUrl,txnType,memberInfo.getT0TradeRate());//微信提现
		}else if("2".equals(txnType)){
			result = doDraw(memberId, memberInfo.getZfbMemberCode(), memberInfo.getZfbMerchantCode(),orderNum,callbackUrl,txnType,memberInfo.getT0TradeRate());//支付宝提现
		}else if("3".equals(txnType)){
			result = doDraw(memberId, memberInfo.getQqMemberCode(), memberInfo.getQqMerchantCode(),orderNum,callbackUrl,txnType,memberInfo.getT0TradeRate());//qq钱包提现
		}else if("4".equals(txnType)){
			result = doDraw(memberId, memberInfo.getBdMemberCode(), memberInfo.getBdMerchantCode(),orderNum,callbackUrl,txnType,memberInfo.getT0TradeRate());//百度钱包提现
		}else if("5".equals(txnType)){
			result = doDraw(memberId, memberInfo.getJdMemberCode(), memberInfo.getJdMerchantCode(),orderNum,callbackUrl,txnType,memberInfo.getT0TradeRate());//京东金融提现
		}
		return JsonBeanReleaseUtil.beanToJson(result, "yyyy-MM-dd HH:mm:ss");
	}
	/**
	 * 校验签名
	 * @param memberInfo
	 * @param srcStr 明码串
	 * @param signStr 签名串
	 * @return
	 */
	public Map<String,Object> validSigned(MemberInfo memberInfo,String srcStr, String signStr){
		Map<String,Object> result = new HashMap<String, Object>();
		if(!"0".equals(memberInfo.getStatus())){
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
		SysOffice sysOffice = sysOfficeList.get(0);
//		String singedStr = EpaySignUtil.sign(sysOffice.getPrivateKeyRsa(), srcStr.toString());
//		System.out.println(singedStr);
		if(!EpaySignUtil.checksign(sysOffice.getPublicKeyRsa(), srcStr.toString(), signStr)){
			result.put("returnCode", "0004");
			result.put("returnMsg", "签名校验错误，请检查签名参数是否正确");
			return result;
		}
		result.put("returnCode", "0000");
		return result;
	}
	/**
	 * 收银台提现
	 * @param memberId
	 * @param memberCode 商户在scan里面的编号
	 * @param merchantCode 商户在银行的商户编号（分渠道）
	 * @param orderNum 商户订单号
	 * @param callbackUrl 回调地址
	 * @param txnType 类型1:微信 2:支付宝 3:QQ钱包
	 * @return
	 */
	public Map<String, Object> doDraw(Integer memberId,String memberCode,String merchantCode, String orderNum, String callbackUrl, String txnType,BigDecimal memberTradeRate){
		Map<String,Object> result = new HashMap<String, Object>();
		try {
			String orderCode =CommonUtil.getOrderCode();
			//创建提现明细
			RoutewayDraw routewayDraw=new RoutewayDraw();//记录民生到scan的信息
			routewayDraw.setCreateDate(new Date());
			routewayDraw.setDelFlag("0");
			routewayDraw.setMemberCode(memberCode);
			routewayDraw.setMemberId(memberId);
			routewayDraw.setMemberTradeRate(memberTradeRate);
			routewayDraw.setMerchantCode(merchantCode);
			routewayDraw.setOrderCode(orderCode);
			routewayDraw.setPtSerialNo(orderCode);
			routewayDraw.setReqDate(DateUtil.getDateTimeStr(new Date()));
			routewayDrawService.insertSelective(routewayDraw);
			
			DrawResultNotice drawResultNotice = new DrawResultNotice();//记录scan到其他商户的信息
			drawResultNotice.setMemberId(memberId);
			drawResultNotice.setOrderCode(orderCode);
			drawResultNotice.setOrderNumOuter(orderNum);
			drawResultNotice.setReturnUrl(callbackUrl);
			drawResultNotice.setCounts(0);
			drawResultNotice.setStatus("1");
			drawResultNotice.setTxnType(txnType);
			drawResultNotice.setDrawTime(DateUtil.getDateTimeStr(new Date()));
			drawResultNotice.setCreateDate(new Date());
			drawResultNoticeService.insertSelective(drawResultNotice);
			
			String callBack = SysConfig.serverUrl + "/cashierDesk/msWithdrawNotify";
			System.out.println("-------------通知地址------" + callBack + "------------");
			
			// 调用支付通道
			
			String serverUrl = MSConfig.msServerUrl;
			PublicKey yhPubKey = null;
			if (serverUrl.startsWith("https://ipay")) {
				yhPubKey = CryptoUtil.getRSAPublicKey(true);
			} else {
				yhPubKey = CryptoUtil.getRSAPublicKey(false);
			}
			PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKey();
			String tranCode = "SMZF021";
			String charset = "utf-8";
			StringBuilder sBuilder = new StringBuilder();
			sBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			sBuilder.append("<merchant>");
			sBuilder.append("<head>");
			sBuilder.append("<version>1.0.0</version>");
			sBuilder.append("<msgType>01</msgType>");
			sBuilder.append("<reqDate>" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + "</reqDate>");
			sBuilder.append("</head>");
			sBuilder.append("<body>");
			sBuilder.append("<merchantCode>" + merchantCode + "</merchantCode>");
			sBuilder.append("</body>");
			sBuilder.append("</merchant>");
			String plainXML = sBuilder.toString();
			byte[] plainBytes = plainXML.getBytes(charset);
			String keyStr = MSCommonUtil.generateLenString(16);
			;
			byte[] keyBytes = keyStr.getBytes(charset);
			String encryptData = new String(Base64.encodeBase64((CryptoUtil.AESEncrypt(plainBytes, keyBytes, "AES", "AES/ECB/PKCS5Padding", null))), charset);
			String signData = new String(Base64.encodeBase64(CryptoUtil.digitalSign(plainBytes, hzfPriKey, "SHA1WithRSA")), charset);
			String encrtptKey = new String(Base64.encodeBase64(CryptoUtil.RSAEncrypt(keyBytes, yhPubKey, 2048, 11, "RSA/ECB/PKCS1Padding")), charset);
			List<NameValuePair> nvps = new LinkedList<NameValuePair>();
			nvps.add(new BasicNameValuePair("encryptData", encryptData));
			nvps.add(new BasicNameValuePair("encryptKey", encrtptKey));
			nvps.add(new BasicNameValuePair("cooperator", MSConfig.cooperator_t0));
			nvps.add(new BasicNameValuePair("signData", signData));
			nvps.add(new BasicNameValuePair("tranCode", tranCode));
			nvps.add(new BasicNameValuePair("callBack", callBack));
			nvps.add(new BasicNameValuePair("reqMsgId", orderCode));
			byte[] b = HttpClient4Util.getInstance().doPost(serverUrl, null, nvps);
			String respStr = new String(b, charset);
			JSONObject jsonObject = JSONObject.fromObject(respStr);
			String resEncryptData = jsonObject.getString("encryptData");
			String resEncryptKey = jsonObject.getString("encryptKey");
			byte[] decodeBase64KeyBytes = Base64.decodeBase64(resEncryptKey.getBytes(charset));
			// 解密encryptKey得到merchantAESKey
			byte[] merchantAESKeyBytes = CryptoUtil.RSADecrypt(decodeBase64KeyBytes, hzfPriKey, 2048, 11, "RSA/ECB/PKCS1Padding");
			// 使用base64解码商户请求报文
			byte[] decodeBase64DataBytes = Base64.decodeBase64(resEncryptData.getBytes(charset));
			// 用解密得到的merchantAESKey解密encryptData
			byte[] merchantXmlDataBytes = CryptoUtil.AESDecrypt(decodeBase64DataBytes, merchantAESKeyBytes, "AES", "AES/ECB/PKCS5Padding", null);
			String resXml = new String(merchantXmlDataBytes, charset);
			logger.info("收银台提现返回报文[{}]", resXml);
			JSONObject respJSONObject = XmlConvertUtil.documentToJSONObjectFilter(resXml);
			
			routewayDraw.setRespCode(respJSONObject.getString("respCode"));
			routewayDraw.setRespDate(respJSONObject.getString("respDate"));
			routewayDraw.setRespMsg(respJSONObject.getString("respMsg"));
			routewayDraw.setRespType(respJSONObject.getString("respType"));
			routewayDrawService.updateByPrimaryKeySelective(routewayDraw);
			
			if("S".equals(respJSONObject.getString("respType"))){
				drawResultNotice.setRespType("2");
				drawResultNotice.setResultCode("0000");
				drawResultNotice.setResultMessage("交易成功");
			}else if("R".equals(respJSONObject.getString("respType"))){
				drawResultNotice.setRespType("1");
				drawResultNotice.setResultCode("0003");   
				drawResultNotice.setResultMessage("交易正在处理中...");
			}else{
				drawResultNotice.setRespType("3");
				drawResultNotice.setResultCode("0003");   
				drawResultNotice.setResultMessage(respJSONObject.getString("respMsg"));
//				drawResultNotice.setResultMessage("交易失败");
			}
			drawResultNoticeService.updateByPrimaryKeySelective(drawResultNotice);
			result.put("returnCode", drawResultNotice.getResultCode());
			result.put("returnMsg", drawResultNotice.getResultMessage());
			return result;
		}  catch (Exception e) {
			e.printStackTrace();
			logger.error("商户："+memberId+"提现失败"+e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", "提现失败");
			return result;
		}
	}
	
	
	@RequestMapping("/cashierDesk/msWithdrawNotify")
	public void msWithdrawNotify(HttpServletRequest request,HttpServletResponse response) {
		try {
			String resEncryptData = request.getParameter("encryptData");
			String resEncryptKey = request.getParameter("encryptKey");
			String charset = "utf-8";
			PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKey();
			byte[] decodeBase64KeyBytes = Base64.decodeBase64(resEncryptKey.getBytes(charset));
			// 解密encryptKey得到merchantAESKey
			byte[] merchantAESKeyBytes = CryptoUtil.RSADecrypt(decodeBase64KeyBytes, hzfPriKey, 2048, 11, "RSA/ECB/PKCS1Padding");
			// 使用base64解码商户请求报文
			byte[] decodeBase64DataBytes = Base64.decodeBase64(resEncryptData.getBytes(charset));
			// 用解密得到的merchantAESKey解密encryptData
			byte[] merchantXmlDataBytes = CryptoUtil.AESDecrypt(decodeBase64DataBytes, merchantAESKeyBytes, "AES", "AES/ECB/PKCS5Padding", null);
			String resXml = new String(merchantXmlDataBytes, charset);
			logger.info("收银台提现回调[{}]",resXml);
			JSONObject respJSONObject = XmlConvertUtil.documentToJSONObjectFilter(resXml);
//
			/**测试数据begin**/
//			JSONObject respJSONObject = new JSONObject();
//			respJSONObject.put("respCode", "000000");
//			respJSONObject.put("smzfMsgId", "666666666666");
//			respJSONObject.put("respType", "S");
//			respJSONObject.put("respDate", "201702261815");
//			respJSONObject.put("respMsg", "测试数据啊啊啊");
//			respJSONObject.put("respType", "S");
//			JSONObject resEntityObject = new JSONObject();
//			resEntityObject.put("reqMsgId", "20170401163925404859");
//			resEntityObject.put("settleDate", "20170226");
//			resEntityObject.put("orderNum", "11223344");
//			resEntityObject.put("drawTime", "2017022618666");
//			resEntityObject.put("drawAmount", "0.01");
//			resEntityObject.put("drawFee", "0.01");
//			resEntityObject.put("tradeFee", "0.01");
//			respJSONObject.put("resEntity", resEntityObject);
			/**测试数据end**/
			JSONObject resEntity = respJSONObject.getJSONObject("resEntity");
			String reqMsgId = resEntity.getString("reqMsgId");
			RoutewayDrawExample routewayDrawExample = new RoutewayDrawExample();
			routewayDrawExample.createCriteria().andPtSerialNoEqualTo(reqMsgId);
			List<RoutewayDraw> routewayDraws = routewayDrawService.selectByExample(routewayDrawExample);

			if (routewayDraws != null && routewayDraws.size() > 0) {
				RoutewayDraw routewayDraw=routewayDraws.get(0);
				routewayDraw.setRespCode(respJSONObject.getString("respCode"));
				routewayDraw.setRespDate(respJSONObject.getString("respDate"));
				routewayDraw.setRespMsg(respJSONObject.getString("respMsg"));
				routewayDraw.setRespType(respJSONObject.getString("respType"));
				routewayDraw.setDrawamount(new BigDecimal(resEntity.getString("drawAmount")));
				routewayDraw.setDrawfee(new BigDecimal(resEntity.getString("drawFee")));
				routewayDraw.setTradefee(new BigDecimal(resEntity.getString("tradeFee")));
				routewayDraw.setSettleDate(resEntity.getString("settleDate"));
				routewayDraw.setUpdateDate(new Date());
				routewayDrawService.updateByPrimaryKey(routewayDraw);
			}
			
			DrawResultNoticeExample drawResultNoticeExample = new DrawResultNoticeExample();
			Criteria drawCriteria = drawResultNoticeExample.createCriteria();
			drawCriteria.andOrderCodeEqualTo(reqMsgId);
			List<DrawResultNotice> drawResultNotices = drawResultNoticeService.selectByExample(drawResultNoticeExample);
			if(drawResultNotices!=null && drawResultNotices.size()>0){
				DrawResultNotice drawResultNotice = drawResultNotices.get(0);
				drawResultNotice.setStatus("2");
				drawResultNotice.setDrawamount(new BigDecimal(resEntity.getString("drawAmount")));
				drawResultNotice.setDrawfee(new BigDecimal(resEntity.getString("drawFee")));
				drawResultNotice.setTradefee(new BigDecimal(resEntity.getString("tradeFee")));
				drawResultNotice.setUpdateDate(new Date());
				if("S".equals(respJSONObject.getString("respType"))){
					drawResultNotice.setRespType("2");
					drawResultNotice.setResultCode("0000");
					drawResultNotice.setResultMessage("交易成功");
				}else if("R".equals(respJSONObject.getString("respType"))){
					drawResultNotice.setRespType("1");
					drawResultNotice.setResultCode("0003");   
					drawResultNotice.setResultMessage("交易正在处理中...");
				}else{
					drawResultNotice.setRespType("3");
					drawResultNotice.setResultCode("0003");   
					drawResultNotice.setResultMessage("交易失败");
				}
				drawResultNoticeService.updateByPrimaryKeySelective(drawResultNotice);
				
				//获取民生通知后即向商户提供结果通知 (后续若因其他因素需多次通知商户,则由相应的定时任务完成)
				System.out.println("getPoolSize====" + threadPoolTaskExecutor.getPoolSize());
				DrawResultNoticeThread thread = new DrawResultNoticeThread(drawResultNotice, epayCodeService, drawResultNoticeService, memberInfoService, accountService, sysOfficeService);
				threadPoolTaskExecutor.execute(thread);
				System.out.println("getActiveCount====" + threadPoolTaskExecutor.getActiveCount());
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("提现通知处理失败:"+e.getMessage());
		}
		
		try {
			response.getWriter().write("000000");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 给第三方平台入驻接口
	 * @param model
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/api/cashierDesk/uploadCert")
	public String uploadCert(HttpServletRequest request,HttpServletResponse response) {
	
		JSONObject result=new JSONObject();
		try {
			String serverUrl = ESKConfig.shopServerUrl;
			
			PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKey();
			String tranCode = "010";
//			String tranCode = "SMZF002";
			String charset = "utf-8";
			
			String orderCode = CommonUtil.getOrderCode();
			JSONObject reqData = new JSONObject();
			reqData.put("orderNumber", orderCode);
			reqData.put("tranCode", tranCode);
			
			String uploadFilePath="C:\\work\\workspace\\";
			FileUtil fileUtil = new FileUtil();

			String cert_correct_path="1.png";
			File cert_correct_file=new File((uploadFilePath+cert_correct_path).replace('/', File.separatorChar));
			if(!cert_correct_file.exists()){
				result.put("returnCode", "0007");
				result.put("returnMsg", "身份证正面图片不存在");
				return result.toString();
			}
			reqData.put("cert_correct", fileUtil.ConvertFileToBase64(uploadFilePath+cert_correct_path));
			
			String cert_opposite_path="2.png";
			File cert_opposite_file=new File((uploadFilePath+cert_opposite_path).replace('/', File.separatorChar));
			if(!cert_opposite_file.exists()){
				result.put("returnCode", "0007");
				result.put("returnMsg", "身份证背面图片不存在");
				return result.toString();
			}
			reqData.put("cert_opposite", fileUtil.ConvertFileToBase64(uploadFilePath+cert_opposite_path));
			
			String bl_img_path="3.png";
			File bl_img_file=new File((uploadFilePath+bl_img_path).replace('/', File.separatorChar));
			if(!bl_img_file.exists()){
				result.put("returnCode", "0007");
				result.put("returnMsg", "营业执照图片不存在");
				return result.toString();
			}
			reqData.put("bl_img", fileUtil.ConvertFileToBase64(uploadFilePath+bl_img_path));
			
			String card_correct_path="4.png";
			File card_correct_file=new File((uploadFilePath+card_correct_path).replace('/', File.separatorChar));
			if(!card_correct_file.exists()){
				result.put("returnCode", "0007");
				result.put("returnMsg", "银行卡正面图片不存在");
				return result.toString();
			}
			reqData.put("card_correct", fileUtil.ConvertFileToBase64(uploadFilePath+card_correct_path));
			
			String card_opposite_path="5.png";
			File card_opposite_file=new File((uploadFilePath+card_opposite_path).replace('/', File.separatorChar));
			if(!card_opposite_file.exists()){
				result.put("returnCode", "0007");
				result.put("returnMsg", "银行卡正面图片不存在");
				return result.toString();
			}
			reqData.put("card_opposite", fileUtil.ConvertFileToBase64(uploadFilePath+card_opposite_path));
			
			System.out.println("待加密数据: "+reqData);
				
			String plainXML = reqData.toString();
			byte[] plainBytes = plainXML.getBytes(charset);
			String keyStr = MSCommonUtil.generateLenString(16);
			;
			byte[] keyBytes = keyStr.getBytes(charset);
			String encryptData = new String(Base64.encodeBase64((CryptoUtil.AESEncrypt(plainBytes, keyBytes, "AES", "AES/ECB/PKCS5Padding", null))), charset);
			//先用对方给的私钥调试 by linxf
			//String signData = new String(Base64.encodeBase64(CryptoUtil.digitalSign(plainBytes, hzfPriKey, "SHA1WithRSA")), charset);
			String signData = new String(Base64.encodeBase64(Key.rsaSign(plainBytes, ESKConfig.privateKey)), charset);
			String encrtptKey = new String(Base64.encodeBase64(Key.jdkRSA(keyBytes, ESKConfig.yhPublicKey)), charset);
			List<NameValuePair> nvps = new LinkedList<NameValuePair>();
			nvps.add(new BasicNameValuePair("Context", encryptData));
			nvps.add(new BasicNameValuePair("encrtpKey", encrtptKey));
			
			nvps.add(new BasicNameValuePair("signData", signData));
			nvps.add(new BasicNameValuePair("agentId", ESKConfig.agentId));
			byte[] b = HttpClient4Util.getInstance().doPost(serverUrl, null, nvps);
			String respStr = new String(b, charset);
			logger.info("返回报文[{}]", new Object[] { respStr });
			
			JSONObject jsonObject = JSONObject.fromObject(respStr);
			String resEncryptData = jsonObject.getString("Context");
			String resEncryptKey = jsonObject.getString("encrtpKey");
			byte[] decodeBase64KeyBytes = Base64.decodeBase64(resEncryptKey.getBytes(charset));
			// 解密encryptKey得到merchantAESKey
			//先用对方给的私钥调试 by linxf
			//byte[] merchantAESKeyBytes = CryptoUtil.RSADecrypt(decodeBase64KeyBytes, hzfPriKey, 2048, 11, "RSA/ECB/PKCS1Padding");
			byte[] merchantAESKeyBytes = Key.jdkRSA_(decodeBase64KeyBytes, ESKConfig.privateKey);
			// 使用base64解码商户请求报文
			byte[] decodeBase64DataBytes = Base64.decodeBase64(resEncryptData.getBytes(charset));
			// 用解密得到的merchantAESKey解密encryptData
			byte[] merchantXmlDataBytes = CryptoUtil.AESDecrypt(decodeBase64DataBytes, merchantAESKeyBytes, "AES", "AES/ECB/PKCS5Padding", null);
			String resXml = new String(merchantXmlDataBytes, charset);
			JSONObject respJSONObject = JSONObject.fromObject(resXml);
			logger.info("返回报文[{}]",  respJSONObject );
			
			

			result.put("returnCode", "0000");
			result.put("returnMsg", "成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
			return result.toString();
		}
		return result.toString();
	}
	
	
	
	
	@ResponseBody
	@RequestMapping("/cashierDesk/register")
	public String registerMember(HttpServletRequest request,HttpServletResponse response) {
	
		JSONObject result=new JSONObject();
		try {
			RegisterTmp registerTmp= new RegisterTmp();
			Map<String,String> params = new HashMap<String,String>();
			String officeCode = request.getParameter("officeCode");
			if(StringUtils.isNotBlank(officeCode)){
				params.put("officeCode", officeCode);
			}else{
				result.put("returnCode", "0007");
				result.put("returnMsg", "缺少officeCode参数");
				return result.toString();
			}
			String settleType = request.getParameter("settleType");
			if(StringUtils.isNotBlank(settleType)){
				params.put("settleType", settleType);
			}else{
				result.put("returnCode", "0007");
				result.put("returnMsg", "缺少settleType参数");
				return result.toString();
			}
			String merchantName = request.getParameter("merchantName");
			if(StringUtils.isNotBlank(merchantName)){
				params.put("merchantName", merchantName);
			}else{
				result.put("returnCode", "0007");
				result.put("returnMsg", "缺少merchantName参数");
				return result.toString();
			}
			String shortName = request.getParameter("shortName");
			if(StringUtils.isNotBlank(shortName)){
				params.put("shortName", shortName);
			}else{
				result.put("returnCode", "0007");
				result.put("returnMsg", "缺少shortName参数");
				return result.toString();
			}
			String contact = request.getParameter("contact");
			if(StringUtils.isNotBlank(contact)){
				params.put("contact", contact);
			}else{
				result.put("returnCode", "0007");
				result.put("returnMsg", "缺少contact参数");
				return result.toString();
			}
			String contactType = request.getParameter("contactType");
			if(StringUtils.isNotBlank(contactType)){
				params.put("contactType", contactType);
			}else{
				result.put("returnCode", "0007");
				result.put("returnMsg", "缺少contactType参数");
				return result.toString();
			}
			String mobilePhone = request.getParameter("mobilePhone");
			if(StringUtils.isNotBlank(mobilePhone)){
				params.put("mobilePhone", mobilePhone);
			}else{
				result.put("returnCode", "0007");
				result.put("returnMsg", "缺少mobilePhone参数");
				return result.toString();
			}
			String province = request.getParameter("province");
			if(StringUtils.isNotBlank(province)){
				params.put("province", province);
			}else{
				result.put("returnCode", "0007");
				result.put("returnMsg", "缺少province参数");
				return result.toString();
			}
			String city = request.getParameter("city");
			if(StringUtils.isNotBlank(city)){
				params.put("city", city);
			}else{
				result.put("returnCode", "0007");
				result.put("returnMsg", "缺少city参数");
				return result.toString();
			}
			String county = request.getParameter("county");
			if(StringUtils.isNotBlank(county)){
				params.put("county", county);
			}else{
				result.put("returnCode", "0007");
				result.put("returnMsg", "缺少county参数");
				return result.toString();
			}
			String addr = request.getParameter("addr");
			if(StringUtils.isNotBlank(addr)){
				params.put("addr", addr);
			}else{
				result.put("returnCode", "0007");
				result.put("returnMsg", "缺少addr参数");
				return result.toString();
			}
			String certNbr = request.getParameter("certNbr");
			if(StringUtils.isNotBlank(certNbr)){
				params.put("certNbr", certNbr);
			}else{
				result.put("returnCode", "0007");
				result.put("returnMsg", "缺少certNbr参数");
				return result.toString();
			}
			String bankCode = request.getParameter("bankCode");
			if(StringUtils.isNotBlank(bankCode)){
				params.put("bankCode", bankCode);
			}else{
				result.put("returnCode", "0007");
				result.put("returnMsg", "缺少bankCode参数");
				return result.toString();
			}
			String accountName = request.getParameter("accountName");
			if(StringUtils.isNotBlank(accountName)){
				params.put("accountName", accountName);
				if(!accountName.equals(contact)){
					result.put("returnCode", "0007");
					result.put("returnMsg", "银行卡账户名与联系人不一致");
					return result.toString();
				}
			}else{
				result.put("returnCode", "0007");
				result.put("returnMsg", "缺少accountName参数");
				return result.toString();
			}
			String accountNumber = request.getParameter("accountNumber");
			if(StringUtils.isNotBlank(accountNumber)){
				params.put("accountNumber", accountNumber);
			}else{
				result.put("returnCode", "0007");
				result.put("returnMsg", "缺少accountNumber参数");
				return result.toString();
			}
			String d0DrawFee = request.getParameter("d0DrawFee");
			if(StringUtils.isNotBlank(d0DrawFee)){
				params.put("d0DrawFee", d0DrawFee);
			}else{
				result.put("returnCode", "0007");
				result.put("returnMsg", "缺少d0DrawFee参数");
				return result.toString();
			}
			String d0TradeRate = request.getParameter("d0TradeRate");
			if(StringUtils.isNotBlank(d0TradeRate)){
				params.put("d0TradeRate", d0TradeRate);
			}else{
				result.put("returnCode", "0007");
				result.put("returnMsg", "缺少d0TradeRate参数");
				return result.toString();
			}
			String t1DrawFee = request.getParameter("t1DrawFee");
			if(StringUtils.isNotBlank(t1DrawFee)){
				params.put("t1DrawFee", t1DrawFee);
			}else{
				result.put("returnCode", "0007");
				result.put("returnMsg", "缺少t1DrawFee参数");
				return result.toString();
			}
			String t1TradeRate = request.getParameter("t1TradeRate");
			if(StringUtils.isNotBlank(t1TradeRate)){
				params.put("t1TradeRate", t1TradeRate);
			}else{
				result.put("returnCode", "0007");
				result.put("returnMsg", "缺少t1TradeRate参数");
				return result.toString();
			}
			String qucikJfFee = request.getParameter("qucikJfFee");
			if(StringUtils.isNotBlank(qucikJfFee)){
				params.put("qucikJfFee", qucikJfFee);
			}else{
				result.put("returnCode", "0007");
				result.put("returnMsg", "缺少qucikJfFee参数");
				return result.toString();
			}
			String quickJfRate = request.getParameter("quickJfRate");
			if(StringUtils.isNotBlank(quickJfRate)){
				params.put("quickJfRate", quickJfRate);
			}else{
				result.put("returnCode", "0007");
				result.put("returnMsg", "缺少quickJfRate参数");
				return result.toString();
			}
			String quickWjfFee = request.getParameter("quickWjfFee");
			if(StringUtils.isNotBlank(quickWjfFee)){
				params.put("quickWjfFee", quickWjfFee);
			}else{
				result.put("returnCode", "0007");
				result.put("returnMsg", "缺少quickWjfFee参数");
				return result.toString();
			}
			String quickWjfRate = request.getParameter("quickWjfRate");
			if(StringUtils.isNotBlank(quickWjfRate)){
				params.put("quickWjfRate", quickWjfRate);
			}else{
				result.put("returnCode", "0007");
				result.put("returnMsg", "缺少quickWjfRate参数");
				return result.toString();
			}
			String signStr = request.getParameter("signStr");
			if(StringUtils.isNotBlank(signStr)){
//				params.put("signStr", signStr);
			}else{
				result.put("returnCode", "0007");
				result.put("returnMsg", "缺少signStr参数");
				return result.toString();
			}
			
			SysOfficeExample sysOfficeExample = new SysOfficeExample();
			sysOfficeExample.or().andCodeEqualTo(officeCode).andDelFlagEqualTo("0");
			List<SysOffice> sysOfficeList = sysOfficeService.selectByExample(sysOfficeExample);
			if(null == sysOfficeList || sysOfficeList.size() == 0){
				result.put("returnCode", "0008");
				result.put("returnMsg", "该商户暂未支持收银台功能，请确认后重试");
				return result.toString();
			}
			SysOffice sysOffice = sysOfficeList.get(0);//getTopAgentOffice(sysOfficeList.get(0).getId());//
			String srcStr = StringUtil.orderedKey(params);
			if(!EpaySignUtil.checksign(sysOffice.getPublicKeyRsa(), srcStr.toString(), signStr)){
				result.put("returnCode", "0004");
				result.put("returnMsg", "签名校验错误，请检查签名参数是否正确");
				return result.toString();
			}
			if(!"0".equals(settleType)){
				result.put("returnCode", "0004");
				result.put("returnMsg", "仅支持类型为D+0的注册商户");
				return result.toString();
			}
			if(!ValidateUtil.checkChinese(merchantName) || merchantName.length()<=4){
				result.put("returnCode", "0004");
				result.put("returnMsg", "商户名称请填写大于4个汉字的名称");
				return result.toString();
			}
			if(!ValidateUtil.checkChinese(shortName) || merchantName.length()<=4){
				result.put("returnCode", "0004");
				result.put("returnMsg", "商户简称请填写大于4个汉字的名称");
				return result.toString();
			}
			BuAreaCodeExample buAreaCodeExample = new BuAreaCodeExample();
			buAreaCodeExample.or().andAreacodeEqualTo(province);
			List<BuAreaCode> arealist = buAreaCodeService.selectByExample(buAreaCodeExample);
			if(arealist==null || arealist.size()==0){
				result.put("returnCode", "0004");
				result.put("returnMsg", "provice参数错误");
				return result.toString();
			}
			buAreaCodeExample.clear();
			buAreaCodeExample.or().andAreacodeEqualTo(city);
			arealist = buAreaCodeService.selectByExample(buAreaCodeExample);
			if(arealist==null || arealist.size()==0){
				result.put("returnCode", "0004");
				result.put("returnMsg", "city参数错误");
				return result.toString();
			}else{
				BuAreaCode area = arealist.get(0);
				if(!province.equals(area.getAreaparentid())){
					result.put("returnCode", "0004");
					result.put("returnMsg", "city与province代码不匹配");
					return result.toString();
				}
			}
			buAreaCodeExample.clear();
			buAreaCodeExample.or().andAreacodeEqualTo(county);
			arealist = buAreaCodeService.selectByExample(buAreaCodeExample);
			if(arealist==null || arealist.size()==0){
				result.put("returnCode", "0004");
				result.put("returnMsg", "county参数错误");
				return result.toString();
			}else{
				BuAreaCode area = arealist.get(0);
				if(!city.equals(area.getAreaparentid())){
					result.put("returnCode", "0004");
					result.put("returnMsg", "city与county代码不匹配");
					return result.toString();
				}
			}
			IdcardValidator idcardValidator = new IdcardValidator();
			if(!idcardValidator.isValidatedAllIdcard(certNbr)){
				result.put("returnCode", "0004");
				result.put("returnMsg", "身份证号码错误");
				return result.toString();
			}
			KbinExample kbinExample = new KbinExample();
			kbinExample.or().andBankCodeEqualTo(bankCode);
			List<Kbin> bankList = kbinService.selectByExample(kbinExample);
			if(null != bankList && bankList.size()>0){
				registerTmp.setBankOpen(bankList.get(0).getBankName());
				registerTmp.setBankId(bankCode);
				boolean isKbin = false;
				for(Kbin kbin : bankList){
					if(accountNumber.startsWith(kbin.getKbin())){
						if(kbin.getLen().equals(accountNumber.length()+"")){
							isKbin = true;
							break;
						}
					}
				}
				if(!isKbin){
					result.put("returnCode", "4004");
					result.put("returnMsg", "银行卡号不在Kbin范围内");
					return result.toString();
				}
			}else{
				result.put("returnCode", "4004");
				result.put("returnMsg", "银行编码错误");
				return result.toString();
			}
			if(!ValidateUtil.isDoubleByTail(d0DrawFee,2)){//D0提现费
				result.put("returnCode", "4004");
				result.put("returnMsg", "d0DrawFee值错误");
				return result.toString();
			}
			if(!ValidateUtil.isDoubleByTail(d0TradeRate,4)){//D0交易费率
				result.put("returnCode", "4004");
				result.put("returnMsg", "d0TradeRate值错误");
				return result.toString();
			}
			if(!ValidateUtil.isDoubleByTail(t1DrawFee,2)){//T1提现费
				result.put("returnCode", "4004");
				result.put("returnMsg", "t1DrawFee值错误");
				return result.toString();
			}
			if(!ValidateUtil.isDoubleByTail(t1TradeRate,4)){//T1交易费率
				result.put("returnCode", "4004");
				result.put("returnMsg", "t1TradeRate值错误");
				return result.toString();
			}
			if(!ValidateUtil.isDoubleByTail(qucikJfFee,2)){//快捷提现费（有积分）
				result.put("returnCode", "4004");
				result.put("returnMsg", "qucikJfFee值错误");
				return result.toString();
			}
			if(!ValidateUtil.isDoubleByTail(quickJfRate,4)){//快捷交易费率（有积分）
				result.put("returnCode", "4004");
				result.put("returnMsg", "quickJfRate值错误");
				return result.toString();
			}
			if(!ValidateUtil.isDoubleByTail(quickWjfFee,2)){//快捷提现费（无积分）
				result.put("returnCode", "4004");
				result.put("returnMsg", "quickWjfFee值错误");
				return result.toString();
			}
			if(!ValidateUtil.isDoubleByTail(quickWjfRate,4)){//快捷交易费率（无积分）
				result.put("returnCode", "4004");
				result.put("returnMsg", "quickWjfRate值错误");
				return result.toString();
			}
			
			EpayCodeExample epayCodeExample = new EpayCodeExample();
			epayCodeExample.or().andOfficeIdEqualTo(sysOffice.getId()).andStatusEqualTo("2").andDelFlagEqualTo("0");
			List<EpayCode> epayCodeList = epayCodeService.selectByExample(epayCodeExample);
			EpayCode epayCode = new EpayCode();
			if(epayCodeList.size()>0){
				epayCode = epayCodeList.get(0);
			}else{
				result.put("returnCode", "4004");
				result.put("returnMsg", "没有可用的二维码，请联系客服。");
				return result.toString();
			}
			
			registerTmp.setType("1");
			registerTmp.setLoginCode(mobilePhone);//手机号作为账号
			registerTmp.setContact(contact);
			registerTmp.setContactType(contactType);
			registerTmp.setPayCode(epayCode.getPayCode());
			registerTmp.setAddr(addr);
			registerTmp.setCertNbr(certNbr);
			registerTmp.setProvince(province);
			registerTmp.setCity(city);
			registerTmp.setCounty(county);
			registerTmp.setMobilePhone(mobilePhone);
			registerTmp.setAccountName(accountName);
			registerTmp.setAccountNumber(accountNumber);
			
			registerTmp.setName(merchantName);
			registerTmp.setShortName(shortName);
			registerTmp.setSettleType(settleType);
			registerTmp.setCreateBy("1");
			registerTmp.setUpdateBy("1");
			registerTmp.setDelFlag("0");
			registerTmp.setStatus("0");
			
			MemberInfoExample  memberInfoExample = new MemberInfoExample();
			memberInfoExample.or().andCertNbrEqualTo(registerTmp.getCertNbr()).andStatusNotEqualTo("1").andDelFlagEqualTo("0");
			int countByExample = 0;
			countByExample = memberInfoService.countByExample(memberInfoExample);
			if(countByExample >0){
				result.put("returnCode", "4004");
				result.put("returnMsg", "身份证号码已存在");
				return result.toString();
			}
			
			memberInfoExample = new MemberInfoExample();
			memberInfoExample.or().andMobilePhoneEqualTo(registerTmp.getMobilePhone()).andStatusNotEqualTo("1").andDelFlagEqualTo("0");
			countByExample = memberInfoService.countByExample(memberInfoExample);
			if(countByExample >0){
				result.put("returnCode", "4004");
				result.put("returnMsg", "该手机号码已注册");
				return result.toString();
			}
			
			registerTmp.setCode(SysConfig.prefixMemberCode+commonService.getNextSequenceVal(SequenseTypeConstant.MEMBERCODE));
			registerTmp.setLoginPass(SecurityUtil.md5Encode(registerTmp.getMobilePhone().substring(5, 11)));
			Date nowDate = new Date();
			registerTmp.setCreateDate(nowDate);
			registerTmp.setUpdateDate(nowDate);
			
			registerTmp.setWxMemberCode("w"+commonService.getNextSequenceVal(SequenseTypeConstant.WXMEMBERCODE));
			registerTmp.setZfbMemberCode("z"+commonService.getNextSequenceVal(SequenseTypeConstant.ZFBMEMBERCODE));
			registerTmp.setQqMemberCode("q"+commonService.getNextSequenceVal(SequenseTypeConstant.QQMEMBERCODE));
			registerTmp.setBdMemberCode("b"+commonService.getNextSequenceVal(SequenseTypeConstant.BDMEMBERCODE));
			registerTmp.setJdMemberCode("j"+commonService.getNextSequenceVal(SequenseTypeConstant.JDMEMBERCODE));
			
			MemberInfo memberInfo = new MemberInfo();
			memberInfo.setCode(registerTmp.getCode());
			memberInfo.setPayCode(registerTmp.getPayCode());
			memberInfo.setType(registerTmp.getType());
			memberInfo.setLoginCode(registerTmp.getLoginCode());
			memberInfo.setLoginPass(registerTmp.getLoginPass());
			memberInfo.setContact(registerTmp.getContact());
			memberInfo.setContactType(registerTmp.getContactType());
			//该项赋值注意区分========
			memberInfo.setCardNbr(registerTmp.getAccountNumber());
			memberInfo.setAddr(registerTmp.getAddr());
			memberInfo.setCertNbr(registerTmp.getCertNbr());
			memberInfo.setProvince(registerTmp.getProvince());
			memberInfo.setCity(registerTmp.getCity());
			memberInfo.setCounty(registerTmp.getCounty());
			memberInfo.setAddr(registerTmp.getAddr());
			memberInfo.setMobilePhone(registerTmp.getMobilePhone());
			memberInfo.setCardPic1(registerTmp.getCardPic1());
			memberInfo.setCertPic1(registerTmp.getCertPic1());
			memberInfo.setLevel("0");
			memberInfo.setStatus("3");//默认状态 审核中
			memberInfo.setCreateBy("1");
			memberInfo.setCreateDate(new Date());
			memberInfo.setUpdateBy("1");
			memberInfo.setUpdateDate(new Date());
			memberInfo.setName(registerTmp.getName());
			memberInfo.setShortName(registerTmp.getShortName());
			memberInfo.setWxMemberCode(registerTmp.getWxMemberCode());
			memberInfo.setZfbMemberCode(registerTmp.getZfbMemberCode());
			memberInfo.setQqMemberCode(registerTmp.getQqMemberCode());
			memberInfo.setBdMemberCode(registerTmp.getBdMemberCode());
			memberInfo.setJdMemberCode(registerTmp.getJdMemberCode());
			memberInfo.setSettleType(registerTmp.getSettleType());
			
			memberInfo.setT0DrawFee(new BigDecimal(d0DrawFee));
			memberInfo.setT0TradeRate(new BigDecimal(d0TradeRate));
			memberInfo.setT1DrawFee(new BigDecimal(t1DrawFee));
			memberInfo.setT1TradeRate(new BigDecimal(t1TradeRate));
			memberInfo.setMlJfFee(new BigDecimal(qucikJfFee));
			memberInfo.setMlJfRate(new BigDecimal(quickJfRate));
			memberInfo.setMlWjfFee(new BigDecimal(quickWjfFee));
			memberInfo.setMlWjfRate(new BigDecimal(quickWjfRate));
				
			RouteWayExample routeWayExample = new RouteWayExample();
			routeWayExample.or().andDelFlagEqualTo("0").andIsUsableEqualTo("0");
			List<RouteWay> routeWayList = routeWayService.selectByExample(routeWayExample);
			if(routeWayList.size() > 4){
				for(RouteWay routeWay:routeWayList){
					if("1".equals(routeWay.getTxnType())){
						memberInfo.setWxRouteId(routeWay.getRouteId());
					}else if("2".equals(routeWay.getTxnType())){
						memberInfo.setZfbRouteId(routeWay.getRouteId());
					}else if("3".equals(routeWay.getTxnType())){
						memberInfo.setQqRouteId(routeWay.getRouteId());
					}else if("4".equals(routeWay.getTxnType())){
						memberInfo.setBdRouteId(routeWay.getRouteId());
					}else if("5".equals(routeWay.getTxnType())){
						memberInfo.setJdRouteId(routeWay.getRouteId());
					}
				}
			}else{
				result.put("returnCode", "4004");
				result.put("returnMsg", "交易类型配置信息缺失");
				return result.toString();
			}
			
			//获取经营类别配置信息
			BusinessCategoryExample businessCategoryExample = new BusinessCategoryExample();
			businessCategoryExample.or().andDelFlagEqualTo("0");
			List<BusinessCategory> businessCategoryList = businessCategoryService.selectByExample(businessCategoryExample);
			BusinessCategory businessCategory = null;
			if(businessCategoryList.size() > 0){
				businessCategory = businessCategoryList.get((new Random()).nextInt(businessCategoryList.size()));
			}else{
				result.put("returnCode", "4004");
				result.put("returnMsg", "经营类别配置信息缺失");
				return result.toString();
			}
			
			//校验并填充默认交易额配置
			
			/*
			SysCommonConfigExample sysCommonConfigExample = new SysCommonConfigExample();
			sysCommonConfigExample.or().andNameEqualTo(SysCommonConfigConstant.SYS_SINGLE_LIMIT).andDelFlagEqualTo("0");
			List<SysCommonConfig> sysCommonConfig = sysCommonConfigService.selectByExample(sysCommonConfigExample);
			if (sysCommonConfig.size() == 0) {
				result.put("returnCode", "4004");
				result.put("returnMsg", "系统默认单笔交易限额配置缺失，请与管理员联系");
				return result.toString();
			}
			memberInfo.setSingleLimit(new BigDecimal(sysCommonConfig.get(0).getValue()));
			*/
			memberInfo.setSingleLimit(new BigDecimal("0"));
			/*
			sysCommonConfigExample = new SysCommonConfigExample();
			sysCommonConfigExample.or().andNameEqualTo(SysCommonConfigConstant.SYS_DAY_LIMIT).andDelFlagEqualTo("0");
			sysCommonConfig = sysCommonConfigService.selectByExample(sysCommonConfigExample);
			if (sysCommonConfig.size() == 0) {
				result.put("returnCode", "4004");
				result.put("returnMsg", "系统默认日交易限额配置缺失，请与管理员联系");
				return result.toString();
			}
			memberInfo.setDayLimit(new BigDecimal(sysCommonConfig.get(0).getValue()));
			*/
			memberInfo.setDayLimit(new BigDecimal("0"));
			
			/*
			sysCommonConfigExample = new SysCommonConfigExample();
			sysCommonConfigExample.or().andNameEqualTo(SysCommonConfigConstant.SYS_DRAW_STATUS).andDelFlagEqualTo("0");
			sysCommonConfig = sysCommonConfigService.selectByExample(sysCommonConfigExample);
			if (sysCommonConfig.size() > 0) {
				memberInfo.setDrawStatus(sysCommonConfig.get(0).getValue());
			}
			*/
			memberInfo.setDrawStatus("0"); //默认D+0提现状态开启
			
			registerLoginController.getMerchantAddress(memberInfo);
			String wxMerchantCode = null;
			String zfbMerchantCode = null;
			String qqMerchantCode = null;
			String bdMerchantCode = null;
			String jdMerchantCode = null;
			String bankType="";
			String bankName="";
			JSONObject wxPayAccount = registerLoginController.registerMsAccount(MSPayWayConstant.WXPAY, memberInfo , businessCategory,bankType,bankName);
			if("0000".equals(wxPayAccount.getString("returnCode"))){
				wxMerchantCode = wxPayAccount.getString("merchantCode");
				memberInfo.setWxStatus("1");//0审核中，1审核通过
			}else if("200012".equals(wxPayAccount.getString("returnCode"))){//审核中
				memberInfo.setWxStatus("0");//0审核中，1审核通过
			}else{
				return wxPayAccount.toString();
			}
			
			JSONObject zfbPayAccount = registerLoginController.registerMsAccount(MSPayWayConstant.ZFBZF, memberInfo, businessCategory,bankType,bankName);
			if("0000".equals(zfbPayAccount.getString("returnCode"))){
				zfbMerchantCode = zfbPayAccount.getString("merchantCode");
				memberInfo.setZfbStatus("1");
			}else if("200012".equals(zfbPayAccount.getString("returnCode"))){//审核中
				memberInfo.setZfbStatus("0");//0审核中，1审核通过
			}else{
				return zfbPayAccount.toString();
			}
			
			JSONObject qqPayAccount = registerLoginController.registerMsAccount(MSPayWayConstant.QQZF, memberInfo, businessCategory,bankType,bankName);
			if("0000".equals(qqPayAccount.getString("returnCode"))){
				qqMerchantCode = qqPayAccount.getString("merchantCode");
				memberInfo.setQqStatus("1");
			}else if("200012".equals(qqPayAccount.getString("returnCode"))){//审核中
				memberInfo.setQqStatus("0");//0审核中，1审核通过
			}else{
				return qqPayAccount.toString();
			}
			JSONObject bdPayAccount1 = registerLoginController.registerMsAccount(MSPayWayConstant.BDZF, memberInfo, businessCategory,bankType,bankName);
			if("0000".equals(bdPayAccount1.getString("returnCode"))){
				bdMerchantCode = bdPayAccount1.getString("merchantCode");
				memberInfo.setBdStatus("1");
			}else if("200012".equals(bdPayAccount1.getString("returnCode"))){//审核中
				memberInfo.setBdStatus("0");//0审核中，1审核通过
			}else{
				return bdPayAccount1.toString();
			}
			JSONObject jdPayAccount = registerLoginController.registerMsAccount(MSPayWayConstant.JDZF, memberInfo, businessCategory,bankType,bankName);
			if("0000".equals(jdPayAccount.getString("returnCode"))){
				jdMerchantCode = jdPayAccount.getString("merchantCode");
				memberInfo.setJdStatus("1");
			}else if("200012".equals(jdPayAccount.getString("returnCode"))){//审核中
				memberInfo.setJdStatus("0");//0审核中，1审核通过
			}else{
				return jdPayAccount.toString();
			}
			registerTmp.setCategory(businessCategory.getId()+"");
			registerTmpService.insertSelective(registerTmp);
			
			memberInfo.setMlJfStatus("1");
			memberInfo.setMlWjfStatus("1");
			//if("1".equals(memberInfo.getWxStatus()) || "1".equals(memberInfo.getZfbStatus()) || "1".equals(memberInfo.getQqStatus()) || "1".equals(memberInfo.getBdStatus())){
			//注册时默认商户未认证
			memberInfo.setStatus("0");
			//}
			memberInfo.setCategory(businessCategory.getId()+"");
			memberInfo.setWxMerchantCode(wxMerchantCode);
			memberInfo.setZfbMerchantCode(zfbMerchantCode);
			memberInfo.setQqMerchantCode(qqMerchantCode);
			memberInfo.setBdMerchantCode(bdMerchantCode);
			memberInfo.setJdMerchantCode(jdMerchantCode);
			memberInfoService.insertSelective(memberInfo);
			
			MemberBank memberBank = new MemberBank();
			memberBank.setMemberId(memberInfo.getId());
			memberBank.setBankId(registerTmp.getBankId());
			memberBank.setSubId(registerTmp.getSubId());
//			memberBank.setProvince(registerTmp.getProvince());
//			memberBank.setCity(registerTmp.getCity());
			memberBank.setBankOpen(registerTmp.getBankOpen());
			memberBank.setAccountName(registerTmp.getAccountName());
			memberBank.setAccountNumber(memberInfo.getCardNbr());
			memberBank.setSettleType(memberInfo.getSettleType());
			memberBank.setCreateBy("1");
			memberBank.setCreateDate(new Date());
			memberBank.setUpdateBy("1");
			memberBank.setUpdateDate(new Date());
			memberBank.setDelFlag("0");
			memberBankService.insertSelective(memberBank);
			
			epayCode.setStatus("7");
			epayCode.setMemberId(memberInfo.getId());
			epayCode.setUpdateDate(new Date());
			epayCode.setT0DrawFee(new BigDecimal(d0DrawFee));
			epayCode.setT0TradeRate(new BigDecimal(d0TradeRate));
			epayCode.setT1DrawFee(new BigDecimal(t1DrawFee));
			epayCode.setT1TradeRate(new BigDecimal(t1TradeRate));
			epayCode.setMlJfFee(new BigDecimal(qucikJfFee));
			epayCode.setMlJfRate(new BigDecimal(quickJfRate));
			epayCode.setMlWjfFee(new BigDecimal(quickWjfFee));
			epayCode.setMlWjfRate(new BigDecimal(quickWjfRate));
			epayCodeService.updateByPrimaryKey(epayCode);
			
			Account accountInfo = new Account();
			accountInfo.setMemberId(memberInfo.getId());
			accountInfo.setBalance(new BigDecimal("0.0"));
			accountInfo.setFreezeMoney(new BigDecimal("0.0"));
			accountInfo.setCreateBy("1");
			accountInfo.setCreateDate(new Date());
			accountInfo.setUpdateBy("1");
			accountInfo.setUpdateDate(new Date());
			accountService.insertSelective(accountInfo);
			
			RegisterTmp registerTempUpdate = new RegisterTmp();
			registerTempUpdate.setId(registerTmp.getId());
			registerTempUpdate.setStatus("1");
			registerTmpService.updateByPrimaryKeySelective(registerTempUpdate);
			
			result.put("memberCode", memberInfo.getCode());
			result.put("epayCodeUrl", SysConfig.epayCodeUrl+memberInfo.getPayCode());
			result.put("returnCode", "0000");
			result.put("returnMsg", "成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
			return result.toString();
		}
		return result.toString();
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * 给第三方平台查询接口
	 */
	@ResponseBody
	@RequestMapping("/cashierDesk/queryMember")
	public String queryMember(HttpServletRequest request,HttpServletResponse response) {
		
		JSONObject result=new JSONObject();
		try {
			Map<String,String> params = new HashMap<String,String>();
			String memberCode = request.getParameter("memberCode");
			if(StringUtils.isNotBlank(memberCode)){
				params.put("memberCode", memberCode);
			}else{
				result.put("returnCode", "0007");
				result.put("returnMsg", "缺少memberCode参数");
				return result.toString();
			}
			String signStr = request.getParameter("signStr");
			if(StringUtils.isNotBlank(signStr)){
//				params.put("signStr", signStr);
			}else{
				result.put("returnCode", "0007");
				result.put("returnMsg", "缺少signStr参数");
				return result.toString();
			}
			MemberInfoExample memberInfoExample = new MemberInfoExample();
			memberInfoExample.or().andCodeEqualTo(memberCode);
			List<MemberInfo> members = memberInfoService.selectByExample(memberInfoExample);
			MemberInfo memberInfo = null;
			if(members!=null && members.size()>0){
				memberInfo = members.get(0);
			}else{
				result.put("returnCode", "0007");
				result.put("returnMsg", "找不到该商户信息");
				return result.toString();
			}
			String srcStr = StringUtil.orderedKey(params);
			Map<String, Object> map = validSigned(memberInfo, srcStr,signStr);
			if(!"0000".equals(map.get("returnCode"))){
				return map.toString();
			}
			MemberBankExample memberBankExample = new MemberBankExample();
			memberBankExample.or().andMemberIdEqualTo(memberInfo.getId());
			List<MemberBank> memberBanks = memberBankService.selectByExample(memberBankExample);
			MemberBank memberBank = null;
			if(memberBanks!=null && memberBanks.size()>0){
				memberBank = memberBanks.get(0);
			}else{
				result.put("returnCode", "0007");
				result.put("returnMsg", "找不到该商户对应银行信息");
				return result.toString();
			}
//			result.put("memberCode", memberInfo.getCode());
			result.put("wxStatus", memberInfo.getWxStatus());
			result.put("zfbStatus", memberInfo.getZfbStatus());
			result.put("qqStatus", memberInfo.getQqStatus());
			result.put("bdStatus", memberInfo.getBdStatus());
			result.put("kjjfStatus", memberInfo.getMlJfStatus());
			result.put("kjwjfStatus", memberInfo.getMlWjfStatus());
			result.put("epayCodeUrl", SysConfig.epayCodeUrl+memberInfo.getPayCode());
			result.put("settleType", memberInfo.getSettleType());
			result.put("merchantName", memberInfo.getName());
			result.put("shortName", memberInfo.getShortName());
			result.put("contact", memberInfo.getContact());
			result.put("contactType", memberInfo.getContactType());
			result.put("mobilePhone", memberInfo.getMobilePhone());
			result.put("province", memberInfo.getProvince());
			result.put("city", memberInfo.getCity());
			result.put("county", memberInfo.getCounty());
			result.put("addr", memberInfo.getAddr());
			result.put("certNbr", memberInfo.getCertNbr());
			result.put("bankName", memberBank.getBankOpen());
			result.put("accountName", memberBank.getAccountName());
			result.put("accountNumber", memberBank.getAccountNumber());
			result.put("d0DrawFee", memberInfo.getT0DrawFee());
			result.put("d0TradeRate", memberInfo.getT0TradeRate());
			result.put("t1DrawFee", memberInfo.getT1DrawFee());
			result.put("t1TradeRate", memberInfo.getT1TradeRate());
			result.put("qucikJfFee", memberInfo.getMlJfFee());
			result.put("quickJfRate", memberInfo.getMlJfRate());
			result.put("quickWjfFee", memberInfo.getMlWjfFee());
			result.put("quickWjfRate", memberInfo.getMlWjfRate());
			result.put("returnCode", "0000");
			result.put("returnMsg", "成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
			return result.toString();
		}
		return result.toString();
	}
	
	private SysOffice getTopAgentOffice(String officeId){
		SysOfficeExample sysOfficeExample = new SysOfficeExample();
		sysOfficeExample.or().andIdEqualTo(officeId);
		List<SysOffice> sysOfficeList = sysOfficeService.selectByExample(sysOfficeExample);
		if(sysOfficeList.size()>0){
			SysOffice sysOffice = sysOfficeList.get(0);
			if("1".equals(sysOffice.getAgtGrade())){//返回顶级即1级代理商的信息
				return sysOffice;
			}else{
				return getTopAgentOffice(sysOffice.getParentId());
			}
		}
		return null;
	}
	
	/**
	 * 给接入商户做绑卡号/账号交易
	 */
	@ResponseBody
	@RequestMapping("/cashierDesk/bindAcc")
	public String bindAcc (HttpServletRequest request,HttpServletResponse response) {
		JSONObject result=new JSONObject();
		Map<String,String> params = new HashMap<String,String>();
		/**获取参数值**/
		/********************start************************/
		String orderNum = request.getParameter("orderNum");
		String memberCode = request.getParameter("memberCode");
		String acctNo = request.getParameter("acctNo");
		String bankCode = request.getParameter("bankCode");
		//String certNbr = request.getParameter("certNbr");
		String name = request.getParameter("name");
		String phoneNo = request.getParameter("phoneNo");
		String signStr = request.getParameter("signStr");
		params.put("orderNum", orderNum);
		params.put("memberCode", memberCode);
		params.put("acctNo", acctNo);
		params.put("bankCode", bankCode);
		//params.put("certNbr", certNbr);
		params.put("name", name);		
		params.put("phoneNo", phoneNo);
		
		/**获取参数值**/
		/********************end************************/
		
		/**
		 * 判断参数是否为空
		 */
		/********************start************************/
		if(StringUtils.isNotBlank(orderNum)){
			params.put("orderNum", orderNum);
		}else{
			result.put("returnCode", "0007");
			result.put("returnMsg", "缺少orderNum参数");
			
			return result.toString();
		} 
		
		
		
		if(StringUtils.isNotBlank(memberCode)){
			params.put("memberCode", memberCode);
		}else{
			result.put("returnCode", "0007");
			result.put("returnMsg", "缺少memberCode参数");
			return result.toString();
		} 
		if(StringUtils.isNotBlank(acctNo)){
			params.put("acctNo", acctNo);
		}else{
			result.put("returnCode", "0007");
			result.put("returnMsg", "缺少acctNo参数");
			return result.toString();
		} 
		
		if(StringUtils.isNotBlank(bankCode)){
			params.put("bankCode", bankCode);
		}else{
			result.put("returnCode", "0007");
			result.put("returnMsg", "缺少bankCode参数");
			return result.toString();
		} 
		
		/*if(StringUtils.isNotBlank(certNbr)){
			params.put("certNbr", certNbr);
		}else{
			result.put("returnCode", "0007");
			result.put("returnMsg", "缺少certNbr参数");
			return result.toString();
		} */
		if(StringUtils.isNotBlank(name)){
			params.put("name", name);
		}else{
			result.put("returnCode", "0007");
			result.put("returnMsg", "缺少name参数");
			return result.toString();
		} 
		
		if(StringUtils.isNotBlank(phoneNo)){
			params.put("phoneNo", phoneNo);
		}else{
			result.put("returnCode", "0007");
			result.put("returnMsg", "缺少phoneNo参数");
			return result.toString();
		}	
		
		if(StringUtils.isNotBlank(signStr)){
//			params.put("signStr", signStr);
		}else{
			result.put("returnCode", "0007");
			result.put("returnMsg", "缺少signStr参数");
			return result.toString();
		}
		
		/**
		 * 判断参数是否为空
		 */
		
		/********************end************************/
		/*************************校验签名字符串****************/
		/********************start************************/
		MemberInfoExample memberInfoExample = new MemberInfoExample();
		memberInfoExample.or().andCodeEqualTo(memberCode);
		List<MemberInfo> members = memberInfoService.selectByExample(memberInfoExample);
		MemberInfo memberInfo = null;
		if(members!=null && members.size()>0){
			memberInfo = members.get(0);
		}else{
			result.put("returnCode", "0007");
			result.put("returnMsg", "找不到该商户信息");
			return result.toString();
		}
		String srcStr = StringUtil.orderedKey(params);
		logger.debug("绑卡参数:"+srcStr);
		Map<String, Object> map = validSigned(memberInfo, srcStr,signStr);
		logger.debug("签名校验结果:    返回码："+map.get("returnCode")+"    返回说明:"+map.get("returnMsg"));
		if(!"0000".equals(map.get("returnCode"))){
			result.put("returnCode", "0004");
			result.put("returnMsg", "签名校验错误，请检查签名参数是否正确");
			return result.toString();
		}
		/********************end************************/
		MemberBindAccExample memberBindAccExample = new MemberBindAccExample();		
		List<MemberBindAcc> memberBindAccs = null;
		memberBindAccExample.createCriteria().andOrderNumEqualTo(orderNum).andMemberIdEqualTo(memberInfo.getId());
		memberBindAccs=memberBindAccService.selectByExample(memberBindAccExample);
		if(null != memberBindAccs && memberBindAccs.size() > 0){
			result.put("returnCode", "0002");
			result.put("returnMsg", "该笔订单已提交，请勿重复提交");
			return result.toString();
		}
		
		
		/****调用绑定接口*******************/
		/********************start************************/
		JSONObject reqData=new JSONObject();
		MemberBindAcc  memberBindAcc=new MemberBindAcc();
		memberBindAcc.setAcc(acctNo);
		memberBindAcc.setMemberId(memberInfo.getId());
		memberBindAcc.setBankId(bankCode);
		memberBindAcc.setName(name);
		memberBindAcc.setOrderNum(orderNum);
		memberBindAcc.setMobilePhone(phoneNo);
		reqData.put("memberBindAcc", memberBindAcc); 
		result=JSONObject.fromObject(HttpUtil.sendPostRequest(SysConfig.serverUrl+"/api/memberInfo/memberBindAcc", CommonUtil.createSecurityRequstData(reqData)));
		logger.debug("绑卡结果:"+result.toString());
		return result.toString();
		 /********************end************************/		
	}
	/**
	 * 第三方快捷支付接口
	 */
	@ResponseBody
	@RequestMapping("/api/cashierDesk/quickPay")
	public String quickPay (HttpServletRequest request,HttpServletResponse response) {
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		
		JSONObject result = new JSONObject(); 
		JSONObject mlTradeDetail = new JSONObject();
		JSONObject reqData = new JSONObject();
		Map<String,String> params = new HashMap<String,String>();
		
		StTradeDetail	stTradeDetail=new StTradeDetail();
		StTradeDetailAll stTradeDetailAll;
		MemberInfoExample memberInfoExample = new MemberInfoExample();
		MemberInfo memberInfo = null;
		List<StTradeDetail> stTradeDetails=null;
		String rtn="";
		
		try {
			stTradeDetailAll= (StTradeDetailAll) JSONObject.toBean(reqDataJson.getJSONObject("mlTradeDetail"),	StTradeDetailAll.class);
			stTradeDetailAll.setDelFlag("0");
			stTradeDetailAll.setCreateDate(new Date());
			stTradeDetailAll.setCreateBy("1");
			
			memberInfoExample.or().andCodeEqualTo(reqDataJson.getJSONObject("mlTradeDetail").optString("memberCode"));
			List<MemberInfo> members = memberInfoService.selectByExample(memberInfoExample);
			
			if(members!=null && members.size()>0){
				memberInfo = members.get(0);
				if(!"0".equals(memberInfo.getStatus())){
					if("4".equals(memberInfo.getStatus())){
						result.put("returnCode", "0011");
						result.put("returnMsg", "该商户未进行认证，暂时无法交易");
						return result.toString();
					}
					result.put("returnCode", "0008");
					result.put("returnMsg", "对不起，该商户暂不可用");
					return result.toString();
				}
			}else{
				result.put("returnCode", "0007");
				result.put("returnMsg", "找不到该商户信息");
				return result.toString();
			} 
			stTradeDetailAll.setMemberId(memberInfo.getId());
		/*************校验加密串start**********************/
		String signStr=reqDataJson.getJSONObject("mlTradeDetail").optString("signStr");
		
		
		//signStr="OvCbgxTje4MXJRLrxgOOIxu9pQQXTHVHaz+LTXQMnrxVxI52oF72juIa5JRICivhj1eznDpxWHauxG2LK6Bsvw==";
		
		params.put("orderNum", stTradeDetailAll.getOrderNum());
		params.put("orderAmt", stTradeDetailAll.getOrderAmt().toString());
		params.put("payType", stTradeDetailAll.getPayType());
		params.put("bgUrl", stTradeDetailAll.getBgUrl());
		params.put("pageUrl", stTradeDetailAll.getPageUrl());
		params.put("memberCode",stTradeDetailAll.getMemberCode());
		params.put("goodsName", stTradeDetailAll.getGoodsName());
		params.put("goodsDesc", stTradeDetailAll.getGoodsDesc());
		params.put("accNo", stTradeDetailAll.getAccNo());
		params.put("add1", stTradeDetailAll.getAdd1());
		params.put("add2", stTradeDetailAll.getAdd2());
		params.put("add3", stTradeDetailAll.getAdd3());
		params.put("add4", stTradeDetailAll.getAdd4());
		String srcStr = StringUtil.orderedKey(params);
		logger.debug("绑卡参数:"+srcStr);
		Map<String, Object> map = validSigned(memberInfo, srcStr,signStr);
		if(!"0000".equals(map.get("returnCode"))){
			result.put("returnCode", "0004");
			result.put("returnMsg", "签名校验错误，请检查签名参数是否正确");
			return result.toString();
		}	
		/*************校验加密串end**********************/
		/*
		JSONObject checkResult = checkPayLimit(memberInfo.getId(), stTradeDetailAll.getOrderAmt(), memberInfo.getSingleLimit(), memberInfo.getDayLimit());
		if(null != checkResult){
			return checkResult.toString();
		}
		*/
		/********************判断订单号是否重复start*****************************/
		StTradeDetailExample stTradeDetailExample = new StTradeDetailExample();
		stTradeDetailExample.createCriteria().andOrderNumEqualTo(stTradeDetailAll.getOrderNum());
		stTradeDetails=stTradeDetailService.selectByExample(stTradeDetailExample);
		if(null != stTradeDetails && stTradeDetails.size() > 0){
			result.put("returnCode", "0002");
			result.put("returnMsg", "该笔订单已提交，请勿重复提交");
			return result.toString();
		}
		/********************判断订单号是否重复end*****************************/
		
		
			mlTradeDetail.put("memberId", memberInfo.getId());
			mlTradeDetail.put("isJf", stTradeDetailAll.getPayType());
			mlTradeDetail.put("acc",  stTradeDetailAll.getAccNo());
			mlTradeDetail.put("money", stTradeDetailAll.getOrderAmt());
			mlTradeDetail.put("orderNum", stTradeDetailAll.getOrderNum());
			reqData.put("mlTradeDetail", mlTradeDetail);
			result = JSONObject.fromObject(HttpUtil.sendPostRequest(SysConfig.serverUrl + "/api/memberInfo/memberQuickPay", CommonUtil.createSecurityRequstData(reqData)));
			logger.debug("快捷支付接口校验:"+result.toString());
			if(!"0000".equals(result.opt("returnCode"))){
				result.put("returnCode", "0003");
				stTradeDetailAll.setTradeCode("0003");
				stTradeDetailAll.setTradeDesc(result.opt("returnMsg").toString());
				rtn= result.toString();
			}else{
				//System.out.println(result.getJSONObject("resData").getJSONObject("memberQuickPay").opt("SETT_AMT").toString());
				//result.getJSONObject("resData").getJSONObject("memberQuickPay").put("PAGE_URL", MSConfig.sfpageUrl);
				stTradeDetailAll.setSettAmt(new BigDecimal(result.getJSONObject("resData").getJSONObject("memberQuickPay").opt("SETT_AMT").toString()).setScale(2, BigDecimal.ROUND_DOWN));
				stTradeDetail.setOrderNum(stTradeDetailAll.getOrderNum());
				stTradeDetail.setMemberId(stTradeDetailAll.getMemberId());
				stTradeDetail.setMemberCode(stTradeDetailAll.getMemberCode());
				stTradeDetail.setOrderAmt(stTradeDetailAll.getOrderAmt());
				stTradeDetail.setPayType(stTradeDetailAll.getPayType());
				stTradeDetail.setBgUrl(stTradeDetailAll.getBgUrl());
				stTradeDetail.setPageUrl(stTradeDetailAll.getPageUrl());
				stTradeDetail.setGoodsName(stTradeDetailAll.getGoodsName());
				stTradeDetail.setGoodsDesc(stTradeDetailAll.getGoodsDesc());
				stTradeDetail.setAccNo(stTradeDetailAll.getAccNo());
				stTradeDetail.setAdd1(stTradeDetailAll.getAdd1());
				stTradeDetail.setAdd2(stTradeDetailAll.getAdd2());
				stTradeDetail.setAdd3(stTradeDetailAll.getAdd3());
				stTradeDetail.setAdd4(stTradeDetailAll.getAdd4());
				stTradeDetail.setCreateBy("1");
				stTradeDetail.setCreateDate(new Date());
				stTradeDetail.setTradeCode("0001");			
				stTradeDetail.setCbRes("0");
				stTradeDetailService.insert(stTradeDetail);				
				rtn=result.toString();
			}			
			stTradeDetailAllService.insert(stTradeDetailAll);
			return rtn;
		}catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");		 
			return result.toString();
		} 
		
		
	}
	
	
	/**
	 * TODO 测试用，删除
	 * 测试准备：商户号绑定公众号、配置授权目录等
	 * 
	 * 测试微微信支付窗支付
	 * @param memberId
	 * @param payType 1--微信  2--支付宝
	 * @param payMoney
	 * @param appId  微信：用户openId  支付宝：用户号
	 * @return
	 */
	@RequestMapping("/cashierDesk/testWeixinAppPay")
	public JSONObject testWeixinAppPay(HttpServletRequest request,HttpServletResponse response) {
		String memberId = request.getParameter("memberId");
		String payType = request.getParameter("payType");
		String payMoney = request.getParameter("payMoney");
		String appId = request.getParameter("appId");
		String userId = request.getParameter("userId");
		
		JSONObject result = new JSONObject();
		JSONObject resData = new JSONObject();
		try {
			// 插入一条收款记录
			String orderCode = CommonUtil.getOrderCode();
			MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(Integer.parseInt(memberId));
			
			// String callBack = "http://" + request.getServerName() + ":" +
			// request.getServerPort() + request.getContextPath()+
			// "/debitNote/msNotify";
//			String callBack = SysConfig.serverUrl + "/cashierDesk/msPayNotify";
			
			// 调用支付通道
			String serverUrl = MSConfig.msServerUrl;
			PublicKey yhPubKey = null;
			if (serverUrl.startsWith("https://ipay")) {
				yhPubKey = CryptoUtil.getRSAPublicKey(true);
			} else {
				yhPubKey = CryptoUtil.getRSAPublicKey(false);
			}
			PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKey();
			String tranCode = "SMZF010";
			String charset = "utf-8";
			StringBuilder sBuilder = new StringBuilder();
			sBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			sBuilder.append("<merchant>");
			sBuilder.append("<head>");
			sBuilder.append("<version>1.0.0</version>");
			sBuilder.append("<msgType>01</msgType>");
			sBuilder.append("<reqDate>" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + "</reqDate>");
			sBuilder.append("</head>");
			sBuilder.append("<body>");
			if("1".equals(payType)){
				sBuilder.append("<merchantCode>" + memberInfo.getWxMerchantCode() + "</merchantCode>");
				sBuilder.append("<subAppid>" + appId + "</subAppid>");
			}else{
				sBuilder.append("<merchantCode>" + memberInfo.getZfbMerchantCode() + "</merchantCode>");
			}
			sBuilder.append("<totalAmount>" + payMoney + "</totalAmount>");
			sBuilder.append("<subject>" + memberInfo.getName() + " 收款" + "</subject>");
			sBuilder.append("<userId>" + userId + "</userId>");
			sBuilder.append("</body>");
			sBuilder.append("</merchant>");
			String plainXML = sBuilder.toString();
			byte[] plainBytes = plainXML.getBytes(charset);
			String keyStr = MSCommonUtil.generateLenString(16);
			byte[] keyBytes = keyStr.getBytes(charset);
			String encryptData = new String(Base64.encodeBase64((CryptoUtil.AESEncrypt(plainBytes, keyBytes, "AES", "AES/ECB/PKCS5Padding", null))), charset);
			String signData = new String(Base64.encodeBase64(CryptoUtil.digitalSign(plainBytes, hzfPriKey, "SHA1WithRSA")), charset);
			String encrtptKey = new String(Base64.encodeBase64(CryptoUtil.RSAEncrypt(keyBytes, yhPubKey, 2048, 11, "RSA/ECB/PKCS1Padding")), charset);
			List<NameValuePair> nvps = new LinkedList<NameValuePair>();
			nvps.add(new BasicNameValuePair("encryptData", encryptData));
			nvps.add(new BasicNameValuePair("encryptKey", encrtptKey));
			//nvps.add(new BasicNameValuePair("cooperator", MSConfig.cooperator_t0));
			
			if("0".equals(memberInfo.getSettleType())){
				nvps.add(new BasicNameValuePair("cooperator", MSConfig.cooperator_t0));
			}else{
				nvps.add(new BasicNameValuePair("cooperator", MSConfig.cooperator_t1));
			}
			
			nvps.add(new BasicNameValuePair("signData", signData));
			nvps.add(new BasicNameValuePair("tranCode", tranCode));
//			nvps.add(new BasicNameValuePair("callBack", callBack));
			nvps.add(new BasicNameValuePair("reqMsgId", orderCode));
			byte[] b = HttpClient4Util.getInstance().doPost(serverUrl, null, nvps);
			String respStr = new String(b, charset);
			logger.info("返回报文[{}]", new Object[] { respStr });
			JSONObject jsonObject = JSONObject.fromObject(respStr);
			String resEncryptData = jsonObject.getString("encryptData");
			String resEncryptKey = jsonObject.getString("encryptKey");
			byte[] decodeBase64KeyBytes = Base64.decodeBase64(resEncryptKey.getBytes(charset));
			// 解密encryptKey得到merchantAESKey
			byte[] merchantAESKeyBytes = CryptoUtil.RSADecrypt(decodeBase64KeyBytes, hzfPriKey, 2048, 11, "RSA/ECB/PKCS1Padding");
			// 使用base64解码商户请求报文
			byte[] decodeBase64DataBytes = Base64.decodeBase64(resEncryptData.getBytes(charset));
			// 用解密得到的merchantAESKey解密encryptData
			byte[] merchantXmlDataBytes = CryptoUtil.AESDecrypt(decodeBase64DataBytes, merchantAESKeyBytes, "AES", "AES/ECB/PKCS5Padding", null);
			String resXml = new String(merchantXmlDataBytes, charset);
			JSONObject respJSONObject = XmlConvertUtil.documentToJSONObjectFilter(resXml);
//			resData.put("orderCode", orderCode);     //暂不回传我方的订单编码
			if (respJSONObject.containsKey("resEntity")) {
				JSONObject resEntity = respJSONObject.getJSONObject("resEntity");
				if ("1".equals(payType)) {
					if (resEntity.containsKey("wxjsapiStr")) {
						JSONObject wxjsapiStr=resEntity.getJSONObject("wxjsapiStr");
//					wxjsapiStr.put("package_str", wxjsapiStr.getString("package"));
						resData.put("wxjsapiStr", wxjsapiStr);
					}
				}else{
					if (resEntity.containsKey("channelNo")) {
						resData.put("channelNo", resEntity.get("channelNo"));
					}
				}
				result.put("resData", resData);
				result.put("returnCode", "0000");
				result.put("returnMsg", "成功");
			}else{
				result.put("returnCode", "0003");
				result.put("returnMsg", "MS-"+respJSONObject.getString("respCode")+ ":" +respJSONObject.getString("respMsg"));
			}
			
		} catch (ArgException e) {
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
			logger.info(e.getMessage());
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
			return result;
		}
		return result;
	}
	
	/**
	 * 若singleLimit或dayLimit未设置值，则认为未设置限额，即不对该项进行校验 
	 * @param memberId
	 * @param tradeMoney
	 * @param singleLimit
	 * @param dayLimit
	 * @return
	 */
	private JSONObject checkPayLimit(Integer memberId, BigDecimal tradeMoney, BigDecimal singleLimit, BigDecimal dayLimit){
		
		JSONObject result = new JSONObject();
		if (null != singleLimit && singleLimit.compareTo(BigDecimal.ZERO) > 0){
			if (tradeMoney.compareTo(singleLimit) > 0) {
				result.put("returnCode", "0012");
				result.put("returnMsg", "您的单笔交易限额为"+singleLimit+"元,当前交易已超出限额,请重新输入");
				return result;
			}
		}
		
		if (null != dayLimit && dayLimit.compareTo(BigDecimal.ZERO) > 0){
			String tradeDate = DateUtil.getDateFormat(new Date(), "yyyyMMdd");
			TradeVolumnDailyExample tradeVolumnDailyExample = new TradeVolumnDailyExample();
			tradeVolumnDailyExample.or().andMemberIdEqualTo(memberId).andTradeDateEqualTo(tradeDate);
			List<TradeVolumnDaily> tradeVolumnDailyList = tradeVolumnDailyService.selectByExample(tradeVolumnDailyExample);
			if(tradeVolumnDailyList.size() > 0){
				TradeVolumnDaily tradeVolumnDaily = tradeVolumnDailyList.get(0);
				if (dayLimit.compareTo(tradeMoney.add(tradeVolumnDaily.getTotalMoney())) < 0) {
					double remindTradeMoney = dayLimit.subtract(tradeVolumnDaily.getTotalMoney()).doubleValue();
					if(remindTradeMoney > 0){
						result.put("returnCode", "0013");
						result.put("returnMsg", "您的日交易限额仅剩" + remindTradeMoney + "元，当前交易已超出限额");
					}else{
						result.put("returnCode", "0014");
						result.put("returnMsg", "抱歉,您本日的交易额度已用完,请联系客服提高额度");
					}
					return result;
				}
			}else{
				if (tradeMoney.compareTo(dayLimit) > 0) {
					result.put("returnCode", "0013");
					result.put("returnMsg", "您的日交易限额为" + dayLimit + "元，当前交易已超出限额");
					return result;
				}
			}
		}
		
		return null;
	}

	@RequestMapping("/cashierDesk/testCallBack")
	public void testCallBack(HttpServletRequest request,HttpServletResponse response){
		Map<String,String> params = new HashMap<String,String>();
		String responseStr = HttpUtil.getPostString(request);
		JSONObject resJo = JSONObject.fromObject(responseStr);
		System.out.println(responseStr);
		if(resJo.containsKey("memberCode")){
			String memberCode = resJo.getString("memberCode");
			params.put("memberCode", memberCode);
		}
		if(resJo.containsKey("orderNum")){
			String orderNum = resJo.getString("orderNum");
			params.put("orderNum", orderNum);
		}
		if(resJo.containsKey("payNum")){
			String payNum = resJo.getString("payNum");
			params.put("payNum", payNum);
		}
		if(resJo.containsKey("payType")){
			String payType = resJo.getString("payType");
			params.put("payType", payType);
		}
		if(resJo.containsKey("payMoney")){
			String payMoney = resJo.getString("payMoney");
			params.put("payMoney", payMoney);
		}
		if(resJo.containsKey("payTime")){
			String payTime = resJo.getString("payTime");
			params.put("payTime", payTime);
		}
		if(resJo.containsKey("platformType")){
			String platformType = resJo.getString("platformType");
			params.put("platformType", platformType);
		}
		if(resJo.containsKey("interfaceType")){
			String interfaceType = resJo.getString("interfaceType");
			params.put("interfaceType", interfaceType);
		}
		if(resJo.containsKey("respType")){
			String respType = resJo.getString("respType");
			params.put("respType", respType);
		}
		if(resJo.containsKey("resultCode")){
			String resultCode = resJo.getString("resultCode");
			params.put("resultCode", resultCode);
		}
		if(resJo.containsKey("resultMsg")){
			String resultMsg = resJo.getString("resultMsg");
			params.put("resultMsg", resultMsg);
		}
		String signStr = resJo.getString("signStr");
		String srcStr = StringUtil.orderedKey(params);
		System.out.println("（接收）排序后的参数串："+srcStr);
		String pubKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAK4hKvMwLnbtrdfwSuxRksgDefm8lZ0mNe5uh581eNH1nLjaRoIbs9YFo+fL2tsT/wBrdkEUPcdgWRSyZmX6iosCAwEAAQ==";
		System.out.println(EpaySignUtil.checksign(pubKey, srcStr, signStr));
		
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
	
	
	@ResponseBody
	@RequestMapping("/cashierDesk/orderQuery")
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
				return signReturn(result);
			}
			srcStr.append("memberCode="+memberCode);
			if(orderNum == null || "".equals(orderNum)){
				result.put("returnCode", "0007");
				result.put("returnMsg", "平台订单号缺失");
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
					result.put("returnCode", "0011");
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
			
			if(!EpaySignUtil.checksign(sysOffice.getPublicKeyRsa(), srcStr.toString(), signStr)){
				result.put("returnCode", "0004");
				result.put("returnMsg", "签名校验错误，请检查签名参数是否正确");
				return signReturn(result);
			}
			
			
			String reqMsgId=CommonUtil.getOrderCode();
			// 调用支付通道

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
			if(RouteCodeConstant.ESK_ROUTE_CODE.equals(routeCode)){
				// 调用支付通道
				String serverUrl = ESKConfig.msServerUrl;
			//	PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKey();
				String tranCode = "004";
				String charset = "utf-8";
				
				JSONObject reqData = new JSONObject();
				reqData.put("oriOrderNumber", debitNote.getOrderCode());
				reqData.put("tranCode", tranCode);
				
				System.out.println("待加密数据: "+reqData);
				
				String plainXML = reqData.toString();
				byte[] plainBytes = plainXML.getBytes(charset);
				String keyStr = MSCommonUtil.generateLenString(16);
				;
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
				logger.info("返回报文[{}]", new Object[] { respStr });
				
				JSONObject jsonObject = JSONObject.fromObject(respStr);
				String resEncryptData = jsonObject.getString("Context");
				String resEncryptKey = jsonObject.getString("encrtpKey");
				byte[] decodeBase64KeyBytes = Base64.decodeBase64(resEncryptKey.getBytes(charset));
				// 解密encryptKey得到merchantAESKey
				//先用对方给的私钥调试 by linxf
				//byte[] merchantAESKeyBytes = CryptoUtil.RSADecrypt(decodeBase64KeyBytes, hzfPriKey, 2048, 11, "RSA/ECB/PKCS1Padding");
				byte[] merchantAESKeyBytes = Key.jdkRSA_(decodeBase64KeyBytes, ESKConfig.privateKey);
				// 使用base64解码商户请求报文
				byte[] decodeBase64DataBytes = Base64.decodeBase64(resEncryptData.getBytes(charset));
				// 用解密得到的merchantAESKey解密encryptData
				byte[] merchantXmlDataBytes = CryptoUtil.AESDecrypt(decodeBase64DataBytes, merchantAESKeyBytes, "AES", "AES/ECB/PKCS5Padding", null);
				String resXml = new String(merchantXmlDataBytes, charset);
				JSONObject respJSONObject = JSONObject.fromObject(resXml);
				logger.info("返回报文[{}]",  respJSONObject );
				
				
				if("S".equals(respJSONObject.getString("respType"))&&"000000".equals(respJSONObject.getString("respCode"))){
				//	String merchantCode = respJSONObject.getString("merchantCode");
					result.put("oriRespType", respJSONObject.getString("oriRespType"));
					result.put("oriRespCode", respJSONObject.getString("oriRespCode"));
					result.put("oriRespMsg", respJSONObject.getString("oriRespMsg"));
					if("S".equals(respJSONObject.getString("oriRespType"))&&"000000".equals(respJSONObject.getString("oriRespCode"))){
						result.put("totalAmount", respJSONObject.getString("buyerPayAmount"));
					}
				}else{
					result.put("oriRespType", "E");
					if(respJSONObject.containsKey("oriRespCode")){
						result.put("oriRespCode", respJSONObject.getString("oriRespCode"));
					}
					if(respJSONObject.containsKey("oriRespMsg")){
						result.put("oriRespMsg", respJSONObject.getString("oriRespMsg"));
					}
					
				}
			}else{
				String serverUrl = MSConfig.msServerUrl;
				PublicKey yhPubKey = null;
				if (serverUrl.startsWith("https://ipay")) {
					yhPubKey = CryptoUtil.getRSAPublicKey(true);
				} else {
					yhPubKey = CryptoUtil.getRSAPublicKey(false);
				}
				PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKey();
				String tranCode = TranCodeConstant.JYCX;//"SMZF006";
				String charset = "utf-8";
				StringBuilder sBuilder = new StringBuilder();
				sBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				sBuilder.append("<merchant>");
				sBuilder.append("<head>");
				sBuilder.append("<version>1.0.0</version>");
				sBuilder.append("<msgType>01</msgType>");
				sBuilder.append("<reqDate>" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + "</reqDate>");
				sBuilder.append("</head>");
				sBuilder.append("<body>");
				sBuilder.append("<oriReqMsgId>" + debitNote.getOrderCode() + "</oriReqMsgId>");
				sBuilder.append("</body>");
				sBuilder.append("</merchant>");
				String plainXML = sBuilder.toString();
				byte[] plainBytes = plainXML.getBytes(charset);
				String keyStr = MSCommonUtil.generateLenString(16);
				;
				byte[] keyBytes = keyStr.getBytes(charset);
				String encryptData = new String(Base64.encodeBase64((CryptoUtil.AESEncrypt(plainBytes, keyBytes, "AES", "AES/ECB/PKCS5Padding", null))), charset);
				String signData = new String(Base64.encodeBase64(CryptoUtil.digitalSign(plainBytes, hzfPriKey, "SHA1WithRSA")), charset);
				String encrtptKey = new String(Base64.encodeBase64(CryptoUtil.RSAEncrypt(keyBytes, yhPubKey, 2048, 11, "RSA/ECB/PKCS1Padding")), charset);
				List<NameValuePair> nvps = new LinkedList<NameValuePair>();
				nvps.add(new BasicNameValuePair("encryptData", encryptData));
				nvps.add(new BasicNameValuePair("encryptKey", encrtptKey));
				
				if("0".equals(debitNote.getSettleType())){
					nvps.add(new BasicNameValuePair("cooperator", MSConfig.cooperator_t0));
				}else{
					nvps.add(new BasicNameValuePair("cooperator", MSConfig.cooperator_t1));
				}
				nvps.add(new BasicNameValuePair("signData", signData));
				nvps.add(new BasicNameValuePair("tranCode", tranCode));
				nvps.add(new BasicNameValuePair("reqMsgId", reqMsgId));
				byte[] b = HttpClient4Util.getInstance().doPost(serverUrl, null, nvps);
				String respStr = new String(b, charset);
				logger.info("返回报文[{}]", new Object[] { respStr });
				JSONObject jsonObject = JSONObject.fromObject(respStr);
				String resEncryptData = jsonObject.getString("encryptData");
				String resEncryptKey = jsonObject.getString("encryptKey");
				byte[] decodeBase64KeyBytes = Base64.decodeBase64(resEncryptKey.getBytes(charset));
				// 解密encryptKey得到merchantAESKey
				byte[] merchantAESKeyBytes = CryptoUtil.RSADecrypt(decodeBase64KeyBytes, hzfPriKey, 2048, 11, "RSA/ECB/PKCS1Padding");
				// 使用base64解码商户请求报文
				byte[] decodeBase64DataBytes = Base64.decodeBase64(resEncryptData.getBytes(charset));
				// 用解密得到的merchantAESKey解密encryptData
				byte[] merchantXmlDataBytes = CryptoUtil.AESDecrypt(decodeBase64DataBytes, merchantAESKeyBytes, "AES", "AES/ECB/PKCS5Padding", null);
				String resXml = new String(merchantXmlDataBytes, charset);
				JSONObject respJSONObject = XmlConvertUtil.documentToJSONObjectFilter(resXml);
				System.out.println(respJSONObject);
				if (respJSONObject.containsKey("resEntity")) {
					JSONObject resEntity = respJSONObject.getJSONObject("resEntity");
						result.put("resData", resEntity);
				}
			}
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
	
	private JSONObject signReturn(JSONObject result){
		String rtSrc = StringUtil.orderedKey(result);
		result.put("signStr", EpaySignUtil.sign(SysConfig.platPrivateKey, rtSrc));
		return result;
	}
	
	
	
}
