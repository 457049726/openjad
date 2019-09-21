package com.openjad.logger.log4j2;

import com.openjad.logger.api.Logger;
import com.openjad.logger.api.LoggerAdapter;

/**
 * 
 * 
 *  @author hechuan
 *
 */
public class Log4j2LoggerAdapter implements LoggerAdapter {

	
	static{
		try {
			Log4j2Loader.reloadLog4j2();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("自动加载log4j失败，系统退出");
			System.exit(0);
		}
	}
	
	private Log4j2LoggerFactory factory = new Log4j2LoggerFactory();
	

	@Override
	public Logger getLogger(Class<?> key) {
		return new Log4j2Logger(factory.getContext().getLogger(key.getName()));
	}

	@Override
	public Logger getLogger(String key) {
		return new Log4j2Logger(factory.getContext().getLogger(key));
	}

}
