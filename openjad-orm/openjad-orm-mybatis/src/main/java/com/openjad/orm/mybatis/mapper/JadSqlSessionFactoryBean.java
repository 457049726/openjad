package com.openjad.orm.mybatis.mapper;

import java.io.IOException;
import java.lang.reflect.Field;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.core.io.Resource;

import com.openjad.logger.api.Logger;
import com.openjad.logger.api.LoggerFactory;
import com.openjad.orm.mybatis.scanner.JadMapperScannerConfigurer;


/**
 * jad 封装的 SqlSessionFactoryBean
 * 
 *  @author hechuan
 *
 */
public class JadSqlSessionFactoryBean extends SqlSessionFactoryBean {

//	private static final Logger logger = LoggerFactory.getLogger(JadSqlSessionFactoryBean.class);

//	private Resource[] mapperLocations;
	
	protected SqlSessionFactory buildSqlSessionFactory() throws IOException {
		SqlSessionFactory sqlSessionFactory = super.buildSqlSessionFactory();
//		try {
//			// 把 mapperLocations 属性记下来
//			Resource[] mapperLocations = (Resource[]) getSuperPrivateField("mapperLocations");
//			if (mapperLocations != null) {
////				JadMapperScannerConfigurer.setMapperLocations(mapperLocations);
//				this.mapperLocations=mapperLocations;
//			}
//		} catch (Exception e) {
//			logger.warn("获取 mapperLocations 配置失败," + e.getMessage(), e);
//		}
		return sqlSessionFactory;
	}

//	private <T> T getSuperPrivateField(String name) throws Exception {
//		Field field = SqlSessionFactoryBean.class.getDeclaredField(name);
//		field.setAccessible(true);
//		return (T) field.get(this);
//	}
//
//	public Resource[] getMapperLocations() {
//		return mapperLocations;
//	}

}



