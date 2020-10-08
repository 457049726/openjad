package com.openjad.orm.enums;

/**
 *  like 枚举
 * 
 *  @author hechuan
 *
 */
public enum SQLlike {
	/**
	 * LEFT
	 */
	LEFT("left", "左边"),
	/**
	 * RIGHT
	 */
	RIGHT("right", "右边"),
	/**
	 * DEFAULT
	 */
	DEFAULT("default", "两边");

	/** 主键 */
	private final String type;

	/** 描述 */
	private final String desc;

	SQLlike(final String type, final String desc) {
		this.type = type;
		this.desc = desc;
	}

	public String getType() {
		return type;
	}

	public String getDesc() {
		return desc;
	}

}
