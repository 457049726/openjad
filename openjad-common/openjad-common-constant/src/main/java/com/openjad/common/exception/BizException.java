package com.openjad.common.exception;

import com.openjad.common.constant.FrameworkCode;
import com.openjad.common.constant.cv.AbstractCode;

/**
 * 普通业务异常
 * 
 * @author hechuan
 *
 */
@SuppressWarnings("serial")
public class BizException extends EncodedException {

	public BizException() {
		super(FrameworkCode.CODE_39999);
	}

	public BizException(AbstractCode code, String message, Throwable cause) {
		super(code, message, cause);
	}

	public BizException(AbstractCode code, String message) {
		super(code, message);
	}

	public BizException(AbstractCode code, Throwable cause) {
		super(code, cause);
	}

	public BizException(AbstractCode code) {
		super(code);
	}

	public BizException(String message, Throwable cause) {
		super(FrameworkCode.CODE_39999,message, cause);
	}

	public BizException(String message) {
		super(FrameworkCode.CODE_39999,message);
	}

	public BizException(Throwable cause) {
		super(FrameworkCode.CODE_39999,cause);
	}

}
