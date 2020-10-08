package com.openjad.logger.api.jdk;

import java.util.logging.Level;

import com.openjad.common.constant.BaseLogCode;
import com.openjad.common.constant.CharacterConstants;
import com.openjad.common.exception.EncodedException;
import com.openjad.logger.api.AbstractLogger;

/**
 * 
 * 
 * @author hechuan
 *
 */
public class JdkLogger extends AbstractLogger {

	private final java.util.logging.Logger logger;

	public static final String MORE_LOG_BEG = "//<<<";

	public static final String MORE_LOG_END = "//>>>";
	
	public JdkLogger(java.util.logging.Logger logger) {
		this.logger = logger;
	}

	@Override
	public void trace(String msg) {
		logger.log(Level.FINER, msg);
	}

	@Override
	public void trace(Throwable e) {
		logger.log(Level.FINER, toMsg(e), e);
	}

	@Override
	public void trace(String msg, Throwable e) {
		logger.log(Level.FINER, toMsg(msg, e), e);
	}

	@Override
	public void debug(String msg) {
		logger.log(Level.FINE, msg);
	}

	@Override
	public void debug(Throwable e) {
		logger.log(Level.FINE, toMsg(e), e);
	}

	@Override
	public void debug(String msg, Throwable e) {
		logger.log(Level.FINE, toMsg(msg, e), e);
	}

	@Override
	public void info(String msg) {
		logger.log(Level.INFO, msg);
	}

	@Override
	public void info(String msg, Throwable e) {
		logger.log(Level.INFO, toMsg(msg, e), e);
	}

	@Override
	public void warn(String msg) {
		logger.log(Level.WARNING, msg);
	}

	@Override
	public void warn(String msg, Throwable e) {
		logger.log(Level.WARNING, toMsg(msg, e), e);
	}

	@Override
	public void error(String msg) {
		logger.log(Level.SEVERE, msg);
	}

	@Override
	public void error(String msg, Throwable e) {
		logger.log(Level.SEVERE, toMsg(msg, e), e);
	}

	@Override
	public void error(Throwable e) {
		logger.log(Level.SEVERE, toMsg(e), e);
	}

	@Override
	public void info(Throwable e) {
		logger.log(Level.INFO, toMsg(e), e);
	}

	@Override
	public void warn(Throwable e) {
		logger.log(Level.WARNING, toMsg(e), e);
	}

	@Override
	public boolean isTraceEnabled() {
		return logger.isLoggable(Level.FINER);
	}

	@Override
	public boolean isDebugEnabled() {
		return logger.isLoggable(Level.FINE);
	}

	@Override
	public boolean isInfoEnabled() {
		return logger.isLoggable(Level.INFO);
	}

	@Override
	public boolean isWarnEnabled() {
		return logger.isLoggable(Level.WARNING);
	}

	@Override
	public boolean isErrorEnabled() {
		return logger.isLoggable(Level.SEVERE);
	}

	@Override
	public void trace(BaseLogCode logMsg) {
		logger.log(Level.FINER, toMsg(logMsg));

	}

	@Override
	public void trace(BaseLogCode logMsg, String msg) {
		logger.log(Level.FINER, toMsg(logMsg, msg));
	}

	@Override
	public void trace(BaseLogCode logMsg, Throwable e) {
		logger.log(Level.FINER, toMsg(logMsg), e);
	}

	@Override
	public void trace(BaseLogCode logMsg, String msg, Throwable e) {
		logger.log(Level.FINER, toMsg(logMsg, msg), e);
	}

	@Override
	public void debug(BaseLogCode logMsg) {
		logger.log(Level.FINER, toMsg(logMsg));
	}

	@Override
	public void debug(BaseLogCode logMsg, String msg) {
		logger.log(Level.FINER, toMsg(logMsg, msg));
	}

	@Override
	public void debug(BaseLogCode logMsg, Throwable e) {
		logger.log(Level.FINER, toMsg(logMsg), e);
	}

	@Override
	public void debug(BaseLogCode logMsg, String msg, Throwable e) {
		logger.log(Level.FINER, toMsg(logMsg, msg), e);
	}

	@Override
	public void info(BaseLogCode logMsg) {
		logger.log(Level.INFO, toMsg(logMsg));
	}

	@Override
	public void info(BaseLogCode logMsg, String msg) {
		logger.log(Level.INFO, toMsg(logMsg, msg));
	}

	@Override
	public void info(BaseLogCode logMsg, Throwable e) {
		logger.log(Level.INFO, toMsg(logMsg), e);
	}

	@Override
	public void info(BaseLogCode logMsg, String msg, Throwable e) {
		logger.log(Level.INFO, toMsg(logMsg, msg), e);
	}

	@Override
	public void warn(BaseLogCode logMsg) {
		logger.log(Level.WARNING, toMsg(logMsg));
	}

	@Override
	public void warn(BaseLogCode logMsg, String msg) {
		logger.log(Level.WARNING, toMsg(logMsg, msg));
	}

	@Override
	public void warn(BaseLogCode logMsg, Throwable e) {
		logger.log(Level.WARNING, toMsg(logMsg), e);
	}

	@Override
	public void warn(BaseLogCode logMsg, String msg, Throwable e) {
		logger.log(Level.WARNING, toMsg(logMsg, msg), e);
	}

	@Override
	public void error(BaseLogCode logMsg) {
		logger.log(Level.SEVERE, toMsg(logMsg));
	}

	@Override
	public void error(BaseLogCode logMsg, String msg) {
		logger.log(Level.SEVERE, toMsg(logMsg, msg));
	}

	@Override
	public void error(BaseLogCode logMsg, Throwable e) {
		logger.log(Level.SEVERE, toMsg(logMsg), e);
	}

	@Override
	public void error(BaseLogCode logMsg, String msg, Throwable e) {
		logger.log(Level.SEVERE, toMsg(logMsg, msg), e);
	}
	
	
	protected String toMsg(Throwable e) {
		return toMsg(null, e);
	}

	protected String toMsg(String msg, Throwable e) {
		if (e == null) {
			return (msg == null || "".equals(msg.trim())) ? "" : msg.trim();
		}
		if (e instanceof EncodedException) {
			EncodedException ex = (EncodedException) e;
			if (ex.getCode() != null && (ex.getCode() instanceof BaseLogCode)) {
				return toMsg((BaseLogCode) ex.getCode(), msg, true);
			}
		}
		return (msg == null || "".equals(msg.trim())) ? ((e.getMessage() == null ? "" : e.getMessage())) : msg;
	}

	protected String toMsg(BaseLogCode logMsg) {
		return toMsg(logMsg, null, false);
	}

	protected String toMsg(BaseLogCode logMsg, String msg) {
		return toMsg(logMsg, msg, false);
	}

	protected String toMsg(BaseLogCode logMsg, String msg, boolean moreFlag) {
		StringBuffer sb = new StringBuffer();
		if (msg == null || "".equals(msg.trim())) {
			sb.append(logMsg.getValue());
		} else {
			sb.append(msg);
		}
		if (logMsg.isUrlForMore()) {
			sb.append(getMsgForMore(logMsg, moreFlag));
		}
		return sb.toString();
	}
	
	
	public String getMsgForMore(BaseLogCode logMsg, boolean moreFlag) {
		StringBuffer sb = new StringBuffer();
		sb.append(CharacterConstants.CONVERT_LINE_CHARACTER);
		if (moreFlag) {
			sb.append(MORE_LOG_BEG);
		}
		sb.append("获取更多信息请访问:  ");
		sb.append(getUrlForMore(logMsg));
		if (moreFlag) {
			sb.append(MORE_LOG_END);
		}
		return sb.toString();
	}

}
