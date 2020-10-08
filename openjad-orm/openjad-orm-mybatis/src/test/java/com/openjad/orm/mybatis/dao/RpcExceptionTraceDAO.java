/**
 */
package com.openjad.orm.mybatis.dao;

import com.openjad.orm.mybatis.dao.EntityDAO;
import com.openjad.orm.mybatis.eo.RpcExceptionTraceDO;
import com.openjad.orm.annotation.JadDao;

/**
 * RpcExceptionTraceDAO
 */	
@JadDao("rpcExceptionTraceDAO")
public interface RpcExceptionTraceDAO extends EntityDAO<RpcExceptionTraceDO,Long> {
}
