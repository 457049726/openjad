package com.openjad.orm.mybatis.entity;


import com.openjad.common.util.StringUtils;
import com.openjad.orm.annotation.OrganizeStrategy;

public class OrganizeStrategyInfo {
	
	private String typeCode;
	private String method;
	private String orgColumn;
	private String orgTable;
	
	public static OrganizeStrategyInfo valueFormOrganizeStrategy(OrganizeStrategy organizeStrategy) {
		
		if(organizeStrategy==null) {
			return null;
		}
		
		OrganizeStrategyInfo info = new OrganizeStrategyInfo();
		info.setTypeCode(organizeStrategy.typeCode());
		info.setMethod(organizeStrategy.method());
		info.setOrgColumn(organizeStrategy.orgColumn());
		info.setOrgTable(organizeStrategy.orgTable());
		
		if(StringUtils.isBlank(organizeStrategy.typeCode()) && StringUtils.isBlank(organizeStrategy.orgColumn())) {
			throw new RuntimeException("类型代码与组织列不能同时为空");
		}else if(StringUtils.isNotBlank(organizeStrategy.typeCode()) && StringUtils.isBlank(organizeStrategy.orgColumn())) {
			info.setOrgColumn(StringUtils.generateTableName(organizeStrategy.typeCode()));
		}else if(StringUtils.isBlank(organizeStrategy.typeCode()) && StringUtils.isNotBlank(organizeStrategy.orgColumn())) {
			info.setTypeCode(StringUtils.uncapitalize(StringUtils.generateJavaClass(organizeStrategy.orgColumn())));
		}
		
		if(OrganizeStrategy.SEPARATE_METHOD.equals(organizeStrategy.method()) && StringUtils.isBlank(organizeStrategy.orgTable())) {
			throw new RuntimeException("分离模式下必段指定组织关联表");
		}
		return info;
		
	}
	
	
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getOrgColumn() {
		return orgColumn;
	}
	public void setOrgColumn(String orgColumn) {
		this.orgColumn = orgColumn;
	}
	public String getOrgTable() {
		return orgTable;
	}
	public void setOrgTable(String orgTable) {
		this.orgTable = orgTable;
	}

}
