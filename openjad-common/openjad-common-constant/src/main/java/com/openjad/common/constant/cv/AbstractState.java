package com.openjad.common.constant.cv;

/**
 * 
 *  @author hechuan
 *
 */
public abstract class AbstractState extends BaseCode implements Type {

	protected AbstractState(String typeFlag, String code, String value) {
		super(typeFlag, code, value);
	}

//	protected AbstractState(String code, String value) {
//		super(STATE_TYPE_FLAG,code,value);
//	}
	
//	public static <T extends BaseCode>T valueOf(String code){
//		return valueOf(STATE_TYPE_FLAG,code);
//	}
	
}
