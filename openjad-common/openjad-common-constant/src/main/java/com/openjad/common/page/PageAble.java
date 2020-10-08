package com.openjad.common.page;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PageAble implements Serializable {

	public static Integer DEF_PAGE_SIZE = 20;

	protected Long pageNo = 1L;

	protected Integer pageSize = DEF_PAGE_SIZE;// 页面大小

	public PageAble() {
	}

	public PageAble(Long pageNo, Integer pageSize) {
		setPageNo(pageNo);
		setPageSize(pageSize);
	}

	public Long getPageNo() {
		return pageNo;
	}

	public void setPageNo(Long pageNo) {
		if (pageNo != null && pageNo.longValue() > 0) {
			this.pageNo = pageNo;
		}
	}

	public Long firstResult(){
		Long firstResult = (getPageNo() - 1) * getPageSize();
		return firstResult;
	}
	
	public Integer maxResults(){
		return getPageSize();
	}
	
	public Integer getPageSize() {
		return pageSize;
	}
	
	

	public void setPageSize(Integer pageSize) {
		if (pageSize != null && pageSize.intValue() > 0) {
			this.pageSize = pageSize;
		}
	}

}
