package com.openjad.orm.mybatis.parse.impl;

import java.lang.reflect.Field;

import javax.persistence.Column;

import com.openjad.orm.mybatis.entity.EoFieldInfo;
import com.openjad.orm.mybatis.utils.EntityUtils;

/**
 * column解析器
 * 
 *  @author hechuan
 *
 */
public class ColumnParser extends AbstractEoFieldAnnotationParser<Column> {

	@Override
	public boolean parseAnnotationEoField(EoFieldInfo eoFieldInfo, Field field, Column annotation) {
		eoFieldInfo.setColumn(true);
		setColumnAttrEo(eoFieldInfo, field, annotation);
		return true;
	}
	
	/**
	 * 保存解析后得到的 column信息
	 * @param eoFieldInfo 实体信息
	 * @param field 属性
	 * @param column 列注解
	 */
	private static void setColumnAttrEo(EoFieldInfo eoFieldInfo, Field field, Column column) {
		String name = column.name();
		String fieldName = field.getName();
		eoFieldInfo.setFieldName(fieldName);
		eoFieldInfo.setColumn((name != null && name.length() > 0) ? name : EntityUtils.convertColumnName(fieldName));
		eoFieldInfo.setUnique(column.unique());
		eoFieldInfo.setNullable(column.nullable());
		eoFieldInfo.setInsertable(column.insertable());
		eoFieldInfo.setUpdatable(column.updatable());
		eoFieldInfo.setLength(column.length());
		eoFieldInfo.setPrecision(column.precision());
		eoFieldInfo.setScale(column.scale());

	}

}
