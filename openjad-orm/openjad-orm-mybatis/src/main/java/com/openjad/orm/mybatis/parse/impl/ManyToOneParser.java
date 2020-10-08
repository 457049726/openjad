package com.openjad.orm.mybatis.parse.impl;

import java.lang.reflect.Field;

import javax.persistence.ManyToOne;

import com.openjad.orm.mybatis.entity.EoFieldInfo;


/**
 * ManyToOne 注解解析器
 * 
 *  @author hechuan
 *
 */
public class ManyToOneParser extends AbstractEoFieldAnnotationParser<ManyToOne>{

	@Override
	public  boolean parseAnnotationEoField(EoFieldInfo eoFieldInfo, Field field, ManyToOne annotation) {
		eoFieldInfo.setColumn(true);
		eoFieldInfo.setRelateColumn(true);
		eoFieldInfo.setFieldName(field.getName());
		eoFieldInfo.setFieldType(field.getType());
		return true;
	}


}
