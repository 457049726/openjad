package com.openjad.logger.log4j2;

/**
 * 
 * 
 *  @author hechuan
 *
 */
public class Log4j2Constants {
	public static final String DEFAULT_LOG4J_FILE = "log4j2.xml";
	public static final String OPENJAD_LOG4J_FILE = "openjad-log4j2.xml";
	
	/**
	 * 日志输出目录
	 * 
	 * 如果在启动脚本中指定了指参数，则使用此参数值作为日志输出目录
	 * 
	 * 默认为 {classPath}/logs 目录中
	 * 
	 */
	public static final String LOG_DIR_PATH = "logDirPath";

	/**
	 * 可以在启动脚本中配置此参数来指定 log4j.xml文件所在路径
	 * 
	 */
	public static final String LOG4J_CONFIG_PATH = "logging.config";

}
