package com.openjad.common.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

/**
 * 
 *  @Title ContextUtils
 *  @author hechuan
 *  @date 2019年9月20日
 *
 *  <功能简述>
 */
public class ContextUtil {

	private static final String BOOTSTRAP = "bootstrap";

	public static boolean isBootstrapContext(ConfigurableEnvironment environment) {
		if (environment != null && environment.getPropertySources() != null
				&& environment.getPropertySources().contains(BOOTSTRAP)) {
			return true;
		}
		return false;
	}

	public static boolean isBootstrapContext(ApplicationContext context) {
		if (context != null && context.getEnvironment() != null) {
			Environment environment = context.getEnvironment();
			if (environment instanceof ConfigurableEnvironment) {
				return isBootstrapContext((ConfigurableEnvironment) environment);
			}
		}
		return false;

	}

	public static boolean isBootstrapContext(ContextRefreshedEvent event) {
		if (event != null && event.getApplicationContext() != null) {
			return isBootstrapContext(event.getApplicationContext());
		}
		return false;

	}

}
