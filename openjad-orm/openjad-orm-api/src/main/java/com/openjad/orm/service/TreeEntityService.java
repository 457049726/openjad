package com.openjad.orm.service;

import java.io.Serializable;
import java.util.List;

import com.openjad.orm.criterion.WhereCriteria;
import com.openjad.orm.vo.TreeEO;

public interface TreeEntityService<EO extends TreeEO<ID, EO>, ID extends Serializable> extends EntityService<EO, ID> {

	/**
	 * 查询子节点列表
	 * 
	 * @param parentId parentId
	 * @return res
	 */
	List<EO> findSubList(ID parentId);
	
	/**
	 * 获取树级别
	 * @param eo
	 * @return
	 */
	Integer getLevel(EO eo) ;

	/**
	 * 查询子节点列表
	 * 
	 * @param parentId     parentId
	 * @param continueSubs 是否包含所有子节点
	 * @return res
	 */
	List<EO> findSubList(ID parentId, boolean continueSubs);

	/**
	 * 查询根节点
	 * @return
	 */
	List<EO> findTopList();


	/**
	 * 跟据父ID查找所有，并组织成一颗树
	 * 
	 * @param parentId 父id
	 * @return 返回树
	 */
	EO findTreeByParentId(ID parentId);

	/**
	 * 跟据父ID查找所有，并组织成一颗树
	 * 
	 * @param parentId   父id
	 * @param queryDepth 最大深度
	 * @return 返回树
	 */
	EO findTreeByParentId(ID parentId, int queryDepth);

	/**
	 * 跟据 id删除
	 * 
	 * @param id      被删除的id
	 * @param delSubs 是否连子节点一起删除
	 * @return 删除结果
	 */
	Integer deleteById(ID id, boolean delSubs);

	/**
	 * 批量删除
	 * 
	 * @param ids     被删除的id列表
	 * @param delSubs 是否连子节点一起删除
	 * @return 删除结果
	 */
	Integer deleteByIds(ID[] ids, boolean delSubs);

	/**
	 * 批量删除
	 * 
	 * @param ids     ids
	 * @param delSubs delSubs
	 * @return 删除结果
	 */
	Integer deleteByIds(List<ID> ids, boolean delSubs);

	/**
	 * 按条件批量删除
	 * 
	 * @param whereCriteria whereCriteria
	 * @param delSubs       delSubs
	 * @return res
	 */
	Integer deleteByCriteria(WhereCriteria whereCriteria, boolean delSubs);

	/**
	 * 限制最大深度
	 * 
	 * @return
	 */
	Integer limitMaxDepth();
}
