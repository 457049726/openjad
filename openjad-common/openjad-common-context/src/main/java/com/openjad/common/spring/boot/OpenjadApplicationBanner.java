package com.openjad.common.spring.boot;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.ResourceBanner;
import org.springframework.core.io.Resource;

import com.openjad.common.util.VersionUtil;

/**
 * 自定 banner
 * @author hechuan
 *
 */
public class OpenjadApplicationBanner extends ResourceBanner{

	public OpenjadApplicationBanner(Resource resource) {
		super(resource);
	}
	
	@Override
	protected String getApplicationVersion(Class<?> sourceClass) {
		String version = null;
		if(StringUtils.isBlank(version)){
			version = VersionUtil.getVersion();
		}
		return version;
	}

}
