package com.epay.scanposp.common.utils.slf.vo;
/**
 * 对账文件下载请求对象
 * @author administrator
 *
 */
public class AccountRequest {
	private String merchantId = "";
	private String accountDate = "";
	private String xml="";
	/**
	 * 取得商户号
	 * @return 商户号
	 */
	public String getMerchantId() {
		return merchantId;
	}
	/**
	 * 设置商户号
	 * @param merchantId 商户号
	 */
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	/**
	 * 取得对账日期
	 * @return
	 */
	public String getAccountDate() {
		return accountDate;
	}
	/**
	 * 设置对账日期
	 * @param accountDate
	 */
	public void setAccountDate(String accountDate) {
		this.accountDate = accountDate;
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
	public String toString(){
		String temp = "";
		temp=temp+"merchantId="+merchantId+"\n";
		temp=temp+"accountDate="+accountDate+"\n";
		temp=temp+"xml="+xml+"\n";
		return temp;
	}
}