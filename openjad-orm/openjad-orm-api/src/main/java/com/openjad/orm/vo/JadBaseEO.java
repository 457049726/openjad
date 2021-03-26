package com.openjad.orm.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

import com.openjad.orm.vo.IdEO;

@SuppressWarnings("serial")
public class JadBaseEO<ID extends Serializable> implements IdEO<ID> {

	@Id
	private ID id;

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}

}
