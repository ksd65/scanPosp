package com.epay.scanposp.common.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;

/**
 * @ClassName: PindanUtil
 * @Description: MX_N
 * @author: zhangjingcheng_91
 * @date: 2015年4月13日 下午5:02:21
 * @version: V1.0
 */
public class BusUtil {

	private static final String PATH_INDEX_NAME = "/";
	private static final long COOKIEEXPIRE = 60 * 2L;

	/**
	 * 设置到cookie中,只读
	 * 
	 * @param reqArgsMap
	 * @throws Exception
	 */
	public static void setDecCookieHttpOnly(HttpServletResponse response, String data, String key)
			throws Exception {
		setCookieByHttpOnly(response, key, data, PATH_INDEX_NAME, null, COOKIEEXPIRE, false);
	}

	/**
	 * 设置到cookie中,只读
	 * 
	 * @param reqArgsMap
	 * @throws Exception
	 */
	public static void setCookie(HttpServletResponse response, String data, String key)
			throws Exception {
		setCookieByHttpOnly(response, key, data, PATH_INDEX_NAME, null, COOKIEEXPIRE, false);
	}

	/**
	 * 从cookie中取出
	 * 
	 * @param reqArgsMap
	 */
	public static String getDecCookie(HttpServletRequest request, String key) throws Exception {

		return getCookie(request, key);
	}

	/**
	 * 根据key从cookie中取出值
	 * 
	 * @param reqArgsMap
	 */
	public static String getCookie(HttpServletRequest request, String key) throws Exception {
		String value = null;
		if (!StringUtils.isEmpty(key)) {
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (int index = 0; index < cookies.length; index++) {
					String cookieName = cookies[index].getName();
					if (key.equals(cookieName)) {
						value = cookies[index].getValue();
						break;
					}
				}
			}
		}

		return value;
	}

	/**
	 * 判断某个cookie是否已经存在
	 * 
	 * @param reqArgsMap
	 * @param key
	 *            设置到cookie中的标示
	 */
	public static boolean cookieIsExist(HttpServletRequest request, String key) throws Exception {
		boolean isExist = false;

		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			String cookieName = null;
			for (int index = 0; index < cookies.length; index++) {
				cookieName = cookies[index].getName();
				if (key.equals(cookieName)) {
					isExist = true;
					break;
				}
			}
		}

		return isExist;
	}

	/**
	 * 将值设置到cookie中，其中设置httponly表示该cookie值js是无法获取到的
	 * 
	 * @param reqArgsMap
	 * @param key
	 *            设置到cookie中的标示
	 * @param value
	 *            设置到cookie中的值
	 * @param path
	 *            设置cookie路径
	 * @param domain
	 *            设置cookie的作用域
	 * @param maxAge
	 *            设置cookie的生存时间
	 * @param secure
	 *            设置浏览器是否通过Https(加密方式)方式来传输cookie
	 * @throws Exception
	 */
	public static void setCookieByHttpOnly(HttpServletResponse response, String key, String value, String path,
			String domain, Long maxAge, boolean secure) throws Exception {
		if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
			return;
		}

		StringBuilder sb = new StringBuilder();
		sb.append(key).append("=").append(value).append(";");
		if (!StringUtils.isEmpty(path)) {
			sb.append("Path=").append(path).append(";");
		}
		if (!StringUtils.isEmpty(domain)) {
			sb.append("Domain=").append(domain).append(";");
		}
		if (maxAge != null) {
			sb.append("Max-Age=").append(maxAge).append(";");
		}
		if (secure) {
			sb.append("Secure;");
		}
		sb.append("HttpOnly");

		response.addHeader("Set-Cookie", sb.toString());
	}

	/**
	 * 将值设置到cookie中
	 * 
	 * @param reqArgsMap
	 * @param key
	 *            设置到cookie中的标示
	 * @param value
	 *            设置到cookie中的值
	 * @param path
	 *            设置cookie路径
	 * @param domain
	 *            设置cookie的作用域
	 * @param maxAge
	 *            设置cookie的生存时间
	 * @param secure
	 *            设置浏览器是否通过Https(加密方式)方式来传输cookie
	 * @throws Exception
	 */
	public static void setCookie(HttpServletResponse response, String key, String value, String path,
			String domain, Long maxAge, boolean secure) throws Exception {
		if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
			return;
		}

		StringBuilder sb = new StringBuilder();

		sb.append(key).append("=").append(value).append(";");

		if (!StringUtils.isEmpty(path)) {
			sb.append("Path=").append(path).append(";");
		}

		if (!StringUtils.isEmpty(domain)) {
			sb.append("Domain=").append(domain).append(";");
		}

		if (maxAge != null) {
			sb.append("Max-Age=").append(maxAge).append(";");
		}

		if (secure) {
			sb.append("Secure;");
		}

		response.addHeader("Set-Cookie", sb.toString());
	}

	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}

		return ip;
	}
}
