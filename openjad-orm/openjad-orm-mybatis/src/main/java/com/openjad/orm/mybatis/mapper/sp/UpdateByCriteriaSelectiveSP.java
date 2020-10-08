package com.openjad.orm.mybatis.mapper.sp;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/*
 * updateByCriteriaSelective 节点
 *   <update id="updateByCriteriaSelective"   >
    update m_rpc_exception
   <include refid="updateSelective" />
    <if test="_parameter != null" >
      <include refid="Update_By_Xr_Where_Clause" />
    </if>
  </update>
 *
 */
public class UpdateByCriteriaSelectiveSP extends MapperItemSP{

	public UpdateByCriteriaSelectiveSP(MapperSP mapperSP) {
		super(mapperSP, "update", "updateByCriteriaSelective");
	}

	@Override
	public Node toNode() {
		Document doc = mapperSP.getDoc();
		Element node = doc.createElement(tag);
		node.setAttribute("id", id);
		
		String tableName = mapperSP.getEoMetaInfo().getTableName();
		
		StringBuffer sb = new StringBuffer();
		sb.append("\n update ").append(tableName).append("\n");
		node.appendChild(doc.createTextNode(sb.toString()));
		
		Element include=doc.createElement("include");	
		include.setAttribute("refid", "updateSelective");
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
