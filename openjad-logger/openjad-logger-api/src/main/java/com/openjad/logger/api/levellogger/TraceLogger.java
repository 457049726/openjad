package com.openjad.logger.api.levellogger;

import com.openjad.common.constant.BaseLogMsg;

/**
 * 
 * <一句话文件描述>
 * 
 *  @Title TraceLogger
 *  @author hechuan
 *  @date 2019年9月20日
 *
 *  <功能详述>
 */
public interface TraceLogger {

	/**
	 * trace
	 * @param logMsg
	 */
	public void trace(BaseLogMsg logMsg);
	
	/**
	 * trace
	 * @param logMsg
	 * @param msg
	 */
	public void trace(BaseLogMsg logMsg,String msg);
	
	/**
	 * trace
	 * @param logMsg
	 * @param e
	 */
	public void trace(BaseLogMsg logMsg,Throwable e);
	
	/**
	 * trace
	 * @param logMsg
	 * @param msg
	 * @param e
	 */
	public void trace(BaseLogMsg logMsg,String msg,Throwable e);
	
	
    /**
     * Is trace logging currently enabled?
     *
     * @return true if trace is enabled
     */
    public boolean isTraceEnabled();
    
	/**
     * Logs a message with trace log level.
     *
     * @param msg log this message
     */
    public void trace(String msg);

    /**
     * Logs an error with trace log level.
     *
     * @param e log this cause
     */
    public void trace(Throwable e);

    /**
     * Logs an error with trace log level.
     *
     * @param msg log this message
     * @param e   log this cause
     */
    public void trace(String msg, Throwable e);
}
