package com.openjad.orm.mybatis.service;

import java.util.List;

import com.openjad.orm.mybatis.eo.RpcExceptionTraceDO;
import com.openjad.orm.criterion.PagedCriteria;
import com.openjad.orm.criterion.SelectCriteria;
import com.openjad.orm.criterion.WhereCriteria;

public interface RpcExceptionTraceService {
	
	/**
	 * 跟据id查找
	 * @param id 
	 * @return
	 */
	RpcExceptionTraceDO getById(Long id);
	
	/**
	 * 按条件分页查找
	 * @param criteria
	 * @return
	 */
	List<RpcExceptionTraceDO> listByPagedCriteria(PagedCriteria pagedCriteria);
	
	/**
	 * 按条件查找
	 * @param criteria
	 * @return
	 */
	List<RpcExceptionTraceDO> listBySelectCriteria(SelectCriteria selectCriteria);
	
	/**
	 * 按id删除
	 * @param id
	 * @return
	 */
	Integer deleteById(Long id);
	
	/**
	 * 按条件删除
	 * @param id
	 * @return
	 */
	Integer deleteByCriteria(WhereCriteria whereCriteria);
	
	/**
	 * 插入(全字段插入)
	 * @param eo
	 */
	Integer insertAllColumn(RpcExceptionTraceDO d);
	
	/**
	 * 插入(只插入非空字段)
	 * @param eo
	 */
	Integer insertSelective(RpcExceptionTraceDO d);
	
	/**
	 * 按条件更新(全字段更新)
	 * @param eo 
	 */
	Integer updateByCriteriaAllColumn(RpcExceptionTraceDO d,WhereCriteria whereCriteria);
	
	/**
	 * 按id更新(全字段更新)
	 * @param eo
	 */
	Integer updateByIdAllColumn(RpcExceptionTraceDO d,Long id);
	
	/**
	 * 按条件更新(只更新非空字段)
	 * @param eo
	 */
	Integer updateByCriteriaSelective(RpcExceptionTraceDO d,WhereCriteria whereCriteria);
	
	/**
	 * 按id更新(只更新非空字段)
	 * @param eo
	 */
	Integer updateByIdSelective(RpcExceptionTraceDO d,Long id);
	

	
}
