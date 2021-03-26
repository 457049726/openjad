package com.openjad.orm.mybatis.mapper.sp;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

import com.openjad.orm.mybatis.utils.SqlHelper;

/*
 * 
 <select id="listByPagedCriteria" resultMap="BaseResultMap"
 * parameterType="com.openjad.orm.criterion.PagedCriteria" >
 * select
 * <if test="distinct" >distinct</if>
 * <include refid="Base_Column_List" />
 * from m_rpc_exception
 * <if test="_parameter != null" >
 * <include refid="Xr_Where_Clause" />
 * </if>
 * <if test="orderByClause != null" >
 * order by ${orderByClause}
 * </if>
 * </select>
 *
 */
public class ListByPagedCriteriaSP extends MapperItemSP {

	public ListByPagedCriteriaSP(MapperSP mapperSP) {
		super(mapperSP, "select", "listByPagedCriteria");
	}

	@Override
	public Node toNode() {
		Document doc = mapperSP.getDoc();
		Element node = doc.createElement(tag);
		node.setAttribute("id", id);

		node.setAttribute("resultType", mapperSP.getEoType().getName());
		node.setAttribute("parameterType", CRITERION_PACKAGE + ".PagedCriteria");

		Text select = doc.createTextNode("select");
		node.appendChild(select);

		Element if1 = doc.createElement("if");
		if1.setAttribute("test", "distinct");
		if1.appendChild(doc.createTextNode("distinct"));
		node.appendChild(if1);

		Element include = doc.createElement("include");
		include.setAttribute("refid", "Base_Column_List");
		node.appendChild(include);

		String tableName = mapperSP.getEoMetaInfo().getTableName();
		String alias = SqlHelper.getDefAlias(mapperSP.getEoMetaInfo());
		String table = tableName + " " + alias;

		node.appendChild(doc.createTextNode(" from " + table));

		Element if2 = doc.createElement("if");
		if2.setAttribute("test", "_parameter != null");
		Element include2 = doc.createElement("include");
		include2.setAttribute("refid", "Xr_Where_Clause");
		if2.appendChild(include2);
		node.appendChild(if2);

		Element if3 = doc.createElement("if");
		if3.setAttribute("test", "orderByClause != null");
		if3.appendChild(doc.createTextNode("order by ${orderByClause}"));
		node.appendChild(if3);

		return node;
	}

}
