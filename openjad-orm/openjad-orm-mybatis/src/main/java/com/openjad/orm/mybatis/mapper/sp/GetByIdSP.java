package com.openjad.orm.mybatis.mapper.sp;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.openjad.orm.mybatis.entity.EoFieldInfo;
import com.openjad.orm.mybatis.utils.SqlHelper;


/*
 * 
 * getById 节点
 * 
 *   <select id="getById" resultMap="BaseResultMap" parameterType="Long" >
    select 
    <include refid="Jad_Base_Column_List" />
    from m_rpc_exception where id = #{id,jdbcType=BIGINT}
  </select>
 *
 */
public class GetByIdSP extends MapperItemSP {

	public GetByIdSP(MapperSP mapperSP) {
		super(mapperSP, "select", "getById");
	}

	@Override
	public Node toNode() {
		Document doc = mapperSP.getDoc();
		Element node = doc.createElement(tag);
		node.setAttribute("id", id);
		
		EoFieldInfo keyField = mapperSP.getEoMetaInfo().getKeyFieldInfo();
		
		node.setAttribute("resultType", mapperSP.getEoType().getName());
		node.setAttribute("parameterType", keyField.getJavaType());

		node.appendChild(doc.createTextNode("\n select "));

		Element include = doc.createElement("include");
		include.setAttribute("refid", "Jad_Base_Column_List");
		node.appendChild(include);

		String tableName = mapperSP.getEoMetaInfo().getTableName();

		String keyColumn = keyField.getColumn();
		String keyJdbcType = keyField.getJdbcType();
		
		String alias = SqlHelper.getDefAlias(mapperSP.getEoMetaInfo());
		
		String table = tableName +" "+ alias ;
		
		node.appendChild(doc.createTextNode("\n from " + table
				+"\n where " + keyColumn + " = #{"+keyField.getFieldName()+",jdbcType=" + keyJdbcType + "} "));

		return node;
	}


}
