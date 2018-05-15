package com.epay.scanposp.tigger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.epay.scanposp.common.utils.DateUtil;
import com.epay.scanposp.common.utils.constant.RouteCodeConstant;
import com.epay.scanposp.entity.SubMerchantBlackList;
import com.epay.scanposp.entity.SubMerchantBlackListExample;
import com.epay.scanposp.entity.SysCommonConfig;
import com.epay.scanposp.entity.SysCommonConfigExample;
import com.epay.scanposp.service.CommonService;
import com.epay.scanposp.service.SubMerchantBlackListService;
import com.epay.scanposp.service.SysCommonConfigService;
import com.epay.scanposp.service.TradeDailyTotalService;

public class SubMerchantBlackListTigger {
	
	private static Logger logger = LoggerFactory.getLogger(SubMerchantBlackListTigger.class);
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private SubMerchantBlackListService subMerchantBlackListService;
	
	
	@Autowired
	private TradeDailyTotalService tradeDailyTotalService;
	
	@Autowired
	private SysCommonConfigService sysCommonConfigService;
	
	public void subMerchantBlackList(){
		
		try{
			logger.info("子商户黑名单额更新定时。。。");
			Date time = new Date(); 
			String date = DateUtil.getDateFormat(time, "yyyy-MM-dd");
			
			String value = "";
			SysCommonConfigExample sysCommonConfigExample = new SysCommonConfigExample();
			sysCommonConfigExample.or().andNameEqualTo("SUB_MERCHANT_FAIL_IP_COUNT").andDelFlagEqualTo("0");
			List<SysCommonConfig> sysCommonConfig = sysCommonConfigService.selectByExample(sysCommonConfigExample);
			if (sysCommonConfig != null && sysCommonConfig.size() > 0) {
				value = sysCommonConfig.get(0).getValue();
			}
			
			if(!"".equals(value)){
				Map<String,Object> paramMap = new HashMap<String, Object>();
				paramMap.put("createDate", date);
				paramMap.put("routeCode", RouteCodeConstant.TLWD_ROUTE_CODE);
				List<Map<String,Object>> list = subMerchantBlackListService.getSubMerchantFailIpCount(paramMap);
				if(list!=null&&list.size()>0){
					for(Map<String,Object> map:list){
						String subMerchantCode = (String)map.get("sub_merchant_code");
						Long count = (Long)map.get("counts");
					//	System.out.println(subMerchantCode+"    "+count);
						if(count>=Integer.parseInt(value)){//超过IP数
							SubMerchantBlackListExample subMerchantBlackListExample = new SubMerchantBlackListExample();
							subMerchantBlackListExample.createCriteria().andBlackTypeEqualTo("1").andSubMerchantCodeEqualTo(subMerchantCode).andTradeDateEqualTo(DateUtil.getDateFormat(time, "yyyyMMdd")).andDelFlagEqualTo("0");
							List<SubMerchantBlackList> bllist = subMerchantBlackListService.selectByExample(subMerchantBlackListExample);
							if(bllist ==null || bllist.size()==0){
								SubMerchantBlackList subMerchantBlackList = new SubMerchantBlackList();
								subMerchantBlackList.setBlackType("1");
								subMerchantBlackList.setSubMerchantCode(subMerchantCode);
								subMerchantBlackList.setTradeDate(DateUtil.getDateFormat(time, "yyyyMMdd"));
								subMerchantBlackList.setCreateDate(new Date());
								subMerchantBlackListService.insertSelective(subMerchantBlackList);
							}
							
						}
					}
				}
			}
			
			String continueCount = "";
			sysCommonConfigExample = new SysCommonConfigExample();
			sysCommonConfigExample.or().andNameEqualTo("SUB_MERCHANT_CONTINUE_BLACK_COUNT").andDelFlagEqualTo("0");
			sysCommonConfig = sysCommonConfigService.selectByExample(sysCommonConfigExample);
			if (sysCommonConfig != null && sysCommonConfig.size() > 0) {
				continueCount = sysCommonConfig.get(0).getValue();
			}
			
			if(!"".equals(continueCount)){
				Map<String,Object> paramMap = new HashMap<String, Object>();
				paramMap.put("tradeDate", DateUtil.getDateFormat(time, "yyyyMMdd"));
				List<SubMerchantBlackList> bllist = subMerchantBlackListService.getSubMerchantBlackToday(paramMap);
				if(bllist!=null&&bllist.size()>0){
					for(SubMerchantBlackList black:bllist){
					//	System.out.println(black.getSubMerchantCode());
						boolean flag = true;
						for(int i=1;i<Integer.parseInt(continueCount);i++){
							SubMerchantBlackListExample subMerchantBlackListExample = new SubMerchantBlackListExample();
							subMerchantBlackListExample.createCriteria().andBlackTypeEqualTo("1").andSubMerchantCodeEqualTo(black.getSubMerchantCode()).andTradeDateEqualTo(DateUtil.getBeforeDate(i, "yyyyMMdd")).andDelFlagEqualTo("0");
							List<SubMerchantBlackList> list_1 = subMerchantBlackListService.selectByExample(subMerchantBlackListExample);
						//	System.out.println(list_1.size());
							if(list_1==null||list_1.size()==0){
								flag = false;
								break;
							}
						}
						if(flag){
							SubMerchantBlackListExample subMerchantBlackListExample = new SubMerchantBlackListExample();
							subMerchantBlackListExample.createCriteria().andBlackTypeEqualTo("2").andSubMerchantCodeEqualTo(black.getSubMerchantCode()).andDelFlagEqualTo("0");
							List<SubMerchantBlackList> bllist1 = subMerchantBlackListService.selectByExample(subMerchantBlackListExample);
							if(bllist1 ==null || bllist1.size()==0){
								SubMerchantBlackList subMerchantBlackList = new SubMerchantBlackList();
								subMerchantBlackList.setBlackType("2");
								subMerchantBlackList.setSubMerchantCode(black.getSubMerchantCode());
								subMerchantBlackList.setCreateDate(new Date());
								subMerchantBlackListService.insertSelective(subMerchantBlackList);
							}
						}
					}
				}
			}
		}catch(Exception e){
			logger.error(e.getMessage());
		}
	}
}
