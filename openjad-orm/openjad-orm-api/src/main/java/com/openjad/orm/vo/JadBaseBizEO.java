package com.openjad.orm.vo;

import java.io.Serializable;
import java.util.Date;

public class JadBaseBizEO<ID extends Serializable> extends JadBaseEO<ID>
		implements ActiveRecordEO, DelFlagEO,TenantEO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String createUser;

	private Date createTime;

	private String updateUser;

	private Date updateTime;

	private String delFlag;

	private String remarks;

	private String status;
	
	private String tenantNo;

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String getDelFlag() {
		return delFlag;
	}

	@Override
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	@Override
	public String getRemarks() {
		return remarks;
	}

	@Override
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	@Override
	public String getTenantNo() {
		return tenantNo;
	}
	@Override
	public void setTenantNo(String tenantNo) {
		this.tenantNo = tenantNo;
	}
	
}
