package com.openjad.orm.mybatis.parse.impl;

import java.lang.reflect.Field;

import com.openjad.orm.mybatis.entity.EoFieldInfo;
import com.openjad.orm.annotation.UpdateConf;

/**
 * UpdateConf 注解解析器
 * 
 *  @author hechuan
 *
 */
public class UpdateConfParser extends AbstractEoFieldAnnotationParser<UpdateConf>{

	@Override
	public  boolean parseAnnotationEoField(EoFieldInfo eoFieldInfo, Field field, UpdateConf annotation) {
		eoFieldInfo.setForceUpdateNull(annotation.forceUpdateNull());
		eoFieldInfo.setUpdatable(annotation.value());
		return true;
	}

}
