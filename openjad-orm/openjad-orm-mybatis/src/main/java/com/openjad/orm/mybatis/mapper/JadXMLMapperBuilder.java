package com.openjad.orm.mybatis.mapper;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.session.Configuration;
import org.w3c.dom.Node;

import com.openjad.orm.mybatis.constant.MybatisLogCode;
import com.openjad.orm.mybatis.mapper.sp.MapperItemSP;
import com.openjad.orm.mybatis.mapper.sp.MapperSP;
import com.openjad.logger.api.Logger;
import com.openjad.logger.api.LoggerFactory;
import com.openjad.orm.exception.JadEntityParseException;

/**
 * Mapper文件生成器
 * 
 *  @author hechuan
 *
 */
public class JadXMLMapperBuilder extends XMLMapperBuilder {

	private static final Logger logger = LoggerFactory.getLogger(JadXMLMapperBuilder.class);

	public JadXMLMapperBuilder(InputStream inputStream, Configuration configuration, String resource, Map<String, XNode> sqlFragments) {
		super(inputStream, configuration, resource, sqlFragments);
	}

	/**
	 * 解析 当前mapper配置
	 */
	public void parse() {
		super.parse();//默认的解析方式
		String resource = null;
		try {
			resource = getResource();
			jadParse(resource);//再解析一把,捡漏
		} catch (Exception e) {
			throw new JadEntityParseException(MybatisLogCode.CODE_00015,"解析配置失败,resource: '" + resource + "'. 原因: " + e.getMessage(), e);
		}
	}
	
	/**
	 * 解析 mapper资源
	 * @param resource mapper资源
	 * @throws Exception 解析失败
	 */
	public void jadParse(String resource) throws Exception {
		if (!configuration.isResourceLoaded(resource)) {
			logger.warn(MybatisLogCode.CODE_00015,"resource:" + resource + "没有被mybatis解析,忽略");
			return;
		}

		jadConfigurationElement(getParser().evalNode("/mapper"));
		invokeSuperPrivateMethod("parsePendingResultMaps", new Class[] {}, new Object[] {});
		invokeSuperPrivateMethod("parsePendingCacheRefs", new Class[] {}, new Object[] {});
		invokeSuperPrivateMethod("parsePendingStatements", new Class[] {}, new Object[] {});
	}
	
	/**
	 * 缓存被解析的 mapper资源列表，防止重复解析
	 */
	private static Map<String, MapperSP> mapperMap = new HashMap<String, MapperSP>();

	/**
	 * 获过 namespace 获取 被解析的 mapper信息
	 * @param namespace namespace
	 * @return 被解析的 mapper信息
	 */
	public static synchronized MapperSP getMapperSP(String namespace) {

		if (mapperMap.containsKey(namespace)) {
			return mapperMap.get(namespace);
		}
		MapperSP sp = new MapperSP(namespace);
		sp.init();
		mapperMap.putIfAbsent(namespace, sp);

		return mapperMap.get(namespace);
	}

	/**
	 * 从 xml节点中解析出相关配置属性
	 * @param context  xml节点
	 * @throws Exception 解析失败
	 */
	private void jadConfigurationElement(XNode context) throws Exception {
		try {
			String namespace = context.getStringAttribute("namespace");
			if (namespace == null || namespace.equals("")) {
				throw new JadEntityParseException("namespace不能为空");
			}
			getBuilderAssistant().setCurrentNamespace(namespace);

			MapperSP sp = getMapperSP(namespace);

			for (Map.Entry<String, String> ent : MapperSP.tagPrivateMethod.entrySet()) {
				List<XNode> nodeList = context.evalNodes("/mapper/" + ent.getKey());
				for (MapperItemSP itemSP : sp.getItemMap().get(ent.getKey())) {
					if (!hasNode(nodeList, itemSP.getId())) {
						List<XNode> newList = new ArrayList<XNode>();
						newList.add(toXNode(itemSP.toNode()));
						invokeSuperPrivateMethod(ent.getValue(), new Class[] { List.class }, new Object[] { newList });
					}
				}
			}
		} catch (Exception e) {
			throw new JadEntityParseException(MybatisLogCode.CODE_00015,
					"跟据resource解析配置失败,resource:" + getResource() + "'，原因: " + e.getMessage(), e);
		}
	}
	
	/**
	 * 将 node转换为 xml node
	 * @param node node
	 * @return xml node
	 * @throws Exception 转换失败
	 */
	private XNode toXNode(Node node) throws Exception {
		XPathParser parser = getParser();
		XNode xnode = new XNode(parser, node, getParserVariables(parser));
		return xnode;
	}
	
	/**
	 * 从 xml node列表判断是否存在指定 id的node
	 * @param list xml node列表
	 * @param id 节点id
	 * @return 是否存在
	 */
	private boolean hasNode(List<XNode> list, String id) {
		for (XNode node : list) {
			if (id.equals(node.getStringAttribute("id"))) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 获取 解析器
	 * @return 解析器
	 * @throws Exception 获取失败
	 */
	private XPathParser getParser() throws Exception {
		return getSuperPrivateField("parser");
	}

	/**
	 * 获取资源
	 * @return 资源
	 * @throws Exception 获取失败
	 */
	private String getResource() throws Exception {
		return getSuperPrivateField("resource");
	}

	/**
	 * 获取 MapperBuilder
	 * @return MapperBuilder
	 * @throws Exception 获取失败
	 */
	private MapperBuilderAssistant getBuilderAssistant() throws Exception {
		return getSuperPrivateField("builderAssistant");
	}

	/**
	 * 获取 variables 属性值
	 * @param parser parser
	 * @return variables 属性值
	 * @throws Exception 获取失败
	 */
	private Properties getParserVariables(XPathParser parser) throws Exception {
		Field field = XPathParser.class.getDeclaredField("variables");
		field.setAccessible(true);
		return (Properties) field.get(parser);
	}
	
	/**
	 * 调用父类私有方法
	 * @param methodName 方法名称
	 * @param parameterTypes 参数类型列表
	 * @param parameterValues 参数值列表
	 * @return 调用结果
	 * @throws Exception 调用失败
	 */
	private <T> T invokeSuperPrivateMethod(String methodName, Class<?>[] parameterTypes, Object[] parameterValues) throws Exception {
		Method method = XMLMapperBuilder.class.getDeclaredMethod(methodName, parameterTypes);
		method.setAccessible(true);
		return (T) method.invoke(this, parameterValues);
	}

	/**
	 * 获取父类私有属性值
	 * @param name 属性名
	 * @return 属性值 
	 * @throws Exception 获取失败
	 */
	private <T> T getSuperPrivateField(String name) throws Exception {
		Field field = XMLMapperBuilder.class.getDeclaredField(name);
		field.setAccessible(true);
		return (T) field.get(this);
	}

}
