package com.epay.scanposp.common.utils.slf.vo;
/**
 * 下单请求对象
 * @author administrator
 *
 */
public class OrderRequest {
	private String merchantId = "";
	private String merchantName = "";
	private String merchantOrderId = "";
	private String merchantOrderAmt = "";
	private String merchantOrderDesc = "";
	private String merchantPayNotifyUrl = "";
	private String merchantFrontEndUrl = "";
	private String userMobileNo = "";
	private String credentialType = "";
	private String credentialNo = "";
	private String payerId = "";
	private String userName = "";
	private String salerId = "";
	private String guaranteeAmt = "";
	private String userType = "1";
	private String accountType = "";
	private String bankId = "";
	private String msgExt = "";
	private String orderTime = "";
	private String bizType = "";
	private String rptType = "";
	private String payMode = "";
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
	 * 取得商户名
	 * @return 商户名
	 */
	public String getMerchantName() {
		return merchantName;
	}
	/**
	 * 设置商户名
	 * @param merchantName 商户名
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
	 * 取得证件类型
	 * @return
	 */
	public String getCredentialType() {
		return credentialType;
	}
	/**
	 * 设置证件类型
	 * @param credentialType
	 */
	public void setCredentialType(String credentialType) {
		this.credentialType = credentialType;
	}
	/**
	 * 取得证件号
	 * @return
	 */
	public String getCredentialNo() {
		return credentialNo;
	}
	/**
	 * 设置证件号
	 * @param credentialNo
	 */
	public void setCredentialNo(String credentialNo) {
		this.credentialNo = credentialNo;
	}
	
	public String getPayerId() {
		return payerId;
	}
	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}
	/**
	 * 取得用户名
	 * @return
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * 设置用户名
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSalerId() {
		return salerId;
	}
	public void setSalerId(String salerId) {
		this.salerId = salerId;
	}
	public String getGuaranteeAmt() {
		return guaranteeAmt;
	}
	public void setGuaranteeAmt(String guaranteeAmt) {
		this.guaranteeAmt = guaranteeAmt;
	}
	/**
	 * 取得用户类型
	 * @return
	 */
	public String getUserType() {
		return userType;
	}
	/**
	 * 设置用户类型
	 * @param userType
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}
	/**
	 * 取得卡种
	 * @return
	 */
	public String getAccountType() {
		return accountType;
	}
	/**
	 * 设置卡种
	 * @param accountType
	 */
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	/**
	 * 取得银行编码
	 * @return
	 */
	public String getBankId() {
		return bankId;
	}
	/**
	 * 设置银行编码
	 * @param bankId
	 */
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	/**
	 * 取得扩展信息
	 * @return
	 */
	public String getMsgExt() {
		return msgExt;
	}
	/**
	 * 设置扩展信息
	 * @param msgExt
	 */
	public void setMsgExt(String msgExt) {
		this.msgExt = msgExt;
	}
	/**
	 * 取得订单时间
	 * @return
	 */
	public String getOrderTime() {
		return orderTime;
	}
	/**
	 * 设置订单时间
	 * @param orderTime 订单时间
	 */
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	/**
	 * 取得商户业务类型
	 * @return
	 */
	public String getBizType() {
		return bizType;
	}
	/**
	 * 设置商户业务类型
	 * @param bizType 商户业务类型
	 */
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	/**
	 * 取得收款方式
	 * @return
	 */
	public String getRptType() {
		return rptType;
	}
	/**
	 * 设置收款方式
	 * @param rptType 收款方式
	 */
	public void setRptType(String rptType) {
		this.rptType = rptType;
	}
	/**
	 * 取得付款类型
	 * @return
	 */
	public String getPayMode() {
		return payMode;
	}
	/**
	 * 设置付款类型
	 * @param payMode 付款类型
	 */
	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}
	/**
	 * 取得商户会员号
	 * @return merchantMemberId
	 */
	/**
	 * 取得订单请求xml报文
	 * @return 订单请求xml报文
	 */
	public String getXml() {
		return xml;
	}
	/**
	 * 设置订单请求xml报文
	 * @param xml 订单请求xml报文
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
		temp=temp+"credentialType="+credentialType+"\n";
		temp=temp+"credentialNo="+credentialNo+"\n";
		temp=temp+"msgExt="+msgExt+"\n";
		temp=temp+"orderTime="+orderTime+"\n";
		temp=temp+"bizType="+bizType+"\n";
		temp=temp+"rptType="+rptType+"\n";
		temp=temp+"payMode="+payMode+"\n";
		temp=temp+"xml="+xml+"\n";
		return temp;
	}
}