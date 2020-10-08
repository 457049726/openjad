package com.openjad.orm.mybatis.mapper;

import java.io.IOException;
import java.lang.reflect.Field;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.core.io.Resource;

import com.openjad.orm.mybatis.constant.MybatisLogCode;
import com.openjad.orm.mybatis.scanner.JadMapperScannerConfigurer;
import com.openjad.logger.api.Logger;
import com.openjad.logger.api.LoggerFactory;


/**
 * jad 封装的 SqlSessionFactoryBean
 * 
 *  @author hechuan
 *
 */
public class JadSqlSessionFactoryBean extends SqlSessionFactoryBean {
	
	private static final Logger logger = LoggerFactory.getLogger(JadSqlSessionFactoryBean.class);

	protected SqlSessionFactory buildSqlSessionFactory() throws Exception {
		SqlSessionFactory sqlSessionFactory = super.buildSqlSessionFactory();
		try {
			//把  mapperLocations 属性偷偷地记下来
			Resource[] mapperLocations = (Resource[]) getSuperPrivateField("mapperLocations");
			if(mapperLocations!=null){
				JadMapperScannerConfigurer.setMapperLocations(mapperLocations);
			}
			
		} catch (Exception e) {
			logger.warn(MybatisLogCode.CODE_00014,"获取 mapperLocations配置失败,"+e.getMessage(),e);
		}
		return sqlSessionFactory;
	}
	
	/**
	 * 获取父类私有属性
	 * @param name 属性名
	 * @return 属性值
	 * @throws Exception 获取失败
	 */
	private <T> T getSuperPrivateField(String name) throws Exception {
		Field field = SqlSessionFactoryBean.class.getDeclaredField(name);
		field.setAccessible(true);
		return (T) field.get(this);
	}

}
