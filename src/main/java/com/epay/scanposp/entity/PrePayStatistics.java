package com.epay.scanposp.entity;

import java.math.BigDecimal;
import java.util.Date;

public class PrePayStatistics {
    private Integer id;

    private Integer memberId;
    
    private BigDecimal tradeRate;
    
    private BigDecimal preMoney;
    
    private BigDecimal hisTradeMoney;
    
    private BigDecimal hisUsePreMoney;
    
    private BigDecimal todayTradeMoney;
    
    private BigDecimal undealMoney;

    private String createBy;

    private Date createDate;

    private String updateBy;

    private Date updateDate;

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

	public BigDecimal getPreMoney() {
		return preMoney;
	}

	public void setPreMoney(BigDecimal preMoney) {
		this.preMoney = preMoney;
	}

	public BigDecimal getHisTradeMoney() {
		return hisTradeMoney;
	}

	public void setHisTradeMoney(BigDecimal hisTradeMoney) {
		this.hisTradeMoney = hisTradeMoney;
	}

	public BigDecimal getTodayTradeMoney() {
		return todayTradeMoney;
	}

	public void setTodayTradeMoney(BigDecimal todayTradeMoney) {
		this.todayTradeMoney = todayTradeMoney;
	}

	public BigDecimal getUndealMoney() {
		return undealMoney;
	}

	public void setUndealMoney(BigDecimal undealMoney) {
		this.undealMoney = undealMoney;
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

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public BigDecimal getTradeRate() {
		return tradeRate;
	}

	public void setTradeRate(BigDecimal tradeRate) {
		this.tradeRate = tradeRate;
	}

	public BigDecimal getHisUsePreMoney() {
		return hisUsePreMoney;
	}

	public void setHisUsePreMoney(BigDecimal hisUsePreMoney) {
		this.hisUsePreMoney = hisUsePreMoney;
	}

    
    
    
}