package com.openjad.common.util;

import static com.openjad.common.constant.CharacterConstants.DOT_CHARACTER;
import static com.openjad.common.constant.CharacterConstants.EXCLAMATION_CHARACTER;
import static com.openjad.common.constant.CharacterConstants.HYPHEN_CHARACTER;
import static com.openjad.common.constant.CharacterConstants.LEFT_SLASH_CHARACTER;
import static com.openjad.common.constant.DefaultValueConstants.DEF_COLLECT_CAPACITY;
import static com.openjad.common.constant.DirConstants.BIN_DIR;
import static com.openjad.common.constant.DirConstants.USER_DOT_DIR;
import static com.openjad.common.constant.ExtensionConstants.JAR_EXTENSIONS;
import static com.openjad.common.constant.PropertiyConstants.FRAMEWORK_DEFAUTL_SITE_LOG_URL;
import static com.openjad.common.constant.PropertiyConstants.FRAMEWORK_DEFAUTL_SITE_WEB_URL;
import static com.openjad.common.constant.PropertiyConstants.FRAMEWORK_SITE_LOG_URL_CONF_KEY;
import static com.openjad.common.constant.PropertiyConstants.FRAMEWORK_SITE_WEB_URL_CONF_KEY;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;

import com.openjad.common.spring.boot.AbstractApplicationRunListener;

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

	public static void main(String[] args) {
		String basePath = "file:/root/biz/jad-book-gate/jad-book-gate-1.0.1-RELEASE.jar!/BOOT-INF/lib/openjad-common-context-2.0.3-RELEASE.jar!/";
		try {
			basePath = URLDecoder.decode(basePath, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		basePath = basePath.replaceAll("/target/classes/", "");

		if (basePath.contains("!/BOOT-INF/")) {
			basePath = basePath.substring(0, basePath.indexOf("!/BOOT-INF/"));
		}

		if (basePath.endsWith(JAR_EXTENSIONS)) {
			if (basePath.indexOf(HYPHEN_CHARACTER) > 0) {
				basePath = basePath.substring(basePath.lastIndexOf(LEFT_SLASH_CHARACTER) + 1, basePath.lastIndexOf(HYPHEN_CHARACTER));
				if (basePath.indexOf(HYPHEN_CHARACTER) > 0) {
					basePath = basePath.substring(basePath.lastIndexOf(LEFT_SLASH_CHARACTER) + 1, basePath.lastIndexOf(HYPHEN_CHARACTER));
				}
			} else {
				basePath = basePath.substring(basePath.lastIndexOf(LEFT_SLASH_CHARACTER) + 1, basePath.lastIndexOf(DOT_CHARACTER));
			}
		} else {
			if (basePath.indexOf(LEFT_SLASH_CHARACTER) >= 0) {
				basePath = basePath.substring(basePath.lastIndexOf(LEFT_SLASH_CHARACTER) + 1);
			}
		}
		System.out.println("basePath:" + basePath);
	}

	/**
	 * 获取项目名称
	 * 
	 * @return 结果
	 */
	public static String readProjectName() {
//		System.out.println("准备获取项目名称=================");

		Class mainClazz = null;
		try {
			mainClazz = SysInitArgUtils.deduceMainApplicationClass();
			if(mainClazz==null) {
				mainClazz = SysInitArgUtils.class;
			}
		} catch (Exception e1) {
			mainClazz = SysInitArgUtils.class;
		}
		String basePath = mainClazz.getProtectionDomain().getCodeSource().getLocation().getPath();
//		System.out.println("首次获取到basePath:" + basePath);
//		首次获取到basePath:file:/root/biz/jad-book-gate/jad-book-gate-1.0.1-RELEASE.jar!/BOOT-INF/lib/openjad-common-context-2.0.3-RELEASE.jar!/
//		/E:/my/workspace/myworkspace2020/openjad-parent/openjad-framework/openjad-common/openjad-common-context
		try {
			basePath = URLDecoder.decode(basePath, "utf-8");
		} catch (UnsupportedEncodingException e) {
			//e.printStackTrace();
		}
		basePath = basePath.replaceAll("/target/classes/", "");

		//20210228
		if (basePath.contains("!/BOOT-INF/")) {
			basePath = basePath.substring(0, basePath.indexOf("!/BOOT-INF/"));
		}

		if (basePath.endsWith(JAR_EXTENSIONS)) {
			if (basePath.indexOf(HYPHEN_CHARACTER) > 0) {
				basePath = basePath.substring(basePath.lastIndexOf(LEFT_SLASH_CHARACTER) + 1, basePath.lastIndexOf(HYPHEN_CHARACTER));
				if (basePath.indexOf(HYPHEN_CHARACTER) > 0) {
					basePath = basePath.substring(basePath.lastIndexOf(LEFT_SLASH_CHARACTER) + 1, basePath.lastIndexOf(HYPHEN_CHARACTER));
				}
			} else {
				basePath = basePath.substring(basePath.lastIndexOf(LEFT_SLASH_CHARACTER) + 1, basePath.lastIndexOf(DOT_CHARACTER));
			}
		} else {
			if (basePath.indexOf(LEFT_SLASH_CHARACTER) >= 0) {
				basePath = basePath.substring(basePath.lastIndexOf(LEFT_SLASH_CHARACTER) + 1);
			}
		}
//		System.out.println("获取到basePath:" + basePath);
		return basePath;
	}

	/**
	 * 获取当前运行main函数所在类
	 * 
	 * @return
	 */
	public static Class<?> deduceMainApplicationClass() {
		StackTraceElement[] stackTrace = new RuntimeException().getStackTrace();
		for (StackTraceElement stackTraceElement : stackTrace) {
			if ("main".equals(stackTraceElement.getMethodName())) {
				try {
					Class mainclass = Class.forName(stackTraceElement.getClassName());
					mainclass.getDeclaredMethod("main", String[].class);
					return mainclass;
				} catch (Exception ex) {
				}
			}
		}
		return null;
	}

//	public static String getPackagesFromEnviron() {
//		ConfigurableEnvironment environment = AbstractApplicationRunListener.ENVIRONMENT;
//		return getPackagesFromEnviron(environment);
//	}

	/**
	 * 在没有配置基本扫描包的情况下自动推断 basePackage
	 * 
	 * @param beanFactory beanFactory
	 * @return basePackage
	 */
	public static Set<String> deduceScanPackage(BeanFactory beanFactory) {

		Set<String> basePackageSet = new HashSet();
		try {
			basePackageSet.addAll(getScanPackageFromBeanFactory(beanFactory));//通过 AutoConfigurationPackages 拿
		} catch (Exception e) {
			throw new RuntimeException("没有从beanFactory中获取到basePackage," + e.getMessage(), e);
		}
		Class mainClass = SysInitArgUtils.deduceMainApplicationClass();
		if (mainClass != null) {
			basePackageSet.addAll(getMainSpringBootApplicationPackage(mainClass));//通过 main类的 SpringBootApplication注解拿
			basePackageSet.addAll(getMainComponentScanPackage(mainClass));//通过 main类的 ComponentScan注解拿
			if (basePackageSet.isEmpty()) {
				basePackageSet.addAll(getMainPackage(mainClass));//通过 main类的 所在的包名拿
			}
		}
		basePackageSet = clearBasePackage(basePackageSet);
		return basePackageSet;
	}

	private static Set<String> getMainComponentScanPackage(Class mainClazz) {
		ComponentScan componentScanAnn = (ComponentScan) mainClazz.getAnnotation(ComponentScan.class);
		Set<String> basePackageList = new HashSet<String>();
		if (componentScanAnn != null) {
			String[] scanBasePackages = componentScanAnn.basePackages();
			Class<?>[] scanBasePackageClasses = componentScanAnn.basePackageClasses();
			basePackageList.addAll(Arrays.asList(scanBasePackages));
			for (Class<?> scanBasePackageClasse : scanBasePackageClasses) {
				basePackageList.add(ClassUtils.getPackageName(scanBasePackageClasse));
			}
		}
		return basePackageList;
	}

	private static Set<String> getMainPackage(Class mainClazz) {
		Set<String> basePackageList = new HashSet<String>();
		basePackageList.add(ClassUtils.getPackageName(mainClazz));
		return basePackageList;
	}

	private static Set<String> getMainSpringBootApplicationPackage(Class mainClazz) {
		Set<String> basePackageList = new HashSet<String>();
		try {
			SpringBootApplication[] annnotations = (SpringBootApplication[]) mainClazz.getAnnotationsByType(SpringBootApplication.class);
			if (annnotations != null && annnotations.length > 0) {
				for (SpringBootApplication annnotation : annnotations) {
					String[] scanBasePackages = annnotation.scanBasePackages();
					Class<?>[] scanBasePackageClasses = annnotation.scanBasePackageClasses();
					basePackageList.addAll(Arrays.asList(scanBasePackages));
					for (Class<?> scanBasePackageClasse : scanBasePackageClasses) {
						basePackageList.add(ClassUtils.getPackageName(scanBasePackageClasse));
					}
				}
			}

		} catch (Exception e) {
			throw new RuntimeException("尝试从类" + mainClazz.getName() + "上获取到基础扫描包失败," + e.getMessage(), e);
		}

		return basePackageList;

	}

	private static Set<String> clearBasePackage(Set<String> basePackageSet) {
		if (basePackageSet == null || basePackageSet.isEmpty()) {
			return basePackageSet;
		}

		Set<String> fullPackageSet = new HashSet<String>();
		for (String pack : basePackageSet) {
			fullPackageSet.add(pack.trim());//去掉可能存在的空格
		}

		Map<String, String> alreadyMap = new HashMap<String, String>();
		for (String pack : fullPackageSet) {
			alreadyMap.put(pack, pack);
			for (String pack2 : fullPackageSet) {
				if (!pack.equals(pack2) && pack.startsWith(pack2 + ".")) {
					alreadyMap.put(pack, pack2);//替换
				}
			}
		}
		Set<String> newSet = new HashSet();
		newSet.addAll(alreadyMap.values());
		return newSet;
	}

	private static Set<String> getScanPackageFromBeanFactory(BeanFactory beanFactory) {
		Set<String> set = new HashSet<String>();
		if (beanFactory == null) {
			return set;
		}
		List<String> packageList = AutoConfigurationPackages.get(beanFactory);
		if (packageList != null && !packageList.isEmpty()) {
			set.addAll(packageList);
		}
		return set;
	}

	public static String getPackagesFromEnviron(ConfigurableEnvironment environment) {
		String key = "openjad.application.basePackage";
		String basePackage = getPackagesFromEnviron(environment, key);
//		if (StringUtils.isBlank(basePackage)) {
//			key = "mybatis.basePackage";
//			basePackage = getPackagesFromEnviron(environment, key);
//		}
		return basePackage;
	}

	private static String getPackagesFromEnviron(ConfigurableEnvironment environment, String key) {

		SortedMap<String, Object> map = SysInitArgUtils.filterProperties(environment, key);

		if (map != null && !map.isEmpty() && map.get(key) != null) {
			return map.get(key).toString();
		}
		return null;
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

	/**
	 * 获取配置
	 * 
	 * @param environment
	 * @param prefix
	 * @return
	 */
	public static SortedMap<String, Object> filterProperties(ConfigurableEnvironment environment, String prefix) {

		SortedMap<String, Object> dubboProperties = new TreeMap<>();

		Map<String, Object> properties = doExtraProperties(environment);

		for (Map.Entry<String, Object> entry : properties.entrySet()) {
			String propertyName = entry.getKey();

			if (propertyName.startsWith(prefix)) {
				dubboProperties.put(propertyName, entry.getValue());
			}
		}

		return Collections.unmodifiableSortedMap(dubboProperties);

	}

}
