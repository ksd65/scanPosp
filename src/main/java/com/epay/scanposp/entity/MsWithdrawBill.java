package com.epay.scanposp.entity;

import java.math.BigDecimal;
import java.util.Date;

public class MsWithdrawBill {
    private Integer id;

    private String cooperator;

    private Integer memberId;

    private String merchantCode;

    private BigDecimal memberTradeRate;
    private String smzfMsgId;

    private String reqMsgId;

    private String accNo;

    private String accName;

    private BigDecimal drawAmount;

    private BigDecimal drawFee;

    private BigDecimal tradeFee;

    private String settleDate;

    private String respType;

    private String respCode;

    private String respMsg;

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

    public String getCooperator1() {
        return cooperator;
    }

    public void setCooperator1(String cooperator) {
        this.cooperator = cooperator == null ? null : cooperator.trim();
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode == null ? null : merchantCode.trim();
    }

    public String getSmzfMsgId() {
        return smzfMsgId;
    }

    public BigDecimal getMemberTradeRate() {
        return memberTradeRate;
    }
    public void setMemberTradeRate(BigDecimal memberTradeRate) {
        this.memberTradeRate = memberTradeRate;
    }
    public void setSmzfMsgId(String smzfMsgId) {
        this.smzfMsgId = smzfMsgId == null ? null : smzfMsgId.trim();
    }

    public String getReqMsgId() {
        return reqMsgId;
    }

    public void setReqMsgId(String reqMsgId) {
        this.reqMsgId = reqMsgId == null ? null : reqMsgId.trim();
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo == null ? null : accNo.trim();
    }

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName == null ? null : accName.trim();
    }

    public BigDecimal getDrawAmount() {
        return drawAmount;
    }

    public void setDrawAmount(BigDecimal drawAmount) {
        this.drawAmount = drawAmount;
    }

    public BigDecimal getDrawFee() {
        return drawFee;
    }

    public void setDrawFee(BigDecimal drawFee) {
        this.drawFee = drawFee;
    }

    public BigDecimal getTradeFee() {
        return tradeFee;
    }

    public void setTradeFee(BigDecimal tradeFee) {
        this.tradeFee = tradeFee;
    }

    public String getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate == null ? null : settleDate.trim();
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
}