package com.openjad.orm.mybatis.multids;

import java.lang.annotation.Annotation;

import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.stereotype.Service;

public class MultidsPointcut extends AnnotationMatchingPointcut{

	public MultidsPointcut() {
		super(Service.class);
	}


}
