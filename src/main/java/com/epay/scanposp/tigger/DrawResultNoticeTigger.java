package com.epay.scanposp.tigger;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.epay.scanposp.entity.DrawResultNotice;
import com.epay.scanposp.entity.DrawResultNoticeExample;
import com.epay.scanposp.service.AccountService;
import com.epay.scanposp.service.DrawResultNoticeService;
import com.epay.scanposp.service.EpayCodeService;
import com.epay.scanposp.service.MemberInfoService;
import com.epay.scanposp.service.SysOfficeService;
import com.epay.scanposp.thread.DrawResultNoticeThread;

public class DrawResultNoticeTigger {
	
	private static Logger logger = LoggerFactory.getLogger(DrawResultNoticeTigger.class);
	
	@Resource
	private DrawResultNoticeService drawResultNoticeService;
	@Resource
	private EpayCodeService epayCodeService;
	@Resource
	private MemberInfoService memberInfoService;
	@Resource
	private AccountService accountService;
	@Resource
	private SysOfficeService sysOfficeService;
	@Resource
	ThreadPoolTaskExecutor threadPoolTaskExecutor;
	
	public void dealDrawResultNotice(){
		DrawResultNoticeExample drawResultNoticeExample = new DrawResultNoticeExample();
		drawResultNoticeExample.or().andStatusEqualTo("2").andCountsLessThan(20);
		drawResultNoticeExample.setOrderByClause(" id asc ");
		List<DrawResultNotice> drawResultNoticeList = drawResultNoticeService.selectByExample(drawResultNoticeExample);
		
		for(final DrawResultNotice drawResultNotice : drawResultNoticeList){
			DrawResultNoticeThread thread = new DrawResultNoticeThread(drawResultNotice, epayCodeService, drawResultNoticeService, memberInfoService, accountService, sysOfficeService);
			threadPoolTaskExecutor.execute(thread);
			//new Thread(thread).start();
		}
	}
}
