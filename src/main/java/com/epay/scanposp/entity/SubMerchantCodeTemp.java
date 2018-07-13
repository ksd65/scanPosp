package com.epay.scanposp.entity;

import java.util.Date;

public class SubMerchantCodeTemp {
	
	private Integer id;
	
	private String routeCode;
	
	private String subMerchantCode;	
	
	private String delFlag;
	
	private String createBy;

    private Date createDate;

    private String updateBy;

    private Date updateDate;
    
    private String remarks;

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

	
	
	public String getSubMerchantCode() {
		return subMerchantCode;
	}

	public void setSubMerchantCode(String subMerchantCode) {
		this.subMerchantCode = subMerchantCode;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getRouteCode() {
		return routeCode;
	}

	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}
    
	
    

}
