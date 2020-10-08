package com.openjad.orm.mybatis.entity;

import com.openjad.orm.enums.QueryOperateType;


/**
 * 全部用 and 连接起来的where条件
 *
 */
public class WhereCondition {
	
	/**
	 * 条件
	 */
	private String conditionKey;
	
	/**
	 * 字段信息
	 */
	private EoFieldInfo fieldInfo;
	
	/**
	 * 实体信息
	 */
	private EoMetaInfo ei;
	
	/**
	 * 操作类型
	 */
	private QueryOperateType operateType;
	
	/**
	 * 是否有效
	 */
	private boolean isValid=true;
	
	
	
	public WhereCondition(EoMetaInfo ei,EoFieldInfo fieldInfo){
		this.ei=ei;
		this.fieldInfo=fieldInfo;
	}

	public String getConditionKey() {
		return conditionKey;
	}

	public void setConditionKey(String conditionKey) {
		this.conditionKey = conditionKey;
	}

	public EoFieldInfo getFieldInfo() {
		return fieldInfo;
	}

	public void setFieldInfo(EoFieldInfo fieldInfo) {
		this.fieldInfo = fieldInfo;
	}

	public QueryOperateType getOperateType() {
		return operateType;
	}

	public void setOperateType(QueryOperateType operateType) {
		this.operateType = operateType;
	}

	public EoMetaInfo getEi() {
		return ei;
	}

	public void setEi(EoMetaInfo ei) {
		this.ei = ei;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
	
	


}
