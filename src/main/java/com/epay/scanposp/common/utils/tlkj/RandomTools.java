package com.epay.scanposp.common.utils.tlkj;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class RandomTools {
	
	/**
	 * 随机字符串
	 * @param length 长度
	 * @return
	 */
	 public static String getRandomString(int length) {
	        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";//含有字符和数字的字符串
	        Random random = new Random();//随机类初始化
	        StringBuffer sb = new StringBuffer();//StringBuffer类生成，为了拼接字符串
	 
	        for (int i = 0; i < length; ++i) {
	            int number = random.nextInt(62);// [0,62)
	 
	            sb.append(str.charAt(number));
	        }
	        return sb.toString();
	    }
	 /**
	  * 订单号
	  * @return
	  */
	 public static String orderIdAuIN(){
			SimpleDateFormat format=new SimpleDateFormat("ddhhmmssSS");
			int a[]= new int[18];
			String s=format.format(new Date());
			for(int i=0;i<a.length;i++ ) {

			    a[i] = (int)(10*(Math.random()));
	            s+=a[i];
			  

			}
			return s;
		}
}
