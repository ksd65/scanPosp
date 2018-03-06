package com.epay.scanposp.tigger;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.epay.scanposp.common.utils.constant.RouteCodeConstant;
import com.epay.scanposp.entity.DebitNote;
import com.epay.scanposp.entity.PayQrCode;
import com.epay.scanposp.entity.PayQrCodeTemp;
import com.epay.scanposp.entity.SysCommonConfig;
import com.epay.scanposp.entity.SysCommonConfigExample;
import com.epay.scanposp.service.DebitNoteService;
import com.epay.scanposp.service.PayQrCodeService;
import com.epay.scanposp.service.PayQrCodeTempService;
import com.epay.scanposp.service.SysCommonConfigService;

public class ReleaseQrCodeOverTimesTigger {
	
	private static Logger logger = LoggerFactory.getLogger(ReleaseQrCodeOverTimesTigger.class);

	
	@Autowired
	private DebitNoteService debitNoteService;
	
	@Autowired
	private SysCommonConfigService sysCommonConfigService;
	
	@Autowired
	private PayQrCodeTempService payQrCodeTempService;
	
	@Autowired
	private PayQrCodeService payQrCodeService;
	
	public void releaseQrCode(){
		
		try{
			//logger.info("释放收款码定时。。。");
			
			String second = "";
			SysCommonConfigExample sysCommonConfigExample = new SysCommonConfigExample();
			sysCommonConfigExample.or().andNameEqualTo("QR_CODE_OVER_TIMES").andDelFlagEqualTo("0");
			List<SysCommonConfig> sysCommonConfig = sysCommonConfigService.selectByExample(sysCommonConfigExample);
			if (sysCommonConfig != null && sysCommonConfig.size() > 0) {
				second = sysCommonConfig.get(0).getValue();
			}
			if(!"".equals(second)){
				Map<String,Object> paramMap = new HashMap<String, Object>();
				paramMap.put("routeId", RouteCodeConstant.GRSM_ROUTE_CODE);
				paramMap.put("qrorderDealStatus", "0");
				paramMap.put("status", "0");
				paramMap.put("seconds", second);
				List<DebitNote> debitNoteList = debitNoteService.selectNoteOutTimes(paramMap);
				if(debitNoteList!=null && debitNoteList.size()>0){
					for(DebitNote debitNote : debitNoteList){
						debitNote.setQrorderDealStatus("1");
						debitNote.setUpdateDate(new Date());
						debitNoteService.updateByPrimaryKey(debitNote);
						
						PayQrCodeTemp payQrCodeTemp = new PayQrCodeTemp();
						payQrCodeTemp.setQrCodeId(debitNote.getPayQrCodeId());
						payQrCodeTempService.delete(payQrCodeTemp);
						
						PayQrCode payQrCode = payQrCodeService.selectByPrimaryKey(debitNote.getPayQrCodeId());
						if(payQrCode!=null){
							payQrCode.setStatus("0");
							payQrCode.setUpdateDate(new Date());
							payQrCodeService.updateByPrimaryKey(payQrCode);
						}
					}
				}
			}
			
		}catch(Exception e){
			logger.error(e.getMessage());
		}
	}
}
