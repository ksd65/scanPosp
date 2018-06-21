package com.epay.scanposp.common.utils.tlkj;

import java.util.Map;
import java.util.TreeMap;

public class MapUtils {

	public static String map2UrlParams(Map<String, Object> params) {
		TreeMap<String, Object> treeMap = new TreeMap<>(params);
		StringBuilder sb = new StringBuilder();
		for(Map.Entry<String, Object> item : treeMap.entrySet()) {
			sb.append(item.getKey()).append("=").append(item.getValue()).append("&");
		}
		return sb.toString().substring(0, sb.toString().length()-1);
	}
	
}
