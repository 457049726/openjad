package com.openjad.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.util.ObjectUtils;

import static com.openjad.common.constant.DefaultValueConstants.*;
import static com.openjad.common.constant.CharacterConstants.*;
import static com.openjad.common.constant.ExtensionConstants.*;
import static com.openjad.common.constant.DirConstants.*;
import static com.openjad.common.constant.PropertiyConstants.*;

/**
 * 系统初始化参数工具类
 * 
 * @author hechuan
 *
 */
public class SysInitArgUtils {

	/**
	 * 从系统属性中获取所有参数
	 * 
	 * @param toLowkey toLowkey
	 * @return 所有参数
	 */
	public static Map<String, String> getPropertiesFromSystem(boolean toLowkey) {
		Map<String, String> map = new HashMap<String, String>(DEF_COLLECT_CAPACITY);
		Properties properties = System.getProperties();

		for (Object key : properties.keySet()) {
			String val = properties.get(key) == null ? "" : properties.get(key).toString();
			map.put(toLowkey ? key.toString().toLowerCase() : key.toString(), val);
		}
		return map;
	}

	/**
	 * 从 spring 环境中获取所有参数
	 * 
	 * @param environment param
	 * @param toLowkey    toLowkey
	 * @return 所有参数
	 */
	public static Map<String, String> getPropertiesFromEnvironment(ConfigurableEnvironment environment, boolean toLowkey) {

		Map<String, String> map = new HashMap<String, String>(DEF_COLLECT_CAPACITY);

		Map<String, Object> properties = doExtraProperties(environment);
		for (Map.Entry<String, Object> entry : properties.entrySet()) {
			String key = entry.getKey();
			Object val = entry.getValue();
			val = val == null ? "" : val.toString();
			if (toLowkey) {
				key = key.toLowerCase();
			}
			map.put(key, val.toString());

		}
		return map;
	}

	@SuppressWarnings("rawtypes")
	private static Map<String, Object> doExtraProperties(ConfigurableEnvironment environment) {

		Map<String, Object> properties = new LinkedHashMap<>(DEF_COLLECT_CAPACITY);
		Map<String, PropertySource<?>> map = doGetPropertySources(environment);

		for (PropertySource<?> source : map.values()) {
			if (source instanceof EnumerablePropertySource) {
				EnumerablePropertySource propertySource = (EnumerablePropertySource) source;
				String[] propertyNames = propertySource.getPropertyNames();
				if (ObjectUtils.isEmpty(propertyNames)) {
					continue;
				}
				for (String propertyName : propertyNames) {
					if (!properties.containsKey(propertyName)) {
						properties.put(propertyName, propertySource.getProperty(propertyName));
					}
				}
			}

		}

		return properties;

	}

	private static Map<String, PropertySource<?>> doGetPropertySources(ConfigurableEnvironment environment) {
		Map<String, PropertySource<?>> map = new LinkedHashMap<String, PropertySource<?>>();
		MutablePropertySources sources = environment.getPropertySources();
		for (PropertySource<?> source : sources) {
			extract("", map, source);
		}
		return map;
	}

	private static void extract(String root, Map<String, PropertySource<?>> map,
			PropertySource<?> source) {
		if (source instanceof CompositePropertySource) {
			for (PropertySource<?> nest : ((CompositePropertySource) source)
					.getPropertySources()) {
				extract(source.getName() + ":", map, nest);
			}
		} else {
			map.put(root + source.getName(), source);
		}
	}

	/**
	 * 判断是否存在 Property
	 * 
	 * @param key key
	 * @return 结果
	 */
	public static boolean hasProperty(String key) {
		return StringUtils.isNotBlank(System.getProperty(key));
	}

	/**
	 * 获取项目名称
	 * 
	 * @return 结果
	 */
	public static String readProjectName() {
		String basePath = SysInitArgUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		try {
			basePath = URLDecoder.decode(basePath, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		basePath = basePath.replaceAll("/target/classes/", "");

		if (basePath.endsWith(JAR_EXTENSIONS)) {
			if (basePath.indexOf(HYPHEN_CHARACTER) > 0) {
				basePath = basePath.substring(basePath.lastIndexOf(LEFT_SLASH_CHARACTER) + 1, basePath.lastIndexOf(HYPHEN_CHARACTER));
			} else {
				basePath = basePath.substring(basePath.lastIndexOf(LEFT_SLASH_CHARACTER) + 1, basePath.lastIndexOf(DOT_CHARACTER));
			}
		} else {
			if (basePath.indexOf(LEFT_SLASH_CHARACTER) >= 0) {
				basePath = basePath.substring(basePath.lastIndexOf(LEFT_SLASH_CHARACTER) + 1);
			}
		}
		return basePath;
	}

	/**
	 * 获取程序启动 classPath
	 * 
	 * @return 结果
	 */
	public static String getClassPath() {
		String classPath = "";
		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			if (loader == null) {
				System.out.print("获取系统类加载器");
				loader = ClassLoader.getSystemClassLoader();
			}

			URL url = loader.getResource("");
			if (url != null) {
				classPath = url.getPath();
				classPath = URLDecoder.decode(classPath, "utf-8");
			}
			//".jar!"
			// 如果是jar包内的，则返回当前路径
			if (classPath == null || "".equals(classPath) || classPath.contains(JAR_EXTENSIONS + EXCLAMATION_CHARACTER)) {
				classPath = System.getProperty(USER_DOT_DIR);
			}
		} catch (Throwable ex) {
			classPath = System.getProperty(USER_DOT_DIR);
			System.err.println("无法读取到 class path, 返回 user.dir: " + classPath + "," + ex.getStackTrace());
		}
		if (classPath.endsWith(BIN_DIR)) {
			classPath = classPath.substring(0, classPath.length() - 4);
		}
		return classPath;
	}

	public static String getFrameworkSiteWebUrl() {
		String url = System.getProperty(FRAMEWORK_SITE_WEB_URL_CONF_KEY);
		if (StringUtils.isBlank(url)) {
			url = FRAMEWORK_DEFAUTL_SITE_WEB_URL;
		}
		return url;
	}

	public static String getFrameworkSiteLogUrl() {
		String url = System.getProperty(FRAMEWORK_SITE_LOG_URL_CONF_KEY);
		if (StringUtils.isBlank(url)) {
			url = FRAMEWORK_DEFAUTL_SITE_LOG_URL;
		}
		return url;
	}

}
