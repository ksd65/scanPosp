package com.epay.scanposp.common.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {

	public static String null2Empty(String oldStr, String newStr) {
		if (null == oldStr) {
			return newStr;
		}
		return oldStr;
	}

	public static String null2Empty(String s) {
		return null2Empty(s, "");
	}

	public static String jsonMsg(boolean flag, String returnStr) {
		return "{\"success\":" + flag + ",\"msg\":\"" + returnStr + "\"}";
	}

	public static List<Long> strToList(String str, String rex) {
		List<Long> result = new ArrayList<Long>();
		String[] items = str.split(",");
		for (int i = 0; i < items.length; i++) {
			if (!"".equals(items[i]))
				result.add(Long.parseLong(items[i]));
		}
		return result;
	}

	/**
	 * 生成省略词工具类
	 * 
	 * @param str
	 * @param limit
	 * @param isAllLength
	 * @return
	 */
	public static String getEllipsisString(String str, int limit,
			boolean isAllLength) {
		if (str.length() <= limit) {
			return str;
		} else {
			StringBuilder stb = new StringBuilder(str);
			if (isAllLength) {
				stb.delete(limit - 3, str.length());
			} else {
				stb.delete(limit, str.length());
			}
			stb.append("...");
			return stb.toString();
		}
	}
	
	public static boolean isEmpty(String str){
		if(str==null||"".equals(str)||"".equals(str.trim())){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 给map里面的参数按字母大小排序
	 * @param dataMap
	 * @return
	 */
	public static String orderedKey(Map<String, String> dataMap){
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
			sb.append("&");
		}
		String result = sb.toString();
		if(StringUtils.isNotBlank(result)){//去掉最后的&
			result = result.substring(0, result.length()-1);
		}
		return result;
	}
	
	public static boolean isRealDouble(String str){
		try{
			int index = str.indexOf(".");
			if(index !=-1){
				String doubleStr = str.substring(index);
				if(new BigDecimal(doubleStr).compareTo(BigDecimal.ZERO)>0){
					return true;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	public static void main(String[] args) {
		System.out.println(StringUtil.isRealDouble("0.002"));
	}
	
	/**
	 * 给map里面的参数按字母大小排序
	 * @param dataMap
	 * @return
	 */
	public static String orderedKeyObj(Map<String, Object> dataMap){
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
			sb.append("&");
		}
		String result = sb.toString();
		if(StringUtils.isNotBlank(result)){//去掉最后的&
			result = result.substring(0, result.length()-1);
		}
		return result;
	}
}
