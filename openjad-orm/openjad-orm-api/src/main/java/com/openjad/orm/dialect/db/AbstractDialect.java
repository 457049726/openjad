package com.openjad.orm.dialect.db;

import java.util.HashMap;
import java.util.Map;

import com.openjad.orm.dialect.Dialect;

/**
 * 方言抽象类
 * 
 *  @author hechuan
 *
 */
public abstract class AbstractDialect implements Dialect {
	
	/**
	 * java类型集
	 */
	private static Map<String,String>JAVA_TYPE_MAP=new HashMap<String,String>();
	
	/**
	 * jdbc类型集
	 */
	private static Map<String,String>JDBC_TYPE_MAP=new HashMap<String,String>();
	
	static{
		
		/*mysql*/
		JAVA_TYPE_MAP.put("bit", "java.lang.String");
		JAVA_TYPE_MAP.put("bigint", "java.lang.Long");
		JAVA_TYPE_MAP.put("smallint", "java.lang.Integer");
		JAVA_TYPE_MAP.put("int", "java.lang.Integer");
		JAVA_TYPE_MAP.put("tinyint", "java.lang.Integer");
		JAVA_TYPE_MAP.put("integer", "java.lang.Integer");
		JAVA_TYPE_MAP.put("char", "java.lang.String");
		JAVA_TYPE_MAP.put("float", "java.lang.Double");
		JAVA_TYPE_MAP.put("double", "java.lang.Double");
		JAVA_TYPE_MAP.put("date", "java.util.Date");
		JAVA_TYPE_MAP.put("time", "java.util.Date");
		JAVA_TYPE_MAP.put("datetime", "java.util.Date");
		JAVA_TYPE_MAP.put("timestamp", "java.util.Date");
		JAVA_TYPE_MAP.put("longblob", "java.lang.Long");
		JAVA_TYPE_MAP.put("mediumblob", "java.lang.String");
		JAVA_TYPE_MAP.put("blob", "java.lang.String");
		JAVA_TYPE_MAP.put("tinyblob", "java.lang.String");
		JAVA_TYPE_MAP.put("binary", "java.lang.String");
		JAVA_TYPE_MAP.put("decimal", "java.lang.Double");
		JAVA_TYPE_MAP.put("longtext", "java.lang.String");
		JAVA_TYPE_MAP.put("mediumtext", "java.lang.String");
		JAVA_TYPE_MAP.put("text", "java.lang.String");
		JAVA_TYPE_MAP.put("varchar", "java.lang.String");
		JAVA_TYPE_MAP.put("boolean", "java.lang.Boolean");
		JAVA_TYPE_MAP.put("numeric", "java.lang.Double");
		JAVA_TYPE_MAP.put("real", "java.lang.String");
		JAVA_TYPE_MAP.put("blob", "java.lang.String");
		JAVA_TYPE_MAP.put("varchar", "java.lang.String");
		JAVA_TYPE_MAP.put("clob", "java.lang.String");
		JAVA_TYPE_MAP.put("nchar", "java.lang.String");
		JAVA_TYPE_MAP.put("nvarchar", "java.lang.String");
		JAVA_TYPE_MAP.put("nclob", "java.lang.String");
		
		/*oracle*/
		JAVA_TYPE_MAP.put("DATE", "java.util.Date");
		JAVA_TYPE_MAP.put("VARCHAR2", "java.lang.String");
		JAVA_TYPE_MAP.put("NUMBER", "java.lang.Long");
		JAVA_TYPE_MAP.put("CHAR", "java.lang.String");
		
		//------------jdbctype
		/*mysql*/
		JDBC_TYPE_MAP.put("java.lang.String", "VARCHAR");
		JDBC_TYPE_MAP.put("java.util.Date", "TIMESTAMP");
		JDBC_TYPE_MAP.put("java.lang.Byte", "INTEGER");
		JDBC_TYPE_MAP.put("java.lang.Short", "INTEGER");
		JDBC_TYPE_MAP.put("java.lang.Integer", "INTEGER");
		JDBC_TYPE_MAP.put("java.lang.Long", "BIGINT");
		JDBC_TYPE_MAP.put("java.lang.Float", "DOUBLE");
		JDBC_TYPE_MAP.put("java.lang.Double", "DOUBLE");
		JDBC_TYPE_MAP.put("java.lang.Character", "VARCHAR");
		JDBC_TYPE_MAP.put("java.lang.Boolean", "BOOLEAN");
		
		JDBC_TYPE_MAP.put("byte", "INTEGER");
		JDBC_TYPE_MAP.put("short", "INTEGER");
		JDBC_TYPE_MAP.put("int", "INTEGER");
		JDBC_TYPE_MAP.put("long", "BIGINT");
		JDBC_TYPE_MAP.put("float", "DOUBLE");
		JDBC_TYPE_MAP.put("double", "DOUBLE");
		JDBC_TYPE_MAP.put("char", "VARCHAR");
		JDBC_TYPE_MAP.put("boolean", "BOOLEAN");
		
		JDBC_TYPE_MAP.put("timestamp", "TIMESTAMP");
		
		/*oracle*/
//		JDBC_TYPE_MAP.put("DATE", "DATE");
		JDBC_TYPE_MAP.put("VARCHAR", "VARCHAR");
		JDBC_TYPE_MAP.put("NUMBER", "java.lang.Integer");
		JDBC_TYPE_MAP.put("CHAR", "VARCHAR");
		
	}
	
	
	
	public static String getType(String type) {
		if(type!=null && type.length()>0 && JAVA_TYPE_MAP.containsKey(type.trim()) ){
			return JAVA_TYPE_MAP.get(type.trim());
		}
		return type;
	}
	
	public static String getJdbcType(String jdbcType) {
		if(jdbcType!=null && jdbcType.length()>0 && JDBC_TYPE_MAP.containsKey(jdbcType.trim()) ){
			return JDBC_TYPE_MAP.get(jdbcType.trim());
		}
		return jdbcType;
	}
	


}
