package com.openjad.logger.logback;

import com.openjad.logger.api.Logger;
import com.openjad.logger.api.LoggerAdapter;

public class LogbackLoggerAdapter implements LoggerAdapter{
	private LogbackLoggerFactory factory = new LogbackLoggerFactory();
	@Override
	public Logger getLogger(Class<?> key) {
		return getLogger(key.getName());
	}

	@Override
	public Logger getLogger(String key) {
		return new LogbackLogger(factory.getContext().getLogger(key));
	}

}
