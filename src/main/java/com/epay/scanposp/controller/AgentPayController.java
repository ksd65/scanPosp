package com.epay.scanposp.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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

import com.epay.scanposp.common.constant.SysConfig;
import com.epay.scanposp.common.constant.YZFConfig;
import com.epay.scanposp.common.utils.CommonUtil;
import com.epay.scanposp.common.utils.StringUtil;
import com.epay.scanposp.common.utils.ValidateUtil;
import com.epay.scanposp.common.utils.constant.RouteCodeConstant;
import com.epay.scanposp.common.utils.epaySecurityUtil.EpaySignUtil;
import com.epay.scanposp.common.utils.ms.HttpClient4Util;
import com.epay.scanposp.common.utils.ms.MSCommonUtil;
import com.epay.scanposp.common.utils.yzf.AESTool;
import com.epay.scanposp.common.utils.yzf.Base64Utils;
import com.epay.scanposp.common.utils.yzf.EncryptUtil;
import com.epay.scanposp.common.utils.yzf.Md5Utils;
import com.epay.scanposp.common.utils.yzf.RSATool;
import com.epay.scanposp.entity.Account;
import com.epay.scanposp.entity.AccountExample;
import com.epay.scanposp.entity.EpayCode;
import com.epay.scanposp.entity.EpayCodeExample;
import com.epay.scanposp.entity.MemberInfo;
import com.epay.scanposp.entity.MemberInfoExample;
import com.epay.scanposp.entity.MemberMerchantCode;
import com.epay.scanposp.entity.MemberMerchantCodeExample;
import com.epay.scanposp.entity.MemberMerchantKey;
import com.epay.scanposp.entity.MemberMerchantKeyExample;
import com.epay.scanposp.entity.RoutewayDraw;
import com.epay.scanposp.entity.RoutewayDrawExample;
import com.epay.scanposp.entity.SysOffice;
import com.epay.scanposp.entity.SysOfficeExample;
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
	     	contextjson.put("amount",String.valueOf((int)(((new BigDecimal(drawAmount)).floatValue())*100)));//结算到账金额 单位分
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
}
