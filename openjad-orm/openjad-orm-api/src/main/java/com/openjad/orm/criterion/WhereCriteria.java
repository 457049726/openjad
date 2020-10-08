package com.openjad.orm.criterion;

import java.util.ArrayList;
import java.util.List;

/**
 * where条件集
 *  @author hechuan
 */
public class WhereCriteria {
	
	/**
	 * 条件集列表
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * 支持以对象做参数，请求格式为 :
	 * where t1.col_name=#{objParam.colName}
	 */
	private Object objParam;
	
	public WhereCriteria(){
		oredCriteria = new ArrayList<Criteria>();
	}
	
	public WhereCriteria addCriteria(Criteria criteria){
		oredCriteria.add(criteria);
		return this;
	}
	


	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	public void setOredCriteria(List<Criteria> oredCriteria) {
		this.oredCriteria = oredCriteria;
	}

	public Object getObjParam() {
		return objParam;
	}

	public void setObjParam(Object objParam) {
		this.objParam = objParam;
	}
}
