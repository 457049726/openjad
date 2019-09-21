package com.openjad.common.spring.boot;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import static com.openjad.common.constant.DefaultValueConstants.*;
import static com.openjad.common.constant.CharacterConstants.*;
import static com.openjad.common.constant.PropertiyConstants.*;

import com.openjad.common.util.StringUtils;
import com.openjad.common.util.SysInitArgUtils;
import com.openjad.logger.api.Logger;
import com.openjad.logger.api.LoggerFactory;

/**
 * 
 * @Title AbstractApplicationRunListener
 * @author hechuan
 * @date 2019年9月20日
 *
 *       <功能简述>
 */
public class AbstractApplicationRunListener implements SpringApplicationRunListener {

	private static final Logger logger = LoggerFactory.getLogger(AbstractApplicationRunListener.class);

	protected SpringApplication application;

	protected String[] args;

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
	}

	@Override
	public void contextPrepared(ConfigurableApplicationContext context) {
	}

	@Override
	public void contextLoaded(ConfigurableApplicationContext context) {
	}

	/**
	 * spring boot 2.x 系统完成时调用
	 */
	@Override
	public void started(ConfigurableApplicationContext context) {
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
	 * @param context
	 * @param exception
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
	 * @param context
	 */
	protected void sysStarted(ConfigurableApplicationContext context) {
	}

	protected void logSysStarted(ConfigurableApplicationContext context) {
		logger.info("系统初始化完成" + getStartLogstr(context));
	}

	private String getStartLogstr(ConfigurableApplicationContext context) {

		String loggerkeys = APPLICATION_STARTED_LOGGER_KEYS;

		Map<String, String> map = SysInitArgUtils.getPropertiesFromEnvironment(context.getEnvironment(), true);
		map.putAll(SysInitArgUtils.getPropertiesFromSystem(true));

		StringBuffer sb = new StringBuffer();

		if (StringUtils.isBlank(map.get(loggerkeys))) {
			return BLANK_STR;
		}

		String[] namekeys = loggerkeys.split(Y_AXIS_CONVERT_CHARACTER);

		boolean first = true;
		for (String nameKey : namekeys) {
			String[] nameKeyarr = nameKey.split(COLON_CHARACTER);
			if (nameKeyarr.length != 2) {
				continue;
			}
			String name = nameKeyarr[0];
			String key = nameKeyarr[1];
			if (StringUtils.isNotBlank(map.get(key))) {
				if (first) {
					first = false;
				} else {
					sb.append(COMMA_CHARACTER);
				}
				sb.append(name).append(COLON_CHARACTER).append(map.get(key));
			}
		}
		if (sb.length() > 0) {
			sb.insert(0, COMMA_CHARACTER);
		}

//		if (map.containsKey("spring.application.name")) {
//			sb.append("appName:").append(map.get("spring.application.name"));
//		}
//		if (map.containsKey("app.workerid")) {
//			sb.append(",workerid:").append(map.get("app.workerid"));
//		}
//
//		if (map.containsKey("pid")) {
//			sb.append(",pid:").append(map.get("pid"));
//		}
//		if (map.containsKey("env")) {
//			sb.append(",env:").append(map.get("env"));
//		}
//		if (map.containsKey("server.port")) {
//			try {
//				if (Integer.parseInt(map.get("server.port")) > 0) {
//					sb.append(",webPort:").append(map.get("server.port"));
//				}
//			} catch (NumberFormatException e) {
//				logger.debug("无法识别的web端口：" + map.get("server.port"));
//			}
//		}
//		if (map.containsKey("dubbo.protocol.port")) {
//			sb.append(",dubboPort:").append(map.get("dubbo.protocol.port"));
//		}
//
//		if (map.containsKey("dubbo.registry.address")) {
//			sb.append(",registryAddress:").append(map.get("dubbo.registry.address"));
//		}
//
//		if (map.containsKey("dubbo.registry.file")) {
//			sb.append(",registryFile:").append(map.get("dubbo.registry.file"));
//		}
//
//		if (map.containsKey("java_home")) {
//			sb.append(",java_home:").append(map.get("java_home"));
//		} else if (map.containsKey("java.home")) {
//			sb.append(",java_home:").append(map.get("java.home"));
//		}
//
//		if (map.containsKey("java.io.tmpdir")) {
//			sb.append(",tmpdir:").append(map.get("java.io.tmpdir"));
//		}

		return sb.toString();
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
