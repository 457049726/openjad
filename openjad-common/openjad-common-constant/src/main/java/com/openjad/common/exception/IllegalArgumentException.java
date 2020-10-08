package com.openjad.common.exception;

import com.openjad.common.constant.FrameworkCode;

/**
 * 参数校验失败
 * 
 *  @author hechuan
 *
 */
@SuppressWarnings("serial")
public class IllegalArgumentException extends EncodedException{
	
	public IllegalArgumentException() {
		super(FrameworkCode.CODE_00020);
	}

	public IllegalArgumentException(String message, Throwable cause) {
		super(FrameworkCode.CODE_00020, message, cause);
	}

	public IllegalArgumentException(String message) {
		super(FrameworkCode.CODE_00020, message);
	}

	public IllegalArgumentException(Throwable cause) {
		super(FrameworkCode.CODE_00020, cause);
	}

}
