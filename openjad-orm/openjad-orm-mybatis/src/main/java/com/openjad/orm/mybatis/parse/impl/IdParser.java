package com.openjad.orm.mybatis.parse.impl;

import java.lang.reflect.Field;

import javax.persistence.Id;

import com.openjad.orm.mybatis.entity.EoFieldInfo;

/**
 * Id注解解析器
 * 
 * @author hechuan
 *
 */
public class IdParser extends AbstractEoFieldAnnotationParser<Id>{
	

	@Override
	public  boolean parseAnnotationEoField(EoFieldInfo eoFieldInfo, Field field, Id annotation) {
		eoFieldInfo.setColumn(true);
		eoFieldInfo.setId(true);
		eoFieldInfo.setUpdatable(false);
		return true;
	}

}
