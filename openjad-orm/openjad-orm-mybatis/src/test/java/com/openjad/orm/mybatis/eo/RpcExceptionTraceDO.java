package com.openjad.orm.mybatis.eo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "m_rpc_exception")
//@KeyGenStrategy(idType=IdType.AUTO)
public class RpcExceptionTraceDO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private Long id;	// id

	private String requestId;	// 请求id

	private String side;	// side

	private String heapStack;	// 异常堆栈

	private String errorMessage;	// 异常信息

	private String errorCode;	// error_code

	public RpcExceptionTraceDO() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	public String getHeapStack() {
		return heapStack;
	}

	public void setHeapStack(String heapStack) {
		this.heapStack = heapStack;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}



}
