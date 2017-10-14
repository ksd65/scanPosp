package com.epay.scanposp.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 * 
 * @author ghq
 *
 */
public class DateUtil {
	/**
	 * 获取日期
	 * 
	 * @return
	 */
	public static String getDate() {
		return getDate(new Date());
	}
	
	public static String getDateStr() {
		return getDateStr(new Date());
	}

	/**
	 * 获取时间
	 * 
	 * @return
	 */
	public static String getTime() {
		return getTime(new Date());
	}

	/**
	 * 获取日期
	 * 
	 * @return
	 */
	public static String getDate(Date date) {
		SimpleDateFormat dateSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateSimpleDateFormat.format(date);
	}
	
	/**
	 * 获取日期
	 * 
	 * @return
	 */
	public static String getDateStr(Date date) {
		SimpleDateFormat dateSimpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		return dateSimpleDateFormat.format(date);
	}

	/**
	 * 获取时间
	 * 
	 * @return
	 */
	public static String getTime(Date date) {
		SimpleDateFormat dateSimpleDateFormat = new SimpleDateFormat("HH:mm:ss");
		return dateSimpleDateFormat.format(date);
	}
	
	/**
	 * 获取时间串
	 * 
	 * @return
	 */
	public static String getDateTimeStr(Date date) {
		SimpleDateFormat dateSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		return dateSimpleDateFormat.format(date);
	}
	public static String getDateFormat(Date date,String fmt) {
		SimpleDateFormat dateSimpleDateFormat = new SimpleDateFormat(fmt);
		return dateSimpleDateFormat.format(date);
	}
	/**
	 * 上个月第一天
	 * @param fmt
	 * @return
	 */
	public static String getPreMonthFirstDay(String fmt){
		SimpleDateFormat dateSimpleDateFormat = new SimpleDateFormat(fmt);
		Calendar   cal_1=Calendar.getInstance();//获取当前日期 
        cal_1.add(Calendar.MONTH, -1);
        cal_1.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
        cal_1.set(Calendar.HOUR_OF_DAY, 0);
        cal_1.set(Calendar.MINUTE, 0);
        cal_1.set(Calendar.SECOND, 0);
        String firstDay = dateSimpleDateFormat.format(cal_1.getTime());
//        System.out.println("-----1------firstDay:"+firstDay);
        return firstDay;
	}
	/**
	 * 上个月最后一天
	 * @param fmt
	 * @return
	 */
	public static String getPreMonthLastDay(String fmt){
		SimpleDateFormat dateSimpleDateFormat = new SimpleDateFormat(fmt);
		Calendar cale = Calendar.getInstance();   
	    cale.set(Calendar.DAY_OF_MONTH,0);//设置为1号,当前日期既为本月第一天 
	    cale.set(Calendar.HOUR_OF_DAY, 23);
	    cale.set(Calendar.MINUTE, 59);
	    cale.set(Calendar.SECOND, 59);
	    String lastDay = dateSimpleDateFormat.format(cale.getTime());
//	    System.out.println("-----2------lastDay:"+lastDay);
		return lastDay;
	}
	/**
	 * 当月第一天
	 * @param fmt
	 * @return
	 */
	public static String getMonthFirstDay(String fmt){
		SimpleDateFormat dateSimpleDateFormat = new SimpleDateFormat(fmt);
		Calendar c = Calendar.getInstance();    
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
        c.set(Calendar.HOUR_OF_DAY, 0);
	    c.set(Calendar.MINUTE, 0);
	    c.set(Calendar.SECOND, 0);
        String first = dateSimpleDateFormat.format(c.getTime());
//        System.out.println("===============first:"+first);
		return first;
	}
	/**
	 * 当月最后一天
	 * @param fmt
	 * @return
	 */
	public static String getMonthLastDay(String fmt){
		SimpleDateFormat dateSimpleDateFormat = new SimpleDateFormat(fmt);
		Calendar ca = Calendar.getInstance();
		ca.add(Calendar.MONTH, 1);
        ca.set(Calendar.DAY_OF_MONTH,0); 
//        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH)); 
        ca.set(Calendar.HOUR_OF_DAY, 23);
	    ca.set(Calendar.MINUTE, 59);
	    ca.set(Calendar.SECOND, 59);
        String last = dateSimpleDateFormat.format(ca.getTime());
//        System.out.println("===============last:"+last);
		return last;
	}
	/**
     * 根据时间类型比较时间大小 
     * 
     * @param source
     * @param traget
     * @param type "YYYY-MM-DD" "yyyyMMdd HH:mm:ss"  类型可自定义
     * @param 传递时间的对比格式
     * @return 
     *  0 ：source和traget时间相同    
     *  1 ：source比traget时间大  
     *  -1：source比traget时间小
     * @throws Exception
     */
	 public static int dateCompare(String source, String traget, String type) throws Exception {
	        int ret = 2;
	        SimpleDateFormat format = new SimpleDateFormat(type);
	        Date sourcedate = format.parse(source);
	        Date tragetdate = format.parse(traget);
	        ret = sourcedate.compareTo(tragetdate);
	        return ret;
	    }
	
	
	public static void main(String[] args) {
		String fmt = "yyyyMMddHHmmss";
		getPreMonthFirstDay(fmt);
		getPreMonthLastDay(fmt);
		getMonthFirstDay(fmt);
		getMonthLastDay(fmt);
		 String nowTime = new SimpleDateFormat("HH:mm:ss").format(new Date());
	        int i=2;
			try {
				i = dateCompare(nowTime,"14:49:00","HH:mm:ss");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        switch (i) {
	        case 0:
	            System.out.println("两个时间相等");
	            break;
	        case 1:
	            System.out.println("当前时间晚于06:00");
	            break;
	        case -1:
	            System.out.println("当前时间早于06:00");
	            break;
	        default:
	            break;
	        }
	}
}
