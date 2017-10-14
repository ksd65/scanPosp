package com.epay.scanposp.entity;

import java.math.BigDecimal;
import java.util.Date;

public class AgentRate {
    private Integer id;

    private String officeId;

    private BigDecimal t0DrawFee;

    private BigDecimal t0TradeRate;

    private BigDecimal t1DrawFee;

    private BigDecimal t1TradeRate;

    private BigDecimal bonusQuickRate;

    private BigDecimal bonusQuickFee;

    private BigDecimal quickRate;

    private BigDecimal quickFee;

    private BigDecimal mT0DrawFee;

    private BigDecimal mT0TradeRate;

    private BigDecimal mT1DrawFee;

    private BigDecimal mT1TradeRate;

    private BigDecimal mBonusQuickRate;

    private BigDecimal mBonusQuickFee;

    private BigDecimal mQuickRate;

    private BigDecimal mQuickFee;

    private String mUrl;

    private String mImg;

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

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId == null ? null : officeId.trim();
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

    public BigDecimal getBonusQuickRate() {
        return bonusQuickRate;
    }

    public void setBonusQuickRate(BigDecimal bonusQuickRate) {
        this.bonusQuickRate = bonusQuickRate;
    }

    public BigDecimal getBonusQuickFee() {
        return bonusQuickFee;
    }

    public void setBonusQuickFee(BigDecimal bonusQuickFee) {
        this.bonusQuickFee = bonusQuickFee;
    }

    public BigDecimal getQuickRate() {
        return quickRate;
    }

    public void setQuickRate(BigDecimal quickRate) {
        this.quickRate = quickRate;
    }

    public BigDecimal getQuickFee() {
        return quickFee;
    }

    public void setQuickFee(BigDecimal quickFee) {
        this.quickFee = quickFee;
    }

    public BigDecimal getmT0DrawFee() {
        return mT0DrawFee;
    }

    public void setmT0DrawFee(BigDecimal mT0DrawFee) {
        this.mT0DrawFee = mT0DrawFee;
    }

    public BigDecimal getmT0TradeRate() {
        return mT0TradeRate;
    }

    public void setmT0TradeRate(BigDecimal mT0TradeRate) {
        this.mT0TradeRate = mT0TradeRate;
    }

    public BigDecimal getmT1DrawFee() {
        return mT1DrawFee;
    }

    public void setmT1DrawFee(BigDecimal mT1DrawFee) {
        this.mT1DrawFee = mT1DrawFee;
    }

    public BigDecimal getmT1TradeRate() {
        return mT1TradeRate;
    }

    public void setmT1TradeRate(BigDecimal mT1TradeRate) {
        this.mT1TradeRate = mT1TradeRate;
    }

    public BigDecimal getmBonusQuickRate() {
        return mBonusQuickRate;
    }

    public void setmBonusQuickRate(BigDecimal mBonusQuickRate) {
        this.mBonusQuickRate = mBonusQuickRate;
    }

    public BigDecimal getmBonusQuickFee() {
        return mBonusQuickFee;
    }

    public void setmBonusQuickFee(BigDecimal mBonusQuickFee) {
        this.mBonusQuickFee = mBonusQuickFee;
    }

    public BigDecimal getmQuickRate() {
        return mQuickRate;
    }

    public void setmQuickRate(BigDecimal mQuickRate) {
        this.mQuickRate = mQuickRate;
    }

    public BigDecimal getmQuickFee() {
        return mQuickFee;
    }

    public void setmQuickFee(BigDecimal mQuickFee) {
        this.mQuickFee = mQuickFee;
    }

    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl == null ? null : mUrl.trim();
    }

    public String getmImg() {
        return mImg;
    }

    public void setmImg(String mImg) {
        this.mImg = mImg == null ? null : mImg.trim();
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