package com.openjad.common.constant;

/**
 * 
 * @Title FrameworkLogMsg
 * @author hechuan
 * @date 2019年9月20日
 *
 *       <功能简述>
 */
public class FrameworkLogMsg extends BaseLogMsg {

	/**
	 * 框架模块日志标识
	 */
	public static String FRAMEWORK_LOG_FLAG = "frameworkLog";

	public static final FrameworkLogMsg CODE_00000 = new FrameworkLogMsg("00000", "成功", false);
	
	public static final FrameworkLogMsg CODE_99999 = new FrameworkLogMsg("99999", "系统异常", false);

	protected FrameworkLogMsg(String code, String value, boolean urlForMore) {
		super(FRAMEWORK_LOG_FLAG, code, value, urlForMore);
	}

}
