package com.epay.scanposp.thread;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epay.scanposp.common.utils.HttpUtil;
import com.epay.scanposp.common.utils.StringUtil;
import com.epay.scanposp.common.utils.epaySecurityUtil.EpaySignUtil;
import com.epay.scanposp.entity.DrawResultNotice;
import com.epay.scanposp.entity.EpayCode;
import com.epay.scanposp.entity.EpayCodeExample;
import com.epay.scanposp.entity.MemberInfo;
import com.epay.scanposp.entity.SysOffice;
import com.epay.scanposp.entity.SysOfficeExample;
import com.epay.scanposp.service.AccountService;
import com.epay.scanposp.service.DrawResultNoticeService;
import com.epay.scanposp.service.EpayCodeService;
import com.epay.scanposp.service.MemberInfoService;
import com.epay.scanposp.service.SysOfficeService;

public class DrawResultNoticeThread implements Runnable {
	private static Logger logger = LoggerFactory.getLogger(DrawResultNoticeThread.class);
	private EpayCodeService epayCodeService;
	private DrawResultNoticeService drawResultNoticeService;
	private MemberInfoService memberInfoService;
	private AccountService accountService;
	private SysOfficeService sysOfficeService;
	private DrawResultNotice drawResultNotice;
	
	public DrawResultNoticeThread(DrawResultNotice drawResultNotice,
			EpayCodeService epayCodeService,
			DrawResultNoticeService drawResultNoticeService,
			MemberInfoService memberInfoService,
			AccountService accountService,
			SysOfficeService sysOfficeService
			) {
		super();
		this.drawResultNotice = drawResultNotice;
		this.epayCodeService = epayCodeService;
		this.drawResultNoticeService = drawResultNoticeService;
		this.memberInfoService = memberInfoService;
		this.accountService = accountService;
		this.sysOfficeService = sysOfficeService;
	}
	
	@Override
	public void run() {
		System.out.println("==============提现通知线程开始执行=================");
		MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(drawResultNotice.getMemberId());
		SysOffice sysOffice = getSysOffice(memberInfo);
		if(sysOffice != null){
			JSONObject paramJson = new JSONObject();
			//商户编号
			String memberCode = memberInfo.getCode();
			paramJson.put("memberCode", memberCode);
			//实际提现金额
			String drawAmount = drawResultNotice.getDrawamount().toString();
			paramJson.put("drawAmount", drawAmount);
			//提现手续费
			String drawFee = drawResultNotice.getDrawfee().toString();
			paramJson.put("drawFee", drawFee);
			//提现时间
			paramJson.put("drawTime", drawResultNotice.getDrawTime());
			//交易手续费
			String tradeFee = drawResultNotice.getTradefee().toString();
			paramJson.put("tradeFee", tradeFee);
			//订单流水号
			paramJson.put("orderNum", drawResultNotice.getOrderNumOuter());
			//应答类型
			paramJson.put("respType", drawResultNotice.getRespType());
			//应答码
			paramJson.put("resultCode", drawResultNotice.getResultCode());
			//应答描述
			paramJson.put("resultMsg", drawResultNotice.getResultMessage());
			String srcStr = StringUtil.orderedKey(paramJson);
			String signStr = EpaySignUtil.sign(sysOffice.getPrivateKeyRsa(), srcStr.toString());
			paramJson.put("signStr", signStr);
			String callBackUrl = drawResultNotice.getReturnUrl();
			String ret = "";
			try {
				System.out.println("darwNoticeJson===="+paramJson.toString());
				ret = HttpUtil.sendPostRequest(callBackUrl, paramJson.toString());
				if(ret.startsWith("\"") && ret.endsWith("\"")){
					ret = ret.replace("\\", "");
					ret = ret.substring(1,ret.length()-1);
				}
				JSONObject resData = JSONObject.fromObject(ret);
				drawResultNotice.setCounts(drawResultNotice.getCounts()+1);
				if(resData.containsKey("resCode") && "0000".equals(resData.getString("resCode"))){
					drawResultNotice.setStatus("3");
				}else{
					if(drawResultNotice.getCounts() > 10){
						drawResultNotice.setStatus("4");
					}
				}
				
			} catch (Exception e) {
				logger.error(e.getMessage());
				if(drawResultNotice.getCounts() > 10){
					drawResultNotice.setStatus("4");
				}else{
					drawResultNotice.setCounts(drawResultNotice.getCounts()+1);
				}
			}finally{
				drawResultNotice.setRemarks(StringUtils.isBlank(ret)?"第三方返回结果为空":(ret.length()>1024?ret.substring(0, 1024):ret));
				drawResultNotice.setUpdateDate(new Date());
				drawResultNoticeService.updateByPrimaryKeySelective(drawResultNotice);
				System.out.println("==============提现回调通知线程执行结束=================");
			}
			drawResultNotice.setUpdateDate(new Date());
			drawResultNoticeService.updateByPrimaryKeySelective(drawResultNotice);
		}
		
	}
	
	/**
	 * 校验签名
	 * @param memberInfo
	 * @param srcStr 明码串
	 * @param signStr 签名串
	 * @return
	 */
	public SysOffice getSysOffice(MemberInfo memberInfo){
		
//		MemberInfoExample memberInfoExample = new MemberInfoExample();
//		MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(drawResultNotice.getMemberId());
		
//		AccountExample accountExample = new AccountExample();
//		accountExample.createCriteria().andMemberIdEqualTo(memberInfo.getId()).andDelFlagEqualTo("0");
//		List<Account> accounts = accountService.selectByExample(accountExample);
		
		EpayCodeExample epayCodeExample = new EpayCodeExample();
		List<String> values = new ArrayList<String>();
		values.add("5");
		values.add("7");
		epayCodeExample.or().andMemberIdEqualTo(memberInfo.getId()).andStatusIn(values);
		List<EpayCode> epayCodeList = epayCodeService.selectByExample(epayCodeExample);
		
		SysOfficeExample sysOfficeExample = new SysOfficeExample();
		sysOfficeExample.or().andIdEqualTo(epayCodeList.get(0).getOfficeId()).andAgtTypeEqualTo("3");
		List<SysOffice> sysOfficeList = sysOfficeService.selectByExample(sysOfficeExample);
		
		SysOffice sysOffice = sysOfficeList.get(0);
		
		return sysOffice;
	}
}
