package com.epay.scanposp.entity;

import java.math.BigDecimal;
import java.util.Date;

public class EpayCode {
    private Integer id;

    private String payCode;

    private String status;

    private String officeId;

    private Integer memberId;

    private String batchNo;

    private String createBatchNo;

    private String path;

    private String flow;
    
    private BigDecimal t0DrawFee;

    private BigDecimal t0TradeRate;

    private BigDecimal t1DrawFee;

    private BigDecimal t1TradeRate;
    
    private BigDecimal mlJfFee;

    private BigDecimal mlJfRate;

    private BigDecimal mlWjfFee;

    private BigDecimal mlWjfRate;

    private String createBy;

    private Date createDate;

    private String updateBy;

    private Date updateDate;

    private String remarks;

    private String delFlag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getCreateBatchNo() {
        return createBatchNo;
    }

    public void setCreateBatchNo(String createBatchNo) {
        this.createBatchNo = createBatchNo;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFlow() {
        return flow;
    }

    public void setFlow(String flow) {
        this.flow = flow;
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
    
    
    
    
    public BigDecimal getMlJfFee() {
		return mlJfFee;
	}

	public void setMlJfFee(BigDecimal mlJfFee) {
		this.mlJfFee = mlJfFee;
	}

	public BigDecimal getMlJfRate() {
		return mlJfRate;
	}

	public void setMlJfRate(BigDecimal mlJfRate) {
		this.mlJfRate = mlJfRate;
	}

	public BigDecimal getMlWjfFee() {
		return mlWjfFee;
	}

	public void setMlWjfFee(BigDecimal mlWjfFee) {
		this.mlWjfFee = mlWjfFee;
	}

	public BigDecimal getMlWjfRate() {
		return mlWjfRate;
	}

	public void setMlWjfRate(BigDecimal mlWjfRate) {
		this.mlWjfRate = mlWjfRate;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
}