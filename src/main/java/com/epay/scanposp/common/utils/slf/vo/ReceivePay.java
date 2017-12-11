package com.epay.scanposp.common.utils.slf.vo;


/**
 * 实名认证&收付款通用对象
 * @author administrator
 *
 */
public class ReceivePay {
	private String sn="";
	private String accNo="";
	private String accName="";
	private String amount="";
	private String credentialType="";
	private String credentialNo="";
	private String beginDate="";
	private String endDate="";
	private String tel="";
	private String summary="";
	private String respCode="";
	private String respDesc="";
	private String charge="";
	private String bankGeneralName="";
	private String payAccNo="";
	private String payAccName="";
	private String recAccNo="";
	private String recAccName="";
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getAccNo() {
		return accNo;
	}
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	public String getAccName() {
		return accName;
	}
	public void setAccName(String accName) {
		this.accName = accName;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getCredentialType() {
		return credentialType;
	}
	public void setCredentialType(String credentialType) {
		this.credentialType = credentialType;
	}
	public String getCredentialNo() {
		return credentialNo;
	}
	public void setCredentialNo(String credentialNo) {
		this.credentialNo = credentialNo;
	}
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getRespDesc() {
		return respDesc;
	}
	public void setRespDesc(String respDesc) {
		this.respDesc = respDesc;
	}
	public String getCharge() {
		return charge;
	}
	public void setCharge(String charge) {
		this.charge = charge;
	}
	public String getBankGeneralName() {
		return bankGeneralName;
	}
	public void setBankGeneralName(String bankGeneralName) {
		this.bankGeneralName = bankGeneralName;
	}
	public String getPayAccNo() {
		return payAccNo;
	}
	public void setPayAccNo(String payAccNo) {
		this.payAccNo = payAccNo;
	}
	public String getPayAccName() {
		return payAccName;
	}
	public void setPayAccName(String payAccName) {
		this.payAccName = payAccName;
	}
	public String getRecAccNo() {
		return recAccNo;
	}
	public void setRecAccNo(String recAccNo) {
		this.recAccNo = recAccNo;
	}
	public String getRecAccName() {
		return recAccName;
	}
	public void setRecAccName(String recAccName) {
		this.recAccName = recAccName;
	}
	@Override
	public String toString() {
		return "ReceivePay [sn=" + sn + ", accNo=" + accNo + ", accName=" + accName 
				+ ", amount=" + amount + ", credentialType=" + credentialType
				+ ", credentialNo=" + credentialNo + ", beginDate=" + beginDate
				+ ", endDate=" + endDate + ", tel=" + tel + ", summary="
				+ summary + ", respCode="
				+ respCode + ", respDesc=" + respDesc + ", charge=" + charge
				+ ", payAccNo=" + payAccNo + ", payAccName=" + payAccName
				+ ", recAccNo=" + recAccNo + ", recAccName=" + recAccName + "]";
	}
}