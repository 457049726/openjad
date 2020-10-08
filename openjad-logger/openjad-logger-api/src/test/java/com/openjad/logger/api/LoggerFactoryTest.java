package com.openjad.logger.api;

import org.junit.Test;

import com.openjad.common.exception.BizException;

/**
 * 
 * 
 *  @author hechuan
 *
 */
public class LoggerFactoryTest{

	@Test
	public void testDebug() {
		Logger logger= LoggerFactory.getLogger(LoggerFactoryTest.class);
//		logger.debug("debug...");
//		logger.info("info...");
//		logger.warn(FrameworkLogCode.CODE_00001,"warn...");
		logger.error("error...",new BizException());
//		logger.error("error...",new BizException(FrameworkLogCode.CODE_00001));
//		
	}
}
