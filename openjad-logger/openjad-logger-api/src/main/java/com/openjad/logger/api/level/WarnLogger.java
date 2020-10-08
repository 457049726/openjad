package com.openjad.logger.api.level;

import com.openjad.common.constant.BaseLogCode;

/**
 * 
 * 
 *  @author hechuan
 *
 */
public interface WarnLogger {
	
	/**
	 * warn
	 * @param logMsg param
	 */
	public void warn(BaseLogCode logMsg);
	
	/**
	 * warn
	 * @param logMsg param
	 * @param msg param
	 */
	public void warn(BaseLogCode logMsg,String msg);
	
	/**
	 * warn
	 * @param logMsg param
	 * @param e param
	 */
	public void warn(BaseLogCode logMsg,Throwable e);
	
	/**
	 * warn
	 * @param logMsg param
	 * @param msg param
	 * @param e param
	 */
	public void warn(BaseLogCode logMsg,String msg,Throwable e);


    /**
     * Is warn logging currently enabled?
     *
     * @return true if warn is enabled
     */
    public boolean isWarnEnabled();
    
	 /**
     * Logs a message with warn log level.
     *
     * @param msg log this message
     */
    public void warn(String msg);

    /**
     * Logs a message with warn log level.
     *
     * @param e log this message
     */
    public void warn(Throwable e);

    /**
     * Logs a message with warn log level.
     *
     * @param msg log this message
     * @param e   log this cause
     */
    public void warn(String msg, Throwable e);
    
}
