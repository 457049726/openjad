package com.openjad.common.spring.boot;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import com.openjad.common.util.SysInitArgUtils;

public class BasePabckageReader implements ImportBeanDefinitionRegistrar,  EnvironmentAware {

	private Environment environment;

//	private BeanFactory beanFactory;

	private ResourceLoader resourceLoader = new DefaultResourceLoader();


	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		String packageNames = getPackagesFormEnviron();
		if (StringUtils.hasText(packageNames)) {
			AutoConfigurationPackages.register(registry, packageNames);

			String[] packageNamesArr = StringUtils.tokenizeToStringArray(packageNames, ",");
			ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry, true, environment, resourceLoader);
			scanner.scan(packageNamesArr);
		}
		
//		else {
//			packageNames = "com.openjad.orm";
//			AutoConfigurationPackages.register(registry, packageNames);
//		}

//		String[] allPackages = getAllPackages();
//		if (allPackages != null && allPackages.length > 0) {
//			ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry, true, environment, resourceLoader);
//			scanner.scan(allPackages);
//		}

	}

//	private String[] getAllPackages() {
//		if (beanFactory != null) {
//			List<String> list = AutoConfigurationPackages.get(beanFactory);
//			Set<String> merged = new LinkedHashSet<>();
//			for (String pack : list) {
//				if (StringUtils.hasText(pack)) {
//					merged.add(pack.trim());
//				}
//			}
//			return StringUtils.toStringArray(merged);
//		}
//		return null;
//	}

	private String getPackagesFormEnviron() {
		if (this.environment != null && (this.environment instanceof ConfigurableEnvironment)) {
			String packagestr = SysInitArgUtils.getPackagesFromEnviron((ConfigurableEnvironment) environment);
			if (packagestr != null) {
				return packagestr.trim();
			}
		}
		return null;

	}

	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

}
