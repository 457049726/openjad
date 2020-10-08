package com.openjad.common.constant;

import com.openjad.common.constant.cv.AbstractCode;

/**
 * 
 * @author hechuan
 *
 */
public class BaseLogCode extends AbstractCode {

	private boolean urlForMore;

	protected BaseLogCode(String logType, String code, String value) {
		this(logType,code,value,true);
	}
	protected BaseLogCode(String logType, String code, String value, boolean urlForMore) {
		super(logType, code, value);
		this.urlForMore = urlForMore;
	}

	public boolean isUrlForMore() {
		return urlForMore;
	}
	
}


