package com.openjad.orm.mybatis.parse.impl;

import java.lang.reflect.Field;

import com.openjad.orm.mybatis.entity.EoFieldInfo;
import com.openjad.orm.annotation.RelateColumn;


/**
 * RelateColumn 注解解析器
 * 
 *  @author hechuan
 *
 */
public class RelateColumnParser extends AbstractEoFieldAnnotationParser<RelateColumn>{

	@Override
	public boolean parseAnnotationEoField(EoFieldInfo eoFieldInfo, Field field, RelateColumn relateColumn) {
		eoFieldInfo.setColumn(true);
		eoFieldInfo.setRelateColumn(true);
		eoFieldInfo.setRelateAlias(relateColumn.alia());
		eoFieldInfo.setRelateColumn(relateColumn);
		eoFieldInfo.setFieldName(field.getName());
		eoFieldInfo.setFieldType(field.getType());
		eoFieldInfo.setColumn(relateColumn.name());
		eoFieldInfo.setNullable(relateColumn.nullable());
		eoFieldInfo.setInsertable(relateColumn.insertable());
		eoFieldInfo.setUpdatable(relateColumn.updatable());
		eoFieldInfo.setLength(relateColumn.length());
		eoFieldInfo.setPrecision(relateColumn.precision());
		eoFieldInfo.setScale(relateColumn.scale());
		return true;
	}


}


