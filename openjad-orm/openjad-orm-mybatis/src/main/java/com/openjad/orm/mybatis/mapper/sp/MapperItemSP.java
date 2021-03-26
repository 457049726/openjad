package com.openjad.orm.mybatis.mapper.sp;

import org.w3c.dom.Node;

import com.openjad.common.util.reflection.ReflectUtils;
import com.openjad.orm.mybatis.entity.EoFieldInfo;

public abstract class MapperItemSP {

	protected transient MapperSP mapperSP;

	protected String id;

	protected String tag;

	protected static final String CRITERION_PACKAGE = "com.openjad.orm.criterion";

	public MapperItemSP(MapperSP mapperSP, String tag, String id) {
		this.mapperSP = mapperSP;
		this.tag = tag;
		this.id = id;
	}

	protected boolean isCommonColumn(EoFieldInfo fieldInfo) {
		return fieldInfo.isColumn() && !fieldInfo.isRelateColumn()
				&& ReflectUtils.isPrimitive(fieldInfo.getFieldType());
	}

	public abstract Node toNode();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

}
