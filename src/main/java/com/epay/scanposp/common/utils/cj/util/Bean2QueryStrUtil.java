package com.epay.scanposp.common.utils.cj.util;

import java.lang.reflect.Field;
import java.util.UUID;

/**
 * @ClassName: Bean2QueryStrUtil
 * @Description: Bean转查询串数据
 * @author LiShiwen
 * @date 2016年6月20日 下午5:40:36
 *
 */
public class Bean2QueryStrUtil{
	
	/**
	 * bean 转查询串
	 * @param obj
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public  String bean2QueryStr(Object obj) throws IllegalArgumentException, IllegalAccessException{
		StringBuffer sb=new StringBuffer();
		if(obj==null){
			return null;
		}
		
		System.out.println(obj);
		
		Class clazz=obj.getClass();
		System.out.println(obj.getClass().getName());
		Field[] fields=clazz.getDeclaredFields();
		System.out.println("字段个数："+fields.length);
		for (Field field : fields) {
			field.setAccessible(true);
			if(field.get(obj)==null){
				sb.append(field.getName()+"=&");
			}else{
				sb.append(field.getName()+"="+field.get(obj)+"&");
			}
			
		}
		return sb.substring(0, sb.toString().length()-1);
	}
	
}
