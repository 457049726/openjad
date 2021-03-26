package com.openjad.orm.mybatis.service;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.openjad.common.exception.BizException;
import com.openjad.common.page.PageBO;
import com.openjad.common.spring.context.RequestContext;
import com.openjad.logger.api.Logger;
import com.openjad.logger.api.LoggerFactory;
import com.openjad.orm.annotation.DelStrategy;
import com.openjad.orm.constant.OrmLogCode;
import com.openjad.orm.criterion.Criteria;
import com.openjad.orm.criterion.PagedCriteria;
import com.openjad.orm.criterion.SelectCriteria;
import com.openjad.orm.criterion.WhereCriteria;
import com.openjad.orm.dao.EntityDAO;
import com.openjad.orm.mybatis.entity.EoFieldInfo;
import com.openjad.orm.mybatis.entity.EoMetaInfo;
import com.openjad.orm.mybatis.utils.EntityUtils;
import com.openjad.orm.service.EntityService;
import com.openjad.orm.vo.ActiveRecordEO;
import com.openjad.orm.vo.BaseEO;
import com.openjad.orm.vo.DelFlagEO;

/**
 * 实体Service
 *
 * @param <EO> eo
 * @param <ID> id
 */
@Transactional(readOnly = true)
public abstract class AbstractEntityService<EO extends BaseEO, ID extends Serializable>
		implements EntityService<EO, ID>, BeanNameAware, ApplicationContextAware, InitializingBean {

	private static final Logger logger = LoggerFactory.getLogger(AbstractEntityService.class);

	private static final String SERVICE_BEAN_NAME_SUFFIX = "Service";

	private static final String DAO_BEAN_NAME_SUFFIX = "DAO";

	private String serviceBeanName;

	protected EntityDAO<EO, ID> entityDAO;

	protected ApplicationContext applicationContext;

	protected EoMetaInfo eoMetaInfo;

	private Boolean logicDelFlag;

	protected EntityDAO<EO, ID> getDao() {
		if (entityDAO == null) {
			initEntityDao();
		}
		return entityDAO;
	}

	@SuppressWarnings("unchecked")
	protected void initEntityDao() {
		if (serviceBeanName.endsWith(SERVICE_BEAN_NAME_SUFFIX)) {
			int index = serviceBeanName.lastIndexOf(SERVICE_BEAN_NAME_SUFFIX);
			String daoBeanName = serviceBeanName.substring(0, index) + DAO_BEAN_NAME_SUFFIX;
			try {
				this.entityDAO = (EntityDAO<EO, ID>) applicationContext.getBean(daoBeanName);
			} catch (BeansException e) {
				String err = "初始化" + serviceBeanName + "失败,找不到对应dao:" + daoBeanName;
				logger.error(err + "," + e.getMessage(), e);
				throw new RuntimeException(err);
			}
		}
	}

	/**
	 * 是否自动过滤已删除的
	 * 
	 * @return
	 */
	protected boolean needFilterDeleted() {
		return true;
	}

	public List<EO> findByIds(List<ID> ids) {
		Assert.notEmpty(ids, "id列表不能为空");
		SelectCriteria selectCriteria = new SelectCriteria();
		Criteria criteria = new Criteria();
		if (eoMetaInfo.getKeyFieldInfo() == null) {
			throw new BizException("没有为实体[" + eoMetaInfo.getObjName() + "]设置主键");
		}
		criteria.andIn(ids, eoMetaInfo.getKeyFieldInfo().getColumn());
		selectCriteria.addCriteria(criteria);
		return listByCriteria(selectCriteria);
	}

	public List<EO> findByIds(ID[] ids) {
		Assert.notEmpty(ids, "id列表不能为空");
		return findByIds(Arrays.asList(ids));
	}

	public EO findOneByPropertity(Object value, String column) {
		List<EO> list = findByPropertity(value, column);
		if (CollectionUtils.isNotEmpty(list)) {
			return list.get(0);
		} else {
			return null;
		}
	}

	public List<EO> findByPropertitys(Object[] values, String[] columns) {
		Assert.notNull(values, "Value cannot be null");
		Assert.notNull(columns, "Value cannot be null");
		Assert.isTrue(values.length == columns.length, "参数个数不正确");

		SelectCriteria selectCriteria = new SelectCriteria();
		Criteria criteria = new Criteria();

		for (int i = 0; i < values.length; i++) {
			criteria.andEqualTo(values[i], columns[i]);
		}
		selectCriteria.addCriteria(criteria);

		List<EO> eoList = listByCriteria(selectCriteria);
		return eoList;
	}

	public List<EO> findByPropertity(Object value, String column) {
		return findByPropertitys(new Object[] { value }, new String[] { column });
	}

	@Override
	public EO getById(ID id) {
		Assert.notNull(id, "Value for id cannot be null");
		EO eo = getDao().getById(id);
		if (eo != null && needFilterDeleted() && isLogicDel()) {
			DelFlagEO delEo = (DelFlagEO) eo;
			if (DelFlagEO.DELETED.equals(delEo.getDelFlag())) {
				return null;
			}
		}
		return eo;
	}

	protected void wrapWhereCriteria(WhereCriteria whereCriteria) {
		if (needFilterDeleted() && isLogicDel()) {
			for (Criteria criteria : whereCriteria.getOredCriteria()) {
				criteria.andEqualTo(DelFlagEO.NOT_DEL, DelFlagEO.DEL_FLAG_COLUMN);
			}
		}
	}

	protected String getOrderByClause(SelectCriteria selectCriteria) {
		if (selectCriteria == null) {
			return null;
		}
		if (StringUtils.isNotBlank(selectCriteria.getOrderByClause())) {
			return selectCriteria.getOrderByClause();
		}
		if (eoMetaInfo == null) {
			return null;
		}

		StringBuffer orderByBuffer = new StringBuffer();
		Map<String, EoFieldInfo> fieldInfoMap = eoMetaInfo.getFieldInfoMap();
		int i = 0;
		for (Map.Entry<String, EoFieldInfo> ent : fieldInfoMap.entrySet()) {
			EoFieldInfo eoFieldInfo = ent.getValue();
			if (eoFieldInfo.getOrderBy() != null && !"".equals(eoFieldInfo.getOrderBy())) {
				if (i > 0) {
					orderByBuffer.append(",");
				}
				orderByBuffer.append(eoFieldInfo.getOrderBy().trim());
				i++;
			}
		}
		if (orderByBuffer.length() > 0) {
			return orderByBuffer.toString();
		}
		return null;
	}

	@Override
	public PageBO<EO> pageByCriteria(PagedCriteria pagedCriteria) {
		Assert.notNull(pagedCriteria, "Value for criteria cannot be null");
		wrapWhereCriteria(pagedCriteria);
		PageBO<EO> page = new PageBO<EO>(pagedCriteria.getPageNo(), pagedCriteria.getPageSize());

		if (pagedCriteria.getOrderByClause() == null) {
			pagedCriteria.setOrderByClause(getOrderByClause(pagedCriteria));
		}
		List<EO> list = getDao().listByPagedCriteria(pagedCriteria);
		page.setList(list);
		page.setCount(pagedCriteria.getCount());
		return page;
	}

	@Override
	public EO getByCriteria(SelectCriteria selectCriteria) {
		Assert.notNull(selectCriteria, "Value for selectCriteria cannot be null");
		wrapWhereCriteria(selectCriteria);
		List<EO> list = listByCriteria(selectCriteria);
		if (list == null || list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	@Override
	public List<EO> listByCriteria(SelectCriteria selectCriteria) {
		Assert.notNull(selectCriteria, "Value for criteria cannot be null");
		wrapWhereCriteria(selectCriteria);
		if (selectCriteria.getOrderByClause() == null) {
			selectCriteria.setOrderByClause(getOrderByClause(selectCriteria));
		}
		return getDao().listBySelectCriteria(selectCriteria);
	}

	/**
	 * 删除前操作
	 * 
	 * @param id id
	 * @return 返回
	 */
	protected int preDeleteById(ID id) {
		if (!isLogicDel()) {
			return -1;
		}
		return convertToLogicDel(id);
	}

	protected boolean isLogicDel() {
		if (logicDelFlag != null) {
			return logicDelFlag.booleanValue();
		}
		if (eoMetaInfo == null || eoMetaInfo.getDelStrategy() == DelStrategy.PHYSICS_DELETE
				|| !DelFlagEO.class.isAssignableFrom(eoMetaInfo.getMetaClass())) {
			logicDelFlag = false;
		} else {
			logicDelFlag = true;
		}
		return logicDelFlag.booleanValue();
	}

	/**
	 * 转换为逻辑删除
	 * 
	 * @param id id
	 */
	private int convertToLogicDel(ID id) {
		try {
			EO eo = (EO) eoMetaInfo.getMetaClass().newInstance();
			DelFlagEO delEo = (DelFlagEO) eo;
			delEo.setDelFlag(DelFlagEO.DELETED);
			return this.updateByIdSelective(eo, id);
		} catch (Exception e) {
			throw new BizException(OrmLogCode.CODE_00002, "逻辑删除失败,id:" + id, e);
		}

	}

	@Override
	@Transactional(readOnly = false)
	public Integer deleteById(ID id) {
		return doDeleteById(id);
	}

	protected Integer doDeleteById(ID id) {
		Assert.notNull(id, "Value for id cannot be null");
		int pre = this.preDeleteById(id);
		if (pre == -1) {
			return getDao().deleteById(id);
		} else {
			return pre;
		}
	}

	/**
	 * 按id删除
	 * 
	 * @param ids ids
	 * @return res
	 */
	@Override
	@Transactional(readOnly = false)
	public Integer deleteByIds(ID[] ids) {
		return doDeleteByIds(ids);
	}

	protected Integer doDeleteByIds(ID[] ids) {
		Assert.notNull(ids, "Value for ids cannot be null");
		int count = 0;
		for (ID id : ids) {
			count = count + deleteById(id);
		}
		return count;
	}

	/**
	 * 按id列表删除
	 * 
	 * @param ids ids
	 * @return res
	 */
	@Transactional(readOnly = false)
	public Integer deleteByIds(List<ID> ids) {
		return doDeleteByIds(ids);
	}

	protected Integer doDeleteByIds(List<ID> ids) {

		Assert.notNull(ids, "Value for ids cannot be null");
		int count = 0;
		for (ID id : ids) {
			count = count + deleteById(id);
		}
		return count;

	}

	/**
	 * 删除前操作
	 * 
	 * @param id id
	 * @return 返回
	 */
	protected int preDeleteByCriteria(WhereCriteria whereCriteria) {
		if (!isLogicDel()) {
			return -1;
		}
		return convertToLogicDel(whereCriteria);
	}

	private int convertToLogicDel(WhereCriteria whereCriteria) {
		try {
			EO eo = (EO) eoMetaInfo.getMetaClass().newInstance();
			DelFlagEO delEo = (DelFlagEO) eo;
			delEo.setDelFlag(DelFlagEO.DELETED);
			return this.updateByCriteriaSelective(eo, whereCriteria);
		} catch (Exception e) {
			throw new BizException(OrmLogCode.CODE_00002, "逻辑删除失败,whereCriteria:" + whereCriteria, e);
		}
	}

	@Override
	@Transactional(readOnly = false)
	public Integer deleteByCriteria(WhereCriteria whereCriteria) {
		return doDeleteByCriteria(whereCriteria);
	}

	protected Integer doDeleteByCriteria(WhereCriteria whereCriteria) {
		Assert.notNull(whereCriteria, "Value for criteria cannot be null");
		int pre = preDeleteByCriteria(whereCriteria);
		if (pre == -1) {
			return getDao().deleteByCriteria(whereCriteria);
		} else {
			return pre;
		}

	}

	protected void afterInsert(EO d) {

	}

	/**
	 * 插入前操作
	 * 
	 * @param d eo
	 * @return 返回
	 */
	protected int preInsert(EO d) {
		if (isActiveRecordEO(d)) {
			ActiveRecordEO eo = (ActiveRecordEO) d;
			if (eo.getCreateTime() == null) {
				eo.setCreateTime(new Date());
			}
			if (StringUtils.isBlank(eo.getCreateUser())) {
				eo.setCreateUser(RequestContext.getCurrentUser());
			}
		}
		if (isLogicDel()) {
			DelFlagEO delEo = (DelFlagEO) d;
			delEo.setDelFlag(DelFlagEO.NOT_DEL);
		}
		return -1;
	}

	protected boolean isActiveRecordEO(EO d) {
		return ActiveRecordEO.class.isAssignableFrom(d.getClass());
	}

	@Override
	@Transactional(readOnly = false)
	public Integer insertAllColumn(EO d) {
		Assert.notNull(d, "Value for d cannot be null");
		int pre = preInsert(d);
		if (pre == -1) {
			int res = getDao().insertAllColumn(d);
			afterInsert(d);
			return res;
		} else {
			return pre;
		}
	}

	@Override
	@Transactional(readOnly = false)
	public Integer insertSelective(EO d) {
		Assert.notNull(d, "Value for d cannot be null");
		int pre = preInsert(d);
		if (pre == -1) {
			int res = getDao().insertSelective(d);
			afterInsert(d);
			return res;
		} else {
			return pre;
		}

	}

	protected int preUpdate(EO d, WhereCriteria whereCriteria) {
		return preUpdate(d);
	}

	protected int preUpdate(EO d, ID id) {
		return preUpdate(d);
	}

	protected int preUpdate(EO d) {
		if (isActiveRecordEO(d)) {
			ActiveRecordEO eo = (ActiveRecordEO) d;
			if (eo.getUpdateTime() == null) {
				eo.setUpdateTime(new Date());
			}
			if (StringUtils.isBlank(eo.getUpdateUser())) {
				eo.setUpdateUser(RequestContext.getCurrentUser());
			}
		}
		if (isLogicDel()) {
			DelFlagEO delEo = (DelFlagEO) d;
			delEo.setDelFlag(DelFlagEO.NOT_DEL);
		}
		return -1;
	}

	@Override
	@Transactional(readOnly = false)
	public Integer updateByCriteriaAllColumn(EO d, WhereCriteria whereCriteria) {
		Assert.notNull(d, "Value for d cannot be null");
		Assert.notNull(whereCriteria, "Value for criteria cannot be null");
		int pre = preUpdate(d);
		if (pre == -1) {
			return getDao().updateByCriteriaAllColumn(d, whereCriteria);
		} else {
			return pre;
		}

	}

	@Override
	@Transactional(readOnly = false)
	public Integer updateByIdAllColumn(EO d, ID id) {
		Assert.notNull(d, "Value for d cannot be null");
		Assert.notNull(id, "Value for id cannot be null");
		int pre = preUpdate(d, id);
		if (pre == -1) {
			return getDao().updateByIdAllColumn(d, id);
		} else {
			return pre;
		}
	}

	@Override
	@Transactional(readOnly = false)
	public Integer updateByCriteriaSelective(EO d, WhereCriteria whereCriteria) {
		Assert.notNull(d, "Value for d cannot be null");
		Assert.notNull(whereCriteria, "Value for criteria cannot be null");
		int pre = preUpdate(d, whereCriteria);
		if (pre == -1) {
			return getDao().updateByCriteriaSelective(d, whereCriteria);
		} else {
			return pre;
		}
	}

	@Override
	@Transactional(readOnly = false)
	public Integer updateByIdSelective(EO d, ID id) {
		Assert.notNull(d, "Value for d cannot be null");
		Assert.notNull(id, "Value for id cannot be null");
		int pre = preUpdate(d, id);
		if (pre == -1) {
			return getDao().updateByIdSelective(d, id);
		} else {
			return pre;
		}
	}

	@Override
	public void setBeanName(String name) {
		this.serviceBeanName = name;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initEntityDao();
		Class eoClass = findEOClass();
		if (eoClass != null) {
			this.eoMetaInfo = EntityUtils.getEoInfo(eoClass);
		}
	}

	private Class findEOClass(Class clazz) {
		if (!EntityService.class.isAssignableFrom(clazz)) {
			return null;
		}
		Type[] interfaces = clazz.getGenericInterfaces();
		if (interfaces == null || interfaces.length == 0) {
			return null;
		}
		for (Type type : interfaces) {
			if (type instanceof ParameterizedType) {
				Type[] params = ((ParameterizedType) type).getActualTypeArguments();
				if (params != null && params.length > 0) {
					Type eoType = params[0];
					if ((eoType instanceof Class) && BaseEO.class.isAssignableFrom((Class) eoType)) {
						return (Class) eoType;
					}
				}
			}
			if (!(type instanceof Class)) {
				continue;
			}
			Class typeClass = (Class) type;
			Class res = findEOClass(typeClass);
			if (res != null) {
				return res;
			}
		}
		return null;
	}

	private Class findEOClass() {
		Class clazz = this.getClass();
		return findEOClass(clazz);
	}

}
