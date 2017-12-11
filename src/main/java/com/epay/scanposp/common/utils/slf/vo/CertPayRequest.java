package com.epay.scanposp.common.utils.slf.vo;

public class CertPayRequest {
	
	private String application;
	private String version;
	private String merchantId;
	private String merchantOrderId;
	private String merchantOrderAmt;
	private String merchantOrderDesc;
	private String payerId = "";
	private String userName = "";
	private String salerId = "";
	private String guaranteeAmt = "";
	private String credentialType;
	private String credentialNo;
	private String userMobileNo;
	private String cardNo;
	private String cvv2;
	private String validPeriod;
	private String merchantPayNotifyUrl;
	private String merchantFrontEndUrl;
	private String xml="";
	public String getApplication() {
		return application;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getMerchantOrderId() {
		return merchantOrderId;
	}
	public void setMerchantOrderId(String merchantOrderId) {
		this.merchantOrderId = merchantOrderId;
	}
	
	public String getMerchantOrderAmt() {
		return merchantOrderAmt;
	}
	public void setMerchantOrderAmt(String merchantOrderAmt) {
		this.merchantOrderAmt = merchantOrderAmt;
	}
	public String getMerchantOrderDesc() {
		return merchantOrderDesc;
	}
	public void setMerchantOrderDesc(String merchantOrderDesc) {
		this.merchantOrderDesc = merchantOrderDesc;
	}
	public String getPayerId() {
		return payerId;
	}
	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSalerId() {
		return salerId;
	}
	public void setSalerId(String salerId) {
		this.salerId = salerId;
	}
	public String getGuaranteeAmt() {
		return guaranteeAmt;
	}
	public void setGuaranteeAmt(String guaranteeAmt) {
		this.guaranteeAmt = guaranteeAmt;
	}
	public String getCredentialType() {
		return credentialType;
	}
	public void setCredentialType(String credentialType) {
		this.credentialType = credentialType;
	}
	public String getCredentialNo() {
		return credentialNo;
	}
	public void setCredentialNo(String credentialNo) {
		this.credentialNo = credentialNo;
	}
	public String getUserMobileNo() {
		return userMobileNo;
	}
	public void setUserMobileNo(String userMobileNo) {
		this.userMobileNo = userMobileNo;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	
	public String getCvv2() {
		return cvv2;
	}
	public void setCvv2(String cvv2) {
		this.cvv2 = cvv2;
	}
	public String getValidPeriod() {
		return validPeriod;
	}
	public void setValidPeriod(String validPeriod) {
		this.validPeriod = validPeriod;
	}
	public String getMerchantPayNotifyUrl() {
		return merchantPayNotifyUrl;
	}
	public void setMerchantPayNotifyUrl(String merchantPayNotifyUrl) {
		this.merchantPayNotifyUrl = merchantPayNotifyUrl;
	}
	public String getMerchantFrontEndUrl() {
		return merchantFrontEndUrl;
	}
	public void setMerchantFrontEndUrl(String merchantFrontEndUrl) {
		this.merchantFrontEndUrl = merchantFrontEndUrl;
	}
	
	/**
	 * 取得xml报文
	 * @return xml报文
	 */
	public String getXml() {
		return xml;
	}
	/**
	 * 设置xml报文
	 * @param xml xml报文
	 */
	public void setXml(String xml) {
		this.xml = xml;
	}
	@Override
	public String toString() {
		return "CertpayRequest [application=" + application + ", version="
				+ version + ", merchantId=" + merchantId + ", merchantOrderId="
				+ merchantOrderId + ", merchantOrderAmt=" + merchantOrderAmt
				+ ", merchantOrderDesc=" + merchantOrderDesc + ", payerId="
				+ payerId + ", userName=" + userName + ", credentialType="
				+ credentialType + ", credentialNo=" + credentialNo +", salerId="
				+ salerId + ", guaranteeAmt=" + guaranteeAmt
				+ ", userMobileNo=" + userMobileNo 
				+ ", cardNo=" + cardNo+ ", cvv2=" + cvv2+ ", validPeriod=" + validPeriod
				+ ", merchantPayNotifyUrl=" + merchantPayNotifyUrl
				+ ", merchantFrontEndUrl=" + merchantFrontEndUrl + ", xml="
				+ xml + "]";
	}
	

	
	
	

}
