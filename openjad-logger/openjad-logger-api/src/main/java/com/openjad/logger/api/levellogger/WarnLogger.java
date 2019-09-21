package com.openjad.logger.api.levellogger;

import com.openjad.common.constant.BaseLogMsg;

/**
 * 
 * <一句话文件描述>
 * 
 *  @Title WarnLogger
 *  @author hechuan
 *  @date 2019年9月20日
 *
 *  <功能详述>
 */
public interface WarnLogger {
	
	/**
	 * warn
	 * @param logMsg
	 */
	public void warn(BaseLogMsg logMsg);
	
	/**
	 * warn
	 * @param logMsg
	 * @param msg
	 */
	public void warn(BaseLogMsg logMsg,String msg);
	
	/**
	 * warn
	 * @param logMsg
	 * @param e
	 */
	public void warn(BaseLogMsg logMsg,Throwable e);
	
	/**
	 * warn
	 * @param logMsg
	 * @param msg
	 * @param e
	 */
	public void warn(BaseLogMsg logMsg,String msg,Throwable e);


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
