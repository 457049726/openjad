package com.openjad.common.exception;

import com.openjad.common.constant.FrameworkCode;

/**
 * 参数校验失败
 * 
 *  @author hechuan
 *
 */
@SuppressWarnings("serial")
public class ParamException extends EncodedException{
	
	public ParamException() {
		super(FrameworkCode.CODE_00020);
	}

	public ParamException(String message, Throwable cause) {
		super(FrameworkCode.CODE_00020, message, cause);
	}

	public ParamException(String message) {
		super(FrameworkCode.CODE_00020, message);
	}

	public ParamException(Throwable cause) {
		super(FrameworkCode.CODE_00020, cause);
	}

}
