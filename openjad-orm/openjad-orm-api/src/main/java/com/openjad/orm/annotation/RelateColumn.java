package com.openjad.orm.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.openjad.orm.enums.RelateLoadMethod;

/**
 * 类联列
 * 当实体的某一属性关性关联到一个对象时用此注解
 * @author hechuan
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({METHOD, FIELD})
public @interface RelateColumn {

	/**
	 * @return 列名
	 */
	String name();
	
	/**
	 * @return 关联表别名，默认跟属性名相同 
	 */
	String alia() default "";

	/**
	 * @return 关联表列名，默认跟主建一至 
	 */
	String relateColumn() default "";
	
	/**
	 * @return 加载方式
	 */
	RelateLoadMethod loadMethod() default RelateLoadMethod.NOT_AUTO;
	
	/**
	 * @return  能否为空
	 */
	boolean nullable() default true;

	/**
	 * @return 能否插入
	 */
	boolean insertable() default true;

	/**
	 * @return 能否修改
	 */
	boolean updatable() default true;
	
	/**
	 * @return  最大长度
	 */
	int length() default 255;
	
	/**
	 * @return 精度范围
	 */
    int precision() default 0;

    /**
     * @return 小数位数
     */
    int scale() default 0;

}
