package com.openjad.orm.vo;

public interface DelFlagEO extends BaseEO {

	String NOT_DEL = "0";

	String DELETED = "1";
	
	String DEL_FLAG_COLUMN="del_flag";

	String getDelFlag();

	void setDelFlag(String delFlag);
}
