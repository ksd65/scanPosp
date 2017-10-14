package com.epay.scanposp.entity;


public class MemberInfoMore extends MemberInfo{
	private Integer memberBankId;
    private String bankId;
    private String subId;
    private String bankOpen;
    private String accountName;
    private String accountNumber;

	public Integer getMemberBankId() {
		return memberBankId;
	}

	public void setMemberBankId(Integer memberBankId) {
		this.memberBankId = memberBankId;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getSubId() {
		return subId;
	}

	public void setSubId(String subId) {
		this.subId = subId;
	}

	public String getBankOpen() {
		return bankOpen;
	}

	public void setBankOpen(String bankOpen) {
		this.bankOpen = bankOpen;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
    
}