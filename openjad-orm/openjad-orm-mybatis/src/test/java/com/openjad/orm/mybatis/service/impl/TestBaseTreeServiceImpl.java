package com.openjad.orm.mybatis.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.openjad.orm.mybatis.eo.TestBaseTreeEO;
import com.openjad.orm.mybatis.service.TestBaseTreeService;
import com.openjad.orm.mybatis.service.AbstractTreeEntityService;

@Service("testBaseTreeService")
@Transactional(readOnly = true)
public class TestBaseTreeServiceImpl extends AbstractTreeEntityService<TestBaseTreeEO,Long> implements TestBaseTreeService {

}
