package com.openjad.orm.enums;

/**
 * 删除策略
 * @author hechuan
 *
 */
public enum DelStrategy {

	/**
	 * 罗辑删除
	 */
	LOGIC("logic", "罗辑删除"), 
	/**
	 * 物理删除
	 */
	PHYSICAL("physical", "物理删除");
	
	private final String type;

	private final String desc;

	DelStrategy(final String type, final String desc) {
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
