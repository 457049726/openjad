package com.openjad.logger.api.jdk;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Properties;
import java.util.logging.LogManager;
import java.util.logging.SimpleFormatter;

import com.openjad.common.constant.FrameworkCode;
import com.openjad.logger.api.jdk.formatter.JadFileFormatter;
import com.openjad.logger.api.jdk.formatter.JadStreamFormatter;

public class JdkLogManager extends LogManager {

	public void readConfiguration() throws IOException, SecurityException {

		super.readConfiguration();

		try {
			String formatterKey = "java.util.logging.StreamHandler.formatter";
			putFormatterIfNotConf(formatterKey, JadStreamFormatter.class, this);

			formatterKey = "java.util.logging.ConsoleHandler.formatter";
			putFormatterIfNotConf(formatterKey, JadStreamFormatter.class, this);

			formatterKey = "java.util.logging.FileHandler.formatter";
			putFormatterIfNotConf(formatterKey, JadFileFormatter.class, this);

		} catch (Exception e) {
			String more = new JdkLogger(null).getMsgForMore(FrameworkCode.CODE_00001,false);
			System.err.println("重置formatter失败," + more);
			e.printStackTrace();
		}
	}

	/**
	 * 如果没有配置 Formatter，就配一些默认的进去
	 * 
	 * @param formatterKey
	 *            key
	 * @param clazz
	 *            formatterclass
	 * @throws Exception
	 *             配置失败
	 */
	private static void putFormatterIfNotConf(String formatterKey, Class clazz, LogManager logManager) throws Exception {
		Properties props = getLogManagerProps(logManager);
		String formatter = logManager.getProperty(formatterKey);
		if (formatter == null || "".equals(formatter.trim()) || SimpleFormatter.class.getName().equals(formatter)) {
			props.put(formatterKey, clazz.getName());
		}
	}

	/**
	 * 获取 Props
	 * 
	 * @param logManager
	 *            logManager
	 * @return Props
	 * @throws Exception
	 *             获取失败
	 */
	private static Properties getLogManagerProps(LogManager logManager) throws Exception {
		Field field = LogManager.class.getDeclaredField("props");
		field.setAccessible(true);
		return (Properties) field.get(logManager);
	}

}
