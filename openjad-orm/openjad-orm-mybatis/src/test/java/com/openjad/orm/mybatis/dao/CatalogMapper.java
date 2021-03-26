package com.openjad.orm.mybatis.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.openjad.orm.mybatis.eo.Catalog;

@Repository
public interface CatalogMapper {
	 List<Catalog> selectAll();
}
