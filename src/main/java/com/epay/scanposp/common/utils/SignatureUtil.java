package com.epay.scanposp.common.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epay.scanposp.common.filter.GetRequestPRMFilter;

/**
 * 工具类
 * 
 *
 * 
 */
public class SignatureUtil {
	private static Logger logger = LoggerFactory.getLogger(SignatureUtil.class);
	public static final String key="3BD0172A-11A3-49";
	
	public static boolean checkSignature(Map<String, String> dataMap,String signature){
		
		String result =createSignature(dataMap);
		logger.debug(result);
		if(signature.equals(result)){
			return true;
		}else{
			return false;
		}
	}
	
	public static String createSignature(Map<String, String> dataMap){
		ArrayList<String> list = new ArrayList<String>();
		for(String key:dataMap.keySet()){
			if(dataMap.get(key)!=null&&!"".equals(dataMap.get(key))){
				list.add(key + "=" + dataMap.get(key));
			}
		}
		int size = list.size();
		String [] arrayToSort = list.toArray(new String[size]);
		Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < size; i ++) {
			sb.append(arrayToSort[i]);
		}
		String result = sb.toString();
		result += "key=" + key;
		result = SecurityUtil.md5Encode(result);
		return result;
	}
}
