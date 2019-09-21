package com.openjad.logger.api.level;

import com.openjad.common.constant.BaseLogMsg;

/**
 * 
 * 
 *  @author hechuan
 *
 */
public interface DebugLogger {
	
	/**
	 * info
	 * @param logMsg param
	 */
	public void debug(BaseLogMsg logMsg);
	
	/**
	 * info
	 * @param logMsg param
	 * @param msg param
	 */
	public void debug(BaseLogMsg logMsg,String msg);
	
	/**
	 * info
	 * @param logMsg param
	 * @param e param
	 */
	public void debug(BaseLogMsg logMsg,Throwable e);
	
	/**
	 * info
	 * @param logMsg param
	 * @param msg param
	 * @param e param
	 */
	public void debug(BaseLogMsg logMsg,String msg,Throwable e);
	

	 /**
     * Is debug logging currently enabled?
     *
     * @return true if debug is enabled
     */
    public boolean isDebugEnabled();
    
    /**
     * Logs a message with debug log level.
     *
     * @param msg log this message
     */
    public void debug(String msg);

    /**
     * Logs an error with debug log level.
     *
     * @param e log this cause
     */
    public void debug(Throwable e);

    /**
     * Logs an error with debug log level.
     *
     * @param msg log this message
     * @param e   log this cause
     */
    public void debug(String msg, Throwable e);
    
    
    

}
