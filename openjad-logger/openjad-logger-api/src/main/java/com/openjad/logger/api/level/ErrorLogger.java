package com.openjad.logger.api.level;

import com.openjad.common.constant.BaseLogCode;

/**
 * 
 * 
 *  @author hechuan
 *
 */
public interface ErrorLogger {
	
	/**
	 * info
	 * @param logMsg param
	 */
	public void error(BaseLogCode logMsg);
	
	/**
	 * info
	 * @param logMsg param
	 * @param msg param
	 */
	public void error(BaseLogCode logMsg,String msg);
	
	/**
	 * info
	 * @param logMsg param
	 * @param e param
	 */
	public void error(BaseLogCode logMsg,Throwable e);
	
	/**
	 * info
	 * @param logMsg param
	 * @param msg param
	 * @param e param
	 */
	public void error(BaseLogCode logMsg,String msg,Throwable e);
	
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
