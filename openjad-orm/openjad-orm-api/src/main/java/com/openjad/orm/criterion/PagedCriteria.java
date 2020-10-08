package com.openjad.orm.criterion;

import java.util.ArrayList;

/**
 * 分页查询条件
 * 
 *  @author hechuan
 *
 */
public class PagedCriteria extends SelectCriteria {
	
	/**
	 * 当前页
	 */
	protected long pageNo = 1 ; 
	
	/**
	 * 每页记录数
	 */
	protected int pageSize = 20;
	
	/**
	 * 总记录数
	 */
	private long count;
	
	/**
	 * 是否不进行总数统计
	 */
	private boolean notCount = false;
	
	/**
	 * 20190807增加
	 * 查总记录数的sql片段
	 * 原则上，框架能自动生成查询总记录数的sql语句，但可能某情况下，生成的总记录数sql有点问题
	 * 因此可以通过此属性自定义总记数sql
	 * 注意，只要需要写 select 到 from 之间的 sql片断即可，程序会自动加上   select 、 from 及 where子句。
	 * 默认值为 count(*)
	 */
	private String countSqlFragment;
	
    public PagedCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public Criteria or(Criteria criteria) {
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public PagedCriteria clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
        return this;
    }

	public long getPageNo() {
		return pageNo;
	}
	

	public void setPageNo(long pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public boolean isNotCount() {
		return notCount;
	}

	public void setNotCount(boolean notCount) {
		this.notCount = notCount;
	}

	
	public long getFirstResult(){
		long firstResult = (getPageNo() - 1) * getPageSize();
		return firstResult;
	}
	
	public int getMaxResults(){
		return getPageSize();
	}

	public String getCountSqlFragment() {
		return countSqlFragment;
	}

	public void setCountSqlFragment(String countSqlFragment) {
		this.countSqlFragment = countSqlFragment;
	}
    
}
