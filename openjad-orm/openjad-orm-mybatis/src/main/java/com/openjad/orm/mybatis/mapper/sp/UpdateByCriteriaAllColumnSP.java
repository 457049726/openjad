package com.openjad.orm.mybatis.mapper.sp;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/*
 * 
 *   <update id="updateByCriteriaAllColumn"   >
    update m_rpc_exception set 
      <include refid="updateAllColumn" />
    <if test="_parameter != null" >
      <include refid="Update_By_Xr_Where_Clause" />
    </if>
  </update>
 *
 */
public class UpdateByCriteriaAllColumnSP extends MapperItemSP{

	public UpdateByCriteriaAllColumnSP(MapperSP mapperSP) {
		super(mapperSP, "update", "updateByCriteriaAllColumn");
	}

	
	@Override
	public Node toNode() {
		Document doc = mapperSP.getDoc();
		Element node = doc.createElement(tag);
		node.setAttribute("id", id);
		
		String tableName = mapperSP.getEoMetaInfo().getTableName();
		
		StringBuffer sb = new StringBuffer();
		sb.append("\n update ").append(tableName).append(" set \n");
		node.appendChild(doc.createTextNode(sb.toString()));
		
		Element include=doc.createElement("include");	
		include.setAttribute("refid", "updateAllColumn");
		node.appendChild(include);
		
		Element ife=doc.createElement("if");	
		ife.setAttribute("test", "_parameter != null");
		Element ifinclude=doc.createElement("include");	
		ifinclude.setAttribute("refid", "Update_By_Xr_Where_Clause");
		ife.appendChild(ifinclude);
		node.appendChild(ife);
		
		return node;
	}
}
