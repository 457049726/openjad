package com.openjad.orm.service;

import java.io.Serializable;
import java.util.List;

import com.openjad.orm.criterion.WhereCriteria;
import com.openjad.orm.vo.TreeEO;

public interface TreeEntityService<EO extends TreeEO<EO,ID>, ID extends Serializable> extends EntityService<EO, ID> {

	/**
	 * 查询子节点列表
	 * 
	 * @param parentId parentId 
	 * @return res
	 */
	public List<EO> findSubList(ID parentId);
	

	/**
	 * 查询子节点列表
	 * @param parentId parentId
	 * @param continueSubs 是否包含所有子节点
	 * @return res
	 */
	public List<EO> findSubList(ID parentId,boolean continueSubs);


	/**
	 * 跟据 id删除
	 * @param id 被删除的id
	 * @param delSubs 是否连子节点一起删除
	 * @return 删除结果
	 */
	public Integer deleteById(ID id,boolean delSubs);
	
	/**
	 * 批量删除 
	 * @param ids 被删除的id列表
	 * @param delSubs 是否连子节点一起删除
	 * @return 删除结果
	 */
	public Integer deleteByIds(ID[] ids,boolean delSubs);
	
	/**
	 * 批量删除
	 * @param ids ids
	 * @param delSubs delSubs
	 * @return 删除结果
	 */
	public Integer deleteByIds(List<ID> ids,boolean delSubs);
	
	/**
	 * 按条件批量删除
	 * @param whereCriteria whereCriteria
	 * @param delSubs delSubs
	 * @return res
	 */
	public Integer deleteByCriteria(WhereCriteria whereCriteria,boolean delSubs);
}
