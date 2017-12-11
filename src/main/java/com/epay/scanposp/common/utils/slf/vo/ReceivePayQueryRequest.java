package com.epay.scanposp.common.utils.slf.vo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 批量收付款查询请求对象
 * @author administrator
 *
 */
public class ReceivePayQueryRequest {
	private String application;
	private String version;
	private String xml;
	private String merchantId;
	private String tranId;
	private String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	private String queryTranId;
	private List<ReceivePay> queryList = new ArrayList<ReceivePay>();
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
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
	public String getXml() {
		return xml;
	}
	public void setXml(String xml) {
		this.xml = xml;
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
	public String getQueryTranId() {
		return queryTranId;
	}
	public void setQueryTranId(String queryTranId) {
		this.queryTranId = queryTranId;
	}
	public List<ReceivePay> getQueryList() {
		return queryList;
	}
	public void setQueryList(List<ReceivePay> queryList) {
		this.queryList = queryList;
	}
}