package com.epay.scanposp.common.utils.slf.vo;

import java.util.ArrayList;
import java.util.List;
/**
 * 支付通知响应对象
 * @author administrator
 *
 */
public class PaymentNotifyResponse {
	private String version = "";
	private String application = "";
	private String merchantId = "";
	private String merchantOrderId = "";
	private List<DeductInfo> deductList = new ArrayList<DeductInfo>();
//	private List<CancelResponse> cancelList = new ArrayList<CancelResponse>();
	private List<RefundResponse> refundList = new ArrayList<RefundResponse>();
	private String xml ="";
	/**
	 * 取得版本号
	 * @return 版本号
	 */
	public String getVersion() {
		return version;
	}
	/**
	 * 设置版本号
	 * @param version 版本号
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	/**
	 * 设置应用类型
	 * @param application 应用类型
	 */
	public void setApplication(String application) {
		this.application = application;
	}
	/**
	 * 取得应用类型
	 * @return 应用类型
	 */
	public String getApplication() {
		return application;
	}
	/**
	 * 取得支付列表
	 * @return 支付列表
	 */
	public List<DeductInfo> getDeductList() {
		return deductList;
	}
//	/**
//	 * 取得撤销列表
//	 * @return 撤销列表
//	 */
//	public List<CancelResponse> getCancelList() {
//		return cancelList;
//	}
	/**
	 * 取得退款列表
	 * @return 退款列表
	 */
	public List<RefundResponse> getRefundList() {
		return refundList;
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
		temp=temp+"version="+version+"\n";
		temp=temp+"application="+application+"\n";
		temp=temp+"merchantId="+merchantId+"\n";
		temp=temp+"merchantOrderId="+merchantOrderId+"\n";
		temp=temp + "缴费信息开始===============\n";
		for(int i = 0; i<deductList.size(); i++){
			temp=temp + deductList.get(i);
		}
		temp=temp + "缴费信息结束===============\n";
		temp=temp + "退款信息开始===============\n";
		for(int i = 0; i<refundList.size(); i++){
			temp=temp + refundList.get(i);
		}
		temp=temp + "退款信息结束===============\n";
//		temp=temp + "撤销信息开始===============\n";
//		for(int i = 0; i<cancelList.size(); i++){
//			temp=temp + cancelList.get(i);
//		}
//		temp=temp + "撤销信息结束===============\n";
		temp=temp+"xml="+xml+"\n";
		return temp;
	}
}