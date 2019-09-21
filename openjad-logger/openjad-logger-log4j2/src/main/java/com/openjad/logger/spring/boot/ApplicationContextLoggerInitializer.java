package com.openjad.logger.spring.boot;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import com.openjad.logger.log4j2.Log4j2Loader;

/**
 * 
 * <一句话文件描述>
 * 
 *  @Title ApplicationContextLoggerInitializer
 *  @author hechuan
 *  @date 2019年9月20日
 *
 *  <功能详述>
 */
public class ApplicationContextLoggerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
	
	@Override
	public void initialize(ConfigurableApplicationContext context) {
		Log4j2Loader.reloadLog4j2();
	}

}
