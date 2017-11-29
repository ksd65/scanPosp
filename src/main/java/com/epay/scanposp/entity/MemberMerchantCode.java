package com.epay.scanposp.entity;

import java.util.Date;

public class MemberMerchantCode {
	
	private Integer id;
	
	private Integer memberId;
	
	private String routeCode;
	
	private String wxMerchantCode;
	
	private String zfbMerchantCode;
	
	private String qqMerchantCode;
	
	private String bdMerchantCode;
	
	private String jdMerchantCode;
	
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
    
    

}
