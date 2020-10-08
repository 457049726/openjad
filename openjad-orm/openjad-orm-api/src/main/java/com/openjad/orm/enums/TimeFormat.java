package com.openjad.orm.enums;

/**
 * 时间类型
 * @author hechuan
 *
 */
public enum TimeFormat {
	
	DEFAULT_FORMAT("yyyy-MM-dd HH:mm:ss", "默认"), 
	API_DEFAULT_FORMAT("yyyyMMddHHmmss", "用于接口api调用的默认类型"), 
	
	DATE_FORMAT("yyyy-MM-dd", "日期类型"),
	API_DATE_FORMAT("yyyyMMdd", "用于接口api调用的日期类型"),
	
	TIME_FORMAT("HH:mm:ss", "时间类型"),
	API_TIME_FORMAT("HHmmss", "用于接口api调用的时间类型"),
	
	MONTH_FORMAT("yyyy-MM", "月份类型"),
	API_MONTH_FORMAT("yyyyMM", "用于接口api调用的日期类型");
	
	private final String type;

	private final String desc;

	TimeFormat(final String type, final String desc) {
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



