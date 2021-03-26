package com.openjad.orm.mybatis.multids;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.AopUtils;

import com.openjad.logger.api.Logger;
import com.openjad.logger.api.LoggerFactory;
import com.openjad.orm.ds.DataSourceNameManager;

/**
 * 多数据源拦截器
 * 
 * @author hechuan
 *
 */
public class MultidsInterceptor implements MethodInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(MultidsInterceptor.class);

	private DataSourceGetter dataSourceGetter;

	protected void beginInvke(MethodInvocation invocation) {
		Method method = null;
		Class clazz = null;
		try {
			method = getTargetMethod(invocation);
			clazz =getTargetClass(invocation);
			String dataSourceName = dataSourceGetter.getDataSourceName(clazz,method);// 获得数据源名称
			if (DataSourceGetter.DEFAULT_DATA_SOURCE.equals(dataSourceName)) {
				DataSourceNameManager.reset();
			} else {
				DataSourceNameManager.set(dataSourceName);
			}
		} catch (Exception e) {
			StringBuffer methodStr = new StringBuffer();
			try {
				Class targetClass = getTargetClass(invocation);
				if (targetClass != null) {
					methodStr.append(targetClass.getName());
				}
				if (method != null) {
					methodStr.append(".").append(method.getName());
				}
			} catch (Throwable e1) {
				logger.error(e1.getMessage(), e1);
			}

			logger.error("多数据源拦截器拦截方法[" + methodStr + "]失败" + e.getMessage(), e);
		}
	}

	protected void finallyInvke(MethodInvocation invocation) {
		DataSourceNameManager.reset();
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		init();
		Object obj = null;
		try {
			beginInvke(invocation);
			obj = invocation.proceed();
		} finally {
			finallyInvke(invocation);
		}
		return obj;
	}

	protected void init() {
		if (dataSourceGetter == null) {
			dataSourceGetter = new DataSourceGetter();
		}
	}

	private Method getTargetMethod(MethodInvocation invocation) {
		Method method = invocation.getMethod();
		return method;
	}

	private Class getTargetClass(MethodInvocation invocation) {
		if (invocation == null) {
			return null;
		}
		Class<?> targetClass = (invocation.getThis() != null ? AopUtils.getTargetClass(invocation.getThis()) : null);
		if (targetClass != null) {
			return targetClass;
		}
		Method method = invocation.getMethod();
		if (method == null) {
			return null;
		}
		Class declaringClass = method.getDeclaringClass();
		return declaringClass;
	}

	public DataSourceGetter getDataSourceGetter() {
		return dataSourceGetter;
	}

	public void setDataSourceGetter(DataSourceGetter dataSourceGetter) {
		this.dataSourceGetter = dataSourceGetter;
	}

}
