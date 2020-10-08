package com.openjad.logger.api.level;

import com.openjad.common.constant.BaseLogCode;

/**
 * 
 * 
 *  @author hechuan
 *
 */
public interface TraceLogger {

	/**
	 * trace
	 * @param logMsg param
	 */
	public void trace(BaseLogCode logMsg);
	
	/**
	 * trace
	 * @param logMsg   param
	 * @param msg param
	 */
	public void trace(BaseLogCode logMsg,String msg);
	
	/**
	 * trace
	 * @param logMsg param
	 * @param e param
	 */
	public void trace(BaseLogCode logMsg,Throwable e);
	
	/**
	 * trace
	 * @param logMsg param
	 * @param msg param
	 * @param e param
	 */
	public void trace(BaseLogCode logMsg,String msg,Throwable e);
	
	
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
