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
import com.epay.scanposp.entity.Account;
import com.epay.scanposp.entity.AccountExample;
import com.epay.scanposp.entity.MemberInfo;
import com.epay.scanposp.entity.MemberMerchantCode;
import com.epay.scanposp.entity.MemberMerchantCodeExample;
import com.epay.scanposp.service.AccountService;
import com.epay.scanposp.service.CommonService;
import com.epay.scanposp.service.MemberInfoService;
import com.epay.scanposp.service.MemberMerchantCodeService;
import com.epay.scanposp.service.MemberMerchantKeyService;
import com.epay.scanposp.service.RoutewayDrawService;

public class AccountBalanceTigger {
	
	private static Logger logger = LoggerFactory.getLogger(AccountBalanceTigger.class);
	
	@Autowired
	private MemberMerchantKeyService memberMerchantKeyService;
		
	@Autowired
	private RoutewayDrawService routewayDrawService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private MemberMerchantCodeService memberMerchantCodeService;
	
	@Autowired
	private MemberInfoService memberInfoService;
	
	public void accountBalance(){
		
		try{
			logger.info("账户余额更新定时。。。");
			
			AccountExample accountExample = new AccountExample();
			accountExample.createCriteria().andDelFlagEqualTo("0");
			List<Account> accountList = accountService.selectByExample(accountExample);
			if(accountList!=null && accountList.size()>0){
				for(Account account:accountList){
					Double balance = account.getBalance().doubleValue();
					Double w5Balance = account.getW5Balance().doubleValue();
					Double w6Balance = account.getW6Balance().doubleValue();
					Double w0Balance = account.getW0Balance().doubleValue();
					
					
					MemberMerchantCodeExample memberMerchantCodeExample = new MemberMerchantCodeExample();
					memberMerchantCodeExample.createCriteria().andMemberIdEqualTo(account.getMemberId()).andRouteCodeEqualTo(RouteCodeConstant.SLF_ROUTE_CODE).andDelFlagEqualTo("0");
					List<MemberMerchantCode> merchantCodes = memberMerchantCodeService.selectByExample(memberMerchantCodeExample);
					if (merchantCodes == null || merchantCodes.size() != 1) {
						continue;
					}
					MemberMerchantCode merchantCode = merchantCodes.get(0);
					
					MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(account.getMemberId());
					if(memberInfo == null){
						continue;
					}
					double tradeRate = 0 ;
					if("0".equals(memberInfo.getSettleType())){
						tradeRate = merchantCode.getT0TradeRate().doubleValue();
					}else{
						tradeRate = merchantCode.getT1TradeRate().doubleValue();
					}
					
					Double drawPercent = 0.7;//D0 70%
					int week = DateUtil.getWeek();
					
					String yesterday = DateUtil.getBeforeDate(1, "yyyyMMdd");
					//前一天的随乐付交易总额
					Map<String,Object> paramMap = new HashMap<String, Object>();
					paramMap.put("memberId", account.getMemberId());
					paramMap.put("routeId", RouteCodeConstant.SLF_ROUTE_CODE);
					paramMap.put("startDate", yesterday);
					paramMap.put("endDate", yesterday);
					Double tradeMoneyCountSLF = commonService.countTransactionMoneyByCondition(paramMap);
					tradeMoneyCountSLF = tradeMoneyCountSLF == null ? 0 : tradeMoneyCountSLF;
					
					Double tradeMoneyBalanceSLF = tradeMoneyCountSLF * (1-tradeRate);//扣除费率后的交易金额
					
					Double tradeMoneyBalanceSLF70 = tradeMoneyBalanceSLF * drawPercent;//扣除费率后70%的交易金额
					
					Double tradeMoneyBalanceSLF30 = tradeMoneyBalanceSLF * (1 - drawPercent);//扣除费率后30%的交易金额
					
					paramMap = new HashMap<String, Object>();
					paramMap.put("memberId", account.getMemberId());
					paramMap.put("respType", "S");
					//前一天成功提现金额（包含代付）
					paramMap.put("respDate", yesterday);
					Double drawMoneyCountYesterDay = commonService.countDrawMoneyByCondition(paramMap);
					drawMoneyCountYesterDay = drawMoneyCountYesterDay == null ? 0 : drawMoneyCountYesterDay;
					//前一天成功失败金额（包含代付）
					paramMap.put("auditStatus", "2");
					paramMap.put("respType", "E");
					paramMap.put("respDate", yesterday);
					Double drawFailMoneyCountYesterDay = commonService.countMoneyByCondition(paramMap);
					drawFailMoneyCountYesterDay = drawFailMoneyCountYesterDay == null ? 0 : drawFailMoneyCountYesterDay;
					if(week == 6){//周六凌晨跑
						account.setW5Balance(new BigDecimal(tradeMoneyBalanceSLF30));
						balance = balance + tradeMoneyBalanceSLF70 - drawMoneyCountYesterDay - drawFailMoneyCountYesterDay;
					}else if(week == 0){//周天凌晨跑
						account.setW6Balance(new BigDecimal(tradeMoneyBalanceSLF30));
						balance = balance + tradeMoneyBalanceSLF70 - drawMoneyCountYesterDay - drawFailMoneyCountYesterDay;
					}else if(week == 1){//周一凌晨跑
						account.setW0Balance(new BigDecimal(tradeMoneyBalanceSLF30));
						balance = balance + tradeMoneyBalanceSLF70 - drawMoneyCountYesterDay - drawFailMoneyCountYesterDay + w5Balance;
						account.setW5Balance(new BigDecimal(0));
					}else if(week == 2){//周二凌晨跑
						balance = balance + tradeMoneyBalanceSLF - drawMoneyCountYesterDay - drawFailMoneyCountYesterDay + w6Balance + w0Balance;
						account.setW6Balance(new BigDecimal(0));
						account.setW0Balance(new BigDecimal(0));
					}else{
						balance = balance + tradeMoneyBalanceSLF - drawMoneyCountYesterDay - drawFailMoneyCountYesterDay ;
					}
					account.setBalance(new BigDecimal(balance));
					account.setUpdateDate(new Date());
					accountService.updateByPrimaryKey(account);
				}
			}
			
		}catch(Exception e){
			logger.error(e.getMessage());
		}
	}
}
