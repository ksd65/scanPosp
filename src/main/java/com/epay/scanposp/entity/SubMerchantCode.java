package com.epay.scanposp.entity;

import java.util.Date;

public class SubMerchantCode {
	
	private Integer id;
	
	private String routeCode;
	
	private String subMerchantCode;
	
	private String delFlag;
	
	private String createBy;

    private Date createDate;

    private String updateBy;

    private Date updateDate;
    
    private String beginTime;
    
    private String endTime;

	
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


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getSubMerchantCode() {
		return subMerchantCode;
	}

	public void setSubMerchantCode(String subMerchantCode) {
		this.subMerchantCode = subMerchantCode;
	}
    
	
    

}
