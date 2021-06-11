package com.openjad.logger.api.support;

import com.openjad.common.constant.BaseLogCode;
import com.openjad.logger.api.AbstractLogger;
import com.openjad.logger.api.Logger;

/**
 * 
 * 
 * @author hechuan
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

	@Override
	public void trace(String msg, Throwable e) {
		try {
			logger.trace(msg, e);
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
			logger.trace(msg);
		} catch (Throwable t) {
		}
	}

	@Override
	public void debug(String msg, Throwable e) {
		try {
			logger.debug(msg, e);
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
			logger.debug(msg);
		} catch (Throwable t) {
		}
	}

	@Override
	public void info(String msg, Throwable e) {
		try {
			logger.info(msg, e);
		} catch (Throwable t) {
		}
	}

	@Override
	public void info(String msg) {
		try {
			logger.info(msg);
		} catch (Throwable t) {
		}
	}

	@Override
	public void warn(String msg, Throwable e) {
		try {
			logger.warn(msg, e);
		} catch (Throwable t) {
		}
	}

	@Override
	public void warn(String msg) {
		try {
			logger.warn(msg);
		} catch (Throwable t) {
		}
	}

	@Override
	public void error(String msg, Throwable e) {
		try {
			logger.error(msg, e);
		} catch (Throwable t) {
		}
	}

	@Override
	public void error(String msg) {
		try {
			logger.error(msg);
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
			logger.warn( e);
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
	public void trace(BaseLogCode logMsg) {
		try {
			logger.trace(logMsg);
		} catch (Throwable t) {
		}

	}

	@Override
	public void trace(BaseLogCode logMsg, String msg) {
		try {
			logger.trace(logMsg, msg);
		} catch (Throwable t) {
		}
	}

	@Override
	public void trace(BaseLogCode logMsg, Throwable e) {
		try {
			logger.trace(logMsg, e);
		} catch (Throwable t) {
		}

	}

	@Override
	public void trace(BaseLogCode logMsg, String msg, Throwable e) {
		try {
			logger.trace(logMsg, msg, e);
		} catch (Throwable t) {
		}

	}

	@Override
	public void debug(BaseLogCode logMsg) {
		try {
			logger.debug(logMsg);
		} catch (Throwable t) {
		}
	}

	@Override
	public void debug(BaseLogCode logMsg, String msg) {
		try {
			logger.debug(logMsg, msg);
		} catch (Throwable t) {
		}
	}

	@Override
	public void debug(BaseLogCode logMsg, Throwable e) {
		try {
			logger.debug(logMsg, e);
		} catch (Throwable t) {
		}
	}

	@Override
	public void debug(BaseLogCode logMsg, String msg, Throwable e) {
		try {
			logger.debug(logMsg, msg, e);
		} catch (Throwable t) {
		}
	}

	@Override
	public void info(BaseLogCode logMsg) {
		try {
			logger.info(logMsg);
		} catch (Throwable t) {
		}
	}

	@Override
	public void info(BaseLogCode logMsg, String msg) {
		try {
			logger.info(logMsg, msg);
		} catch (Throwable t) {
		}
	}

	@Override
	public void info(BaseLogCode logMsg, Throwable e) {
		try {
			logger.info(logMsg, e);
		} catch (Throwable t) {
		}
	}

	@Override
	public void info(BaseLogCode logMsg, String msg, Throwable e) {
		try {
			logger.info(logMsg, msg, e);
		} catch (Throwable t) {
		}
	}

	@Override
	public void warn(BaseLogCode logMsg) {
		try {
			logger.warn(logMsg);
		} catch (Throwable t) {
		}
	}

	@Override
	public void warn(BaseLogCode logMsg, String msg) {
		try {
			logger.warn(logMsg, msg);
		} catch (Throwable t) {
		}
	}

	@Override
	public void warn(BaseLogCode logMsg, Throwable e) {
		try {
			logger.warn(logMsg, e);
		} catch (Throwable t) {
		}
	}

	@Override
	public void warn(BaseLogCode logMsg, String msg, Throwable e) {
		try {
			logger.warn(logMsg, msg, e);
		} catch (Throwable t) {
		}
	}

	@Override
	public void error(BaseLogCode logMsg) {
		try {
			logger.error(logMsg);
		} catch (Throwable t) {
		}
	}

	@Override
	public void error(BaseLogCode logMsg, String msg) {
		try {
			logger.error(logMsg, msg);
		} catch (Throwable t) {
		}
	}

	@Override
	public void error(BaseLogCode logMsg, Throwable e) {
		try {
			logger.error(logMsg, e);
		} catch (Throwable t) {
		}
	}

	@Override
	public void error(BaseLogCode logMsg, String msg, Throwable e) {
		try {
			logger.error(logMsg, msg, e);
		} catch (Throwable t) {
		}
	}

}
