package com.epay.scanposp.entity;

import java.math.BigDecimal;
import java.util.Date;

public class MemberInfo {
    private Integer id;

    private String code;

    private String type;

    private String loginCode;

    private String loginPass;

    private Integer parentId;

    private String email;

    private String mobilePhone;

    private String name;

    private String shortName;

    private String contact;

    private String level;

    private String drawStatus;

    private String status;

    private String wxStatus;

    private String zfbStatus;

    private String qqStatus;

    private String bdStatus;

    private String jdStatus;

    private String mlJfStatus;

    private String mlWjfStatus;

    private String sexType;

    private Date birthday;

    private String homePhone;

    private String certNbr;

    private String certPic1;

    private String certPic2;

    private String memcertPic;

    private String cardNbr;

    private String cardPic1;

    private String cardPic2;
    
    private String authPic;

    private String busLicenceNbr;

    private String addr;

    private String category;

    private String province;

    private String city;

    private String county;

    private String contactType;

    private String verifyFlag;

    private String wxRouteId;

    private String wxMemberCode;

    private String wxMerchantCode;

    private String settleType;

    private String zfbRouteId;

    private String zfbMemberCode;

    private String zfbMerchantCode;

    private String qqRouteId;

    private String qqMemberCode;

    private String qqMerchantCode;

    private String bdRouteId;

    private String bdMemberCode;

    private String bdMerchantCode;

    private String jdRouteId;

    private String jdMemberCode;

    private String jdMerchantCode;

    private String wxChannelMerchantCode;

    private String qqChannelMerchantCode;

    private String zfbChannelMerchantCode;

    private String bdChannelMerchantCode;

    private String jdChannelMerchantCode;

    private BigDecimal t0DrawFee;

    private BigDecimal t0TradeRate;

    private BigDecimal t1DrawFee;

    private BigDecimal t1TradeRate;

    private BigDecimal mlJfFee;

    private BigDecimal mlJfRate;

    private BigDecimal mlWjfFee;

    private BigDecimal mlWjfRate;

    private String payCode;

    private BigDecimal singleLimit;

    private BigDecimal dayLimit;

    private String busPic;

    private String headPic;

    private String deskPic;

    private String insidePic;

    private String staffPic;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode == null ? null : loginCode.trim();
    }

    public String getLoginPass() {
        return loginPass;
    }

    public void setLoginPass(String loginPass) {
        this.loginPass = loginPass == null ? null : loginPass.trim();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone == null ? null : mobilePhone.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName == null ? null : shortName.trim();
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact == null ? null : contact.trim();
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
    }

    public String getDrawStatus() {
        return drawStatus;
    }

    public void setDrawStatus(String drawStatus) {
        this.drawStatus = drawStatus == null ? null : drawStatus.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getWxStatus() {
        return wxStatus;
    }

    public void setWxStatus(String wxStatus) {
        this.wxStatus = wxStatus == null ? null : wxStatus.trim();
    }

    public String getZfbStatus() {
        return zfbStatus;
    }

    public void setZfbStatus(String zfbStatus) {
        this.zfbStatus = zfbStatus == null ? null : zfbStatus.trim();
    }

    public String getQqStatus() {
        return qqStatus;
    }

    public void setQqStatus(String qqStatus) {
        this.qqStatus = qqStatus == null ? null : qqStatus.trim();
    }

    public String getBdStatus() {
        return bdStatus;
    }

    public void setBdStatus(String bdStatus) {
        this.bdStatus = bdStatus == null ? null : bdStatus.trim();
    }

    public String getJdStatus() {
        return jdStatus;
    }

    public void setJdStatus(String jdStatus) {
        this.jdStatus = jdStatus == null ? null : jdStatus.trim();
    }

    public String getMlJfStatus() {
        return mlJfStatus;
    }

    public void setMlJfStatus(String mlJfStatus) {
        this.mlJfStatus = mlJfStatus == null ? null : mlJfStatus.trim();
    }

    public String getMlWjfStatus() {
        return mlWjfStatus;
    }

    public void setMlWjfStatus(String mlWjfStatus) {
        this.mlWjfStatus = mlWjfStatus == null ? null : mlWjfStatus.trim();
    }

    public String getSexType() {
        return sexType;
    }

    public void setSexType(String sexType) {
        this.sexType = sexType == null ? null : sexType.trim();
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone == null ? null : homePhone.trim();
    }

    public String getCertNbr() {
        return certNbr;
    }

    public void setCertNbr(String certNbr) {
        this.certNbr = certNbr == null ? null : certNbr.trim();
    }

    public String getCertPic1() {
        return certPic1;
    }

    public void setCertPic1(String certPic1) {
        this.certPic1 = certPic1 == null ? null : certPic1.trim();
    }

    public String getCertPic2() {
        return certPic2;
    }

    public void setCertPic2(String certPic2) {
        this.certPic2 = certPic2 == null ? null : certPic2.trim();
    }

    public String getMemcertPic() {
        return memcertPic;
    }

    public void setMemcertPic(String memcertPic) {
        this.memcertPic = memcertPic == null ? null : memcertPic.trim();
    }

    public String getCardNbr() {
        return cardNbr;
    }

    public void setCardNbr(String cardNbr) {
        this.cardNbr = cardNbr == null ? null : cardNbr.trim();
    }

    public String getCardPic1() {
        return cardPic1;
    }

    public void setCardPic1(String cardPic1) {
        this.cardPic1 = cardPic1 == null ? null : cardPic1.trim();
    }

    public String getCardPic2() {
        return cardPic2;
    }

    public void setCardPic2(String cardPic2) {
        this.cardPic2 = cardPic2 == null ? null : cardPic2.trim();
    }

    public String getBusLicenceNbr() {
        return busLicenceNbr;
    }

    public void setBusLicenceNbr(String busLicenceNbr) {
        this.busLicenceNbr = busLicenceNbr == null ? null : busLicenceNbr.trim();
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr == null ? null : addr.trim();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county == null ? null : county.trim();
    }

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType == null ? null : contactType.trim();
    }

    public String getVerifyFlag() {
        return verifyFlag;
    }

    public void setVerifyFlag(String verifyFlag) {
        this.verifyFlag = verifyFlag == null ? null : verifyFlag.trim();
    }

    public String getWxRouteId() {
        return wxRouteId;
    }

    public void setWxRouteId(String wxRouteId) {
        this.wxRouteId = wxRouteId == null ? null : wxRouteId.trim();
    }

    public String getWxMemberCode() {
        return wxMemberCode;
    }

    public void setWxMemberCode(String wxMemberCode) {
        this.wxMemberCode = wxMemberCode == null ? null : wxMemberCode.trim();
    }

    public String getWxMerchantCode() {
        return wxMerchantCode;
    }

    public void setWxMerchantCode(String wxMerchantCode) {
        this.wxMerchantCode = wxMerchantCode == null ? null : wxMerchantCode.trim();
    }

    public String getSettleType() {
        return settleType;
    }

    public void setSettleType(String settleType) {
        this.settleType = settleType == null ? null : settleType.trim();
    }

    public String getZfbRouteId() {
        return zfbRouteId;
    }

    public void setZfbRouteId(String zfbRouteId) {
        this.zfbRouteId = zfbRouteId == null ? null : zfbRouteId.trim();
    }

    public String getZfbMemberCode() {
        return zfbMemberCode;
    }

    public void setZfbMemberCode(String zfbMemberCode) {
        this.zfbMemberCode = zfbMemberCode == null ? null : zfbMemberCode.trim();
    }

    public String getZfbMerchantCode() {
        return zfbMerchantCode;
    }

    public void setZfbMerchantCode(String zfbMerchantCode) {
        this.zfbMerchantCode = zfbMerchantCode == null ? null : zfbMerchantCode.trim();
    }

    public String getQqRouteId() {
        return qqRouteId;
    }

    public void setQqRouteId(String qqRouteId) {
        this.qqRouteId = qqRouteId == null ? null : qqRouteId.trim();
    }

    public String getQqMemberCode() {
        return qqMemberCode;
    }

    public void setQqMemberCode(String qqMemberCode) {
        this.qqMemberCode = qqMemberCode == null ? null : qqMemberCode.trim();
    }

    public String getQqMerchantCode() {
        return qqMerchantCode;
    }

    public void setQqMerchantCode(String qqMerchantCode) {
        this.qqMerchantCode = qqMerchantCode == null ? null : qqMerchantCode.trim();
    }

    public String getBdRouteId() {
        return bdRouteId;
    }

    public void setBdRouteId(String bdRouteId) {
        this.bdRouteId = bdRouteId == null ? null : bdRouteId.trim();
    }

    public String getBdMemberCode() {
        return bdMemberCode;
    }

    public void setBdMemberCode(String bdMemberCode) {
        this.bdMemberCode = bdMemberCode == null ? null : bdMemberCode.trim();
    }

    public String getBdMerchantCode() {
        return bdMerchantCode;
    }

    public void setBdMerchantCode(String bdMerchantCode) {
        this.bdMerchantCode = bdMerchantCode == null ? null : bdMerchantCode.trim();
    }

    public String getJdRouteId() {
        return jdRouteId;
    }

    public void setJdRouteId(String jdRouteId) {
        this.jdRouteId = jdRouteId == null ? null : jdRouteId.trim();
    }

    public String getJdMemberCode() {
        return jdMemberCode;
    }

    public void setJdMemberCode(String jdMemberCode) {
        this.jdMemberCode = jdMemberCode == null ? null : jdMemberCode.trim();
    }

    public String getJdMerchantCode() {
        return jdMerchantCode;
    }

    public void setJdMerchantCode(String jdMerchantCode) {
        this.jdMerchantCode = jdMerchantCode == null ? null : jdMerchantCode.trim();
    }

    public String getWxChannelMerchantCode() {
        return wxChannelMerchantCode;
    }

    public void setWxChannelMerchantCode(String wxChannelMerchantCode) {
        this.wxChannelMerchantCode = wxChannelMerchantCode == null ? null : wxChannelMerchantCode.trim();
    }

    public String getQqChannelMerchantCode() {
        return qqChannelMerchantCode;
    }

    public void setQqChannelMerchantCode(String qqChannelMerchantCode) {
        this.qqChannelMerchantCode = qqChannelMerchantCode == null ? null : qqChannelMerchantCode.trim();
    }

    public String getZfbChannelMerchantCode() {
        return zfbChannelMerchantCode;
    }

    public void setZfbChannelMerchantCode(String zfbChannelMerchantCode) {
        this.zfbChannelMerchantCode = zfbChannelMerchantCode == null ? null : zfbChannelMerchantCode.trim();
    }

    public String getBdChannelMerchantCode() {
        return bdChannelMerchantCode;
    }

    public void setBdChannelMerchantCode(String bdChannelMerchantCode) {
        this.bdChannelMerchantCode = bdChannelMerchantCode == null ? null : bdChannelMerchantCode.trim();
    }

    public String getJdChannelMerchantCode() {
        return jdChannelMerchantCode;
    }

    public void setJdChannelMerchantCode(String jdChannelMerchantCode) {
        this.jdChannelMerchantCode = jdChannelMerchantCode == null ? null : jdChannelMerchantCode.trim();
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

    public BigDecimal getMlJfFee() {
        return mlJfFee;
    }

    public void setMlJfFee(BigDecimal mlJfFee) {
        this.mlJfFee = mlJfFee;
    }

    public BigDecimal getMlJfRate() {
        return mlJfRate;
    }

    public void setMlJfRate(BigDecimal mlJfRate) {
        this.mlJfRate = mlJfRate;
    }

    public BigDecimal getMlWjfFee() {
        return mlWjfFee;
    }

    public void setMlWjfFee(BigDecimal mlWjfFee) {
        this.mlWjfFee = mlWjfFee;
    }

    public BigDecimal getMlWjfRate() {
        return mlWjfRate;
    }

    public void setMlWjfRate(BigDecimal mlWjfRate) {
        this.mlWjfRate = mlWjfRate;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode == null ? null : payCode.trim();
    }

    public BigDecimal getSingleLimit() {
        return singleLimit;
    }

    public void setSingleLimit(BigDecimal singleLimit) {
        this.singleLimit = singleLimit;
    }

    public BigDecimal getDayLimit() {
        return dayLimit;
    }

    public void setDayLimit(BigDecimal dayLimit) {
        this.dayLimit = dayLimit;
    }

    public String getBusPic() {
        return busPic;
    }

    public void setBusPic(String busPic) {
        this.busPic = busPic == null ? null : busPic.trim();
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic == null ? null : headPic.trim();
    }

    public String getDeskPic() {
        return deskPic;
    }

    public void setDeskPic(String deskPic) {
        this.deskPic = deskPic == null ? null : deskPic.trim();
    }

    public String getInsidePic() {
        return insidePic;
    }

    public void setInsidePic(String insidePic) {
        this.insidePic = insidePic == null ? null : insidePic.trim();
    }

    public String getStaffPic() {
        return staffPic;
    }

    public void setStaffPic(String staffPic) {
        this.staffPic = staffPic == null ? null : staffPic.trim();
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

	public String getAuthPic() {
		return authPic;
	}

	public void setAuthPic(String authPic) {
		this.authPic = authPic;
	}
}