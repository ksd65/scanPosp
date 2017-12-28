package com.epay.scanposp.tigger;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.epay.scanposp.controller.DebitNoteController;
import com.epay.scanposp.entity.PayResultNotice;
import com.epay.scanposp.entity.PayResultNoticeExample;
import com.epay.scanposp.service.PayResultNoticeLogService;
import com.epay.scanposp.service.PayResultNoticeService;
import com.epay.scanposp.service.PayResultNotifyService;
import com.epay.scanposp.service.SysOfficeExtendService;
import com.epay.scanposp.thread.PayResultNoticeThread;

public class PayResultNoticeTigger {
	
	private static Logger logger = LoggerFactory.getLogger(PayResultNoticeTigger.class);
	
	@Resource
	private PayResultNoticeService payResultNoticeService;
	
	@Resource
	private PayResultNoticeLogService payResultNoticeLogService;
	
	@Resource
	private PayResultNotifyService payResultNotifyService;
	
	@Resource
	private SysOfficeExtendService sysOfficeExtendService;
	
	@Resource
	ThreadPoolTaskExecutor threadPoolTaskExecutor;
	
	public void dealPayResultNotice(){
		logger.info("回调通知定时开始执行");
		PayResultNoticeExample payResultNoticeExample = new PayResultNoticeExample();
		payResultNoticeExample.or().andStatusEqualTo("2").andCountsLessThan(3);
		payResultNoticeExample.setOrderByClause(" id asc ");
		List<PayResultNotice> payResultNoticeList = payResultNoticeService.selectByExample(payResultNoticeExample);
		if(payResultNoticeList!=null){
			logger.info("回调通知定时查询个数："+payResultNoticeList.size());
		}
		for(PayResultNotice payResultNotice : payResultNoticeList){
			payResultNotifyService.notify(payResultNotice);
			/*PayResultNoticeThread payResultNoticeThread = new PayResultNoticeThread(payResultNoticeService, sysOfficeExtendService, payResultNotice,payResultNoticeLogService);
			threadPoolTaskExecutor.execute(payResultNoticeThread);
			//new Thread(payResultNoticeThread).start();
			 */
		}
	}
}
