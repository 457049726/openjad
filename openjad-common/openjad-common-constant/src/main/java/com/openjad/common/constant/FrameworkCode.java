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
	
	
	
	public static final FrameworkCode CODE_00010 = new FrameworkCode("00010", "反射操作失败", true);
	public static final FrameworkCode CODE_00011 = new FrameworkCode("00011", "在目标对象中找不到属性", true);
	public static final FrameworkCode CODE_00012 = new FrameworkCode("00012", "在目标对象中找不到方法", true);
	public static final FrameworkCode CODE_00013 = new FrameworkCode("00013", "反射操作时类型不兼容", true);
	public static final FrameworkCode CODE_00014 = new FrameworkCode("00014", "没有找到属性对应的get/set方法", true);
	public static final FrameworkCode CODE_00015 = new FrameworkCode("00015", "反射操作时属性复制失败", true);
	
	public static final FrameworkCode CODE_00020 = new FrameworkCode("00020", "参数校验失败", true);//通常用于参数校验
	
	public static final FrameworkCode CODE_00401 = new FrameworkCode("00401", "没有权限", true);
	public static final FrameworkCode CODE_00404 = new FrameworkCode("00404", "无效资源", true);
	
	public static final FrameworkCode CODE_39999 = new FrameworkCode("39999", "业务异常", false);
	
	public static final FrameworkCode CODE_99999 = new FrameworkCode("99999", "系统异常", false);
	

	protected FrameworkCode(String code, String value, boolean urlForMore) {
		super(FRAMEWORK_LOG_FLAG, code, value, urlForMore);
	}

}
