package com.epay.scanposp.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.epay.scanposp.common.constant.ESKConfig;
import com.epay.scanposp.common.constant.MSConfig;
import com.epay.scanposp.common.constant.SysConfig;
import com.epay.scanposp.common.constant.WxConfig;
import com.epay.scanposp.common.excep.ArgException;
import com.epay.scanposp.common.utils.CommonUtil;
import com.epay.scanposp.common.utils.FileUtil;
import com.epay.scanposp.common.utils.SecurityUtil;
import com.epay.scanposp.common.utils.ValidateUtil;
import com.epay.scanposp.common.utils.XmlConvertUtil;
import com.epay.scanposp.common.utils.constant.MSPayWayConstant;
import com.epay.scanposp.common.utils.constant.SequenseTypeConstant;
import com.epay.scanposp.common.utils.constant.SysCommonConfigConstant;
import com.epay.scanposp.common.utils.constant.TranCodeConstant;
import com.epay.scanposp.common.utils.esk.Key;
import com.epay.scanposp.common.utils.ms.CryptoUtil;
import com.epay.scanposp.common.utils.ms.HttpClient4Util;
import com.epay.scanposp.common.utils.ms.MSCommonUtil;
import com.epay.scanposp.entity.Account;
import com.epay.scanposp.entity.AgentRate;
import com.epay.scanposp.entity.AgentRateExample;
import com.epay.scanposp.entity.Bank;
import com.epay.scanposp.entity.BankSub;
import com.epay.scanposp.entity.BankSubExample;
import com.epay.scanposp.entity.BuAreaCode;
import com.epay.scanposp.entity.BuAreaCodeExample;
import com.epay.scanposp.entity.BusinessCategory;
import com.epay.scanposp.entity.BusinessCategoryExample;
import com.epay.scanposp.entity.EpayCode;
import com.epay.scanposp.entity.EpayCodeExample;
import com.epay.scanposp.entity.Kbin;
import com.epay.scanposp.entity.KbinExample;
import com.epay.scanposp.entity.MemberBank;
import com.epay.scanposp.entity.MemberBankExample;
import com.epay.scanposp.entity.MemberInfo;
import com.epay.scanposp.entity.MemberInfoExample;
import com.epay.scanposp.entity.MemberInfoExample.Criteria;
import com.epay.scanposp.entity.MemberOpenid;
import com.epay.scanposp.entity.MemberOpenidExample;
import com.epay.scanposp.entity.RegisterResultNotice;
import com.epay.scanposp.entity.RegisterTmp;
import com.epay.scanposp.entity.RouteWay;
import com.epay.scanposp.entity.RouteWayExample;
import com.epay.scanposp.entity.SysArea;
import com.epay.scanposp.entity.SysAreaExample;
import com.epay.scanposp.entity.SysCommonConfig;
import com.epay.scanposp.entity.SysCommonConfigExample;
import com.epay.scanposp.entity.SysOffice;
import com.epay.scanposp.entity.SysOfficeExample;
import com.epay.scanposp.entity.TransactionRate;
import com.epay.scanposp.entity.TransactionRateExample;
import com.epay.scanposp.entity.VerifyCode;
import com.epay.scanposp.entity.VerifyCodeExample;
import com.epay.scanposp.service.AccountService;
import com.epay.scanposp.service.AgentRateService;
import com.epay.scanposp.service.BankService;
import com.epay.scanposp.service.BankSubService;
import com.epay.scanposp.service.BuAreaCodeService;
import com.epay.scanposp.service.BusinessCategoryService;
import com.epay.scanposp.service.CommonService;
import com.epay.scanposp.service.EpayCodeService;
import com.epay.scanposp.service.KbinService;
import com.epay.scanposp.service.MemberBankService;
import com.epay.scanposp.service.MemberInfoService;
import com.epay.scanposp.service.MemberOpenidService;
import com.epay.scanposp.service.RegisterResultNoticeService;
import com.epay.scanposp.service.RegisterTmpService;
import com.epay.scanposp.service.RouteWayService;
import com.epay.scanposp.service.SysAraeService;
import com.epay.scanposp.service.SysCommonConfigService;
import com.epay.scanposp.service.SysOfficeService;
import com.epay.scanposp.service.TransactionRateService;
import com.epay.scanposp.service.VerifyCodeService;
import com.epay.scanposp.thread.QrcodeCreateThread;

@Controller
@RequestMapping("api/registerLogin")
public class RegisterLoginController {
	private static Logger logger = LoggerFactory.getLogger(RegisterLoginController.class);
	
	@Resource
	ThreadPoolTaskExecutor threadPoolTaskExecutor;
	
	@Autowired
	private VerifyCodeService verifyCodeService;
	
	@Autowired
	private RegisterTmpService registerTmpService;
	
	@Autowired
	private MemberInfoService memberInfoService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private EpayCodeService epayCodeService;
	
	@Autowired
	private MemberBankService memberBankService;
	
	@Autowired
	private BankService bankService;
	
	@Autowired
	private BankSubService bankSubService;

	@Autowired
	private SysAraeService sysAreaService ;
	@Autowired
	private BuAreaCodeService buAreaCodeService ;
	
	@Autowired
	private BusinessCategoryService businessCategoryService;
	
	@Autowired
	private TransactionRateService transactionRateService;
	
	@Autowired
	private RouteWayService routeWayService;
	
	@Autowired
	private RegisterResultNoticeService registerResultNoticeService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private MemberOpenidService memberOpenidService;
	
	@Autowired
	private KbinService kbinService;
	
	@Autowired
	private SysOfficeService sysOfficeService;
	
	@Autowired
	private AgentRateService agentRateService;
	
	@Autowired
	private SysCommonConfigService sysCommonConfigService;
	
	@Resource
	private MerchantManagerController merchantManagerController;
	
	@ResponseBody
	@RequestMapping("/toRegister")
	public String toRegister(Model model, HttpServletRequest request) {
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		JSONObject result=new JSONObject();
		try {
			if("ESK".equals(SysConfig.passCode)){
				return toRegisterESK(model, request);
			}
			String verifyCode=reqDataJson.getString("verifyCode");
			RegisterTmp registerTmp=(RegisterTmp) JSONObject.toBean(reqDataJson.getJSONObject("registerTmp"), RegisterTmp.class);
			
			//直接根据用户信息表进行校验
			MemberInfoExample memberInfoExample = new MemberInfoExample();
			memberInfoExample.or().andCertNbrEqualTo(registerTmp.getCertNbr()).andStatusNotEqualTo("1");
			int countMemberInfo = memberInfoService.countByExample(memberInfoExample);
			if(countMemberInfo >0){
				result.put("returnCode", "4004");
				result.put("returnMsg", "商户信息已存在，该身份证号码已注册");
				return result.toString();
			}
			memberInfoExample = new MemberInfoExample();
			memberInfoExample.or().andMobilePhoneEqualTo(registerTmp.getMobilePhone()).andStatusNotEqualTo("1");
			countMemberInfo = memberInfoService.countByExample(memberInfoExample);
			if(countMemberInfo > 0){
				result.put("returnCode", "4004");
				result.put("returnMsg", "商户信息已存在，该手机号码已注册");
				return result.toString();
			}
			//暂时屏蔽营业执照
//			memberInfoExample = new MemberInfoExample();
//			memberInfoExample.or().andBusLicenceNbrEqualTo(registerTmp.getBusLicenceNbr()).andStatusEqualTo("1");
//			countMemberInfo = memberInfoService.countByExample(memberInfoExample);
//			if(countMemberInfo > 0){
//				result.put("returnCode", "4004");
//				result.put("returnMsg", "对不起，该营业执照编号已注册商户信息");
//				return result.toString();
//			}
			
			VerifyCodeExample verifyCodeExample = new VerifyCodeExample();
			verifyCodeExample.setOrderByClause("create_date desc");
			VerifyCodeExample.Criteria createCriteriaVerify = verifyCodeExample.createCriteria();
			createCriteriaVerify.andMobilePhoneEqualTo(registerTmp.getMobilePhone());
			createCriteriaVerify.andTypeEqualTo("1");
			createCriteriaVerify.andStatusEqualTo("1");
			List<VerifyCode> verifyCodeList = verifyCodeService.selectByExample(verifyCodeExample);
			if(verifyCodeList.size()>0 && verifyCode.equals(verifyCodeList.get(0).getVerifyCode())){
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());
				calendar.add(Calendar.MINUTE, -30);
				if(verifyCodeList.get(0).getCreateDate().compareTo(calendar.getTime())<0){
					result.put("returnCode", "4004");
					result.put("returnMsg", "验证码已过期");
					return result.toString();
				}
				VerifyCode verifyCodeEntity = new VerifyCode();
				verifyCodeEntity.setId(verifyCodeList.get(0).getId());
				verifyCodeEntity.setStatus("0");
				verifyCodeService.updateByPrimaryKeySelective(verifyCodeEntity);
			}else{
				result.put("returnCode", "4004");
				result.put("returnMsg", "验证码无效");
				return result.toString();
			}
			
			registerTmp.setCode(SysConfig.prefixMemberCode+commonService.getNextSequenceVal(SequenseTypeConstant.MEMBERCODE));
			registerTmp.setLoginPass(SecurityUtil.md5Encode(registerTmp.getMobilePhone().substring(5, 11)));
			Date nowDate = new Date();
			registerTmp.setCreateDate(nowDate);
			registerTmp.setUpdateDate(nowDate);
			
//			BankSubExample bankSubExample = new BankSubExample();
//			bankSubExample.or().andBankIdEqualTo(Integer.parseInt(registerTmp.getBankId())).andSubIdEqualTo(registerTmp.getSubId());
//			List<BankSub> bankSubList = bankSubService.selectByExample(bankSubExample);
//			if(null != bankSubList && bankSubList.size()>0){
//				registerTmp.setBankOpen(bankSubList.get(0).getSubName());
//			}else{
//				result.put("returnCode", "4004");
//				result.put("returnMsg", "银行支行信息错误");
//				return result.toString();
//			}
//			//联行号
//			String bankType = bankSubList.get(0).getSubId();
//			String bankName = bankSubList.get(0).getSubName();
			KbinExample kbinExample = new KbinExample();
			kbinExample.or().andBankCodeEqualTo(registerTmp.getBankId());
			List<Kbin> bankList = kbinService.selectByExample(kbinExample);
			if(null != bankList && bankList.size()>0){
				registerTmp.setBankOpen(bankList.get(0).getBankName());
				boolean isKbin = false;
				for(Kbin kbin : bankList){
					if(registerTmp.getAccountNumber().startsWith(kbin.getKbin())){
						if(kbin.getLen().equals(registerTmp.getAccountNumber().length()+"")){
							isKbin = true;
							break;
						}
					}
				}
				if(!isKbin){
					result.put("returnCode", "4004");
					result.put("returnMsg", "银行卡号不在有效范围内,请更换银行卡!");
					return result.toString();
				}
			}else{
				result.put("returnCode", "4004");
				result.put("returnMsg", "开户银行错误");
				return result.toString();
			}
			
			if(reqDataJson.containsKey("agentType") && !"".equals(reqDataJson.getString("agentType"))){
				//agentType	1--瑞卡通，2--其他oem厂商
				String agentType = reqDataJson.getString("agentType");
				if(!"1".equals(agentType) && !"2".equals(agentType)){
					result.put("returnCode", "4004");
					result.put("returnMsg", "无效的请求");
					return result.toString();
				}
				if(!reqDataJson.containsKey("agentCode") || "".equals(reqDataJson.getString("agentCode"))){
					result.put("returnCode", "4004");
					result.put("returnMsg", "无效的机构编码");
					return result.toString();
				}
				String agentCode = reqDataJson.getString("agentCode");
				SysOfficeExample sysOfficeExample = new SysOfficeExample();
				sysOfficeExample.or().andCodeEqualTo(agentCode).andDelFlagEqualTo("0");
				List<SysOffice> sysOfficeList = sysOfficeService.selectByExample(sysOfficeExample);
				if(sysOfficeList.size() == 0){
					result.put("returnCode", "4004");
					result.put("returnMsg", "无效的机构编码");
					return result.toString();
				}
				//根据机构号直接从机构下获取易付码
//				EpayCodeExample payCodeExample = new EpayCodeExample();
//				payCodeExample.or().andOfficeIdEqualTo(sysOfficeList.get(0).getId()).andStatusEqualTo("8").andDelFlagEqualTo("0");
//				payCodeExample.setOrderByClause(" id asc ");
//				payCodeExample.setLimitStart(0);
//				payCodeExample.setLimitSize(1);
//				List<EpayCode> epayCodeEnableList = epayCodeService.selectByExample(payCodeExample);
//				if(epayCodeEnableList.size() == 0){
//					result.put("returnCode", "4004");
//					result.put("returnMsg", "库存二维码不足，请联系代理商！");
//					return result.toString();
//				}
				
				//更改获取epayCode方式，改由统一从指定的机构下获取再分配
				sysOfficeExample = new SysOfficeExample();
				sysOfficeExample.or().andTypeEqualTo("2").andDelFlagEqualTo("0");
				List<SysOffice> sysOfficeCommon = sysOfficeService.selectByExample(sysOfficeExample);
				if (sysOfficeCommon.size()==0) {
					result.put("returnCode", "4004");
					result.put("returnMsg", "系统配置错误，无法获取易付码");
					return result.toString();
				}
				EpayCodeExample payCodeExample = new EpayCodeExample();
				payCodeExample.or().andOfficeIdEqualTo(sysOfficeCommon.get(0).getId()).andStatusEqualTo("8").andDelFlagEqualTo("0");
				payCodeExample.setOrderByClause(" id asc ");
				payCodeExample.setLimitStart(0);
				payCodeExample.setLimitSize(1);
				List<EpayCode> epayCodeEnableList = epayCodeService.selectByExample(payCodeExample);
				if(epayCodeEnableList.size() == 0){
					result.put("returnCode", "4004");
					result.put("returnMsg", "库存二维码不足，请联系代理商！");
					return result.toString();
				}
				EpayCode epayCode = epayCodeEnableList.get(0);
				payCodeExample = new EpayCodeExample();
				EpayCode epayCodeUpdate = new EpayCode();
				epayCodeUpdate.setId(epayCode.getId());
				//暂用标志  -1  表示该epayCode暂时被暂用，降低并发时多用户使用同一code的风险
				epayCode.setStatus("-1");
				epayCode.setOfficeId(sysOfficeList.get(0).getId());
				epayCodeService.updateByPrimaryKeySelective(epayCode);
				registerTmp.setPayCode(epayCode.getPayCode());
				
			}else{
				String payCode = registerTmp.getPayCode();
				EpayCodeExample payCodeExample = new EpayCodeExample();
				payCodeExample.or().andPayCodeEqualTo(payCode).andDelFlagEqualTo("0");
				List<EpayCode> payCodeList = epayCodeService.selectByExample(payCodeExample);
				if(payCodeList.size() > 0){
					String payCodeStatus = payCodeList.get(0).getStatus();
					if(!"3".equals(payCodeStatus)){
						result.put("returnCode", "4004");
						result.put("returnMsg", "对不起,当前收款码无效,请与管理员联系");
						return result.toString();
					}
				}else {
					result.put("returnCode", "4004");
					result.put("returnMsg", "对不起,收款码不存在,请联系管理员获取正确收款码");
					return result.toString();
				}
			}
			String bankType = "";//D+0不送此参数
			String bankName = "";//bankList.get(0).getBankName();
			registerTmp.setWxMemberCode("w"+commonService.getNextSequenceVal(SequenseTypeConstant.WXMEMBERCODE));
			registerTmp.setZfbMemberCode("z"+commonService.getNextSequenceVal(SequenseTypeConstant.ZFBMEMBERCODE));
			registerTmp.setQqMemberCode("q"+commonService.getNextSequenceVal(SequenseTypeConstant.QQMEMBERCODE));
			registerTmp.setBdMemberCode("b"+commonService.getNextSequenceVal(SequenseTypeConstant.BDMEMBERCODE));
			registerTmp.setJdMemberCode("j"+commonService.getNextSequenceVal(SequenseTypeConstant.JDMEMBERCODE));

			if(registerTmp.getShortName() == null || "".equals(registerTmp.getShortName())){
				registerTmp.setShortName(registerTmp.getName());
			}
			
//			if("0".equals(registerTmp.getSettleType())){
//				Bank bankInfo = bankService.selectByPrimaryKey(Integer.parseInt(registerTmp.getBankId()));
//				if(null == bankInfo){
//					result.put("returnCode", "4004");
//					result.put("returnMsg", "所选银行信息错误");
//					return result.toString();
//				}
//				if(bankInfo.getKbinId() != null){
//					Kbin kbinInfo = kbinService.selectByPrimaryKey(bankInfo.getKbinId());
//					if(null == kbinInfo){
//						result.put("returnCode", "4004");
//						result.put("returnMsg", "所选结算方式不支持所选银行");
//						return result.toString();
//					}
//				}else{
//					result.put("returnCode", "4004");
//					result.put("returnMsg", "所选结算方式不支持所选银行");
//					return result.toString();
//				}
//			}
			
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
			getMerchantAddress(memberInfo);
			memberInfo.setMobilePhone(registerTmp.getMobilePhone());
			memberInfo.setCardPic1(registerTmp.getCardPic1());
			memberInfo.setCertPic1(registerTmp.getCertPic1());
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
//			memberInfo.setCategory(registerTmp.getCategory());
//			memberInfo.setBusLicenceNbr(registerTmp.getBusLicenceNbr());//营业执照编号
			memberInfo.setLevel("0");
			
			SysCommonConfigExample sysCommonConfigExample = new SysCommonConfigExample();
			sysCommonConfigExample.or().andNameEqualTo(SysCommonConfigConstant.SYS_SINGLE_LIMIT).andDelFlagEqualTo("0");
			List<SysCommonConfig> sysCommonConfig = sysCommonConfigService.selectByExample(sysCommonConfigExample);
			if (sysCommonConfig.size() == 0) {
				result.put("returnCode", "4004");
				result.put("returnMsg", "系统默认单笔交易限额配置缺失，请与管理员联系");
				return result.toString();
			}
			memberInfo.setSingleLimit(new BigDecimal(sysCommonConfig.get(0).getValue()));
			
			sysCommonConfigExample = new SysCommonConfigExample();
			sysCommonConfigExample.or().andNameEqualTo(SysCommonConfigConstant.SYS_DAY_LIMIT).andDelFlagEqualTo("0");
			sysCommonConfig = sysCommonConfigService.selectByExample(sysCommonConfigExample);
			if (sysCommonConfig.size() == 0) {
				result.put("returnCode", "4004");
				result.put("returnMsg", "系统默认日交易限额配置缺失，请与管理员联系");
				return result.toString();
			}
			memberInfo.setDayLimit(new BigDecimal(sysCommonConfig.get(0).getValue()));
			
			memberInfo.setDrawStatus("1"); //默认D+0提现状态关闭
			sysCommonConfigExample = new SysCommonConfigExample();
			sysCommonConfigExample.or().andNameEqualTo(SysCommonConfigConstant.SYS_DRAW_STATUS).andDelFlagEqualTo("0");
			sysCommonConfig = sysCommonConfigService.selectByExample(sysCommonConfigExample);
			if (sysCommonConfig.size() > 0) {
				memberInfo.setDrawStatus(sysCommonConfig.get(0).getValue());
			}
			
			AgentRate agentRate = null;
			boolean isDefaultRate = true;
			if (reqDataJson.containsKey("agentType") && !"".equals(reqDataJson.getString("agentType"))) {
				//机构注册  获取费率信息
				String agentCode = reqDataJson.getString("agentCode");
				SysOfficeExample sysOfficeExample = new SysOfficeExample();
				sysOfficeExample.or().andCodeEqualTo(agentCode).andDelFlagEqualTo("0");
				List<SysOffice> sysOfficeList = sysOfficeService.selectByExample(sysOfficeExample);
				
				AgentRateExample agentRateExample = new AgentRateExample();
				agentRateExample.or().andOfficeIdEqualTo(sysOfficeList.get(0).getId()).andDelFlagEqualTo("0");
				List<AgentRate> agentRateList = agentRateService.selectByExample(agentRateExample);
				if (agentRateList.size()>0) {
					isDefaultRate = false;
					agentRate = agentRateList.get(0);
					memberInfo.setT0DrawFee(agentRate.getmT0DrawFee());
					memberInfo.setT0TradeRate(agentRate.getmT0TradeRate());
					memberInfo.setT1DrawFee(agentRate.getmT1DrawFee());
					memberInfo.setT1TradeRate(agentRate.getmT1TradeRate());
					memberInfo.setMlJfFee(agentRate.getmBonusQuickFee());
					memberInfo.setMlJfRate(agentRate.getmBonusQuickRate());
					memberInfo.setMlWjfFee(agentRate.getmQuickFee());
					memberInfo.setMlWjfRate(agentRate.getmQuickRate());
				}
			}else{
				if(registerTmp.getPayCode() != null && !"".equals(registerTmp.getPayCode())){
					EpayCodeExample epayCodeExample = new EpayCodeExample();
					epayCodeExample.or().andPayCodeEqualTo(registerTmp.getPayCode());
					List<EpayCode> epayCodeList = epayCodeService.selectByExample(epayCodeExample);
					if(epayCodeList.size()>0){
						isDefaultRate = false;
						EpayCode epayCode = new EpayCode();
						epayCode.setId(epayCodeList.get(0).getId());
						memberInfo.setT0DrawFee(epayCodeList.get(0).getT0DrawFee());
						memberInfo.setT0TradeRate(epayCodeList.get(0).getT0TradeRate());
						memberInfo.setT1DrawFee(epayCodeList.get(0).getT1DrawFee());
						memberInfo.setT1TradeRate(epayCodeList.get(0).getT1TradeRate());
						memberInfo.setMlJfFee(epayCodeList.get(0).getMlJfFee());
						memberInfo.setMlJfRate(epayCodeList.get(0).getMlJfRate());
						memberInfo.setMlWjfFee(epayCodeList.get(0).getMlWjfFee());
						memberInfo.setMlWjfRate(epayCodeList.get(0).getMlWjfRate());
					}
				}
			}
			
			if (isDefaultRate){
				TransactionRateExample transactionRateExample = new TransactionRateExample();
				transactionRateExample.or().andTypeEqualTo("0").andDelFlagEqualTo("0");
				
				List<TransactionRate> transactionRateList = transactionRateService.selectByExample(transactionRateExample);
				if(transactionRateList.size() == 0){
					result.put("returnCode", "4004");
					result.put("returnMsg", "交易费率信息配置缺失");
					return result.toString();
				}
				//设置费率
				memberInfo.setT0DrawFee(transactionRateList.get(0).getT0DrawFee());
				memberInfo.setT0TradeRate(transactionRateList.get(0).getT0TradeRate());
				memberInfo.setT1DrawFee(transactionRateList.get(0).getT1DrawFee());
				memberInfo.setT1TradeRate(transactionRateList.get(0).getT1TradeRate());
				memberInfo.setMlJfFee(transactionRateList.get(0).getMlJfFee());
				memberInfo.setMlJfRate(transactionRateList.get(0).getMlJfRate());
				memberInfo.setMlWjfFee(transactionRateList.get(0).getMlWjfFee());
				memberInfo.setMlWjfRate(transactionRateList.get(0).getMlWjfRate());
			}
			
			//支付通道信息获取   --- 微信
			RouteWayExample routeWayExample = new RouteWayExample();
			routeWayExample.or().andTxnTypeEqualTo("1").andDelFlagEqualTo("0").andIsUsableEqualTo("0");
			List<RouteWay> routeWayList = routeWayService.selectByExample(routeWayExample);
			if(routeWayList.size() > 0){
				memberInfo.setWxRouteId(routeWayList.get(0).getRouteId());
			}else{
				result.put("returnCode", "4004");
				result.put("returnMsg", "微信交易类型配置信息缺失");
				return result.toString();
			}
			//支付通道信息获取   --- 支付宝
			routeWayExample = new RouteWayExample();
			routeWayExample.or().andTxnTypeEqualTo("2").andDelFlagEqualTo("0").andIsUsableEqualTo("0");
			routeWayList = routeWayService.selectByExample(routeWayExample);
			if(routeWayList.size() > 0){
				memberInfo.setZfbRouteId(routeWayList.get(0).getRouteId());
			}else{
				result.put("returnCode", "4004");
				result.put("returnMsg", "支付宝交易类型配置信息缺失");
				return result.toString();
			}
			
			//支付通道信息获取   --- QQ钱包
			routeWayExample = new RouteWayExample();
			routeWayExample.or().andTxnTypeEqualTo("3").andDelFlagEqualTo("0").andIsUsableEqualTo("0");
			routeWayList = routeWayService.selectByExample(routeWayExample);
			if(routeWayList.size() > 0){
				memberInfo.setQqRouteId(routeWayList.get(0).getRouteId());
			}else{
				result.put("returnCode", "4004");
				result.put("returnMsg", "QQ钱包交易类型配置信息缺失");
				return result.toString();
			}
			
			//支付通道信息获取   --- 百度钱包
			routeWayExample = new RouteWayExample();
			routeWayExample.or().andTxnTypeEqualTo("4").andDelFlagEqualTo("0").andIsUsableEqualTo("0");
			routeWayList = routeWayService.selectByExample(routeWayExample);
			if(routeWayList.size() > 0){
				memberInfo.setBdRouteId(routeWayList.get(0).getRouteId());
			}else{
				result.put("returnCode", "4004");
				result.put("returnMsg", "百度钱包交易类型配置信息缺失");
				return result.toString();
			}
			
			//支付通道信息获取   --- 京东金融
			routeWayExample = new RouteWayExample();
			routeWayExample.or().andTxnTypeEqualTo("5").andDelFlagEqualTo("0").andIsUsableEqualTo("0");
			routeWayList = routeWayService.selectByExample(routeWayExample);
			if(routeWayList.size() > 0){
				memberInfo.setJdRouteId(routeWayList.get(0).getRouteId());
			}else{
				result.put("returnCode", "4004");
				result.put("returnMsg", "京东金融交易类型配置信息缺失");
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
			
			String wxMerchantCode = null;
			String zfbMerchantCode = null;
			String qqMerchantCode = null;
			String bdMerchantCode = null;
			String jdMerchantCode = null;
			String channelMerchantCode=null;
			JSONObject wxPayAccount = this.registerMsAccount(MSPayWayConstant.WXPAY, memberInfo , businessCategory,bankType,bankName);
			if("0000".equals(wxPayAccount.getString("returnCode"))){
				wxMerchantCode = wxPayAccount.getString("merchantCode");
				channelMerchantCode=wxPayAccount.getString("channelMerchantCode");
				if(channelMerchantCode!=null && !channelMerchantCode.equals("")){
					memberInfo.setWxChannelMerchantCode(channelMerchantCode);
				}
				memberInfo.setWxStatus("1");//0审核中，1审核通过
			}else if("200012".equals(wxPayAccount.getString("returnCode"))){//审核中
				memberInfo.setWxStatus("0");//0审核中，1审核通过
			}else{
//				return wxPayAccount.toString();
				memberInfo.setWxStatus("4");//注册不成功
			}
			
			JSONObject zfbPayAccount = this.registerMsAccount(MSPayWayConstant.ZFBZF, memberInfo, businessCategory,bankType,bankName);
			if("0000".equals(zfbPayAccount.getString("returnCode"))){
				zfbMerchantCode = zfbPayAccount.getString("merchantCode");
				channelMerchantCode=zfbPayAccount.getString("channelMerchantCode");
				if(channelMerchantCode!=null && !channelMerchantCode.equals("")){
					memberInfo.setZfbChannelMerchantCode(channelMerchantCode);
				}
				memberInfo.setZfbStatus("1");
			}else if("200012".equals(zfbPayAccount.getString("returnCode"))){//审核中
				memberInfo.setZfbStatus("0");//0审核中，1审核通过
			}else{
//				return zfbPayAccount.toString();
				memberInfo.setZfbStatus("4");//0审核中，1审核通过
			}
			
			JSONObject qqPayAccount = this.registerMsAccount(MSPayWayConstant.QQZF, memberInfo, businessCategory,bankType,bankName);
			if("0000".equals(qqPayAccount.getString("returnCode"))){
				qqMerchantCode = qqPayAccount.getString("merchantCode");
				channelMerchantCode=qqPayAccount.getString("channelMerchantCode");
				if(channelMerchantCode!=null && !channelMerchantCode.equals("")){
					memberInfo.setQqChannelMerchantCode(channelMerchantCode);
				}
				
				memberInfo.setQqStatus("1");
			}else if("200012".equals(qqPayAccount.getString("returnCode"))){//审核中
				memberInfo.setQqStatus("0");//0审核中，1审核通过
			}else{
//				return qqPayAccount.toString();
				memberInfo.setQqStatus("4");//0审核中，1审核通过
			}
			JSONObject bdPayAccount = this.registerMsAccount(MSPayWayConstant.BDZF, memberInfo, businessCategory,bankType,bankName);
			if("0000".equals(bdPayAccount.getString("returnCode"))){
				bdMerchantCode = bdPayAccount.getString("merchantCode");
				channelMerchantCode=bdPayAccount.getString("channelMerchantCode");
				if(channelMerchantCode!=null && !channelMerchantCode.equals("")){
					memberInfo.setBdChannelMerchantCode(channelMerchantCode);
				}
				memberInfo.setBdStatus("1");
			}else if("200012".equals(bdPayAccount.getString("returnCode"))){//审核中
				memberInfo.setBdStatus("0");//0审核中，1审核通过
			}else{
//				return bdPayAccount.toString();
				memberInfo.setBdStatus("4");//0审核中，1审核通过
			}
			JSONObject jdPayAccount = this.registerMsAccount(MSPayWayConstant.JDZF, memberInfo, businessCategory,bankType,bankName);
			if("0000".equals(jdPayAccount.getString("returnCode"))){
				jdMerchantCode = jdPayAccount.getString("merchantCode");
				channelMerchantCode=jdPayAccount.getString("channelMerchantCode");
				if(channelMerchantCode!=null && !channelMerchantCode.equals("")){
					memberInfo.setJdChannelMerchantCode(channelMerchantCode);
				}
				memberInfo.setJdStatus("1");
			}else if("200012".equals(jdPayAccount.getString("returnCode"))){//审核中
				memberInfo.setJdStatus("0");//0审核中，1审核通过
			}else{
//				return jdPayAccount.toString();
				memberInfo.setJdStatus("4");//0审核中，1审核通过
			}
			/*
			if (null == wxMerchantCode){
				result.put("returnCode", "4004");
				result.put("returnMsg", "失败");
				return result.toString();
			}
			*/
			registerTmp.setCategory(businessCategory.getId()+"");
			registerTmpService.insertSelective(registerTmp);

			if("1".equals(memberInfo.getWxStatus()) || "1".equals(memberInfo.getZfbStatus()) 
					|| "1".equals(memberInfo.getQqStatus()) || "1".equals(memberInfo.getBdStatus())
					|| "1".equals(memberInfo.getJdStatus())){
//				memberInfo.setStatus("0");
				memberInfo.setStatus("4");   //默认注册成功后为未认证状态，不允许交易
			}
			memberInfo.setCategory(businessCategory.getId()+"");
			memberInfo.setWxMerchantCode(wxMerchantCode);
			memberInfo.setZfbMerchantCode(zfbMerchantCode);
			memberInfo.setQqMerchantCode(qqMerchantCode);
			memberInfo.setBdMerchantCode(bdMerchantCode);
			memberInfo.setJdMerchantCode(jdMerchantCode);
			//hkz 默认开通米联平台支付
			memberInfo.setMlJfStatus("1");
			memberInfo.setMlWjfStatus("1");
			memberInfoService.insertSelective(memberInfo);
			
			EpayCode epayCode = null;
			if(registerTmp.getPayCode() != null && !"".equals(registerTmp.getPayCode())){
				EpayCodeExample epayCodeExample = new EpayCodeExample();
				epayCodeExample.or().andPayCodeEqualTo(registerTmp.getPayCode());
				List<EpayCode> epayCodeList = epayCodeService.selectByExample(epayCodeExample);
				if(epayCodeList.size()>0){
					epayCode = epayCodeList.get(0);
					epayCode.setStatus("5");
					epayCode.setMemberId(memberInfo.getId());
					epayCode.setT0DrawFee(memberInfo.getT0DrawFee());
					epayCode.setT0TradeRate(memberInfo.getT0TradeRate());
					epayCode.setT1DrawFee(memberInfo.getT1DrawFee());
					epayCode.setT1TradeRate(memberInfo.getT1TradeRate());
					epayCode.setMlJfFee(memberInfo.getMlJfFee());
					epayCode.setMlJfRate(memberInfo.getMlJfRate());
					epayCode.setMlWjfFee(memberInfo.getMlWjfFee());
					epayCode.setMlWjfRate(memberInfo.getMlWjfRate());
					epayCodeService.updateByPrimaryKeySelective(epayCode);
				}
			}
			
			MemberBank memberBank = new MemberBank();
			memberBank.setMemberId(memberInfo.getId());
			memberBank.setBankId(registerTmp.getBankId());
			memberBank.setSubId(registerTmp.getSubId());
			memberBank.setProvince(registerTmp.getProvince());
			memberBank.setCity(registerTmp.getCity());
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
			//生成二维码
			if (reqDataJson.containsKey("agentType") && !"".equals(reqDataJson.getString("agentType")) && null != agentRate) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String defaultQrcodeBasePath = SysConfig.defaultQrcodeBasePath;
				String qrUrl = agentRate.getmUrl();
				/*
				if (null != qrUrl && !"".equals(qrUrl)) {
					if (qrUrl.contains("?")) {
						if (!qrUrl.endsWith("?")) {
							qrUrl = qrUrl + "&epayCode" + memberInfo.getPayCode();
						}else{
							qrUrl = qrUrl + "epayCode" + memberInfo.getPayCode();
						}
					}else {
						qrUrl = qrUrl + "?epayCode" + memberInfo.getPayCode();
					}
					
				}
				*/
				String logoPath = defaultQrcodeBasePath + File.separator + agentRate.getmImg();
				String epayCodePath = File.separator + sdf.format(new Date()) + File.separator + memberInfo.getPayCode() + ".png";
				String saveFilePath = defaultQrcodeBasePath + epayCodePath;
				String savePath = defaultQrcodeBasePath +File.separator+ sdf.format(new Date()) +File.separator;
				if (null != epayCode) {
					epayCode.setPath(epayCodePath);
					epayCodeService.updateByPrimaryKeySelective(epayCode);
				}
				String code = memberInfo.getPayCode();
				String mImg = agentRate.getmImg();
				QrcodeCreateThread qrcodeCreateThread = new QrcodeCreateThread(qrUrl, logoPath, saveFilePath, code ,defaultQrcodeBasePath, mImg, savePath);
				threadPoolTaskExecutor.execute(qrcodeCreateThread);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
			return result.toString();
		}
		result.put("returnCode", "0000");
		result.put("returnMsg", "成功");
		return result.toString();
	}
	
	
	
	public String toRegisterESK(Model model, HttpServletRequest request) {
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		JSONObject result=new JSONObject();
		try {
			String verifyCode=reqDataJson.getString("verifyCode");
			RegisterTmp registerTmp=(RegisterTmp) JSONObject.toBean(reqDataJson.getJSONObject("registerTmp"), RegisterTmp.class);
			
			//直接根据用户信息表进行校验
			MemberInfoExample memberInfoExample = new MemberInfoExample();
			memberInfoExample.or().andCertNbrEqualTo(registerTmp.getCertNbr()).andStatusNotEqualTo("1");
			int countMemberInfo = memberInfoService.countByExample(memberInfoExample);
			if(countMemberInfo >0){
				result.put("returnCode", "4004");
				result.put("returnMsg", "商户信息已存在，该身份证号码已注册");
				return result.toString();
			}
			memberInfoExample = new MemberInfoExample();
			memberInfoExample.or().andMobilePhoneEqualTo(registerTmp.getMobilePhone()).andStatusNotEqualTo("1");
			countMemberInfo = memberInfoService.countByExample(memberInfoExample);
			if(countMemberInfo > 0){
				result.put("returnCode", "4004");
				result.put("returnMsg", "商户信息已存在，该手机号码已注册");
				return result.toString();
			}
			//暂时屏蔽营业执照
//			memberInfoExample = new MemberInfoExample();
//			memberInfoExample.or().andBusLicenceNbrEqualTo(registerTmp.getBusLicenceNbr()).andStatusEqualTo("1");
//			countMemberInfo = memberInfoService.countByExample(memberInfoExample);
//			if(countMemberInfo > 0){
//				result.put("returnCode", "4004");
//				result.put("returnMsg", "对不起，该营业执照编号已注册商户信息");
//				return result.toString();
//			}
			
			VerifyCodeExample verifyCodeExample = new VerifyCodeExample();
			verifyCodeExample.setOrderByClause("create_date desc");
			VerifyCodeExample.Criteria createCriteriaVerify = verifyCodeExample.createCriteria();
			createCriteriaVerify.andMobilePhoneEqualTo(registerTmp.getMobilePhone());
			createCriteriaVerify.andTypeEqualTo("1");
			createCriteriaVerify.andStatusEqualTo("1");
			List<VerifyCode> verifyCodeList = verifyCodeService.selectByExample(verifyCodeExample);
			//by linxf 验证码验证屏蔽
			if(verifyCodeList.size()>0 && verifyCode.equals(verifyCodeList.get(0).getVerifyCode())){
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());
				calendar.add(Calendar.MINUTE, -30);
				if(verifyCodeList.get(0).getCreateDate().compareTo(calendar.getTime())<0){
					result.put("returnCode", "4004");
					result.put("returnMsg", "验证码已过期");
					return result.toString();
				}
				VerifyCode verifyCodeEntity = new VerifyCode();
				verifyCodeEntity.setId(verifyCodeList.get(0).getId());
				verifyCodeEntity.setStatus("0");
				verifyCodeService.updateByPrimaryKeySelective(verifyCodeEntity);
			}else{
				result.put("returnCode", "4004");
				result.put("returnMsg", "验证码无效");
				return result.toString();
			}
			
			registerTmp.setCode(SysConfig.prefixMemberCode+commonService.getNextSequenceVal(SequenseTypeConstant.MEMBERCODE));
			registerTmp.setLoginPass(SecurityUtil.md5Encode(registerTmp.getMobilePhone().substring(5, 11)));
			Date nowDate = new Date();
			registerTmp.setCreateDate(nowDate);
			registerTmp.setUpdateDate(nowDate);
			
//			BankSubExample bankSubExample = new BankSubExample();
//			bankSubExample.or().andBankIdEqualTo(Integer.parseInt(registerTmp.getBankId())).andSubIdEqualTo(registerTmp.getSubId());
//			List<BankSub> bankSubList = bankSubService.selectByExample(bankSubExample);
//			if(null != bankSubList && bankSubList.size()>0){
//				registerTmp.setBankOpen(bankSubList.get(0).getSubName());
//			}else{
//				result.put("returnCode", "4004");
//				result.put("returnMsg", "银行支行信息错误");
//				return result.toString();
//			}
//			//联行号
//			String bankType = bankSubList.get(0).getSubId();
//			String bankName = bankSubList.get(0).getSubName();
			//by linxf  没有选择银行 所以屏蔽
			KbinExample kbinExample = new KbinExample();
			kbinExample.or().andBankCodeEqualTo(registerTmp.getBankId());
			List<Kbin> bankList = kbinService.selectByExample(kbinExample);
			if(null != bankList && bankList.size()>0){
				registerTmp.setBankOpen(bankList.get(0).getBankName());
				boolean isKbin = false;
				for(Kbin kbin : bankList){
					if(registerTmp.getAccountNumber().startsWith(kbin.getKbin())){
						if(kbin.getLen().equals(registerTmp.getAccountNumber().length()+"")){
							isKbin = true;
							break;
						}
					}
				}
				if(!isKbin){
					result.put("returnCode", "4004");
					result.put("returnMsg", "银行卡号不在有效范围内,请更换银行卡!");
					return result.toString();
				}
			}else{
				result.put("returnCode", "4004");
				result.put("returnMsg", "开户银行错误");
				return result.toString();
			}
			
			if(reqDataJson.containsKey("agentType") && !"".equals(reqDataJson.getString("agentType"))){
				//agentType	1--瑞卡通，2--其他oem厂商
				String agentType = reqDataJson.getString("agentType");
				if(!"1".equals(agentType) && !"2".equals(agentType)){
					result.put("returnCode", "4004");
					result.put("returnMsg", "无效的请求");
					return result.toString();
				}
				if(!reqDataJson.containsKey("agentCode") || "".equals(reqDataJson.getString("agentCode"))){
					result.put("returnCode", "4004");
					result.put("returnMsg", "无效的机构编码");
					return result.toString();
				}
				String agentCode = reqDataJson.getString("agentCode");
				SysOfficeExample sysOfficeExample = new SysOfficeExample();
				sysOfficeExample.or().andCodeEqualTo(agentCode).andDelFlagEqualTo("0");
				List<SysOffice> sysOfficeList = sysOfficeService.selectByExample(sysOfficeExample);
				if(sysOfficeList.size() == 0){
					result.put("returnCode", "4004");
					result.put("returnMsg", "无效的机构编码");
					return result.toString();
				}
				//根据机构号直接从机构下获取易付码
//				EpayCodeExample payCodeExample = new EpayCodeExample();
//				payCodeExample.or().andOfficeIdEqualTo(sysOfficeList.get(0).getId()).andStatusEqualTo("8").andDelFlagEqualTo("0");
//				payCodeExample.setOrderByClause(" id asc ");
//				payCodeExample.setLimitStart(0);
//				payCodeExample.setLimitSize(1);
//				List<EpayCode> epayCodeEnableList = epayCodeService.selectByExample(payCodeExample);
//				if(epayCodeEnableList.size() == 0){
//					result.put("returnCode", "4004");
//					result.put("returnMsg", "库存二维码不足，请联系代理商！");
//					return result.toString();
//				}
				
				//更改获取epayCode方式，改由统一从指定的机构下获取再分配
				sysOfficeExample = new SysOfficeExample();
				sysOfficeExample.or().andTypeEqualTo("2").andDelFlagEqualTo("0");
				List<SysOffice> sysOfficeCommon = sysOfficeService.selectByExample(sysOfficeExample);
				if (sysOfficeCommon.size()==0) {
					result.put("returnCode", "4004");
					result.put("returnMsg", "系统配置错误，无法获取易付码");
					return result.toString();
				}
				EpayCodeExample payCodeExample = new EpayCodeExample();
				payCodeExample.or().andOfficeIdEqualTo(sysOfficeCommon.get(0).getId()).andStatusEqualTo("8").andDelFlagEqualTo("0");
				payCodeExample.setOrderByClause(" id asc ");
				payCodeExample.setLimitStart(0);
				payCodeExample.setLimitSize(1);
				List<EpayCode> epayCodeEnableList = epayCodeService.selectByExample(payCodeExample);
				if(epayCodeEnableList.size() == 0){
					result.put("returnCode", "4004");
					result.put("returnMsg", "库存二维码不足，请联系代理商！");
					return result.toString();
				}
				EpayCode epayCode = epayCodeEnableList.get(0);
				payCodeExample = new EpayCodeExample();
				EpayCode epayCodeUpdate = new EpayCode();
				epayCodeUpdate.setId(epayCode.getId());
				//暂用标志  -1  表示该epayCode暂时被暂用，降低并发时多用户使用同一code的风险
				epayCode.setStatus("-1");
				epayCode.setOfficeId(sysOfficeList.get(0).getId());
				epayCodeService.updateByPrimaryKeySelective(epayCode);
				registerTmp.setPayCode(epayCode.getPayCode());
				
			}else{
				String payCode = registerTmp.getPayCode();
				EpayCodeExample payCodeExample = new EpayCodeExample();
				payCodeExample.or().andPayCodeEqualTo(payCode).andDelFlagEqualTo("0");
				List<EpayCode> payCodeList = epayCodeService.selectByExample(payCodeExample);
				if(payCodeList.size() > 0){
					String payCodeStatus = payCodeList.get(0).getStatus();
					if(!"3".equals(payCodeStatus)){
						result.put("returnCode", "4004");
						result.put("returnMsg", "对不起,当前收款码无效,请与管理员联系");
						return result.toString();
					}
				}else {
					result.put("returnCode", "4004");
					result.put("returnMsg", "对不起,收款码不存在,请联系管理员获取正确收款码");
					return result.toString();
				}
			}
			String bankType = "";//D+0不送此参数
			String bankName = "";//bankList.get(0).getBankName();
			registerTmp.setWxMemberCode("w"+commonService.getNextSequenceVal(SequenseTypeConstant.WXMEMBERCODE));
			registerTmp.setZfbMemberCode("z"+commonService.getNextSequenceVal(SequenseTypeConstant.ZFBMEMBERCODE));
			registerTmp.setQqMemberCode("q"+commonService.getNextSequenceVal(SequenseTypeConstant.QQMEMBERCODE));
			registerTmp.setBdMemberCode("b"+commonService.getNextSequenceVal(SequenseTypeConstant.BDMEMBERCODE));
			registerTmp.setJdMemberCode("j"+commonService.getNextSequenceVal(SequenseTypeConstant.JDMEMBERCODE));

			if(registerTmp.getShortName() == null || "".equals(registerTmp.getShortName())){
				registerTmp.setShortName(registerTmp.getName());
			}
			
//			if("0".equals(registerTmp.getSettleType())){
//				Bank bankInfo = bankService.selectByPrimaryKey(Integer.parseInt(registerTmp.getBankId()));
//				if(null == bankInfo){
//					result.put("returnCode", "4004");
//					result.put("returnMsg", "所选银行信息错误");
//					return result.toString();
//				}
//				if(bankInfo.getKbinId() != null){
//					Kbin kbinInfo = kbinService.selectByPrimaryKey(bankInfo.getKbinId());
//					if(null == kbinInfo){
//						result.put("returnCode", "4004");
//						result.put("returnMsg", "所选结算方式不支持所选银行");
//						return result.toString();
//					}
//				}else{
//					result.put("returnCode", "4004");
//					result.put("returnMsg", "所选结算方式不支持所选银行");
//					return result.toString();
//				}
//			}
			
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
			getMerchantAddress(memberInfo);
			memberInfo.setMobilePhone(registerTmp.getMobilePhone());
			memberInfo.setCardPic1(registerTmp.getCardPic1());
			memberInfo.setCardPic2(registerTmp.getCardPic2());
			memberInfo.setCertPic1(registerTmp.getCertPic1());
			memberInfo.setCertPic2(registerTmp.getCertPic2());
			memberInfo.setBusPic(registerTmp.getMemcertPic());//营业执照照片
			memberInfo.setMemcertPic(registerTmp.getAuthPic());//授权证书
			//memberInfo.setAuthPic(registerTmp.getAuthPic());
			
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
//			memberInfo.setCategory(registerTmp.getCategory());
			memberInfo.setBusLicenceNbr(registerTmp.getBusLicenceNbr());//营业执照编号
			memberInfo.setEmail(registerTmp.getEmail());
			memberInfo.setLevel("0");
			
			SysCommonConfigExample sysCommonConfigExample = new SysCommonConfigExample();
			sysCommonConfigExample.or().andNameEqualTo(SysCommonConfigConstant.SYS_SINGLE_LIMIT).andDelFlagEqualTo("0");
			List<SysCommonConfig> sysCommonConfig = sysCommonConfigService.selectByExample(sysCommonConfigExample);
			if (sysCommonConfig.size() == 0) {
				result.put("returnCode", "4004");
				result.put("returnMsg", "系统默认单笔交易限额配置缺失，请与管理员联系");
				return result.toString();
			}
			memberInfo.setSingleLimit(new BigDecimal(sysCommonConfig.get(0).getValue()));
			
			sysCommonConfigExample = new SysCommonConfigExample();
			sysCommonConfigExample.or().andNameEqualTo(SysCommonConfigConstant.SYS_DAY_LIMIT).andDelFlagEqualTo("0");
			sysCommonConfig = sysCommonConfigService.selectByExample(sysCommonConfigExample);
			if (sysCommonConfig.size() == 0) {
				result.put("returnCode", "4004");
				result.put("returnMsg", "系统默认日交易限额配置缺失，请与管理员联系");
				return result.toString();
			}
			memberInfo.setDayLimit(new BigDecimal(sysCommonConfig.get(0).getValue()));
			
			memberInfo.setDrawStatus("1"); //默认D+0提现状态关闭
			sysCommonConfigExample = new SysCommonConfigExample();
			sysCommonConfigExample.or().andNameEqualTo(SysCommonConfigConstant.SYS_DRAW_STATUS).andDelFlagEqualTo("0");
			sysCommonConfig = sysCommonConfigService.selectByExample(sysCommonConfigExample);
			if (sysCommonConfig.size() > 0) {
				memberInfo.setDrawStatus(sysCommonConfig.get(0).getValue());
			}
			
			AgentRate agentRate = null;
			boolean isDefaultRate = true;
			if (reqDataJson.containsKey("agentType") && !"".equals(reqDataJson.getString("agentType"))) {
				//机构注册  获取费率信息
				String agentCode = reqDataJson.getString("agentCode");
				SysOfficeExample sysOfficeExample = new SysOfficeExample();
				sysOfficeExample.or().andCodeEqualTo(agentCode).andDelFlagEqualTo("0");
				List<SysOffice> sysOfficeList = sysOfficeService.selectByExample(sysOfficeExample);
				
				AgentRateExample agentRateExample = new AgentRateExample();
				agentRateExample.or().andOfficeIdEqualTo(sysOfficeList.get(0).getId()).andDelFlagEqualTo("0");
				List<AgentRate> agentRateList = agentRateService.selectByExample(agentRateExample);
				if (agentRateList.size()>0) {
					isDefaultRate = false;
					agentRate = agentRateList.get(0);
					memberInfo.setT0DrawFee(agentRate.getmT0DrawFee());
					memberInfo.setT0TradeRate(agentRate.getmT0TradeRate());
					memberInfo.setT1DrawFee(agentRate.getmT1DrawFee());
					memberInfo.setT1TradeRate(agentRate.getmT1TradeRate());
					memberInfo.setMlJfFee(agentRate.getmBonusQuickFee());
					memberInfo.setMlJfRate(agentRate.getmBonusQuickRate());
					memberInfo.setMlWjfFee(agentRate.getmQuickFee());
					memberInfo.setMlWjfRate(agentRate.getmQuickRate());
				}
			}else{
				if(registerTmp.getPayCode() != null && !"".equals(registerTmp.getPayCode())){
					EpayCodeExample epayCodeExample = new EpayCodeExample();
					epayCodeExample.or().andPayCodeEqualTo(registerTmp.getPayCode());
					List<EpayCode> epayCodeList = epayCodeService.selectByExample(epayCodeExample);
					if(epayCodeList.size()>0){
						isDefaultRate = false;
						EpayCode epayCode = new EpayCode();
						epayCode.setId(epayCodeList.get(0).getId());
						memberInfo.setT0DrawFee(epayCodeList.get(0).getT0DrawFee());
						memberInfo.setT0TradeRate(epayCodeList.get(0).getT0TradeRate());
						memberInfo.setT1DrawFee(epayCodeList.get(0).getT1DrawFee());
						memberInfo.setT1TradeRate(epayCodeList.get(0).getT1TradeRate());
						memberInfo.setMlJfFee(epayCodeList.get(0).getMlJfFee());
						memberInfo.setMlJfRate(epayCodeList.get(0).getMlJfRate());
						memberInfo.setMlWjfFee(epayCodeList.get(0).getMlWjfFee());
						memberInfo.setMlWjfRate(epayCodeList.get(0).getMlWjfRate());
					}
				}
			}
			
			if (isDefaultRate){
				TransactionRateExample transactionRateExample = new TransactionRateExample();
				transactionRateExample.or().andTypeEqualTo("0").andDelFlagEqualTo("0");
				
				List<TransactionRate> transactionRateList = transactionRateService.selectByExample(transactionRateExample);
				if(transactionRateList.size() == 0){
					result.put("returnCode", "4004");
					result.put("returnMsg", "交易费率信息配置缺失");
					return result.toString();
				}
				//设置费率
				memberInfo.setT0DrawFee(transactionRateList.get(0).getT0DrawFee());
				memberInfo.setT0TradeRate(transactionRateList.get(0).getT0TradeRate());
				memberInfo.setT1DrawFee(transactionRateList.get(0).getT1DrawFee());
				memberInfo.setT1TradeRate(transactionRateList.get(0).getT1TradeRate());
				memberInfo.setMlJfFee(transactionRateList.get(0).getMlJfFee());
				memberInfo.setMlJfRate(transactionRateList.get(0).getMlJfRate());
				memberInfo.setMlWjfFee(transactionRateList.get(0).getMlWjfFee());
				memberInfo.setMlWjfRate(transactionRateList.get(0).getMlWjfRate());
			}
			
			//支付通道信息获取   --- 微信
			RouteWayExample routeWayExample = new RouteWayExample();
			routeWayExample.or().andTxnTypeEqualTo("1").andDelFlagEqualTo("0").andIsUsableEqualTo("0");
			List<RouteWay> routeWayList = routeWayService.selectByExample(routeWayExample);
			if(routeWayList.size() > 0){
				memberInfo.setWxRouteId(routeWayList.get(0).getRouteId());
			}else{
				result.put("returnCode", "4004");
				result.put("returnMsg", "微信交易类型配置信息缺失");
				return result.toString();
			}
			//支付通道信息获取   --- 支付宝
			routeWayExample = new RouteWayExample();
			routeWayExample.or().andTxnTypeEqualTo("2").andDelFlagEqualTo("0").andIsUsableEqualTo("0");
			routeWayList = routeWayService.selectByExample(routeWayExample);
			if(routeWayList.size() > 0){
				memberInfo.setZfbRouteId(routeWayList.get(0).getRouteId());
			}else{
				result.put("returnCode", "4004");
				result.put("returnMsg", "支付宝交易类型配置信息缺失");
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
			
			JSONObject picObj = this.uploadCert(memberInfo);
			
			if(!"0000".equals(picObj.getString("returnCode"))){
				return picObj.toString();
			}
			
			String picOrderNo = picObj.getString("orderNumber");
			
			
			String wxMerchantCode = null;
			String zfbMerchantCode = null;
			String qqMerchantCode = null;
			String bdMerchantCode = null;
			String jdMerchantCode = null;
			String channelMerchantCode=null;
			JSONObject wxPayAccount = this.registerEskAccount(MSPayWayConstant.WXPAY, memberInfo , businessCategory,bankType,bankName,picOrderNo,registerTmp.getBankArea());
			if("0000".equals(wxPayAccount.getString("returnCode"))){
				//wxMerchantCode = wxPayAccount.getString("merchantCode");
			//	channelMerchantCode=wxPayAccount.getString("channelMerchantCode");
			//	if(channelMerchantCode!=null && !channelMerchantCode.equals("")){
				//	memberInfo.setWxChannelMerchantCode(channelMerchantCode);
				//}
				memberInfo.setOrderNo(wxPayAccount.getString("orderNumber"));
				memberInfo.setWxStatus("0");//0审核中，1审核通过
			}else{
				return wxPayAccount.toString();
		//		memberInfo.setWxStatus("4");//注册不成功
			}
			
			
			/*
			if (null == wxMerchantCode){
				result.put("returnCode", "4004");
				result.put("returnMsg", "失败");
				return result.toString();
			}
			*/
			registerTmp.setCategory(businessCategory.getId()+"");
			registerTmpService.insertSelective(registerTmp);

			if("1".equals(memberInfo.getWxStatus()) || "1".equals(memberInfo.getZfbStatus()) 
					|| "1".equals(memberInfo.getQqStatus()) || "1".equals(memberInfo.getBdStatus())
					|| "1".equals(memberInfo.getJdStatus())){
//				memberInfo.setStatus("0");
				memberInfo.setStatus("4");   //默认注册成功后为未认证状态，不允许交易
			}
			memberInfo.setCategory(businessCategory.getId()+"");
			memberInfo.setWxMerchantCode(wxMerchantCode);
			memberInfo.setZfbMerchantCode(zfbMerchantCode);
			memberInfo.setQqMerchantCode(qqMerchantCode);
			memberInfo.setBdMerchantCode(bdMerchantCode);
			memberInfo.setJdMerchantCode(jdMerchantCode);
			//hkz 默认开通米联平台支付
			memberInfo.setMlJfStatus("1");
			memberInfo.setMlWjfStatus("1");
			memberInfoService.insertSelective(memberInfo);
			
			EpayCode epayCode = null;
			if(registerTmp.getPayCode() != null && !"".equals(registerTmp.getPayCode())){
				EpayCodeExample epayCodeExample = new EpayCodeExample();
				epayCodeExample.or().andPayCodeEqualTo(registerTmp.getPayCode());
				List<EpayCode> epayCodeList = epayCodeService.selectByExample(epayCodeExample);
				if(epayCodeList.size()>0){
					epayCode = epayCodeList.get(0);
					epayCode.setStatus("5");
					epayCode.setMemberId(memberInfo.getId());
					epayCode.setT0DrawFee(memberInfo.getT0DrawFee());
					epayCode.setT0TradeRate(memberInfo.getT0TradeRate());
					epayCode.setT1DrawFee(memberInfo.getT1DrawFee());
					epayCode.setT1TradeRate(memberInfo.getT1TradeRate());
					epayCode.setMlJfFee(memberInfo.getMlJfFee());
					epayCode.setMlJfRate(memberInfo.getMlJfRate());
					epayCode.setMlWjfFee(memberInfo.getMlWjfFee());
					epayCode.setMlWjfRate(memberInfo.getMlWjfRate());
					epayCodeService.updateByPrimaryKeySelective(epayCode);
				}
			}
			
			MemberBank memberBank = new MemberBank();
			memberBank.setMemberId(memberInfo.getId());
			memberBank.setBankId(registerTmp.getBankId());
			memberBank.setSubId(registerTmp.getSubId());
			memberBank.setProvince(registerTmp.getProvince());
			memberBank.setCity(registerTmp.getCity());
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
			//生成二维码
			if (reqDataJson.containsKey("agentType") && !"".equals(reqDataJson.getString("agentType")) && null != agentRate) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String defaultQrcodeBasePath = SysConfig.defaultQrcodeBasePath;
				String qrUrl = agentRate.getmUrl();
				/*
				if (null != qrUrl && !"".equals(qrUrl)) {
					if (qrUrl.contains("?")) {
						if (!qrUrl.endsWith("?")) {
							qrUrl = qrUrl + "&epayCode" + memberInfo.getPayCode();
						}else{
							qrUrl = qrUrl + "epayCode" + memberInfo.getPayCode();
						}
					}else {
						qrUrl = qrUrl + "?epayCode" + memberInfo.getPayCode();
					}
					
				}
				*/
				String logoPath = defaultQrcodeBasePath + File.separator + agentRate.getmImg();
				String epayCodePath = File.separator + sdf.format(new Date()) + File.separator + memberInfo.getPayCode() + ".png";
				String saveFilePath = defaultQrcodeBasePath + epayCodePath;
				String savePath = defaultQrcodeBasePath +File.separator+ sdf.format(new Date()) +File.separator;
				if (null != epayCode) {
					epayCode.setPath(epayCodePath);
					epayCodeService.updateByPrimaryKeySelective(epayCode);
				}
				String code = memberInfo.getPayCode();
				String mImg = agentRate.getmImg();
				QrcodeCreateThread qrcodeCreateThread = new QrcodeCreateThread(qrUrl, logoPath, saveFilePath, code ,defaultQrcodeBasePath, mImg, savePath);
				threadPoolTaskExecutor.execute(qrcodeCreateThread);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
			return result.toString();
		}
		result.put("returnCode", "0000");
		result.put("returnMsg", "成功");
		return result.toString();
	}
	
	
	
	public JSONObject uploadCert(MemberInfo member) {
		
		JSONObject result=new JSONObject();
		try {
			String serverUrl = ESKConfig.shopServerUrl;
			
			String baseFilePath = SysConfig.baseUploadFilePath; 
			
		//	PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKey();
			String tranCode = "010";
			String charset = "utf-8";
			
			String orderCode = CommonUtil.getOrderCode();
			JSONObject reqData = new JSONObject();
			reqData.put("orderNumber", orderCode);
			reqData.put("tranCode", tranCode);
			
			FileUtil fileUtil = new FileUtil();

			String cert_correct_path = member.getCertPic1();
			File cert_correct_file=new File((baseFilePath+cert_correct_path).replace('/', File.separatorChar));
			if(!cert_correct_file.exists()){
				result.put("returnCode", "0007");
				result.put("returnMsg", "身份证正面图片不存在");
				return result;
			}
			reqData.put("cert_correct", fileUtil.ConvertFileToBase64(baseFilePath+cert_correct_path));
			
			String cert_opposite_path = member.getCertPic2();
			File cert_opposite_file=new File((baseFilePath+cert_opposite_path).replace('/', File.separatorChar));
			if(!cert_opposite_file.exists()){
				result.put("returnCode", "0007");
				result.put("returnMsg", "身份证背面图片不存在");
				return result;
			}
			reqData.put("cert_opposite", fileUtil.ConvertFileToBase64(baseFilePath+cert_opposite_path));
			
			String bl_img_path = member.getBusPic();
			File bl_img_file=new File((baseFilePath+bl_img_path).replace('/', File.separatorChar));
			if(!bl_img_file.exists()){
				result.put("returnCode", "0007");
				result.put("returnMsg", "营业执照图片不存在");
				return result;
			}
			reqData.put("bl_img", fileUtil.ConvertFileToBase64(baseFilePath+bl_img_path));
			
			String card_correct_path = member.getCardPic1();
			File card_correct_file=new File((baseFilePath+card_correct_path).replace('/', File.separatorChar));
			if(!card_correct_file.exists()){
				result.put("returnCode", "0007");
				result.put("returnMsg", "银行卡正面图片不存在");
				return result;
			}
			reqData.put("card_correct", fileUtil.ConvertFileToBase64(baseFilePath+card_correct_path));
			
			String card_opposite_path = member.getCardPic2();
			File card_opposite_file=new File((baseFilePath+card_opposite_path).replace('/', File.separatorChar));
			if(!card_opposite_file.exists()){
				result.put("returnCode", "0007");
				result.put("returnMsg", "银行卡背面图片不存在");
				return result;
			}
			reqData.put("card_opposite", fileUtil.ConvertFileToBase64(baseFilePath+card_opposite_path));
			
			if(!"01".equals(member.getContactType())){
				String auth_path = member.getMemcertPic();
				File auth_file=new File((baseFilePath + auth_path).replace('/', File.separatorChar));
				if(!auth_file.exists()){
					result.put("returnCode", "0007");
					result.put("returnMsg", "授权书图片不存在");
					return result;
				}
				reqData.put("authorization", fileUtil.ConvertFileToBase64(baseFilePath + auth_path));
			}
			
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
			
			if("000000".equals(respJSONObject.getString("respCode"))&&"S".equals(respJSONObject.getString("respType"))){
				result.put("returnCode", "0000");
				result.put("returnMsg", "成功");
				result.put("orderNumber", respJSONObject.getString("orderNumber") );
			}else{
				result.put("returnCode", "0001");
				result.put("returnMsg", "照片上传失败["+respJSONObject.getString("respMsg")+"]");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
			return result;
		}
		return result;
	}
	
	
	
	
	
	
	
	
	
	@ResponseBody
	@RequestMapping("/toHandleRegister")
	public String toHandleRegister(Model model, HttpServletRequest request) {
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		JSONObject result=new JSONObject();
		try {
			RegisterTmp registerTmp=(RegisterTmp) JSONObject.toBean(reqDataJson.getJSONObject("registerTmp"), RegisterTmp.class);
			
			//直接根据用户信息表进行校验
			MemberInfoExample memberInfoExample = new MemberInfoExample();
			memberInfoExample.or().andCertNbrEqualTo(registerTmp.getCertNbr()).andStatusNotEqualTo("1");
			int countMemberInfo = memberInfoService.countByExample(memberInfoExample);
			if(countMemberInfo >0){
				result.put("returnCode", "4004");
				result.put("returnMsg", "商户信息已存在，该身份证号码已注册");
				return result.toString();
			}
			memberInfoExample = new MemberInfoExample();
			memberInfoExample.or().andMobilePhoneEqualTo(registerTmp.getMobilePhone()).andStatusNotEqualTo("1");
			countMemberInfo = memberInfoService.countByExample(memberInfoExample);
			if(countMemberInfo > 0){
				result.put("returnCode", "4004");
				result.put("returnMsg", "商户信息已存在，该手机号码已注册");
				return result.toString();
			}
			
			registerTmp.setCode(SysConfig.prefixMemberCode+commonService.getNextSequenceVal(SequenseTypeConstant.MEMBERCODE));
			registerTmp.setLoginPass(SecurityUtil.md5Encode(registerTmp.getMobilePhone().substring(5, 11)));
			Date nowDate = new Date();
			registerTmp.setCreateDate(nowDate);
			registerTmp.setUpdateDate(nowDate);
			
			KbinExample kbinExample = new KbinExample();
			kbinExample.or().andBankCodeEqualTo(registerTmp.getBankId());
			List<Kbin> bankList = kbinService.selectByExample(kbinExample);
			if(null != bankList && bankList.size()>0){
				registerTmp.setBankOpen(bankList.get(0).getBankName());
				boolean isKbin = false;
				for(Kbin kbin : bankList){
					if(registerTmp.getAccountNumber().startsWith(kbin.getKbin())){
						if(kbin.getLen().equals(registerTmp.getAccountNumber().length()+"")){
							isKbin = true;
							break;
						}
					}
				}
				if(!isKbin){
					result.put("returnCode", "4004");
					result.put("returnMsg", "银行卡号不在有效范围内,请更换银行卡!");
					return result.toString();
				}
			}else{
				result.put("returnCode", "4004");
				result.put("returnMsg", "开户银行错误");
				return result.toString();
			}
			
			if(reqDataJson.containsKey("agentType") && !"".equals(reqDataJson.getString("agentType"))){
				//agentType	1--瑞卡通，2--其他oem厂商
				String agentType = reqDataJson.getString("agentType");
				if(!"1".equals(agentType) && !"2".equals(agentType)){
					result.put("returnCode", "4004");
					result.put("returnMsg", "无效的请求");
					return result.toString();
				}
				if(!reqDataJson.containsKey("agentCode") || "".equals(reqDataJson.getString("agentCode"))){
					result.put("returnCode", "4004");
					result.put("returnMsg", "无效的机构编码");
					return result.toString();
				}
				String agentCode = reqDataJson.getString("agentCode");
				SysOfficeExample sysOfficeExample = new SysOfficeExample();
				sysOfficeExample.or().andCodeEqualTo(agentCode).andDelFlagEqualTo("0");
				List<SysOffice> sysOfficeList = sysOfficeService.selectByExample(sysOfficeExample);
				if(sysOfficeList.size() == 0){
					result.put("returnCode", "4004");
					result.put("returnMsg", "无效的机构编码");
					return result.toString();
				}
				//根据机构号直接从机构下获取易付码
//				EpayCodeExample payCodeExample = new EpayCodeExample();
//				payCodeExample.or().andOfficeIdEqualTo(sysOfficeList.get(0).getId()).andStatusEqualTo("8").andDelFlagEqualTo("0");
//				payCodeExample.setOrderByClause(" id asc ");
//				payCodeExample.setLimitStart(0);
//				payCodeExample.setLimitSize(1);
//				List<EpayCode> epayCodeEnableList = epayCodeService.selectByExample(payCodeExample);
//				if(epayCodeEnableList.size() == 0){
//					result.put("returnCode", "4004");
//					result.put("returnMsg", "库存二维码不足，请联系代理商！");
//					return result.toString();
//				}
				
				//更改获取epayCode方式，改由统一从指定的机构下获取再分配
				sysOfficeExample = new SysOfficeExample();
				sysOfficeExample.or().andTypeEqualTo("2").andDelFlagEqualTo("0");
				List<SysOffice> sysOfficeCommon = sysOfficeService.selectByExample(sysOfficeExample);
				if (sysOfficeCommon.size()==0) {
					result.put("returnCode", "4004");
					result.put("returnMsg", "系统配置错误，无法获取易付码");
					return result.toString();
				}
				EpayCodeExample payCodeExample = new EpayCodeExample();
				payCodeExample.or().andOfficeIdEqualTo(sysOfficeCommon.get(0).getId()).andStatusEqualTo("8").andDelFlagEqualTo("0");
				payCodeExample.setOrderByClause(" id asc ");
				payCodeExample.setLimitStart(0);
				payCodeExample.setLimitSize(1);
				List<EpayCode> epayCodeEnableList = epayCodeService.selectByExample(payCodeExample);
				if(epayCodeEnableList.size() == 0){
					result.put("returnCode", "4004");
					result.put("returnMsg", "库存二维码不足，请联系代理商！");
					return result.toString();
				}
				EpayCode epayCode = epayCodeEnableList.get(0);
				payCodeExample = new EpayCodeExample();
				EpayCode epayCodeUpdate = new EpayCode();
				epayCodeUpdate.setId(epayCode.getId());
				//暂用标志  -1  表示该epayCode暂时被暂用，降低并发时多用户使用同一code的风险
				epayCode.setStatus("-1");
				epayCode.setOfficeId(sysOfficeList.get(0).getId());
				epayCodeService.updateByPrimaryKeySelective(epayCode);
				registerTmp.setPayCode(epayCode.getPayCode());
				
			}else{
				String payCode = registerTmp.getPayCode();
				EpayCodeExample payCodeExample = new EpayCodeExample();
				payCodeExample.or().andPayCodeEqualTo(payCode).andDelFlagEqualTo("0");
				List<EpayCode> payCodeList = epayCodeService.selectByExample(payCodeExample);
				if(payCodeList.size() > 0){
					String payCodeStatus = payCodeList.get(0).getStatus();
					if(!"3".equals(payCodeStatus)){
						result.put("returnCode", "4004");
						result.put("returnMsg", "对不起,当前收款码无效,请与管理员联系");
						return result.toString();
					}
				}else {
					result.put("returnCode", "4004");
					result.put("returnMsg", "对不起,收款码不存在,请联系管理员获取正确收款码");
					return result.toString();
				}
			}
			String bankType = "";//D+0不送此参数
			String bankName = "";//bankList.get(0).getBankName();
			registerTmp.setWxMemberCode("w"+commonService.getNextSequenceVal(SequenseTypeConstant.WXMEMBERCODE));
			registerTmp.setZfbMemberCode("z"+commonService.getNextSequenceVal(SequenseTypeConstant.ZFBMEMBERCODE));
			registerTmp.setQqMemberCode("q"+commonService.getNextSequenceVal(SequenseTypeConstant.QQMEMBERCODE));
			registerTmp.setBdMemberCode("b"+commonService.getNextSequenceVal(SequenseTypeConstant.BDMEMBERCODE));
			registerTmp.setJdMemberCode("j"+commonService.getNextSequenceVal(SequenseTypeConstant.JDMEMBERCODE));
			
			if(registerTmp.getShortName() == null || "".equals(registerTmp.getShortName())){
				registerTmp.setShortName(registerTmp.getName());
			}
			
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
			getMerchantAddress(memberInfo);
			memberInfo.setMobilePhone(registerTmp.getMobilePhone());
			memberInfo.setCardPic1(registerTmp.getCardPic1());
			memberInfo.setCertPic1(registerTmp.getCertPic1());
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
//			memberInfo.setCategory(registerTmp.getCategory());
//			memberInfo.setBusLicenceNbr(registerTmp.getBusLicenceNbr());//营业执照编号
			memberInfo.setLevel("0");
			
			SysCommonConfigExample sysCommonConfigExample = new SysCommonConfigExample();
			sysCommonConfigExample.or().andNameEqualTo(SysCommonConfigConstant.SYS_SINGLE_LIMIT).andDelFlagEqualTo("0");
			List<SysCommonConfig> sysCommonConfig = sysCommonConfigService.selectByExample(sysCommonConfigExample);
			if (sysCommonConfig.size() == 0) {
				result.put("returnCode", "4004");
				result.put("returnMsg", "系统默认单笔交易限额配置缺失，请与管理员联系");
				return result.toString();
			}
			memberInfo.setSingleLimit(new BigDecimal(sysCommonConfig.get(0).getValue()));
			
			sysCommonConfigExample = new SysCommonConfigExample();
			sysCommonConfigExample.or().andNameEqualTo(SysCommonConfigConstant.SYS_DAY_LIMIT).andDelFlagEqualTo("0");
			sysCommonConfig = sysCommonConfigService.selectByExample(sysCommonConfigExample);
			if (sysCommonConfig.size() == 0) {
				result.put("returnCode", "4004");
				result.put("returnMsg", "系统默认日交易限额配置缺失，请与管理员联系");
				return result.toString();
			}
			memberInfo.setDayLimit(new BigDecimal(sysCommonConfig.get(0).getValue()));
			
			memberInfo.setDrawStatus("1"); //默认D+0提现状态关闭
			sysCommonConfigExample = new SysCommonConfigExample();
			sysCommonConfigExample.or().andNameEqualTo(SysCommonConfigConstant.SYS_DRAW_STATUS).andDelFlagEqualTo("0");
			sysCommonConfig = sysCommonConfigService.selectByExample(sysCommonConfigExample);
			if (sysCommonConfig.size() > 0) {
				memberInfo.setDrawStatus(sysCommonConfig.get(0).getValue());
			}
			
			AgentRate agentRate = null;
			boolean isDefaultRate = true;
			if (reqDataJson.containsKey("agentType") && !"".equals(reqDataJson.getString("agentType"))) {
				//机构注册  获取费率信息
				String agentCode = reqDataJson.getString("agentCode");
				SysOfficeExample sysOfficeExample = new SysOfficeExample();
				sysOfficeExample.or().andCodeEqualTo(agentCode).andDelFlagEqualTo("0");
				List<SysOffice> sysOfficeList = sysOfficeService.selectByExample(sysOfficeExample);
				
				AgentRateExample agentRateExample = new AgentRateExample();
				agentRateExample.or().andOfficeIdEqualTo(sysOfficeList.get(0).getId()).andDelFlagEqualTo("0");
				List<AgentRate> agentRateList = agentRateService.selectByExample(agentRateExample);
				if (agentRateList.size()>0) {
					isDefaultRate = false;
					agentRate = agentRateList.get(0);
					memberInfo.setT0DrawFee(agentRate.getmT0DrawFee());
					memberInfo.setT0TradeRate(agentRate.getmT0TradeRate());
					memberInfo.setT1DrawFee(agentRate.getmT1DrawFee());
					memberInfo.setT1TradeRate(agentRate.getmT1TradeRate());
					memberInfo.setMlJfFee(agentRate.getmBonusQuickFee());
					memberInfo.setMlJfRate(agentRate.getmBonusQuickRate());
					memberInfo.setMlWjfFee(agentRate.getmQuickFee());
					memberInfo.setMlWjfRate(agentRate.getmQuickRate());
				}
			}else{
				if(registerTmp.getPayCode() != null && !"".equals(registerTmp.getPayCode())){
					EpayCodeExample epayCodeExample = new EpayCodeExample();
					epayCodeExample.or().andPayCodeEqualTo(registerTmp.getPayCode());
					List<EpayCode> epayCodeList = epayCodeService.selectByExample(epayCodeExample);
					if(epayCodeList.size()>0){
						isDefaultRate = false;
						EpayCode epayCode = new EpayCode();
						epayCode.setId(epayCodeList.get(0).getId());
						memberInfo.setT0DrawFee(epayCodeList.get(0).getT0DrawFee());
						memberInfo.setT0TradeRate(epayCodeList.get(0).getT0TradeRate());
						memberInfo.setT1DrawFee(epayCodeList.get(0).getT1DrawFee());
						memberInfo.setT1TradeRate(epayCodeList.get(0).getT1TradeRate());
						memberInfo.setMlJfFee(epayCodeList.get(0).getMlJfFee());
						memberInfo.setMlJfRate(epayCodeList.get(0).getMlJfRate());
						memberInfo.setMlWjfFee(epayCodeList.get(0).getMlWjfFee());
						memberInfo.setMlWjfRate(epayCodeList.get(0).getMlWjfRate());
					}
				}
			}
			
			if (isDefaultRate){
				TransactionRateExample transactionRateExample = new TransactionRateExample();
				transactionRateExample.or().andTypeEqualTo("0").andDelFlagEqualTo("0");
				
				List<TransactionRate> transactionRateList = transactionRateService.selectByExample(transactionRateExample);
				if(transactionRateList.size() == 0){
					result.put("returnCode", "4004");
					result.put("returnMsg", "交易费率信息配置缺失");
					return result.toString();
				}
				//设置费率
				memberInfo.setT0DrawFee(transactionRateList.get(0).getT0DrawFee());
				memberInfo.setT0TradeRate(transactionRateList.get(0).getT0TradeRate());
				memberInfo.setT1DrawFee(transactionRateList.get(0).getT1DrawFee());
				memberInfo.setT1TradeRate(transactionRateList.get(0).getT1TradeRate());
				memberInfo.setMlJfFee(transactionRateList.get(0).getMlJfFee());
				memberInfo.setMlJfRate(transactionRateList.get(0).getMlJfRate());
				memberInfo.setMlWjfFee(transactionRateList.get(0).getMlWjfFee());
				memberInfo.setMlWjfRate(transactionRateList.get(0).getMlWjfRate());
			}
			
			//支付通道信息获取   --- 微信
			RouteWayExample routeWayExample = new RouteWayExample();
			routeWayExample.or().andTxnTypeEqualTo("1").andDelFlagEqualTo("0").andIsUsableEqualTo("0");
			List<RouteWay> routeWayList = routeWayService.selectByExample(routeWayExample);
			if(routeWayList.size() > 0){
				memberInfo.setWxRouteId(routeWayList.get(0).getRouteId());
			}else{
				result.put("returnCode", "4004");
				result.put("returnMsg", "微信交易类型配置信息缺失");
				return result.toString();
			}
			//支付通道信息获取   --- 支付宝
			routeWayExample = new RouteWayExample();
			routeWayExample.or().andTxnTypeEqualTo("2").andDelFlagEqualTo("0").andIsUsableEqualTo("0");
			routeWayList = routeWayService.selectByExample(routeWayExample);
			if(routeWayList.size() > 0){
				memberInfo.setZfbRouteId(routeWayList.get(0).getRouteId());
			}else{
				result.put("returnCode", "4004");
				result.put("returnMsg", "支付宝交易类型配置信息缺失");
				return result.toString();
			}
			
			//支付通道信息获取   --- QQ钱包
			routeWayExample = new RouteWayExample();
			routeWayExample.or().andTxnTypeEqualTo("3").andDelFlagEqualTo("0").andIsUsableEqualTo("0");
			routeWayList = routeWayService.selectByExample(routeWayExample);
			if(routeWayList.size() > 0){
				memberInfo.setQqRouteId(routeWayList.get(0).getRouteId());
			}else{
				result.put("returnCode", "4004");
				result.put("returnMsg", "QQ钱包交易类型配置信息缺失");
				return result.toString();
			}
			
			//支付通道信息获取   --- 百度钱包
			routeWayExample = new RouteWayExample();
			routeWayExample.or().andTxnTypeEqualTo("4").andDelFlagEqualTo("0").andIsUsableEqualTo("0");
			routeWayList = routeWayService.selectByExample(routeWayExample);
			if(routeWayList.size() > 0){
				memberInfo.setBdRouteId(routeWayList.get(0).getRouteId());
			}else{
				result.put("returnCode", "4004");
				result.put("returnMsg", "百度钱包交易类型配置信息缺失");
				return result.toString();
			}
			
			//支付通道信息获取   --- 京东金融
			routeWayExample = new RouteWayExample();
			routeWayExample.or().andTxnTypeEqualTo("5").andDelFlagEqualTo("0").andIsUsableEqualTo("0");
			routeWayList = routeWayService.selectByExample(routeWayExample);
			if(routeWayList.size() > 0){
				memberInfo.setJdRouteId(routeWayList.get(0).getRouteId());
			}else{
				result.put("returnCode", "4004");
				result.put("returnMsg", "京东金融交易类型配置信息缺失");
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
			
			String wxMerchantCode = null;
			String zfbMerchantCode = null;
			String qqMerchantCode = null;
			String bdMerchantCode = null;
			String jdMerchantCode = null;
			String channelMerchantCode=null;
			JSONObject wxPayAccount = this.registerMsAccount(MSPayWayConstant.WXPAY, memberInfo , businessCategory,bankType,bankName);
			if("0000".equals(wxPayAccount.getString("returnCode"))){
				wxMerchantCode = wxPayAccount.getString("merchantCode");
				channelMerchantCode=wxPayAccount.getString("channelMerchantCode");
				if(channelMerchantCode!=null && !channelMerchantCode.equals("")){
					memberInfo.setWxChannelMerchantCode(channelMerchantCode);
				}
				memberInfo.setWxStatus("1");//0审核中，1审核通过
			}else if("200012".equals(wxPayAccount.getString("returnCode"))){//审核中
				memberInfo.setWxStatus("0");//0审核中，1审核通过
			}else{
//				return wxPayAccount.toString();
				memberInfo.setWxStatus("4");//注册不成功
			}
			
			JSONObject zfbPayAccount = this.registerMsAccount(MSPayWayConstant.ZFBZF, memberInfo, businessCategory,bankType,bankName);
			if("0000".equals(zfbPayAccount.getString("returnCode"))){
				zfbMerchantCode = zfbPayAccount.getString("merchantCode");
				channelMerchantCode=zfbPayAccount.getString("channelMerchantCode");
				if(channelMerchantCode!=null && !channelMerchantCode.equals("")){
					memberInfo.setZfbChannelMerchantCode(channelMerchantCode);
				}
				memberInfo.setZfbStatus("1");
			}else if("200012".equals(zfbPayAccount.getString("returnCode"))){//审核中
				memberInfo.setZfbStatus("0");//0审核中，1审核通过
			}else{
//				return zfbPayAccount.toString();
				memberInfo.setZfbStatus("4");//0审核中，1审核通过
			}
			
			JSONObject qqPayAccount = this.registerMsAccount(MSPayWayConstant.QQZF, memberInfo, businessCategory,bankType,bankName);
			if("0000".equals(qqPayAccount.getString("returnCode"))){
				qqMerchantCode = qqPayAccount.getString("merchantCode");
				channelMerchantCode=qqPayAccount.getString("channelMerchantCode");
				if(channelMerchantCode!=null && !channelMerchantCode.equals("")){
					memberInfo.setQqChannelMerchantCode(channelMerchantCode);
				}
				
				memberInfo.setQqStatus("1");
			}else if("200012".equals(qqPayAccount.getString("returnCode"))){//审核中
				memberInfo.setQqStatus("0");//0审核中，1审核通过
			}else{
//				return qqPayAccount.toString();
				memberInfo.setQqStatus("4");//0审核中，1审核通过
			}
			JSONObject bdPayAccount = this.registerMsAccount(MSPayWayConstant.BDZF, memberInfo, businessCategory,bankType,bankName);
			if("0000".equals(bdPayAccount.getString("returnCode"))){
				bdMerchantCode = bdPayAccount.getString("merchantCode");
				channelMerchantCode=bdPayAccount.getString("channelMerchantCode");
				if(channelMerchantCode!=null && !channelMerchantCode.equals("")){
					memberInfo.setBdChannelMerchantCode(channelMerchantCode);
				}
				memberInfo.setBdStatus("1");
			}else if("200012".equals(bdPayAccount.getString("returnCode"))){//审核中
				memberInfo.setBdStatus("0");//0审核中，1审核通过
			}else{
//				return bdPayAccount.toString();
				memberInfo.setBdStatus("4");//0审核中，1审核通过
			}
			JSONObject jdPayAccount = this.registerMsAccount(MSPayWayConstant.JDZF, memberInfo, businessCategory,bankType,bankName);
			if("0000".equals(jdPayAccount.getString("returnCode"))){
				jdMerchantCode = jdPayAccount.getString("merchantCode");
				channelMerchantCode=jdPayAccount.getString("channelMerchantCode");
				if(channelMerchantCode!=null && !channelMerchantCode.equals("")){
					memberInfo.setJdChannelMerchantCode(channelMerchantCode);
				}
				memberInfo.setJdStatus("1");
			}else if("200012".equals(jdPayAccount.getString("returnCode"))){//审核中
				memberInfo.setJdStatus("0");//0审核中，1审核通过
			}else{
//				return jdPayAccount.toString();
				memberInfo.setJdStatus("4");//0审核中，1审核通过
			}
			/*
			if (null == wxMerchantCode){
				result.put("returnCode", "4004");
				result.put("returnMsg", "失败");
				return result.toString();
			}
			 */
			registerTmp.setCategory(businessCategory.getId()+"");
			registerTmpService.insertSelective(registerTmp);
			
			if("1".equals(memberInfo.getWxStatus()) || "1".equals(memberInfo.getZfbStatus()) 
					|| "1".equals(memberInfo.getQqStatus()) || "1".equals(memberInfo.getBdStatus())
					|| "1".equals(memberInfo.getJdStatus())){
//				memberInfo.setStatus("0");
				memberInfo.setStatus("4");   //默认注册成功后为未认证状态，不允许交易
			}
			memberInfo.setCategory(businessCategory.getId()+"");
			memberInfo.setWxMerchantCode(wxMerchantCode);
			memberInfo.setZfbMerchantCode(zfbMerchantCode);
			memberInfo.setQqMerchantCode(qqMerchantCode);
			memberInfo.setBdMerchantCode(bdMerchantCode);
			memberInfo.setJdMerchantCode(jdMerchantCode);
			//hkz 默认开通米联平台支付
			memberInfo.setMlJfStatus("1");
			memberInfo.setMlWjfStatus("1");
			memberInfoService.insertSelective(memberInfo);
			
			EpayCode epayCode = null;
			if(registerTmp.getPayCode() != null && !"".equals(registerTmp.getPayCode())){
				EpayCodeExample epayCodeExample = new EpayCodeExample();
				epayCodeExample.or().andPayCodeEqualTo(registerTmp.getPayCode());
				List<EpayCode> epayCodeList = epayCodeService.selectByExample(epayCodeExample);
				if(epayCodeList.size()>0){
					epayCode = epayCodeList.get(0);
					epayCode.setStatus("5");
					epayCode.setMemberId(memberInfo.getId());
					epayCode.setT0DrawFee(memberInfo.getT0DrawFee());
					epayCode.setT0TradeRate(memberInfo.getT0TradeRate());
					epayCode.setT1DrawFee(memberInfo.getT1DrawFee());
					epayCode.setT1TradeRate(memberInfo.getT1TradeRate());
					epayCode.setMlJfFee(memberInfo.getMlJfFee());
					epayCode.setMlJfRate(memberInfo.getMlJfRate());
					epayCode.setMlWjfFee(memberInfo.getMlWjfFee());
					epayCode.setMlWjfRate(memberInfo.getMlWjfRate());
					epayCodeService.updateByPrimaryKeySelective(epayCode);
				}
			}
			
			MemberBank memberBank = new MemberBank();
			memberBank.setMemberId(memberInfo.getId());
			memberBank.setBankId(registerTmp.getBankId());
			memberBank.setSubId(registerTmp.getSubId());
			memberBank.setProvince(registerTmp.getProvince());
			memberBank.setCity(registerTmp.getCity());
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
			//生成二维码
			if (reqDataJson.containsKey("agentType") && !"".equals(reqDataJson.getString("agentType")) && null != agentRate) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String defaultQrcodeBasePath = SysConfig.defaultQrcodeBasePath;
				String qrUrl = agentRate.getmUrl();
				/*
				if (null != qrUrl && !"".equals(qrUrl)) {
					if (qrUrl.contains("?")) {
						if (!qrUrl.endsWith("?")) {
							qrUrl = qrUrl + "&epayCode" + memberInfo.getPayCode();
						}else{
							qrUrl = qrUrl + "epayCode" + memberInfo.getPayCode();
						}
					}else {
						qrUrl = qrUrl + "?epayCode" + memberInfo.getPayCode();
					}
					
				}
				 */
				String logoPath = defaultQrcodeBasePath + File.separator + agentRate.getmImg();
				String epayCodePath = File.separator + sdf.format(new Date()) + File.separator + memberInfo.getPayCode() + ".png";
				String saveFilePath = defaultQrcodeBasePath + epayCodePath;
				String savePath = defaultQrcodeBasePath +File.separator+ sdf.format(new Date()) +File.separator;
				if (null != epayCode) {
					epayCode.setPath(epayCodePath);
					epayCodeService.updateByPrimaryKeySelective(epayCode);
				}
				String code = memberInfo.getPayCode();
				String mImg = agentRate.getmImg();
				QrcodeCreateThread qrcodeCreateThread = new QrcodeCreateThread(qrUrl, logoPath, saveFilePath, code ,defaultQrcodeBasePath, mImg, savePath);
				threadPoolTaskExecutor.execute(qrcodeCreateThread);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
			return result.toString();
		}
		result.put("returnCode", "0000");
		result.put("returnMsg", "成功");
		return result.toString();
	}
	/**请确保memberInfo里面已有province，city,county,addr**/
	public String getMerchantAddress(MemberInfo memberInfo){
		String merchantAddress = "";
		BuAreaCodeExample buAreaCodeExample = new BuAreaCodeExample();
		buAreaCodeExample.or().andAreacodeEqualTo(memberInfo.getProvince());
		List<BuAreaCode> areas = buAreaCodeService.selectByExample(buAreaCodeExample);
		if(areas!=null && areas.size()>0){
			BuAreaCode area = areas.get(0);
			merchantAddress += area.getAreaname();
		}
		buAreaCodeExample.clear();
		areas.clear();
		buAreaCodeExample.or().andAreacodeEqualTo(memberInfo.getCity());
		areas = buAreaCodeService.selectByExample(buAreaCodeExample);
		if(areas!=null && areas.size()>0){
			BuAreaCode area = areas.get(0);
			merchantAddress += area.getAreaname();
		}
		buAreaCodeExample.clear();
		areas.clear();
		buAreaCodeExample.or().andAreacodeEqualTo(memberInfo.getCounty());
		areas = buAreaCodeService.selectByExample(buAreaCodeExample);
		if(areas!=null && areas.size()>0){
			BuAreaCode area = areas.get(0);
			merchantAddress += area.getAreaname();
		}
		merchantAddress += memberInfo.getAddr();
		memberInfo.setAddr(merchantAddress);
		return merchantAddress;
	}
	/**
	 * 给第三方平台批量入驻接口
	 * @param model
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/registerBatch")
	public String registerBatch(Model model, HttpServletRequest request) {
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		JSONObject result=new JSONObject();
		try {
			RegisterTmp registerTmp= new RegisterTmp();
			String settleType = reqDataJson.getString("settleType");
			String merchantName = reqDataJson.getString("merchantName");
			String shortName = reqDataJson.getString("shortName");
			String contact = reqDataJson.getString("contact");
			String mobilePhone = reqDataJson.getString("mobilePhone");
			String addr = reqDataJson.getString("addr");
			String certNbr = reqDataJson.getString("certNbr");
			String province = reqDataJson.getString("province");
			String city = reqDataJson.getString("city");
			String memberName = reqDataJson.getString("memberName");
			String cardNbr = reqDataJson.getString("cardNbr");
//			String bankId = reqDataJson.getString("bankId");
			String subId = reqDataJson.getString("subId");//其实是subName
			String t0TradeRate = reqDataJson.getString("t0TradeRate");
			String t0DrawFee = reqDataJson.getString("t0DrawFee");
			String t1TradeRate = reqDataJson.getString("t1TradeRate");
			String t1DrawFee = reqDataJson.getString("t1DrawFee");
			
			registerTmp.setType("1");
			registerTmp.setLoginCode(mobilePhone);//手机号作为账号
			registerTmp.setContact(contact);
			registerTmp.setAddr(addr);
			registerTmp.setCertNbr(certNbr);
//			registerTmp.setProvince(province);
//			registerTmp.setCity(city);
//			registerTmp.setBankId(bankId);
//			registerTmp.setSubId(subId);
			registerTmp.setMobilePhone(mobilePhone);
			registerTmp.setAccountName(memberName);
			registerTmp.setAccountNumber(cardNbr);
			
			registerTmp.setName(merchantName);
			registerTmp.setShortName(shortName);
			registerTmp.setSettleType(settleType);
			registerTmp.setCreateBy("1");
			registerTmp.setUpdateBy("1");
			registerTmp.setDelFlag("0");
			registerTmp.setStatus("0");
			
			//直接根据用户信息表进行校验
			MemberInfoExample memberInfoExample = new MemberInfoExample();
			memberInfoExample.or().andCertNbrEqualTo(registerTmp.getCertNbr()).andStatusNotEqualTo("1");
			int countMemberInfo = memberInfoService.countByExample(memberInfoExample);
			if(countMemberInfo >0){
				result.put("returnCode", "4004");
				result.put("returnMsg", "商户信息已存在，该身份证号码已注册");
				return result.toString();
			}
			memberInfoExample = new MemberInfoExample();
			memberInfoExample.or().andMobilePhoneEqualTo(registerTmp.getMobilePhone()).andStatusNotEqualTo("1");
			countMemberInfo = memberInfoService.countByExample(memberInfoExample);
			if(countMemberInfo > 0){
				result.put("returnCode", "4004");
				result.put("returnMsg", "商户信息已存在，该手机号码已注册");
				return result.toString();
			}
			
//			RegisterTmpExample registerTmpExample = new RegisterTmpExample();
//			registerTmpExample.or().andCertNbrEqualTo(registerTmp.getCertNbr()).andStatusNotEqualTo("2");
//			int countRegisterTemp = registerTmpService.countByExample(registerTmpExample);
//			if(countRegisterTemp >0){
//				result.put("returnCode", "4004");
//				result.put("returnMsg", "商户信息已存在");
//				return result.toString();
//			}
//			registerTmpExample = new RegisterTmpExample();
//			registerTmpExample.or().andMobilePhoneEqualTo(registerTmp.getMobilePhone()).andStatusNotEqualTo("2");
//			countRegisterTemp = registerTmpService.countByExample(registerTmpExample);
//			if(countRegisterTemp > 0){
//				result.put("returnCode", "4004");
//				result.put("returnMsg", "商户信息已存在");
//				return result.toString();
//			}
//			
//			MemberInfoExample  eemberInfoExample = new MemberInfoExample();
//			MemberInfoExample.Criteria createCriteria = eemberInfoExample.createCriteria();
//			createCriteria.andCertNbrEqualTo(registerTmp.getCertNbr());
//			int countByExample = 0;
//			countByExample = memberInfoService.countByExample(eemberInfoExample);
//			if(countByExample >0){
//				result.put("returnCode", "4004");
//				result.put("returnMsg", "身份证号码已存在");
//				return result.toString();
//			}
//			
//			eemberInfoExample = new MemberInfoExample();
//			createCriteria = eemberInfoExample.createCriteria();
//			createCriteria.andMobilePhoneEqualTo(registerTmp.getMobilePhone());
//			countByExample = memberInfoService.countByExample(eemberInfoExample);
//			if(countByExample >0){
//				result.put("returnCode", "4004");
//				result.put("returnMsg", "该手机号码已注册");
//				return result.toString();
//			}
			
			registerTmp.setCode(SysConfig.prefixMemberCode+commonService.getNextSequenceVal(SequenseTypeConstant.MEMBERCODE));
			registerTmp.setLoginPass(SecurityUtil.md5Encode(registerTmp.getMobilePhone().substring(5, 11)));
			Date nowDate = new Date();
			registerTmp.setCreateDate(nowDate);
			registerTmp.setUpdateDate(nowDate);
			
			SysAreaExample sysAreaExample = new SysAreaExample();
			sysAreaExample.or().andNameEqualTo(province).andTypeEqualTo("2");//省
			List<SysArea> areas = sysAreaService.selectByExample(sysAreaExample);
			if(null != areas && areas.size()>0){
				SysArea area = areas.get(0);
				registerTmp.setProvince(area.getCode());
			}else{
				result.put("returnCode", "4004");
				result.put("returnMsg", "找不到对应省份");
				return result.toString();
			}
			
			sysAreaExample = new SysAreaExample();
			sysAreaExample.or().andNameEqualTo(city).andTypeEqualTo("3");//市
			areas = sysAreaService.selectByExample(sysAreaExample);
			if(null != areas && areas.size()>0){
				SysArea area = areas.get(0);
				registerTmp.setCity(area.getCode());
			}else{
				result.put("returnCode", "4004");
				result.put("returnMsg", "找不到对应城市");
				return result.toString();
			}
			
			BankSubExample bankSubExample = new BankSubExample();
			bankSubExample.or().andSubNameEqualTo(subId);
			List<BankSub> bankSubList = bankSubService.selectByExample(bankSubExample);
			if(null != bankSubList && bankSubList.size()>0){
				BankSub bankSub = bankSubList.get(0);
				registerTmp.setBankOpen(bankSub.getSubName());
				registerTmp.setBankId(bankSub.getBankId()+"");
				registerTmp.setSubId(bankSub.getSubId());
			}else{
				result.put("returnCode", "4004");
				result.put("returnMsg", "银行支行信息错误");
				return result.toString();
			}
			//联行号
			String bankType = bankSubList.get(0).getSubId();
			String bankName = bankSubList.get(0).getSubName();
			
			registerTmp.setWxMemberCode("w"+commonService.getNextSequenceVal(SequenseTypeConstant.WXMEMBERCODE));
			registerTmp.setZfbMemberCode("z"+commonService.getNextSequenceVal(SequenseTypeConstant.ZFBMEMBERCODE));
			registerTmp.setQqMemberCode("q"+commonService.getNextSequenceVal(SequenseTypeConstant.QQMEMBERCODE));
			registerTmp.setBdMemberCode("b"+commonService.getNextSequenceVal(SequenseTypeConstant.BDMEMBERCODE));
			registerTmp.setJdMemberCode("j"+commonService.getNextSequenceVal(SequenseTypeConstant.JDMEMBERCODE));

			if(registerTmp.getShortName() == null || "".equals(registerTmp.getShortName())){
				registerTmp.setShortName(registerTmp.getName());
			}
			
			if("0".equals(registerTmp.getSettleType())){
				Bank bankInfo = bankService.selectByPrimaryKey(Integer.parseInt(registerTmp.getBankId()));
				if(null == bankInfo){
					result.put("returnCode", "4004");
					result.put("returnMsg", "所选银行信息错误");
					return result.toString();
				}
				if(bankInfo.getKbinId() != null){
					Kbin kbinInfo = kbinService.selectByPrimaryKey(bankInfo.getKbinId());
					if(null == kbinInfo){
						result.put("returnCode", "4004");
						result.put("returnMsg", "所选结算方式不支持所选银行");
						return result.toString();
					}
				}else{
					result.put("returnCode", "4004");
					result.put("returnMsg", "所选结算方式不支持所选银行");
					return result.toString();
				}
			}
			
			MemberInfo memberInfo = new MemberInfo();
			memberInfo.setCode(registerTmp.getCode());
			memberInfo.setPayCode(registerTmp.getPayCode());
			memberInfo.setType(registerTmp.getType());
			memberInfo.setLoginCode(registerTmp.getLoginCode());
			memberInfo.setLoginPass(registerTmp.getLoginPass());
			memberInfo.setContact(registerTmp.getContact());
			//该项赋值注意区分========
			memberInfo.setCardNbr(registerTmp.getAccountNumber());
			memberInfo.setAddr(registerTmp.getAddr());
			memberInfo.setCertNbr(registerTmp.getCertNbr());
			memberInfo.setProvince(registerTmp.getProvince());
			memberInfo.setCity(registerTmp.getCity());
			memberInfo.setCounty(registerTmp.getCounty());
			getMerchantAddress(memberInfo);
			memberInfo.setMobilePhone(registerTmp.getMobilePhone());
			memberInfo.setCardPic1(registerTmp.getCardPic1());
			memberInfo.setCertPic1(registerTmp.getCertPic1());
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
//			memberInfo.setCategory(registerTmp.getCategory());
			
			if(StringUtils.isNotEmpty(t0DrawFee)){
				memberInfo.setT0DrawFee(new BigDecimal(t0DrawFee));
			}
			if(StringUtils.isNotEmpty(t0TradeRate)){
				memberInfo.setT0TradeRate(new BigDecimal(t0TradeRate));
			}
			if(StringUtils.isNotEmpty(t1DrawFee)){
				memberInfo.setT1DrawFee(new BigDecimal(t1DrawFee));
			}
			if(StringUtils.isNotEmpty(t1TradeRate)){
				memberInfo.setT1TradeRate(new BigDecimal(t1TradeRate));
			}
			
			//支付通道信息获取   --- 微信
			RouteWayExample routeWayExample = new RouteWayExample();
			routeWayExample.or().andTxnTypeEqualTo("1").andDelFlagEqualTo("0").andIsUsableEqualTo("0");
			List<RouteWay> routeWayList = routeWayService.selectByExample(routeWayExample);
			if(routeWayList.size() > 0){
				memberInfo.setWxRouteId(routeWayList.get(0).getRouteId());
			}else{
				result.put("returnCode", "4004");
				result.put("returnMsg", "微信交易类型配置信息缺失");
				return result.toString();
			}
			//支付通道信息获取   --- 支付宝
			routeWayExample = new RouteWayExample();
			routeWayExample.or().andTxnTypeEqualTo("2").andDelFlagEqualTo("0").andIsUsableEqualTo("0");
			routeWayList = routeWayService.selectByExample(routeWayExample);
			if(routeWayList.size() > 0){
				memberInfo.setZfbRouteId(routeWayList.get(0).getRouteId());
			}else{
				result.put("returnCode", "4004");
				result.put("returnMsg", "支付宝交易类型配置信息缺失");
				return result.toString();
			}
			
			//支付通道信息获取   --- QQ钱包
			routeWayExample = new RouteWayExample();
			routeWayExample.or().andTxnTypeEqualTo("3").andDelFlagEqualTo("0").andIsUsableEqualTo("0");
			routeWayList = routeWayService.selectByExample(routeWayExample);
			if(routeWayList.size() > 0){
				memberInfo.setQqRouteId(routeWayList.get(0).getRouteId());
			}else{
				result.put("returnCode", "4004");
				result.put("returnMsg", "QQ钱包交易类型配置信息缺失");
				return result.toString();
			}
			
			//支付通道信息获取   --- 百度钱包
			routeWayExample = new RouteWayExample();
			routeWayExample.or().andTxnTypeEqualTo("4").andDelFlagEqualTo("0").andIsUsableEqualTo("0");
			routeWayList = routeWayService.selectByExample(routeWayExample);
			if(routeWayList.size() > 0){
				memberInfo.setBdRouteId(routeWayList.get(0).getRouteId());
			}else{
				result.put("returnCode", "4004");
				result.put("returnMsg", "百度钱包交易类型配置信息缺失");
				return result.toString();
			}
			
			//支付通道信息获取   --- 京东金融
			routeWayExample = new RouteWayExample();
			routeWayExample.or().andTxnTypeEqualTo("5").andDelFlagEqualTo("0").andIsUsableEqualTo("0");
			routeWayList = routeWayService.selectByExample(routeWayExample);
			if(routeWayList.size() > 0){
				memberInfo.setJdRouteId(routeWayList.get(0).getRouteId());
			}else{
				result.put("returnCode", "4004");
				result.put("returnMsg", "京东金融交易类型配置信息缺失");
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
			
			String wxMerchantCode = null;
			String zfbMerchantCode = null;
			String qqMerchantCode = null;
			String bdMerchantCode = null;
			String jdMerchantCode = null;
			String channelMerchantCode=null;
			JSONObject wxPayAccount = this.registerMsAccount(MSPayWayConstant.WXPAY, memberInfo , businessCategory,bankType,bankName);
			if("0000".equals(wxPayAccount.getString("returnCode"))){
				wxMerchantCode = wxPayAccount.getString("merchantCode");
				memberInfo.setWxStatus("1");//0审核中，1审核通过
			}else if("200012".equals(wxPayAccount.getString("returnCode"))){//审核中
				memberInfo.setWxStatus("0");//0审核中，1审核通过
			}else{
				return wxPayAccount.toString();
			}
			
			JSONObject zfbPayAccount = this.registerMsAccount(MSPayWayConstant.ZFBZF, memberInfo, businessCategory,bankType,bankName);
			if("0000".equals(zfbPayAccount.getString("returnCode"))){
				zfbMerchantCode = zfbPayAccount.getString("merchantCode");
				memberInfo.setZfbStatus("1");
			}else if("200012".equals(zfbPayAccount.getString("returnCode"))){//审核中
				memberInfo.setZfbStatus("0");//0审核中，1审核通过
			}else{
				return zfbPayAccount.toString();
			}
			
			JSONObject qqPayAccount = this.registerMsAccount(MSPayWayConstant.QQZF, memberInfo, businessCategory,bankType,bankName);
			if("0000".equals(qqPayAccount.getString("returnCode"))){
				qqMerchantCode = qqPayAccount.getString("merchantCode");
				memberInfo.setQqStatus("1");
			}else if("200012".equals(qqPayAccount.getString("returnCode"))){//审核中
				memberInfo.setQqStatus("0");//0审核中，1审核通过
			}else{
				return qqPayAccount.toString();
			}
			JSONObject bdPayAccount = this.registerMsAccount(MSPayWayConstant.BDZF, memberInfo, businessCategory,bankType,bankName);
			if("0000".equals(bdPayAccount.getString("returnCode"))){
				bdMerchantCode = bdPayAccount.getString("merchantCode");
				memberInfo.setBdStatus("1");
			}else if("200012".equals(bdPayAccount.getString("returnCode"))){//审核中
				memberInfo.setBdStatus("0");//0审核中，1审核通过
			}else{
				return bdPayAccount.toString();
			}
			JSONObject jdPayAccount = this.registerMsAccount(MSPayWayConstant.JDZF, memberInfo, businessCategory,bankType,bankName);
			if("0000".equals(jdPayAccount.getString("returnCode"))){
				jdMerchantCode = jdPayAccount.getString("merchantCode");
				channelMerchantCode=jdPayAccount.getString("channelMerchantCode");
				if(channelMerchantCode!=null && !channelMerchantCode.equals("")){
					memberInfo.setJdChannelMerchantCode(channelMerchantCode);
				}
				memberInfo.setJdStatus("1");
			}else if("200012".equals(jdPayAccount.getString("returnCode"))){//审核中
				memberInfo.setJdStatus("0");//0审核中，1审核通过
			}else{
				return jdPayAccount.toString();
			}
			registerTmp.setCategory(businessCategory.getId()+"");
			registerTmpService.insertSelective(registerTmp);
			
			if("1".equals(memberInfo.getWxStatus()) || "1".equals(memberInfo.getZfbStatus()) 
					|| "1".equals(memberInfo.getQqStatus()) || "1".equals(memberInfo.getBdStatus())){
				memberInfo.setStatus("0");
			}
			memberInfo.setCategory(businessCategory.getId()+"");
			memberInfo.setWxMerchantCode(wxMerchantCode);
			memberInfo.setZfbMerchantCode(zfbMerchantCode);
			memberInfo.setQqMerchantCode(qqMerchantCode);
			memberInfo.setBdMerchantCode(bdMerchantCode);
			memberInfo.setJdMerchantCode(jdMerchantCode);
			memberInfoService.insertSelective(memberInfo);
			
			if(registerTmp.getPayCode() != null && !"".equals(registerTmp.getPayCode())){
				EpayCodeExample epayCodeExample = new EpayCodeExample();
				epayCodeExample.or().andPayCodeEqualTo(registerTmp.getPayCode());
				List<EpayCode> epayCodeList = epayCodeService.selectByExample(epayCodeExample);
				if(epayCodeList.size()>0){
					EpayCode epayCode = new EpayCode();
					epayCode.setId(epayCodeList.get(0).getId());
					epayCode.setStatus("5");
					epayCode.setMemberId(memberInfo.getId());
					epayCodeService.updateByPrimaryKeySelective(epayCode);
				}
			}
			
			MemberBank memberBank = new MemberBank();
			memberBank.setMemberId(memberInfo.getId());
			memberBank.setBankId(registerTmp.getBankId());
			memberBank.setSubId(registerTmp.getSubId());
			memberBank.setProvince(registerTmp.getProvince());
			memberBank.setCity(registerTmp.getCity());
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
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
			return result.toString();
		}
		result.put("returnCode", "0000");
		result.put("returnMsg", "成功");
		return result.toString();
	}
	/**
	 * 为商户入驻T1
	 * @param model
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/toRegisterT1")
	public String toRegisterT1(Model model, HttpServletRequest request) {
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		JSONObject result=new JSONObject();
		try {
			RegisterTmp registerTmp=(RegisterTmp) JSONObject.toBean(reqDataJson.getJSONObject("registerTmp"), RegisterTmp.class);
			
			//直接根据用户信息表进行校验
			MemberInfoExample memberInfoExample = new MemberInfoExample();
			memberInfoExample.or().andCertNbrEqualTo(registerTmp.getCertNbr()).andStatusNotEqualTo("1");
			int countMemberInfo = memberInfoService.countByExample(memberInfoExample);
			if(countMemberInfo >0){
				result.put("returnCode", "4004");
				result.put("returnMsg", "商户信息已存在，该身份证号码已注册");
				return result.toString();
			}
			memberInfoExample = new MemberInfoExample();
			memberInfoExample.or().andMobilePhoneEqualTo(registerTmp.getMobilePhone()).andStatusNotEqualTo("1");
			countMemberInfo = memberInfoService.countByExample(memberInfoExample);
			if(countMemberInfo > 0){
				result.put("returnCode", "4004");
				result.put("returnMsg", "商户信息已存在，该手机号码已注册");
				return result.toString();
			}
			
			registerTmp.setCode(SysConfig.prefixMemberCode+commonService.getNextSequenceVal(SequenseTypeConstant.MEMBERCODE));
			registerTmp.setLoginPass(SecurityUtil.md5Encode(registerTmp.getMobilePhone().substring(5, 11)));
			Date nowDate = new Date();
			registerTmp.setCreateDate(nowDate);
			registerTmp.setUpdateDate(nowDate);
			
			BankSubExample bankSubExample = new BankSubExample();
			bankSubExample.or().andBankIdEqualTo(Integer.parseInt(registerTmp.getBankId())).andSubIdEqualTo(registerTmp.getSubId());
			List<BankSub> bankSubList = bankSubService.selectByExample(bankSubExample);
			if(null != bankSubList && bankSubList.size()>0){
				registerTmp.setBankOpen(bankSubList.get(0).getSubName());
			}else{
				result.put("returnCode", "4004");
				result.put("returnMsg", "银行支行信息错误");
				return result.toString();
			}
			//联行号
			String bankType = bankSubList.get(0).getSubId();
			String bankName = bankSubList.get(0).getSubName();
			
			registerTmp.setWxMemberCode("w"+commonService.getNextSequenceVal(SequenseTypeConstant.WXMEMBERCODE));
			registerTmp.setZfbMemberCode("z"+commonService.getNextSequenceVal(SequenseTypeConstant.ZFBMEMBERCODE));
			registerTmp.setQqMemberCode("q"+commonService.getNextSequenceVal(SequenseTypeConstant.QQMEMBERCODE));
			registerTmp.setBdMemberCode("b"+commonService.getNextSequenceVal(SequenseTypeConstant.BDMEMBERCODE));
			registerTmp.setJdMemberCode("j"+commonService.getNextSequenceVal(SequenseTypeConstant.JDMEMBERCODE));

			if(registerTmp.getShortName() == null || "".equals(registerTmp.getShortName())){
				registerTmp.setShortName(registerTmp.getName());
			}
			
			if("0".equals(registerTmp.getSettleType())){
				Bank bankInfo = bankService.selectByPrimaryKey(Integer.parseInt(registerTmp.getBankId()));
				if(null == bankInfo){
					result.put("returnCode", "4004");
					result.put("returnMsg", "所选银行信息错误");
					return result.toString();
				}
				if(bankInfo.getKbinId() != null){
					Kbin kbinInfo = kbinService.selectByPrimaryKey(bankInfo.getKbinId());
					if(null == kbinInfo){
						result.put("returnCode", "4004");
						result.put("returnMsg", "所选结算方式不支持所选银行");
						return result.toString();
					}
				}else{
					result.put("returnCode", "4004");
					result.put("returnMsg", "所选结算方式不支持所选银行");
					return result.toString();
				}
			}
			
			MemberInfo memberInfo = new MemberInfo();
			memberInfo.setCode(registerTmp.getCode());
			memberInfo.setPayCode(registerTmp.getPayCode());
			memberInfo.setType(registerTmp.getType());
			memberInfo.setLoginCode(registerTmp.getLoginCode());
			memberInfo.setLoginPass(registerTmp.getLoginPass());
			memberInfo.setContact(registerTmp.getContact());
			//该项赋值注意区分========
			memberInfo.setCardNbr(registerTmp.getAccountNumber());
			memberInfo.setAddr(registerTmp.getAddr());
			memberInfo.setCertNbr(registerTmp.getCertNbr());
			memberInfo.setProvince(registerTmp.getProvince());
			memberInfo.setCity(registerTmp.getCity());
			memberInfo.setCounty(registerTmp.getCounty());
			getMerchantAddress(memberInfo);
			memberInfo.setMobilePhone(registerTmp.getMobilePhone());
			memberInfo.setCardPic1(registerTmp.getCardPic1());
			memberInfo.setCertPic1(registerTmp.getCertPic1());
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
//			memberInfo.setCategory(registerTmp.getCategory());
			
			if(registerTmp.getPayCode() != null && !"".equals(registerTmp.getPayCode())){
				EpayCodeExample epayCodeExample = new EpayCodeExample();
				epayCodeExample.or().andPayCodeEqualTo(registerTmp.getPayCode());
				List<EpayCode> epayCodeList = epayCodeService.selectByExample(epayCodeExample);
				if(epayCodeList.size()>0){
					EpayCode epayCode = new EpayCode();
					epayCode.setId(epayCodeList.get(0).getId());
					memberInfo.setT0DrawFee(epayCodeList.get(0).getT0DrawFee());
					memberInfo.setT0TradeRate(epayCodeList.get(0).getT0TradeRate());
					memberInfo.setT1DrawFee(epayCodeList.get(0).getT1DrawFee());
					memberInfo.setT1TradeRate(epayCodeList.get(0).getT1TradeRate());
				}
			}else{
				TransactionRateExample transactionRateExample = new TransactionRateExample();
				transactionRateExample.or().andTypeEqualTo("0").andDelFlagEqualTo("0");
				
				List<TransactionRate> transactionRateList = transactionRateService.selectByExample(transactionRateExample);
				if(transactionRateList.size() == 0){
					result.put("returnCode", "4004");
					result.put("returnMsg", "交易费率信息配置缺失");
					return result.toString();
				}
				//设置费率
				memberInfo.setT0DrawFee(transactionRateList.get(0).getT0DrawFee());
				memberInfo.setT0TradeRate(transactionRateList.get(0).getT0TradeRate());
				memberInfo.setT1DrawFee(transactionRateList.get(0).getT1DrawFee());
				memberInfo.setT1TradeRate(transactionRateList.get(0).getT1TradeRate());
			}
			
			//支付通道信息获取   --- 微信
			RouteWayExample routeWayExample = new RouteWayExample();
			routeWayExample.or().andTxnTypeEqualTo("1").andDelFlagEqualTo("0").andIsUsableEqualTo("0");
			List<RouteWay> routeWayList = routeWayService.selectByExample(routeWayExample);
			if(routeWayList.size() > 0){
				memberInfo.setWxRouteId(routeWayList.get(0).getRouteId());
			}else{
				result.put("returnCode", "4004");
				result.put("returnMsg", "微信交易类型配置信息缺失");
				return result.toString();
			}
			//支付通道信息获取   --- 支付宝
			routeWayExample = new RouteWayExample();
			routeWayExample.or().andTxnTypeEqualTo("2").andDelFlagEqualTo("0").andIsUsableEqualTo("0");
			routeWayList = routeWayService.selectByExample(routeWayExample);
			if(routeWayList.size() > 0){
				memberInfo.setZfbRouteId(routeWayList.get(0).getRouteId());
			}else{
				result.put("returnCode", "4004");
				result.put("returnMsg", "支付宝交易类型配置信息缺失");
				return result.toString();
			}
			
			//支付通道信息获取   --- QQ钱包
			routeWayExample = new RouteWayExample();
			routeWayExample.or().andTxnTypeEqualTo("3").andDelFlagEqualTo("0").andIsUsableEqualTo("0");
			routeWayList = routeWayService.selectByExample(routeWayExample);
			if(routeWayList.size() > 0){
				memberInfo.setQqRouteId(routeWayList.get(0).getRouteId());
			}else{
				result.put("returnCode", "4004");
				result.put("returnMsg", "QQ钱包交易类型配置信息缺失");
				return result.toString();
			}
			
			//支付通道信息获取   --- 百度钱包
			routeWayExample = new RouteWayExample();
			routeWayExample.or().andTxnTypeEqualTo("4").andDelFlagEqualTo("0").andIsUsableEqualTo("0");
			routeWayList = routeWayService.selectByExample(routeWayExample);
			if(routeWayList.size() > 0){
				memberInfo.setBdRouteId(routeWayList.get(0).getRouteId());
			}else{
				result.put("returnCode", "4004");
				result.put("returnMsg", "百度钱包交易类型配置信息缺失");
				return result.toString();
			}
			
			//支付通道信息获取   --- 京东金融
			routeWayExample = new RouteWayExample();
			routeWayExample.or().andTxnTypeEqualTo("5").andDelFlagEqualTo("0").andIsUsableEqualTo("0");
			routeWayList = routeWayService.selectByExample(routeWayExample);
			if(routeWayList.size() > 0){
				memberInfo.setJdRouteId(routeWayList.get(0).getRouteId());
			}else{
				result.put("returnCode", "4004");
				result.put("returnMsg", "百度钱包交易类型配置信息缺失");
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
			String wxMerchantCode = null;
			String zfbMerchantCode = null;
			String qqMerchantCode = null;
			String bdMerchantCode = null;
			String jdMerchantCode = null;
			String channelMerchantCode = null;
			JSONObject wxPayAccount = this.registerMsAccount(MSPayWayConstant.WXPAY, memberInfo , businessCategory,bankType,bankName);
			if("0000".equals(wxPayAccount.getString("returnCode"))){
				wxMerchantCode = wxPayAccount.getString("merchantCode");
				memberInfo.setWxStatus("1");//0审核中，1审核通过
			}else if("200012".equals(wxPayAccount.getString("returnCode"))){//审核中
				memberInfo.setWxStatus("0");//0审核中，1审核通过
			}else{
				memberInfo.setWxStatus("4");//0审核中，1审核通过
//				return wxPayAccount.toString();
			}
			
			JSONObject zfbPayAccount = this.registerMsAccount(MSPayWayConstant.ZFBZF, memberInfo, businessCategory,bankType,bankName);
			if("0000".equals(zfbPayAccount.getString("returnCode"))){
				zfbMerchantCode = zfbPayAccount.getString("merchantCode");
				memberInfo.setZfbStatus("1");
			}else if("200012".equals(zfbPayAccount.getString("returnCode"))){//审核中
				memberInfo.setZfbStatus("0");//0审核中，1审核通过
			}else{
				memberInfo.setZfbStatus("4");//0审核中，1审核通过
//				return zfbPayAccount.toString();
			}
			
			JSONObject qqPayAccount = this.registerMsAccount(MSPayWayConstant.QQZF, memberInfo, businessCategory,bankType,bankName);
			if("0000".equals(qqPayAccount.getString("returnCode"))){
				qqMerchantCode = qqPayAccount.getString("merchantCode");
				memberInfo.setQqStatus("1");
			}else if("200012".equals(qqPayAccount.getString("returnCode"))){//审核中
				memberInfo.setQqStatus("0");//0审核中，1审核通过
			}else{
//				return qqPayAccount.toString();
				memberInfo.setQqStatus("4");//0审核中，1审核通过
			}
			JSONObject bdPayAccount = this.registerMsAccount(MSPayWayConstant.BDZF, memberInfo, businessCategory,bankType,bankName);
			if("0000".equals(bdPayAccount.getString("returnCode"))){
				bdMerchantCode = bdPayAccount.getString("merchantCode");
				memberInfo.setBdStatus("1");
			}else if("200012".equals(bdPayAccount.getString("returnCode"))){//审核中
				memberInfo.setBdStatus("0");//0审核中，1审核通过
			}else{
				memberInfo.setBdStatus("4");//0审核中，1审核通过
//				return bdPayAccount.toString();
			}
			JSONObject jdPayAccount = this.registerMsAccount(MSPayWayConstant.JDZF, memberInfo, businessCategory,bankType,bankName);
			if("0000".equals(jdPayAccount.getString("returnCode"))){
				jdMerchantCode = jdPayAccount.getString("merchantCode");
				channelMerchantCode=jdPayAccount.getString("channelMerchantCode");
				if(channelMerchantCode!=null && !channelMerchantCode.equals("")){
					memberInfo.setJdChannelMerchantCode(channelMerchantCode);
				}
				memberInfo.setJdStatus("1");
			}else if("200012".equals(jdPayAccount.getString("returnCode"))){//审核中
				memberInfo.setJdStatus("0");//0审核中，1审核通过
			}else{
				memberInfo.setJdStatus("4");//0审核中，1审核通过
//				return jdPayAccount.toString();
			}
			if (null == wxMerchantCode){
				result.put("returnCode", "4004");
				result.put("returnMsg", "失败");
				return result.toString();
			}
			registerTmp.setCategory(businessCategory.getId()+"");
			registerTmpService.insertSelective(registerTmp);
			
			if("1".equals(memberInfo.getWxStatus()) || "1".equals(memberInfo.getZfbStatus()) 
					|| "1".equals(memberInfo.getQqStatus()) || "1".equals(memberInfo.getBdStatus())
					|| "1".equals(memberInfo.getJdStatus())){
				memberInfo.setStatus("0");
			}
			memberInfo.setCategory(businessCategory.getId()+"");
			memberInfo.setWxMerchantCode(wxMerchantCode);
			memberInfo.setZfbMerchantCode(zfbMerchantCode);
			memberInfo.setQqMerchantCode(qqMerchantCode);
			memberInfo.setBdMerchantCode(bdMerchantCode);
			memberInfo.setJdMerchantCode(jdMerchantCode);
			memberInfoService.insertSelective(memberInfo);
			
			if(registerTmp.getPayCode() != null && !"".equals(registerTmp.getPayCode())){
				EpayCodeExample epayCodeExample = new EpayCodeExample();
				epayCodeExample.or().andPayCodeEqualTo(registerTmp.getPayCode());
				List<EpayCode> epayCodeList = epayCodeService.selectByExample(epayCodeExample);
				if(epayCodeList.size()>0){
					EpayCode epayCode = new EpayCode();
					epayCode.setId(epayCodeList.get(0).getId());
					epayCode.setStatus("5");
					epayCode.setMemberId(memberInfo.getId());
					epayCodeService.updateByPrimaryKeySelective(epayCode);
				}
			}
			
			MemberBank memberBank = new MemberBank();
			memberBank.setMemberId(memberInfo.getId());
			memberBank.setBankId(registerTmp.getBankId());
			memberBank.setSubId(registerTmp.getSubId());
			memberBank.setProvince(registerTmp.getProvince());
			memberBank.setCity(registerTmp.getCity());
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
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
			return result.toString();
		}
		result.put("returnCode", "0000");
		result.put("returnMsg", "成功");
		return result.toString();
	}

	@ResponseBody
	@RequestMapping("/toLogin")
	public String toLogin(Model model, HttpServletRequest request) {
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		JSONObject result=new JSONObject();
		JSONObject resData=new JSONObject();
		try {
			String loginCode=reqDataJson.getString("loginCode");
			String password=reqDataJson.getString("password");
			String userOpenID=reqDataJson.getString("userOpenID");
			if("".equals(loginCode) || !ValidateUtil.isMbPhone(loginCode)){
				throw new ArgException("手机号格式不正确");
			}
			
			if("".equals(password)){
				throw new ArgException("密码不为空");
			}
			
			MemberInfoExample memberInfoExample = new MemberInfoExample();
			List<String> statusList = new ArrayList<String>();
			statusList.add("0");
			statusList.add("4");
			if("ESK".equals(SysConfig.passCode)){
				statusList.add("3");
			}
			memberInfoExample.or().andLoginCodeEqualTo(loginCode).andStatusIn(statusList);
			List<MemberInfo> memberInfoList = memberInfoService.selectByExample(memberInfoExample);
			if(memberInfoList.size()==0){
				result.put("returnCode", "4004");
				result.put("returnMsg", "账户不存在");
				return result.toString();
			}else{
				MemberInfo memberInfo = memberInfoList.get(0);
				if(memberInfo.getLoginPass().equals(SecurityUtil.md5Encode(password))){
					/*
					if("3".equals(memberInfo.getStatus())){//审核中
						result.put("returnCode", "4004");
						result.put("returnMsg", "商户正在审核中");
						return result.toString();
					}
					if("1".equals(memberInfo.getStatus())){//已停用
						result.put("returnCode", "4004");
						result.put("returnMsg", "商户已停用");
						return result.toString();
					}
					*/
					EpayCodeExample epayCodeExample = new EpayCodeExample();
					epayCodeExample.or().andMemberIdEqualTo(memberInfo.getId());
					List<EpayCode> epayCodeList = epayCodeService.selectByExample(epayCodeExample);
					if(null != epayCodeList && epayCodeList.size()>0){
						resData.put("epayCode", epayCodeList.get(0).getPayCode());
					}else{
						resData.put("epayCode", "");
					}
					if(!"".equals(userOpenID) && null != userOpenID){
						MemberOpenidExample memberOpenidExample = new MemberOpenidExample();
						memberOpenidExample.or().andMemberIdEqualTo(memberInfo.getId());
						memberOpenidService.deleteByExample(memberOpenidExample);
						MemberOpenid memberOpenid = new MemberOpenid();
						memberOpenid.setMemberId(memberInfo.getId());
						memberOpenid.setOpenid(userOpenID);
						memberOpenid.setCreateBy("1");
						memberOpenid.setCreateDate(new Date());
						memberOpenidService.insertSelective(memberOpenid);
					}
					
					result.put("returnCode", "0000");
					result.put("returnMsg", "登录成功");
					resData.put("memberInfo", memberInfo);
					result.put("resData", resData.toString());
				}else{
					result.put("returnCode", "4004");
					result.put("returnMsg", "密码错误");
				}
			}
			
		}catch (ArgException e){
			result.put("returnCode", "4004");
			result.put("returnMsg", e.getMessage());
			logger.info(e.getMessage());
			return result.toString();
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
	@RequestMapping("/toLoginOut")
	public String toLoginOut(Model model, HttpServletRequest request) {
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		JSONObject result=new JSONObject();
		int memberID=reqDataJson.getInt("memberID");
		MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(memberID);
		if(null == memberInfo){
			result.put("returnCode", "4004");
			result.put("returnMsg", "账户不存在");
		}else{
			MemberOpenidExample memberOpenidExample = new MemberOpenidExample();
			memberOpenidExample.or().andMemberIdEqualTo(memberID);
			memberOpenidService.deleteByExample(memberOpenidExample);
			result.put("returnCode", "0000");
			result.put("returnMsg", "登出成功");
		}
		return result.toString();
	}
	
	/**
	 * 商户入驻接口
	 * @param payWay  支付方式
	 * @param memberInfo  用户信息
	 * @param businessCategory  经营类目
	 * @param bankType	收款人账户联行号
	 * @param bankName  收款人账户开户行名称
	 * @return
	 */
	public JSONObject registerMsAccount(String payWay ,MemberInfo memberInfo ,BusinessCategory businessCategory ,String bankType ,String bankName) {
	
		JSONObject result = new JSONObject();
		String reqMsgId=CommonUtil.getOrderCode();
		try {
			// 调用支付通道
			String serverUrl = MSConfig.msServerUrl;
			PublicKey yhPubKey = null;
			if (serverUrl.startsWith("https://ipay")) {
				yhPubKey = CryptoUtil.getRSAPublicKey(true);
			} else {
				yhPubKey = CryptoUtil.getRSAPublicKey(false);
			}
			PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKey();
			String tranCode = TranCodeConstant.SHRZ;
			String charset = "utf-8";
			String merchantId = "";
			
			if(businessCategory == null){
				result.put("returnCode", "4004");
				result.put("returnMsg", "经营类目信息缺失");
				return result;
			}
			String categoryVal = "";
			String cooperator = "";
			
			if("0".equals(memberInfo.getSettleType())){//先设置通用的合作方标识
				cooperator = MSConfig.cooperator_t0;
			}else{
				cooperator = MSConfig.cooperator_t1;
			}
			if(MSPayWayConstant.WXPAY.equals(payWay)){
				merchantId = memberInfo.getWxMemberCode();
				categoryVal = businessCategory.getWxCategoryId();
			}else if(MSPayWayConstant.ZFBZF.equals(payWay)){
				merchantId = memberInfo.getZfbMemberCode();
				categoryVal = businessCategory.getZfbCategoryId();
			}else if(MSPayWayConstant.QQZF.equals(payWay)){
				merchantId = memberInfo.getQqMemberCode();
				categoryVal = businessCategory.getQqCategoryId();
			}else if(MSPayWayConstant.BDZF.equals(payWay)){
				merchantId = memberInfo.getBdMemberCode();
				categoryVal = businessCategory.getBdCategoryId();
			}else if(MSPayWayConstant.JDZF.equals(payWay)){
				merchantId = memberInfo.getJdMemberCode();
				categoryVal = businessCategory.getJdCategoryId();
			}
			
			String callBack = SysConfig.serverUrl + "/api/registerLogin/msExamNotify";
			System.out.println("-------------注册审核结果通知地址------" + callBack + "------------");
			
			StringBuilder sBuilder = new StringBuilder();
			sBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			sBuilder.append("<merchant>");
			sBuilder.append("<head>");
			sBuilder.append("<version>1.0.0</version>");
			sBuilder.append("<msgType>01</msgType>");
			sBuilder.append("<reqDate>" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + "</reqDate>");
			sBuilder.append("</head>");
			sBuilder.append("<body>");
			sBuilder.append("<payWay>" + payWay + "</payWay>");
			sBuilder.append("<merchantId>" + merchantId + "</merchantId>");
			sBuilder.append("<merchantName>" + memberInfo.getName() + "</merchantName>");
			sBuilder.append("<shortName>" + memberInfo.getShortName() + "</shortName>");
			sBuilder.append("<merchantAddress>" + memberInfo.getAddr() + "</merchantAddress>");
			sBuilder.append("<provinceCode>"+memberInfo.getProvince()+"</provinceCode>");
			sBuilder.append("<cityCode>"+memberInfo.getCity()+"</cityCode>");
			sBuilder.append("<districtCode>"+memberInfo.getCounty()+"</districtCode>");
			sBuilder.append("<contactName>"+memberInfo.getContact()+"</contactName>");
			sBuilder.append("<contactType>"+memberInfo.getContactType()+"</contactType>");			
			sBuilder.append("<servicePhone>" + memberInfo.getMobilePhone() + "</servicePhone>");
			sBuilder.append("<accNo>" + memberInfo.getCardNbr() + "</accNo>");
			sBuilder.append("<accName>" + memberInfo.getContact() + "</accName>");
			sBuilder.append("<category>" + categoryVal + "</category>");
			sBuilder.append("<idCard>" + memberInfo.getCertNbr() + "</idCard>");
			//if("0".equals(memberInfo.getSettleType())){
			sBuilder.append("<t0drawFee>" + memberInfo.getT0DrawFee() + "</t0drawFee>");
			sBuilder.append("<t0tradeRate>" + memberInfo.getT0TradeRate() + "</t0tradeRate>");
			//}
			sBuilder.append("<t1drawFee>" + memberInfo.getT1DrawFee() + "</t1drawFee>");
			sBuilder.append("<t1tradeRate>" + memberInfo.getT1TradeRate() + "</t1tradeRate>");
			if(StringUtils.isNotBlank(bankType)){
				sBuilder.append("<bankType>" + bankType + "</bankType>");
			}
			if(StringUtils.isNotBlank(bankName)){
				sBuilder.append("<bankName>" + bankName + "</bankName>");
			}
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
			nvps.add(new BasicNameValuePair("cooperator", cooperator));
			nvps.add(new BasicNameValuePair("signData", signData));
			nvps.add(new BasicNameValuePair("tranCode", tranCode));
			nvps.add(new BasicNameValuePair("reqMsgId",reqMsgId ));
			nvps.add(new BasicNameValuePair("callBack", callBack));
			byte[] b = HttpClient4Util.getInstance().doPost(serverUrl, null, nvps);
			// post表单,将响应回来的二维码码字符串转为图片 
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
			logger.info(payWay+"返回报文[{}]", new Object[] { resXml });
			JSONObject respJSONObject = XmlConvertUtil.documentToJSONObjectFilter(resXml);
			if("000000".equals(respJSONObject.getString("respCode"))){
				JSONObject resEntity = respJSONObject.getJSONObject("resEntity");
				String merchantCode = resEntity.getString("merchantCode");
				if(resEntity.containsKey("channelMerchantCode")){
					String channelMerchantCode = resEntity.optString("channelMerchantCode");
					result.put("channelMerchantCode", channelMerchantCode);
				}		
				
				result.put("returnCode", "0000");
				result.put("merchantCode", merchantCode);
				
				result.put("returnMsg", "成功");
			}else if("200012".equals(respJSONObject.getString("respCode"))){
				result.put("returnCode", "200012");
				result.put("returnMsg", respJSONObject.getString("respMsg"));
			}else{
				result.put("returnCode", "4004");
				result.put("returnMsg", respJSONObject.getString("respMsg"));
			}
		} catch (ArgException e) {
			result.put("returnCode", "4004");
			result.put("returnMsg", "商户入驻失败");
			logger.info(e.getMessage());
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
			return result;
		}
		return result;
	
	}
	
	
	
	public JSONObject registerEskAccount(String payWay ,MemberInfo memberInfo ,BusinessCategory businessCategory ,String bankType ,String bankName ,String picOrderNo,String bankOpen) {
		
		JSONObject result = new JSONObject();
		//String reqMsgId=CommonUtil.getOrderCode();
		try {
			// 调用支付通道
			String serverUrl = ESKConfig.shopServerUrl;
		//	PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKey();
			String tranCode = "011";
			String charset = "utf-8";
			
			String merchantId = "";
			
			String orderCode = CommonUtil.getOrderCode();
			
			if(businessCategory == null){
				result.put("returnCode", "4004");
				result.put("returnMsg", "经营类目信息缺失");
				return result;
			}
			String categoryVal = "";
			String cooperator = "";
			
			
			if(MSPayWayConstant.WXPAY.equals(payWay)){
				merchantId = memberInfo.getWxMemberCode();
				categoryVal = businessCategory.getWxCategoryId();
			}else if(MSPayWayConstant.ZFBZF.equals(payWay)){
				merchantId = memberInfo.getZfbMemberCode();
				categoryVal = businessCategory.getZfbCategoryId();
			}else if(MSPayWayConstant.QQZF.equals(payWay)){
				merchantId = memberInfo.getQqMemberCode();
				categoryVal = businessCategory.getQqCategoryId();
			}else if(MSPayWayConstant.BDZF.equals(payWay)){
				merchantId = memberInfo.getBdMemberCode();
				categoryVal = businessCategory.getBdCategoryId();
			}else if(MSPayWayConstant.JDZF.equals(payWay)){
				merchantId = memberInfo.getJdMemberCode();
				categoryVal = businessCategory.getJdCategoryId();
			}
			
			String callBack = SysConfig.serverUrl + "/registerLogin/eskExamNotify";
			System.out.println("-------------注册审核结果通知地址------" + callBack + "------------");
			
			
			
			
			JSONObject reqData = new JSONObject();
			reqData.put("orderNumber", orderCode);
			reqData.put("tranCode", tranCode);
			reqData.put("imgOrderNumber", picOrderNo);
			
			reqData.put("phone", memberInfo.getMobilePhone());
			reqData.put("contatctNumber", memberInfo.getMobilePhone());
			reqData.put("contactType", memberInfo.getContactType());
			reqData.put("realName", memberInfo.getContact());
			reqData.put("cardType", "1");
			reqData.put("cardNo", memberInfo.getCardNbr());
			reqData.put("settlement", "D0");//写死D0
			reqData.put("certType", "00");
			reqData.put("certNo", memberInfo.getCertNbr() );
			reqData.put("mobile", memberInfo.getMobilePhone());
			reqData.put("location", bankOpen);
			reqData.put("shopName", memberInfo.getName() );
			reqData.put("cmer", memberInfo.getName());//by linxf先写商户名称
			reqData.put("cmerShort", memberInfo.getShortName());
			reqData.put("businessId", categoryVal);
			reqData.put("isCorp", "N");
			double tradeRate = memberInfo.getT0TradeRate().doubleValue();
			if(tradeRate > 0.006){
				tradeRate = 0.006;
			}
			reqData.put("wxRate", tradeRate);
			reqData.put("aliRate", tradeRate);//by linxf 先一样
			
			reqData.put("channelCode", "WXPAY");
			
			reqData.put("email", memberInfo.getEmail());
			reqData.put("businessType", "NATIONAL_LEGAL_MERGE");
			reqData.put("business", memberInfo.getBusLicenceNbr());
			
			
			reqData.put("ProviceCode", memberInfo.getProvince());
			reqData.put("ctityCode", memberInfo.getCity());
			reqData.put("districtCode", memberInfo.getCounty());
			reqData.put("address", memberInfo.getAddr());
			
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
			
			
			
			
			
			
			if("000000".equals(respJSONObject.getString("respCode"))){
			//	String merchantCode = respJSONObject.getString("merchantCode");
				
				result.put("returnCode", "0000");
				result.put("orderNumber", orderCode);
			//	result.put("merchantCode", merchantCode);
				
				result.put("returnMsg", "成功");
			}else if("200012".equals(respJSONObject.getString("respCode"))){
				result.put("returnCode", "200012");
				result.put("returnMsg", respJSONObject.getString("respMsg"));
			}else{
				result.put("returnCode", "4004");
				result.put("returnMsg", respJSONObject.getString("respMsg"));
			}
		} catch (ArgException e) {
			result.put("returnCode", "4004");
			result.put("returnMsg", "商户入驻失败");
			logger.info(e.getMessage());
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
			return result;
		}
		return result;
	
	}
	
	
	
	
	
	/**
	 * 商户入驻接口 T1
	 * @param payWay  支付方式
	 * @param memberInfo  用户信息
	 * @param businessCategory  经营类目
	 * @param bankType	收款人账户联行号
	 * @param bankName  收款人账户开户行名称
	 * @return
	 */
	@Deprecated
	public JSONObject registerMsAccountT1(String payWay ,MemberInfo memberInfo ,BusinessCategory businessCategory ,String bankType ,String bankName) {
		
		JSONObject result = new JSONObject();
		String reqMsgId=CommonUtil.getOrderCode();
		try {
			// 调用支付通道
			String serverUrl = MSConfig.msServerUrl;
			PublicKey yhPubKey = null;
			if (serverUrl.startsWith("https://ipay")) {
				yhPubKey = CryptoUtil.getRSAPublicKey(true);
			} else {
				yhPubKey = CryptoUtil.getRSAPublicKey(false);
			}
			PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKey();
			String tranCode = TranCodeConstant.SHRZ;
			String charset = "utf-8";
			String merchantId = "";
			
			if(businessCategory == null){
				result.put("returnCode", "4004");
				result.put("returnMsg", "经营类目信息缺失");
				return result;
			}
			String categoryVal = "";
			String cooperator = "";
			if("0".equals(memberInfo.getSettleType())){//先设置通用的合作方标识
				cooperator = MSConfig.cooperator_t0;
			}else{
				cooperator = MSConfig.cooperator_t1;
			}
			if(MSPayWayConstant.WXPAY.equals(payWay)){
				merchantId = memberInfo.getWxMemberCode();
				categoryVal = businessCategory.getWxCategoryId();
			}else if(MSPayWayConstant.ZFBZF.equals(payWay)){
				merchantId = memberInfo.getZfbMemberCode();
				categoryVal = businessCategory.getZfbCategoryId();
			}else if(MSPayWayConstant.QQZF.equals(payWay)){
				merchantId = memberInfo.getQqMemberCode();
				categoryVal = businessCategory.getQqCategoryId();
			}else if(MSPayWayConstant.BDZF.equals(payWay)){
				merchantId = memberInfo.getBdMemberCode();
				categoryVal = businessCategory.getBdCategoryId();
			}
			
			String callBack = SysConfig.serverUrl + "/api/registerLogin/msExamNotify";
			System.out.println("-------------注册审核结果通知地址------" + callBack + "------------");
			
			StringBuilder sBuilder = new StringBuilder();
			sBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			sBuilder.append("<merchant>");
			sBuilder.append("<head>");
			sBuilder.append("<version>1.0.0</version>");
			sBuilder.append("<msgType>01</msgType>");
			sBuilder.append("<reqDate>" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + "</reqDate>");
			sBuilder.append("</head>");
			sBuilder.append("<body>");
			sBuilder.append("<payWay>" + payWay + "</payWay>");
			sBuilder.append("<merchantId>" + merchantId + "</merchantId>");
			sBuilder.append("<merchantName>" + memberInfo.getName() + "</merchantName>");
			sBuilder.append("<shortName>" + memberInfo.getShortName() + "</shortName>");
			sBuilder.append("<merchantAddress>" + memberInfo.getAddr() + "</merchantAddress>");
			sBuilder.append("<servicePhone>" + memberInfo.getMobilePhone() + "</servicePhone>");
			sBuilder.append("<accNo>" + memberInfo.getCardNbr() + "</accNo>");
			sBuilder.append("<accName>" + memberInfo.getContact() + "</accName>");
			sBuilder.append("<category>" + categoryVal + "</category>");
			sBuilder.append("<idCard>" + memberInfo.getCertNbr() + "</idCard>");
			//if("0".equals(memberInfo.getSettleType())){
			sBuilder.append("<t0drawFee>" + memberInfo.getT0DrawFee() + "</t0drawFee>");
			sBuilder.append("<t0tradeRate>" + memberInfo.getT0TradeRate() + "</t0tradeRate>");
			//}
			sBuilder.append("<t1drawFee>" + memberInfo.getT1DrawFee() + "</t1drawFee>");
			sBuilder.append("<t1tradeRate>" + memberInfo.getT1TradeRate() + "</t1tradeRate>");
			sBuilder.append("<bankType>" + bankType + "</bankType>");
			sBuilder.append("<bankName>" + bankName + "</bankName>");
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
//			nvps.add(new BasicNameValuePair("cooperator", MSConfig.cooperator_t1));
			nvps.add(new BasicNameValuePair("cooperator", cooperator));
			
			/*
			if("0".equals(memberInfo.getSettleType())){
				nvps.add(new BasicNameValuePair("cooperator", MSConfig.cooperator_t0));
			}else{
				nvps.add(new BasicNameValuePair("cooperator", MSConfig.cooperator_t1));
			}
			 */
			nvps.add(new BasicNameValuePair("signData", signData));
			nvps.add(new BasicNameValuePair("tranCode", tranCode));
			nvps.add(new BasicNameValuePair("reqMsgId",reqMsgId ));
			nvps.add(new BasicNameValuePair("callBack", callBack));
			byte[] b = HttpClient4Util.getInstance().doPost(serverUrl, null, nvps);
			// post表单,将响应回来的二维码码字符串转为图片 
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
			logger.info(payWay+"返回报文[{}]", new Object[] { resXml });
			JSONObject respJSONObject = XmlConvertUtil.documentToJSONObjectFilter(resXml);
			if("000000".equals(respJSONObject.getString("respCode"))){
				JSONObject resEntity = respJSONObject.getJSONObject("resEntity");
				String merchantCode = resEntity.getString("merchantCode");
				result.put("returnCode", "0000");
				result.put("merchantCode", merchantCode);
				result.put("returnMsg", "成功");
			}else if("200012".equals(respJSONObject.getString("respCode"))){
				result.put("returnCode", "200012");
				result.put("returnMsg", respJSONObject.getString("respMsg"));
			}else{
				result.put("returnCode", "4004");
				result.put("returnMsg", respJSONObject.getString("respMsg"));
			}
		} catch (ArgException e) {
			result.put("returnCode", "4004");
			result.put("returnMsg", "商户入驻失败");
			logger.info(e.getMessage());
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
			return result;
		}
		return result;
		
	}
	
	/**
	 * 商户入驻审核结果异步通知
	 * @param request
	 * @param response
	 */
	@RequestMapping("/msExamNotify")
	public void msExamNotify(HttpServletRequest request,HttpServletResponse response) {
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
			logger.info("注册审核结果通知报文:"+resXml);
			
			JSONObject resEntity = respJSONObject.getJSONObject("resEntity");
			String merchantId = resEntity.getString("merchantId");
			String merchantCode = resEntity.getString("merchantCode");
			String payWay = resEntity.getString("payWay");
			String merchantName = resEntity.getString("merchantName");
			String shortName = resEntity.getString("shortName");
			String merchantAddress = resEntity.getString("merchantAddress");
			String servicePhone = resEntity.getString("servicePhone");
			String orgCode = resEntity.getString("orgCode");
			String contactName = resEntity.getString("contactName");
			String contactPhone = resEntity.getString("contactPhone");
			String contactMobile = resEntity.getString("contactMobile");
			String contactEmail = resEntity.getString("contactEmail");
			String category = resEntity.getString("category");
			String idCard = resEntity.getString("idCard");
			String merchantLicense = resEntity.getString("merchantLicense");
			String remark = resEntity.getString("remark");
			String status = resEntity.getString("status");
			String accNo = resEntity.getString("accNo");
			String accName = resEntity.getString("accName");
			String bankType = resEntity.getString("bankType");
			String bankName = resEntity.getString("bankName");
			String t0drawFee = resEntity.getString("t0drawFee");
			String t0tradeRate = resEntity.getString("t0tradeRate");
			String t1drawFee = resEntity.getString("t1drawFee");
			String t1tradeRate = resEntity.getString("t1tradeRate");
			String channelMerchantCode = resEntity.getString("channelMerchantCode");
			
			RegisterResultNotice registerResultNotice = new RegisterResultNotice();
			registerResultNotice.setMemberCode(merchantId);
			registerResultNotice.setMerchantCode(merchantCode);
			registerResultNotice.setPayWay(payWay);
			registerResultNotice.setMerchantName(merchantName);
			registerResultNotice.setShortName(shortName);
			registerResultNotice.setMerchantAddress(merchantAddress);
			registerResultNotice.setServicePhone(servicePhone);
			registerResultNotice.setOrgCode(orgCode);
			registerResultNotice.setContactName(contactName);
			registerResultNotice.setContactPhone(contactPhone);
			registerResultNotice.setContactMobile(contactMobile);
			registerResultNotice.setContactEmail(contactEmail);
			registerResultNotice.setCategory(category);
			registerResultNotice.setIdCard(idCard);
			registerResultNotice.setMerchantLicense(merchantLicense);
			registerResultNotice.setRemark(remark);
			registerResultNotice.setStatus(status);
			registerResultNotice.setAccNo(accNo);
			registerResultNotice.setAccName(accName);
			registerResultNotice.setBankType(bankType);
			registerResultNotice.setBankName(bankName);
			registerResultNotice.setT0drawFee(Integer.parseInt(t0drawFee));
			registerResultNotice.setT0tradeRate(Integer.parseInt(t0tradeRate));
			registerResultNotice.setT1drawFee(Integer.parseInt(t1drawFee));
			registerResultNotice.setT1tradeRate(Integer.parseInt(t1tradeRate));
			registerResultNotice.setChannelMerchantCode(channelMerchantCode);
			registerResultNoticeService.insertSelective(registerResultNotice);
			
			MemberInfoExample memberInfoExample = new MemberInfoExample();
			Criteria memberInfoCriteria = memberInfoExample.createCriteria();
			if(MSPayWayConstant.WXPAY.equals(payWay)){
				memberInfoCriteria.andWxMemberCodeEqualTo(merchantId);
			}else if(MSPayWayConstant.ZFBZF.equals(payWay)){
				memberInfoCriteria.andZfbMemberCodeEqualTo(merchantId);
			}else if(MSPayWayConstant.QQZF.equals(payWay)){
				memberInfoCriteria.andQqMemberCodeEqualTo(merchantId);
			}else if(MSPayWayConstant.BDZF.equals(payWay)){
				memberInfoCriteria.andBdMemberCodeEqualTo(merchantId);
			}else if(MSPayWayConstant.JDZF.equals(payWay)){
				memberInfoCriteria.andJdMemberCodeEqualTo(merchantId);
			}
			MemberInfo member = new MemberInfo();
			if(MSPayWayConstant.WXPAY.equals(payWay)){
				if(StringUtils.isNotEmpty(merchantCode)){
					member.setWxMerchantCode(merchantCode);
				}
				member.setWxStatus(status);
			}else if(MSPayWayConstant.ZFBZF.equals(payWay)){
				if(StringUtils.isNotEmpty(merchantCode)){
					member.setZfbMerchantCode(merchantCode);
				}
				member.setZfbStatus(status);
			}else if(MSPayWayConstant.QQZF.equals(payWay)){
				if(StringUtils.isNotEmpty(merchantCode)){
					member.setQqMerchantCode(merchantCode);
				}
				member.setQqStatus(status);
			}else if(MSPayWayConstant.BDZF.equals(payWay)){
				if(StringUtils.isNotEmpty(merchantCode)){
					member.setBdMerchantCode(merchantCode);
				}
				member.setBdStatus(status);
			}else if(MSPayWayConstant.JDZF.equals(payWay)){
				if(StringUtils.isNotEmpty(merchantCode)){
					member.setJdMerchantCode(merchantCode);
				}
				member.setJdStatus(status);
			}
			memberInfoService.updateByExampleSelective(member, memberInfoExample);
			
			List<MemberInfo> members = memberInfoService.selectByExample(memberInfoExample);
			if(members !=null && members.size()>0){
				MemberInfo m = members.get(0);
				if("1".equals(m.getWxStatus()) || "1".equals(m.getZfbStatus()) || "1".equals(m.getQqStatus()) 
						|| "1".equals(m.getBdStatus()) || "1".equals(m.getJdStatus())){
					m.setStatus("0");
					memberInfoService.updateByExampleSelective(m, memberInfoExample);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("注册审核结果通知处理失败:"+e.getMessage());
		}
		
		try {
			response.getWriter().write("000000");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
 	
	/**
	 * 为所有商户入驻t0,Test.java用
	 * @param request
	 * @return
	 */
	@RequestMapping("/registerT0")
	@ResponseBody
	public String registerT0(HttpServletRequest request,HttpServletResponse response){
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		String memberID = reqDataJson.getString("memberID");
		JSONObject result = new JSONObject();
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
		
		MemberInfoExample memberInfoExample = new MemberInfoExample();
		if(StringUtils.isNotEmpty(memberID)){
			memberInfoExample.or().andIdEqualTo(Integer.parseInt(memberID)).andDelFlagEqualTo("0");
		}else{
			memberInfoExample.or().andDelFlagEqualTo("0");//.andCodeEqualTo("1010000067");
		}
		List<MemberInfo> memberInfoList = memberInfoService.selectByExample(memberInfoExample);
		for(MemberInfo memberInfo : memberInfoList){
			
			MemberBankExample memberBankExample = new MemberBankExample();
			memberBankExample.or().andMemberIdEqualTo(memberInfo.getId());
			List<MemberBank> memberBankList = memberBankService.selectByExample(memberBankExample);
			if(null != memberBankList && memberBankList.size() > 0){
				//String bankType = memberBankList.get(0).getSubId();
				//String bankName = memberBankList.get(0).getBankOpen();
				String bankType = "";
				String bankName = "";
				JSONObject returnWxJson = msMerchantInfo(null, memberInfo.getWxMemberCode(), memberInfo);//先查询，再入驻
				if(!"0000".equals(returnWxJson.getString("returnCode"))){
					JSONObject account = registerMsAccount(MSPayWayConstant.WXPAY ,memberInfo ,businessCategory ,bankType ,bankName);
					if("0000".equals(account.getString("returnCode"))){
						String merchantCode = account.getString("merchantCode");
						memberInfo.setWxMerchantCode(merchantCode);
						memberInfo.setWxStatus("1");
					}else if("200012".equals(account.getString("returnCode"))){//审核中
						memberInfo.setWxStatus("0");//0审核中，1审核通过
					}else{
						memberInfo.setWxStatus("4");
					}
				}
				JSONObject returnZfbJson = msMerchantInfo(null, memberInfo.getZfbMemberCode(), memberInfo);//先查询，再入驻
				if(!"0000".equals(returnZfbJson.getString("returnCode"))){
					JSONObject account = registerMsAccount(MSPayWayConstant.ZFBZF ,memberInfo ,businessCategory ,bankType ,bankName);
					if("0000".equals(account.getString("returnCode"))){
						String merchantCode = account.getString("merchantCode");
						memberInfo.setZfbMerchantCode(merchantCode);
						memberInfo.setZfbStatus("1");
					}else if("200012".equals(account.getString("returnCode"))){//审核中
						memberInfo.setZfbStatus("0");//0审核中，1审核通过
					}else{
						memberInfo.setZfbStatus("4");
					}
				}
				JSONObject returnQqJson = msMerchantInfo(null, memberInfo.getQqMemberCode(), memberInfo);//先查询，再入驻
				if(!"0000".equals(returnQqJson.getString("returnCode"))){
					JSONObject account = registerMsAccount(MSPayWayConstant.QQZF ,memberInfo ,businessCategory ,bankType ,bankName);
					if("0000".equals(account.getString("returnCode"))){
						String merchantCode = account.getString("merchantCode");
						memberInfo.setQqMerchantCode(merchantCode);
						memberInfo.setQqStatus("1");
					}else if("200012".equals(account.getString("returnCode"))){//审核中
						memberInfo.setQqStatus("0");//0审核中，1审核通过
					}else{
						memberInfo.setQqStatus("4");
					}
				}
				JSONObject returnBdJson = msMerchantInfo(null, memberInfo.getBdMemberCode(), memberInfo);//先查询，再入驻
				if(!"0000".equals(returnBdJson.getString("returnCode"))){
					JSONObject account = registerMsAccount(MSPayWayConstant.BDZF ,memberInfo ,businessCategory ,bankType ,bankName);
					if("0000".equals(account.getString("returnCode"))){
						String merchantCode = account.getString("merchantCode");
						memberInfo.setBdMerchantCode(merchantCode);
						memberInfo.setBdStatus("1");
					}else if("200012".equals(account.getString("returnCode"))){//审核中
						memberInfo.setBdStatus("0");//0审核中，1审核通过
					}else{
						memberInfo.setBdStatus("4");
					}
				}
				JSONObject returnJdJson = msMerchantInfo(null, memberInfo.getJdMemberCode(), memberInfo);//先查询，再入驻
				if(!"0000".equals(returnJdJson.getString("returnCode"))){
					JSONObject account = registerMsAccount(MSPayWayConstant.JDZF ,memberInfo ,businessCategory ,bankType ,bankName);
					if("0000".equals(account.getString("returnCode"))){
						String merchantCode = account.getString("merchantCode");
						memberInfo.setJdMerchantCode(merchantCode);
						memberInfo.setJdStatus("1");
					}else if("200012".equals(account.getString("returnCode"))){//审核中
						memberInfo.setJdStatus("0");//0审核中，1审核通过
					}else{
						memberInfo.setJdStatus("4");
					}
				}
				memberInfoService.updateByPrimaryKeySelective(memberInfo);
			}
		}
		result.put("returnCode", "0000");
		result.put("returnMsg", "入驻完成");
		return result.toString();
	}
	
	/**
	 * 商户入驻前先查询（专用）
	 * @param queryId
	 * @param merchantId
	 * @param memberInfo
	 * @return
	 */
	public JSONObject msMerchantInfo(String queryId,String merchantId,MemberInfo memberInfo){
		JSONObject result=new JSONObject();
		String tranCode = TranCodeConstant.SHCX;
		String reqMsgId=CommonUtil.getOrderCode();
		try {
			// 调用支付通道
			String serverUrl = MSConfig.msServerUrl;
			PublicKey yhPubKey = null;
			if (serverUrl.startsWith("https://ipay")) {
				yhPubKey = CryptoUtil.getRSAPublicKey(true);
			} else {
				yhPubKey = CryptoUtil.getRSAPublicKey(false);
			}
			PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKey();
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
			sBuilder.append("<merchantId>" + merchantId + "</merchantId>");
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
//			nvps.add(new BasicNameValuePair("cooperator", MSConfig.cooperator_t0));
			if("0".equals(memberInfo.getSettleType())){
				nvps.add(new BasicNameValuePair("cooperator", MSConfig.cooperator_t0));
			}else{
				nvps.add(new BasicNameValuePair("cooperator", MSConfig.cooperator_t1));
			}
			nvps.add(new BasicNameValuePair("signData", signData));
			nvps.add(new BasicNameValuePair("tranCode", tranCode));
			nvps.add(new BasicNameValuePair("reqMsgId",reqMsgId ));
			byte[] b = HttpClient4Util.getInstance().doPost(serverUrl, null, nvps);
			// post表单,将响应回来的二维码码字符串转为图片 
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
			logger.info(queryId+"查询商户信息[{}]",  resXml);
			JSONObject respJSONObject = XmlConvertUtil.documentToJSONObjectFilter(resXml);
			if("000000".equals(respJSONObject.getString("respCode"))){
				JSONObject resEntity = respJSONObject.getJSONObject("resEntity");
				String merchantCode = resEntity.getString("merchantCode");
				String oriRespCode = resEntity.getString("oriRespCode");
				//if("000000".equals(oriRespCode) || "200012".equals(oriRespCode)){
				if("000000".equals(oriRespCode)){
					result.put("returnCode", "0000");
					result.put("merchantCode", merchantCode);
					result.put("returnMsg", "成功");
				}else if("200013".equals(oriRespCode) || "200012".equals(oriRespCode)){//审核失败
					result.put("returnCode", "4001");
					result.put("returnMsg", resEntity.getString("oriRespMsg"));
				}else{
					result.put("returnCode", "4004");
					result.put("returnMsg", resEntity.getString("oriRespMsg"));
				}
			}else{
				result.put("returnCode", "4004");
				result.put("returnMsg", respJSONObject.getString("respMsg"));
			}
		} catch (ArgException e) {
			result.put("returnCode", "4004");
			result.put("returnMsg", "商户信息查询失败");
			logger.info(e.getMessage());
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
			return result;
		}
		return result;
	}
	
	/**
	 * 查询所有未审核通过商户,Test.java用
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryUnExam")
	@ResponseBody
	public void queryUnExam(HttpServletRequest request,HttpServletResponse response){
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		MemberInfoExample memberInfoExample = new MemberInfoExample();
		memberInfoExample.or().andWxMerchantCodeIsNull();
		memberInfoExample.or().andZfbMerchantCodeIsNull();
		memberInfoExample.or().andQqMerchantCodeIsNull();
		memberInfoExample.or().andBdMerchantCodeIsNull();
		memberInfoExample.or().andJdMerchantCodeIsNull();
		memberInfoExample.or().andWxMerchantCodeEqualTo("");
		memberInfoExample.or().andZfbMerchantCodeEqualTo("");
		memberInfoExample.or().andQqMerchantCodeEqualTo("");
		memberInfoExample.or().andBdMerchantCodeEqualTo("");
		memberInfoExample.or().andJdMerchantCodeEqualTo("");
		List<String> values = new ArrayList<String>();
		values.add("0");
		values.add("2");
		memberInfoExample.or().andWxStatusIn(values);
		memberInfoExample.or().andZfbStatusIn(values);
		memberInfoExample.or().andQqStatusIn(values);
		memberInfoExample.or().andBdStatusIn(values);
		memberInfoExample.or().andJdStatusIn(values);
		
		List<MemberInfo> memberInfoList = memberInfoService.selectByExample(memberInfoExample);
		
		System.out.println(memberInfoList.size());
		for(MemberInfo memberInfo:memberInfoList){
			if("0".equals(memberInfo.getWxStatus()) || StringUtils.isEmpty(memberInfo.getWxMerchantCode())){
				JSONObject returnJson = msMerchantInfo(null, memberInfo.getWxMemberCode(), memberInfo);
				if("0000".equals(returnJson.getString("returnCode"))){
					memberInfo.setWxMerchantCode(returnJson.getString("merchantCode"));
					memberInfo.setWxStatus("1");
					merchantManagerController.msChildMerchant(null, memberInfo.getWxMerchantCode(), memberInfo, "1", WxConfig.authDirectory);
					merchantManagerController.msChildMerchant(null, memberInfo.getWxMerchantCode(), memberInfo, "2", WxConfig.appid);
				}else if("4001".equals(returnJson.getString("returnCode"))){
					memberInfo.setWxStatus("2");
				}else{
					memberInfo.setWxStatus("0");
				}
			}
			if("0".equals(memberInfo.getZfbStatus()) || StringUtils.isEmpty(memberInfo.getZfbMerchantCode())){
				JSONObject returnJson = msMerchantInfo2(null, memberInfo.getZfbMemberCode(), memberInfo);
				if("0000".equals(returnJson.getString("returnCode"))){
					memberInfo.setZfbMerchantCode(returnJson.getString("merchantCode"));
					memberInfo.setZfbStatus("1");
				}else if("4001".equals(returnJson.getString("returnCode"))){
					memberInfo.setZfbStatus("2");
				}else{
					memberInfo.setZfbStatus("0");
				}
			}
			if("0".equals(memberInfo.getQqStatus()) || StringUtils.isEmpty(memberInfo.getQqMerchantCode())){
				JSONObject returnJson = msMerchantInfo2(null, memberInfo.getQqMemberCode(), memberInfo);
				if("0000".equals(returnJson.getString("returnCode"))){
					memberInfo.setQqMerchantCode(returnJson.getString("merchantCode"));
					memberInfo.setQqStatus("1");
				}else if("4001".equals(returnJson.getString("returnCode"))){
					memberInfo.setQqStatus("2");
				}else{
					memberInfo.setQqStatus("0");
				}
			}
			if("0".equals(memberInfo.getBdStatus()) || StringUtils.isEmpty(memberInfo.getBdMerchantCode())){
				JSONObject returnJson = msMerchantInfo2(null, memberInfo.getBdMemberCode(), memberInfo);
				if("0000".equals(returnJson.getString("returnCode"))){
					memberInfo.setBdMerchantCode(returnJson.getString("merchantCode"));
					memberInfo.setBdStatus("1");
				}else if("4001".equals(returnJson.getString("returnCode"))){
					memberInfo.setBdStatus("2");
				}else{
					memberInfo.setBdStatus("0");
				}
			}
			if("0".equals(memberInfo.getJdStatus()) || StringUtils.isEmpty(memberInfo.getJdMerchantCode())){
				JSONObject returnJson = msMerchantInfo2(null, memberInfo.getJdMemberCode(), memberInfo);
				if("0000".equals(returnJson.getString("returnCode"))){
					memberInfo.setJdMerchantCode(returnJson.getString("merchantCode"));
					memberInfo.setJdStatus("1");
				}else if("4001".equals(returnJson.getString("returnCode"))){
					memberInfo.setJdStatus("2");
				}else{
					memberInfo.setJdStatus("0");
				}
			}
			memberInfoService.updateByPrimaryKeySelective(memberInfo);
		}
	}
	/**
	 * queryUnExam专用
	 * @param queryId
	 * @param merchantId
	 * @param memberInfo
	 * @return
	 */
	public JSONObject msMerchantInfo2(String queryId,String merchantId,MemberInfo memberInfo){
		JSONObject result=new JSONObject();
		String tranCode = TranCodeConstant.SHCX;
		String reqMsgId=CommonUtil.getOrderCode();
		try {
			// 调用支付通道
			String serverUrl = MSConfig.msServerUrl;
			PublicKey yhPubKey = null;
			if (serverUrl.startsWith("https://ipay")) {
				yhPubKey = CryptoUtil.getRSAPublicKey(true);
			} else {
				yhPubKey = CryptoUtil.getRSAPublicKey(false);
			}
			PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKey();
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
			sBuilder.append("<merchantId>" + merchantId + "</merchantId>");
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
			if("0".equals(memberInfo.getSettleType())){
				nvps.add(new BasicNameValuePair("cooperator", MSConfig.cooperator_t0));
			}else{
				nvps.add(new BasicNameValuePair("cooperator", MSConfig.cooperator_t1));
			}
			nvps.add(new BasicNameValuePair("signData", signData));
			nvps.add(new BasicNameValuePair("tranCode", tranCode));
			nvps.add(new BasicNameValuePair("reqMsgId",reqMsgId ));
			byte[] b = HttpClient4Util.getInstance().doPost(serverUrl, null, nvps);
			// post表单,将响应回来的二维码码字符串转为图片 
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
			logger.info(queryId+"查询商户信息[{}]",  resXml);
			JSONObject respJSONObject = XmlConvertUtil.documentToJSONObjectFilter(resXml);
			if("000000".equals(respJSONObject.getString("respCode"))){
				JSONObject resEntity = respJSONObject.getJSONObject("resEntity");
				String oriRespCode = resEntity.getString("oriRespCode");
				if("000000".equals(oriRespCode)){
					String merchantCode = resEntity.getString("merchantCode");
					result.put("returnCode", "0000");
					result.put("merchantCode", merchantCode);
					result.put("returnMsg", "成功");
				}else if("200013".equals(oriRespCode)){//审核失败
					result.put("returnCode", "4001");
					result.put("returnMsg", resEntity.getString("oriRespMsg"));
				}else{
					result.put("returnCode", "4004");
					result.put("returnMsg", resEntity.getString("oriRespMsg"));
				}
			}else{
				result.put("returnCode", "4004");
				result.put("returnMsg", respJSONObject.getString("respMsg"));
			}
		} catch (ArgException e) {
			result.put("returnCode", "4004");
			result.put("returnMsg", "商户信息查询失败");
			logger.info(e.getMessage());
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
			return result;
		}
		return result;
	}
	
	@RequestMapping("/resetPassword")
	@ResponseBody
	public String resetPassword(HttpServletRequest request,HttpServletResponse response){
		JSONObject result = new JSONObject();
		try {
			JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
			JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
			String contactName = reqDataJson.getString("contactName");
			String mobilePhone = reqDataJson.getString("mobilePhone");
			String certNbr = reqDataJson.getString("certNbr");
			String password = reqDataJson.getString("password");
			String confirmPassword = reqDataJson.getString("confirmPassword");
			if (!password.equals(confirmPassword)) {
				result.put("returnCode", "4004");
				result.put("returnMsg", "两次输入的密码不一致");
				return result.toString();
			}
//			MemberInfoExample memberInfoExample = new MemberInfoExample();
//			memberInfoExample.or().andMobilePhoneEqualTo(mobilePhone).andContactEqualTo(contactName).andCertNbrEqualTo(certNbr).andDelFlagEqualTo("0");
//			List<MemberInfo> memberInfoList = memberInfoService.selectByExample(memberInfoExample);
//			if (memberInfoList.size() == 0) {
//				result.put("returnCode", "4004");
//				result.put("returnMsg", "商户信息未注册或输入信息与该手机号注册信息不一致");
//				return result.toString();
//			}
			MemberInfoExample memberInfoExample = new MemberInfoExample();
			memberInfoExample.or().andMobilePhoneEqualTo(mobilePhone).andDelFlagEqualTo("0");
			List<MemberInfo> memberInfoList = memberInfoService.selectByExample(memberInfoExample);
			if (memberInfoList.size() == 0) {
				result.put("returnCode", "4004");
				result.put("returnMsg", "输入的手机号未注册商户信息");
				return result.toString();
			}
			MemberInfo memberInfo = memberInfoList.get(0);
			if (!memberInfo.getContact().equals(contactName)) {
				result.put("returnCode", "4004");
				result.put("returnMsg", "真是姓名不正确，与该手机号注册的姓名不一致");
				return result.toString();
			}
			if (!memberInfo.getCertNbr().equals(certNbr)) {
				result.put("returnCode", "4004");
				result.put("returnMsg", "身份证号码不正确，与该手机号注册的身份证不一致");
				return result.toString();
			}
			memberInfo.setLoginPass(SecurityUtil.md5Encode(password));
			memberInfo.setUpdateDate(new Date());
			memberInfoService.updateByPrimaryKey(memberInfo);
			result.put("returnCode", "0000");
			result.put("returnMsg", "密码修改成功");
			return result.toString();
		} catch (Exception e) {
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
			return result.toString();
		}
	}
}
