package com.openjad.orm.mybatis.mapper.sp;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.openjad.orm.mybatis.entity.EoFieldInfo;
import com.openjad.orm.enums.IdType;

/*
 *  insertSelective 节点
 *  
 * <insert id="insertSelective" parameterType="com.jad.orm.mybatis.eo.RpcExceptionTraceDO"
 * useGeneratedKeys="true" keyProperty="id">
 * insert into m_rpc_exception
 * <trim prefix="(" suffix=")" suffixOverrides="," >
 * <if test="id != null" >id,</if>
 * <if test="requestId != null" >request_id,</if>
 * <if test="side != null" >side,</if>
 * <if test="heapStack != null" >heap_stack,</if>
 * <if test="errorMessage != null" >error_message,</if>
 * <if test="errorCode != null" >error_code</if>
 * </trim>
 * <trim prefix="values (" suffix=")" suffixOverrides="," >
 * <if test="id != null" >#{id,jdbcType=BIGINT},</if>
 * <if test="requestId != null" >#{requestId,jdbcType=VARCHAR},</if>
 * <if test="side != null" >#{side,jdbcType=VARCHAR},</if>
 * <if test="heapStack != null" >#{heapStack,jdbcType=VARCHAR},</if>
 * <if test="errorMessage != null" >#{errorMessage,jdbcType=VARCHAR},</if>
 * <if test="errorCode != null" >#{errorCode,jdbcType=VARCHAR}</if>
 * </trim>
 * </insert>
 *
 */
public class InsertSelectiveSP extends MapperItemSP {

	public InsertSelectiveSP(MapperSP mapperSP) {
		super(mapperSP, "insert", "insertSelective");
	}

	@Override
	public Node toNode() {
		Document doc = mapperSP.getDoc();
		Element node = doc.createElement(tag);
		node.setAttribute("id", id);
		node.setAttribute("parameterType", mapperSP.getEoType().getName());

		String tableName = mapperSP.getEoMetaInfo().getTableName();

		boolean containKey = false;

		IdType idType = mapperSP.getEoMetaInfo().getIdType();

		EoFieldInfo keyField = mapperSP.getEoMetaInfo().getKeyFieldInfo();
		if (keyField != null) {
			node.setAttribute("useGeneratedKeys", "true");
			node.setAttribute("keyProperty", keyField.getFieldName());
			if (!IdType.AUTO.equals(idType) && !IdType.IDENTITY.equals(idType)) {
				containKey = true;
			}
		}

		StringBuffer sb = new StringBuffer();
		sb.append("\n");
		sb.append("insert into ");
		sb.append(tableName).append("\n");

		node.appendChild(doc.createTextNode(sb.toString()));

		Element trim1 = doc.createElement("trim");
		trim1.setAttribute("prefix", "(");
		trim1.setAttribute("suffix", ")");
		trim1.setAttribute("suffixOverrides", ",");

		for (EoFieldInfo fieldInfo : mapperSP.getEoMetaInfo().getFieldInfoMap().values()) {
			if(!isCommonColumn(fieldInfo)){
				continue;
			}
			if(!fieldInfo.isInsertable()){
				continue;
			}
			String column = fieldInfo.getColumn();
			String fieldName = fieldInfo.getFieldName();
			if (keyField != null && !containKey && keyField.getColumn().equals(column)) {
				continue;
			}
			Element ife = doc.createElement("if");
			ife.setAttribute("test", fieldName + "!=null");
			ife.appendChild(doc.createTextNode(column + ","));
			trim1.appendChild(ife);
		}
		

		Element trim2 = doc.createElement("trim");
		trim2.setAttribute("prefix", "values (");
		trim2.setAttribute("suffix", ")");
		trim2.setAttribute("suffixOverrides", ",");

		for (EoFieldInfo fieldInfo : mapperSP.getEoMetaInfo().getFieldInfoMap().values()) {
			if(!isCommonColumn(fieldInfo)){
				continue;
			}
			if(!fieldInfo.isInsertable()){
				continue;
			}
			String column = fieldInfo.getColumn();
			String fieldName = fieldInfo.getFieldName();
			if (keyField != null && !containKey && keyField.getColumn().equals(column)) {
				continue;
			}
			Element ife = doc.createElement("if");
			ife.setAttribute("test", fieldName + "!=null");
			sb = new StringBuffer();
			sb.append("#{").append(fieldName);
			sb.append(",jdbcType=").append(fieldInfo.getJdbcType());
			sb.append("},");
			ife.appendChild(doc.createTextNode(sb.toString()));
			trim2.appendChild(ife);
		}
		node.appendChild(trim1);
		node.appendChild(trim2);

		return node;
	}

}
