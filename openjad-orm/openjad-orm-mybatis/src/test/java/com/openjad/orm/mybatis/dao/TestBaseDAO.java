package com.openjad.orm.mybatis.dao;

import com.openjad.orm.annotation.JadDao;
import com.openjad.orm.dao.EntityDAO;
import com.openjad.orm.mybatis.eo.TestBaseEO;


@JadDao("testBaseDAO")
public interface TestBaseDAO extends EntityDAO<TestBaseEO,Long>{
}
