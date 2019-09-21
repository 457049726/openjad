package com.openjad.logger.log4j2;

import java.net.URI;
import java.net.URL;

import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.xml.XmlConfigurationFactory;
import org.apache.logging.log4j.core.util.Loader;
import static com.openjad.logger.log4j2.Log4j2Constants.*;

/**
 * 
 * 
 *  @author hechuan
 *
 */
public class JadXmlConfigurationFactory extends XmlConfigurationFactory {

	@Override
	public Configuration getConfiguration(final LoggerContext loggerContext, final String name, final URI configLocation) {
		if (configLocation == null) {
			try {
				return super.getConfiguration(loggerContext, name, getResourceUri());
			} catch (Exception e) {
				System.err.println("无法加载openjad-log4j2.xml," + e.getMessage());
				return super.getConfiguration(loggerContext, name, configLocation);
			}
		} else {
			return super.getConfiguration(loggerContext, name, configLocation);
		}

	}

	private URI getResourceUri() throws Exception {
		String resource = DEFAULT_LOG4J_FILE;
		URI uri = gerResourceUri(resource);
		if (uri == null) {
			resource = OPENJAD_LOG4J_FILE;
			uri = gerResourceUri(resource);
		}
		return uri;
	}

	private URI gerResourceUri(String resource) throws Exception {
		URL url = Loader.getResource(resource, JadXmlConfigurationFactory.class.getClassLoader());
		if (url != null) {
			return url.toURI();
		}
		return null;
	}

}
