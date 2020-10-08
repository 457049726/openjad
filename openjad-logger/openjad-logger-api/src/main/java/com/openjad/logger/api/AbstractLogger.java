package com.openjad.logger.api;

import static com.openjad.common.constant.CharacterConstants.LEFT_SLASH_CHARACTER;
import static com.openjad.common.constant.ExtensionConstants.HTML_EXTENSIONS;
import static com.openjad.common.constant.PropertiyConstants.FRAMEWORK_DEFAUTL_SITE_LOG_URL;
import static com.openjad.common.constant.PropertiyConstants.FRAMEWORK_SITE_LOG_URL_CONF_KEY;

import com.openjad.common.constant.BaseLogCode;

/**
 * 
 * @author hechuan
 *
 */
public abstract class AbstractLogger implements Logger {

	public static String FRAMEWORK_SITE_LOG_URL;

	private static String getFrameworkSiteLogUrl() {
		if (FRAMEWORK_SITE_LOG_URL != null) {
			return FRAMEWORK_SITE_LOG_URL;
		}
		FRAMEWORK_SITE_LOG_URL = System.getProperty(FRAMEWORK_SITE_LOG_URL_CONF_KEY);
		if (FRAMEWORK_SITE_LOG_URL == null || "".equals(FRAMEWORK_SITE_LOG_URL)) {
			FRAMEWORK_SITE_LOG_URL = FRAMEWORK_DEFAUTL_SITE_LOG_URL;
		}
		return FRAMEWORK_SITE_LOG_URL;
	}

	public String getUrlForMore(BaseLogCode logMsg) {
		StringBuffer sb = new StringBuffer();
		sb.append(getFrameworkSiteLogUrl());
		sb.append(LEFT_SLASH_CHARACTER);
		sb.append(logMsg.getTypeFlag());
		sb.append(LEFT_SLASH_CHARACTER);
		sb.append(logMsg.getCode());
		sb.append(HTML_EXTENSIONS);
		return sb.toString();
	}

	

}
