package com.epay.scanposp.common.utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class ValidateUtil {

	// 汉字
	public static final String V_CHINESE = "[\u4e00-\u9fa5]+";
	// 字符串、字母、下划线、数字
	public static final String V_USERNAME = "[a-zA-Z0-9_\u4e00-\u9fa5()]+";
	// QQ规则,允许为空
	public static final String V_QQ = "^(\\d{11})|^([1-9][0-9]{4})|([0-9]{6,10})?$";
	// url规则
	public static final String V_URL = "^((https|http|ftp|rtsp|mms):\\/\\/)?[a-z0-9A-Z]{3}\\.[a-z0-9A-Z][a-z0-9A-Z]{0,61}?[a-z0-9A-Z]\\.com|net|cn|cc (:s[0-9]{1,4})?\\/$";
	// 不能包含特殊字符 ?![#^$%&*'<>]代表不出现其中一个,零宽断言
	public static final String V_DANGER = "((?![#^$%&*'<>]).)*";

	public static final String TEXT_ERROR = "格式不合法";

	public static final String TEXT_DANGER = "非法字符";

	public static final String TEXT_FORBIN_NULL = "不能为空";

	/** 是否是手机号 */
//	private static final Pattern isMBPhonePattern = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(177)|(18[0-9]))\\d{8}$");
	private static final Pattern isMBPhonePattern = Pattern.compile("^\\d{11}$");
	/** 是否固定电话 */
	private static final Pattern isPhonePattern = Pattern
			.compile("^(0[0-9]{2,3}\\-)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?$");
	/** 是否整数 */
	private static final Pattern isDoublePattern = Pattern.compile("^\\d+(\\.\\d*)?|\\.\\d+$");

	/**
	 * 
	 * @Title: isMbPhone
	 * @Description: 是否是手机号格式
	 * @param mobile
	 * @return
	 * @return: boolean
	 */
	public static boolean isMbPhone(String mobile) {
		Matcher matcher = isMBPhonePattern.matcher(mobile);
		if (matcher.find()) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @Title: isPhone
	 * @Description: 是否是固定电话
	 * @param phone
	 * @return
	 * @return: boolean
	 */
	public static boolean isPhone(String phone) {
		Matcher matcher = isPhonePattern.matcher(phone);
		if (matcher.find()) {
			return true;
		}
		return false;
	}

	public static boolean isDouble(String sdouble) {
		Matcher matcher = isDoublePattern.matcher(sdouble);
		if (matcher.find()) {
			return true;
		}
		return false;
	}

	public static boolean checkRex(String str, String rex) {
		if (str == null) {
			return false;
		}
		return str.matches(rex);
	}

	/**
	 * 名称确定 字母、数字、下划线、括号、中文
	 * 
	 * @param str
	 *            名称
	 * @return 符合true 不符合false
	 */
	public static boolean checkStrName(String str) {
		return checkRex(str, V_USERNAME);
	}

	/**
	 * QQ验证
	 * 
	 * @param str
	 * @return
	 */
	public static boolean checkQQ(String str) {
		return checkRex(str, V_QQ);
	}

	/**
	 * 验证域名
	 * 
	 * @param str
	 *            域名串
	 * @return 符合true 不符合false
	 */
	public static boolean checkDomain(String str) {
		return checkRex(str, V_URL);
	}

	/**检验是否全是汉字
	 * true 是
	 * **/
	public static boolean checkChinese(String str){
		return checkRex(str, V_CHINESE);
	}
	/**
	 * 判断字符串长度
	 * 
	 * @param str
	 *            源字符串
	 * @param min
	 *            最小长度
	 * @param max
	 *            最大长度
	 * @param border
	 *            是否包含判断边界
	 * @return 符合true 不符合false
	 */
	public static boolean checkStrLen(String str, int min, int max, boolean border) {
		boolean flag = false;
		if (str != null) {
			if (border) {
				if (str.length() >= min && str.length() <= max)
					return true;
			} else {
				if (str.length() > min && str.length() < max)
					return true;
			}
		}
		return flag;
	}

	public static boolean isInNumRange(long val, long min, long max, boolean border) {
		if (border) {
			if (val >= min && val <= max)
				return true;
		} else {
			if (val > min && val < max)
				return true;
		}
		return false;
	}

	public static boolean isMinNumRange(long val, long min, boolean border) {
		if (border) {
			if (val >= min)
				return true;
		}
		return false;
	}

	/**
	 * 收集web控制器抛出的参数异常
	 * 
	 * @param result
	 *            结果集
	 * @return 异常字符串
	 */
	public static String getInputErrorMsg(BindingResult result) {
		StringBuffer msg = new StringBuffer();
		List<FieldError> list = result.getFieldErrors();
		for (int i = 0; i < list.size(); i++) {
			FieldError error = (FieldError) list.get(i);
			msg.append(error.getDefaultMessage());
		}
		return msg.toString();
	}
	
	//校验是否是可以金额  包括整数和小数
	public static boolean isDoubleT(String str) {

		/*
		 * Pattern pattern = Pattern .compile(
		 * "^(([1-9][0-9]{0,7})|0|0\\.[0-9]{1,2}|[1-9][0-9]{0,7}(\\.[0-9]{1,2})?)$"
		 * );
		 */
		Pattern pattern = Pattern.compile("^([\\d]|[1-9][\\d]{0,7})(\\.[\\d]{1,2})?$");

		return pattern.matcher(str).matches();

	}
	/**最多支持tail位小数**/
	public static boolean isDoubleByTail(String str,int tail) {
		Pattern pattern = Pattern.compile("^([\\d]|[1-9][\\d]{0,7})(\\.[\\d]{1,"+tail+"})?$");
		return pattern.matcher(str).matches();
	}
	/**
	 * 返回1说明 src大，
	 * 返回0说明 src=target，
	 * 返回-1说明 src小
	 * @return
	 */
	public static int compare(String src,String target){
		double num1 = Double.parseDouble(src);
		double num2 = Double.parseDouble(target);
		if(num1>num2)return 1;
		else if(num1==num2)return 0;
		else return -1;
	}
	public static void main(String[] args) {
		System.out.println(isDoubleByTail("0",4));
		System.out.println(isDoubleByTail("0.22",4));
		System.out.println(isDoubleByTail("1.2222",4));
		System.out.println(isDoubleByTail("0.22222",4));
		System.out.println(isDoubleByTail(".22",4));
	}
}
