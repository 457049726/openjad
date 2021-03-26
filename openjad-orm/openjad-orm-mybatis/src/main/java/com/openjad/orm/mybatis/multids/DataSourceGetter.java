package com.openjad.orm.mybatis.multids;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.openjad.orm.annotation.DataSource;

public class DataSourceGetter {


	// 缓存标注在类的数据源
	private Map<Class, String> classDataSourceCache = new HashMap<Class, String>();

	/**
	 * datasource 缓存
	 */
	private Map<Class, Map<Method, String>> dataSourceCache = new HashMap<Class, Map<Method, String>>();

	public static final String DEFAULT_DATA_SOURCE = "_DEFAULT_DATA_SOURCE";

	public String getDataSourceName(Class clazz, Method method) {
		if (clazz == null || method == null) {
			return DEFAULT_DATA_SOURCE;
		}

		if (dataSourceCache.containsKey(clazz)) {
			if (dataSourceCache.get(clazz).containsKey(method)) {
				return dataSourceCache.get(clazz).get(method);// 缓存命中
			}
		} else {
			dataSourceCache.put(clazz, new HashMap<Method, String>());
		}

		String dataSourceName = getDataSourceNameByMethod(method);// 从方法上找
		if (dataSourceName == null) {
			dataSourceName = getDataSourceNameByClass(clazz);// 从类上找
		}

		dataSourceCache.get(clazz).put(method, dataSourceName);// 缓存起来
		return dataSourceName;
	}

	public String getDataSourceNameByMethod(Method method) {
		DataSource ds = method.getAnnotation(DataSource.class);
		if (ds == null || ds.value() == null || "".equals(ds.value())) {
			return null;
		} else {
			return ds.value();
		}
	}

	public String getDataSourceNameByClass(Class clazz) {
		if (classDataSourceCache.containsKey(clazz)) {
			return classDataSourceCache.get(clazz);
		}

		String dataSourceName = DEFAULT_DATA_SOURCE;

		if (clazz.getAnnotation(DataSource.class) != null) {
			DataSource ds = (DataSource) clazz.getAnnotation(DataSource.class);
			if (ds.value() != null && !"".equals(ds.value())) {
				dataSourceName = ds.value();
			}
		}
		classDataSourceCache.put(clazz, dataSourceName);
		return dataSourceName;
	}

//	public String getDataSourceName(Method method) {
//		if (method == null) {
//			return DEFAULT_DATA_SOURCE;
//		}
//		if (methodDataSourceCache.containsKey(method)) {
//			return methodDataSourceCache.get(method);
//		}
//		DataSource ds = method.getAnnotation(DataSource.class);
//		if (ds != null) {
//			methodDataSourceCache.put(method, ds.value());
//			return ds.value();
//		}
//
//		Class clazz = method.getDeclaringClass();
//		if (classDataSourceCache.containsKey(clazz)) {
//			methodDataSourceCache.put(method, classDataSourceCache.get(clazz));
//			return classDataSourceCache.get(clazz);
//		} else if (clazz.getAnnotation(DataSource.class) != null) {// 20200529
//			ds = (DataSource) clazz.getAnnotation(DataSource.class);
//			methodDataSourceCache.put(method, ds.value());
//			classDataSourceCache.put(clazz, ds.value());
//			return ds.value();
//		} else {
//			classDataSourceCache.put(clazz, DEFAULT_DATA_SOURCE);
//			methodDataSourceCache.put(method, DEFAULT_DATA_SOURCE);
//			return DEFAULT_DATA_SOURCE;
//		}
//	}
}
