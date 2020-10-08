package com.openjad.orm.mybatis.mapper;

import javax.persistence.Entity;

import org.w3c.dom.Node;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

import com.openjad.common.util.reflection.ReflectUtils;
import com.openjad.logger.api.Logger;
import com.openjad.logger.api.LoggerFactory;
import com.openjad.orm.mybatis.constant.MybatisLogCode;
import com.openjad.orm.mybatis.mapper.sp.MapperSP;

/**
 * Mapper文件操作工具
 * 
 *  @author hechuan
 *
 */
public class JadXMLMapperUtils {

	private static final Logger logger = LoggerFactory.getLogger(JadXMLMapperUtils.class);
	
	/**
	 * 将 mapper node 转换成  xml字符片段
	 * @param node node
	 * @param format 是否格试化
	 * @return xml字符片段
	 */
	public static String toString(Node node,boolean format){
		DOMImplementationLS lsImpl = (DOMImplementationLS) node.getOwnerDocument().getImplementation().getFeature("LS", "3.0");
		LSSerializer serializer = lsImpl.createLSSerializer();
		serializer.getDomConfig().setParameter("xml-declaration", false); //by default its true, so set it to false to get String without xml-declaration
		serializer.getDomConfig().setParameter("format-pretty-print", format);
		String str = serializer.writeToString(node);
		return str;
	}
	
	/**
	 * 通过 mapper信息获取 实体类
	 * @param mapperSP mapper配置
	 * @return 实体类
	 */
	public static Class findEntityClass(MapperSP mapperSP) {
		if(mapperSP==null){
			return null;
		}
		String namespace = mapperSP.getNamespace();
		if (namespace == null || namespace.length() == 0) {
			return null;
		}
		Class daoClass = null;
		try {
			daoClass = Class.forName(namespace);
		} catch (Exception e) {
			logger.error(MybatisLogCode.CODE_00002,"无法识别的namespace:" + namespace + "," + e.getMessage(), e);
			return null;
		}

		Class entityClass = ReflectUtils.getSuperClassGenricType(daoClass);
		if (entityClass == Class.class) {
			return null;
		}
		if (entityClass.getAnnotation(Entity.class) != null) {
			logger.warn(MybatisLogCode.CODE_00006,"类:" + entityClass.getName() + "没用使用@Entity标注,忽略");
			return null;
		}
		return entityClass;
	}
	
	
	
	


}
