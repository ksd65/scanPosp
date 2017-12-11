package com.epay.scanposp.common.utils.slf.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 单笔收付款请求对象
 * @author administrator
 *
 */
public class ReceivePayRequest {
	private String application;
	private String version;
	private String merchantId;
	private String tranId;
	private String receivePayNotifyUrl;
	private String receivePayType;
	private String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	private String accountProp;
	private String bankGeneralName;
	private String accNo;
	private String accName;
	private String amount;
	private String credentialType;
	private String credentialNo;
	private String tel;
	private String summary;
	private String xml;
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getApplication() {
		return application;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getTranId() {
		return tranId;
	}
	public String getXml() {
		return xml;
	}
	public void setXml(String xml) {
		this.xml = xml;
	}
	public void setTranId(String tranId) {
		this.tranId = tranId;
	}
	public String getReceivePayNotifyUrl() {
		return receivePayNotifyUrl;
	}
	public void setReceivePayNotifyUrl(String receivePayNotifyUrl) {
		this.receivePayNotifyUrl = receivePayNotifyUrl;
	}
	public String getReceivePayType() {
		return receivePayType;
	}
	public void setReceivePayType(String receivePayType) {
		this.receivePayType = receivePayType;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getAccountProp() {
		return accountProp;
	}
	public void setAccountProp(String accountProp) {
		this.accountProp = accountProp;
	}
	public String getBankGeneralName() {
		return bankGeneralName;
	}
	public void setBankGeneralName(String bankGeneralName) {
		this.bankGeneralName = bankGeneralName;
	}
	public String getAccNo() {
		return accNo;
	}
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	public String getAccName() {
		return accName;
	}
	public void setAccName(String accName) {
		this.accName = accName;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
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
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
}