package com.openjad.orm.vo;

import java.util.Date;

@SuppressWarnings("serial")
public class JadActiveRecordEO implements ActiveRecordEO {

	private String createUser; // CREATE_USER

	private Date createTime; // CREATE_TIME

	private String updateUser; // UPDATE_USER

	private Date updateTime; // UPDATE_TIME

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}



}
