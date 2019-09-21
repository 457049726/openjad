package com.openjad.logger.api;

import com.openjad.common.constant.BaseLogMsg;

import static com.openjad.common.constant.CharacterConstants.*;

/**
 * 
 * <一句话文件描述>
 * 
 *  @Title AbstractLogger
 *  @author hechuan
 *  @date 2019年9月20日
 *
 *  <功能详述>
 */
public abstract class AbstractLogger implements Logger {

	protected String toMsg(BaseLogMsg logMsg) {
		return toMsg(logMsg, null);
	}

	protected String toMsg(BaseLogMsg logMsg, String msg) {
		StringBuffer sb = new StringBuffer();

		if (msg == null || "".equals(msg)) {
			msg = logMsg.getValue();
		}
		sb.append(logMsg.getValue());
		if (logMsg.isUrlForMore()) {
			sb.append(CONVERT_LINE_CHARACTER);
			sb.append(logMsg.getMsgForMore());
		}
		return sb.toString();
	}

}
