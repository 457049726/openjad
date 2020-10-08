package com.openjad.common.util.reflection;

import com.openjad.common.constant.cv.AbstractCode;
import com.openjad.common.exception.BizException;

/**
 * 
 *  @author hechuan
 *  
 */
@SuppressWarnings("serial")
public class ReflectionException extends BizException {

	public ReflectionException() {
		super();
	}

	public ReflectionException(AbstractCode code, String message, Throwable cause) {
		super(code, message, cause);
	}

	public ReflectionException(AbstractCode code, String message) {
		super(code, message);
	}

	public ReflectionException(AbstractCode code, Throwable cause) {
		super(code, cause);
	}

	public ReflectionException(AbstractCode code) {
		super(code);
	}

	public ReflectionException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReflectionException(String message) {
		super(message);
	}

	public ReflectionException(Throwable cause) {
		super(cause);
	}
	
}
