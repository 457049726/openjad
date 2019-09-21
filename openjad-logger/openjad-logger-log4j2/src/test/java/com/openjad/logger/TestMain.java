package com.openjad.logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 
 * <一句话文件描述>
 * 
 *  @Title TestMain
 *  @author hechuan
 *  @date 2019年9月20日
 *
 *  <功能详述>
 */
@SpringBootApplication
public class TestMain {

	public static void main(String[] args) {
//		Logger logger = LoggerFactory.getLogger(TestMain.class);
		SpringApplication.run(TestMain.class, args);
	}

}
