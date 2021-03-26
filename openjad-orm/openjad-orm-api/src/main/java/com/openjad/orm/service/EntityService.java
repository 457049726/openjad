package com.openjad.orm.service;

import java.io.Serializable;
import java.util.List;

import com.openjad.common.page.PageBO;
import com.openjad.orm.criterion.PagedCriteria;
import com.openjad.orm.criterion.SelectCriteria;
import com.openjad.orm.criterion.WhereCriteria;
import com.openjad.orm.vo.BaseEO;

public interface EntityService<EO extends BaseEO, ID extends Serializable> extends BaseService {

	/**
	 * 跟据id查找
	 * 
	 * @param id id
	 * @return eo
	 */
	EO getById(ID id);
	
	List<EO> findByIds(List<ID> ids);
	List<EO> findByIds(ID[] ids);
	
	EO findOneByPropertity(Object value,String column);
	List<EO> findByPropertitys(Object[] values,String[] columns);
	List<EO> findByPropertity(Object value,String column);
	
	
	/**
	 * 按条件分页查找
	 * 
	 * @param pagedCriteria pagedCriteria
	 * @return page
	 */
	PageBO<EO> pageByCriteria(PagedCriteria pagedCriteria);

	/**
	 * 按条件查找
	 * 
	 * @param selectCriteria selectCriteria
	 * @return list
	 */
	List<EO> listByCriteria(SelectCriteria selectCriteria);

	/**
	 * 按条件查找
	 * 
	 * @param selectCriteria selectCriteria
	 * @return eo
	 */
	EO getByCriteria(SelectCriteria selectCriteria);

	/**
	 * 按id删除
	 * 
	 * @param id id
	 * @return 行数
	 */
	Integer deleteById(ID id);

	/**
	 * 按id删除
	 * 
	 * @param ids ids
	 * @return 行数
	 */
	Integer deleteByIds(ID[] ids);

	/**
	 * 按id列表删除
	 * 
	 * @param ids ids
	 * @return 行数
	 */
	Integer deleteByIds(List<ID> ids);

	/**
	 * 按条件删除
	 * 
	 * @param whereCriteria whereCriteria
	 * @return 行数
	 */
	Integer deleteByCriteria(WhereCriteria whereCriteria);

	/**
	 * 插入(全字段插入)
	 * 
	 * @param d eo
	 * @return 行数
	 */
	Integer insertAllColumn(EO d);

	/**
	 * 插入(只插入非空字段)
	 * 
	 * @param d d
	 * @return 行数
	 */
	Integer insertSelective(EO d);

	/**
	 * 按条件更新(全字段更新)
	 * 
	 * @param d             eo
	 * @param whereCriteria whereCriteria
	 * @return 行数
	 */
	Integer updateByCriteriaAllColumn(EO d, WhereCriteria whereCriteria);

	/**
	 * 按id更新(全字段更新)
	 * 
	 * @param d eo
	 * @param id id
	 * @return 行数
	 */
	Integer updateByIdAllColumn(EO d, ID id);

	/**
	 * 按条件更新(只更新非空字段)
	 * 
	 * @param d eo
	 *  @param whereCriteria whereCriteria
	 *  @return 行数
	 */
	Integer updateByCriteriaSelective(EO d, WhereCriteria whereCriteria);

	/**
	 * 按id更新(只更新非空字段)
	 * 
	 * @param d eo
	 * @param id id
	 * @return 行数
	 */
	Integer updateByIdSelective(EO d, ID id);

}
