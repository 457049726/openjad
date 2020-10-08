package com.openjad.common.exception;

import com.openjad.common.constant.BaseLogCode;
import com.openjad.common.constant.FrameworkCode;
import com.openjad.common.constant.cv.AbstractCode;

/**
 * 捕获异常
 * 
 * @author hechuan
 *
 */
@SuppressWarnings("serial")
public class EncodedException extends RuntimeException {

	/**
	 * 异常代码
	 * 异常日志可跟据此代码自动生成更新信息的网址
	 */
	protected AbstractCode code;

	public EncodedException(AbstractCode code) {
		super();
		this.code = code;
	}

	public EncodedException(AbstractCode code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public EncodedException(AbstractCode code, String message) {
		super(message);
		this.code = code;
	}

	public EncodedException(AbstractCode code, Throwable cause) {
		super(cause);
		this.code = code;
	}

	public EncodedException() {
		this(FrameworkCode.CODE_09999);
	}

	public EncodedException(String message, Throwable cause) {
		this(FrameworkCode.CODE_09999, message, cause);
	}

	public EncodedException(String message) {
		this(FrameworkCode.CODE_09999, message);
	}

	public EncodedException(Throwable cause) {
		this(FrameworkCode.CODE_09999, cause);
	}

	public AbstractCode getCode() {
		return code;
	}

	public void setCode(BaseLogCode code) {
		this.code = code;
	}

}
