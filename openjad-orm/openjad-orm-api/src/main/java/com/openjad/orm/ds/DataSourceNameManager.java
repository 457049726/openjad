package com.openjad.orm.ds;

/**
 * 数据源名称管理器
 * 
 * @author hechuan
 *
 */
public class DataSourceNameManager {

	/**
	 * 当前数据源名称
	 */
	private static ThreadLocal<String> CURR_DATA_SOURCE_NAME = new ThreadLocal<String>();

	/**
	 * 默认数据源名称
	 */
	private static String defaultDataSourceName;

	public static String get() {
		return CURR_DATA_SOURCE_NAME.get() == null ? defaultDataSourceName : CURR_DATA_SOURCE_NAME.get();
	}

	public static void set(String dataSourceName) {
		CURR_DATA_SOURCE_NAME.set(dataSourceName);
	}

	public static void reset() {
		set(defaultDataSourceName);
	}

	public static void setDefaultDataSourceName(String defaultDataSourceName) {
		DataSourceNameManager.defaultDataSourceName = defaultDataSourceName;
	}

}
