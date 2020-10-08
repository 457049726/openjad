package com.openjad.orm.mybatis.utils;

import org.junit.Test;

import com.openjad.orm.mybatis.eo.TestDictEo;

public class SqlHelperTest{
	@Test
	public void testGetEoInfo(){
		
		String sql = SqlHelper.getSelectSql(TestDictEo.class, "a");
		System.out.println(sql);
		
	}
}
