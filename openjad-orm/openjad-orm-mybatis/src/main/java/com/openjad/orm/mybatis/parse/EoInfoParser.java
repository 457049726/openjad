package com.openjad.orm.mybatis.parse;

import com.openjad.orm.mybatis.entity.EoMetaInfo;

/**
 * 实体信息解析
 * @author hechuan
 *	
 */
public interface EoInfoParser {

	/**
	 * 解析实体信息
	 * @param clazz 实体类
	 * @return 实体信息
	 */
	public  EoMetaInfo parseEo(Class clazz);
	
}
