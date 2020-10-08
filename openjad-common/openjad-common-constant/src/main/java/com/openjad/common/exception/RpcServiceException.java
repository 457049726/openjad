package com.openjad.common.exception;

import com.openjad.common.constant.cv.AbstractCode;

/**
 * 服务调用异常异常
 * 
 * @author hechuan
 *
 */
@SuppressWarnings("serial")
public class RpcServiceException extends EncodedException {

	public RpcServiceException() {
		super();
	}

	public RpcServiceException(AbstractCode code, String message, Throwable cause) {
		super(code, message, cause);
	}

	public RpcServiceException(AbstractCode code, String message) {
		super(code, message);
	}

	public RpcServiceException(AbstractCode code, Throwable cause) {
		super(code, cause);
	}

	public RpcServiceException(AbstractCode code) {
		super(code);
	}

	public RpcServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public RpcServiceException(String message) {
		super(message);
	}

	public RpcServiceException(Throwable cause) {
		super(cause);
	}

	public synchronized Throwable fillInStackTrace() {
		return this;
	}

}
