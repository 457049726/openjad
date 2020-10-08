package com.openjad.logger.api.jdk.formatter;

import java.util.logging.LogRecord;

import com.openjad.common.constant.CharacterConstants;
import com.openjad.logger.api.jdk.JdkLogger;

public class JadLogFormatter {

	private static String BEG = JdkLogger.MORE_LOG_BEG;

	private static String END = JdkLogger.MORE_LOG_END;

	public String format(LogRecord record, String msg) {
		if (msg == null || "".equals(msg)) {
			return msg;
		}
		//包含更多信息
		String more = getMore(msg);
		if (more != null) {
			StringBuffer sb = new StringBuffer();
			sb.append(msg.substring(0, msg.indexOf(BEG)));
			sb.append(msg.substring(msg.indexOf(END) + END.length()).trim());
			sb.append(CharacterConstants.CONVERT_LINE_CHARACTER);
			sb.append(more);
			sb.append(CharacterConstants.CONVERT_LINE_CHARACTER);
			return sb.toString();
		} else {
			return msg;
		}

	}

	private String getMore(String msg) {
		if (msg.contains(BEG) && msg.contains(END)) {
			return msg.substring(msg.indexOf(BEG) + BEG.length(), msg.indexOf(END));
		}
		return null;
	}

}
