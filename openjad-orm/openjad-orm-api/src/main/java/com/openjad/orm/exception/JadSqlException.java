package com.openjad.orm.exception;

import com.openjad.common.constant.cv.AbstractCode;
import com.openjad.common.exception.BizException;

/**
 * jad sql异常
 * 
 * @author hechuan
 *
 */
@SuppressWarnings("serial")
public class JadSqlException extends BizException {

	public JadSqlException() {
	}

	public JadSqlException(AbstractCode code, String message, Throwable cause) {
		super(code, message, cause);
	}

	public JadSqlException(AbstractCode code, String message) {
		super(code, message);
	}

	public JadSqlException(AbstractCode code, Throwable cause) {
		super(code, cause);
	}

	public JadSqlException(AbstractCode code) {
		super(code);
	}

	public JadSqlException(String message) {
		super(message);
	}

	public JadSqlException(Throwable cause) {
		super(cause);
	}

	public JadSqlException(String message, Throwable cause) {
		super(message, cause);
	}

}
