package com.epay.scanposp.common.utils.slf.vo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 批量收付款请求对象
 * @author administrator
 *
 */
public class ReceivePayBatchRequest {
	private String application;
	private String version;
	private String merchantId;
	private String tranId;
	private String receivePayNotifyUrl;
	private String receivePayType;
	private String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	private String accountProp;
	private List<ReceivePay> tranList = new ArrayList<ReceivePay>();
	private String xml;
	
	public String getXml() {
		return xml;
	}
	public void setXml(String xml) {
		this.xml = xml;
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
	public String getAccountProp() {
		return accountProp;
	}
	public void setAccountProp(String accountProp) {
		this.accountProp = accountProp;
	}
	public List<ReceivePay> getTranList() {
		return tranList;
	}
	public void setTranList(List<ReceivePay> tranList) {
		this.tranList = tranList;
	}
}