package com.openjad.common.constant;

/**
 * 
 * @author hechuan
 *
 */
public class FrameworkCode extends BaseLogCode {

	/**
	 * 框架模块日志标识
	 */
	public static String FRAMEWORK_LOG_FLAG = "frameworkLog";

	public static final FrameworkCode CODE_00000 = new FrameworkCode("00000", "成功", false);
	
	public static final FrameworkCode CODE_00001 = new FrameworkCode("00001", "日志初始化失败", true);
	public static final FrameworkCode CODE_00002 = new FrameworkCode("00002", "context尚末初始化", true);
	
	
	public static final FrameworkCode CODE_00010 = new FrameworkCode("00010", "参数无法识别", true);
	public static final FrameworkCode CODE_00020 = new FrameworkCode("00020", "参数校验失败", true);//通常用于参数校验
	
	
	public static final FrameworkCode CODE_09999 = new FrameworkCode("09999", "系统异常", false);
	

	protected FrameworkCode(String code, String value, boolean urlForMore) {
		super(FRAMEWORK_LOG_FLAG, code, value, urlForMore);
	}

}
