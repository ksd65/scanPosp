package com.epay.scanposp.entity;


import java.math.BigDecimal;
import java.sql.Date;

public class RequestMsg {
     	
	/**
	 * waitsinfo表 
	 */
	private Integer ID;
	private String MSG_TYPE;    //业务代码，快捷支付：3001请求，3002应答
	private Integer REF_ID;     //quick_pay_reg表对应订单记录的id
	private String MESSAGE;		//各字段拼接成的字符串
	private String STATUS;      //处理状态（0 待处理 1 异常失败 2 处理中 3 处理失败 4 处理成功）
	private Date INSERT_TIME;	//2016/7/19 0:33:27	
	private Date UPDATE_TIME;	//2016/7/19 0:33:28	
	private String REMARK;	    //处理成功	
	private String PORT_STATUS;		
    private String F_STATUS;    //0：待处理，1：已处理
    private String AUTH_CODE;
	/**
	 * quick_pay_reg表
	 */	
     private String TRANS_AMT1;    //字符串型金额
    
     private BigDecimal TRANS_AMT;	   //数据型金额
     private String FRONT_STATUS;		
     private String PAY_STATUS;		
     private String REQ_URL;		
     private String PAGE_URL;		
     private String BG_URL;		
     private String TRANS_ATIME;		
     private String PAY_TYPE;		
     private String ORDER_ID;		
     private String GOODS_NAME;		
     private String GOODS_NUM;		
     private String GOODS_DESC;		
     private String SIGN;		
//     private Date INSERT_TIME;		//两个表共有
//     private Date UPDATE_TIME;		//两个表共有
     private String ADD1;		
     private String USER_ID;		
     private Integer FEE_AMT;		
     private String RESP_CODE;		
     private String RESP_DESCR;		
     private String TRANS_STIME;		
     private String USERTYPE;	
     private String USER_TYPE;
     private String REF_NO;		
//     private String MSG_TYPE;		    //两个表共有
     private String CCT;		
     private String SIGN_TYPE;		
     private String SIGN_STR;		
     private String ADD2;		
     private String ADD3;		
     private String ADD4;		
     private String ADD5;		
     private String PAYCH_TIME;
     private String PAN;           
     private String BANK_CODE; 
     private String PAN_TYPE; 
     private String CHANL_INST_NO; 
     private String CHANL_MCHT_NO; 
     private String CHANL_TERM_NO; 
     private String CHANL_MCHT_KEY; 
     private String TRANS_SN; 
     private String CHECK_TYPE;
     private String PayResult; 
     private String PAYAMOUNT;//PayAmount;
     private String ID_NO;
     private String NAME;
     private String PHONE_NO;
     private String CARD_TYPE;
     private BigDecimal SETT_AMT;
     private String SETT_ACCT_NO; 
     private String CARD_INST_NAME;
     private String BIND_TYPE;
     private String TXNTYPE;
     private String orderNum;
     
     
 
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getTXNTYPE() {
		return TXNTYPE;
	}
	public void setTXNTYPE(String tXNTYPE) {
		TXNTYPE = tXNTYPE;
	}
	public String getBIND_TYPE() {
		return BIND_TYPE;
	}
	public void setBIND_TYPE(String bIND_TYPE) {
		BIND_TYPE = bIND_TYPE;
	}
	public String getSETT_ACCT_NO() {
		return SETT_ACCT_NO;
	}
	public void setSETT_ACCT_NO(String sETT_ACCT_NO) {
		SETT_ACCT_NO = sETT_ACCT_NO;
	}
	public BigDecimal getSETT_AMT() {
		return SETT_AMT;
	}
	public void setSETT_AMT(BigDecimal sETT_AMT) {
		SETT_AMT = sETT_AMT;
	}
	public String getPHONE_NO() {
		return PHONE_NO;
	}
	public void setPHONE_NO(String pHONE_NO) {
		PHONE_NO = pHONE_NO;
	}
	public String getCARD_TYPE() {
		return CARD_TYPE;
	}
	public void setCARD_TYPE(String cARD_TYPE) {
		CARD_TYPE = cARD_TYPE;
	}
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	public String getID_NO() {
		return ID_NO;
	}
	public void setID_NO(String iD_NO) {
		ID_NO = iD_NO;
	}
	public String getCHECK_TYPE() {
		return CHECK_TYPE;
	}
	public void setCHECK_TYPE(String cHECK_TYPE) {
		CHECK_TYPE = cHECK_TYPE;
	}
	private String CODE_URL;

     //以下为代付接口
     private String ACCOUNT_NO;		//账号
     private String ACCOUNT_NAME;	//账号名
     
     //3105业务
     private String OPEN_ID;        
     private String PAY_INFO;
     private String APPID;
     /* 高汇通：境外*/
     private String CHILD_MERCHANT_NO;   //子商户号

	public BigDecimal getTRANS_AMT() {
		return TRANS_AMT;
	}
	public void setTRANS_AMT(BigDecimal tRANS_AMT) {
		TRANS_AMT = tRANS_AMT;
	}
	public String getFRONT_STATUS() {
		return FRONT_STATUS;
	}
	public void setFRONT_STATUS(String fRONT_STATUS) {
		FRONT_STATUS = fRONT_STATUS;
	}
	public String getPAY_STATUS() {
		return PAY_STATUS;
	}
	public void setPAY_STATUS(String pAY_STATUS) {
		PAY_STATUS = pAY_STATUS;
	}
	public String getREQ_URL() {
		return REQ_URL;
	}
	public void setREQ_URL(String rEQ_URL) {
		REQ_URL = rEQ_URL;
	}
 
	public String getTRANS_ATIME() {
		return TRANS_ATIME;
	}
	public void setTRANS_ATIME(String tRANS_ATIME) {
		TRANS_ATIME = tRANS_ATIME;
	}
	public String getPAY_TYPE() {
		return PAY_TYPE;
	}
	public void setPAY_TYPE(String pAY_TYPE) {
		PAY_TYPE = pAY_TYPE;
	}
	public String getORDER_ID() {
		return ORDER_ID;
	}
	public void setORDER_ID(String oRDER_ID) {
		ORDER_ID = oRDER_ID;
	}
	public String getGOODS_NAME() {
		return GOODS_NAME;
	}
	public void setGOODS_NAME(String gOODS_NAME) {
		GOODS_NAME = gOODS_NAME;
	}
	public String getGOODS_NUM() {
		return GOODS_NUM;
	}
	public void setGOODS_NUM(String gOODS_NUM) {
		GOODS_NUM = gOODS_NUM;
	}
	public String getGOODS_DESC() {
		return GOODS_DESC;
	}
	public void setGOODS_DESC(String gOODS_DESC) {
		GOODS_DESC = gOODS_DESC;
	}
	public String getSIGN() {
		return SIGN;
	}
	public void setSIGN(String sIGN) {
		SIGN = sIGN;
	}
	public Date getINSERT_TIME() {
		return INSERT_TIME;
	}
	public void setINSERT_TIME(Date iNSERT_TIME) {
		INSERT_TIME = iNSERT_TIME;
	}
	public Date getUPDATE_TIME() {
		return UPDATE_TIME;
	}
	public void setUPDATE_TIME(Date uPDATE_TIME) {
		UPDATE_TIME = uPDATE_TIME;
	}
	public String getADD1() {
		return ADD1;
	}
	public void setADD1(String aDD1) {
		ADD1 = aDD1;
	}
	public String getUSER_ID() {
		return USER_ID;
	}
	public void setUSER_ID(String uSER_ID) {
		USER_ID = uSER_ID;
	}
	public Integer getFEE_AMT() {
		return FEE_AMT;
	}
	public void setFEE_AMT(Integer fEE_AMT) {
		FEE_AMT = fEE_AMT;
	}
	public String getRESP_CODE() {
		return RESP_CODE;
	}
	public void setRESP_CODE(String rESP_CODE) {
		RESP_CODE = rESP_CODE;
	}
	public String getRESP_DESCR() {
		return RESP_DESCR;
	}
	public void setRESP_DESCR(String rESP_DESCR) {
		RESP_DESCR = rESP_DESCR;
	}
	public String getTRANS_STIME() {
		return TRANS_STIME;
	}
	public void setTRANS_STIME(String tRANS_STIME) {
		TRANS_STIME = tRANS_STIME;
	}
	
	public String getREF_NO() {
		return REF_NO;
	}
	public void setREF_NO(String rEF_NO) {
		REF_NO = rEF_NO;
	}
	public String getMSG_TYPE() {
		return MSG_TYPE;
	}
	public void setMSG_TYPE(String mSG_TYPE) {
		MSG_TYPE = mSG_TYPE;
	}
	public String getCCT() {
		return CCT;
	}
	public void setCCT(String cCT) {
		CCT = cCT;
	}
	public String getSIGN_TYPE() {
		return SIGN_TYPE;
	}
	public void setSIGN_TYPE(String sIGN_TYPE) {
		SIGN_TYPE = sIGN_TYPE;
	}
	public String getSIGN_STR() {
		return SIGN_STR;
	}
	public void setSIGN_STR(String sIGN_STR) {
		SIGN_STR = sIGN_STR;
	}
	public String getADD2() {
		return ADD2;
	}
	public void setADD2(String aDD2) {
		ADD2 = aDD2;
	}
	public String getADD3() {
		return ADD3;
	}
	public void setADD3(String aDD3) {
		ADD3 = aDD3;
	}
	public String getADD4() {
		return ADD4;
	}
	public void setADD4(String aDD4) {
		ADD4 = aDD4;
	}
	public String getADD5() {
		return ADD5;
	}
	public void setADD5(String aDD5) {
		ADD5 = aDD5;
	}
	public String getPAYCH_TIME() {
		return PAYCH_TIME;
	}
	public void setPAYCH_TIME(String pAYCH_TIME) {
		PAYCH_TIME = pAYCH_TIME;
	}
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public Integer getREF_ID() {
		return REF_ID;
	}
	public void setREF_ID(Integer rEF_ID) {
		REF_ID = rEF_ID;
	}
	public String getMESSAGE() {
		return MESSAGE;
	}
	public void setMESSAGE(String mESSAGE) {
		MESSAGE = mESSAGE;
	}
	public String getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
	public String getREMARK() {
		return REMARK;
	}
	public void setREMARK(String rEMARK) {
		REMARK = rEMARK;
	}
	public String getPORT_STATUS() {
		return PORT_STATUS;
	}
	public void setPORT_STATUS(String pORT_STATUS) {
		PORT_STATUS = pORT_STATUS;
	}
	public String getPAN() {
		return PAN;
	}
	public void setPAN(String pAN) {
		PAN = pAN;
	}
	public String getBANK_CODE() {
		return BANK_CODE;
	}
	public void setBANK_CODE(String bANK_CODE) {
		BANK_CODE = bANK_CODE;
	}
	public String getPAN_TYPE() {
		return PAN_TYPE;
	}
	public void setPAN_TYPE(String pAN_TYPE) {
		PAN_TYPE = pAN_TYPE;
	}
	public String getCHANL_INST_NO() {
		return CHANL_INST_NO;
	}
	public void setCHANL_INST_NO(String cHANL_INST_NO) {
		CHANL_INST_NO = cHANL_INST_NO;
	}
	public String getCHANL_TERM_NO() {
		return CHANL_TERM_NO;
	}
	public void setCHANL_TERM_NO(String cHANL_TERM_NO) {
		CHANL_TERM_NO = cHANL_TERM_NO;
	}
	public String getCHANL_MCHT_NO() {
		return CHANL_MCHT_NO;
	}
	public void setCHANL_MCHT_NO(String cHANL_MCHT_NO) {
		CHANL_MCHT_NO = cHANL_MCHT_NO;
	}
	public String getCHANL_MCHT_KEY() {
		return CHANL_MCHT_KEY;
	}
	public void setCHANL_MCHT_KEY(String cHANL_MCHT_KEY) {
		CHANL_MCHT_KEY = cHANL_MCHT_KEY;
	}
	public String getTRANS_SN() {
		return TRANS_SN;
	}
	public void setTRANS_SN(String tRANS_SN) {
		TRANS_SN = tRANS_SN;
	}
	public String getF_STATUS() {
		return F_STATUS;
	}
	public void setF_STATUS(String f_STATUS) {
		F_STATUS = f_STATUS;
	}
	public String getPayResult() {
		return PayResult;
	}
	public void setPayResult(String payResult) {
		PayResult = payResult;
	}
 
 
	public String getPAYAMOUNT() {
		return PAYAMOUNT;
	}
	public void setPAYAMOUNT(String pAYAMOUNT) {
		PAYAMOUNT = pAYAMOUNT;
	}
	public String getTRANS_AMT1() {
		return TRANS_AMT1;
	}
	public void setTRANS_AMT1(String tRANS_AMT1) {
		TRANS_AMT1 = tRANS_AMT1;
	}
	public String getCODE_URL() {
		return CODE_URL;
	}
	public void setCODE_URL(String cODE_URL) {
		CODE_URL = cODE_URL;
	}
	public String getACCOUNT_NO() {
		return ACCOUNT_NO;
	}
	public void setACCOUNT_NO(String aCCOUNT_NO) {
		ACCOUNT_NO = aCCOUNT_NO;
	}
	public String getACCOUNT_NAME() {
		return ACCOUNT_NAME;
	}
	public void setACCOUNT_NAME(String aCCOUNT_NAME) {
		ACCOUNT_NAME = aCCOUNT_NAME;
	}
	public String getAUTH_CODE() {
		return AUTH_CODE;
	}
	public void setAUTH_CODE(String aUTH_CODE) {
		AUTH_CODE = aUTH_CODE;
	}
	public String getOPEN_ID() {
		return OPEN_ID;
	}
	public void setOPEN_ID(String oPEN_ID) {
		OPEN_ID = oPEN_ID;
	}
	public String getPAY_INFO() {
		return PAY_INFO;
	}
	public void setPAY_INFO(String pAY_INFO) {
		PAY_INFO = pAY_INFO;
	}
	public String getUSER_TYPE() {
		return USER_TYPE;
	}
	public void setUSER_TYPE(String uSER_TYPE) {
		USER_TYPE = uSER_TYPE;
	}
	public String getUSERTYPE() {
		return USERTYPE;
	}
	public void setUSERTYPE(String uSERTYPE) {
		USERTYPE = uSERTYPE;
	}
	public String getAPPID() {
		return APPID;
	}
	public void setAPPID(String aPPID) {
		APPID = aPPID;
	}
	public String getCHILD_MERCHANT_NO() {
		return CHILD_MERCHANT_NO;
	}
	public void setCHILD_MERCHANT_NO(String cHILD_MERCHANT_NO) {
		CHILD_MERCHANT_NO = cHILD_MERCHANT_NO;
	}
 
	public String getPAGE_URL() {
		return PAGE_URL;
	}
	public void setPAGE_URL(String pAGE_URL) {
		PAGE_URL = pAGE_URL;
	}
	public String getBG_URL() {
		return BG_URL;
	}
	public void setBG_URL(String bG_URL) {
		BG_URL = bG_URL;
	}
	public String getCARD_INST_NAME() {
		return CARD_INST_NAME;
	}
	public void setCARD_INST_NAME(String cARD_INST_NAME) {
		CARD_INST_NAME = cARD_INST_NAME;
	}		

}
