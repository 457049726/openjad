package com.openjad.orm.mybatis.entity;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.persistence.TemporalType;

import com.openjad.common.util.reflection.ReflectUtils;
import com.openjad.orm.vo.JadBaseEO;

/**
 * 对像属性信息
 * 
 * @author hechuan
 *
 */
@SuppressWarnings("serial")
public class ObjectFieldInfo implements Serializable {

	/**
	 * 原属性
	 */
	protected transient Field field;

	/**
	 * 属性类型
	 */
	@SuppressWarnings("rawtypes")
	protected transient Class fieldType;

	/**
	 * 属性名
	 */
	protected String fieldName;

	/**
	 * 长度
	 */
	protected int length = 255;

	/**
	 * 精度
	 */
	protected int precision = 0;

	/**
	 * 小数位
	 */
	protected int scale = 0;

	/**
	 * 如果字段为java.util.Date 或 java.util.Calendar类型， 且用 Temporal 注解标注了此字段
	 * ,这里保存Temporal注解的值
	 */
	protected TemporalType temporalType;

	@SuppressWarnings("rawtypes")
	public ObjectFieldInfo(Field field, Class clazz) {
		this.field = field;
		if (JadBaseEO.class.isAssignableFrom(clazz) && "id".equals(field.getName())) {
			Class idClazz = ReflectUtils.getSuperClassGenricType(clazz, 0);
			if (idClazz != Object.class) {
				this.fieldType = idClazz;
			} else {
				this.fieldType = this.field.getType();
			}
		} else {
			this.fieldType = this.field.getType();
		}

		this.fieldName = field.getName();
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public Class getFieldType() {
		return fieldType;
	}

	public void setFieldType(Class fieldType) {
		this.fieldType = fieldType;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getPrecision() {
		return precision;
	}

	public void setPrecision(int precision) {
		this.precision = precision;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public TemporalType getTemporalType() {
		return temporalType;
	}

	public void setTemporalType(TemporalType temporalType) {
		this.temporalType = temporalType;
	}

}
