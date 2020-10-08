package com.openjad.orm.mybatis.parse.impl;

import java.lang.reflect.Field;

import com.openjad.orm.mybatis.entity.EoFieldInfo;
import com.openjad.orm.annotation.InsertConf;

/**
 * InsertConf 注解解析器
 * 
 *  @author hechuan
 *
 */
public class InsertConfParser extends AbstractEoFieldAnnotationParser<InsertConf>{

	@Override
	public  boolean parseAnnotationEoField(EoFieldInfo eoFieldInfo, Field field, InsertConf annotation) {
		eoFieldInfo.setInsertable(annotation.value());
		return true;
	}


}
