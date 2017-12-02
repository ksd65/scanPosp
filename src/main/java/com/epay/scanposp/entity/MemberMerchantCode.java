package com.epay.scanposp.entity;

import java.math.BigDecimal;
import java.util.Date;

public class MemberMerchantCode {
	
	private Integer id;
	
	private Integer memberId;
	
	private String routeCode;
	
	private String aisleType;
	
	private String wxMerchantCode;
	
	private String zfbMerchantCode;
	
	private String qqMerchantCode;
	
	private String bdMerchantCode;
	
	private String jdMerchantCode;
	
    private BigDecimal t0DrawFee;

    private BigDecimal t0TradeRate;

    private BigDecimal t1DrawFee;

    private BigDecimal t1TradeRate;
	
	private String delFlag;
	
	private String createBy;

    private Date createDate;

    private String updateBy;

    private Date updateDate;

	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getRouteCode() {
		return routeCode;
	}

	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}

	public String getWxMerchantCode() {
		return wxMerchantCode;
	}

	public void setWxMerchantCode(String wxMerchantCode) {
		this.wxMerchantCode = wxMerchantCode;
	}

	public String getZfbMerchantCode() {
		return zfbMerchantCode;
	}

	public void setZfbMerchantCode(String zfbMerchantCode) {
		this.zfbMerchantCode = zfbMerchantCode;
	}

	public String getQqMerchantCode() {
		return qqMerchantCode;
	}

	public void setQqMerchantCode(String qqMerchantCode) {
		this.qqMerchantCode = qqMerchantCode;
	}

	public String getBdMerchantCode() {
		return bdMerchantCode;
	}

	public void setBdMerchantCode(String bdMerchantCode) {
		this.bdMerchantCode = bdMerchantCode;
	}

	public String getJdMerchantCode() {
		return jdMerchantCode;
	}

	public void setJdMerchantCode(String jdMerchantCode) {
		this.jdMerchantCode = jdMerchantCode;
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

	public String getAisleType() {
		return aisleType;
	}

	public void setAisleType(String aisleType) {
		this.aisleType = aisleType;
	}

	public BigDecimal getT0DrawFee() {
		return t0DrawFee;
	}

	public void setT0DrawFee(BigDecimal t0DrawFee) {
		this.t0DrawFee = t0DrawFee;
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
    
    

}
