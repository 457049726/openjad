package com.openjad.common.page;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class PageData<T> extends PageAble {


	protected Long count = 0L;// 总记录数

	protected List<T> list = new ArrayList<T>();

	public PageData() {
	}

	public PageData(Long pageNo, Integer pageSize) {
		super(pageNo, pageSize);
	}

	public PageData(Long pageNo, Integer pageSize, Long count, List<T> list) {
		super(pageNo, pageSize);
		setCount(count);
		setList(list);
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		if (count != null && count.longValue() >= 0) {
			this.count = count;
		}

	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		if (list != null) {
			this.list = list;
		}
	}

}
