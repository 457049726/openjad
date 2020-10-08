package com.openjad.orm.mybatis.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openjad.orm.mybatis.dao.RpcExceptionTraceDAO;
import com.openjad.orm.mybatis.eo.RpcExceptionTraceDO;
import com.openjad.orm.mybatis.service.RpcExceptionTraceService;
import com.openjad.logger.api.Logger;
import com.openjad.logger.api.LoggerFactory;
import com.openjad.orm.criterion.PagedCriteria;
import com.openjad.orm.criterion.SelectCriteria;
import com.openjad.orm.criterion.WhereCriteria;

@Service("rpcExceptionTraceService")
public class RpcExceptionTraceServiceImpl implements RpcExceptionTraceService{
	
	private static final Logger logger = LoggerFactory.getLogger(RpcExceptionTraceServiceImpl.class);

	@Autowired
	private RpcExceptionTraceDAO rpcExceptionTraceDAO;
	
	/**
	 * 跟据id查找
	 * @param id 	
	 * @return
	 */
	public RpcExceptionTraceDO getById(Long id){
		return rpcExceptionTraceDAO.getById(id);
	}

	@Override
	public List<RpcExceptionTraceDO> listByPagedCriteria(PagedCriteria pagedCriteria) {
		return rpcExceptionTraceDAO.listByPagedCriteria(pagedCriteria);
	}

	@Override
	public List<RpcExceptionTraceDO> listBySelectCriteria(SelectCriteria selectCriteria) {
		return rpcExceptionTraceDAO.listBySelectCriteria(selectCriteria);
	}

	@Override
	public Integer deleteById(Long id) {
		return rpcExceptionTraceDAO.deleteById(id);
	}

	@Override
	public Integer deleteByCriteria(WhereCriteria whereCriteria) {
		// TODO Auto-generated method stub
		return rpcExceptionTraceDAO.deleteByCriteria(whereCriteria);
	}

	@Override
	public Integer insertAllColumn(RpcExceptionTraceDO d) {
		// TODO Auto-generated method stub
		return rpcExceptionTraceDAO.insertAllColumn(d);
	}

	@Override
	public Integer insertSelective(RpcExceptionTraceDO d) {
		// TODO Auto-generated method stub
		return rpcExceptionTraceDAO.insertSelective(d);
	}

	@Override
	public Integer updateByCriteriaAllColumn(RpcExceptionTraceDO d, WhereCriteria whereCriteria) {
		// TODO Auto-generated method stub
		return rpcExceptionTraceDAO.updateByCriteriaAllColumn(d, whereCriteria);
	}

	@Override
	public Integer updateByIdAllColumn(RpcExceptionTraceDO d, Long id) {
		// TODO Auto-generated method stub
		return rpcExceptionTraceDAO.updateByIdAllColumn(d, id);
	}

	@Override
	public Integer updateByCriteriaSelective(RpcExceptionTraceDO d, WhereCriteria whereCriteria) {
		// TODO Auto-generated method stub
		return rpcExceptionTraceDAO.updateByCriteriaSelective(d, whereCriteria);
	}

	@Override
	public Integer updateByIdSelective(RpcExceptionTraceDO d, Long id) {
		// TODO Auto-generated method stub
		return rpcExceptionTraceDAO.updateByIdSelective(d, id);
	}
	

	


}
