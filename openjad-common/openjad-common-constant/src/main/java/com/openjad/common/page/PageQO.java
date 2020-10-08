package com.openjad.common.page;

/**
 * 分页查询
 * @author hechuan
 *
 */
@SuppressWarnings("serial")
public class PageQO extends PageAble {

	protected String sort;
	
	protected String order;
	
	public PageQO() {
	}
	
	public PageQO(Long pageNo, Integer pageSize) {
		super(pageNo, pageSize);
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	
}
