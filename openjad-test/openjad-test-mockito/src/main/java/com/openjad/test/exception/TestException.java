package com.openjad.test.exception;

/**
 * 
 * 
 *  @author hechuan
 *
 */
public class TestException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8923471256298739792L;

	public TestException() {
	}

	public TestException(String message) {
		super(message);
	}

	public TestException(Throwable cause) {
		super(cause);
	}

	public TestException(String message, Throwable cause) {
		super(message, cause);
	}

	public TestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
