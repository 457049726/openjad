package com.openjad.common.constant.cv;

/**
 * 
 *  @Title AbstractState
 *  @author hechuan
 *  @date 2019年9月20日
 *
 *  <功能简述>
 */
public abstract class AbstractState extends BaseCode implements Type {

	protected AbstractState(String code, String value) {
		super(STATE_TYPE_FLAG,code,value);
	}
	
}
