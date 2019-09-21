package com.openjad.common.constant.cv;

/**
 * 
 * @author hechuan
 *
 */
public abstract class AbstractCode extends BaseCode implements Code {

	protected AbstractCode(String code, String value) {
		super(CODE_TYPE_FLAG, code, value);
	}
	
	protected AbstractCode(String typeFlag,String code, String value) {
		super(typeFlag, code, value);
	}

}
