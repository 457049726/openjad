package com.openjad.orm.mybatis.mapper.sp;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.openjad.orm.mybatis.entity.EoFieldInfo;

/*
 * 
 *   <update id="updateByIdAllColumn"   >
    update m_rpc_exception 
    set 
      <include refid="updateAllColumn" />
where id = #{id,jdbcType=BIGINT}
  </update>
 *
 */
public class UpdateByIdAllColumnSP extends MapperItemSP{
	
	public UpdateByIdAllColumnSP(MapperSP mapperSP) {
		super(mapperSP, "update", "updateByIdAllColumn");
	}

	@Override
	public Node toNode() {
		Document doc = mapperSP.getDoc();
		Element node = doc.createElement(tag);
		node.setAttribute("id", id);
		
		String tableName = mapperSP.getEoMetaInfo().getTableName();
		EoFieldInfo keyFieldInfo = mapperSP.getEoMetaInfo().getKeyFieldInfo();
		
		StringBuffer sb = new StringBuffer();
		sb.append("\n update ").append(tableName).append(" set \n");
		node.appendChild(doc.createTextNode(sb.toString()));
		Element include = doc.createElement("include");
		include.setAttribute("refid", "updateAllColumn");
		node.appendChild(include);
		sb = new StringBuffer();
		sb.append("\n where ").append(keyFieldInfo.getColumn());
//		sb.append(" = #{").append(keyFieldInfo.getFieldName());
		sb.append(" = #{").append("id");
		sb.append(",jdbcType=").append(keyFieldInfo.getJdbcType()).append("}\n");
		
		node.appendChild(doc.createTextNode(sb.toString()));
		return node;
	}
}
