package com.openjad.orm.mybatis.mapper.sp;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.openjad.orm.mybatis.entity.EoFieldInfo;
import com.openjad.orm.enums.IdType;

/**
 * insertAllColumn 节点
 * 
 *  @author hechuan
 *
 */
public class InsertAllColumnSP extends MapperItemSP {

	/* 
	 * <insert id="insertAllColumn" parameterType="com.jad.orm.mybatis.eo.RpcExceptionTraceDO"
	 * useGeneratedKeys="true" keyProperty="id">
	 * insert into m_rpc_exception (
	 * id,request_id,side,heap_stack,error_message,error_code
	 * )
	 * values (
	 * #{id,jdbcType=BIGINT},
	 * #{requestId,jdbcType=VARCHAR},
	 * #{side,jdbcType=VARCHAR},
	 * #{heapStack,jdbcType=VARCHAR},
	 * #{errorMessage,jdbcType=VARCHAR},
	 * #{errorCode,jdbcType=VARCHAR}
	 * )
	 * </insert>
	 * @param mapperSP mapperSP
	 */
	public InsertAllColumnSP(MapperSP mapperSP) {
		super(mapperSP, "insert", "insertAllColumn");
	}

	@Override
	public Node toNode() {
		Document doc = mapperSP.getDoc();
		Element node = doc.createElement(tag);
		node.setAttribute("id", id);
		node.setAttribute("parameterType", mapperSP.getEoType().getName());

		String tableName = mapperSP.getEoMetaInfo().getTableName();
		
		boolean containKey=false;
		
		IdType idType = mapperSP.getEoMetaInfo().getIdType();
		
		EoFieldInfo keyField = mapperSP.getEoMetaInfo().getKeyFieldInfo();
		if (keyField != null) {
			node.setAttribute("useGeneratedKeys", "true");
			node.setAttribute("keyProperty", keyField.getFieldName());
			if(!IdType.AUTO.equals(idType) && !IdType.IDENTITY.equals(idType)){
				containKey = true;
			}
		}

		StringBuffer sb = new StringBuffer();
		sb.append("\n");
		sb.append("insert into ");
		sb.append(tableName).append("\n").append("(");

		
		int count = 0;
		for (EoFieldInfo fieldInfo : mapperSP.getEoMetaInfo().getFieldInfoMap().values()) {
			
			if(!isCommonColumn(fieldInfo)){
				continue;
			}
			if(!fieldInfo.isInsertable()){
				continue;
			}
			
			String column = fieldInfo.getColumn();
			if(keyField!=null && !containKey && keyField.getColumn().equals(column)){
				continue;
			}
			if(count>0){
				sb.append(",");
			}
			sb.append(column);
			count++;
			if(count % 5 ==0){
				sb.append("\n");
			}
		}
		sb.append(")");
		sb.append("values (");
		
		count = 0;
		for (EoFieldInfo fieldInfo : mapperSP.getEoMetaInfo().getFieldInfoMap().values()) {
			if(!isCommonColumn(fieldInfo)){
				continue;
			}
			if(!fieldInfo.isInsertable()){
				continue;
			}
			String column = fieldInfo.getColumn();
			String fieldName = fieldInfo.getFieldName();
			if(keyField!=null && !containKey && keyField.getColumn().equals(column)){
				continue;
			}
			if(count>0){
				sb.append(",");
			}
			sb.append("#{").append(fieldName);
			sb.append(",jdbcType=").append(fieldInfo.getJdbcType()).append("}").append("\n");
			count++;
		}
		sb.append(")");
		
		node.appendChild(doc.createTextNode(sb.toString()));

		return node;
	}
	

}
