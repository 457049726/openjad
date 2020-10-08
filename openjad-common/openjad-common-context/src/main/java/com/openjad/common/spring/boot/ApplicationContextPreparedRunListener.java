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
 * @author hechuan
 * 
 *         最后执行
 */
@Order(Ordered.LOWEST_PRECEDENCE - 100)
public class ApplicationContextPreparedRunListener extends AbstractApplicationRunListener {

	public ApplicationContextPreparedRunListener(SpringApplication application, String[] args) {
		super(application, args);
	}

	@Override
	public void contextPrepared(ConfigurableApplicationContext context) {
		if(isBootstrapContext(context)){
			return ;
		}
		setBeanFactoryInfo(context);
	}
	
	private void setBeanFactoryInfo(ConfigurableApplicationContext context){
		ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
		SpringContextHolder.setBeanFactory(beanFactory);
		SpringContextHolder.setStaticApplicationContext(context);
	}
}
