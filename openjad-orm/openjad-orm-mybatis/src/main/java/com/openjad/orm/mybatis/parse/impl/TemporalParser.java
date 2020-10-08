package com.openjad.orm.mybatis.parse.impl;

import java.lang.reflect.Field;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.openjad.orm.mybatis.constant.MybatisLogCode;
import com.openjad.orm.mybatis.entity.EoFieldInfo;
import com.openjad.orm.enums.JavaType;
import com.openjad.orm.exception.JadEntityParseException;


/**
 * Temporal 注解解析器
 * 
 *  @author hechuan
 *
 */
public class TemporalParser extends AbstractEoFieldAnnotationParser<Temporal> {

	@Override
	public  boolean parseAnnotationEoField(EoFieldInfo eoFieldInfo, Field field, Temporal temporal) {
		String type = JavaType.getJavaType(eoFieldInfo.getFieldType().getName()).getType();
		if (!JavaType.utilDate.getType().equalsIgnoreCase(type) 
				&& !JavaType.Calendar.getType().equalsIgnoreCase(type)) {
			throw new JadEntityParseException(MybatisLogCode.CODE_00005,
					String.format("实体类%s的属性%s上不能使用Temporal注解",
							eoFieldInfo.getEoInfo().getMetaClass().getName(),
							eoFieldInfo.getFieldName()));
		}
		eoFieldInfo.setTemporalType(temporal.value() == null ? TemporalType.TIMESTAMP : temporal.value());

		return true;
	}

}
