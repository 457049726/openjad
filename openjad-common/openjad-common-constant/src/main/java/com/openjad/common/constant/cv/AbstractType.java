package com.openjad.common.constant.cv;

/**
 * 
 *  @Title AbstractType
 *  @author hechuan
 *  @date 2019年9月20日
 *
 *  <功能简述>
 */
public abstract class AbstractType extends BaseCode implements Type {
	protected AbstractType(String code, String value) {
		super(TYPE_TYPE_FLAG,code,value);
	}
}
