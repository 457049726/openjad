package com.openjad.orm.criterion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 多条件集
 * 
 * @author hechuan
 */
public class Criteria {

	/**
	 * 默认是否忽略null
	 */
	private static final boolean DEF_IGNORE_NULL = true;

	/**
	 * 条件列表
	 */
	protected List<Criterion> criteria;

	public Criteria() {
		super();
		criteria = new ArrayList<Criterion>();
	}

	public boolean isValid() {
		return criteria.size() > 0;
	}

	public List<Criterion> getAllCriteria() {
		return criteria;
	}

	public List<Criterion> getCriteria() {
		return criteria;
	}

	public Criteria addCriterion(String condition) {
		if (condition == null) {
			throw new RuntimeException("condition不能为空");
		}
		criteria.add(new Criterion(condition));
		return this;
	}

	public Criteria addCriterion(String condition, Object value, String column, boolean ignoreNull) {
		if (condition == null) {
			throw new RuntimeException("condition不能为空");
		}

		//忽略空值
		if (value == null && ignoreNull) {
			return this;
		}
		criteria.add(new Criterion(condition, value));
		return this;
	}

	public Criteria addCriterion(String condition, Object value1, Object value2, String column) {
		if (value1 == null || value2 == null) {
			throw new RuntimeException("列" + column + "区间值不能为空 ");
		}
		criteria.add(new Criterion(condition, value1, value2));
		return this;
	}

	public Criteria andIsNull(String column) {
		addCriterion(column + " is null");
		return (Criteria) this;
	}

	public Criteria andIsNotNull(String column) {
		addCriterion(column + " is not null");
		return (Criteria) this;
	}

	public Criteria andEqualTo(Object value, String column, boolean ignoreNull) {
		addCriterion(column + " =", value, column, ignoreNull);
		return (Criteria) this;
	}

	public Criteria andNotEqualTo(Object value, String column, boolean ignoreNull) {
		addCriterion(column + " <>", value, column, ignoreNull);
		return (Criteria) this;
	}

	public Criteria andGreaterThan(Object value, String column, boolean ignoreNull) {
		addCriterion(column + " >", value, column, ignoreNull);
		return (Criteria) this;
	}

	public Criteria andGreaterThanOrEqualTo(Object value, String column, boolean ignoreNull) {
		addCriterion(column + " >=", value, column, ignoreNull);
		return (Criteria) this;
	}

	public Criteria andLessThan(Object value, String column, boolean ignoreNull) {
		addCriterion(column + " <", value, column, ignoreNull);
		return (Criteria) this;
	}

	public Criteria andLessThanOrEqualTo(Object value, String column, boolean ignoreNull) {
		addCriterion(column + " <=", value, column, ignoreNull);
		return (Criteria) this;
	}

	public Criteria andIn(List<Object> values, String column, boolean ignoreNull) {
		addCriterion(column + " in", values, column, ignoreNull);
		return (Criteria) this;
	}

	public Criteria andNotIn(List<Object> values, String column, boolean ignoreNull) {
		addCriterion(column + " not in", values, column, ignoreNull);
		return (Criteria) this;
	}

	public Criteria andEqualTo(Object value, String column) {
		addCriterion(column + " =", value, column, DEF_IGNORE_NULL);
		return (Criteria) this;
	}

	public Criteria andNotEqualTo(Object value, String column) {
		addCriterion(column + " <>", value, column, DEF_IGNORE_NULL);
		return (Criteria) this;
	}

	public Criteria andGreaterThan(Object value, String column) {
		addCriterion(column + " >", value, column, DEF_IGNORE_NULL);
		return (Criteria) this;
	}

	public Criteria andGreaterThanOrEqualTo(Object value, String column) {
		addCriterion(column + " >=", value, column, DEF_IGNORE_NULL);
		return (Criteria) this;
	}

	public Criteria andLessThan(Object value, String column) {
		addCriterion(column + " <", value, column, DEF_IGNORE_NULL);
		return (Criteria) this;
	}

	public Criteria andLessThanOrEqualTo(Object value, String column) {
		addCriterion(column + " <=", value, column, DEF_IGNORE_NULL);
		return (Criteria) this;
	}

	public Criteria andIn(List<?> values, String column) {
		addCriterion(column + " in", values, column, DEF_IGNORE_NULL);
		return (Criteria) this;
	}

	public Criteria andIn(Object[] values, String column) {
		return andIn(Arrays.asList(values), column);
	}

	public Criteria andNotIn(Object[] values, String column) {
		return andNotIn(Arrays.asList(values), column);
	}

	public Criteria andNotIn(List<Object> values, String column) {
		addCriterion(column + " not in", values, column, DEF_IGNORE_NULL);
		return (Criteria) this;
	}

	public Criteria andBetween(Object value1, Object value2, String column) {
		addCriterion(column + " between", value1, value2, column);
		return (Criteria) this;
	}

	public Criteria andNotBetween(Object value1, Object value2, String column) {
		addCriterion(column + " not between", value1, value2, column);
		return (Criteria) this;
	}

	public Criteria andLeftLike(String value, String column) {
		if (value != null) {
			addCriterion(column + " like '" + value + "%'");
		}
		
		return (Criteria) this;
	}

	public Criteria andRightLike(String value, String column) {
		if (value != null) {
			addCriterion(column + " like '%" + value + "'");
		}
		
		return (Criteria) this;
	}

	public Criteria andLike(String value, String column) {
		if (value != null) {
			addCriterion(column + " like '%" + value + "%'");
		}
		
		return (Criteria) this;
	}

	public Criteria andNotLike(String value, String column) {
		if (value != null) {
			addCriterion(column + " not like '%" + value + "%'");
		}
		
		return (Criteria) this;
	}

}
