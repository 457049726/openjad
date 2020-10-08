package com.openjad.orm.enums;


/**
 * 关联对像加载方式
 * 
 *  @author hechuan
 *
 */
public enum RelateLoadMethod {

	LEFT_JOIN("leftJoin", "左连接"),
	DECLARE("declare", "迪卡尔"),
	NOT_AUTO("notAuto", "不自动加载");
	
	private final String type;

	private final String desc;
	
	RelateLoadMethod(final String type, final String desc) {
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



