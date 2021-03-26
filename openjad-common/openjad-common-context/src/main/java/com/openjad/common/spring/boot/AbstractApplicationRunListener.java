package com.openjad.common.spring.boot;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import static com.openjad.common.constant.DefaultValueConstants.*;
import static com.openjad.common.constant.CharacterConstants.*;
import static com.openjad.common.constant.PropertiyConstants.*;
import static com.openjad.common.util.ContextUtil.isBootstrapContext;

import com.openjad.common.spring.context.SpringContextHolder;
import com.openjad.common.util.StringUtils;
import com.openjad.common.util.SysInitArgUtils;
import com.openjad.logger.api.Logger;
import com.openjad.logger.api.LoggerFactory;

/**
 * 
 * @author hechuan
 *
 */
public class AbstractApplicationRunListener implements SpringApplicationRunListener {

	private static final Logger logger = LoggerFactory.getLogger(AbstractApplicationRunListener.class);

	protected SpringApplication application;

	protected String[] args;
	
//	public static ConfigurableEnvironment ENVIRONMENT;

	public AbstractApplicationRunListener(SpringApplication application, String[] args) {
		this.application = application;
		this.args = args;
	}

	/**
	 * spring boot 2.x 系统开始启时调用
	 */
	@Override
	public void starting() {
		sysStarting();
	}

	@Override
	public void environmentPrepared(ConfigurableEnvironment environment) {
		if (isBootstrapContext(environment)) {
			return;
		}
//		ENVIRONMENT = environment;
	}

	@Override
	public void contextPrepared(ConfigurableApplicationContext context) {
		if (isBootstrapContext(context)) {
			return;
		}
	}
	
	@Override
	public void contextLoaded(ConfigurableApplicationContext context) {
		if (isBootstrapContext(context)) {
			return;
		}
	}

	/**
	 * spring boot 2.x 系统完成时调用
	 */
	@Override
	public void started(ConfigurableApplicationContext context) {
		if (isBootstrapContext(context)) {
			return;
		}
		sysStarted(context);
	}

	@Override
	public void running(ConfigurableApplicationContext context) {
	}

	@Override
	public void failed(ConfigurableApplicationContext context, Throwable exception) {
	}

	/**
	 * spring boot 1.x 系统开始启时调用
	 */
	public void started() {
		sysStarting();
	}

	/**
	 * spring boot 1.x 系统完成时调用
	 * 
	 * @param context param 
	 * @param exception param
	 */
	public void finished(ConfigurableApplicationContext context, Throwable exception) {
		sysStarted(context);
	}

	
	
	/**
	 * 系统开始启动
	 */
	public void sysStarting() {
	}

	/**
	 * 系统启动完成，子类可继承
	 * 
	 * @param context param
	 */
	protected void sysStarted(ConfigurableApplicationContext context) {
	}

	


	protected void putApplicationParams(ConfigurableEnvironment environment, String key, String value) {
		if (StringUtils.isBlank(key) || StringUtils.isBlank(value)) {
			logger.warn("无法识别的系统参数,缺失相关信息，key:" + key + ",value:" + value);
			return;
		}
		Map<String, Object> map = new HashMap<String, Object>(DEF_COLLECT_CAPACITY);
		map.put(key, value);
		MapPropertySource mps = new MapPropertySource(key, map);
		environment.getPropertySources().addLast(mps);
	}

	protected static String getProperty(ConfigurableEnvironment environment, String key) {
		String value = environment.getProperty(key);
		if (StringUtils.isBlank(value)) {
			value = getSystemProperty(key);
		}
		return value;
	}

	protected static String getSystemProperty(String key) {
		String value = System.getenv(key);
		if (StringUtils.isBlank(value)) {
			value = System.getProperty(key);
		}
		return value;
	}

}
