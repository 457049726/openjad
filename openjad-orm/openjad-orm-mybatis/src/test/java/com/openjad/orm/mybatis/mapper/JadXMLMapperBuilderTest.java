package com.openjad.orm.mybatis.mapper;

import com.openjad.common.exception.BizException;
import com.openjad.logger.api.Logger;
import com.openjad.logger.api.LoggerFactory;
import com.openjad.orm.mybatis.dao.HealthCheckDefConfDAO;
import com.openjad.orm.mybatis.mapper.sp.MapperSP;

public class JadXMLMapperBuilderTest {
	
	private static final Logger logger = LoggerFactory.getLogger(JadXMLMapperBuilderTest.class);
	
	public static void main(String[] args) {
		try {
			MapperSP mapper = JadXMLMapperBuilder.getMapperSP(HealthCheckDefConfDAO.class.getName());
			System.out.println(mapper.toXml());
		} catch (BizException e) {
			//抛一个带异常代码的异常出来，控制台会自动生成网址
			logger.error(e);
		}
	}

}
