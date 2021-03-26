package com.openjad.orm.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.openjad.orm.criterion.PagedCriteria;
import com.openjad.orm.criterion.SelectCriteria;
import com.openjad.orm.criterion.WhereCriteria;
import com.openjad.orm.dao.BaseDAO;

/**
 * 实体类DAO
 * 
 *  @author hechuan
 *
 */
public interface EntityDAO <EO extends Object,ID extends Serializable> extends BaseDAO{

	
	/**
	 * 跟据id查找
	 * @param id  id
	 * @return res
	 */
	EO getById(ID id);
	
	/**
	 * 按条件分页查找
	 * @param pagedCriteria pagedCriteria
	 * @return res
	 */
	List<EO> listByPagedCriteria(PagedCriteria pagedCriteria);
	
	/**
	 * 按条件查找
	 * @param selectCriteria selectCriteria
	 * @return res
	 */
	List<EO> listBySelectCriteria(SelectCriteria selectCriteria);
	
	/**
	 * 按id删除
	 * @param id id
	 * @return res
	 */
	Integer deleteById(ID id);
	
	/**
	 * 按条件删除
	 * @param whereCriteria whereCriteria 
	 * @return res
	 */
	Integer deleteByCriteria(WhereCriteria whereCriteria);
	
	/**
	 * 插入(全字段插入)
	 * @param d d
	 * @return res
	 */
	Integer insertAllColumn(EO d);
	
	/**
	 * 插入(只插入非空字段)
	 * @param d d
	 *  @return res
	 */
	Integer insertSelective(EO d);
	
	/**
	 *  按条件更新(全字段更新)
	 * @param d d
	 * @param whereCriteria whereCriteria
	 * @return res
	 */
	Integer updateByCriteriaAllColumn(@Param("record") EO d,@Param("whereCriteria") WhereCriteria whereCriteria);
	
	/**
	 * 按id更新(全字段更新)
	 * @param d d
	 * @param id id
	 * @return res
	 */
	Integer updateByIdAllColumn(@Param("record")EO d,@Param("id")ID id);
	
	/**
	 * 按条件更新(只更新非空字段)
	 * @param d d
	 * @param whereCriteria whereCriteria
	 * @return res
	 */
	Integer updateByCriteriaSelective(@Param("record") EO d,@Param("whereCriteria") WhereCriteria whereCriteria);
	
	/**
	 * 按id更新(只更新非空字段)
	 * @param d d
	 * @param id id
	 * @return res
	 */
	Integer updateByIdSelective(@Param("record")EO d,@Param("id")ID id);
	


}
