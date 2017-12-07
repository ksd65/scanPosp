package com.epay.scanposp.common.utils.sand.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	private static final String DATE_FORMAT_14 = "yyyyMMddHHmmss";
	private static final String DATE_FORMAT_08 = "yyyyMMdd";

	  public static String getCurrentDate14()
	  {
	    String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	    return date;
	  }

	  public static String getCurrentDate08() {
	    String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
	    return date;
	  }

	  public static String getYesterday()
	  {
	    Calendar cal = Calendar.getInstance();
	    cal.add(5, -1);
	    String yesterday = new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
	    return yesterday;
	  }

}
