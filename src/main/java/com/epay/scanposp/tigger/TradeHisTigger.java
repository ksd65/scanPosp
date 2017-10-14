package com.epay.scanposp.tigger;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epay.scanposp.service.CommonService;

public class TradeHisTigger {
	
	private static Logger logger = LoggerFactory.getLogger(TradeHisTigger.class);
	
	@Resource
	private CommonService commonService;
	
	public void moveToHis(){
		/*
		try {
			Map<String,Object> paramMap = new HashMap<String, Object>();
			for(int i=1; i<=10; i++){
				Integer ret = commonService.moveToHis(paramMap);
				logger.info("第[{}]次，迁移bu_trade_detail上个月数据到历史表,结果[{}]",i,ret);
				if(ret==0)break;
				Thread.sleep(10*1000);
			}
		} catch (InterruptedException e) {
			logger.error("迁移历史数据出错：",e);
		}
		*/
	}
}
