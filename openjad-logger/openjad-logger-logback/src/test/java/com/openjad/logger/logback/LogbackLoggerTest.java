package com.openjad.logger.logback;

import org.junit.Test;

import com.openjad.logger.api.Logger;
import com.openjad.logger.api.LoggerFactory;

public class LogbackLoggerTest {
	@Test
	public void testDebug() {
		Logger logger= LoggerFactory.getLogger(LogbackLoggerTest.class);
		logger.debug("debug...");
		logger.info("info...");
		logger.warn("warn...");
		logger.error("error...");
		
		try {
			throw  new RuntimeException("ee");
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
		};
	}
}
