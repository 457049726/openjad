package com.openjad.logger.log4j2;

import org.apache.logging.log4j.Level;

import com.openjad.common.constant.BaseLogMsg;
import com.openjad.logger.api.AbstractLogger;
import com.openjad.logger.api.support.FailsafeLogger;

/**
 * 
 * 
 *  @author hechuan
 *
 */
public class Log4j2Logger extends AbstractLogger {

	private static final String FQCN = FailsafeLogger.class.getName();
	
	private final org.apache.logging.log4j.spi.ExtendedLogger logger;

	public Log4j2Logger(org.apache.logging.log4j.spi.ExtendedLogger logger) {
		this.logger = logger;
	}

	@Override
	public void trace(String msg) {
		logger.logIfEnabled(FQCN, Level.TRACE, null, msg);
	}

	@Override
	public void trace(Throwable e) {
		logger.logIfEnabled(FQCN, Level.TRACE, null, e == null ? null : e.getMessage(), e);
	}

	@Override
	public void trace(String msg, Throwable e) {
		logger.logIfEnabled(FQCN, Level.TRACE, null, msg, e);
	}

	@Override
	public void debug(String msg) {
		logger.logIfEnabled(FQCN, Level.DEBUG, null, msg);
	}

	@Override
	public void debug(Throwable e) {
		logger.logIfEnabled(FQCN, Level.DEBUG, null, e == null ? null : e.getMessage(), e);
	}

	@Override
	public void debug(String msg, Throwable e) {
		logger.logIfEnabled(FQCN, Level.DEBUG, null, msg, e);
	}

	@Override
	public void info(String msg) {
		logger.logIfEnabled(FQCN, Level.INFO, null, msg);
	}

	@Override
	public void info(Throwable e) {
		logger.logIfEnabled(FQCN, Level.INFO, null, e == null ? null : e.getMessage(), e);
	}

	@Override
	public void info(String msg, Throwable e) {
		logger.logIfEnabled(FQCN, Level.INFO, null, msg, e);
	}

	@Override
	public void warn(String msg) {
		logger.logIfEnabled(FQCN, Level.WARN, null, msg);
	}

	@Override
	public void warn(Throwable e) {
		logger.logIfEnabled(FQCN, Level.WARN, null, e == null ? null : e.getMessage(), e);
	}

	@Override
	public void warn(String msg, Throwable e) {
		logger.logIfEnabled(FQCN, Level.WARN, null, msg, e);
	}

	@Override
	public void error(String msg) {
		logger.logIfEnabled(FQCN, Level.ERROR, null, msg);
	}

	@Override
	public void error(Throwable e) {
		logger.logIfEnabled(FQCN, Level.ERROR, null, e == null ? null : e.getMessage(), e);
	}

	@Override
	public void error(String msg, Throwable e) {
		logger.logIfEnabled(FQCN, Level.ERROR, null, msg, e);
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
	public void trace(BaseLogMsg logMsg) {
		logger.logIfEnabled(FQCN, Level.TRACE, null, toMsg(logMsg));
	}

	@Override
	public void trace(BaseLogMsg logMsg, String msg) {
		logger.logIfEnabled(FQCN, Level.TRACE, null, toMsg(logMsg,msg));
	}

	@Override
	public void trace(BaseLogMsg logMsg, Throwable e) {
		logger.logIfEnabled(FQCN, Level.TRACE, null, e == null ? toMsg(logMsg) : toMsg(logMsg,e.getMessage()), e);
	}

	@Override
	public void trace(BaseLogMsg logMsg, String msg, Throwable e) {
		logger.logIfEnabled(FQCN, Level.TRACE, null, toMsg(logMsg,msg), e);		
	}

	@Override
	public void debug(BaseLogMsg logMsg) {
		logger.logIfEnabled(FQCN, Level.DEBUG, null, toMsg(logMsg));
	}

	@Override
	public void debug(BaseLogMsg logMsg, String msg) {
		// TODO Auto-generated method stub
		logger.logIfEnabled(FQCN, Level.DEBUG, null, toMsg(logMsg,msg));
	}

	@Override
	public void debug(BaseLogMsg logMsg, Throwable e) {
		logger.logIfEnabled(FQCN, Level.DEBUG, null, e == null ? toMsg(logMsg) : toMsg(logMsg,e.getMessage()), e);
	}

	@Override
	public void debug(BaseLogMsg logMsg, String msg, Throwable e) {
		logger.logIfEnabled(FQCN, Level.DEBUG, null, toMsg(logMsg,msg), e);		
	}

	@Override
	public void info(BaseLogMsg logMsg) {
		logger.logIfEnabled(FQCN, Level.INFO, null, toMsg(logMsg));
	}

	@Override
	public void info(BaseLogMsg logMsg, String msg) {
		logger.logIfEnabled(FQCN, Level.INFO, null, toMsg(logMsg,msg));
	}

	@Override
	public void info(BaseLogMsg logMsg, Throwable e) {
		logger.logIfEnabled(FQCN, Level.INFO, null, e == null ? toMsg(logMsg) : toMsg(logMsg,e.getMessage()), e);
	}

	@Override
	public void info(BaseLogMsg logMsg, String msg, Throwable e) {
		logger.logIfEnabled(FQCN, Level.INFO, null, toMsg(logMsg,msg), e);		
	}

	@Override
	public void warn(BaseLogMsg logMsg) {
		logger.logIfEnabled(FQCN, Level.WARN, null, toMsg(logMsg));
	}

	@Override
	public void warn(BaseLogMsg logMsg, String msg) {
		logger.logIfEnabled(FQCN, Level.WARN, null, toMsg(logMsg,msg));
	}

	@Override
	public void warn(BaseLogMsg logMsg, Throwable e) {
		logger.logIfEnabled(FQCN, Level.WARN, null, e == null ? toMsg(logMsg) : toMsg(logMsg,e.getMessage()), e);
	}

	@Override
	public void warn(BaseLogMsg logMsg, String msg, Throwable e) {
		logger.logIfEnabled(FQCN, Level.WARN, null, toMsg(logMsg,msg), e);		
	}

	@Override
	public void error(BaseLogMsg logMsg) {
		logger.logIfEnabled(FQCN, Level.ERROR, null, toMsg(logMsg));
	}

	@Override
	public void error(BaseLogMsg logMsg, String msg) {
		logger.logIfEnabled(FQCN, Level.ERROR, null, toMsg(logMsg,msg));
	}

	@Override
	public void error(BaseLogMsg logMsg, Throwable e) {
		logger.logIfEnabled(FQCN, Level.ERROR, null, e == null ? toMsg(logMsg) : toMsg(logMsg,e.getMessage()), e);
	}

	@Override
	public void error(BaseLogMsg logMsg, String msg, Throwable e) {
		logger.logIfEnabled(FQCN, Level.ERROR, null, toMsg(logMsg,msg), e);	
	}

}
