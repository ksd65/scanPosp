package com.epay.scanposp.entity;

import java.math.BigDecimal;
import java.util.Date;

public class RoutewayDraw {
    private Integer id;

    private Integer memberId;

    private String memberCode;

    private String merchantCode;
    
    private String routeCode;

    private BigDecimal memberTradeRate;

    private BigDecimal money;

    private String orderCode;

    private String ptSerialNo;

    private String reqDate;

    private String respDate;

    private String respType;

    private String respCode;

    private String respMsg;

    private BigDecimal drawamount;

    private BigDecimal drawfee;

    private BigDecimal tradefee;

    private String settleDate;

    private String createBy;

    private Date createDate;

    private String updateBy;

    private Date updateDate;

    private String remarks;

    private String delFlag;
    
    private String auditStatus;
    
    private String auditBy;

    private Date auditDate;
    
    private String drawType;
    
    private String orderNumOuter;
    
    private String bankName;
    
    private String bankAccount;
    
    private String accountName;
    
    private String certNo;
    
    private String tel;
    
    private String subName;
    
    private String subId;
    
    private String bankCode;

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

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public BigDecimal getMemberTradeRate() {
        return memberTradeRate;
    }

    public void setMemberTradeRate(BigDecimal memberTradeRate) {
        this.memberTradeRate = memberTradeRate;
    }


    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getPtSerialNo() {
        return ptSerialNo;
    }

    public void setPtSerialNo(String ptSerialNo) {
        this.ptSerialNo = ptSerialNo;
    }

    public String getReqDate() {
        return reqDate;
    }

    public void setReqDate(String reqDate) {
        this.reqDate = reqDate;
    }

    public String getRespDate() {
        return respDate;
    }

    public void setRespDate(String respDate) {
        this.respDate = respDate;
    }

    public String getRespType() {
        return respType;
    }

    public void setRespType(String respType) {
        this.respType = respType;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public BigDecimal getDrawamount() {
        return drawamount;
    }

    public void setDrawamount(BigDecimal drawamount) {
        this.drawamount = drawamount;
    }

    public BigDecimal getDrawfee() {
        return drawfee;
    }

    public void setDrawfee(BigDecimal drawfee) {
        this.drawfee = drawfee;
    }

    public BigDecimal getTradefee() {
        return tradefee;
    }

    public void setTradefee(BigDecimal tradefee) {
        this.tradefee = tradefee;
    }

    public String getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate;
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

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getAuditBy() {
		return auditBy;
	}

	public void setAuditBy(String auditBy) {
		this.auditBy = auditBy;
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public String getDrawType() {
		return drawType;
	}

	public void setDrawType(String drawType) {
		this.drawType = drawType;
	}

	public String getOrderNumOuter() {
		return orderNumOuter;
	}

	public void setOrderNumOuter(String orderNumOuter) {
		this.orderNumOuter = orderNumOuter;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getRouteCode() {
		return routeCode;
	}

	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	public String getSubId() {
		return subId;
	}

	public void setSubId(String subId) {
		this.subId = subId;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
    
    
}