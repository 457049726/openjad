/**
 */
package com.openjad.orm.mybatis.dao;

import com.openjad.orm.annotation.JadDao;
import com.openjad.orm.dao.EntityDAO;
import com.openjad.orm.mybatis.eo.RpcExceptionTraceDO;

/**
 * RpcExceptionTraceDAO
 */	
@JadDao("rpcExceptionTraceDAO")
public interface RpcExceptionTraceDAO extends EntityDAO<RpcExceptionTraceDO,Long> {
}
