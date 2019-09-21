package com.openjad.logger.api;

import static com.openjad.common.constant.CharacterConstants.CONVERT_LINE_CHARACTER;

import com.openjad.common.constant.BaseLogMsg;

/**
 * 
 * 
 *  @author hechuan
 *
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
