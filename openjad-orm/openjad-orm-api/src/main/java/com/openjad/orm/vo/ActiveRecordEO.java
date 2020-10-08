package com.openjad.orm.vo;

import java.util.Date;

public interface ActiveRecordEO extends BaseEO {

	public String getCreateUser();

	public void setCreateUser(String createUser);

	public Date getCreateTime();

	public void setCreateTime(Date createTime);

	public String getUpdateUser();

	public void setUpdateUser(String updateUser);

	public Date getUpdateTime();

	public void setUpdateTime(Date updateTime);

}
