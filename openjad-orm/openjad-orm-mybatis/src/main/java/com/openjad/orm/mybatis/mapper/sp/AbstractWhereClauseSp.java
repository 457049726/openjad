package com.openjad.orm.mybatis.mapper.sp;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


/*
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
 */
public abstract class AbstractWhereClauseSp extends MapperItemSP {

	public AbstractWhereClauseSp(MapperSP mapperSP, String id) {
		super(mapperSP, "sql", id);
	}
	
	
	public void addIfElement(Document doc, Element foreach) {

		Element ifEl = doc.createElement("if");
		ifEl.setAttribute("test", "criteria.valid");
		foreach.appendChild(ifEl);

		Element trim = doc.createElement("trim");
		trim.setAttribute("prefix", "(");
		trim.setAttribute("suffix", ")");
		trim.setAttribute("prefixOverrides", "and");
		ifEl.appendChild(trim);

		Element foreach2 = doc.createElement("foreach");
		foreach2.setAttribute("collection", "criteria.criteria");
		foreach2.setAttribute("item", "criterion");
		trim.appendChild(foreach2);

		Element chooseEl = doc.createElement("choose");
		foreach2.appendChild(chooseEl);

		Element when1 = doc.createElement("when");
		when1.setAttribute("test", "criterion.noValue");
		when1.setTextContent("and ${criterion.condition} ");
		chooseEl.appendChild(when1);

		Element when2 = doc.createElement("when");
		when2.setAttribute("test", "criterion.singleValue");
		when2.setTextContent("and ${criterion.condition} #{criterion.value} ");
		chooseEl.appendChild(when2);

		Element when3 = doc.createElement("when");
		when3.setAttribute("test", "criterion.betweenValue");
		when3.setTextContent("and ${criterion.condition} #{criterion.value} and #{criterion.secondValue} ");
		chooseEl.appendChild(when3);

		Element when4 = doc.createElement("when");
		when4.setAttribute("test", "criterion.listValue");
		when4.setTextContent("and ${criterion.condition} ");

		chooseEl.appendChild(when4);

		Element foreach3 = doc.createElement("foreach");
		foreach3.setAttribute("collection", "criterion.value");
		foreach3.setAttribute("item", "listItem");
		foreach3.setAttribute("open", "(");
		foreach3.setAttribute("close", ")");
		foreach3.setAttribute("separator", ",");
		foreach3.setTextContent("#{listItem}");

		when4.appendChild(foreach3);
	
	}
	
}
