package com.epay.scanposp.entity;

public class Kbin {
    private Integer id;

    private String bankCode;

    private String bankName;

    private String kbin;

    private String len;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getKbin() {
        return kbin;
    }

    public void setKbin(String kbin) {
        this.kbin = kbin;
    }

    public String getLen() {
        return len;
    }

    public void setLen(String len) {
        this.len = len;
    }
}