package com.openjad.orm.vo;

import java.io.Serializable;

public interface IdEO<ID extends Serializable> extends BaseEO {
	public ID getId();
	public void setId(ID id);
}
