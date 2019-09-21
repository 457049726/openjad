package com.openjad.logger.log4j2;

import org.junit.Test;

import com.openjad.logger.api.Logger;
import com.openjad.logger.api.LoggerFactory;

/**
 * 
 * <一句话文件描述>
 * 
 *  @Title Log4j2LoggerTest
 *  @author hechuan
 *  @date 2019年9月20日
 *
 *  <功能详述>
 */
public class Log4j2LoggerTest {
	
	@Test
	public void testDebug() {
		Logger logger= LoggerFactory.getLogger(Log4j2LoggerTest.class);
		logger.debug("debug...");
		logger.info("info...");
		logger.warn("warn...");
		logger.error("error...");
	}
}
