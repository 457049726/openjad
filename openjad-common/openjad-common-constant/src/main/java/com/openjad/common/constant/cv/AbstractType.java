package com.openjad.common.constant.cv;

/**
 * 
 *  @author hechuan
 *
 */
public abstract class AbstractType extends BaseCode implements Type {
//	protected AbstractType(String code, String value) {
//		this(TYPE_TYPE_FLAG,code,value);
//	}
	
	protected AbstractType(String typeFlag, String code, String value) {
		super(typeFlag,code,value);
	}
	
//	public static <T extends BaseCode>T valueOf(String code){
//		return valueOf(TYPE_TYPE_FLAG,code);
//	}
}
