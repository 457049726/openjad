package com.openjad.test.base;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

import com.openjad.test.junit.MySpringJunit4ClassRunner;

@RunWith(MySpringJunit4ClassRunner.class)
@SpringBootTest(classes = {JadBaseTestCase.class})
public class JadBaseTestCase {

}
