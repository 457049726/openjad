package com.openjad.orm.mybatis.parse.impl;

import java.lang.reflect.Field;

import com.openjad.orm.mybatis.entity.EoFieldInfo;
import com.openjad.orm.annotation.NotColumn;


/**
 * NotColumn 注解解析器
 * 
 *  @author hechuan
 *
 */
public class NotColumnParser extends AbstractEoFieldAnnotationParser<NotColumn>{

	@Override
	public  boolean parseAnnotationEoField(EoFieldInfo eoFieldInfo, Field field, NotColumn annotation) {
		eoFieldInfo.setColumn(false);
		return true;
	}


}
