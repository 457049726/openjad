package com.openjad.orm.mybatis.mapper.sp;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.openjad.orm.mybatis.entity.EoMetaInfo;
import com.openjad.orm.mybatis.utils.SqlHelper;

/*
 * <sql id="Base_Column_List" >
id,request_id,side,heap_stack,error_message,error_code
</sql> 
 *
 */
public class BaseColumnListSP extends MapperItemSP{

	public BaseColumnListSP(MapperSP mapperSP) {
		super(mapperSP, "sql", "Base_Column_List");
	}

	@Override
	public Node toNode() {
		
		Document doc = mapperSP.getDoc();
		
		Element node = doc.createElement(tag);
		node.setAttribute("id", id);
		EoMetaInfo ei = mapperSP.getEoMetaInfo();
		node.setTextContent(SqlHelper.getSelectColumn(ei,SqlHelper.getDefAlias(ei)));
		
		return node;
	}


}
