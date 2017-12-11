package com.epay.scanposp.common.utils.slf.vo;
/**
 * 支付确认响应对象
 * @author administrator
 *
 */
public class CertPayConfirmResponse {
	private String merchantId = "";
	private String merchantOrderId = "";
	private String bindId = "";
	private String cardNo = "";
	private String cardType = "";
	private String bankId = "";
	private String bankName = "";
	private String userMobileNo = "";
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
	public String getBindId() {
		return bindId;
	}
	public void setBindId(String bindId) {
		this.bindId = bindId;
	}
	
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getUserMobileNo() {
		return userMobileNo;
	}
	public void setUserMobileNo(String userMobileNo) {
		this.userMobileNo = userMobileNo;
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
		temp = temp + "bindId="+bindId+"\n";
		temp = temp + "cardNo="+cardNo+"\n";
		temp = temp + "cardType="+cardType+"\n";
		temp = temp + "bankId="+bankId+"\n";
		temp = temp + "bankName="+bankName+"\n";
		temp = temp + "userMobileNo="+userMobileNo+"\n";
		temp = temp + "respCode="+respCode+"\n";
		temp = temp + "respDesc="+respDesc+"\n";
		temp=temp+"xml="+xml+"\n";
		return temp;
	}
}