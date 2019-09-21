package com.openjad.logger.log4j2;

import org.apache.logging.log4j.core.impl.Log4jContextFactory;

import com.openjad.logger.api.LoggerFactory;

/**
 * 
 * 
 *  @author hechuan
 *
 */
public class JadLog4jContextFactory extends Log4jContextFactory {
	static {
		LoggerFactory.init();
	}
}
