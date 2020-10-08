package com.openjad.orm.mybatis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.openjad.orm.mybatis.annotation.JadMapperScan;


@JadMapperScan
@SpringBootApplication
public class MybatisMain {

	public static void main(String[] args) {
		SpringApplication.run(MybatisMain.class, args);
	}

}
