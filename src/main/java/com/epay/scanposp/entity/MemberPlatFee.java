package com.epay.scanposp.entity;

import java.math.BigDecimal;
import java.util.Date;

public class MemberPlatFee {
	
	private Integer id;
	
	private Integer memberId;
	
	private String memberCode;
	
	private String memberName;
	
	private BigDecimal platFee;
    
    private BigDecimal agentFee;
    
    private BigDecimal agentFeeLevel2;

    private BigDecimal memberFee;
    
    private String agentName;
	
	private String payMode;
	
	private String payType;
	
	private String routeId;
	
	private String merchantCode;
	
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

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public BigDecimal getPlatFee() {
		return platFee;
	}

	public void setPlatFee(BigDecimal platFee) {
		this.platFee = platFee;
	}

	public BigDecimal getAgentFee() {
		return agentFee;
	}

	public void setAgentFee(BigDecimal agentFee) {
		this.agentFee = agentFee;
	}

	public BigDecimal getMemberFee() {
		return memberFee;
	}

	public void setMemberFee(BigDecimal memberFee) {
		this.memberFee = memberFee;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getPayMode() {
		return payMode;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}

	public String getAgentOfficeId() {
		return agentOfficeId;
	}

	public void setAgentOfficeId(String agentOfficeId) {
		this.agentOfficeId = agentOfficeId;
	}

	public BigDecimal getAgentFeeLevel2() {
		return agentFeeLevel2;
	}

	public void setAgentFeeLevel2(BigDecimal agentFeeLevel2) {
		this.agentFeeLevel2 = agentFeeLevel2;
	}

	
    
	
    

}
