package com.epay.scanposp.tigger;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.epay.scanposp.common.utils.DateUtil;
import com.epay.scanposp.common.utils.constant.RouteCodeConstant;
import com.epay.scanposp.entity.PayQrCodeTotal;
import com.epay.scanposp.entity.PayQrCodeTotalExample;
import com.epay.scanposp.entity.Payee;
import com.epay.scanposp.entity.PayeeExample;
import com.epay.scanposp.service.PayQrCodeTotalService;
import com.epay.scanposp.service.PayeeService;

public class PayQrCodeTotalTigger {
	
	private static Logger logger = LoggerFactory.getLogger(PayQrCodeTotalTigger.class);
	
	
	@Autowired
	private PayQrCodeTotalService payQrCodeTotalService;
	
	@Autowired
	private PayeeService payeeService;
	
	public void qrCodeTotal(){
		
		try{
			//logger.info("个人收款支付更新定时。。。");
			Date time = new Date();
			String date = DateUtil.getDate(time);
			String dateStr = DateUtil.getDateStr(time);
			
			
			PayeeExample payeeExample = new PayeeExample();
			payeeExample.createCriteria().andDelFlagEqualTo("0");
			List<Payee> payeeList = payeeService.selectByExample(payeeExample);
			if(payeeList != null && payeeList.size()>0){
				for(Payee payee:payeeList){
					Map<String,String> param = new HashMap<String, String>();
					param.put("routeId", RouteCodeConstant.GRSM_ROUTE_CODE);
					param.put("beginTime", date +" 00:00:00");
					param.put("endTime", date +" 23:59:59");
					param.put("payeeId", String.valueOf(payee.getId()));
					List<PayQrCodeTotal> totalList = payQrCodeTotalService.countPayeeOrder(param);
					int counts = 0;
					Double totalMoney = 0d;
					if(totalList!=null && totalList.size()>0){
						counts = totalList.get(0).getCounts();
						totalMoney = totalList.get(0).getTotalMoney().doubleValue();
					}
					PayQrCodeTotalExample payQrCodeTotalExample = new PayQrCodeTotalExample();
					payQrCodeTotalExample.createCriteria().andPayeeIdEqualTo(payee.getId()).andTradeDateEqualTo(dateStr).andDelFlagEqualTo("0");
					List<PayQrCodeTotal> eList = payQrCodeTotalService.selectByExample(payQrCodeTotalExample);
					if(eList == null || eList.size() == 0){
						PayQrCodeTotal payTotal = new PayQrCodeTotal();
						payTotal.setPayeeId(payee.getId());
						payTotal.setTradeDate(dateStr);
						payTotal.setTotalMoney(new BigDecimal(totalMoney));
						payTotal.setCounts(counts);
						payTotal.setCreateDate(time);
						payQrCodeTotalService.insertSelective(payTotal);
					}else{
						PayQrCodeTotal payTotal = eList.get(0);
						payTotal.setTotalMoney(new BigDecimal(totalMoney));
						payTotal.setCounts(counts);
						payTotal.setUpdateDate(time);
						payQrCodeTotalService.updateByPrimaryKey(payTotal);
					}
					
				}
			}
			
			
			
		}catch(Exception e){
			logger.error(e.getMessage());
		}
	}
}
