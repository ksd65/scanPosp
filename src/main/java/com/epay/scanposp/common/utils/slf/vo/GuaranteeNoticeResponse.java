package com.epay.scanposp.common.utils.slf.vo;
/**
 * 担保通知响应对象
 * @author administrator
 *
 */
public class GuaranteeNoticeResponse {
	private String merchantId = "";
	private String merchantOrderId = "";
	private String respCode = "000";
	private String respDesc = "处理成功";
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
	/**
	 * 取得响应码
	 * @return 响应码
	 */
	public String getRespCode() {
		return respCode;
	}
	/**
	 * 设置响应码
	 * @param respCode 响应码
	 */
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	/**
	 * 取得响应描述
	 * @return 响应描述 
	 */
	public String getRespDesc() {
		return respDesc;
	}
	/**
	 * 设置响应描述
	 * @param respDesc 响应描述
	 */
	public void setRespDesc(String respDesc) {
		this.respDesc = respDesc;
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
		temp = temp + "merchantId="+merchantId+"\n";
		temp = temp + "merchantOrderId="+merchantOrderId+"\n";
		temp = temp + "respCode="+respCode+"\n";
		temp = temp + "respDesc="+respDesc+"\n";
		temp=temp+"xml="+xml+"\n";
		return temp;
	}
}