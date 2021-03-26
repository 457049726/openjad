package com.openjad.orm.mybatis.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.openjad.orm.mybatis.eo.RpcExceptionTraceDO;
import com.openjad.orm.mybatis.service.RpcExceptionTraceService;
import com.openjad.test.junit.MySpringJunit4ClassRunner;
import com.openjad.orm.criterion.Criteria;
import com.openjad.orm.criterion.PagedCriteria;
import com.openjad.orm.criterion.SelectCriteria;
import com.openjad.orm.criterion.WhereCriteria;

@EnableAutoConfiguration
@RunWith(MySpringJunit4ClassRunner.class)
@SpringBootTest
public class MybatisMainTest {

	@Autowired
	private RpcExceptionTraceService rpcExceptionTraceService;

	@Test
	public void whenQuerySuccess(){
		System.out.println("ok");
 		try {
			RpcExceptionTraceDO rpcExceptionTraceDO = rpcExceptionTraceService.getById(1L);
			
			PagedCriteria pagedCriteria =new PagedCriteria();
			pagedCriteria.setPageNo(1);
			pagedCriteria.setPageSize(10);
			
//			List<RpcExceptionTraceDO>list = rpcExceptionTraceService.listByPagedCriteria(pagedCriteria);
			
			SelectCriteria selectCriteria = new SelectCriteria();
			Criteria c = new Criteria();
			c.andEqualTo(1, "id");
			selectCriteria.addCriteria(c);
			
			List<RpcExceptionTraceDO>list = rpcExceptionTraceService.listBySelectCriteria(selectCriteria);
			
			Integer res = rpcExceptionTraceService.deleteById(6L);
			
			WhereCriteria whereCriteria =new WhereCriteria();
			
			Criteria c2 = new Criteria();
			c2.andEqualTo(33, "id");
			whereCriteria.addCriteria(c2);
			
			rpcExceptionTraceService.deleteByCriteria(whereCriteria);
			
			System.out.println("is true:" + (rpcExceptionTraceDO == null));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
