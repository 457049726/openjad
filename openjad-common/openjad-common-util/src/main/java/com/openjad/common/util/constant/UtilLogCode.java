package com.openjad.common.util.constant;

import com.openjad.common.constant.BaseLogCode;

public class UtilLogCode extends BaseLogCode {

	private static final String LOG_TYPE = "util";

	public static final UtilLogCode CODE_00001 = new UtilLogCode("00001", "获取机器码失败");
	public static final UtilLogCode CODE_00002 = new UtilLogCode("00002", "获取进程信息失败");
	
	public static final UtilLogCode CODE_00003 = new UtilLogCode("00003", "属性复制失败");
	


	
	public UtilLogCode(String code, String value) {
		super(LOG_TYPE, code, value);
	}

}
