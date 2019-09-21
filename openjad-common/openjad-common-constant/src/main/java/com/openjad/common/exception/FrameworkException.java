package com.openjad.common.exception;

import com.openjad.common.constant.FrameworkLogMsg;
import com.openjad.common.constant.cv.AbstractCode;

/**
 * 
 * 框架异常
 * 
 *  @author hechuan
 *
 */
@SuppressWarnings("serial")
public class FrameworkException extends RuntimeException {

	private AbstractCode code;

	public FrameworkException(AbstractCode code) {
		super();
	}

	public FrameworkException(AbstractCode code, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FrameworkException(AbstractCode code, String message, Throwable cause) {
		super(message, cause);
	}

	public FrameworkException(AbstractCode code, String message) {
		super(message);
	}

	public FrameworkException(AbstractCode code, Throwable cause) {
		super(cause);
	}

	public FrameworkException() {
		this(FrameworkLogMsg.CODE_99999);
	}

	public FrameworkException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		this(FrameworkLogMsg.CODE_99999, message, cause, enableSuppression, writableStackTrace);
	}

	public FrameworkException(String message, Throwable cause) {
		this(FrameworkLogMsg.CODE_99999, message, cause);
	}

	public FrameworkException(String message) {
		this(FrameworkLogMsg.CODE_99999, message);
	}

	public FrameworkException(Throwable cause) {
		super(cause);
	}

	public AbstractCode getCode() {
		return code;
	}

	public void setCode(AbstractCode code) {
		this.code = code;
	}

}
