package com.epay.scanposp.common.utils.slf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.epay.scanposp.common.utils.slf.vo.AccountRequest;
import com.epay.scanposp.common.utils.slf.vo.CertPayConfirmRequest;
import com.epay.scanposp.common.utils.slf.vo.CertPayConfirmResponse;
import com.epay.scanposp.common.utils.slf.vo.CertPayH5Request;
import com.epay.scanposp.common.utils.slf.vo.CertPayRequest;
import com.epay.scanposp.common.utils.slf.vo.CertPayResponse;
import com.epay.scanposp.common.utils.slf.vo.DeductInfo;
import com.epay.scanposp.common.utils.slf.vo.GuaranteeNoticeRequest;
import com.epay.scanposp.common.utils.slf.vo.GuaranteeNoticeResponse;
import com.epay.scanposp.common.utils.slf.vo.OrderRequest;
import com.epay.scanposp.common.utils.slf.vo.OrderResponse;
import com.epay.scanposp.common.utils.slf.vo.PaymentNotifyResponse;
import com.epay.scanposp.common.utils.slf.vo.QueryRequest;
import com.epay.scanposp.common.utils.slf.vo.QueryResponse;
import com.epay.scanposp.common.utils.slf.vo.RealNameAuthRequest;
import com.epay.scanposp.common.utils.slf.vo.ReceivePay;
import com.epay.scanposp.common.utils.slf.vo.ReceivePayBatchRequest;
import com.epay.scanposp.common.utils.slf.vo.ReceivePayBatchResponse;
import com.epay.scanposp.common.utils.slf.vo.ReceivePayQueryRequest;
import com.epay.scanposp.common.utils.slf.vo.ReceivePayQueryResponse;
import com.epay.scanposp.common.utils.slf.vo.ReceivePayRequest;
import com.epay.scanposp.common.utils.slf.vo.ReceivePayResponse;
import com.epay.scanposp.common.utils.slf.vo.RefundRequest;
import com.epay.scanposp.common.utils.slf.vo.RefundResponse;
import com.epay.scanposp.common.utils.slf.vo.WeiXinScanRequest;
import com.epay.scanposp.common.utils.slf.vo.WeiXinScanResponse;


public class MerchantXmlUtil {
	private static DocumentBuilderFactory factory = null;
	private static DocumentBuilder builder = null;
	private Document document;
	static {
		try {
			factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 订单请求转换成xml
	 * @param request 请求
	 * @return 请求的xml报文
	 */	
	protected String orderRequestToXml(OrderRequest request) {
		//if(request.getMerchantId().length() == 0)request.setMerchantId(new MerchantClient().merchantId);
		//if(request.getMerchantPayNotifyUrl().length() == 0)request.setMerchantPayNotifyUrl(MerchantClient.merchantPayNotifyURL);
		//if(request.getMerchantFrontEndUrl().length() == 0)request.setMerchantFrontEndUrl(MerchantClient.merchantFrontEndUrl);
		document = builder.newDocument();
		Element root = document.createElement("message");
		root.setAttribute("application", "SubmitOrder");
		root.setAttribute("version", MerchantClient.version);
		root.setAttribute("merchantId", request.getMerchantId());
		if(request.getMerchantName() != null && request.getMerchantName().trim().length() > 0){
			root.setAttribute("merchantName", request.getMerchantName().trim());
		}
		root.setAttribute("merchantOrderId", request.getMerchantOrderId());
		root.setAttribute("merchantOrderAmt", request.getMerchantOrderAmt());
		if(request.getMerchantOrderDesc() != null && request.getMerchantOrderDesc().length() > 0){
			root.setAttribute("merchantOrderDesc", request.getMerchantOrderDesc());
		}
		if(request.getMerchantPayNotifyUrl() != null && request.getMerchantPayNotifyUrl().trim().length() > 0){
			root.setAttribute("merchantPayNotifyUrl", request.getMerchantPayNotifyUrl().trim());
		}
		if(request.getMerchantFrontEndUrl() != null && request.getMerchantFrontEndUrl().trim().length() > 0){
		//	root.setAttribute("merchantFrontEndUrl", request.getMerchantFrontEndUrl().trim());
		}
		if(request.getUserMobileNo() != null && request.getUserMobileNo().length() > 0){
			root.setAttribute("userMobileNo", request.getUserMobileNo());
		}
		if(request.getCredentialType() != null && request.getCredentialType().length() > 0){
			root.setAttribute("credentialType", request.getCredentialType());
		}
		if(request.getCredentialNo() != null && request.getCredentialNo().length() > 0){
			root.setAttribute("credentialNo", request.getCredentialNo());
		}
		if(request.getPayerId() != null && request.getPayerId().length() > 0){
			root.setAttribute("payerId", request.getPayerId());
		}
		if(request.getUserName() != null && request.getUserName().length() > 0){
			root.setAttribute("userName", request.getUserName());
		}
		if(request.getSalerId() != null && request.getSalerId().length() > 0){
			root.setAttribute("salerId", request.getSalerId());
		}
		if(request.getGuaranteeAmt() != null && request.getGuaranteeAmt().length() > 0){
			root.setAttribute("guaranteeAmt", request.getGuaranteeAmt());
		}
		if(request.getUserType() != null && request.getUserType().length() > 0){
			root.setAttribute("userType", request.getUserType());
		}
		root.setAttribute("accountType", request.getAccountType());
		if(request.getBankId() != null && request.getBankId().length() > 0){
			root.setAttribute("bankId", request.getBankId());
		}
		if(request.getMsgExt() != null && request.getMsgExt().length() > 0){
			root.setAttribute("msgExt", request.getMsgExt());
		}
		root.setAttribute("orderTime", request.getOrderTime());
		if(request.getBizType() != null && request.getBizType().length() > 0){
			root.setAttribute("bizType", request.getBizType());
		}
		root.setAttribute("rptType",request.getRptType());
		root.setAttribute("payMode",request.getPayMode());
		document.appendChild(root);
		request.setXml(createXml());
		return request.getXml();
	}
	/**
	 * 快捷支付请求转换成xml
	 * @param request 请求
	 * @return 请求的xml报文
	 */	
	protected String certPayRequestToXml(CertPayRequest request) {
		//if(request.getMerchantId().length() == 0)request.setMerchantId(new MerchantClient().merchantId);
		//if(request.getMerchantPayNotifyUrl().length() == 0)request.setMerchantPayNotifyUrl(MerchantClient.merchantPayNotifyURL);
		if(request.getMerchantFrontEndUrl().length() == 0)request.setMerchantFrontEndUrl(MerchantClient.merchantFrontEndUrl);
		document = builder.newDocument();
		Element root = document.createElement("message");
		root.setAttribute("application", request.getApplication());
		root.setAttribute("version", request.getVersion());
		root.setAttribute("merchantId", request.getMerchantId());
		root.setAttribute("merchantOrderId", request.getMerchantOrderId());
		root.setAttribute("merchantOrderAmt", request.getMerchantOrderAmt());
		if(request.getMerchantOrderDesc() != null && request.getMerchantOrderDesc().length() > 0){
			root.setAttribute("merchantOrderDesc", request.getMerchantOrderDesc());
		}
		root.setAttribute("userMobileNo", request.getUserMobileNo());
		root.setAttribute("payerId", request.getPayerId());
		root.setAttribute("userName", request.getUserName());
		root.setAttribute("salerId", request.getSalerId());
		root.setAttribute("guaranteeAmt", request.getGuaranteeAmt());
		root.setAttribute("credentialType", request.getCredentialType());
		root.setAttribute("credentialNo", request.getCredentialNo());
		if(request.getMerchantPayNotifyUrl() != null && request.getMerchantPayNotifyUrl().trim().length() > 0){
			root.setAttribute("merchantPayNotifyUrl", request.getMerchantPayNotifyUrl().trim());
		}
		if(request.getMerchantFrontEndUrl() != null && request.getMerchantFrontEndUrl().trim().length() > 0){
			root.setAttribute("merchantFrontEndUrl", request.getMerchantFrontEndUrl().trim());
		}
		root.setAttribute("cardNo", request.getCardNo());
		root.setAttribute("cvv2", request.getCvv2());
		root.setAttribute("validPeriod", request.getValidPeriod());
		document.appendChild(root);
		request.setXml(createXml());
		return request.getXml();
	}
	/**
	 * 微信扫码支付请求转换成xml
	 * @param request 请求
	 * @return 请求的xml报文
	 */	
	protected String weiXinScanRequestToXml(WeiXinScanRequest request) {
		//if(request.getMerchantId().length() == 0)request.setMerchantId(new MerchantClient().merchantId);
		//if(request.getMerchantPayNotifyUrl().length() == 0)request.setMerchantPayNotifyUrl(MerchantClient.merchantPayNotifyURL);
		document = builder.newDocument();
		Element root = document.createElement("message");
		root.setAttribute("application", request.getApplication());
		root.setAttribute("version", request.getVersion());
		root.setAttribute("timestamp", request.getTimestamp());
		root.setAttribute("merchantId", request.getMerchantId());
		root.setAttribute("merchantOrderId", request.getMerchantOrderId());
		root.setAttribute("merchantOrderAmt", request.getMerchantOrderAmt());
		if(request.getMerchantOrderDesc() != null && request.getMerchantOrderDesc().length() > 0){
			root.setAttribute("merchantOrderDesc", request.getMerchantOrderDesc());
		}
		root.setAttribute("userName", request.getUserName());
		root.setAttribute("payerId", request.getPayerId());
		root.setAttribute("salerId", request.getSalerId());
		root.setAttribute("guaranteeAmt", request.getGuaranteeAmt());
		if(request.getMerchantPayNotifyUrl() != null && request.getMerchantPayNotifyUrl().trim().length() > 0){
			root.setAttribute("merchantPayNotifyUrl", request.getMerchantPayNotifyUrl().trim());
		}
		document.appendChild(root);
		request.setXml(createXml());
		return request.getXml();
	}
	/**
	 * 快捷支付H5请求转换成xml
	 * @param request 请求
	 * @return 请求的xml报文
	 */	
	protected String certPayH5RequestToXml(CertPayH5Request request) {
		//if(request.getMerchantId().length() == 0)request.setMerchantId(new MerchantClient().merchantId);
		//if(request.getMerchantPayNotifyUrl().length() == 0)request.setMerchantPayNotifyUrl(MerchantClient.merchantPayNotifyURL);
		if(request.getMerchantFrontEndUrl().length() == 0)request.setMerchantFrontEndUrl(MerchantClient.merchantFrontEndUrl);
		document = builder.newDocument();
		Element root = document.createElement("message");
		root.setAttribute("application", request.getApplication());
		root.setAttribute("version", request.getVersion());
		root.setAttribute("merchantId", request.getMerchantId());
		root.setAttribute("merchantOrderId", request.getMerchantOrderId());
		root.setAttribute("merchantName", request.getMerchantName());
		root.setAttribute("merchantOrderAmt", request.getMerchantOrderAmt());
		if(request.getMerchantOrderDesc() != null && request.getMerchantOrderDesc().length() > 0){
			root.setAttribute("merchantOrderDesc", request.getMerchantOrderDesc());
		}
		root.setAttribute("payerId", request.getPayerId());
		root.setAttribute("salerId", request.getSalerId());
		root.setAttribute("guaranteeAmt", request.getGuaranteeAmt());
		if(request.getMerchantPayNotifyUrl() != null && request.getMerchantPayNotifyUrl().trim().length() > 0){
			root.setAttribute("merchantPayNotifyUrl", request.getMerchantPayNotifyUrl().trim());
		}
		if(request.getMerchantFrontEndUrl() != null && request.getMerchantFrontEndUrl().trim().length() > 0){
			root.setAttribute("merchantFrontEndUrl", request.getMerchantFrontEndUrl().trim());
		}
		document.appendChild(root);
		request.setXml(createXml());
		return request.getXml();
	}
	/**
	 * 快捷支付支付确认请求转换成xml
	 * @param request 请求
	 * @return 请求的xml报文
	 */	
	protected String certPayConfirmRequestToXml(CertPayConfirmRequest request) {
		document = builder.newDocument();
		Element root = document.createElement("message");
		root.setAttribute("application", request.getApplication());
		root.setAttribute("version", request.getVersion());
		root.setAttribute("merchantId", request.getMerchantId());
		root.setAttribute("merchantOrderId", request.getMerchantOrderId());
		root.setAttribute("checkCode", request.getCheckCode());
		document.appendChild(root);
		request.setXml(createXml());
		return request.getXml();
	}
	/**
	 * 退款请求转换成xml
	 * @param request 请求
	 * @return 请求的xml报文
	 */	
	protected String refundRequestToXml(RefundRequest request) {
		document = builder.newDocument();
		Element root = document.createElement("message");
		root.setAttribute("application", "RefundOrder");
		root.setAttribute("version", MerchantClient.version);
		root.setAttribute("merchantId", request.getMerchantId());
		root.setAttribute("merchantOrderId", request.getMerchantOrderId());
		root.setAttribute("merchantRefundAmt", request.getMerchantRefundAmt());
		document.appendChild(root);
		request.setXml(createXml());
		return request.getXml();
	}
	
//	/**
//	 * 缴费撤销请求转换成xml
//	 * @param request 请求
//	 * @return 请求的xml报文
//	 */	
//	protected String cancelRequestToXml(CancelRequest request) {
//		document = builder.newDocument();
//		Element root = document.createElement("message");
//		root.setAttribute("application", "CancelOrder");
//		root.setAttribute("version", MerchantClient.version);
//		root.setAttribute("merchantId", request.getMerchantId());
//		root.setAttribute("merchantOrderId", request.getMerchantOrderId());
//		document.appendChild(root);
//		request.setXml(createXml());
//		return request.getXml();
//	}
	/**
	 * 对账文件下载请求转换成xml
	 * @param request 请求
	 * @return 请求的xml报文
	 */	
	protected String accountRequestToXml(AccountRequest request) {
		document = builder.newDocument();
		Element root = document.createElement("message");
		root.setAttribute("application", "AccountFile");
		root.setAttribute("version", MerchantClient.version);
		root.setAttribute("merchantId", request.getMerchantId());
		root.setAttribute("accountDate", request.getAccountDate());
		document.appendChild(root);
		request.setXml(createXml());
		return request.getXml();
	}
	/**
	 * 担保通知请求转换成xml
	 * @param request 请求
	 * @return 请求的xml报文
	 */	
	protected String guaranteeNoticeRequestToXml(GuaranteeNoticeRequest request) {
		document = builder.newDocument();
		Element root = document.createElement("message");
		root.setAttribute("application", "GuaranteeNotice");
		root.setAttribute("version", MerchantClient.version);
		root.setAttribute("merchantId", request.getMerchantId());
		root.setAttribute("merchantOrderId", request.getMerchantOrderId());
		root.setAttribute("status", request.getStatus());
		document.appendChild(root);
		request.setXml(createXml());
		return request.getXml();
	}
	/**
	 * 查询订单请求转换成xml
	 * @param request 请求
	 * @return 请求的xml报文
	 */	
	protected String queryRequestToXml(QueryRequest request) {
		document = builder.newDocument();
		Element root = document.createElement("message");
		root.setAttribute("application", "QueryOrder");
		root.setAttribute("version", MerchantClient.version);
		root.setAttribute("merchantId", request.getMerchantId());
		root.setAttribute("merchantOrderId", request.getMerchantOrderId());
		document.appendChild(root);
		request.setXml(createXml());
		return request.getXml();
	}
	
	/**
	 * TODO 实名认证请求转xml 
	 * @param authRequest
	 * @return
	 */
	protected String realNameAuthRequestToXml(RealNameAuthRequest authRequest) {
		document = builder.newDocument();
		Element root = document.createElement("message");
		root.setAttribute("application", "RealNameAuth");
		root.setAttribute("version", authRequest.getVersion());
		root.setAttribute("merchantId", authRequest.getMerchantId());
		root.setAttribute("tranId", authRequest.getTranId());
		root.setAttribute("timestamp", authRequest.getTimestamp());
		Element createElement = document.createElement("authList");
		root.appendChild(createElement);
		List<ReceivePay> authList = authRequest.getAuthList();
		for(int i = 0; i<authList.size(); i++){
			ReceivePay receivePay = authList.get(i);
			Element item = document.createElement("item");
			item.setAttribute("sn", receivePay.getSn());
			item.setAttribute("accNo", receivePay.getAccNo());
			item.setAttribute("accName", receivePay.getAccName());
			item.setAttribute("credentialType", receivePay.getCredentialType());
			item.setAttribute("credentialNo", receivePay.getCredentialNo());
			item.setAttribute("tel", receivePay.getTel());
			createElement.appendChild(item);
		}
		document.appendChild(root);
		authRequest.setXml(createXml());
		return authRequest.getXml();
	}
	
	
	/**
	 * TODO 单笔收付款
	 * @param certPayRequest
	 * @return
	 */
	protected String receivePayRequestToXml(ReceivePayRequest request) {
		document = builder.newDocument();
		Element root = document.createElement("message");
		root.setAttribute("application", "ReceivePay");
		root.setAttribute("version", request.getVersion());
		root.setAttribute("merchantId", request.getMerchantId());
		root.setAttribute("tranId", request.getTranId());
		root.setAttribute("receivePayNotifyUrl", request.getReceivePayNotifyUrl());
		root.setAttribute("timestamp", request.getTimestamp());
		root.setAttribute("receivePayType", request.getReceivePayType());
		root.setAttribute("accountProp", request.getAccountProp());
		root.setAttribute("bankGeneralName", request.getBankGeneralName());
		root.setAttribute("accNo", request.getAccNo());
		root.setAttribute("accName", request.getAccName());
		root.setAttribute("amount", request.getAmount());
		root.setAttribute("credentialType", request.getCredentialType());
		root.setAttribute("credentialNo", request.getCredentialNo());
		root.setAttribute("tel", request.getTel());
		root.setAttribute("summary", request.getSummary());
		document.appendChild(root);
		request.setXml(createXml());
		return request.getXml();
	}
	
	/**
	 * 批量收付款xml转换
	 * @param receivePayBatchRequest
	 * @return
	 */
	protected String receivePayBatchRequestToXml(ReceivePayBatchRequest receivePayBatchRequest) {
		document = builder.newDocument();
		Element root = document.createElement("message");
		root.setAttribute("application", "ReceivePayBatch");
		root.setAttribute("version", receivePayBatchRequest.getVersion());
		root.setAttribute("merchantId", receivePayBatchRequest.getMerchantId());
		root.setAttribute("tranId", receivePayBatchRequest.getTranId());
		root.setAttribute("receivePayNotifyUrl", receivePayBatchRequest.getReceivePayNotifyUrl());
		root.setAttribute("receivePayType", receivePayBatchRequest.getReceivePayType());
		root.setAttribute("timestamp", receivePayBatchRequest.getTimestamp());
		root.setAttribute("accountProp", receivePayBatchRequest.getAccountProp());
		Element createElement = document.createElement("tranList");
		root.appendChild(createElement);
		for(int i = 0; i<receivePayBatchRequest.getTranList().size(); i++){
			ReceivePay receivePay = receivePayBatchRequest.getTranList().get(i);
			Element item = document.createElement("item");
			item.setAttribute("sn", receivePay.getSn());
			item.setAttribute("accNo", receivePay.getAccNo());
			item.setAttribute("bankGeneralName", receivePay.getBankGeneralName());
			item.setAttribute("accName", receivePay.getAccName());
			item.setAttribute("amount", receivePay.getAmount());
			item.setAttribute("credentialType", receivePay.getCredentialType());
			item.setAttribute("credentialNo", receivePay.getCredentialNo());
			item.setAttribute("tel", receivePay.getTel());
			item.setAttribute("summary", receivePay.getSummary());
			createElement.appendChild(item);
		}
		document.appendChild(root);
		receivePayBatchRequest.setXml(createXml());
		return receivePayBatchRequest.getXml();
	}
	
	/**
	 * 收付款查询请求转换为xml
	 * @param queryrRequest
	 * @return
	 */
	protected String receivePayQueryRequestToXml(ReceivePayQueryRequest queryrRequest) {
		document = builder.newDocument();
		Element root = document.createElement("message");
		root.setAttribute("application", "ReceivePayQuery");
		root.setAttribute("version", queryrRequest.getVersion());
		root.setAttribute("merchantId", queryrRequest.getMerchantId());
		root.setAttribute("timestamp", queryrRequest.getTimestamp());
		root.setAttribute("queryTranId", queryrRequest.getQueryTranId());
		document.appendChild(root);
		queryrRequest.setXml(createXml());
		return queryrRequest.getXml();
	}
	
	/**
	 * TODO xml转实名认证响应 
	 * @param responseSrc
	 * @return
	 */
	protected ReceivePayResponse xmlToRealNameAuthResponse(String responseSrc) {
		ByteArrayInputStream bais = null;
		DataInputStream dis = null;
		try {
			bais = new ByteArrayInputStream(responseSrc.getBytes("utf-8"));
			dis = new DataInputStream(bais);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.newDocument();
			document = builder.parse(dis);
			document.getDocumentElement().normalize();
			NamedNodeMap nnm = document.getFirstChild().getAttributes();
			ReceivePayResponse response = new ReceivePayResponse();
			response.setXml(responseSrc);
			for(int j = 0; j<nnm.getLength(); j++){
				Node nodeitm= nnm.item(j);
				String value = nodeitm.getNodeValue();
				if(nodeitm.getNodeName().equals("application")){
					response.setApplication(value);
				}else if (nodeitm.getNodeName().equals("version")) {
					response.setVersion(value);
				} else if (nodeitm.getNodeName().equals("merchantId")) {
					response.setMerchantId(value);
				} else if (nodeitm.getNodeName().equals("tranId")) {
					response.setTranId(value);
				} else if (nodeitm.getNodeName().equals("timestamp")) {
					response.setTimestamp(value);
				} else if (nodeitm.getNodeName().equals("respCode")) {
					response.setRespCode(value);
				} else if (nodeitm.getNodeName().equals("respDesc")) {
					response.setRespDesc(value);
				}
			}
			NodeList messageChildList = document.getFirstChild().getChildNodes();
			for(int i = 0; i<messageChildList.getLength(); i++){
				Node node = messageChildList.item(i);
				NodeList nodeCList = node.getChildNodes();
				if(node.getNodeName().equals("authList")){
					for(int j = 0; j<nodeCList.getLength();j++){
						nnm = nodeCList.item(j).getAttributes();
						if(nnm == null)continue;
						ReceivePay receivePay = new ReceivePay();
						for(int k = 0; k<nnm.getLength(); k++){
							Node nodeitm= nnm.item(k);
							String value = nodeitm.getNodeValue();
							if (nodeitm.getNodeName().equals("sn")) {
								receivePay.setSn(value);
							} else if (nodeitm.getNodeName().equals("respCode")) {
								receivePay.setRespCode(value);
							} else if (nodeitm.getNodeName().equals("respDesc")) {
								receivePay.setRespDesc(value);
							}
						}
						response.getTranList().add(receivePay);
					}
					
				}
			}
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(dis != null){
				try {
					dis.close();
				} catch (Exception e2) {}
			}
			if(bais != null){
				try {
					bais.close();
				} catch (Exception e2) {}
			}
		}
		return null;
	}
	
	/**
	 * TODO xml转换成单笔收付款响应
	 * @param responseSrc
	 * @return
	 */
	protected ReceivePayResponse xmlToReceivePayResponse(String responseSrc) {
		return this.xmlToRealNameAuthResponse(responseSrc);
	}
	
	/**
	 * TODO xml转换多笔收付款响应
	 * @param responseSrc
	 * @return
	 */
	protected ReceivePayBatchResponse xmlToReceivePayBatchResponse(String responseSrc) {
		ByteArrayInputStream bais = null;
		DataInputStream dis = null;
		try {
			bais = new ByteArrayInputStream(responseSrc.getBytes("utf-8"));
			dis = new DataInputStream(bais);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.newDocument();
			document = builder.parse(dis);
			document.getDocumentElement().normalize();
			NamedNodeMap nnm = document.getFirstChild().getAttributes();
			ReceivePayBatchResponse response = new ReceivePayBatchResponse();
			response.setXml(responseSrc);
			for(int j = 0; j<nnm.getLength(); j++){
				Node nodeitm= nnm.item(j);
				String value = nodeitm.getNodeValue();
				if(nodeitm.getNodeName().equals("application")){
					response.setApplication(value);
				}else if (nodeitm.getNodeName().equals("version")) {
					response.setVersion(value);
				} else if (nodeitm.getNodeName().equals("merchantId")) {
					response.setMerchantId(value);
				} else if (nodeitm.getNodeName().equals("tranId")) {
					response.setTranId(value);
				} else if (nodeitm.getNodeName().equals("timestamp")) {
					response.setTimestamp(value);
				} else if (nodeitm.getNodeName().equals("respCode")) {
					response.setRespCode(value);
				} else if (nodeitm.getNodeName().equals("respDesc")) {
					response.setRespDesc(value);
				}
			}
			NodeList messageChildList = document.getFirstChild().getChildNodes();
			for(int i = 0; i<messageChildList.getLength(); i++){
				Node node = messageChildList.item(i);
				NodeList nodeCList = node.getChildNodes();
				if(node.getNodeName().equals("tranList")){
					for(int j = 0; j<nodeCList.getLength();j++){
						nnm = nodeCList.item(j).getAttributes();
						if(nnm == null)continue;
						ReceivePay receivePay = new ReceivePay();
						for(int k = 0; k<nnm.getLength(); k++){
							Node nodeitm= nnm.item(k);
							String value = nodeitm.getNodeValue();
							if (nodeitm.getNodeName().equals("sn")) {
								receivePay.setSn(value);
							} else if (nodeitm.getNodeName().equals("respCode")) {
								receivePay.setRespCode(value);
							} else if (nodeitm.getNodeName().equals("respDesc")) {
								receivePay.setRespDesc(value);
							}
						}
						response.getTranList().add(receivePay);
					}
				} 
			}
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(dis != null){
				try {
					dis.close();
				} catch (Exception e2) {}
			}
			if(bais != null){
				try {
					bais.close();
				} catch (Exception e2) {}
			}
		}
		return null;
	}
	
	/**
	 * xml转成收付款查询响应对象
	 * @param responseSrc
	 * @return
	 */
	protected ReceivePayQueryResponse xmlToReceivePayQueryResponse(String responseSrc) {
		ByteArrayInputStream bais = null;
		DataInputStream dis = null;
		try {
			bais = new ByteArrayInputStream(responseSrc.getBytes("utf-8"));
			dis = new DataInputStream(bais);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.newDocument();
			document = builder.parse(dis);
			document.getDocumentElement().normalize();
			NamedNodeMap nnm = document.getFirstChild().getAttributes();
			ReceivePayQueryResponse response = new ReceivePayQueryResponse();
			response.setXml(responseSrc);
			for(int j = 0; j<nnm.getLength(); j++){
				Node nodeitm= nnm.item(j);
				String value = nodeitm.getNodeValue();
				if(nodeitm.getNodeName().equals("application")) response.setApplication(value);
				else if (nodeitm.getNodeName().equals("version")) response.setVersion(value);
				else if (nodeitm.getNodeName().equals("merchantId")) response.setMerchantId(value);
				else if (nodeitm.getNodeName().equals("timestamp")) response.setTimestamp(value);
				else if (nodeitm.getNodeName().equals("respCode")) response.setRespCode(value);
				else if (nodeitm.getNodeName().equals("respDesc")) response.setRespDesc(value);
				else if (nodeitm.getNodeName().equals("tranId")) response.setTranId(value);
			}
			NodeList messageChildList = document.getFirstChild().getChildNodes();
			for(int i = 0; i<messageChildList.getLength(); i++){
				Node node = messageChildList.item(i);
				NodeList nodeCList = node.getChildNodes();
				if(node.getNodeName().equals("queryList")){
					for(int j = 0; j<nodeCList.getLength();j++){
						nnm = nodeCList.item(j).getAttributes();
						if(nnm == null)continue;
						ReceivePay receivePay = new ReceivePay();
						for(int k = 0; k<nnm.getLength(); k++){
							Node nodeitm= nnm.item(k);
							String value = nodeitm.getNodeValue();
							if (nodeitm.getNodeName().equals("sn")) receivePay.setSn(value);
							else if (nodeitm.getNodeName().equals("respCode")) receivePay.setRespCode(value);
							else if (nodeitm.getNodeName().equals("respDesc")) receivePay.setRespDesc(value);
						}
						response.getQueryList().add(receivePay);
					}
				} 
			}
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(dis != null) try { dis.close(); } catch (Exception e2) {}
			if(bais != null) try { bais.close(); } catch (Exception e2) {}
		}
		return null;
	}
	
	
	/**
	 * xml转换成订单响应
	 * @param xmlStr 响应报文
	 * @return 响应对象
	 */
	protected OrderResponse xmlToOrderResponse(String xmlStr) {
		ByteArrayInputStream bais = null;
		DataInputStream dis = null;
		try {
			bais = new ByteArrayInputStream(xmlStr.getBytes("utf-8"));
			dis = new DataInputStream(bais);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.newDocument();
			document = builder.parse(dis);
			document.getDocumentElement().normalize();
			NamedNodeMap nnm = document.getFirstChild().getAttributes();
			OrderResponse response = new OrderResponse();
			response.setXml(xmlStr);
			for(int j = 0; j<nnm.getLength(); j++){
				Node nodeitm= nnm.item(j);
				String value = nodeitm.getNodeValue();
				if (nodeitm.getNodeName().equals("merchantId")) {
					response.setMerchantId(value);
				} else if (nodeitm.getNodeName().equals("merchantOrderId")) {
					response.setMerchantOrderId(value);
				} else if (nodeitm.getNodeName().equals("respCode")) {
					response.setRespCode(value);
				} else if (nodeitm.getNodeName().equals("respDesc")) {
					response.setRespDesc(value);
				}
			}
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(dis != null){
				try {
					dis.close();
				} catch (Exception e2) {}
			}
			if(bais != null){
				try {
					bais.close();
				} catch (Exception e2) {}
			}
		}
		return null;
	}
	/**
	 * xml转换成退款响应
	 * @param xmlStr  响应报文
	 * @return 响应对象
	 */
	protected RefundResponse xmlToRefundResponse(String xmlStr) {
		ByteArrayInputStream bais = null;
		DataInputStream dis = null;
		try {
			bais = new ByteArrayInputStream(xmlStr.getBytes("utf-8"));
			dis = new DataInputStream(bais);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.newDocument();
			document = builder.parse(dis);
			document.getDocumentElement().normalize();
			NamedNodeMap nnm = document.getFirstChild().getAttributes();
			RefundResponse response = new RefundResponse();
			response.setXml(xmlStr);
			for(int j = 0; j<nnm.getLength(); j++){
				Node nodeitm= nnm.item(j);
				String value = nodeitm.getNodeValue();
				if (nodeitm.getNodeName().equals("merchantId")) {
					response.setMerchantId(value);
				} else if (nodeitm.getNodeName().equals("merchantOrderId")) {
					response.setMerchantOrderId(value);
				} else if (nodeitm.getNodeName().equals("respCode")) {
					response.setRespCode(value);
				} else if (nodeitm.getNodeName().equals("respDesc")) {
					response.setRespDesc(value);
				}
			}
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(dis != null){
				try {
					dis.close();
				} catch (Exception e2) {}
			}
			if(bais != null){
				try {
					bais.close();
				} catch (Exception e2) {}
			}
		}
		return null;
	}
	/**
	 * xml转换成担保通知响应
	 * @param xmlStr  响应报文
	 * @return 响应对象
	 */
	protected GuaranteeNoticeResponse xmlToGuaranteeNoticeResponse(String xmlStr) {
		ByteArrayInputStream bais = null;
		DataInputStream dis = null;
		try {
			bais = new ByteArrayInputStream(xmlStr.getBytes("utf-8"));
			dis = new DataInputStream(bais);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.newDocument();
			document = builder.parse(dis);
			document.getDocumentElement().normalize();
			NamedNodeMap nnm = document.getFirstChild().getAttributes();
			GuaranteeNoticeResponse response = new GuaranteeNoticeResponse();
			response.setXml(xmlStr);
			for(int j = 0; j<nnm.getLength(); j++){
				Node nodeitm= nnm.item(j);
				String value = nodeitm.getNodeValue();
				if (nodeitm.getNodeName().equals("merchantId")) {
					response.setMerchantId(value);
				} else if (nodeitm.getNodeName().equals("merchantOrderId")) {
					response.setMerchantOrderId(value);
				} else if (nodeitm.getNodeName().equals("respCode")) {
					response.setRespCode(value);
				} else if (nodeitm.getNodeName().equals("respDesc")) {
					response.setRespDesc(value);
				}
			}
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(dis != null){
				try {
					dis.close();
				} catch (Exception e2) {}
			}
			if(bais != null){
				try {
					bais.close();
				} catch (Exception e2) {}
			}
		}
		return null;
	}
	/**
	 * xml转换成快捷支付下单响应
	 * @param xmlStr  响应报文
	 * @return 响应对象
	 */
	protected CertPayResponse xmlToCertPayResponse(String xmlStr) {
		ByteArrayInputStream bais = null;
		DataInputStream dis = null;
		try {
			bais = new ByteArrayInputStream(xmlStr.getBytes("utf-8"));
			dis = new DataInputStream(bais);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.newDocument();
			document = builder.parse(dis);
			document.getDocumentElement().normalize();
			NamedNodeMap nnm = document.getFirstChild().getAttributes();
			CertPayResponse response = new CertPayResponse();
			response.setXml(xmlStr);
			for(int j = 0; j<nnm.getLength(); j++){
				Node nodeitm= nnm.item(j);
				String value = nodeitm.getNodeValue();
				if (nodeitm.getNodeName().equals("merchantId")) {
					response.setMerchantId(value);
				} else if (nodeitm.getNodeName().equals("merchantOrderId")) {
					response.setMerchantOrderId(value);
				} else if (nodeitm.getNodeName().equals("bindId")) {
					response.setBindId(value);
				} else if (nodeitm.getNodeName().equals("bankId")) {
					response.setBankId(value);
				} else if (nodeitm.getNodeName().equals("bankName")) {
					response.setBankName(value);
				} else if (nodeitm.getNodeName().equals("respCode")) {
					response.setRespCode(value);
				} else if (nodeitm.getNodeName().equals("respDesc")) {
					response.setRespDesc(value);
				}
			}
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(dis != null){
				try {
					dis.close();
				} catch (Exception e2) {}
			}
			if(bais != null){
				try {
					bais.close();
				} catch (Exception e2) {}
			}
		}
		return null;
	}
	/**
	 * xml转换成微信扫码支付下单响应
	 * @param xmlStr  响应报文
	 * @return 响应对象
	 */
	protected WeiXinScanResponse xmlToWeiXinScanResponse(String xmlStr) {
		ByteArrayInputStream bais = null;
		DataInputStream dis = null;
		try {
			bais = new ByteArrayInputStream(xmlStr.getBytes("utf-8"));
			dis = new DataInputStream(bais);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.newDocument();
			document = builder.parse(dis);
			document.getDocumentElement().normalize();
			NamedNodeMap nnm = document.getFirstChild().getAttributes();
			WeiXinScanResponse response = new WeiXinScanResponse();
			response.setXml(xmlStr);
			for(int j = 0; j<nnm.getLength(); j++){
				Node nodeitm= nnm.item(j);
				String value = nodeitm.getNodeValue();
				if (nodeitm.getNodeName().equals("merchantId")) {
					response.setMerchantId(value);
				} else if (nodeitm.getNodeName().equals("merchantOrderId")) {
					response.setMerchantOrderId(value);
				} else if (nodeitm.getNodeName().equals("codeUrl")) {
					response.setCodeUrl(value);
				} else if (nodeitm.getNodeName().equals("respCode")) {
					response.setRespCode(value);
				} else if (nodeitm.getNodeName().equals("respDesc")) {
					response.setRespDesc(value);
				}
			}
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(dis != null){
				try {
					dis.close();
				} catch (Exception e2) {}
			}
			if(bais != null){
				try {
					bais.close();
				} catch (Exception e2) {}
			}
		}
		return null;
	}
	/**
	 * xml转换成快捷支付支付确认响应
	 * @param xmlStr  响应报文
	 * @return 响应对象
	 */
	protected CertPayConfirmResponse xmlToCertPayConfirmResponse(String xmlStr) {
		ByteArrayInputStream bais = null;
		DataInputStream dis = null;
		try {
			bais = new ByteArrayInputStream(xmlStr.getBytes("utf-8"));
			dis = new DataInputStream(bais);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.newDocument();
			document = builder.parse(dis);
			document.getDocumentElement().normalize();
			NamedNodeMap nnm = document.getFirstChild().getAttributes();
			CertPayConfirmResponse response = new CertPayConfirmResponse();
			response.setXml(xmlStr);
			for(int j = 0; j<nnm.getLength(); j++){
				Node nodeitm= nnm.item(j);
				String value = nodeitm.getNodeValue();
				if (nodeitm.getNodeName().equals("merchantId")) {
					response.setMerchantId(value);
				} else if (nodeitm.getNodeName().equals("merchantOrderId")) {
					response.setMerchantOrderId(value);
				} else if (nodeitm.getNodeName().equals("bindId")) {
					response.setBindId(value);
				} else if (nodeitm.getNodeName().equals("cardNo")) {
					response.setCardNo(value);
				} else if (nodeitm.getNodeName().equals("cardType")) {
					response.setCardType(value);
				} else if (nodeitm.getNodeName().equals("bankId")) {
					response.setBankId(value);
				} else if (nodeitm.getNodeName().equals("bankName")) {
					response.setBankName(value);
				} else if (nodeitm.getNodeName().equals("userMobileNo")) {
					response.setUserMobileNo(value);
				} else if (nodeitm.getNodeName().equals("respCode")) {
					response.setRespCode(value);
				} else if (nodeitm.getNodeName().equals("respDesc")) {
					response.setRespDesc(value);
				}
			}
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(dis != null){
				try {
					dis.close();
				} catch (Exception e2) {}
			}
			if(bais != null){
				try {
					bais.close();
				} catch (Exception e2) {}
			}
		}
		return null;
	}
//	/**
//	 * xml转换成缴费撤销响应
//	 * @param xmlStr 响应报文
//	 * @return 响应对象
//	 */
//	protected CancelResponse xmlToCancelResponse(String xmlStr) {
//		ByteArrayInputStream bais = null;
//		DataInputStream dis = null;
//		try {
//			bais = new ByteArrayInputStream(xmlStr.getBytes("utf-8"));
//			dis = new DataInputStream(bais);
//			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//			DocumentBuilder builder = factory.newDocumentBuilder();
//			document = builder.newDocument();
//			document = builder.parse(dis);
//			document.getDocumentElement().normalize();
//			NamedNodeMap nnm = document.getFirstChild().getAttributes();
//			CancelResponse response = new CancelResponse();
//			response.setXml(xmlStr);
//			for(int j = 0; j<nnm.getLength(); j++){
//				Node nodeitm= nnm.item(j);
//				String value = nodeitm.getNodeValue();
//				if (nodeitm.getNodeName().equals("merchantId")) {
//					response.setMerchantId(value);
//				} else if (nodeitm.getNodeName().equals("merchantOrderId")) {
//					response.setMerchantOrderId(value);
//				} else if (nodeitm.getNodeName().equals("respCode")) {
//					response.setRespCode(value);
//				} else if (nodeitm.getNodeName().equals("respDesc")) {
//					response.setRespDesc(value);
//				}
//			}
//			return response;
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if(dis != null){
//				try {
//					dis.close();
//				} catch (Exception e2) {}
//			}
//			if(bais != null){
//				try {
//					bais.close();
//				} catch (Exception e2) {}
//			}
//		}
//		return null;
//	}
	/**
	 * xml转换成查询订单响应
	 * @param xmlStr 响应报文
	 * @return 响应对象
	 */
	protected QueryResponse xmlToQueryResponse(String xmlStr) {
		ByteArrayInputStream bais = null;
		DataInputStream dis = null;
		try {
			bais = new ByteArrayInputStream(xmlStr.getBytes("utf-8"));
			dis = new DataInputStream(bais);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.newDocument();
			document = builder.parse(dis);
			document.getDocumentElement().normalize();
			NamedNodeMap nnm = document.getFirstChild().getAttributes();
			QueryResponse response = new QueryResponse();
			response.setXml(xmlStr);
			for(int j = 0; j<nnm.getLength(); j++){
				Node nodeitm= nnm.item(j);
				String value = nodeitm.getNodeValue();
				if (nodeitm.getNodeName().equals("merchantId")) {
					response.setMerchantId(value);
				} else if (nodeitm.getNodeName().equals("merchantOrderId")) {
					response.setMerchantOrderId(value);
				} else if (nodeitm.getNodeName().equals("respCode")) {
					response.setRespCode(value);
				} else if (nodeitm.getNodeName().equals("respDesc")) {
					response.setRespDesc(value);
				}
			}
			NodeList messageChildList = document.getFirstChild().getChildNodes();
			for(int i = 0; i<messageChildList.getLength(); i++){
				Node node = messageChildList.item(i);
				NodeList nodeCList = node.getChildNodes();
				if(node.getNodeName().equals("deductList")){
					for(int j = 0; j<nodeCList.getLength();j++){
						nnm = nodeCList.item(j).getAttributes();
						if(nnm == null)continue;
						DeductInfo deductInfo = new DeductInfo();
						for(int k = 0; k<nnm.getLength(); k++){
							Node nodeitm= nnm.item(k);
							String value = nodeitm.getNodeValue();
							if (nodeitm.getNodeName().equals("payOrderId")) {
								deductInfo.setPayOrderId(value);
							} else if (nodeitm.getNodeName().equals("payAmt")) {
								deductInfo.setPayAmt(value);
							} else if (nodeitm.getNodeName().equals("payTime")) {
								deductInfo.setPayTime(value);
							} else if (nodeitm.getNodeName().equals("payStatus")) {					
								deductInfo.setPayStatus(value);
							} else if (nodeitm.getNodeName().equals("payDesc")) {
								deductInfo.setPayDesc(value);
							}
						}
						response.getDeductList().add(deductInfo);
					}
//				} else if(node.getNodeName().equals("cancelList")){
//					for(int j = 0; j<nodeCList.getLength();j++){
//						nnm = nodeCList.item(j).getAttributes();
//						if(nnm == null)continue;
//						CancelResponse coResponse = new CancelResponse();
//						coResponse.setMerchantId(response.getMerchantId());
//						coResponse.setMerchantOrderId(response.getMerchantOrderId());
//						for(int k = 0; k<nnm.getLength(); k++){
//							Node nodeitm= nnm.item(k);
//							String value = nodeitm.getNodeValue();
//							if (nodeitm.getNodeName().equals("respCode")) {
//								coResponse.setRespCode(value);
//							} else if (nodeitm.getNodeName().equals("respDesc")) {
//								coResponse.setRespDesc(value);
//							}
//						}
//						response.getCancelList().add(coResponse);
//					}
				} else if(node.getNodeName().equals("refundList")){
					for(int j = 0; j<nodeCList.getLength();j++){
						nnm = nodeCList.item(j).getAttributes();
						if(nnm == null)continue;
						RefundResponse refundResponse = new RefundResponse();
						refundResponse.setMerchantId(response.getMerchantId());
						refundResponse.setMerchantOrderId(response.getMerchantOrderId());
						for(int k = 0; k<nnm.getLength(); k++){
							Node nodeitm= nnm.item(k);
							String value = nodeitm.getNodeValue();
							if (nodeitm.getNodeName().equals("merchantRefundAmt")) {
								refundResponse.setMerchantRefundAmt(value);
							} else if (nodeitm.getNodeName().equals("refundStatus")) {
								refundResponse.setRefundStatus(value);
							} else if (nodeitm.getNodeName().equals("refundDesc")) {
								refundResponse.setRefundDesc(value);
							} else if (nodeitm.getNodeName().equals("refundTime")) {
								refundResponse.setRefundTime(value);
							}
						}
						response.getRefundList().add(refundResponse);
					}
				}
			}
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(dis != null){
				try {
					dis.close();
				} catch (Exception e2) {}
			}
			if(bais != null){
				try {
					bais.close();
				} catch (Exception e2) {}
			}
		}
		return null;
	}
	/**
	 * xml转换成支付通知响应
	 * @param xmlStr 响应报文
	 * @return 响应对象
	 */
	protected PaymentNotifyResponse xmlToPaymentNotifyResponse(String xmlStr) {
		ByteArrayInputStream bais = null;
		DataInputStream dis = null;
		try {
			bais = new ByteArrayInputStream(xmlStr.getBytes("utf-8"));
			dis = new DataInputStream(bais);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.newDocument();
			document = builder.parse(dis);
			document.getDocumentElement().normalize();
			NamedNodeMap nnm = document.getFirstChild().getAttributes();
			PaymentNotifyResponse response = new PaymentNotifyResponse();
			response.setXml(xmlStr);
			for(int j = 0; j<nnm.getLength(); j++){
				Node nodeitm= nnm.item(j);
				String value = nodeitm.getNodeValue();
				if (nodeitm.getNodeName().equals("merchantId")) {
					response.setMerchantId(value);
				} else if (nodeitm.getNodeName().equals("merchantOrderId")) {
					response.setMerchantOrderId(value);
				} else if (nodeitm.getNodeName().equals("version")) {
					response.setVersion(value);
				} else if (nodeitm.getNodeName().equals("application")) {
					response.setApplication(value);
				}
			}
			NodeList messageChildList = document.getFirstChild().getChildNodes();
			for(int i = 0; i<messageChildList.getLength(); i++){
				Node node = messageChildList.item(i);
				NodeList nodeCList = node.getChildNodes();
				if(node.getNodeName().equals("deductList")){
					for(int j = 0; j<nodeCList.getLength();j++){
						nnm = nodeCList.item(j).getAttributes();
						if(nnm == null)continue;
						DeductInfo deductInfo = new DeductInfo();
						for(int k = 0; k<nnm.getLength(); k++){
							Node nodeitm= nnm.item(k);
							String value = nodeitm.getNodeValue();
							if (nodeitm.getNodeName().equals("payOrderId")) deductInfo.setPayOrderId(value);
							else if (nodeitm.getNodeName().equals("payAmt")) deductInfo.setPayAmt(value);
							else if (nodeitm.getNodeName().equals("payTime")) deductInfo.setPayTime(value);
							else if (nodeitm.getNodeName().equals("payStatus")) deductInfo.setPayStatus(value);
							else if (nodeitm.getNodeName().equals("payDesc")) deductInfo.setPayDesc(value);
						}
						response.getDeductList().add(deductInfo);
					}
				} else if(node.getNodeName().equals("refundList")){
					for(int j = 0; j<nodeCList.getLength();j++){
						nnm = nodeCList.item(j).getAttributes();
						if(nnm == null)continue;
						RefundResponse refundResponse = new RefundResponse();
						refundResponse.setMerchantId(response.getMerchantId());
						refundResponse.setMerchantOrderId(response.getMerchantOrderId());
						for(int k = 0; k<nnm.getLength(); k++){
							Node nodeitm= nnm.item(k);
							String value = nodeitm.getNodeValue();
							if (nodeitm.getNodeName().equals("respCode")) {
								refundResponse.setRespCode(value);
							} else if (nodeitm.getNodeName().equals("respDesc")) {
								refundResponse.setRespDesc(value);
							} else if (nodeitm.getNodeName().equals("merchantRefundAmt")) {
								refundResponse.setMerchantRefundAmt(value);
							}
						}
						response.getRefundList().add(refundResponse);
					}
				}
			}
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(dis != null)try {dis.close();} catch (Exception e2) {}
			if(bais != null)try {bais.close();} catch (Exception e2) {}
		}
		return null;
	}
	/**
	 * xml转换成支付通知响应
	 * @param xmlStr 响应报文
	 * @return 响应对象
	 */
	protected ReceivePayResponse xmlToReceivePayNotifyResponse(String xmlStr) {
		ByteArrayInputStream bais = null;
		DataInputStream dis = null;
		try {
			bais = new ByteArrayInputStream(xmlStr.getBytes("utf-8"));
			dis = new DataInputStream(bais);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.newDocument();
			document = builder.parse(dis);
			document.getDocumentElement().normalize();
			NamedNodeMap nnm = document.getFirstChild().getAttributes();
			ReceivePayResponse response = new ReceivePayResponse();
			response.setXml(xmlStr);
			for(int j = 0; j<nnm.getLength(); j++){
				Node nodeitm= nnm.item(j);
				String value = nodeitm.getNodeValue();
				if (nodeitm.getNodeName().equals("merchantId")) response.setMerchantId(value);
				else if (nodeitm.getNodeName().equals("tranId")) response.setTranId(value);
				else if (nodeitm.getNodeName().equals("version")) response.setVersion(value);
				else if (nodeitm.getNodeName().equals("application")) response.setApplication(value);
				else if (nodeitm.getNodeName().equals("timestamp")) response.setTimestamp(value);
			}
			NodeList messageChildList = document.getFirstChild().getChildNodes();
			for(int i = 0; i<messageChildList.getLength(); i++){
				Node node = messageChildList.item(i);
				NodeList nodeCList = node.getChildNodes();
				if(node.getNodeName().equals("tranList")){
					for(int j = 0; j<nodeCList.getLength();j++){
						nnm = nodeCList.item(j).getAttributes();
						if(nnm == null)continue;
						ReceivePay receivePay = new ReceivePay();
						for(int k = 0; k<nnm.getLength(); k++){
							Node nodeitm= nnm.item(k);
							String value = nodeitm.getNodeValue();
							if (nodeitm.getNodeName().equals("sn")) receivePay.setSn(value);
							else if (nodeitm.getNodeName().equals("respCode")) receivePay.setRespCode(value);
							else if (nodeitm.getNodeName().equals("respDesc")) receivePay.setRespDesc(value);
						}
						response.getTranList().add(receivePay);
					}
				}
			}
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(dis != null)try {dis.close();} catch (Exception e2) {}
			if(bais != null)try {bais.close();} catch (Exception e2) {}
		}
		return null;
	}
	/**
	 * 生成sml
	 * @return
	 */
	private String createXml() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			DOMSource source = new DOMSource(document);
			transformer.setOutputProperty(OutputKeys.ENCODING,"utf-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			bos = new ByteArrayOutputStream();
			dos = new DataOutputStream(bos);
			StreamResult result = new StreamResult(dos);
			transformer.transform(source, result);
			return new String(bos.toByteArray(),"utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(dos != null){
				try {
					dos.close();
				} catch (Exception e2) {}
			}
			if(bos != null){
				try {
					bos.close();
				} catch (Exception e2) {}
			}
		}
		return "";
	}
}
