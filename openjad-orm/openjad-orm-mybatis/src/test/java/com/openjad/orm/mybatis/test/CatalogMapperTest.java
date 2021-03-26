package com.openjad.orm.mybatis.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import com.google.gson.Gson;
import com.openjad.orm.mybatis.dao.CatalogMapper;
import com.openjad.orm.mybatis.eo.Catalog;
import com.openjad.test.junit.MySpringJunit4ClassRunner;

@EnableAutoConfiguration
@RunWith(MySpringJunit4ClassRunner.class)
@SpringBootTest
public class CatalogMapperTest {


	@Autowired
	private CatalogMapper catalogMapper;

	@Test
	public void testSelectAll() {
		List<Catalog>list=catalogMapper.selectAll();
//		List<Catalog>list= getCatalog();
		try {
			System.out.println(new Gson().toJson(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private List<Catalog>getCatalog(){
		List<Catalog>list = new ArrayList<Catalog>();
		
		Catalog c1=new Catalog();
		c1.setId(1);
		c1.setName("name1");
		
		Catalog c2=new Catalog();
		c2.setId(2);
		c2.setName("name2");
//		c1.setCatalog(c2);
		
		
		Catalog c3=new Catalog();
		c3.setId(3);
		c3.setName("name3");
		
		list.add(c1);
		list.add(c3);
		
		return list;
	}
	
}
