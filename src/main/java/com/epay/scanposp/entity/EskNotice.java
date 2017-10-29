package com.epay.scanposp.entity;

import java.util.Date;

public class EskNotice {
    
    private String noticeData;

    private String orderNumber;

    private Date createDate;

	public String getNoticeData() {
		return noticeData;
	}

	public void setNoticeData(String noticeData) {
		this.noticeData = noticeData;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

    
}