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
	
	private String wyMerchantCode;
	
	private String kjMerchantCode;
	
    private BigDecimal t0DrawFee;
    
    private BigDecimal t0DrawRate;

    private BigDecimal t0TradeRate;

    private BigDecimal t1DrawFee;

    private BigDecimal t1TradeRate;
    
    private BigDecimal t1DrawRate;
    
    private BigDecimal zfbT0DrawFee;

    private BigDecimal zfbT0TradeRate;

    private BigDecimal zfbT1DrawFee;

    private BigDecimal zfbT1TradeRate;
    
    private BigDecimal qqT0DrawFee;

    private BigDecimal qqT0TradeRate;

    private BigDecimal qqT1DrawFee;

    private BigDecimal qqT1TradeRate;
    
    private BigDecimal jdT0DrawFee;

    private BigDecimal jdT0TradeRate;

    private BigDecimal jdT1DrawFee;

    private BigDecimal jdT1TradeRate;
    
    private BigDecimal wyT0DrawFee;

    private BigDecimal wyT0TradeRate;

    private BigDecimal wyT1DrawFee;

    private BigDecimal wyT1TradeRate;
    
    private BigDecimal kjT0DrawFee;

    private BigDecimal kjT0TradeRate;

    private BigDecimal kjT1DrawFee;

    private BigDecimal kjT1TradeRate;
	
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

	public BigDecimal getZfbT0DrawFee() {
		return zfbT0DrawFee;
	}

	public void setZfbT0DrawFee(BigDecimal zfbT0DrawFee) {
		this.zfbT0DrawFee = zfbT0DrawFee;
	}

	public BigDecimal getZfbT0TradeRate() {
		return zfbT0TradeRate;
	}

	public void setZfbT0TradeRate(BigDecimal zfbT0TradeRate) {
		this.zfbT0TradeRate = zfbT0TradeRate;
	}

	public BigDecimal getZfbT1DrawFee() {
		return zfbT1DrawFee;
	}

	public void setZfbT1DrawFee(BigDecimal zfbT1DrawFee) {
		this.zfbT1DrawFee = zfbT1DrawFee;
	}

	public BigDecimal getZfbT1TradeRate() {
		return zfbT1TradeRate;
	}

	public void setZfbT1TradeRate(BigDecimal zfbT1TradeRate) {
		this.zfbT1TradeRate = zfbT1TradeRate;
	}

	public BigDecimal getQqT0DrawFee() {
		return qqT0DrawFee;
	}

	public void setQqT0DrawFee(BigDecimal qqT0DrawFee) {
		this.qqT0DrawFee = qqT0DrawFee;
	}

	public BigDecimal getQqT0TradeRate() {
		return qqT0TradeRate;
	}

	public void setQqT0TradeRate(BigDecimal qqT0TradeRate) {
		this.qqT0TradeRate = qqT0TradeRate;
	}

	public BigDecimal getQqT1DrawFee() {
		return qqT1DrawFee;
	}

	public void setQqT1DrawFee(BigDecimal qqT1DrawFee) {
		this.qqT1DrawFee = qqT1DrawFee;
	}

	public BigDecimal getQqT1TradeRate() {
		return qqT1TradeRate;
	}

	public void setQqT1TradeRate(BigDecimal qqT1TradeRate) {
		this.qqT1TradeRate = qqT1TradeRate;
	}

	public BigDecimal getT0DrawRate() {
		return t0DrawRate;
	}

	public void setT0DrawRate(BigDecimal t0DrawRate) {
		this.t0DrawRate = t0DrawRate;
	}

	public BigDecimal getT1DrawRate() {
		return t1DrawRate;
	}

	public void setT1DrawRate(BigDecimal t1DrawRate) {
		this.t1DrawRate = t1DrawRate;
	}

	public String getWyMerchantCode() {
		return wyMerchantCode;
	}

	public void setWyMerchantCode(String wyMerchantCode) {
		this.wyMerchantCode = wyMerchantCode;
	}

	public BigDecimal getWyT0DrawFee() {
		return wyT0DrawFee;
	}

	public void setWyT0DrawFee(BigDecimal wyT0DrawFee) {
		this.wyT0DrawFee = wyT0DrawFee;
	}

	public BigDecimal getWyT0TradeRate() {
		return wyT0TradeRate;
	}

	public void setWyT0TradeRate(BigDecimal wyT0TradeRate) {
		this.wyT0TradeRate = wyT0TradeRate;
	}

	public BigDecimal getWyT1DrawFee() {
		return wyT1DrawFee;
	}

	public void setWyT1DrawFee(BigDecimal wyT1DrawFee) {
		this.wyT1DrawFee = wyT1DrawFee;
	}

	public BigDecimal getWyT1TradeRate() {
		return wyT1TradeRate;
	}

	public void setWyT1TradeRate(BigDecimal wyT1TradeRate) {
		this.wyT1TradeRate = wyT1TradeRate;
	}

	public BigDecimal getJdT0DrawFee() {
		return jdT0DrawFee;
	}

	public void setJdT0DrawFee(BigDecimal jdT0DrawFee) {
		this.jdT0DrawFee = jdT0DrawFee;
	}

	public BigDecimal getJdT0TradeRate() {
		return jdT0TradeRate;
	}

	public void setJdT0TradeRate(BigDecimal jdT0TradeRate) {
		this.jdT0TradeRate = jdT0TradeRate;
	}

	public BigDecimal getJdT1DrawFee() {
		return jdT1DrawFee;
	}

	public void setJdT1DrawFee(BigDecimal jdT1DrawFee) {
		this.jdT1DrawFee = jdT1DrawFee;
	}

	public BigDecimal getJdT1TradeRate() {
		return jdT1TradeRate;
	}

	public void setJdT1TradeRate(BigDecimal jdT1TradeRate) {
		this.jdT1TradeRate = jdT1TradeRate;
	}

	public String getKjMerchantCode() {
		return kjMerchantCode;
	}

	public void setKjMerchantCode(String kjMerchantCode) {
		this.kjMerchantCode = kjMerchantCode;
	}

	public BigDecimal getKjT0DrawFee() {
		return kjT0DrawFee;
	}

	public void setKjT0DrawFee(BigDecimal kjT0DrawFee) {
		this.kjT0DrawFee = kjT0DrawFee;
	}

	public BigDecimal getKjT0TradeRate() {
		return kjT0TradeRate;
	}

	public void setKjT0TradeRate(BigDecimal kjT0TradeRate) {
		this.kjT0TradeRate = kjT0TradeRate;
	}

	public BigDecimal getKjT1DrawFee() {
		return kjT1DrawFee;
	}

	public void setKjT1DrawFee(BigDecimal kjT1DrawFee) {
		this.kjT1DrawFee = kjT1DrawFee;
	}

	public BigDecimal getKjT1TradeRate() {
		return kjT1TradeRate;
	}

	public void setKjT1TradeRate(BigDecimal kjT1TradeRate) {
		this.kjT1TradeRate = kjT1TradeRate;
	}
    
    

}
