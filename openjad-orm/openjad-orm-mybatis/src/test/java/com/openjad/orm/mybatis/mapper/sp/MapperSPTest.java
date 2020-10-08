package com.openjad.orm.mybatis.mapper.sp;

import org.junit.Test;

import com.openjad.orm.mybatis.dao.RpcExceptionTraceDAO;

public class MapperSPTest{
	
	@Test
	public void testToXml(){
		
		MapperSP mapper = new MapperSP(RpcExceptionTraceDAO.class.getName());
		mapper.init();
		String xml = mapper.toXml();
		System.out.println(xml);
		
//		Class clazz = ReflectionUtils.getSuperClassGenricType(RpcExceptionTraceDAOImpl.class);
//		System.out.println(clazz.getName());
	}

}
