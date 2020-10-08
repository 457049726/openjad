package com.openjad.orm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 插入配置
 * 
 *  @author hechuan
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface InsertConf {
	
	/**
	 * @return 表列名
	 */
	boolean value() default true;
	
	/**
	 * @return 标签
	 */
	String label() default "";
	
	/**
	 * 是否必须，插入之前会自动判断此列是否必须
	 * @return 是否必须
	 */
	boolean required() default false;
	
}

