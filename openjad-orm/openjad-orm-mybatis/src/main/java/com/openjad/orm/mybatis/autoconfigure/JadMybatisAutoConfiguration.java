package com.openjad.orm.mybatis.autoconfigure;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.context.annotation.Bean;

public class JadMybatisAutoConfiguration  {

	@Bean
	public JadSqlSessionFactory jadSqlSessionFactory(SqlSessionFactory sqlSessionFactory,MybatisProperties properties) throws Exception {
		JadSqlSessionFactory jadSqlSessionFactory = new JadSqlSessionFactory();
		jadSqlSessionFactory.setSqlSessionFactory(sqlSessionFactory);
		jadSqlSessionFactory.setProperties(properties);
		return jadSqlSessionFactory;
	}

}
