package com.epay.scanposp.entity;

import java.math.BigDecimal;
import java.util.Date;

public class PayTypeRule {
	
	private Integer id;
	
	private String payMethod;
	
	private String payType;
	
	private String ruleType;
	
	private Integer memberId;
	
	private BigDecimal minMoney;
	
	private BigDecimal maxMoney;
	
	private String routeCode;
	
	private String aisleType;
	
	private String merchantCode;
	
	private BigDecimal ruleRate;
	
	private String delFlag;
	
	private String createBy;

    private Date createDate;

    private String updateBy;

    private Date updateDate;
    
    private String beginTime;
    
    private String endTime;

	
	public String getAisleType() {
		return aisleType;
	}

	public void setAisleType(String aisleType) {
		this.aisleType = aisleType;
	}

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


	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getRouteCode() {
		return routeCode;
	}

	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}


	public String getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public BigDecimal getRuleRate() {
		return ruleRate;
	}

	public void setRuleRate(BigDecimal ruleRate) {
		this.ruleRate = ruleRate;
	}

	public String getRuleType() {
		return ruleType;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}

	public BigDecimal getMinMoney() {
		return minMoney;
	}

	public void setMinMoney(BigDecimal minMoney) {
		this.minMoney = minMoney;
	}

	public BigDecimal getMaxMoney() {
		return maxMoney;
	}

	public void setMaxMoney(BigDecimal maxMoney) {
		this.maxMoney = maxMoney;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
    
	
    

}
