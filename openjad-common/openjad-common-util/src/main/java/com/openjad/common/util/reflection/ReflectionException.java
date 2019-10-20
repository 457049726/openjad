package com.openjad.common.util.reflection;

/**
 * 
 *  @author hechuan
 *  
 */
@SuppressWarnings("serial")
public class ReflectionException extends RuntimeException {

	public ReflectionException() {
	}

	public ReflectionException(String message) {
		super(message);
	}

	public ReflectionException(Throwable cause) {
		super(cause);
	}

	public ReflectionException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReflectionException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
