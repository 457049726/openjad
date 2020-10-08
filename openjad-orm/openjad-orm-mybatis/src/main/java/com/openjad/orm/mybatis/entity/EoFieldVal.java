package com.openjad.orm.mybatis.entity;


/**
 * 属性值
 * 
 *  @author hechuan
 *
 */
public class EoFieldVal {
	
	/**
	 * 属性信息
	 */
	private transient EoFieldInfo info;
	
	/**
	 * 属性值
	 */
	private Object value;

	public EoFieldVal(EoFieldInfo info, Object value) {
		super();
		this.info = info;
		this.value = value;
	}

	public EoFieldInfo getInfo() {
		return info;
	}

	public void setInfo(EoFieldInfo info) {
		this.info = info;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
