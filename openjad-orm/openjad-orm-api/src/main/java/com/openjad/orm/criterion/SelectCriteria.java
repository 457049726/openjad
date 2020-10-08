package com.openjad.orm.criterion;

/**
 * select 语句查询条件
 *  @author hechuan
 */
public class SelectCriteria extends WhereCriteria {
	
	/**
	 * 排序字段
	 */
	protected String orderByClause;

	/**
	 * 是否包含 distinct 关键字
	 */
    protected boolean distinct;

	public String getOrderByClause() {
		return orderByClause;
	}

	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	public boolean isDistinct() {
		return distinct;
	}

	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

    
    
}
