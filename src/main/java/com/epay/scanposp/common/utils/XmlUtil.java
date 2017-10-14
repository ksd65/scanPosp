package com.epay.scanposp.common.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import  com.epay.scanposp.entity.RequestMsg;



/** 
 *@author Lao.ZH 
 *@date 2017-2-13 上午9:30:13 
 *@version V1.0
 */
public class XmlUtil {

	/**
	 * 把xml字符串转变为实体类
	 *@param RM
	 *@param xmlString
	 *@throws DocumentException 
	 *@author Lao.ZH
	 */
	public void XmlToBean(RequestMsg RM,String xmlString) throws DocumentException{		
		
		Document reqDoc = DocumentHelper.parseText(xmlString);
		Element rootEl = reqDoc.getRootElement();
		RM.setORDER_ID(rootEl.elementText("ORDER_ID"));
		RM.setTRANS_AMT1(rootEl.elementText("ORDER_AMT"));
		RM.setTRANS_ATIME(rootEl.elementText("ORDER_TIME"));
		RM.setSIGN(rootEl.elementText("SIGN"));
		RM.setSIGN_TYPE(rootEl.elementText("SIGN_TYPE"));
		RM.setMSG_TYPE(rootEl.elementText("BUS_CODE"));
		RM.setRESP_DESCR(rootEl.elementText("RESP_DESC"));
		RM.setRESP_CODE(rootEl.elementText("RESP_CODE"));
		RM.setCODE_URL(rootEl.elementText("CODE_URL"));
		RM.setUSER_ID(rootEl.elementText("USER_ID"));
		RM.setCCT(rootEl.elementText("CCT"));
		
	}
}
