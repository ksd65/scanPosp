package com.epay.scanposp.common.utils.slf.vo;
/**
 * 支付确认请求对象
 * @author administrator
 *
 */
public class CertPayConfirmRequest {
	private String application="";
	private String version="";
	private String merchantId = "";
	private String merchantOrderId = "";
	private String checkCode = "";
	private String xml="";
	/**
	 * 取得应用名称
	 * @return
	 */
	public String getApplication() {
		return application;
	}
	/**
	 * 设置应用名称
	 * @param application 应用名称
	 */
	public void setApplication(String application) {
		this.application = application;
	}
	/**
	 * 取得版本
	 * @return
	 */
	public String getVersion() {
		return version;
	}
	/**
	 * 设置版本
	 * @param version
	 */
	public void setVersion(String version) {
		this.version = version;
	}
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
	 * 取得商户订单号
	 * @return 商户订单号
	 */
	public String getMerchantOrderId() {
		return merchantOrderId;
	}
	/**
	 * 设置商户订单号
	 * @param merchantOrderId 商户订单号
	 */
	public void setMerchantOrderId(String merchantOrderId) {
		this.merchantOrderId = merchantOrderId;
	}
	public String getCheckCode() {
		return checkCode;
	}
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
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
		temp=temp+"application="+application+"\n";
		temp=temp+"version="+version+"\n";
		temp=temp+"merchantId="+merchantId+"\n";
		temp=temp+"merchantOrderId="+merchantOrderId+"\n";
		temp=temp+"checkCode="+checkCode+"\n";
		temp=temp+"xml="+xml+"\n";
		return temp;
	}
}