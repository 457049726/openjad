package com.openjad.common.constant.cv;

/**
 * 
 *  @author hechuan
 *
 */
public abstract class AbstractType extends BaseCode implements Type {
	protected AbstractType(String code, String value) {
		super(TYPE_TYPE_FLAG,code,value);
	}
}
