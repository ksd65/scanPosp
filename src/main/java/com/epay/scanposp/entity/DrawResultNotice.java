package com.epay.scanposp.entity;

import java.math.BigDecimal;
import java.util.Date;

public class DrawResultNotice {
    private Integer id;

    private Integer memberId;

    private String orderCode;

    private String orderNumOuter;

    private String returnUrl;

    private Integer counts;

    private String txnType;

    private BigDecimal drawamount;

    private BigDecimal drawfee;

    private BigDecimal tradefee;

    private String drawTime;

    private String status;

    private String respType;

    private String resultCode;

    private String resultMessage;

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

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderNumOuter() {
        return orderNumOuter;
    }

    public void setOrderNumOuter(String orderNumOuter) {
        this.orderNumOuter = orderNumOuter;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public Integer getCounts() {
        return counts;
    }

    public void setCounts(Integer counts) {
        this.counts = counts;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
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

    public String getDrawTime() {
        return drawTime;
    }

    public void setDrawTime(String drawTime) {
        this.drawTime = drawTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRespType() {
        return respType;
    }

    public void setRespType(String respType) {
        this.respType = respType;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
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