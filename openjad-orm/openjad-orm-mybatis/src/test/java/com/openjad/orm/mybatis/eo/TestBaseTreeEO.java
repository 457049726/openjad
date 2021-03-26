package com.openjad.orm.mybatis.eo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.openjad.orm.annotation.DelStrategy;
import com.openjad.orm.vo.JadBaseBizTreeEO;


@Entity
@Table(name = "test_base_tree")
@DelStrategy(DelStrategy.LOGIC_DELETE)
public class TestBaseTreeEO extends JadBaseBizTreeEO<Long,TestBaseTreeEO> {
//	
	private static final long serialVersionUID = 1L;
	
//	@UpdateConf(value = false)
	private String name;	
	
	private Integer age;
	
//	@UpdateConf(forceUpdateNull = true)
	private Date columnDate;	
	
	private String bitText;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Date getColumnDate() {
		return columnDate;
	}

	public void setColumnDate(Date columnDate) {
		this.columnDate = columnDate;
	}

	public String getBitText() {
		return bitText;
	}

	public void setBitText(String bitText) {
		this.bitText = bitText;
	}
	

	
	
}
