package com.openjad.common.constant.cv;

/**
 * 
 * @Title CodeValue
 * @author hechuan
 * @date 2019年9月20日
 *
 *       <功能简述>
 */
public interface CodeValue {

	/**
	 * 返回code
	 * 
	 * @return
	 */
	String getCode();

	/**
	 * 返回value
	 * 
	 * @return
	 */
	String getValue();

	/**
	 * 返回类型
	 * 
	 * @return
	 */
	String getTypeFlag();
}

/**
 * 
 * @Title CodeValuePair
 * @author hechuan
 * @date 2019年9月20日
 *
 *       <功能简述>
 */
class CodeValuePair implements CodeValue {
	private String code;
	private String value;
	private String typeFlag;

	CodeValuePair() {
	}
	
	public CodeValuePair(String typeFlag, String code, String value) {
		this.typeFlag = typeFlag;
		this.code = code;
		this.value = value;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String getCode() {
		return this.code;
	}

	@Override
	public String getValue() {
		return this.value;
	}

	public String getTypeFlag() {
		return typeFlag;
	}

	public void setTypeFlag(String typeFlag) {
		this.typeFlag = typeFlag;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append(this.getCode());
		sb.append(":");
		sb.append("\"");
		sb.append(this.getValue());
		sb.append("\"");
		sb.append("}");

		return sb.toString();
	}
}