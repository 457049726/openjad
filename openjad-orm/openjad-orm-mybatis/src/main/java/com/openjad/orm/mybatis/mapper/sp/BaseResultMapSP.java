package com.openjad.orm.mybatis.mapper.sp;

import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.openjad.orm.mybatis.entity.EoFieldInfo;

/*
 * <resultMap id="BaseResultMap" type="com.jad.dao.mybatis.eo.RpcExceptionTraceDO" >
 * <id column="id" property="id" jdbcType="BIGINT" />
 * <result column="request_id" property="requestId" jdbcType="VARCHAR" />
 * <result column="side" property="side" jdbcType="VARCHAR" />
 * <result column="heap_stack" property="heapStack" jdbcType="VARCHAR" />
 * <result column="error_message" property="errorMessage" jdbcType="VARCHAR" />
 * <result column="error_code" property="errorCode" jdbcType="VARCHAR" />
 * </resultMap>
 *
 */
public class BaseResultMapSP extends MapperItemSP {

	private String type;

	public BaseResultMapSP(MapperSP mapperSP) {
		super(mapperSP, "resultMap", "BaseResultMap");
	}

	@Override
	public Node toNode() {

		Document doc = mapperSP.getDoc();

		Element node = doc.createElement(tag);
		node.setAttribute("id", id);
		node.setAttribute("type", mapperSP.getEoType().getName());

		for (Map.Entry<String, EoFieldInfo> ent : mapperSP.getEoMetaInfo().getFieldInfoMap().entrySet()) {
			EoFieldInfo fieldInfo = ent.getValue();
			if (!isCommonColumn(fieldInfo)  || !fieldInfo.isId()) {
				continue;
			}
			String column = fieldInfo.getColumn();
			String javaType = fieldInfo.getFieldType().getName();
			String property = fieldInfo.getFieldName();
			String jdbcType = fieldInfo.getJdbcType();
//			node.appendChild(getResultNode(doc, "id", column, javaType, jdbcType));
			node.appendChild(getResultNode(doc, "id", column, property, jdbcType));
		}
		
		for (Map.Entry<String, EoFieldInfo> ent : mapperSP.getEoMetaInfo().getFieldInfoMap().entrySet()) {
			EoFieldInfo fieldInfo = ent.getValue();
			if (!isCommonColumn(fieldInfo)  || fieldInfo.isId()) {
				continue;
			}
			
			String column = fieldInfo.getColumn();
			String javaType = fieldInfo.getJavaType();
			String jdbcType = fieldInfo.getJdbcType();
			String property = fieldInfo.getFieldName();
			
//			node.appendChild(getResultNode(doc, "result", column, javaType, jdbcType));
			node.appendChild(getResultNode(doc, "result", column, property, jdbcType));//修复一个 bug
			
		}

		return node;
	}

	private Node getResultNode(Document doc, String tag, String column, String property, String jdbcType) {
		Element node = doc.createElement(tag);
		node.setAttribute("column", column);
		node.setAttribute("property", property);
		node.setAttribute("jdbcType", jdbcType);
		return node;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
