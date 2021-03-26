package com.openjad.orm.ds;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源
 * 
 * @author hechuan
 * 
 */
public class DynamicRountingDataSource extends AbstractRoutingDataSource {

	private String defaultDataSourceName;

	@Override
	protected Object determineCurrentLookupKey() {
		return DataSourceNameManager.get();
	}

	public void afterPropertiesSet() {
		super.afterPropertiesSet();
		if (defaultDataSourceName == null || "".equals(defaultDataSourceName.trim())) {
			throw new RuntimeException("请指定默认数据源名称");
		}
		DataSourceNameManager.setDefaultDataSourceName(defaultDataSourceName);
	}

	public String getDefaultDataSourceName() {
		return defaultDataSourceName;
	}

	public void setDefaultDataSourceName(String defaultDataSourceName) {
		this.defaultDataSourceName = defaultDataSourceName;
	}

}
