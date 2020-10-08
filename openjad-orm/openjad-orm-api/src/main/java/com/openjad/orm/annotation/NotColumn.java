package com.openjad.orm.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标识为非数据库字段列
 *  @author hechuan
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({METHOD, FIELD})
public @interface NotColumn {
}
