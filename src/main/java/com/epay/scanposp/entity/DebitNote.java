package com.epay.scanposp.entity;

import java.math.BigDecimal;
import java.util.Date;

public class DebitNote {
    private Integer id;

    private BigDecimal money;

    private Integer memberId;

    private String memberCode;

    private String merchantCode;

    private String orderCode;
    
    private String orderNumOuter;

    private String settleType;
    
    private String txnMethod;

    private String txnType;
    
    private BigDecimal tradeRate;

    private String status;

    private String routeId;

    private String respCode;

    private String respMsg;

    private String createBy;

    private Date createDate;

    private String updateBy;

    private Date updateDate;

    private String remarks;
    
    private String ip;
    
    private String userAgent;
    
    private String browser;
    
    private String deviceInfo;
    
    private Integer payQrCodeId;
    
    private String payAccount;
    
    private String payUserName;
    
    private Integer payeeId;
    
    private String qrorderDealStatus;
    
    private String preType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
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
        this.memberCode = memberCode == null ? null : memberCode.trim();
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode == null ? null : merchantCode.trim();
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode == null ? null : orderCode.trim();
    }

    public String getSettleType() {
        return settleType;
    }

    public void setSettleType(String settleType) {
        this.settleType = settleType == null ? null : settleType.trim();
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType == null ? null : txnType.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId == null ? null : routeId.trim();
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode == null ? null : respCode.trim();
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg == null ? null : respMsg.trim();
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

	public BigDecimal getTradeRate() {
		return tradeRate;
	}

	public void setTradeRate(BigDecimal tradeRate) {
		this.tradeRate = tradeRate;
	}

	public String getOrderNumOuter() {
		return orderNumOuter;
	}

	public void setOrderNumOuter(String orderNumOuter) {
		this.orderNumOuter = orderNumOuter;
	}

	public String getTxnMethod() {
		return txnMethod;
	}

	public void setTxnMethod(String txnMethod) {
		this.txnMethod = txnMethod;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	public Integer getPayQrCodeId() {
		return payQrCodeId;
	}

	public void setPayQrCodeId(Integer payQrCodeId) {
		this.payQrCodeId = payQrCodeId;
	}

	public String getPayAccount() {
		return payAccount;
	}

	public void setPayAccount(String payAccount) {
		this.payAccount = payAccount;
	}

	public String getPayUserName() {
		return payUserName;
	}

	public void setPayUserName(String payUserName) {
		this.payUserName = payUserName;
	}

	public Integer getPayeeId() {
		return payeeId;
	}

	public void setPayeeId(Integer payeeId) {
		this.payeeId = payeeId;
	}

	public String getQrorderDealStatus() {
		return qrorderDealStatus;
	}

	public void setQrorderDealStatus(String qrorderDealStatus) {
		this.qrorderDealStatus = qrorderDealStatus;
	}

	public String getPreType() {
		return preType;
	}

	public void setPreType(String preType) {
		this.preType = preType;
	}
    
    
    
}