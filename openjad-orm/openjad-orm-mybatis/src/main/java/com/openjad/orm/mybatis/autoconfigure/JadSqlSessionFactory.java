package com.openjad.orm.mybatis.autoconfigure;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.Resource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

import com.openjad.orm.mybatis.constant.MybatisLogCode;
import com.openjad.orm.mybatis.mapper.JadXMLMapperBuilder;
import com.openjad.orm.mybatis.mapper.sp.MapperItemSP;
import com.openjad.orm.mybatis.mapper.sp.MapperSP;
import com.openjad.common.util.SystemUtil;
import com.openjad.logger.api.Logger;
import com.openjad.logger.api.LoggerFactory;
import com.openjad.orm.exception.JadEntityParseException;

public class JadSqlSessionFactory implements ApplicationListener<ContextRefreshedEvent> {

	private SqlSessionFactory sqlSessionFactory;

	private MybatisProperties properties;

	public static Set<String> NOT_LOAD_CLASS_NAME_SET = new HashSet<String>();

	private volatile static Boolean started = false;

	private static final Logger logger = LoggerFactory.getLogger(JadSqlSessionFactory.class);

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		synchronized (started) {
			if (started.booleanValue()) {
				return;
			} else {
				started = true;
			}
		}
		reloadExists();//重新加载存在的
		
		Set<String> set = NOT_LOAD_CLASS_NAME_SET;

		for (String resource : set) {
			try {
				logger.debug(MybatisLogCode.CODE_00010,"没有加载资源:" + resource + ",自动加载");

//				Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
				
				MapperSP sp = JadXMLMapperBuilder.getMapperSP(resource);
				
				Document doc = sp.getDoc();
				Element node = doc.createElement("mapper");

				node.setAttribute("namespace", resource);
				
				List<MapperItemSP> itemList = sp.getAllItemList();
				for(MapperItemSP item:itemList){
					node.appendChild(item.toNode());
				}
				
				DOMImplementationLS lsImpl = (DOMImplementationLS) node.getOwnerDocument().getImplementation().getFeature("LS", "3.0");
				LSSerializer serializer = lsImpl.createLSSerializer();
				serializer.getDomConfig().setParameter("xml-declaration", false); //by default its true, so set it to false to get String without xml-declaration
				String str = serializer.writeToString(node);

				str = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
						"<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >" +
						str;

				XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(
						new ByteArrayInputStream(str.getBytes()), sqlSessionFactory.getConfiguration(),
						resource, sqlSessionFactory.getConfiguration().getSqlFragments());
				xmlMapperBuilder.parse();
			} catch (Exception e) {
				logger.error(MybatisLogCode.CODE_00015,"自动加载资源["+resource+"]失败,"+e.getMessage(),e);
				SystemUtil.exit();
			}
		}

	}

	private void reloadExists() {
		for (Resource mapperLocation : getMapperLocations()) {
			try {
				JadXMLMapperBuilder xmlMapperBuilder = new JadXMLMapperBuilder(mapperLocation.getInputStream(),
						sqlSessionFactory.getConfiguration(), mapperLocation.toString(),
						sqlSessionFactory.getConfiguration().getSqlFragments());
				xmlMapperBuilder.parse();
				String namespace = this.getParser(xmlMapperBuilder).evalNode("/mapper").getStringAttribute("namespace");
				if (NOT_LOAD_CLASS_NAME_SET.contains(namespace)) {
					NOT_LOAD_CLASS_NAME_SET.remove(namespace);
				}

			} catch (Exception e) {
				throw new JadEntityParseException(MybatisLogCode.CODE_00015,"加载资源失败: '" + mapperLocation + "'", e);
			} finally {
				ErrorContext.instance().reset();
			}
		}

	}

	private Resource[] getMapperLocations()  {
		return this.properties.resolveMapperLocations();
	}

	private XPathParser getParser(XMLMapperBuilder xmlMapperBuilder) throws Exception {
		Field field = XMLMapperBuilder.class.getDeclaredField("parser");
		field.setAccessible(true);
		return (XPathParser) field.get(xmlMapperBuilder);
	}

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	public void setProperties(MybatisProperties properties) {
		this.properties = properties;
	}

}
