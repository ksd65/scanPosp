package com.epay.scanposp.common.utils.slf.vo;

public class WeiXinScanRequest {
	
	private String application;
	private String version;
	private String timestamp;
	private String merchantId;
	private String merchantOrderId;
	private String merchantOrderAmt;
	private String merchantOrderDesc;
	private String userName = "";
	private String payerId = "";
	private String salerId = "";
	private String guaranteeAmt = "";
	private String merchantPayNotifyUrl;
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
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPayerId() {
		return payerId;
	}
	public void setPayerId(String payerId) {
		this.payerId = payerId;
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
	public String getMerchantPayNotifyUrl() {
		return merchantPayNotifyUrl;
	}
	public void setMerchantPayNotifyUrl(String merchantPayNotifyUrl) {
		this.merchantPayNotifyUrl = merchantPayNotifyUrl;
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
				+ payerId + ", userName=" + userName 
				+ ", merchantPayNotifyUrl=" + merchantPayNotifyUrl
				+ ", xml="
				+ xml + "]";
	}
}
