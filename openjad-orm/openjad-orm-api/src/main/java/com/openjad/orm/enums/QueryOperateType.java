package com.openjad.orm.enums;

/**
 * <p>
 * 条件查询操作类型枚举类
 * </p>
 * 
 * @author hubin
 */
public enum QueryOperateType {
	eq("eq","Eq", "=","等同于 field=value"), 
	gt("gt","BeginGt",">", "等同于 field>value"),
	ge("ge","Begin", ">=","等同于 field>=value"),
	lt("lt","EndLt", "<","等同于field<value表达式"),
	le("le","End", "<=","等同于field<=value表达式"),
	
	like("like","Like","like", "等同于field like '%value表达式%'"),
	likeLeft("likeLeft","LikeLeft","like", "等同于field like '%value表达式'"),
	likeRight("likeRight","LikeRight","like", "等同于field like 'value表达式%'"),
	
	notLike("notLike","NotLike", "not like","等同于field not like '%value表达式%'"),
	notLikeLeft("notLikeLeft","NotLikeLeft", "not like","等同于field not like '%value表达式'"),
	notLikeRight("notLikeRight","NotLikeRight","not like", "等同于field not like 'value表达式%'"),
	
	isNull("isNull","IsNull", "is null","等同于field is null"),
	isNotNull("isNotNull","IsNotNull","is not null", "等同于field is not null"),
	
	in("in","In","in", "等同于field in (values)"),
	notIn("notIn","NotIn", "not in","等同于field not in (values)"),
	
	between("between","Between","between", "等同于field between (value1,value2)"),
	;
	
	public static boolean isSimpleSingleOperate(String type){
		return eq.getType().equals(type) || gt.getType().equals(type) 
				|| ge.getType().equals(type) || lt.getType().equals(type) 
				|| le.getType().equals(type) ;
	}
	
	private final String type;
	
	private final String suffix;
	
	private final String desc;
	
	private final String expression;

	QueryOperateType(final String type,final String suffix ,final String expression, final String desc) {
		this.type = type;
		this.expression = expression;
		this.suffix = suffix;
		this.desc = desc;
	}

//	/**
//	 * <p>
//	 * 主键策略 （默认 ID_WORKER）
//	 * </p>
//	 * 
//	 * @param idType
//	 *            ID 策略类型
//	 * @return
//	 */
//	public static WhereOperateType getIdType(int idType) {
//		WhereOperateType[] its = WhereOperateType.values();
//		for (WhereOperateType it : its) {
//			if (it.getKey() == idType) {
//				return it;
//			}
//		}
//		return EQ;
//	}


	public String getDesc() {
		return this.desc;
	}

	public String getType() {
		return type;
	}

	public String getSuffix() {
		return suffix;
	}

	public String getExpression() {
		return expression;
	}

}
