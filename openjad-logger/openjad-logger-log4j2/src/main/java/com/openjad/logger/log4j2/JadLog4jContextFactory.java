package com.openjad.logger.log4j2;

import org.apache.logging.log4j.core.impl.Log4jContextFactory;

import com.openjad.logger.api.LoggerFactory;

/**
 * 
 * <一句话文件描述>
 * 
 *  @Title JadLog4jContextFactory
 *  @author hechuan
 *  @date 2019年9月20日
 *
 *  <功能详述>
 */
public class JadLog4jContextFactory extends Log4jContextFactory {
	static {
		LoggerFactory.init();
	}
}
