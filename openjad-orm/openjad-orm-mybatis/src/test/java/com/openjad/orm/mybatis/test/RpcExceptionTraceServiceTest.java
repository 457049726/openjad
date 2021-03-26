package com.openjad.orm.mybatis.test;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.util.Assert;

import com.openjad.orm.criterion.Criteria;
import com.openjad.orm.criterion.PagedCriteria;
import com.openjad.orm.criterion.SelectCriteria;
import com.openjad.orm.criterion.WhereCriteria;
import com.openjad.orm.mybatis.eo.RpcExceptionTraceDO;
import com.openjad.orm.mybatis.service.RpcExceptionTraceService;
import com.openjad.test.base.JadBaseTestCase;

@EnableAutoConfiguration
public class RpcExceptionTraceServiceTest extends JadBaseTestCase{
	
	@Autowired
	private RpcExceptionTraceService rpcExceptionTraceService;

	@Test
	public void insertAllColumn() {
		RpcExceptionTraceDO d = newRpcExceptionTraceDO();
		rpcExceptionTraceService.insertAllColumn(d);
		Assert.notNull(d.getId(), "测试失败");
		RpcExceptionTraceDO d2 = rpcExceptionTraceService.getById(d.getId());
		Assert.notNull(d2, "测试失败");
	}

	//@Test
	public void insertSelective() {
		RpcExceptionTraceDO d = newRpcExceptionTraceDO();
		rpcExceptionTraceService.insertSelective(d);
		Assert.notNull(d.getId(), "测试失败");
		
		RpcExceptionTraceDO d2 = rpcExceptionTraceService.getById(d.getId());
		Assert.notNull(d2, "测试失败");
	}

	/**
	 * 跟据id查找
	 * @param id 	
	 * @return
	 */
	//@Test
	public void testGetById(){
		RpcExceptionTraceDO d = newRpcExceptionTraceDO();
		rpcExceptionTraceService.insertSelective(d);
		Assert.notNull(d.getId(), "测试失败");
		
		RpcExceptionTraceDO d2 = rpcExceptionTraceService.getById(d.getId());
		Assert.notNull(d2, "测试失败");
	}

	//@Test
	public void testListByPagedCriteria() {
		RpcExceptionTraceDO d = newRpcExceptionTraceDO();
		rpcExceptionTraceService.insertSelective(d);
		Assert.notNull(d.getId(), "测试失败");
		
		PagedCriteria pagedCriteria=new PagedCriteria();
		Criteria criteria = new Criteria();
		criteria.andEqualTo(d.getRequestId(), "REQUEST_ID");
		pagedCriteria.addCriteria(criteria);
//		insertAllColumn
		List<RpcExceptionTraceDO> list = rpcExceptionTraceService.listByPagedCriteria(pagedCriteria);
		Assert.notEmpty(list, "测试失败");
		
	}

	//@Test
	public void listBySelectCriteria() {
		RpcExceptionTraceDO d = newRpcExceptionTraceDO();
		rpcExceptionTraceService.insertSelective(d);
		Assert.notNull(d.getId(), "测试失败");
		
		SelectCriteria selectCriteria=new SelectCriteria();
		Criteria criteria = new Criteria();
		criteria.andEqualTo(d.getRequestId(), "REQUEST_ID");
		selectCriteria.addCriteria(criteria);
		
		List<RpcExceptionTraceDO> list = rpcExceptionTraceService.listBySelectCriteria(selectCriteria);
		Assert.notEmpty(list, "测试失败");
	}

	//@Test
	public void deleteById() {
		
		RpcExceptionTraceDO d = newRpcExceptionTraceDO();
		rpcExceptionTraceService.insertSelective(d);
		Assert.notNull(d.getId(), "测试失败");
		
		RpcExceptionTraceDO d2 = rpcExceptionTraceService.getById(d.getId());
		Assert.notNull(d2, "测试失败");
		
		rpcExceptionTraceService.deleteById(d.getId());
		
		RpcExceptionTraceDO d3 = rpcExceptionTraceService.getById(d.getId());
		Assert.isNull(d3, "测试失败");
		
	}

	//@Test
	public void deleteByCriteria() {
		RpcExceptionTraceDO d = newRpcExceptionTraceDO();
		d.setRequestId("deleteByCriteria"+d.getRequestId());
		rpcExceptionTraceService.insertSelective(d);
		Assert.notNull(d.getId(), "测试失败");
		RpcExceptionTraceDO d2 = rpcExceptionTraceService.getById(d.getId());
		Assert.notNull(d2, "测试失败");
		
		WhereCriteria whereCriteria=new WhereCriteria();
		Criteria criteria = new Criteria();
		criteria.andEqualTo(d.getRequestId(), "REQUEST_ID");
		whereCriteria.addCriteria(criteria);
		rpcExceptionTraceService.deleteByCriteria(whereCriteria);
		
		RpcExceptionTraceDO d3 = rpcExceptionTraceService.getById(d.getId());
		Assert.isNull(d3, "测试失败");
		
		
	}

	

	//@Test
	public void updateByCriteriaAllColumn() {
		
		RpcExceptionTraceDO d = newRpcExceptionTraceDO();
		d.setRequestId("updateByCriteriaAllColumn"+d.getRequestId());
		rpcExceptionTraceService.insertSelective(d);
		Assert.notNull(d.getId(), "测试失败");
		RpcExceptionTraceDO d2 = rpcExceptionTraceService.getById(d.getId());
		Assert.notNull(d2, "测试失败");
		
		
		WhereCriteria whereCriteria=new WhereCriteria();
		Criteria criteria = new Criteria();
		criteria.andEqualTo(d.getRequestId(), "REQUEST_ID");
		whereCriteria.addCriteria(criteria);
		
		d.setRequestId("33333"+d.getRequestId());
		rpcExceptionTraceService.updateByCriteriaAllColumn(d, whereCriteria);
		
		RpcExceptionTraceDO d3 = rpcExceptionTraceService.getById(d.getId());
		Assert.notNull(d3, "测试失败");
		Assert.isTrue(d3.getRequestId().equals(d.getRequestId()),"测试失败");
		
	}

	//@Test
	public void updateByIdAllColumn() {
		
		RpcExceptionTraceDO d = newRpcExceptionTraceDO();
		rpcExceptionTraceService.insertSelective(d);
		Assert.notNull(d.getId(), "测试失败");
		RpcExceptionTraceDO d2 = rpcExceptionTraceService.getById(d.getId());
		Assert.notNull(d2, "测试失败");
		
		d.setRequestId("44444"+d.getRequestId());
		rpcExceptionTraceService.updateByIdAllColumn(d, d.getId());
		
		RpcExceptionTraceDO d3 = rpcExceptionTraceService.getById(d.getId());
		Assert.notNull(d3, "测试失败");
		Assert.isTrue(d3.getRequestId().equals(d.getRequestId()),"测试失败");
		
		
	}

	//@Test
	public void updateByCriteriaSelective() {
		
		RpcExceptionTraceDO d = newRpcExceptionTraceDO();
		d.setRequestId("updateByCriteriaSelective"+d.getRequestId());
		rpcExceptionTraceService.insertSelective(d);
		Assert.notNull(d.getId(), "测试失败");
		RpcExceptionTraceDO d2 = rpcExceptionTraceService.getById(d.getId());
		Assert.notNull(d2, "测试失败");
		
		WhereCriteria whereCriteria=new WhereCriteria();
		Criteria criteria = new Criteria();
		criteria.andEqualTo(d.getRequestId(), "REQUEST_ID");
		whereCriteria.addCriteria(criteria);
		
		d.setRequestId("999"+d.getRequestId());
		rpcExceptionTraceService.updateByCriteriaSelective(d, whereCriteria);
		
		RpcExceptionTraceDO d3 = rpcExceptionTraceService.getById(d.getId());
		Assert.notNull(d3, "测试失败");
		Assert.isTrue(d3.getRequestId().equals(d.getRequestId()),"测试失败");
		
	
	}

	//@Test
	public void updateByIdSelective() {
		
		RpcExceptionTraceDO d = newRpcExceptionTraceDO();
		rpcExceptionTraceService.insertSelective(d);
		Assert.notNull(d.getId(), "测试失败");
		RpcExceptionTraceDO d2 = rpcExceptionTraceService.getById(d.getId());
		Assert.notNull(d2, "测试失败");
		
		d.setRequestId("1111"+d.getRequestId());
		rpcExceptionTraceService.updateByIdSelective(d, d.getId());
		
		RpcExceptionTraceDO d3 = rpcExceptionTraceService.getById(d.getId());
		Assert.notNull(d3, "测试失败");
		Assert.isTrue(d3.getRequestId().equals(d.getRequestId()),"测试失败");
	
	}
	
	private RpcExceptionTraceDO newRpcExceptionTraceDO(){
		RpcExceptionTraceDO d=new RpcExceptionTraceDO();
		
		String requestId = System.currentTimeMillis()+"";	// 请求id
		d.setRequestId(requestId);
		
		String side="side";	// side
		d.setSide(side);
		
		String heapStack="heapStack";	// 异常堆栈
		d.setHeapStack(heapStack);
		
		String errorMessage="errorMessage";	// 异常信息
		d.setErrorMessage(errorMessage);
		
		String errorCode="9999";	// error_code
		d.setErrorCode(errorCode);
		
		return d;
	}
	
	
}
