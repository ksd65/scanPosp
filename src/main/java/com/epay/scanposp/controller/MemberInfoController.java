
package com.epay.scanposp.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.epay.scanposp.common.constant.MSConfig;
import com.epay.scanposp.common.constant.SysConfig;
import com.epay.scanposp.common.excep.ArgException;
import com.epay.scanposp.common.utils.CommonUtil;
import com.epay.scanposp.common.utils.DateUtil;
import com.epay.scanposp.common.utils.HttpUtil;
import com.epay.scanposp.common.utils.JsonBeanReleaseUtil;
import com.epay.scanposp.common.utils.SecurityUtil;
import com.epay.scanposp.common.utils.SendUrlUtil;
import com.epay.scanposp.common.utils.SignUtil;
import com.epay.scanposp.common.utils.StringUtil;
import com.epay.scanposp.common.utils.WxMessageUtil;
import com.epay.scanposp.common.utils.XmlUtil;
import com.epay.scanposp.common.utils.constant.SysCommonConfigConstant;
import com.epay.scanposp.entity.Bank;
import com.epay.scanposp.entity.BankName;
import com.epay.scanposp.entity.BankNameExample;
import com.epay.scanposp.entity.EpayCode;
import com.epay.scanposp.entity.EpayCodeExample;
import com.epay.scanposp.entity.MemberBank;
import com.epay.scanposp.entity.MemberBankExample;
import com.epay.scanposp.entity.MemberBindAcc;
import com.epay.scanposp.entity.MemberBindAccDtl;
import com.epay.scanposp.entity.MemberBindAccExample;
import com.epay.scanposp.entity.MemberInfo;
import com.epay.scanposp.entity.MemberInfoExample;
import com.epay.scanposp.entity.MemberInfoMore;
import com.epay.scanposp.entity.MemberOpenid;
import com.epay.scanposp.entity.MemberOpenidExample;
import com.epay.scanposp.entity.MemberQuickPay;
import com.epay.scanposp.entity.MlTradeDetail;
import com.epay.scanposp.entity.MlTradeDetailAll;
import com.epay.scanposp.entity.MlTradeDetailAllExample;
import com.epay.scanposp.entity.MlTradeResNotice;
import com.epay.scanposp.entity.PayRoute;
import com.epay.scanposp.entity.PayRouteExample;
import com.epay.scanposp.entity.PayType;
import com.epay.scanposp.entity.PayTypeExample;
import com.epay.scanposp.entity.RequestMsg;
import com.epay.scanposp.entity.RoutewayDraw;
import com.epay.scanposp.entity.RoutewayDrawExample;
import com.epay.scanposp.entity.StTradeDetail;
import com.epay.scanposp.entity.StTradeDetailExample;
import com.epay.scanposp.entity.SysCommonConfig;
import com.epay.scanposp.entity.SysCommonConfigExample;
import com.epay.scanposp.entity.SysOffice;
import com.epay.scanposp.entity.SysOfficeConfigOem;
import com.epay.scanposp.entity.SysOfficeConfigOemExample;
import com.epay.scanposp.entity.SysOfficeExample;
import com.epay.scanposp.entity.TradeDetailAll;
import com.epay.scanposp.service.AccountLogService;
import com.epay.scanposp.service.BankNameService;
import com.epay.scanposp.service.BankService;
import com.epay.scanposp.service.CommonService;
import com.epay.scanposp.service.EpayCodeService;
import com.epay.scanposp.service.MemberBankService;
import com.epay.scanposp.service.MemberBindAccDtlService;
import com.epay.scanposp.service.MemberBindAccService;
import com.epay.scanposp.service.MemberInfoMoreService;
import com.epay.scanposp.service.MemberInfoService;
import com.epay.scanposp.service.MemberOpenidService;
import com.epay.scanposp.service.MlTradeDetailAllService;
import com.epay.scanposp.service.MlTradeDetailService;
import com.epay.scanposp.service.MlTradeResNoticeService;
import com.epay.scanposp.service.PayRouteService;
import com.epay.scanposp.service.PayTypeService;
import com.epay.scanposp.service.RoutewayDrawService;
import com.epay.scanposp.service.StTradeDetailAllService;
import com.epay.scanposp.service.StTradeDetailService;
import com.epay.scanposp.service.SysCommonConfigService;
import com.epay.scanposp.service.SysOfficeConfigOemService;
import com.epay.scanposp.service.SysOfficeExtendService;
import com.epay.scanposp.service.SysOfficeService;
import com.epay.scanposp.service.TradeDetailAllService;
import com.epay.scanposp.service.TradeDetailService;
import com.epay.scanposp.thread.MlPayResultNoticeThread;
@Controller
@RequestMapping("api/memberInfo")
public class MemberInfoController {
	private static Logger logger = LoggerFactory.getLogger(MemberInfoController.class);

	@Autowired
	private MemberInfoMoreService memberInfoMoreService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private AccountLogService accountLogService;
	@Resource
	private SysOfficeService sysOfficeService;
	@Resource
	private MemberOpenidService memberOpenidService;
	@Resource
	private SysOfficeConfigOemService sysOfficeConfigOemService;
	@Resource
	private EpayCodeService epayCodeService;

	@Resource
	private MemberInfoService memberInfoService;

	@Resource
	private TradeDetailService tradeDetailService;
	@Resource
	private TradeDetailAllService tradeDetailAllService;
	@Resource
	private RoutewayDrawService routewayDrawService;
	@Resource
	private MemberBindAccService memberBindAccService;
	@Resource
	private BankService bankService;
	
	@Resource
	private MemberBankService memberBankService;
	
	@Resource
	private MlTradeDetailService mlTradeDetailService;
	
	@Resource 
	private MemberBindAccDtlService memberBindAccDtlService;
	
	@Resource 
	private MlTradeDetailAllService mlTradeDetailAllService;
	
	@Resource 
	private MlTradeResNoticeService mlTradeResNoticeService;
	
	@Resource
	
	private BankNameService bankNameService;
 
	@Resource
	
	private StTradeDetailService stTradeDetailService;
	
	@Resource
	
	private StTradeDetailAllService stTradeDetailAllService;
	@Resource
	private SysOfficeExtendService sysOfficeExtendService;
	
	@Autowired
	private PayRouteService payRouteService;
	
	@Autowired
	private PayTypeService payTypeService;
	
	@Autowired
	private SysCommonConfigService sysCommonConfigService;
	
	@Resource
	ThreadPoolTaskExecutor threadPoolTaskExecutor;
	
	@ResponseBody
	@RequestMapping("/memberInfo")
	public String memberInfo(Model model, HttpServletRequest request) {
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数

		JSONObject result = new JSONObject();
		MemberInfoMore memberInfoMore = memberInfoMoreService
				.selectMemberInfoMoreByMember(reqDataJson.getInt("memberId"));
		JSONObject resData = new JSONObject();
		resData.put("memberInfoMore", memberInfoMore);
		result.put("returnCode", "0000");
		result.put("returnMsg", "成功");
		result.put("resData", resData);
		return result.toString();
	}
	
	@ResponseBody
	@RequestMapping("/feeRate")
	public String feeRate(Model model, HttpServletRequest request) {
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数

		JSONObject result = new JSONObject();
		MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(reqDataJson.getInt("memberId"));
		JSONObject resData = new JSONObject();
		resData.put("rate", SysConfig.rate);
		resData.put("memberInfo", memberInfo);
		result.put("returnCode", "0000");
		result.put("returnMsg", "成功");
		result.put("resData", resData);
		return result.toString();
	}

	@ResponseBody
	@RequestMapping("/showChannel")
	public String showChannel(Model model, HttpServletRequest request) {
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数

		JSONObject result = new JSONObject();
		MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(reqDataJson.getInt("memberId"));
		JSONObject resData = new JSONObject();
		resData.put("rate", SysConfig.rate);
		resData.put("memberInfo", memberInfo);
		result.put("returnCode", "0000");
		result.put("returnMsg", "成功");
		result.put("resData", resData);
		return result.toString();
	}

	@ResponseBody
	@RequestMapping("/memberCenter")
	public String memberCenter(Model model, HttpServletRequest request) {
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		JSONObject result = new JSONObject();
		JSONObject resData = new JSONObject();
		SimpleDateFormat dateFormatLong = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat dateFormatShort = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		try {
			long t1 = System.currentTimeMillis();
			int memberId = reqDataJson.getInt("memberId");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(memberId);

			// 指定月份的第一天
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getMinimum(Calendar.DAY_OF_MONTH));
			Date startDate = dateFormatLong.parse(dateFormatShort.format(calendar.getTime()) + " 00:00:00");

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("memberId", reqDataJson.getInt("memberId"));
			paramMap.put("startDate", df.format(startDate));
			paramMap.put("endDate", df.format(new Date()));
			System.out.println(paramMap.toString());
			Double tradeMoneyCount = commonService.countTransactionMoneyByCondition(paramMap);
			tradeMoneyCount = tradeMoneyCount == null ? 0 : tradeMoneyCount;
			long t2 = System.currentTimeMillis();
			logger.info("当月交易总额 waste time :"+(t2-t1));
			// 今天开始时间
			Date begin = dateFormatLong.parse(dateFormatShort.format(new Date()) + " 00:00:00");
			// 今天结束时间
			Date end = dateFormatLong.parse(dateFormatShort.format(new Date()) + " 23:59:59");
			// 今天交易总额(包括快捷)
			paramMap.put("startDate", df.format(begin));
			paramMap.put("endDate",df.format(end));
			System.out.println(paramMap.toString());
			Double tradeMoneyCountToday = commonService.countTransactionMoneyByCondition(paramMap);
			tradeMoneyCountToday = tradeMoneyCountToday == null ? 0 : tradeMoneyCountToday;
			long t3 = System.currentTimeMillis();
			logger.info("当日交易总额 waste time :"+(t3-t2));
			//今天快捷支付交易额
			Double mlDrawMoneyCount = commonService.countMlDrawMoneyByCondition(paramMap);
			mlDrawMoneyCount = mlDrawMoneyCount == null ? 0 : mlDrawMoneyCount;
			long t4 = System.currentTimeMillis();
			logger.info("当日快捷支付总额 waste time :"+(t4-t3));
			paramMap.put("startDate", begin);
			paramMap.put("endDate",end);
			// 今天提现成功总额(不包括快捷)
			Double drawMoneyCount = commonService.countDrawMoneyByCondition(paramMap);
			drawMoneyCount = drawMoneyCount == null ? 0 : drawMoneyCount;
			long t5 = System.currentTimeMillis();
			logger.info("当日提现总额 waste time :"+(t5-t4));
			
			drawMoneyCount += mlDrawMoneyCount;//提现总额 = 提现成功金额 + 快捷支付金额 
			
			// 今天未提现总额
			Double unDrawMoneyCount = tradeMoneyCountToday - drawMoneyCount;

			resData.put("unDrawMoneyCount", new DecimalFormat("0.00").format(unDrawMoneyCount));
			resData.put("tradeMoneyCount", new DecimalFormat("0.00").format(tradeMoneyCount));
			resData.put("tradeMoneyDayCount", new DecimalFormat("0.00").format(tradeMoneyCountToday));
			resData.put("memberInfo", memberInfo);
			result.put("resData", resData);
			result.put("returnCode", "0000");
			result.put("returnMsg", "成功");
			return JsonBeanReleaseUtil.beanToJson(result, "yyyy-MM-dd HH:mm:ss");
		} catch (ParseException e) {
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
			return result.toString();
		}
	}

	/**
	 * 提现1所需
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/draw")
	public String draw(Model model, HttpServletRequest request) {
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		JSONObject result = new JSONObject();
		JSONObject resData = new JSONObject();
		SimpleDateFormat dateFormatLong = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat dateFormatShort = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		try {
			int memberId = reqDataJson.getInt("memberId");
			MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(memberId);

			// 今天开始时间
			Date begin = dateFormatLong.parse(dateFormatShort.format(new Date()) + " 00:00:00");
			// 今天结束时间
			Date end = dateFormatLong.parse(dateFormatShort.format(new Date()) + " 23:59:59");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("memberId", reqDataJson.getInt("memberId"));
			paramMap.put("startDate", begin);
			paramMap.put("endDate", end);
			// 今天提现成功总额(不包括快捷)
			Double drawMoneyCount = commonService.countDrawMoneyByCondition(paramMap);
			drawMoneyCount = drawMoneyCount == null ? 0 : drawMoneyCount;
			paramMap.put("startDate", df.format(begin));
			paramMap.put("endDate", df.format(end));
			// 今天交易总额(包括快捷)
			Double tradeMoneyCountToday = commonService.countTransactionMoneyByCondition(paramMap);
			tradeMoneyCountToday = tradeMoneyCountToday == null ? 0 : tradeMoneyCountToday;
			//今天快捷支付交易额
			Double mlDrawMoneyCount = commonService.countMlDrawMoneyByCondition(paramMap);
			mlDrawMoneyCount = mlDrawMoneyCount == null ? 0 : mlDrawMoneyCount;
			
			drawMoneyCount += mlDrawMoneyCount;//提现总额 = 提现成功金额 + 快捷支付金额 
			
			// 今天未提现总额
			Double unDrawMoneyCount = tradeMoneyCountToday - drawMoneyCount;

			resData.put("unDrawMoneyCount", new DecimalFormat("0.00").format(unDrawMoneyCount));
			resData.put("drawMoneyCount", new DecimalFormat("0.00").format(drawMoneyCount));
			resData.put("tradeMoneyCountToday", new DecimalFormat("0.00").format(tradeMoneyCountToday));
			resData.put("memberInfo", memberInfo);
			result.put("resData", resData);
			result.put("returnCode", "0000");
			result.put("returnMsg", "成功");
			return JsonBeanReleaseUtil.beanToJson(result, "yyyy-MM-dd HH:mm:ss");
		} catch (ParseException e) {
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
			return result.toString();
		}
	}
	
	
	@ResponseBody
	@RequestMapping("/checkToDraw")
	public String checkToDraw(Model model, HttpServletRequest request) {
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		JSONObject result = new JSONObject();
		int memberId = reqDataJson.getInt("memberId");
		MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(memberId);

		if (null != memberInfo && "1".equals(memberInfo.getDrawStatus())) {
			result.put("returnCode", "4004");
			result.put("returnMsg", "您当前的商户等级未开启D0提现权限，请联系客服提交材料获取权限。");
			return result.toString();
		}
		
		result.put("returnCode", "0000");
		result.put("returnMsg", "可提现");
		return JsonBeanReleaseUtil.beanToJson(result, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 提现2所需
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/toDraw")
	public String toDraw(Model model, HttpServletRequest request) {
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		JSONObject result = new JSONObject();
		JSONObject resData = new JSONObject();
		SimpleDateFormat dateFormatLong = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat dateFormatShort = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		try {
			int memberId = reqDataJson.getInt("memberId");
			MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(memberId);

			// 今天开始时间
			Date begin = dateFormatLong.parse(dateFormatShort.format(new Date()) + " 00:00:00");
			// 今天结束时间
			Date end = dateFormatLong.parse(dateFormatShort.format(new Date()) + " 23:59:59");
			Map<String, Object> paramNew = new HashMap<String, Object>();
			paramNew.put("memberId", reqDataJson.getInt("memberId"));
			paramNew.put("startDate", df.format(begin));
			paramNew.put("endDate", df.format(end));
			paramNew.put("txnType", "1");// 1:微信 2:支付宝 3:qq钱包
			paramNew.put("wxOrZfb", "w%");// w:微信 z:支付宝 q:qq钱包
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("memberId", reqDataJson.getInt("memberId"));
			paramMap.put("startDate", begin);
			paramMap.put("endDate", end);
			paramMap.put("txnType", "1");// 1:微信 2:支付宝 3:qq钱包
			paramMap.put("wxOrZfb", "w%");// w:微信 z:支付宝 q:qq钱包
			// 微信今天交易总额
			Double tradeMoneyCountTodayWx = commonService.countTransactionMoneyByCondition(paramNew);
			tradeMoneyCountTodayWx = tradeMoneyCountTodayWx == null ? 0 : tradeMoneyCountTodayWx;
			// 微信今天提现成功总额
			Double drawMoneyCountWx = commonService.countDrawMoneyByCondition(paramMap);
			drawMoneyCountWx = drawMoneyCountWx == null ? 0 : drawMoneyCountWx;
			// 微信今天未提现总额
			Double unDrawMoneyCountWx = tradeMoneyCountTodayWx - drawMoneyCountWx;

			paramNew.put("txnType", "2");// 1:微信 2:支付宝 3:qq钱包
			paramNew.put("wxOrZfb", "z%");// w:微信 z:支付宝 q:qq钱包
			paramMap.put("txnType", "2");// 1:微信 2:支付宝 3:qq钱包
			paramMap.put("wxOrZfb", "z%");// w:微信 z:支付宝 q:qq钱包
			// 支付宝今天交易总额
			Double tradeMoneyCountTodayZfb = commonService.countTransactionMoneyByCondition(paramNew);
			tradeMoneyCountTodayZfb = tradeMoneyCountTodayZfb == null ? 0 : tradeMoneyCountTodayZfb;
			// 支付宝今天提现成功总额
			Double drawMoneyCountZfb = commonService.countDrawMoneyByCondition(paramMap);
			drawMoneyCountZfb = drawMoneyCountZfb == null ? 0 : drawMoneyCountZfb;
			// 支付宝今天未提现总额
			Double unDrawMoneyCountZfb = tradeMoneyCountTodayZfb - drawMoneyCountZfb;

			paramNew.put("txnType", "3");// 1:微信 2:支付宝 3:qq钱包
			paramNew.put("wxOrZfb", "q%");// w:微信 z:支付宝 q:qq钱包
			paramMap.put("txnType", "3");// 1:微信 2:支付宝 3:qq钱包
			paramMap.put("wxOrZfb", "q%");// w:微信 z:支付宝 q:qq钱包
			// qq钱包今天交易总额
			Double tradeMoneyCountTodayQq = commonService.countTransactionMoneyByCondition(paramNew);
			tradeMoneyCountTodayQq = tradeMoneyCountTodayQq == null ? 0 : tradeMoneyCountTodayQq;
			// qq钱包今天提现成功总额
			Double drawMoneyCountQq = commonService.countDrawMoneyByCondition(paramMap);
			drawMoneyCountQq = drawMoneyCountQq == null ? 0 : drawMoneyCountQq;
			// qq钱包今天未提现总额
			Double unDrawMoneyCountQq = tradeMoneyCountTodayQq - drawMoneyCountQq;
			
			paramNew.put("txnType", "4");// 1:微信 2:支付宝 3:qq钱包  4:百度钱包
			paramNew.put("wxOrZfb", "b%");// w:微信 z:支付宝 q:qq钱包 b:百度钱包
			paramMap.put("txnType", "4");// 1:微信 2:支付宝 3:qq钱包  4:百度钱包
			paramMap.put("wxOrZfb", "b%");// w:微信 z:支付宝 q:qq钱包 b:百度钱包
			// 百度钱包今天交易总额
			Double tradeMoneyCountTodayBd = commonService.countTransactionMoneyByCondition(paramNew);
			tradeMoneyCountTodayBd = tradeMoneyCountTodayBd == null ? 0 : tradeMoneyCountTodayBd;
			// 百度钱包今天提现成功总额
			Double drawMoneyCountBd = commonService.countDrawMoneyByCondition(paramMap);
			drawMoneyCountBd = drawMoneyCountBd == null ? 0 : drawMoneyCountBd;
			// 百度钱包今天未提现总额
			Double unDrawMoneyCountBd = tradeMoneyCountTodayBd - drawMoneyCountBd;
			
			
			paramNew.put("txnType", "5");// 1:微信 2:支付宝 3:qq钱包 4:百度钱包  5:京东钱包
			paramNew.put("wxOrZfb", "j%");// w:微信 z:支付宝 q:qq钱包 b:百度钱包 j:京东钱包
			paramMap.put("txnType", "5");// 1:微信 2:支付宝 3:qq钱包 4:百度钱包  5:京东钱包
			paramMap.put("wxOrZfb", "j%");// w:微信 z:支付宝 q:qq钱包 b:百度钱包 j:京东钱包
			// 京东钱包今天交易总额
			Double tradeMoneyCountTodayJd = commonService.countTransactionMoneyByCondition(paramNew);
			tradeMoneyCountTodayJd = tradeMoneyCountTodayJd == null ? 0 : tradeMoneyCountTodayJd;
			// 京东钱包今天提现成功总额
			Double drawMoneyCountJd = commonService.countDrawMoneyByCondition(paramMap);
			drawMoneyCountJd = drawMoneyCountJd == null ? 0 : drawMoneyCountJd;
			// 京东钱包今天未提现总额
			Double unDrawMoneyCountJd = tradeMoneyCountTodayJd - drawMoneyCountJd;

			resData.put("unDrawMoneyCountWx", new DecimalFormat("0.00").format(unDrawMoneyCountWx));
			resData.put("unDrawMoneyCountZfb", new DecimalFormat("0.00").format(unDrawMoneyCountZfb));
			resData.put("unDrawMoneyCountQq", new DecimalFormat("0.00").format(unDrawMoneyCountQq));
			resData.put("unDrawMoneyCountBd", new DecimalFormat("0.00").format(unDrawMoneyCountBd));
			resData.put("unDrawMoneyCountJd", new DecimalFormat("0.00").format(unDrawMoneyCountJd));
			resData.put("memberInfo", memberInfo);
			result.put("resData", resData);
			result.put("returnCode", "0000");
			result.put("returnMsg", "成功");
			return JsonBeanReleaseUtil.beanToJson(result, "yyyy-MM-dd HH:mm:ss");
		} catch (ParseException e) {
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
			return result.toString();
		}
	}

	@ResponseBody
	@RequestMapping("/transactionLog")
	public String transactionLog(Model model, HttpServletRequest request) {
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		// JSONObject result=new JSONObject();
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			Date startDate = null;
			Date endDate = null;
			if ("1".equals(reqDataJson.getString("dataType"))) { // 表示当前月,取近7天的数据
				endDate = calendar.getTime();
				calendar.add(Calendar.DAY_OF_MONTH, -7);
				startDate = calendar.getTime();
			}else{//上个月，取7天之后的30天记录
				calendar.add(Calendar.DAY_OF_MONTH, -8);
				endDate = calendar.getTime();
				calendar.add(Calendar.DAY_OF_MONTH, -30);
				startDate = calendar.getTime();
			}
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("memberId", reqDataJson.getInt("memberId"));
			paramMap.put("startDate", df.format(startDate));
			paramMap.put("endDate", df.format(endDate));
			paramMap.put("orderByClause", " create_date desc");
			paramMap.put("limitStart", reqDataJson.getInt("limitStart"));
			paramMap.put("limitSize", reqDataJson.getInt("limitSize"));
			System.out.println(paramMap.toString());
			int transactionCounts = 0;
			List<TradeDetailAll> transactionLogList = null;
			Double moneyCount = 0.0;
			if ("1".equals(reqDataJson.getString("dataType"))) { // 表示当前月,查询实时记录表
				transactionCounts = tradeDetailAllService.selectAllTradeDetailCount(paramMap);
				transactionLogList = tradeDetailAllService.selectAllTradeDetail(paramMap);
				moneyCount = commonService.countTransactionMoneyByCondition(paramMap);
			}else{//查询历史表
				transactionCounts = tradeDetailAllService.selectAllTradeDetailCountHis(paramMap);
				transactionLogList = tradeDetailAllService.selectAllTradeDetailHis(paramMap);
				moneyCount = commonService.countTransactionMoneyByConditionHis(paramMap);
			}

			moneyCount = moneyCount == null ? 0 : moneyCount;
			Map<String, Object> resData = new HashMap<String, Object>();
			// JSONObject resData=new JSONObject();
			resData.put("transactionLogList", transactionLogList);
			resData.put("transactionCounts", transactionCounts);
			// resData.put("moneyCount", moneyCount);
			resData.put("moneyCount", new DecimalFormat("0.00").format(moneyCount));
			result.put("returnCode", "0000");
			result.put("returnMsg", "成功");
			result.put("resData", resData);
			// result.put("resData", JsonBeanReleaseUtil.beanToJson(resData,
			// "yyyy-MM-dd HH:mm:ss"));
			// System.out.println(JsonBeanReleaseUtil.beanToJson(resData,
			// "yyyy-MM-dd HH:mm:ss"));
			return JsonBeanReleaseUtil.beanToJson(result, "yyyy-MM-dd HH:mm:ss");
		} catch (Exception e) {
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
			return result.toString();
		}

	}

	@ResponseBody
	@RequestMapping("/getMemberInfoByEpayCode")
	public JSONObject getMemberInfoByEpayCode(HttpServletRequest request) {
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		JSONObject result = new JSONObject();
		JSONObject resData = new JSONObject();
		result.put("returnCode", "0000");
		result.put("returnMsg", "成功");
		try {
			String epayCodeStr = reqDataJson.getString("epayCode");
			if (StringUtil.isEmpty(epayCodeStr)) {
				throw new ArgException("支付码不能为空");
			}

			EpayCodeExample epayCodeExample = new EpayCodeExample();
			epayCodeExample.createCriteria().andPayCodeEqualTo(epayCodeStr).andDelFlagEqualTo("0");
			List<EpayCode> epayCodes = epayCodeService.selectByExample(epayCodeExample);
			if (epayCodes == null || epayCodes.size() != 1) {
				throw new ArgException("支付码不存在！");
			}

			EpayCode epayCode = epayCodes.get(0);
			String epayCodeStatus = epayCode.getStatus();
			resData.put("epayCodeStatus", epayCodeStatus);
			if ("5".equals(epayCodeStatus) || "7".equals(epayCodeStatus)) {
				MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(epayCode.getMemberId());
				resData.put("memberName", memberInfo.getShortName());
				resData.put("memberId", memberInfo.getId());
				resData.put("wxStatus", memberInfo.getWxStatus());
				resData.put("zfbStatus", memberInfo.getZfbStatus());
			}
			result.put("resData", resData);

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
		return result;
	}

	@ResponseBody
	@RequestMapping("/getMemberQRCodeInfo")
	public JSONObject getMemberQRInfo(HttpServletRequest request) {
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		JSONObject result = new JSONObject();
		JSONObject resData = new JSONObject();
		Integer memberID = reqDataJson.getInt("memberId");
		EpayCodeExample epayCodeExample = new EpayCodeExample();
		epayCodeExample.createCriteria().andMemberIdEqualTo(memberID).andDelFlagEqualTo("0");
		List<EpayCode> epayCodes = epayCodeService.selectByExample(epayCodeExample);
		if (epayCodes == null) {
			result.put("returnCode", "4004");
			result.put("returnMsg", "支付码不存在！");
			return result;
		} else if (epayCodes.size() > 1) {
			result.put("returnCode", "4004");
			result.put("returnMsg", "获取支付码信息出错！");
			return result;
		}
		result.put("returnCode", "0000");
		result.put("returnMsg", "成功");
		resData.put("qrCodePath", epayCodes.get(0).getPath());
		result.put("resData", resData);
		return result;
	}

	@ResponseBody
	@RequestMapping("/modifyPwd")
	public String modifyPwd(Model model, HttpServletRequest request, HttpServletResponse response) {
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		JSONObject result = new JSONObject();
		Integer memberId = reqDataJson.getInt("memberId");
		String oldPassword = reqDataJson.getString("oldPassword");
		String newPassword = reqDataJson.getString("newPassword");
		String confirmPassword = reqDataJson.getString("confirmPassword");
		try {
			if (memberId == null) {
				throw new ArgException("缺少会员id");
			}
			if (StringUtils.isEmpty(oldPassword)) {
				throw new ArgException("旧密码不能为空");
			}
			if (StringUtils.isEmpty(newPassword)) {
				throw new ArgException("新密码不能为空");
			}
			if (StringUtils.isEmpty(confirmPassword)) {
				throw new ArgException("确认密码不能为空");
			}
			if (!newPassword.equals(confirmPassword)) {
				throw new ArgException("新密码输入不一致");
			}
			MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(memberId);
			if (memberInfo == null) {
				result.put("returnCode", "4004");
				result.put("returnMsg", "用户不存在");
			}
			if (!memberInfo.getLoginPass().equals(SecurityUtil.md5Encode(oldPassword))) {
				result.put("returnCode", "4004");
				result.put("returnMsg", "旧密码输入错误");
			}
			memberInfo.setLoginPass(SecurityUtil.md5Encode(newPassword));
			memberInfoService.updateByPrimaryKeySelective(memberInfo);
			result.put("returnCode", "0000");
		} catch (ArgException e) {
			result.put("returnCode", "4002");
			result.put("returnMsg", e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
		}
		System.out.println("修改密码");
		return JsonBeanReleaseUtil.beanToJson(result, "yyyy-MM-dd HH:mm:ss");
	}

	@ResponseBody
	@RequestMapping("/drawList")
	public String drawList(Model model, HttpServletRequest request, HttpServletResponse response) {
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> resData = new HashMap<String, Object>();
		Integer memberId = reqDataJson.getInt("memberId");
		SimpleDateFormat dateFormatLong = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat dateFormatShort = new SimpleDateFormat("yyyy-MM-dd");

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		// JSONObject result=new JSONObject();

		try {
			if (memberId == null) {
				throw new ArgException("缺少会员id");
			}
			// 指定月份的第一天
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getMinimum(Calendar.DAY_OF_MONTH));
			Date startDate = dateFormatLong.parse(dateFormatShort.format(calendar.getTime()) + " 00:00:00");
			// 指定月份的最后一天
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			Date endDate = dateFormatLong.parse(dateFormatShort.format(calendar.getTime()) + " 23:59:59");

			RoutewayDrawExample routewayDrawExample = new RoutewayDrawExample();
			RoutewayDrawExample.Criteria routewayDrawCriteria = routewayDrawExample.createCriteria();
			routewayDrawCriteria.andCreateDateBetween(startDate, endDate).andMemberIdEqualTo(memberId)
					.andRespTypeNotEqualTo("R");
			int drawCnt = routewayDrawService.countByExample(routewayDrawExample);
			routewayDrawExample.setOrderByClause("create_date desc");
			routewayDrawExample.setLimitSize(reqDataJson.getInt("limitSize"));
			routewayDrawExample.setLimitStart(reqDataJson.getInt("limitStart"));

			List<RoutewayDraw> routewayDraws = routewayDrawService.selectByExample(routewayDrawExample);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("memberId", reqDataJson.getInt("memberId"));
			paramMap.put("startDate", dateFormatLong.format(startDate));
			paramMap.put("endDate", dateFormatLong.format(endDate));
			Double moneyCount = commonService.countDrawMoneyByCondition(paramMap);
			moneyCount = moneyCount == null ? 0 : moneyCount;
			DecimalFormat df = new DecimalFormat("0.00");
			resData.put("draws", routewayDraws);
			resData.put("drawCnt", drawCnt);
			resData.put("moneyCount", df.format(moneyCount));
			result.put("resData", resData);
			result.put("returnCode", "0000");
		} catch (ArgException e) {
			result.put("returnCode", "4002");
			result.put("returnMsg", e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
		}
		return JsonBeanReleaseUtil.beanToJson(result, "yyyy-MM-dd HH:mm:ss");
	}

	@ResponseBody
	@RequestMapping("/drawDetail")
	public String drawDetail(Model model, HttpServletRequest request, HttpServletResponse response) {
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> resData = new HashMap<String, Object>();
		Integer drawId = reqDataJson.getInt("drawId");
		Integer memberId = reqDataJson.getInt("memberId");
		try {
			if (drawId == null) {
				throw new ArgException("缺少唯一标识");
			}
			RoutewayDraw routewayDraw = routewayDrawService.selectByPrimaryKey(drawId);
			MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(memberId);
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			// Date d = df.parse(routewayDraw.getReqDate());
			// routewayDraw.setReqDate(DateUtil.getDateFormat(d, "yyyy-MM-dd
			// HH:mm:ss"));

			resData.put("memberInfo", memberInfo);
			resData.put("routewayDraw", routewayDraw);
			result.put("resData", resData);
			result.put("returnCode", "0000");
		} catch (ArgException e) {
			result.put("returnCode", "4002");
			result.put("returnMsg", e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
		}
		return JsonBeanReleaseUtil.beanToJson(result, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 获取商户绑定账号信息
	 * 
	 * @author hkz
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getMemberBindAccList")
	public String getMemberBindAccList(Model model, HttpServletRequest request, HttpServletResponse response) {
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> resData = new HashMap<String, Object>();
		Integer memberId;
		MemberBindAccExample memberBindAccExample = new MemberBindAccExample();
		
		List<MemberBindAcc> memberBindAccs = null;
		try {
			memberId=reqDataJson.getInt("memberId");
			if (memberId == null) {
				throw new ArgException("商户号为空");
			}
			memberBindAccExample.createCriteria().andMemberIdEqualTo(memberId);
			int binAccCnt = memberBindAccService.countByExample(memberBindAccExample);
			// 判断是否有绑卡，如果大于说明有绑卡数据，否则为空

			if (binAccCnt > 0) {
				memberBindAccs = memberBindAccService.selectByExample(memberBindAccExample);
				resData.put("memberBindAccList", memberBindAccs);
				result.put("returnMsg", "成功");
			} else {
				result.put("returnMsg", "用户未绑定账号");
			}
			resData.put("binAccCnt", binAccCnt);
			result.put("returnCode", "0000");
			result.put("resData", resData);
		} catch (ArgException e) {
			result.put("returnCode", "4002");
			result.put("returnMsg", e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
		}
		return JsonBeanReleaseUtil.beanToJson(result, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 商户绑卡动作
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/memberBindAcc")
	public String memberBindAcc(Model model, HttpServletRequest request, HttpServletResponse response) {
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		JSONObject result = new JSONObject();

		RequestMsg rm = new RequestMsg();
		RequestMsg rmg = new RequestMsg();
		List<MemberBindAcc> memberBindAccs = null;
		MemberBindAcc bindAcc = null;
		String jfState="0";
		String wjfState="0";
		boolean bkflag=true; 

	 	try {			
	 		//调用米联绑卡结果
			/**
			 * 实名认证设置参数
			 */	 
	 		MemberBindAcc memberBindAcc = (MemberBindAcc) JSONObject.toBean(reqDataJson.getJSONObject("memberBindAcc"),	MemberBindAcc.class);
	 		//判断同一个商户同一个卡号是否已经绑定，若绑定，不能重复
	 		MemberBindAccExample memberBindAccExample=new MemberBindAccExample();
	 		memberBindAccExample.createCriteria().andAccEqualTo(memberBindAcc.getAcc()).andMemberIdEqualTo(memberBindAcc.getMemberId());
	 		memberBindAccs=memberBindAccService.selectByExample(memberBindAccExample);
	 		//无积分绑卡	 		
	 		MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(memberBindAcc.getMemberId());
	 		
	 		
	 		
	 		int len=memberBindAcc.getBankId().trim().length();
			String bankName="";
			if(len<4){
				Bank bank=bankService.selectByPrimaryKey(Integer.parseInt(memberBindAcc.getBankId()));
				if(!(bank==null)){
					bankName=bank.getName();
				}
			}else{
				BankNameExample bankNameExample=new BankNameExample();
				bankNameExample.createCriteria().andBankCodeEqualTo(memberBindAcc.getBankId());
				List<BankName> bankNameList=bankNameService.selectByExample(bankNameExample);
				if(!(bankNameList==null||bankNameList.size()<1)){
					bankName=bankNameList.get(0).getBankName();
				}
			}
			if(bankName==null||bankName.equals("")){
	 			result.put("returnCode", "4004");
				result.put("returnMsg", "参数bankCode值不匹配");
				return result.toString();		
	 		}
	 		
	 		memberBindAcc.setBankName(bankName);	 		
	 		
	 		
	 		
	 		if(memberInfo==null){
	 			result.put("returnCode", "4004");
				result.put("returnMsg", "该商户未注册");
				return result.toString();		
	 		}
	 		MemberBankExample memberBankExample=new MemberBankExample();
			memberBankExample.createCriteria().andMemberIdEqualTo(memberBindAcc.getMemberId());
			List<MemberBank> memberBank=memberBankService.selectByExample(memberBankExample);
			if(memberBank==null||memberBank.size()<1){
				result.put("returnCode", "4004");
				result.put("returnMsg", "未找到结算卡信息");
				return result.toString();
			}  
			
			if(memberBank.get(0).getAccountNumber().equals(memberBindAcc.getAcc())){
				result.put("returnCode", "4004");
				result.put("returnMsg", "绑定卡不能与结算卡相同");
				return result.toString();
			}
			
	 		if(!memberBindAcc.getName().equals(memberBank.get(0).getAccountName())){
	 			result.put("returnCode", "4004");
				result.put("returnMsg", "持卡人姓名与结算账号姓名不一致");
				return result.toString();	
	 		}
	 		//无记录，未绑卡
	 		if(memberBindAccs==null||memberBindAccs.size()==0){
	 			bkflag=false;
	 		}
	 		//rm.setORDER_ID(MSConfig.orderId+new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));  
	 		rm.setORDER_ID(CommonUtil.getOrderCode());
	 		rm.setUSER_TYPE("02"); 	 					    
	 		rm.setSIGN_TYPE("03");
	 		rm.setMSG_TYPE("1011");
	 		rm.setCHECK_TYPE("01"); 
	 		rm.setCARD_TYPE("2");
			//填写变量
			/******************************开始 ****************************/
			rm.setID_NO(memberInfo.getCertNbr());//身份证
			rm.setNAME(memberBindAcc.getName());//账号对应的姓名
			rm.setACCOUNT_NO(memberBindAcc.getAcc());	  //账号
			rm.setPHONE_NO(memberBindAcc.getMobilePhone());//手机号码
			try {
				rm.setNAME(URLEncoder.encode(rm.getNAME(),"UTF-8"));
			} catch (UnsupportedEncodingException e) { 
				rm.setNAME("");
				e.printStackTrace();
			}
			/******************************结束 ***************************/
			
	 		
	 		/**
	 		 * 1.判断是否有绑卡记录
	 		 * 2.无：则进行有积分与无积分绑卡动作，并写入日志
	 		 * 3.有，判断哪类型未绑定，再次重现绑定，并写入日志
	 		 */
	 		
	 		if(bkflag){
	 			//有绑卡贵,判断无积分与有积分是否都绑定
	 			bindAcc=memberBindAccs.get(0);
	 			jfState=bindAcc.getJfState();
	 			wjfState=bindAcc.getWjfState();
	 			if(jfState!=null&&wjfState!=null&&jfState.equals("1")&&wjfState.equals("1")){
	 				result.put("returnCode", "4004");
					result.put("returnMsg", "该卡号已绑定");
					return result.toString();
	 			}
	 		
	 			if(jfState==null||jfState.equals("")||jfState.equals("0"))
	 			{
	 				rm.setUSER_ID(MSConfig.userIDJF);	
	 				rm.setBIND_TYPE("1");
	 				rmg=bindAcc(rm,memberBindAcc);
	 				if(rmg.getRESP_CODE().equals("0000")){	
	 					jfState="1";
	 					bindAcc.setJfState(jfState);
	 				}
	 			}
	 			if(wjfState==null||wjfState.equals("")||wjfState.equals("0"))
	 			{
	 				rm.setUSER_ID(MSConfig.userIDWJF);	
	 				rm.setBIND_TYPE("0");
	 				rmg=bindAcc(rm,memberBindAcc);
	 				if(rmg.getRESP_CODE().equals("0000")){	
	 					wjfState="1";
	 					bindAcc.setWjfState(wjfState);
	 				}
	 			}
	 			memberBindAccService.updateByExample(bindAcc, memberBindAccExample);
	 			result.put("returnCode", "0000");
	 			result.put("returnMsg", "成功");
	 			return result.toString();
	 			
	 		}else{
	 			//进行邮寄费无积分绑定
	 			rm.setUSER_ID(MSConfig.userIDJF);	
 				rm.setBIND_TYPE("1");
 				rmg=bindAcc(rm,memberBindAcc);
 				if(rmg.getRESP_CODE().equals("0000")){	
 					jfState="1";
 					memberBindAcc.setJfState(jfState);
 				}
 				rm.setUSER_ID(MSConfig.userIDWJF);	
 				rm.setBIND_TYPE("0");
 				rm.setORDER_ID(CommonUtil.getOrderCode());
 				//rm.setORDER_ID(MSConfig.orderId+new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())); 
 				rmg=bindAcc(rm,memberBindAcc);
 				if(rmg.getRESP_CODE().equals("0000")){	
 					wjfState="1";
 					memberBindAcc.setWjfState(wjfState);
 				}
 				if(jfState.equals("0")&&wjfState.equals("0")){
 					result.put("returnCode", "4004");
 					result.put("returnMsg", rmg.getRESP_DESCR());
 					return result.toString();	
 				}
	 		} 
	 		Date nowDate = new Date();
	 		memberBindAcc.setCreateDate(nowDate);
	 		memberBindAcc.setDelFlag("0");
			memberBindAccService.insertSelective(memberBindAcc);
			if ("1".equals(memberInfo.getDrawStatus())) {
				MemberInfo memberInfoUpdate = new MemberInfo();
				memberInfoUpdate.setId(memberInfo.getId());
				memberInfoUpdate.setDrawStatus("0");
				memberInfoUpdate.setStatus("0");
				memberInfoService.updateByPrimaryKeySelective(memberInfoUpdate);
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
	/**
	 * 绑卡公共程序部分
	 * @param rm
	 * @return
	 */
	public RequestMsg bindAcc(RequestMsg rm,MemberBindAcc memberBindAcc){
		
		SendUrlUtil sendUrlUtil = new SendUrlUtil();
		SignUtil signUtil=new SignUtil();
		String respResult;
		String signStr="";
		String singMsg1="";
		String signMsg;
		String paramss=null;
		RequestMsg rmg=new RequestMsg();
		MemberBindAccDtl memberBindAccDtl=new MemberBindAccDtl();
		try {
			signStr=rm.getORDER_ID()+rm.getUSER_TYPE()+rm.getUSER_ID()+rm.getMSG_TYPE()+rm.getCHECK_TYPE()+rm.getACCOUNT_NO()+rm.getPHONE_NO();
			signMsg=signUtil.makeSign(signStr, rm.getSIGN_TYPE(),rm.getBIND_TYPE());
			logger.debug("第二次MD5："+signMsg);
			rm.setSIGN(signMsg);
			//发送请求参数
			paramss="ORDER_ID="+rm.getORDER_ID()+
					"&USER_TYPE="+rm.getUSER_TYPE()+
					"&USER_ID="+rm.getUSER_ID()+	 
					"&SIGN="+rm.getSIGN()+	 
					"&SIGN_TYPE="+rm.getSIGN_TYPE()+	 
					"&BUS_CODE="+rm.getMSG_TYPE()+	  
					"&CHECK_TYPE="+rm.getCHECK_TYPE()+	 
					"&ID_NO="+rm.getID_NO()+	 
					"&NAME="+rm.getNAME()+	 
					"&ACCT_NO="+rm.getACCOUNT_NO()+	 
					"&PHONE_NO="+rm.getPHONE_NO();			
			logger.info("paramss="+MSConfig.bindAccUrl+"?"+paramss);			
		    respResult = sendUrlUtil.sendPost(MSConfig.bindAccUrl,paramss);
		    logger.info("返回结果："+respResult);
			XmlUtil xmlUtil = new XmlUtil();			
			xmlUtil.XmlToBean(rmg, respResult);
			memberBindAccDtl.setMemberId(memberBindAcc.getMemberId());
			memberBindAccDtl.setAcc(rm.getACCOUNT_NO());
			memberBindAccDtl.setBindType(rm.getBIND_TYPE());
			memberBindAccDtl.setBankId(memberBindAcc.getBankId());
			memberBindAccDtl.setBankName(memberBindAcc.getBankName());
			memberBindAccDtl.setOrderNum(memberBindAcc.getOrderNum());
			memberBindAccDtl.setName(memberBindAcc.getName());
			memberBindAccDtl.setMobilePhone(rm.getPHONE_NO());
			memberBindAccDtl.setRespCode(rmg.getRESP_CODE());
			memberBindAccDtl.setRespMsg(rmg.getRESP_DESCR());
			memberBindAccDtl.setCreateDate(new Date());
			memberBindAccDtl.setDelFlag("0");
			memberBindAccDtlService.insert(memberBindAccDtl);
			} catch (DocumentException e) {
				
				e.printStackTrace();
			}
			return rmg;
	}
	/**
	 * 商户快捷支付
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/memberQuickPay")
	public String memberQuickPay(Model model, HttpServletRequest request, HttpServletResponse response) {
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		//JSONObject result = new JSONObject();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> resData = new HashMap<String, Object>();
		MlTradeDetailAll mlTradeDetailAll =new MlTradeDetailAll();
 
	//	RequestMsg rm = new RequestMsg();
		MemberQuickPay memberQuickPay=new MemberQuickPay();
		SignUtil signUtil=new SignUtil();
		String respResult;
		String signStr=""; 
		String isJf="";
		String signMsg;
		String paramss=null;
		String resp_type="R";	
		String bindType="";
		double sETT_AM=0.0;
		double d0MemberDraw;
		double d0MemberFee;
		String rate;
		String txnType="";
		  String RESP_CODE="";
		  String RESP_DESC="";
	 	try {			
	 		//调用米联支付接口
	 		MlTradeDetail mlTradeDetail = (MlTradeDetail) JSONObject.toBean(reqDataJson.getJSONObject("mlTradeDetail"),	MlTradeDetail.class);
	 		MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(mlTradeDetail.getMemberId());	 
	 		isJf=mlTradeDetail.getIsJf();
	 		if(isJf==null&&isJf.equals("")){
	 			result.put("returnCode", "4004");
				result.put("returnMsg", "支付类型未选择");
				return JsonBeanReleaseUtil.beanToJson(result, "yyyy-MM-dd HH:mm:ss");	
	 		}
	 		if(memberInfo==null){
	 			result.put("returnCode", "4004");
				result.put("returnMsg", "该商户未注册");
				return JsonBeanReleaseUtil.beanToJson(result, "yyyy-MM-dd HH:mm:ss");		
	 		}
	 		MemberBindAccExample memberBindAccExample=new MemberBindAccExample();
	 		memberBindAccExample.createCriteria().andAccEqualTo(mlTradeDetail.getAcc()).andMemberIdEqualTo(mlTradeDetail.getMemberId());
	 		int accCnt=memberBindAccService.countByExample(memberBindAccExample);
	 		if(accCnt==0){
	 			result.put("returnCode", "4004");
				result.put("returnMsg", "该卡号未绑定");
				return JsonBeanReleaseUtil.beanToJson(result, "yyyy-MM-dd HH:mm:ss");
	 		}
	 		
	 		//结算金额
			if (memberInfo.getMlJfFee()==null){
				result.put("returnCode", "4004");
				result.put("returnMsg", "该商户有积分提现手续费为空");
				return JsonBeanReleaseUtil.beanToJson(result, "yyyy-MM-dd HH:mm:ss");
			}
			if (memberInfo.getMlWjfFee()==null){
				result.put("returnCode", "4004");
				result.put("returnMsg", "该商户无积分提现手续费为空");
				return JsonBeanReleaseUtil.beanToJson(result, "yyyy-MM-dd HH:mm:ss");
			}
			if(memberInfo.getMlJfRate()==null){
				result.put("returnCode", "4004");
				result.put("returnMsg", "该商户有积分扣率为空");
				return JsonBeanReleaseUtil.beanToJson(result, "yyyy-MM-dd HH:mm:ss");
			}
			if(memberInfo.getMlWjfRate()==null){
				result.put("returnCode", "4004");
				result.put("returnMsg", "该商户无积分扣率为空");
				return JsonBeanReleaseUtil.beanToJson(result, "yyyy-MM-dd HH:mm:ss");
			}
	 		
	 		/**
			 * 快捷支付设置参数
			 */
			//memberQuickPay.setORDER_ID(MSConfig.orderId+new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));  
			memberQuickPay.setORDER_ID(CommonUtil.getOrderCode());
			memberQuickPay.setUSER_TYPE("02"); 

	 					    
			memberQuickPay.setSIGN_TYPE("03");
			memberQuickPay.setBUS_CODE("3001");
			memberQuickPay.setORDER_TIME(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
			memberQuickPay.setPAY_TYPE("13");
			memberQuickPay.setCCT("CNY"); 
			memberQuickPay.setBG_URL(MSConfig.bgUrl);
			memberQuickPay.setPAGE_URL(MSConfig.pageUrl);
			
			  //变量
			  /******************************开始 ***************************/
			//订单金额
			memberQuickPay.setORDER_AMT(mlTradeDetail.getMoney().setScale(2, BigDecimal.ROUND_DOWN).toString());
			 //身份证
			memberQuickPay.setID_NO(memberInfo.getCertNbr());
			//结算卡号 
			memberQuickPay.setSETT_ACCT_NO(memberInfo.getCardNbr());
			//支付卡号
			memberQuickPay.setADD1(mlTradeDetail.getAcc());
			//商品名称
			memberQuickPay.setGOODS_NAME(memberQuickPay.getORDER_ID());
			//商品描述
			memberQuickPay.setGOODS_DESC("");
			//结算账户名
			memberQuickPay.setNAME(memberInfo.getContact());
			//memberQuickPay.setNAME(URLEncoder.encode(memberQuickPay.getNAME()));
			/*try {
				memberQuickPay.setNAME(URLEncoder.encode(memberQuickPay.getNAME(),"UTF-8"));
			} catch (UnsupportedEncodingException e) {
				memberQuickPay.setNAME("");
				e.printStackTrace();
			}*/
	 		//判断是否积分支付
	 		//积分支付
	 		if(isJf.equals("1")){
	 			memberQuickPay.setUSER_ID(MSConfig.userIDJF);	
	 			bindType="1";
	 			sETT_AM=Double.parseDouble(memberQuickPay.getORDER_AMT())*memberInfo.getMlJfRate().doubleValue()+memberInfo.getMlJfFee().doubleValue();	
	 			//交易手续费
	 			d0MemberFee=Double.parseDouble(memberQuickPay.getORDER_AMT())*memberInfo.getMlJfRate().doubleValue();
	 			//提现手续费
	 			d0MemberDraw=memberInfo.getMlJfFee().doubleValue();
	 			rate=memberInfo.getMlJfRate().toString();
	 			txnType="6";
	 		}else{
	 			memberQuickPay.setUSER_ID(MSConfig.userIDWJF);	
	 			bindType="0";
	 			sETT_AM=Double.parseDouble(memberQuickPay.getORDER_AMT())*memberInfo.getMlWjfRate().doubleValue()+memberInfo.getMlWjfFee().doubleValue();	
	 			//提现手续费
	 			d0MemberDraw=memberInfo.getMlWjfFee().doubleValue();
                //交易手续费
	 		 	d0MemberFee=Double.parseDouble(memberQuickPay.getORDER_AMT())*memberInfo.getMlWjfRate().doubleValue();
	 		 	txnType="7";
	 		 	rate=memberInfo.getMlWjfRate().toString();
	 		}
			
			
			
	 		memberQuickPay.setSETT_AMT(String.valueOf(new BigDecimal(Double.parseDouble(memberQuickPay.getORDER_AMT())-sETT_AM).setScale(2, BigDecimal.ROUND_DOWN)));
			//获取结算卡号对应银行名称
			MemberBankExample memberBankExample=new MemberBankExample();
			memberBankExample.createCriteria().andMemberIdEqualTo(memberInfo.getId()).andAccountNumberEqualTo(memberInfo.getCardNbr());
			List<MemberBank> memberBank=memberBankService.selectByExample(memberBankExample);
			if(memberBank==null||memberBank.size()<1){
				result.put("returnCode", "4004");
				result.put("returnMsg", "未找到结算卡号对应开户银行");
				return JsonBeanReleaseUtil.beanToJson(result, "yyyy-MM-dd HH:mm:ss");
			}
			int len=memberBank.get(0).getBankId().trim().length();
			String bankName="";
			if(len<4){
				Bank bank=bankService.selectByPrimaryKey(Integer.parseInt(memberBank.get(0).getBankId()));
				if(!(bank==null)){
					bankName=bank.getName();
				}
			}else{
				BankNameExample bankNameExample=new BankNameExample();
				bankNameExample.createCriteria().andBankCodeEqualTo(memberBank.get(0).getBankId());
				List<BankName> bankNameList=bankNameService.selectByExample(bankNameExample);
				if(!(bankNameList==null||bankNameList.size()<1)){
					bankName=bankNameList.get(0).getBankName();
				}
			}
			
			//银行综合名称 
			
			memberQuickPay.setCARD_INST_NAME(bankName);
			 
			memberQuickPay.setADD2(mlTradeDetail.getMemberId().toString());
			memberQuickPay.setADD3(memberInfo.getCode());  
			//手续费|结算金额|积分类型|第三方订单号
			memberQuickPay.setADD4(rate+"|"+memberQuickPay.getSETT_AMT()+"|"+txnType+"|"+mlTradeDetail.getOrderNum());
			memberQuickPay.setADD5(String.valueOf(d0MemberDraw));
			 
			  /******************************结束 ***************************/
	 		
	 		
			  signStr =memberQuickPay.getORDER_ID()+memberQuickPay.getORDER_AMT()+memberQuickPay.getORDER_TIME()+memberQuickPay.getPAY_TYPE()+memberQuickPay.getUSER_TYPE()+memberQuickPay.getUSER_ID()+memberQuickPay.getBUS_CODE();		
			 
			  signMsg=signUtil.makeSign(signStr, memberQuickPay.getSIGN_TYPE(),bindType);
              System.out.println("signStr="+signStr);
              System.out.println("signMsg="+signMsg);
			  memberQuickPay.setSIGN(signMsg);
			  paramss="ORDER_ID="+memberQuickPay.getORDER_ID()
					  +"&ORDER_AMT="+memberQuickPay.getORDER_AMT()
					  +"&ORDER_TIME="+memberQuickPay.getORDER_TIME()
					  + "&PAY_TYPE="+memberQuickPay.getPAY_TYPE()
					  + "&USER_TYPE="+memberQuickPay.getUSER_TYPE()
					  + "&BG_URL="+memberQuickPay.getBG_URL()
					  + "&PAGE_URL="+memberQuickPay.getPAGE_URL()
					  + "&USER_ID="+memberQuickPay.getUSER_ID()
					  + "&SIGN="+memberQuickPay.getSIGN()
					  + "&SIGN_TYPE="+memberQuickPay.getSIGN_TYPE()
					  + "&BUS_CODE="+memberQuickPay.getBUS_CODE()
					  + "&GOODS_NAME="+memberQuickPay.getGOODS_NAME()
					  + "&GOODS_DESC="+memberQuickPay.getGOODS_DESC()
					  + "&CCT="+memberQuickPay.getCCT()
					  + "&AGEN_NO="+""
					  + "&ID_NO="+memberQuickPay.getID_NO()
					  + "&SETT_ACCT_NO="+memberQuickPay.getSETT_ACCT_NO()
					  + "&CARD_INST_NAME="+memberQuickPay.getCARD_INST_NAME()
					  + "&NAME="+memberQuickPay.getNAME()
					  + "&SETT_AMT="+memberQuickPay.getSETT_AMT()
					  + "&ADD1="+memberQuickPay.getADD1()
					  + "&ADD2="+memberQuickPay.getADD2()
					  + "&ADD3="+memberQuickPay.getADD3()
					  + "&ADD4="+memberQuickPay.getADD4()
					  + "&ADD5="+memberQuickPay.getADD5();
						System.out.println("paramss="+paramss);
			  logger.info("paramss="+MSConfig.payUrl+"?"+paramss);			
			  respResult="";
  
			  logger.debug("respResult="+respResult);
 
			  mlTradeDetailAll.setMemberCode(memberInfo.getCode());
			  mlTradeDetailAll.setMemberId(mlTradeDetail.getMemberId());
			  mlTradeDetailAll.setMemberTradeRate(memberInfo.getMlJfFee());
			  mlTradeDetailAll.setMemberDrawFee(new BigDecimal(d0MemberDraw).setScale(2, BigDecimal.ROUND_DOWN));
			  mlTradeDetailAll.setMoney(new BigDecimal(memberQuickPay.getORDER_AMT()).setScale(2, BigDecimal.ROUND_DOWN));
			  mlTradeDetailAll.setMerchantCode(memberQuickPay.getUSER_ID());
			  mlTradeDetailAll.setMemberSettleMoney(new BigDecimal(memberQuickPay.getSETT_AMT()).setScale(2, BigDecimal.ROUND_DOWN));
			  mlTradeDetailAll.setOrderCode(memberQuickPay.getORDER_ID());
			  mlTradeDetailAll.setReqDate(memberQuickPay.getORDER_TIME());
			  mlTradeDetailAll.setRespType(resp_type);
			  mlTradeDetailAll.setD0Money(new BigDecimal(memberQuickPay.getSETT_AMT()).setScale(2, BigDecimal.ROUND_DOWN));
			  mlTradeDetailAll.setD0MemberDraw(new BigDecimal(d0MemberDraw).setScale(2, BigDecimal.ROUND_DOWN));
			  mlTradeDetailAll.setD0MemberFee(new BigDecimal(d0MemberFee).setScale(2, BigDecimal.ROUND_DOWN));
			  mlTradeDetailAll.setRespCode(RESP_CODE);
			  mlTradeDetailAll.setRespMsg(RESP_DESC);
			  mlTradeDetailAll.setTxnType(txnType);
			  mlTradeDetailAll.setSettleType("0");
			  mlTradeDetailAll.setPayTime("");
			
			  mlTradeDetailAll.setCreateBy(memberInfo.getContact());
			  mlTradeDetailAll.setCreateDate(new Date());
			  mlTradeDetailAll.setDelFlag("0");
			  mlTradeDetailAll.setOrderNum(mlTradeDetail.getOrderNum());
			  mlTradeDetailAll.setRemarks(mlTradeDetail.getRemarks());
			  mlTradeDetailAllService.insertSelective(mlTradeDetailAll);		
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
			return JsonBeanReleaseUtil.beanToJson(result, "yyyy-MM-dd HH:mm:ss");
		}
	 	if (resp_type.equals("E")){
	 		result.put("returnCode", "4004");
			result.put("returnMsg", RESP_DESC);
			return JsonBeanReleaseUtil.beanToJson(result, "yyyy-MM-dd HH:mm:ss");
	 	}
		result.put("returnCode", "0000");
		result.put("returnMsg", "成功");
		resData.put("memberQuickPay", memberQuickPay);
		result.put("resData",resData);
		return JsonBeanReleaseUtil.beanToJson(result, "yyyy-MM-dd HH:mm:ss");
	}
	@ResponseBody
	@RequestMapping("/memberQuickPayCallBack")
	public String memberQuickPayCallBack(HttpServletRequest request,HttpServletResponse response) { 
		
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		JSONObject result = new JSONObject();
		Integer memberId;
		StTradeDetailExample stTradeDetailExample=new StTradeDetailExample();
		String streq="";
		String content="";
		StTradeDetail stTradeDetail=new StTradeDetail();
		logger.info("回调获取参数结果:"+requestPRM.toString()); 
		//
		try{
			RequestMsg reMsg = (RequestMsg) JSONObject.toBean(reqDataJson.getJSONObject("requestMsg"),	RequestMsg.class);
			memberId=Integer.parseInt(reMsg.getADD2());
			MlTradeDetail mlTradeDetail =new MlTradeDetail();
			String temp="";
	
			temp="ORDER_ID	 ="+reMsg.getORDER_ID()                                                           
				+	"ORDER_AMT  ="+reMsg.getTRANS_AMT()                                                                
				+	"REF_NO	   ="+reMsg.getREF_NO()                                                                 
				+	"ORDER_TIME ="+reMsg.getTRANS_STIME()                                                               
				+	"PAY_AMOUNT ="+reMsg.getPAYAMOUNT()
				+	"RESP_CODE  ="+reMsg.getRESP_CODE()  
				+	"RESP_DESC	 ="+reMsg.getRESP_DESCR() 
				+	"USER_ID	   ="+reMsg.getUSER_ID()	   
				+	"SIGN	     ="+reMsg.getSIGN()	     
				+	"SIGN_TYPE	 ="+reMsg.getSIGN_TYPE()	 
				+	"BUS_CODE = ="+reMsg.getMSG_TYPE() 
				+	"CNY	    ="+reMsg.getCCT()	       
				+	"ADD5	    ="+reMsg.getADD5()	   
				+	"ADD1	    ="+reMsg.getADD1()	   
				+	"ADD2	   ="+reMsg.getADD2()	   	   
				+	"ADD3	   ="+reMsg.getADD3()	   	   
				+	"ADD4	   ="+reMsg.getADD4();					
			logger.info("回调获取参数:"+temp); 
			
		 
			
			MlTradeResNotice mlTradeResNotice =new MlTradeResNotice();
			mlTradeResNotice.setOrderId(reMsg.getORDER_ID());
			mlTradeResNotice.setOrderAmt(String.valueOf(reMsg.getTRANS_AMT()));
			mlTradeResNotice.setRespCode(reMsg.getRESP_CODE()  );
			mlTradeResNotice.setRespDesc(reMsg.getRESP_DESCR());
			mlTradeResNotice.setSignType(reMsg.getSIGN_TYPE());
			mlTradeResNotice.setBusCode(reMsg.getMSG_TYPE() );
			mlTradeResNotice.setCny(reMsg.getCCT()	);
			mlTradeResNotice.setAdd1(reMsg.getADD1());
			mlTradeResNotice.setAdd2(reMsg.getADD2());
			mlTradeResNotice.setAdd3(reMsg.getADD3());
			mlTradeResNotice.setAdd4(reMsg.getADD4());
			mlTradeResNotice.setAdd5(reMsg.getADD5());
			mlTradeResNotice.setRemarks(reMsg.getMESSAGE());
			mlTradeResNotice.setCreateDate(new Date());
			mlTradeResNotice.setDelFlag("0");
			mlTradeResNoticeService.insert(mlTradeResNotice);
			
			  mlTradeDetail.setOrderCode(reMsg.getORDER_ID() ); 		  
			  if(reMsg.getRESP_CODE().equals("0000")){
				  sendWxMessage(memberId,reMsg.getTRANS_STIME() ,reMsg.getTRANS_AMT().setScale(2, BigDecimal.ROUND_DOWN)+"");
				  mlTradeDetail.setRespType("S");
			  }else{
				  mlTradeDetail.setRespType("E");
			  }
			  String tradeDate = DateUtil.getDateFormat(new Date(), "yyyyMMdd");
			  double  d0MemberFee=  reMsg.getTRANS_AMT().doubleValue()*new BigDecimal(reMsg.getADD4()).doubleValue();
			  mlTradeDetail.setMemberId(memberId) ;
			  mlTradeDetail.setTxnDate(tradeDate);
			  mlTradeDetail.setMemberCode(reMsg.getADD3());
			  mlTradeDetail.setMerchantCode(reMsg.getUSER_ID()	);  
			  mlTradeDetail.setMoney(  reMsg.getTRANS_AMT().setScale(2, BigDecimal.ROUND_DOWN));
			  mlTradeDetail.setMemberTradeRate(new BigDecimal(reMsg.getADD4()).setScale(6, BigDecimal.ROUND_DOWN));
			  mlTradeDetail.setMemberDrawFee(new BigDecimal(reMsg.getADD5()).setScale(6, BigDecimal.ROUND_DOWN));
			  mlTradeDetail.setMemberSettleMoney(new BigDecimal( reMsg.getPAYAMOUNT()).setScale(2, BigDecimal.ROUND_DOWN));
			  mlTradeDetail.setD0Money(new BigDecimal(reMsg.getPAYAMOUNT()).setScale(2, BigDecimal.ROUND_DOWN));
			  mlTradeDetail.setD0MemberFee(new BigDecimal(d0MemberFee).setScale(2, BigDecimal.ROUND_DOWN));
			  mlTradeDetail.setD0MemberDraw(new BigDecimal(reMsg.getADD5()).setScale(6, BigDecimal.ROUND_DOWN));
			  mlTradeDetail.setAcc(reMsg.getADD1()	 );
			  mlTradeDetail.setOrderCode(reMsg.getORDER_ID());		  
			  mlTradeDetail.setTxnType(reMsg.getTXNTYPE());
			  mlTradeDetail.setReqDate(reMsg.getTRANS_STIME() );
			  mlTradeDetail.setRespDate(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
			  mlTradeDetail.setRespCode(reMsg.getRESP_CODE() );
			  mlTradeDetail.setRespMsg(reMsg.getRESP_DESCR());
			  mlTradeDetail.setPayTime(reMsg.getPAYCH_TIME()); 
			  mlTradeDetail.setSettleType("0");
			  mlTradeDetail.setCreateDate(new Date());
			  mlTradeDetail.setDelFlag("0");
			  mlTradeDetail.setOrderNum(reMsg.getOrderNum());
			  mlTradeDetailService.insertSelective(mlTradeDetail);
			  //判断是否第三方调用,如果订单号不为空，说明是第三方调用快捷支付
			  if(!(reMsg.getOrderNum()==null||"".equals(reMsg.getOrderNum()))){
				 
				  stTradeDetailExample.createCriteria().andOrderNumEqualTo(reMsg.getOrderNum());				  
				  List<StTradeDetail> stTradeDetailsts=stTradeDetailService.selectByExample(stTradeDetailExample);
				  if(!(stTradeDetailsts==null||stTradeDetailsts.size()==0)){	
					  stTradeDetail=stTradeDetailsts.get(0);
					  stTradeDetail.setTradeCode(reMsg.getRESP_CODE());
					  stTradeDetail.setTradeDesc(reMsg.getRESP_DESCR());
					  stTradeDetail.setUpdateBy("1");
					  stTradeDetail.setUpdateDate(new Date());
					  stTradeDetail.setDelFlag("0");
					  stTradeDetail.setSettAmt(mlTradeDetail.getMemberSettleMoney());
					  stTradeDetail.setCounts(0);
					  stTradeDetail.setPayTime(reMsg.getPAYCH_TIME());
					  stTradeDetailService.updateByPrimaryKey(stTradeDetail);
					  MlPayResultNoticeThread thread = new MlPayResultNoticeThread(sysOfficeExtendService,stTradeDetail,stTradeDetailService);
						threadPoolTaskExecutor.execute(thread);		
					  //通知第三方快捷支付结果
/*					  content="orderNum="+stTradeDetail.getOrderNum()
					  +"&orderAmt="+stTradeDetail.getOrderAmt()
					  +"&payType="+stTradeDetail.getPayType()
					  +"&bgUrl="+stTradeDetail.getBgUrl()
					  +"&pageUrl="+stTradeDetail.getPageUrl()
					  +"&memberCode="+stTradeDetail.getMemberCode()
					  +"&goodsName="+stTradeDetail.getGoodsName()
					  +"&goodsDesc="+stTradeDetail.getGoodsDesc()
					  +"&accNo="+stTradeDetail.getAccNo()
					  +"&settAmt="+mlTradeDetail.getMemberSettleMoney()
					  +"&add1="+stTradeDetail.getAdd1()
					  +"&add2="+stTradeDetail.getAdd2()
					  +"&add3="+stTradeDetail.getAdd3()
					  +"&orderTime="+reMsg.getPAYCH_TIME()
					  +"&returnCode="+reMsg.getRESP_CODE()
					  +"&returnMsg="+reMsg.getRESP_DESCR()
					  +"&add4="+stTradeDetail.getAdd4();*/
					  //处理签名字符串
					 /* Map<String,Object> paramMap = new HashMap<String, Object>();
						paramMap.put("memberCode", stTradeDetail.getMemberCode());
						paramMap.put("agtType", "3");
						SysOffice sysOffice = sysOfficeExtendService.getSysOfficeByMemberCode(paramMap);
						if(sysOffice != null){
							Map<String,String> params = new HashMap<String, String>();
							params.put("orderNum", stTradeDetail.getOrderNum());
							params.put("orderAmt", stTradeDetail.getOrderAmt().toString());
							params.put("payType", stTradeDetail.getPayType());
							params.put("bgUrl",stTradeDetail.getBgUrl());
							params.put("pageUrl", stTradeDetail.getPageUrl());
							params.put("memberCode", stTradeDetail.getMemberCode());
							params.put("goodsName", stTradeDetail.getGoodsName());
							params.put("goodsDesc", stTradeDetail.getGoodsDesc());
							params.put("accNo",stTradeDetail.getAccNo());
							params.put("settAmt", mlTradeDetail.getMemberSettleMoney().toString());
							params.put("add1", stTradeDetail.getAdd1());
							params.put("add2", stTradeDetail.getAdd2());
							params.put("add3", stTradeDetail.getAdd3());
							params.put("add4", stTradeDetail.getAdd4());
							params.put("orderTime", reMsg.getPAYCH_TIME());
							params.put("returnCode", reMsg.getRESP_CODE());
							params.put("returnMsg", reMsg.getRESP_DESCR());
							String srcStr = StringUtil.orderedKey(params);
							String signStr = EpaySignUtil.sign(sysOffice.getPrivateKeyRsa(), srcStr.toString());
							content=srcStr+"&signStr="+signStr;
							try {
							  streq= HttpUtil.sendPostRequest(stTradeDetail.getBgUrl(), content);
							  if(streq.startsWith("\"") && streq.endsWith("\"")){
								  streq = streq.replace("\\", "");
								  streq = streq.substring(1,streq.length()-1);
								}
							  if(!streq.equals("")){
								JSONObject resData = JSONObject.fromObject(streq);
								if(resData.containsKey("resCode") && "0000".equals(resData.getString("resCode"))){
								  stTradeDetail.setCbRes("1");
							  }
							  }
							} catch (Exception e) {
								logger.error("回调第三方异常:"+e.getMessage());
							}
						}
					 
					  stTradeDetailService.updateByPrimaryKey(stTradeDetail);
					  */
				  }
				  
			  }
			  
		}catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");		
			return JsonBeanReleaseUtil.beanToJson(result, "yyyy-MM-dd HH:mm:ss");
		}
	
		 result.put("returnCode", "0000"); 
	     return JsonBeanReleaseUtil.beanToJson(result, "yyyy-MM-dd HH:mm:ss");
	 
	}
	/**发送微信模板消息**/
	public void sendWxMessage(int memberId,String payTime,String money) throws ParseException {
		EpayCodeExample epayCodeExample = new EpayCodeExample();
		epayCodeExample.or().andMemberIdEqualTo(memberId).andStatusEqualTo("5");
		List<EpayCode> epayCodeList = epayCodeService.selectByExample(epayCodeExample);
		if(epayCodeList.size() > 0){
			String memberOfficeId = epayCodeList.get(0).getOfficeId();
			SysOffice sysOffice = getTopAgentOffice(memberOfficeId);
			if(null != sysOffice){
				MemberOpenidExample memberOpenidExample = new MemberOpenidExample();
				memberOpenidExample.or().andMemberIdEqualTo(memberId);
				List<MemberOpenid> memberOpenidList = memberOpenidService.selectByExample(memberOpenidExample);
				if(memberOpenidList.size()>0){
					MemberOpenid memberOpenid = memberOpenidList.get(0);
					if(null != memberOpenid.getOpenid() && !"".equals(memberOpenid.getOpenid())){
						SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String messageTitle = "恭喜您有一笔收款到账";
						String remark = "详细信息请查看交易记录";
						MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(memberId);
//						String payTime = msResultNotice.getPayTime();
						if(null != payTime && !"".equals(payTime) && payTime.length() >= 14){
							payTime = sdf.format(new SimpleDateFormat("yyyyMMddHHmmss").parse(payTime.length()>14?payTime.substring(0,14):payTime));
						}else{
							payTime = sdf.format(new Date());
						}
						if("2".equals(sysOffice.getAgtType())){
							SysOfficeConfigOemExample sysOfficeConfigOemExample = new SysOfficeConfigOemExample();
							sysOfficeConfigOemExample.or().andOfficeIdEqualTo(sysOffice.getId());
							List<SysOfficeConfigOem> officeConfogList = sysOfficeConfigOemService.selectByExample(sysOfficeConfigOemExample);
							if(officeConfogList.size()>0){
								int expiresSeconds = 7200;//单位秒
								SysOfficeConfigOem officeConfigOEM = officeConfogList.get(0);
								String accessToken = officeConfigOEM.getAccessToken();
								Date updateDate = officeConfigOEM.getUpdateDate();
								if(null != accessToken && !"".equals(accessToken) && ((new Date()).getTime() < updateDate.getTime()+expiresSeconds*1000)){
									//accessToken有效
									WxMessageUtil.sendTmpMsgSKTZOEM(accessToken,officeConfigOEM.getWxTemplateid(), memberOpenid.getOpenid(), messageTitle, memberInfo.getName(), money + "元", payTime, remark);
								}else{
									String result = HttpUtil.sendGetRequest("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + officeConfigOEM.getWxAppid() + "&secret=" + officeConfigOEM.getWxAppsecret());
									JSONObject resultJson = JSONObject.fromObject(result);
									if (resultJson.has("access_token")) {
										accessToken = resultJson.getString("access_token");
										updateDate = new Date();
										officeConfigOEM.setAccessToken(accessToken);
										officeConfigOEM.setUpdateDate(new Date());
										sysOfficeConfigOemService.updateByPrimaryKeySelective(officeConfigOEM);
										WxMessageUtil.sendTmpMsgSKTZOEM(accessToken,officeConfigOEM.getWxTemplateid(), memberOpenid.getOpenid(), messageTitle, memberInfo.getName(), money + "元", payTime, remark);
									}
								}
							}
						}else{
							WxMessageUtil.sendTmpMsgSKTZ(memberOpenid.getOpenid(), messageTitle, memberInfo.getName(), money + "元", payTime, remark);
						}
					}
				}
				
			}
		}
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
	 * 快捷支付，米联页面回调接口获取用户身份证信息。
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/miPayBack")
	public String miPayBack(HttpServletRequest request,HttpServletResponse response) { 
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		String orderId=null;
		orderId=reqDataJson.optString("orderId");
		MlTradeDetailAllExample mlTradeDetailAllExample=new MlTradeDetailAllExample();
		mlTradeDetailAllExample.createCriteria().andOrderCodeEqualTo(orderId);
 		List<MlTradeDetailAll> mlTradeDetailAll=mlTradeDetailAllService.selectByExample(mlTradeDetailAllExample);
 		JSONObject result = new JSONObject();
		if(!(mlTradeDetailAll==null||mlTradeDetailAll.size()==0)){			
			MemberInfoMore memberInfoMore = memberInfoMoreService
					.selectMemberInfoMoreByMember(mlTradeDetailAll.get(0).getMemberId());
			JSONObject resData = new JSONObject();
			resData.put("memberInfoMore", memberInfoMore);
			result.put("returnCode", "0000");
			result.put("returnMsg", "成功");
			result.put("resData", resData);				
			}else{				
				result.put("returnCode", "0001");
				result.put("returnMsg", "失败");
			}
		return result.toString();
	}
	/**
	 * 第三方快捷支付，米联回调后，重新读取用户订单信息，返回到第三
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/st/miPayBack")
	public String stmiPayBack(HttpServletRequest request,HttpServletResponse response) { 
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		String orderId=null;
		orderId=reqDataJson.optString("orderId");
		MlTradeDetailAllExample mlTradeDetailAllExample=new MlTradeDetailAllExample();
		mlTradeDetailAllExample.createCriteria().andOrderCodeEqualTo(orderId);
 		List<MlTradeDetailAll> mlTradeDetailAll=mlTradeDetailAllService.selectByExample(mlTradeDetailAllExample);
 		StTradeDetailExample stTradeDetailExample=new StTradeDetailExample();
 		List<StTradeDetail> stTradeDetail;
 		
 		JSONObject result = new JSONObject();
		if(!(mlTradeDetailAll==null||mlTradeDetailAll.size()==0)){		
			stTradeDetailExample.createCriteria().andOrderNumEqualTo(mlTradeDetailAll.get(0).getOrderNum());
			stTradeDetail=stTradeDetailService.selectByExample(stTradeDetailExample); 
			JSONObject resData = new JSONObject();
			if(!(stTradeDetail==null||stTradeDetail.size()==0)){
				
			
				resData.put("stTradeDetail", stTradeDetail.get(0));
				result.put("returnCode", "0000");
				result.put("returnMsg", "成功");
				result.put("resData", resData);		
			}else{
				result.put("returnCode", "0001");
				result.put("returnMsg", "失败");
			}
			
			}else{				
				result.put("returnCode", "0001");
				result.put("returnMsg", "失败");
			}
		return result.toString();
	}
	
	
	@ResponseBody
	@RequestMapping("/getMemberPayTypeByCode")
	public JSONObject getMemberPayTypeByCode(HttpServletRequest request) {
		JSONObject requestPRM = (JSONObject) request.getAttribute("requestPRM");
		JSONObject reqDataJson = requestPRM.getJSONObject("reqData");// 获取请求参数
		JSONObject result = new JSONObject();
		JSONObject resData = new JSONObject();
		result.put("returnCode", "0000");
		result.put("returnMsg", "成功");
		try {
			String memberCode = reqDataJson.getString("memberCode");
			if (StringUtil.isEmpty(memberCode)) {
				throw new ArgException("商户编码不能为空");
			}
			MemberInfoExample memberInfoExample = new MemberInfoExample();
			memberInfoExample.createCriteria().andCodeEqualTo(memberCode).andDelFlagEqualTo("0");
			List<MemberInfo> memberList = memberInfoService.selectByExample(memberInfoExample);
			if(memberList == null || memberList.size()!=1){
				throw new ArgException("商户不存在！");
			}
			String payMethod = reqDataJson.getString("payMethod");
			MemberInfo memberInfo = memberList.get(0);
			
			PayTypeExample payTypeExample = new PayTypeExample();
			payTypeExample.createCriteria().andMemberIdEqualTo(memberInfo.getId()).andPayMethodEqualTo(payMethod).andDelFlagEqualTo("0");
			List<PayType> payTypeList = payTypeService.selectByExample(payTypeExample);
			if(payTypeList == null || payTypeList.size() == 0){
				payTypeExample = new PayTypeExample();
				payTypeExample.createCriteria().andMemberIdEqualTo(0).andPayMethodEqualTo(payMethod).andDelFlagEqualTo("0");
				payTypeList = payTypeService.selectByExample(payTypeExample);
				
			}
			String typeStr = "";
			if(payTypeList != null && payTypeList.size() > 0){
				if(payTypeList!=null&&payTypeList.size()>0){
					for(int i=0;i<payTypeList.size();i++){
						typeStr += payTypeList.get(i).getPayType()+"#";
					}
				}
			}
			
			resData.put("payTypeList", typeStr);
			
			result.put("resData", resData);

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
		return result;
	}
	
	private String getRouteCode(){
		String routeCode = SysConfig.passCode;
		SysCommonConfigExample sysCommonConfigExample = new SysCommonConfigExample();
		sysCommonConfigExample.or().andNameEqualTo(SysCommonConfigConstant.SYS_ROUTE_CODE).andDelFlagEqualTo("0");
		List<SysCommonConfig> sysCommonConfig = sysCommonConfigService.selectByExample(sysCommonConfigExample);
		if (sysCommonConfig != null && sysCommonConfig.size() > 0) {
			routeCode = sysCommonConfig.get(0).getValue();
		}
		return routeCode;
	}
}
