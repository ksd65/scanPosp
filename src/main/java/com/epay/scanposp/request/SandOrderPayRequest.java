/**
 * Copyright : http://www.sandpay.com.cn , 2011-2014
 * Project : sandpay-cashier-sdk
 * $Id$
 * $Revision$
 * Last Changed by pxl at 2016-12-27 下午7:13:08
 * $URL$
 * 
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * pxl         2016-12-27        Initailized
 */
package com.epay.scanposp.request;


import com.alibaba.fastjson.annotation.JSONField;
import com.epay.scanposp.common.utils.sand.SandpayRequest;
import com.epay.scanposp.response.SandOrderPayResponse;



/**
 * @author pan.xl
 *
 */
public class SandOrderPayRequest extends SandpayRequest {
	
	private SandOrderPayRequestBody body;
	
	public SandOrderPayRequestBody getBody() {
		return body;
	}
	public void setBody(SandOrderPayRequestBody body) {
		this.body = body;
	}

	public static class SandOrderPayRequestBody {
		private String orderCode;  // 商户订单号
		private String totalAmount;  // 订单金额
		private String subject;  // 订单标题
		private String body;  // 订单描述
		private String txnTimeOut;  // 订单超时时间
		private String payMode;  // 支付模式
		private String payExtra;  // 支付扩展域
		private String clientIp;  // 客户端IP
		private String notifyUrl;  // 异步通知地址
		private String frontUrl;  // 前台通知地址
		private String storeId;  // 商户门店编号
		private String terminalId;  // 商户终端编号
		private String operatorId;  // 操作员编号
		private String clearCycle;  // 清算模式
		private String royaltyInfo;  // 分账信息
		private String riskRateInfo;  // 风控信息域
		private String bizExtendParams;  // 业务扩展参数
		private String merchExtendParams;  // 商户扩展参数
		private String extend;  // 扩展域
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
		public String getSubject() {
			return subject;
		}
		public void setSubject(String subject) {
			this.subject = subject;
		}
		public String getBody() {
			return body;
		}
		public void setBody(String body) {
			this.body = body;
		}
		public String getTxnTimeOut() {
			return txnTimeOut;
		}
		public void setTxnTimeOut(String txnTimeOut) {
			this.txnTimeOut = txnTimeOut;
		}
		public String getPayMode() {
			return payMode;
		}
		public void setPayMode(String payMode) {
			this.payMode = payMode;
		}
		public String getPayExtra() {
			return payExtra;
		}
		public void setPayExtra(String payExtra) {
			this.payExtra = payExtra;
		}
		public String getClientIp() {
			return clientIp;
		}
		public void setClientIp(String clientIp) {
			this.clientIp = clientIp;
		}
		public String getNotifyUrl() {
			return notifyUrl;
		}
		public void setNotifyUrl(String notifyUrl) {
			this.notifyUrl = notifyUrl;
		}
		public String getFrontUrl() {
			return frontUrl;
		}
		public void setFrontUrl(String frontUrl) {
			this.frontUrl = frontUrl;
		}
		public String getStoreId() {
			return storeId;
		}
		public void setStoreId(String storeId) {
			this.storeId = storeId;
		}
		public String getTerminalId() {
			return terminalId;
		}
		public void setTerminalId(String terminalId) {
			this.terminalId = terminalId;
		}
		public String getOperatorId() {
			return operatorId;
		}
		public void setOperatorId(String operatorId) {
			this.operatorId = operatorId;
		}
		public String getClearCycle() {
			return clearCycle;
		}
		public void setClearCycle(String clearCycle) {
			this.clearCycle = clearCycle;
		}
		public String getRoyaltyInfo() {
			return royaltyInfo;
		}
		public void setRoyaltyInfo(String royaltyInfo) {
			this.royaltyInfo = royaltyInfo;
		}
		public String getRiskRateInfo() {
			return riskRateInfo;
		}
		public void setRiskRateInfo(String riskRateInfo) {
			this.riskRateInfo = riskRateInfo;
		}
		public String getBizExtendParams() {
			return bizExtendParams;
		}
		public void setBizExtendParams(String bizExtendParams) {
			this.bizExtendParams = bizExtendParams;
		}
		public String getMerchExtendParams() {
			return merchExtendParams;
		}
		public void setMerchExtendParams(String merchExtendParams) {
			this.merchExtendParams = merchExtendParams;
		}
		public String getExtend() {
			return extend;
		}
		public void setExtend(String extend) {
			this.extend = extend;
		}
		
		
	}

	/* (non-Javadoc)
	 * @see cn.com.sandpay.api.SandpayRequest#getResponseClass()
	 */
	@Override
	@JSONField(serialize=false) 
	public Class<SandOrderPayResponse> getResponseClass() {
		return SandOrderPayResponse.class;
	}

	/* (non-Javadoc)
	 * @see cn.com.sandpay.api.SandpayRequest#getTxnDesc()
	 */
	@Override
	@JSONField(serialize=false) 
	public String getTxnDesc() {
		return "payOrderCreate";
	}

}
