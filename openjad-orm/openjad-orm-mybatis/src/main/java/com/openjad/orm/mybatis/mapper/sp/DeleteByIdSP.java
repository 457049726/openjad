package com.openjad.orm.mybatis.mapper.sp;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.openjad.orm.mybatis.entity.EoFieldInfo;

/*
 * 
 *   <delete id="deleteById" parameterType="Long" >
    delete from m_rpc_exception where id = #{id,jdbcType=BIGINT}
  </delete>
 *
 */
public class DeleteByIdSP extends MapperItemSP {

	public DeleteByIdSP(MapperSP mapperSP) {
		super(mapperSP, "delete", "deleteById");
	}

	@Override
	public Node toNode() {

		Document doc = mapperSP.getDoc();
		Element node = doc.createElement(tag);
		node.setAttribute("id", id);

		EoFieldInfo keyField = mapperSP.getEoMetaInfo().getKeyFieldInfo();
		node.setAttribute("parameterType", keyField.getJavaType());

		String tableName = mapperSP.getEoMetaInfo().getTableName();
		String keyColumn = keyField.getColumn();
		String keyJdbcType = keyField.getJdbcType();

		String sql = "delete from " + tableName + " where " + keyColumn + " = #{"+keyField.getFieldName()+",jdbcType=" + keyJdbcType + "}";

		node.appendChild(doc.createTextNode(sql));

		return node;
	}

}
