package com.openjad.orm.mybatis.dao;

import com.openjad.orm.annotation.JadDao;
import com.openjad.orm.dao.EntityDAO;
import com.openjad.orm.mybatis.eo.TestBaseTreeEO;


@JadDao("testBaseTreeDAO")
public interface TestBaseTreeDAO extends EntityDAO<TestBaseTreeEO,Long>{
}
