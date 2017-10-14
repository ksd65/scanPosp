package com.epay.scanposp.common.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import net.sf.ezmorph.MorphException;
import net.sf.ezmorph.object.AbstractObjectMorpher;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.JSONUtils;

public class JsonBeanReleaseUtil {
	
	public static String beanToJson(Object obj,String dateFormat) {
    	JsonConfig config = new JsonConfig();
    	//设置Timestamps类型的属性转化为指定格式字符串                      注册timestamp处理器
    	config.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor(dateFormat));
    	//设置java.util.Date类型的属性转化为指定格式字符串          注册java.util.date处理器
    	config.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor(dateFormat));
    	JSONObject json = JSONObject.fromObject(obj, config);
    	return json.toString();
    }
	
	public static Object jsonToBean(String json,Class cls) {
        String[] formats = { "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd" };
        //注册timestamp处理器
        JSONUtils.getMorpherRegistry().registerMorpher(new TimestampMorpher(formats));
        //注册java.util.date处理器
        JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(formats));
        JSONObject jsonObject = JSONObject.fromObject(json);
        return JSONObject.toBean(jsonObject, cls);
    }
	
}

class DateJsonValueProcessor implements JsonValueProcessor {
    public static final String Default_DATE_PATTERN ="yyyy-MM-dd";
    private DateFormat dateFormat ;
    public DateJsonValueProcessor(String datePattern){
        try{
            dateFormat  = new SimpleDateFormat(datePattern);}
        catch(Exception e ){
            dateFormat = new SimpleDateFormat(Default_DATE_PATTERN);
        }
    }
    public Object processArrayValue(Object value, JsonConfig jsonConfig) {
        return process(value);
    }
    public Object processObjectValue(String key, Object value,JsonConfig jsonConfig) {
        return process(value);
    }
    private Object process(Object value){
    	if(value instanceof java.util.Date ){
    		return dateFormat.format((java.util.Date)value);
    	}
    	if(value instanceof java.sql.Timestamp){
    		return dateFormat.format((Timestamp)value);
    	}
    	return value;
    		
    }
}

class TimestampMorpher extends AbstractObjectMorpher {
    /*** 日期字符串格式 */
    private String[] formats;

    public TimestampMorpher(String[] formats) {
        this.formats = formats;
    }

    public Object morph(Object value) {
        if (value == null) {
            return null;
        }
        if (Timestamp.class.isAssignableFrom(value.getClass())) {
            return (Timestamp) value;
        }
        if (!supports(value.getClass())) {
            throw new MorphException(value.getClass() + " 是不支持的类型");
//        	System.out.println(value.getClass() + " 是不支持的类型");
        }
        String strValue = (String) value;
        SimpleDateFormat dateParser = null;
        for (int i = 0; i < formats.length; i++) {
            if (null == dateParser) {
                dateParser = new SimpleDateFormat(formats[i]);
            } else {
                dateParser.applyPattern(formats[i]);
            }
            try {
                return new Timestamp(dateParser.parse(strValue.toLowerCase()).getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Class morphsTo() {
        return Timestamp.class;
    }

    public boolean supports(Class clazz) {
        return String.class.isAssignableFrom(clazz);
    }

}


class DateMorpher extends AbstractObjectMorpher {
    /*** 日期字符串格式 */
    private String[] formats;

    public DateMorpher(String[] formats) {
        this.formats = formats;
    }

    public Object morph(Object value) {
        if (value == null) {
            return null;
        }
        if (java.util.Date.class.isAssignableFrom(value.getClass())) {
            return (java.util.Date) value;
        }
        if (!supports(value.getClass())) {
            throw new MorphException(value.getClass() + " 是不支持的类型");
//        	System.out.println(value.getClass() + " 是不支持的类型");
        }
        String strValue = (String) value;
        SimpleDateFormat dateParser = null;
        for (int i = 0; i < formats.length; i++) {
            if (null == dateParser) {
                dateParser = new SimpleDateFormat(formats[i]);
            } else {
                dateParser.applyPattern(formats[i]);
            }
            try {
                return new java.util.Date(dateParser.parse(strValue.toLowerCase()).getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Class morphsTo() {
        return java.util.Date.class;
    }

    public boolean supports(Class clazz) {
        return String.class.isAssignableFrom(clazz);
    }

}

