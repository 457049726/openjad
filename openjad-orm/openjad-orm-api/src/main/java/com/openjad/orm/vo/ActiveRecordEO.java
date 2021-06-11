package com.openjad.orm.vo;

import java.util.Date;

public interface ActiveRecordEO extends BaseEO {

	String getCreateUser();
	void setCreateUser(String createUser);

	Date getCreateTime();
	void setCreateTime(Date createTime);

	String getUpdateUser();
	void setUpdateUser(String updateUser);

	Date getUpdateTime();
	void setUpdateTime(Date updateTime);



}
