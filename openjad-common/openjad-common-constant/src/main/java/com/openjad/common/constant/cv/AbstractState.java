package com.openjad.common.constant.cv;

/**
 * 
 *  @author hechuan
 *
 */
public abstract class AbstractState extends BaseCode implements Type {

	protected AbstractState(String code, String value) {
		super(STATE_TYPE_FLAG,code,value);
	}
	
}
