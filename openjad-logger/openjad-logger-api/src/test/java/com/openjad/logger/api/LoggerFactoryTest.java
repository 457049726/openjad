package com.openjad.logger.api;

import org.junit.Test;

/**
 * 
 * <一句话文件描述>
 * 
 *  @Title LoggerFactoryTest
 *  @author hechuan
 *  @date 2019年9月20日
 *
 *  <功能详述>
 */
public class LoggerFactoryTest{

	@Test
	public void testDebug() {
		
		Logger logger= LoggerFactory.getLogger(LoggerFactoryTest.class);
		
		logger.debug("debug...");
		logger.info("info...");
		logger.warn("warn...");
		logger.error("error...");
	}
}
