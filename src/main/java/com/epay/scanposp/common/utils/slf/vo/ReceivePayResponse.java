package com.epay.scanposp.common.utils.slf.vo;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * 实名认证、协议签约、单笔收付款响应对象
 * @author administrator
 *
 */
public class ReceivePayResponse {
	private String application;
	private String version;
	private String merchantId = "";
	private String tranId = "";
	private String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	private String respCode = "";
	private String respDesc = "";
	private List<ReceivePay> tranList = new ArrayList<ReceivePay>();
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
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getRespDesc() {
		return respDesc;
	}
	public void setRespDesc(String respDesc) {
		this.respDesc = respDesc;
	}
	public String getXml() {
		return xml;
	}
	public void setXml(String xml) {
		this.xml = xml;
	}
	public List<ReceivePay> getTranList() {
		return tranList;
	}
	public void setTranList(List<ReceivePay> tranList) {
		this.tranList = tranList;
	}
	@Override
	public String toString() {
		return "ReceivePayResponse [application=" + application + ", version="
				+ version + ", merchantId=" + merchantId + ", tranId=" + tranId
				+ ", timestamp=" + timestamp + ", respCode=" + respCode
				+ ", respDesc=" + respDesc + ", tranList=" + tranList
				+ ", xml=" + xml + "]";
	}
}