package com.openjad.common.dto;

import java.io.Serializable;

import com.openjad.common.constant.FrameworkCode;
import com.openjad.common.constant.cv.BaseCode;

@SuppressWarnings("serial")
public class ResultDTO<T> implements Serializable {

	private String code;

	private String msg;

	private T data;

	public ResultDTO() {
	}

	public ResultDTO(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public ResultDTO(String code, String msg, T data) {
		this(code, msg);
		this.data = data;
	}
	
	public static boolean isSuccess(ResultDTO<?> result){
		if(result==null){
			return false;
		}
		return FrameworkCode.CODE_00000.getCode().equals(result.getCode());
	}

	/**
	 * 成功 
	 * @param <T> t
	 * @param data data
	 * @return data
	 */
	public static <T> ResultDTO<T> newSuccess(T data) {
		return newResult(FrameworkCode.CODE_00000, data);
	}

	public static <T> ResultDTO<T> newSuccess() {
		return newResult(FrameworkCode.CODE_00000, null);
	}

	/**
	 * 系统异常
	 * 
	 * @return data
	 */
	@SuppressWarnings("rawtypes")
	public static ResultDTO newSystemException() {
		return newSystemException(null);
	}

	/**
	 * 系统异常
	 * @param msg data
	 * @return data
	 */
	@SuppressWarnings("rawtypes")
	public static ResultDTO newSystemException(String msg) {
		return newResult(FrameworkCode.CODE_99999, msg);

	}

	/**
	 * 业务异常
	 * 
	 * @return data
	 */
	@SuppressWarnings("rawtypes")
	public static ResultDTO newBizException() {
		return newBizException(null);
	}

	/**
	 * 业务异常
	 * @param msg data
	 * @return data
	 */
	@SuppressWarnings("rawtypes")
	public static ResultDTO newBizException(String msg) {
		return newResult(FrameworkCode.CODE_39999, msg);
	}

	/**
	 * 参数异常 
	 * 
	 * @return data
	 */
	@SuppressWarnings("rawtypes")
	public static ResultDTO newArgumentException() {
		return newArgumentException(null);
	}

	/**
	 * 参数异常 
	 * 
	 * @param msg data
	 * @return data
	 */
	@SuppressWarnings("rawtypes")
	public static ResultDTO newArgumentException(String msg) {
		return newResult(FrameworkCode.CODE_00020, msg);
	}

	/**
	 * 生成结果
	 * @param <T> t 
	 * @param resultCode resultCode
	 * @return res
	 */
	public static <T> ResultDTO<T> newResult(BaseCode resultCode) {
		return newResult(resultCode, null, null);
	}

	/**
	 * 生成结果
	 * @param <T> t
	 * @param resultCode resultCode
	 * @param msg msg
	 * @return res
	 */
	public static <T> ResultDTO<T> newResult(BaseCode resultCode, String msg) {
		return newResult(resultCode, msg, null);
	}
	
	/**
	 * 生成结果 
	 * @param <T> t
	 * @param resultCode  resultCode
	 * @param data data
	 * @return data
	 */
	public static <T> ResultDTO<T> newResult(BaseCode resultCode, T data) {
		return newResult(resultCode, null, data);
	}
	
	/**
	 * 生成结果 
	 * @param <T> t
	 * @param resultCode resultCode
	 * @param msg msg
	 * @param data data
	 * @return data
	 */
	public static <T> ResultDTO<T> newResult(BaseCode resultCode, String msg, T data) {
		if (resultCode == null) {
			resultCode = FrameworkCode.CODE_99999;
		}
		if (msg == null || "".equals(msg.trim())) {
			msg = resultCode.getValue();
		}
		return newResult(resultCode.getCode(), msg, data);
	}
	
	/**
	 *  生成结果 
	 * @param <T> t
	 * @param code code
	 * @param msg msg
	 * @return msg
	 */
	public static <T> ResultDTO<T> newResult(String code, String msg) {
		return new ResultDTO<T>(code, msg, null);
	}

	/**
	 * 生成结果
	 * @param <T> t
	 * @param code code
	 * @param msg msg
	 * @param data data
	 * @return data
	 */
	public static <T> ResultDTO<T> newResult(String code, String msg, T data) {
		return new ResultDTO<T>(code, msg, data);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
