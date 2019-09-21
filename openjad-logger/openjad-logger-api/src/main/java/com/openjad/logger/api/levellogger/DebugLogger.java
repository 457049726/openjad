package com.openjad.logger.api.levellogger;

import com.openjad.common.constant.BaseLogMsg;

/**
 * 
 * <一句话文件描述>
 * 
 *  @Title DebugLogger
 *  @author hechuan
 *  @date 2019年9月20日
 *
 *  <功能详述>
 */
public interface DebugLogger {
	
	/**
	 * info
	 * @param logMsg
	 */
	public void debug(BaseLogMsg logMsg);
	
	/**
	 * info
	 * @param logMsg
	 * @param msg
	 */
	public void debug(BaseLogMsg logMsg,String msg);
	
	/**
	 * info
	 * @param logMsg
	 * @param e
	 */
	public void debug(BaseLogMsg logMsg,Throwable e);
	
	/**
	 * info
	 * @param logMsg
	 * @param msg
	 * @param e
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
