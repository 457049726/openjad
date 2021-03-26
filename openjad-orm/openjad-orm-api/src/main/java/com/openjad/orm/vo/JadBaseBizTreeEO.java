package com.openjad.orm.vo;

import java.io.Serializable;
import java.util.Date;

public class JadBaseBizTreeEO<ID extends Serializable,EO extends BaseEO> 
	extends JadBaseTreeEO<ID,EO> implements ActiveRecordEO,DelFlagEO,TenantEO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private String createUser; // CREATE_USER

	private Date createTime; // CREATE_TIME

	private String updateUser; // UPDATE_USER

	private Date updateTime; // UPDATE_TIME
	
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

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTenantNo() {
		return tenantNo;
	}

	public void setTenantNo(String tenantNo) {
		this.tenantNo = tenantNo;
	}
	
	
	
}
