/**
 */
package com.openjad.common.page;

import java.util.ArrayList;
import java.util.List;


/**
 * 分页类
 * @param <T> t
 */
@SuppressWarnings("serial")
public class PageBO<T> extends PageData<T> {

	/**
	 * 是否不进行总数统计
	 */
	private Boolean notCount = false;


	public PageBO() {
	}

	/**
	 * 构造方法
	 * @param pageNo 当前页码
	 * @param pageSize 分页大小
	 */
	public PageBO(Long pageNo, Integer pageSize) {
		super(pageNo, pageSize);
	}

	/**
	 * 构造方法
	 * @param pageNo 当前页码
	 * @param pageSize 分页大小
	 * @param count 数据条数
	 */
	public PageBO(Long pageNo, Integer pageSize, Long count) {
		super(pageNo, pageSize, count, new ArrayList<T>());
	}

	/**
	 * 构造方法
	 * @param pageNo 当前页码
	 * @param pageSize 分页大小
	 * @param count 数据条数
	 * @param list 本页数据对象列表
	 */
	public PageBO(Long pageNo, Integer pageSize, Long count, List<T> list) {
		super(pageNo, pageSize, count, list);
	}

	/**
	 * 获取页面总数
	 * @return getLast();
	 */
	public long getTotalPage() {
		long pageSize = this.pageSize < 1 ? DEF_PAGE_SIZE : this.pageSize;
		long last = count / pageSize;
		if (this.count % pageSize != 0) {
			last++;
		}
		return last;
	}
	
	/**
	 * 是否不为空
	 * @param page page
	 * @return 是否不为空
	 */
	public static boolean isNotEmpty(PageBO page) {
		return !isEmpty(page);
	}
	/**
	 * 是否为空
	 * @param page page
	 * @return 是否为空
	 */
	public static boolean isEmpty(PageBO page) {
		if (page == null) {
			return true;
		}
		if (page.getList() == null || page.getList().isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * 上一页索引值
	 * @return 上一页索引值
	 */
	public long getPrev() {
		if (pageNo == 0) {
			return pageNo;
		} else {
			return pageNo - 1;
		}
	}

	/**
	 * 下一页索引值
	 * @return  下一页索引值
	 */
	public long getNext() {
		return pageNo + 1;
	}
	
	
	public long getFirstResult() {
		long firstResult = (getPageNo() - 1) * getPageSize();
		return firstResult;
	}


	public Boolean getNotCount() {
		return notCount;
	}

	public void setNotCount(Boolean notCount) {
		this.notCount = notCount;
	}

	public Integer getMaxResults() {
		return getPageSize();
	}

}
