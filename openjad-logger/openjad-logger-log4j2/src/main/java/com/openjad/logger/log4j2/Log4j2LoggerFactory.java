package com.openjad.logger.log4j2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.spi.AbstractLoggerAdapter;
import org.apache.logging.log4j.spi.LoggerContext;
import org.apache.logging.slf4j.Log4jLogger;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

/**
 * 
 * 
 *  @author hechuan
 *
 */
public class Log4j2LoggerFactory extends AbstractLoggerAdapter<Logger> implements ILoggerFactory{

    @Override
    protected Logger newLogger(final String name, final LoggerContext context) {
        final String key = Logger.ROOT_LOGGER_NAME.equals(name) ? LogManager.ROOT_LOGGER_NAME : name;
        return new Log4jLogger(context.getLogger(key), name);
    }

    @Override
    protected LoggerContext getContext() {
        return LogManager.getContext();
    }
}
