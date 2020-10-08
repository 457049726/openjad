package com.openjad.orm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 更新配置
 *  @author hechuan
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface UpdateConf {
	
	/**
	 * @return 是否支持更新
	 */
	boolean value() default true;
	
	/**
	 * @return 标签
	 */
	String label() default "";
	
	/**
	 * @return 是否必需
	 */
	boolean required() default false;
	
	/**
	 * @return 为空时，是否强制更新为null
	 */
	boolean forceUpdateNull() default false;
	
}

