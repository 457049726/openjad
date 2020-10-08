package com.openjad.orm.mybatis.service;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.openjad.common.page.PageBO;
import com.openjad.orm.mybatis.dao.EntityDAO;
import com.openjad.orm.criterion.PagedCriteria;
import com.openjad.orm.criterion.SelectCriteria;
import com.openjad.orm.criterion.WhereCriteria;
import com.openjad.orm.service.EntityService;
import com.openjad.orm.vo.BaseEO;

/**
 * 实体Service
 *
 * @param <EO> eo
 * @param <ID> id
 */
@Transactional(readOnly = true)
public abstract class AbstractEntityService<EO extends BaseEO,ID extends Serializable> 
	implements EntityService<EO,ID>, BeanNameAware,ApplicationContextAware,InitializingBean {
	
	private static final Logger logger = LoggerFactory.getLogger(AbstractEntityService.class);
	
	private static final String SERVICE_BEAN_NAME_SUFFIX="Service";
	
	private static final String DAO_BEAN_NAME_SUFFIX="DAO";
	
	private String serviceBeanName;
	
	protected EntityDAO<EO,ID> entityDAO;
	
	protected ApplicationContext applicationContext;
	
	protected EntityDAO<EO,ID> getDao(){
		if(entityDAO==null){
			initEntityDao();
		}
		return entityDAO;
	}
	
	@SuppressWarnings("unchecked")
	protected void initEntityDao(){
		
		
		if(serviceBeanName.endsWith(SERVICE_BEAN_NAME_SUFFIX)){
			
			int index=serviceBeanName.lastIndexOf(SERVICE_BEAN_NAME_SUFFIX);
			
			String daoBeanName = serviceBeanName.substring(0,index)+DAO_BEAN_NAME_SUFFIX;
			
			try {
				this.entityDAO=(EntityDAO<EO,ID>)applicationContext.getBean(daoBeanName);
				
			} catch (BeansException e) {
				String err="初始化"+serviceBeanName+"失败,找不到对应dao:"+daoBeanName;
				logger.error(err+","+e.getMessage(),e);
				throw new RuntimeException(err);
			}
			
		}
	}
	
	
	
	
	
	@Override
	public EO getById(ID id) {
		Assert.notNull(id,"Value for id cannot be null");
		return getDao().getById(id);
	}

	
	
	@Override
	public PageBO<EO> pageByCriteria(PagedCriteria pagedCriteria) {
		
		Assert.notNull(pagedCriteria,"Value for criteria cannot be null");
		
		PageBO<EO>page=new PageBO<EO>(pagedCriteria.getPageNo(),pagedCriteria.getPageSize());
		
		List<EO>list=getDao().listByPagedCriteria(pagedCriteria);
		
		page.setList(list);
		page.setCount(pagedCriteria.getCount());
		
		return page;
	}
	
	@Override
	public EO getByCriteria(SelectCriteria selectCriteria) {
		List<EO> list= listByCriteria(selectCriteria);
		if(list==null){
			return null;
		}
		return list.get(0);
	}
	
	@Override
	public List<EO> listByCriteria(SelectCriteria selectCriteria) {
		Assert.notNull(selectCriteria,"Value for criteria cannot be null");
		return getDao().listBySelectCriteria(selectCriteria);
	}
	

	@Override
	@Transactional(readOnly = false)
	public Integer deleteById(ID id) {
		Assert.notNull(id,"Value for id cannot be null");
		return getDao().deleteById(id);
	}
	
	/**
	 * 按id删除
	 * @param ids ids
	 * @return res
	 */
	@Override
	@Transactional(readOnly = false)
	public Integer deleteByIds(ID[] ids){
		if (ids == null ) {
			throw new IllegalArgumentException("ids 不能为空");
		}
		int count = 0;
		for(ID id:ids){
			count = count+deleteById(id);
		}
		return count;
		
	}
	
	
	/**
	 * 按id列表删除
	 * @param ids ids
	 * @return res
	 */
	public Integer deleteByIds(List<ID> ids){
		if (ids == null ) {
			throw new IllegalArgumentException("ids 不能为空");
		}
		int count = 0;
		for(ID id:ids){
			count = count+deleteById(id);
		}
		return count;
	}
	
	

	@Override
	@Transactional(readOnly = false)
	public Integer deleteByCriteria(WhereCriteria whereCriteria) {
		Assert.notNull(whereCriteria,"Value for criteria cannot be null");
		return getDao().deleteByCriteria(whereCriteria);
	}

	@Override
	@Transactional(readOnly = false)
	public Integer insertAllColumn(EO d) {
		Assert.notNull(d,"Value for d cannot be null");
		return getDao().insertAllColumn(d);
	}

	@Override
	@Transactional(readOnly = false)
	public Integer insertSelective(EO d) {
		Assert.notNull(d,"Value for d cannot be null");
		return getDao().insertSelective(d);
	}

	@Override
	@Transactional(readOnly = false)
	public Integer updateByCriteriaAllColumn(EO d, WhereCriteria whereCriteria) {
		Assert.notNull(d,"Value for d cannot be null");
		Assert.notNull(whereCriteria,"Value for criteria cannot be null");
		return getDao().updateByCriteriaAllColumn(d, whereCriteria);
	}

	@Override
	@Transactional(readOnly = false)
	public Integer updateByIdAllColumn(EO d, ID id) {
		Assert.notNull(d,"Value for d cannot be null");
		Assert.notNull(id,"Value for id cannot be null");
		return getDao().updateByIdAllColumn(d, id);
	}

	@Override
	@Transactional(readOnly = false)
	public Integer updateByCriteriaSelective(EO d, WhereCriteria whereCriteria) {
		Assert.notNull(d,"Value for d cannot be null");
		Assert.notNull(whereCriteria,"Value for criteria cannot be null");
		return getDao().updateByCriteriaSelective(d, whereCriteria);
	}

	@Override
	@Transactional(readOnly = false)
	public Integer updateByIdSelective(EO d, ID id) {
		Assert.notNull(d,"Value for d cannot be null");
		Assert.notNull(id,"Value for id cannot be null");
		
		
		return getDao().updateByIdSelective(d, id);
	}
	

	@Override
	public void setBeanName(String name) {
		this.serviceBeanName=name;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext=applicationContext;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initEntityDao();
	}

}
