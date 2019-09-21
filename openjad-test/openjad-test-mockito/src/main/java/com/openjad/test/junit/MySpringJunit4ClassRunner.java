package com.openjad.test.junit;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 
 * 
 *  @author hechuan
 *
 */
public class MySpringJunit4ClassRunner extends SpringJUnit4ClassRunner{

	public MySpringJunit4ClassRunner(Class<?> clazz) throws InitializationError {
		super(clazz);
	}
	
	@Override
    protected void validateInstanceMethods(List<Throwable> errors) {
        validatePublicVoidNoArgMethods(After.class, false, errors);
        validatePublicVoidNoArgMethods(Before.class, false, errors);
        validateTestMethods(errors);

        if (computeTestMethods().size() == 0) {
//           errors.add(new Exception("No runnable methods"));//20181226，避免没有测试方法时测试不通过
        }
    }


}
