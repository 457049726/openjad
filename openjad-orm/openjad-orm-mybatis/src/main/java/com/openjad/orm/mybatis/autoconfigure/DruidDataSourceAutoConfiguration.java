package com.openjad.orm.mybatis.autoconfigure;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.alibaba.druid.pool.DruidDataSource;

@ConditionalOnClass({ SqlSessionFactory.class, SqlSessionFactoryBean.class, DruidDataSource.class })
@EnableConfigurationProperties({ DataSourceProperties.class, OldDataSourceProperties.class, OldDataSourcePoolProperties.class })
@AutoConfigureBefore({ DataSourceAutoConfiguration.class })
public class DruidDataSourceAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public DataSource druidDataSource(DataSourceProperties prop, OldDataSourceProperties oldProp,
			OldDataSourcePoolProperties oldPoolProp) {

		DruidDataSource dataSource = new DruidDataSource();

		String driverClass = prop.getDriverClassName();
		if (StringUtils.isNotBlank(driverClass)) {
			dataSource.setDriverClassName(driverClass);
		} else if (StringUtils.isNotBlank(oldProp.getDriver())) {
			dataSource.setDriverClassName(oldProp.getDriver());
		}

		String jdbcUrl = prop.getUrl();
		if (StringUtils.isNotBlank(jdbcUrl)) {
			dataSource.setUrl(jdbcUrl);
		} else if (StringUtils.isNotBlank(oldProp.getUrl())) {
			dataSource.setUrl(oldProp.getUrl());
		}

		String username = prop.getUsername();
		if (StringUtils.isNotBlank(username)) {
			dataSource.setUsername(username);
		} else if (StringUtils.isNotBlank(oldProp.getUsername())) {
			dataSource.setUsername(oldProp.getUsername());
		}

		String password = prop.getPassword();
		if (StringUtils.isNotBlank(password)) {
			dataSource.setPassword(password);
		} else if (StringUtils.isNotBlank(oldProp.getPassword())) {
			dataSource.setPassword(oldProp.getPassword());
		}
		
		if(oldPoolProp.getInit()!=null) {
			dataSource.setInitialSize(oldPoolProp.getInit().intValue());//int
		}
		
		if(oldPoolProp.getMinIdle()!=null) {
			dataSource.setMinIdle(oldPoolProp.getMinIdle().intValue());//int
		}
		
		
		if(oldPoolProp.getMaxActive()!=null) {
			dataSource.setMaxActive(oldPoolProp.getMaxActive().intValue());//int
		}
		
		
		if(StringUtils.isNotBlank(oldPoolProp.getTestSql())) {
			dataSource.setValidationQuery(oldPoolProp.getTestSql());
		}
		
		
		if(oldPoolProp.getMaxWait()!=null) {
			dataSource.setMaxWait(oldPoolProp.getMaxWait().longValue());
		}
		
		if(oldPoolProp.getTimeBetweenEvictionRunsMillis()!=null) {
			dataSource.setTimeBetweenEvictionRunsMillis(oldPoolProp.getTimeBetweenEvictionRunsMillis().longValue());//long
		}
		
		if(oldPoolProp.getMinEvictableIdleTimeMillis()!=null) {
			dataSource.setMinEvictableIdleTimeMillis(oldPoolProp.getMinEvictableIdleTimeMillis().longValue());//long
		}
		return dataSource;
	}
	

}
