package com.openjad.logger.log4j2;

import static com.openjad.common.util.SysInitArgUtils.getClassPath;
import static com.openjad.common.util.SysInitArgUtils.hasProperty;
import static com.openjad.common.util.SysInitArgUtils.readProjectName;
import static com.openjad.logger.log4j2.Log4j2Constants.DEFAULT_LOG4J_FILE;
import static com.openjad.logger.log4j2.Log4j2Constants.LOG4J_CONFIG_PATH;
import static com.openjad.logger.log4j2.Log4j2Constants.LOG_DIR_PATH;
import static com.openjad.logger.log4j2.Log4j2Constants.OPENJAD_LOG4J_FILE;

import static com.openjad.common.constant.PropertiyConstants.*;

import java.io.File;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.status.StatusLogger;

import com.openjad.common.util.StringUtils;

/**
 * 自动加载log4j2 的配置，它能自动找到log4j2.xml文件(可以加载config目录或classPath或指定目录中的文件) 并加载
 * 
 * 同时，它还自动获取 服务端口，应用名称等属性，用于作为 log4j2.xml文件中的通配符
 * 
 * @author hechuan
 * 
 *
 */
public class Log4j2Loader {


	/**
	 * 加载标志位，防止重复加载
	 */
	private volatile static boolean isloaded = false;

	/**
	 * classPath
	 */
	private static String CLASS_PATH = getClassPath();

	/**
	 * 重新加载配置文件
	 */
	public static synchronized void reloadLog4j2() {
		if (isloaded) {
			return;
		}

		isloaded = true;
		try {
			doReloadLog4j2();
		} catch (Throwable ex) {
			System.err.println("加载log4j2日志文件失败,classPath:" + CLASS_PATH);
			ex.printStackTrace();
		}

	}

	/**
	 * 加载配置文件
	 */
	private static void doReloadLog4j2() {

		preReloadLog4j2();

		String logFilePath = getLogj2PathAndSetLoggingConfig();

		if (StringUtils.isNotBlank(logFilePath)) {
			System.out.println("开始加载Log4j2配置文件：" + logFilePath);
			StatusLogger.getLogger().setLevel(Level.OFF);
			
			// 加载外部日志文件
			Configurator.initialize("Log4j2", logFilePath);
		}

	}

	

	public static void preReloadLog4j2() {
		
		// 获取端口
		setServerPort();

		// 获取日志输出路径
		setLogDirPath(CLASS_PATH);

		// 获取appid
		setSystemCode();
	}

	private static String getLogj2PathAndSetLoggingConfig() {

		if (hasProperty(LOG4J_CONFIG_PATH)) {

			String propertylogFilePath = System.getProperty(LOG4J_CONFIG_PATH);

			if (isExists(propertylogFilePath)) {

				return propertylogFilePath;

			} else {
				System.err.println("找不到指定的Log4j2配置文件：" + propertylogFilePath + ",使用默认配置");
			}

		}

		String logFilePath = CLASS_PATH + File.separator + "config" + File.separator + DEFAULT_LOG4J_FILE;
		if (!isExists(logFilePath)) {
			logFilePath = new File(CLASS_PATH).getParent() + File.separator + "config" + File.separator + DEFAULT_LOG4J_FILE;
		}

		if (isExists(logFilePath)) {
			System.setProperty(LOG4J_CONFIG_PATH, logFilePath);
			return logFilePath;
		} else {
			// 没有log4j配置，整个默认的进去
			System.setProperty(LOG4J_CONFIG_PATH, "classpath:" + OPENJAD_LOG4J_FILE);
		}

		return null;
	}

	private static boolean isExists(String path) {
		return new File(path).exists();
	}

	/**
	 * 获取应用端口 （只能获取启动脚本中的端口配置，无法获取apollo中的端口配置）
	 */
	private static void setServerPort() {
		String key = "serverPort";
		if (hasProperty(key)) {
			return;
		}
		if (hasProperty(SERVER_PORT_KEY)) {
			System.setProperty(key, System.getProperty(SERVER_PORT_KEY));
			return;
		}
		System.setProperty(key, "");
	}

	/**
	 * 获取日志输出路径
	 * 
	 * @param classPath param
	 */
	private static void setLogDirPath(String classPath) {
		String key = LOG_DIR_PATH;
		if (hasProperty(key)) {
			return;
		}
		String logDirPath = classPath + File.separator + "logs" + File.separator;
		System.setProperty(key, logDirPath);
	}

	/**
	 * 获取系统代码
	 */
	private static void setSystemCode() {
		String key = "systemCode";
		if (hasProperty(key)) {
			return;
		}
		if (hasProperty(APPLICATION_NAME_KEY)) {
			System.setProperty(key, System.getProperty(APPLICATION_NAME_KEY));
			return;
		}
		String projectName = readProjectName();
		if (StringUtils.isNotBlank(projectName)) {
			System.setProperty(key, projectName);
			return;
		} else {
			projectName = "unknownApp";
		}
		System.setProperty(key, projectName);
	}


}
