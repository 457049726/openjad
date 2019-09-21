package com.openjad.common.constant.cv;

/**
 * 
 * @Title AbstractCode
 * @author hechuan
 * @date 2019年9月20日
 *
 *       <功能简述>
 */
public abstract class AbstractCode extends BaseCode implements Code {

	protected AbstractCode(String code, String value) {
		super(CODE_TYPE_FLAG, code, value);
	}
	
	protected AbstractCode(String typeFlag,String code, String value) {
		super(typeFlag, code, value);
	}

}
