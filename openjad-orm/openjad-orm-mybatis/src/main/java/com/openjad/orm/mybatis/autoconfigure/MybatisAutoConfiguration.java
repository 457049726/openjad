package com.openjad.orm.mybatis.autoconfigure;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.sql.DataSource;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.openjad.logger.api.Logger;
import com.openjad.logger.api.LoggerFactory;
import com.openjad.orm.annotation.JadDao;
import com.openjad.orm.mybatis.mapper.JadSqlSessionFactoryBean;
import com.openjad.orm.mybatis.scanner.JadMapperScannerConfigurer;

@org.springframework.context.annotation.Configuration
@ConditionalOnClass({ SqlSessionFactory.class, SqlSessionFactoryBean.class })
//@ConditionalOnSingleCandidate(DataSource.class)
@EnableConfigurationProperties(MybatisProperties.class)
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
public class MybatisAutoConfiguration implements InitializingBean  {

	private static final Logger logger = LoggerFactory.getLogger(MybatisAutoConfiguration.class);

	private final MybatisProperties properties;

	private final Interceptor[] interceptors;

	private final ResourceLoader resourceLoader;

	private final DatabaseIdProvider databaseIdProvider;

	private final List<ConfigurationCustomizer> configurationCustomizers;

	public MybatisAutoConfiguration(MybatisProperties properties, 
			ObjectProvider<Interceptor[]> interceptorsProvider,
			ResourceLoader resourceLoader, 
			ObjectProvider<DatabaseIdProvider> databaseIdProvider,
			ObjectProvider<List<ConfigurationCustomizer>> configurationCustomizersProvider) {
		this.properties = properties;
		this.interceptors = interceptorsProvider.getIfAvailable();
		this.resourceLoader = resourceLoader;
		this.databaseIdProvider = databaseIdProvider.getIfAvailable();
		this.configurationCustomizers = configurationCustomizersProvider.getIfAvailable();
	}

	@Override
	public void afterPropertiesSet() {
		checkConfigFileExists();
	}

	private void checkConfigFileExists() {
		if (this.properties.isCheckConfigLocation() && StringUtils.hasText(this.properties.getConfigLocation())) {
			Resource resource = this.resourceLoader.getResource(this.properties.getConfigLocation());
			Assert.state(resource.exists(),
					"Cannot find config location: " + resource + " (please add config file or check your Mybatis configuration)");
		}
	}

	@Bean
	@ConditionalOnMissingBean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource,JadMapperScannerConfigurer scannerConfigurer) throws Exception {
//		SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
		SqlSessionFactoryBean factory = new JadSqlSessionFactoryBean();// 20200329
		factory.setDataSource(dataSource);
		factory.setVfs(SpringBootVFS.class);

		// 默认配置,20200329
		if (!StringUtils.hasText(this.properties.getConfigLocation())) {
			this.properties.setConfigLocation("classpath:mybatis/jad-mybatis-config.xml");
		}
		if (this.properties.getMapperLocations() == null || this.properties.getMapperLocations().length == 0) {
			this.properties.setMapperLocations(new String[] { "classpath*:**/*Mapper.xml" });
		}

		if (StringUtils.hasText(this.properties.getConfigLocation())) {
			factory.setConfigLocation(this.resourceLoader.getResource(this.properties.getConfigLocation()));
		}
		applyConfiguration(factory);
		if (this.properties.getConfigurationProperties() != null) {
			factory.setConfigurationProperties(this.properties.getConfigurationProperties());
		}
		if (!ObjectUtils.isEmpty(this.interceptors)) {
			factory.setPlugins(this.interceptors);
		}
		if (this.databaseIdProvider != null) {
			factory.setDatabaseIdProvider(this.databaseIdProvider);
		}
		if (StringUtils.hasLength(this.properties.getTypeAliasesPackage())) {
			factory.setTypeAliasesPackage(this.properties.getTypeAliasesPackage());
		}
		if (this.properties.getTypeAliasesSuperType() != null) {
			factory.setTypeAliasesSuperType(this.properties.getTypeAliasesSuperType());
		}
		if (StringUtils.hasLength(this.properties.getTypeHandlersPackage())) {
			factory.setTypeHandlersPackage(this.properties.getTypeHandlersPackage());
		}
		
		Resource[] mapperLocations = this.properties.resolveMapperLocations();
		if (!ObjectUtils.isEmpty(mapperLocations)) {
			factory.setMapperLocations(mapperLocations);
			scannerConfigurer.setMapperLocations(mapperLocations);
		}
		
		return factory.getObject();
	}

	private void applyConfiguration(SqlSessionFactoryBean factory) {
		Configuration configuration = this.properties.getConfiguration();
		if (configuration == null && !StringUtils.hasText(this.properties.getConfigLocation())) {
			configuration = new Configuration();
		}
		if (configuration != null && !CollectionUtils.isEmpty(this.configurationCustomizers)) {
			for (ConfigurationCustomizer customizer : this.configurationCustomizers) {
				customizer.customize(configuration);
			}
		}
		factory.setConfiguration(configuration);
	}

	@Bean
	@ConditionalOnMissingBean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		ExecutorType executorType = this.properties.getExecutorType();
		if (executorType != null) {
			return new SqlSessionTemplate(sqlSessionFactory, executorType);
		} else {
			return new SqlSessionTemplate(sqlSessionFactory);
		}
	}

	/**
	 * This will just scan the same base package as Spring Boot does. If you want
	 * more power, you can explicitly use
	 * {@link org.mybatis.spring.annotation.MapperScan} but this will get typed
	 * mappers working correctly, out-of-the-box, similar to using Spring Data JPA
	 * repositories.
	 */
	public static class AutoConfiguredMapperScannerRegistrar implements  ImportBeanDefinitionRegistrar {


		@Override
		public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
			logger.debug("Searching for mappers");
//			BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(MapperScannerConfigurer.class);
			BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(JadMapperScannerConfigurer.class);// 20200329
			builder.addPropertyValue("processPropertyPlaceHolders", true);
//			builder.addPropertyValue("annotationClass", Mapper.class);
//			builder.addPropertyValue("annotationClass", GneteDao.class);
			List<Class<? extends Annotation>> annotationClasses =new ArrayList<Class<? extends Annotation>>();
			annotationClasses.add(Mapper.class);
			annotationClasses.add(JadDao.class);
			annotationClasses.add(Repository.class);
			builder.addPropertyValue("annotationClasses", annotationClasses);
//			TODO 设基本包	,暂时不要
//			builder.addPropertyValue("basePackage", StringUtils.collectionToCommaDelimitedString(packages));
			
			BeanWrapper beanWrapper = new BeanWrapperImpl(JadMapperScannerConfigurer.class);
			Stream.of(beanWrapper.getPropertyDescriptors())
					// Need to mybatis-spring 2.0.2+
					.filter(x -> x.getName().equals("lazyInitialization")).findAny()
					.ifPresent(x -> builder.addPropertyValue("lazyInitialization", "${mybatis.lazy-initialization:false}"));
			registry.registerBeanDefinition(JadMapperScannerConfigurer.class.getName(), builder.getBeanDefinition());
		}


	}

	/**
	 * If mapper registering configuration or mapper scanning configuration not
	 * present, this configuration allow to scan mappers based on the same
	 * component-scanning path as Spring Boot itself.
	 */
	@org.springframework.context.annotation.Configuration
	@Import(AutoConfiguredMapperScannerRegistrar.class)
	@ConditionalOnMissingBean({ MapperFactoryBean.class,JadMapperScannerConfigurer.class })
	public static class MapperScannerRegistrarNotFoundConfiguration implements InitializingBean {

		@Override
		public void afterPropertiesSet() {
//			logger.debug("Not found configuration for registering mapper bean using @MapperScan, MapperFactoryBean and MapperScannerConfigurer.");
		}

	}


}
