package com.openjad.common.exception;

import com.openjad.common.constant.FrameworkCode;
import com.openjad.common.constant.cv.AbstractCode;

public class AuthorityException extends EncodedException{


	public AuthorityException() {
		super(FrameworkCode.CODE_00401);
	}

	public AuthorityException(AbstractCode code, String message, Throwable cause) {
		super(code, message, cause);
	}

	public AuthorityException(AbstractCode code, String message) {
		super(code, message);
	}

	public AuthorityException(AbstractCode code, Throwable cause) {
		super(code, cause);
	}

	public AuthorityException(AbstractCode code) {
		super(code);
	}

	public AuthorityException(String message, Throwable cause) {
		super(FrameworkCode.CODE_00401,message, cause);
	}

	public AuthorityException(String message) {
		super(FrameworkCode.CODE_00401,message);
	}

	public AuthorityException(Throwable cause) {
		super(FrameworkCode.CODE_00401,cause);
	}


}
