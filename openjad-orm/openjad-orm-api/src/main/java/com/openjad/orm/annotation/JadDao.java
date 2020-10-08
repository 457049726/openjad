package com.openjad.orm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 标识一个DAO
 * 被此注解标识的类最终被初始化为一个 spring bean
 *  @author hechuan
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
//@Component
public @interface JadDao {
	
	/**
	 * dao名称，整个系统中名称唯一
	 * @return  dao名称
	 */
	String value() default "";
	
	/**
	 * 
	 * @return  是否自动加载
	 */
	boolean autoLoad() default true;
}
