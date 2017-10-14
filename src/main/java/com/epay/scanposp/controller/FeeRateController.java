package com.epay.scanposp.controller;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.epay.scanposp.common.constant.SysConfig;
import com.epay.scanposp.common.utils.JsonBeanReleaseUtil;
import com.epay.scanposp.entity.MemberInfo;
import com.epay.scanposp.service.MemberInfoService;

@Controller
@RequestMapping("api/feeRate")
public class FeeRateController {
	private static Logger logger = LoggerFactory.getLogger(FeeRateController.class);
	@Autowired
	private MemberInfoService memberInfoService;
	@ResponseBody
	@RequestMapping("/getRate")
	public String pay(Model model, HttpServletRequest request) {
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		JSONObject result=new JSONObject();
		JSONObject resData=new JSONObject();
		try {
			int memberId = reqDataJson.getInt("memberId");
			MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(memberId);
			
			resData.put("rate", SysConfig.rate);
			resData.put("memberInfo", memberInfo);
			result.put("resData", resData);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
			return result.toString();
		}
		result.put("returnCode", "0000");
		result.put("returnMsg", "成功");
		return JsonBeanReleaseUtil.beanToJson(result, "yyyy-MM-dd HH:mm:ss");
	}

	
}
