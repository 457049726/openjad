package com.openjad.orm.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class JadTreeEO<EO extends JadBaseEO<ID>, ID extends Serializable> 
		extends JadBaseEO<ID> implements TreeEO<EO,ID> {
	
	private ID parentId;
	
	private String parentIds; // 所有父级编号

	private String name; // 名称

	private Integer sort; // 排序


	public ID getParentId() {
		return parentId;
	}

	public void setParentId(ID parentId) {
		this.parentId = parentId;
	}

	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	
}
