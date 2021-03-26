package com.openjad.orm.mybatis.votest;

import java.util.Date;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.openjad.orm.mybatis.eo.TestBaseEO;
import com.openjad.orm.mybatis.service.TestBaseService;
import com.openjad.test.junit.MySpringJunit4ClassRunner;

@EnableAutoConfiguration
@RunWith(MySpringJunit4ClassRunner.class)
@SpringBootTest
public class BaseEOTest {
	@Autowired
	private TestBaseService testBaseService;
	
//	@Test
	public void insertAllColumn() {
		TestBaseEO eo = newTestBaseEO("name1");
		testBaseService.insertAllColumn(eo);
		Assert.notNull(eo.getId(), "测试失败");
		testBaseService.deleteById(eo.getId());
	}
	
	@Test
	public void updateByIdAllColumn() {
		
		TestBaseEO eo = newTestBaseEO("name1");
		testBaseService.insertAllColumn(eo);
		Assert.notNull(eo.getId(), "测试失败");
//		eo.setName("name2");
//		eo.setColumnDate(null);
//		testBaseService.updateByIdSelective(eo, eo.getId());
		testBaseService.deleteById(eo.getId());
		
	}
	
	
	private TestBaseEO newTestBaseEO(String name) {
		TestBaseEO eo = new TestBaseEO();
		eo.setName(name);
		eo.setAge(new Random().nextInt(10));
		eo.setColumnDate(new Date());
		eo.setBitText("bitText");
		return eo;
	}

}
