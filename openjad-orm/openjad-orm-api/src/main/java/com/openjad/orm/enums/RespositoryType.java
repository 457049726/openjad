package com.openjad.orm.enums;

/**
 *  待久化类型
 * 
 *  @author hechuan
 *
 */
public enum RespositoryType {
	/**
	 * mybatis
	 */
	MYBATIS("mybatis", "mybatis"),
	/**
	 * jpa
	 */
	JPA("jpa", "jpa"),
	/**
	 * hibernate
	 */
	HIBERNATE("hibernate", "hibernate");

	private final String respositoryType;

	private final String desc;

	RespositoryType(final String respositoryType, final String desc) {
		this.respositoryType = respositoryType;
		this.desc = desc;
	}

	/**
	 * <p>
	 * 获取待化类型
	 * </p>
	 * 
	 * @param respositoryType 数据库类型字符串
	 * @return res
	 */
	public static RespositoryType getRespositoryType(String respositoryType) {
		RespositoryType[] dts = RespositoryType.values();
		for (RespositoryType dt : dts) {
			if (dt.getRespositoryType().equalsIgnoreCase(respositoryType)) {
				return dt;
			}
		}
		return HIBERNATE;
	}

	public String getRespositoryType() {
		return this.respositoryType;
	}

	public String getDesc() {
		return this.desc;
	}

}
