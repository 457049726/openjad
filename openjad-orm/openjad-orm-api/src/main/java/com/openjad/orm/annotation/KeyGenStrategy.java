package com.openjad.orm.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.openjad.orm.enums.IdType;

/**
 * 主键生成策略
 * 
 * @author hechuan
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ TYPE, METHOD, FIELD })
public @interface KeyGenStrategy {

	IdType idType() default IdType.AUTO;

	String sequenceName() default "DEFAULT_ID_GEN_SEQ";

}
