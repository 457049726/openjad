package com.openjad.logger.api;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.openjad.logger.api.jdk.JdkLoggerAdapter;
import com.openjad.logger.api.support.FailsafeLogger;

/**
 * 
 * <一句话文件描述>
 * 
 * @Title LoggerFactory
 * @author hechuan
 * @date 2019年9月20日
 *
 *       <功能详述>
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
			System.err.println("日志框架初始化失败，自动转换为JdkLoggerAdapter");
			setLoggerAdapter(new JdkLoggerAdapter());
		}
	}

	static {
		init();
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
