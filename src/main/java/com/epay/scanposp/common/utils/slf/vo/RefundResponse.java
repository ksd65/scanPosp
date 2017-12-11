package com.epay.scanposp.common.utils.slf.vo;
/**
 * 退款响应对象
 * @author administrator
 *
 */
public class RefundResponse {
	private String merchantId = "";
	private String merchantOrderId = "";
	private String merchantRefundAmt = "";
	private String refundStatus = "";
	private String refundDesc = "";
	private String refundTime = "";
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
	 * 取得退款金额
	 * @return
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
	 * 取得退款状态
	 * @return 退款状态
	 */
	public String getRefundStatus() {
		return refundStatus;
	}
	/**
	 * 设置退款状态
	 * @param refundStatus 退款状态
	 */
	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}
	/**
	 * 取得退款描述
	 * @return 退款描述
	 */
	public String getRefundDesc() {
		return refundDesc;
	}
	/**
	 * 设置退款描述
	 * @param refundDesc 退款描述
	 */
	public void setRefundDesc(String refundDesc) {
		this.refundDesc = refundDesc;
	}
	/**
	 * 取得退款时间
	 * @return 退款时间
	 */
	public String getRefundTime() {
		return refundTime;
	}
	/**
	 * 设置退款时间
	 * @param refundTime 退款时间
	 */
	public void setRefundTime(String refundTime) {
		this.refundTime = refundTime;
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
		temp = temp + "merchantRefundAmt="+merchantRefundAmt+"\n";
		temp = temp + "refundStatus="+refundStatus+"\n";
		temp = temp + "refundDesc="+refundDesc+"\n";
		temp = temp + "refundTime="+refundTime+"\n";
		temp = temp + "respCode="+respCode+"\n";
		temp = temp + "respDesc="+respDesc+"\n";
		temp=temp+"xml="+xml+"\n";
		return temp;
	}
}