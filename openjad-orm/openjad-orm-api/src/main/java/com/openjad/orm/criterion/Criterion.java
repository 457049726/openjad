package com.openjad.orm.criterion;

import java.util.List;

/**
 * 单个条件
 *  @author hechuan
 */
public class Criterion {
	
	/**
	 * 条件字段
	 */
	private String condition;
 
	/**
	 * 条件值
	 */
    private Object value;

    /**
	 * 第二个条件值
	 */
    private Object secondValue;

    /**
	 * 是否为无值条件
	 */
    private boolean noValue;

    /**
	 * 是否为单个值
	 */
    private boolean singleValue;

    /**
	 * 是否为between值
	 */
    private boolean betweenValue;

    /**
	 * 列表值
	 */
    private boolean listValue;

    /**
	 * 类型处理器
	 */
    private String typeHandler;

    public String getCondition() {
        return condition;
    }

    public Object getValue() {
        return value;
    }

    public Object getSecondValue() {
        return secondValue;
    }

    public boolean isNoValue() {
        return noValue;
    }

    public boolean isSingleValue() {
        return singleValue;
    }

    public boolean isBetweenValue() {
        return betweenValue;
    }

    public boolean isListValue() {
        return listValue;
    }

    public String getTypeHandler() {
        return typeHandler;
    }

    protected Criterion(String condition) {
        super();
        this.condition = condition;
        this.typeHandler = null;
        this.noValue = true;
    }

    protected Criterion(String condition, Object value, String typeHandler) {
        super();
        this.condition = condition;
        this.value = value;
        this.typeHandler = typeHandler;
        if (value instanceof List<?>) {
            this.listValue = true;
        } else {
            this.singleValue = true;
        }
    }

    protected Criterion(String condition, Object value) {
        this(condition, value, null);
    }

    protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
        super();
        this.condition = condition;
        this.value = value;
        this.secondValue = secondValue;
        this.typeHandler = typeHandler;
        this.betweenValue = true;
    }

    protected Criterion(String condition, Object value, Object secondValue) {
        this(condition, value, secondValue, null);
    }
}
