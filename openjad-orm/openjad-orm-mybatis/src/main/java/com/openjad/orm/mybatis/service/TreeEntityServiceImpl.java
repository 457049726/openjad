package com.openjad.orm.mybatis.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.openjad.common.exception.BizException;
import com.openjad.common.util.AssertUtil;
import com.openjad.common.util.StringUtils;
import com.openjad.common.util.reflection.ReflectUtils;
import com.openjad.logger.api.Logger;
import com.openjad.logger.api.LoggerFactory;
import com.openjad.orm.criterion.Criteria;
import com.openjad.orm.criterion.SelectCriteria;
import com.openjad.orm.criterion.WhereCriteria;
import com.openjad.orm.mybatis.constant.MybatisLogCode;
import com.openjad.orm.service.TreeEntityService;
import com.openjad.orm.vo.JadTreeEO;

@Transactional(readOnly = true)
public class TreeEntityServiceImpl<EO extends JadTreeEO<EO, ID>, ID extends Serializable>
		extends AbstractEntityService<EO, ID> implements TreeEntityService<EO, ID> {

	private static final Logger logger = LoggerFactory.getLogger(TreeEntityServiceImpl.class);

	@Override
	@Transactional(readOnly = false)
	public Integer deleteById(ID id) {
		return deleteById(id, true);
	}

	@Override
	@Transactional(readOnly = false)
	public Integer deleteById(ID id, boolean delSubs) {
		List<EO> subList = findSubList(id);
		if (subList == null || subList.isEmpty()) {
			return getDao().deleteById(id);
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
		AssertUtil.notNull(ids, "ids 不能为空");
		int count = 0;
		for (ID id : ids) {
			count = count + deleteById(id, delSubs);
		}
		return count;
	}

	@Override
	@Transactional(readOnly = false)
	public Integer deleteByIds(List<ID> ids) {
		return deleteByIds(ids, true);
	}

	@Override
	@Transactional(readOnly = false)
	public Integer deleteByIds(ID[] ids, boolean delSubs) {
		AssertUtil.notNull(ids, "ids 不能为空");
		int count = 0;
		for (ID id : ids) {
			count = count + deleteById(id, delSubs);
		}
		return count;
	}

	@Override
	@Transactional(readOnly = false)
	public Integer deleteByIds(ID[] ids) {
		return deleteByIds(ids, true);
	}

	@Override
	@Transactional(readOnly = false)
	public Integer deleteByCriteria(WhereCriteria whereCriteria, boolean delSubs) {
		AssertUtil.notNull(whereCriteria, "whereCriteria 不能为空");
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
	@Transactional(readOnly = false)
	public Integer deleteByCriteria(WhereCriteria whereCriteria) {
		return deleteByCriteria(whereCriteria, true);
	}
	
	
	private void setParent(EO d){
		if (d.getParentId() == null ) {
			d.setParentId(getDefRootId(d));
			d.setParentIds("");
		} else {
			if(StringUtils.isBlank(d.getParentIds())){
				EO parent = getById(d.getParentId());
				if(parent==null){
					d.setParentId(getDefRootId(d));
					d.setParentIds("");
				}else{
					d.setParentIds(parent.getParentIds()+parent.getId()+",");
				}
			}
		}
	}
	
	@Override
	public Integer insertAllColumn(EO d) {
		setParent(d);
		return super.insertAllColumn(d);
	}

	public ID getParentId(EO d) {
		ID id = null;
		if (d.getParentId() != null) {
			id = d.getParentId();
		}
		if (id == null) {
			id = getDefRootId(d);
		}
		return id;
	}

	/**
	 *  默认跟节点，子类可覆盖
	 * @param d d
	 * @return res
	 */
	 
	@SuppressWarnings("unchecked")
	public ID getDefRootId(EO d) {
		Class<ID> clazz = ReflectUtils.getSuperClassGenricType(d.getClass());
		try {
			if (Number.class.isAssignableFrom(clazz)) {
				return clazz.newInstance();
			} else if (String.class.isAssignableFrom(clazz)) {
				return (ID) "";
			} else if (Date.class.isAssignableFrom(clazz)) {
				return (ID) (new Date());
			} else {
				logger.warn(MybatisLogCode.CODE_00017,
						"无法识别主键类型,serviceClassName:" + this.getClass().getName());
			}
		} catch (Exception e) {
			logger.warn(MybatisLogCode.CODE_00017,
					"无法识别主键类型,serviceClassName:" + this.getClass().getName(), e);
		}
		return null;
	}

	@Override
	public Integer insertSelective(EO d) {
		return super.insertSelective(d);
	}

	@Override
	public Integer updateByCriteriaAllColumn(EO d, WhereCriteria whereCriteria) {
		setParent(d);
		return super.updateByCriteriaAllColumn(d, whereCriteria);
	}

	@Override
	public Integer updateByIdAllColumn(EO d, ID id) {
		setParent(d);
		return super.updateByIdAllColumn(d, id);
	}

	@Override
	public Integer updateByCriteriaSelective(EO d, WhereCriteria whereCriteria) {
		return super.updateByCriteriaSelective(d, whereCriteria);
	}

	@Override
	public Integer updateByIdSelective(EO d, ID id) {
		return super.updateByIdSelective(d, id);
	}


	@Override
	public List<EO> findSubList(ID parentId) {
		AssertUtil.notNull(parentId, "parentId 不能为空");
		SelectCriteria selectCriteria = new SelectCriteria();
		Criteria criteria = new Criteria();
		criteria.andEqualTo(parentId, "parent_id");
		selectCriteria.addCriteria(criteria);
		return this.listByCriteria(selectCriteria);
	}
	
	
	/**
	 * 查询子节点列表
	 * @param parentId parentId
	 * @param continueSubs 是否包含所有子节点
	 * @return res
	 */
	public List<EO> findSubList(ID parentId,boolean continueSubs){
		AssertUtil.notNull(parentId, "parentId 不能为空");
		if(continueSubs){
			SelectCriteria selectCriteria = new SelectCriteria();
			Criteria criteria = new Criteria();
			criteria.andLike(","+parentId+",", "parent_ids");
			selectCriteria.addCriteria(criteria);
			return this.listByCriteria(selectCriteria);
		}else{
			return findSubList(parentId);
		}
	}


}
