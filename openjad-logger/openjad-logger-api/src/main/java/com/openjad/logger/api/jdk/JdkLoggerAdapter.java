package com.openjad.logger.api.jdk;

import com.openjad.logger.api.Logger;
import com.openjad.logger.api.LoggerAdapter;

/**
 * 
 * 
 *  @author hechuan
 *
 */
public class JdkLoggerAdapter implements LoggerAdapter {

    @Override
    public Logger getLogger(Class<?> key) {
        return new JdkLogger(java.util.logging.Logger.getLogger(key == null ? "" : key.getName()));
    }

    @Override
    public Logger getLogger(String key) {
        return new JdkLogger(java.util.logging.Logger.getLogger(key));
    }

}


