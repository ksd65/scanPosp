package com.epay.scanposp.entity;

import java.math.BigDecimal;
import java.util.Date;

public class MemberPayType {
	
	private Integer id;
	
	private Integer memberId;
	
	private String payMethod;
	
	private String payType;
	
	private String routeCode;
	
	private String aisleType;
	
	private String merchantCode;
	
	private BigDecimal t0DrawFee;
    
    private BigDecimal t0DrawRate;

    private BigDecimal t0TradeRate;

    private BigDecimal t1DrawFee;

    private BigDecimal t1TradeRate;
    
    private BigDecimal t1DrawRate;
	
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

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
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

	public String getRouteCode() {
		return routeCode;
	}

	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}

	public String getAisleType() {
		return aisleType;
	}

	public void setAisleType(String aisleType) {
		this.aisleType = aisleType;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public BigDecimal getT0DrawFee() {
		return t0DrawFee;
	}

	public void setT0DrawFee(BigDecimal t0DrawFee) {
		this.t0DrawFee = t0DrawFee;
	}

	public BigDecimal getT0DrawRate() {
		return t0DrawRate;
	}

	public void setT0DrawRate(BigDecimal t0DrawRate) {
		this.t0DrawRate = t0DrawRate;
	}

	public BigDecimal getT0TradeRate() {
		return t0TradeRate;
	}

	public void setT0TradeRate(BigDecimal t0TradeRate) {
		this.t0TradeRate = t0TradeRate;
	}

	public BigDecimal getT1DrawFee() {
		return t1DrawFee;
	}

	public void setT1DrawFee(BigDecimal t1DrawFee) {
		this.t1DrawFee = t1DrawFee;
	}

	public BigDecimal getT1TradeRate() {
		return t1TradeRate;
	}

	public void setT1TradeRate(BigDecimal t1TradeRate) {
		this.t1TradeRate = t1TradeRate;
	}

	public BigDecimal getT1DrawRate() {
		return t1DrawRate;
	}

	public void setT1DrawRate(BigDecimal t1DrawRate) {
		this.t1DrawRate = t1DrawRate;
	}
    
	
    

}
