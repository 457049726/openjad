package com.openjad.orm.mybatis.mapper.sp;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/*
 * <sql id="Update_By_Xr_Where_Clause" >
    <where >
      <foreach collection="whereCriteria.oredCriteria" item="criteria" separator="or" >
        <if test="criteria.valid" >
          <trim prefix="(" suffix=")" prefixOverrides="and" >
            <foreach collection="criteria.criteria" item="criterion" >
              <choose >
                <when test="criterion.noValue" >
                  and ${criterion.condition}
                </when>
                <when test="criterion.singleValue" >
                  and ${criterion.condition} #{criterion.value}
                </when>
                <when test="criterion.betweenValue" >
                  and ${criterion.condition} #{criterion.value} and #{criterion.secondValue}
                </when>
                <when test="criterion.listValue" >
                  and ${criterion.condition}
                  <foreach collection="criterion.value" item="listItem" open="(" close=")" separator="," >#{listItem}</foreach>
                </when>
              </choose>
            </foreach>
          </trim>
        </if>
      </foreach>
    </where>
  </sql>
 *
 */
public class UpdateByXrWhereClauseSP extends AbstractWhereClauseSp {
	
	public UpdateByXrWhereClauseSP(MapperSP mapperSP)  {
		super(mapperSP,  "Update_By_Xr_Where_Clause");
	}

	@Override
	public Node toNode() {
		
		Document doc = mapperSP.getDoc();
		Element node = doc.createElement(tag);
		node.setAttribute("id", id);
		
		Element where = doc.createElement("where");
		node.appendChild(where);
		
		Element foreach = doc.createElement("foreach");
		foreach.setAttribute("collection", "whereCriteria.oredCriteria");
		foreach.setAttribute("item", "criteria");
		foreach.setAttribute("separator", "or");
		where.appendChild(foreach);
		
		addIfElement(doc, foreach);
		
		
		return node;
	}

}
