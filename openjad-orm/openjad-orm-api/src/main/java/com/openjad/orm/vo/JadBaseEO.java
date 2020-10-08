package com.openjad.orm.vo;

import java.io.Serializable;

import javax.persistence.Id;

@SuppressWarnings("serial")
public class JadBaseEO<ID extends Serializable> implements IdEO<ID> {

	@Id
	protected ID id;

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}

}
