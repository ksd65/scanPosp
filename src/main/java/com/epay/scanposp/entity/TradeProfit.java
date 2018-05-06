package com.epay.scanposp.entity;

import java.math.BigDecimal;
import java.util.Date;

public class TradeProfit {
	
	private Integer id;
	
	private Integer memberId;
	
	private String txnDate;
	
	private String txnMethod;
	
	private String txnType;
	
	private String routeCode;
	
	private String merchantCode;
	
	private BigDecimal tradeMoney;
	
	private BigDecimal platTradeRate;
	
	private BigDecimal agentTradeRate;
	
	private BigDecimal memberTradeRate;
	
	private BigDecimal platCost;
	
	private BigDecimal agentCost;
	
	private BigDecimal memberCost;
	
	private BigDecimal drawPer;
	
	private BigDecimal agentProfit;
	
	private BigDecimal agentProfitD1;
	
	private String agentOfficeId;
	
	private String delFlag;
	
	private String createBy;

    private Date createDate;

    private String updateBy;

    private Date updateDate;

	
	

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getRouteCode() {
		return routeCode;
	}

	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTxnDate() {
		return txnDate;
	}

	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}

	public BigDecimal getTradeMoney() {
		return tradeMoney;
	}

	public void setTradeMoney(BigDecimal tradeMoney) {
		this.tradeMoney = tradeMoney;
	}

	public BigDecimal getPlatCost() {
		return platCost;
	}

	public void setPlatCost(BigDecimal platCost) {
		this.platCost = platCost;
	}

	public BigDecimal getAgentCost() {
		return agentCost;
	}

	public void setAgentCost(BigDecimal agentCost) {
		this.agentCost = agentCost;
	}

	public BigDecimal getMemberCost() {
		return memberCost;
	}

	public void setMemberCost(BigDecimal memberCost) {
		this.memberCost = memberCost;
	}

	public BigDecimal getDrawPer() {
		return drawPer;
	}

	public void setDrawPer(BigDecimal drawPer) {
		this.drawPer = drawPer;
	}

	public String getAgentOfficeId() {
		return agentOfficeId;
	}

	public void setAgentOfficeId(String agentOfficeId) {
		this.agentOfficeId = agentOfficeId;
	}

	public String getTxnMethod() {
		return txnMethod;
	}

	public void setTxnMethod(String txnMethod) {
		this.txnMethod = txnMethod;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public BigDecimal getPlatTradeRate() {
		return platTradeRate;
	}

	public void setPlatTradeRate(BigDecimal platTradeRate) {
		this.platTradeRate = platTradeRate;
	}

	public BigDecimal getAgentTradeRate() {
		return agentTradeRate;
	}

	public void setAgentTradeRate(BigDecimal agentTradeRate) {
		this.agentTradeRate = agentTradeRate;
	}

	public BigDecimal getMemberTradeRate() {
		return memberTradeRate;
	}

	public void setMemberTradeRate(BigDecimal memberTradeRate) {
		this.memberTradeRate = memberTradeRate;
	}

	public BigDecimal getAgentProfit() {
		return agentProfit;
	}

	public void setAgentProfit(BigDecimal agentProfit) {
		this.agentProfit = agentProfit;
	}

	public BigDecimal getAgentProfitD1() {
		return agentProfitD1;
	}

	public void setAgentProfitD1(BigDecimal agentProfitD1) {
		this.agentProfitD1 = agentProfitD1;
	}

	
    
	
    

}
