package com.openjad.logger.api.jdk;

import java.util.logging.Level;

import com.openjad.common.constant.BaseLogMsg;
import com.openjad.logger.api.AbstractLogger;

/**
 * 
 * <一句话文件描述>
 * 
 *  @Title JdkLogger
 *  @author hechuan
 *  @date 2019年9月20日
 *
 *  <功能详述>
 */
public class JdkLogger extends AbstractLogger {

    private final java.util.logging.Logger logger;

    public JdkLogger(java.util.logging.Logger logger) {
        this.logger = logger;
    }

    @Override
    public void trace(String msg) {
        logger.log(Level.FINER, msg);
    }

    @Override
    public void trace(Throwable e) {
        logger.log(Level.FINER, e.getMessage(), e);
    }

    @Override
    public void trace(String msg, Throwable e) {
        logger.log(Level.FINER, msg, e);
    }

    @Override
    public void debug(String msg) {
        logger.log(Level.FINE, msg);
    }

    @Override
    public void debug(Throwable e) {
        logger.log(Level.FINE, e.getMessage(), e);
    }

    @Override
    public void debug(String msg, Throwable e) {
        logger.log(Level.FINE, msg, e);
    }

    @Override
    public void info(String msg) {
        logger.log(Level.INFO, msg);
    }

    @Override
    public void info(String msg, Throwable e) {
        logger.log(Level.INFO, msg, e);
    }

    @Override
    public void warn(String msg) {
        logger.log(Level.WARNING, msg);
    }

    @Override
    public void warn(String msg, Throwable e) {
        logger.log(Level.WARNING, msg, e);
    }

    @Override
    public void error(String msg) {
        logger.log(Level.SEVERE, msg);
    }

    @Override
    public void error(String msg, Throwable e) {
        logger.log(Level.SEVERE, msg, e);
    }

    @Override
    public void error(Throwable e) {
        logger.log(Level.SEVERE, e.getMessage(), e);
    }

    @Override
    public void info(Throwable e) {
        logger.log(Level.INFO, e.getMessage(), e);
    }

    @Override
    public void warn(Throwable e) {
        logger.log(Level.WARNING, e.getMessage(), e);
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
	public void trace(BaseLogMsg logMsg) {
		 logger.log(Level.FINER, toMsg(logMsg));
		
	}

	@Override
	public void trace(BaseLogMsg logMsg, String msg) {
		logger.log(Level.FINER, toMsg(logMsg,msg));
	}

	@Override
	public void trace(BaseLogMsg logMsg, Throwable e) {
		  logger.log(Level.FINER, toMsg(logMsg), e);
	}

	@Override
	public void trace(BaseLogMsg logMsg, String msg, Throwable e) {
		logger.log(Level.FINER, toMsg(logMsg,msg), e);
	}

	@Override
	public void debug(BaseLogMsg logMsg) {
		logger.log(Level.FINER, toMsg(logMsg));
	}

	@Override
	public void debug(BaseLogMsg logMsg, String msg) {
		logger.log(Level.FINER, toMsg(logMsg,msg));
	}

	@Override
	public void debug(BaseLogMsg logMsg, Throwable e) {
		logger.log(Level.FINER, toMsg(logMsg), e);
	}

	@Override
	public void debug(BaseLogMsg logMsg, String msg, Throwable e) {
		logger.log(Level.FINER, toMsg(logMsg,msg), e);
	}

	@Override
	public void info(BaseLogMsg logMsg) {
		logger.log(Level.INFO, toMsg(logMsg));
	}

	@Override
	public void info(BaseLogMsg logMsg, String msg) {
		logger.log(Level.INFO, toMsg(logMsg,msg));
	}

	@Override
	public void info(BaseLogMsg logMsg, Throwable e) {
		logger.log(Level.INFO, toMsg(logMsg), e);
	}

	@Override
	public void info(BaseLogMsg logMsg, String msg, Throwable e) {
		logger.log(Level.INFO, toMsg(logMsg,msg), e);
	}

	@Override
	public void warn(BaseLogMsg logMsg) {
		logger.log(Level.WARNING, toMsg(logMsg));
	}

	@Override
	public void warn(BaseLogMsg logMsg, String msg) {
		logger.log(Level.WARNING, toMsg(logMsg,msg));
	}

	@Override
	public void warn(BaseLogMsg logMsg, Throwable e) {
		logger.log(Level.WARNING, toMsg(logMsg),e);
	}

	@Override
	public void warn(BaseLogMsg logMsg, String msg, Throwable e) {
		logger.log(Level.WARNING, toMsg(logMsg,msg),e);
	}

	@Override
	public void error(BaseLogMsg logMsg) {
		logger.log(Level.SEVERE, toMsg(logMsg));
	}

	@Override
	public void error(BaseLogMsg logMsg, String msg) {
		logger.log(Level.SEVERE, toMsg(logMsg,msg));
	}

	@Override
	public void error(BaseLogMsg logMsg, Throwable e) {
		logger.log(Level.SEVERE, toMsg(logMsg),e);
	}

	@Override
	public void error(BaseLogMsg logMsg, String msg, Throwable e) {
		logger.log(Level.SEVERE, toMsg(logMsg,msg),e);
		
	}

}
