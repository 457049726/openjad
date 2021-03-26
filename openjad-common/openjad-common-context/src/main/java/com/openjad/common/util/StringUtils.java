package com.openjad.common.util;

import java.util.Collection;
import java.util.regex.Pattern;

/**
 * 
 * @author hechuan
 *
 *         字符串工具类, 继承org.apache.commons.lang.StringUtils类
 * 
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {
	/**
	 * join string.
	 *
	 * @param array
	 *            String array.
	 * @return String.
	 */
	public static String join(String[] array) {
		if (array.length == 0)
			return "";
		StringBuilder sb = new StringBuilder();
		for (String s : array)
			sb.append(s);
		return sb.toString();
	}

	/**
	 * join string like javascript.
	 *
	 * @param array
	 *            String array.
	 * @param split
	 *            split
	 * @return String.
	 */
	public static String join(String[] array, char split) {
		if (array.length == 0)
			return "";
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < array.length; i++) {
			if (i > 0)
				sb.append(split);
			sb.append(array[i]);
		}
		return sb.toString();
	}

	/**
	 * join string like javascript.
	 *
	 * @param array
	 *            String array.
	 * @param split
	 *            split
	 * @return String.
	 */
	public static String join(String[] array, String split) {
		if (array.length == 0)
			return "";
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < array.length; i++) {
			if (i > 0)
				sb.append(split);
			sb.append(array[i]);
		}
		return sb.toString();
	}

	public static String join(Collection<String> coll, String split) {
		if (coll.isEmpty())
			return "";

		StringBuilder sb = new StringBuilder();
		boolean isFirst = true;
		for (String s : coll) {
			if (isFirst)
				isFirst = false;
			else
				sb.append(split);
			sb.append(s);
		}
		return sb.toString();
	}
	
	

	/**
	 * 生成一个符合规范的JAVA类名
	 * 
	 * @param name
	 * @return
	 */
	public static String generateJavaClass(String name) {
		String result = name.toLowerCase();
		String[] specialCharacters = new String[] { "-", "_" };
		for (String specialCharacter : specialCharacters) {
			int index = result.indexOf(specialCharacter);
			while (index != -1) {
				if (index == result.length() - 1) {
					break;
				}
				result = result.substring(0, index)
						+ result.substring(index + 1, index + 2).toUpperCase()
						+ result.substring(index + 2);
				index = result.indexOf(specialCharacter);
			}
		}
		result = result.replaceAll("[^a-zA-Z]", "");
		result = result.substring(0, 1).toUpperCase() + result.substring(1);
		return result;
	}

	/**
	 * 首字母转换成小写
	 */
	public static String uncapitalize(String str) {
		return changeFirstCharacterCase(str, false);
	}

	public static String changeFirstCharacterCase(String str, boolean capitalize) {
		if (str == null || str.length() == 0) {
			return str;
		}
		StringBuilder sb = new StringBuilder(str.length());
		if (capitalize) {
			sb.append(Character.toUpperCase(str.charAt(0)));
		} else {
			sb.append(Character.toLowerCase(str.charAt(0)));
		}
		sb.append(str.substring(1));
		return sb.toString();
	}

	/**
	 * 生成一个符合规范的JAVA变量名
	 * 
	 * @param name
	 * @return
	 */
	public static String generateJavaVari(String name) {
		String result = name.toLowerCase();
		String[] specialCharacters = new String[] { "-", "_" };
		for (String specialCharacter : specialCharacters) {
			int index = result.indexOf(specialCharacter);
			while (index != -1) {
				if (index == result.length() - 1) {
					break;
				}
				result = result.substring(0, index)
						+ result.substring(index + 1, index + 2).toUpperCase()
						+ result.substring(index + 2);
				index = result.indexOf(specialCharacter);
			}
		}
//		result = result.replaceAll("[^a-zA-Z]", "");
		result = result.replaceAll("[^a-zA-Z0-9]", "");//20171219
		result = result.substring(0, 1).toLowerCase() + result.substring(1);
		return result;
	}

	/**
	 * 生成一个符合规范的表名
	 * 
	 * @param name
	 * @return
	 */
	public static String generateTableName(String name) {
		return generateTableName(name, "");

	}

	/**
	 * 生成一个符合规范的表名
	 * 
	 * @param name
	 * @return
	 */
	public static String generateTableName(String name, String prefix) {

		String connStr = "_";

		String nameStr = name.replaceAll("[^a-zA-Z0-9]", "");
		if ("".equals(nameStr)) {
			return "";
		}
		boolean flag = false;
		StringBuffer sb = new StringBuffer();
		sb.append(nameStr.charAt(0));

		for (int i = 1; i < nameStr.length(); i++) {
			String c = nameStr.charAt(i) + "";
			
			if (Pattern.matches("[A-Z]", c) && !sb.toString().endsWith(connStr)) {
				if (!flag) {
					sb.append(connStr);
				}
				flag = true;
			} else {
				flag = false;
			}
			sb.append(c);
		}

		return sb.toString().toLowerCase();
	
	}

}
