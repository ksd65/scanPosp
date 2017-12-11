package com.epay.scanposp.common.utils.slf.vo;
/**
 * 退款请求对象
 * @author administrator
 *
 */
public class RefundRequest {
	private String merchantId = "";
	private String merchantOrderId = "";
	private String merchantRefundAmt = "";
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
	 * 取得退款金额
	 * @return 退款金额
	 */
	public String getMerchantRefundAmt() {
		return merchantRefundAmt;
	}
	/**
	 * 设置退款金额
	 * @param merchantRefundAmt 退款金额
	 */
	public void setMerchantRefundAmt(String merchantRefundAmt) {
		this.merchantRefundAmt = merchantRefundAmt;
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
		temp=temp+"merchantOrderId="+merchantOrderId+"\n";
		temp=temp+"merchantRefundAmt="+merchantRefundAmt+"\n";
		temp=temp+"xml="+xml+"\n";
		return temp;
	}
}