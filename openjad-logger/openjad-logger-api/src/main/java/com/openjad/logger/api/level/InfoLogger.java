package com.openjad.logger.api.level;

import com.openjad.common.constant.BaseLogCode;

/**
 * 
 * 
 *  @author hechuan
 *
 */
public interface InfoLogger {
	
	/**
	 * info
	 * @param logMsg param
	 */
	public void info(BaseLogCode logMsg);
	
	/**
	 * info
	 * @param logMsg param
	 * @param msg param
	 */
	public void info(BaseLogCode logMsg,String msg);
	
	/**
	 * info
	 * @param logMsg param
	 * @param e param
	 */
	public void info(BaseLogCode logMsg,Throwable e);
	
	/**
	 * info
	 * @param logMsg param
	 * @param msg param
	 * @param e param
	 */
	public void info(BaseLogCode logMsg,String msg,Throwable e);
	
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
