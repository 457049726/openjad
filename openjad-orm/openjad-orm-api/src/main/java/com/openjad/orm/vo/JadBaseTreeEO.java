package com.openjad.orm.vo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.OrderBy;

import com.openjad.orm.annotation.NotColumn;
import com.openjad.orm.vo.JadBaseEO;
import com.openjad.orm.vo.TreeEO;

@SuppressWarnings("serial")
public class JadBaseTreeEO<ID extends Serializable,EO extends BaseEO>
		extends JadBaseEO<ID> implements TreeEO<ID,EO> {

	private ID parentId;

	private String parentIds; // 所有父级编号

	@OrderBy(" sort desc ")
	private Long sort;//排序序号
	
	@NotColumn
	private List<EO> children;
	
	private Integer level;//级别

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

	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

	@Override
	public List<EO> getChildren() {
		return children;
	}

	@Override
	public void setChildren(List<EO> children) {
		this.children=children;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}



}
