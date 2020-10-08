package com.openjad.common.page;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class PageDTO<T> extends PageData<T> {


	public PageDTO() {
	}
	
	public PageDTO(PageAble page) {
		this(page.getPageNo(),page.getPageSize());
	}

	public PageDTO(Long pageNo, Integer pageSize) {
		this(pageNo, pageSize, 0L, new ArrayList<T>());
	}
	

	public PageDTO(Long pageNo, Integer pageSize, Long count, List<T> list) {
		super(pageNo, pageSize, count, list);
	}
	
	
	
}
