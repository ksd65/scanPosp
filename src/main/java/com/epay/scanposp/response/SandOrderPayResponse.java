/**
 * Copyright : http://www.sandpay.com.cn , 2011-2014
 * Project : sandpay-cashier-sdk
 * $Id$
 * $Revision$
 * Last Changed by pxl at 2016-12-27 下午7:13:25
 * $URL$
 * 
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * pxl         2016-12-27        Initailized
 */
package com.epay.scanposp.response;

import com.epay.scanposp.common.utils.sand.SandpayResponse;



/**
 * @author pan.xl
 *
 */
public class SandOrderPayResponse extends SandpayResponse {

	private SandOrderPayResponseBody body;
	
	public SandOrderPayResponseBody getBody() {
		return body;
	}
	public void setBody(SandOrderPayResponseBody body) {
		this.body = body;
	}

	public static class SandOrderPayResponseBody {
		private String orderCode;  // 商户订单号
		private String totalAmount;  // 订单金额
		private String credential;  // 支付凭证
		private String traceNo;  // 交易流水
		private String buyerPayAmount;  // 买家付款金额
		private String discAmount;  // 优惠金额
		private String payTime;  // 支付时间
		private String clearDate;  // 清算日期
		public String getOrderCode() {
			return orderCode;
		}
		public void setOrderCode(String orderCode) {
			this.orderCode = orderCode;
		}
		public String getTotalAmount() {
			return totalAmount;
		}
		public void setTotalAmount(String totalAmount) {
			this.totalAmount = totalAmount;
		}
		public String getCredential() {
			return credential;
		}
		public void setCredential(String credential) {
			this.credential = credential;
		}
		public String getTraceNo() {
			return traceNo;
		}
		public void setTraceNo(String traceNo) {
			this.traceNo = traceNo;
		}
		public String getBuyerPayAmount() {
			return buyerPayAmount;
		}
		public void setBuyerPayAmount(String buyerPayAmount) {
			this.buyerPayAmount = buyerPayAmount;
		}
		public String getDiscAmount() {
			return discAmount;
		}
		public void setDiscAmount(String discAmount) {
			this.discAmount = discAmount;
		}
		public String getPayTime() {
			return payTime;
		}
		public void setPayTime(String payTime) {
			this.payTime = payTime;
		}
		public String getClearDate() {
			return clearDate;
		}
		public void setClearDate(String clearDate) {
			this.clearDate = clearDate;
		}
		
	}
}
