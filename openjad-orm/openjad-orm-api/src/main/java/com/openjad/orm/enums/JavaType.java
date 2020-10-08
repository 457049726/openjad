package com.openjad.orm.enums;

/**
 *  java 常用数据类型
 * 
 */
public enum JavaType {
	
	Object("java.lang.Object", "超类"),
	
	String("java.lang.String", "字符串类型"),
	
	booleanType("boolean", "boolean"),
	Boolean("java.lang.Boolean", "Boolean"),
	
	charType("char", "char"),
	Character("java.lang.Character", "Character"),
	
	byteType("byte", "byte"),
	Byte("java.lang.Byte", "Byte"),
	
	shortType("short", "short"),
	Short("java.lang.Short", "Short"),
	
	intType("int", "int"),
	Integer("java.lang.Integer", "Integer"),
	
	longType("long", "long"),
	Long("java.lang.Long", "Integer"),
	
	floatType("float", "float"),
	Float("java.lang.Float", "Integer"),
	
	doubleType("double", "double"),
	Double("java.lang.Double", "Double"),
	
	utilDate("java.util.Date", "java.util.Date"),
	Calendar("java.util.Calendar", "java.util.Calendar"),
	sqlDate("java.sql.Date", "java.sql.Date"),
	sqlTime("java.sql.Time", "java.sql.Time"),
	Timestamp("java.sql.Timestamp", "java.sql.Timestamp"),
	
	BigDecimal("java.math.BigDecimal", "java.math.BigDecimal"),
	BigInteger("java.math.BigInteger", "java.math.BigInteger"),
	
	Clob("java.sql.Clob", "java.sql.Clob"),
	Blob("java.sql.Blob", "java.sql.Blob"),
	
	
	;

	private final String type;

	private final String desc;

	JavaType(final String type, final String desc) {
		this.type = type;
		this.desc = desc;
	}

	/**
	 * javaType
	 * @param javaType javaType
	 * @return res
	 */
	public static JavaType getJavaType(String javaType) {
		JavaType[] dts = JavaType.values();
		for (JavaType dt : dts) {
			if (dt.getType().equalsIgnoreCase(javaType)) {
				return dt;
			}
		}
		return Object;
	}
	
	
//	utilDate("java.util.Date", "java.util.Date"),
//	Calendar("java.util.Calendar", "java.util.Calendar"),
//	sqlDate("java.sql.Date", "java.sql.Date"),
//	sqlTime("java.sql.Time", "java.sql.Time"),
//	TimestampType("java.sql.Timestamp", "java.sql.Timestamp"),
	
	public static boolean isDateOrTime(String javaType){
		return JavaType.utilDate.getType().equalsIgnoreCase(javaType) 
				|| JavaType.Calendar.getType().equalsIgnoreCase(javaType)
				|| JavaType.sqlDate.getType().equalsIgnoreCase(javaType)
				|| JavaType.sqlTime.getType().equalsIgnoreCase(javaType)
				|| JavaType.Timestamp.getType().equalsIgnoreCase(javaType);
	}
	
	
	public static boolean isFloat(String javaType){
		return JavaType.Float.getType().equalsIgnoreCase(javaType) 
				|| JavaType.floatType.getType().equalsIgnoreCase(javaType);
	}
	
	public static boolean isDouble(String javaType){
		return JavaType.Double.getType().equalsIgnoreCase(javaType) 
				|| JavaType.doubleType.getType().equalsIgnoreCase(javaType);
	}
	
	
	public static boolean isLong(String javaType){
		return JavaType.Long.getType().equalsIgnoreCase(javaType) 
				|| JavaType.longType.getType().equalsIgnoreCase(javaType);
	}
	
	
	public static boolean isInt(String javaType){
		return JavaType.Integer.getType().equalsIgnoreCase(javaType) 
				|| JavaType.intType.getType().equalsIgnoreCase(javaType);
	}
	public static boolean isShort(String javaType){
		return JavaType.Short.getType().equalsIgnoreCase(javaType) 
				|| JavaType.shortType.getType().equalsIgnoreCase(javaType);
	}
	
	public static boolean isByte(String javaType){
		return JavaType.byteType.getType().equalsIgnoreCase(javaType) 
				|| JavaType.Byte.getType().equalsIgnoreCase(javaType);
	}
	
	public static boolean isChar(String javaType){
		return JavaType.charType.getType().equalsIgnoreCase(javaType) 
				|| JavaType.Character.getType().equalsIgnoreCase(javaType);
	}
	
	public static boolean isBool(String javaType){
		return JavaType.booleanType.getType().equalsIgnoreCase(javaType) 
				|| JavaType.Boolean.getType().equalsIgnoreCase(javaType);
	}
	
	public static boolean isBaseType(String javaType){
		return !JavaType.Object.getType().equals(JavaType.getJavaType(javaType).getType());
	}

	public String getType() {
		return this.type;
	}

	public String getDesc() {
		return this.desc;
	}

}
