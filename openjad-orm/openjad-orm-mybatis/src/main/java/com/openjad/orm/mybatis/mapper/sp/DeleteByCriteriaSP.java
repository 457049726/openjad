package com.openjad.orm.mybatis.mapper.sp;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class DeleteByCriteriaSP extends MapperItemSP {

	public DeleteByCriteriaSP(MapperSP mapperSP) {
		super(mapperSP, "delete", "deleteByCriteria");
	}

	/*
	 * <delete id="deleteByCriteria"
	 * parameterType="com.jad.dao.criterion.WhereCriteria" >
	 * delete from m_rpc_exception
	 * <if test="_parameter != null" >
	 * <include refid="Xr_Where_Clause" />
	 * </if>
	 * </delete>
	 */
	@Override
	public Node toNode() {
		Document doc = mapperSP.getDoc();
		Element node = doc.createElement(tag);
		node.setAttribute("id", id);
		node.setAttribute("parameterType", CRITERION_PACKAGE + ".WhereCriteria");

		String tableName = mapperSP.getEoMetaInfo().getTableName();
		node.appendChild(doc.createTextNode("delete from " + tableName));

		Element if1 = doc.createElement("if");
		if1.setAttribute("test", "_parameter != null");
		Element include = doc.createElement("include");
		include.setAttribute("refid", "Xr_Where_Clause");
		if1.appendChild(include);
		node.appendChild(if1);

		return node;
	}

}
