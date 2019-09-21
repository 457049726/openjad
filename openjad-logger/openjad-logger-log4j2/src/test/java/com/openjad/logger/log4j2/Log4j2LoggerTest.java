package com.openjad.logger.log4j2;

import org.junit.Test;

import com.openjad.logger.api.Logger;
import com.openjad.logger.api.LoggerFactory;

/**
 * 
 * 
 *  @author hechuan
 *
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
