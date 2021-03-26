package com.openjad.orm.mybatis.scanner;

import java.io.ByteArrayInputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.Resource;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

import com.openjad.common.util.ContextUtil;
import com.openjad.common.util.SysInitArgUtils;
import com.openjad.logger.api.Logger;
import com.openjad.logger.api.LoggerFactory;
import com.openjad.orm.annotation.JadDao;
import com.openjad.orm.mybatis.mapper.JadXMLMapperBuilder;
import com.openjad.orm.mybatis.mapper.sp.MapperItemSP;
import com.openjad.orm.mybatis.mapper.sp.MapperSP;
import com.openjad.orm.mybatis.multids.MultidsAdvisor;

/**
 * mapper扫描器配置类
 * 
 * @author hechuan
 *
 */
public class JadMapperScannerConfigurer extends MapperScannerConfigurer implements BeanFactoryAware, ApplicationListener<ContextRefreshedEvent> {

	private static final Logger logger = LoggerFactory.getLogger(JadMapperScannerConfigurer.class);

	private volatile static Boolean started = false;

	private String myBasePackage;

	private List<Class<? extends Annotation>> annotationClasses;

	private boolean annotationMultiDataSource;

	private JadClassPathMapperScanner scanner = null;

	private Resource[] mapperLocations;

	private BeanFactory beanFactory;

	@Override
	public void afterPropertiesSet() throws Exception {

		String basePackage = getSuperPrivateField("basePackage");//如果配置了 mybatis.basePackage 这个属性，这里将自动获取到
		if (basePackage == null || "".equals(basePackage.trim())) {
			ApplicationContext applicationContext = (ApplicationContext) getSuperPrivateField("applicationContext");
			ConfigurableEnvironment environment = (ConfigurableEnvironment) applicationContext.getEnvironment();
			basePackage = SysInitArgUtils.getPackagesFromEnviron(environment);//通过 openjad.application.basePackage 这个配置拿
		}

		if (basePackage == null || "".equals(basePackage.trim())) {
			basePackage = toStr(SysInitArgUtils.deduceScanPackage(beanFactory));
		}

		if (basePackage != null && !"".equals(basePackage.trim())) {
			this.setBasePackage(basePackage);
		}
		this.myBasePackage = basePackage;
		super.afterPropertiesSet();
	}

	
	private String toStr(Collection<String> strList) {
		if (strList == null || strList.isEmpty()) {
			return null;
		}
		StringBuffer sb = new StringBuffer();

		boolean first = true;
		for (String str : strList) {
			if (first) {
				first = false;
			} else {
				sb.append(",");
			}
			sb.append(str);
		}
		return sb.toString();
	}

	

	/**
	 * 覆盖父类方法
	 */
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) {
		try {
			if ((Boolean) getSuperPrivateField("processPropertyPlaceHolders")) {
				invokeSuperPrivateMethod("processPropertyPlaceHolders", new Class[] {}, new Object[] {});
			}
			this.scanner = new JadClassPathMapperScanner(registry);
			scanner.setAddToConfig(getSuperPrivateField("addToConfig"));
			scanner.setAnnotationClass(getSuperPrivateField("annotationClass"));
			scanner.setMarkerInterface(getSuperPrivateField("markerInterface"));
			scanner.setSqlSessionFactory(getSuperPrivateField("sqlSessionFactory"));
			scanner.setSqlSessionTemplate(getSuperPrivateField("sqlSessionTemplate"));
			scanner.setSqlSessionFactoryBeanName(getSuperPrivateField("sqlSessionFactoryBeanName"));
			scanner.setSqlSessionTemplateBeanName(getSuperPrivateField("sqlSessionTemplateBeanName"));
			scanner.setResourceLoader(getSuperPrivateField("applicationContext"));
			scanner.setBeanNameGenerator(getSuperPrivateField("nameGenerator"));
			scanner.setAnnotationClasses(annotationClasses);
			scanner.registerFilters();
			String basePackage = myBasePackage;
			if (basePackage == null || "".equals(basePackage.trim())) {
				basePackage = getSuperPrivateField("basePackage");
			}
			scanner.scan(StringUtils.tokenizeToStringArray(basePackage, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS));

		} catch (Exception e) {
			logger.error("自定义dao扫描失败，自动切换为mybatis默认的扫描器," + e.getMessage(), e);
			super.postProcessBeanDefinitionRegistry(registry);
		}

		try {
			if (annotationMultiDataSource) {
				registerMultidsInterceptor(registry);
			}
		} catch (Exception e) {
			logger.error("注入多数据源拦截器失败," + e.getMessage(), e);
		}
	}

	/**
	 * 注入多数据源拦截器
	 */
	private void registerMultidsInterceptor(BeanDefinitionRegistry registry) {
		String beanName = MultidsAdvisor.class.getName();
		if (registry.containsBeanDefinition(beanName)) {
			return;
		}
		RootBeanDefinition beanDefinition = new RootBeanDefinition(MultidsAdvisor.class);
		beanDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
		registry.registerBeanDefinition(beanName, beanDefinition);

	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (ContextUtil.isBootstrapContext(event)) {
			return;
		}
		synchronized (started) {
			if (started.booleanValue()) {
				return;
			} else {
				started = true;
			}

			if (scanner == null) {
				return;
			}
			// 获取 sessionFactory
			SqlSessionFactory sqlSessionFactory = event.getApplicationContext().getBean(SqlSessionFactory.class);

//			reloadExists(sqlSessionFactory);// 重新加载已存在mapper
//			for (String daoClassName : JadClassPathMapperScanner.getAllDaoClassName()) {
//				if (needAutoLoad(daoClassName, sqlSessionFactory)) {
//					autoloadNotExist(sqlSessionFactory, daoClassName);// 自动加载不存在的mapper
//				}
//			}
			reloadDao(sqlSessionFactory);

		}

	}

	private void autoloadNotExist(SqlSessionFactory sqlSessionFactory, String daoClassName) {

		try {

			logger.debug("自动解析dao:" + daoClassName);

			MapperSP sp = JadXMLMapperBuilder.getMapperSP(daoClassName);

			Document doc = sp.getDoc();
			Element node = doc.createElement("mapper");

			node.setAttribute("namespace", daoClassName);

			List<MapperItemSP> itemList = sp.getAllItemList();
			for (MapperItemSP item : itemList) {
				node.appendChild(item.toNode());
			}

			DOMImplementationLS lsImpl = (DOMImplementationLS) node.getOwnerDocument().getImplementation().getFeature("LS", "3.0");
			LSSerializer serializer = lsImpl.createLSSerializer();
			serializer.getDomConfig().setParameter("xml-declaration", false);
			String str = serializer.writeToString(node);

			str = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
					"<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >\n" +
					str;

			XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(
					new ByteArrayInputStream(str.getBytes()), sqlSessionFactory.getConfiguration(),
					daoClassName, sqlSessionFactory.getConfiguration().getSqlFragments());

			xmlMapperBuilder.parse();
		} catch (Exception e) {
			throw new RuntimeException("自动加载dao[" + daoClassName + "]失败," + e.getMessage(), e);
		}

	}

	private void reloadDao(SqlSessionFactory sqlSessionFactory) {

		Resource[] mapperLocations = this.mapperLocations;

		Set<String> alreadList = new HashSet<String>();
		//先加载已存在的
		if (mapperLocations != null && mapperLocations.length > 0) {
			for (Resource mapperLocation : mapperLocations) {
				try {
					JadXMLMapperBuilder xmlMapperBuilder = new JadXMLMapperBuilder(mapperLocation.getInputStream(),
							sqlSessionFactory.getConfiguration(), mapperLocation.toString(),
							sqlSessionFactory.getConfiguration().getSqlFragments());

					String namespace = getParser(xmlMapperBuilder).evalNode("/mapper").getStringAttribute("namespace");

					if (needAutoLoad(namespace)) {
						xmlMapperBuilder.parse();// 用自己的 builder 重新解析一番
					}
					alreadList.add(namespace);
				} catch (Exception e) {
					logger.warn("自定义加载mapper文件" + mapperLocation.toString() + "失败," + e.getMessage(), e);
				}
			}
		}

		//再加载已初始化的
		for (Class daoClass : sqlSessionFactory.getConfiguration().getMapperRegistry().getMappers()) {
			String namespace = daoClass.getName();
			if (alreadList.contains(namespace)) {
				continue;
			}
			alreadList.add(namespace);
			JadDao jadDao = (JadDao) daoClass.getAnnotation(JadDao.class);
			if (jadDao == null || !jadDao.autoLoad()) {
				continue;
			}
			autoloadNotExist(sqlSessionFactory, namespace);
		}

//		 再查看有没有漏网之鱼
		for (String daoClassName : scanner.getAllDaoClassName()) {
			if (alreadList.contains(daoClassName)) {
				continue;
			}
			if (needAutoLoad(daoClassName, sqlSessionFactory)) {
				autoloadNotExist(sqlSessionFactory, daoClassName);// 自动加载不存在的mapper
			}
			alreadList.add(daoClassName);
		}

	}

//	private void reloadExists_bak(SqlSessionFactory sqlSessionFactory) {
////		TODO	 这个方法有bug
//		if (MAPPER_LOCATIONS == null || MAPPER_LOCATIONS.length == 0) {
//			return;
//		}
//
//		for (Resource mapperLocation : MAPPER_LOCATIONS) {
//			if (mapperLocation == null) {
//				continue;
//			}
//
//			try {
//				JadXMLMapperBuilder xmlMapperBuilder = new JadXMLMapperBuilder(mapperLocation.getInputStream(),
//						sqlSessionFactory.getConfiguration(), mapperLocation.toString(),
//						sqlSessionFactory.getConfiguration().getSqlFragments());
//
//				String namespace = getParser(xmlMapperBuilder).evalNode("/mapper").getStringAttribute("namespace");
//
//				if (needAutoLoad(namespace)) {
//					xmlMapperBuilder.parse();// 用自己的 builder 重新解析一番
//				}
//
//			} catch (Exception e) {
//				logger.warn("自定义加载mapper文件" + mapperLocation.toString() + "失败," + e.getMessage(), e);
//			}
//
//		}
//
//	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean needAutoLoad(String daoClassName, SqlSessionFactory sqlSessionFactory) {

		try {
			Class daoClass = Class.forName(daoClassName);

			JadDao jadDao = (JadDao) daoClass.getAnnotation(JadDao.class);
			if (jadDao != null && jadDao.autoLoad()) {

				if (sqlSessionFactory == null) {
					return true;
				} else {
					boolean res = !sqlSessionFactory.getConfiguration().hasMapper(daoClass);
					return res;
				}
			} else {
				return false;
			}

		} catch (ClassNotFoundException e) {
			logger.warn("无法识别的类:" + daoClassName + ",可能是mapper文件中的namespace有误," + e.getMessage(), e);
			return false;
		}

	}

	private boolean needAutoLoad(String daoClassName) {
		return needAutoLoad(daoClassName, null);
	}

	private XPathParser getParser(XMLMapperBuilder xmlMapperBuilder) throws Exception {
		Field field = XMLMapperBuilder.class.getDeclaredField("parser");
		field.setAccessible(true);
		return (XPathParser) field.get(xmlMapperBuilder);
	}

	private <T> T invokeSuperPrivateMethod(String methodName, Class<?>[] parameterTypes, Object[] parameterValues) throws Exception {
		Method method = MapperScannerConfigurer.class.getDeclaredMethod(methodName, parameterTypes);
		method.setAccessible(true);
		return (T) method.invoke(this, parameterValues);
	}

	private <T> T getSuperPrivateField(String name) throws Exception {
		Field field = MapperScannerConfigurer.class.getDeclaredField(name);
		field.setAccessible(true);
		return (T) field.get(this);
	}

	public List<Class<? extends Annotation>> getAnnotationClasses() {
		return annotationClasses;
	}

	public void setAnnotationClasses(List<Class<? extends Annotation>> annotationClasses) {
		this.annotationClasses = annotationClasses;
	}

	public boolean isAnnotationMultiDataSource() {
		return annotationMultiDataSource;
	}

	public void setAnnotationMultiDataSource(boolean annotationMultiDataSource) {
		this.annotationMultiDataSource = annotationMultiDataSource;
	}

	public Resource[] getMapperLocations() {
		return mapperLocations;
	}

	public void setMapperLocations(Resource[] mapperLocations) {
		this.mapperLocations = mapperLocations;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		// TODO Auto-generated method stub
		this.beanFactory = beanFactory;

	}

}
