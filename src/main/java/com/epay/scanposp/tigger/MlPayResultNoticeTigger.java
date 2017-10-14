package com.epay.scanposp.tigger;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.epay.scanposp.entity.StTradeDetail;
import com.epay.scanposp.entity.StTradeDetailExample;
import com.epay.scanposp.service.StTradeDetailService;
import com.epay.scanposp.service.SysOfficeExtendService;
import com.epay.scanposp.thread.MlPayResultNoticeThread;

 

 
 

public class MlPayResultNoticeTigger {
	private static Logger logger = LoggerFactory.getLogger(MlPayResultNoticeTigger.class);
	@Resource
	ThreadPoolTaskExecutor threadPoolTaskExecutor;

	@Resource
	StTradeDetailService stTradeDetailService;
	@Resource
	SysOfficeExtendService sysOfficeExtendService;

	public void mlPayResultNotice(){
		StTradeDetailExample stTradeDetailExample = new StTradeDetailExample();
		//交易成功且回调结果为未回调成功的数据
		stTradeDetailExample.createCriteria().andCbResEqualTo("0").andTradeCodeEqualTo("0000").andCountsLessThan(11);
		stTradeDetailExample.setOrderByClause(" id asc ");
		List<StTradeDetail> stTradeDetails = stTradeDetailService.selectByExample(stTradeDetailExample);
		
		for(final StTradeDetail stTradeDetail : stTradeDetails){
			MlPayResultNoticeThread thread = new MlPayResultNoticeThread(sysOfficeExtendService,stTradeDetail,stTradeDetailService);
			threadPoolTaskExecutor.execute(thread);			
		}
	}
}
