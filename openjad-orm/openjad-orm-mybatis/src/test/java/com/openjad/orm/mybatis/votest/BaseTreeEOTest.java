package com.openjad.orm.mybatis.votest;

import java.util.Date;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.google.gson.Gson;
import com.openjad.orm.mybatis.eo.TestBaseTreeEO;
import com.openjad.orm.mybatis.service.TestBaseTreeService;
import com.openjad.test.junit.MySpringJunit4ClassRunner;

@EnableAutoConfiguration
@RunWith(MySpringJunit4ClassRunner.class)
@SpringBootTest
public class BaseTreeEOTest {
	@Autowired
	private TestBaseTreeService testBaseTreeService;
	
	
//	findTreeByParentId
	
	@Test
	public void findTreeByParentId() {
		TestBaseTreeEO tree = testBaseTreeService.findTreeByParentId(null);
		System.out.println(new Gson().toJson(tree));
	}
	
//	@Test
	public void insertAllColumn() {
		TestBaseTreeEO eo = newTestBaseTreeEO("name1");
		testBaseTreeService.insertAllColumn(eo);
		Assert.notNull(eo.getId(), "测试失败");
		
		
		TestBaseTreeEO eo2 = newTestBaseTreeEO("name2");
		eo2.setParentId(eo.getId());
		testBaseTreeService.insertSelective(eo2);
		
		TestBaseTreeEO eo3 = newTestBaseTreeEO("name3");
		eo3.setParentId(eo2.getId());
		testBaseTreeService.insertSelective(eo3);
		
		TestBaseTreeEO eo4 = newTestBaseTreeEO("name4");
		eo4.setParentId(eo2.getId());
		testBaseTreeService.insertSelective(eo4);
		
//		testBaseTreeService.deleteById(eo.getId());
	}
	
	
	
	private TestBaseTreeEO newTestBaseTreeEO(String name) {
		TestBaseTreeEO eo = new TestBaseTreeEO();
		eo.setName(name);
		eo.setAge(new Random().nextInt(10));
		eo.setColumnDate(new Date());
		eo.setBitText("bitText");
		return eo;
	}

}
