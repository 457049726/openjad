package com.openjad.common.util;

import java.util.Collection;

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

}
