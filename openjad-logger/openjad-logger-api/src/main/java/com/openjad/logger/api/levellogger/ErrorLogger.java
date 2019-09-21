package com.openjad.logger.api.levellogger;

import com.openjad.common.constant.BaseLogMsg;

/**
 * 
 * <一句话文件描述>
 * 
 *  @Title ErrorLogger
 *  @author hechuan
 *  @date 2019年9月20日
 *
 *  <功能详述>
 */
public interface ErrorLogger {
	
	/**
	 * info
	 * @param logMsg
	 */
	public void error(BaseLogMsg logMsg);
	
	/**
	 * info
	 * @param logMsg
	 * @param msg
	 */
	public void error(BaseLogMsg logMsg,String msg);
	
	/**
	 * info
	 * @param logMsg
	 * @param e
	 */
	public void error(BaseLogMsg logMsg,Throwable e);
	
	/**
	 * info
	 * @param logMsg
	 * @param msg
	 * @param e
	 */
	public void error(BaseLogMsg logMsg,String msg,Throwable e);
	
	 /**
     * Is error logging currently enabled?
     *
     * @return true if error is enabled
     */
    public boolean isErrorEnabled();

    /**
     * Logs a message with error log level.
     *
     * @param msg log this message
     */
    public void error(String msg);

    /**
     * Logs an error with error log level.
     *
     * @param e log this cause
     */
    public void error(Throwable e);

    /**
     * Logs an error with error log level.
     *
     * @param msg log this message
     * @param e   log this cause
     */
    public void error(String msg, Throwable e);

}
