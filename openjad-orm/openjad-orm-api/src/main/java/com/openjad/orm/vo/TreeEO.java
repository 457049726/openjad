package com.openjad.orm.vo;

import java.io.Serializable;
import java.util.List;

public interface TreeEO<ID extends Serializable,EO extends BaseEO> extends BaseEO, IdEO<ID> {

	
	ID getParentId();

	void setParentId(ID parentId);

	String getParentIds();

	void setParentIds(String parentIds);
	
	List<EO> getChildren();

	void setChildren(List<EO> children) ;
	
	
	Integer getLevel() ;

	void setLevel(Integer level) ;


}
