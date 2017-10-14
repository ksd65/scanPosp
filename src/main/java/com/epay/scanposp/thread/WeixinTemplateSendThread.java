package com.epay.scanposp.thread;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epay.scanposp.common.utils.HttpUtil;
import com.epay.scanposp.common.utils.WxMessageUtil;
import com.epay.scanposp.entity.DebitNote;
import com.epay.scanposp.entity.EpayCode;
import com.epay.scanposp.entity.EpayCodeExample;
import com.epay.scanposp.entity.MemberInfo;
import com.epay.scanposp.entity.MemberOpenid;
import com.epay.scanposp.entity.MemberOpenidExample;
import com.epay.scanposp.entity.SysOffice;
import com.epay.scanposp.entity.SysOfficeConfigOem;
import com.epay.scanposp.entity.SysOfficeConfigOemExample;
import com.epay.scanposp.entity.SysOfficeExample;
import com.epay.scanposp.service.EpayCodeService;
import com.epay.scanposp.service.MemberInfoService;
import com.epay.scanposp.service.MemberOpenidService;
import com.epay.scanposp.service.SysOfficeConfigOemService;
import com.epay.scanposp.service.SysOfficeService;

public class WeixinTemplateSendThread implements Runnable{
	private static Logger logger = LoggerFactory.getLogger(WeixinTemplateSendThread.class);
	
	private EpayCodeService epayCodeService;
	private MemberOpenidService memberOpenidService;
	private SysOfficeConfigOemService sysOfficeConfigOemService;
	private MemberInfoService memberInfoService;
	private SysOfficeService sysOfficeService;
	private DebitNote debitNote;
	private String payTime;
	
	public WeixinTemplateSendThread(EpayCodeService epayCodeService, MemberOpenidService memberOpenidService,
			SysOfficeConfigOemService sysOfficeConfigOemService, MemberInfoService memberInfoService,
			SysOfficeService sysOfficeService, DebitNote debitNote, String payTime) {
		this.epayCodeService = epayCodeService;
		this.memberOpenidService = memberOpenidService;
		this.sysOfficeConfigOemService = sysOfficeConfigOemService;
		this.memberInfoService = memberInfoService;
		this.sysOfficeService = sysOfficeService;
		this.debitNote = debitNote;
		this.payTime = payTime;
	}

	@Override
	public void run() {
		System.out.println("==============模板消息线程开始执行=================");
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
						if(null != payTime && !"".equals(payTime) && payTime.length() >= 14){
							try {
								payTime = sdf.format(new SimpleDateFormat("yyyyMMddHHmmss").parse(payTime.length()>14?payTime.substring(0,14):payTime));
							} catch (ParseException e) {
								//TODO 该异常不处理，有异常抛出则线程终结
							}
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
	
}
