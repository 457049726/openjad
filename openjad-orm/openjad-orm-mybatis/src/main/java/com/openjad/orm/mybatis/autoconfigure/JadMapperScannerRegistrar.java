package com.openjad.orm.mybatis.autoconfigure;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.mybatis.spring.annotation.MapperScannerRegistrar;
import org.mybatis.spring.mapper.ClassPathMapperScanner;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.StandardAnnotationMetadata;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import com.openjad.orm.mybatis.annotation.JadMapperScan;
import com.openjad.orm.mybatis.constant.MybatisLogCode;
import com.openjad.logger.api.Logger;
import com.openjad.logger.api.LoggerFactory;

public class JadMapperScannerRegistrar extends MapperScannerRegistrar {

	private static final Logger logger = LoggerFactory.getLogger(JadMapperScannerRegistrar.class);

	private ResourceLoader resourceLoader;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
		super.setResourceLoader(resourceLoader);
	}

	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

		Map<String, Object> attributes = importingClassMetadata.getAnnotationAttributes(JadMapperScan.class.getName());
		AnnotationAttributes mapperScanAttrs = AnnotationAttributes.fromMap(attributes);
		if (mapperScanAttrs != null) {
			try {

				registerBeanDefinitionsJad(mapperScanAttrs, registry, getDefScanPackage(importingClassMetadata));

			} catch (Throwable e) {
				logger.error(MybatisLogCode.CODE_00016,"注册jad-dao失败，程序异常退出," + e.getMessage(), e);
				System.exit(0);
			}

		}
	}

	@SuppressWarnings("rawtypes")
	private void registerBeanDefinitionsJad(AnnotationAttributes annoAttrs, BeanDefinitionRegistry registry, String defScanPackage) {

		 ClassPathMapperScanner scanner = new ClassPathMapperScanner(registry);

		// this check is needed in Spring 3.1
		Optional.ofNullable(resourceLoader).ifPresent(scanner::setResourceLoader);

		Class<? extends Annotation> annotationClass = annoAttrs.getClass("annotationClass");
		if (!Annotation.class.equals(annotationClass)) {
			scanner.setAnnotationClass(annotationClass);
		}

		Class<?> markerInterface = annoAttrs.getClass("markerInterface");
		if (!Class.class.equals(markerInterface)) {
			scanner.setMarkerInterface(markerInterface);
		}

		Class<? extends BeanNameGenerator> generatorClass = annoAttrs.getClass("nameGenerator");
		if (!BeanNameGenerator.class.equals(generatorClass)) {
			scanner.setBeanNameGenerator(BeanUtils.instantiateClass(generatorClass));
		}

		Class<? extends MapperFactoryBean> mapperFactoryBeanClass = annoAttrs.getClass("factoryBean");
		if (!MapperFactoryBean.class.equals(mapperFactoryBeanClass)) {
			scanner.setMapperFactoryBeanClass(mapperFactoryBeanClass);
		}

		scanner.setSqlSessionTemplateBeanName(annoAttrs.getString("sqlSessionTemplateRef"));
		scanner.setSqlSessionFactoryBeanName(annoAttrs.getString("sqlSessionFactoryRef"));

		List<String> basePackages = new ArrayList<>();
		basePackages.addAll(Arrays.stream(annoAttrs.getStringArray("value")).filter(StringUtils::hasText).collect(Collectors.toList()));

		basePackages.addAll(Arrays.stream(annoAttrs.getStringArray("basePackages")).filter(StringUtils::hasText).collect(Collectors.toList()));

		basePackages.addAll(Arrays.stream(annoAttrs.getClassArray("basePackageClasses")).map(ClassUtils::getPackageName).collect(Collectors.toList()));

		// 20190512
		if (basePackages.isEmpty() && defScanPackage != null && defScanPackage.length() > 0) {
			basePackages.add(defScanPackage);
		}

		scanner.registerFilters();
		Set<BeanDefinitionHolder> definitions = scanner.doScan(StringUtils.toStringArray(basePackages));
		
		// 20190512
		for (BeanDefinitionHolder definition : definitions) {
			try {
				String daoClassName = definition.getBeanDefinition().getConstructorArgumentValues()
						.getArgumentValue(0, String.class).getValue().toString();
				JadSqlSessionFactory.NOT_LOAD_CLASS_NAME_SET.add(daoClassName);
			} catch (Exception e) {
				logger.debug(MybatisLogCode.CODE_00009,"获取"+definition.getBeanName()+"对应的接口类失败,忽略,"+e.getMessage(),e);
			}
		}
	}

	private String getDefScanPackage(AnnotationMetadata importingClassMetadata) {
		if (importingClassMetadata != null && (importingClassMetadata instanceof StandardAnnotationMetadata)) {
			Class cl = ((StandardAnnotationMetadata) importingClassMetadata).getIntrospectedClass();
			String defPackage = cl.getPackage().getName();
			return defPackage;
		}
		return null;
	}
}
