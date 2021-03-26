package com.openjad.logger.api;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.LogManager;

import com.openjad.logger.api.jdk.JdkLogManager;
import com.openjad.logger.api.jdk.JdkLoggerAdapter;
import com.openjad.logger.api.jdk.formatter.JadStreamFormatter;
import com.openjad.logger.api.support.FailsafeLogger;

/**
 * 
 * 
 * @author hechuan
 *
 */
public class LoggerFactory {

	private static final ConcurrentMap<String, FailsafeLogger> LOGGERS = new ConcurrentHashMap<String, FailsafeLogger>();
	private static volatile LoggerAdapter LOGGER_ADAPTER;

	private volatile static Boolean INITED = false;

	public static synchronized void init() {
		if (INITED) {
			return;
		}
		INITED = true;
		try {
			setLoggerAdapter((LoggerAdapter) Class.forName("com.openjad.logger.log4j2.Log4j2LoggerAdapter").newInstance());
		} catch (Exception e) {
			try {
				setLoggerAdapter((LoggerAdapter) Class.forName("com.openjad.logger.logback.LogbackLoggerAdapter").newInstance());
			} catch (Exception e2) {
//				System.err.println("日志框架初始化失败，自动转换为JdkLoggerAdapter");
				setLoggerAdapter(new JdkLoggerAdapter());
			}
		}
		
		

		try {
			initJdkLogFormatter();
		} catch (Exception e) {
			System.err.println("初始化日志Formatter失败");
		}

	}

	static {
		init();
	}

	private static void initJdkLogFormatter() throws Exception {
		String cname = System.getProperty("java.util.logging.manager");
		if (cname == null || "".equals(cname)) {
			System.setProperty("java.util.logging.manager", JdkLogManager.class.getName());
		}
	}

	public static void setLoggerAdapter(LoggerAdapter loggerAdapter) {
		if (loggerAdapter != null) {
//			System.out.println("使用日志适配器: " + loggerAdapter.getClass().getName());
			LOGGER_ADAPTER = loggerAdapter;
			for (Map.Entry<String, FailsafeLogger> entry : LOGGERS.entrySet()) {
				entry.getValue().setLogger(LOGGER_ADAPTER.getLogger(entry.getKey()));
			}
		}
	}

	public static Logger getLogger(Class<?> key) {
		FailsafeLogger logger = LOGGERS.get(key.getName());
		if (logger == null) {
			LOGGERS.putIfAbsent(key.getName(), new FailsafeLogger(LOGGER_ADAPTER.getLogger(key)));
			logger = LOGGERS.get(key.getName());
		}
		return logger;
	}

	public static Logger getLogger(String key) {
		FailsafeLogger logger = LOGGERS.get(key);
		if (logger == null) {
			LOGGERS.putIfAbsent(key, new FailsafeLogger(LOGGER_ADAPTER.getLogger(key)));
			logger = LOGGERS.get(key);
		}
		return logger;
	}

}
