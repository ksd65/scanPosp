package com.epay.scanposp.entity;

import java.math.BigDecimal;
import java.util.Date;

public class TradeDetail {
    private Integer id;

    private String txnDate;
    
    private Integer memberId;

    private String memberCode;

    private String merchantCode;

    private BigDecimal money;

    private BigDecimal memberTradeRate;

    private BigDecimal memberDrawFee;

    private BigDecimal memberSettleMoney;

    private BigDecimal d0Money;

    private BigDecimal d0MemberFee;

    private BigDecimal d0MemberDraw;

    private BigDecimal d0RoutewayFee;

    private BigDecimal d0RoutewayDraw;

    private BigDecimal t1Money;

    private BigDecimal t1MemberFee;

    private BigDecimal t1MemberDraw;

    private BigDecimal t1RoutewayFee;

    private BigDecimal t1RoutewayDraw;

    private String orderCode;

    private String orderNumOuter;
    
    private String txnMethod;

    private String txnType;

    private String ptSerialNo;

    private String reqDate;

    private String respDate;

    private String respType;

    private String respCode;

    private String respMsg;

    private String cardType;

    private String routeId;

    private String payTime;

    private String balanceDate;

    private String channelNo;

    private String settleType;

    private String interfaceType;

    private String platformType;

    private String settleCancelFlag;

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

    
    
    
    public String getTxnDate() {
		return txnDate;
	}

	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
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

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getMemberTradeRate() {
        return memberTradeRate;
    }

    public void setMemberTradeRate(BigDecimal memberTradeRate) {
        this.memberTradeRate = memberTradeRate;
    }

    public BigDecimal getMemberDrawFee() {
        return memberDrawFee;
    }

    public void setMemberDrawFee(BigDecimal memberDrawFee) {
        this.memberDrawFee = memberDrawFee;
    }

    public BigDecimal getMemberSettleMoney() {
        return memberSettleMoney;
    }

    public void setMemberSettleMoney(BigDecimal memberSettleMoney) {
        this.memberSettleMoney = memberSettleMoney;
    }

    public BigDecimal getD0Money() {
        return d0Money;
    }

    public void setD0Money(BigDecimal d0Money) {
        this.d0Money = d0Money;
    }

    public BigDecimal getD0MemberFee() {
        return d0MemberFee;
    }

    public void setD0MemberFee(BigDecimal d0MemberFee) {
        this.d0MemberFee = d0MemberFee;
    }

    public BigDecimal getD0MemberDraw() {
        return d0MemberDraw;
    }

    public void setD0MemberDraw(BigDecimal d0MemberDraw) {
        this.d0MemberDraw = d0MemberDraw;
    }

    public BigDecimal getD0RoutewayFee() {
        return d0RoutewayFee;
    }

    public void setD0RoutewayFee(BigDecimal d0RoutewayFee) {
        this.d0RoutewayFee = d0RoutewayFee;
    }

    public BigDecimal getD0RoutewayDraw() {
        return d0RoutewayDraw;
    }

    public void setD0RoutewayDraw(BigDecimal d0RoutewayDraw) {
        this.d0RoutewayDraw = d0RoutewayDraw;
    }

    public BigDecimal getT1Money() {
        return t1Money;
    }

    public void setT1Money(BigDecimal t1Money) {
        this.t1Money = t1Money;
    }

    public BigDecimal getT1MemberFee() {
        return t1MemberFee;
    }

    public void setT1MemberFee(BigDecimal t1MemberFee) {
        this.t1MemberFee = t1MemberFee;
    }

    public BigDecimal getT1MemberDraw() {
        return t1MemberDraw;
    }

    public void setT1MemberDraw(BigDecimal t1MemberDraw) {
        this.t1MemberDraw = t1MemberDraw;
    }

    public BigDecimal getT1RoutewayFee() {
        return t1RoutewayFee;
    }

    public void setT1RoutewayFee(BigDecimal t1RoutewayFee) {
        this.t1RoutewayFee = t1RoutewayFee;
    }

    public BigDecimal getT1RoutewayDraw() {
        return t1RoutewayDraw;
    }

    public void setT1RoutewayDraw(BigDecimal t1RoutewayDraw) {
        this.t1RoutewayDraw = t1RoutewayDraw;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode == null ? null : orderCode.trim();
    }

    public String getOrderNumOuter() {
        return orderNumOuter;
    }

    public void setOrderNumOuter(String orderNumOuter) {
        this.orderNumOuter = orderNumOuter == null ? null : orderNumOuter.trim();
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType == null ? null : txnType.trim();
    }

    public String getPtSerialNo() {
        return ptSerialNo;
    }

    public void setPtSerialNo(String ptSerialNo) {
        this.ptSerialNo = ptSerialNo == null ? null : ptSerialNo.trim();
    }

    public String getReqDate() {
        return reqDate;
    }

    public void setReqDate(String reqDate) {
        this.reqDate = reqDate == null ? null : reqDate.trim();
    }

    public String getRespDate() {
        return respDate;
    }

    public void setRespDate(String respDate) {
        this.respDate = respDate == null ? null : respDate.trim();
    }

    public String getRespType() {
        return respType;
    }

    public void setRespType(String respType) {
        this.respType = respType == null ? null : respType.trim();
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

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType == null ? null : cardType.trim();
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId == null ? null : routeId.trim();
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime == null ? null : payTime.trim();
    }

    public String getBalanceDate() {
        return balanceDate;
    }

    public void setBalanceDate(String balanceDate) {
        this.balanceDate = balanceDate == null ? null : balanceDate.trim();
    }

    public String getChannelNo() {
        return channelNo;
    }

    public void setChannelNo(String channelNo) {
        this.channelNo = channelNo == null ? null : channelNo.trim();
    }

    public String getSettleType() {
        return settleType;
    }

    public void setSettleType(String settleType) {
        this.settleType = settleType == null ? null : settleType.trim();
    }

    public String getInterfaceType() {
        return interfaceType;
    }

    public void setInterfaceType(String interfaceType) {
        this.interfaceType = interfaceType == null ? null : interfaceType.trim();
    }

    public String getPlatformType() {
        return platformType;
    }

    public void setPlatformType(String platformType) {
        this.platformType = platformType == null ? null : platformType.trim();
    }

    public String getSettleCancelFlag() {
        return settleCancelFlag;
    }

    public void setSettleCancelFlag(String settleCancelFlag) {
        this.settleCancelFlag = settleCancelFlag == null ? null : settleCancelFlag.trim();
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

	public String getTxnMethod() {
		return txnMethod;
	}

	public void setTxnMethod(String txnMethod) {
		this.txnMethod = txnMethod;
	}
    
    
}