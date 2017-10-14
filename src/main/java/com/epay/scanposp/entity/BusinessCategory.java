package com.epay.scanposp.entity;

import java.util.Date;

public class BusinessCategory {
    private Integer id;

    private String wxCategoryName;

    private String wxCategoryId;

    private String zfbCategoryName;

    private String zfbCategoryId;

    private String qqCategoryName;

    private String qqCategoryId;

    private String bdCategoryName;

    private String bdCategoryId;

    private String jdCategoryName;

    private String jdCategoryId;

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

    public String getWxCategoryName() {
        return wxCategoryName;
    }

    public void setWxCategoryName(String wxCategoryName) {
        this.wxCategoryName = wxCategoryName == null ? null : wxCategoryName.trim();
    }

    public String getWxCategoryId() {
        return wxCategoryId;
    }

    public void setWxCategoryId(String wxCategoryId) {
        this.wxCategoryId = wxCategoryId == null ? null : wxCategoryId.trim();
    }

    public String getZfbCategoryName() {
        return zfbCategoryName;
    }

    public void setZfbCategoryName(String zfbCategoryName) {
        this.zfbCategoryName = zfbCategoryName == null ? null : zfbCategoryName.trim();
    }

    public String getZfbCategoryId() {
        return zfbCategoryId;
    }

    public void setZfbCategoryId(String zfbCategoryId) {
        this.zfbCategoryId = zfbCategoryId == null ? null : zfbCategoryId.trim();
    }

    public String getQqCategoryName() {
        return qqCategoryName;
    }

    public void setQqCategoryName(String qqCategoryName) {
        this.qqCategoryName = qqCategoryName == null ? null : qqCategoryName.trim();
    }

    public String getQqCategoryId() {
        return qqCategoryId;
    }

    public void setQqCategoryId(String qqCategoryId) {
        this.qqCategoryId = qqCategoryId == null ? null : qqCategoryId.trim();
    }

    public String getBdCategoryName() {
        return bdCategoryName;
    }

    public void setBdCategoryName(String bdCategoryName) {
        this.bdCategoryName = bdCategoryName == null ? null : bdCategoryName.trim();
    }

    public String getBdCategoryId() {
        return bdCategoryId;
    }

    public void setBdCategoryId(String bdCategoryId) {
        this.bdCategoryId = bdCategoryId == null ? null : bdCategoryId.trim();
    }

    public String getJdCategoryName() {
        return jdCategoryName;
    }

    public void setJdCategoryName(String jdCategoryName) {
        this.jdCategoryName = jdCategoryName == null ? null : jdCategoryName.trim();
    }

    public String getJdCategoryId() {
        return jdCategoryId;
    }

    public void setJdCategoryId(String jdCategoryId) {
        this.jdCategoryId = jdCategoryId == null ? null : jdCategoryId.trim();
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