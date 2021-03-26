package com.openjad.orm.mybatis.mapper.sp;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.openjad.orm.enums.IdType;
import com.openjad.orm.mybatis.entity.EoFieldInfo;

/*
 * <sql id="updateSelective" > <set >
 * <if test="record.requestId != null" >request_id =
 * #{record.requestId,jdbcType=VARCHAR},</if>
 * <if test="record.side != null" >side = #{record.side,jdbcType=VARCHAR},</if>
 * <if test="record.heapStack != null" >heap_stack =
 * #{record.heapStack,jdbcType=VARCHAR},</if>
 * <if test="record.errorMessage != null" >error_message =
 * #{record.errorMessage,jdbcType=VARCHAR},</if>
 * <if test="record.errorCode != null" >error_code =
 * #{record.errorCode,jdbcType=VARCHAR}</if> </set> </sql>
 */
public class UpdateSelectiveSP extends MapperItemSP {

	public UpdateSelectiveSP(MapperSP mapperSP) {
		super(mapperSP, "sql", "updateSelective");
	}

	@Override
	public Node toNode() {

		Document doc = mapperSP.getDoc();
		Element node = doc.createElement(tag);
		node.setAttribute("id", id);

		boolean containKey = false;

		IdType idType = mapperSP.getEoMetaInfo().getIdType();

		EoFieldInfo keyField = mapperSP.getEoMetaInfo().getKeyFieldInfo();
		if (keyField != null && !IdType.AUTO.equals(idType) && !IdType.IDENTITY.equals(idType)) {
			containKey = true;
		}
		Element setNode = doc.createElement("set");
		for (EoFieldInfo fieldInfo : mapperSP.getEoMetaInfo().getFieldInfoMap().values()) {
			if (!isCommonColumn(fieldInfo)) {
				continue;
			}
			if (!fieldInfo.isUpdatable()) {
				continue;
			}
			if (fieldInfo.isId()) {
				continue;
			}
			String column = fieldInfo.getColumn();
			if (keyField != null && !containKey && keyField.getColumn().equals(column)) {
				continue;
			}

			if (fieldInfo.isForceUpdateNull()) {
				StringBuffer sb = new StringBuffer();
				sb.append(column).append(" = #{record.");
				sb.append(fieldInfo.getFieldName()).append(",jdbcType=");
				sb.append(fieldInfo.getJdbcType()).append("},");
				setNode.appendChild(doc.createTextNode(sb.toString()));
			} else {
				Element ife = doc.createElement("if");
				StringBuffer sb = new StringBuffer();
				sb.append("record.").append(fieldInfo.getFieldName()).append("!= null");
				ife.setAttribute("test", sb.toString());
				sb = new StringBuffer();
				sb.append(fieldInfo.getColumn()).append(" = #{record.").append(fieldInfo.getFieldName());
				sb.append(",jdbcType=").append(fieldInfo.getJdbcType()).append("},");
				ife.appendChild(doc.createTextNode(sb.toString()));
				setNode.appendChild(ife);
			}


		}

		node.appendChild(setNode);
		return node;

	}

}
