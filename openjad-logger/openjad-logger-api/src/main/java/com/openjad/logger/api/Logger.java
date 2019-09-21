package com.openjad.logger.api;

import com.openjad.logger.api.level.DebugLogger;
import com.openjad.logger.api.level.ErrorLogger;
import com.openjad.logger.api.level.InfoLogger;
import com.openjad.logger.api.level.TraceLogger;
import com.openjad.logger.api.level.WarnLogger;

/**
 * 
 * 
 *  @author hechuan
 *
 */
public interface Logger extends TraceLogger,DebugLogger,InfoLogger,WarnLogger,ErrorLogger{

}
