package com.openjad.orm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 组织策略
 * 
 * @author hechuan
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface OrganizeStrategy {

	public static String INTEGRATE_METHOD = "integrate";//集成方式
	public static String SEPARATE_METHOD = "separate";//分离方式

	/**
	 * 组织类型码
	 * 组织类型码 与 组织列名 不能同时为空
	 * 如果组织类型码为空，则按组织列名进行转换成驼峰形式
	 * 
	 * @return
	 */
	String typeCode() default "";

	/**
	 * 组织方式
	 * 
	 * @return
	 */
	String method() default INTEGRATE_METHOD;

	/**
	 * 组织列名
	 * 组织类型码 与 组织列名 不能同时为空
	 * 如果组织列名为空，则按照组织类型进行转换成数据表列的形式
	 * 
	 * @return
	 */
	String orgColumn() default "";

	/**
	 * 组织表名
	 * 集成方式下忽略此字段
	 * 
	 * @return
	 */
	String orgTable() default "";

}
