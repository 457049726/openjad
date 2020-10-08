package com.openjad.orm.enums;

/**
 * 生成ID类型枚举类
 * 
 * @author hechuan
 *
 */
public enum IdType {

	TABLE(0, "table方式"), //jad暂时没有实现 
	SEQUENCE(1, "序列"), //目前jad只在oracle中实现，对于mysql自动切换为uuid
	IDENTITY(2, "数据库ID自增"),
	AUTO(3, "自动判断"), //自动判断，如果当前用的是mysql，则主键自动递增，如果当用的是oracle，此方式同sequence
	UUID(4, "全局唯一ID")//
	;

	/** 
	 * 主键
	 */
	private final int key;

	/** 
	 * 描述
	 */
	private final String desc;

	IdType(final int key, final String desc) {

		this.key = key;
		this.desc = desc;
	}

	/**
	 * <p>
	 * 主键策略
	 * </p>
	 * 
	 * @param idType
	 *            ID 策略类型
	 * @return 主键策略
	 */
	public static IdType getIdType(int idType) {
		IdType[] its = IdType.values();
		for (IdType it : its) {
			if (it.getKey() == idType) {
				return it;
			}
		}
		return UUID;
	}

	public int getKey() {
		return this.key;
	}

	public String getDesc() {
		return this.desc;
	}

}
