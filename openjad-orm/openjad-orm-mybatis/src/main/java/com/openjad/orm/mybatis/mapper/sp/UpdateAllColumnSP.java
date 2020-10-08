package com.openjad.orm.mybatis.mapper.sp;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.openjad.orm.mybatis.entity.EoFieldInfo;
import com.openjad.orm.enums.IdType;


/*
 * updateAllColumn 节点 
 * 
 *  <sql id="updateAllColumn" >
   request_id = #{record.requestId,jdbcType=VARCHAR},
   side = #{record.side,jdbcType=VARCHAR},   heap_stack = #{record.heapStack,jdbcType=VARCHAR},
   error_message = #{record.errorMessage,jdbcType=VARCHAR},   error_code = #{record.errorCode,jdbcType=VARCHAR}
  </sql>
 *
 */
public class UpdateAllColumnSP extends MapperItemSP {

	public UpdateAllColumnSP(MapperSP mapperSP) {
		super(mapperSP, "sql", "updateAllColumn");
	}

	@Override
	public Node toNode() {
		Document doc = mapperSP.getDoc();
		Element node = doc.createElement(tag);
		node.setAttribute("id", id);
		
		boolean containKey=false;
		IdType idType = mapperSP.getEoMetaInfo().getIdType();
		EoFieldInfo keyField = mapperSP.getEoMetaInfo().getKeyFieldInfo();
		if (keyField != null) {
			if(!IdType.AUTO.equals(idType) && !IdType.IDENTITY.equals(idType)){
				containKey = true;
			}
		}
		StringBuffer sb = new StringBuffer();
		sb.append("\n");
		
		int count = 0;
		for (EoFieldInfo fieldInfo : mapperSP.getEoMetaInfo().getFieldInfoMap().values()) {
			if(!isCommonColumn(fieldInfo)){
				continue;
			}
			if(!fieldInfo.isUpdatable()){
				continue;
			}
			if(fieldInfo.isId()){
				continue;
			}
			String column = fieldInfo.getColumn();
			if(keyField!=null && !containKey && keyField.getColumn().equals(column)){
				continue;
			}
			if(count>0){
				sb.append(",\n");
			}
			sb.append(column).append(" = #{record.");
			sb.append(fieldInfo.getFieldName()).append(",jdbcType=");
			sb.append(fieldInfo.getJdbcType()).append("}");
			count++;
		}
		sb.append("\n");
		node.appendChild(doc.createTextNode(sb.toString()));
		return node;
	}

}
