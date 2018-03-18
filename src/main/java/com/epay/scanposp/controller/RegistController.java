package com.epay.scanposp.controller;

import java.math.BigDecimal;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
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

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.epay.scanposp.common.constant.SysConfig;
import com.epay.scanposp.common.constant.XinFuConfig;
import com.epay.scanposp.common.constant.YSConfig;
import com.epay.scanposp.common.constant.YZFConfig;
import com.epay.scanposp.common.utils.CommonUtil;
import com.epay.scanposp.common.utils.SecurityUtil;
import com.epay.scanposp.common.utils.StringUtil;
import com.epay.scanposp.common.utils.constant.RouteCodeConstant;
import com.epay.scanposp.common.utils.constant.SequenseTypeConstant;
import com.epay.scanposp.common.utils.epaySecurityUtil.EpaySignUtil;
import com.epay.scanposp.common.utils.epaySecurityUtil.RSAUtil;
import com.epay.scanposp.common.utils.ms.HttpClient4Util;
import com.epay.scanposp.common.utils.ms.MSCommonUtil;
import com.epay.scanposp.common.utils.xinfu.HttpPostRequest;
import com.epay.scanposp.common.utils.xinfu.Md5SignUtil;
import com.epay.scanposp.common.utils.ys.HttpUtils;
import com.epay.scanposp.common.utils.ys.SwpHashUtil;
import com.epay.scanposp.common.utils.yzf.AESTool;
import com.epay.scanposp.common.utils.yzf.Base64Utils;
import com.epay.scanposp.common.utils.yzf.EncryptUtil;
import com.epay.scanposp.common.utils.yzf.RSATool;
import com.epay.scanposp.entity.Account;
import com.epay.scanposp.entity.BankSub;
import com.epay.scanposp.entity.BankSubExample;
import com.epay.scanposp.entity.EpayCode;
import com.epay.scanposp.entity.MemberBank;
import com.epay.scanposp.entity.MemberInfo;
import com.epay.scanposp.entity.MemberInfoExample;
import com.epay.scanposp.entity.MemberMerchantCode;
import com.epay.scanposp.entity.MemberMerchantKey;
import com.epay.scanposp.entity.RegisterTmp;
import com.epay.scanposp.entity.SysCommonConfig;
import com.epay.scanposp.entity.SysCommonConfigExample;
import com.epay.scanposp.entity.SysOffice;
import com.epay.scanposp.entity.SysOfficeExample;
import com.epay.scanposp.service.AccountService;
import com.epay.scanposp.service.BankSubService;
import com.epay.scanposp.service.CommonService;
import com.epay.scanposp.service.EpayCodeService;
import com.epay.scanposp.service.MemberBankService;
import com.epay.scanposp.service.MemberInfoService;
import com.epay.scanposp.service.MemberMerchantCodeService;
import com.epay.scanposp.service.MemberMerchantKeyService;
import com.epay.scanposp.service.RegisterTmpService;
import com.epay.scanposp.service.SysCommonConfigService;
import com.epay.scanposp.service.SysOfficeService;

@Controller
public class RegistController {
	
	private static Logger logger = LoggerFactory.getLogger(RegistController.class);
	
	@Resource
	private SysOfficeService sysOfficeService;
	
	@Resource
	private MemberInfoService memberInfoService;
	
	@Resource
	private MemberBankService memberBankService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private EpayCodeService epayCodeService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private RegisterTmpService registerTmpService;
	
	@Autowired
	private MemberMerchantCodeService memberMerchantCodeService;
	
	@Autowired
	private MemberMerchantKeyService memberMerchantKeyService;
	
	@Autowired
	private SysCommonConfigService sysCommonConfigService;
	
	@Autowired
	private BankSubService bankSubService;

	@ResponseBody
	@RequestMapping("/memberInfo/toRegist")
	public JSONObject toRegist(HttpServletRequest request,HttpServletResponse response){
		JSONObject result = new JSONObject();
		String officeId = request.getParameter("officeId");
		String orderNum = request.getParameter("orderNum");
		String name = request.getParameter("name");
		String shortName = request.getParameter("shortName");
		String userName = request.getParameter("userName");
		String mobilePhone = request.getParameter("mobilePhone");
		String certNbr = request.getParameter("certNbr");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		String remark = request.getParameter("remark");
		String srouteCode = request.getParameter("routeCode");
		String tradeRate = request.getParameter("tradeRate");
		String settleFee = request.getParameter("settleFee");
		String settleBankAccount = request.getParameter("settleBankAccount");
		String settleAccountName = request.getParameter("settleAccountName");
		String settleBankPhone = request.getParameter("settleBankPhone");
		String signStr = request.getParameter("signStr");
		
		String routeCode = srouteCode;
		if(srouteCode == null ||"".equals(srouteCode)){
			routeCode = RouteCodeConstant.YZF_ROUTE_CODE;
		}
		
		//待签名字符串
		Map<String,String> params = new HashMap<String,String>();
		if(officeId == null || "".equals(officeId)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "机构ID[officeId]缺失");
			return CommonUtil.signReturn(result);
		}
		params.put("officeId", officeId);
		
		if(orderNum == null || "".equals(orderNum)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "商户订单号[orderNum]缺失");
			return CommonUtil.signReturn(result);
		}
		params.put("orderNum", orderNum);
		
		if(name == null || "".equals(name)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "商户名称[name]缺失");
			return CommonUtil.signReturn(result);
		}
		params.put("name", name);
		
		if(shortName == null || "".equals(shortName)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "商户简称[shortName]缺失");
			return CommonUtil.signReturn(result);
		}
		params.put("shortName", shortName);
		
		if(userName == null || "".equals(userName)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "法人名称[userName]缺失");
			return CommonUtil.signReturn(result);
		}
		params.put("userName", userName);
		
		if(mobilePhone == null || "".equals(mobilePhone)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "法人电话[mobilePhone]缺失");
			return CommonUtil.signReturn(result);
		}
		params.put("mobilePhone", mobilePhone);
		
		if(certNbr == null || "".equals(certNbr)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "身份证号[certNbr]缺失");
			return CommonUtil.signReturn(result);
		}
		params.put("certNbr", certNbr);
		
		if(email == null || "".equals(email)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "邮箱[email]缺失");
			return CommonUtil.signReturn(result);
		}
		params.put("email", email);
		
		if(address == null || "".equals(address)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "商户地址[address]缺失");
			return CommonUtil.signReturn(result);
		}
		params.put("address", address);
		
		if(remark == null || "".equals(remark)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "商户备注[remark]缺失");
			return CommonUtil.signReturn(result);
		}
		params.put("remark", remark);
		
		if(srouteCode != null && !"".equals(srouteCode)){
			params.put("routeCode", srouteCode);
		}
		
		if(tradeRate == null || "".equals(tradeRate)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "交易费率[tradeRate]缺失");
			return CommonUtil.signReturn(result);
		}
		params.put("tradeRate", tradeRate);
		
		if(settleFee == null || "".equals(settleFee)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "提现费用[settleFee]缺失");
			return CommonUtil.signReturn(result);
		}
		params.put("settleFee", settleFee);
		
		if(settleBankAccount == null || "".equals(settleBankAccount)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "绑定结算卡号[settleBankAccount]缺失");
			return CommonUtil.signReturn(result);
		}
		params.put("settleBankAccount", settleBankAccount);
		
		if(settleAccountName == null || "".equals(settleAccountName)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "绑定结算卡账户名[settleAccountName]缺失");
			return CommonUtil.signReturn(result);
		}
		params.put("settleAccountName", settleAccountName);
		
		if(settleBankPhone == null || "".equals(settleBankPhone)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "绑定结算卡手机号[settleBankPhone]缺失");
			return CommonUtil.signReturn(result);
		}
		params.put("settleBankPhone", settleBankPhone);
		
		if(signStr == null || "".equals(signStr)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "签名[signStr]缺失");
			return CommonUtil.signReturn(result);
		}
		
		String srcStr = StringUtil.orderedKey(params);
		
		SysOfficeExample sysOfficeExample = new SysOfficeExample();
		sysOfficeExample.or().andIdEqualTo(officeId).andDelFlagEqualTo("0");
		List<SysOffice> sysOfficeList = sysOfficeService.selectByExample(sysOfficeExample);
		if(null == sysOfficeList || sysOfficeList.size() == 0){
			result.put("returnCode", "0008");
			result.put("returnMsg", "机构不存在");
			return CommonUtil.signReturn(result);
		}
		
		SysOffice sysOffice = sysOfficeList.get(0);
	/*	if(!EpaySignUtil.checksign(sysOffice.getPublicKeyRsa(), srcStr, signStr)){
			result.put("returnCode", "0004");
			result.put("returnMsg", "签名校验错误，请检查签名参数是否正确");
			return CommonUtil.signReturn(result);
		}*/
		
		
	/*	MemberInfoExample memberInfoExample = new MemberInfoExample();
		memberInfoExample.or().andCertNbrEqualTo(certNbr).andStatusNotEqualTo("1").andDelFlagEqualTo("0");
		int countMemberInfo = memberInfoService.countByExample(memberInfoExample);
		if(countMemberInfo >0){
			result.put("returnCode", "4004");
			result.put("returnMsg", "商户信息已存在，该身份证号码已注册");
			return CommonUtil.signReturn(result);
		}
		memberInfoExample = new MemberInfoExample();
		memberInfoExample.or().andMobilePhoneEqualTo(mobilePhone).andStatusNotEqualTo("1").andDelFlagEqualTo("0");
		countMemberInfo = memberInfoService.countByExample(memberInfoExample);
		if(countMemberInfo > 0){
			result.put("returnCode", "4004");
			result.put("returnMsg", "商户信息已存在，该手机号码已注册");
			return CommonUtil.signReturn(result);
		}
		*/
		
		
		String platTradeRate = "", platDrawFee = "";
		SysCommonConfigExample sysCommonConfigExample = new SysCommonConfigExample();
		sysCommonConfigExample.or().andNameEqualTo("PLAT_TRADE_RATE_"+routeCode).andDelFlagEqualTo("0");
		List<SysCommonConfig>  sysCommonConfig = sysCommonConfigService.selectByExample(sysCommonConfigExample);
		if (sysCommonConfig.size() == 0) {
			result.put("returnCode", "4004");
			result.put("returnMsg", "系统默认交易费率配置缺失，请与管理员联系");
			return CommonUtil.signReturn(result);
		}
		platTradeRate = sysCommonConfig.get(0).getValue();
		
		sysCommonConfigExample = new SysCommonConfigExample();
		sysCommonConfigExample.or().andNameEqualTo("PLAT_DRAW_FEE_"+routeCode).andDelFlagEqualTo("0");
		sysCommonConfig = sysCommonConfigService.selectByExample(sysCommonConfigExample);
		if (sysCommonConfig.size() == 0) {
			result.put("returnCode", "4004");
			result.put("returnMsg", "系统默认提现费用配置缺失，请与管理员联系");
			return CommonUtil.signReturn(result);
		}
		platDrawFee = sysCommonConfig.get(0).getValue();
		
		if(Double.valueOf(tradeRate)<Double.valueOf(platTradeRate)){
			result.put("returnCode", "4004");
			result.put("returnMsg", "交易费率小于最小金额");
			return CommonUtil.signReturn(result);
		}
		
		if(Double.valueOf(settleFee)<Double.valueOf(platDrawFee)){
			result.put("returnCode", "4004");
			result.put("returnMsg", "提现手续费小于最小金额");
			return CommonUtil.signReturn(result);
		}
		String subId = "";
		String subName = "";
		String bankId = "";
		if(routeCode.equals(RouteCodeConstant.YZF_ROUTE_CODE)){
			JSONObject subObj = queryBankSubId(settleBankAccount);
			if(!"0000".equals(subObj.getString("returnCode"))){
				result.put("returnCode", "4004");
				result.put("returnMsg", "结算卡错误，查询联行号失败："+subObj.getString("returnMsg"));
				return CommonUtil.signReturn(result);
			}
			subId = subObj.getString("subId");
			BankSubExample bankSubExample = new BankSubExample();
			bankSubExample.createCriteria().andSubIdEqualTo(subId);
			List<BankSub> bankSubList = bankSubService.selectByExample(bankSubExample);
			if(bankSubList!=null &&bankSubList.size()>0){
				subName = bankSubList.get(0).getSubName();
				bankId = String.valueOf(bankSubList.get(0).getBankId());
			}
		}
		
		Date nowDate = new Date();
		
		EpayCode epayCode = new EpayCode();
		epayCode.setPayCode("J"+commonService.getNextSequenceVal(SequenseTypeConstant.EPAYCODE));
		epayCode.setT0DrawFee(new BigDecimal(settleFee));
		epayCode.setT1DrawFee(new BigDecimal(settleFee));
		epayCode.setT0TradeRate(new BigDecimal(tradeRate));
		epayCode.setT1TradeRate(new BigDecimal(tradeRate));
		epayCode.setStatus("5");
		epayCode.setOfficeId(officeId);
		epayCode.setCreateDate(nowDate);
		epayCode.setUpdateDate(nowDate);
		epayCode.setCreateBy("1");
		epayCode.setUpdateBy("1");
		
		RegisterTmp registerTmp = new RegisterTmp();
		registerTmp.setParentId(0);
		registerTmp.setType("1");
		registerTmp.setLoginCode(mobilePhone);
		registerTmp.setContact(userName);
		registerTmp.setName(name);
		registerTmp.setShortName(shortName);
		registerTmp.setSettleType("1");
		registerTmp.setAddr(address);
		registerTmp.setCertNbr(certNbr);
		registerTmp.setProvince("");
		registerTmp.setCity("");
		registerTmp.setCounty("");
		registerTmp.setContactType("00");
		registerTmp.setEmail(email);
		registerTmp.setBankId(bankId);
		registerTmp.setSubId(subId);
		registerTmp.setBankOpen(subName);
		registerTmp.setMobilePhone(mobilePhone);
		registerTmp.setAccountName(settleAccountName);
		registerTmp.setAccountNumber(settleBankAccount);
		registerTmp.setPayCode(epayCode.getPayCode());
		registerTmp.setRemarks(remark);
		registerTmp.setCreateBy("1");
		registerTmp.setUpdateBy("1");
		registerTmp.setDelFlag("0");
		registerTmp.setStatus("0");
		registerTmp.setCode(SysConfig.prefixMemberCode+commonService.getNextSequenceVal(SequenseTypeConstant.MEMBERCODE));
		registerTmp.setLoginPass(SecurityUtil.md5Encode(registerTmp.getMobilePhone().substring(5, 11)));
		registerTmp.setCreateDate(nowDate);
		registerTmp.setUpdateDate(nowDate);
		registerTmp.setWxMemberCode("w"+commonService.getNextSequenceVal(SequenseTypeConstant.WXMEMBERCODE));
		registerTmp.setZfbMemberCode("z"+commonService.getNextSequenceVal(SequenseTypeConstant.ZFBMEMBERCODE));
		registerTmp.setQqMemberCode("q"+commonService.getNextSequenceVal(SequenseTypeConstant.QQMEMBERCODE));
		registerTmp.setBdMemberCode("b"+commonService.getNextSequenceVal(SequenseTypeConstant.BDMEMBERCODE));
		registerTmp.setJdMemberCode("j"+commonService.getNextSequenceVal(SequenseTypeConstant.JDMEMBERCODE));
		registerTmp.setOrderNumOuter(orderNum);
		registerTmpService.insertSelective(registerTmp);
		
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
		memberInfo.setCertNbr(registerTmp.getCertNbr());
		memberInfo.setProvince(registerTmp.getProvince());
		memberInfo.setCity(registerTmp.getCity());
		memberInfo.setCounty(registerTmp.getCounty());
		memberInfo.setAddr(registerTmp.getAddr());
		//getMerchantAddress(memberInfo);
		memberInfo.setMobilePhone(registerTmp.getMobilePhone());
		memberInfo.setCardPic1(registerTmp.getCardPic1());
		memberInfo.setCardPic2(registerTmp.getCardPic2());
		memberInfo.setCertPic1(registerTmp.getCertPic1());
		memberInfo.setCertPic2(registerTmp.getCertPic2());
		memberInfo.setBusPic(registerTmp.getMemcertPic());//营业执照照片
		memberInfo.setMemcertPic(registerTmp.getAuthPic());//授权证书
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
		memberInfo.setBusLicenceNbr(registerTmp.getBusLicenceNbr());//营业执照编号
		memberInfo.setEmail(registerTmp.getEmail());
		memberInfo.setLevel("0");
		memberInfo.setDrawStatus("1"); //默认D+0提现状态关闭
		memberInfo.setT0DrawFee(new BigDecimal(settleFee));
		memberInfo.setT1DrawFee(new BigDecimal(settleFee));
		memberInfo.setT0TradeRate(new BigDecimal(tradeRate));
		memberInfo.setT1TradeRate(new BigDecimal(tradeRate));
		memberInfo.setWxRouteId(routeCode);
		memberInfo.setZfbRouteId(routeCode);
		memberInfo.setRemarks(registerTmp.getRemarks());
		
		MemberBank memberBank = new MemberBank();
		memberBank.setBankId(registerTmp.getBankId());
		memberBank.setSubId(registerTmp.getSubId());
		memberBank.setProvince(registerTmp.getProvince());
		memberBank.setCity(registerTmp.getCity());
		memberBank.setBankOpen(subName);
		memberBank.setAccountName(registerTmp.getAccountName());
		memberBank.setAccountNumber(memberInfo.getCardNbr());
		memberBank.setMobilePhone(settleBankPhone);
		memberBank.setSettleType(memberInfo.getSettleType());
		
		JSONObject merchantObj = null;
		if(routeCode.equals(RouteCodeConstant.YZF_ROUTE_CODE)){
			merchantObj = registYzfAccount(memberInfo, memberBank);
		}else{
			merchantObj = registXinfuAccount(memberInfo, memberBank,platTradeRate,platDrawFee);
		}
		String merchantCode = "",merchantMd5Key = "",orderCode = "";
		if("0000".equals(merchantObj.getString("returnCode"))){
			merchantCode = merchantObj.getString("merchantCode");
			if(merchantObj.containsKey("merchantKey")){
				merchantMd5Key = merchantObj.getString("merchantKey");
			}
			orderCode = merchantObj.getString("orderCode");
		}else{
			String resultMsg = merchantObj.getString("returnMsg");
			RegisterTmp registerTempUpdate = new RegisterTmp();
			registerTempUpdate.setId(registerTmp.getId());
			registerTempUpdate.setStatus("2");
			registerTempUpdate.setRespCode(merchantObj.getString("returnCode"));
			if(!"".equals(resultMsg)){
				registerTempUpdate.setRespMsg(resultMsg.length()>250?resultMsg.substring(0, 250):resultMsg);
			}
			registerTmpService.updateByPrimaryKeySelective(registerTempUpdate);
			result.put("returnCode", "0003");
			result.put("returnMsg", "商户入驻失败："+resultMsg);
			return CommonUtil.signReturn(result);
		}
		
		memberInfo.setStatus("0");
		memberInfo.setWxStatus("1");
		memberInfo.setZfbStatus("1");
		memberInfo.setOrderNo(orderCode);
		memberInfoService.insertSelective(memberInfo);
		
		epayCode.setMemberId(memberInfo.getId());
		epayCodeService.insertSelective(epayCode);
		
		memberBank.setCreateBy("1");
		memberBank.setCreateDate(new Date());
		memberBank.setUpdateBy("1");
		memberBank.setUpdateDate(new Date());
		memberBank.setDelFlag("0");
		memberBank.setMemberId(memberInfo.getId());
		memberBankService.insertSelective(memberBank);
		
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
		
		MemberMerchantCode memberMerchantCode = new MemberMerchantCode();
		memberMerchantCode.setMemberId(memberInfo.getId());
		memberMerchantCode.setRouteCode(routeCode);
		memberMerchantCode.setKjMerchantCode(merchantCode);
		memberMerchantCode.setWyMerchantCode(merchantCode);
		memberMerchantCode.setWyT0DrawFee(new BigDecimal(settleFee));
		memberMerchantCode.setWyT1DrawFee(new BigDecimal(settleFee));
		memberMerchantCode.setWyT0TradeRate(new BigDecimal(tradeRate));
		memberMerchantCode.setWyT1TradeRate(new BigDecimal(tradeRate));
		memberMerchantCode.setKjT0DrawFee(new BigDecimal(settleFee));
		memberMerchantCode.setKjT1DrawFee(new BigDecimal(settleFee));
		memberMerchantCode.setKjT0TradeRate(new BigDecimal(tradeRate));
		memberMerchantCode.setKjT1TradeRate(new BigDecimal(tradeRate));
		memberMerchantCode.setCreateDate(new Date());
		memberMerchantCodeService.insertSelective(memberMerchantCode);
		
		MemberMerchantKey memberMerchantKey = new MemberMerchantKey();
		memberMerchantKey.setRouteCode(routeCode);
		memberMerchantKey.setMerchantCode(merchantCode);
		memberMerchantKey.setPrivateKeyPassword(merchantMd5Key);
		memberMerchantKey.setCreateDate(new Date());
		memberMerchantKeyService.insertSelective(memberMerchantKey);
		if(routeCode.equals(RouteCodeConstant.YZF_ROUTE_CODE)){
			JSONObject rateObj = setMemberRate(merchantCode, merchantMd5Key, platTradeRate, platDrawFee);
			if(!"0000".equals(rateObj.getString("returnCode"))){
				String resultMsg = rateObj.getString("returnMsg");
				registerTempUpdate = new RegisterTmp();
				registerTempUpdate.setId(registerTmp.getId());
				registerTempUpdate.setStatus("3");
				registerTempUpdate.setRespCode(rateObj.getString("returnCode"));
				if(!"".equals(resultMsg)){
					registerTempUpdate.setRespMsg(resultMsg.length()>250?resultMsg.substring(0, 250):resultMsg);
				}
				registerTmpService.updateByPrimaryKeySelective(registerTempUpdate);
				MemberInfo memberInfoUpdate = new MemberInfo();
				memberInfoUpdate.setId(memberInfo.getId());
				memberInfoUpdate.setStatus("2");
				memberInfoService.updateByPrimaryKeySelective(memberInfoUpdate);
				result.put("returnCode", "0003");
				result.put("returnMsg", "商户费率设置失败："+resultMsg);
				return CommonUtil.signReturn(result);
			}
		}
		
		result.put("returnCode", "0000");
		result.put("returnMsg", "商户进件成功");
		result.put("memberCode", memberInfo.getCode());
		return CommonUtil.signReturn(result);
	}
	
	/**
	 * 联行号查询
	 * @param bankAccount
	 * @return
	 */
	public JSONObject queryBankSubId(String bankAccount){
		JSONObject result = new JSONObject();
		try{
			String serverUrl = YZFConfig.msServerUrl;
			String charset = "UTF-8";
			String version = "2.0";	
			String action = "SdkSettleBankCnaps";
			String merNo = YZFConfig.defaultMerNo;//商户号	
			String orgNo = YZFConfig.orgNo;//机构
			String privateKeyStr = YZFConfig.rsaPrivateKey;//RSA私钥
			String md5Key = YZFConfig.defaultMd5Key;
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
	
	
	public JSONObject registYzfAccount(MemberInfo memberInfo,MemberBank memberBank){
		JSONObject result = new JSONObject();
		try{
			String orderCode = CommonUtil.getOrderCode();
			String serverUrl = YZFConfig.msServerUrl;
			String charset = "UTF-8";
			String version = "2.0";	
			String action = "SdkUserStoreBind";
			String merNo = YZFConfig.defaultMerNo;//商户号	
		//	String merNo ="990581000011066";
			String orgNo = YZFConfig.orgNo;//机构
			String privateKeyStr = YZFConfig.rsaPrivateKey;//RSA私钥
			String md5Key = YZFConfig.defaultMd5Key;
		//	String md5Key = "39d0c119ad2b61f89f711a984b7cd9af";
			String aesKey = MSCommonUtil.generateLenString(16);
			byte[] keyBytes = Base64Utils.decode(privateKeyStr);  
		    PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);  
		    KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
		    PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);  
			byte[] RSAKeyBy=RSATool.encrypt(aesKey.getBytes("UTF-8"),privateK);
			String encryptkey= EncryptUtil.encodeBase64(RSAKeyBy);
			
			JSONObject contextjson = new JSONObject();
	     	
			
			contextjson.put("userId",orderCode);//商户编号
	     	contextjson.put("userName",memberInfo.getName());//商户名称
	     	contextjson.put("userNick",memberInfo.getShortName());//商户昵称
	     	contextjson.put("userPhone",memberInfo.getMobilePhone());//法人电话
	     	contextjson.put("userAccount",memberInfo.getContact());//法人姓名
	     	contextjson.put("userCert",memberInfo.getCertNbr());//身份证号
	     	contextjson.put("userEmail",memberInfo.getEmail());//商户邮箱
	     	contextjson.put("userAddress",memberInfo.getAddr());//商户地址
	     	contextjson.put("userMemo",memberInfo.getRemarks());//商户备注
	     	contextjson.put("settleBankNo",memberBank.getAccountNumber());//绑定结算卡号
	     	contextjson.put("settleBankPhone",memberBank.getMobilePhone());//绑定结算卡手机
	     	contextjson.put("settleBankCnaps",memberBank.getSubId());//绑定结算卡联行号
	     	contextjson.put("branchId",YZFConfig.branchId);//渠道类型
			
	     	String aesData=AESTool.encrypt(contextjson.toString(),aesKey);
			
			logger.info("商户进件未加密参数[{}]",contextjson.toString() );
			
			System.out.println("aesDate===="+aesData);
	     	String data	= aesData;
	     	
	     	String signData = version+orgNo+merNo+action+data+md5Key;
	     	System.out.println("signData==="+signData);
	    	String sign	= EncryptUtil.MD5(signData, 1);
	    	System.out.println("sign==="+sign);
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
			logger.info("商户进件返回报文[{}]", new Object[] { respStr });
			
			JSONObject jsonObj = JSONObject.fromObject(respStr);
			byte[] encryptkey_RES=EncryptUtil.decodeBase64(jsonObj.get("encryptkey").toString());
			
			byte[] key_RES_B=RSATool.decrypt(encryptkey_RES,privateK);
			String aes_res_key=new String (key_RES_B);
			//logger.info("代付联行号查询返回报文解密AES key[{}]", new Object[] { aes_res_key });
			String data_RES=jsonObj.get("data").toString();
			String resData=AESTool.decrypt(data_RES, aes_res_key);
			logger.info("商户进件返回报文解密结果[{}]", new Object[] { resData });
			
			JSONObject resObj = JSONObject.fromObject(resData);
			String code = resObj.getString("code");
			if("0000".equals(code)){
				result.put("returnCode", "0000");
				result.put("returnMsg", "成功");
				result.put("merchantCode", resObj.get("userCode"));
				result.put("merchantKey", resObj.get("userKey"));
				result.put("orderCode", orderCode);
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
	
	public JSONObject setMemberRate(String merchantCode,String merchantMd5Key,String platTradeRate,String platDrawFee){
		JSONObject result = new JSONObject();
		try{
			String serverUrl = YZFConfig.msServerUrl;
			String charset = "UTF-8";
			String version = "2.0";	
			String action = "SdkUserStoreRate";
			String merNo = merchantCode;//商户号	
			String orgNo = YZFConfig.orgNo;//机构
			String privateKeyStr = YZFConfig.rsaPrivateKey;//RSA私钥
			String md5Key = merchantMd5Key;
			String aesKey = MSCommonUtil.generateLenString(16);
			
			byte[] keyBytes = Base64Utils.decode(privateKeyStr);  
		    PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);  
		    KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
		    PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);  
			byte[] RSAKeyBy=RSATool.encrypt(aesKey.getBytes("UTF-8"),privateK);
			String encryptkey= EncryptUtil.encodeBase64(RSAKeyBy);
			
			JSONObject contextjson = new JSONObject();
	     	
			contextjson.put("userCode",merchantCode);//商户编号
	     	contextjson.put("payType","1");//交易类型 1快捷
	     	contextjson.put("orderRateT0",String.valueOf((int)(((new BigDecimal(platTradeRate)).floatValue())*100)));//交易费率
	     	contextjson.put("settleChargeT0",String.valueOf((int)(((new BigDecimal(platDrawFee)).floatValue())*100)));//提现附加费用
	     	
	     	String aesData=AESTool.encrypt(contextjson.toString(),aesKey);
			
			logger.info("商户费率设置未加密参数[{}]",contextjson.toString() );
			
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
			logger.info("商户费率设置返回报文[{}]", new Object[] { respStr });
			
			JSONObject jsonObj = JSONObject.fromObject(respStr);
			byte[] encryptkey_RES=EncryptUtil.decodeBase64(jsonObj.get("encryptkey").toString());
			
			byte[] key_RES_B=RSATool.decrypt(encryptkey_RES,privateK);
			String aes_res_key=new String (key_RES_B);
			//logger.info("代付联行号查询返回报文解密AES key[{}]", new Object[] { aes_res_key });
			String data_RES=jsonObj.get("data").toString();
			String resData=AESTool.decrypt(data_RES, aes_res_key);
			logger.info("商户费率设置返回报文解密结果[{}]", new Object[] { resData });
			
			JSONObject resObj = JSONObject.fromObject(resData);
			String code = resObj.getString("code");
			if("0000".equals(code)){
				result.put("returnCode", "0000");
				result.put("returnMsg", "成功");
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
	@ResponseBody
	@RequestMapping("/memberInfo/memberRate")
	public JSONObject memberRate(HttpServletRequest request,HttpServletResponse response){
		JSONObject result = new JSONObject();
		try{
			String merchantCode = request.getParameter("merchantCode");
			String merchantMd5Key = request.getParameter("merchantMd5Key");
			String platTradeRate = request.getParameter("platTradeRate");
			String platDrawFee = request.getParameter("platDrawFee");
			String serverUrl = YZFConfig.msServerUrl;
			String charset = "UTF-8";
			String version = "2.0";	
			String action = "SdkUserStoreRate";
			String merNo = merchantCode;//商户号	
			String orgNo = YZFConfig.orgNo;//机构
			String privateKeyStr = YZFConfig.rsaPrivateKey;//RSA私钥
			String md5Key = merchantMd5Key;
			String aesKey = MSCommonUtil.generateLenString(16);
			
			byte[] keyBytes = Base64Utils.decode(privateKeyStr);  
		    PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);  
		    KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
		    PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);  
			byte[] RSAKeyBy=RSATool.encrypt(aesKey.getBytes("UTF-8"),privateK);
			String encryptkey= EncryptUtil.encodeBase64(RSAKeyBy);
			
			JSONObject contextjson = new JSONObject();
	     	
			contextjson.put("userCode",merchantCode);//商户编号
	     	contextjson.put("payType","1");//交易类型 1快捷
	     	contextjson.put("orderRateT0",String.valueOf((float)(((new BigDecimal(platTradeRate)).floatValue())*100)));//交易费率
	     	contextjson.put("settleChargeT0",String.valueOf((float)(((new BigDecimal(platDrawFee)).floatValue())*100)));//提现附加费用
	     	
	     	String aesData=AESTool.encrypt(contextjson.toString(),aesKey);
			
			logger.info("商户费率设置未加密参数[{}]",contextjson.toString() );
			
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
			logger.info("商户费率设置返回报文[{}]", new Object[] { respStr });
			
			JSONObject jsonObj = JSONObject.fromObject(respStr);
			byte[] encryptkey_RES=EncryptUtil.decodeBase64(jsonObj.get("encryptkey").toString());
			
			byte[] key_RES_B=RSATool.decrypt(encryptkey_RES,privateK);
			String aes_res_key=new String (key_RES_B);
			//logger.info("代付联行号查询返回报文解密AES key[{}]", new Object[] { aes_res_key });
			String data_RES=jsonObj.get("data").toString();
			String resData=AESTool.decrypt(data_RES, aes_res_key);
			logger.info("商户费率设置返回报文解密结果[{}]", new Object[] { resData });
			
			JSONObject resObj = JSONObject.fromObject(resData);
			String code = resObj.getString("code");
			if("0000".equals(code)){
				result.put("returnCode", "0000");
				result.put("returnMsg", "成功");
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
	
	public JSONObject registXinfuAccount(MemberInfo memberInfo,MemberBank memberBank,String platTradeRate,String platDrawFee){
		JSONObject result = new JSONObject();
		try{
			String orderCode = CommonUtil.getOrderCode();
			String serverUrl = XinFuConfig.msServerUrl+"/Mer/api/newMerInfoJoin";
			String orgNo = XinFuConfig.orgNo;//机构
			String md5Key = XinFuConfig.md5Key;
		
			Map<String,String> param = new HashMap<String, String>();
			param.put("serialNo", orderCode);
			param.put("brhId", orgNo);
			param.put("merchantName", memberInfo.getName());
			param.put("accountName", memberBank.getAccountName());
			param.put("account", memberBank.getAccountNumber());
			param.put("idCard", memberInfo.getCertNbr());
			param.put("telephone", memberInfo.getMobilePhone());
			param.put("rate", String.valueOf((float)(((new BigDecimal(platTradeRate)).floatValue())*1000)));//借记卡费率/贷记卡费率
			param.put("type", "syb2kj");//syb2kj：不封顶有积分
			param.put("top", "8000");//封顶值     先写死
			param.put("isNeedD0", "YES");//YES:开通;NO:不开通
			param.put("d0Rate", "8");//代付垫资费率  单位：千分之   先写死
			param.put("d0FeeBi", String.valueOf((int)(((new BigDecimal(platDrawFee)).floatValue())*100)));//代付单笔手续费  单位：分
			param.put("d0MinAmount", "500");//代付单笔最小金额
			param.put("doMinFee", String.valueOf((int)(((new BigDecimal(platDrawFee)).floatValue())*100)));//保底手续费
			
			String sign = Md5SignUtil.md5Sign(param, md5Key);
			param.put("ptform", XinFuConfig.ptform);//商户平台代码
			param.put("sign", sign);
			
			
			
			logger.info("商户进件参数[{}]",JSONObject.fromObject(param).toString() );
		
			JSONObject resObj = HttpPostRequest.sendRequest(param, serverUrl);
			
			logger.info("商户进件返回报文[{}]", new Object[] { resObj });
			
			
			String code = resObj.getString("resultCode");
			if("0000".equals(code)){
				result.put("returnCode", "0000");
				result.put("returnMsg", "成功");
				result.put("merchantCode", resObj.get("merId"));
				result.put("orderCode", orderCode);
			}else{
				result.put("returnCode", "0003");
				result.put("returnMsg", resObj.getString("resultMsg"));
			}
	     	
		}catch(Exception e){
			logger.info(e.getMessage(), e);
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
		}
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping("/toRegister")
	public JSONObject toRegister(Model model, HttpServletRequest request) {
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		JSONObject result=new JSONObject();
		try {
			String name =  reqDataJson.getString("name");
			String shortName =  reqDataJson.getString("shortName");
			String contact =  reqDataJson.getString("contact");
			String mobilePhone =  reqDataJson.getString("mobilePhone");
			String certNbr =  reqDataJson.getString("certNbr");
			String busLicenceNbr =  reqDataJson.getString("busLicenceNbr");
			String email =  reqDataJson.getString("email");
			String officeId =  reqDataJson.getString("officeId");
			String remarks =  reqDataJson.getString("remarks");
			String tradeInfo =  reqDataJson.getString("tradeInfo");
			
			String[] tardeObj = tradeInfo.split(";");
			String[] obj = tardeObj[0].split("#");
			String t0TradeRate = obj[2];
			String t1TradeRate = obj[3];
			
			if(StringUtil.isEmpty(shortName)){
				shortName = name;
			}
			if(StringUtil.isEmpty(contact)){
				contact = name;
			}
			
			
			
			Date nowDate = new Date();
			
			Map<String,Object> keyMap=RSAUtil.initKey();  
	        byte[] publicKey=RSAUtil.getPublicKey(keyMap);  //公钥  
	        byte[] privateKey=RSAUtil.getPrivateKey(keyMap);  //私钥  
			
			EpayCode epayCode = new EpayCode();
			epayCode.setPayCode("J"+commonService.getNextSequenceVal(SequenseTypeConstant.EPAYCODE));
			epayCode.setStatus("5");
			epayCode.setOfficeId(officeId);
			epayCode.setCreateDate(nowDate);
			epayCode.setUpdateDate(nowDate);
			epayCode.setCreateBy("1");
			epayCode.setUpdateBy("1");
			
			RegisterTmp registerTmp = new RegisterTmp();
			registerTmp.setParentId(0);
			registerTmp.setType("1");
			registerTmp.setLoginCode(mobilePhone);
			registerTmp.setContact(contact);
			registerTmp.setName(name);
			registerTmp.setShortName(shortName);
			registerTmp.setSettleType("1");
			registerTmp.setAddr("");
			registerTmp.setCertNbr(certNbr);
			registerTmp.setProvince("");
			registerTmp.setCity("");
			registerTmp.setCounty("");
			registerTmp.setContactType("00");
			registerTmp.setEmail(email);
			registerTmp.setBankId("");
			registerTmp.setSubId("");
			registerTmp.setBankOpen("");
			registerTmp.setMobilePhone(mobilePhone);
			registerTmp.setAccountName("");
			registerTmp.setAccountNumber("");
			registerTmp.setPayCode(epayCode.getPayCode());
			registerTmp.setRemarks(remarks);
			registerTmp.setCreateBy("1");
			registerTmp.setUpdateBy("1");
			registerTmp.setDelFlag("0");
			registerTmp.setStatus("0");
			registerTmp.setCode(SysConfig.prefixMemberCode+commonService.getNextSequenceVal(SequenseTypeConstant.MEMBERCODE));
			registerTmp.setLoginPass(SecurityUtil.md5Encode(registerTmp.getMobilePhone().substring(5, 11)));
			registerTmp.setCreateDate(nowDate);
			registerTmp.setUpdateDate(nowDate);
			registerTmp.setWxMemberCode("w"+commonService.getNextSequenceVal(SequenseTypeConstant.WXMEMBERCODE));
			registerTmp.setZfbMemberCode("z"+commonService.getNextSequenceVal(SequenseTypeConstant.ZFBMEMBERCODE));
			registerTmp.setQqMemberCode("q"+commonService.getNextSequenceVal(SequenseTypeConstant.QQMEMBERCODE));
			registerTmp.setBdMemberCode("b"+commonService.getNextSequenceVal(SequenseTypeConstant.BDMEMBERCODE));
			registerTmp.setJdMemberCode("j"+commonService.getNextSequenceVal(SequenseTypeConstant.JDMEMBERCODE));
			registerTmp.setOrderNumOuter("");
			registerTmpService.insertSelective(registerTmp);
			
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
			memberInfo.setCertNbr(registerTmp.getCertNbr());
			memberInfo.setProvince(registerTmp.getProvince());
			memberInfo.setCity(registerTmp.getCity());
			memberInfo.setCounty(registerTmp.getCounty());
			memberInfo.setAddr(registerTmp.getAddr());
			//getMerchantAddress(memberInfo);
			memberInfo.setMobilePhone(registerTmp.getMobilePhone());
			memberInfo.setCardPic1(registerTmp.getCardPic1());
			memberInfo.setCardPic2(registerTmp.getCardPic2());
			memberInfo.setCertPic1(registerTmp.getCertPic1());
			memberInfo.setCertPic2(registerTmp.getCertPic2());
			memberInfo.setBusPic(registerTmp.getMemcertPic());//营业执照照片
			memberInfo.setMemcertPic(registerTmp.getAuthPic());//授权证书
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
			memberInfo.setBusLicenceNbr(registerTmp.getBusLicenceNbr());//营业执照编号
			memberInfo.setEmail(registerTmp.getEmail());
			memberInfo.setLevel("0");
			memberInfo.setDrawStatus("1"); //默认D+0提现状态关闭
			//memberInfo.setT0DrawFee(new BigDecimal(settleFee));
			//memberInfo.setT1DrawFee(new BigDecimal(settleFee));
			memberInfo.setT0TradeRate(new BigDecimal(t0TradeRate));
			memberInfo.setT1TradeRate(new BigDecimal(t1TradeRate));
			memberInfo.setWxRouteId("1004");
			memberInfo.setZfbRouteId("1004");
			memberInfo.setRemarks(registerTmp.getRemarks());
			
			MemberBank memberBank = new MemberBank();
			memberBank.setBankId(registerTmp.getBankId());
			memberBank.setSubId(registerTmp.getSubId());
			memberBank.setProvince(registerTmp.getProvince());
			memberBank.setCity(registerTmp.getCity());
			memberBank.setBankOpen("");
			memberBank.setAccountName(registerTmp.getAccountName());
			memberBank.setAccountNumber(memberInfo.getCardNbr());
			memberBank.setMobilePhone("");
			memberBank.setSettleType(memberInfo.getSettleType());
			
			SysOfficeExample sysOfficeExample = new SysOfficeExample();
			sysOfficeExample.createCriteria().andIdEqualTo(officeId);
			
			sysOfficeService.selectByExample(sysOfficeExample);
			
			
			SysOffice sysOffice = new SysOffice();
			sysOffice.setId(officeId);
			sysOffice.setPublicKeyRsa(Base64.encodeBase64String(publicKey));
			sysOffice.setPrivateKeyRsa(Base64.encodeBase64String(privateKey));
			sysOfficeService.updateByPrimaryKeySelective(sysOffice);

		}catch(Exception e){
			
		}
		return result;
	}
	
	
	/**
	 * 易生商户进件
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/memberInfo/toRegistNew")
	public JSONObject toRegistNew(HttpServletRequest request,HttpServletResponse response){
		Map<String,String> inparam = new HashMap<String, String>();
		Enumeration<String> pNames=request.getParameterNames();
		while(pNames.hasMoreElements()){
		    String name=(String)pNames.nextElement();
		    String value=request.getParameter(name);
		    inparam.put(name, value);
		}
		logger.info("易生商户进件下游入参[{}]",  JSONObject.fromObject(inparam).toString() );
		
		
		JSONObject result = new JSONObject();
		String officeId = request.getParameter("officeId");
		String orderNum = request.getParameter("orderNum");
		String name = request.getParameter("name");
		String shortName = request.getParameter("shortName");
		String userName = request.getParameter("userName");
		String mobilePhone = request.getParameter("mobilePhone");
		String certNbr = request.getParameter("certNbr");
		String email = request.getParameter("email");
		String tradeRate = request.getParameter("tradeRate");
		String settleFee = request.getParameter("settleFee");
		String signStr = request.getParameter("signStr");
		
		String routeCode = RouteCodeConstant.YS_ROUTE_CODE;
		
		//待签名字符串
		Map<String,String> params = new HashMap<String,String>();
		if(officeId == null || "".equals(officeId)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "机构ID[officeId]缺失");
			return CommonUtil.signReturn(result);
		}
		params.put("officeId", officeId);
		
		if(orderNum == null || "".equals(orderNum)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "商户订单号[orderNum]缺失");
			return CommonUtil.signReturn(result);
		}
		params.put("orderNum", orderNum);
		
		if(name == null || "".equals(name)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "商户名称[name]缺失");
			return CommonUtil.signReturn(result);
		}
		params.put("name", name);
		
		if(shortName == null || "".equals(shortName)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "商户简称[shortName]缺失");
			return CommonUtil.signReturn(result);
		}
		params.put("shortName", shortName);
		
		if(userName == null || "".equals(userName)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "法人名称[userName]缺失");
			return CommonUtil.signReturn(result);
		}
		params.put("userName", userName);
		
		if(mobilePhone == null || "".equals(mobilePhone)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "法人电话[mobilePhone]缺失");
			return CommonUtil.signReturn(result);
		}
		params.put("mobilePhone", mobilePhone);
		
		if(certNbr == null || "".equals(certNbr)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "身份证号[certNbr]缺失");
			return CommonUtil.signReturn(result);
		}
		params.put("certNbr", certNbr);
		
		if(email == null || "".equals(email)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "邮箱[email]缺失");
			return CommonUtil.signReturn(result);
		}
		params.put("email", email);
		
		if(tradeRate == null || "".equals(tradeRate)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "交易费率[tradeRate]缺失");
			return CommonUtil.signReturn(result);
		}
		params.put("tradeRate", tradeRate);
		
		if(settleFee == null || "".equals(settleFee)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "提现费用[settleFee]缺失");
			return CommonUtil.signReturn(result);
		}
		params.put("settleFee", settleFee);
		
		if(signStr == null || "".equals(signStr)){
			result.put("returnCode", "0007");
			result.put("returnMsg", "签名[signStr]缺失");
			return CommonUtil.signReturn(result);
		}
		
		String srcStr = StringUtil.orderedKey(params);
		
		SysOfficeExample sysOfficeExample = new SysOfficeExample();
		sysOfficeExample.or().andIdEqualTo(officeId).andDelFlagEqualTo("0");
		List<SysOffice> sysOfficeList = sysOfficeService.selectByExample(sysOfficeExample);
		if(null == sysOfficeList || sysOfficeList.size() == 0){
			result.put("returnCode", "0008");
			result.put("returnMsg", "机构不存在");
			return CommonUtil.signReturn(result);
		}
		
		SysOffice sysOffice = sysOfficeList.get(0);
		if(!EpaySignUtil.checksign(sysOffice.getPublicKeyRsa(), srcStr, signStr)){
			result.put("returnCode", "0004");
			result.put("returnMsg", "签名校验错误，请检查签名参数是否正确");
			return CommonUtil.signReturn(result);
		}
		
		
		MemberInfoExample memberInfoExample = new MemberInfoExample();
		memberInfoExample.or().andCertNbrEqualTo(certNbr).andStatusNotEqualTo("1").andDelFlagEqualTo("0");
		int countMemberInfo = memberInfoService.countByExample(memberInfoExample);
		if(countMemberInfo >0){
			result.put("returnCode", "4004");
			result.put("returnMsg", "商户信息已存在，该身份证号码已注册");
			return CommonUtil.signReturn(result);
		}
		/*memberInfoExample = new MemberInfoExample();
		memberInfoExample.or().andMobilePhoneEqualTo(mobilePhone).andStatusNotEqualTo("1").andDelFlagEqualTo("0");
		countMemberInfo = memberInfoService.countByExample(memberInfoExample);
		if(countMemberInfo > 0){
			result.put("returnCode", "4004");
			result.put("returnMsg", "商户信息已存在，该手机号码已注册");
			return CommonUtil.signReturn(result);
		}
		*/
		
		
		String platTradeRate = "", platDrawFee = "";
		SysCommonConfigExample sysCommonConfigExample = new SysCommonConfigExample();
		sysCommonConfigExample.or().andNameEqualTo("PLAT_TRADE_RATE_"+routeCode).andDelFlagEqualTo("0");
		List<SysCommonConfig>  sysCommonConfig = sysCommonConfigService.selectByExample(sysCommonConfigExample);
		if (sysCommonConfig.size() == 0) {
			result.put("returnCode", "4004");
			result.put("returnMsg", "系统默认交易费率配置缺失，请与管理员联系");
			return CommonUtil.signReturn(result);
		}
		platTradeRate = sysCommonConfig.get(0).getValue();
		
		sysCommonConfigExample = new SysCommonConfigExample();
		sysCommonConfigExample.or().andNameEqualTo("PLAT_DRAW_FEE_"+routeCode).andDelFlagEqualTo("0");
		sysCommonConfig = sysCommonConfigService.selectByExample(sysCommonConfigExample);
		if (sysCommonConfig.size() == 0) {
			result.put("returnCode", "4004");
			result.put("returnMsg", "系统默认提现费用配置缺失，请与管理员联系");
			return CommonUtil.signReturn(result);
		}
		platDrawFee = sysCommonConfig.get(0).getValue();
		
		if(Double.valueOf(tradeRate)<Double.valueOf(platTradeRate)){
			result.put("returnCode", "4004");
			result.put("returnMsg", "交易费率小于最小金额");
			return CommonUtil.signReturn(result);
		}
		
		if(Double.valueOf(settleFee)<Double.valueOf(platDrawFee)){
			result.put("returnCode", "4004");
			result.put("returnMsg", "提现手续费小于最小金额");
			return CommonUtil.signReturn(result);
		}
		String subId = "";
		String subName = "";
		String bankId = "";
		Date nowDate = new Date();
		
		EpayCode epayCode = new EpayCode();
		epayCode.setPayCode("J"+commonService.getNextSequenceVal(SequenseTypeConstant.EPAYCODE));
		epayCode.setT0DrawFee(new BigDecimal(settleFee));
		epayCode.setT1DrawFee(new BigDecimal(settleFee));
		epayCode.setT0TradeRate(new BigDecimal(tradeRate));
		epayCode.setT1TradeRate(new BigDecimal(tradeRate));
		epayCode.setStatus("5");
		epayCode.setOfficeId(officeId);
		epayCode.setCreateDate(nowDate);
		epayCode.setUpdateDate(nowDate);
		epayCode.setCreateBy("1");
		epayCode.setUpdateBy("1");
		
		RegisterTmp registerTmp = new RegisterTmp();
		registerTmp.setParentId(0);
		registerTmp.setType("1");
		registerTmp.setLoginCode(mobilePhone);
		registerTmp.setContact(userName);
		registerTmp.setName(name);
		registerTmp.setShortName(shortName);
		registerTmp.setSettleType("1");
		registerTmp.setAddr("");
		registerTmp.setCertNbr(certNbr);
		registerTmp.setProvince("");
		registerTmp.setCity("");
		registerTmp.setCounty("");
		registerTmp.setContactType("00");
		registerTmp.setEmail(email);
		registerTmp.setBankId(bankId);
		registerTmp.setSubId(subId);
		registerTmp.setBankOpen(subName);
		registerTmp.setMobilePhone(mobilePhone);
		registerTmp.setAccountName("");
		registerTmp.setAccountNumber("");
		registerTmp.setPayCode(epayCode.getPayCode());
		registerTmp.setRemarks("");
		registerTmp.setCreateBy("1");
		registerTmp.setUpdateBy("1");
		registerTmp.setDelFlag("0");
		registerTmp.setStatus("0");
		registerTmp.setCode(SysConfig.prefixMemberCode+commonService.getNextSequenceVal(SequenseTypeConstant.MEMBERCODE));
		registerTmp.setLoginPass(SecurityUtil.md5Encode(registerTmp.getMobilePhone().substring(5, 11)));
		registerTmp.setCreateDate(nowDate);
		registerTmp.setUpdateDate(nowDate);
		registerTmp.setWxMemberCode("w"+commonService.getNextSequenceVal(SequenseTypeConstant.WXMEMBERCODE));
		registerTmp.setZfbMemberCode("z"+commonService.getNextSequenceVal(SequenseTypeConstant.ZFBMEMBERCODE));
		registerTmp.setQqMemberCode("q"+commonService.getNextSequenceVal(SequenseTypeConstant.QQMEMBERCODE));
		registerTmp.setBdMemberCode("b"+commonService.getNextSequenceVal(SequenseTypeConstant.BDMEMBERCODE));
		registerTmp.setJdMemberCode("j"+commonService.getNextSequenceVal(SequenseTypeConstant.JDMEMBERCODE));
		registerTmp.setOrderNumOuter(orderNum);
		registerTmpService.insertSelective(registerTmp);
		
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
		memberInfo.setCertNbr(registerTmp.getCertNbr());
		memberInfo.setProvince(registerTmp.getProvince());
		memberInfo.setCity(registerTmp.getCity());
		memberInfo.setCounty(registerTmp.getCounty());
		memberInfo.setAddr(registerTmp.getAddr());
		//getMerchantAddress(memberInfo);
		memberInfo.setMobilePhone(registerTmp.getMobilePhone());
		memberInfo.setCardPic1(registerTmp.getCardPic1());
		memberInfo.setCardPic2(registerTmp.getCardPic2());
		memberInfo.setCertPic1(registerTmp.getCertPic1());
		memberInfo.setCertPic2(registerTmp.getCertPic2());
		memberInfo.setBusPic(registerTmp.getMemcertPic());//营业执照照片
		memberInfo.setMemcertPic(registerTmp.getAuthPic());//授权证书
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
		memberInfo.setBusLicenceNbr(registerTmp.getBusLicenceNbr());//营业执照编号
		memberInfo.setEmail(registerTmp.getEmail());
		memberInfo.setLevel("0");
		memberInfo.setDrawStatus("1"); //默认D+0提现状态关闭
		memberInfo.setT0DrawFee(new BigDecimal(settleFee));
		memberInfo.setT1DrawFee(new BigDecimal(settleFee));
		memberInfo.setT0TradeRate(new BigDecimal(tradeRate));
		memberInfo.setT1TradeRate(new BigDecimal(tradeRate));
		memberInfo.setWxRouteId(routeCode);
		memberInfo.setZfbRouteId(routeCode);
		memberInfo.setRemarks(registerTmp.getRemarks());
		
		MemberBank memberBank = new MemberBank();
		memberBank.setBankId(registerTmp.getBankId());
		memberBank.setSubId(registerTmp.getSubId());
		memberBank.setProvince(registerTmp.getProvince());
		memberBank.setCity(registerTmp.getCity());
		memberBank.setBankOpen(subName);
		memberBank.setAccountName(registerTmp.getAccountName());
		memberBank.setAccountNumber(memberInfo.getCardNbr());
		memberBank.setMobilePhone("");
		memberBank.setSettleType(memberInfo.getSettleType());
		
		JSONObject merchantObj =  registYsAccount(memberInfo, memberBank,platTradeRate,platDrawFee);
		
		String merchantCode = "",orderCode = "";
		if("0000".equals(merchantObj.getString("returnCode"))){
			merchantCode = merchantObj.getString("merchantCode");
			//if(merchantObj.containsKey("merchantKey")){
			//	merchantMd5Key = merchantObj.getString("merchantKey");
			//}
			orderCode = merchantObj.getString("orderCode");
		}else{
			String resultMsg = merchantObj.getString("returnMsg");
			RegisterTmp registerTempUpdate = new RegisterTmp();
			registerTempUpdate.setId(registerTmp.getId());
			registerTempUpdate.setStatus("2");
			registerTempUpdate.setRespCode(merchantObj.getString("returnCode"));
			if(!"".equals(resultMsg)){
				registerTempUpdate.setRespMsg(resultMsg.length()>250?resultMsg.substring(0, 250):resultMsg);
			}
			registerTmpService.updateByPrimaryKeySelective(registerTempUpdate);
			result.put("returnCode", "0003");
			result.put("returnMsg", "商户入驻失败："+resultMsg);
			return CommonUtil.signReturn(result);
		}
		
		memberInfo.setStatus("0");
		memberInfo.setWxStatus("1");
		memberInfo.setZfbStatus("1");
		memberInfo.setOrderNo(orderCode);
		memberInfoService.insertSelective(memberInfo);
		
		epayCode.setMemberId(memberInfo.getId());
		epayCodeService.insertSelective(epayCode);
		
		memberBank.setCreateBy("1");
		memberBank.setCreateDate(new Date());
		memberBank.setUpdateBy("1");
		memberBank.setUpdateDate(new Date());
		memberBank.setDelFlag("0");
		memberBank.setMemberId(memberInfo.getId());
		memberBankService.insertSelective(memberBank);
		
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
		
		MemberMerchantCode memberMerchantCode = new MemberMerchantCode();
		memberMerchantCode.setMemberId(memberInfo.getId());
		memberMerchantCode.setRouteCode(routeCode);
		memberMerchantCode.setKjMerchantCode(merchantCode);
		memberMerchantCode.setWyMerchantCode(merchantCode);
		memberMerchantCode.setWyT0DrawFee(new BigDecimal(settleFee));
		memberMerchantCode.setWyT1DrawFee(new BigDecimal(settleFee));
		memberMerchantCode.setWyT0TradeRate(new BigDecimal(tradeRate));
		memberMerchantCode.setWyT1TradeRate(new BigDecimal(tradeRate));
		memberMerchantCode.setKjT0DrawFee(new BigDecimal(settleFee));
		memberMerchantCode.setKjT1DrawFee(new BigDecimal(settleFee));
		memberMerchantCode.setKjT0TradeRate(new BigDecimal(tradeRate));
		memberMerchantCode.setKjT1TradeRate(new BigDecimal(tradeRate));
		memberMerchantCode.setCreateDate(new Date());
		memberMerchantCodeService.insertSelective(memberMerchantCode);
		
	/*	MemberMerchantKey memberMerchantKey = new MemberMerchantKey();
		memberMerchantKey.setRouteCode(routeCode);
		memberMerchantKey.setMerchantCode(merchantCode);
		memberMerchantKey.setPrivateKeyPassword(merchantMd5Key);
		memberMerchantKey.setCreateDate(new Date());
		memberMerchantKeyService.insertSelective(memberMerchantKey);*/
		
		result.put("returnCode", "0000");
		result.put("returnMsg", "商户进件成功");
		result.put("memberCode", memberInfo.getCode());
		return CommonUtil.signReturn(result);
	}
	
	//易生商户进件
	public JSONObject registYsAccount(MemberInfo memberInfo,MemberBank memberBank,String platTradeRate,String platDrawFee){
		JSONObject result = new JSONObject();
		try{
			String orderCode = CommonUtil.getOrderCode();
			String serverUrl = YSConfig.msServerUrl+"/swp/dh/register_mer.do";
			String orgNo = YSConfig.orgNo;//机构
			String privateKey = YSConfig.privateKey;
		
			Map<String,String> param = new HashMap<String, String>();
			param.put("sp_id", orgNo);
			param.put("mch_id", YSConfig.defaultMerNo);
			param.put("out_trade_no", orderCode);
			param.put("id_type", "01");
			param.put("acc_name", memberInfo.getContact());
			param.put("id_no", memberInfo.getCertNbr());
			param.put("settle_rate", platTradeRate);//借记卡费率/贷记卡费率
			param.put("extra_rate", String.valueOf((int)(((new BigDecimal(platDrawFee)).floatValue())*100)));
			
			Date t = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(t);
			long sys_timestamp = cal.getTimeInMillis();
			param.put("timestamp", String.valueOf(sys_timestamp));
			
			String srcStr = StringUtil.orderedKey(param)+"&key="+privateKey;
			String sign = SwpHashUtil.getSign(srcStr, privateKey, "SHA256");
			param.put("sign", sign);
			String paramStr = StringUtil.orderedKey(param) + "&sign="+sign;
			logger.info("商户进件参数[{}]",JSONObject.fromObject(param).toString() );
			
			HttpResponse httpResponse =HttpUtils.doPost(serverUrl, "", paramStr, "application/x-www-form-urlencoded; charset=UTF-8");
			String respStr = EntityUtils.toString(httpResponse.getEntity());
			logger.info("商户进件返回报文[{}]", new Object[] { respStr });
			
			JSONObject resObj = JSONObject.fromObject(respStr);
			
			String code = resObj.getString("status");
			if("SUCCESS".equals(code)){
				String resSign = resObj.getString("sign");
				resObj.remove("sign");
				srcStr = StringUtil.orderedKey(resObj)+"&key="+privateKey;
				if(resSign.equals(SwpHashUtil.getSign(srcStr, privateKey, "SHA256"))){
					String trade_state = resObj.getString("trade_state");
					if("SUCCESS".equals(trade_state)){
						result.put("returnCode", "0000");
						result.put("returnMsg", "成功");
						result.put("merchantCode", resObj.get("sub_mch_id"));
					}else if("EXIST".equals(trade_state)){
						result.put("returnCode", "0004");
						result.put("returnMsg", "身份证已进件，商户信息已存在");
					}else{
						result.put("returnCode", "0003");
						result.put("returnMsg", resObj.getString("trade_state_desc"));
					}
				}else{
					result.put("returnCode", "0003");
					result.put("returnMsg", "商户进件出参验签失败");
				}
			}else{
				result.put("returnCode", "0003");
				result.put("returnMsg", resObj.getString("message"));
			}
			result.put("orderCode", orderCode);
		}catch(Exception e){
			logger.info(e.getMessage(), e);
			result.put("returnCode", "0096");
			result.put("returnMsg", e.getMessage());
		}
		return result;
	}
	
	
}
