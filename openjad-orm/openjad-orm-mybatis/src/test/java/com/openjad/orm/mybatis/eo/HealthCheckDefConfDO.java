package com.openjad.orm.mybatis.eo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="health_check_def_conf")
public class HealthCheckDefConfDO implements Serializable {


	@Id
	private Long idTestPri; // id

	private String checkType; // 检查类型。appHealth应用健康检查，echoTest接口回声测试jvmMonitor监控

	private String checkItems; // 检查条目。此字段目前只对接口回声测试和jvm监控有效，指定量回声测试具体接口或jvm中具体细项

	private Integer rate; // 检查频率。以秒为单位

	private String warnThreshold; // 预警阈值。触发预警的阈值

	private String thresholdPeriod; // 阈值时间窗口。有效阈值的时间段

	private String state; // 状态。0禁用，1启用

	private Integer timeout; // 超时时间，以毫秒为单位

	public HealthCheckDefConfDO() {
		super();
	}


	public Long getIdTestPri() {
		return idTestPri;
	}


	public void setIdTestPri(Long idTestPri) {
		this.idTestPri = idTestPri;
	}


	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

	public String getCheckItems() {
		return checkItems;
	}

	public void setCheckItems(String checkItems) {
		this.checkItems = checkItems;
	}

	public Integer getRate() {
		return rate;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
	}

	public String getWarnThreshold() {
		return warnThreshold;
	}

	public void setWarnThreshold(String warnThreshold) {
		this.warnThreshold = warnThreshold;
	}

	public String getThresholdPeriod() {
		return thresholdPeriod;
	}

	public void setThresholdPeriod(String thresholdPeriod) {
		this.thresholdPeriod = thresholdPeriod;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

}
