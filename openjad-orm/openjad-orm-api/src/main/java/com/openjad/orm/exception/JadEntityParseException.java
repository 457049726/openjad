package com.openjad.orm.exception;

import com.openjad.common.constant.cv.AbstractCode;
import com.openjad.common.exception.BizException;

/**
 * jad持久化异常
 * 
 *  @author hechuan
 *
 */
@SuppressWarnings("serial")
public class JadEntityParseException extends BizException{

	public JadEntityParseException() {
		super();
	}
	
	public JadEntityParseException(AbstractCode code, String message, Throwable cause) {
		super(code, message, cause);
	}

	public JadEntityParseException(AbstractCode code, String message) {
		super(code, message);
	}

	public JadEntityParseException(AbstractCode code, Throwable cause) {
		super(code, cause);
	}

	public JadEntityParseException(AbstractCode code) {
		super(code);
	}

	public JadEntityParseException(String message, Throwable cause) {
		super(message, cause);
	}

	public JadEntityParseException(String message) {
		super(message);
	}

	public JadEntityParseException(Throwable cause) {
		super(cause);
	}

}
