package com.openjad.orm.mybatis.entity;

import java.io.Serializable;
import java.util.List;


/**
 * 要执行的sql上下文
 * @author hechuan
 *
 */
@SuppressWarnings("serial")
public abstract class SqlContext implements Serializable {

	
	/**
	 * 实体元信息
	 */
	protected EoMetaInfo ei;
	
	/**
	 * sql执行参数
	 */
	@SuppressWarnings("rawtypes")
	protected List params;
	
	/**
	 * 默认别名
	 */
	protected String alias;
	
	/**
	 * 关联sql
	 */
	protected List<String> rcSqlList;
	
	/**
	 * where条件
	 */
	protected String whereSql ;
	
	/**
	 * @return sql语句
	 */
	public abstract String getSql();
	
	
	public EoMetaInfo getEi() {
		return ei;
	}

	public void setEi(EoMetaInfo ei) {
		this.ei = ei;
	}

	public List getParams() {
		return params;
	}

	public void setParams(List params) {
		this.params = params;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public List<String> getRcSqlList() {
		return rcSqlList;
	}

	public void setRcSqlList(List<String> rcSqlList) {
		this.rcSqlList = rcSqlList;
	}


	public String getWhereSql() {
		return whereSql;
	}

	public void setWhereSql(String whereSql) {
		this.whereSql = whereSql;
	}

	
	

}




