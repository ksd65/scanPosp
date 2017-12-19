package com.epay.scanposp.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Account {
    private Integer id;

    private Integer memberId;

    private BigDecimal balance;

    private BigDecimal freezeMoney;
    
    private BigDecimal w5Balance;
    
    private BigDecimal w6Balance;
    
    private BigDecimal w0Balance;

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

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getFreezeMoney() {
        return freezeMoney;
    }

    public void setFreezeMoney(BigDecimal freezeMoney) {
        this.freezeMoney = freezeMoney;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
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
        this.updateBy = updateBy == null ? null : updateBy.trim();
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
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag == null ? null : delFlag.trim();
    }

	public BigDecimal getW5Balance() {
		return w5Balance;
	}

	public void setW5Balance(BigDecimal w5Balance) {
		this.w5Balance = w5Balance;
	}

	public BigDecimal getW6Balance() {
		return w6Balance;
	}

	public void setW6Balance(BigDecimal w6Balance) {
		this.w6Balance = w6Balance;
	}

	public BigDecimal getW0Balance() {
		return w0Balance;
	}

	public void setW0Balance(BigDecimal w0Balance) {
		this.w0Balance = w0Balance;
	}
    
    
}