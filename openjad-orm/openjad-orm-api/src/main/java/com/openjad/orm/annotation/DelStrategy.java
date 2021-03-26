package com.openjad.orm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 删除策略
 * 
 * @author hechuan
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DelStrategy {

	/**
	 * 物理删除
	 */
	public static final int PHYSICS_DELETE = 0;

	/**
	 * 逻辑删除
	 */
	public static final int LOGIC_DELETE = 1;

	int value() default PHYSICS_DELETE;
}
