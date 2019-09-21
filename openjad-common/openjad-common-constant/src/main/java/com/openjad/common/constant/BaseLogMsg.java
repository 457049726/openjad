package com.openjad.common.constant;

import static com.openjad.common.constant.PropertiyConstants.FRAMEWORK_DEFAUTL_SITE_LOG_URL;
import static com.openjad.common.constant.PropertiyConstants.FRAMEWORK_SITE_LOG_URL_CONF_KEY;

import com.openjad.common.constant.cv.AbstractCode;


/**
 * 
 *  @Title BaseEncodedLogMsg
 *  @author hechuan
 *  @date 2019年9月20日
 *
 *  <功能简述>
 */
public class BaseLogMsg extends AbstractCode {

	private boolean urlForMore;
	
	protected BaseLogMsg(String logType,String code, String value, boolean urlForMore) {
		super(logType,code, value);
		this.urlForMore = urlForMore;
	}

	
	public boolean isUrlForMore() {
		return urlForMore;
	}



	private static String getFrameworkSiteLogUrl() {
		String url = System.getProperty(FRAMEWORK_SITE_LOG_URL_CONF_KEY);
		if (url==null || "".equals(url)) {
			url = FRAMEWORK_DEFAUTL_SITE_LOG_URL;
		}
		return url;
	}
	
	public String getUrlForMore() {
		String url = getFrameworkSiteLogUrl();
		url = url + "/" + getTypeFlag() + "/" + getCode();
		return url;
	}

	public String getMsgForMore() {
		StringBuffer sb = new StringBuffer();
		sb.append("更多日志信息请访问:");
		sb.append(getUrlForMore());
		return sb.toString();
	}

	

}
