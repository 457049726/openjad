package com.openjad.common.spring.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.openjad.common.constant.FrameworkCode;
import com.openjad.common.exception.BizException;

public class SpringContextHolder implements ApplicationContextAware {

	private static ApplicationContext applicationContext;
	
	private static ConfigurableListableBeanFactory beanFactory;

	/**
	 * 取得存储在静态变量中的ApplicationContext.
	 * @return ApplicationContext
	 */
	public static ApplicationContext getApplicationContext() {
		assertContextInjected();
		return applicationContext;
	}

	/**
	 * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 * @param <T> t
	 * @param name name
	 * @return Bean
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		assertContextInjected();
		return (T) applicationContext.getBean(name);
	}

	/**
	 * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 * @param <T> t
	 * @param requiredType requiredType
	 * @return Bean
	 */
	public static <T> T getBean(Class<T> requiredType) {
		assertContextInjected();
		return applicationContext.getBean(requiredType);
	}

	/**
	 * 初始化 context
	 * @param appContext appContext
	 */
	public static void initApplicationContext(ApplicationContext appContext) {
		applicationContext = appContext;
	}

	/**
	 * 检查ApplicationContext不为空.
	 */
	private static void assertContextInjected() {
		if(applicationContext==null){
			throw new BizException(FrameworkCode.CODE_00002);
		}
		
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//		if (SpringContextHolder.applicationContext == null) {
//			SpringContextHolder.applicationContext = applicationContext;
//		}
	}

	public static ConfigurableListableBeanFactory getBeanFactory() {
		return beanFactory;
	}

	public static void setBeanFactory(ConfigurableListableBeanFactory beanFactory) {
		SpringContextHolder.beanFactory = beanFactory;
	}
	
	public static void setStaticApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextHolder.applicationContext = applicationContext;
	}

}
