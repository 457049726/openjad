package com.openjad.common.spring.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import static com.openjad.common.util.ContextUtil.*;

/**
 * 
 *  @author hechuan
 *
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApplicationBannerRunListener extends AbstractApplicationRunListener {

	private static final String BANNER_FILE_NAME = "openjadBanner.txt";

	public ApplicationBannerRunListener(SpringApplication application, String[] args) {
		super(application, args);
	}

	@Override
	public void environmentPrepared(ConfigurableEnvironment environment) {
		if(isBootstrapContext(environment)){
			return ;
		}
		ResourceLoader resourceLoader = application.getResourceLoader() != null ? application.getResourceLoader()
				: new DefaultResourceLoader(application.getClassLoader());
		Resource resource = resourceLoader.getResource(BANNER_FILE_NAME);
		application.setBanner(new OpenjadApplicationBanner(resource));

	}

}
