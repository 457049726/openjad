package com.openjad.logger.logback;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.spi.AbstractLoggerAdapter;
import org.apache.logging.log4j.spi.LoggerContext;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.impl.StaticLoggerBinder;

public class LogbackLoggerFactory extends AbstractLoggerAdapter<Logger> implements ILoggerFactory{

	@Override
	protected Logger newLogger(String name, LoggerContext context) {
		 final String key = Logger.ROOT_LOGGER_NAME.equals(name) ? LogManager.ROOT_LOGGER_NAME : name;
		 return StaticLoggerBinder.getSingleton().getLoggerFactory().getLogger(key);
	}

	@Override
	protected LoggerContext getContext() {
		return LogManager.getContext();
	}

}
