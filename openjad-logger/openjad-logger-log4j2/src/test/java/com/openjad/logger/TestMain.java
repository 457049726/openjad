package com.openjad.logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 
 * 
 *  @author hechuan
 *
 */
@SpringBootApplication
public class TestMain {

	public static void main(String[] args) {
//		Logger logger = LoggerFactory.getLogger(TestMain.class);
		SpringApplication.run(TestMain.class, args);
	}

}
