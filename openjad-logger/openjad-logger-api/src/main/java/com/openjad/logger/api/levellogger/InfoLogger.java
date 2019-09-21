package com.openjad.logger.api.levellogger;

import com.openjad.common.constant.BaseLogMsg;

/**
 * 
 * <一句话文件描述>
 * 
 *  @Title InfoLogger
 *  @author hechuan
 *  @date 2019年9月20日
 *
 *  <功能详述>
 */
public interface InfoLogger {
	
	/**
	 * info
	 * @param logMsg
	 */
	public void info(BaseLogMsg logMsg);
	
	/**
	 * info
	 * @param logMsg
	 * @param msg
	 */
	public void info(BaseLogMsg logMsg,String msg);
	
	/**
	 * info
	 * @param logMsg
	 * @param e
	 */
	public void info(BaseLogMsg logMsg,Throwable e);
	
	/**
	 * info
	 * @param logMsg
	 * @param msg
	 * @param e
	 */
	public void info(BaseLogMsg logMsg,String msg,Throwable e);
	
	 /**
     * Is info logging currently enabled?
     *
     * @return true if info is enabled
     */
    public boolean isInfoEnabled();

	 /**
     * Logs a message with info log level.
     *
     * @param msg log this message
     */
    public void info(String msg);

    /**
     * Logs an error with info log level.
     *
     * @param e log this cause
     */
    public void info(Throwable e);

    /**
     * Logs an error with info log level.
     *
     * @param msg log this message
     * @param e   log this cause
     */
    public void info(String msg, Throwable e);
    
}
