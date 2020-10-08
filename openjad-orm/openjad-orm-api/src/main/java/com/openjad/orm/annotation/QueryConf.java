package com.openjad.orm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.openjad.orm.enums.QueryOperateType;
import com.openjad.orm.enums.TimeFormat;

/**
 * 查询配置
 *  @author hechuan
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface QueryConf {
	
	/**
	 * @return 表列名
	 */
	boolean value() default true;
	
	/**
	 * @return 标签
	 */
	String label() default "";
	
	/**
	 * 
	 * @return 时间格式
	 */
	TimeFormat timeFormat() default TimeFormat.DEFAULT_FORMAT;
	
	/**
	 * 关联属性
	 * 如果是关联属性，以“.”号分隔
	 * @return  关联属性
	 */
	String property() default "";
	
	/**
	 * 查询操作类型
	 * @return  查询操作类型
	 */
	QueryOperateType queryOperateType() default QueryOperateType.eq;
	
}


