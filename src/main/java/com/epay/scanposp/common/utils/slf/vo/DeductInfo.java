package com.epay.scanposp.common.utils.slf.vo;
/**
 * 支付信息对象
 * @author administrator
 *
 */
public class DeductInfo {
	private String payOrderId = "";
	private String payAmt = "";
	private String payTime = "";
	private String payStatus = "";
	private String payDesc = "";
	private String xml="";
	/**
	 * 取得支付订单号
	 * @return 支付订单号
	 */
	public String getPayOrderId() {
		return payOrderId;
	}
	/**
	 * 设置支付订单号
	 * @param payOrderId 支付订单号
	 */
	public void setPayOrderId(String payOrderId) {
		this.payOrderId = payOrderId;
	}
	/**
	 * 取得支付金额
	 * @return 支付金额
	 */
	public String getPayAmt() {
		return payAmt;
	}
	/**
	 * 设置支付金额
	 * @param payAmt 支付金额
	 */
	public void setPayAmt(String payAmt) {
		this.payAmt = payAmt;
	}
	/**
	 * 取得支付时间
	 * @return 得支付时间
	 */
	public String getPayTime() {
		return payTime;
	}
	/**
	 * 设置得支付时间
	 * @param payTime 得支付时间
	 */
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	/**
	 * 取得支付状态
	 * @return 支付状态
	 */
	public String getPayStatus() {
		return payStatus;
	}
	/**
	 * 设置支付状态
	 * @param payStatus 支付状态
	 */
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	/**
	 * 取得支付描述
	 * @return 支付描述
	 */
	public String getPayDesc() {
		return payDesc;
	}
	/**
	 * 支付描述
	 * @param payDesc 支付描述
	 */
	public void setPayDesc(String payDesc) {
		this.payDesc = payDesc;
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
		temp = temp + "payOrderId="+payOrderId+"\n";
		temp = temp + "payAmt="+payAmt+"\n";
		temp = temp + "payTime="+payTime+"\n";
		temp = temp + "payStatus="+payStatus+"\n";
		temp = temp + "payDesc="+payDesc+"\n";
		temp = temp + "xml="+xml+"\n";
		return temp;
	}
}