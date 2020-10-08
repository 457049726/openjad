package com.openjad.orm.mybatis.entity;

import java.lang.reflect.Field;

import com.openjad.orm.annotation.RelateColumn;

/**
 * 实体属性信息
 * 
 * @author hechuan
 *
 */
@SuppressWarnings("serial")
public class EoFieldInfo extends ObjectFieldInfo {

	/**
	 * 实体信息
	 */
	private transient EoMetaInfo eoInfo;

	/**
	 * 字段列名
	 */
	private String column;

	/**
	 * 数据库列类型
	 */
	private String jdbcType;

	/**
	 * 是否为实体表的列
	 */
	private boolean isColumn = true;

	/**
	 * 是否关联列
	 */
	private boolean isRelateColumn = false;

	/**
	 * 是否为实体表的id
	 */
	private boolean isId = false;

	/**
	 * 关联列,如果实体属性类型为其它实体类型的对象，这里保存关联属信息
	 */
	private transient RelateColumn relateColumn;

	/**
	 * 关联表别名
	 */
	private String relateAlias;
	
	/**
	 * 排序
	 */
	private String orderBy;

	/**
	 * 是否唯一
	 */
	private boolean unique = false;
	
	/**
	 * 能否为null
	 */
	private boolean nullable = true;
	
	/**
	 * 能否插入
	 */
	private boolean insertable = true;
	
	/**
	 * 能否更新
	 */
	private boolean updatable = true;

	/**
	 * 入参为null时，是否强制更新为null
	 */
	private boolean forceUpdateNull = false;

	public EoFieldInfo(EoMetaInfo eoInfo, Field field) {
		super(field,eoInfo.getMetaClass());
		this.eoInfo = eoInfo;
	}

	public boolean isRelateColumn() {
		return isRelateColumn;
	}

	public void setRelateColumn(boolean isRelateColumn) {
		this.isRelateColumn = isRelateColumn;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public boolean isUnique() {
		return unique;
	}

	public void setUnique(boolean unique) {
		this.unique = unique;
	}

	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public boolean isInsertable() {
		return insertable;
	}

	public void setInsertable(boolean insertable) {
		this.insertable = insertable;
	}

	public boolean isUpdatable() {
		return updatable;
	}

	public void setUpdatable(boolean updatable) {
		this.updatable = updatable;
	}

	public EoMetaInfo getEoInfo() {
		return eoInfo;
	}

	public void setEoInfo(EoMetaInfo eoInfo) {
		this.eoInfo = eoInfo;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public boolean isColumn() {
		return isColumn;
	}

	public void setColumn(boolean isColumn) {
		this.isColumn = isColumn;
	}

	public boolean isId() {
		return isId;
	}

	public void setId(boolean isId) {
		this.isId = isId;
	}

	public RelateColumn getRelateColumn() {
		return relateColumn;
	}

	public void setRelateColumn(RelateColumn relateColumn) {
		this.relateColumn = relateColumn;
	}

	public String getRelateAlias() {
		return relateAlias;
	}

	public void setRelateAlias(String relateAlias) {
		this.relateAlias = relateAlias;
	}

	public boolean isForceUpdateNull() {
		return forceUpdateNull;
	}

	public void setForceUpdateNull(boolean forceUpdateNull) {
		this.forceUpdateNull = forceUpdateNull;
	}

	public String getJavaType() {
		return this.getFieldType().getName();
	}

	public String getJdbcType() {
		return jdbcType;
	}

	public void setJdbcType(String jdbcType) {
		this.jdbcType = jdbcType;
	}

}
