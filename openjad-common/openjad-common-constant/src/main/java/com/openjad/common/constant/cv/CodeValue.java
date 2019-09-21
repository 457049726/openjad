package com.openjad.common.constant.cv;

/**
 * 
 * @author hechuan
 *
 */
public interface CodeValue {

	/**
	 * 
	 * 
	 * @return 返回code
	 */
	String getCode();

	/**
	 *  
	 * 
	 * @return 返回value
	 */
	String getValue();

	/**
	 * 
	 * 
	 * @return 返回类型
	 */
	String getTypeFlag();
}

/**
 * 
 * @author hechuan
 *
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