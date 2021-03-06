package com.epay.scanposp.common.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import com.epay.scanposp.common.constant.SysConfig;
import com.epay.scanposp.common.utils.epaySecurityUtil.EpaySignUtil;
import com.epay.scanposp.common.utils.sm.CertUtil;

import net.sf.json.JSONObject;

public class CommonUtil {
	
	private static Map<String, List<String>> orderCodeMap=new HashMap<String, List<String>>();
	private static SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
	/**
	 * 获取单号,不重复
	 * @return
	 */
	public static String getOrderCode(){
	       
		//生成随机数:当前精确到秒的时间再加6位的数字随机序列
        String rdNum=df.format(new Date());
        String orderCode=null;
        orderCode = rdNum + getRandomNum(6);
        if(orderCodeMap.get(rdNum)==null){
        	orderCodeMap.clear();
        	List<String> orderCodeList=new ArrayList<String>();
        	orderCodeList.add(orderCode);
        	orderCodeMap.put(rdNum, orderCodeList);
        	return orderCode;
        	
        }else{
        	List<String> orderCodeList=orderCodeMap.get(rdNum);
        	if(!orderCodeList.contains(orderCode)){
        		orderCodeList.add(orderCode);
        		return orderCode;
        	}else{
        		return getOrderCode();
        	}
        }
        
	}
	
	/**
	 * 获取指定位数的随机数(纯数字)
	 * 
	 * @param length
	 *            随机数的位数
	 * @return String
	 */
	public static String getRandomNum(int length) {
		if (length <= 0) {
			length = 1;
		}
		StringBuilder res = new StringBuilder();
		Random random = new Random();
		int i = 0;
		while (i < length) {
			res.append(random.nextInt(10));
			i++;
		}
		return res.toString();
	}
	
	
	
	/**
	 * 生成带签名的请求数据
	 * @param paramData
	 * @return
	 */
	public static String createSecurityRequstData(Object reqData){
		
		
		String token="04faecde-792e-4027-89c9-6910798887b6";
		String timeStamp=String.valueOf(new Date().getTime() / 1000);
		String nonceStr=getRandomStringByLength(32);
		
		Map<String, String> paramMap=new HashMap<String, String>();
		paramMap.put("token", token);
		paramMap.put("timeStamp", timeStamp);
		paramMap.put("nonceStr", nonceStr);
		paramMap.put("reqData", reqData.toString());
		String sign=SignatureUtil.createSignature(paramMap);
		
		JSONObject result=new JSONObject();
		result.put("token", token);
		result.put("timeStamp", timeStamp);
		result.put("nonceStr", nonceStr);
		result.put("signature", sign);
		result.put("reqData", reqData);
		System.out.println(result.toString());
		return result.toString();
	}
	
	/**
	 * 获取随机数 	
	 * @param length
	 * @return
	 */
	    public static String getRandomStringByLength(int length) {
	        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	        Random random = new Random();
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < length; i++) {
	            int number = random.nextInt(base.length());
	            sb.append(base.charAt(number));
	        }
	        return sb.toString();
	    }
	
	
	public static void main(String[] args) {
		Map<String, String> map=new HashMap<String, String>();
		for (int i = 0; i < 1000555; i++) {
			String a=CommonUtil.getOrderCode();
			System.out.println(a);
			if(map.containsKey(a)){
				System.out.println("---has--"+i+"---"+a);
				break;
			}
			map.put(a, a);
			
		}
		
	}
	
	public static String getRemoteHost(HttpServletRequest request){
	    String ip = request.getHeader("x-forwarded-for");
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getRemoteAddr();
	    }
	    return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
	}
	
	public static JSONObject signReturn(JSONObject result){
		String rtSrc = StringUtil.orderedKey(result);
		result.put("signStr", EpaySignUtil.sign(SysConfig.platPrivateKey, rtSrc));
		return result;
	}
	
	/**
	 * bean 转化为实体
	 * 
	 * @param bean
	 * @return
	 */
	public static HashMap<String, Object> beanToMap(Object bean) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		if (null == bean) {
			return map;
		}
		Class<?> clazz = bean.getClass();
		BeanInfo beanInfo = null;
		try {
			beanInfo = Introspector.getBeanInfo(clazz);
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor descriptor : descriptors) {
			String propertyName = descriptor.getName();
			if (!"class".equals(propertyName)) {
				Method method = descriptor.getReadMethod();
				String result;
				try {
					result = (String) method.invoke(bean);
					if (null != result) {
						map.put(propertyName, result);
					} else {
						map.put(propertyName, "");
					}
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return map;
	}
	
	
	public  String getConfigPath(){
    	String configPath = "";
    	try {
			configPath = new java.io.File(java.net.URLDecoder.decode(
					CommonUtil.class.getProtectionDomain().getCodeSource().getLocation().getFile(),
					"utf-8")).getParentFile().getAbsolutePath()+ File.separator+"config"+File.separator;
			if(!new File(configPath).exists()){
				File file = new File((this.getClass().getClassLoader().getResource("").getPath()));
				String p = file.getParent();
				if(p.endsWith(File.separator))configPath = p+"classes"+File.separator+EnvironmentUtil.propertyPath;
				else configPath = p +File.separator+"classes"+File.separator+EnvironmentUtil.propertyPath;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			
		}
    	return configPath;
    }
}
