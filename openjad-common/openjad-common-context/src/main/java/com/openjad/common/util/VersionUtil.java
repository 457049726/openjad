package com.openjad.common.util;

import java.net.URL;
import java.security.CodeSource;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import static com.openjad.common.constant.CharacterConstants.*;
import static com.openjad.common.constant.VersionConstants.*;
import static com.openjad.common.constant.ExtensionConstants.*;
import com.openjad.logger.api.Logger;
import com.openjad.logger.api.LoggerFactory;

/**
 * 版本工具类
 * 
 * @author hechuan
 *
 */
public class VersionUtil {

	private static final String VERSION;

	private static final Logger logger = LoggerFactory.getLogger(VersionUtil.class);
	

	static {
		VersionUtil.checkDuplicate(VersionUtil.class);
		VERSION = getVersion(VersionUtil.class, CURRENT_FRAMEWORK_VERSION);
	}

	private VersionUtil() {
	}

	public static String getVersion() {
		return VERSION;
	}

	public static String getVersion(Class<?> cls, String defaultVersion) {
		try {
			String version = cls.getPackage().getImplementationVersion();
			if (version == null || version.length() == 0) {
				version = cls.getPackage().getSpecificationVersion();
			}
			if (version == null || version.length() == 0) {
				CodeSource codeSource = cls.getProtectionDomain().getCodeSource();
				if (codeSource == null) {
					logger.info("找不到类 " + cls.getName() + " 返回默认的版本号 " + defaultVersion);
				} else {
					String file = codeSource.getLocation().getFile();
					if (file != null && file.length() > 0 && file.endsWith(JAR_EXTENSIONS)) {
						file = file.substring(0, file.length() - 4);
						int i = file.lastIndexOf(LEFT_SLASH_CHARACTER);
						if (i >= 0) {
							file = file.substring(i + 1);
						}
						i = file.indexOf(HYPHEN_CHARACTER);
						if (i >= 0) {
							file = file.substring(i + 1);
						}
						while (file.length() > 0 && !Character.isDigit(file.charAt(0))) {
							i = file.indexOf(HYPHEN_CHARACTER);
							if (i >= 0) {
								file = file.substring(i + 1);
							} else {
								break;
							}
						}
						version = file;
					}
				}
			}
			return version == null || version.length() == 0 ? defaultVersion : version;
		} catch (Throwable e) {
			logger.error("获取版本号失败，忽略异常，返回默认返本号:" + defaultVersion + ", " + e.getMessage(), e);
			return defaultVersion;
		}
	}

	public static void checkDuplicate(Class<?> cls, boolean failOnError) {
		checkDuplicate(cls.getName().replace(PERIOD_CHARACTER, LEFT_SLASH_CHARACTER) + CLASS_EXTENSIONS, failOnError);
	}

	public static void checkDuplicate(Class<?> cls) {
		checkDuplicate(cls, false);
	}

	public static void checkDuplicate(String path, boolean failOnError) {
		try {
			Enumeration<URL> urls = ClassLoader.getSystemClassLoader().getResources(path);
			Set<String> files = new HashSet<String>();
			while (urls.hasMoreElements()) {
				URL url = urls.nextElement();
				if (url != null) {
					String file = url.getFile();
					if (file != null && file.length() > 0) {
						files.add(file);
					}
				}
			}
			if (files.size() > 1) {
				String error = "重复加载类，jar包冲突, " + path + " ,重复数量: " + files.size() + " ,jar: " + files;
				if (failOnError) {
					throw new IllegalStateException(error);
				} else {
					logger.error(error);
				}
			}
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
		}
	}

}
