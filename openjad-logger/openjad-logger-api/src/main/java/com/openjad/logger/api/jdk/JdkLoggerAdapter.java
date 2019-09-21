package com.openjad.logger.api.jdk;

import com.openjad.logger.api.Logger;
import com.openjad.logger.api.LoggerAdapter;

/**
 * 
 * <一句话文件描述>
 * 
 *  @Title JdkLoggerAdapter
 *  @author hechuan
 *  @date 2019年9月20日
 *
 *  <功能详述>
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


