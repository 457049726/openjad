package com.openjad.logger.log4j2;

import org.apache.logging.log4j.Level;
import org.slf4j.MDC;

import com.openjad.common.constant.BaseLogCode;
import com.openjad.common.constant.CharacterConstants;
import com.openjad.common.exception.EncodedException;
import com.openjad.logger.api.AbstractLogger;
import com.openjad.logger.api.support.FailsafeLogger;

/**
 * 
 * 
 * @author hechuan
 *
 */
public class Log4j2Logger extends AbstractLogger {

	//这里必须用 FailsafeLogger 
	private static final String FQCN = FailsafeLogger.class.getName();

	private static final String MORE_MSG_MDC_KEY = "jadMoreLog";

	private final org.apache.logging.log4j.spi.ExtendedLogger logger;

	public Log4j2Logger(org.apache.logging.log4j.spi.ExtendedLogger logger) {
		this.logger = logger;
	}

	@Override
	public void trace(String msg) {
		logIfEnabled(Level.TRACE,msg, null, null);
		
	}

	@Override
	public void trace(Throwable e) {
		logIfEnabled(Level.TRACE,null, null, e);
		
	}

	@Override
	public void trace(String msg, Throwable e) {
		logIfEnabled(Level.TRACE,msg, null, e);
		
	}

	@Override
	public void debug(String msg) {
		logIfEnabled(Level.DEBUG,msg, null, null);
		
	}

	@Override
	public void debug(Throwable e) {
		logIfEnabled(Level.DEBUG,null, null, e);
		
	}

	@Override
	public void debug(String msg, Throwable e) {
		logIfEnabled(Level.DEBUG,msg, null, e);
		
	}

	@Override
	public void info(String msg) {
		logIfEnabled(Level.INFO,msg, null, null);
		
	}

	@Override
	public void info(Throwable e) {
		logIfEnabled(Level.INFO,null, null, e);
		
	}

	@Override
	public void info(String msg, Throwable e) {
		logIfEnabled(Level.INFO,msg, null, e);
		
	}

	@Override
	public void warn(String msg) {
		logIfEnabled(Level.WARN, msg, null, null);
		
	}

	@Override
	public void warn(Throwable e) {
		logIfEnabled(Level.WARN, null, null, e);
	}

	@Override
	public void warn(String msg, Throwable e) {
		logIfEnabled(Level.WARN, msg, null, e);
	}

	@Override
	public void error(String msg) {
		logIfEnabled(Level.ERROR,msg, null, null);
		
	}

	@Override
	public void error(Throwable e) {
		logIfEnabled(Level.ERROR,null, null, e);
	}

	@Override
	public void error(String msg, Throwable e) {
		logIfEnabled(Level.ERROR,msg, null, e);
		
	}

	@Override
	public boolean isTraceEnabled() {
		return logger.isTraceEnabled();
	}

	@Override
	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	@Override
	public boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}

	@Override
	public boolean isWarnEnabled() {
		return logger.isWarnEnabled();
	}

	@Override
	public boolean isErrorEnabled() {
		return logger.isErrorEnabled();
	}

	@Override
	public void trace(BaseLogCode logMsg) {
		logIfEnabled(Level.TRACE, null, logMsg, null);
	}

	@Override
	public void trace(BaseLogCode logMsg, String msg) {
		logIfEnabled(Level.TRACE, msg, logMsg, null);
	}

	@Override
	public void trace(BaseLogCode logMsg, Throwable e) {
		logIfEnabled(Level.TRACE, null, logMsg, e);
		
	}

	@Override
	public void trace(BaseLogCode logMsg, String msg, Throwable e) {
		logIfEnabled(Level.TRACE, msg, logMsg, e);
		
	}

	@Override
	public void debug(BaseLogCode logMsg) {
		logIfEnabled(Level.DEBUG,  null, logMsg, null);
		
	}

	@Override
	public void debug(BaseLogCode logMsg, String msg) {
		logIfEnabled(Level.DEBUG,  msg, logMsg, null);
		
	}

	@Override
	public void debug(BaseLogCode logMsg, Throwable e) {
		logIfEnabled(Level.DEBUG,  null, logMsg, e);
		
	}

	@Override
	public void debug(BaseLogCode logMsg, String msg, Throwable e) {
		logIfEnabled(Level.DEBUG,  msg, logMsg, e);
		
	}

	@Override
	public void info(BaseLogCode logMsg) {
		logIfEnabled(Level.INFO, null, logMsg, null);
		
	}

	@Override
	public void info(BaseLogCode logMsg, String msg) {
		logIfEnabled(Level.INFO, msg, logMsg, null);
		
	}

	@Override
	public void info(BaseLogCode logMsg, Throwable e) {
		logIfEnabled(Level.INFO, null, logMsg, e);
		
	}

	@Override
	public void info(BaseLogCode logMsg, String msg, Throwable e) {
		logIfEnabled(Level.INFO, msg, logMsg, e);
		
	}

	@Override
	public void warn(BaseLogCode logMsg) {
		logIfEnabled(Level.WARN,null, logMsg, null);
		
	}

	@Override
	public void warn(BaseLogCode logMsg, String msg) {
		logIfEnabled(Level.WARN,msg, logMsg, null);
		
	}

	@Override
	public void warn(BaseLogCode logMsg, Throwable e) {
		logIfEnabled(Level.WARN,null, logMsg, e);
		
	}

	@Override
	public void warn(BaseLogCode logMsg, String msg, Throwable e) {
		logIfEnabled(Level.WARN,msg, logMsg, e);
		
	}

	@Override
	public void error(BaseLogCode logMsg) {
		logIfEnabled(Level.ERROR,null, logMsg, null);
		
	}

	@Override
	public void error(BaseLogCode logMsg, String msg) {
		logIfEnabled(Level.ERROR,msg, logMsg, null);
		
	}

	@Override
	public void error(BaseLogCode logMsg, Throwable e) {
		logIfEnabled(Level.ERROR,null, logMsg, e);
		
	}

	@Override
	public void error(BaseLogCode logMsg, String msg, Throwable e) {
		logIfEnabled(Level.ERROR,msg, logMsg, e);
		
	}

	private void logIfEnabled(Level level,String message, BaseLogCode logMsg, Throwable e) {
		boolean flag = false;
		try {
			String more = getMsgForMore(logMsg, e);
			if (more != null) {
				flag = true;
				putMoreToMDC(more);
			}

			if (message == null && logMsg != null) {
				message = logMsg.getValue();
			}
			if ((message == null || "".equals(message)) && e != null) {
				message = e.getMessage();
			}

			logger.logIfEnabled(FQCN, level, null, message, e);
		} finally {
			if (flag) {
				removeMoreFormMDC();
			}
		}
	}

	private String getMsgForMore(BaseLogCode logMsg, Throwable e) {
		if (logMsg != null) {
			return getMsgForMore(logMsg);
		} else if (e != null) {
			return getMsgForMore(e);
		}
		return null;
	}

	private String getMsgForMore(BaseLogCode logMsg) {
		if (logMsg == null || !logMsg.isUrlForMore()) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		sb.append("获取更多信息请访问:  ");
		sb.append(getUrlForMore(logMsg));
		sb.append(CharacterConstants.CONVERT_LINE_CHARACTER);
		return sb.toString();

	}

	private String getMsgForMore(Throwable e) {
		if (e == null) {
			return null;
		}
		if (e instanceof EncodedException) {
			EncodedException ex = (EncodedException) e;
			if (ex.getCode() != null && (ex.getCode() instanceof BaseLogCode)) {
				return getMsgForMore((BaseLogCode) ex.getCode());
			}
		}
		return null;
	}

	private static void putMoreToMDC(String msg) {
		MDC.put(MORE_MSG_MDC_KEY, msg);
	}

	private static void removeMoreFormMDC() {
		try {
			MDC.remove(MORE_MSG_MDC_KEY);
		} catch (Throwable e) {
		}
	}

}
