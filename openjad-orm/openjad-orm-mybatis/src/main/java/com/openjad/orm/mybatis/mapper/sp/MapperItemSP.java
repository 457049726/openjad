package com.openjad.orm.mybatis.mapper.sp;

import org.w3c.dom.Node;

import com.openjad.common.util.reflection.ReflectUtils;
import com.openjad.orm.mybatis.entity.EoFieldInfo;

/**
 * mapper节点信息
 * 
 * 用于描述mapper文件一个具体的节点
 * 
 *  @author hechuan
 *
 */
public abstract class MapperItemSP {

	/**
	 * mapper信息
	 */
	protected transient MapperSP mapperSP;

	/**
	 * id属性值
	 */
	protected String id;

	/**
	 * 标签名
	 */
	protected String tag;
	
	/**
	 * criterion 包名
	 */
	protected static final String CRITERION_PACKAGE = "com.openjad.orm.criterion";

	public MapperItemSP(MapperSP mapperSP, String tag, String id) {
		this.mapperSP = mapperSP;
		this.tag = tag;
		this.id = id;
	}
	
	/**
	 * 判断属性是否可能为数据库列
	 * @param fieldInfo 属性信息
	 * @return 是否可能为数据库列
	 */
	protected boolean isCommonColumn(EoFieldInfo fieldInfo) {
		return fieldInfo.isColumn() && !fieldInfo.isRelateColumn()
				&& ReflectUtils.isPrimitive(fieldInfo.getFieldType());
	}
	
	/**
	 * 将当前 mapperItem 转换为 node
	 * @return res
	 */
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
