package com.epay.scanposp.entity;

import java.math.BigDecimal;
import java.util.Date;

public class MemberDrawRoute {
	
	private Integer id;
	
	private Integer memberId;
	
	private String payMethod;
	
	private String payType;
	
	private String routeCode;
	
	private String aisleType;
	
	private String merchantCode;
	
	private BigDecimal d0Percent;
	
	private BigDecimal d1Percent;
	
	private BigDecimal t1Percent;
	
	private BigDecimal drawFee;
	
	private String delFlag;
	
	private String createBy;

    private Date createDate;

    private String updateBy;

    private Date updateDate;

	
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

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public BigDecimal getD0Percent() {
		return d0Percent;
	}

	public void setD0Percent(BigDecimal d0Percent) {
		this.d0Percent = d0Percent;
	}

	public BigDecimal getD1Percent() {
		return d1Percent;
	}

	public void setD1Percent(BigDecimal d1Percent) {
		this.d1Percent = d1Percent;
	}

	public BigDecimal getT1Percent() {
		return t1Percent;
	}

	public void setT1Percent(BigDecimal t1Percent) {
		this.t1Percent = t1Percent;
	}

	public BigDecimal getDrawFee() {
		return drawFee;
	}

	public void setDrawFee(BigDecimal drawFee) {
		this.drawFee = drawFee;
	}
    
	
    

}
