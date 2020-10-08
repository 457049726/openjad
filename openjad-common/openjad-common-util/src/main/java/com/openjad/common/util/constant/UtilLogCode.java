package com.openjad.common.util.constant;

import com.openjad.common.constant.BaseLogCode;

public class UtilLogCode extends BaseLogCode {

	private static final String LOG_TYPE = "util";

	public static final UtilLogCode CODE_00001 = new UtilLogCode("00001", "获取机器码失败");
	public static final UtilLogCode CODE_00002 = new UtilLogCode("00002", "获取进程信息失败");
	
	public static final UtilLogCode CODE_00003 = new UtilLogCode("00003", "属性复制失败");
	

	public static final UtilLogCode CODE_00004 = new UtilLogCode("00004", "在目标对象中找不到属性");
	public static final UtilLogCode CODE_00005 = new UtilLogCode("00005", "在目标对象中找不到方法");
	
	public static final UtilLogCode CODE_00006 = new UtilLogCode("00006", "反射操作时类型不兼容");
	
	public static final UtilLogCode CODE_00007 = new UtilLogCode("00007", "没有找到属性对应的get/set方法");
	
	public static final UtilLogCode CODE_00010 = new UtilLogCode("00010", "反射操作失败");
	
	public UtilLogCode(String code, String value) {
		super(LOG_TYPE, code, value);
	}

}
