package com.openjad.orm.mybatis.eo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAttribute;


@Entity
@Table(name = "sys_dict")
public class TestDictEo {

	private static final long serialVersionUID = 1L;
	
	private String id;
	
	
	
	private String value;	// 数据值
	
	private String label;	// 标签名
	
	private String type;	// 类型
	
	private String description;// 描述
	
	private Integer sort;	// 排序
	
	private String parentId;//父Id

	public TestDictEo() {
		super();
	}
	
	
	public TestDictEo(String value, String label){
		this.value = value;
		this.label = label;
	}
	
	
	@Id
	@Column(name="id",updatable=false)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name="value")
	@XmlAttribute
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Column(name="label")
	@XmlAttribute
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Column(name="type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name="description")
	@XmlAttribute
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name="sort")
	@OrderBy("sort asc,create_Date desc ")
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Column(name="parent_id")
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	
}
