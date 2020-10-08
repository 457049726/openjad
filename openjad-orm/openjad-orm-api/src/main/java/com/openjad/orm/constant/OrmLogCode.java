package com.openjad.orm.constant;

import com.openjad.common.constant.BaseLogCode;
import com.openjad.common.constant.FrameworkCode;

/**
 * orm日志代码
 * 
 *  @author hechuan
 *
 */
public class OrmLogCode extends BaseLogCode {

	private static final String LOG_TYPE = "orm";
	
	public static final OrmLogCode CODE_00001 = new OrmLogCode("00001", "Dialect暂不支持的时间格式");

	
	public OrmLogCode(String code, String value) {
		super(LOG_TYPE, code, value);
	}

}
