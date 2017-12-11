package com.epay.scanposp.common.utils.swift;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	public static boolean isEmpty(String str){
		if(null == str || "".equals(str) || "".equals(str.trim())){
			return true;
		}
		return false;
	}
	
	public static boolean isEmail(String email){
		if(isEmpty(email))return false;
		boolean bool = true;
		String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		if(!matcher.matches()){
			bool = false;
		}
		return bool;
	}
	
	public static boolean isNumeric(String number){
		if(number.matches("//d*")){
			return true;
		}else{
			return false;
		}
	}
	
}
