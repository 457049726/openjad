package com.openjad.orm.mybatis.parse;

import java.lang.reflect.Field;

import com.openjad.orm.mybatis.entity.EoFieldInfo;

/**
 * 实体字段解析
 * @author hechuan
 * 
 */
public interface EoFieldParser  {
	
	/**
	 * 字段解析
	 * @param entityFieldInfo 实体信息
	 * @param field 字段
	 * @return 是否解析
	 */
	 boolean parseEoField(EoFieldInfo entityFieldInfo,Field field);
	
}






