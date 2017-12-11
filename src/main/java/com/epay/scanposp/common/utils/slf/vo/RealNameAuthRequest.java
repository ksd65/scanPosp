package com.epay.scanposp.common.utils.slf.vo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 实名认证请求对象
 * @author administrator
 *
 */
public class RealNameAuthRequest {
	private String application;
	private String version;
	private String merchantId = "";
	private String tranId = "";
	private String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	private List<ReceivePay> authList = new ArrayList<ReceivePay>();
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
	public String getTranId() {
		return tranId;
	}
	public void setTranId(String tranId) {
		this.tranId = tranId;
	}
	public String getTimestamp() {
		return timestamp;
	}
	
	public List<ReceivePay> getAuthList() {
		return authList;
	}
	public void setAuthList(List<ReceivePay> authList) {
		this.authList = authList;
	}
	public String getXml() {
		return xml;
	}
	public void setXml(String xml) {
		this.xml = xml;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	@Override
	public String toString() {
		return "RealNameAuthRequest [application=" + application + ", version="
				+ version + ", merchantId=" + merchantId + ", tranId=" + tranId
				+ ", timestamp=" + timestamp + ", authList=" + authList
				+ ", xml=" + xml + "]";
	}
}