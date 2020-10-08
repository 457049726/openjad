package com.openjad.orm.mybatis.utils;

import org.junit.Test;

import com.google.gson.Gson;
import com.openjad.orm.mybatis.entity.EoMetaInfo;
import com.openjad.orm.mybatis.eo.TestDictEo;

public class EntityUtilsTest{
	
	@Test
	public void testGetEoInfo(){
		
		try {
			EoMetaInfo eo = EntityUtils.getEoInfo(TestDictEo.class);
			System.out.println(new Gson().toJson(eo));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
