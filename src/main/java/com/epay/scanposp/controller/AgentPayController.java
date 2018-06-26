package com.epay.scanposp.controller;


import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

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
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.epay.scanposp.common.constant.MLConfig;
import com.epay.scanposp.common.constant.SysConfig;
import com.epay.scanposp.common.constant.TLConfig;
import com.epay.scanposp.common.constant.TLKJConfig;
import com.epay.scanposp.common.constant.WWConfig;
import com.epay.scanposp.common.constant.YSConfig;
import com.epay.scanposp.common.constant.YZFConfig;
import com.epay.scanposp.common.utils.CommonUtil;
import com.epay.scanposp.common.utils.HttpUtil;
import com.epay.scanposp.common.utils.StringUtil;
import com.epay.scanposp.common.utils.ValidateUtil;
import com.epay.scanposp.common.utils.cj.util.MD5Util;
import com.epay.scanposp.common.utils.constant.RouteCodeConstant;
import com.epay.scanposp.common.utils.epaySecurityUtil.EpaySignUtil;
import com.epay.scanposp.common.utils.ms.HttpClient4Util;
import com.epay.scanposp.common.utils.ms.MSCommonUtil;
import com.epay.scanposp.common.utils.tl.CertUtil;
import com.epay.scanposp.common.utils.tlkj.MapUtils;
import com.epay.scanposp.common.utils.tlkj.RandomTools;
import com.epay.scanposp.common.utils.ys.HttpUtils;
import com.epay.scanposp.common.utils.ys.SwpHashUtil;
import com.epay.scanposp.common.utils.yzf.AESTool;
import com.epay.scanposp.common.utils.yzf.Base64Utils;
import com.epay.scanposp.common.utils.yzf.EncryptUtil;
import com.epay.scanposp.common.utils.yzf.Md5Utils;
import com.epay.scanposp.common.utils.yzf.RSATool;
import com.epay.scanposp.entity.Bank;
import com.epay.scanposp.entity.BankExample;
import com.epay.scanposp.entity.BankRoute;
import com.epay.scanposp.entity.BankRouteExample;
import com.epay.scanposp.entity.EpayCode;
import com.epay.scanposp.entity.EpayCodeExample;
import com.epay.scanposp.entity.MemberDrawRoute;
import com.epay.scanposp.entity.MemberDrawRouteExample;
import com.epay.scanposp.entity.MemberInfo;
import com.epay.scanposp.entity.MemberInfoExample;
import com.epay.scanposp.entity.MemberMerchantCode;
import com.epay.scanposp.entity.MemberMerchantCodeExample;
import com.epay.scanposp.entity.MemberMerchantKey;
import com.epay.scanposp.entity.MemberMerchantKeyExample;
import com.epay.scanposp.entity.RoutewayAccount;
import com.epay.scanposp.entity.RoutewayAccountExample;
import com.epay.scanposp.entity.RoutewayDraw;
import com.epay.scanposp.entity.RoutewayDrawExample;
import com.epay.scanposp.entity.RoutewayDrawLog;
import com.epay.scanposp.entity.RoutewayDrawLogExample;
import com.epay.scanposp.entity.RoutewayDrawTemp;
import com.epay.scanposp.entity.SysCommonConfig;
import com.epay.scanposp.entity.SysCommonConfigExample;
import com.epay.scanposp.entity.SysOffice;
import com.epay.scanposp.entity.SysOfficeExample;
import com.epay.scanposp.service.AccountService;
import com.epay.scanposp.service.BankRouteService;
import com.epay.scanposp.service.BankService;
import com.epay.scanposp.service.CommonService;
import com.epay.scanposp.service.CommonUtilService;
import com.epay.scanposp.service.DebitNoteIpService;
import com.epay.scanposp.service.DebitNoteService;
import com.epay.scanposp.service.EpayCodeService;
import com.epay.scanposp.service.MemberBankService;
import com.epay.scanposp.service.MemberDrawRouteService;
import com.epay.scanposp.service.MemberInfoService;
import com.epay.scanposp.service.MemberMerchantCodeService;
import com.epay.scanposp.service.MemberMerchantKeyService;
import com.epay.scanposp.service.MsResultNoticeService;
import com.epay.scanposp.service.PayResultNoticeService;
import com.epay.scanposp.service.PayResultNotifyService;
import com.epay.scanposp.service.PayTypeService;
import com.epay.scanposp.service.RoutewayAccountService;
import com.epay.scanposp.service.RoutewayDrawLogService;
import com.epay.scanposp.service.RoutewayDrawService;
import com.epay.scanposp.service.RoutewayDrawTempService;
import com.epay.scanposp.service.SysCommonConfigService;
import com.epay.scanposp.service.SysOfficeExtendService;
import com.epay.scanposp.service.SysOfficeService;
import com.epay.scanposp.service.TradeDetailDailyService;
import com.kspay.cert.CertVerify;
import com.kspay.cert.LoadKeyFromPKCS12;



@Controller
public class AgentPayController {
	
	private static Logger logger = LoggerFactory.getLogger(AgentPayController.class);
	
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
	private MemberDrawRouteService memberDrawRouteService;
	
	@Autowired
	private CommonUtilService commonUtilService;
	
	@Autowired
	private RoutewayAccountService routewayAccountService;
	
	@Autowired
	private BankService bankService;
	
	@Autowired
	private BankRouteService bankRouteService;
	
	@Autowired
	private RoutewayDrawLogService routewayDrawLogService;
	
	@Autowired
	private RoutewayDrawTempService routewayDrawTempService;
	
	@ResponseBody
	@RequestMapping("/agentPay/toPay")
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
			memberMerchantCodeExample.createCriteria().andMemberIdEqualTo(memberInfo.getId()).andRouteCodeEqualTo(RouteCodeConstant.YZF_ROUTE_CODE).andDelFlagEqualTo("0");
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
				result.put("returnMsg", "该商户机构信息不完整，请确认后重试");
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
	        memberMerchantKeyExample.createCriteria().andRouteCodeEqualTo(RouteCodeConstant.YZF_ROUTE_CODE).andMerchantCodeEqualTo(merchantCode.getWxMerchantCode()).andDelFlagEqualTo("0");
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
	/*		if(!EpaySignUtil.checksign(sysOffice.getPublicKeyRsa(), signOrginalStr, signedStr)){//by linxf 测试屏蔽
				result.put("returnCode", "0004");
				result.put("returnMsg", "签名校验错误，请检查签名参数是否正确");
				return result;
			}
		*/
			
			double  drawRate = 0 ;
			if("0".equals(memberInfo.getSettleType())){
				drawRate = merchantCode.getT0DrawRate().doubleValue();
			}else{
				drawRate = merchantCode.getT1DrawRate().doubleValue();
			}
			
			String orderCode = CommonUtil.getOrderCode();
			String reqDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			
			//代付手续费
			Double fee = Double.valueOf(new DecimalFormat("#.00").format((new BigDecimal(payMoney)).floatValue()*drawRate));
			
			//实际代付金额
			Double drawAmount = (new BigDecimal(payMoney)).floatValue() - fee;
			
			RoutewayDraw routewayDraw=new RoutewayDraw();
			routewayDraw.setCreateDate(new Date());
			routewayDraw.setDelFlag("0");
			routewayDraw.setRouteCode(RouteCodeConstant.YZF_ROUTE_CODE);
			routewayDraw.setMemberCode(memberInfo.getCode());
			routewayDraw.setMemberId(memberInfo.getId());
			routewayDraw.setMerchantCode(merchantCode.getWxMerchantCode());
			routewayDraw.setDrawType("2");
			routewayDraw.setDrawRate(new BigDecimal(drawRate));
			routewayDraw.setMoney(new BigDecimal(payMoney));
			routewayDraw.setOrderCode(orderCode);
			routewayDraw.setPtSerialNo(orderCode);
			routewayDraw.setOrderNumOuter(orderNum);
			routewayDraw.setDrawamount(new BigDecimal(drawAmount));
			routewayDraw.setDrawfee(new BigDecimal(fee));
			routewayDraw.setTradefee(new BigDecimal(0));
			routewayDraw.setReqDate(reqDate);
			routewayDraw.setAuditStatus("2");
			routewayDraw.setAuditDate(new Date());
			routewayDraw.setBankName(bankName);
			routewayDraw.setBankAccount(bankAccount);
			routewayDraw.setAccountName(accountName);
			routewayDraw.setCertNo(certNo);
			routewayDraw.setTel(tel);
			routewayDrawService.insertSelective(routewayDraw);
			
			routeWayDrawExample = new RoutewayDrawExample();
			routeWayDrawExample.createCriteria().andOrderCodeEqualTo(orderCode);
			List<RoutewayDraw> drawList = routewayDrawService.selectByExample(routeWayDrawExample);
			if(drawList==null || drawList.size()==0){
				result.put("returnCode", "0096");
				result.put("returnMsg", "订单录入异常");
			}
			RoutewayDraw draw = drawList.get(0);
			String callBack = SysConfig.serverUrl + "/agentPay/receivePayNotify";
			logger.info("回调地址："+callBack);
			String serverUrl = YZFConfig.msServerUrl;
			String charset = "UTF-8";
			String version = "2.0";	
			String merNo = merchantCode.getWxMerchantCode();//商户号	
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
			
			JSONObject balancejson = new JSONObject();
	     	
			balancejson.put("payType","10");
			balancejson.put("linkId",CommonUtil.getOrderCode());
			
			String balanceAesData=AESTool.encrypt(balancejson.toString(),aesKey);
			
			logger.info("代付余额查询请求未加密参数[{}]",balancejson.toString() );
			
			String balanceData	= balanceAesData;
			
			String action = "SdkSettleBalance";
	     	
	     	String balanceSignData = version+orgNo+merNo+action+balanceData+md5Key;
	    	String balanceSign	= EncryptUtil.MD5(balanceSignData, 1);
	    	
	     	
	     	List<NameValuePair> nvps = new LinkedList<NameValuePair>();
			nvps.add(new BasicNameValuePair("version", version));
			nvps.add(new BasicNameValuePair("merNo", merNo));
			nvps.add(new BasicNameValuePair("orgNo", orgNo));
			nvps.add(new BasicNameValuePair("action", action));
			nvps.add(new BasicNameValuePair("data", balanceAesData));
			nvps.add(new BasicNameValuePair("encryptkey", encryptkey));
			nvps.add(new BasicNameValuePair("sign", balanceSign));
			byte[] bb = HttpClient4Util.getInstance().doPost(serverUrl, null, nvps);
			
			String respStrb = new String(bb, charset);
			logger.info("代付余额查询返回报文[{}]", new Object[] { respStrb });
			
			JSONObject jsonObjb = JSONObject.fromObject(respStrb);
			byte[] encryptkey_RESb=EncryptUtil.decodeBase64(jsonObjb.get("encryptkey").toString());
			
			byte[] key_RES_Bb=RSATool.decrypt(encryptkey_RESb,privateK);
			String aes_res_keyb=new String (key_RES_Bb);
			//logger.info("代付余额查询返回报文解密AES key[{}]", new Object[] { aes_res_keyb });
			String data_RESb=jsonObjb.get("data").toString();
			String resDatab=AESTool.decrypt(data_RESb, aes_res_keyb);
			logger.info("代付余额查询返回报文解密结果[{}]", new Object[] { resDatab });
			
			JSONObject resObjb = JSONObject.fromObject(resDatab);
			Double balance = 0d;
			if("0000".equals(resObjb.getString("code"))){
				balance = Double.valueOf(resObjb.getString("ableBalanceT0"));
			}else{
				result.put("returnCode", "0003");
				result.put("returnMsg", "账户余额查询失败:"+resObjb.getString("msg"));
				draw.setRespType("E");
				draw.setRespCode(resObjb.getString("code"));
				draw.setRespMsg(resObjb.getString("msg"));
				draw.setUpdateDate(new Date());
				routewayDrawService.updateByPrimaryKey(draw);
				return result;
			}
			
			if(drawAmount>balance){
				result.put("returnCode", "0005");
				result.put("returnMsg", "代付金额大于账户余额");
				draw.setRespType("E");
				draw.setRespMsg("代付金额大于账户余额");
				draw.setUpdateDate(new Date());
				routewayDrawService.updateByPrimaryKey(draw);
				return result;
			}
			
			JSONObject subObj = queryBankSubId(bankAccount,merchantCode,merchantKey);
			String subId = "";
			if("0000".equals(subObj.get("returnCode"))){
				subId = subObj.getString("subId");
			}
			
			action = "SdkSettleMcg";
			
			JSONObject contextjson = new JSONObject();
	     	
	     	contextjson.put("linkId",orderCode);//三方订单流水号
	     	contextjson.put("amount",String.valueOf((new BigDecimal(drawAmount)).multiply(new BigDecimal(100)).intValue()));//结算到账金额 单位分
	     	contextjson.put("bankNo",bankAccount);//结算银行卡号
	     	contextjson.put("bankAccount",accountName);//结算银行账户
	     	contextjson.put("bankPhone",tel);//绑定手机号码
	     	contextjson.put("bankCert",certNo);//身份证号
	     	contextjson.put("bankName",bankName);//银行名称
	     	//contextjson.put("bankCode",bankCode);//银行支行联行号
	     	contextjson.put("notifyUrl",callBack);//支付结果回调地址
	     	if(!"".equals(subId)){
	     		contextjson.put("bankCode",subId);
	     	}
	     	draw.setSubId(subId);
	     	
	     	String aesData=AESTool.encrypt(contextjson.toString(),aesKey);
			
			logger.info("代付请求未加密参数[{}]",contextjson.toString() );
			
			
	     	String data	= aesData;//EncryptUtil.getBase64(aesData);			
	     	
	     	String signData = version+orgNo+merNo+action+data+md5Key;
	    	String sign	= EncryptUtil.MD5(signData, 1);
	    	
	     	
			nvps = new LinkedList<NameValuePair>();
			nvps.add(new BasicNameValuePair("version", version));
			nvps.add(new BasicNameValuePair("merNo", merNo));
			nvps.add(new BasicNameValuePair("orgNo", orgNo));
			nvps.add(new BasicNameValuePair("action", action));
			nvps.add(new BasicNameValuePair("data", aesData));
			nvps.add(new BasicNameValuePair("encryptkey", encryptkey));
			nvps.add(new BasicNameValuePair("sign", sign));
			byte[] b = HttpClient4Util.getInstance().doPost(serverUrl, null, nvps);
			
			String respStr = new String(b, charset);
			logger.info("代付请求返回报文[{}]", new Object[] { respStr });
			
			JSONObject jsonObj = JSONObject.fromObject(respStr);
			byte[] encryptkey_RES=EncryptUtil.decodeBase64(jsonObj.get("encryptkey").toString());
			
			byte[] key_RES_B=RSATool.decrypt(encryptkey_RES,privateK);
			String aes_res_key=new String (key_RES_B);
			logger.info("代付请求返回报文解密AES key[{}]", new Object[] { aes_res_key });
			String data_RES=jsonObj.get("data").toString();
			String resData=AESTool.decrypt(data_RES, aes_res_key);
			logger.info("代付请求返回报文解密结果[{}]", new Object[] { resData });
			
			JSONObject resObj = JSONObject.fromObject(resData);
			String code = resObj.getString("code");
			if("0000".equals(code)||"0100".equals(code)){
				draw.setRespType("R");
				draw.setUpdateDate(new Date());
				result.put("returnCode", "0000");
				result.put("returnMsg", "提交成功");
			}else{
				result.put("returnCode", "0003");
				result.put("returnMsg", resObj.getString("msg"));
				draw.setRespType("E");
				draw.setRespCode(code);
				draw.setRespMsg(resObj.getString("msg"));
				draw.setUpdateDate(new Date());
			}
			
			routewayDrawService.updateByPrimaryKey(draw);
			

		}catch(Exception e){
			logger.info(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
		}
		return result;
	}
	
	public JSONObject queryBankSubId(String bankAccount,MemberMerchantCode merchantCode,MemberMerchantKey merchantKey){
		JSONObject result = new JSONObject();
		try{
			String serverUrl = YZFConfig.msServerUrl;
			String charset = "UTF-8";
			String version = "2.0";	
			String action = "SdkSettleBankCnaps";
			String merNo = merchantCode.getWxMerchantCode();//商户号	
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
	     	contextjson.put("bankNo",bankAccount);
	     	
	     	String aesData=AESTool.encrypt(contextjson.toString(),aesKey);
			
			logger.info("代付联行号查询未加密参数[{}]",contextjson.toString() );
			
			
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
			logger.info("代付联行号查询返回报文[{}]", new Object[] { respStr });
			
			JSONObject jsonObj = JSONObject.fromObject(respStr);
			byte[] encryptkey_RES=EncryptUtil.decodeBase64(jsonObj.get("encryptkey").toString());
			
			byte[] key_RES_B=RSATool.decrypt(encryptkey_RES,privateK);
			String aes_res_key=new String (key_RES_B);
			//logger.info("代付联行号查询返回报文解密AES key[{}]", new Object[] { aes_res_key });
			String data_RES=jsonObj.get("data").toString();
			String resData=AESTool.decrypt(data_RES, aes_res_key);
			logger.info("代付联行号查询返回报文解密结果[{}]", new Object[] { resData });
			
			JSONObject resObj = JSONObject.fromObject(resData);
			String code = resObj.getString("code");
			if("0000".equals(code)){
				result.put("returnCode", "0000");
				result.put("returnMsg", "查询成功");
				result.put("subId", resObj.get("settleBankCnaps"));
			}else{
				result.put("returnCode", "0003");
				result.put("returnMsg", resObj.getString("msg"));
			}
	     	
		}catch(Exception e){
			logger.info(e.getMessage(), e);
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
		}
		return result;
	}
	
	
	@RequestMapping("/agentPay/receivePayNotify")
	public void receivePayNotify(HttpServletRequest request,HttpServletResponse response) {
		String respString = "success";
		try {
			String linkId = request.getParameter("linkId");
			String settleNo = request.getParameter("settleNo");
			String settleStatus = request.getParameter("settleStatus");
			String settleTime = request.getParameter("settleTime");
			String settleMemo = request.getParameter("settleMemo");
			String sign = request.getParameter("sign");
			logger.info("receivePayNotify回调通知返回linkId[{}]",  linkId );
			logger.info("receivePayNotify回调通知返回settleNo[{}]",  settleNo );
			logger.info("receivePayNotify回调通知返回settleStatus[{}]",  settleStatus );
			logger.info("receivePayNotify回调通知返回settleTime[{}]",  settleTime );
			logger.info("receivePayNotify回调通知返回settleMemo[{}]",  settleMemo );
			logger.info("receivePayNotify回调通知返回sign[{}]",  sign );
			
			RoutewayDrawExample routewayDrawExample = new RoutewayDrawExample();
			routewayDrawExample.createCriteria().andOrderCodeEqualTo(linkId).andDelFlagEqualTo("0");
			List<RoutewayDraw> routeWayDrawList = routewayDrawService.selectByExample(routewayDrawExample);
			if(routeWayDrawList==null || routeWayDrawList.size()==0){
				response.getWriter().write(respString);
				return;
			}
			
			RoutewayDraw draw = routeWayDrawList.get(0);
			if("R".equals(draw.getRespType())){
				MemberMerchantKeyExample memberMerchantKeyExample = new MemberMerchantKeyExample();
		        memberMerchantKeyExample.createCriteria().andRouteCodeEqualTo(RouteCodeConstant.YZF_ROUTE_CODE).andMerchantCodeEqualTo(draw.getMerchantCode()).andDelFlagEqualTo("0");
		        List<MemberMerchantKey> keyList = memberMerchantKeyService.selectByExample(memberMerchantKeyExample);
		        if(keyList == null || keyList.size()!=1){
		        	return;
		        }
		        MemberMerchantKey merchantKey = keyList.get(0);
		        String signStr = Md5Utils.getMd5(settleNo+settleStatus+merchantKey.getPrivateKeyPassword());
		        if(!sign.equals(signStr)){
		        	logger.info("receivePayNotify回调通知验签失败，加密后sign[{}]",  signStr );
		        	response.getWriter().write("fail");
					return;
		        }
		        
		        if("0000".equals(settleStatus)){
					draw.setRespType("S");
				}else if("0001".equals(settleStatus)){
					draw.setRespType("E");
				}
				draw.setRespCode(settleStatus);
				draw.setRespMsg(settleMemo);
				draw.setRespDate(settleTime);
				draw.setUpdateDate(new Date());
				routewayDrawService.updateByPrimaryKey(draw);
		        
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
	
	private JSONObject signReturn(JSONObject result){
		String rtSrc = StringUtil.orderedKey(result);
		result.put("signStr", EpaySignUtil.sign(SysConfig.platPrivateKey, rtSrc));
		return result;
	}
	
	
	/**
	 * 同名信用卡代付
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/agentPay/toSameNamePay")
	public JSONObject toSameNamePay(HttpServletRequest request,HttpServletResponse response){
		Map<String,String> inparam = new HashMap<String, String>();
		Enumeration<String> pNames=request.getParameterNames();
		while(pNames.hasMoreElements()){
		    String name=(String)pNames.nextElement();
		    String value=request.getParameter(name);
		    inparam.put(name, value);
		}
		logger.info("同名信用卡代付下游入参[{}]",  JSONObject.fromObject(inparam).toString() );
		String bankAccount = request.getParameter("bankAccount");
		String memberCode = request.getParameter("memberCode");
		String orderNum = request.getParameter("orderNum");
		String payMoney = request.getParameter("payMoney");
		String signStr = request.getParameter("signStr");
		
		StringBuilder srcStr = new StringBuilder();
		JSONObject result = new JSONObject();
		
		if(bankAccount == null || "".equals(bankAccount)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "银行卡号缺失");
			return signReturn(result);
		}
		srcStr.append("bankAccount="+bankAccount);
		
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
		
		if(signStr == null || "".equals(signStr)){ 
			result.put("returnCode", "0007");
			result.put("returnMsg", "缺少签名信息");
			return signReturn(result);
		}
		result = validMemberInfoForSameAgentPay(memberCode, orderNum, payMoney,bankAccount,"","","",srcStr.toString(), signStr);
		
		return signReturn(result);
	}
	
	
	public JSONObject validMemberInfoForSameAgentPay(String memberCode,String orderNum,String payMoney,String bankAccount,String accountName,String bankCode,String settleFeeStr,String signOrginalStr,String signedStr){
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
			
			String routeCode = memberInfo.getWxRouteId();
			MemberMerchantCodeExample memberMerchantCodeExample = new MemberMerchantCodeExample();
			memberMerchantCodeExample.createCriteria().andMemberIdEqualTo(memberInfo.getId()).andRouteCodeEqualTo(routeCode).andDelFlagEqualTo("0");
			List<MemberMerchantCode> merchantCodes = memberMerchantCodeService.selectByExample(memberMerchantCodeExample);
			if (merchantCodes == null || merchantCodes.size() != 1) {
				result.put("returnCode", "0008");
				result.put("returnMsg", "对不起，商户编码不存在");
				return result;
			}
			
			MemberMerchantCode merchantCode = merchantCodes.get(0);
			String merCode = merchantCode.getKjMerchantCode();
			
			if(routeCode.equals(RouteCodeConstant.TLKJ_ROUTE_CODE)){
				if(settleFeeStr==null || "".equals(settleFeeStr)){
					result.put("returnCode", "0007");
					result.put("returnMsg", "缺少代付手续费");
					return result;
				}
			}
			
			SysOfficeExample sysOfficeExample = new SysOfficeExample();
			sysOfficeExample.or().andIdEqualTo(epayCodeList.get(0).getOfficeId()).andAgtTypeEqualTo("3");
			List<SysOffice> sysOfficeList = sysOfficeService.selectByExample(sysOfficeExample);
			if(null == sysOfficeList || sysOfficeList.size() == 0){
				result.put("returnCode", "0008");
				result.put("returnMsg", "该商户机构信息不完整，请确认后重试");
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
			
			SysOffice sysOffice = sysOfficeList.get(0);
			
			if(!EpaySignUtil.checksign(sysOffice.getPublicKeyRsa(), signOrginalStr, signedStr)){//by linxf 测试屏蔽
				result.put("returnCode", "0004");
				result.put("returnMsg", "签名校验错误，请检查签名参数是否正确");
				return result;
			}
		
			String bankName = "";
			if(!StringUtil.isEmpty(bankCode)){
				BankRouteExample bankRouteExample = new BankRouteExample();
				bankRouteExample.createCriteria().andCodeEqualTo(bankCode).andRouteCodeEqualTo(routeCode).andDelFlagEqualTo("0");
				List<BankRoute> list = bankRouteService.selectByExample(bankRouteExample);
				if(list==null || list.size()==0){
					result.put("returnCode", "0004");
					result.put("returnMsg", "银行编码不存在或暂不支持该银行");
					return result;
				}
				BankRoute bankRoute = list.get(0);
				bankName = bankRoute.getRouteBankName();
				bankCode = bankRoute.getRouteBankCode();
			}
			
			String platDrawFee = "0";
			SysCommonConfigExample sysCommonConfigExample = new SysCommonConfigExample();
			sysCommonConfigExample.or().andNameEqualTo("PLAT_DRAW_FEE_"+routeCode+"_"+sysOffice.getId()).andDelFlagEqualTo("0");
			List<SysCommonConfig> sysCommonConfig = sysCommonConfigService.selectByExample(sysCommonConfigExample);
			if (sysCommonConfig.size() == 0) {
				result.put("returnCode", "4004");
				result.put("returnMsg", "系统默认代付费用配置缺失，请与管理员联系");
				return CommonUtil.signReturn(result);
			}
			platDrawFee = sysCommonConfig.get(0).getValue();
			
			double  drawFee = 0 ;
			double memberDrawFee = 0;
			double settleFee = 0;
			if("0".equals(memberInfo.getSettleType())){
				memberDrawFee = merchantCode.getKjT0DrawFee().doubleValue();
			}else{
				memberDrawFee = merchantCode.getKjT1DrawFee().doubleValue();
			}
			if(!"".equals(settleFeeStr)){
				settleFee = Double.parseDouble(settleFeeStr);
			}else{
				settleFee = memberDrawFee;
			}
			drawFee = settleFee;
			
			String orderCode = CommonUtil.getOrderCode();
			String reqDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			
			//实际代付金额
			Double drawAmount = (new BigDecimal(payMoney).subtract(new BigDecimal(drawFee))).doubleValue();
			
			RoutewayDraw routewayDraw=new RoutewayDraw();
			routewayDraw.setCreateDate(new Date());
			routewayDraw.setDelFlag("0");
			routewayDraw.setRouteCode(routeCode);
			routewayDraw.setMemberCode(memberInfo.getCode());
			routewayDraw.setMemberId(memberInfo.getId());
			routewayDraw.setMerchantCode(merCode);
			routewayDraw.setDrawType("2");
			//routewayDraw.setDrawRate(new BigDecimal(drawRate));
			routewayDraw.setMoney(new BigDecimal(payMoney));
			routewayDraw.setOrderCode(orderCode);
			routewayDraw.setPtSerialNo(orderCode);
			routewayDraw.setOrderNumOuter(orderNum);
			routewayDraw.setDrawamount(new BigDecimal(drawAmount));
			routewayDraw.setDrawfee(new BigDecimal(drawFee));
			routewayDraw.setTradefee(new BigDecimal(0));
			routewayDraw.setReqDate(reqDate);
			routewayDraw.setAuditStatus("2");
			routewayDraw.setAuditDate(new Date());
			routewayDraw.setBankName(bankName);
			routewayDraw.setBankAccount(bankAccount);
			routewayDraw.setAccountName(accountName);
			routewayDraw.setCertNo("");
			routewayDraw.setTel("");
			routewayDraw.setDrawProfit(new BigDecimal(memberDrawFee).subtract(new BigDecimal(platDrawFee)));
			routewayDraw.setMemberDrawProfit(new BigDecimal(drawFee).subtract(new BigDecimal(memberDrawFee)));
			routewayDrawService.insertSelective(routewayDraw);
			
			routeWayDrawExample = new RoutewayDrawExample();
			routeWayDrawExample.createCriteria().andOrderCodeEqualTo(orderCode);
			List<RoutewayDraw> drawList = routewayDrawService.selectByExample(routeWayDrawExample);
			if(drawList==null || drawList.size()==0){
				result.put("returnCode", "0096");
				result.put("returnMsg", "订单录入异常");
			}
			RoutewayDraw draw = drawList.get(0);
			
			if(settleFee < memberDrawFee){
				result.put("returnCode", "0005");
				result.put("returnMsg", "代付手续费必须大等于商户最低费用");
				draw.setRespType("E");
				draw.setRespMsg("代付手续费必须大等于商户最低费用");
				draw.setUpdateDate(new Date());
				routewayDrawService.updateByPrimaryKey(draw);
				return result;
			}
			
			if(Double.parseDouble(payMoney)<=drawFee){
				result.put("returnCode", "0005");
				result.put("returnMsg", "代付金额必须大于代付手续费");
				draw.setRespType("E");
				draw.setRespMsg("代付金额必须大于代付手续费");
				draw.setUpdateDate(new Date());
				routewayDrawService.updateByPrimaryKey(draw);
				return result;
			}
			
			JSONObject resObjb = null;
			if(routeCode.equals(RouteCodeConstant.YS_ROUTE_CODE)){
				resObjb = ysBalance(merCode);
			}else if(routeCode.equals(RouteCodeConstant.WW_ROUTE_CODE)){
				resObjb = wwBalance(merCode);
			}else if(routeCode.equals(RouteCodeConstant.ML_ROUTE_CODE)){
				resObjb = mlBalance(merCode);
			}else if(routeCode.equals(RouteCodeConstant.TLKJ_ROUTE_CODE)){
				resObjb = tlkjBalance(merCode,bankAccount);
			}
			
			Double balance = 0d;
			if("0000".equals(resObjb.getString("returnCode"))){
				if(routeCode.equals(RouteCodeConstant.YS_ROUTE_CODE)){
					balance = Double.valueOf(resObjb.getString("balance"))/100;
				}else if(routeCode.equals(RouteCodeConstant.WW_ROUTE_CODE)){
					balance = Double.valueOf(resObjb.getString("balance"));
				}else if(routeCode.equals(RouteCodeConstant.ML_ROUTE_CODE)){
					balance = Double.valueOf(resObjb.getString("balance"));
				}else if(routeCode.equals(RouteCodeConstant.TLKJ_ROUTE_CODE)){
					balance = Double.valueOf(resObjb.getString("balance"))/100;
				}
			}else{
				result.put("returnCode", "0003");
				result.put("returnMsg", "账户余额查询失败:"+resObjb.getString("returnMsg"));
				draw.setRespType("E");
				draw.setRespCode(resObjb.getString("returnCode"));
				draw.setRespMsg(resObjb.getString("returnMsg"));
				draw.setUpdateDate(new Date());
				routewayDrawService.updateByPrimaryKey(draw);
				return result;
			}
			if(Double.valueOf(payMoney)>balance){
				result.put("returnCode", "0005");
				result.put("returnMsg", "代付金额大于账户余额");
				draw.setRespType("E");
				draw.setRespMsg("代付金额大于账户余额");
				draw.setUpdateDate(new Date());
				routewayDrawService.updateByPrimaryKey(draw);
				return result;
			}
			
			JSONObject resObj = null;
			if(routeCode.equals(RouteCodeConstant.YS_ROUTE_CODE)){
				resObj = ysAgentPay(orderCode, merCode, bankAccount, payMoney);
			}else if(routeCode.equals(RouteCodeConstant.WW_ROUTE_CODE)){
				resObj = wwAgentPay(orderCode, merCode, bankAccount, payMoney);
			}else if(routeCode.equals(RouteCodeConstant.ML_ROUTE_CODE)){
				resObj = mlAgentPay(orderCode, merCode, bankAccount,accountName,bankCode,bankName, String.valueOf(drawAmount),draw);
			}else if(routeCode.equals(RouteCodeConstant.TLKJ_ROUTE_CODE)){
				resObj = tlkjAgentPay(orderCode, merCode, bankAccount,accountName,bankCode,bankName, String.valueOf(drawAmount),draw);
			}
			String code = resObj.getString("returnCode");
			String resultMsg = "";
			boolean flag = false;
			String channelNo = "";
			if(resObj.containsKey("channelNo")){
				channelNo = resObj.getString("channelNo");
			}
			if("0000".equals(code)){
				draw.setRespType("R");
				draw.setUpdateDate(new Date());
				draw.setChannelNo(channelNo);
				result.put("returnCode", "0000");
				result.put("returnMsg", "提交成功");
				flag = true;
			}else{
				resultMsg = resObj.getString("returnMsg");
			}
			if(!flag){
				result.put("returnCode", "0003");
				result.put("returnMsg", resultMsg);
				draw.setRespType("E");
				draw.setRespCode(code);
				draw.setChannelNo(channelNo);
				draw.setRespMsg(resultMsg);
				draw.setUpdateDate(new Date());
			}
			
			routewayDrawService.updateByPrimaryKey(draw);
		}catch(Exception e){
			logger.info(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
		}
		return result;
	}
	//易生代付
	public JSONObject ysAgentPay(String orderCode,String merCode,String bankAccount,String payMoney){
		JSONObject result = new JSONObject();
		try{
			String callBack = SysConfig.serverUrl + "/agentPay/ysReceivePayNotify";
			String serverUrl = YSConfig.msServerUrl + "/swp/dh/jiesuan.do";
			String privateKey = YSConfig.privateKey;
			
			Map<String,String> param = new HashMap<String, String>();
			param.put("sp_id", YSConfig.orgNo);
			param.put("mch_id", YSConfig.defaultMerNoApay);
			param.put("out_js_trade_no", orderCode);
			param.put("sub_mch_id", merCode);
			param.put("in_acc_no", bankAccount);
			param.put("daifu_amt", String.valueOf((new BigDecimal(payMoney)).multiply(new BigDecimal(100)).intValue()));
			param.put("notifyurl", callBack);
			
			Date t = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(t);
			long sys_timestamp = cal.getTimeInMillis();
			param.put("timestamp", String.valueOf(sys_timestamp));
			
			String srcStr = StringUtil.orderedKey(param)+"&key="+privateKey;
			String sign = SwpHashUtil.getSign(srcStr, privateKey, "SHA256");
			String paramStr = StringUtil.orderedKey(param) + "&sign="+sign;
			logger.info("易生代付参数[{}]",paramStr );

			HttpResponse httpResponse =HttpUtils.doPost(serverUrl, "", paramStr, "application/x-www-form-urlencoded; charset=UTF-8");
			String respStr = EntityUtils.toString(httpResponse.getEntity());
			logger.info("易生代付返回报文[{}]", new Object[] { respStr });
			
			JSONObject resObj = JSONObject.fromObject(respStr);
			String code = resObj.getString("status");
			String resultMsg = "";
			boolean flag = false;
			String channelNo = "";
			if(resObj.containsKey("daifu_sys_trade_no")){
				channelNo = resObj.getString("daifu_sys_trade_no");
			}
			if("SUCCESS".equals(code)){
				String resSign = resObj.getString("sign");
				resObj.remove("sign");
				srcStr = StringUtil.orderedKey(resObj)+"&key="+privateKey;
				if(resSign.equals(SwpHashUtil.getSign(srcStr, privateKey, "SHA256"))){
					String trade_state = resObj.getString("trade_state");
					if("SUCCESS".equals(trade_state)||"PROCESSING".equals(trade_state)){
						result.put("returnCode", "0000");
						result.put("returnMsg", "提交成功");
						result.put("channelNo", channelNo);
						flag = true;
					}else{
						resultMsg = resObj.getString("trade_state_desc");
					}
				}else{
					resultMsg = "代付出参验签失败";
				}
			}else{
				resultMsg = resObj.getString("message");
			}
			if(!flag){
				result.put("returnCode", "0003");
				result.put("returnMsg", resultMsg);
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
		}
		return result;
	}
	//微微代付
	public JSONObject wwAgentPay(String orderCode,String merCode,String bankAccount,String payMoney){
		JSONObject result = new JSONObject();
		try{
			String serverUrl = WWConfig.msServerUrl + "/agentPay/toSameNamePay";
			String privateKey = WWConfig.privateKey;
			
			Map<String,String> param = new HashMap<String, String>();
			param.put("orderNum", orderCode);
			param.put("memberCode", merCode);
			param.put("bankAccount", bankAccount);
			param.put("payMoney", payMoney);
			
			String srcStr = StringUtil.orderedKey(param);
			String sign = EpaySignUtil.sign(privateKey, srcStr);
			param.put("signStr", sign);
			logger.info("微微代付参数[{}]",JSONObject.fromObject(param).toString() );

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
			logger.info("微微代付返回报文[{}]", new Object[] { respStr });
			
			String code = resObj.getString("returnCode");
			String resSign = resObj.getString("signStr");
			resObj.remove("signStr");
			srcStr = StringUtil.orderedKey(resObj);
			if(EpaySignUtil.checksign(WWConfig.platPublicKey, srcStr, resSign)){
				if("0000".equals(code)){
					result.put("returnCode", "0000");
					result.put("returnMsg", "提交成功");
				}else{
					result.put("returnCode", "0003");
					result.put("returnMsg", resObj.getString("returnMsg"));
				}
			}else{
				result.put("returnCode", "0003");
				result.put("returnMsg", "代付出参验签失败");
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
		}
		return result;
	}
	
	
	/**
	 * 易生商户余额查询
	 * @param merCode
	 * @return
	 */
	public JSONObject ysBalance(String merCode){
		JSONObject result = new JSONObject();
		try {
			String serverUrl = YSConfig.msServerUrl+"/swp/dh/mer_accinfo.do";
			String privateKey = YSConfig.privateKey;
			
			Map<String,String> param = new HashMap<String, String>();
			param.put("sp_id", YSConfig.orgNo);
			param.put("mch_id", YSConfig.defaultMerNoApay);
			param.put("sub_mch_id", merCode);
			
			Date t = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(t);
			long sys_timestamp = cal.getTimeInMillis();
			param.put("timestamp", String.valueOf(sys_timestamp));
			
			String srcStr = StringUtil.orderedKey(param)+"&key="+privateKey;
			String sign = SwpHashUtil.getSign(srcStr, privateKey, "SHA256");
			String paramStr = StringUtil.orderedKey(param) + "&sign="+sign;
			logger.info("易生商户余额查询参数[{}]",paramStr );

			HttpResponse httpResponse =HttpUtils.doPost(serverUrl, "", paramStr, "application/x-www-form-urlencoded; charset=UTF-8");
			String respStr = EntityUtils.toString(httpResponse.getEntity());
			logger.info("易生商户余额查询返回报文[{}]", new Object[] { respStr });
			
			JSONObject resObj = JSONObject.fromObject(respStr);
			String code = resObj.getString("status");
			String resultMsg = "";
			boolean flag = false;
			if("SUCCESS".equals(code)){
				String resSign = resObj.getString("sign");
				resObj.remove("sign");
				srcStr = StringUtil.orderedKey(resObj)+"&key="+privateKey;
				if(resSign.equals(SwpHashUtil.getSign(srcStr, privateKey, "SHA256"))){
					String trade_state = resObj.getString("trade_state");
					if("SUCCESS".equals(trade_state)){
						result.put("returnCode", "0000");
						result.put("returnMsg", "成功");
						result.put("balance", resObj.getString("amt"));
						flag = true;
					}else{
						resultMsg = resObj.getString("trade_state_desc");
					}
				}else{
					resultMsg = "商户余额查询出参验签失败";
				}
			}else{
				resultMsg = resObj.getString("message");
			}
			if(!flag){
				result.put("returnCode", "0003");
				result.put("returnMsg", resultMsg);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
			return result;
		}
		return result;
	}
	/**
	 * 易生代付回调通知
	 * @param request
	 * @param response
	 */
	@RequestMapping("/agentPay/ysReceivePayNotify")
	public void ysReceivePayNotify(HttpServletRequest request,HttpServletResponse response) {
		String respString = "SUCCESS";
		String res = "";
		try {
			Map<String,String> param = new HashMap<String, String>();
			Enumeration pNames=request.getParameterNames();
			while(pNames.hasMoreElements()){
			    String name=(String)pNames.nextElement();
			    String value=request.getParameter(name);
			    param.put(name, value);
			}
			logger.info("ysReceivePayNotify回调返回报文[{}]",  JSONObject.fromObject(param).toString() );
			String out_trade_no = param.get("out_js_trade_no");
			String sys_trade_no = param.get("daifu_sys_trade_no");
			String daifu_state = param.get("daifu_state");
			String daifu_amt = param.get("daifu_amt");
			String daifu_fee = param.get("daifu_fee");
			String sign = param.get("sign");
			param.remove("sign");
			String privateKey = YSConfig.privateKey;
			String srcStr = StringUtil.orderedKey(param)+"&key="+privateKey;
			if(!sign.equals(SwpHashUtil.getSign(srcStr, privateKey, "SHA256"))){
				logger.info("验证签名不通过");
                response.getWriter().write("FAIL");
        		return;
			}
			String reqMsgId = out_trade_no;
			RoutewayDrawExample routewayDrawExample = new RoutewayDrawExample();
			routewayDrawExample.createCriteria().andOrderCodeEqualTo(reqMsgId).andDelFlagEqualTo("0");
			List<RoutewayDraw> routeWayDrawList = routewayDrawService.selectByExample(routewayDrawExample);
			if(routeWayDrawList==null || routeWayDrawList.size()==0){
				res = "订单不存在";
                respString = "FAIL";
                logger.info(res);
                response.getWriter().write(respString);
        		return;
			}
			RoutewayDraw draw = routeWayDrawList.get(0);
			if("R".equals(draw.getRespType())){
				if("SUCCESS".equals(daifu_state)){
					draw.setRespType("S");
					draw.setRespCode("000");
				}else if("PROCESSING".equals(daifu_state)){
					draw.setRespType("R");
				}else{
					draw.setRespType("E");
					draw.setRespCode(daifu_state);
				}
	            draw.setRespDate(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
				draw.setUpdateDate(new Date());
				draw.setChannelNo(sys_trade_no);
				routewayDrawService.updateByPrimaryKey(draw);
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
	
	/**
	 * 微微商户余额查询
	 * @param merCode
	 * @return
	 */
	public JSONObject wwBalance(String merCode){
		JSONObject result = new JSONObject();
		try {
			String serverUrl = WWConfig.msServerUrl+"/memberInfo/balance";
			String privateKey = WWConfig.privateKey;
			
			Map<String,String> param = new HashMap<String, String>();
			param.put("memberCode", merCode);
			
			String srcStr = StringUtil.orderedKey(param);
			String sign = EpaySignUtil.sign(privateKey, srcStr);
			param.put("signStr", sign);
			logger.info("微微商户余额查询参数[{}]",JSONObject.fromObject(param).toString() );
			
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
			logger.info("微微商户余额查询返回报文[{}]", new Object[] { respStr });
			
			String code = resObj.getString("returnCode");
			String resSign = resObj.getString("signStr");
			resObj.remove("signStr");
			srcStr = StringUtil.orderedKey(resObj);
			if(EpaySignUtil.checksign(WWConfig.platPublicKey, srcStr, resSign)){
				if("0000".equals(code)){
					result.put("returnCode", "0000");
					result.put("returnMsg", "成功");
					result.put("balance", resObj.get("balance"));
				}else{
					result.put("returnCode", "0003");
					result.put("returnMsg", resObj.getString("returnMsg"));
				}
			}else{
				result.put("returnCode", "0003");
				result.put("returnMsg", "商户余额查询出参验签失败");
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
	@RequestMapping("/memberInfo/balancetest")
	public JSONObject balance1(HttpServletRequest request,HttpServletResponse response){
		JSONObject result = new JSONObject();
		try {
			String serverUrl = TLConfig.msServerUrl;
			JSONObject reqData = new JSONObject();
			reqData.put("ACTION_NAME", "QUERY_CHANNEL");
			JSONObject reqBody = new JSONObject();
			reqBody.put("COM_ID", "10000013");
			reqBody.put("NONCE_STR", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
			
			String srcStr = StringUtil.orderedKey(reqBody);
			
			CertUtil util = new CertUtil();
			String keyStorePath = util.getConfigPath()+"10000013.pfx";
			String keyStorePassword = "999999";
			String aliasPassword = "999999";
			String alias = "co";
			String sign = CertUtil.sign(srcStr, keyStorePath, keyStorePassword, alias, aliasPassword);
			
			reqBody.put("SIGN", sign);
			
			reqData.put("ACTION_INFO", reqBody.toString());
			
			logger.info("通联扫码请求数据[{}]", new Object[] { reqData.toString() });
			
			String respStr = HttpUtil.sendPostRequest(serverUrl, reqData.toString());
			
			logger.info("通联扫码返回报文[{}]", new Object[] { respStr });
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
			return CommonUtil.signReturn(result);
		}
		return CommonUtil.signReturn(result);
	}
	
	/**
	 * 代付申请
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/agentPay/toApply")
	public JSONObject toApply(HttpServletRequest request,HttpServletResponse response){
		Map<String,String> inparam = new HashMap<String, String>();
		Enumeration<String> pNames=request.getParameterNames();
		while(pNames.hasMoreElements()){
		    String name=(String)pNames.nextElement();
		    String value=request.getParameter(name);
		    inparam.put(name, value);
		}
		logger.info("代付申请下游入参[{}]",  JSONObject.fromObject(inparam).toString() );
		
		String accountName = request.getParameter("accountName");
		String bankAccount = request.getParameter("bankAccount");
		String bankCode = request.getParameter("bankCode");
		String certNo = request.getParameter("certNo");
		String memberCode = request.getParameter("memberCode");
		String orderNum = request.getParameter("orderNum");
		String routeCode = request.getParameter("payFlag");
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
		
		if(bankCode == null || "".equals(bankCode)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "银行编码缺失");
			return signReturn(result);
		}
		srcStr.append("&bankCode="+bankCode);
		
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
		
		if(routeCode == null || "".equals(routeCode)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "代付标识缺失");
			return signReturn(result);
		}
		srcStr.append("&payFlag="+routeCode);
		
		if(null == payMoney || !ValidateUtil.isDoubleT(payMoney) || Double.parseDouble(payMoney)<=0){
			result.put("returnCode", "0007");
			result.put("returnMsg", "代付金额输入不正确");
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
		result = validMemberInfoForAgentApply(memberCode, orderNum, payMoney,routeCode, bankCode,accountName,bankAccount,certNo,tel,  srcStr.toString(), signStr);
		
		return signReturn(result);
	}
	
	public JSONObject validMemberInfoForAgentApply(String memberCode,String orderNum,String payMoney,String routeCode,String bankCode,String accountName,String bankAccount,String certNo ,String tel,String signOrginalStr,String signedStr){
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
				result.put("returnMsg", "该商户机构信息不完整，请确认后重试");
				return result;
			}
			
			
			RoutewayDrawExample routeWayDrawExample = new RoutewayDrawExample();
			routeWayDrawExample.createCriteria().andOrderNumOuterEqualTo(orderNum).andDelFlagEqualTo("0");
			List<RoutewayDraw> routeWayDrawList = routewayDrawService.selectByExample(routeWayDrawExample);
			if(null != routeWayDrawList && routeWayDrawList.size() > 0){
				result.put("returnCode", "0002");
				result.put("returnMsg", "该订单号已存在，请勿重复发起");
				return result;
			}
			
			SysOffice sysOffice = sysOfficeList.get(0);
			if(!EpaySignUtil.checksign(sysOffice.getPublicKeyRsa(), signOrginalStr, signedStr)){//by linxf 测试屏蔽
				result.put("returnCode", "0004");
				result.put("returnMsg", "签名校验错误，请检查签名参数是否正确");
				return result;
			}
		
			MemberDrawRouteExample memberDrawRouteExample = new MemberDrawRouteExample();
			memberDrawRouteExample.createCriteria().andMemberIdEqualTo(memberInfo.getId()).andRouteCodeEqualTo(routeCode).andDelFlagEqualTo("0");
			List<MemberDrawRoute> routeList = memberDrawRouteService.selectByExample(memberDrawRouteExample);
			if(routeList==null||routeList.size()==0){
				memberDrawRouteExample = new MemberDrawRouteExample();
				memberDrawRouteExample.createCriteria().andMemberIdEqualTo(0).andRouteCodeEqualTo(routeCode).andDelFlagEqualTo("0");
				routeList = memberDrawRouteService.selectByExample(memberDrawRouteExample);
			}
			if(routeList==null||routeList.size()==0){
				result.put("returnCode", "0003");
				result.put("returnMsg", "该代付标识暂不支持代付");
				return result;
			}
			
			MemberDrawRoute drawRoute = routeList.get(0);
			Integer memberId = memberInfo.getId();
			
			MemberMerchantCodeExample memberMerchantCodeExample = new MemberMerchantCodeExample();
			memberMerchantCodeExample.createCriteria().andMemberIdEqualTo(memberId).andRouteCodeEqualTo(routeCode).andDelFlagEqualTo("0");
			List<MemberMerchantCode> merchantCodes = memberMerchantCodeService.selectByExample(memberMerchantCodeExample);
			if (merchantCodes == null || merchantCodes.size() != 1) {
				result.put("returnCode", "0008");
				result.put("returnMsg", "对不起，没有该通道的代付权限");
				return result;
			}
			
			String platDrawFee = "";
			SysCommonConfigExample sysCommonConfigExample = new SysCommonConfigExample();
			sysCommonConfigExample.or().andNameEqualTo("PLAT_DRAW_FEE_"+routeCode);
			List<SysCommonConfig> sysCommonConfig = sysCommonConfigService.selectByExample(sysCommonConfigExample);
			if (sysCommonConfig.size() == 0) {
				result.put("returnCode", "4004");
				result.put("returnMsg", "平台代付手续费配置缺失，请与管理员联系");
				return result;
			}
			platDrawFee = sysCommonConfig.get(0).getValue();
			
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat dateFormatLong = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat dateFormatShort = new SimpleDateFormat("yyyy-MM-dd");
			
			String configName = "SINGLE_DRAW_LIMIT_"+routeCode;
			JSONObject limitResult = commonUtilService.checkSingleDrawLimit(configName,new BigDecimal(payMoney));
			if(limitResult!=null){
				return limitResult;
			}
			
			double  drawFee = drawRoute.getDrawFee().doubleValue();
			
			
			if(Double.parseDouble(payMoney)<=drawFee){
				result.put("returnCode", "4004");
				result.put("returnMsg", "代付金额必须大于代付手续费");
				return result;
			}
			
			try{
				RoutewayDrawTemp routewayDrawTemp = new RoutewayDrawTemp();
				routewayDrawTemp.setMemberId(memberId);
				routewayDrawTemp.setRouteCode(routeCode);
				routewayDrawTemp.setTxnDate(df.format(new Date()));
				routewayDrawTempService.insertRoutewayDrawTemp(routewayDrawTemp);
			}catch(Exception e){
				e.printStackTrace();
				result.put("returnCode", "4004");
				result.put("returnMsg", "该商户目前有代付请求正在处理中，请稍后再试");
				return result;
			}
			
			// 今天开始时间
			Date begin = dateFormatLong.parse(dateFormatShort.format(new Date()) + " 00:00:00");
			// 今天结束时间
			Date end = dateFormatLong.parse(dateFormatShort.format(new Date()) + " 23:59:59");
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("memberId", memberId);
			paramMap.put("respType", "S");
			paramMap.put("routeCode", routeCode);
			//当天成功提现金额
			paramMap.put("respDate", df.format(new Date()));
			Double drawMoneyCountToday = commonService.countDrawMoneyByCondition(paramMap);
			drawMoneyCountToday = drawMoneyCountToday == null ? 0 : drawMoneyCountToday;
		
			paramMap = new HashMap<String, Object>();
			paramMap.put("memberId", memberId);
			//待审核提现金额
			paramMap.put("auditStatus", "1");
			paramMap.put("routeCode", routeCode);
			Double waitAuditMoneyCountAll = commonService.countMoneyByCondition(paramMap);
			waitAuditMoneyCountAll = waitAuditMoneyCountAll == null ? 0 : waitAuditMoneyCountAll;
			
			//提现中的金额
			paramMap.put("auditStatus", "2");
			paramMap.put("respType", "R");
			Double ingMoneyCountAll = commonService.countMoneyByCondition(paramMap);
			ingMoneyCountAll = ingMoneyCountAll == null ? 0 : ingMoneyCountAll;
			
			paramMap = new HashMap<String, Object>();
			paramMap.put("memberId", memberId);
			paramMap.put("routeId", routeCode);
			paramMap.put("startDate", df.format(begin));
			paramMap.put("endDate", df.format(end));
			if(RouteCodeConstant.TL_ROUTE_CODE.equals(routeCode)||routeCode.equals(RouteCodeConstant.TLH5_ROUTE_CODE)||routeCode.equals(RouteCodeConstant.TLWD_ROUTE_CODE)){
				paramMap.put("settleType", "0");//D0
			}else{
				paramMap.put("settleType", "1");//D1
			}
			Double balanceToday = commonService.countTransactionRealMoneyByCondition(paramMap);
			balanceToday = balanceToday == null ? 0 : balanceToday;//当天交易账户余额
			Double canDrawToday = new BigDecimal(balanceToday.doubleValue()).multiply(drawRoute.getD0Percent()).doubleValue();//当天可提现的金额
			
			Double balanceHis = 0d;
			RoutewayAccountExample routewayAccountExample = new RoutewayAccountExample();
			routewayAccountExample.createCriteria().andMemberIdEqualTo(memberId).andRouteCodeEqualTo(routeCode).andDelFlagEqualTo("0");
			List<RoutewayAccount> accountList = routewayAccountService.selectByExample(routewayAccountExample);
			if(accountList != null && accountList.size()>0){
				RoutewayAccount account = accountList.get(0);
				balanceHis = account.getBalance().doubleValue();
			}
			//可提现总额
			Double canDrawMoneyCount = Double.valueOf(new DecimalFormat("#.00").format(balanceHis + canDrawToday - drawMoneyCountToday - waitAuditMoneyCountAll - ingMoneyCountAll));
			if(Double.parseDouble(payMoney)>canDrawMoneyCount){
				result.put("returnCode", "4004");
				result.put("returnMsg", "代付金额大于可代付金额，无法代付");
				return result;
			}
			
			
			RoutewayDraw routewayDraw=new RoutewayDraw();
			routewayDraw.setCreateDate(new Date());
			routewayDraw.setDelFlag("0");
			routewayDraw.setMemberCode(memberInfo.getCode());
			routewayDraw.setMemberId(memberId);
			routewayDraw.setRouteCode(routeCode);
			routewayDraw.setMoney(new BigDecimal(payMoney));
			routewayDraw.setDrawamount(new BigDecimal(Double.parseDouble(payMoney)-drawFee));
			routewayDraw.setDrawfee(new BigDecimal(drawFee));
			//routewayDraw.setDrawRate(new BigDecimal(drawRate));
			routewayDraw.setTradefee(new BigDecimal(0));
			routewayDraw.setDrawType("2");
			routewayDraw.setAuditStatus("1");
			routewayDraw.setOrderNumOuter(orderNum);
			
			BankExample bankExample = new BankExample();
			bankExample.createCriteria().andCodeEqualTo(bankCode);
			List<Bank> bankList = bankService.selectByExample(bankExample);
			if(bankList!=null && bankList.size()>0){
				routewayDraw.setBankName(bankList.get(0).getName());
			}
			
			routewayDraw.setBankAccount(bankAccount);
			routewayDraw.setAccountName(accountName);
			routewayDraw.setBankCode(bankCode);
			routewayDraw.setCertNo(certNo);
			routewayDraw.setTel(tel);
			routewayDraw.setDrawProfit(new BigDecimal(drawFee).subtract(new BigDecimal(platDrawFee)));
			routewayDrawService.insertSelective(routewayDraw);
			
			try{
				RoutewayDrawTemp routewayDrawTemp1 = new RoutewayDrawTemp();
				routewayDrawTemp1.setMemberId(memberId);
				routewayDrawTemp1.setRouteCode(routeCode);
				routewayDrawTemp1.setTxnDate(df.format(new Date()));
				routewayDrawTempService.deleteRoutewayDrawTemp(routewayDrawTemp1);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			result.put("returnCode", "0000");
			result.put("returnMsg", "提交成功");
		}catch(Exception e){
			logger.info(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
		}
		return result;
	}
	
	/**
	 * 代付余额查询接口
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/agentPay/balance")
	public JSONObject balance(HttpServletRequest request,HttpServletResponse response){
		JSONObject result = new JSONObject();
		try {
			
			Map<String,String> inparam = new HashMap<String, String>();
			Enumeration<String> pNames=request.getParameterNames();
			while(pNames.hasMoreElements()){
			    String name=(String)pNames.nextElement();
			    String value=request.getParameter(name);
			    inparam.put(name, value);
			}
			logger.info("商户代付余额查询下游入参[{}]",  JSONObject.fromObject(inparam).toString() );
			
			String memberCode = request.getParameter("memberCode");
			String routeCode = request.getParameter("payFlag");
			String signStr = request.getParameter("signStr");
			
			//待签名字符串
			Map<String,String> params = new HashMap<String,String>();
			if(memberCode == null || "".equals(memberCode)){
				result.put("returnCode", "0007");
				result.put("returnMsg", "商户编号[memberCode]缺失");
				return CommonUtil.signReturn(result);
			}
			params.put("memberCode", memberCode);
			
			if(routeCode == null || "".equals(routeCode)){
				result.put("returnCode", "0007");
				result.put("returnMsg", "代付标识[payFlag]缺失");
				return CommonUtil.signReturn(result);
			}
			params.put("payFlag", routeCode);
			
			if(signStr == null || "".equals(signStr)){
				result.put("returnCode", "0007");
				result.put("returnMsg", "签名[signStr]缺失");
				return CommonUtil.signReturn(result);
			}
			
			String srcStr = StringUtil.orderedKey(params);
			
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
				result.put("returnMsg", "该商户机构信息不完整，请确认后重试");
				return CommonUtil.signReturn(result);
			}
			
			SysOffice sysOffice = sysOfficeList.get(0);
			
			if(!EpaySignUtil.checksign(sysOffice.getPublicKeyRsa(), srcStr, signStr)){//by linxf 测试屏蔽
				result.put("returnCode", "0004");
				result.put("returnMsg", "签名校验错误，请检查签名参数是否正确");
				return CommonUtil.signReturn(result);
			}
			
			MemberDrawRouteExample memberDrawRouteExample = new MemberDrawRouteExample();
			memberDrawRouteExample.createCriteria().andMemberIdEqualTo(memberInfo.getId()).andRouteCodeEqualTo(routeCode).andDelFlagEqualTo("0");
			List<MemberDrawRoute> routeList = memberDrawRouteService.selectByExample(memberDrawRouteExample);
			if(routeList==null||routeList.size()==0){
				memberDrawRouteExample = new MemberDrawRouteExample();
				memberDrawRouteExample.createCriteria().andMemberIdEqualTo(0).andRouteCodeEqualTo(routeCode).andDelFlagEqualTo("0");
				routeList = memberDrawRouteService.selectByExample(memberDrawRouteExample);
			}
			if(routeList==null||routeList.size()==0){
				result.put("returnCode", "0003");
				result.put("returnMsg", "该代付标识暂不支持代付");
				return CommonUtil.signReturn(result);
			}
			
			MemberDrawRoute drawRoute = routeList.get(0);
			
			SimpleDateFormat dateFormatLong = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat dateFormatShort = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			
			// 今天开始时间
			Date begin = dateFormatLong.parse(dateFormatShort.format(new Date()) + " 00:00:00");
			// 今天结束时间
			Date end = dateFormatLong.parse(dateFormatShort.format(new Date()) + " 23:59:59");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			String memberId = String.valueOf(memberInfo.getId());
			paramMap = new HashMap<String, Object>();
			paramMap.put("memberId", memberId);
			paramMap.put("respType", "S");
			paramMap.put("routeCode", routeCode);
			//当天成功提现金额
			paramMap.put("respDate", df.format(new Date()));
			Double drawMoneyCountToday = commonService.countDrawMoneyByCondition(paramMap);
			drawMoneyCountToday = drawMoneyCountToday == null ? 0 : drawMoneyCountToday;
			
			paramMap = new HashMap<String, Object>();
			paramMap.put("memberId", memberId);
			//待审核提现金额
			paramMap.put("auditStatus", "1");
			paramMap.put("routeCode", routeCode);
			Double waitAuditMoneyCountAll = commonService.countMoneyByCondition(paramMap);
			waitAuditMoneyCountAll = waitAuditMoneyCountAll == null ? 0 : waitAuditMoneyCountAll;
			//提现中的金额
			paramMap.put("auditStatus", "2");
			paramMap.put("respType", "R");
			Double ingMoneyCountAll = commonService.countMoneyByCondition(paramMap);
			ingMoneyCountAll = ingMoneyCountAll == null ? 0 : ingMoneyCountAll;
			
			paramMap = new HashMap<String, Object>();
			paramMap.put("memberId", memberId);
			paramMap.put("routeId", routeCode);
			paramMap.put("startDate", df.format(begin));
			paramMap.put("endDate", df.format(end));
			if(RouteCodeConstant.TL_ROUTE_CODE.equals(routeCode)||RouteCodeConstant.TLH5_ROUTE_CODE.equals(routeCode)||RouteCodeConstant.TLWD_ROUTE_CODE.equals(routeCode)){
				paramMap.put("settleType", "0");//D0
			}else{
				paramMap.put("settleType", "1");//D1
			}
			Double balanceToday = commonService.countTransactionRealMoneyByCondition(paramMap);
			balanceToday = balanceToday == null ? 0 : balanceToday;//当天交易账户余额
			Double canDrawToday = new BigDecimal(balanceToday.doubleValue()).multiply(drawRoute.getD0Percent()).doubleValue();//当天可提现的金额
			
			Double balanceHis = 0d;
			Double balanceT1 = 0d;
			RoutewayAccountExample routewayAccountExample = new RoutewayAccountExample();
			routewayAccountExample.createCriteria().andMemberIdEqualTo(memberInfo.getId()).andRouteCodeEqualTo(routeCode).andDelFlagEqualTo("0");
			List<RoutewayAccount> accountList = routewayAccountService.selectByExample(routewayAccountExample);
			if(accountList != null && accountList.size()>0){
				RoutewayAccount account = accountList.get(0);
				balanceHis = account.getBalance().doubleValue();
				balanceT1 = account.getT1Balance().doubleValue();
			}
			Double balance = balanceHis + balanceT1 + balanceToday - drawMoneyCountToday;//总账户余额
			//可提现总额
			Double canDrawMoneyCount = Double.valueOf(new DecimalFormat("#.00").format(balanceHis + canDrawToday - drawMoneyCountToday - waitAuditMoneyCountAll - ingMoneyCountAll));
			result.put("returnCode", "0000");
			result.put("returnMsg", "余额查询成功");
			result.put("balance", new DecimalFormat("0.00").format(balance));
			result.put("canDrawMoney", new DecimalFormat("0.00").format(canDrawMoneyCount));
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
			return CommonUtil.signReturn(result);
		}
		return CommonUtil.signReturn(result);
	}
	
	@ResponseBody
	@RequestMapping("/memberInfo/balancetest1")
	public JSONObject balance11(HttpServletRequest request,HttpServletResponse response){
		JSONObject result = new JSONObject();
		try {
			String serverUrl = TLConfig.agentPayUrl;
			
			/* URL url = new URL(serverUrl);  
	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();  
				connection.setConnectTimeout(30000);
				connection.setReadTimeout(120000);
	            connection.setDoOutput(true);  
	            connection.setDoInput(true);  
	            connection.setRequestMethod("POST");  
	            connection.setUseCaches(false);  
	            connection.setInstanceFollowRedirects(true);               
	            connection.setRequestProperty("Content-Type","application/json; charset=GBK");                     
	            connection.connect();  
	  
	            //POST请求  
	            DataOutputStream out = new DataOutputStream(connection.getOutputStream());  */
	            JSONObject reqData = new JSONObject();           
	            
	            String transDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());	//交易日期 YYYYMMDDhhmmss
	            String merId = "821774951710000";	//商户号
	           
	            //transBody信息域
	            JSONObject transData = new JSONObject(); 
	            transData.put("transDate", transDate); 
	            //私钥证书加密
				CertUtil util = new CertUtil();
				PrivateKey privateKey = LoadKeyFromPKCS12.initPrivateKey(util.getConfigPath()+TLConfig.pfxFileName, TLConfig.pfxPassword);
				String  transBody=LoadKeyFromPKCS12.PrivateSign(transData.toString(),privateKey);
				
				reqData.put("transBody", transBody);
	            reqData.put("businessType", "450000"); 
	            reqData.put("merId", merId); 
	            reqData.put("versionId", "001"); 
	            String srcStr = StringUtil.orderedKey(reqData)+"&key=08A788C4893F244B";
				String sign = MD5Util.MD5Encode(srcStr).toUpperCase();
	            reqData.put("signData", sign); 
				reqData.put("signType", "MD5");  

				logger.info("新通联余额查询请求数据[{}]", new Object[] { reqData.toString() });
				String respStr = HttpUtil.sendPostRequest(serverUrl, reqData.toString(),"GBK");
				logger.info("新通联余额查询返回报文[{}]", new Object[] { respStr });
		        
		        JSONObject resultObj = JSONObject.fromObject(respStr);
		        
		        
		        String result_message = "";
				String result_code = "";
				if("00".equals(resultObj.getString("status"))&&resultObj.containsKey("resBody")){
					String data = resultObj.getString("resBody");
					byte[]signByte=LoadKeyFromPKCS12.encryptBASE64(data);
					PublicKey publicKey = CertVerify.initPublicKey(util.getConfigPath()+TLConfig.cerFileName);
					byte[] str1=CertVerify.publicKeyDecrypt(signByte,publicKey);
					JSONObject respJSONObject =  JSONObject.fromObject(new  String(str1));
					logger.info("新通联余额查询返回报文解密[{}]", new Object[] { respJSONObject.toString() });
					result_code = respJSONObject.getString("refCode");//
					if("1".equals(result_code)){
						result.put("returnCode", "0000");
						result.put("returnMsg", "请求成功");
						
					}else{
						result_message = URLDecoder.decode(respJSONObject.getString("refMsg"),"GBK");
					}
				}else{
					result_message = URLDecoder.decode(resultObj.getString("refMsg"), "GBK");
					result_code = resultObj.getString("refCode");
				}
		        
		        System.out.println(result_message);
		        
	    /*        out.write(obj.toString().getBytes("GBK"));
	            out.flush();  
	            out.close();  
	              
	            //读取响应  
	            BufferedReader reader = new BufferedReader(new InputStreamReader(  
	                    connection.getInputStream()));  
	            String lines;  
	            StringBuffer sb = new StringBuffer("");  
	            while ((lines = reader.readLine()) != null) {  
	                lines = new String(lines.getBytes(), "gbk");  
	                sb.append(lines);  
	            }  
	            System.out.println("响应报文==》"+sb); 
	            
	    		
	            reader.close();  
	            // 断开连接  
	            connection.disconnect();  */
	            
	          
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
			return CommonUtil.signReturn(result);
		}
		return CommonUtil.signReturn(result);
	}
	
	
	
	
	@ResponseBody
	@RequestMapping("/memberInfo/queryKey")
	public JSONObject queryKey(HttpServletRequest request,HttpServletResponse response){
		JSONObject result = new JSONObject();
		try {
			String serverUrl = "http://222.76.210.177:9006/ChannelPay/merchBaseInfo/queryMerchKey";
			
			Map<String,String> reqData = new HashMap<String, String>();           
	            
	        reqData.put("orderId", "C"+CommonUtil.getOrderCode());
            reqData.put("merchId", "C20180427692065"); 
            reqData.put("parent", MLConfig.orgNo); 
            String srcStr = StringUtil.orderedKey(reqData)+"&key="+MLConfig.privateKey;
			String sign = MD5Util.MD5Encode(srcStr).toUpperCase();
            reqData.put("sign", sign); 
			
            
            List<NameValuePair> nvps = new LinkedList<NameValuePair>();
			List<String> keys = new ArrayList<String>(reqData.keySet());
			for (int i = 0; i < keys.size(); i++) {
				String name=(String) keys.get(i);
				String value=(String) reqData.get(name);
				if(value!=null && !"".equals(value)){
					nvps.add(new BasicNameValuePair(name, value));
				}
			}
            
            
            
			logger.info("新通联余额查询请求数据[{}]", new Object[] { reqData.toString() });
			
			byte[] b = HttpClient4Util.getInstance().doPost(serverUrl, null, nvps);
			String respStr = new String(b, "UTF-8");
			
			logger.info("新通联余额查询返回报文[{}]", new Object[] { respStr });
		        
		        
		        
	   
	          
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
			return CommonUtil.signReturn(result);
		}
		return CommonUtil.signReturn(result);
	}
	
	
	/**
	 * 米联同名信用卡代付
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/agentPay/toSamePayNew")
	public JSONObject toSamePayNew(HttpServletRequest request,HttpServletResponse response){
		Map<String,String> inparam = new HashMap<String, String>();
		Enumeration<String> pNames=request.getParameterNames();
		while(pNames.hasMoreElements()){
		    String name=(String)pNames.nextElement();
		    String value=request.getParameter(name);
		    inparam.put(name, value);
		}
		logger.info("同名信用卡代付下游入参[{}]",  JSONObject.fromObject(inparam).toString() );
		String bankAccount = request.getParameter("bankAccount");
		String memberCode = request.getParameter("memberCode");
		String orderNum = request.getParameter("orderNum");
		String payMoney = request.getParameter("payMoney");
		String bankCode = request.getParameter("bankCode");
		String accountName = request.getParameter("accountName");
		String settleFee = request.getParameter("settleFee");
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
		
		if(bankCode == null || "".equals(bankCode)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "银行编码缺失");
			return signReturn(result);
		}
		srcStr.append("&bankCode="+bankCode);
		
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
		
		if(settleFee!=null && !"".equals(settleFee)){
			if(!ValidateUtil.isDoubleT(settleFee) || Double.parseDouble(settleFee)<=0){
				result.put("returnCode", "0007");
				result.put("returnMsg", "代付手续费输入不正确");
				return signReturn(result);
			}
			srcStr.append("&settleFee="+settleFee);
		}
		
		if(signStr == null || "".equals(signStr)){ 
			result.put("returnCode", "0007");
			result.put("returnMsg", "缺少签名信息");
			return signReturn(result);
		}
		result = validMemberInfoForSameAgentPay(memberCode, orderNum, payMoney,bankAccount,accountName,bankCode,settleFee, srcStr.toString(), signStr);
		
		return signReturn(result);
	}
	
	
	public JSONObject mlBalance(String merCode){
		JSONObject result = new JSONObject();
		try {
			String serverUrl = MLConfig.serverUrl+"/MerchBalanceQuery";
			String routeCode = RouteCodeConstant.ML_ROUTE_CODE;
			
			MemberMerchantKeyExample memberMerchantKeyExample = new MemberMerchantKeyExample();
	        memberMerchantKeyExample.createCriteria().andRouteCodeEqualTo(routeCode).andMerchantCodeEqualTo(merCode).andDelFlagEqualTo("0");
	        List<MemberMerchantKey> keyList = memberMerchantKeyService.selectByExample(memberMerchantKeyExample);
	        if(keyList == null || keyList.size()!=1){
	            result.put("returnCode", "0008");
				result.put("returnMsg", "商户私钥未配置");
				return result;
	        }
	        MemberMerchantKey merchantKey = keyList.get(0);
	        
	        String orderCode = "X"+CommonUtil.getOrderCode();
	        String srcStr = orderCode+"02"+merCode;
	     	//String srcStr = StringUtil.orderedKey(contextjson)+"&key="+merchantKey.getPrivateKey();
	     	System.out.println("加密源串："+srcStr);
			String sign1 = com.epay.scanposp.common.utils.ml.EncryptUtil.MD5(srcStr, 1).toUpperCase();
			System.out.println("第一次加密结果："+sign1);
			String sign = com.epay.scanposp.common.utils.ml.EncryptUtil.MD5(sign1+merchantKey.getPrivateKey(), 0).toUpperCase();
			System.out.println("第二次加密结果："+sign);
			
			
			Map<String,String> param = new HashMap<String, String>();
			param.put("ORDER_ID", orderCode);
			param.put("USER_TYPE", "02");
			param.put("USER_ID", merCode);
			param.put("SIGN_TYPE", "03");
			param.put("SIGN", sign);
			
			logger.info("米联商户余额查询参数[{}]",JSONObject.fromObject(param).toString() );
			
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
			logger.info("米联商户余额查询返回报文[{}]", new Object[] { respStr });
			
			Document reqDoc = DocumentHelper.parseText(respStr);
			Element rootEl = reqDoc.getRootElement();
			
			String code = rootEl.elementText("RESP_CODE");
			if("0000".equals(code)){
				result.put("returnCode", "0000");
				result.put("returnMsg", "成功");
				result.put("balance", rootEl.elementText("BALANCE"));
			}else{
				result.put("returnCode", "0003");
				result.put("returnMsg", rootEl.elementText("RESP_DESC"));
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
			return result;
		}
		return result;
	}
	
	//米联代付
	public JSONObject mlAgentPay(String orderCode,String merCode,String bankAccount,String accountName,String bankCode,String bankName,String payMoney,RoutewayDraw draw){
		JSONObject result = new JSONObject();
		try{
			String routeCode = RouteCodeConstant.ML_ROUTE_CODE;
			MemberMerchantKeyExample memberMerchantKeyExample = new MemberMerchantKeyExample();
	        memberMerchantKeyExample.createCriteria().andRouteCodeEqualTo(routeCode).andMerchantCodeEqualTo(merCode).andDelFlagEqualTo("0");
	        List<MemberMerchantKey> keyList = memberMerchantKeyService.selectByExample(memberMerchantKeyExample);
	        if(keyList == null || keyList.size()!=1){
	            result.put("returnCode", "0008");
				result.put("returnMsg", "商户私钥未配置");
				return result;
	        }
	        MemberMerchantKey merchantKey = keyList.get(0);
	        
			String serverUrl = MLConfig.serverUrl+"/DfReq";
			payMoney = new DecimalFormat("0.00").format(Double.valueOf(payMoney));
	     	String orderTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

			String srcStr = orderCode+payMoney+orderTime+"13"+"02"+merCode+"1006";
	     	System.out.println("加密源串："+srcStr);
			String sign1 = com.epay.scanposp.common.utils.ml.EncryptUtil.MD5(srcStr, 1).toUpperCase();
			System.out.println("第一次加密结果："+sign1);
			String sign = com.epay.scanposp.common.utils.ml.EncryptUtil.MD5(sign1+merchantKey.getPrivateKey(), 0).toUpperCase();
			System.out.println("第二次加密结果："+sign);
			
			Map<String,String> contextjson = new HashMap<String, String>();
			contextjson.put("ORDER_ID",orderCode);//订单流水号
	     	contextjson.put("ORDER_TIME",orderTime);//
	     	contextjson.put("ORDER_AMT",payMoney);//消费金额
	     	contextjson.put("USER_TYPE", "02");
	     	contextjson.put("USER_ID", merCode);
	     	contextjson.put("SIGN_TYPE", "03");
	     	contextjson.put("BUS_CODE", "1006");
	     	contextjson.put("BANK_CODE", bankCode);
	     	contextjson.put("ACCOUNT_NO",bankAccount);//银行卡号
	    
	     	contextjson.put("BANK_NAME",bankName);
	     	contextjson.put("ACCOUNT_NAME",accountName);
	     	contextjson.put("PAY_TYPE","13");
	     	contextjson.put("CCT","CNY" );
	     	contextjson.put("SIGN", sign);
	     	
	     	logger.info("米联代付参数[{}]",JSONObject.fromObject(contextjson).toString() );
	     	try{
				RoutewayDrawLog routewayDrawLog = new RoutewayDrawLog();
				routewayDrawLog.setDrawId(draw.getId());
				routewayDrawLog.setOrderCode(orderCode);
				routewayDrawLog.setOrderNumOuter(draw.getOrderNumOuter());
				String inparam = contextjson.toString();
				routewayDrawLog.setInParam(inparam.length()>1000?inparam.substring(0, 1000):inparam);
				routewayDrawLog.setCreateDate(new Date());
				routewayDrawLogService.insert(routewayDrawLog);
			}catch(Exception e){
				logger.info("代付入日志异常", e);
			}
			
			List<NameValuePair> nvps = new LinkedList<NameValuePair>();
			List<String> keys = new ArrayList<String>(contextjson.keySet());
			for (int i = 0; i < keys.size(); i++) {
				String name=(String) keys.get(i);
				String value=(String) contextjson.get(name);
				if(value!=null && !"".equals(value)){
					nvps.add(new BasicNameValuePair(name, value));
				}
			}
            
            byte[] b = HttpClient4Util.getInstance().doPost(serverUrl, null, nvps);
			String respStr = new String(b, "UTF-8");
			logger.info("米联代付返回报文[{}]", new Object[] { respStr });
			
			try{
				RoutewayDrawLogExample routewayDrawLogExample = new RoutewayDrawLogExample();
				routewayDrawLogExample.createCriteria().andOrderCodeEqualTo(orderCode);
				List<RoutewayDrawLog> logList = routewayDrawLogService.selectByExample(routewayDrawLogExample);
				if(logList != null && logList.size()>0){
					RoutewayDrawLog routewayDrawLog1 = logList.get(0);
					routewayDrawLog1.setOutParam(respStr.length()>1000?respStr.substring(0, 1000):respStr);
					routewayDrawLog1.setUpdateDate(new Date());
					routewayDrawLogService.updateByPrimaryKey(routewayDrawLog1);
				}
			}catch(Exception e){
				logger.info("代付更新日志异常", e);
			}
			Document reqDoc = DocumentHelper.parseText(respStr);
			Element rootEl = reqDoc.getRootElement();
			
			String code = rootEl.elementText("RESP_CODE");
			String resSign = rootEl.elementText("SIGN");
			String resultMsg = "";
			boolean flag = false;
			
		/*	srcStr = rootEl.elementText("ORDER_ID")+rootEl.elementText("ORDER_AMT")+rootEl.elementText("USER_ID")+rootEl.elementText("BUS_CODE")+rootEl.elementText("SIGN_TYPE")+rootEl.elementText("RESP_CODE");
	     	System.out.println("出参加密源串："+srcStr);
			sign1 = com.epay.scanposp.common.utils.ml.EncryptUtil.MD5(srcStr, 1).toUpperCase();
			System.out.println("出参第一次加密结果："+sign1);
			sign = com.epay.scanposp.common.utils.ml.EncryptUtil.MD5(sign1+merchantKey.getPrivateKey(), 0).toUpperCase();
			System.out.println("出参第二次加密结果："+sign);
		*/	
		//	if(resSign.equals(sign)){
				if("0000".equals(code)||"00".equals(code)||"0100".equals(code)){
					result.put("returnCode", "0000");
					result.put("returnMsg", "提交成功");
					flag = true;
				}else{
					resultMsg = rootEl.elementText("RESP_DESC");
				}
		//	}else{
		//		resultMsg = "代付出参验签失败";
		//	}
			
			if(!flag){
				result.put("returnCode", "0003");
				result.put("returnMsg", resultMsg);
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
		}
		return result;
	}
	
	
	/**
	 * 通联快捷商户余额查询
	 * @param merCode
	 * @return
	 */
	public JSONObject tlkjBalance(String merCode,String bankAccount){
		JSONObject result = new JSONObject();
		try {
			String serverUrl = TLKJConfig.serverUrl+"/df/merchantPay/req";
			String routeCode = RouteCodeConstant.TLKJ_ROUTE_CODE;
			
			MemberMerchantKeyExample memberMerchantKeyExample = new MemberMerchantKeyExample();
	        memberMerchantKeyExample.createCriteria().andRouteCodeEqualTo(routeCode).andMerchantCodeEqualTo(merCode).andDelFlagEqualTo("0");
	        List<MemberMerchantKey> keyList = memberMerchantKeyService.selectByExample(memberMerchantKeyExample);
	        if(keyList == null || keyList.size()!=1){
	            result.put("returnCode", "0008");
				result.put("returnMsg", "商户私钥未配置");
				return result;
	        }
	        MemberMerchantKey merchantKey = keyList.get(0);
	        
	        Map<String ,Object> reqMap = new HashMap<>();
	        reqMap.put("agentNo", merchantKey.getAppId());
	        reqMap.put("shopNo", merCode);
	        reqMap.put("cardNo", bankAccount);
	        reqMap.put("settleMode", "2");
	        reqMap.put("reqMethod", "0653000101");
	        reqMap.put("signType", "SHAWITHRSA");
	        reqMap.put("randomStr", RandomTools.getRandomString(32));
	        String s = MapUtils.map2UrlParams(reqMap);
	        CommonUtil commonUtil = new CommonUtil();
	        String privateKeyFile = commonUtil.getConfigPath() + "tlkjkey"+File.separator + TLKJConfig.rsaPrivateKey;
	        String sign = com.epay.scanposp.common.utils.tlkj.RSATool.signByPrivateKey(privateKeyFile, merchantKey.getPrivateKeyPassword(), s);
	        reqMap.put("sign", sign);
	   //     CommonUtil.httpsClientRequestStr("http://localhost:8080/QuickPlatDFService/merchantPay/req", "POST",JSON.toJSONString(reqMap));
			
			logger.info("通联快捷商户余额查询参数[{}]",JSONObject.fromObject(reqMap).toString() );
			
			String respStr =  HttpUtil.sendPostRequest(serverUrl, JSON.toJSONString(reqMap));
			logger.info("通联快捷商户余额查询返回报文[{}]", new Object[] { respStr });
			
			JSONObject resObj = JSONObject.fromObject(respStr);
			String code = resObj.getString("respCode");
			if("00".equals(code)){
				result.put("returnCode", "0000");
				result.put("returnMsg", "成功");
				result.put("balance", resObj.getString("amount"));
			}else{
				result.put("returnCode", "0003");
				result.put("returnMsg", resObj.getString("respMsg"));
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
			return result;
		}
		return result;
	}
	
	public JSONObject tlkjAgentPay(String orderCode,String merCode,String bankAccount,String accountName,String bankCode,String bankName,String payMoney,RoutewayDraw draw){
		JSONObject result = new JSONObject();
		try{
			String routeCode = RouteCodeConstant.TLKJ_ROUTE_CODE;
			MemberMerchantKeyExample memberMerchantKeyExample = new MemberMerchantKeyExample();
	        memberMerchantKeyExample.createCriteria().andRouteCodeEqualTo(routeCode).andMerchantCodeEqualTo(merCode).andDelFlagEqualTo("0");
	        List<MemberMerchantKey> keyList = memberMerchantKeyService.selectByExample(memberMerchantKeyExample);
	        if(keyList == null || keyList.size()!=1){
	            result.put("returnCode", "0008");
				result.put("returnMsg", "商户私钥未配置");
				return result;
	        }
	        MemberMerchantKey merchantKey = keyList.get(0);
	        
			String serverUrl = TLKJConfig.serverUrl+"/df/merchantPay/req";
			payMoney = String.valueOf((new BigDecimal(payMoney)).multiply(new BigDecimal(100)).intValue());
	     	
			Map<String ,Object> reqMap = new HashMap<String ,Object>();

	        reqMap.put("agentNo", merchantKey.getAppId());
	        reqMap.put("settleMode", "2");
	        reqMap.put("reqMethod", "0651000101");
	        reqMap.put("amount", payMoney);
	        reqMap.put("dfFee", String.valueOf(draw.getDrawfee().multiply(new BigDecimal(100)).intValue()));
	        reqMap.put("cardNo", bankAccount);
	        reqMap.put("acctName", accountName);
	        reqMap.put("shopNo", merCode);
	        reqMap.put("orderNo",orderCode);
	        reqMap.put("randomStr", RandomTools.getRandomString(32));
	        reqMap.put("signType", "SHAWITHRSA");
	        String s = MapUtils.map2UrlParams(reqMap);
	        CommonUtil commonUtil = new CommonUtil();
	        String privateKeyFile = commonUtil.getConfigPath() + "tlkjkey"+File.separator + TLKJConfig.rsaPrivateKey;
	        String sign = com.epay.scanposp.common.utils.tlkj.RSATool.signByPrivateKey(privateKeyFile, merchantKey.getPrivateKeyPassword(), s);
	        reqMap.put("sign", sign);
			
	     	
	     	logger.info("通联快捷代付参数[{}]",JSONObject.fromObject(reqMap).toString() );
	     	try{
				RoutewayDrawLog routewayDrawLog = new RoutewayDrawLog();
				routewayDrawLog.setDrawId(draw.getId());
				routewayDrawLog.setOrderCode(orderCode);
				routewayDrawLog.setOrderNumOuter(draw.getOrderNumOuter());
				String inparam = JSONObject.fromObject(reqMap).toString();
				routewayDrawLog.setInParam(inparam.length()>1000?inparam.substring(0, 1000):inparam);
				routewayDrawLog.setCreateDate(new Date());
				routewayDrawLogService.insert(routewayDrawLog);
			}catch(Exception e){
				logger.info("代付入日志异常", e);
			}
			
			
	     	String respStr =  HttpUtil.sendPostRequest(serverUrl, JSON.toJSONString(reqMap));
	     	
	     	logger.info("通联代付返回报文[{}]", new Object[] { respStr });
			
			try{
				RoutewayDrawLogExample routewayDrawLogExample = new RoutewayDrawLogExample();
				routewayDrawLogExample.createCriteria().andOrderCodeEqualTo(orderCode);
				List<RoutewayDrawLog> logList = routewayDrawLogService.selectByExample(routewayDrawLogExample);
				if(logList != null && logList.size()>0){
					RoutewayDrawLog routewayDrawLog1 = logList.get(0);
					routewayDrawLog1.setOutParam(respStr.length()>1000?respStr.substring(0, 1000):respStr);
					routewayDrawLog1.setUpdateDate(new Date());
					routewayDrawLogService.updateByPrimaryKey(routewayDrawLog1);
				}
			}catch(Exception e){
				logger.info("代付更新日志异常", e);
			}
			
			JSONObject resObj = JSONObject.fromObject(respStr);
            
        	String code = resObj.getString("respCode");
			String resultMsg = "";
			boolean flag = false;
			
			if("00".equals(code)){
				String status = resObj.getString("status");//0、待出款，2、出款失败；3、出款中；大于等于6、出款成功。
				if("0".equals(status)||"3".equals(status)||Integer.parseInt(status)>=6){
					result.put("returnCode", "0000");
					result.put("returnMsg", "提交成功");
					flag = true;
				}else{
					resultMsg = resObj.getString("respMsg");
				}
			}else{
				resultMsg = resObj.getString("respMsg");
			}
				
			if(!flag){
				result.put("returnCode", "0003");
				result.put("returnMsg", resultMsg);
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
		}
		return result;
	}
}
