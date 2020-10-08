package com.openjad.orm.mybatis.entity;

import java.io.Serializable;

/**
 * 
 * 对像元信息
 * 
 */
@SuppressWarnings("serial")
public class ObjectMetaInfo implements Serializable{
	
	/**
	 * 对象class
	 */
	protected transient Class metaClass;
	
	/**
	 * 对像名称
	 */
	private String objName;
	
	public ObjectMetaInfo(Class metaClass){
		this.metaClass=metaClass;
		this.objName=metaClass.getName();
	}

	public Class getMetaClass() {
		return metaClass;
	}

	public void setMetaClass(Class metaClass) {
		this.metaClass = metaClass;
	}

	public String getObjName() {
		return objName;
	}

	public void setObjName(String objName) {
		this.objName = objName;
	}

	
	
	
	

}


