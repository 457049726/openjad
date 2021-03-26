package com.openjad.orm.mybatis.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.openjad.common.exception.BizException;
import com.openjad.common.util.ParamAssert;
import com.openjad.common.util.StringUtils;
import com.openjad.orm.constant.OrmLogCode;
import com.openjad.orm.criterion.Criteria;
import com.openjad.orm.criterion.SelectCriteria;
import com.openjad.orm.criterion.WhereCriteria;
import com.openjad.orm.mybatis.constant.MybatisLogCode;
import com.openjad.orm.mybatis.utils.EntityUtils;
import com.openjad.orm.service.TreeEntityService;
import com.openjad.orm.vo.DelFlagEO;
import com.openjad.orm.vo.JadBaseTreeEO;
import com.openjad.orm.vo.TreeEO;

@Transactional(readOnly = true)
public class AbstractTreeEntityService<EO extends TreeEO<ID, EO>, ID extends Serializable>
		extends AbstractEntityService<EO, ID> implements TreeEntityService<EO, ID> {

	private static final String PARENT_IDS_SEP = ",";

	/**
	 * 默认不自动删除子节点
	 */
	private static final boolean DEL_SUB = false;

	/**
	 * 默认树的最大深度为10
	 */
	private static final int MAX_DEPTH = 10;

	@Override
	public Integer doDeleteById(ID id) {
		return deleteById(id, DEL_SUB);
	}

	@Override
	public EO findTreeByParentId(ID parentId) {
		return findTreeByParentId(parentId, -1);
	}

	@Override
	public EO findTreeByParentId(ID parentId, int queryDepth) {
		EO eo = null;
		if (parentId != null) {
			eo = getById(parentId);
		} else if (eoMetaInfo != null && eoMetaInfo.getMetaClass() != null) {
			eo = EntityUtils.newEoInstance(eoMetaInfo.getMetaClass());
		}
		if (eo == null) {
			return null;
		}
		setTreeByParentId(eo, parentId, 1, queryDepth);
		return eo;
	}

	private void setTreeByParentId(EO parent, ID parentId, int depth, int queryDepth) {
		if (queryDepth > 0 && queryDepth <= depth) {
			return;
		}
		if (depth > getMaxDepth() + 1) {
			throw new BizException(OrmLogCode.CODE_00004, "超出最大深度[" + getMaxDepth() + "]限制，请检查数据是否错误，比如出现死循环递归");
		}
		List<EO> list = this.findSubList(parentId);
		if (list == null || list.isEmpty()) {
			return;
		}
		List<EO> children = new ArrayList<EO>();
		parent.setChildren(children);
		for (EO sub : list) {
			setTreeByParentId(sub, sub.getId(), depth + 1, queryDepth);
			children.add(sub);
		}
	}


	@Override
	@Transactional(readOnly = false)
	public Integer deleteById(ID id, boolean delSubs) {
		List<EO> subList = findSubList(id);
		if (subList == null || subList.isEmpty()) {
//			return super.deleteById(id);
			return super.doDeleteById(id);//20210221
		} else {
			if (!delSubs) {
				throw new BizException(MybatisLogCode.CODE_00030, "因为存在下级节点而无法删除,parentId:" + id);
			}
			int count = 0;
			//删除子节点
			for (EO subEo : subList) {
				if (subEo != null && subEo.getId() != null) {
					count = count + deleteById(subEo.getId(), delSubs);
				}
			}
			return count;
		}
	}

	@Override
	@Transactional(readOnly = false)
	public Integer deleteByIds(List<ID> ids, boolean delSubs) {
		ParamAssert.notNull(ids, "ids 不能为空");
		int count = 0;
		for (ID id : ids) {
			count = count + deleteById(id, delSubs);
		}
		return count;
	}

	@Override
	public Integer doDeleteByIds(List<ID> ids) {
		return deleteByIds(ids, DEL_SUB);
	}

	@Override
	@Transactional(readOnly = false)
	public Integer deleteByIds(ID[] ids, boolean delSubs) {
		ParamAssert.notNull(ids, "ids 不能为空");
		int count = 0;
		for (ID id : ids) {
			count = count + deleteById(id, delSubs);
		}
		return count;
	}

	@Override
	public Integer doDeleteByIds(ID[] ids) {
		return deleteByIds(ids, DEL_SUB);
	}

	@Override
	@Transactional(readOnly = false)
	public Integer deleteByCriteria(WhereCriteria whereCriteria, boolean delSubs) {
		ParamAssert.notNull(whereCriteria, "whereCriteria 不能为空");
		SelectCriteria selectCriteria = new SelectCriteria();
		selectCriteria.setObjParam(whereCriteria.getObjParam());
		selectCriteria.setOredCriteria(whereCriteria.getOredCriteria());
		List<EO> eoList = listByCriteria(selectCriteria);
		if (eoList == null || eoList.isEmpty()) {
			return 0;
		}
		List<ID> idList = new ArrayList<ID>();
		for (EO eo : eoList) {
			idList.add(eo.getId());
		}
		return deleteByIds(idList, delSubs);

	}

	@Override
	public Integer doDeleteByCriteria(WhereCriteria whereCriteria) {
		return deleteByCriteria(whereCriteria, DEL_SUB);
	}

	private void setParent(EO d, ID id) {
		if (d.getParentId() == null) {
			EO eo = getById(id);
			if (eo != null && (eo instanceof TreeEO)) {
				d.setParentId((ID) ((TreeEO) eo).getParentId());
			}
		}
		setParent(d);
		
	}

	private void setParent(EO d) {
		if (d.getParentId() == null) {
			d.setParentId(null);
			d.setParentIds(null);
		} else {
			if (StringUtils.isBlank(d.getParentIds())) {
				EO parent = getById(d.getParentId());
				if (parent == null) {
					String tableName = eoMetaInfo == null ? null : eoMetaInfo.getTableName();
					String error = "没有找到ID为:" + d.getParentId() + "的记录";
					if (StringUtils.isNotBlank(tableName)) {
						error = error + ",请检查表[" + tableName + "]的数据是否正常";
					}
					throw new BizException(error);
				} else {
					int parentDepth = getParentDepth(parent.getParentIds());
					if (parentDepth >= getMaxDepth()) {
						throw new BizException(OrmLogCode.CODE_00004, "超出最大深度限制:" + getMaxDepth());
					}
					StringBuffer sb = new StringBuffer();
					if (StringUtils.isBlank(parent.getParentIds())) {
						sb.append(PARENT_IDS_SEP);

					} else {
						sb.append(parent.getParentIds());
					}
					sb.append(parent.getId());
					sb.append(PARENT_IDS_SEP);
					d.setParentIds(sb.toString());
				}
			}
		}
		d.setLevel(getLevel(d));
	}

	private int getParentDepth(String parentIds) {
		if (StringUtils.isBlank(parentIds)) {
			return 1;
		}
		String[] ids = parentIds.split(PARENT_IDS_SEP);
		int depth = ids.length - 1;
		if (depth <= 0) {
			return 1;
		} else {
			return depth;
		}
	}
	public Integer getLevel(EO eo) {
		if (eo.getParentIds() != null) {
			String[] levelStr = eo.getParentIds().split(PARENT_IDS_SEP);
			return levelStr.length;
		}
		return 1;
	}

	@Override
	protected int preInsert(EO d) {
		setParent(d);
		return super.preInsert(d);
	}

	protected int preUpdate(EO d) {
		setParent(d);
		return super.preUpdate(d);
	}

	protected int preUpdate(EO d, ID id) {
		setParent(d, id);
		return super.preUpdate(d, id);
	}

	protected int preUpdate(EO d, WhereCriteria whereCriteria) {
		if (d.getParentId() != null) {
			setParent(d);
		}
		return super.preUpdate(d, whereCriteria);
	}


	@Override
	public List<EO> findSubList(ID parentId) {
		return findSubList(parentId, false);
	}

	

	/**
	 * 查询子节点列表
	 * 
	 * @param parentId     parentId
	 * @param continueSubs 是否包含所有子节点
	 * @return res
	 */
	public List<EO> findSubList(ID parentId, boolean continueSubs) {

		if (continueSubs) {
			ParamAssert.notNull(parentId, "parentId 不能为空");
			SelectCriteria selectCriteria = new SelectCriteria();
			Criteria criteria = new Criteria();
			criteria.andLike(PARENT_IDS_SEP + parentId + PARENT_IDS_SEP, "parent_ids");
			selectCriteria.addCriteria(criteria);
			return this.listByCriteria(selectCriteria);
		} else {

			SelectCriteria selectCriteria = new SelectCriteria();
			Criteria criteria = new Criteria();
			if (parentId == null) {
				criteria.andIsNull("parent_id");
			} else {
				criteria.andEqualTo(parentId, "parent_id");
			}
			selectCriteria.addCriteria(criteria);
			List<EO> list = this.listByCriteria(selectCriteria);
//			sort(list);
			return list;
		}
	}

	public List<EO> findTopList() {
		EO eo = findTreeByParentId(null);
		if (eo != null) {
			return eo.getChildren();
		}
		return null;

	}

	private int getMaxDepth() {
		int maxDepth = limitMaxDepth();
		if (maxDepth <= 0) {
			throw new BizException(OrmLogCode.CODE_00003);
		}
		return maxDepth;
	}

	@Override
	public Integer limitMaxDepth() {
		return MAX_DEPTH;
	}

}
