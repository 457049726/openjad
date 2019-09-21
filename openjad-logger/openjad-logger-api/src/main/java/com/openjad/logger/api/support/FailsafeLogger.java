package com.openjad.logger.api.support;

import com.openjad.common.constant.BaseLogMsg;
import com.openjad.logger.api.AbstractLogger;
import com.openjad.logger.api.Logger;

/**
 * 
 * 
 *  @author hechuan
 *
 */
public class FailsafeLogger extends AbstractLogger {

	private Logger logger;

	public FailsafeLogger(Logger logger) {
		this.logger = logger;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	private String appendContextMessage(String msg) {
//    	return " [JAD-LOGGER] " + msg + ", jad version: " + Version.getVersion() ;
//		return " [JAD-LOGGER] " + msg + ", jad version: " + LoggerVersionUtils.getVersion();
		return msg;
	}

	@Override
	public void trace(String msg, Throwable e) {
		try {
			logger.trace(appendContextMessage(msg), e);
		} catch (Throwable t) {
		}
	}

	@Override
	public void trace(Throwable e) {
		try {
			logger.trace(e);
		} catch (Throwable t) {
		}
	}

	@Override
	public void trace(String msg) {
		try {
			logger.trace(appendContextMessage(msg));
		} catch (Throwable t) {
		}
	}

	@Override
	public void debug(String msg, Throwable e) {
		try {
			logger.debug(appendContextMessage(msg), e);
		} catch (Throwable t) {

		}
	}

	@Override
	public void debug(Throwable e) {
		try {
			logger.debug(e);
		} catch (Throwable t) {

		}
	}

	@Override
	public void debug(String msg) {
		try {
			logger.debug(appendContextMessage(msg));
		} catch (Throwable t) {

		}
	}

	@Override
	public void info(String msg, Throwable e) {
		try {
			logger.info(appendContextMessage(msg), e);
		} catch (Throwable t) {

		}
	}

	@Override
	public void info(String msg) {
		try {
			logger.info(appendContextMessage(msg));
		} catch (Throwable t) {

		}
	}

	@Override
	public void warn(String msg, Throwable e) {
		try {
			logger.warn(appendContextMessage(msg), e);
		} catch (Throwable t) {

		}
	}

	@Override
	public void warn(String msg) {
		try {
			logger.warn(appendContextMessage(msg));
		} catch (Throwable t) {

		}
	}

	@Override
	public void error(String msg, Throwable e) {
		try {
			logger.error(appendContextMessage(msg), e);
		} catch (Throwable t) {

		}
	}

	@Override
	public void error(String msg) {
		try {
			logger.error(appendContextMessage(msg));
		} catch (Throwable t) {

		}
	}

	@Override
	public void error(Throwable e) {
		try {
			logger.error(e);
		} catch (Throwable t) {

		}
	}

	@Override
	public void info(Throwable e) {
		try {
			logger.info(e);
		} catch (Throwable t) {

		}
	}

	@Override
	public void warn(Throwable e) {
		try {
			logger.warn(e);
		} catch (Throwable t) {

		}
	}

	@Override
	public boolean isTraceEnabled() {
		try {
			return logger.isTraceEnabled();
		} catch (Throwable t) {
			return false;
		}
	}

	@Override
	public boolean isDebugEnabled() {
		try {
			return logger.isDebugEnabled();
		} catch (Throwable t) {
			return false;
		}
	}

	@Override
	public boolean isInfoEnabled() {
		try {
			return logger.isInfoEnabled();
		} catch (Throwable t) {
			return false;
		}
	}

	@Override
	public boolean isWarnEnabled() {
		try {
			return logger.isWarnEnabled();
		} catch (Throwable t) {
			return false;
		}
	}

	@Override
	public boolean isErrorEnabled() {
		try {
			return logger.isErrorEnabled();
		} catch (Throwable t) {
			return false;
		}
	}

	@Override
	public void trace(BaseLogMsg logMsg) {
		try {
			logger.trace(appendContextMessage(toMsg(logMsg)));
		} catch (Throwable t) {
		}
		
	}

	@Override
	public void trace(BaseLogMsg logMsg, String msg) {
		try {
			logger.trace(appendContextMessage(toMsg(logMsg,msg)));
		} catch (Throwable t) {
		}
	}

	@Override
	public void trace(BaseLogMsg logMsg, Throwable e) {
		try {
			logger.trace(appendContextMessage(toMsg(logMsg)), e);
		} catch (Throwable t) {
		}
		
	}

	@Override
	public void trace(BaseLogMsg logMsg, String msg, Throwable e) {
		try {
			logger.trace(appendContextMessage(toMsg(logMsg,msg)), e);
		} catch (Throwable t) {
		}
		
	}

	@Override
	public void debug(BaseLogMsg logMsg) {
		try {
			logger.debug(appendContextMessage(toMsg(logMsg)));
		} catch (Throwable t) {
		}
	}

	@Override
	public void debug(BaseLogMsg logMsg, String msg) {
		try {
			logger.debug(appendContextMessage(toMsg(logMsg,msg)));
		} catch (Throwable t) {
		}
	}

	@Override
	public void debug(BaseLogMsg logMsg, Throwable e) {
		try {
			logger.debug(appendContextMessage(toMsg(logMsg)), e);
		} catch (Throwable t) {
		}
	}

	@Override
	public void debug(BaseLogMsg logMsg, String msg, Throwable e) {
		try {
			logger.debug(appendContextMessage(toMsg(logMsg,msg)), e);
		} catch (Throwable t) {
		}
	}

	@Override
	public void info(BaseLogMsg logMsg) {
		try {
			logger.info(appendContextMessage(toMsg(logMsg)));
		} catch (Throwable t) {
		}
	}

	@Override
	public void info(BaseLogMsg logMsg, String msg) {
		try {
			logger.info(appendContextMessage(toMsg(logMsg,msg)));
		} catch (Throwable t) {
		}
	}

	@Override
	public void info(BaseLogMsg logMsg, Throwable e) {
		try {
			logger.info(appendContextMessage(toMsg(logMsg)), e);
		} catch (Throwable t) {
		}
	}

	@Override
	public void info(BaseLogMsg logMsg, String msg, Throwable e) {
		try {
			logger.info(appendContextMessage(toMsg(logMsg,msg)), e);
		} catch (Throwable t) {
		}
	}

	@Override
	public void warn(BaseLogMsg logMsg) {
		try {
			logger.warn(appendContextMessage(toMsg(logMsg)));
		} catch (Throwable t) {
		}
	}

	@Override
	public void warn(BaseLogMsg logMsg, String msg) {
		try {
			logger.warn(appendContextMessage(toMsg(logMsg,msg)));
		} catch (Throwable t) {
		}
	}

	@Override
	public void warn(BaseLogMsg logMsg, Throwable e) {
		try {
			logger.warn(appendContextMessage(toMsg(logMsg)), e);
		} catch (Throwable t) {
		}
	}

	@Override
	public void warn(BaseLogMsg logMsg, String msg, Throwable e) {
		try {
			logger.warn(appendContextMessage(toMsg(logMsg,msg)), e);
		} catch (Throwable t) {
		}
	}

	@Override
	public void error(BaseLogMsg logMsg) {
		try {
			logger.error(appendContextMessage(toMsg(logMsg)));
		} catch (Throwable t) {
		}
	}

	@Override
	public void error(BaseLogMsg logMsg, String msg) {
		try {
			logger.error(appendContextMessage(toMsg(logMsg,msg)));
		} catch (Throwable t) {
		}
	}

	@Override
	public void error(BaseLogMsg logMsg, Throwable e) {
		try {
			logger.error(appendContextMessage(toMsg(logMsg)), e);
		} catch (Throwable t) {
		}
	}

	@Override
	public void error(BaseLogMsg logMsg, String msg, Throwable e) {
		try {
			logger.error(appendContextMessage(toMsg(logMsg,msg)), e);
		} catch (Throwable t) {
		}
	}

}
