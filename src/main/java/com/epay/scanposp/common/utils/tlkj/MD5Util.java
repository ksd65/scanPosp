package com.epay.scanposp.common.utils.tlkj;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;



import com.alibaba.fastjson.JSONObject;

public class MD5Util {
//	private static Logger logger = LoggerFactory.getLogger(MD5Util.class);

	/** 签名属性名 sign **/
	private static final String SIGN_KEY = "sign";

	/** 密钥属性名key **/
	private static final String SECRET_KEY = "key";

	/**
	 * 计算签名
	 * 
	 * @param o
	 *            要参与签名的数据对象
	 * @param md5Key
	 *            密钥
	 * @return 签名
	 * @throws Exception
	 */
	public static String getSign(Object o, String md5Key) throws Exception {
		ArrayList<String> list = new ArrayList<String>();
		Class cls = o.getClass();
		Field[] fields = cls.getDeclaredFields();
		for (Field f : fields) {
			System.out.println(f.getName());
			if (f.getName().equals(SIGN_KEY)) {
				continue;
			}
			f.setAccessible(true);
			if (f.get(o) != null && !"".equals(f.get(o))) {
				list.add(f.getName() + "=" + f.get(o) + "&");
			}
		}
		int size = list.size();
		String[] arrayToSort = list.toArray(new String[size]);
		Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			sb.append(arrayToSort[i]);
		}
		sb.append(SECRET_KEY).append("=").append(md5Key);
		String str2Sign = sb.toString();
		System.out.println("Sign Before MD5:" + str2Sign);
		String result = MD5.MD5Encode(str2Sign).toUpperCase();
		System.out.println("Sign Result:" + result);
		return result;
	}

	/**
	 * 计算签名
	 * 
	 * @param map
	 *            要参与签名的map数据
	 * @param md5Key
	 *            密钥
	 * @return 签名
	 * @throws Exception
	 */
	public static String getSign(Map<String, Object> map, String md5Key)
			throws Exception {
		ArrayList<String> list = new ArrayList<String>();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			if (entry.getKey().equals(SIGN_KEY)) {
				continue;
			}
			if (entry.getValue() != null && !"".equals(entry.getValue())) {
				list.add(entry.getKey() + "=" + entry.getValue() + "&");
			}
		}
		int size = list.size();
		String[] arrayToSort = list.toArray(new String[size]);
		Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			sb.append(arrayToSort[i]);
		}
		sb.append(SECRET_KEY).append("=").append(md5Key);
		String str2Sign = sb.toString();
		System.out.println("Sign Before MD5:" + str2Sign);
		String result = MD5.MD5Encode(str2Sign).toUpperCase();
		System.out.println("Sign Result:" + result);
		return result;
	}

	/**
	 * 计算签名
	 * 
	 * @param jsonObject
	 *            要参与签名的json数据
	 * @param md5Key
	 *            密钥
	 * @return 签名
	 * @throws Exception
	 */
	public static String getSign(JSONObject jsonObject, String md5Key,int n)
			throws Exception {
		ArrayList<String> list = new ArrayList<String>();
		for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
			if (entry.getKey().equals(SIGN_KEY)) {
				continue;
			}
			if (entry.getValue() != null && !"".equals(entry.getValue())) {
				list.add(entry.getKey() + "=" + entry.getValue() + "&");
			}
		}
		int size = list.size();
		String[] arrayToSort = list.toArray(new String[size]);
		Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			sb.append(arrayToSort[i]);
		}
		if(n==1){
		sb.append(SECRET_KEY).append("=").append(md5Key);
		}else{
			sb.append(SECRET_KEY.toUpperCase()).append("=").append(md5Key);
		}
		String str2Sign = sb.toString();
		System.out.println("Sign Before MD5:" + str2Sign);
		String result = MD5.MD5Encode(str2Sign).toUpperCase();
		System.out.println("Sign Result:" + result);
		return result;
	}

	/**
	 * 验证签名
	 * 
	 * @param map
	 *            要参与签名的map数据
	 * @param md5Key
	 *            密钥
	 * @param sign
	 *            签名
	 * @return
	 * @throws Exception
	 */
	public static boolean verifySign(Map<String, Object> map, String md5Key,
			String sign) throws Exception {
		String md5Text = getSign(map, md5Key);
		return md5Text.equalsIgnoreCase(sign);
	}

	/**
	 * 验证签名
	 * 
	 * @param jsonObject
	 *            要参与签名的json数据
	 * @param md5Key
	 *            密钥
	 * @param sign
	 *            签名
	 * @return
	 * @throws Exception
	 */
	public static boolean verifySign(JSONObject jsonObject, String md5Key,
			String sign) throws Exception {
		String md5Text = getSign(jsonObject, md5Key);
		return md5Text.equalsIgnoreCase(sign);
	}

	/**
	 * 验证签名
	 * 
	 * @param o
	 *            要参与签名的数据对象
	 * @param md5Key
	 *            密钥
	 * @param sign
	 *            签名
	 * @return
	 * @throws Exception
	 */
	public static boolean verifySign(Object o, String md5Key, String sign)
			throws Exception {
		String md5Text = getSign(o, md5Key);
		return md5Text.equalsIgnoreCase(sign);
	}
	public static void main(String[] args) {
//		Map map=new HashMap<>();
//		map.put("A", "ssssss");
//		map.put("B", "stttttts");
//		map.put("C", "sttttwwwws");
//		String string="kdkdkkdkkfkfkkf";
//		try {
//			System.out.println(getSign(map, string));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
