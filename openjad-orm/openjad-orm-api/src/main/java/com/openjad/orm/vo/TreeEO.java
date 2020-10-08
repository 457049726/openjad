package com.openjad.orm.vo;

import java.io.Serializable;

public interface TreeEO<EO extends BaseEO, ID extends Serializable> extends BaseEO {

	public ID getParentId();

	public void setParentId(ID parentId);

	public String getParentIds();

	public void setParentIds(String parentIds);

	public String getName();

	public void setName(String name);

	public Integer getSort();

	public void setSort(Integer sort);

}
