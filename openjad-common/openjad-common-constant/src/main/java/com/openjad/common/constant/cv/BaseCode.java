package com.openjad.common.constant.cv;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 
 * @Title BaseCode
 * @author hechuan
 * @date 2019年9月20日
 *
 *       <功能简述>
 */
public abstract class BaseCode {

	private CodeValuePair codeValue = new CodeValuePair();

	protected static final String TYPE_TYPE_FLAG = "type";
	protected static final String STATE_TYPE_FLAG = "state";
	protected static final String CODE_TYPE_FLAG = "code";

	public static Map<String, Map<String, BaseCode>> ALL = new TreeMap<String, Map<String, BaseCode>>();

	protected BaseCode(String typeFlag, String code, String value) {
		if (typeFlag == null || "".equals(typeFlag)) {
			throw new IllegalArgumentException("typeFlag不能为空");
		}
		if (code == null || "".equals(code)) {
			throw new IllegalArgumentException("code不能为空");
		}
		if (value == null || "".equals(value)) {
			throw new IllegalArgumentException("value不能为空");
		}

		this.codeValue.setTypeFlag(typeFlag);
		this.codeValue.setCode(code);
		this.codeValue.setValue(value);

		synchronized (ALL) {
			Map<String, BaseCode> map = ALL.get(typeFlag);
			if (map == null) {
				ALL.put(typeFlag, new HashMap<String, BaseCode>());
			}
			BaseCode old = ALL.get(typeFlag).put(code, this);
			if (old != null) {
				StringBuffer sb = new StringBuffer();
				sb.append("代码值重复,[").append(typeFlag)
						.append("]类型中,至少存在两个相同代码[").append(code)
						.append("]的值:[").append(old.getValue())
						.append("]和[").append(value)
						.append("]");
				throw new RuntimeException(sb.toString());
			}

		}

	}

	@SuppressWarnings("unchecked")
	public static <T extends BaseCode> List<T> getOrderedList(String typeFlag) {
		if (typeFlag == null || "".equals(typeFlag)) {
			throw new IllegalArgumentException("typeFlag不能为空");
		}
		if (ALL.get(typeFlag) == null) {
			return Collections.emptyList();
		}

		List<T> orderedList = Collections.emptyList();
		for(BaseCode code:ALL.get(typeFlag).values()){
			orderedList.add((T)code);
		}

		Collections.sort(orderedList, new Comparator<T>() {
			@Override
			public int compare(T o1, T o2) {
				BaseCode type1 = (BaseCode) o1;
				BaseCode type2 = (BaseCode) o2;
				return type1.getValue().compareTo(type2.getValue());
			}
		});

		return orderedList;
	}

	public String getCode() {
		return this.codeValue.getCode();
	}

	public void setCode(String code) {
		this.codeValue.setCode(code);
	}

	public String getValue() {
		return this.codeValue.getValue();
	}

	public String getTypeFlag() {
		return this.codeValue.getTypeFlag();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (obj.getClass() != this.getClass()) {
			return false;
		}

		return this.codeValue.getValue().equals(((Type) obj).getValue());
	}

	@Override
	public int hashCode() {
		return this.codeValue.getValue().hashCode();
	}

	@Override
	public String toString() {
		return this.codeValue.toString();
	}
}
