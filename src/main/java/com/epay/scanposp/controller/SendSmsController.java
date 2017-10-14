package com.epay.scanposp.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
import com.epay.scanposp.common.utils.CaptchaUtils;
import com.epay.scanposp.common.utils.HttpUtil;
import com.epay.scanposp.common.utils.ValidateUtil;
import com.epay.scanposp.entity.MemberInfoExample;
import com.epay.scanposp.entity.VerifyCode;
import com.epay.scanposp.entity.VerifyCodeExample;
import com.epay.scanposp.service.MemberInfoService;
import com.epay.scanposp.service.RegisterTmpService;
import com.epay.scanposp.service.VerifyCodeService;

@Controller
@RequestMapping("api/sendSms")
public class SendSmsController {
	private static Logger logger = LoggerFactory.getLogger(SendSmsController.class);
	
	@Autowired
	private VerifyCodeService verifyCodeService;
	
	@Autowired
	private RegisterTmpService registerTmpService;
	
	@Autowired
	private MemberInfoService memberInfoService;
	
	@ResponseBody
	@RequestMapping("/toSend")
	public JSONObject sendSms(Model model, HttpServletRequest request){
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		JSONObject result=new JSONObject();
		
		String mobilePhone = reqDataJson.getString("mobilePhone");
		//type 标记该验证码的作用类型
		String type = reqDataJson.getString("type");
		if(ValidateUtil.isMbPhone(mobilePhone)){
			String captcha = CaptchaUtils.getRandomNum(4);
			String content = "";
			if(type.equals("1")){
				content = "欢迎注册"+SysConfig.smsFromName+"，手机验证码："+captcha+"，验证码在30分钟内有效";
			}else{
				result.put("returnCode", "4004");
				result.put("returnMsg", "事件类型不正确");
				return result;
			}
			
			MemberInfoExample memberInfoExample = new MemberInfoExample();
			memberInfoExample.or().andMobilePhoneEqualTo(mobilePhone).andStatusNotEqualTo("1");
			int isPhoneRegister = memberInfoService.countByExample(memberInfoExample);
			if(isPhoneRegister >0){
				result.put("returnCode", "4004");
				result.put("returnMsg", "该手机号码已注册");
				return result;
			}
			
			Map<String, String> p = new HashMap<String, String>();
			p.put("username", SysConfig.smsUsername);
			p.put("password", SysConfig.smsPassword);
			p.put("from", SysConfig.smsFrom);
			p.put("to", mobilePhone);
			p.put("content", content);
			p.put("presendTime", "");
			String msg = HttpUtil.sendPostCharsetRequest("http://219.133.59.101/GsmsHttp", p, "gbk");
			System.out.println("msg>>>>>>>>>>>>>>>>>>>"+msg);
			//失效所有验证码
			//=====执行有错误
//			VerifyCode verifyCode = new VerifyCode();
//			verifyCode.setStatus("0");
//			VerifyCodeExample verifyCodeExample = new VerifyCodeExample();
//			verifyCodeExample.or().andMobilePhoneEqualTo(mobilePhone).andTypeEqualTo(type);
//			verifyCodeService.updateByExampleSelective(verifyCode, verifyCodeExample);
			Map<String,Object> paramMap = new HashMap<String, Object>();
			paramMap.put("status", "0");
			paramMap.put("mobilePhone", mobilePhone);
			paramMap.put("type", type);
			verifyCodeService.updateCodeStateByType(paramMap);
			
			VerifyCode verifyCode = new VerifyCode();
			verifyCode.setVerifyCode(captcha);
			verifyCode.setMobilePhone(mobilePhone);
			verifyCode.setRemarks("注册验证码");
			verifyCode.setCreateDate(new Date());
			verifyCode.setStatus("1");
			verifyCode.setType(type);
			verifyCode.setMsgCode(msg);
			verifyCodeService.insertSelective(verifyCode);
//			logger.info("短信返回="+msg);
			
			result.put("returnCode", "0000");
			result.put("returnMsg", "成功");
		}else{
			result.put("returnCode", "4004");
			result.put("returnMsg", "手机号不正确");
		}
		return result;
	}
	public static void main(String[] args) {
		Map<String, String> p = new HashMap<String, String>();
		p.put("key", "wzrqim");
		p.put("secret", "7wdkiayN");
		p.put("from", "001");
		p.put("to", "8618259171844");
		String text="";
		p.put("text", "【无限云】您的验证码为：000，请在一分钟内使用，谢谢！");
//		System.out.println(text);
		String msg = HttpUtil.sendGetCharsetRequest("http://api.paasoo.com/json", p, "utf-8");
		System.out.println("msg>>>>>>>>>>>>>>>>>>>"+msg);
//		String msg = "{\"status\":\"0\",\"messageid\":\"0159c3-d7ce15-64e\"}";
//		JSONObject jo = JSONObject.fromObject(msg);
//		System.out.println(jo.getString("messageid"));
//		Map<String, String> p = new HashMap<String, String>();
//		p.put("key", "wzrqim");
//		p.put("secret", "7wdkiayN");
//		p.put("messageid", "0159c3-d7ce15-64e");
//		String param = HttpUtil.createPostParamsFromMap(p);
//		String msg = HttpUtil.sendGetRequest("http://clientsms.paasoo.com/api/dlr?"+param);
////		String msg = "[{\"type\":\"dlr\",\"messageid\":\"0159c3-d7ce15-64e\",\"to\":\"8618259171844\",\"status\":0,\"drStatus\":0,\"drStatuscode\":\"delivered\",\"drErrcode\":null,\"price\":0.05,\"counts\":1}]";
//		System.out.println("msg>>>>>>>>>>>>>>>>>>>"+msg);
	}
}
