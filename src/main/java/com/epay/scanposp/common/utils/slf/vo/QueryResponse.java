package com.epay.scanposp.common.utils.slf.vo;
import java.util.ArrayList;
import java.util.List;
/**
 * 查询响应对象
 * @author administrator
 *
 */
public class QueryResponse {
	private String merchantId = "";
	private String merchantOrderId = "";
	private List<DeductInfo> deductList = new ArrayList<DeductInfo>();
//	private List<CancelResponse> cancelList = new ArrayList<CancelResponse>();
	private List<RefundResponse> refundList = new ArrayList<RefundResponse>();
	private String respCode = "";
	private String respDesc = "";
	private String xml="";
	/**
	 * 取得用户支付列表
	 * @return 支付列表
	 */
	public List<DeductInfo> getDeductList() {
		return deductList;
	}
	/**
	 * 设置支付列表
	 * @param deductList 支付列表
	 */
	public void setDeductList(List<DeductInfo> deductList) {
		this.deductList = deductList;
	}
//	/**
//	 * 取得撤销列表
//	 * @return 撤销列表
//	 */
//	public List<CancelResponse> getCancelList() {
//		return cancelList;
//	}
//	/**
//	 * 设置撤销列表
//	 * @param cancelList 撤销列表
//	 */
//	public void setCancelList(List<CancelResponse> cancelList) {
//		this.cancelList = cancelList;
//	}
	/**
	 * 取得订单退款列表
	 * @return 订单退款列表
	 */
	public List<RefundResponse> getRefundList() {
		return refundList;
	}
	/**
	 * 设置订单退款列表
	 * @param refundList 订单退款列表
	 */
	public void setRefundList(List<RefundResponse> refundList) {
		this.refundList = refundList;
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
		temp=temp+"merchantId="+merchantId+"\n";
		temp=temp+"merchantOrderId="+merchantOrderId+"\n";
		temp=temp+"respCode="+respCode+"\n";
		temp=temp+"respDesc="+respDesc+"\n";
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