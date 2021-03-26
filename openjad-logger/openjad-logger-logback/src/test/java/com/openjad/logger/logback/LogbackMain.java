package com.openjad.logger.logback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 
 * 
 *  @author hechuan
 *
 */
@SpringBootApplication
public class LogbackMain {

	public static void main(String[] args) {
//		Logger logger = LoggerFactory.getLogger(TestMain.class);
		SpringApplication.run(LogbackMain.class, args);
	}

}
