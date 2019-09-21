package com.openjad.common.spring.boot;

import static com.openjad.common.util.ContextUtil.isBootstrapContext;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * 
 *  @author hechuan
 * 
 *  最后执行
 */
@Order(Ordered.LOWEST_PRECEDENCE)
public class ApplicationStartedRunListener extends AbstractApplicationRunListener {

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
	

}
