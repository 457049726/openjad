package com.openjad.orm.mybatis.parse.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.openjad.common.util.reflection.ReflectUtils;
import com.openjad.orm.mybatis.entity.EoFieldInfo;
import com.openjad.orm.mybatis.parse.EoFieldParser;

/**
 * 基本注解的属性解析器
 * 
 * @author hechuan
 *
 */
public abstract class AbstractEoFieldAnnotationParser<A extends Annotation> implements EoFieldParser {

	/**
	 * 被解析的注解
	 */
	private Class<A> annotationClass;

	@SuppressWarnings("unchecked")
	public AbstractEoFieldAnnotationParser() {
		this.annotationClass = ReflectUtils.getSuperClassGenricType(this.getClass(), 0);
	}

	/**
	 * 通过注解解析实体属性
	 * @param eoFieldInfo 实体信息
	 * @param field 属性
	 * @param annotation 被解析的注解
	 * @return 是否解析
	 */
	public abstract boolean parseAnnotationEoField(EoFieldInfo eoFieldInfo, Field field, A annotation);

	@Override
	public boolean parseEoField(EoFieldInfo eoFieldInfo, Field field) {

		Class metaClass = eoFieldInfo.getEoInfo().getMetaClass();
		Method getterMethod = ReflectUtils.findGetterMethod(metaClass, field.getName());

		//先解析getter方法上的注解
		boolean parseRes = parseEoField(eoFieldInfo, field, getterMethod);

		//getter方法上没有注解，再从字段上解析
		if (!parseRes) {
			A annotation = field.getAnnotation(annotationClass);
			if (annotation != null) {
				parseRes = parseAnnotationEoField(eoFieldInfo, field, annotation);
			}
		}

		return parseRes;
	}

	/**
	 * 字段解析
	 * @param eoFieldInfo 实体信息
	 * @param field 字段
	 * @param getterMethod 字段对应的 get方法
	 * @return 是否解析
	 */
	private boolean parseEoField(EoFieldInfo eoFieldInfo, Field field, Method getterMethod) {
		A annotation = getterMethod.getAnnotation(annotationClass);
		if (annotation != null) {
			return parseAnnotationEoField(eoFieldInfo, field, annotation);
		} else {
			return false;
		}
	}

}
