package com.openjad.orm.mybatis.mapper.sp;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.openjad.orm.mybatis.entity.EoFieldInfo;


/*
 * updateByIdSelective 节点
 * 
 *   <update id="updateByIdSelective"  >
    update m_rpc_exception
  <include refid="updateSelective" />
where id = #{id,jdbcType=BIGINT}
  </update>
 *
 */
public class UpdateByIdSelectiveSP extends MapperItemSP{

	public UpdateByIdSelectiveSP(MapperSP mapperSP) {
		super(mapperSP, "update", "updateByIdSelective");
	}

	@Override
	public Node toNode() {

		Document doc = mapperSP.getDoc();
		Element node = doc.createElement(tag);
		node.setAttribute("id", id);
		
		String tableName = mapperSP.getEoMetaInfo().getTableName();
		
		EoFieldInfo keyField = mapperSP.getEoMetaInfo().getKeyFieldInfo();
		
		node.appendChild(doc.createTextNode("\n update "+tableName+" \n"));
		Element include = doc.createElement("include");
		include.setAttribute("refid", "updateSelective");
		node.appendChild(include);
		StringBuffer sb=new StringBuffer();
		sb.append("\n where ").append(keyField.getColumn());
//		sb.append("= #{").append(keyField.getFieldName());
		sb.append("= #{").append("id");
		sb.append(",jdbcType=").append(keyField.getJdbcType()).append("}\n");
		node.appendChild(doc.createTextNode(sb.toString()));
		
		return node;
	}
}
