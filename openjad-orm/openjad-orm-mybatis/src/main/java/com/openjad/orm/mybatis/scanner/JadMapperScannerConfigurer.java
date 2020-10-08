package com.openjad.orm.mybatis.scanner;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

import com.openjad.common.util.ContextUtil;
import com.openjad.orm.mybatis.constant.MybatisLogCode;
import com.openjad.orm.mybatis.mapper.JadXMLMapperBuilder;
import com.openjad.orm.mybatis.mapper.sp.MapperItemSP;
import com.openjad.orm.mybatis.mapper.sp.MapperSP;
import com.openjad.logger.api.Logger;
import com.openjad.logger.api.LoggerFactory;
import com.openjad.orm.annotation.JadDao;
import com.openjad.orm.exception.JadEntityParseException;


/**
 * mapper扫描器配置类
 * 
 *  @author hechuan
 *
 */
public class JadMapperScannerConfigurer extends MapperScannerConfigurer implements ApplicationListener<ContextRefreshedEvent> {

	private static final Logger logger = LoggerFactory.getLogger(JadMapperScannerConfigurer.class);
	
	/**
	 * 启动标志位
	 */
	private volatile static Boolean started = false;

	/**
	 * mapper资源列表
	 */
	private static Resource[] MAPPER_LOCATIONS;

	
	/**
	 * 覆盖父类方法
	 */
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) {
		try {
			if ((Boolean) getSuperPrivateField("processPropertyPlaceHolders")) {
				invokeSuperPrivateMethod("processPropertyPlaceHolders", new Class[] {}, new Object[] {});
			}
			JadClassPathMapperScanner scanner = new JadClassPathMapperScanner(registry);
			scanner.setAddToConfig(getSuperPrivateField("addToConfig"));
			scanner.setAnnotationClass(getSuperPrivateField("annotationClass"));
			scanner.setMarkerInterface(getSuperPrivateField("markerInterface"));
			scanner.setSqlSessionFactory(getSuperPrivateField("sqlSessionFactory"));
			scanner.setSqlSessionTemplate(getSuperPrivateField("sqlSessionTemplate"));
			scanner.setSqlSessionFactoryBeanName(getSuperPrivateField("sqlSessionFactoryBeanName"));
			scanner.setSqlSessionTemplateBeanName(getSuperPrivateField("sqlSessionTemplateBeanName"));
			scanner.setResourceLoader(getSuperPrivateField("applicationContext"));
			scanner.setBeanNameGenerator(getSuperPrivateField("nameGenerator"));
			scanner.registerFilters();
			scanner.scan(StringUtils.tokenizeToStringArray(getSuperPrivateField("basePackage"), ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS));
		} catch (Exception e) {
			logger.error(MybatisLogCode.CODE_00003,"自定义dao扫描失败，自动切换为mybatis默认的扫描器," + e.getMessage(), e);
			super.postProcessBeanDefinitionRegistry(registry);
		}
	}

	
	/**
	 * 监听application加载事件
	 */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(ContextUtil.isBootstrapContext(event)){
			return;
		}
		synchronized (started) {
			if (started.booleanValue()) {
				return;
			} else {
				started = true;
			}

			// 获取 sessionFactory
			SqlSessionFactory sqlSessionFactory = event.getApplicationContext().getBean(SqlSessionFactory.class);

			// 重新加载已存在mapper
			reloadExists(sqlSessionFactory);

			for (String daoClassName : JadClassPathMapperScanner.getAllDaoClassName()) {
				if (needAutoLoad(daoClassName, sqlSessionFactory)) {
					autoloadNotExist(sqlSessionFactory, daoClassName);// 自动加载不存在的mapper
				}

			}
		}

	}
	
	/**
	 * 自动加载没存在的 mapper配置
	 * 如果只写了dao类，但是没有写对应的mapper配置文件，此方法会自动生成一个
	 * 
	 * @param sqlSessionFactory sqlSessionFactory
	 * @param daoClassName dao类名
	 */
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
			throw new JadEntityParseException(
					MybatisLogCode.CODE_00004,"自动加载dao[" + daoClassName + "]失败," + e.getMessage(),e);
		}

	}

	/**
	 * 重新加载已存在mapper配置
	 * 主要用于自动初始化已存在的mapper文件中的缺省配置
	 * @param sqlSessionFactory sqlSessionFactory
	 */
	private void reloadExists(SqlSessionFactory sqlSessionFactory) {

		if (MAPPER_LOCATIONS == null || MAPPER_LOCATIONS.length == 0) {
			return;
		}

		for (Resource mapperLocation : MAPPER_LOCATIONS) {
			if (mapperLocation == null) {
				continue;
			}

			try {
				JadXMLMapperBuilder xmlMapperBuilder = new JadXMLMapperBuilder(mapperLocation.getInputStream(),
						sqlSessionFactory.getConfiguration(), mapperLocation.toString(),
						sqlSessionFactory.getConfiguration().getSqlFragments());

				String namespace = getParser(xmlMapperBuilder).evalNode("/mapper").getStringAttribute("namespace");

				if (needAutoLoad(namespace)) {
					xmlMapperBuilder.parse();// 用自己的 builder 重新解析一番
				}

			} catch (Exception e) {
				logger.warn(MybatisLogCode.CODE_00004,
						"自定义加载mapper文件" + mapperLocation.toString() + "失败," + e.getMessage(), e);
			}

		}

	}

	/**
	 * 判断某个 dao类是否需要重新加载对应的 mapper
	 * @param daoClassName dao类 
	 * @param sqlSessionFactory sqlSessionFactory
	 * @return 是否需要重新加载对应的 mapper
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean needAutoLoad(String daoClassName, SqlSessionFactory sqlSessionFactory) {

		try {
			Class daoClass = Class.forName(daoClassName);
			JadDao jadDao = (JadDao) daoClass.getAnnotation(JadDao.class);

			if (jadDao != null && jadDao.autoLoad()) {

				if (sqlSessionFactory == null) {
					return true;
				} else {
					return !sqlSessionFactory.getConfiguration().hasMapper(daoClass);
				}
			} else {
				return false;
			}

		} catch (ClassNotFoundException e) {
			logger.warn(MybatisLogCode.CODE_00002,
					"自动重新加载出错,无法识别的类:" + daoClassName + ",可能是某个mapper文件中的namespace有误,"+e.getMessage(),e);
			
			return false;
		}

	}

	/**
	 * 指定 mapper资源列表
	 * @param mapperLocations  mapper资源列表
	 */
	public static void setMapperLocations(Resource[] mapperLocations) {
		JadMapperScannerConfigurer.MAPPER_LOCATIONS = mapperLocations;
	}

	/**
	 * 获取 mapper资源列表
	 * @return mapper资源列表
	 */
	public static Resource[] getMapperLocations() {
		return MAPPER_LOCATIONS;
	}
	
	/**
	 * 是否重新加载 mapper配置
	 * @param daoClassName dao类
	 * @return 是否重新加载 mapper配置
	 */
	private boolean needAutoLoad(String daoClassName) {
		return needAutoLoad(daoClassName, null);
	}

	/**
	 * 生成 xmlParser
	 * @param xmlMapperBuilder xmlMapperBuilder
	 * @return xmlParser
	 * @throws Exception 获取 parser失败
	 */
	private XPathParser getParser(XMLMapperBuilder xmlMapperBuilder) throws Exception {
		Field field = XMLMapperBuilder.class.getDeclaredField("parser");
		field.setAccessible(true);
		return (XPathParser) field.get(xmlMapperBuilder);
	}

	/**
	 * 调用父类私有方法
	 * 
	 * @param methodName 方法名称
	 * @param parameterTypes 参数类型表
	 * @param parameterValues 参数类型值
	 * @return 调用结果
	 * @throws Exception 调用失败
	 */
	private <T> T invokeSuperPrivateMethod(String methodName, Class<?>[] parameterTypes, Object[] parameterValues) throws Exception {
		Method method = MapperScannerConfigurer.class.getDeclaredMethod(methodName, parameterTypes);
		method.setAccessible(true);
		return (T) method.invoke(this, parameterValues);
	}

	/**
	 * 获取父类私有属性
	 * @param name 属性名
	 * @return 私有属性值
	 * @throws Exception 获取失败
	 */
	private <T> T getSuperPrivateField(String name) throws Exception {
		Field field = MapperScannerConfigurer.class.getDeclaredField(name);
		field.setAccessible(true);
		return (T) field.get(this);
	}

}
