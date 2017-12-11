package com.epay.scanposp.common.utils.slf.vo;
/**
 * 支付通知请求对象
 * @author administrator
 *
 */
public class NotifyRequest {
	private String merchantId = "";
	private String merchantName = "";
	private String merchantOrderId = "";
	private String merchantOrderAmt = "";
	private String merchantOrderDesc = "";
	private String merchantPayNotifyUrl = "";
	private String merchantFrontEndUrl = "";
	private String userMobileNo = "";
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
	 * 取得商户名称
	 * @return 商户名称
	 */
	public String getMerchantName() {
		return merchantName;
	}
	/**
	 * 设置商户名称
	 * @param merchantName 商户名称
	 */
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
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
	 * 取得订单金额
	 * @return 订单金额
	 */
	public String getMerchantOrderAmt() {
		return merchantOrderAmt;
	}
	/**
	 * 设置订单金额
	 * @param merchantOrderAmt 订单金额
	 */
	public void setMerchantOrderAmt(String merchantOrderAmt) {
		this.merchantOrderAmt = merchantOrderAmt;
	}
	/**
	 * 取得订单描述
	 * @return 订单描述
	 */
	public String getMerchantOrderDesc() {
		return merchantOrderDesc;
	}
	/**
	 * 设置订单描述
	 * @param merchantOrderDesc 订单描述
	 */
	public void setMerchantOrderDesc(String merchantOrderDesc) {
		this.merchantOrderDesc = merchantOrderDesc;
	}
	/**
	 * 取得商户后台地址
	 * @return 后台地址
	 */
	public String getMerchantPayNotifyUrl() {
		return merchantPayNotifyUrl;
	}
	/**
	 * 设置商户后台地址
	 * @param merchantPayNotifyUrl 后台地址 
	 */
	public void setMerchantPayNotifyUrl(String merchantPayNotifyUrl) {
		this.merchantPayNotifyUrl = merchantPayNotifyUrl;
	}
	/**
	 * 取得商户前台地址
	 * @return 商户前台地址
	 */
	public String getMerchantFrontEndUrl() {
		return merchantFrontEndUrl;
	}
	/**
	 * 设置商户前台地址
	 * @param merchantFrontEndUrl 商户前台地址
	 */
	public void setMerchantFrontEndUrl(String merchantFrontEndUrl) {
		this.merchantFrontEndUrl = merchantFrontEndUrl;
	}
	/**
	 * 取得用户手机号
	 * @return 用户手机号
	 */
	public String getUserMobileNo() {
		return userMobileNo;
	}
	/**
	 * 设置用户手机号
	 * @param userMobileNo 用户手机号
	 */
	public void setUserMobileNo(String userMobileNo) {
		this.userMobileNo = userMobileNo;
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
		temp=temp+"merchantName="+merchantName+"\n";
		temp=temp+"merchantOrderId="+merchantOrderId+"\n";
		temp=temp+"merchantOrderAmt="+merchantOrderAmt+"\n";
		temp=temp+"merchantOrderDesc="+merchantOrderDesc+"\n";
		temp=temp+"merchantPayNotifyUrl="+merchantPayNotifyUrl+"\n";
		temp=temp+"merchantFrontEndUrl="+merchantFrontEndUrl+"\n";
		temp=temp+"userMobileNo="+userMobileNo+"\n";
		temp=temp+"xml="+xml+"\n";
		return temp;
	}
}