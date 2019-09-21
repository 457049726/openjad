package com.openjad.logger.api;

import com.openjad.logger.api.levellogger.DebugLogger;
import com.openjad.logger.api.levellogger.ErrorLogger;
import com.openjad.logger.api.levellogger.InfoLogger;
import com.openjad.logger.api.levellogger.TraceLogger;
import com.openjad.logger.api.levellogger.WarnLogger;

/**
 * 
 * <一句话文件描述>
 * 
 *  @Title Logger
 *  @author hechuan
 *  @date 2019年9月20日
 *
 *  <功能详述>
 */
public interface Logger extends TraceLogger,DebugLogger,InfoLogger,WarnLogger,ErrorLogger{

}
