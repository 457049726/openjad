package com.openjad.orm.mybatis.mapper.sp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.openjad.common.util.reflection.ReflectUtils;
import com.openjad.orm.exception.JadEntityParseException;
import com.openjad.orm.mybatis.constant.MybatisLogCode;
import com.openjad.orm.mybatis.entity.EoMetaInfo;
import com.openjad.orm.mybatis.mapper.JadXMLMapperUtils;
import com.openjad.orm.mybatis.utils.EntityUtils;

/**
 * mapper信息
 * 
 * 用于描述一个 mapper文件
 * 
 *  @author hechuan
 *
 */
public class MapperSP {

	/**
	 * namespace
	 * 通常与此 mapper对应的实体类全路径保持一致
	 */
	private String namespace;
	
	/**
	 * 此mapper文件对应的文档对象
	 */
	private Document doc;

	/**
	 * 所有节点列表
	 */
	private transient List<MapperItemSP> allItemList;
	
	/**
	 * 所有节点列表的 map表示
	 */
	private transient HashMap<String, List<MapperItemSP>> itemMap;

	/**
	 * 此mapper对应的实体元数据
	 */
	private transient EoMetaInfo eoMetaInfo;
	
	/**
	 * 此mapper对应的实体类
	 */
	@SuppressWarnings("rawtypes")
	private transient Class eoType;

	
	/**
	 * 所有tag类型对应的父类私有方法名称 
	 */
	public transient static Map<String,String>tagPrivateMethod=new HashMap<String,String>();
	static{
		tagPrivateMethod.put("sql", "sqlElement");
		tagPrivateMethod.put("resultMap", "resultMapElements");
		tagPrivateMethod.put("select", "buildStatementFromContext");
		tagPrivateMethod.put("delete", "buildStatementFromContext");
		tagPrivateMethod.put("insert", "buildStatementFromContext");
		tagPrivateMethod.put("update", "buildStatementFromContext");
	}
	
	
	/**
	 * 构造函数
	 * @param namespace namespace
	 */
	public MapperSP(String namespace) {
		this.namespace = namespace;
		this.itemMap = new HashMap<String, List<MapperItemSP>>();
		this.allItemList = new ArrayList<MapperItemSP>();
		
		try {
			this.doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		} catch (ParserConfigurationException e) {
			throw new RuntimeException("无法创建Document,"+e.getMessage(),e);
		}
	}
	
	/**
	 * 初始化
	 */
	@SuppressWarnings("rawtypes")
	public void init() {
		try {
			Class daoType = Class.forName(namespace);
			eoType = ReflectUtils.getSuperInterfaceGenricType(daoType);
			if (eoType == null || eoType == Object.class) {
				throw new JadEntityParseException(MybatisLogCode.CODE_00011,
						"无法通过dao类" + namespace + "解析出对应的实体类型");
			}
		} catch (ClassNotFoundException e) {
			throw new JadEntityParseException(MybatisLogCode.CODE_00012,
					"无法加载dao类:" + namespace + "," + e.getMessage(), e);
		}

		try {
			eoMetaInfo = EntityUtils.getEoInfo(eoType);
		} catch (Exception e) {
			throw new JadEntityParseException(MybatisLogCode.CODE_00013,
					"无法分析实体类" + eoType.getName() + "的元属性," + e.getMessage(), e);
		}
		
		putItem();
	}
	
	/**
	 * 初始化所有默认节点
	 */
	private void putItem(){
		boolean hasKey = false;
		if (eoMetaInfo.getKeyFieldInfo() != null) {
			hasKey = true;
		}
		putItem(new BaseResultMapSP(this));
		putItem(new JadBaseColumnListSP(this));
		putItem(new XrWhereClauseSP(this));
		putItem(new UpdateByXrWhereClauseSP(this));
		putItem(new ListByPagedCriteriaSP(this));
		putItem(new ListBySelectCriteriaSP(this));
		if (hasKey) {
			putItem(new DeleteByIdSP(this));
		}
		putItem(new DeleteByCriteriaSP(this));
		putItem(new InsertAllColumnSP(this));
		putItem(new InsertSelectiveSP(this));
		putItem(new UpdateByCriteriaSelectiveSP(this));
		putItem(new UpdateSelectiveSP(this));
		if (hasKey) {
			putItem(new UpdateByIdSelectiveSP(this));
		}

		putItem(new UpdateAllColumnSP(this));
		if (hasKey) {
			putItem(new UpdateByIdAllColumnSP(this));
		}
		putItem(new UpdateByCriteriaAllColumnSP(this));
		if (hasKey) {
			putItem(new GetByIdSP(this));
		}
	}
	
	/**
	 * 初始化某个 节点
	 * @param item mapper节点
	 */
	private void putItem(MapperItemSP item) {
		if (itemMap.get(item.getTag()) == null) {
			itemMap.put(item.getTag(), new LinkedList<MapperItemSP>());
		}
		itemMap.get(item.getTag()).add(item);
		allItemList.add(item);
	}

	public Node toNode() {
		Element root = doc.createElement("mapper");
		root.setAttribute("namespace", namespace);

		if (allItemList != null) {
			for (MapperItemSP item : allItemList) {
				root.appendChild(item.toNode());
			}
		}

		return root;
	}

	/**
	 * 将此 mapper转换为 xml字符
	 * @return xml字符
	 */
	public String toXml() {

		String str = JadXMLMapperUtils.toString(toNode(), true);
		str = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
				"<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >\n" +
				str;
		return str;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public EoMetaInfo getEoMetaInfo() {
		return eoMetaInfo;
	}

	@SuppressWarnings("rawtypes")
	public Class getEoType() {
		return eoType;
	}

	public void setEoMetaInfo(EoMetaInfo eoMetaInfo) {
		this.eoMetaInfo = eoMetaInfo;
	}

	public List<MapperItemSP> getAllItemList() {
		return allItemList;
	}

	public HashMap<String, List<MapperItemSP>> getItemMap() {
		return itemMap;
	}

	public Document getDoc() {
		return doc;
	}

}
