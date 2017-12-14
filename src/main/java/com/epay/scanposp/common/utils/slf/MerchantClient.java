package com.epay.scanposp.common.utils.slf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import com.epay.scanposp.common.utils.EnvironmentUtil;
import com.epay.scanposp.common.utils.slf.vo.AccountRequest;
import com.epay.scanposp.common.utils.slf.vo.CertPayConfirmRequest;
import com.epay.scanposp.common.utils.slf.vo.CertPayConfirmResponse;
import com.epay.scanposp.common.utils.slf.vo.CertPayH5Request;
import com.epay.scanposp.common.utils.slf.vo.CertPayRequest;
import com.epay.scanposp.common.utils.slf.vo.CertPayResponse;
import com.epay.scanposp.common.utils.slf.vo.GuaranteeNoticeRequest;
import com.epay.scanposp.common.utils.slf.vo.GuaranteeNoticeResponse;
import com.epay.scanposp.common.utils.slf.vo.OrderRequest;
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
import com.sun.org.apache.xml.internal.security.utils.Base64;

/**
 * 交易处理类，包括下定单、交易查询、交易支付结果通知、退款、撤销订单、结算查询。
 * @author xmobo 
  */
public class MerchantClient {
	public static String configPath = "";
	/**
	 * 支付平台证书路径
	 */
	public static String transCertPath = "";
	/**
	 * 支付平台商户交易网址
	 */
	public static String payURL = "";
	/**
	 * 支付平台证书文件档名（由支付平台配置）
	 */
	public static String trustCertFile = "";
	/**
	 * 版本号
	 */
	public static String version = "";
	/**
	 * 商户编号
	 */
	public String merchantId = "";
	/**
	 * 商户代收付通知接口地址
	 */
	public static String receivePayNotifyUrl = "";
	/**
	 * 商户前台下单地址
	 */
	public static String merchantFrontEndUrl = "";
	/**
	 * 调试开关
	 */
	public static String debug = "0";
	/**
	 * 应用编码
	 */
	public static String encode = "utf-8";
	private static boolean firstLoad = false;
	/**
	 * 构造函数，载入证书和商户配置，一次性载入
	 */
	public MerchantClient(String merchantId) {
		this.merchantId = merchantId;
		if (!firstLoad) {
			try {
				configPath = new java.io.File(java.net.URLDecoder.decode(
						MerchantClient.class.getProtectionDomain().getCodeSource().getLocation().getFile(),
						encode)).getParentFile().getAbsolutePath()+ File.separator+"config"+File.separator;
				System.out.println(configPath);
				if(!new File(configPath).exists()){
					File file = new File((this.getClass().getClassLoader().getResource("").getPath()));
					String p = file.getParent();
					if(p.endsWith(File.separator))configPath = p+"classes"+File.separator+EnvironmentUtil.propertyPath+"slfkey"+File.separator;
					else configPath = p +File.separator+"classes"+File.separator+EnvironmentUtil.propertyPath+"slfkey"+File.separator;
					System.out.println(configPath);
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return; 
			}
			//载入配置文件
			loadConfigure();
			firstLoad = true;
		}
	}
	
	public MerchantClient() {
		if (!firstLoad) {
			try {
				configPath = new java.io.File(java.net.URLDecoder.decode(
						MerchantClient.class.getProtectionDomain().getCodeSource().getLocation().getFile(),
						encode)).getParentFile().getAbsolutePath()+ File.separator+"config"+File.separator;
				System.out.println(configPath);
				if(!new File(configPath).exists()){
					File file = new File((this.getClass().getClassLoader().getResource("").getPath()));
					String p = file.getParent();
					if(p.endsWith(File.separator))configPath = p+"classes"+File.separator+EnvironmentUtil.propertyPath+"slfkey"+File.separator;
					else configPath = p +File.separator+"classes"+File.separator+EnvironmentUtil.propertyPath+"slfkey"+File.separator;
					System.out.println(configPath);
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return; 
			}
			//载入配置文件
			loadConfigure();
			firstLoad = true;
		}
	}
	
	private static void loadConfigure() {
		try {
			ResourceBundle bundle = PropertyResourceBundle.getBundle(EnvironmentUtil.propertyPath + "slf_config");
			payURL = bundle.getString("PayURL");
			trustCertFile = bundle.getString("TrustCertFile");
			version = bundle.getString("Version");
			receivePayNotifyUrl = bundle.getString("ReceivePayNotifyUrl");
			merchantFrontEndUrl = bundle.getString("MerchantFrontEndUrl");
			debug = bundle.getString("Debug");
			transCertPath = configPath + trustCertFile;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		SecurityUtil.transCertPath = transCertPath;
		System.out.println();
	}
	/**
	 * 输出成员信息
	 */
	public String toString(){
		String temp = "";
		temp=temp+"payURL="+payURL+"\n";
		temp=temp+"trustCertFile="+trustCertFile+"\n";
		temp=temp+"merchantId="+merchantId+"\n";
		temp=temp+"merchantFrontEndUrl="+merchantFrontEndUrl+"\n";
		temp=temp+"transCertPath="+transCertPath+"\n";
		return temp;
	}
	/**
	 * 商户下单接口
	 * @param orderRequest 下单请求
	 * @return 订单响应
	 * @throws Exception
	 */
	public String sendOrderRequest(OrderRequest orderRequest) throws Exception {
		try {
			//if(merchantId.length() == 0)new MerchantClient();
			//校验订单请求
			checkOrderRequest(orderRequest);
			//发送请求
			MerchantXmlUtil merchantXmlUtil  = new MerchantXmlUtil();
			String xml = merchantXmlUtil.orderRequestToXml(orderRequest);
			if("1".equals(debug)) System.out.println("待加密入参:"+xml);
//			orderRequest.setXml(xml);
			try {
				return createTransStr(xml);
			} catch (Exception e) {
				if("1".equals(debug))e.printStackTrace();
				throw e;
			}
		} catch (Exception e) {
			if("1".equals(debug))e.printStackTrace();
			throw e;
		}
	}
	/**
	 * 快捷支付-创建订单
	 * @param certPayRequest 支付请求
	 * @return 
	 * @throws Exception
	 */
	public CertPayResponse sendCertPayRequest(CertPayRequest certPayRequest) throws Exception {
		try {
			//if(merchantId.length() == 0)new MerchantClient();
			//校验订单请求
			checkCertPayRequest(certPayRequest);
			//发送请求
			MerchantXmlUtil merchantXmlUtil  = new MerchantXmlUtil();
			String xml = merchantXmlUtil.certPayRequestToXml(certPayRequest);
			if("1".equals(debug)) System.out.println(xml);
			String responseXml = new String(new DataTransUtil().doPost(payURL,createTransStr(xml).getBytes(encode)));
			if("1".equals(debug))System.out.println("响应报文\n"+responseXml);
			String [] e = Tools.split(responseXml, "|");
			if(e.length !=2 )throw new Exception("响应报文【"+responseXml+"】错误");
			String responseSrc = new String(Base64.decode(e[0]),encode);
			if("1".equals(debug))System.out.println("xml报文\n"+responseSrc);
			//验签
			if (!verifyResult(responseSrc,e[1])) {
				throw new Exception("验证签名失败");
			}
			CertPayResponse response = merchantXmlUtil.xmlToCertPayResponse(responseSrc);
			response.setXml(responseSrc);
			return response;
		} catch (Exception e) {
			if("1".equals(debug))e.printStackTrace();
			throw e;
		}
	}
	/**
	 * 微信扫码/微信WAP/支付宝扫码支付-创建订单
	 * @param payRequest 支付请求
	 * @return 
	 * @throws Exception
	 */
	public WeiXinScanResponse sendWeiXinScanRequest(WeiXinScanRequest payRequest) throws Exception {
		try {
			//if(merchantId.length() == 0)new MerchantClient();
			//校验订单请求
			checkWeiXinScanRequest(payRequest);
			//发送请求
			MerchantXmlUtil merchantXmlUtil  = new MerchantXmlUtil();
			String xml = merchantXmlUtil.weiXinScanRequestToXml(payRequest);
			if("1".equals(debug)) System.out.println(xml);
			String responseXml = new String(new DataTransUtil().doPost(payURL,createTransStr(xml).getBytes(encode)));
			if("1".equals(debug))System.out.println("响应报文\n"+responseXml);
			String [] e = Tools.split(responseXml, "|");
			if(e.length !=2 )throw new Exception("响应报文【"+responseXml+"】错误");
			String responseSrc = new String(Base64.decode(e[0]),encode);
			if("1".equals(debug))System.out.println("xml报文\n"+responseSrc);
			//验签
			if (!verifyResult(responseSrc,e[1])) {
				throw new Exception("验证签名失败");
			}
			WeiXinScanResponse response = merchantXmlUtil.xmlToWeiXinScanResponse(responseSrc);
			response.setXml(responseSrc);
			return response;
		} catch (Exception e) {
			if("1".equals(debug))e.printStackTrace();
			throw e;
		}
	}
	/**
	 * 快捷支付-H5下单接口
	 * @param orderRequest 下单请求
	 * @return 订单响应
	 * @throws Exception
	 */
	public String sendCertPayH5Request(CertPayH5Request certPayH5Request) throws Exception {
		try {
			//if(merchantId.length() == 0)new MerchantClient();
			//校验订单请求
			checkCertPayH5Request(certPayH5Request);
			//发送请求
			MerchantXmlUtil merchantXmlUtil  = new MerchantXmlUtil();
			String xml = merchantXmlUtil.certPayH5RequestToXml(certPayH5Request);
			if("1".equals(debug)) System.out.println(xml);
			try {
				return createTransStr(xml);
			} catch (Exception e) {
				if("1".equals(debug))e.printStackTrace();
				throw e;
			}
		} catch (Exception e) {
			if("1".equals(debug))e.printStackTrace();
			throw e;
		}
	}
	/**
	 * 快捷支付支付确认
	 * @param confirmRequest 确认请求
	 * @return 支付加密数据
	 * @throws Exception
	 */
	public CertPayConfirmResponse sendCertPayConfirmRequest(CertPayConfirmRequest confirmRequest) throws Exception {
		try {
			//if(merchantId.length() == 0)new MerchantClient();
			//校验订单请求
			checkCertPayConfirmRequest(confirmRequest);
			//发送请求
			MerchantXmlUtil merchantXmlUtil  = new MerchantXmlUtil();
			String xml = merchantXmlUtil.certPayConfirmRequestToXml(confirmRequest);
			if("1".equals(debug)) System.out.println(xml);
			String responseXml = new String(new DataTransUtil().doPost(payURL,createTransStr(xml).getBytes(encode)));
			if("1".equals(debug))System.out.println("响应报文\n"+responseXml);
			String [] e = Tools.split(responseXml, "|");
			if(e.length !=2 )throw new Exception("响应报文【"+responseXml+"】错误");
			String responseSrc = new String(Base64.decode(e[0]),encode);
			if("1".equals(debug))System.out.println("xml报文\n"+responseSrc);
			//验签
			if (!verifyResult(responseSrc,e[1])) {
				throw new Exception("验证签名失败");
			}
			CertPayConfirmResponse response = merchantXmlUtil.xmlToCertPayConfirmResponse(responseSrc);
			response.setXml(responseSrc);
			return response;
		} catch (Exception e) {
			if("1".equals(debug))e.printStackTrace();
			throw e;
		}
	}
	/**
	 * 商户退款接口
	 * @param orderRequest 退款请求
	 * @return 退款响应
	 * @throws Exception
	 */
	public RefundResponse sendRefundRequest(RefundRequest refundRequest) throws Exception {
		try {
			//校验订单请求
			checkRefundRequest(refundRequest);
			//发送请求
			MerchantXmlUtil merchantXmlUtil  = new MerchantXmlUtil();
			String xml = merchantXmlUtil.refundRequestToXml(refundRequest);
			if("1".equals(debug))System.out.println(xml);
//			refundRequest.setXml(xml);
			String responseXml = new String(new DataTransUtil().doPost(payURL,createTransStr(xml).getBytes(encode)));
			if("1".equals(debug))System.out.println("响应报文\n"+responseXml);
			String [] e = Tools.split(responseXml, "|");
			if(e.length !=2 )throw new Exception("响应报文【"+responseXml+"】错误");
			String responseSrc = new String(Base64.decode(e[0]),encode);
			if("1".equals(debug))System.out.println("xml报文\n"+responseSrc);
			//验签
			if (!verifyResult(responseSrc,e[1])) {
				throw new Exception("验证签名失败");
			}
			RefundResponse response = merchantXmlUtil.xmlToRefundResponse(responseSrc);
			response.setXml(responseSrc);
			return response;
		} catch (Exception e) {
			if("1".equals(debug))e.printStackTrace();
			throw e;
		}
	}
//	/**
//	 * 撤销订单接口
//	 * @param orderRequest 撤销订单请求
//	 * @return 撤销订单响应
//	 * @throws Exception
//	 */
//	public CancelResponse sendCancelRequest(CancelRequest cancelRequest) throws Exception {
//		try {
//			//校验订单请求
//			checkCancelRequest(cancelRequest);
//			//发送请求
//			MerchantXmlUtil merchantXmlUtil  = new MerchantXmlUtil();
//			String xml = merchantXmlUtil.cancelRequestToXml(cancelRequest);
//			if("1".equals(debug))System.out.println(xml);
////			cancelRequest.setXml(xml);
//			String responseXml = new String(new DataTransUtil().doPost(payURL,
//					createTransStr(xml).getBytes(encode)));
//			if("1".equals(debug))System.out.println("响应报文\n"+responseXml);
//			String [] e = Tools.split(responseXml, "|");
//			if(e.length !=2 )throw new Exception("响应报文【"+responseXml+"】错误");
//			String responseSrc = new String(Base64.decode(e[0]),encode);
//			if("1".equals(debug))System.out.println("xml报文\n"+responseSrc);
//			//验签
//			if (!verifyResult(responseSrc,e[1])) {
//				throw new Exception("验证签名失败");
//			}
//			CancelResponse response = merchantXmlUtil.xmlToCancelResponse(responseSrc);
//			return response;
//		} catch (Exception e) {
//			if("1".equals(debug))e.printStackTrace();
//			throw e;
//		}
//	}
	/**
	 * 对账文件下载接口（对账信息写入到临时文件）
	 * @param accountRequest 对账文件请求
	 * @param accountTmpFile 对账临时文件
	 * @throws Exception
	 */
	public void sendAccountRequest(AccountRequest accountRequest,String accountTmpFile) throws Exception {
		try {
			//校验订单请求
			checkAccountRequest(accountRequest);
			//发送请求
			MerchantXmlUtil merchantXmlUtil  = new MerchantXmlUtil();
			String xml = merchantXmlUtil.accountRequestToXml(accountRequest);
			if("1".equals(debug))System.out.println(xml);
//			accountRequest.setXml(xml);
			new DataTransUtil().doPost(payURL,createTransStr(xml).getBytes(encode),
				new FileOutputStream(accountTmpFile));
		} catch (Exception e) {
			if("1".equals(debug))e.printStackTrace();
			throw e;
		}
	}
	/**
	 * 担保通知接口
	 * @param guaranteeNoticeRequest 担保通知请求
	 * @return 担保通知响应
	 * @throws Exception
	 */
	public GuaranteeNoticeResponse sendGuaranteeNoticeRequest(
			GuaranteeNoticeRequest guaranteeNoticeRequest) throws Exception {
		try {
			//校验请求
			checkGuaranteeNoticeRequest(guaranteeNoticeRequest);
			//发送请求
			MerchantXmlUtil merchantXmlUtil  = new MerchantXmlUtil();
			String xml = merchantXmlUtil.guaranteeNoticeRequestToXml(guaranteeNoticeRequest);
			if("1".equals(debug))System.out.println(xml);
//			refundRequest.setXml(xml);
			String responseXml = new String(new DataTransUtil().doPost(payURL,createTransStr(xml).getBytes(encode)));
			if("1".equals(debug))System.out.println("响应报文\n"+responseXml);
			String [] e = Tools.split(responseXml, "|");
			if(e.length !=2 )throw new Exception("响应报文【"+responseXml+"】错误");
			String responseSrc = new String(Base64.decode(e[0]),encode);
			if("1".equals(debug))System.out.println("xml报文\n"+responseSrc);
			//验签
			if (!verifyResult(responseSrc,e[1])) {
				throw new Exception("验证签名失败");
			}
			GuaranteeNoticeResponse response = merchantXmlUtil.xmlToGuaranteeNoticeResponse(responseSrc);
			response.setXml(responseSrc);
			return response;
		} catch (Exception e) {
			if("1".equals(debug))e.printStackTrace();
			throw e;
		}
	}
	/**
	 * 查询订单接口
	 * @param orderRequest 查询订单请求
	 * @return 查询订单响应
	 * @throws Exception
	 */
	public QueryResponse sendQueryRequest(QueryRequest queryRequest) throws Exception {
		try {
			//校验订单请求
			checkQueryRequest(queryRequest);
			//发送请求
			MerchantXmlUtil merchantXmlUtil  = new MerchantXmlUtil();
			String xml = merchantXmlUtil.queryRequestToXml(queryRequest);
			if("1".equals(debug))System.out.println(xml);
//			queryRequest.setXml(xml);
			String responseXml = new String(new DataTransUtil().doPost(payURL,
					createTransStr(xml).getBytes(encode)),encode);
			if("1".equals(debug))System.out.println("响应报文\n"+responseXml);
			String [] e = Tools.split(responseXml, "|");
			if(e.length !=2 )throw new Exception("响应报文【"+responseXml+"】错误");
			String responseSrc = new String(Base64.decode(e[0]),encode);
			if("1".equals(debug))System.out.println("xml报文\n"+responseSrc);
			//验签
			if (!verifyResult(responseSrc,e[1])) {
				throw new Exception("验证签名失败");
			}
			QueryResponse response = merchantXmlUtil.xmlToQueryResponse(responseSrc);
			response.setXml(responseSrc);
			return response;
		} catch (Exception e) {
			if("1".equals(debug))e.printStackTrace();
			throw e;
		}
	}
	/**
	 * 支付通知结果解析
	 * @param notifyResultStr 通知字符串
	 * @return 通知结果
	 * @throws Exception
	 */
	public PaymentNotifyResponse parsePaymentNotify(String notifyResultStr) throws Exception {
		if("1".equals(debug))System.out.println("通知报文\n"+notifyResultStr);
		String [] e = Tools.split(notifyResultStr, "|");
		String responseSrc = e[0];
		//验签
		if(e.length == 2){
			responseSrc = new String(Base64.decode(e[0]),encode);
			if("1".equals(debug))System.out.println("xml报文\n"+responseSrc);
			if (!verifyResult(responseSrc,e[1])) throw new Exception("验证签名失败");
		}
		MerchantXmlUtil merchantXmlUtil  = new MerchantXmlUtil();
		PaymentNotifyResponse response = null;
		response = merchantXmlUtil.xmlToPaymentNotifyResponse(responseSrc);
		return response;
	}
	/**
	 * 代收付结果解析
	 * @param notifyResultStr 通知字符串
	 * @return 通知结果
	 * @throws Exception
	 */
	public ReceivePayResponse parseReceivePayNotify(String notifyResultStr) throws Exception {
		if("1".equals(debug))System.out.println("通知报文\n"+notifyResultStr);
		String [] e = Tools.split(notifyResultStr, "|");
		String responseSrc = e[0];
		//验签
		if(e.length == 2){
			responseSrc = new String(Base64.decode(e[0]),encode);
			if("1".equals(debug))System.out.println("xml报文\n"+responseSrc);
			if (!verifyResult(responseSrc,e[1])) throw new Exception("验证签名失败");
		}
		MerchantXmlUtil merchantXmlUtil  = new MerchantXmlUtil();
		ReceivePayResponse response = null;
		response = merchantXmlUtil.xmlToReceivePayNotifyResponse(responseSrc);
		return response;
	}
	
	/**
	 * 银行卡实名认证 
	 * @param authRequest 认证参数
	 * @return 返回认证响应报文
	 */
	public ReceivePayResponse realNameAuth(RealNameAuthRequest authRequest) throws Exception {
		checkRealNameAuthRequst(authRequest);
		MerchantXmlUtil merchantXmlUtil  = new MerchantXmlUtil();
		String xml = merchantXmlUtil.realNameAuthRequestToXml(authRequest);
		if("1".equals(debug))System.out.println(xml);
		authRequest.setXml(xml);
		try {
			String responseXml = new String(new DataTransUtil().doPost(payURL,createTransStr(xml).getBytes(encode)));
			if("1".equals(debug))System.out.println("响应报文\n"+responseXml);
			String [] e = Tools.split(responseXml, "|");
			if(e.length !=2 )throw new Exception("响应报文【"+responseXml+"】错误");
			String responseSrc = new String(Base64.decode(e[0]),encode);
			if("1".equals(debug))System.out.println("xml报文\n"+responseSrc);
			if (!verifyResult(responseSrc,e[1])) {
				throw new Exception("验证签名失败");
			}
			ReceivePayResponse response = merchantXmlUtil.xmlToRealNameAuthResponse(responseSrc);
			response.setXml(responseSrc);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 单笔收付款 
	 * @param receivePayRequest 请求参数
	 * @return 返回响应报文
	 */
	public ReceivePayResponse receivePayRequest(ReceivePayRequest receivePayRequest) throws Exception {
		checkReceivePayRequest(receivePayRequest);
		MerchantXmlUtil merchantXmlUtil  = new MerchantXmlUtil();
		String xml = merchantXmlUtil.receivePayRequestToXml(receivePayRequest);
		if("1".equals(debug))System.out.println(xml);
		receivePayRequest.setXml(xml);
		try {
			String responseXml = new String(new DataTransUtil().doPost(payURL,createTransStr(xml).getBytes(encode)));
			if("1".equals(debug))System.out.println("响应报文\n"+responseXml);
			String [] e = Tools.split(responseXml, "|");
			if(e.length !=2 )throw new Exception("响应报文【"+responseXml+"】错误");
			String responseSrc = new String(Base64.decode(e[0]),encode);
			if("1".equals(debug))System.out.println("xml报文\n"+responseSrc);
			if (!verifyResult(responseSrc,e[1])) {
				throw new Exception("验证签名失败");
			}
			ReceivePayResponse response = merchantXmlUtil.xmlToReceivePayResponse(responseSrc);
			response.setXml(responseSrc);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 批量收付款 
	 * @param certPayRequest 请求参数
	 * @return 返回响应报文
	 */
	public ReceivePayBatchResponse receivePayBatchRequest(ReceivePayBatchRequest receivePayBatchRequest) throws Exception {
		checkReceiveBatchPayRequest(receivePayBatchRequest);
		MerchantXmlUtil merchantXmlUtil  = new MerchantXmlUtil();
		String xml = merchantXmlUtil.receivePayBatchRequestToXml(receivePayBatchRequest);
		if("1".equals(debug))System.out.println(xml);
		receivePayBatchRequest.setXml(xml);
		try {
			String responseXml = new String(new DataTransUtil().doPost(payURL,createTransStr(xml).getBytes(encode)));
			if("1".equals(debug))System.out.println("响应报文\n"+responseXml);
			String [] e = Tools.split(responseXml, "|");
			if(e.length !=2 )throw new Exception("响应报文【"+responseXml+"】错误");
			String responseSrc = new String(Base64.decode(e[0]),encode);
			if("1".equals(debug))System.out.println("xml报文\n"+responseSrc);
			if (!verifyResult(responseSrc,e[1])) {
				throw new Exception("验证签名失败");
			}
			ReceivePayBatchResponse response = merchantXmlUtil.xmlToReceivePayBatchResponse(responseSrc);
			response.setXml(responseSrc);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 收付款查询
	 * @param queryResponse
	 * @return
	 */
	public ReceivePayQueryResponse receivePayQuery(ReceivePayQueryRequest queryrRequest)throws Exception {
		checkReceivePayQueryRequest(queryrRequest);
		MerchantXmlUtil merchantXmlUtil  = new MerchantXmlUtil();
		String xml = merchantXmlUtil.receivePayQueryRequestToXml(queryrRequest);
		if("1".equals(debug))System.out.println(xml);
		queryrRequest.setXml(xml);
		try {
			String responseXml = new String(new DataTransUtil().doPost(payURL,createTransStr(xml).getBytes(encode)));
			if("1".equals(debug))System.out.println("响应报文\n"+responseXml);
			String [] e = Tools.split(responseXml, "|");
			if(e.length !=2 )throw new Exception("响应报文【"+responseXml+"】错误");
			String responseSrc = new String(Base64.decode(e[0]),encode);
			if("1".equals(debug))System.out.println("xml报文\n"+responseSrc);
			if (!verifyResult(responseSrc,e[1])) {
				throw new Exception("验证签名失败");
			}
			ReceivePayQueryResponse response = merchantXmlUtil.xmlToReceivePayQueryResponse(responseSrc);
			response.setXml(responseSrc);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	/**
	 * 校验实名认证请求参数是否合法 
	 * @param authRequest 认证请求参数
	 * @throws Exception 
	 */
	private void checkRealNameAuthRequst(RealNameAuthRequest request) throws Exception {
		if(request.getMerchantId().length() == 0 || request.getMerchantId().length() > 24) 
			throw new Exception("商户代码非法");
		if(request.getMerchantId().length() == 0 || request.getMerchantId().length() > 24)
			throw new Exception("商户代码非法");
		if(request.getTranId().length() == 0 || request.getTranId().length() > 20||request.getTranId().indexOf('_')>=0)
			throw new Exception("交易请求号非法(不能包含字符\"_\")");
		try {
			Long.parseLong(request.getTimestamp());
			new SimpleDateFormat("yyyyMMddHHmmss").parse(request.getTimestamp());
			if(request.getTimestamp().length() != 14)throw new Exception();
		} catch (Exception e) {throw new Exception("订单时间非法");}
		
		List<ReceivePay> authList = request.getAuthList();
		if(authList == null || authList.size() == 0)throw new Exception("认证列表非法");
		for (int i = 0; i < authList.size(); i++) {
			ReceivePay receivePay = authList.get(i);
			if(receivePay.getSn().length() == 0 || receivePay.getSn().length() > 50
					||request.getTranId().indexOf('_')>=0)throw new Exception("明细号非法(不能包含字符\"_\")");
			if(receivePay.getAccNo().length() == 0 || receivePay.getAccNo().length() > 50)throw new Exception("账号非法");
			if(receivePay.getAccName().length() == 0 || receivePay.getAccName().length() > 60)throw new Exception("账户名称非法");
			if(receivePay.getCredentialType().length() != 2)throw new Exception("开户证件类型非法");
			if(receivePay.getCredentialNo().length() < 10 || receivePay.getCredentialNo().length() > 22)throw new Exception("证件号非法");
			try {
				if(receivePay.getTel().length() != 11 || !receivePay.getTel().startsWith("1"))throw new Exception();
				Long.parseLong(receivePay.getTel());
			}catch(Exception e) {
				e.printStackTrace();
				throw new Exception("手机号非法");
			}
		}
	}
	
	/**
	 * 单笔收付款校验
	 * @param certPayRequest
	 */
	private void checkReceivePayRequest(ReceivePayRequest request) throws Exception {
		if(request.getMerchantId().length() == 0 || request.getMerchantId().length() > 24)throw new Exception("商户代码非法");
		if(request.getTranId().length() == 0 || request.getTranId().length() > 32
				||request.getTranId().indexOf('_')>=0)throw new Exception("交易请求号非法(不能包含字符\"_\")");
		try {
			if(request.getReceivePayNotifyUrl().length() == 0)request.setReceivePayNotifyUrl(receivePayNotifyUrl);				
			if(request.getReceivePayNotifyUrl().length() == 0 
				|| request.getReceivePayNotifyUrl().length() > 200
				|| !request.getReceivePayNotifyUrl().toLowerCase().trim().startsWith("http")) throw new Exception();
		} catch (Exception e) {throw new Exception("收付款通知地址非法");}
		if(!"0".equals(request.getReceivePayType())&&!"1".equals(request.getReceivePayType()))throw new Exception("代收付类型非法");
		try {
			Long.parseLong(request.getTimestamp());
			new SimpleDateFormat("yyyyMMddHHmmss").parse(request.getTimestamp());
			if(request.getTimestamp().length() != 14)throw new Exception();
		} catch (Exception e) {throw new Exception("时间戳非法");}
		if(!"0".equals(request.getAccountProp())
				&&!"4".equals(request.getAccountProp())) throw new Exception("对公对私非法");
		if(request.getBankGeneralName().length() == 0 
				|| request.getBankGeneralName().length() > 60)throw new Exception("银行名称非法");
		if(request.getAccNo().length() == 0 
				|| request.getAccNo().length() > 32)throw new Exception("账号非法");
		if(request.getAccName().length() == 0 
				|| request.getAccName().length() > 60)throw new Exception("账户名称非法");
		try {
			long amount = Long.parseLong(request.getAmount());
			if(amount <= 0 || amount > 100000000000l)throw new Exception();
		} catch (Exception e) {throw new Exception("订单金额非法");}
		if("0".equals(request.getAccountProp())&&request.getCredentialType().length() != 2) throw new Exception("证件类型非法");
		if("0".equals(request.getAccountProp())&&(request.getCredentialNo().length() == 0 
				|| request.getCredentialNo().length() > 22)) throw new Exception("证件号非法");
		try {
			if("0".equals(request.getAccountProp())){
				if(request.getTel().length() != 11 
					|| !request.getTel().startsWith("1"))throw new Exception();
				Long.parseLong(request.getTel());
			}
		} catch(Exception e) {
			e.printStackTrace();
			throw new Exception("手机号非法");
		}
	//	if(request.getSummary().length() > 256) throw new Exception("备注非法");
	}
	
	/**
	 * 批量代收款参数校验
	 * @param receivePayBatchRequest
	 */
	private void checkReceiveBatchPayRequest(ReceivePayBatchRequest request)throws Exception {
		if(request.getMerchantId().length() == 0 
				|| request.getMerchantId().length() > 24) throw new Exception("商户代码非法");
		if(request.getTranId().length() == 0 
				|| request.getTranId().length() > 32)throw new Exception("交易请求号非法");
		try {
			if(request.getReceivePayNotifyUrl().length() == 0) request.setReceivePayNotifyUrl(receivePayNotifyUrl);				
			if(request.getReceivePayNotifyUrl().length() == 0 
				|| request.getReceivePayNotifyUrl().length() > 200
				|| !request.getReceivePayNotifyUrl().toLowerCase().trim().startsWith("http")) throw new Exception();
		} catch (Exception e) {throw new Exception("收付款通知地址非法");}
		try {
			Long.parseLong(request.getTimestamp());
			new SimpleDateFormat("yyyyMMddHHmmss").parse(request.getTimestamp());
			if(request.getTimestamp().length() != 14)throw new Exception();
		} catch (Exception e) {throw new Exception("订单时间非法");}
		if(request.getAccountProp().length() != 1) throw new Exception("对公对私非法");
		List<ReceivePay> tranList = request.getTranList();
		if(tranList == null || tranList.size() == 0) throw new Exception("认证列表非法");
		for (int i = 0; i < tranList.size(); i++) {
			ReceivePay receivePay = tranList.get(i);
			if(receivePay.getSn().length() == 0 || receivePay.getSn().length() > 50)throw new Exception("明细号非法");
			if(receivePay.getAccNo().length() == 0 
					|| receivePay.getAccNo().length() > 50)throw new Exception("账号非法");
			if(receivePay.getAccName().length() == 0 
					|| receivePay.getAccName().length() > 60)throw new Exception("账户名称非法");
			try {
				long amount = Long.parseLong(receivePay.getAmount());
				if(amount <= 0 || amount > 100000000000l)throw new Exception();
			} catch (Exception e) {throw new Exception("订单金额非法");}
			if("0".equals(request.getAccountProp())&&receivePay.getCredentialType().length() != 2) throw new Exception("开户证件类型非法");
			if("0".equals(request.getAccountProp())&&(receivePay.getCredentialNo().length()==0
					||receivePay.getCredentialNo().length() > 22)) throw new Exception("证件号非法");
			try {
				if("0".equals(request.getAccountProp())&&receivePay.getTel().length() > 0) {
					if(receivePay.getTel().length() != 11 || !receivePay.getTel().startsWith("1"))throw new Exception();
					Long.parseLong(receivePay.getTel());
				}
			} catch(Exception e) {
				e.printStackTrace();
				throw new Exception("手机号非法");
			}
			if(receivePay.getSummary().getBytes("utf-8").length > 256)throw new Exception("备注非法");
		}
	}
	
	/**
	 * 校验收付款查询
	 * @param queryrRequest
	 * @throws Exception 
	 */
	private void checkReceivePayQueryRequest(ReceivePayQueryRequest queryrRequest) throws Exception {
		if(queryrRequest.getMerchantId().length() == 0 || queryrRequest.getMerchantId().length() > 24)
			throw new Exception("商户代码非法");
		try {
			Long.parseLong(queryrRequest.getTimestamp());
			new SimpleDateFormat("yyyyMMddHHmmss").parse(queryrRequest.getTimestamp());
			if(queryrRequest.getTimestamp().length() != 14)throw new Exception();
		} catch (Exception e) {throw new Exception("订单时间非法");}
		if(queryrRequest.getQueryTranId().length() == 0 || queryrRequest.getQueryTranId().length() > 32)
			throw new Exception("查询请求号非法");
	}
	
	/**
	 * 发送信息拼接（原串+签名串）
	 * @param src
	 * @return
	 * @throws Exception
	 */
	public static String createTransStr(String src) throws Exception {
		String xmlBase64Sign = new String(Base64.encode(
				SecurityUtil.signWithRSA(MD5.getDigest(src.getBytes(encode)))));
		String xmlBase64 = new String(Base64.encode(src.getBytes(encode)));
		return xmlBase64+"|"+xmlBase64Sign;
	}
	/**
	 * 验证签名
	 * @param src
	 * @param sign
	 * @return
	 * @throws Exception
	 */
	public static boolean verifyResult(String src,String sign) throws Exception {
		return SecurityUtil.verifyWithRSA(MD5.getDigest(src.getBytes(encode)),
			Base64.decode(sign.getBytes(encode)));
	}
	
	/**
	 * 验证下单请求
	 * @param request
	 * @throws Exception
	 */
	private void checkOrderRequest(OrderRequest request) throws Exception {
		if(request.getMerchantId() ==null ||request.getMerchantId().length()==0
			||request.getMerchantId().length() > 24)throw new Exception("商户代码非法");
		if(request.getMerchantName().trim().length() > 0
			&& request.getMerchantName().getBytes(encode).length > 24){
			throw new Exception("商户名称非法");
		}
		if(request.getMerchantOrderId().getBytes(encode).length == 0 
				|| request.getMerchantOrderId().getBytes(encode).length > 32){
			throw new Exception("订单号非法");
		}
		try {
			long merchantOrderAmt = Long.parseLong(request.getMerchantOrderAmt());
			if(merchantOrderAmt <= 0 || merchantOrderAmt > 100000000000l)throw new Exception();
		} catch (Exception e) {throw new Exception("订单金额非法");}
		if(request.getMerchantOrderDesc().length() > 0 
			&& request.getMerchantOrderDesc().getBytes(encode).length > 128){
			throw new Exception("订单描述非法");
		}
		try {
			if(request.getMerchantPayNotifyUrl().length() == 0 
				|| request.getMerchantPayNotifyUrl().length() > 200
				|| !request.getMerchantPayNotifyUrl().toLowerCase().trim().startsWith("http"))
				throw new Exception();
		} catch (Exception e) {throw new Exception("异步通知地址");}
		try {
			if(request.getMerchantFrontEndUrl().length() == 0) {
				request.setMerchantFrontEndUrl(merchantFrontEndUrl);				
			}
			if(request.getMerchantFrontEndUrl().length() > 200
				|| (request.getMerchantFrontEndUrl().length() !=0 && 
				!request.getMerchantFrontEndUrl().toLowerCase().trim().startsWith("http")))
				throw new Exception();
		} catch (Exception e) {throw new Exception("商户前台地址非法");}
		try {			
			String m = request.getUserMobileNo().trim();
			if(m.length() > 0){
				new Long(m);
				if(m.length() != 11 || !m.startsWith("1"))throw new Exception();
			}
		} catch (Exception e) {throw new Exception("手机号码非法");}
		if(request.getCredentialType().length() > 0){
			if(Constant.credentialTypeMap.get(request.getCredentialType())==null)throw new Exception("证件类型非法");
			if(request.getCredentialNo().length()==0
				||request.getCredentialNo().length()>20)throw new Exception("证件号码非法");
		}
		if(request.getPayerId().length() > 0 
			&& request.getPayerId().getBytes(encode).length > 100){
			throw new Exception("买家ID非法");
		}
		if(request.getUserName().length() > 0 
				&& request.getUserName().getBytes(encode).length > 50){
			throw new Exception("用户姓名非法");
		}
		if(request.getSalerId().length() > 0 
			&& request.getSalerId().getBytes(encode).length > 50){
			throw new Exception("卖家ID非法");
		}
		try {
			long guaranteeAmt = Long.parseLong(request.getGuaranteeAmt());
			if(guaranteeAmt < 0 || guaranteeAmt > 999999999999l)throw new Exception();
		} catch (Exception e) {request.setGuaranteeAmt("0");}
		if(!"1".equals(request.getUserType())&&!"2".equals(request.getUserType())){
			request.setUserType("1");
		}
		if(!"0".equals(request.getAccountType())&&!"1".equals(request.getAccountType())
				&&!"4".equals(request.getAccountType()))throw new Exception("卡种非法");
		else {
			if("1".equals(request.getUserType())){
				if("4".equals(request.getAccountType())) throw new Exception("卡种非法");
			} else {
				if(!"4".equals(request.getAccountType()))throw new Exception("卡种非法");
			}
		}
		if(request.getBankId().length() > 0 
			&& request.getBankId().getBytes(encode).length > 10){
			throw new Exception("银行编码非法");
		}
		if(request.getMsgExt().length() > 0 
			&& request.getMsgExt().getBytes(encode).length > 128){
			throw new Exception("附加信息非法");
		}
		try {
			Long.parseLong(request.getOrderTime());
			new java.text.SimpleDateFormat("yyyyMMddHHmmss").parse(request.getOrderTime());
			if(request.getOrderTime().length() != 14)throw new Exception();
		} catch (Exception e) {throw new Exception("订单时间非法");}
		if(request.getBizType().length() > 0){
			if(Constant.bizTypeMap.get(request.getBizType())==null)throw new Exception("商户业务类型非法");
		}
		if(!"1".equals(request.getRptType())){
			throw new Exception("收款方式非法");
		}
		if(!"0".equals(request.getPayMode())&&!"1".equals(request.getPayMode())){
			throw new Exception("付款类型非法");
		}
	}
	private void checkCertPayRequest(CertPayRequest request) throws Exception {
		if(request.getMerchantId() ==null ||request.getMerchantId().length()==0
			||request.getMerchantId().length() > 24)throw new Exception("商户代码非法");
		if(request.getMerchantOrderId().getBytes(encode).length == 0 
				|| request.getMerchantOrderId().getBytes(encode).length > 32){
			throw new Exception("订单号非法");
		}
		try {
			long merchantOrderAmt = Long.parseLong(request.getMerchantOrderAmt());
			if(merchantOrderAmt <= 0 || merchantOrderAmt > 100000000000l)throw new Exception();
		} catch (Exception e) {throw new Exception("订单金额非法");}
		if(request.getMerchantOrderDesc().length() > 0 
			&& request.getMerchantOrderDesc().getBytes(encode).length > 128){
			throw new Exception("订单描述非法");
		}
		try {
			if(request.getMerchantPayNotifyUrl().length() == 0 
				|| request.getMerchantPayNotifyUrl().length() > 200
				|| !request.getMerchantPayNotifyUrl().toLowerCase().trim().startsWith("http"))
				throw new Exception();
		} catch (Exception e) {throw new Exception("异步通知地址");}
		try {
			if(request.getMerchantFrontEndUrl().length() == 0) {
				request.setMerchantFrontEndUrl(merchantFrontEndUrl);				
			}
			if(request.getMerchantFrontEndUrl().length() > 200
				|| (request.getMerchantFrontEndUrl().length() !=0 && 
				!request.getMerchantFrontEndUrl().toLowerCase().trim().startsWith("http")))
				throw new Exception();
		} catch (Exception e) {throw new Exception("商户前台地址非法");}
		if(request.getPayerId().length() > 0 
				&& request.getPayerId().getBytes(encode).length > 100){
			throw new Exception("买家ID非法");
		}
		if(request.getUserName().length() > 0 
				&& request.getUserName().getBytes(encode).length > 50){
			throw new Exception("用户姓名非法");
		}
		if(request.getSalerId().length() > 0 
				&& request.getSalerId().getBytes(encode).length > 50){
			throw new Exception("卖家ID非法");
		}
		try {
			long guaranteeAmt = Long.parseLong(request.getGuaranteeAmt());
			if(guaranteeAmt < 0 || guaranteeAmt > 999999999999l)throw new Exception();
		} catch (Exception e) {request.setGuaranteeAmt("0");}
		if(Constant.credentialTypeMap.get(request.getCredentialType())==null)throw new Exception("证件类型非法");
		if(request.getCredentialNo().length()==0
			||request.getCredentialNo().length()>20)throw new Exception("证件号码非法");
		try {			
			String m = request.getUserMobileNo().trim();
			if(m.length() > 0){
				new Long(m);
				if(m.length() != 11 || !m.startsWith("1"))throw new Exception();
			}
		} catch (Exception e) {throw new Exception("手机号码非法");}
		if(request.getCardNo().length() > 0 
				&& request.getCardNo().getBytes(encode).length > 25){
				throw new Exception("银行卡号非法");
		}
		if(request.getCvv2().length() > 0 
				&& request.getCvv2().length() != 3){
				throw new Exception("信用卡背面的3位数字非法");
		}
		if(request.getValidPeriod().length() > 0 
				&& request.getValidPeriod().length() >8){
				throw new Exception("信用卡有效期非法");
		}
	}
	private void checkWeiXinScanRequest(WeiXinScanRequest request) throws Exception {
		if(request.getMerchantId() ==null ||request.getMerchantId().length()==0
			||request.getMerchantId().length() > 24)throw new Exception("商户代码非法");
		if(request.getMerchantOrderId().getBytes(encode).length == 0 
				|| request.getMerchantOrderId().getBytes(encode).length > 32){
			throw new Exception("订单号非法");
		}
		try {
			long merchantOrderAmt = Long.parseLong(request.getMerchantOrderAmt());
			if(merchantOrderAmt <= 0 || merchantOrderAmt > 100000000000l)throw new Exception();
		} catch (Exception e) {throw new Exception("订单金额非法");}
		if(request.getMerchantOrderDesc().length() > 0 
			&& request.getMerchantOrderDesc().getBytes(encode).length > 128){
			throw new Exception("订单描述非法");
		}
		try {
			if(request.getMerchantPayNotifyUrl().length() == 0 
				|| request.getMerchantPayNotifyUrl().length() > 200
				|| !request.getMerchantPayNotifyUrl().toLowerCase().trim().startsWith("http"))
				throw new Exception();
		} catch (Exception e) {throw new Exception("异步通知地址");}
		if(request.getUserName().length() > 0 
				&& request.getUserName().getBytes(encode).length > 50){
			throw new Exception("用户名非法");
		}
		if(request.getPayerId().length() > 0 
				&& request.getPayerId().getBytes(encode).length > 50){
			throw new Exception("买家ID非法");
		}
		if(request.getSalerId().length() > 0 
				&& request.getSalerId().getBytes(encode).length > 50){
			throw new Exception("卖家ID非法");
		}
		try {
			long guaranteeAmt = Long.parseLong(request.getGuaranteeAmt());
			if(guaranteeAmt < 0 || guaranteeAmt > 999999999999l)throw new Exception();
		} catch (Exception e) {request.setGuaranteeAmt("0");}
	}
	private void checkCertPayH5Request(CertPayH5Request request) throws Exception {
		if(request.getMerchantId() ==null ||request.getMerchantId().length()==0
			||request.getMerchantId().length() > 24)throw new Exception("商户代码非法");
		if(request.getMerchantOrderId().getBytes(encode).length == 0 
				|| request.getMerchantOrderId().getBytes(encode).length > 32){
			throw new Exception("订单号非法");
		}
		if(request.getMerchantName().length() > 0 
			&& request.getMerchantName().getBytes(encode).length > 24){
			throw new Exception("商户名称");
		}
		try {
			long merchantOrderAmt = Long.parseLong(request.getMerchantOrderAmt());
			if(merchantOrderAmt <= 0 || merchantOrderAmt > 100000000000l)throw new Exception();
		} catch (Exception e) {throw new Exception("订单金额非法");}
		if(request.getMerchantOrderDesc().length() > 0 
			&& request.getMerchantOrderDesc().getBytes(encode).length > 128){
			throw new Exception("订单描述非法");
		}
		try {
			if(request.getMerchantPayNotifyUrl().length() == 0 
				|| request.getMerchantPayNotifyUrl().length() > 200
				|| !request.getMerchantPayNotifyUrl().toLowerCase().trim().startsWith("http"))
				throw new Exception();
		} catch (Exception e) {throw new Exception("异步通知地址");}
		try {
			if(request.getMerchantFrontEndUrl().length() == 0) {
				request.setMerchantFrontEndUrl(merchantFrontEndUrl);				
			}
			if(request.getMerchantFrontEndUrl().length() > 200
				|| (request.getMerchantFrontEndUrl().length() !=0 && 
				!request.getMerchantFrontEndUrl().toLowerCase().trim().startsWith("http")))
				throw new Exception();
		} catch (Exception e) {throw new Exception("商户前台地址非法");}
		if(request.getPayerId().length() > 0 
				&& request.getPayerId().getBytes(encode).length > 50){
			throw new Exception("买家ID非法");
		}
		if(request.getSalerId().length() > 0 
				&& request.getSalerId().getBytes(encode).length > 50){
			throw new Exception("卖家ID非法");
		}
		try {
			long guaranteeAmt = Long.parseLong(request.getGuaranteeAmt());
			if(guaranteeAmt < 0 || guaranteeAmt > 999999999999l)throw new Exception();
		} catch (Exception e) {request.setGuaranteeAmt("0");}
	}
	private void checkCertPayConfirmRequest(CertPayConfirmRequest request) throws Exception {
		if(request.getMerchantId() ==null ||request.getMerchantId().length()==0
			||request.getMerchantId().length() > 24)throw new Exception("商户代码非法");
		if(request.getMerchantOrderId().getBytes(encode).length == 0 
				|| request.getMerchantOrderId().getBytes(encode).length > 32){
			throw new Exception("订单号非法");
		}
		if(request.getCheckCode().length() == 0 || request.getCheckCode().length() > 10){
			throw new Exception("验证码非法");
		}
	}
	/**
	 * 验证退款请求
	 * @param request
	 * @throws Exception
	 */
	private void checkRefundRequest(RefundRequest request) throws Exception {
		if(request.getMerchantId() ==null ||request.getMerchantId().length()==0
				||request.getMerchantId().length() > 24)throw new Exception("商户代码非法");
		if(request.getMerchantOrderId().getBytes(encode).length > 32){
			throw new Exception("订单号非法");
		}
		try {
			long merchantRefundAmt = Long.parseLong(request.getMerchantRefundAmt());
			if(merchantRefundAmt <= 0 || merchantRefundAmt >= 100000000l)throw new Exception();
		} catch (Exception e) {throw new Exception("退款金额非法");}
	}
	/**
	 * 验证对账文件下载请求
	 * @param request
	 * @throws Exception
	 */
	private void checkAccountRequest(AccountRequest request) throws Exception {
		if(request.getMerchantId() ==null ||request.getMerchantId().length()==0
				||request.getMerchantId().length() > 24)throw new Exception("商户代码非法");
		try {
			if(request.getAccountDate()==null || request.getAccountDate().length()!=8)throw new Exception();
			new java.text.SimpleDateFormat("yyyyMMdd").parse(request.getAccountDate());
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("请求日期非法");
		}
	}
	/**
	 * 验证查询订单请求
	 * @param request
	 * @throws Exception
	 */
	private void checkQueryRequest(QueryRequest request) throws Exception {
		if(request.getMerchantId() ==null ||request.getMerchantId().length()==0
				||request.getMerchantId().length() > 24)throw new Exception("商户代码非法");
		if(request.getMerchantOrderId().length() == 0 
				|| request.getMerchantOrderId().getBytes(encode).length > 32){
			throw new Exception("订单号非法");
		}
	}
	/**
	 * 验证担保通知请求
	 * @param request
	 * @throws Exception
	 */
	private void checkGuaranteeNoticeRequest(GuaranteeNoticeRequest request) throws Exception {
		if(request.getMerchantId() ==null ||request.getMerchantId().length()==0
				||request.getMerchantId().length() > 24)throw new Exception("商户代码非法");
		if(request.getMerchantOrderId().length() == 0 
				|| request.getMerchantOrderId().getBytes(encode).length > 32){
			throw new Exception("订单号非法");
		}
		if(!"0".equals(request.getStatus())&&!"1".equals(request.getStatus())){
			throw new Exception("担保通知状态非法");
		}
	}
}