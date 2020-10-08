package com.openjad.orm.mybatis.entity;

import java.util.HashMap;
import java.util.Map;

import com.openjad.orm.enums.IdType;

/**
 * 实体元信息
 * 
 *  @author hechuan
 *
 */
@SuppressWarnings("serial")
public class EoMetaInfo extends ObjectMetaInfo {

	public EoMetaInfo(Class clazz) {
		super(clazz);
	}

	/**
	 * 表名称
	 */
	private String tableName;

	/**
	 * 记录实体属性上所有的 OrderBy 注解
	 */
	private String orderBy;

	/**
	 * id类型
	 */
	private IdType idType = IdType.AUTO;

	/**
	 * 序列名称
	 */
	private String sequenceName ;

	/**
	 * 实体字段 key:field name,val:EoFieldInfo
	 */
	private Map<String, EoFieldInfo> fieldInfoMap;

	/**
	 * 主键字段信息
	 */
	private EoFieldInfo keyFieldInfo;

	/**
	 * 缓存 where条件 key:where key_sepeciType,val:WhereCondition
	 */
	private Map<String, WhereCondition> whereConditionMap = new HashMap<String, WhereCondition>();

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public EoFieldInfo getKeyFieldInfo() {
		return keyFieldInfo;
	}

	public void setKeyFieldInfo(EoFieldInfo keyFieldInfo) {
		this.keyFieldInfo = keyFieldInfo;
	}

	public Map<String, WhereCondition> getWhereConditionMap() {
		return whereConditionMap;
	}

	public void setWhereConditionMap(Map<String, WhereCondition> whereConditionMap) {
		this.whereConditionMap = whereConditionMap;
	}

	public Map<String, EoFieldInfo> getFieldInfoMap() {
		return fieldInfoMap;
	}

	public void setFieldInfoMap(Map<String, EoFieldInfo> fieldInfoMap) {
		this.fieldInfoMap = fieldInfoMap;
	}

	public IdType getIdType() {
		return idType;
	}

	public void setIdType(IdType idType) {
		this.idType = idType;
	}

	public String getSequenceName() {
		return sequenceName;
	}

	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
	}

}
