package com.openjad.orm.mybatis.test;

import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import com.openjad.test.junit.MySpringJunit4ClassRunner;


@RunWith(MySpringJunit4ClassRunner.class)
@SpringBootTest(classes = {BaseTestCase.class})
public class BaseTestCase {

}
