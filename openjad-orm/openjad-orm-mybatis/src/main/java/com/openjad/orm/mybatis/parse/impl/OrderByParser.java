package com.openjad.orm.mybatis.parse.impl;

import java.lang.reflect.Field;

import javax.persistence.OrderBy;

import com.openjad.orm.mybatis.entity.EoFieldInfo;

/**
 * OrderBy 注解解析器
 * 
 * @author hechuan
 *
 */
public class OrderByParser extends AbstractEoFieldAnnotationParser<OrderBy> {

	@Override
	public boolean parseAnnotationEoField(EoFieldInfo eoFieldInfo, Field field, OrderBy annotation) {
		if (annotation.value() != null && annotation.value().length() > 0) {
			eoFieldInfo.setOrderBy(annotation.value());
			return true;
		} else {
			return false;
		}
	}

}
