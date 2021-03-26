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
import com.openjad.orm.mybatis.entity.EoMetaInfo;
import com.openjad.orm.mybatis.mapper.JadXMLMapperUtils;
import com.openjad.orm.mybatis.utils.EntityUtils;

public class MapperSP {

	private String namespace;
	
	private Document doc;

	private transient List<MapperItemSP> allItemList;
	
	private transient HashMap<String, List<MapperItemSP>> itemMap;

	private transient EoMetaInfo eoMetaInfo;
	
	@SuppressWarnings("rawtypes")
	private transient Class eoType;

//	@SuppressWarnings("rawtypes")
//	private transient Class daoType;
	
	
	public transient static Map<String,String>tagPrivateMethod=new HashMap<String,String>();
	static{
		tagPrivateMethod.put("sql", "sqlElement");
		tagPrivateMethod.put("resultMap", "resultMapElements");
		tagPrivateMethod.put("select", "buildStatementFromContext");
		tagPrivateMethod.put("delete", "buildStatementFromContext");
		tagPrivateMethod.put("insert", "buildStatementFromContext");
		tagPrivateMethod.put("update", "buildStatementFromContext");
	}
	
	

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

	@SuppressWarnings("rawtypes")
	public void init() {
		try {
			Class daoType = Class.forName(namespace);
			eoType = ReflectUtils.getSuperInterfaceGenricType(daoType);
			if (eoType == null || eoType == Object.class) {
				throw new JadEntityParseException("无法通过dao类" + namespace + "解析出对应的实体类型");
			}
		} catch (ClassNotFoundException e) {
			throw new JadEntityParseException("无法加载dao类:" + namespace + "," + e.getMessage(), e);
		}

		try {
			eoMetaInfo = EntityUtils.getEoInfo(eoType);
		} catch (Exception e) {
			throw new JadEntityParseException("无法分析实体类" + eoType.getName() + "的元属性," + e.getMessage(), e);
		}
		
		putItem();
	}
	
	private void putItem(){
		boolean hasKey = false;
		if (eoMetaInfo.getKeyFieldInfo() != null) {
			hasKey = true;
		}
		putItem(new BaseResultMapSP(this));
		putItem(new BaseColumnListSP(this));
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
