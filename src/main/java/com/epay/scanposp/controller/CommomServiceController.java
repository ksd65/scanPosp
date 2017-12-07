package com.epay.scanposp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.epay.scanposp.common.excep.ArgException;
import com.epay.scanposp.entity.Bank;
import com.epay.scanposp.entity.BankExample;
import com.epay.scanposp.entity.BankName;
import com.epay.scanposp.entity.BankNameExample;
import com.epay.scanposp.entity.BankSub;
import com.epay.scanposp.entity.BankSubExample;
import com.epay.scanposp.entity.BankSubExample.Criteria;
import com.epay.scanposp.entity.BuAreaCode;
import com.epay.scanposp.entity.BuAreaCodeExample;
import com.epay.scanposp.entity.MemberAliOpenid;
import com.epay.scanposp.entity.MemberAliOpenidExample;
import com.epay.scanposp.entity.MemberInfo;
import com.epay.scanposp.entity.MemberOpenid;
import com.epay.scanposp.entity.MemberOpenidExample;
import com.epay.scanposp.entity.PayType;
import com.epay.scanposp.entity.PayTypeExample;
import com.epay.scanposp.entity.SysArea;
import com.epay.scanposp.entity.SysAreaExample;
import com.epay.scanposp.entity.SysCommonConfig;
import com.epay.scanposp.entity.SysCommonConfigExample;
import com.epay.scanposp.service.BankNameService;
import com.epay.scanposp.service.BankService;
import com.epay.scanposp.service.BankSubService;
import com.epay.scanposp.service.BuAreaCodeService;
import com.epay.scanposp.service.MemberAliOpenidService;
import com.epay.scanposp.service.MemberInfoService;
import com.epay.scanposp.service.MemberOpenidService;
import com.epay.scanposp.service.PayTypeService;
import com.epay.scanposp.service.SysAraeService;
import com.epay.scanposp.service.SysCommonConfigService;

@Controller
@RequestMapping("api/common")
public class CommomServiceController {
	private static Logger logger = LoggerFactory.getLogger(CommomServiceController.class);
	
	@Autowired
	private SysAraeService sysAraeService ;
	@Autowired
	private BankService bankService ;
	@Autowired
	private BankNameService bankNameService ;
	@Autowired
	private BankSubService bankSubService ;
	@Autowired
	private MemberOpenidService memberOpenidService;
	@Autowired
	private MemberInfoService memberInfoService;
	@Autowired
	private BuAreaCodeService buAreaCodeService;
	
	@Autowired
	private SysCommonConfigService sysCommonConfigService;
	
	@Autowired
	private PayTypeService payTypeService;
	
	@Autowired
	private MemberAliOpenidService memberAliOpenidService;
	
	@ResponseBody
	@RequestMapping("/getAddrAreaList")
	public String getAddrAreaList(Model model, HttpServletRequest request) {
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		JSONObject result=new JSONObject();
		JSONObject resData=new JSONObject();
		try {
			String areaCode = "1";
			if(reqDataJson.containsKey("areacode") && !"".equals(reqDataJson.getString("areacode"))){
				areaCode = reqDataJson.getString("areacode");
			}
			BuAreaCodeExample buAreaCodeExample = new BuAreaCodeExample();
			buAreaCodeExample.or().andAreaparentidEqualTo(areaCode);
			buAreaCodeExample.setOrderByClause("areaCode");
			List<BuAreaCode> areaList = buAreaCodeService.selectByExample(buAreaCodeExample);
			
			resData.put("areaList", areaList);
			
			result.put("returnCode", "0000");
			result.put("returnMsg", "获取省市信息成功");
			result.put("resData", resData);
			System.out.println(result);
			return result.toString();
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
			return result.toString();
		}
	}
	@ResponseBody
	@RequestMapping("/getAreaList")
	public String getAreaList(Model model, HttpServletRequest request) {
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		JSONObject result=new JSONObject();
		JSONObject resData=new JSONObject();
		try {
			String parentId = "0";
			if(reqDataJson.containsKey("parentId") && !"".equals(reqDataJson.getString("parentId"))){
				parentId = reqDataJson.getString("parentId");
			}
			SysAreaExample sysAreaExample = new SysAreaExample();
			sysAreaExample.or().andParentIdEqualTo(parentId);
			List<SysArea> areaList = sysAraeService.selectByExample(sysAreaExample);
			
			resData.put("areaList", areaList);
			
			result.put("returnCode", "0000");
			result.put("returnMsg", "获取省市信息成功");
			result.put("resData", resData);
			System.out.println(result);
			return result.toString();
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
			return result.toString();
		}
	}
	
	@ResponseBody
	@RequestMapping("/getBankList")
	public String getBankList(Model model, HttpServletRequest request) {
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		JSONObject result=new JSONObject();
		JSONObject resData=new JSONObject();
		try {
			int bankLevel = reqDataJson.getInt("bankLevel");
			if(bankLevel == 1){
				BankExample bankExample = new BankExample();
				bankExample.setOrderByClause("sort asc");
				List<Bank> bankList = bankService.selectByExample(bankExample);
				resData.put("bankList", bankList);
			}else if(bankLevel == 2){
				Integer bankId = null;
				Integer cityId = null;
				String subBankName = null;
				BankSubExample bankSubExample = new BankSubExample();
				Criteria banSubCriteria = bankSubExample.createCriteria();
				if(reqDataJson.containsKey("bankId")){
					bankId = reqDataJson.getInt("bankId");
					banSubCriteria.andBankIdEqualTo(bankId);
				}
				if(reqDataJson.containsKey("cityId")){
					cityId = reqDataJson.getInt("cityId");
					banSubCriteria.andCityIdEqualTo(cityId);
				}
				if(reqDataJson.containsKey("subBankName")){
					subBankName = reqDataJson.getString("subBankName");
					banSubCriteria.andSubNameLike("%"+subBankName+"%");
				}
				
//				bankSubExample.or().andBankIdEqualTo(bankId).andCityIdEqualTo(cityId).andSubNameLike(subBankName);
				List<BankSub> subBankList = bankSubService.selectByExample(bankSubExample);
				resData.put("subBankList", subBankList);
			}else{
				throw new ArgException("银行参数错误");
			}
			
			result.put("returnCode", "0000");
			result.put("returnMsg", "获取银行信息成功");
			result.put("resData", resData);
			return result.toString();
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
			return result.toString();
		}
	}
	@ResponseBody
	@RequestMapping("/getKBankList")
	public String getKBankList(Model model, HttpServletRequest request) {
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		JSONObject result=new JSONObject();
		JSONObject resData=new JSONObject();
		try {
			BankNameExample bankNameExample = new BankNameExample();
			bankNameExample.or().andStatusEqualTo("0");
			List<BankName> list = bankNameService.selectByExample(bankNameExample);
			
			resData.put("bankList", list);
			result.put("returnCode", "0000");
			result.put("returnMsg", "获取银行信息成功");
			result.put("resData", resData);
			return result.toString();
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
			return result.toString();
		}
	}
	
	/**
	 * 检查用户是否在微信端登陆过  即判断是否可免登陆
	 * @param model
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkAutoLogin")
	public String checkAutoLogin(Model model, HttpServletRequest request) {
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		JSONObject result=new JSONObject();
		JSONObject resData=new JSONObject();
		String openid = reqDataJson.getString("openid");
		MemberOpenidExample memberOpenidExample = new MemberOpenidExample();
		memberOpenidExample.or().andOpenidEqualTo(openid);
		List<MemberOpenid> memberOpenidList = memberOpenidService.selectByExample(memberOpenidExample);
		result.put("returnCode", "0000");
		if(memberOpenidList.size()>0){
			MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(memberOpenidList.get(0).getMemberId());
			memberOpenidExample = new MemberOpenidExample();
			memberOpenidExample.or().andMemberIdEqualTo(memberOpenidList.get(0).getMemberId()).andOpenidNotEqualTo(openid);
			memberOpenidService.deleteByExample(memberOpenidExample);
			result.put("returnMsg", "存在微信登录信息");
			resData.put("memberInfo", memberInfo);
			resData.put("loginFlag", 1);
		}else{
			resData.put("loginFlag", 0);
			result.put("returnMsg", "不存在微信登录记录");
		}
		result.put("resData", resData);
		return result.toString();
	}
	
	/**
	 * 检查用户是否在支付宝登陆过  即判断是否可免登陆
	 * @param model
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkAliAutoLogin")
	public String checkAliAutoLogin(Model model, HttpServletRequest request) {
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		JSONObject result=new JSONObject();
		JSONObject resData=new JSONObject();
		String openid = reqDataJson.getString("openid");
		MemberAliOpenidExample memberAliOpenidExample = new MemberAliOpenidExample();
		memberAliOpenidExample.or().andOpenidEqualTo(openid);
		List<MemberAliOpenid> memberOpenidList = memberAliOpenidService.selectByExample(memberAliOpenidExample);
		result.put("returnCode", "0000");
		if(memberOpenidList.size()>0){
			MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(memberOpenidList.get(0).getMemberId());
			memberAliOpenidExample = new MemberAliOpenidExample();
			memberAliOpenidExample.or().andMemberIdEqualTo(memberOpenidList.get(0).getMemberId()).andOpenidNotEqualTo(openid);
			memberAliOpenidService.deleteByExample(memberAliOpenidExample);
			result.put("returnMsg", "存在支付宝登录信息");
			resData.put("memberInfo", memberInfo);
			resData.put("loginFlag", 1);
		}else{
			resData.put("loginFlag", 0);
			result.put("returnMsg", "不存在支付宝登录记录");
		}
		result.put("resData", resData);
		return result.toString();
	}
	
	
	@ResponseBody
	@RequestMapping("/getCommonConfig")
	public String getCommonConfig(Model model, HttpServletRequest request) {
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		JSONObject result=new JSONObject();
		try {
			String configName = reqDataJson.getString("configName");
			String value = "";
			SysCommonConfigExample sysCommonConfigExample = new SysCommonConfigExample();
			sysCommonConfigExample.or().andNameEqualTo(configName).andDelFlagEqualTo("0");
			List<SysCommonConfig> sysCommonConfig = sysCommonConfigService.selectByExample(sysCommonConfigExample);
			if (sysCommonConfig != null && sysCommonConfig.size() > 0) {
				value = sysCommonConfig.get(0).getValue();
			}
			
			result.put("returnCode", "0000");
			result.put("returnMsg", "获取系统配置成功");
			result.put("resData", value);
			System.out.println(result);
			return result.toString();
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
			return result.toString();
		}
	}
	@ResponseBody
	@RequestMapping("/getRouteCode")
	public String getRouteCode(Model model, HttpServletRequest request){
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		JSONObject result=new JSONObject();
		try {
			String payMethod = reqDataJson.getString("payMethod");
			String payType = reqDataJson.getString("payType");
			Integer memberId = reqDataJson.getInt("memberId");
			String value = "";
			PayTypeExample payTypeExample = new PayTypeExample();
			payTypeExample.createCriteria().andMemberIdEqualTo(memberId).andPayMethodEqualTo(payMethod).andPayTypeEqualTo(payType).andDelFlagEqualTo("0");
			List<PayType> payTypeList = payTypeService.selectByExample(payTypeExample);
			if(payTypeList == null || payTypeList.size() == 0){
				payTypeExample = new PayTypeExample();
				payTypeExample.createCriteria().andMemberIdEqualTo(0).andPayMethodEqualTo(payMethod).andPayTypeEqualTo(payType).andDelFlagEqualTo("0");
				payTypeList = payTypeService.selectByExample(payTypeExample);
				
			}
			if(payTypeList != null && payTypeList.size() > 0){
				value = payTypeList.get(0).getRouteCode();
			}
			result.put("returnCode", "0000");
			result.put("returnMsg", "获取系统配置成功");
			result.put("resData", value);
			System.out.println(result);
			return result.toString();
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
			return result.toString();
		}
	}
	
}
