package com.epay.scanposp.entity;

import java.util.Date;

public class PayRoute {
	
	private Integer payRouteId;
	
	private String routeCode;
	
	private String tranCode;
	
	private String aisleType;
	
	private String delFlag;
	
	private String createBy;

    private Date createDate;

    private String updateBy;

    private Date updateDate;

	public Integer getPayRouteId() {
		return payRouteId;
	}

	public void setPayRouteId(Integer payRouteId) {
		this.payRouteId = payRouteId;
	}

	public String getRouteCode() {
		return routeCode;
	}

	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}

	public String getTranCode() {
		return tranCode;
	}

	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}

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
    
    

}
