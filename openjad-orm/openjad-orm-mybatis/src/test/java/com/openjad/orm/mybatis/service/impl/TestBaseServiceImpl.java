package com.openjad.orm.mybatis.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.openjad.orm.mybatis.eo.TestBaseEO;
import com.openjad.orm.mybatis.service.AbstractEntityService;
import com.openjad.orm.mybatis.service.TestBaseService;

@Service("testBaseService")
@Transactional(readOnly = true)
public class TestBaseServiceImpl extends AbstractEntityService<TestBaseEO,Long> implements TestBaseService {

}
