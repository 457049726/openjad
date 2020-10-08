package com.openjad.common.spring.boot;

import static com.openjad.common.constant.CharacterConstants.BLANK_STR;
import static com.openjad.common.constant.CharacterConstants.COLON_CHARACTER;
import static com.openjad.common.constant.CharacterConstants.COMMA_CHARACTER;
import static com.openjad.common.constant.CharacterConstants.Y_AXIS_CONVERT_CHARACTER;
import static com.openjad.common.constant.PropertiyConstants.APPLICATION_STARTED_LOGGER_KEYS;
import static com.openjad.common.util.ContextUtil.isBootstrapContext;

import java.util.Map;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import com.openjad.common.spring.context.SpringContextHolder;
import com.openjad.common.util.StringUtils;
import com.openjad.common.util.SysInitArgUtils;
import com.openjad.logger.api.Logger;
import com.openjad.logger.api.LoggerFactory;

/**
 * 
 *  @author hechuan
 * 
 *  最后执行
 */
@Order(Ordered.LOWEST_PRECEDENCE)
public class ApplicationStartedRunListener extends AbstractApplicationRunListener {
	
	private static final Logger logger = LoggerFactory.getLogger(ApplicationStartedRunListener.class);

	public ApplicationStartedRunListener(SpringApplication application, String[] args) {
		super(application,args);
	}
	
	@Override
	protected void sysStarted(ConfigurableApplicationContext context) {
		if(isBootstrapContext(context)){
			return ;
		}
		logSysStarted(context);
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
	

}
